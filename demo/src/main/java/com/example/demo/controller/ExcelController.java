package com.example.demo.controller;

import com.example.demo.bean.Detailed;
import com.example.demo.bean.Language;
import com.example.demo.bean.RestResultModule;
import com.example.demo.dao.LanguageDao;
import com.example.demo.entity.ExcelConstant;
import com.example.demo.entity.ExcelData;
import com.example.demo.service.DetailedService;
import com.example.demo.service.LanguageService;
import com.example.demo.util.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * 报表Excel
 * paddy 2019/02/28
 * */
@RestController
@RequestMapping("excel")
public class ExcelController {
    private  static Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @Resource
    private LanguageDao languageDao;

    @Resource
    private DetailedService detailedService;

    @RequestMapping("/test")
    public void test(){
        int rowIndex = 0;
        List<Detailed> list = detailedService.getAllByLangId(6);
        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList<>();
        titles.add("ID");
        titles.add("userName");
        titles.add("password");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        for(int i = 0, length = list.size();i<length;i++){
            Detailed detailed = list.get(i);
            List<Object> row = new ArrayList();
            row.add(detailed.getId());
            row.add(detailed.getTitle());
            row.add(detailed.getTitle());
            rows.add(row);
        }
        data.setRows(rows);
        try{
            rowIndex = ExcelUtils.generateExcel(data, ExcelConstant.FILE_PATH + ExcelConstant.FILE_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }
        //return RetResponse.makeOKRsp(Integer.valueOf(rowIndex));
    }

    @RequestMapping("/test2")
    public void test2(HttpServletResponse response){
        List<Language> languages = languageDao.findAll();
        for (Language l:languages) {

            List<Detailed> list = detailedService.getAllByLangId(l.getId());
            ExcelData data = new ExcelData();
            data.setName(l.getTitle());
            List<String> titles = new ArrayList();
            titles.add("父级");
            titles.add("问题ID");
            titles.add("标题");
            titles.add("答案");
            data.setTitles(titles);
            List<List<Object>> rows = new ArrayList();
            for(int i = 0, length = list.size();i<length;i++){
                Detailed detailed = list.get(i);
                List<Object> row = new ArrayList();
                row.add(detailed.getFlTitle());
                row.add(detailed.getId());
                row.add(detailed.getTitle());
                row.add(detailed.getContentTxt());
                rows.add(row);
            }
            data.setRows(rows);
            try{
                ExcelUtils.exportExcel(response,"Faq20190301全部问题详情",data);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }




}
