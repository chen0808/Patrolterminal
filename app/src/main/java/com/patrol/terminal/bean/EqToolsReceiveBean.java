package com.patrol.terminal.bean;

import java.io.Serializable;

public class EqToolsReceiveBean implements Serializable {

    private String eq_tools_id;
    private String eq_tools_name;
    private String type;
    private String unit;
    private Integer total;
    private String brand;
    private String user_id;
    private String user_name;
    private String dep_id;
    private String dep_name;
    private String remarks;
    private Integer number;
    private String tool_type;

    public String getEq_tools_id() {
        return eq_tools_id;
    }

    public void setEq_tools_id(String eq_tools_id) {
        this.eq_tools_id = eq_tools_id;
    }

    public String getEq_tools_name() {
        return eq_tools_name;
    }

    public void setEq_tools_name(String eq_tools_name) {
        this.eq_tools_name = eq_tools_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTool_type() {
        return tool_type;
    }

    public void setTool_type(String tool_type) {
        this.tool_type = tool_type;
    }
}
