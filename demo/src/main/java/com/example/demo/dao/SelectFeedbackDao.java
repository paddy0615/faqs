package com.example.demo.dao;

import com.example.demo.bean.SelectFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component(value = "selectFeedbackDao")
public interface SelectFeedbackDao extends JpaRepository<SelectFeedback,Long> {

    @Modifying
    @Query("update SelectFeedback d set d.status = :status where d.id = :id")
    void updateFeedbackStatus(@Param("id") long id, @Param("status") long status);
}
