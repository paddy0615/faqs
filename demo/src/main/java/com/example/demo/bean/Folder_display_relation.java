package com.example.demo.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 文件夹显示关系
 */
@Entity
@DynamicInsert
@Table(name="folder_display_relation")
public class Folder_display_relation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "fdr_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Long id;

    @Column(name = "fdr_f_id")
    private Long fid;    // 文件夹ID-f_key_random

    @Column(name = "fdr_parenid")
    private Long pfid;    // 文件夹父级ID

    @Column(name = "fdr_level")
    private Long level;    // 第几层/级

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "fdr_createdate")
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getPfid() {
        return pfid;
    }

    public void setPfid(Long pfid) {
        this.pfid = pfid;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
