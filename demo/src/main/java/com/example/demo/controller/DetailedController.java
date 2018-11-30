package com.example.demo.controller;

import com.example.demo.bean.*;
import com.example.demo.dao.CategoryDao;
import com.example.demo.dao.DetailedDao;
import com.example.demo.dao.LanguageDao;
import com.example.demo.dao.MonitorDao;
import com.example.demo.entity.DetailedEntity;
import com.example.demo.service.DetailedService;
import com.example.demo.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/*
 * 前台-详情DetailedController
 * paddy 2018/9/17
 * */
@Controller()
@RequestMapping(value = "appJson")
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
        List<Category> categories = categoryDao.findAllByLangIdAndStatus(detailed.getLangId(),1);

        long dfcount = detailedService.getFeedbackCnt(dlId);

        module.setCode(200);
        module.putData("languages",languages);
        module.putData("categories",categories);
        module.putData("detailed",detailed);
        module.putData("langId",detailed.getLangId());
        module.putData("dfcount",dfcount);

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

    /**
     * 按标签搜索
     * @param search 内容
     */
    @ResponseBody
    @RequestMapping("/getSearchTags")
    public RestResultModule getSearchTags(
            @RequestParam(name = "search",required = false,defaultValue = "")String search){
        RestResultModule module = new RestResultModule();
        String [] sarr = search.split(" ");
        List<String> searchs = Arrays.asList(sarr);
        List<DetailedEntity> detaileds = null;
        detaileds = detailedService.getSearchTags(searchs);
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
        Pageable pageable = new PageRequest(0,10);
        Page<Detailed> ds = detailedDao.getHpSearchCount1(pageable);
        //List<Detailed> detaileds = null;
        //detaileds = detailedDao.getHpSearchCount();
        module.putData("detaileds",ds.getContent());
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

    /**
     * 添加反馈信息, 按IP记录
     * @param request
     * @param feedback 反馈对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addFeedback",method= RequestMethod.POST)
    public RestResultModule addFeedback(HttpServletRequest request,@RequestBody DetailedFeedback feedback){
        RestResultModule module = new RestResultModule();
        if(null == feedback && feedback.getDlId() == 0){
            module.setMessage(400,"传参有误!");
            return module;
        }
        feedback.setIp(ipUtil.getIpAddr(request));
        long dfId =  detailedService.addFeedback(feedback);
        module.putData("id",dfId);
        module.putData("type",feedback.getType());
        return module;
    }

    /**
     * 更新反馈信息, 按id
     * @param feedback 反馈对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateFeedback",method= RequestMethod.POST)
    public RestResultModule updateFeedback(HttpServletRequest request,@RequestBody DetailedFeedback feedback){
        RestResultModule module = new RestResultModule();
        if("" == feedback.getContent()){
            module.setMessage(400,"内容为空!");
            return module;
        }
        if(feedback.getId() == 0){
            feedback.setIp(ipUtil.getIpAddr(request));
            feedback.setCreateDate(new Date());
        }
        detailedService.updateFeedback(feedback);
        return module;
    }

    /**
     * 删除反馈信息, 按id
     * @param feedback 反馈对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delFeedback",method= RequestMethod.POST)
    public void delFeedback(@RequestBody DetailedFeedback feedback){
        if(null == feedback && feedback.getId() == 0){
            return;
        }
        detailedService.delFeedback(feedback);
    }

    /* 自定义修改后台数据,因域名变化,图片位置也变化*/
    @ResponseBody
    @RequestMapping("/xiugaishujuYuming")
    public List<Detailed> addHotspot(){
        List<Detailed> detaileds = detailedDao.findAll();
        for (Detailed d:detaileds) {
            //d.setContent(d.getContent().replace("/ueditor/jsp", "/hkexpress/ueditor/jsp"));
            //detailedDao.save(d);
        }
        return detaileds;
    }

}
