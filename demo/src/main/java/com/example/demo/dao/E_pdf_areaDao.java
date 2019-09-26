package com.example.demo.dao;

import com.example.demo.bean.E_pdf_area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component(value = "e_pdf_areaDao")
public interface E_pdf_areaDao extends JpaRepository<E_pdf_area,Long> {

    @Query("from E_pdf_area e where e.key = :key")
    E_pdf_area getByKey(@Param("key") String key);
}
