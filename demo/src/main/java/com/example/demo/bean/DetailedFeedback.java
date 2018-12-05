package com.example.demo.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 反馈信息表
 */
@Entity
@Table(name="faqs_detailed_feedback")
public class DetailedFeedback implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "df_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Long id;

    @Column(name = "df_type")
    private Long type;  // 类别(1支持,2反对)

    @Column(name = "df_dl_id")
    private Long dlId;

    @Column(name = "df_ip")
    private String ip;  // ip

    @Column(name = "df_nay_content")
    private String content;  // 反对内容

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "df_createdate")
    private Date createDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getDlId() {
        return dlId;
    }

    public void setDlId(Long dlId) {
        this.dlId = dlId;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}