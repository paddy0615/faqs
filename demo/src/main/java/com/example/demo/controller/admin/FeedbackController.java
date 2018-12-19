package com.example.demo.controller.admin;

import com.example.demo.bean.Detailed;
import com.example.demo.bean.DetailedFeedback;
import com.example.demo.bean.RestResultModule;
import com.example.demo.dao.DetailedDao;
import com.example.demo.dao.DetailedFeedbackDao;
import com.example.demo.dao.DfeedbackDao;
import com.example.demo.dao.LanguageDao;
import com.example.demo.entity.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/*
 * FeedbackController 反馈信息
 * paddy 2018/11/30
 * */
@Controller
@RequestMapping(value = "appJson/admin")
@Component("AdminFeedbackController")
public class FeedbackController {
    private  static Logger logger = LoggerFactory.getLogger(FeedbackController.class);
    @Resource
    DfeedbackDao dfeedbackDao;
    @Resource
    DetailedFeedbackDao detailedFeedbackDao;
    @Resource
    LanguageDao languageDao;
    @Resource
    DetailedDao detailedDao;

    /* 初始化*/
    @ResponseBody
    @RequestMapping("/getFeedbackPage")
    public RestResultModule getFeedbackPage(
            @RequestParam(name = "df_type",defaultValue = "0",required = true) long df_type,
            @RequestParam(name = "CurrentPage",defaultValue = "1",required = true) int CurrentPage,
            @RequestParam(name = "PageSize",defaultValue = "10",required = true) int PageSize,
            @RequestParam(name = "startTime",defaultValue = "",required = true) String startTime,
            @RequestParam(name = "endTime",defaultValue = "",required = true) String endTime){

        RestResultModule module = new RestResultModule();
        System.out.println(CurrentPage+","+PageSize);
   /*   分页
        Pageable pageable = new PageRequest(CurrentPage-1,PageSize);
        Page<Feedback> feedbacks = null;
        if(df_type > 0){
            feedbacks = dfeedbackDao.getAllByDfType(df_type,pageable);
        }else{
            feedbacks = dfeedbackDao.getAllBy(pageable);
        }
        module.putData("feedbacks",feedbacks.getContent());
        module.putData("PageCount",feedbacks.getTotalElements());*/

        // 原查询 , 后可删jpa
        List<Feedback> feedbacks = dfeedbackDao.getAllByDfType1(df_type,startTime,endTime);
        module.putData("feedbacks",feedbacks);
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

}
