package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.bean.Language;
import com.example.demo.bean.Logs;
import com.example.demo.bean.RestResultModule;
import com.example.demo.bean.User;
import com.example.demo.dao.LanguageDao;
import com.example.demo.dao.LogsDao;
import com.example.demo.service.LanguageService;
import com.example.demo.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/*
 * 语言LanguageController
 * paddy 2018/9/17
 * */
@Controller
@RequestMapping(value = "appJson")
public class LanguageController {
    private  static Logger logger = LoggerFactory.getLogger(LanguageController.class);
    @Resource
    LanguageDao languageDao;
    @Resource
    LanguageService languageService;
    @Resource
    LogsDao logsDao;
    @Resource
    IpUtil ipUtil;

    @ResponseBody
    @RequestMapping("/getLanguageAll")
    public List<Language> getLanguageAll(){
        List<Language> languages = languageDao.findAll();
        return languages;
    }

    @ResponseBody
    @RequestMapping("/getLanguage")
    public Language getLanguage(@RequestParam(name = "langId",defaultValue = "0",required = true) long langId){
        return languageDao.findById(langId);
    }


    @ResponseBody
    @RequestMapping(value = "/language/update",method= RequestMethod.POST)
    public void update(HttpServletRequest request, HttpSession session,
                       @RequestBody Language language){
        User user = (User)session.getAttribute("userSession");
        if(null != user) {
            if(null != language){
                Date date = new Date();
                language.setUpdateDate(date);
                language.setUpdateUser(user.getId());
                languageService.save(language);

                // 添加日志
                Logs logs = new Logs(user.getId(), ipUtil.getIpAddr(request), "language/update", JSON.toJSONString(language), "",date);
                logsDao.save(logs);

            }
        }

    }

    @ResponseBody
    @RequestMapping(value = "/language/add")
    public void add(HttpServletRequest request, HttpSession session,
                    @RequestParam(name = "title",defaultValue = "",required = true) String title,
                    @RequestParam(name = "problem",defaultValue = " ",required = true) String problem){
        User user = (User)session.getAttribute("userSession");
        if(null != user) {
            Language language = new Language();
            Date date = new Date();
            language.setCreateDate(date);
            language.setUpdateDate(date);
            language.setCreateUser(user.getId());
            language.setUpdateUser(user.getId());
            language.setTitle(title);
            language.setProblem(problem);
            languageService.save(language);

            // 添加日志
            Logs logs = new Logs(user.getId(), ipUtil.getIpAddr(request), "language/add", JSON.toJSONString(language), "",date);
            logsDao.save(logs);
        }


    }

    @ResponseBody
    @RequestMapping("/language/delete")
    public void delete(HttpServletRequest request, HttpSession session,
                       @RequestParam(name = "langId",defaultValue = "0",required = true) long langId){
        User user = (User)session.getAttribute("userSession");
        if(null != user) {
            if ("admin".equals(user.getRole())) {
                Logs logs = new Logs(user.getId(), ipUtil.getIpAddr(request), "language/delete", JSON.toJSONString(languageDao.findById(langId)), "",new Date());

                languageService.deleteById(langId);
                // 添加日志
                logsDao.save(logs);
            }
        }
    }


    @ResponseBody
    @RequestMapping("/language/getSearchTitle")
    public RestResultModule getSearchTitle(@RequestParam(name = "serach",required = false,defaultValue = "")String serach){
        RestResultModule module = new RestResultModule();
        List<Language> languages = null;
        languages = languageDao.findAllByTitleContaining(serach);
        module.putData("languages",languages);
        return module;
    }






}
