package com.example.demo.service;

import com.example.demo.dao.EsDao;
import com.example.demo.entity.EsEntiy;
import com.example.demo.entity.EsHighlight;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *2019/10/22
 */
@Service
public class EsService {

    /*@Autowired*/
    @Resource
    private EsDao esDao;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /*
     * index 索引index
     * text 需要被分析的词语
     * 默认使用中文ik_smart分词
     * */
    public String[] getAnalyzes(String index,String text){
        //调用ES客户端分词器进行分词
        AnalyzeRequestBuilder ikRequest = new AnalyzeRequestBuilder(elasticsearchTemplate.getClient(),
                AnalyzeAction.INSTANCE,index,text).setAnalyzer("ik_smart");
        List<AnalyzeResponse.AnalyzeToken> ikTokenList = ikRequest.execute().actionGet().getTokens();

        // 赋值
        List<String> searchTermList = new ArrayList<>();
        ikTokenList.forEach(ikToken -> { searchTermList.add(ikToken.getTerm()); });

        return searchTermList.toArray(new String[searchTermList.size()]);
    }


    /**
     * 保存
     * @param esEntiy
     * @return
     */
    public EsEntiy save(EsEntiy esEntiy){
        return esDao.save(esEntiy);
    }

    /**
     * 查询单个
     * @param code
     * @return
     */
    public EsEntiy findbyEs(long code){
        return esDao.findById(code).orElse(null);
    }

    /**
     * 查询所有
     * @return
     */
    public List<EsEntiy> findAll(){
        List<EsEntiy> list = esDao.findAll();
        return list;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public void delete(long id) {
        esDao.deleteById(id);
    }

    /**
     * matchQuery       : 单个字段查询
     * matchAllQuery    : 匹配所有
     * multiMatchQuery  : 多个字段匹配某一个值
     * wildcardQuery    : 模糊查询
     * boost            : 设置权重,数值越大权重越大
     * 混合搜索
     * @param content
     * @return
     */
    public Page<EsEntiy> querySearch(String content){
        DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();
        QueryBuilder ikTypeQuery = QueryBuilders.matchQuery("title", content);
        QueryBuilder pinyinTypeQuery = QueryBuilders.matchQuery("title.pinyin", content);
        QueryBuilder wildcardCodeQuery = QueryBuilders.wildcardQuery("title", content);
        /*QueryBuilder multiCodeQuery = QueryBuilders.multiMatchQuery(content,"title");*/
        disMaxQueryBuilder.add(ikTypeQuery);
        disMaxQueryBuilder.add(pinyinTypeQuery);
        disMaxQueryBuilder.add(wildcardCodeQuery);
       /* disMaxQueryBuilder.add(multiCodeQuery);*/
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(disMaxQueryBuilder).build();
        Page<EsEntiy> search = esDao.search(searchQuery);
        return search;
    }

    /**
     * 高亮检索
     * @param type
     * @return
     */
    public AggregatedPage<EsEntiy> querySearchType(String type){
        DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();
        QueryBuilder ikTypeQuery = QueryBuilders.matchQuery("title", type);
        QueryBuilder pinyinTypeQuery = QueryBuilders.matchQuery("title.pinyin", type);
        QueryBuilder wildcardCodeQuery = QueryBuilders.wildcardQuery("title", type);
  /*      QueryBuilder ikTypeQuery1 = QueryBuilders.wildcardQuery("title", type).boost(2f);
        QueryBuilder ikCodeQuery = QueryBuilders.wildcardQuery("title", type).boost(2f);*/
        List<String> highlightFields = new ArrayList<String>();
        highlightFields.add("title");
        highlightFields.add("title");
        HighlightBuilder.Field[] fields = new HighlightBuilder.Field[highlightFields.size()];
        for (int x = 0; x < highlightFields.size(); x++) {
            fields[x] = new HighlightBuilder.Field(highlightFields.get(x)).preTags(EsHighlight.HIGH_LIGHT_START_TAG)
                    .postTags(EsHighlight.HIGH_LIGHT_END_TAG);
        }
        disMaxQueryBuilder.add(ikTypeQuery);
        disMaxQueryBuilder.add(pinyinTypeQuery);
        disMaxQueryBuilder.add(wildcardCodeQuery);
     /*   disMaxQueryBuilder.add(ikTypeQuery1);
        disMaxQueryBuilder.add(ikCodeQuery);*/
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(disMaxQueryBuilder)
                .withHighlightFields(fields)
                //.withPageable(PageRequest.of(1, 10))
                .build();
        //不需要高亮就直接分页返回
        //Page<EsEntiy> esEntiys = esDao.search(searchQuery);
        //高亮显示
        AggregatedPage<EsEntiy> esEntiys = elasticsearchTemplate.queryForPage(searchQuery, EsEntiy.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                pageable = PageRequest.of(1, 10);
                List<EsEntiy> chunk = new ArrayList<>();
                for (SearchHit searchHit : searchResponse.getHits()) {
                    if (searchResponse.getHits().getHits().length <= 0) {
                        return null;
                    }
                    EsEntiy esEntiy = new EsEntiy();
                    esEntiy.setId(Long.parseLong(searchHit.getId()));
                    //esEntiy.setEsIndex(searchHit.getIndex());
                    //name or memoe
                    HighlightField code = searchHit.getHighlightFields().get("title");
                    if (code != null) {
                        esEntiy.setTitle(code.fragments()[0].toString());
                    } else {
                        Object esCode = searchHit.getSourceAsMap().get("title");
                        if(esCode == null)
                            esEntiy.setTitle("");
                        else
                            esEntiy.setTitle(esCode.toString());
                    }
                 /*   HighlightField type = searchHit.getHighlightFields().get("esType");
                    if (type != null) {
                        esEntiy.setEsType(type.fragments()[0].toString());
                    }else {
                        Object esType = searchHit.getSourceAsMap().get("esType");
                        if(esType == null )
                            esEntiy.setEsType("");
                        else
                            esEntiy.setEsType(esType.toString());
                    }*/
                    chunk.add(esEntiy);
                }
                if (chunk.size() > 0) {
                    return  new AggregatedPageImpl<>((List<T>) chunk);
                }
                return null;
            }
        });

        return esEntiys;
    }
}

