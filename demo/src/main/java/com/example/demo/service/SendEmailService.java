package com.example.demo.service;

import com.example.demo.service.impl.SendEmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Properties;

@Service("sendEmailService")
public class SendEmailService implements SendEmailServiceImpl {
    private  static Logger logger = LoggerFactory.getLogger(SendEmailService.class);

    public void sendSimpleMail() throws Exception {
    }



}
