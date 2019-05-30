package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class SecondTicketBean implements Serializable {
    /*** 自定义字段 ***/

    // PDA工作任务 sz
    List<TicketWork> workList;
    // PDA人员签名 sz
    List<TicketSign> signList;
    // PDA工作人员 sz
    List<TicketUser> userList;
    // PDA安全事项 sz
    List<TicketSafeContent> safeList;
    // 主键id
    private String id;
    // 单位id
    private String unit_id;
    // 单位名称
    private String unit_name;
    // 编号
    private String ticket_number;
    // 工作负责人id
    private String duty_user_id;
    // 工作负责人名称
    private String duty_user_name;
    // 工作班组id
    private String work_dep_id;
    // 工作班组名称
    private String work_dep_name;
    // 线路id
    private String line_id;
    // 检修任务id
    private String task_id;
    // 计划开始时间
    private String begin_time;
    // 计划结束时间
    private String end_time;
    // 有效期延期时间
    private String delay_time;
    // 备注
    private String remarks;
    // 工作票种类（1：悬挂相序牌，2：调整拉线，3：拆除异物，4：拆除鸟巢，5：擦拭热点，6：安装放松垫，7：安装、拆除在线监测设备）
    private String ticket_type;

    public List<TicketSafeContent> getSafeList() {
        return safeList;
    }

    public void setSafeList(List<TicketSafeContent> safeList) {
        this.safeList = safeList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getTicket_number() {
        return ticket_number;
    }

    public void setTicket_number(String ticket_number) {
        this.ticket_number = ticket_number;
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

    public String getWork_dep_id() {
        return work_dep_id;
    }

    public void setWork_dep_id(String work_dep_id) {
        this.work_dep_id = work_dep_id;
    }

    public String getWork_dep_name() {
        return work_dep_name;
    }

    public void setWork_dep_name(String work_dep_name) {
        this.work_dep_name = work_dep_name;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDelay_time() {
        return delay_time;
    }

    public void setDelay_time(String delay_time) {
        this.delay_time = delay_time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTicket_type() {
        return ticket_type;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }

    public List<TicketWork> getWorkList() {
        return workList;
    }

    public void setWorkList(List<TicketWork> workList) {
        this.workList = workList;
    }

    public List<TicketSign> getSignList() {
        return signList;
    }

    public void setSignList(List<TicketSign> signList) {
        this.signList = signList;
    }

    public List<TicketUser> getUserList() {
        return userList;
    }

    public void setUserList(List<TicketUser> userList) {
        this.userList = userList;
    }
}
