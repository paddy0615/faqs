package com.example.demo.controller;

import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

/*
 * PdfController 下载PDF
 * paddy 2019/8/28
 * */
@Controller
@RequestMapping(value = "appJson")
public class PdfController {
    private  static Logger logger = LoggerFactory.getLogger(PdfController.class);

    @Value("${web.upload-path}")
    private static String path; //读取配置文件中的参数

    // 利用模板生成pdf
    public static void fillTemplate() {
        System.out.println("path="+path);
        // 模板路径
        String templatePath = "E:\\Flight Cancel Certificate.pdf";
        // 生成的新文件路径
        String newPDFPath = "E:\\Flight Cancel CertificateNew1.pdf";
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            out = new FileOutputStream(newPDFPath);// 输出流
            reader = new PdfReader(templatePath);// 读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            String[] str = { "UO1850",
                    "2016 年 8 月 2 日 "+"\n"+" dfdf한 글자 이상되면 중간에",
                    "高松 " +"\n"+" スペイスで分けてくださいませ",
                    "香港"+"\n"+" Hong Kong",
                    "Tung Hin Chuen "+"\n"+" Cheng  Chau Lai "+"\n"+" "+"\n"+" Tung Min Shun",
                    "2100LT / 2016 年 08 月 02  日"+"\n"+" 2100LT / 02 Aug 2016",
                    "天氣原因",
                    "Bad Weather Condition" ,
                    "2016 年 8 月 9 日"+"\n"+"09Aug 2016"};

            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
            int i = 0;
            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next().toString();
                System.out.println(name);
                    // 设置支持中文
                form.setFieldProperty(name, "textfont", bf, null);
                form.setField(name, str[i++]);

            }
            stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();

            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();

        } catch (IOException e) {
            System.out.println(e);
        } catch (DocumentException e) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        fillTemplate();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String d =  df.format(new Date());
        System.out.println(d);// new Date()为获取当前系统时间
    }

}
