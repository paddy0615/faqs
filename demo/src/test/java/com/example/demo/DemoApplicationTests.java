package com.example.demo;

import com.example.demo.bean.Detailed;
import com.example.demo.bean.Language;
import com.example.demo.dao.LanguageDao;
import com.example.demo.service.DetailedService;
import com.example.demo.util.POIExcelUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Resource
	private LanguageDao languageDao;

	@Resource
	private DetailedService detailedService;

	@Test
	public void contextLoads() throws Exception {


	}

}
