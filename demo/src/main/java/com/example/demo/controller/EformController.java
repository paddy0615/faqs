package com.example.demo.controller;

import com.example.demo.bean.*;
import com.example.demo.entity.CommomClass;
import com.example.demo.entity.EformTotal;
import com.example.demo.service.ConnectionSqlService;
import com.example.demo.service.EformService;
import com.example.demo.service.PdfService;
import com.example.demo.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.*;

/*
 * EformController
 * paddy 2019/3/26
 * */
@Controller
@RequestMapping(value = "appJson")
public class EformController {
    private  static Logger logger = LoggerFactory.getLogger(EformController.class);
    @Value("${spring.profiles.active}")
    private String active; //读取配置文件中的参数

    @Resource
    EformService eformService;
    @Resource
    ConnectionSqlService connectionSqlService;
    @Resource
    PdfService pdfService;


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
            //t = "RefundWithNewBbooking";
            String s = "en";
            if(langId == 1){
                s = "zh_hk";
            }else if(langId == 2){
                s = "zh_cn";
            }else if(langId == 4){
                s = "ja";
            }else if(langId == 5){
                s = "ko";
            }
            return "redirect:https://securesettlement.net/hkexpress/e-refund/"+s;
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
                   Map<String, Object> valueMap = new HashMap<>();
                   eformService.save(eform);
                   result.setEid(eform.getId());
                   String zohomailtitle = eformService.getMailTypeNew(eform,crm_uid,-1);
                   result.setZohomailtitle(zohomailtitle);
                   eformService.saveResult(result);
                   E_form_relation relation = eformTotal.getRelation();
                   if(relation != null && relation.getTriptype() > 0){
                       relation.setEid(eform.getId());
                       eformService.saveRelation(relation);
                       valueMap.put("eformRelation", relation);
                   }

