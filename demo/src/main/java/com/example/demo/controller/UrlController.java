package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * url跳转页面
 * paddy 2018/9/17
 * */
@Controller
@RequestMapping("appPage")
public class UrlController {

    @RequestMapping("/")
    public String index(){return "faqs/index";}

    @RequestMapping("/index")
    public String index1(){return "faqs/index";}

    @RequestMapping("/search")
    public String search(){return "faqs/search";}

    @RequestMapping("/indexDetailed")
    public String indexDetailed(){return "faqs/indexDetailed";}

    @RequestMapping("/admin/login")
    public String login(){return "faqs/admin/login";}

    @RequestMapping("/goLogin")
    public String goLogin(){
        return "redirect:/appPage/admin/login";
    }

    @RequestMapping("/admin/category")
    public String category(){
        return "faqs/admin/category";
    }

    @RequestMapping("/admin/categoryEdit")
    public String categoryEdit(){
        return "faqs/admin/categoryEdit";
    }

    @RequestMapping("/admin/languageEdit")
    public String languageEdit(){
        return "faqs/admin/languageEdit";
    }

    @RequestMapping("/admin/language")
    public String language(){
        return "faqs/admin/language";
    }

    @RequestMapping("/admin/detailed")
    public String detailed(){
        return "faqs/admin/detailed";
    }

    @RequestMapping("/admin/detailedEdit")
    public String detailedEdit(){
        return "faqs/admin/detailedEdit";
    }

    @RequestMapping("/admin/feedback")
    public String feedback(){
        return "faqs/admin/feedback";
    }

    @RequestMapping("/admin/feedbackSet")
    public String feedbackSet(){
        return "faqs/admin/feedbackSet";
    }


    @RequestMapping("/admin/notags")
    public String notags(){
        return "faqs/admin/notags";
    }

}
