package com.example.demo.service;

import com.example.demo.bean.*;
import com.example.demo.dao.*;
import com.example.demo.entity.DetailedEntity;
import com.example.demo.mapper.DetailedMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service("detailedService")
public class DetailedService {
    @Resource
    DetailedDao detailedDao;
    @Resource
    HotspotDao hotspotDao;
    @Resource
    DetailedTagsDao detailedTagsDao;
    @Resource
    DtagsRelationDao dtagsRelationDao;
    @Resource
    DetailedEntityDao detailedEntityDao;
    @Resource
    DetailedFeedbackDao feedbackDao;


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

    public  String [] getTags(long dlId){
        return detailedTagsDao.getAllByDlId(dlId);
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


    // 添加搜索数量
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

    /**
     * 按标签查询 Detailed 自定义
     * @param srr
     * @return
     */
    public List<DetailedEntity> getSearchTags(List<String> srr){
        return detailedEntityDao.getSearchTags(srr);
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
        if(feedbacks.size() >= 5 ){
            return;
        }
        String conten = feedback.getContent();
        if(feedback.getId() != 0){
            feedback = feedbackDao.getById(feedback.getId());
        }
        feedback.setContent(conten);
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

}
