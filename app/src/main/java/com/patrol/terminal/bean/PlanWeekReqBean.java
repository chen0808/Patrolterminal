package com.patrol.terminal.bean;

import java.util.List;

public class PlanWeekReqBean {
    private  String year;
    private  String month;
    private  String week;
    // 开始时间
    private String begin_time;
    // 结束时间
    private String end_time;
    private  String day;
    private  String line_id;

    private  String type_id;
    private  String type_val;
    private List<Tower> planWeekTowerList;
    private List<Tower> towers;
    private PlanWeekLineBean planWeekLine;
    private PlanWeekLineBean planDayLine;
    private String  planTime;
    private List<DayPlanReqBean>  weekPlanIds;
    private String dep_id;



    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public void setTowers(List<Tower> towers) {
        this.towers = towers;
    }

    public PlanWeekLineBean getPlanDayLine() {
        return planDayLine;
    }

    public void setPlanDayLine(PlanWeekLineBean planDayLine) {
        this.planDayLine = planDayLine;
    }

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


    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }
}
