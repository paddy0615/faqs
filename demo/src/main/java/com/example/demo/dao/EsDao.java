package com.example.demo.dao;

import com.example.demo.entity.EsEntiy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import java.util.List;

//@Repository
@Component(value = "esDao")
public interface EsDao extends ElasticsearchRepository<EsEntiy,Long> {

    List<EsEntiy> findAll();
}
