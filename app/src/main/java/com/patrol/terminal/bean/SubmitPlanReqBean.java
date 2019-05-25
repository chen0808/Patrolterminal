package com.patrol.terminal.bean;

import java.util.List;

public class SubmitPlanReqBean {
    private String year;
    private String month;
    private String week;
    private String audit_status;
    private List<Tower> lines;
    private List<Tower> lineIds;
    private List<Tower> weekIds;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

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

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public List<Tower> getLines() {
        return lines;
    }

    public void setLines(List<Tower> lines) {
        this.lines = lines;
    }

    public List<Tower> getWeekIds() {
        return weekIds;
    }

    public void setWeekIds(List<Tower> weekIds) {
        this.weekIds = weekIds;
    }

    public List<Tower> getLineIds() {
        return lineIds;
    }

    public void setLineIds(List<Tower> lineIds) {
        this.lineIds = lineIds;
    }
}
