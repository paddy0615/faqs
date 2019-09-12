package com.example.demo.bean;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 首页E-form点击数
 */
@Entity
@Table(name="e_form_monitor")
public class E_form_Monitor {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "m_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Long id;

    @Column(name = "m_clientip")
    private String clientip;    // IP

    @Column(name = "m_lang_id")
    private Long langId;

    @Column(name = "m_et_id")
    private Long etId;


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "m_createdate")
    private Date createDate;


    @Column(name = "m_crm_uid")
    private String crmuid;    // crmuid


    public String getCrmuid() {
        return crmuid;
    }

    public void setCrmuid(String crmuid) {
        this.crmuid = crmuid;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getEtId() {
        return etId;
    }

    public void setEtId(Long etId) {
        this.etId = etId;
    }
}
