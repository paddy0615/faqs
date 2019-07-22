package com.example.demo.controller.admin;

import com.alibaba.fastjson.JSON;
import com.example.demo.bean.*;
import com.example.demo.dao.*;
import com.example.demo.entity.Feedback;
import com.example.demo.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 * FeedbackController 反馈信息
 * paddy 2018/11/30
 * */
@Controller
@RequestMapping(value = "appJson/admin")
@Component("AdminFeedbackController")
public class  FeedbackController {
    private  static Logger logger = LoggerFactory.getLogger(FeedbackController.class);
    @Resource
    DfeedbackDao dfeedbackDao;
    @Resource
    DetailedFeedbackDao detailedFeedbackDao;
    @Resource
    LanguageDao languageDao;
    @Resource
    DetailedDao detailedDao;
    @Resource
    LogsDao logsDao;
    @Resource
    IpUtil ipUtil;


    /* 初始化*/
    @ResponseBody
    @RequestMapping("/getFeedbackPage")
    public RestResultModule getFeedbackPage(@RequestBody Map<String,Object> map){
        RestResultModule module = new RestResultModule();
        int CurrentPage = Integer.parseInt(map.get("CurrentPage").toString());
        int PageSize = Integer.parseInt(map.get("PageSize").toString());
        long langId = Long.parseLong(map.get("langId").toString());
        long comment = Long.parseLong(map.get("comment").toString());
        long df_type = Long.parseLong(map.get("df_type").toString());
        long commentStatu = Long.parseLong(map.get("commentStatu").toString());
        String startTime = map.get("startTime").toString();
        String endTime = map.get("endTime").toString();
        //分页
        Pageable pageable = new PageRequest(CurrentPage-1,PageSize);
        Page<Feedback> feedbacks = null;
        feedbacks = dfeedbackDao.getAllByDfType(langId,comment,commentStatu,df_type,startTime,endTime,pageable);

        module.putData("feedbacks",feedbacks.getContent());
        module.putData("PageCount",feedbacks.getTotalElements());
        module.putData("languages",languageDao.findAll());
        return module;
    }

    /**
     * 按ID查询
     * @param df_id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFeedbackById")
    public RestResultModule getFeedbackById(@RequestParam(name = "df_id",defaultValue = "0",required = true) long df_id){
        RestResultModule module = new RestResultModule();
       /* Feedback feedback  = dfeedbackDao.getAllById(df_id);
        module.putData("feedback",feedback);*/
        DetailedFeedback feedback = detailedFeedbackDao.getById(df_id);
        if(null != feedback){
            Detailed detailed = detailedDao.findById((long)feedback.getDlId());
            if(null != detailed){
                feedback.setLang_title(languageDao.getByTitle(detailed.getLangId()));
                feedback.setDetailed_title(detailed.getTitle());
            }
        }
        module.putData("feedback",feedback);
        return module;
    }

    /**
     * 按ID查询
     * @param df_id
     * @return
     */
    @Transactional
    @ResponseBody
    @RequestMapping("/updateFeedbackStatus")
    public RestResultModule updateFeedbackStatus(HttpServletRequest request, HttpSession session,
                                                 @RequestParam(name = "df_id",defaultValue = "0",required = true) long df_id,
                                                 @RequestParam(name = "df_nay_status",defaultValue = "0",required = true) long df_nay_status){
        RestResultModule module = new RestResultModule();
        User user = (User)session.getAttribute("userSession");
        if(null != user) {
            if(df_id > 0){
                detailedFeedbackDao.updateFeedbackStatus(df_id,df_nay_status);
            }else{
                module.setCode(500);
            }

            // 添加日志
            Logs logs = new Logs(user.getId(), ipUtil.getIpAddr(request), "updateFeedbackStatus", "df_id="+df_id, "df_nay_status="+df_nay_status,new Date());
            logsDao.save(logs);
        }

        return module;
    }

}
