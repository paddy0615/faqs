package com.example.demo.controller.admin;

import com.example.demo.bean.Category;
import com.example.demo.bean.Detailed;
import com.example.demo.bean.Language;
import com.example.demo.bean.RestResultModule;
import com.example.demo.dao.CategoryDao;
import com.example.demo.dao.DetailedDao;
import com.example.demo.dao.LanguageDao;
import com.example.demo.service.DetailedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/*
 * 后台-详情DetailedController
 * paddy 2018/9/17
 * */
@Controller()
@RequestMapping(value = "/json/admin")
@Component("AdminDetailedController")
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


    /* 初始化*/
    @ResponseBody
    @RequestMapping("/getDetailedPage")
    public RestResultModule getDetailedPage(@RequestParam(name = "langId",defaultValue = "0",required = true) long langId,
                                            @RequestParam(name = "catId",defaultValue = "0",required = true) long catId){
        RestResultModule module = new RestResultModule();
        // 获取语言集合
        List<Language> languages = languageDao.findAll();
        // 获取类别集合
        List<Category> categories = categoryDao.findByLangId(langId);
        List<Detailed> detaileds = null;
        if(categories.size() > 0 ){
            // 移除第一个（主页）
            categories.remove(0);
            // 获取详情集合--默认第一个开始
            if(catId == 0){
                detaileds = detailedDao.findAllByLangIdAndCatId(langId,categories.get(0).getId());
                catId = categories.get(0).getId();
            }else{
                detaileds = detailedDao.findAllByLangIdAndCatId(langId,catId);
            }
        }

        module.setCode(200);
        module.putData("languages",languages);
        module.putData("categories",categories);
        module.putData("detaileds",detaileds);
        module.putData("selectCatId",catId);
        return module;
    }

    /* 按固定语言ID和类别ID，查询详情集合*/
    @ResponseBody
    @RequestMapping("/getDetaileds")
    public RestResultModule getDetaileds(@RequestParam(name = "langId",defaultValue = "0",required = true) long langId,
                                         @RequestParam(name = "catId",defaultValue = "0",required = true) long catId){
        RestResultModule module = new RestResultModule();
        List<Detailed> detaileds = null;
        if(langId == 0 || catId == 0){
            module.setMessage(404,"参数错误");
        }else{
            detaileds = detailedDao.findAllByLangIdAndCatId(langId,catId);
        }
        module.putData("detaileds",detaileds);
        return module;
    }

    /* 编辑页面*/
    @ResponseBody
    @RequestMapping("/getDetailedUpdate")
    public RestResultModule getDetailedUpdate(@RequestParam(name = "dlId",defaultValue = "0",required = true) long dlId){
        RestResultModule module = new RestResultModule();
        // 获取详情
        Detailed detailed = detailedDao.findById(dlId);
        // 获取语言
        Language language = languageDao.findById(detailed.getLangId().longValue());
        // 获取类别集合
        List<Category> categories = categoryDao.findByLangId(detailed.getLangId());
        Category category = categoryDao.findById(detailed.getCatId().longValue());
        // 移除第一个（主页）
        categories.remove(0);
        module.setCode(200);
        module.putData("detailed",detailed);
        module.putData("language",language);
        module.putData("category",category);
        module.putData("categories",categories);
        return module;
    }

    /* 添加页面*/
    @ResponseBody
    @RequestMapping("/getDetailedAdd")
    public RestResultModule getDetailedAdd(){
        RestResultModule module = new RestResultModule();
        // 获取语言
        List<Language> languages = languageDao.findAll();
        // 获取类别集合-默认第一个
        List<Category> categories = categoryDao.findByLangId(languages.get(0).getId());
        // 移除第一个（主页）
        categories.remove(0);
        module.setCode(200);
        module.putData("languages",languages);
        module.putData("categories",categories);
        module.putData("selectCatId",categories.get(0).getId());
        return module;
    }

    /* 更新*/
    @ResponseBody
    @RequestMapping(value = "/detailed/update",method= RequestMethod.POST)
    public void update(@RequestBody  Detailed detailed){
        if(null != detailed){
            detailed.setUpdateDate(new Date());
            detailed.setOrderTopDate(new Date());
            detailedService.save(detailed);
        }
    }

    /* 添加*/
    @ResponseBody
    @RequestMapping(value = "/detailed/add",method= RequestMethod.POST)
    public void add(@RequestBody  Detailed detailed){
        if(null != detailed){
            detailed.setCreateDate(new Date());
            detailed.setUpdateDate(new Date());
            detailed.setCreateUser(Long.parseLong("1"));
            detailed.setUpdateUser(Long.parseLong("1"));
            detailed.setOrderTopDate(new Date());
            detailedService.save(detailed);
        }
    }

    /* 是否存在某个类别下的详情集合*/
    @ResponseBody
    @RequestMapping(value = "/detailed/countDlByCatId")
    public boolean countDlByCatId(@RequestParam(name = "catId",defaultValue = "0",required = true) long catId){
        return detailedService.countByCatId(catId);
    }

    /* 删除*/
    @ResponseBody
    @RequestMapping(value = "/detailed/delete")
    public void delete(@RequestParam(name = "dlId",defaultValue = "0",required = true) long dlId){
        detailedService.deleteById(dlId);
    }

    /* 按title模糊查询*/
    @ResponseBody
    @RequestMapping("/detailed/getSearchTitle")
    public RestResultModule getSearchTitle(@RequestParam(name = "serach",required = false,defaultValue = "")String serach){
        RestResultModule module = new RestResultModule();
        List<Detailed> detaileds = null;
        detaileds = detailedDao.findAllByTitleContaining(serach);
        module.putData("detaileds",detaileds);
        return module;
    }

    /* 修改状态*/
    @ResponseBody
    @RequestMapping(value = "/detailed/editStatus")
    public void editStatus(@RequestParam(name = "dlId",defaultValue = "0",required = true) long dlId,
                           @RequestParam(name = "status",defaultValue = "0",required = true) long status){
        detailedService.saveStatus(dlId,status);
    }

    /* 置顶*/
    @ResponseBody
    @RequestMapping(value = "/detailed/editTop")
    public void editTop(@RequestParam(name = "dlId",defaultValue = "0",required = true) long dlId){
       detailedService.saveTop(dlId);
    }


}
