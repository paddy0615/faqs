package com.example.demo.dao;

import com.example.demo.bean.Hotspot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "hotspotDao")
public interface HotspotDao extends JpaRepository<Hotspot,Long> {

    Hotspot findByDlId(long id);

    void deleteByDlId(long dlid);

    @Query(value = "SELECT d.* FROM  faqs_dl_hotspot d" +
            "            WHERE d.dlh_dl_id IN(:ids)",nativeQuery = true)
    List<Hotspot> getByIds(@Param("ids")List<String> ids);

}
