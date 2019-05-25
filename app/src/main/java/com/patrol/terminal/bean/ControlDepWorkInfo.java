package com.patrol.terminal.bean;

public class ControlDepWorkInfo {
    private int divisonNo;
    private String content;
    private String divisonName;
    private String userId;
    private String w_p_id;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDivisonNo() {
        return divisonNo;
    }

    public void setDivisonNo(int divisonNo) {
        this.divisonNo = divisonNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDivisonName() {
        return divisonName;
    }

    public void setDivisonName(String divisonName) {
        this.divisonName = divisonName;
    }

    public String getW_p_id() {
        return w_p_id;
    }

    public void setW_p_id(String w_p_id) {
        this.w_p_id = w_p_id;
    }
}
