package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * url- 只对eform系统有关。
 * */
@Controller
@RequestMapping("appPage12")
public class UrlControllerEform {

    /**
     * Request for itinerary 重新發送行程單
     * @return
     */
    @RequestMapping("/RequestForItinerary")
    public String eForm1(){
        return "faqs/eForm1";

    }

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



}
