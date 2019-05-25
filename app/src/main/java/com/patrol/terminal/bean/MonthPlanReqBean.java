package com.patrol.terminal.bean;

import java.util.List;

public class MonthPlanReqBean {
    private String id;

    /**
     * 年度计划流程ID
     */
    private String f_id;

    /**
     * 年度计划ID
     */
    private String y_id;

    /**
     * 月度计划ID
     */
    private String m_id;

    /**
     * 计划类型ID
     */
    private String type_id;

    /**
     * 类型关联值（1：定巡，2：定检，3：缺陷，4：隐患）
     */
    private String type_val;

    /**
     * 关联数据ID
     */
    private String line_id;

    /**
     * 计划状态（0：未编辑，1：已编辑）
     */
    private String make_status;

    /**
     * 审核状态（0：未审核，1：审核通过，2：审核不通过）
     */
    private String audit_status;

    /**
     * 流程记录
     */
    private String flow_record;

    /*** 自定义字段 ***/

    private String year;
    private String month;
    private String dep_id;
    private String dep_name;
    private String line_name;
    private String type_name;
    private String plan_name;
    private String line_no;

    /*** pda自定义字段 ***/
    List<DangerBean> planTypeList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getY_id() {
        return y_id;
    }

    public void setY_id(String y_id) {
        this.y_id = y_id;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_val() {
        return type_val;
    }

    public void setType_val(String type_val) {
        this.type_val = type_val;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getMake_status() {
        return make_status;
    }

    public void setMake_status(String make_status) {
        this.make_status = make_status;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public String getFlow_record() {
        return flow_record;
    }

    public void setFlow_record(String flow_record) {
        this.flow_record = flow_record;
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

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getLine_no() {
        return line_no;
    }

    public void setLine_no(String line_no) {
        this.line_no = line_no;
    }

    public List<DangerBean> getPlanTypeList() {
        return planTypeList;
    }

    public void setPlanTypeList(List<DangerBean> planTypeList) {
        this.planTypeList = planTypeList;
    }
}
