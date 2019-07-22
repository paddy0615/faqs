package com.example.demo.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * logs
 */
@Entity
@Table(name="faqs_logs")
public class Logs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "l_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Long id;

    @Column(name = "l_user_id")
    private long userid;

    @Column(name = "l_ip")
    private String ip;

    @Column(name = "l_title")
    private String title;

    @Column(name = "l_content")
    private String content;

    @Column(name = "l_content_other")
    private String contentother;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "l_createdate")
    private Date createdate;


    public Logs(){}

    public Logs(long userid, String ip, String title, String content,String content1, Date createdate){
        this.userid = userid;
        this.ip = ip;
        this.title = title;
        this.content = content;
        this.contentother = content1;
        this.createdate = createdate;
    }


    public String getContentother() {
        return contentother;
    }

    public void setContentother(String contentother) {
        this.contentother = contentother;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}
