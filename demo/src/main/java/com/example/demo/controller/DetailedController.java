package com.example.demo.controller;

import com.example.demo.bean.*;
import com.example.demo.dao.CategoryDao;
import com.example.demo.dao.DetailedDao;
import com.example.demo.dao.E_form_typeDao;
import com.example.demo.dao.LanguageDao;
import com.example.demo.entity.DetailedEntity;
import com.example.demo.entity.EsEntiy;
import com.example.demo.service.DetailedService;
import com.example.demo.service.EsService;
import com.example.demo.util.IpUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
 * 前台-详情DetailedController
 * paddy 2018/9/17
 * */
@Controller()
@RequestMapping(value = "appJson")
@Component("DetailedController")
public class DetailedController {
    private  static Logger logger = LoggerFactory.getLogger(DetailedController.class);
    @Resource
    CategoryDao categoryDao;
    @Resource
    DetailedDao detailedDao;
    @Resource
    LanguageDao languageDao;
    @Resource
    private DetailedService detailedService;
    @Resource
    IpUtil ipUtil;
    @Resource
    E_form_typeDao e_form_typeDao;

    @PersistenceContext
    EntityManager entityManager;

    @Resource
    private EsService esService;

    @ResponseBody
    @RequestMapping("/getByDetaileds")
    public List<Detailed> getByDetaileds(
            @RequestParam(name = "lang_id",required = false,defaultValue = "0")long lang_id,
            @RequestParam(name = "cat_id",required = false,defaultValue = "0")long cat_id){
        List<Detailed> detaileds = null;
        if(lang_id == 0 || cat_id == 0){
            logger.error("---lang_id或cat_id为0");
        }else{
            detaileds = detailedService.getByDetaileds(lang_id,cat_id);
        }
        return detaileds;
    }

    /* 获取某个类别*/
    @ResponseBody
    @RequestMapping("/getByDetailed")
    public RestResultModule getByDetailed(@RequestParam(name = "dlId", defaultValue = "0", required = true) long dlId){
        RestResultModule module = new RestResultModule();
        if(dlId == 0){
            module.setCode(404);
            module.setMsg("Parameter error");
            return module;
        }
        // 获取语言集合
        List<Language> languages = languageDao.findAll();
        // 获取详情信息
        Detailed detailed = detailedDao.findById(dlId);
        if(null == detailed){
            module.setCode(404);
            module.setMsg("Detailed is null");
            return module;
        }
        List<Category> categories = categoryDao.findAllByLangIdAndStatus(detailed.getLangId(),1);
        List<E_form_type> eFormTypes = detailedService.getEformTypeByDlId(dlId);


        long dfcount = detailedService.getFeedbackCnt(dlId);

        module.setCode(200);
        module.putData("languages",languages);
        module.putData("categories",categories);
        module.putData("detailed",detailed);
        module.putData("langId",detailed.getLangId());
        module.putData("dfcount",dfcount);
        module.putData("eFormTypes",eFormTypes);

        return module;
    }

    /* index搜索，带语言*/
    @ResponseBody
    @RequestMapping("/getSearch")
    public RestResultModule getSearch(
            @RequestParam(name = "langId",required = true,defaultValue = "0")long langId,
            @RequestParam(name = "search",required = false,defaultValue = "")String search){
        RestResultModule module = new RestResultModule();
        List<Detailed> detaileds = null;
        detaileds = detailedDao.getSearch(langId,search);
        module.setCode(200);
        module.putData("detaileds",detaileds);
        return module;
    }

