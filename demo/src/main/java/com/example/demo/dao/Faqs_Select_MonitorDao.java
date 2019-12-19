package com.example.demo.dao;

import com.example.demo.bean.Faqs_Select_Monitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "faqs_Select_MonitorDao")
public interface Faqs_Select_MonitorDao extends JpaRepository<Faqs_Select_Monitor,Long> {

    @Query(value = "SELECT m.m_id,m.m_clientip,l.lang_title,m.m_selete,DATE_FORMAT(m.m_createdate, '%Y-%m-%d %H:%i:%s') as createdate,m.m_crm_uid" +
            " FROM faqs_select_monitor m,faqs_language l" +
            " WHERE m.m_lang_id = l.lang_id" +
            " AND if(:langId > 0,m.m_lang_id = :langId,1=1)"+
            " AND if(:startTime != '',m.m_createdate > :startTime,1=1)"+
            " AND if(:endTime != '',m.m_createdate <= :endTime,1=1)"+
            " AND if(:search != '',m.m_selete like CONCAT('%',:search,'%'),1=1)"+
            " ORDER BY m.m_createdate DESC"
            ,countQuery="SELECT COUNT(*)"+
            " FROM faqs_select_monitor m,faqs_language l" +
            " WHERE m.m_lang_id = l.lang_id" +
            " AND if(:langId > 0,m.m_lang_id = :langId,1=1)"+
            " AND if(:startTime != '',m.m_createdate > :startTime,1=1)"+
            " AND if(:endTime != '',m.m_createdate <= :endTime,1=1)"+
            " AND if(:search != '',m.m_selete like CONCAT('%',:search,'%'),1=1)"+
            " ORDER BY m.m_createdate DESC"
            , nativeQuery = true)
    Page<Object[]> getSearchCollectionPage(@Param("langId") long langId, @Param("startTime") String startTime, @Param("endTime") String endTime,@Param("search")String search, Pageable pageable);


    /**
     *  all-Question
     */
    @Query(value =" SELECT DATE_FORMAT(m_createdate, '%Y-%m') AS 'ym',SUM(1) AS sum1" +
            " FROM faqs_select_monitor" +
            " GROUP BY DATE_FORMAT(m_createdate, '%Y-%m')"
            ,nativeQuery = true)
    List<Object[]> allPage();

}
