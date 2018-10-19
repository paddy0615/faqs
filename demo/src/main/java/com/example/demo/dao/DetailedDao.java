package com.example.demo.dao;

import com.example.demo.bean.Detailed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component(value = "detailedDao")
@Transactional(readOnly = true)
public interface DetailedDao extends JpaRepository<Detailed,Long> {
    Detailed findById(long id);

    List<Detailed> findAllByLangIdAndCatId(long longId,long catId);

    List<Detailed> findAllByLangIdAndCatIdAndStatus(long longId,long catId,long status);

    @Query("select new Detailed(d.id,d.title) from Detailed d where d.langId = :langId  and (d.title like %:search%  or d.contentTxt like %:search%) and d.status = 1")
    List<Detailed> getSearch(@Param("langId")long langId, @Param("search")String search);

    List<Detailed> findAllByTitleContaining(String s);
    List<Detailed> findAllByStatusAndTitleContaining(long status,String s);

    int countByCatId(long id);

    // 按搜索点击数量获取
    @Query("select new Detailed(d.id,d.title) from Detailed d,Hotspot h where d.id = h.dlId and d.status = 1  order by h.searchCount desc ")
    List<Detailed> getHpSearchCount();

    List<Detailed> findAllByCatId(long catId);


    @Modifying
    @Query("update Detailed d set d.status = :status , d.updateDate = :date where d.id = :dlId")
    void editStatus(@Param("dlId")long dlId, @Param("status")long status , @Param("date")Date date);

    @Modifying
    @Query("update Detailed d set d.orderTopDate = :date where d.id = :dlId")
    void saveTop(@Param("dlId")long dlId,@Param("date")Date date);

}
