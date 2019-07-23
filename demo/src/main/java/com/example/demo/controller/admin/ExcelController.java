package com.example.demo.controller.admin;

import com.example.demo.bean.Detailed;
import com.example.demo.bean.Language;
import com.example.demo.dao.LanguageDao;
import com.example.demo.entity.ExcelConstant;
import com.example.demo.entity.ExcelData;
import com.example.demo.service.DetailedService;
import com.example.demo.util.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * 报表Excel
 * paddy 2019/02/28
 * */
@RestController
@RequestMapping(value = "appJson/admin/excel")
@Component("AdminExcelController")
public class ExcelController {
    private  static Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @Resource
    private LanguageDao languageDao;

    @Resource
    private DetailedService detailedService;

    @RequestMapping("/test")
    public void test(){
        int rowIndex = 0;
        List<Detailed> list = detailedService.getAllByLangId(6);
        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList<>();
        titles.add("ID");
        titles.add("userName");
        titles.add("password");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        for(int i = 0, length = list.size();i<length;i++){
            Detailed detailed = list.get(i);
            List<Object> row = new ArrayList();
            row.add(detailed.getId());
            row.add(detailed.getTitle());
            row.add(detailed.getTitle());
            rows.add(row);
        }
        data.setRows(rows);
        try{
            rowIndex = ExcelUtils.generateExcel(data, ExcelConstant.FILE_PATH + ExcelConstant.FILE_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }
        //return RetResponse.makeOKRsp(Integer.valueOf(rowIndex));
    }

    @RequestMapping("/test2")
    public void test2(HttpServletResponse response){
        List<Language> languages = languageDao.findAll();
        Language l  = languages.get(2);
        List<Detailed> list = detailedService.getAllByLangId(l.getId());
        ExcelData data = new ExcelData();
        data.setName(l.getTitle());
        List<String> titles = new ArrayList();
        titles.add("父级");
        titles.add("问题ID");
        titles.add("标题");
        titles.add("答案");
        data.setTitles(titles);
        List<List<Object>> rows = new ArrayList();
        for(int i = 0, length = list.size();i<length;i++){
            Detailed detailed = list.get(i);
            List<Object> row = new ArrayList();
            row.add(detailed.getFlTitle());
            row.add(detailed.getId());
            row.add(detailed.getTitle());
            row.add(detailed.getContentTxt());
            rows.add(row);
        }
        data.setRows(rows);
        try{
            ExcelUtils.exportExcel(response,"Faq20190510全部问题详情"+l.getId(),data);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 获取全部FAQ
     * @param response
     */
    @RequestMapping("/faq")
    public void test3(HttpServletResponse response){
        List<Language> languages = languageDao.findAll();
        try {
            Date now = new Date( );
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd hh:mm:ss");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("FAQ全部问题"+ ft.format(now) + ".xls", "utf-8"));
            OutputStream out = response.getOutputStream();
            String[] headers = { "父级", "问题ID" ,"标题","答案"};
            ExcelUtils eeu = new ExcelUtils();
            HSSFWorkbook workbook = new HSSFWorkbook();
            int index = 0;
            for (Language l:languages) {
                List<Detailed> list = detailedService.getAllByLangId(l.getId());
                List<List<Object>> data = new ArrayList<List<Object>>();
                for(int i = 0, length = list.size();i<length;i++){
                    Detailed detailed = list.get(i);
                    List rowData = new ArrayList();
                    rowData.add(detailed.getFlTitle());
                    rowData.add(detailed.getId());
                    rowData.add(detailed.getTitle());
                    rowData.add(detailed.getContentTxt());
                    data.add(rowData);
                }
                eeu.exportExcel1(workbook, index++, l.getTitle(), headers, data, out);
            }
            //原理就是将所有的数据一起写入，然后再关闭输入流。
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取全部点击率
     * @param response
     */
    @RequestMapping("/monitor{id}")
    public void test4( @PathVariable("id") long id,HttpServletResponse response){
        List<Language> languages = languageDao.findAll();
        try {
            Date now = new Date( );
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd hh:mm:ss");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("FAQ点击率"+id+ ".xls", "utf-8"));
            OutputStream out = response.getOutputStream();
            String[] headers = { "父级名", "FAQ标题" ,"发布状态","点击率"};
            ExcelUtils eeu = new ExcelUtils();
            HSSFWorkbook workbook = new HSSFWorkbook();
            int index = 0;
          /*  String s = "2019-"+id+"-01 00:00:00";
            String e = "2019-"+(id+1)+"-01 00:00:00";*/
            String s = "2019-07-22 00:00:00";
            String e = "2019-07-23 00:00:00";
            for (Language l:languages) {
                List<Object[]> list = languageDao.getAllObjects(l.getId(),s,e);
                List<List<Object>> data = new ArrayList<List<Object>>();
                for(int i = 0, length = list.size();i<length;i++){

                    Object[] os = list.get(i);
                    List rowData = new ArrayList();
                    rowData.add(os[0]);
                    rowData.add(os[1]);
                    rowData.add(os[2]);
                    rowData.add(os[3]);
                    data.add(rowData);
                }
                eeu.exportExcel1(workbook, index++, l.getTitle(), headers, data, out);
            }
            //原理就是将所有的数据一起写入，然后再关闭输入流。
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取全部反馈数
     * @param response
     */
    @RequestMapping("/feedback{id}")
    public void test5( @PathVariable("id") long id,HttpServletResponse response){
        List<Language> languages = languageDao.findAll();
        try {
            Date now = new Date( );
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd hh:mm:ss");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("FAQ反馈"+id+ ".xls", "utf-8"));
            OutputStream out = response.getOutputStream();
            String[] headers = { "序号", "语言" ,"FAQ标题","FAQ问题内容","Rating","创建时间","评价内容","评价邮件","评价电话"};
            ExcelUtils eeu = new ExcelUtils();
            HSSFWorkbook workbook = new HSSFWorkbook();
            int index = 0;
            String s = "2019-"+id+"-01 00:00:00";
            String e = "2019-"+(id+1)+"-01 00:00:00";
            List<Object[]> list = languageDao.getAllObjects1(s,e);
            List<List<Object>> data = new ArrayList<List<Object>>();
            for(int i = 0, length = list.size();i<length;i++){

                Object[] os = list.get(i);
                List rowData = new ArrayList();
                rowData.add(os[0]);
                rowData.add(os[1]);
                rowData.add(os[2]);
                rowData.add(os[3]);
                rowData.add(os[4]);
                rowData.add(os[5]);
                rowData.add(os[6]);
                rowData.add(os[7]);
                rowData.add(os[8]);
                data.add(rowData);
            }
            eeu.exportExcel1(workbook, index++, "sheet1", headers, data, out);


          /*  for (Language l:languages) {
                List<Object[]> list = languageDao.getAllObjects1(l.getId());
                List<List<Object>> data = new ArrayList<List<Object>>();
                for(int i = 0, length = list.size();i<length;i++){

                    Object[] os = list.get(i);
                    List rowData = new ArrayList();
                    rowData.add(os[0]);
                    rowData.add(os[1]);
                    rowData.add(os[2]);
                    rowData.add(os[3]);
                    data.add(rowData);
                }
                eeu.exportExcel1(workbook, index++, l.getTitle(), headers, data, out);
            }*/
            //原理就是将所有的数据一起写入，然后再关闭输入流。
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
