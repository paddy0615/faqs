package com.example.demo.entity;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class CommomClass implements Serializable {

    private String irrCategory;
    private String template;
    private String reasons;
    private String flightNo;
    private String newFlightNo;
    private String departingFrom;
    private String arrivingAt;
    private String departureDate;
    private String departureTime;
    private String arrivalTime;
    private String arrivalDate;
    private String newDepartureTime;
    private String newArrivalTime;
    private String createdDate;
    private String newDepartureDate;
    private String newArrivalDate;
    private String protectionOffer;
    private String jobID;

    private int r_id;
    private String reason_EN;
    private String reason_TC;
    private String reason_SC;
    private String reason_JP;
    private String reason_KR;


    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getReason_EN() {
        return reason_EN;
    }

    public void setReason_EN(String reason_EN) {
        this.reason_EN = reason_EN;
    }

    public String getReason_TC() {
        return reason_TC;
    }

    public void setReason_TC(String reason_TC) {
        this.reason_TC = reason_TC;
    }

    public String getReason_SC() {
        return reason_SC;
    }

    public void setReason_SC(String reason_SC) {
        this.reason_SC = reason_SC;
    }

    public String getReason_JP() {
        return reason_JP;
    }

    public void setReason_JP(String reason_JP) {
        this.reason_JP = reason_JP;
    }

    public String getReason_KR() {
        return reason_KR;
    }

    public void setReason_KR(String reason_KR) {
        this.reason_KR = reason_KR;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getNewDepartureDate() {
        return newDepartureDate;
    }

    public void setNewDepartureDate(String newDepartureDate) {
        this.newDepartureDate = newDepartureDate;
    }

    public String getNewArrivalDate() {
        return newArrivalDate;
    }

    public void setNewArrivalDate(String newArrivalDate) {
        this.newArrivalDate = newArrivalDate;
    }

    public String getNewDepartureTime() {
        return newDepartureTime;
    }

    public void setNewDepartureTime(String newDepartureTime) {
        this.newDepartureTime = newDepartureTime;
    }

    public String getNewArrivalTime() {
        return newArrivalTime;
    }

    public void setNewArrivalTime(String newArrivalTime) {
        this.newArrivalTime = newArrivalTime;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getProtectionOffer() {
        return protectionOffer;
    }

    public void setProtectionOffer(String protectionOffer) {
        this.protectionOffer = protectionOffer;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getIrrCategory() {
        return irrCategory;
    }

    public void setIrrCategory(String irrCategory) {
        this.irrCategory = irrCategory;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getNewFlightNo() {
        return newFlightNo;
    }

    public void setNewFlightNo(String newFlightNo) {
        this.newFlightNo = newFlightNo;
    }

    public String getDepartingFrom() {
        return departingFrom;
    }

    public void setDepartingFrom(String departingFrom) {
        this.departingFrom = departingFrom;
    }

    public String getArrivingAt() {
        return arrivingAt;
    }

    public void setArrivingAt(String arrivingAt) {
        this.arrivingAt = arrivingAt;
    }
}
