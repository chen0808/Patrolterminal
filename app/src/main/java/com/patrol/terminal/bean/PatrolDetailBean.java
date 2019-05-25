package com.patrol.terminal.bean;

import java.io.Serializable;

public class PatrolDetailBean implements Serializable {

    /**
     * fileList : []
     * id : f24768a485314addbdd3acf3fba2987e
     * isConfirm : 0
     * name : 攀登杆塔或在杆塔上架设电力线、通信线、广播线，以及安装广播喇叭
     * sort : 2
     * state : 1
     * typeName : 沿线环境
     * typeSort : 1
     */

    private String id;
    private String isConfirm;
    private String name;
    private String sort;
    private String state;
    private String typeName;
    private String typeSort;
    /**
     * detail : 嘻嘻嘻嘻嘻
     * fileList : []
     * inspector : 999
     * tEnd : 杆塔002
     * tStart : 杆塔001
     * time : 2019年04月
     */

    private String detail;
    private String inspector;
    private String tEnd;
    private String tStart;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(String isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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

    public String getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(String typeSort) {
        this.typeSort = typeSort;
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

    public String getTEnd() {
        return tEnd;
    }

    public void setTEnd(String tEnd) {
        this.tEnd = tEnd;
    }

    public String getTStart() {
        return tStart;
    }

    public void setTStart(String tStart) {
        this.tStart = tStart;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
