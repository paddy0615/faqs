package com.example.demo.dao;

import com.example.demo.bean.E_form_result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component(value = "e_form_resultDao")
public interface E_form_resultDao extends JpaRepository<E_form_result,Long> {

    @Query("from E_form_result where eid=:eid")
    E_form_result findByeEid(@Param("eid")long eid);

}
