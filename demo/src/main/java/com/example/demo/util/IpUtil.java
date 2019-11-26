package com.example.demo.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component(value = "ipUtil")
public class IpUtil {
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    public static boolean checkInternal(HttpServletRequest request) {
        String ip = getIpAddr(request);
        //判断是否以指定字符串开头
        //输出结果是否为真，返回布尔类型
        //判断字符串是否已百度二字开头
        return ip.startsWith("192.168") || ip.startsWith("118.143.2.7");
    }


}
