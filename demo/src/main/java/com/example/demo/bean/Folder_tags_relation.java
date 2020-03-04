package com.example.demo.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 文件夹和文件夹标签的关系
 */
@Entity
@DynamicInsert
@Table(name="folder_tags_relation")
public class Folder_tags_relation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ftr_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Long id;

    @Column(name = "ftr_f_id")
    private Long fid;    // 文件夹ID

    @Column(name = "ftr_ft_id")
    private Long ftid;    // 文件夹的标签ID

    @Column(name = "ftr_order")
    private Long order;    // 排序

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "ftr_createdate")
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

    public Long getFtid() {
        return ftid;
    }

    public void setFtid(Long ftid) {
        this.ftid = ftid;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
