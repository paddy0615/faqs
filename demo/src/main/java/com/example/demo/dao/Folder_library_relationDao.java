package com.example.demo.dao;

import com.example.demo.bean.Folder_library_relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "folder_library_relation")
public interface Folder_library_relationDao extends JpaRepository<Folder_library_relation,Long> {

    /**
     * folder library 页面初始化
     */
    @Query(value ="SELECT r.flr_parenid,l.fl_id,l.fl_title,DATE_FORMAT(r.flr_createdate, '%Y-%m-%d %H:%i') AS 'createdate' FROM folder_library_relation r,faqs_librabry l" +
            " WHERE r.flr_fl_id = l.fl_id" +
            " AND if(:parenId > 0,r.flr_parenid IN (:parenId),1=1)"
            ,nativeQuery = true)
    List<Object[]> getFolderLibraryPage(@Param("parenId") long parenId);

    void deleteByPfid(long id);
}
