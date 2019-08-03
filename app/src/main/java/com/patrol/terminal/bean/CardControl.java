package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 表名: CARD_CONTROL - 班组作业控制卡表
 *
 * Date：2019-05-30 20:04:55
 */
public class CardControl  implements Serializable {

    // 数据id
    private String id;

    // 检修任务id
    private String task_repair_id;

    // 编制人id
    private String writer_user_id;

    // 编制人姓名
    private String writer_user_name;

    // 审核人id
    private String auditor_user_id;

    // 审核人姓名
    private String auditor_user_name;

    // 批准人id
    private String approver_user_id;

    // 批准人姓名
    private String approver_user_name;

    // 作业名称
    private String content;

    // 作业性质
    private String property;

    // 作业班组名称
    private String dep_name;

    // 作业班组id
    private String dep_id;

    // 工作负责人id
    private String duty_user_id;

    // 工作负责人姓名
    private String duty_user_name;

    // 工作票号
    private String ticket_number;

    // 执行工作开始时间
    private String start_time;

    // 执行工作结束时间
    private String end_time;

    /*** 自定义字段 ***/

    // 作业项目人员关系 sz
    private List<CardControlProject> projectList;

    // 安全控制措施负责人 sz
    private List<CardControlSafe> safeList;

    // 签名文件 sz
    private List<CardControlSign> signList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask_repair_id() {
        return task_repair_id;
    }

    public void setTask_repair_id(String task_repair_id) {
        this.task_repair_id = task_repair_id;
    }

    public String getWriter_user_id() {
        return writer_user_id;
    }

    public void setWriter_user_id(String writer_user_id) {
        this.writer_user_id = writer_user_id;
    }

    public String getWriter_user_name() {
        return writer_user_name;
    }

    public void setWriter_user_name(String writer_user_name) {
        this.writer_user_name = writer_user_name;
    }

    public String getAuditor_user_id() {
        return auditor_user_id;
    }

    public void setAuditor_user_id(String auditor_user_id) {
        this.auditor_user_id = auditor_user_id;
    }

    public String getAuditor_user_name() {
        return auditor_user_name;
    }

    public void setAuditor_user_name(String auditor_user_name) {
        this.auditor_user_name = auditor_user_name;
    }

    public String getApprover_user_id() {
        return approver_user_id;
    }

    public void setApprover_user_id(String approver_user_id) {
        this.approver_user_id = approver_user_id;
    }

    public String getApprover_user_name() {
        return approver_user_name;
    }

    public void setApprover_user_name(String approver_user_name) {
        this.approver_user_name = approver_user_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getDuty_user_id() {
        return duty_user_id;
    }

    public void setDuty_user_id(String duty_user_id) {
        this.duty_user_id = duty_user_id;
    }

    public String getDuty_user_name() {
        return duty_user_name;
    }

    public void setDuty_user_name(String duty_user_name) {
        this.duty_user_name = duty_user_name;
    }

    public String getTicket_number() {
        return ticket_number;
    }

    public void setTicket_number(String ticket_number) {
        this.ticket_number = ticket_number;
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

    public List<CardControlProject> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<CardControlProject> projectList) {
        this.projectList = projectList;
    }

    public List<CardControlSafe> getSafeList() {
        return safeList;
    }

    public void setSafeList(List<CardControlSafe> safeList) {
        this.safeList = safeList;
    }

    public List<CardControlSign> getSignList() {
        return signList;
    }

    public void setSignList(List<CardControlSign> signList) {
        this.signList = signList;
    }
}