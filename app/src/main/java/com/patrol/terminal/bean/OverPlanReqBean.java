package com.patrol.terminal.bean;

public class OverPlanReqBean {
    private String id ;
    private String month_audit_status;
    private String week_audit_status;
    private String from_user_id;
    private String line_name;
    private String year;
    private String month;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }


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
