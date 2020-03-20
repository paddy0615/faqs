package com.example.demo.dao;

import com.example.demo.bean.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "folderDao")
public interface FolderDao extends JpaRepository<Folder,Long> {

    @Query(value = " SELECT COUNT(*) FROM folder WHERE f_key_random = :s",nativeQuery = true)
    int countByRandom(@Param("s") int s);


    @Query(value = " SELECT t.ft_tags FROM folder_tags_relation r,folder_tags t" +
            " WHERE r.ftr_ft_id = t.ft_id" +
            " AND r.ftr_f_id = :fid" +
            " ORDER BY ftr_order"
            ,nativeQuery = true)
    String [] getTagsAllByFid(@Param("fid") long fid);


    @Query(value = " SELECT pf.* FROM  folder pf WHERE pf.f_key_random = :key_random",nativeQuery = true)
    List<Folder> getFolderAllByKey_random(@Param("key_random") long key_random);


    void deleteById(long id);

    @Modifying
    @Query(value = " DELETE FROM folder WHERE f_key_random = :key",nativeQuery = true)
    void deleteBykey(@Param("key") long key);
}
