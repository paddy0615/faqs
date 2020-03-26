package com.example.demo.dao;

import com.example.demo.bean.Folder_monitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component(value = "folder_MonitorDao")
public interface Folder_MonitorDao extends JpaRepository<Folder_monitor,Long> {


}
