package com.example.demo.dao;

import com.example.demo.bean.E_form_Monitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component(value = "e_form_MonitorDao")
public interface E_form_MonitorDao extends JpaRepository<E_form_Monitor,Long> {


}
