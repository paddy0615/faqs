package com.example.demo.dao;

import com.example.demo.bean.E_form_type_display;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "e_form_type_displayDao")
public interface E_form_type_displayDao extends JpaRepository<E_form_type_display,Long> {

  /*  @Modifying
    @Query("delete from e_form_type_display")
    void  deleteAll();*/

}
