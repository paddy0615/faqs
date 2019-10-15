package com.example.demo.service;

import com.example.demo.bean.E_pdf_area;
import com.example.demo.bean.Eform;
import com.example.demo.dao.E_pdf_areaDao;
import com.example.demo.entity.CommomClass;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
 * pdfService
 * paddy 2019/9/25
 * */
@Service("pdfService")
public class PdfService {
    private  static Logger logger = LoggerFactory.getLogger(PdfService.class);

    @Value("${web.upload-path}")
    private String path; //读取配置文件中的参数

    @Resource
    E_pdf_areaDao e_pdf_areaDao;

    // 利用模板生成pdf
    public String fillTemplate(Eform eform,String firstnameArr,String lastnameArr, CommomClass commomClass) {
        try {
            String eFormpath = path+"/eForm/";
            // 模板路径
            //String templatePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/img/Flight Cancel Certificate.pdf";
            String templatePath = eFormpath+"Certificate"+eform.getEcertificatetype()+".pdf";
            // 生成的新文件路径
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
            String filePath1 = eFormpath+df.format(new Date());
            //目标目录
            File targetfile1 = new File(filePath1);
            if(!targetfile1.exists()) {
                targetfile1.mkdirs();
            }
            String filePath2 = filePath1+"/"+eform.getRandom();
            File targetfile2 = new File(filePath2);
            if(!targetfile2.exists()) {
                targetfile2.mkdirs();
            }
            // 附件名称
            String certificateType = "/Flight Delay Certificate";
            if(eform.getEcertificatetype() == 4){
                certificateType = "/Flight Cancel Certificate";
            }
            certificateType +="-"+lastnameArr+" "+firstnameArr+".pdf";
            String newPDFPath = filePath2+certificateType;
            PdfReader reader;
            FileOutputStream out;
            ByteArrayOutputStream bos;
            PdfStamper stamper;
            out = new FileOutputStream(newPDFPath);// 输出流
            reader = new PdfReader(templatePath);// 读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
            // 黑体：win=c://windows//fonts//SIMHEI.TTF , linux=/usr/share/fonts/simsun.ttc
            //String ttf = "c://windows//fonts//SIMHEI.TTF";
            //BaseFont bfComic = BaseFont.createFont(ttf, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            int i = 0;
            java.util.Iterator<String> it = form.getFields().keySet().iterator();

            while (it.hasNext()) {
                String name = it.next().toString();
                // 设置支持中文
                form.setFieldProperty(name, "textfont", bf, null);
                //form.setFieldProperty(name, "textfont", bfComic, null);
                switch (name)
                {
                    case "flightNo":
                        form.setField(name, commomClass.getFlightNo());
                        break;
                    case "departureDate":
                        try{
                            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(commomClass.getDepartureDate());
                            String now1 = new SimpleDateFormat("yyyy 年 MM 月 dd 日").format(date);
                            String now2 = new SimpleDateFormat("dd MMMM yyyy", Locale.UK).format(date);      // 日月年 月为英文
                            form.setField(name, now1 +"\n"+ now2);
                        }catch (Exception e){
                            logger.error("时间错误="+commomClass.getDepartureDate()+","+e);
                        }
                        break;
                    case "departingFrom":
                        E_pdf_area area1 = e_pdf_areaDao.getByKey(commomClass.getDepartingFrom());
                        form.setField(name, area1.getHk()+"\n"+ area1.getEn());
                        break;
                    case "arrivingAt":
                        E_pdf_area area2 = e_pdf_areaDao.getByKey(commomClass.getArrivingAt());
                        form.setField(name, area2.getHk()+"\n"+ area2.getEn());
                        break;
                    case "guestName":
                        form.setField(name, firstnameArr+" "+lastnameArr);
                        break;
                    case "departureDateTime":
                        String departureDateTime = commomClass.getDepartureDate()+" "+commomClass.getDepartureTime();
                        try{
                            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(departureDateTime);
                            String now1 = new SimpleDateFormat("HHmm 'LT' / yyyy 年 MM 月 dd 日").format(date);
                            String now2 = new SimpleDateFormat("HHmm 'LT' / dd MMMM yyyy", Locale.UK).format(date);     // 日月年 月为英文
                            form.setField(name, now1 +"\n"+ now2);
                        }catch (Exception e){
                            logger.error("时间错误="+departureDateTime+","+e);
                        }
                        break;
                    case "newdepartureDateTime":
                        String newdepartureDateTime = commomClass.getNewDepartureDate()+" "+commomClass.getNewDepartureTime();
                        try{
                            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(newdepartureDateTime);
                            String now1 = new SimpleDateFormat("HHmm 'LT' / yyyy 年 MM 月 dd 日").format(date);
                            String now2 = new SimpleDateFormat("HHmm 'LT' / dd MMMM yyyy", Locale.UK).format(date);     // 日月年 月为英文
                            form.setField(name, now1 +"\n"+ now2);
                        }catch (Exception e){
                            logger.error("时间错误="+newdepartureDateTime+","+e);
                        }
                        break;
                    case "reasons":
                        String [] arr = commomClass.getReasons().split("/");
                        String e_s = "";
                        for (String s:arr) {
                            e_s += s+"\n";
                        }
                        form.setField(name, e_s);
                        break;
                    case "createdDate":
                        String now1 = new SimpleDateFormat("yyyy 年 MM 月 dd 日").format(eform.getUpdateDate());
                        String now2 = new SimpleDateFormat("dd MMMM yyyy", Locale.UK).format(eform.getUpdateDate());
                        form.setField(name, now1 +"\n"+ now2);
                        break;
                }

            }
            stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();

            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
            String flieUrl = newPDFPath.substring(newPDFPath.indexOf("eForm"));
            logger.info("pdf路径="+ flieUrl);
            return flieUrl;
        } catch (IOException e) {
            System.out.println(e);
            logger.error("IOException",e);
            return null;
        } catch (DocumentException e) {
            System.out.println(e);
            logger.error("DocumentException",e);
            return null;
        }

    }

}
