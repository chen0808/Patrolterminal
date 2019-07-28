package com.patrol.terminal.bean;

import java.util.List;

public class TeamAndTaskBean {

    /**
     * id : 29E60E1C50194B7B91CC538BEF36453C
     * day_id : null
     * name : 2019-07-02祁浩任务小组
     * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
     * dep_name : 西固运维班
     * year : 2019
     * month : 7
     * week : 26
     * day : 3
     * duty_user_id : 593279A75ED34E258FCD4ADE09E177A4
     * duty_user_name : 祁浩
     * done_status : 0
     * done_time : 0
     * users : null
     * lists : []
     * from_user_id : null
     * from_user_name : null
     */

    private String id;
    private String day_id;
    private String name;
    private String dep_id;
    private String dep_name;
    private int year;
    private int month;
    private int week;
    private int day;
    private String duty_user_id;
    private String duty_user_name;
    private String done_status;
    private String done_time;
    private String users;
    private String from_user_id;
    private String from_user_name;
    private List<GroupOfDayBean> lists;
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay_id() {
        return day_id;
    }

    public void setDay_id(String day_id) {
        this.day_id = day_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDuty_user_id() {
        return duty_user_id;
    }

    public void setDuty_user_id(String duty_user_id) {
        this.duty_user_id = duty_user_id;
    }

    public String getDuty_user_name() {
        return duty_user_name;
    }

    public void setDuty_user_name(String duty_user_name) {
        this.duty_user_name = duty_user_name;
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

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getFrom_user_name() {
        return from_user_name;
    }

    public void setFrom_user_name(String from_user_name) {
        this.from_user_name = from_user_name;
    }

    public List<GroupOfDayBean> getLists() {
        return lists;
    }

    public void setLists(List<GroupOfDayBean> lists) {
        this.lists = lists;
    }
}
