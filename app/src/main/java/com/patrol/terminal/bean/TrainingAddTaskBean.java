package com.patrol.terminal.bean;

import java.util.List;

public class TrainingAddTaskBean {
    private String name;
    private String start_time;
    private String end_time;
    private double train_days;
    private String train_level;
    private String host_unit;
    private String train_place;
    private String type_id;
    private String teacher;
    private String content;
    private int year;
    private int month;
    private String remark;
    private String plan_train_id;
    private String train_val;
    private List<UserListBean> userList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public double getTrain_days() {
        return train_days;
    }

    public void setTrain_days(double train_days) {
        this.train_days = train_days;
    }

    public String getTrain_level() {
        return train_level;
    }

    public void setTrain_level(String train_level) {
        this.train_level = train_level;
    }

    public String getHost_unit() {
        return host_unit;
    }

    public void setHost_unit(String host_unit) {
        this.host_unit = host_unit;
    }

    public String getTrain_place() {
        return train_place;
    }

    public void setTrain_place(String train_place) {
        this.train_place = train_place;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPlan_train_id() {
        return plan_train_id;
    }

    public void setPlan_train_id(String plan_train_id) {
        this.plan_train_id = plan_train_id;
    }

    public String getTrain_val() {
        return train_val;
    }

    public void setTrain_val(String train_val) {
        this.train_val = train_val;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean {
        private String user_id;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
