package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.*;
import com.example.demo.dao.E_area_nameDao;
import com.example.demo.dao.EformDao;
import com.example.demo.dao.LanguageDao;
import com.example.demo.entity.EformTotal;
import com.example.demo.service.EformService;
import com.example.demo.service.LanguageService;
import com.example.demo.util.IpUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/*
 * EformController
 * paddy 2019/3/26
 * */
@Controller
@RequestMapping(value = "appJson")
public class EformController {
    private  static Logger logger = LoggerFactory.getLogger(EformController.class);

    @Resource
    EformService eformService;

    /**
     * URL跳转
     * @return
     */
    @RequestMapping(value="/eForm{id}",method= RequestMethod.GET)
    public String eFormIndex(HttpServletRequest request, @PathVariable("id") String id,
                             @RequestParam(name = "langId",defaultValue = "6",required = true) long langId
            , @RequestParam(name = "dlId",defaultValue = "0",required = true) long dlId
            , @RequestParam(name = "crm_uid",defaultValue = "",required = true) String crm_uid){
        E_form_Monitor monitor = new E_form_Monitor();
        monitor.setClientip(IpUtil.getIpAddr(request));
        monitor.setLangId(langId);
        monitor.setEtId(Long.parseLong(id));
        monitor.setCreateDate(new Date());
        if(!IpUtil.checkInternal(request)){
            crm_uid = "";
        }
        monitor.setCrmuid(crm_uid);
        eformService.saveE_form_Monitor(monitor);

        String t = "";
        if("1".equals(id)){
            t = "RequestForItinerary";
        }else if("2".equals(id)){
            t = "DuplicateBooking";
        }else if("3".equals(id)){
            t = "RequestForCertificate";
        }else if("4".equals(id)){
            t = "NameCorrection";
        }else if("5".equals(id)){
            t = "PaymentFailure";
        }else if("6".equals(id)){
            t = "ReconfirmFlight";
        }else if("7".equals(id)){
            t = "TyphoonMoveFlight";
        }else if("8".equals(id)){
            t = "RefundWithNewBbooking";
        }else if("9".equals(id)){
            // t = "CheckFlightStatus";
            String s = "en";
            if(langId == 1){
                s = "tc";
            }else if(langId == 2){
                s = "sc";
            }
            return "redirect:https://www.hongkongairport.com/"+s+"/search-result.page?q=UO";
        }else if("10".equals(id)){
            String s = "en-hk";
            if(langId == 1){
                s = "zh-hk";
            }else if(langId == 2){
                s = "zh-cn";
            }
            return "redirect:https://www.hkexpress.com/"+s+"/your-trips/important-travel-notice/";
        }
        return "redirect:/appPage/"+t+"?langId="+langId+"&dlId="+dlId+"&crm_uid="+crm_uid;
    }


    /**
     * 地区名
     * @return
     */
    @ResponseBody
    @RequestMapping("/E/getAreaNames")
    public List<E_area_name> getAreaNames(){
        return eformService.getAreaNames();
    }

    /**
     * eForm3
     * 实例化
     * @return
     */
    @ResponseBody
    @RequestMapping("/E/getEform3")
    public RestResultModule getEform3(){
        RestResultModule module = new RestResultModule();
        // 语言
        module.putData("languages",eformService.findAllLanguage());
        // 地区名
        module.putData("e_area_names",eformService.getAreaNames());
        // 证明类别
        module.putData("e_certificates",eformService.getCertificates());
        return module;
    }


