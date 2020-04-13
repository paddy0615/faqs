package com.example.demo.controller.admin;

import com.alibaba.fastjson.JSON;
import com.example.demo.bean.*;
import com.example.demo.dao.LanguageDao;
import com.example.demo.dao.LibrabryDao;
import com.example.demo.dao.LogsDao;
import com.example.demo.service.FolderService;
import com.example.demo.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 * 后台-新:文件夹分类模式
 * paddy 2019/12/25
 * */
@Controller()
@RequestMapping(value = "appJson/admin")
@Component("AdminFolderController")
public class FolderController {
    private  static Logger logger = LoggerFactory.getLogger(FolderController.class);
    @Resource
    LanguageDao languageDao;
    @Resource
    FolderService folderService;
    @Resource
    LogsDao logsDao;
    @Resource
    LibrabryDao librabryDao;
    @Resource
    IpUtil ipUtil;

    /**
     * folder 页面初始化
     * @return
     */
    @ResponseBody
    @RequestMapping("/getFolderPage")
    public RestResultModule getFolderPage(@RequestBody Map<String,Object> map){
        RestResultModule module = new RestResultModule();
        long level = Long.parseLong(map.get("level").toString());
        long parenId = Long.parseLong(map.get("parenId").toString());
        long langId = Long.parseLong(map.get("langId").toString());
        // 固定显示英文
        List<Object[]> list = folderService.getFolderPage(level,parenId,6);
        module.putData("list",list);
        module.putData("languages",languageDao.findAll());
        module.putData("tableofContents",folderService.getFolderTableofContents(level,parenId));
        module.putData("librabries",librabryDao.findAll());
        module.putData("list_librabries",folderService.getFolderLibraryPage(parenId));
        return module;
    }

    /**
     * edit getfolder
     * @return
     */
    @ResponseBody
    @RequestMapping("/editGetFolderAll")
    public List<Folder> editGetFolderAll(@RequestParam(name = "key_random",defaultValue = "0",required = true) long key_random){
        return folderService.getFolderAllByKey_random(key_random);
    }


    /**
     * addORupdateFolder
     * @return
     */
    @ResponseBody
    @RequestMapping("/addORupdateFolder")
    public RestResultModule addORupdateFolder(HttpServletRequest request, HttpSession session, @RequestBody Map<String,Object> map){
        RestResultModule module = new RestResultModule();
        User user = (User)session.getAttribute("userSession");
        if(null != user) {
            long level = Long.parseLong(map.get("level").toString());
            long parenId = Long.parseLong(map.get("parenId").toString());
            List<Map<String,Object>> mapArr = (List<Map<String,Object>>)map.get("dataMapArr");
            long key_random = Long.parseLong(map.get("key_random").toString());
            if(key_random > 0){
                // 更新
                folderService.updateFolderALL(key_random,level,parenId,mapArr);
            }else{
                // 添加
                folderService.saveFolderALL(level,parenId,mapArr);
            }

            // 添加日志
            Logs logs = new Logs(user.getId(), IpUtil.getIpAddr(request), "addORupdateFolder/"+key_random, map.toString(), "",new Date());
            logsDao.save(logs);
        }

        return module;
    }



    /**
     * addORupdateLibrary
     * @return
     */
    @ResponseBody
    @RequestMapping("/addORupdateLibrary")
    public RestResultModule addORupdateLibrary(HttpServletRequest request, HttpSession session, @RequestBody Map<String,Object> map) {
        RestResultModule module = new RestResultModule();
        User user = (User)session.getAttribute("userSession");
        if(null != user) {

            long level = Long.parseLong(map.get("level").toString());
            long parenId = Long.parseLong(map.get("parenId").toString());
            List<Integer> arr = (List<Integer>) map.get("libraryArr");
            long key_random = Long.parseLong(map.get("key_random").toString());
            if(key_random > 0){
                // 更新
            }else{
                // 添加
                folderService.saveLibraryALL(parenId,arr);
            }

            // 添加日志
            Logs logs = new Logs(user.getId(), IpUtil.getIpAddr(request), "addORupdateLibrary/"+key_random, map.toString(), "",new Date());
            logsDao.save(logs);
        }
        return module;
    }


    /* 删除deleteFolder*/
    @ResponseBody
    @RequestMapping(value = "/deleteFolder")
    public void delete(HttpServletRequest request,HttpSession session,
                       @RequestParam(name = "key_random",defaultValue = "0",required = true) long key_random){
        User user = (User)session.getAttribute("userSession");
        if(null != user){
            if("admin".equals(user.getRole())){
                // 删除
                folderService.eachDeleteFdr(key_random);
                // 添加日志
                Logs logs = new Logs(user.getId(),ipUtil.getIpAddr(request),"folder/delete",key_random+"","",new Date());
                logsDao.save(logs);
            }
        }

    }

    /* 删除deleteLiaray*/
    @ResponseBody
    @RequestMapping(value = "/deleteLiaray")
    public void deleteLiaray(HttpServletRequest request,HttpSession session,
                       @RequestParam(name = "id",defaultValue = "0",required = true) long id){
        User user = (User)session.getAttribute("userSession");
        if(null != user){
            if("admin".equals(user.getRole())){
                // 删除
                folderService.deleteLiaray(id);
                // 添加日志
                Logs logs = new Logs(user.getId(),ipUtil.getIpAddr(request),"folderLiaray/delete",id+"","",new Date());
                logsDao.save(logs);
            }
        }

    }



}
