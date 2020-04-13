package com.example.demo.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * e_form_relation 关系表
 */
@Entity
@Table(name="e_form_relation")
public class E_form_relation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "er_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Long id;

    @Column(name = "er_e_id")
    private Long eid;

    @Column(name = "er_trip_type",columnDefinition="long default 0")
    private Long triptype = (long)0;

    @Column(name = "er_outbound_unchanged",columnDefinition="long default 0")
    private Long outboundunchanged = (long)0;

    @Column(name = "er_inbound_unchanged",columnDefinition="long default 0")
    private Long inboundunchanged = (long)0;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Column(name = "er_outbound_one")
    private Date outboundone;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Column(name = "er_outbound_two")
    private Date outboundtwo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Column(name = "er_outbound_three")
    private Date outboundthree;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Column(name = "er_inbound_one")
    private Date inboundone;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Column(name = "er_inbound_two")
    private Date inboundtwo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Column(name = "er_inbound_three")
    private Date inboundthree;

    @Column(name = "er_eleven_style")
    private String elevenstyle;


    public String getElevenstyle() {
        return elevenstyle;
    }

    public void setElevenstyle(String elevenstyle) {
        this.elevenstyle = elevenstyle;
    }

    public Long getOutboundunchanged() {
        return outboundunchanged;
    }

    public void setOutboundunchanged(Long outboundunchanged) {
        this.outboundunchanged = outboundunchanged;
    }

    public Long getInboundunchanged() {
        return inboundunchanged;
    }

    public void setInboundunchanged(Long inboundunchanged) {
        this.inboundunchanged = inboundunchanged;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }

    public Long getTriptype() {
        return triptype;
    }

    public void setTriptype(Long triptype) {
        this.triptype = triptype;
    }

    public Date getOutboundone() {
        return outboundone;
    }

    public void setOutboundone(Date outboundone) {
        this.outboundone = outboundone;
    }

    public Date getOutboundtwo() {
        return outboundtwo;
    }

    public void setOutboundtwo(Date outboundtwo) {
        this.outboundtwo = outboundtwo;
    }

    public Date getOutboundthree() {
        return outboundthree;
    }

    public void setOutboundthree(Date outboundthree) {
        this.outboundthree = outboundthree;
    }

    public Date getInboundone() {
        return inboundone;
    }

    public void setInboundone(Date inboundone) {
        this.inboundone = inboundone;
    }

    public Date getInboundtwo() {
        return inboundtwo;
    }

    public void setInboundtwo(Date inboundtwo) {
        this.inboundtwo = inboundtwo;
    }

    public Date getInboundthree() {
        return inboundthree;
    }

    public void setInboundthree(Date inboundthree) {
        this.inboundthree = inboundthree;
    }
}
