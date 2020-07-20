package com.example.demo.service;

import com.example.demo.bean.*;
import com.example.demo.dao.*;
import com.example.demo.entity.DetailedEntity;
import com.example.demo.mapper.DetailedMapper;
import com.example.demo.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("detailedService")
public class DetailedService {
    private  static Logger logger = LoggerFactory.getLogger(EformService.class);

    @Resource
    DetailedDao detailedDao;
    @Resource
    HotspotDao hotspotDao;
    @Resource
    DetailedTagsDao detailedTagsDao;
    @Resource
    DetailedNoTagsDao detailedNoTagsDao;
    @Resource
    DtagsRelationDao dtagsRelationDao;
    @Resource
    DetailedEntityDao detailedEntityDao;
    @Resource
    DetailedFeedbackDao feedbackDao;
    @Resource
    IpUtil ipUtil;
    @Resource
    MonitorDao monitorDao;
    @Resource
    CategoryDao categoryDao;
    @Resource
    LibrabryDao librabryDao;
    @Resource
    E_form_typeDao e_form_typeDao;
    @Resource
    DeFormTypeRelationDao deFormTypeRelationDao;
    @Resource
    SelectFeedbackDao selectFeedbackDao;

    @Value("${spring.profiles.active}")
    private String active; //读取配置文件中的参数
    @Value("${spring.mail.username}")
    private String Sender; //读取配置文件中的参数
    @Resource
    private TemplateEngine templateEngine;
    @Resource
    private JavaMailSender mailSender; //自动注入的Bean

    @Resource
    private DetailedMapper detailedMapper;

    public List<Detailed> getByDetaileds(long lang_id,long cat_id){
        return detailedMapper.getByDetaileds(lang_id,cat_id);
    }


    @Transactional
    public void save(Detailed detailed){
        detailedDao.save(detailed);
    }

    @Transactional
    public void saveTags(Long dlId,String [] tags){
        // 删除全部关联
        dtagsRelationDao.deleteAllByDlId(dlId);
        int i = 1;
        for (String t : tags) {
            //System.out.println(t);
            // 添加标签,防止多个,取第一个
            List<DetailedTags> detailedTags = detailedTagsDao.findByTitle(t);
            DetailedTags tag = null;
            if(detailedTags.size() > 0){
                tag = detailedTags.get(0);
            }else{
                tag = new DetailedTags();
                tag.setTitle(t);
                tag.setCreateDate(new Date());
                detailedTagsDao.save(tag);
            }
            // 添加标签关系
            DtagsRelation relation = new DtagsRelation();
            relation.setDlId(dlId);
            relation.setDtId(tag.getId());
            relation.setOrd(Long.valueOf(i));
            relation.setCreateDate(new Date());
            dtagsRelationDao.save(relation);
            i++;
        }
    }

    @Transactional
    public void saveEformType(Long dlId,long [] tags){
        // 删除全部关联
        deFormTypeRelationDao.deleteAllByDlId(dlId);
        for (long t : tags) {
            // 添加标签关系
            DeFormTypeRelation relation = new DeFormTypeRelation();
            relation.setDlId(dlId);
            relation.setEtId(t);
            deFormTypeRelationDao.save(relation);
        }
    }

    public  String [] getTags(long dlId){
        return detailedTagsDao.getAllByDlId(dlId);
    }

    public List<DeFormTypeRelation> getDeFormTypeRelation(long dlId){
        return deFormTypeRelationDao.findAllByDlId(dlId);
    }

    public List<E_form_type> getEformTypeByDlId(long dlId){
        //return e_form_typeDao.getAllByDlIdtest(dlId);
        return e_form_typeDao.getAllByDlId(dlId);
    }

    public boolean countByCatId(long id){
        boolean falg = true;
        int count = detailedDao.countByCatId(id);
        if(count > 0){
            falg = false;
        }
        return falg;
    }

    // delete,catid
    @Modifying
    @Transactional
    public void deleteAllByCatId(long catId){
        List<Detailed> detaileds = detailedDao.findAllByCatId(catId);
        if(detaileds.size() > 0){
            for (Detailed d : detaileds) {
                deleteById(d.getId());
            }
        }
    }

    // delete,id
    @Modifying
    @Transactional
    public void deleteById(long id){
        // 删除搜索数量
        hotspotDao.deleteByDlId(id);
        detailedDao.deleteById(id);
    }

    @Modifying
    @Transactional
    public void deleteByFlId(long flid){
        detailedDao.deleteByFlId(flid);
    }


    /**
     * 添加搜索数量
     * @param id
     * @return
     */
    public boolean addHotspot(long id){
        boolean flag = false;
        try {
            Hotspot hotspot =  null;
            hotspot = hotspotDao.findByDlId(id);
            if(hotspot == null){
                hotspot = new Hotspot();
                hotspot.setSearchCount(1);
                hotspot.setDlId(id);
            }else{
                hotspot.setSearchCount(hotspot.getSearchCount()+1);
            }
            hotspotDao.save(hotspot);
            flag = true;
        }catch (Exception e){
            flag = false;
        }
        return flag;
    }

