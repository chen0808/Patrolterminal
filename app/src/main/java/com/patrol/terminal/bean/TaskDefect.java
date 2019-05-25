package com.patrol.terminal.bean;

/**
 * 本类由Code-Builder自动生成
 * 表名: TASK_DEFECT - 缺陷记录
 *
 * Created with Code-Builder.
 * Date：2019-04-28 18:45:09
 */

public class TaskDefect {

    // 数据ID
    private String id;

    // 月计划id
    private String month_id;

    // 周计划id
    private String week_id;

    // 日计划id
    private String day_id;

    // 小组任务id
    private String group_id;

    // 个人任务id
    private String task_id;

    // 缺陷类别id
    private String category_id;

    // 缺陷级别id
    private String grade_id;

    // 缺陷巡视内容id
    private String patrol_id;

    // 缺陷内容
    private String content;

    // 线路ID
    private String line_id;

    // 起始杆段id
    private String start_id;

    // 结束杆段id
    private String end_id;

    // 发现时间
    private String find_time;

    // 处理措施
    private String deal_notes;

    // 完成情况（0：未完成，1：进行中，2：已完成）
    private String status;

    // 消缺班组
    private String deal_unit;

    // 消除时间
    private String deal_time;

    // 审核人
    private String auditor;

    // 审核状态（0：未审核，1：已审核）
    private String audit_status;

    /*** 自定义字段 ***/
    
    // 类别名称
    private String category_name;
    
    // 级别名称
    private String grade_name;
    
    // 线路名称
    private String line_name;
    
    // 巡视标准内容
    private String remarks;
    

    // 起始杆塔名称
    private String start_name;
    
    // 终止杆塔名称
    private String end_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth_id() {
        return month_id;
    }

    public void setMonth_id(String month_id) {
        this.month_id = month_id;
    }

    public String getWeek_id() {
        return week_id;
    }

    public void setWeek_id(String week_id) {
        this.week_id = week_id;
    }

    public String getDay_id() {
        return day_id;
    }

    public void setDay_id(String day_id) {
        this.day_id = day_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(String grade_id) {
        this.grade_id = grade_id;
    }

    public String getPatrol_id() {
        return patrol_id;
    }

    public void setPatrol_id(String patrol_id) {
        this.patrol_id = patrol_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    public String getEnd_id() {
        return end_id;
    }

    public void setEnd_id(String end_id) {
        this.end_id = end_id;
    }

    public String getFind_time() {
        return find_time;
    }

    public void setFind_time(String find_time) {
        this.find_time = find_time;
    }

    public String getDeal_notes() {
        return deal_notes;
    }

    public void setDeal_notes(String deal_notes) {
        this.deal_notes = deal_notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeal_unit() {
        return deal_unit;
    }

    public void setDeal_unit(String deal_unit) {
        this.deal_unit = deal_unit;
    }

    public String getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(String deal_time) {
        this.deal_time = deal_time;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStart_name() {
        return start_name;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name;
    }

    public String getEnd_name() {
        return end_name;
    }

    public void setEnd_name(String end_name) {
        this.end_name = end_name;
    }
}