package com.example.demo.controller.admin;

import com.example.demo.bean.DetailedNoTags;
import com.example.demo.bean.RestResultModule;
import com.example.demo.dao.DetailedNoTagsDao;
import com.example.demo.dao.LanguageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/*
 * NoTagsController 前台-搜索无结果类
 * paddy 2018/12/19
 * */
@Controller
@RequestMapping(value = "appJson/admin")
@Component("AdminNoTagsController")
public class NoTagsController {
    private  static Logger logger = LoggerFactory.getLogger(NoTagsController.class);
    @Resource
    DetailedNoTagsDao noTagsDao;
    @Resource
    LanguageDao languageDao;

    /* 初始化*/
    @ResponseBody
    @RequestMapping("/getNoTagsPage")
    public RestResultModule getNoTagsPage(@RequestBody Map<String,Object> map){
        RestResultModule module = new RestResultModule();
        int CurrentPage = Integer.parseInt(map.get("CurrentPage").toString());
        int PageSize = Integer.parseInt(map.get("PageSize").toString());
        long langId = Long.parseLong(map.get("langId").toString());
        String startTime = map.get("startTime").toString();
        String endTime = map.get("endTime").toString();
        //分页
        Pageable pageable = new PageRequest(CurrentPage-1,PageSize);
        Page<Object[]> noTags = null;
        noTags = noTagsDao.getAllByDNTs(langId,startTime,endTime,pageable);

        module.putData("notags",noTags.getContent());
        module.putData("PageCount",noTags.getTotalElements());
        module.putData("languages",languageDao.findAll());
        return module;
    }


}
