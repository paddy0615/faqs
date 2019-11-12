package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 *2019/10/22
 */
@Data
@Document(indexName = "es_index2",type = "es_db")
public class EsEntiy implements Serializable {
    /**
     *     index：是否设置分词
     *     analyzer：存储时使用的分词器
     *           ik_smart 详细
     *           ik_max_word 不详细
     *     searchAnalyze：搜索时使用的分词器
     *     store：是否存储
     *     type: 数据类型
     *     http://localhost:9200/_analyze?analyzer=ik_smart&pretty=true&text=我爱我中国
     */

    @Id
    private Long id;

   /* @Field(type = FieldType.Text)*/
    @Field(type = FieldType.Text, searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
    private String title;  //详情标题

    @Field(type = FieldType.Text)
    private String contentTxt;  //详情内容纯文本,用于搜索

    @Field(type = FieldType.Text)
    private long status;


    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentTxt() {
        return contentTxt;
    }

    public void setContentTxt(String contentTxt) {
        this.contentTxt = contentTxt;
    }
}

