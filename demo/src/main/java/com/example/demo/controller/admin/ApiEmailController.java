package com.example.demo.controller.admin;


import com.example.demo.bean.EmailEntity;
import com.example.demo.beanConfig.EmailConfig;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/json/admin/api/email")
public class ApiEmailController {
    private  static Logger logger = LoggerFactory.getLogger(ApiEmailController.class);
    @Autowired
    EmailConfig mc;
    @Ignore
    @PostMapping("send")
    public void testEmailConfig(){
        EmailEntity email = new EmailEntity();
        email.setReceiver("942539456@qq.com");
        email.setContent("welcome Email Sender");
        email.setSubject("Spring Boot Java EE Developer");
        mc.sendSimpleMail(email);
        logger.info("successful to send message!");
    }
}
