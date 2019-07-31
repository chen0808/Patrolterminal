package com.patrol.terminal.bean;

import java.util.List;

public class MakeDefectPlanBean {
    private String id;
    private String make_status;
    private String from_user_id;
    private String deal_dep_id;
    private String line_name;
    private String tower_name;
    private String deal_notes;
    private String deal_time;
    private String close_time;
    private String deal_dep_name;
    private String line_id;
    private String tower_id;
    private List<TaskDefectUser> taskDefectUserList;


    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getTower_id() {
        return tower_id;
    }

    public void setTower_id(String tower_id) {
        this.tower_id = tower_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMake_status() {
        return make_status;
    }

    public void setMake_status(String make_status) {
        this.make_status = make_status;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getDeal_dep_id() {
        return deal_dep_id;
    }

    public void setDeal_dep_id(String deal_dep_id) {
        this.deal_dep_id = deal_dep_id;
    }

    public String getDeal_dep_name() {
        return deal_dep_name;
    }

    public void setDeal_dep_name(String deal_dep_name) {
        this.deal_dep_name = deal_dep_name;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getTower_name() {
        return tower_name;
    }

    public void setTower_name(String tower_name) {
        this.tower_name = tower_name;
    }

    public String getDeal_notes() {
        return deal_notes;
    }

    public void setDeal_notes(String deal_notes) {
        this.deal_notes = deal_notes;
    }

    public String getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(String deal_time) {
        this.deal_time = deal_time;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public List<TaskDefectUser> getTaskDefectUserList() {
        return taskDefectUserList;
    }

    public void setTaskDefectUserList(List<TaskDefectUser> taskDefectUserList) {
        this.taskDefectUserList = taskDefectUserList;
    }
}
