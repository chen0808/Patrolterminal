package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class FourTicketBean implements Serializable {

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
    // 抢修任务布置人id
    private String task_user_id;
    // 抢修任务布置人名称
    private String task_user_name;
    // 工作班组id
    private String work_dep_id;
    // 工作班组名称
    private String work_dep_name;
    // 检修任务id
    private String task_id;
    // 计划开始时间
    private String begin_time;
    // 计划结束时间
    private String end_time;
    // 抢修任务（抢修地点和抢修内容）
    private String repair_content;
    // 抢修地点保留带电部分或注意事项
    private String care_content;
    // 经现场勘察需要补充的安全措施
    private String extra_content;
    // 抢修完成时间
    private String done_time;
    // 现场设备状况及保留的安全措施
    private String done_content;
    /*** 自定义字段 ***/

    // PDA人员签名 sz
    private List<TicketSign> signList;
    // PDA工作人员 sz
    private List<TicketUser> userList;

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

    public String getTask_user_id() {
        return task_user_id;
    }

    public void setTask_user_id(String task_user_id) {
        this.task_user_id = task_user_id;
    }

    public String getTask_user_name() {
        return task_user_name;
    }

    public void setTask_user_name(String task_user_name) {
        this.task_user_name = task_user_name;
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

    public String getRepair_content() {
        return repair_content;
    }

    public void setRepair_content(String repair_content) {
        this.repair_content = repair_content;
    }

    public String getCare_content() {
        return care_content;
    }

    public void setCare_content(String care_content) {
        this.care_content = care_content;
    }

    public String getExtra_content() {
        return extra_content;
    }

    public void setExtra_content(String extra_content) {
        this.extra_content = extra_content;
    }

    public String getDone_time() {
        return done_time;
    }

    public void setDone_time(String done_time) {
        this.done_time = done_time;
    }

    public String getDone_content() {
        return done_content;
    }

    public void setDone_content(String done_content) {
        this.done_content = done_content;
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