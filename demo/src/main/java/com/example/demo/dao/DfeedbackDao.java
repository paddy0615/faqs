package com.example.demo.dao;

import com.example.demo.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "dfeedbackDao")
public interface DfeedbackDao extends JpaRepository<Feedback,Long> {

    @Query(value = "SELECT f.df_id AS id,\n" +
            "f.df_type AS df_type,\n" +
            "d.dl_id AS dl_id,\n" +
            "d.dl_title AS dl_title,\n" +
            "f.df_ip AS ip,\n" +
            "f.df_nay_content AS df_content,\n" +
            "null AS lang_title,\n" +
            "null AS cat_title,\n" +
            "f.df_createdate AS df_createdate\n" +
            "FROM faqs_detailed_feedback f,faqs_detailed d\n" +
            "WHERE f.df_dl_id = d.dl_id\n"+
            "ORDER BY f.df_createdate DESC"
            , nativeQuery = true)
    List<Feedback> getAllBy();

    @Query(value = "SELECT f.df_id AS id,\n" +
            "f.df_type AS df_type,\n" +
            "d.dl_id AS dl_id,\n" +
            "d.dl_title AS dl_title,\n" +
            "f.df_ip AS ip,\n" +
            "f.df_nay_content AS df_content,\n" +
            "null AS lang_title,\n" +
            "null AS cat_title,\n" +
            "f.df_createdate AS df_createdate\n" +
            "FROM faqs_detailed_feedback f,faqs_detailed d\n" +
            "WHERE f.df_dl_id = d.dl_id\n"+
            "AND f.df_type = :type\n"+
            "ORDER BY f.df_createdate DESC"
            , nativeQuery = true)
    List<Feedback> getAllByDfType(@Param("type") long type);

    @Query(value = "SELECT f.df_id AS id,\n" +
            "f.df_type AS df_type,\n" +
            "d.dl_id AS dl_id,\n" +
            "d.dl_title AS dl_title,\n" +
            "l.lang_title AS lang_title,\n" +
            "c.cat_title AS cat_title,\n" +
            "f.df_ip AS ip,\n" +
            "f.df_nay_content AS df_content,\n" +
            "f.df_createdate AS df_createdate\n" +
            "FROM faqs_detailed_feedback f,faqs_detailed d,\n" +
            "faqs_language l,faqs_category c\n" +
            "WHERE f.df_dl_id = d.dl_id AND d.dl_lang_id = l.lang_id AND d.dl_cat_id = c.cat_id\n" +
            "AND f.df_id  = :id"
            , nativeQuery = true)
    Feedback getAllById(@Param("id") long id);

}
