package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 * url跳转页面
 * paddy 2018/9/17
 * */
@Controller
@RequestMapping("appPage1")
public class UrlController {
    @RequestMapping("/")
    public String index(){return "faqs/index";}

    @RequestMapping("/index")
    public String index1(){
        return "faqs/index";
    }

    @RequestMapping("/indexCRM")
    public String indexCRM(){
        return "faqs/indexCRM";
    }

    @RequestMapping("/search")
    public String search(){return "faqs/search";}

    @RequestMapping("/testChatbot")
    public String testChatbot(){return "faqs/testChatbot";}

    @RequestMapping("/testChatbot_infosun")
    public String testChatbot_infosun(){return "faqs/testChatbot_infosun";}

    @RequestMapping("/indexDetailed")
    public String indexDetailed(){return "faqs/indexDetailed";}

    @RequestMapping("/indexDetailedCRM")
    public String indexDetailedCRM(){return "faqs/indexDetailedCRM";}

    @RequestMapping("/admin/login")
    public String login(){return "faqs/admin/login";}

    @RequestMapping("/goLogin")
    public String goLogin(){
        return "redirect:/appPage/admin/login";
    }

    @RequestMapping("/admin/category")
    public String category(){
        return "faqs/admin/category";
    }

    @RequestMapping("/admin/categoryEdit")
    public String categoryEdit(){
        return "faqs/admin/categoryEdit";
    }

    @RequestMapping("/admin/languageEdit")
    public String languageEdit(){
        return "faqs/admin/languageEdit";
    }

    @RequestMapping("/admin/language")
    public String language(){
        return "faqs/admin/language";
    }

    @RequestMapping("/admin/detailed")
    public String detailed(){
        return "faqs/admin/detailed";
    }

    @RequestMapping("/admin/detailedEdit")
    public String detailedEdit(){
        return "faqs/admin/detailedEdit";
    }

    @RequestMapping("/admin/feedback")
    public String feedback(){
        return "faqs/admin/feedback";
    }

    @RequestMapping("/admin/feedbackSet")
    public String feedbackSet(){
        return "faqs/admin/feedbackSet";
    }

    @RequestMapping("/admin/notags")
    public String notags(){
        return "faqs/admin/notags";
    }

    @RequestMapping("/admin/faqOne")
    public String faqOne(){
        return "faqs/admin/faqOne";
    }

    @RequestMapping("/admin/faqOneEdit")
    public String faqOneEdit(){
        return "faqs/admin/faqOneEdit";
    }

    @RequestMapping("/admin/faqTwo")
    public String faqTwo(){return "faqs/admin/faqTwo";}

    @RequestMapping("/admin/faqThree")
    public String faqThree(){
        return "faqs/admin/faqThree";
    }

    @RequestMapping("/admin/eForm")
    public String eForm(){
        return "faqs/admin/eForm";
    }

    @RequestMapping("/admin/eFormSet")
    public String eFormSet(){
        return "faqs/admin/eFormSet";
    }

    @RequestMapping("/admin/report")
    public String report(){
        return "faqs/admin/report";
    }

    @RequestMapping("/admin/searchCollection")
    public String searchCollection(){
        return "faqs/admin/searchCollection";
    }

    /**
     * Request for itinerary 重新發送行程單
     * @return
     */
    @RequestMapping("/RequestForItinerary")
    public String eForm1(@RequestParam(name = "langId",defaultValue = "6",required = true) long langId
            , @RequestParam(name = "dlId",defaultValue = "0",required = true) long dlId
            , @RequestParam(name = "crm_uid",defaultValue = "",required = true) String crm_uid){
        return "redirect:https://eform.securesettlement.net/hkexpress/appPage/RequestForItinerary?langId="+langId+"&dlId="+dlId+"&crm_uid="+crm_uid;
    }

    /**
     * Duplicate Booking 重復訂單RefundWithNewBbooking
     * @return
     */
    @RequestMapping("/DuplicateBooking")
    public String eForm2(@RequestParam(name = "langId",defaultValue = "6",required = true) long langId
            , @RequestParam(name = "dlId",defaultValue = "0",required = true) long dlId
            , @RequestParam(name = "crm_uid",defaultValue = "",required = true) String crm_uid){
        return "redirect:https://eform.securesettlement.net/hkexpress/appPage/DuplicateBooking?langId="+langId+"&dlId="+dlId+"&crm_uid="+crm_uid;
    }

