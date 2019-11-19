package com.example.demo.controller.admin;

import com.example.demo.bean.*;
import com.example.demo.dao.*;
import com.example.demo.entity.EformEntity;
import com.example.demo.service.EformService;
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
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

/*
 * admin/EformController
 * paddy 2019/4/10
 * */
@Controller
@RequestMapping(value = "appJson/admin")
@Component("AdminEformController")
public class EformController {
    private  static Logger logger = LoggerFactory.getLogger(EformController.class);

    @Resource
    EformService eformService;
    @Resource
    E_form_type_displayDao e_form_type_displayDao;
    @Resource
    LogsDao logsDao;
    @Resource
    IpUtil ipUtil;
    @Resource
    E_form_resultDao e_form_resultDao;



    /* 初始化*/
    @ResponseBody
    @RequestMapping("/getEformPage")
    public RestResultModule getEformPage(@RequestBody Map<String,Object> map){
        RestResultModule module = new RestResultModule();
        int CurrentPage = Integer.parseInt(map.get("CurrentPage").toString());
        int PageSize = Integer.parseInt(map.get("PageSize").toString());
        long langId = Long.parseLong(map.get("langId").toString());
        long type = Long.parseLong(map.get("type").toString());
        String startTime = map.get("startTime").toString();
        String endTime = map.get("endTime").toString();
        String searchTest = map.get("searchTest").toString();
        //分页
        Pageable pageable = new PageRequest(CurrentPage-1,PageSize);
        Page<EformEntity> eformEntities = null;
        eformEntities = eformService.getEformEntityPage(langId,type,startTime,endTime,searchTest,pageable);
        module.putData("eforms",eformEntities.getContent());
        module.putData("PageCount",eformEntities.getTotalElements());
        module.putData("languages",eformService.findAllLanguage());
        module.putData("eformTypes",eformService.getAllByDlIdtest());
        module.putData("eformTypes_n",eformService.getAllByDlIdtest());
        module.putData("efds",e_form_type_displayDao.findAll());
        return module;
    }

    /**
     * 按ID查询
     * @param eId
     * @return
     */
    @ResponseBody
    @RequestMapping("/findEformById")
    public RestResultModule findEformById(@RequestParam(name = "eId",defaultValue = "0",required = true) long eId) throws Exception{
        RestResultModule module = new RestResultModule();
        Eform eform = eformService.findEformById(eId);
        String title = "";
        String certificate_Nature = "";
        if(null != eform){
            E_form_result result = e_form_resultDao.findByeEid(eform.getId());
       /*     if("3".equals(eform.getType())){
                title = eformService.getMailType1(eform,result.getCrmuid());
            }else{
                title = eformService.getMailType(eform.getType(),eform.getLangId(),eform.getPnr(),result.getCrmuid());
            }*/
            title = eformService.getMailTypeNew(eform,result.getCrmuid());

            certificate_Nature = eformService.getCertificateTitle(eform.getEcertificatetype());
        }
        module.putData("title",title);
        module.putData("eform",eform);
        module.putData("Certificate_Nature",certificate_Nature);
        return module;
    }

    /**
     * 前端首页显示Eform,编辑
     * @param efs
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateEformHomeHisplay")
    public RestResultModule updateEformHomeHisplay(HttpServletRequest request, HttpSession session,
                                          @RequestParam(name = "efs",defaultValue = "",required = true) String efs) throws Exception{
        RestResultModule module = new RestResultModule();
        System.out.println(efs);
        User user = (User)session.getAttribute("userSession");
        if(null != user){
            e_form_type_displayDao.deleteAll();
            E_form_type_display display = null;
            String [] ids = efs.split(",");
            int i = 0;
            for (String id : ids) {
                if("".equals(id)){
                    continue;
                }
                display = new E_form_type_display();
                display.setUserid(user.getId());
                display.setCreatedate(new Date());
                display.setEtid(Long.parseLong(id));
                display.setOrder(++i);
                e_form_type_displayDao.save(display);
            }

            // 添加日志
            Logs logs = new Logs(user.getId(),ipUtil.getIpAddr(request),"E-Form/updateEformHomeHisplay","show="+efs,"",new Date());
            logsDao.save(logs);

        }
        module.setMsg("Success");
        return module;
    }


}