    /**
     * 添加
     */
    @ResponseBody
    @RequestMapping(value = "/E/add",method= RequestMethod.POST)
    public RestResultModule add(HttpServletRequest request,@RequestBody EformTotal eformTotal){
        Eform eform = eformTotal.getEform();
        String crm_uid = eformTotal.getCrmuid();
        if(!IpUtil.checkInternal(request)){
            crm_uid = "";
        }
        RestResultModule module = new RestResultModule();
        if(null != eform){
           try{
               eform.setRandom(eformService.getRandom());
               eform.setUpdateDate(new Date());
               // 添加form结果表
               E_form_result result = new E_form_result();
               result.setCrmuid(crm_uid);
               String state = "-3";
               if(null != eform.getPnr()){
                   state = eformService.getBookingAPI(eform,result);
               }else{
                   state = "0";
               }
               // tpey=8需比较两次PNR
               if("0".equals(state) && "8".equals(eform.getType())){
                   Eform e1 = new Eform();
                   e1.setPnr(eform.getPnrnew());
                   e1.setFirstname(eform.getFirstname());
                   e1.setLastname(eform.getLastname());
                   e1.setEmail(eform.getEmail());
                   E_form_result r1 = new E_form_result();
                   r1.setCrmuid(crm_uid);
                   String s1 = eformService.getBookingAPI(e1,r1);
                   if("0".equals(s1)){
                       result.setEid(eform.getId());
                       eformService.saveResult(r1);
                   }else{
                       module.setCode(404);
                       return module;
                   }
               }

               // 比较接口 State=0时表示"Matched"; 其它值表示"Not Matched".
               if("0".equals(state)){
                   eformService.save(eform);
                   result.setEid(eform.getId());
                   eformService.saveResult(result);
                   // 发邮件
                   Map<String, Object> valueMap = new HashMap<>();
                   valueMap.put("eform", eform);
                   valueMap.put("title", eformService.getMailType(eform.getType(),eform.getLangId(),eform.getPnr(),crm_uid));
                   valueMap.put("cc", eform.getEmail());
                   valueMap.put("Certificate_Nature", eformService.getCertificateTitle(eform.getEcertificatetype()));
                   eformService.sendSimpleMail(valueMap);
                   // 发确认邮件
                   Map<String, Object> valueMapUser = new HashMap<>();
                   valueMapUser.put("title", eformService.getMailUserType(eform.getLangId().toString()));
                   valueMapUser.put("To",eform.getEmail());
                   valueMapUser.put("langId",eform.getLangId());
                   valueMapUser.put("random",eform.getRandom());
                   eformService.sendSimpleMailUser(valueMapUser);
                   // 返回成功码
                   module.putData("key",eform.getRandom());
               }else{
                   module.setCode(404);
               }
           }catch (Exception e){
               logger.error("-----------/E/add-----------"+e,eform);
               module.setCode(500);
           }
       }
       return module;
    }


    /**
     * eform9 - 查询航班信息
     */
    @ResponseBody
    @RequestMapping(value = "/E/searchFlightIRR",method= RequestMethod.POST)
    public RestResultModule addTest(@RequestBody Eform eform){
        RestResultModule module = new RestResultModule();
        if(null != eform){
            try{
                eform.setRandom(eformService.getRandom());
                eform.setUpdateDate(new Date());
                // 添加form结果表
                E_form_result result = new E_form_result();
                Map<String,String> map = eformService.getBookingAPIEform9(eform,result);
                // 比较接口 State=0时表示"Matched"; 其它值表示"Not Matched".
                if("0".equals(map.get("State"))){
                    eformService.save(eform);
                    result.setEid(eform.getId());
                    eformService.saveResult(result);
                    // 判断是否显示 New Flight Schedule
                    if("null".equals(map.get("newFlightNo")) && "null".equals(map.get("newDepartureDate"))&& "null".equals(map.get("newDepartingFrom"))&& "null".equals(map.get("newArrivingAt"))){
                        module.putData("map_no_date",true);
                    }else{
                        module.putData("map_no_date",false);
                    }

                    // 返回成功码
                    module.putData("map",map);
                }else{
                    logger.error("-----------/E/searchFlightIRR-----------error=",eform);
                    module.setCode(404);
                }
            }catch (Exception e){
                logger.error("-----------/E/searchFlightIRR-----------"+e,eform);
                module.setCode(500);
            }
        }
        return module;
    }

    /**
     * test
     * @return
     */
    @ResponseBody
    @RequestMapping("/test")
    public String test(Model model) throws Exception{
        System.out.println("开始");
        return "ok";
    }

}
