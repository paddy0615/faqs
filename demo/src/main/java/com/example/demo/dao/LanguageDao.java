package com.example.demo.dao;

import com.example.demo.bean.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "languageDao")
public interface LanguageDao extends JpaRepository<Language,Long> {

    Language findById(long langId);

    List<Language> findAllByTitleContaining(String s);

    @Query("select title from Language where id = :langId")
    String getByTitle(@Param("langId")  long langId);

    /**
     *  all
     */
    @Query(value =" SELECT ym AS '年月',SUM(sum1) AS '数量' FROM (" +
            " SELECT SUM(1) AS sum1, DATE_FORMAT(m_createdate, '%Y-%m') AS 'ym'" +
            " FROM faqs_monitor" +
            " WHERE m_dl_id > 0" +
            " GROUP BY DATE_FORMAT(m_createdate, '%Y-%m')" +
            " UNION ALL" +
            " SELECT SUM(1) AS sum1, DATE_FORMAT(m_createdate, '%Y-%m') AS 'ym'" +
            " FROM e_form_monitor" +
            " GROUP BY DATE_FORMAT(m_createdate, '%Y-%m')" +
            " ) a" +
            " GROUP BY ym"
            ,nativeQuery = true)
    List<Object[]> allPage();

    /**
     *  all-Question
     */
    @Query(value =" SELECT DATE_FORMAT(m_createdate, '%Y-%m') AS 'ym',SUM(1) AS sum1" +
            " FROM faqs_monitor" +
            " WHERE m_dl_id > 0" +
            " GROUP BY DATE_FORMAT(m_createdate, '%Y-%m')"
            ,nativeQuery = true)
    List<Object[]> allPage1();

    /**
     *  all-E-from
     */
    @Query(value =" SELECT DATE_FORMAT(m_createdate, '%Y-%m') AS 'ym',SUM(1) AS sum1" +
            " FROM e_form_monitor" +
            " GROUP BY DATE_FORMAT(m_createdate, '%Y-%m')"
            ,nativeQuery = true)
    List<Object[]> allPage2();



    /**
     *  获取Question全部点击率 - 报表
     */
    @Query(value =" SELECT " +
            " fl_title AS '父级名'," +
            " d.dl_title AS 'FAQ标题'," +
            " CASE WHEN d.dl_status = 1 THEN 'Show' ELSE 'Hide' END AS '发布状态'," +
            " CASE WHEN cnt IS NULL THEN '0' ELSE cnt END AS '点击率'" +
            " FROM faqs_detailed d " +
            " LEFT JOIN (" +
            "  SELECT m.m_dl_id AS 'id',COUNT(m.m_dl_id) AS 'cnt'  FROM faqs_monitor m" +
            "  WHERE m.m_dl_id != 0 " +
            "  AND if(:s != '',m.m_createdate > :s,1=1)"+
            "  AND if(:e != '',m.m_createdate <= :e,1=1)"+
            "  GROUP BY m.m_dl_id " +
            " ) a  ON d.dl_id = a.id" +
            " INNER JOIN faqs_librabry ON d.dl_fl_id = fl_id" +
            " WHERE 1=1" +
            " AND if(:langId > 0,d.dl_lang_id = :langId,1=1)"+
            " ORDER BY cnt DESC"
            ,nativeQuery = true)
    List<Object[]> monitorQuestionReport(@Param("langId")  long langId,@Param("s")  String s,@Param("e")  String e);


    /**
     *  获取Question全部点击率 - 分页
     */
    @Query(value =" SELECT " +
            " fl_title AS '父级名'," +
            " d.dl_title AS 'FAQ标题'," +
            " CASE WHEN d.dl_status = 1 THEN 'Show' ELSE 'Hide' END AS '发布状态'," +
            " CASE WHEN cnt IS NULL THEN '0' ELSE cnt END AS '点击率'" +
            " FROM faqs_detailed d " +
            " LEFT JOIN (" +
            "  SELECT m.m_dl_id AS 'id',COUNT(m.m_dl_id) AS 'cnt'  FROM faqs_monitor m" +
            "  WHERE m.m_dl_id != 0 " +
            "  AND if(:s != '',m.m_createdate > :s,1=1)"+
            "  AND if(:e != '',m.m_createdate <= :e,1=1)"+
            "  GROUP BY m.m_dl_id " +
            " ) a  ON d.dl_id = a.id" +
            " INNER JOIN faqs_librabry ON d.dl_fl_id = fl_id" +
            " WHERE 1=1" +
            " AND if(:langId > 0,d.dl_lang_id = :langId,1=1)"+
            " ORDER BY cnt DESC"
            ,countQuery="SELECT COUNT(*)"+
            " FROM faqs_detailed d " +
            " LEFT JOIN (" +
            "  SELECT m.m_dl_id AS 'id',COUNT(m.m_dl_id) AS 'cnt'  FROM faqs_monitor m" +
            "  WHERE m.m_dl_id != 0 " +
            "  AND if(:s != '',m.m_createdate > :s,1=1)"+
            "  AND if(:e != '',m.m_createdate <= :e,1=1)"+
            "  GROUP BY m.m_dl_id " +
            " ) a  ON d.dl_id = a.id" +
            " INNER JOIN faqs_librabry ON d.dl_fl_id = fl_id" +
            " WHERE 1=1" +
            " AND if(:langId > 0,d.dl_lang_id = :langId,1=1)"+
            " ORDER BY cnt DESC"
            ,nativeQuery = true)
    Page<Object[]> monitorQuestionPage(@Param("langId")  long langId, @Param("s")  String s, @Param("e")  String e, Pageable pageable);


    /**
     *  获取Eform全部点击率 - 分页/报表
     */
    @Query(value ="  SELECT m.m_et_id AS 'id',t.et_title_en AS 'title',COUNT(m.m_et_id) AS 'cnt' " +
            "  FROM e_form_monitor m,e_form_type t" +
            "  WHERE m.m_et_id = t.et_id " +
            "  AND if(:langId > 0,m.m_lang_id = :langId,1=1)"+
            "  AND if(:s != '',m.m_createdate > :s,1=1)"+
            "  AND if(:e != '',m.m_createdate <= :e,1=1)"+
            "  GROUP BY m.m_et_id"
            ,nativeQuery = true)
    List<Object[]> monitorEformPage(@Param("langId")  long langId, @Param("s")  String s, @Param("e")  String e);

    /**
     *  获取FAQ全部详细内容 - 分页/报表
     */
    @Query(value ="  SELECT m.m_et_id AS 'id',t.et_title_en AS 'title',COUNT(m.m_et_id) AS 'cnt' " +
            "  FROM e_form_monitor m,e_form_type t" +
            "  WHERE m.m_et_id = t.et_id " +
            "  AND if(:langId > 0,m.m_lang_id = :langId,1=1)"+
            "  AND if(:s != '',m.m_createdate > :s,1=1)"+
            "  AND if(:e != '',m.m_createdate <= :e,1=1)"+
            "  GROUP BY m.m_et_id"
            ,nativeQuery = true)
    List<Object[]> monitorFaq(@Param("langId")  long langId);


    @Query(value =" SELECT  GROUP_CONCAT(dt.dt_title) AS 'dt_title' FROM faqs_dtags_relation dr ,faqs_detailed_tags dt" +
            " WHERE dr.dr_dt_id = dt.dt_id" +
            " AND dr.dr_dl_id = :id" +
            " ORDER BY dr.dr_order" ,nativeQuery = true)
    String getDetailedTagsTitle(@Param("id")  long id);

}