    /**
     * 按标签搜索 (标签数, 权重 , 热点 , 更新时间)
     * @param search 内容
     */
    @ResponseBody
    @RequestMapping("/getSearchTags")
    public RestResultModule getSearchTags(
            @RequestParam(name = "langId",required = true,defaultValue = "0")long langId,
            @RequestParam(name = "status",required = true,defaultValue = "1")long status,
            @RequestParam(name = "search",required = false,defaultValue = "")String search){
        RestResultModule module = new RestResultModule();
        String [] sarr = search.split(" ");
        List<String> searchs = Arrays.asList(sarr);
        List<DetailedEntity> detaileds = new ArrayList<>();
        DetailedEntity detailedEntity = null;
        String s = String.valueOf(status);
        if(status == 3 ){
            s = "";
        }
        detaileds = detailedService.getSearchTags(langId,s,searchs);

        Map<Long, String> map = detaileds.stream().collect(Collectors.toMap(DetailedEntity::getId, DetailedEntity::getTitle));

        // 匹配标题和内容
   /*     detailedEntity = new DetailedEntity();
        detailedEntity.setId((long)9999999);
        detailedEntity.setTitle("----------------------------------------------------------------------------------以下是搜索引擎结果------------------------------------------------------------------------------------");
        detailedEntity.setStatus(1);
        detaileds.add(detailedEntity);*/

        Page<EsEntiy> esEntiys = null;
        try {
            if(!"".equals(searchs)){
                System.out.println("搜索="+search);
                System.out.println("---------");
                esEntiys = esService.querySearch(search);
                for (EsEntiy e:esEntiys) {
                    if(map.containsKey(e.getId())){
                        continue;
                    }
                    if(e.getStatus() == 0){
                        continue;
                    }
                    detailedEntity = new DetailedEntity();
                    detailedEntity.setId(e.getId());
                    detailedEntity.setTitle(e.getTitle());
                    detailedEntity.setStatus(e.getStatus());
                    if(status == e.getStatus()){
                        detaileds.add(detailedEntity);
                    }else if(status == 3){
                        detaileds.add(detailedEntity);
                    }
                    System.out.println(e);

                }
                System.out.println("---------");
            }
        }catch (Exception e){
            System.out.println(e);
        }


        module.putData("detaileds",detaileds);
        return module;
    }

    /**
     * 前台-检查搜索结果是否存在
     * @param search 内容
     */
    @ResponseBody
    @RequestMapping("/checkSearchTags")
    public RestResultModule checkSearchTags(HttpServletRequest request,
            @RequestParam(name = "langId",required = false,defaultValue = "0")long langId,
            @RequestParam(name = "search",required = false,defaultValue = "")String search){
        RestResultModule module = new RestResultModule();
        detailedService.getNoTagsCount(request,langId,search);
        return module;
    }


    /* 首页搜索*/
    @ResponseBody
    @RequestMapping("/getSerDetaileds")
    public RestResultModule getSerDetaileds(@RequestParam(name = "serach",required = false,defaultValue = "")String serach){
        RestResultModule module = new RestResultModule();
        if(!"".equals(serach)){
            List<Detailed> detaileds = null;
            detaileds = detailedDao.findAllByStatusAndTitleContaining(1,serach);
            module.putData("detaileds",detaileds);
        }
        return module;
    }

    /* 搜索页-获取热点数据*/
    @ResponseBody
    @RequestMapping("/getHotspot")
    public RestResultModule getHotspot(@RequestParam(name = "langId",required = true,defaultValue = "0")long langId){
        RestResultModule module = new RestResultModule();
        Pageable pageable = new PageRequest(0,10);
        Page<Detailed> ds = detailedDao.getHpSearchCount1(langId,pageable);
        //List<Detailed> detaileds = null;
        //detaileds = detailedDao.getHpSearchCount();
        module.putData("detaileds",ds.getContent());
        return module;
    }

    /* 搜索页-获取eform*/
    @ResponseBody
    @RequestMapping("/getEform")
    public List<E_form_type> getEform(){
        List<E_form_type> types = null;
        types = e_form_typeDao.getAllByHomeDisplay();
        return types;
    }

    /* 搜索页-获取Internal*/
    @ResponseBody
    @RequestMapping("/getDetailedInternal")
    public List<DetailedEntity> getDetailedInternal(@RequestParam(name = "langId",required = true,defaultValue = "6")long langId){
        List<DetailedEntity> detaileds = null;
        detaileds = detailedService.getDetailedInternal(langId);
        return detaileds;
    }


