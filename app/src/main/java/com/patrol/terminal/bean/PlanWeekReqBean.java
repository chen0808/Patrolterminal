package com.patrol.terminal.bean;

import java.util.List;

public class PlanWeekReqBean {
    private  String year;
    private  String month;
    private  String week;
    private  String day;
    private  String line_id;
    private  String type_id;
    private  String type_val;
    private List<Tower> planWeekTowerList;
    private PlanWeekLineBean planWeekLine;
    private PlanWeekLineBean planDayLine;
    private String  planTime;
    private List<DayPlanReqBean>  weekPlanIds;

    public PlanWeekLineBean getPlanDaykLine() {
        return planDayLine;
    }

    public void setPlanDaykLine(PlanWeekLineBean planDaykLine) {
        this.planDayLine = planDaykLine;
    }

    public PlanWeekLineBean getPlanWeekLine() {
        return planWeekLine;
    }

    public void setPlanWeekLine(PlanWeekLineBean planWeekLine) {
        this.planWeekLine = planWeekLine;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public List<DayPlanReqBean> getWeekPlanIds() {
        return weekPlanIds;
    }

    public void setWeekPlanIds(List<DayPlanReqBean> weekPlanIds) {
        this.weekPlanIds = weekPlanIds;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_val() {
        return type_val;
    }

    public void setType_val(String type_val) {
        this.type_val = type_val;
    }

    public List<Tower> getPlanWeekTowerList() {
        return planWeekTowerList;
    }

    public void setPlanWeekTowerList(List<Tower> planWeekTowerList) {
        this.planWeekTowerList = planWeekTowerList;
    }


}
