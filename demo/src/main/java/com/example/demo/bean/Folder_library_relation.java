package com.example.demo.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 文件夹与faq父级关系
 */
@Entity
@DynamicInsert
@Table(name="folder_library_relation")
public class Folder_library_relation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "flr_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Long id;

    @Column(name = "flr_fl_id")
    private Long flid;    // library-ID

    @Column(name = "flr_parenid")
    private Long pfid;    // 文件夹ID-f_key_random

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "flr_createdate")
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPfid() {
        return pfid;
    }

    public void setPfid(Long pfid) {
        this.pfid = pfid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getFlid() {
        return flid;
    }

    public void setFlid(Long flid) {
        this.flid = flid;
    }
}
