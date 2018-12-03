package com.example.demo.controller.admin;

import com.example.demo.bean.RestResultModule;
import com.example.demo.dao.DetailedFeedbackDao;
import com.example.demo.dao.DfeedbackDao;
import com.example.demo.entity.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /* 初始化*/
    @ResponseBody
    @RequestMapping("/getFeedbackPage")
    public RestResultModule getFeedbackPage(@RequestParam(name = "df_type",defaultValue = "0",required = true) long df_type){
        RestResultModule module = new RestResultModule();
        System.out.println(df_type);
        List<Feedback> feedbacks = null;
        if(df_type > 0){
            feedbacks = dfeedbackDao.getAllByDfType(df_type);
        }else{
            feedbacks = dfeedbackDao.getAllBy();
        }
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
        System.out.println(df_id);
        Feedback feedback  = dfeedbackDao.getAllById(df_id);
        module.putData("feedback",feedback);
        return module;
    }

}
