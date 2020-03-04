package com.example.demo.dao;

import com.example.demo.bean.Folder_tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component(value = "folder_tagsDao")
public interface Folder_tagsDao extends JpaRepository<Folder_tags,Long> {

    void deleteById(long id);

}
