package com.patrol.terminal.bean;

import java.io.Serializable;

/**
 * 作者：陈飞
 * 时间：2019/08/12 10:29
 */
public class ProjectBoardBean implements Serializable {

    /**
     * id : 19F71E32F1E846BDB1942964C8F976F1
     * name : 项目名称1
     * project_no : 45242
     * total_money : 6.526296536E7
     * address : 项目地点123
     * detailed_address : 详细地点fff
     * dep_name : 班组xxx
     * parent_project : 父项目发发发
     * model : 服务模式
     * status : 0
     * type_sign : 工程类型
     * start_time : 2019-01-01
     * end_time : 2019-12-12
     * content : 内容
     * created_user_id : null
     * created_user_name : null
     * files : null
     * tempProjectImgList : null
     */

    private String id;
    private String name;
    private String project_no;
    private double total_money;
    private String address;
    private String detailed_address;
    private String dep_name;
    private String parent_project;
    private String model;
    private String status;
    private String type_sign;
    private String start_time;
    private String end_time;
    private String content;
    private Object created_user_id;
    private Object created_user_name;
    private Object files;
    private Object tempProjectImgList;

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

    public String getProject_no() {
        return project_no;
    }

    public void setProject_no(String project_no) {
        this.project_no = project_no;
    }

    public double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(double total_money) {
        this.total_money = total_money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailed_address() {
        return detailed_address;
    }

    public void setDetailed_address(String detailed_address) {
        this.detailed_address = detailed_address;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getParent_project() {
        return parent_project;
    }

    public void setParent_project(String parent_project) {
        this.parent_project = parent_project;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType_sign() {
        return type_sign;
    }

    public void setType_sign(String type_sign) {
        this.type_sign = type_sign;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getCreated_user_id() {
        return created_user_id;
    }

    public void setCreated_user_id(Object created_user_id) {
        this.created_user_id = created_user_id;
    }

    public Object getCreated_user_name() {
        return created_user_name;
    }

    public void setCreated_user_name(Object created_user_name) {
        this.created_user_name = created_user_name;
    }

    public Object getFiles() {
        return files;
    }

    public void setFiles(Object files) {
        this.files = files;
    }

    public Object getTempProjectImgList() {
        return tempProjectImgList;
    }

    public void setTempProjectImgList(Object tempProjectImgList) {
        this.tempProjectImgList = tempProjectImgList;
    }
}
