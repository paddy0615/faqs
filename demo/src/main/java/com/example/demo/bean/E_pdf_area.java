package com.example.demo.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * e_pdf_area pdf地区
 */
@Entity
@Table(name="e_pdf_area")
public class E_pdf_area implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "epa_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Long id;

    @Column(name = "epa_key")
    private String key;

    @Column(name = "epa_title_hk")
    private String hk;

    @Column(name = "epa_title_en")
    private String en;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHk() {
        return hk;
    }

    public void setHk(String hk) {
        this.hk = hk;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }
}
