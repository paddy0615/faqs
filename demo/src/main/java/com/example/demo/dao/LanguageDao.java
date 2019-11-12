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
    List<Object[]> getAllObjects(@Param("langId")  long langId,@Param("s")  String s,@Param("e")  String e);


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
    Page<Object[]> monitorPage(@Param("langId")  long langId, @Param("s")  String s, @Param("e")  String e, Pageable pageable);



    @Query(value ="SELECT \n" +
            "fl_title AS '序号',\n" +
            "lang_title AS '语言',\n" +
            "dl_title AS 'FAQ标题',\n" +
            "dl_contenttxt AS 'FAQ问题内容',\n" +
            "CASE WHEN df_type=1 THEN '加1'ELSE '减1' END AS 'Rating',\n" +
            "df_createdate AS '创建时间',\n" +
            "df_nay_content AS '评价内容',\n" +
            "CASE WHEN df_nay_email IS NULL THEN '' ELSE df_nay_email END AS '评价邮件',\n" +
            "CASE WHEN df_nay_number IS NULL THEN '' ELSE df_nay_number END AS '评价电话'\n" +
            "FROM faqs_detailed_feedback \n" +
            "INNER JOIN faqs_detailed ON dl_id = df_dl_id\n" +
            "INNER JOIN faqs_librabry ON fl_id = dl_fl_id\n" +
            "INNER JOIN faqs_language ON lang_id = dl_lang_id\n" +
            "WHERE 1=1 \n" +
            "AND dl_lang_id = \n"+
            "AND df_createdate BETWEEN :s AND :e\n" +
            "ORDER BY df_type,dl_lang_id,df_createdate DESC"
            ,nativeQuery = true)
    List<Object[]> getAllObjects1(@Param("s")  String s,@Param("e")  String e);

}