    /**
     * Request for Certificate   證明申請
     * @return
     */
    @RequestMapping("/RequestForCertificate")
    public String eForm3(@RequestParam(name = "langId",defaultValue = "6",required = true) long langId
            , @RequestParam(name = "dlId",defaultValue = "0",required = true) long dlId
            , @RequestParam(name = "crm_uid",defaultValue = "",required = true) String crm_uid){
        return "redirect:https://eform.securesettlement.net/hkexpress/appPage/RequestForCertificate?langId="+langId+"&dlId="+dlId+"&crm_uid="+crm_uid;
    }

    /**
     * Name Correction    姓名修正
     * @return
     */
    @RequestMapping("/NameCorrection")
    public String eForm4(@RequestParam(name = "langId",defaultValue = "6",required = true) long langId
            , @RequestParam(name = "dlId",defaultValue = "0",required = true) long dlId
            , @RequestParam(name = "crm_uid",defaultValue = "",required = true) String crm_uid){
        return "redirect:https://eform.securesettlement.net/hkexpress/appPage/NameCorrection?langId="+langId+"&dlId="+dlId+"&crm_uid="+crm_uid;
    }

    /**
     * Payment Failure   支付失敗
     * @return
     */
    @RequestMapping("/PaymentFailure")
    public String eForm5(@RequestParam(name = "langId",defaultValue = "6",required = true) long langId
            , @RequestParam(name = "dlId",defaultValue = "0",required = true) long dlId
            , @RequestParam(name = "crm_uid",defaultValue = "",required = true) String crm_uid){
        return "redirect:https://eform.securesettlement.net/hkexpress/appPage/PaymentFailure?langId="+langId+"&dlId="+dlId+"&crm_uid="+crm_uid;
    }

    /**
     * Reconfirm Flight 確認航班
     * @return
     */
    @RequestMapping("/ReconfirmFlight")
    public String eForm6(){
        // "faqs/eForm6"
        return "";
    }

    /**
     * Typhoon Move Flight 台风移动飞行
     * @return
     */
    @RequestMapping("/TyphoonMoveFlight")
    public String eForm7(@RequestParam(name = "langId",defaultValue = "6",required = true) long langId
            , @RequestParam(name = "dlId",defaultValue = "0",required = true) long dlId
            , @RequestParam(name = "crm_uid",defaultValue = "",required = true) String crm_uid){
        return "redirect:https://eform.securesettlement.net/hkexpress/appPage/TyphoonMoveFlight?langId="+langId+"&dlId="+dlId+"&crm_uid="+crm_uid;
    }

    /**
     * Refund with new booking 已購買新訂單的退款
     * @return
     */
    @RequestMapping("/RefundWithNewBbooking")
    public String eForm8(@RequestParam(name = "langId",defaultValue = "6",required = true) long langId
            , @RequestParam(name = "dlId",defaultValue = "0",required = true) long dlId
            , @RequestParam(name = "crm_uid",defaultValue = "",required = true) String crm_uid){
        return "redirect:https://eform.securesettlement.net/hkexpress/appPage/RefundWithNewBbooking?langId="+langId+"&dlId="+dlId+"&crm_uid="+crm_uid;
    }

    /**
     * check flight status 我想知道我的航班起飛時間
     * @return
     */
    @RequestMapping("/CheckFlightStatus")
    public String eForm9(@RequestParam(name = "langId",defaultValue = "6",required = true) long langId
            , @RequestParam(name = "dlId",defaultValue = "0",required = true) long dlId
            , @RequestParam(name = "crm_uid",defaultValue = "",required = true) String crm_uid){
        return "redirect:https://eform.securesettlement.net/hkexpress/appPage/CheckFlightStatus?langId="+langId+"&dlId="+dlId+"&crm_uid="+crm_uid;
    }

    /**
     * CreditVoucher 改為以禮券退款
     * @return
     */
    @RequestMapping("/CreditVoucher")
    public String eForm11(@RequestParam(name = "langId",defaultValue = "6",required = true) long langId
            , @RequestParam(name = "dlId",defaultValue = "0",required = true) long dlId
            , @RequestParam(name = "crm_uid",defaultValue = "",required = true) String crm_uid){
        return "redirect:https://eform.securesettlement.net/hkexpress/appPage/CreditVoucher?langId="+langId+"&dlId="+dlId+"&crm_uid="+crm_uid;
    }

    @RequestMapping("/admin/folder")
    public String folder(){
        return "faqs/admin/folder";
    }

    @RequestMapping("/admin/selectFeedback")
    public String selectFeedback(){
        return "faqs/admin/selectFeedback";
    }


}
