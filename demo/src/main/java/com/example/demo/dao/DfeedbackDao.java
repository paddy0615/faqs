package com.example.demo.dao;

import com.example.demo.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "dfeedbackDao")
public interface DfeedbackDao extends JpaRepository<Feedback,Long> {

    @Query(value = "SELECT f.df_id AS id," +
            " f.df_type AS df_type," +
            " d.dl_id AS dl_id," +
            " d.dl_title AS dl_title," +
            " f.df_ip AS ip," +
            " f.df_nay_content AS df_content," +
            " NULL AS lang_title," +
            " NULL AS cat_title," +
            " f.df_createdate AS df_createdate" +
            " FROM faqs_detailed_feedback f" +
            " INNER JOIN faqs_detailed d ON f.df_dl_id = d.dl_id" +
            " ORDER BY f.df_createdate DESC"
            ,countQuery="SELECT COUNT(*)" +
            " FROM faqs_detailed_feedback f" +
            " INNER JOIN faqs_detailed d ON f.df_dl_id = d.dl_id"
            , nativeQuery = true)
    Page<Feedback> getAllBy(Pageable pageable);

    @Query(value = "SELECT f.df_id AS id," +
            " f.df_type AS df_type," +
            " d.dl_id AS dl_id," +
            " d.dl_title AS dl_title," +
            " f.df_ip AS ip," +
            " f.df_nay_content AS df_content," +
            " null AS lang_title," +
            " null AS cat_title," +
            " f.df_createdate AS df_createdate" +
            " FROM faqs_detailed_feedback f" +
            " INNER JOIN faqs_detailed d ON f.df_dl_id = d.dl_id" +
            " WHERE f.df_type = :type"+
            " ORDER BY f.df_createdate DESC"
            ,countQuery="SELECT COUNT(*)" +
            " FROM faqs_detailed_feedback f" +
            " INNER JOIN faqs_detailed d ON f.df_dl_id = d.dl_id"+
            " WHERE f.df_type = :type"
            , nativeQuery = true)
    Page<Feedback> getAllByDfType(@Param("type") long type,Pageable pageable);

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
            "AND if(:type > 0,f.df_type = :type,1=1)\n"+
            "AND if(:startTime != '',f.df_createdate > :startTime,1=1)\n"+
            "AND if(:endTime != '',f.df_createdate <= :endTime,1=1)\n"+
            "ORDER BY f.df_createdate DESC"
            , nativeQuery = true)
    List<Feedback> getAllByDfType1(@Param("type") long type,@Param("startTime") String startTime,@Param("endTime") String endTime);

}
