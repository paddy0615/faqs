package com.example.demo.dao;

import com.example.demo.bean.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "languageDao")
public interface LanguageDao extends JpaRepository<Language,Long> {

    Language findById(long langId);

    List<Language> findAllByTitleContaining(String s);
}
