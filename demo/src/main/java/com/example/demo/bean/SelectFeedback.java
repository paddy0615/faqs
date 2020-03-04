package com.example.demo.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 搜索反馈信息表
 */
@Entity
@Table(name="faqs_select_feedback")
public class SelectFeedback implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "df_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Long id;

    @Column(name = "df_lang_id")
    private Long langId;

    @Column(name = "df_ip")
    private String ip;  // ip

    @Column(name = "df_suggest_content")
    private String suggestContent;  // 建议内容

    @Column(name = "df_follow_content")
    private String followContent;  // 跟进内容

    @Column(name = "df_follow",columnDefinition="long default 1")
    private Long follow = (long)1;  // 默认跟进为1

    @Column(name = "df_name")
    private String name;  // name

    @Column(name = "df_email")
    private String email;  // email

    @Column(name = "df_number")
    private String number;  // number

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "df_createdate")
    private Date createDate;

    @Column(name = "df_status",columnDefinition="long default 0")
    private Long status = (long)0;  // 反馈信息状态（0关闭,1打开）


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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSuggestContent() {
        return suggestContent;
    }

    public void setSuggestContent(String suggestContent) {
        this.suggestContent = suggestContent;
    }

    public String getFollowContent() {
        return followContent;
    }

    public void setFollowContent(String followContent) {
        this.followContent = followContent;
    }

    public Long getFollow() {
        return follow;
    }

    public void setFollow(Long follow) {
        this.follow = follow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
