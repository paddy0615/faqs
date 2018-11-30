package com.example.demo.dao;

import com.example.demo.entity.DetailedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "detailedEntityDao")
public interface DetailedEntityDao extends JpaRepository<DetailedEntity,Long> {

    @Query(value = "SELECT a.dl_id ,a.dl_title FROM (\n" +
            "SELECT d.dl_id,d.dl_title,COUNT(d.dl_id) AS cnt FROM  faqs_detailed d\n" +
            "INNER JOIN faqs_dtags_relation dr ON (dr.dr_dl_id = d.dl_id)\n" +
            "INNER JOIN faqs_detailed_tags dt ON (dr.dr_dt_id = dt.dt_id)\n" +
            "WHERE d.dl_status = 1 AND dt.dt_title IN(:searchs)\n" +
            "GROUP BY d.dl_id\n" +
            "ORDER BY cnt DESC\n" +
            ") a", nativeQuery = true)
    List<DetailedEntity> getSearchTags(@Param("searchs")List<String> searchs);

}
