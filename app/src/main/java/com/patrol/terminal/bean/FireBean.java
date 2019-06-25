package com.patrol.terminal.bean;

import java.io.Serializable;

public class FireBean implements Serializable {

    /**
     * id : C8D9E740C2924CBBAC8784E9F87BB558
     * task_trouble_id : 8267FF790AA84D9095CE51AEE218F02B
     * reason : 塔基内有杂草或垃圾堆积
     * deal_notes : 对基础杂草和小树进行清理
     * done_time : null
     * remarks : 未完成
     * status : null
     * towers : #005-#007
     * tower_number : 3
     * find_time : 2019-01-01
     * line_name : 1116桃南线
     * line_level : 4
     * dep_name : 西固运维班
     * type_name : 防山火
     */

    private String id;
    private String task_trouble_id;
    private String reason;
    private String deal_notes;
    private String done_time;
    private String remarks;
    private String status;
    private String towers;
    private String tower_number;
    private String find_time;
    private String line_name;
    private String line_level;
    private String dep_name;
    private String type_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask_trouble_id() {
        return task_trouble_id;
    }

    public void setTask_trouble_id(String task_trouble_id) {
        this.task_trouble_id = task_trouble_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDeal_notes() {
        return deal_notes;
    }

    public void setDeal_notes(String deal_notes) {
        this.deal_notes = deal_notes;
    }

    public String getDone_time() {
        return done_time;
    }

    public void setDone_time(String done_time) {
        this.done_time = done_time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTowers() {
        return towers;
    }

    public void setTowers(String towers) {
        this.towers = towers;
    }

    public String getTower_number() {
        return tower_number;
    }

    public void setTower_number(String tower_number) {
        this.tower_number = tower_number;
    }

    public String getFind_time() {
        return find_time;
    }

    public void setFind_time(String find_time) {
        this.find_time = find_time;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getLine_level() {
        return line_level;
    }

    public void setLine_level(String line_level) {
        this.line_level = line_level;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
