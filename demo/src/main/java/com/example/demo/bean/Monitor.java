package com.example.demo.bean;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="faqs_monitor")
public class Monitor {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "m_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Long id;

    @Column(name = "m_clientip")
    private String clientip;

    @Column(name = "m_lang_id")
    private Long langId;

    @Column(name = "m_cat_id")
    private Long catId;

    @Column(name = "m_dl_id")
    private Long dlId;

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

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public Long getDlId() {
        return dlId;
    }

    public void setDlId(Long dlId) {
        this.dlId = dlId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
