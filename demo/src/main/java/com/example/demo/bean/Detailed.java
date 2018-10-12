package com.example.demo.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="faqs_detailed")
public class Detailed implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "dl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Long id;

    @Column(name = "dl_lang_id")
    private Long langId;   //faq语言ID

    @Column(name = "dl_cat_id")
    private Long catId;   //faq类别ID

    @Column(name = "dl_title")
    private String title;  //详情标题

    @Column(name = "dl_content")
    private String content;  //详情内容带html

    @Column(name = "dl_contenttxt")
    private String contentTxt;  //详情内容纯文本,用于搜索

    @Column(name = "dl_createdate")
    private Date createDate;

    @Column(name = "dl_updatedate")
    private Date updateDate;

    @Column(name = "dl_createuser")
    private Long createUser;

    @Column(name = "dl_updateuser")
    private Long updateUser;

    @Column(name = "dl_ordertopdate")
    private Date orderTopDate;

    @Column(name = "dl_status")
    private long status;   //'状态（1发布，0未发布默认）'


    public Detailed(){}
    public Detailed(long id,String title){
        this.id = id;
        this.title = title;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getContentTxt() {
        return contentTxt;
    }

    public void setContentTxt(String contentTxt) {
        this.contentTxt = contentTxt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getOrderTopDate() {
        return orderTopDate;
    }

    public void setOrderTopDate(Date orderTopDate) {
        this.orderTopDate = orderTopDate;
    }
}
