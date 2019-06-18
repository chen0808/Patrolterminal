package com.patrol.terminal.bean;

public class DayOfWeekBean {


    /**
     * id : E4BC8BBE76FD42C1AD93E879558C3BC7
     * week_id : 8FEE244B32894FDAAAFF78DB9E924C3A
     * month_line_id : 1750D21F819F447BB052EB42DD5E7DF9
     * week_line_id : null
     * type_id : C7A9A60BDB1B4FE986014CA7DA24A467
     * type_sign : 1
     * type_name : 定期巡视
     * line_id : 06CD39FC7726400F92C00D4C89C80F1C
     * line_name : 1111桃郑线
     * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
     * dep_name : 西固运维班
     * name : #001-#038
     * tower_id : null
     * towers_id : 5EA2AB0F591442839BD285A9B0C1A941
     * tower_type : 1
     * year : 2019
     * month : 5
     * week : 5
     * allot_status : 0
     * audit_status : 2
     * done_status : 0
     * done_time : null
     */

    private String id;
    private String week_id;
    private String month_line_id;
    private String week_line_id;
    private String week_tower_id;
    private String day;
    private String type_id;
    private String type_sign;
    private String type_name;
    private String line_id;
    private String line_name;
    private String dep_id;
    private String dep_name;
    private String name;
    private String tower_id;
    private String towers_id;
    private String tower_type;
    private int year;
    private int month;
    private int week;
    private String start_time;
    private String end_time;

    private String allot_status;
    private String audit_status;
    private String done_status;
    private String done_time;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeek_tower_id() {
        return week_tower_id;
    }

    public void setWeek_tower_id(String week_tower_id) {
        this.week_tower_id = week_tower_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeek_id() {
        return week_id;
    }

    public void setWeek_id(String week_id) {
        this.week_id = week_id;
    }

    public String getMonth_line_id() {
        return month_line_id;
    }

    public void setMonth_line_id(String month_line_id) {
        this.month_line_id = month_line_id;
    }

    public String getWeek_line_id() {
        return week_line_id;
    }

    public void setWeek_line_id(String week_line_id) {
        this.week_line_id = week_line_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_sign() {
        return type_sign;
    }

    public void setType_sign(String type_sign) {
        this.type_sign = type_sign;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTower_id() {
        return tower_id;
    }

    public void setTower_id(String tower_id) {
        this.tower_id = tower_id;
    }

    public String getTowers_id() {
        return towers_id;
    }

    public void setTowers_id(String towers_id) {
        this.towers_id = towers_id;
    }

    public String getTower_type() {
        return tower_type;
    }

    public void setTower_type(String tower_type) {
        this.tower_type = tower_type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getAllot_status() {
        return allot_status;
    }

    public void setAllot_status(String allot_status) {
        this.allot_status = allot_status;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public String getDone_status() {
        return done_status;
    }

    public void setDone_status(String done_status) {
        this.done_status = done_status;
    }

    public String getDone_time() {
        return done_time;
    }

    public void setDone_time(String done_time) {
        this.done_time = done_time;
    }
}
