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
import org.elasticsearch.index.query.QueryStringQueryBuilder;
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
import java.util.Iterator;
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
        //QueryBuilder wildcardCodeQuery = QueryBuilders.wildcardQuery("title", content);
        /*QueryBuilder multiCodeQuery = QueryBuilders.multiMatchQuery(content,"title");*/
        disMaxQueryBuilder.add(ikTypeQuery);
        disMaxQueryBuilder.add(pinyinTypeQuery);
        //disMaxQueryBuilder.add(wildcardCodeQuery);
       /* disMaxQueryBuilder.add(multiCodeQuery);*/
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(disMaxQueryBuilder).build();
        Page<EsEntiy> search = esDao.search(searchQuery);
        return search;
    }

}

