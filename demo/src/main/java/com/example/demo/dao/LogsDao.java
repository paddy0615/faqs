package com.example.demo.dao;

import com.example.demo.bean.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component(value = "logsDao")
public interface LogsDao extends JpaRepository<Logs,Long> {


}
