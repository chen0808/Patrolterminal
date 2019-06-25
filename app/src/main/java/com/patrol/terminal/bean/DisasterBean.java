package com.patrol.terminal.bean;

import java.io.Serializable;

public class DisasterBean implements Serializable {

    /**
     * id : 275FF62A5E784527AFEEB5359D1FB6D2
     * task_trouble_id : 82FDC56FCD654B1CA4FF9337EC4687AB
     * disaster_type : 地裂缝
     * join_reason : 田家台地处沉陷区，受田地当地农民灌溉影响和雨水冲刷，是造成杆塔基础周围大量裂缝、塌陷主要原因。
     * main_reason : 山体沉陷
     * scale : 1000
     * deal_notes : 1、安排人员不定期进行监视运行。
     * 2、目前线路运行安全。
     * final_deal : null
     * plan_time : null
     * done_time : null
     * remarks : null
     * status : null
     * towers : #052-#055
     * tower_number : 4
     * find_time : 2019-01-01
     * line_name : 1112新盐一线
     * line_level : 4
     * dep_name : 和华运维班
     * type_name : 地质灾害
     */

    private String id;
    private String task_trouble_id;
    private String disaster_type;
    private String join_reason;
    private String main_reason;
    private String scale;
    private String deal_notes;
    private String final_deal;
    private String plan_time;
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

    public String getDisaster_type() {
        return disaster_type;
    }

    public void setDisaster_type(String disaster_type) {
        this.disaster_type = disaster_type;
    }

    public String getJoin_reason() {
        return join_reason;
    }

    public void setJoin_reason(String join_reason) {
        this.join_reason = join_reason;
    }

    public String getMain_reason() {
        return main_reason;
    }

    public void setMain_reason(String main_reason) {
        this.main_reason = main_reason;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getDeal_notes() {
        return deal_notes;
    }

    public void setDeal_notes(String deal_notes) {
        this.deal_notes = deal_notes;
    }

    public String getFinal_deal() {
        return final_deal;
    }

    public void setFinal_deal(String final_deal) {
        this.final_deal = final_deal;
    }

    public String getPlan_time() {
        return plan_time;
    }

    public void setPlan_time(String plan_time) {
        this.plan_time = plan_time;
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
