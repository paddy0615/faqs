package com.example.demo.dao;

import com.example.demo.bean.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component(value = "monitorDao")
public interface MonitorDao extends JpaRepository<Monitor,Long> {

}
