package com.patrol.terminal.bean;

import java.io.Serializable;

public class JxbSignInfo implements Serializable {

    /**
     * id : 4E4F69658A2C4499B4DA689113A0A2FB
     * task_id : 9A9B9FCE7C044E7CA984765EE3DC7F96
     * task_status : 2
     * user_id : 4B01F91D1E10479BA898DE45023CF25B
     * user_name : 刘海生
     * sign : 4
     * year : 2019
     * month : 5
     * week : 5
     * day : 30
     */

    private String id;
    private String task_id;
    private String task_status;
    private String user_id;
    private String user_name;
    private String sign;
    private int year;
    private int month;
    private int week;
    private int day;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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
}
