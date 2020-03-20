package com.example.demo.dao;

import com.example.demo.bean.Folder_tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "folder_tagsDao")
public interface Folder_tagsDao extends JpaRepository<Folder_tags,Long> {

    void deleteById(long id);

    @Modifying
    @Query(value ="DELETE FROM folder_tags WHERE  ft_id IN(" +
            "   SELECT ftr_ft_id FROM folder_tags_relation" +
            "   WHERE ftr_f_id IN (" +
            "       SELECT f_id FROM folder" +
            "       WHERE f_key_random = :key"+
            "       )" +
            "   )"
            ,nativeQuery = true)
    void deleteBykey(@Param("key") long key);
}
