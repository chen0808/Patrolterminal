package com.patrol.terminal.bean;

import java.io.Serializable;

public class BirdBean implements Serializable {

    /**
     * id : 93733B39FED047C7B2AF363CEA89B515
     * task_trouble_id : D62C086B866B4BC593047E74EB70199D
     * deal_notes : 安装防鸟刺
     * total : null
     * year : 2018年
     * category : 成本
     * batch : 第一批
     * reporting_time : 2017-11-12
     * orders_company : null
     * orders_time : null
     * arrival_time : null
     * installed : 否
     * plna_time : 2018-12-31
     * remakes : null
     * towers : #001-#055
     * tower_number : 55
     * find_time : 2019-01-01
     * line_name : 1112南刘线
     * line_level : 2
     * dep_name : 西固运维班
     * type_name : 防鸟害
     */

    private String id;
    private String task_trouble_id;
    private String deal_notes;
    private Object total;
    private String year;
    private String category;
    private String batch;
    private String reporting_time;
    private Object orders_company;
    private Object orders_time;
    private Object arrival_time;
    private String installed;
    private String plna_time;
    private Object remakes;
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

    public String getDeal_notes() {
        return deal_notes;
    }

    public void setDeal_notes(String deal_notes) {
        this.deal_notes = deal_notes;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getReporting_time() {
        return reporting_time;
    }

    public void setReporting_time(String reporting_time) {
        this.reporting_time = reporting_time;
    }

    public Object getOrders_company() {
        return orders_company;
    }

    public void setOrders_company(Object orders_company) {
        this.orders_company = orders_company;
    }

    public Object getOrders_time() {
        return orders_time;
    }

    public void setOrders_time(Object orders_time) {
        this.orders_time = orders_time;
    }

    public Object getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(Object arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getInstalled() {
        return installed;
    }

    public void setInstalled(String installed) {
        this.installed = installed;
    }

    public String getPlna_time() {
        return plna_time;
    }

    public void setPlna_time(String plna_time) {
        this.plna_time = plna_time;
    }

    public Object getRemakes() {
        return remakes;
    }

    public void setRemakes(Object remakes) {
        this.remakes = remakes;
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
