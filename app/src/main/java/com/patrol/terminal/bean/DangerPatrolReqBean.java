package com.patrol.terminal.bean;

public class DangerPatrolReqBean {
    private String f_id;
    private String id;
    private int patrol_cycle;
    private String type_id;
    private String close_time;

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPatrol_cycle() {
        return patrol_cycle;
    }

    public void setPatrol_cycle(int patrol_cycle) {
        this.patrol_cycle = patrol_cycle;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }
}