    /**
     * 详细页- 智能向导
     * @param dlId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSmartGuide")
    public RestResultModule getSmartGuide(@RequestParam(name = "dlId",required = true,defaultValue = "0")long dlId){
        RestResultModule module = new RestResultModule();
        List<DetailedEntity> detaileds = null;
        if(dlId > 0){
            Detailed detailed = detailedDao.findById(dlId);
            detaileds = detailedService.getSmartGuide(dlId,detailed.getLangId());
        }
        module.putData("detaileds",detaileds);
        return module;
    }

    /**
     * 添加反馈信息, 按IP记录
     * @param request
     * @param feedback 反馈对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addFeedback",method= RequestMethod.POST)
    public RestResultModule addFeedback(HttpServletRequest request,@RequestBody DetailedFeedback feedback){
        RestResultModule module = new RestResultModule();
        if(null == feedback && feedback.getDlId() == 0){
            module.setMessage(400,"传参有误!");
            return module;
        }
        feedback.setIp(ipUtil.getIpAddr(request));
        long dfId =  detailedService.addFeedback(feedback);
        module.putData("id",dfId);
        module.putData("type",feedback.getType());
        return module;
    }

    /**
     * 更新反馈信息, 按id
     * @param feedback 反馈对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateFeedback",method= RequestMethod.POST)
    public RestResultModule updateFeedback(HttpServletRequest request,@RequestBody DetailedFeedback feedback){
        RestResultModule module = new RestResultModule();
        if("" == feedback.getContent()){
            module.setMessage(400,"内容为空!");
            return module;
        }
        if(feedback.getId() == 0){
            feedback.setIp(ipUtil.getIpAddr(request));
            feedback.setCreateDate(new Date());
        }
        detailedService.updateFeedback(feedback);
        return module;
    }

    /**
     * 删除反馈信息, 按id
     * @param feedback 反馈对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delFeedback",method= RequestMethod.POST)
    public void delFeedback(@RequestBody DetailedFeedback feedback){
        if(null == feedback && feedback.getId() == 0){
            return;
        }
        detailedService.delFeedback(feedback);
    }

    /**
     * 新:跳转详情页,添加IP数,添加热点数
     * @param dlId 详情ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getIndexDetailed",method= RequestMethod.GET)
    public String indexDetailed(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "dlId",required = true,defaultValue = "0")long dlId)throws Exception {
        if(dlId > 0){
            // 添加IP数
            if(!detailedService.addip(request,0,dlId)){
                logger.error("---------添加IP数错误,dlId="+dlId+",IP="+ipUtil.getIpAddr(request));
            }
            // 添加热点数
            if(!detailedService.addHotspot(dlId)){
                logger.error("---------添加热点数错误,dlId="+dlId+",IP="+ipUtil.getIpAddr(request));
            }
        }
        response.sendRedirect(request.getContextPath()+"/appPage/indexDetailed?dlId="+dlId);
        return "";
    }

    /**
     * 新:CRM-跳转详情页,添加IP数
     * @param dlId 详情ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getIndexDetailedCRM",method= RequestMethod.GET)
    public String indexDetailedCRM(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "dlId",required = true,defaultValue = "0")long dlId)throws Exception {
        String crm_uid = request.getParameter("crm_uid");
        if(dlId > 0){
            // 添加IP数
            if(!detailedService.addip(request,0,dlId)){
                logger.error("---------添加IP数错误,dlId="+dlId+",IP="+ipUtil.getIpAddr(request));
            }
        }
        response.sendRedirect(request.getContextPath()+"/appPage/indexDetailedCRM?dlId="+dlId+"&crm_uid="+crm_uid);
        return "";
    }


    /**
     * 新:详细页面,点击语言;应跳转对应语言详细页
     * @param dlId 详情ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getIndexDetailedNew",method= RequestMethod.GET)
    public String getIndexDetailedNew(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(name = "dlId",required = true,defaultValue = "0")long dlId,
                                      @RequestParam(name = "langId",required = true,defaultValue = "0")long langId)throws Exception {
        String url = "/appPage/index?langId="+langId;
        if(dlId > 0){
            String id = detailedService.getIndexDetailedNew(dlId,langId);
            if(null != id){
                dlId =Long.parseLong(id);
                url = "/appPage/indexDetailed?dlId="+dlId;
            }
        }
        response.sendRedirect(request.getContextPath()+url);
        return "";
    }

    /**
     * 新:详细页面,点击语言;应跳转对应语言详细页--CRM
     * @param dlId 详情ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getIndexDetailedNewCRM",method= RequestMethod.GET)
    public String getIndexDetailedNewCRM(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(name = "dlId",required = true,defaultValue = "0")long dlId,
                                      @RequestParam(name = "langId",required = true,defaultValue = "0")long langId)throws Exception {
        String url = "/appPage/indexCRM?langId="+langId;
        if(dlId > 0){
            String id = detailedService.getIndexDetailedNew(dlId,langId);
            if(null != id){
                dlId =Long.parseLong(id);
                url = "/appPage/indexDetailedCRM?dlId="+dlId;
            }
        }
        response.sendRedirect(request.getContextPath()+url);
        return "";
    }




    /* 自定义修改后台数据,因域名变化,图片位置也变化*/
    @ResponseBody
    @RequestMapping("/xiugaishujuYuming")
    public List<Detailed> addHotspot(){
        List<Detailed> detaileds = detailedDao.findAll();
        for (Detailed d:detaileds) {
            //d.setContent(d.getContent().replace("/ueditor/jsp", "/hkexpress/ueditor/jsp"));
            //detailedDao.save(d);
        }
        return detaileds;
    }

