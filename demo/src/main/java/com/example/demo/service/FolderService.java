package com.example.demo.service;

import com.example.demo.bean.*;
import com.example.demo.dao.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("folderService")
public class FolderService {

    @Resource
    FolderDao folderDao;
    @Resource
    Folder_tagsDao folder_tagsDao;
    @Resource
    Folder_tags_relationDao folder_tags_relationDao;
    @Resource
    Folder_display_relationDao folder_display_relationDao;
    @Resource
    Folder_library_relationDao folder_library_relationDao;


    /**
     * 搜索
     */
    public long getMaxlevel(){
        return folder_display_relationDao.getMaxLevel();
    }
    public List<Object[]> getFolderSelect(long level,long langId,List<String> searchs){
        return folder_display_relationDao.getFolderSelect(level,langId,searchs);
    }
    public List<Object[]> getFolderSelectByLevel1(long langId){
        return folder_display_relationDao.getFolderSelectByLevel1(langId);
    }
    public List<Object[]> getSearchFolder(long langId,long key){
        return folder_display_relationDao.getSearchFolder(langId,key);
    }
    public List<Object[]> getAllByFolderTags(long level,long langId){
        return folder_display_relationDao.getAllByFolderTags(level,langId);
    }


    /**
     * folder 页面初始化
     *
     * @return
     */
    public List<Object[]> getFolderPage(long level, long parenId, long langId) {
        return folder_display_relationDao.getFolderPage(level, parenId, langId);
    }
    /**
     * folder library 页面初始化
     * @return
     */
    public List<Object[]> getFolderLibraryPage(long parenId) {
        return folder_library_relationDao.getFolderLibraryPage(parenId);
    }


    /**
     * folder 页面初始化路径
     *
     * @return
     */
    public List<Object[]> getFolderTableofContents(long level, long parenId) {
        List<Object[]> list = new ArrayList<>();
        long level_new = level;
        Object[] objects = new Object[2];
        objects[0] = level_new;
        objects[1] = parenId;
        list.add(objects);
        for (int i = 1; i < level; i++) {
            level_new--;
            objects = new Object[2];
            objects[0] = level_new;
            long id = folder_display_relationDao.getFolderTableofContents(parenId);
            objects[1] = id;
            parenId = id;
            list.add(objects);
        }
        return list;
    }

    /**
     * edit getfolder
     *
     * @return
     */
    public List<Folder> getFolderAllByKey_random(long key_random) {
        List<Folder> folders = folderDao.getFolderAllByKey_random(key_random);
        for (Folder f : folders) {
            f.setTags(folderDao.getTagsAllByFid(f.getId()));
        }
        return folders;
    }


    /**
     * 获取随机数-文件夹key-同个Q
     *
     * @return
     */
    public int getRandom() {
        int i = (int) (Math.random() * 1000000000);
        if (folderDao.countByRandom(i) > 0) {
            return getRandom();
        } else {
            return i;
        }

    }

    @Transactional
    public void saveFolderALL(long level, long parenId, List<Map<String, Object>> mapArr) {
        try {
            // 多语言:同一个文件夹的key
            int key = getRandom();
            Date date = new Date();

            for (Map<String, Object> m : mapArr) {
                long langId = Long.parseLong(m.get("langId").toString());
                String title = m.get("title").toString().trim();
                String str = m.get("tags").toString();
                str = str.substring(1);
                str = str.substring(0, str.length() - 1);
                String[] tags = str.split(",");
                // 添加文件夹
                Folder folder = new Folder();
                folder.setLangId(langId);
                folder.setTitle(title);
                folder.setRandom(key);
                folder.setCreateDate(date);
                folderDao.save(folder);

                long i = 1;
                for (String t : tags) {
                    if (t.trim().equals("")) {
                        continue;
                    }
                    // 添加标签
                    Folder_tags folder_tags = new Folder_tags();
                    folder_tags.setTags(t.trim());
                    folder_tagsDao.save(folder_tags);
                    // 添加文件夹与标签关系
                    Folder_tags_relation folder_tags_relation = new Folder_tags_relation();
                    folder_tags_relation.setFid(folder.getId());
                    folder_tags_relation.setFtid(folder_tags.getId());
                    folder_tags_relation.setOrder(i);
                    folder_tags_relation.setCreateDate(date);
                    folder_tags_relationDao.save(folder_tags_relation);
                    i++;
                }


            }
            // 添加文件夹与文件夹的关系
            Folder_display_relation folder_display_relation = new Folder_display_relation();
            folder_display_relation.setLevel(level);
            folder_display_relation.setPfid(parenId);
            folder_display_relation.setFid((long) key);
            folder_display_relation.setCreateDate(date);
            folder_display_relationDao.save(folder_display_relation);
        } catch (Exception e) {
            System.out.println(e);
        }


    }


    @Modifying
    @Transactional
    public void updateFolderALL(long key_random, long level, long parenId, List<Map<String, Object>> mapArr) {
        try {
            Date date = new Date();
            List<Folder> folders = folderDao.getFolderAllByKey_random(key_random);
            for (Folder f : folders) {
                long[] ftids = folder_tags_relationDao.getTagsAllByFid(f.getId());
                for (long l : ftids) {
                    // 删除标签
                    folder_tagsDao.deleteById(l);
                }
                // 删除文件夹与标签的关系 - 根据文件夹ID
                folder_tags_relationDao.deleteByFid(f.getId());
            }
            long i = 1;
            for (Map<String, Object> m : mapArr) {
                long langId = Long.parseLong(m.get("langId").toString());
                String title = m.get("title").toString().trim();
                String str = m.get("tags").toString();
                str = str.substring(1);
                str = str.substring(0, str.length() - 1);
                String[] tags = str.split(",");
                // 更新
                Folder folder = new Folder();
                for (Folder f : folders) {
                    if (f.getLangId() == langId) {
                        folder.setId(f.getId());
                        continue;
                    }
                }
                folder.setLangId(langId);
                folder.setTitle(title);
                folder.setRandom((int) key_random);
                folder.setCreateDate(date);
                folderDao.save(folder);
                for (String t : tags) {
                    if (t.trim().equals("")) {
                        continue;
                    }
                    // 添加标签
                    Folder_tags folder_tags = new Folder_tags();
                    folder_tags.setTags(t.trim());
                    folder_tagsDao.save(folder_tags);
                    // 添加文件夹与标签关系
                    Folder_tags_relation folder_tags_relation = new Folder_tags_relation();
                    folder_tags_relation.setFid(folder.getId());
                    folder_tags_relation.setFtid(folder_tags.getId());
                    folder_tags_relation.setOrder(i);
                    folder_tags_relation.setCreateDate(date);
                    folder_tags_relationDao.save(folder_tags_relation);
                    i++;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }


    }


    @Modifying
    @Transactional
    public void saveLibraryALL(long parenId, List<Integer> arr){
        // 先删除，再添加
        folder_library_relationDao.deleteByPfid(parenId);
        Date date = new Date();
        Folder_library_relation relation = null;
        for (Integer s:arr) {
            System.out.println(s);
            relation = new Folder_library_relation();
            relation.setFlid((long)s);
            relation.setPfid(parenId);
            relation.setCreateDate(date);
            folder_library_relationDao.save(relation);
        }
    }
}
