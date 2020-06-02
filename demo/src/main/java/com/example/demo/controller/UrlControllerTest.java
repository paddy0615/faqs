package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * url跳转页面
 * paddy 2018/9/17
 * */
@Controller
@RequestMapping("appPage")
public class UrlControllerTest {
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
    public String eForm1(){return "faqs/eForm1";}

    /**
     * Duplicate Booking 重復訂單RefundWithNewBbooking
     * @return
     */
    @RequestMapping("/DuplicateBooking")
    public String eForm2(){return "faqs/eForm2";}

    /**
     * Request for Certificate   證明申請
     * @return
     */
    @RequestMapping("/RequestForCertificate")
    public String eForm3(){return "faqs/eForm3";}

    /**
     * Name Correction    姓名修正
     * @return
     */
    @RequestMapping("/NameCorrection")
    public String eForm4(){return "faqs/eForm4";}


    /**
     * Payment Failure   支付失敗
     * @return
     */
    @RequestMapping("/PaymentFailure")
    public String eForm5(){return "faqs/eForm5";}

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
    public String eForm7(){return "faqs/eForm7";}

    /**
     * Refund with new booking 已購買新訂單的退款
     * @return
     */
    @RequestMapping("/RefundWithNewBbooking")
    public String eForm8(){return "faqs/eForm8";}

    /**
     * check flight status 我想知道我的航班起飛時間
     * @return
     */
    @RequestMapping("/CheckFlightStatus")
    public String eForm9(){return "faqs/eForm9";}

    /**
     * CreditVoucher 改為以禮券退款
     * @return
     */
    @RequestMapping("/CreditVoucher")
    public String eForm11(){
        return "faqs/eFormError";
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
