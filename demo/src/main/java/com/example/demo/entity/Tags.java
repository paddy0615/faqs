package com.example.demo.entity;

import com.example.demo.bean.Detailed;

import java.io.Serializable;

public class Tags implements Serializable {
    private static final long serialVersionUID = 1L;

    private Detailed detailed;
    private String [] tagsArr;

    public Detailed getDetailed() {
        return detailed;
    }

    public void setDetailed(Detailed detailed) {
        this.detailed = detailed;
    }

    public String[] getTagsArr() {
        return tagsArr;
    }

    public void setTagsArr(String[] tagsArr) {
        this.tagsArr = tagsArr;
    }
}
