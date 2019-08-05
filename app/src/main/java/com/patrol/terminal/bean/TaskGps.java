package com.patrol.terminal.bean;

import java.io.Serializable;

/**
 * 表名: TASK_GPS - 定位表
 * <p>
 * Date：2019-05-25 19:11:29
 */
public class TaskGps implements Serializable {

    // 数据id
    private String id;

    // 打点信息
    private String content;

    // 经度
    private double lon;

    // 纬度
    private double lat;

    // 用户id
    private String user_id;

    // 用户名字
    private String user_name;

    // 班组id
    private String dep_id;

    // 班组名字
    private String dep_name;

    // 定位时间
    private String loc_time;

    // 地理位置
    private String address;

    // 标识（1：定期巡视）
    private String sign;

    // 关联id
    private String data_id;

    // 年份
    private String year;

    // 月份
    private String month;

    // 日
    private String day;

    /*** 自定义字段 ***/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
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

    public String getLoc_time() {
        return loc_time;
    }

    public void setLoc_time(String loc_time) {
        this.loc_time = loc_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}