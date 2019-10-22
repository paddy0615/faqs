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
@Document(indexName = "es_index",type = "es_db")
public class EsEntiy implements Serializable {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String title;  //详情标题
    @Field(type = FieldType.Text)
    private String content;  //详情内容带html
    @Field(type = FieldType.Text)
    private String contentTxt;  //详情内容纯文本,用于搜索


}

