package com.example.demo.dao;

import com.example.demo.bean.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component(value = "categoryDao")
public interface CategoryDao extends JpaRepository<Category,Long> {

    Category findById(long catId);
    // 按id获取类别集合
    List<Category> findByLangId(long longId);

    // 按id获取类别集合,排序top
    List<Category> findAllByLangIdOrderByOrdertopdateDesc(long longId);

    int countByLangId(long id);

    List<Category> findAllByTitleContaining(String s);

    @Modifying
    @Query("update Category c set c.status = :status , c.updateDate = :date where c.id = :catId")
    void editStatus(@Param("catId") long catId, @Param("status")long status , @Param("date")Date date);

    @Modifying
    @Query("update Category c set c.ordertopdate = :date where c.id = :catId")
    void saveTop(@Param("catId") long catId,@Param("date")Date date);

}