    @Transactional
    public void saveStatus(long dlId,long status){
        detailedDao.editStatus(dlId,status,new Date());
    }

    @Transactional
    public void saveTop(long dlId){
        //detailedDao.saveTop(dlId,new Date());
    }

    public List<DetailedEntity> getByTitleAndContenttxt(long langId,String status,String srr){
        return detailedEntityDao.getByTitleAndContenttxt(langId,status,srr);
    }

    /**
     * 按标签查询 Detailed 自定义
     * @param srr
     * @return
     */
    public List<DetailedEntity> getSearchTags(long langId,String status,List<String> srr){
        return detailedEntityDao.getSearchTags(langId,status,srr);
    }
    public List<DetailedEntity> getOnChatList(List<String> srr){
        return detailedEntityDao.getOnChatList(srr);
    }

    public List<DetailedEntity> getDetailedInternal(long langId){
        return detailedEntityDao.getDetailedInternal(langId);
    }

    public List<DetailedEntity> getSearchFolderByLibrary(long key,long langId,String status){
        return detailedEntityDao.getSearchFolderByLibrary(key,langId,status);
    }

    public List<Object[]> getAllTagsBys(long langId,String status){
        return detailedEntityDao.getAllTagsBys(langId,status);
    }




    /**
     * 前台-操作搜索不出结果,存值
     * @param srr
     * @return
     */
    public void getNoTagsCount(HttpServletRequest request,long langId,String srr){
        String [] sarr = srr.split(" ");
        List<String> searchs = Arrays.asList(sarr);
        long cut1 = detailedEntityDao.getNoTagsCounts(searchs);
        if(cut1 == 0){ // 不存在
            DetailedNoTags noTags1 = detailedNoTagsDao.findByTitle(srr);
            if(null == noTags1){
                noTags1 = new DetailedNoTags();
                noTags1.setCreateDate(new Date());
                noTags1.setIp(ipUtil.getIpAddr(request));
                noTags1.setCount(1);
                noTags1.setLangId(langId);
                noTags1.setTitle(srr);
            }else{
                noTags1.setCount(noTags1.getCount()+1);
            }
            detailedNoTagsDao.save(noTags1);
            return;
        }
        if(searchs.size() > 1){
            for (String s : searchs) {
                if(("").equals(s)){
                    continue;
                }
                long cut2 = detailedEntityDao.getNoTagsCount(s);
                if(cut2 == 0){
                    DetailedNoTags noTags1 = detailedNoTagsDao.findByTitle(s);
                    if(null == noTags1){
                        noTags1 = new DetailedNoTags();
                        noTags1.setCreateDate(new Date());
                        noTags1.setIp(ipUtil.getIpAddr(request));
                        noTags1.setCount(1);
                        noTags1.setLangId(langId);
                        noTags1.setTitle(s);
                    }else{
                        noTags1.setCount(noTags1.getCount()+1);
                    }
                    detailedNoTagsDao.save(noTags1);
                }
            }
        }
    }


    /**
     * add反馈信息
     */
    public long addFeedback(DetailedFeedback feedback){
        List<DetailedFeedback>  feedbacks = feedbackDao.findAllByTypeAndDlIdAndIp(feedback.getType(),feedback.getDlId(),feedback.getIp());
        if(feedbacks.size() >= 5 ){
            return 0;
        }
        feedback.setCreateDate(new Date());
        feedbackDao.save(feedback);
        return feedback.getId();

    }

    /**
     * update反馈信息
     */
    public void updateFeedback(DetailedFeedback feedback){
        List<DetailedFeedback>  feedbacks = feedbackDao.findAllByTypeAndDlIdAndIp(feedback.getType(),feedback.getDlId(),feedback.getIp());
      /*  if(feedbacks.size() >= 5 ){
            return;
        }*/
        String conten = feedback.getContent();
        String email = feedback.getEmail();
        String number = feedback.getNumber();
        if(feedback.getId() != 0){
            feedback = feedbackDao.getById(feedback.getId());
        }
        feedback.setContent(conten);
        feedback.setEmail(email);
        feedback.setNumber(number);
        feedbackDao.save(feedback);
    }

    /**
     * del反馈信息
     */
    public void delFeedback(DetailedFeedback feedback){
        // 删除
        feedbackDao.deleteById(feedback.getId());
    }

    /**
     * 查询支持反馈信息数量
     */
    public long getFeedbackCnt(long id){
        return feedbackDao.getByTepeCount(id);
    }


