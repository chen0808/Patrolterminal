package com.patrol.terminal.bean;

public class HwcwBean {


    /**
     * id : D0B8297E75874CD1A7A6914BDF77E5C6
     * task_id : E1B78B4B36B34E38BBC39F016A8AF783
     * tower_id : 82B6D7D35CA145B49C9E5981EA2147B4
     * up_big : 5.0
     * up_small : 8.0
     * mid_big : 7.0
     * mid_small : 8.0
     * down_big : 8.0
     * down_small : 8.0
     * temperature : 55.0
     * results : 56.0
     * remark : 富贵花
     * work_time : 2019年05月22日 03时25分
     * user_id : 9CF4DCD383474DAC89F0D0C9DCC8071B
     * tower_model : null
     * line_name : 桃南线
     * tower_name : #020
     */

    private String id;
    private String task_id;
    private String tower_id;
    private double up_big;
    private double up_small;
    private double mid_big;
    private double mid_small;
    private double down_big;
    private double down_small;
    private double temperature;
    private double results;
    private String connection_type;

    private String remark;
    private String work_time;
    private String user_id;
    private String line_name;
    private String tower_name;
    private String tower_model;

    public String getConnection_type() {
        return connection_type;
    }

    public void setConnection_type(String connection_type) {
        this.connection_type = connection_type;
    }

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

    public String getTower_id() {
        return tower_id;
    }

    public void setTower_id(String tower_id) {
        this.tower_id = tower_id;
    }

    public double getUp_big() {
        return up_big;
    }

    public void setUp_big(double up_big) {
        this.up_big = up_big;
    }

    public double getUp_small() {
        return up_small;
    }

    public void setUp_small(double up_small) {
        this.up_small = up_small;
    }

    public double getMid_big() {
        return mid_big;
    }

    public void setMid_big(double mid_big) {
        this.mid_big = mid_big;
    }

    public double getMid_small() {
        return mid_small;
    }

    public void setMid_small(double mid_small) {
        this.mid_small = mid_small;
    }

    public double getDown_big() {
        return down_big;
    }

    public void setDown_big(double down_big) {
        this.down_big = down_big;
    }

    public double getDown_small() {
        return down_small;
    }

    public void setDown_small(double down_small) {
        this.down_small = down_small;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getResults() {
        return results;
    }

    public void setResults(double results) {
        this.results = results;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTower_model() {
        return tower_model;
    }

    public void setTower_model(String tower_model) {
        this.tower_model = tower_model;
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
}
