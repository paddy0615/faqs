package com.example.demo.dao;

import com.example.demo.bean.E_form_relation;
import com.example.demo.bean.E_form_result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component(value = "e_form_relationDao")
public interface E_form_relationDao extends JpaRepository<E_form_relation,Long> {

}