    @ResponseBody
    @RequestMapping("/getByAllDetaileds")
    public List<Detailed> getByAllDetaileds(
            @RequestParam(name = "title",required = false,defaultValue = "")String title){
        List<Detailed> detaileds = null;
        if(!"".equals(title)){
            detaileds = detailedService.getByAllDetaileds(title);
        }
        System.out.println(detaileds);
        return detaileds;
    }


    @ResponseBody
    @RequestMapping(value = "/es/1")
    public List<EsEntiy> esTest1() throws Exception{
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        System.out.println("当前时间为: " + ft.format(dNow));
        List<EsEntiy> list = null;
        try{
           System.out.println("开始ES");
           EsEntiy esEntiy = null;
           List<Detailed> detaileds = detailedDao.findAll();
           System.out.println("原数据大小="+detaileds.size());
           for (Detailed e:detaileds) {
               esEntiy = new EsEntiy();
               esEntiy.setId(e.getId());
               esEntiy.setTitle(e.getTitle());
               esEntiy.setContentTxt(e.getContentTxt());
               esEntiy.setStatus(e.getStatus());
               esService.save(esEntiy);
           }
           System.out.println("结束ES");
        }catch (Exception e){
           System.out.println(e);
        }

        return list;
    }

    @ResponseBody
    @RequestMapping(value = "/es/2")
    public List<EsEntiy> esTest2() throws Exception{
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        System.out.println("当前时间为: " + ft.format(dNow));
        List<EsEntiy> list = null;
        try{
            System.out.println("开始ES");
            list  = esService.findAll();
            for (EsEntiy e:list) {
                System.out.println(e);
            }
            System.out.println("结束ES");
        }catch (Exception e){
            System.out.println(e);
        }
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "/es/3")
    public Page<EsEntiy> esTest3( @RequestParam(name = "title",required = false,defaultValue = "")String title) throws Exception{
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        System.out.println("当前时间为: " + ft.format(dNow));
        Page<EsEntiy> list = null;
        try{
            System.out.println("开始ES");
            list  = esService.querySearch(title);
            for (EsEntiy e:list) {
                System.out.println(e);
            }
            System.out.println("结束ES");
        }catch (Exception e){
            System.out.println(e);
        }
        return list;
    }


    /**
     * 2019-11-06
     * onChat API
     */
    @ResponseBody
    @RequestMapping(value = "/onChat/list")
    public String onChatList( @RequestParam(name = "title",required = false,defaultValue = "")String title) throws Exception {
        String html = "";
        try{
            String [] sarr = title.split(" ");
            List<String> searchs = Arrays.asList(sarr);
            if(title == "" ){
                return "";
            }
            List<DetailedEntity> detaileds = detailedService.getOnChatList(searchs);
            if(detaileds.size() > 0){
                html +="<div><ul style='list-style: disc !important;'>";
                for (DetailedEntity d:detaileds) {
                    html +="<li style='margin-bottom: 10px;line-height: 30px;' onClick='onChatDetailed("+d.getId()+",\""+d.getTitle()+"\")'><a style='text-decoration: underline !important;' href='javascript:;'>"+d.getTitle()+"</a></li>";
                }
                html +="</ul></div>";
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return html;
    }

    /**
     * 2019-11-06
     * onChat API
     * onChatDetailed
     */
    @ResponseBody
    @RequestMapping(value = "/onChat/detailed")
    public String onChatDetailed( @RequestParam(name = "id",required = false,defaultValue = "0")long id) throws Exception {
        String html = "";
        try{
            if(id > 0){
                Detailed detailed = detailedDao.findById(id);
                html +="<div>";
                html += detailed.getContent();
                html +="</div>";
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return html;
    }

    @ResponseBody
    @RequestMapping(value = "/onChat/test")
    public String onChatTest() throws Exception {
        System.out.println("onChattest 开始");
        String html = "";
        try{

            String text="自古刀扇过背刺";
            StringReader sr=new StringReader(text);
          /*  IKSegmenter ik=new IKSegmenter(sr, true);
            Lexeme lex=null;
            while((lex=ik.next())!=null) {
                System.out.print(lex.getLexemeText() + "|");
            }*/

        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("onChattest 结束");
        return html;
    }



}

