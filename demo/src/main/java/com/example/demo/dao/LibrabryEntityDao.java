package com.example.demo.dao;

import com.example.demo.entity.LibrabryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Component(value = "librabryEntityDao")
public interface LibrabryEntityDao extends JpaRepository<LibrabryEntity,Long> {

    @Query(value = "SELECT " +
            " d.dl_id," +
            " d.dl_title," +
            " d.dl_lang_id," +
            " d.dl_updatedate," +
            " d.dl_status," +
            " fl.fl_title," +
            " l.lang_title" +
            " FROM faqs_detailed d,faqs_librabry fl,faqs_language l" +
            " WHERE d.dl_fl_id = fl.fl_id AND d.dl_lang_id = l.lang_id" +
            " AND if(:fl_id > 0,fl.fl_id = :fl_id,1=1)"+
            " AND if(:langId > 0,d.dl_lang_id = :langId,1=1)"+
            " AND if(:dl_status >= 0,d.dl_status = :dl_status,1=1)"+
            " ORDER BY fl.fl_id,l.lang_id",nativeQuery = true)
    List<LibrabryEntity> getLibrabryEntity(@Param("fl_id") long fl_id,@Param("langId") long langId,@Param("dl_status") long dl_status);
}
