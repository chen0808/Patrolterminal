package com.patrol.terminal.bean;

public class GroupLineBean {
    private String hungLocation;
    private String groundLineNumber;
    private String hungTime;
    private String demolitionTime;

    public GroupLineBean(String hungLocation, String groundLineNumber, String hungTime, String demolitionTime) {
        this.hungLocation = hungLocation;
        this.groundLineNumber = groundLineNumber;
        this.hungTime = hungTime;
        this.demolitionTime = demolitionTime;
    }

    public String getHungLocation() {
        return hungLocation;
    }

    public void setHungLocation(String hungLocation) {
        this.hungLocation = hungLocation;
    }

    public String getGroundLineNumber() {
        return groundLineNumber;
    }

    public void setGroundLineNumber(String groundLineNumber) {
        this.groundLineNumber = groundLineNumber;
    }

    public String getHungTime() {
        return hungTime;
    }

    public void setHungTime(String hungTime) {
        this.hungTime = hungTime;
    }

    public String getDemolitionTime() {
        return demolitionTime;
    }

    public void setDemolitionTime(String demolitionTime) {
        this.demolitionTime = demolitionTime;
    }
}
