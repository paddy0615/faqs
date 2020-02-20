package com.example.demo.entity;

import com.example.demo.bean.E_form_relation;
import com.example.demo.bean.Eform;

import java.io.Serializable;

public class EformTotal implements Serializable {
    private static final long serialVersionUID = 1L;

    private Eform eform;

    private String crmuid;

    private E_form_relation relation;

    public E_form_relation getRelation() {
        return relation;
    }

    public void setRelation(E_form_relation relation) {
        this.relation = relation;
    }

    public Eform getEform() {
        return eform;
    }

    public void setEform(Eform eform) {
        this.eform = eform;
    }

    public String getCrmuid() {
        return crmuid;
    }

    public void setCrmuid(String crmuid) {
        this.crmuid = crmuid;
    }
}
