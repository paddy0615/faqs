package com.example.demo.dao;

import com.example.demo.bean.DetailedNoTags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "detailedNoTagsDao")
public interface DetailedNoTagsDao extends JpaRepository<DetailedNoTags,Long> {

    DetailedNoTags findByTitle(String s);

    @Query(value = "SELECT f.nt_id,f.nt_ip,DATE_FORMAT(f.nt_createdate,'%Y-%m-%d %H:%i:%s') AS 'ny',l.lang_title,f.nt_title,f.nt_count" +
            " FROM faqs_no_tags f,faqs_language l" +
            " WHERE f.nt_lang_id = l.lang_id"+
            " AND if(:langId > 0,f.nt_lang_id = :langId,1=1)"+
            " AND if(:startTime != '',f.nt_createdate > :startTime,1=1)"+
            " AND if(:endTime != '',f.nt_createdate <= :endTime,1=1)"+
            " ORDER BY f.nt_createdate DESC"
            ,countQuery="SELECT COUNT(*)"+
            " FROM faqs_no_tags f,faqs_language l" +
            " WHERE f.nt_lang_id = l.lang_id"+
            " AND if(:langId > 0,f.nt_lang_id = :langId,1=1)"+
            " AND if(:startTime != '',f.nt_createdate > :startTime,1=1)"+
            " AND if(:endTime != '',f.nt_createdate <= :endTime,1=1)"+
            " ORDER BY f.nt_createdate DESC"
            , nativeQuery = true)
    Page<Object[]> getAllByDNTs(@Param("langId") long langId,@Param("startTime") String startTime, @Param("endTime") String endTime, Pageable pageable);


}
