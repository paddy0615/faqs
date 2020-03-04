package com.example.demo.bean;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 文件夹-标签
 */
@Entity
@DynamicInsert
@Table(name="folder_tags")
public class Folder_tags implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ft_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Long id;

    @Column(name = "ft_tags")
    private String tags;       //标签

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
