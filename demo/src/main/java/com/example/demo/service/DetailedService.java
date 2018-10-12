package com.example.demo.service;

import com.example.demo.bean.Detailed;
import com.example.demo.bean.Hotspot;
import com.example.demo.dao.CategoryDao;
import com.example.demo.dao.DetailedDao;
import com.example.demo.dao.HotspotDao;
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
    private DetailedMapper detailedMapper;

    public List<Detailed> getByDetaileds(long lang_id,long cat_id){
        return detailedMapper.getByDetaileds(lang_id,cat_id);
    }


    @Transactional
    public void save(Detailed detailed){
        detailedDao.save(detailed);
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

}