    /**
     * 添加IP数
     * @param request
     * @param catId
     * @param dlId
     * @return
     */
    public boolean addip(HttpServletRequest request,long catId,long dlId) throws Exception{
        Monitor monitor = new Monitor();
        String ip = ipUtil.getIpAddr(request);
        monitor.setClientip(ip);
        Date date = new Date();
        monitor.setCreateDate(date);
        if(catId > 0){
            Category category = categoryDao.findById(catId);
            if(null != category){
                monitor.setLangId(category.getLangId());
                monitor.setCatId(category.getId());
                monitorDao.save(monitor);
                return true;
            }
        }else if(dlId > 0){
            Detailed detailed = detailedDao.findById(dlId);
            if(null != detailed){
                // 添加父ID
                Monitor monitor1 = monitorDao.getAllByClientip(ip);
                if(null != monitor1){
                    // 已上一次浏览时间比较 ,一天内
                    //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //if(judgmentDate(df.format(date),monitor1.getCreateDate().toString())){
                        monitor.setDlIdFather(monitor1.getDlId());
                    //}
                }
                String crm_uid = request.getParameter("crm_uid");
               /* if(!IpUtil.checkInternal(request)){
                    crm_uid = "";
                }*/
                monitor.setCrmuid(crm_uid);
                monitor.setLangId(detailed.getLangId());
                monitor.setCatId(detailed.getCatId());
                monitor.setDlId(detailed.getId());
                monitorDao.save(monitor);
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两个时间是否在一天内
     * @param date1
     * @param date2
     * @return
     * @throws Exception
     */
    public static boolean judgmentDate(String date1, String date2) throws Exception {
        boolean falg = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        Date start = sdf.parse(date1);
        Date end = sdf.parse(date2);
        long cha = end.getTime() - start.getTime();
        if(cha<0){
            falg = false;
        }
        double result = cha * 1.0 / (1000 * 60 * 60);
        if(result<=24){
            falg = true;
        }else{
            falg = false;
        }
        return falg;

    }


    /**
     * 2.2
     * 查询全部父级
     * @return
     */
    public List<Librabry> getLibrabrys(){
        return librabryDao.findAll();
    }

    /**
     * 2.2
     * 报表
     * @return
     */
    public List<Detailed> getAllByLangId(long id){
        return detailedDao.getAllByLangId(id);
    }


    /**
     * 2.2
     * 按父级,语言查询所有
     * @return
     */
    public List<DetailedEntity> getSmartGuide(long id,long langId){
        return detailedEntityDao.getSmartGuide(id,langId);
    }


    /**
     * 2.2
     * 按dl_id ,langid -> 查询对应语言的dl_id
     * @return
     */
    public String getIndexDetailedNew(long dlId,long langId){
        return detailedDao.getIndexDetailedNew(dlId,langId);
    }

    /**
     * 模糊搜索-不用语言3
     * @return
     */
    public List<Detailed> getByAllDetaileds(String t){
        return detailedDao.getByAllDetaileds(t);
    }

    public void addSelectFeedback(SelectFeedback feedback){
        selectFeedbackDao.save(feedback);
    }


    /**
     * 2020-05-06
     * 发邮件-zoho
     * @throws Exception
     */
    public void sendSimpleMail(Map<String, Object> valueMap) throws Exception {
        try {
            MimeMessage mimeMessage = null;
            mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // 设置发件人邮箱
            helper.setFrom(Sender);
            if("pro".equals(active)){
                // 设置收件人邮箱- 此邮件已关联zoho.
                helper.setTo("guest.relations@hkexpress.com");
                // 抄送邮件接收人
                helper.setCc(Sender);
            }else{
                // 设置收件人邮箱
                helper.setTo("windy.tam@sonic-teleservices.com");
                // 抄送邮件接收人
                helper.setCc(new String[]{Sender,"sarsi.pablo@sonic-teleservices.com","erica.yu@sonic-teleservices.com","gary.lam@sonic-callcenter.com","cecile.agbing@sonic-teleservices.com","emerson.bautista@sonic-teleservices.com","sisi.yip@sonic-callcenter.com","paddy.pong@sonic-teleservices.com","sam.mok@sonic-teleservices.com"});
                //helper.setCc(new String[]{Sender,"paddy.pong@sonic-teleservices.com"});

            }
            // 设置邮件标题
            helper.setSubject(valueMap.get("title").toString());
            Context context = new Context();
            context.setVariables(valueMap);
            String content = this.templateEngine.process("faqs/feedbackMail.html", context);
            helper.setText(content, true);
            org.springframework.core.io.Resource resource = new ClassPathResource("static/img/faq_top4.png");
            // 图片
            FileSystemResource file = new FileSystemResource(resource.getFile());
            helper.addInline("faq_top4", file);
            // 发送邮件
            mailSender.send(mimeMessage);
            logger.info("成功发送邮件："+valueMap.toString());
        }catch( Exception e ){
            e.printStackTrace();
            logger.error(e.toString()+" ；错误发送邮件："+valueMap.toString());
        }


    }


}
