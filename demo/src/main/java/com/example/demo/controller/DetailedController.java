package com.example.demo.controller;

import com.example.demo.bean.*;
import com.example.demo.dao.CategoryDao;
import com.example.demo.dao.DetailedDao;
import com.example.demo.dao.LanguageDao;
import com.example.demo.dao.MonitorDao;
import com.example.demo.service.DetailedService;
import com.example.demo.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/*
 * 前台-详情DetailedController
 * paddy 2018/9/17
 * */
@Controller()
@RequestMapping(value = "/json")
@Component("DetailedController")
public class DetailedController {
    private  static Logger logger = LoggerFactory.getLogger(DetailedController.class);
    @Resource
    CategoryDao categoryDao;
    @Resource
    DetailedDao detailedDao;
    @Resource
    LanguageDao languageDao;
    @Resource
    private DetailedService detailedService;
    @Resource
    IpUtil ipUtil;
    @Resource
    MonitorDao monitorDao;

    @ResponseBody
    @RequestMapping("/getByDetaileds")
    public List<Detailed> getByDetaileds(
            @RequestParam(name = "lang_id",required = false,defaultValue = "0")long lang_id,
            @RequestParam(name = "cat_id",required = false,defaultValue = "0")long cat_id){
        List<Detailed> detaileds = null;
        if(lang_id == 0 || cat_id == 0){
            logger.error("---lang_id或cat_id为0");
        }else{
            detaileds = detailedService.getByDetaileds(lang_id,cat_id);
        }
        return detaileds;
    }

    /* 获取某个类别*/
    @ResponseBody
    @RequestMapping("/getByDetailed")
    public RestResultModule getByDetailed(@RequestParam(name = "dlId", defaultValue = "0", required = true) long dlId){
        RestResultModule module = new RestResultModule();
        if(dlId == 0){
            module.setCode(404);
            module.setMsg("Parameter error");
            return module;
        }
        // 获取语言集合
        List<Language> languages = languageDao.findAll();
        // 获取详情信息
        Detailed detailed = detailedDao.findById(dlId);
        if(null == detailed){
            module.setCode(404);
            module.setMsg("Detailed is null");
            return module;
        }
        List<Category> categories = categoryDao.findByLangId(detailed.getLangId());

        module.setCode(200);
        module.putData("languages",languages);
        module.putData("categories",categories);
        module.putData("detailed",detailed);
        module.putData("langId",detailed.getLangId());

        return module;
    }

    /* index搜索，带语言*/
    @ResponseBody
    @RequestMapping("/getSearch")
    public RestResultModule getSearch(
            @RequestParam(name = "langId",required = true,defaultValue = "0")long langId,
            @RequestParam(name = "search",required = false,defaultValue = "")String search){
        RestResultModule module = new RestResultModule();
        List<Detailed> detaileds = null;
        detaileds = detailedDao.getSearch(langId,search);
        module.setCode(200);
        module.putData("detaileds",detaileds);
        return module;
    }


    /* 首页搜索*/
    @ResponseBody
    @RequestMapping("/getSerDetaileds")
    public RestResultModule getSerDetaileds(@RequestParam(name = "serach",required = false,defaultValue = "")String serach){
        RestResultModule module = new RestResultModule();
        if(!"".equals(serach)){
            List<Detailed> detaileds = null;
            detaileds = detailedDao.findAllByStatusAndTitleContaining(1,serach);
            module.putData("detaileds",detaileds);
        }
        return module;
    }

    /* 搜索页-获取热点数据*/
    @ResponseBody
    @RequestMapping("/getHotspot")
    public RestResultModule getHotspot(){
        RestResultModule module = new RestResultModule();
        List<Detailed> detaileds = null;
        detaileds = detailedDao.getHpSearchCount();
        module.putData("detaileds",detaileds);
        return module;
    }
    /* 搜索页-添加热点数量*/
    @ResponseBody
    @RequestMapping("/addHotspot")
    public boolean addHotspot(@RequestParam(name = "dlId",required = true,defaultValue = "0")long dlId){
        boolean flag = detailedService.addHotspot(dlId);
        return flag;
    }

    /* 添加流量数*/
    @ResponseBody
    @RequestMapping("/addip")
    public boolean addip(HttpServletRequest request,
                         @RequestParam(name = "catId",required = true,defaultValue = "0")long catId,
                         @RequestParam(name = "dlId",required = true,defaultValue = "0")long dlId){
        Monitor monitor = new Monitor();
        String ip = ipUtil.getIpAddr(request);
        monitor.setClientip(ip);
        monitor.setCreateDate(new Date());
        if(catId > 0){
            Category category = categoryDao.findById(catId);
            if(null != category){
                monitor.setLangId(category.getLangId());
                monitor.setCatId(category.getId());
                monitorDao.save(monitor);
                return true;
            }
        }else if(dlId > 0){
            Detailed detailed = detailedDao.findById(dlId);
            if(null != detailed){
                monitor.setLangId(detailed.getLangId());
                monitor.setCatId(detailed.getCatId());
                monitor.setDlId(detailed.getId());
                monitorDao.save(monitor);
                return true;
            }
        }
        return false;
    }

}
