package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class ThirdTicketBean implements Serializable {
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
    // 停用重合闸线路id
    private String reload_line_id;
    // 停用重合闸线路名称
    private String reload_line_name;
    // 检修任务id
    private String task_id;
    // 计划开始时间
    private String begin_time;
    // 计划结束时间
    private String end_time;
    // 工作条件（等电位、中间电位或地电位作业，或邻近带电设备名称）
    private String work_condition;
    // 注意事项（安全措施）
    private String care_content;
    // 调控许可人id
    private String permit_user_id;
    // 调控许可人名称
    private String permit_user_name;
    // 专责监护人id
    private String monitor_user_id;
    // 专责监护人名称
    private String monitor_user_name;
    // 补充安全措施
    private String other_care_content;
    // 工作终结汇报调控许可人id
    private String done_user_id;
    // 工作终结汇报调控许可人名称
    private String done_user_name;
    // 备注
    private String remakes;
    /*** 自定义字段 ***/

    // PDA人员签名 sz
    private List<TicketSign> signList;
    // PDA工作人员 sz
    private List<TicketUser> userList;
    // PDA工作任务 sz
    private List<TicketWork> workList;

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

    public String getReload_line_id() {
        return reload_line_id;
    }

    public void setReload_line_id(String reload_line_id) {
        this.reload_line_id = reload_line_id;
    }

    public String getReload_line_name() {
        return reload_line_name;
    }

    public void setReload_line_name(String reload_line_name) {
        this.reload_line_name = reload_line_name;
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

    public String getWork_condition() {
        return work_condition;
    }

    public void setWork_condition(String work_condition) {
        this.work_condition = work_condition;
    }

    public String getCare_content() {
        return care_content;
    }

    public void setCare_content(String care_content) {
        this.care_content = care_content;
    }

    public String getPermit_user_id() {
        return permit_user_id;
    }

    public void setPermit_user_id(String permit_user_id) {
        this.permit_user_id = permit_user_id;
    }

    public String getPermit_user_name() {
        return permit_user_name;
    }

    public void setPermit_user_name(String permit_user_name) {
        this.permit_user_name = permit_user_name;
    }

    public String getMonitor_user_id() {
        return monitor_user_id;
    }

    public void setMonitor_user_id(String monitor_user_id) {
        this.monitor_user_id = monitor_user_id;
    }

    public String getMonitor_user_name() {
        return monitor_user_name;
    }

    public void setMonitor_user_name(String monitor_user_name) {
        this.monitor_user_name = monitor_user_name;
    }

    public String getOther_care_content() {
        return other_care_content;
    }

    public void setOther_care_content(String other_care_content) {
        this.other_care_content = other_care_content;
    }

    public String getDone_user_id() {
        return done_user_id;
    }

    public void setDone_user_id(String done_user_id) {
        this.done_user_id = done_user_id;
    }

    public String getDone_user_name() {
        return done_user_name;
    }

    public void setDone_user_name(String done_user_name) {
        this.done_user_name = done_user_name;
    }

    public String getRemakes() {
        return remakes;
    }

    public void setRemakes(String remakes) {
        this.remakes = remakes;
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

    public List<TicketWork> getWorkList() {
        return workList;
    }

    public void setWorkList(List<TicketWork> workList) {
        this.workList = workList;
    }
}