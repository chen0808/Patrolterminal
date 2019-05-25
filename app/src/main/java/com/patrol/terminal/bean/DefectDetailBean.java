package com.patrol.terminal.bean;

import java.io.Serializable;

public class DefectDetailBean implements Serializable {
    private String id;//主键id
    private String name;//巡视内容名称
    private String taskId;//任务id
    private String state;//状态
    private String typeName;//巡视类型
    private String sort;//排序
    private String typeSort;//类型排序
    private String time;//巡视时间
    private String detail;//备注
    private String inspector;//检测人
    private String liable;//工作负责人
    private String team;//工作班组
    private String isConfirm;//是否确认0：待审核，1：审核通过，2：审核未通过
    private String audtorId;//确认审核员id
    private String tower;//杆塔名称

    public DefectDetailBean(String id, String name, String state, String typeName, String time, String detail, String inspector, String liable, String team, String isConfirm, String audtorId, String tower) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.typeName = typeName;
        this.time = time;
        this.detail = detail;
        this.inspector = inspector;
        this.liable = liable;
        this.team = team;
        this.isConfirm = isConfirm;
        this.audtorId = audtorId;
        this.tower = tower;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(String typeSort) {
        this.typeSort = typeSort;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getLiable() {
        return liable;
    }

    public void setLiable(String liable) {
        this.liable = liable;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(String isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getAudtorId() {
        return audtorId;
    }

    public void setAudtorId(String audtorId) {
        this.audtorId = audtorId;
    }

    public String getTower() {
        return tower;
    }

    public void setTower(String tower) {
        this.tower = tower;
    }
}
