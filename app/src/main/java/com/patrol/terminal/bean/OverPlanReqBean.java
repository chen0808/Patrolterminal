package com.patrol.terminal.bean;

public class OverPlanReqBean {
    private String id ;
    private String month_audit_status;
    private String week_audit_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth_audit_status() {
        return month_audit_status;
    }

    public void setMonth_audit_status(String month_audit_status) {
        this.month_audit_status = month_audit_status;
    }

    public String getWeek_audit_status() {
        return week_audit_status;
    }

    public void setWeek_audit_status(String week_audit_status) {
        this.week_audit_status = week_audit_status;
    }
}
