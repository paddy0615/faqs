package com.example.demo.bean;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * folder_monitor,IP记录表
 */
@Entity
@Table(name="folder_monitor")
public class Folder_monitor {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "m_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Long id;

    @Column(name = "m_clientip")
    private String clientip;    // IP

    @Column(name = "m_lang_id")
    private Long langId;

    @Column(name = "m_f_id")
    private Long fId;

    @Column(name = "m_f_key")
    private Long fKey;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "m_createdate")
    private Date createDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientip() {
        return clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public Long getfId() {
        return fId;
    }

    public void setfId(Long fId) {
        this.fId = fId;
    }

    public Long getfKey() {
        return fKey;
    }

    public void setfKey(Long fKey) {
        this.fKey = fKey;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
