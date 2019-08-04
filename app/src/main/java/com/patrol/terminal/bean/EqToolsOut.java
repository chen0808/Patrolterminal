package com.patrol.terminal.bean;

/**
 * 表名: EQ_TOOLS_OUT - 工器具借出表
 * <p>
 * Date：2019-08-03 15:45:37
 */

public class EqToolsOut {

    // 数据id
    private String id;

    // 工器具id
    private String eq_tools_id;

    // 工器具名字
    private String eq_tools_name;

    // 型号/规格
    private String type;

    // 单位
    private String unit;

    // 借出数量
    private Integer total;

    // 品牌
    private String brand;

    // 用户id
    private String user_id;

    // 用户名字
    private String user_name;

    // 班组id
    private String dep_id;

    // 班组名字
    private String dep_name;

    // 备注
    private String remarks;

    // 借出时间
    private String out_time;

    // 年
    private String year;

    // 月
    private String month;

    // 日
    private String day;
    // 归还时间
    private String back_time;
    // （0：工器具，1：材料）
    private String tool_type;

    // 借出状态（0：借出，1：归还）
    private String out_status;

    private boolean isCheck = false;

    public String getBack_time() {
        return back_time;
    }

    public void setBack_time(String back_time) {
        this.back_time = back_time;
    }

    public String getTool_type() {
        return tool_type;
    }

    public void setTool_type(String tool_type) {
        this.tool_type = tool_type;
    }

    public String getOut_status() {
        return out_status;
    }

    public void setOut_status(String out_status) {
        this.out_status = out_status;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    /*** 自定义字段 ***/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}