package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * 接收HKE,url做跳转
 * paddy 2018/12/26
 * */
@Controller
@RequestMapping
public class HKEController {
    /**
     * HKE请求
     * 中文
     * @return
     */
    @RequestMapping(value="/videofaq/page/{name}",method= RequestMethod.GET)
    public String farerules_zh_cn(@PathVariable("name") String name){
        // 查看数据库对应ID  -> faqs_language
        // 默认英文;
        long langId = 6;
        if(("farerules_zh_hk.ftl").equals(name)){
            // 香港
            langId = 1;
        }else if(("farerules_zh_cn.ftl").equals(name)){
            // 简体
            langId = 2;
        }else if(("farerules_zh_tw.ftl").equals(name)){
            // 台湾
            langId = 3;
        }else if(("farerules_ja.ftl").equals(name)){
            // 日本
            langId = 4;
        }else if(("farerules_ko.ftl").equals(name)){
            // 韩文
            langId = 5;
        }
        System.out.println(name);
        return "redirect:/appPage/index?langId="+langId;
    }


}
