package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.bean.*;
import com.example.demo.dao.*;
import com.example.demo.entity.DetailedEntity;
import com.example.demo.entity.EsEntiy;
import com.example.demo.service.DetailedService;
import com.example.demo.service.EsService;
import com.example.demo.service.FolderService;
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
import java.net.URLDecoder;
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
    @Resource
    Faqs_Select_MonitorDao faqs_select_monitorDao;

    @PersistenceContext
    EntityManager entityManager;

    @Resource
    private EsService esService;

    @Resource
    FolderService folderService;

    @Resource
    Folder_MonitorDao folder_monitorDao;







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
    public RestResultModule getSearchTags(HttpServletRequest request,
            @RequestParam(name = "langId",required = true,defaultValue = "0")long langId,
            @RequestParam(name = "status",required = true,defaultValue = "1")long status,
            @RequestParam(name = "search",required = false,defaultValue = "")String search) throws Exception{
        // 添加搜索记录
        Faqs_Select_Monitor monitor = new Faqs_Select_Monitor();
        monitor.setClientip(ipUtil.getIpAddr(request));
        monitor.setCreateDate(new Date());
        monitor.setLangId(langId);
        monitor.setSelect(search);
        monitor.setCrmuid(request.getParameter("crm_uid"));
        faqs_select_monitorDao.save(monitor);

        RestResultModule module = new RestResultModule();
        String [] sarr = search.split(" ");
        List<String> searchs = Arrays.asList(sarr);
        List<DetailedEntity> detaileds = new ArrayList<>();
        String s = String.valueOf(status);
        if(status == 3 ){
            s = "";
        }

        List<Object[]> folderList = null;
        Map<String,Integer> foldermap = new LinkedHashMap<>();

        // 第一步:整句话匹配标签
        detaileds = detailedService.getSearchTags(langId,s,searchs);
        if(detaileds.size() == 0){
            // 第二步: 所有标签是否包含再整句话
            List<Object[]> tagsList = detailedService.getAllTagsBys(langId,s);
            Map<String,Object[]> map_Date = new HashMap<>();
            Map<String,Integer> map = new LinkedHashMap<>();
            for(int i = 0; i < tagsList.size();i++){
                Object[] os = tagsList.get(i);
                if(search.toLowerCase().contains(os[0].toString().toLowerCase())){
                    if(map.containsKey(os[1].toString())){
                        map.put(os[1].toString(),map.get(os[1].toString())+1);
                    }else{
                        map.put(os[1].toString(),1);
                    }
                    map_Date.put(os[1].toString(),os);
                }
            }
            System.out.println(langId+"搜索："+search);
            List<Map.Entry<String,Integer>> list_Data = new ArrayList<>(map.entrySet());
            Collections.sort(list_Data, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            System.out.println("排序后map："+list_Data);
            if(map.size() > 0){
                DetailedEntity detailedEntity = null;
                for (Map.Entry<String,Integer> map_key :list_Data){
                    Object[] os = map_Date.get(map_key.getKey());
                    detailedEntity = new DetailedEntity();
                    detailedEntity.setId(Long.parseLong(os[1].toString()));
                    detailedEntity.setTitle(os[2].toString());
                    detailedEntity.setStatus(Long.parseLong(os[1].toString()));
                    detaileds.add(detailedEntity);
                }
            }

            // 特殊: 匹配标题和内容是否包含
            if(detaileds.size() == 0){
                String newStr = "%"+search.replaceAll(" ","%")+"%";
                System.out.println("搜索："+newStr);
                detaileds = detailedService.getByTitleAndContenttxt(langId,s,newStr);
                System.out.println("存在标题或内容："+detaileds.size());
            }

            // 第三步: 显示文件夹
            if(detaileds.size() == 0){
                long maxLevel = folderService.getMaxlevel();
                Map<String,Object[]> foldermap_Date = new HashMap<>();
                while(maxLevel > 0){
                    folderList = folderService.getAllByFolderTags(maxLevel,langId,s);
                    if(folderList.size() == 0){
                        maxLevel--;
                        continue;
                    }
                    //folderList = folderService.getFolderSelect(maxLevel,langId,searchs);
                    for(Object[] os : folderList){
                        if(search.toLowerCase().contains(os[4].toString().toLowerCase())){
                            if(foldermap.containsKey(os[0].toString())){
                                foldermap.put(os[0].toString(),foldermap.get(os[0].toString())+1);
                            }else{
                                foldermap.put(os[0].toString(),1);
                            }
                        }
                        foldermap_Date.put(os[0].toString(),os);
                    }
                    if(foldermap.size() > 0){
                        break;
                    }
                    if(maxLevel == 1){
                        // 搜索无结果-显示首层文件夹
                        folderList = new ArrayList<>();
                        folderList = folderService.getFolderSelectByLevel1(langId,s);
                        break;
                    }
                    maxLevel--;
                }
                // 有结果-排序
                if(foldermap.size() > 0){
                    List<Map.Entry<String,Integer>> folder_list_Data = new ArrayList<>(foldermap.entrySet());
                    Collections.sort(folder_list_Data, new Comparator<Map.Entry<String, Integer>>() {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                            return o2.getValue().compareTo(o1.getValue());
                        }
                    });
                    System.out.println("foldermap排序前："+foldermap.toString());
                    System.out.println("folder_list_Data排序后："+folder_list_Data);
                    folderList = new ArrayList<>();
                    for (Map.Entry<String,Integer> map_key1 : folder_list_Data) {
                        Object[] os1 = foldermap_Date.get(map_key1.getKey());
                        folderList.add(os1);
                    }
                }

            }

        }

        /* 搜索引擎*/
        /*
        Map<Long, String> map = detaileds.stream().collect(Collectors.toMap(DetailedEntity::getId, DetailedEntity::getTitle));
        Page<EsEntiy> esEntiys = null;
        try {
            if(!"".equals(searchs)){
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
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }*/


        module.putData("detaileds",detaileds);
        module.putData("folderList",folderList);
        boolean searchFeedback = false;
        if(detaileds.size() > 0 || foldermap.size() > 0){
            searchFeedback = true;
        }
        module.putData("searchFeedback",searchFeedback);
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


    /**
     * 文件夹搜索
     * 存在：显示子文件夹
     * 不存在：显示Lirabry
     */
    @ResponseBody
    @RequestMapping("/getSearchFolder")
    public RestResultModule getSearchFolder(HttpServletRequest request,
                                            @RequestParam(name = "langId",required = true,defaultValue = "0")long langId,
                                            @RequestParam(name = "key",required = true,defaultValue = "0")long key,
                                            @RequestParam(name = "status",required = true,defaultValue = "1")long status,
                                            @RequestParam(name = "fid",required = true,defaultValue = "0")long fid) throws Exception{
        RestResultModule module = new RestResultModule();
        // 添加搜索记录
        Folder_monitor monitor = new Folder_monitor();
        monitor.setClientip(ipUtil.getIpAddr(request));
        monitor.setCreateDate(new Date());
        monitor.setLangId(langId);
        monitor.setfId(fid);
        monitor.setfKey(key);
        folder_monitorDao.save(monitor);

        String s = String.valueOf(status);
        if(status == 3 ){
            s = "";
        }

        List<Object[]> folderList = folderService.getSearchFolder(langId,key,s);
        List<DetailedEntity> detaileds = null;
        detaileds = detailedService.getSearchFolderByLibrary(key,langId,s);
        module.putData("folderList",folderList);
        module.putData("detaileds",detaileds);
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
        long dfId = 0;
        if(feedback.getType() == 1){
            dfId =  detailedService.addFeedback(feedback);
        }
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
        if("" == feedback.getContent() && feedback.getType() == 2){
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
        String uid = request.getParameter("crm_uid");
        String url = "/appPage/indexCRM?langId="+langId+"&crm_uid="+uid;;
        if(dlId > 0){
            String id = detailedService.getIndexDetailedNew(dlId,langId);
            if(null != id){
                dlId =Long.parseLong(id);
                url = "/appPage/indexDetailedCRM?dlId="+dlId+"&crm_uid="+uid;
            }
        }
        response.sendRedirect(request.getContextPath()+url);
        return "";
    }

    /**
     * 2020-1-2
     * 添加搜索反馈信息, 按IP记录
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addSelectFeedback",method= RequestMethod.POST)
    public RestResultModule addSelectFeedback(HttpServletRequest request,@RequestBody SelectFeedback feedback) throws Exception{
        RestResultModule module = new RestResultModule();
        feedback.setIp(ipUtil.getIpAddr(request));
        feedback.setCreateDate(new Date());
        detailedService.addSelectFeedback(feedback);
        logger.info(JSON.toJSONString(feedback));
        // 发邮件（给zoho跟单）
        Map<String, Object> valueMap = new HashMap<>();
        String language = languageDao.getByTitle(feedback.getLangId());
        valueMap.put("title", "searchfeedback/"+feedback.getName()+"/"+feedback.getEmail()+"/"+feedback.getNumber()+"/"+language);
        valueMap.put("feedback",feedback);
        valueMap.put("language",language);
        detailedService.sendSimpleMail(valueMap);

        return module;
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
    public String esTest3( @RequestParam(name = "title",required = false,defaultValue = "")String title) throws Exception{
        String html = "";
        try{
            String[] arr = esService.getAnalyzes("es_index2",title);
            for (String s:arr) {
                html += s;
                html += "<br>";
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return html;
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

    /**
     * 2020-5-12
     * emailBot API
     * @param search 内容
     */
    @ResponseBody
    @RequestMapping("/emailBotSearch")
    public RestResultModule emailBotSearch(HttpServletRequest request,
                                          @RequestParam(name = "search",required = false,defaultValue = "")String search) throws Exception{
        System.out.println("emailBotSearch:"+search);
        RestResultModule module = new RestResultModule();
        String [] sarr = search.split(" ");
        List<String> searchs = Arrays.asList(sarr);
        List<DetailedEntity> detaileds = new ArrayList<>();
        String s = String.valueOf(1);
        long langId = 1;

        List<Object[]> folderList = null;
        Map<String,Integer> foldermap = new LinkedHashMap<>();

        // 第一步:整句话匹配标签
        detaileds = detailedService.getSearchTags(langId,s,searchs);
        for (DetailedEntity d:detaileds) {
            d.setContenttxt(detailedDao.getByIdShowContentTxt(d.getId()));
        }
        if(detaileds.size() == 0){
            // 第二步: 所有标签是否包含再整句话
            List<Object[]> tagsList = detailedService.getAllTagsBys(langId,s);
            Map<String,Object[]> map_Date = new HashMap<>();
            Map<String,Integer> map = new LinkedHashMap<>();
            for(int i = 0; i < tagsList.size();i++){
                Object[] os = tagsList.get(i);
                if(search.toLowerCase().contains(os[0].toString().toLowerCase())){
                    if(map.containsKey(os[1].toString())){
                        map.put(os[1].toString(),map.get(os[1].toString())+1);
                    }else{
                        map.put(os[1].toString(),1);
                    }
                    map_Date.put(os[1].toString(),os);
                }
            }
            System.out.println("搜索："+search);
            System.out.println("排序前map："+map.toString());
            List<Map.Entry<String,Integer>> list_Data = new ArrayList<>(map.entrySet());
            Collections.sort(list_Data, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            System.out.println("排序后map："+list_Data);
            if(map.size() > 0){
                DetailedEntity detailedEntity = null;
                for (Map.Entry<String,Integer> map_key :list_Data){
                    Object[] os = map_Date.get(map_key.getKey());
                    detailedEntity = new DetailedEntity();
                    detailedEntity.setId(Long.parseLong(os[1].toString()));
                    detailedEntity.setTitle(os[2].toString());
                    detailedEntity.setStatus(Long.parseLong(os[1].toString()));
                    detailedEntity.setContenttxt(detailedDao.getByIdShowContentTxt(Long.parseLong(os[1].toString())));
                    detaileds.add(detailedEntity);
                }
            }

        }

        // 第三步: 显示文件夹
        if(detaileds.size() == 0){
            long maxLevel = folderService.getMaxlevel();
            Map<String,Object[]> foldermap_Date = new HashMap<>();
            while(maxLevel > 0){
                folderList = folderService.getAllByFolderTags(maxLevel,langId,s);
                if(folderList.size() == 0){
                    maxLevel--;
                    continue;
                }
                //folderList = folderService.getFolderSelect(maxLevel,langId,searchs);
                for(Object[] os : folderList){
                    if(search.toLowerCase().contains(os[4].toString().toLowerCase())){
                        if(foldermap.containsKey(os[0].toString())){
                            foldermap.put(os[0].toString(),foldermap.get(os[0].toString())+1);
                        }else{
                            foldermap.put(os[0].toString(),1);
                        }
                    }
                    foldermap_Date.put(os[0].toString(),os);
                }
                if(foldermap.size() > 0){
                    break;
                }
                if(maxLevel == 1){
                    // 搜索无结果-显示首层文件夹
                    folderList = new ArrayList<>();
                    break;
                }
                maxLevel--;
            }
            // 有结果-排序
            if(foldermap.size() > 0){
                List<Map.Entry<String,Integer>> folder_list_Data = new ArrayList<>(foldermap.entrySet());
                Collections.sort(folder_list_Data, new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return o2.getValue().compareTo(o1.getValue());
                    }
                });
                System.out.println("foldermap排序前："+foldermap.toString());
                System.out.println("folder_list_Data排序后："+folder_list_Data);
                folderList = new ArrayList<>();
                for (Map.Entry<String,Integer> map_key1 : folder_list_Data) {
                    Object[] os1 = foldermap_Date.get(map_key1.getKey());
                    folderList.add(os1);
                }
            }

        }
        module.putData("detaileds",detaileds);
        module.putData("folderList",folderList);
        return module;
    }



}