                   // 发邮件(zoho)
                   valueMap.put("eform", eform);
                   valueMap.put("title", zohomailtitle);
                   valueMap.put("cc", eform.getEmail());
                   valueMap.put("Certificate_Nature", eformService.getCertificateTitle(eform.getEcertificatetype()));
                   eformService.sendSimpleMail(valueMap);
                   // 发确认邮件(给客户)
                   Map<String, Object> valueMapUser = new HashMap<>();
                   valueMapUser.put("title", eformService.getMailUserType(eform.getLangId().toString()));
                   valueMapUser.put("To",eform.getEmail());
                   valueMapUser.put("langId",eform.getLangId());
                   valueMapUser.put("random",eform.getRandom());
                   eformService.sendSimpleMailUser(valueMapUser);
                   // 返回成功码
                   eformService.updateEformStatus(eform.getId(),1);
                   module.putData("key",eform.getRandom());
                   module.putData("email",eform.getEmail());
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
     * 添加3 - RequestForCertificate
     */
    @ResponseBody
    @RequestMapping(value = "/E/add3",method= RequestMethod.POST)
    public RestResultModule add3(HttpServletRequest request,@RequestBody EformTotal eformTotal){
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
                // 因有多个旅客，需一一与API对比
                String [] firstnameArr = eform.getFirstname().split(",");
                String [] lastnameArr = eform.getLastname().split(",");
                if(null != eform.getPnr()){
                    state = "0";
                    if("pro".equals(active)){
                        for (int i = 0;i<firstnameArr.length;i++){
                            if(firstnameArr[i] == "" || lastnameArr[i] == ""){
                                module.setCode(404);
                                return module;
                            }
                            Eform eform1 = new Eform();
                            eform1.setPnr(eform.getPnr());
                            eform1.setFirstname(firstnameArr[i]);
                            eform1.setLastname(lastnameArr[i]);
                            System.out.println(firstnameArr[i]+","+lastnameArr[i]);
                            state = eformService.getBookingAPI(eform1,result);
                            if(!"0".equals(state)){
                                module.setCode(404);
                                return module;
                            }
                        }
                    }
                }

                // 比较接口 State=0时表示"Matched"; 其它值表示"Not Matched".
                if("0".equals(state)){
                    eformService.save(eform);
                    result.setEid(eform.getId());
                    eformService.saveResult(result);
                    // 发邮件（给zoho跟单）
                    Map<String, Object> valueMap = new HashMap<>();
                    // 发确认邮件(给客)
                    Map<String, Object> valueMapUser = new HashMap<>();
                    int listSize = -1;
                    // 如何e_certificate_type =1 or =4 ,邮件方式不一样
                    if(eform.getEcertificatetype() == 1 || eform.getEcertificatetype() == 4 ){
                        List list = connectionSqlService.searchFlightIRRList(eform.getPnr(),eform.getFlightno(),eform.getDeparturedate(),result);
                        eformService.updateResultXml(result);
                        listSize = list.size();
                        if(listSize == 0){
                            if(eform.getEcertificatetype() == 1){
                                module.setCode(404);
                                return module;
                            }
                        }else{
                            // 对应类型。
                            CommomClass commomClass = (CommomClass) list.get(0);
                            // TBA ,IRR待定信息
                            if(commomClass.getTemplate()!=null && commomClass.getTemplate().contains("is TBA")){
                                module.setCode(405);
                                return module;
                            }
                            if(eform.getEcertificatetype() == 1){
                                if(!"Rescheduled Flights".equalsIgnoreCase(commomClass.getTemplate())){
                                    module.setCode(404);
                                    return module;
                                }
                            }else{
                                if(!"Cancelled Flights with new flight schedule".equalsIgnoreCase(commomClass.getTemplate()) &&
                                        !"Cancelled Flights without new flight schedule".equalsIgnoreCase(commomClass.getTemplate()) &&
                                        !"Cancelled Flights but new flight".equalsIgnoreCase(commomClass.getTemplate()) &&
                                        !"schedule is TBA".equalsIgnoreCase(commomClass.getTemplate())){
                                    module.setCode(404);
                                    return module;
                                }
                            }
                            // 填充pdf-一个旅客对应一个个PDF
                            String e_flie = "";
                            for (int i = 0;i<firstnameArr.length;i++){
                                String s = pdfService.fillTemplate(eform,firstnameArr[i],lastnameArr[i],commomClass);
                                e_flie += s+",";
                            }
                            e_flie = e_flie.substring(0,e_flie.length()-1);
                            eform.setFlie(e_flie);
                            eformService.updateEformFlie(eform.getId(),e_flie);
                            valueMap.put("ecertificatetype", eform.getEcertificatetype().toString());
                            valueMapUser.put("ecertificatetype", eform.getEcertificatetype().toString());
                            module.putData("ecertificatetype",eform.getEcertificatetype());
                        }

                    }
                    valueMap.put("eform", eform);
                    String zohomailtitle = eformService.getMailTypeNew(eform,crm_uid,listSize);
                    result.setZohomailtitle(zohomailtitle);
                    eformService.updateResultzohomailtitle(result);
                    valueMap.put("title", zohomailtitle);
                    valueMap.put("cc", eform.getEmail());
                    valueMap.put("Certificate_Nature", eformService.getCertificateTitle(eform.getEcertificatetype()));
                    eformService.sendSimpleMail(valueMap);

                    valueMapUser.put("eform", eform);
                    if(eform.getEcertificatetype() == 1 || eform.getEcertificatetype() == 4 ){
                        if(listSize == 0){
                            valueMapUser.put("title", eformService.getMailUserType(eform.getLangId().toString()));
                        }else{
                            String s = "Certificate -";
                            if(eform.getEcertificatetype() == 1){
                                s += " Flight Delay";
                            }else {
                                s += " Flight Cancel";
                            }
                            s += " -- PNR: "+eform.getPnr();
                            valueMapUser.put("title", s);
                        }
                    }else{
                        valueMapUser.put("title", eformService.getMailUserType(eform.getLangId().toString()));
                    }
                    valueMapUser.put("To",eform.getEmail());
                    valueMapUser.put("langId",eform.getLangId());
                    valueMapUser.put("random",eform.getRandom());
                    eformService.sendSimpleMailUser(valueMapUser);

                    // 返回成功码
                    eformService.updateEformStatus(eform.getId(),1);
                    module.putData("key",eform.getRandom());
                    module.putData("email",eform.getEmail());
                }else{
                    module.setCode(404);
                }
            }catch (Exception e){
                logger.error("-----------/E/add3-----------"+e,eform);
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
    @RequestMapping(value = "/E/searchFlightIRRTest")
    public String getList(@RequestParam(name = "pnr",defaultValue = "",required = true) String pnr) throws Exception{
        System.out.println("开始");
        List irrList = new ArrayList();
        String sql = "";
        String ordersID = "";
        try {
            if ((pnr != null) && (pnr.length() != 0)) {
                ordersID = connectionSqlService.getJobID(pnr);
                if ((ordersID != null) && (ordersID.length() != 0)) {
                    ordersID = ordersID.substring(0, ordersID.length() - 1);
                }
            }else {
                // 结束
                return "null";
            }
            if ((ordersID != null) && (ordersID.length() != 0)) {
                sql = "select jobID,irrCategory,template,reasons,flightNo,newFlightNo,departingFrom,arrivingAt,departureDate,arrivalDate,newDepartureDate,newArrivalDate,createdDate,protectionoffer from flightirr_sendingorders where ID in (" + ordersID + ") and status=1";
              /*  sql = "select jobID,irrCategory,template,reasons,flightNo,newFlightNo,departingFrom,arrivingAt," +
                        "departureDate,arrivalDate,newDepartureDate,newArrivalDate,createdDate,protectionoffer " +
                        "from flightirr_sendingorders where ID in (" + ordersID + ") and status=1 and flightNo='UO" + fltNo + "'";
            */
            }
            Connection con = null;
            if (!sql.equals("")) {
                irrList = connectionSqlService.getList(sql);
            }

        }catch (Exception e){
            logger.error(e.toString());
            System.out.println(e.toString());
        }

        String xml = "";
        for (int i = 0; irrList.size() > i; i++) {
            CommomClass cc = (CommomClass) irrList.get(i);
            //获取class对象a中声明的所有字段
            Field[] field = cc.getClass().getDeclaredFields();
            for(int j=0;j<field.length;j++){
                //设置是否允许访问，不是修改原来的访问权限修饰词。
                field[j].setAccessible(true);
                //返回输出指定对象a上此 Field表示的字段名和字段值
                xml += " ("+field[j].getName()+") "+"<span style='color:red'>"+field[j].get(cc)+"</span>";
                xml += "<br/>";
            }
            xml += "<br/>";
        }
        if(xml == ""){
            xml = "pnr错误";
        }

        return xml;
    }

    /**
     * test
     * XML
     * produces = MediaType.APPLICATION_XML_VALUE
     * JSON
     * produces = MediaType.APPLICATION_JSON_VALUE
     */
    @ResponseBody
    @RequestMapping(value = "/E/searchFlightIRRTest1")
    public String getList1() throws Exception{
        // 发确认邮件(给客)
        Map<String, Object> valueMapUser = new HashMap<>();
        valueMapUser.put("title", "Certificate - Flight Cancel -- PNR: test1");
        valueMapUser.put("To","gary.lam@sonic-teleservices.com");
        valueMapUser.put("langId",1);
        valueMapUser.put("random","123456789");
        eformService.sendSimpleMailUser(valueMapUser);


        return "";
    }

}
