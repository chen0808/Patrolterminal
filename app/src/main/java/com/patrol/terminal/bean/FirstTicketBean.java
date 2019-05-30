package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class FirstTicketBean implements Serializable {

    private String id;
    private String task_id;
    private String unit_name;
    private String duty_user_id;
    private String duty_user_name;
    private String work_dep_id;
    private String work_dep_name;
    private String line_id;
    private String begin_time;
    private String end_time;
    private String relate_device_operate;
    private String switch_id;
    private String gate_id;
    private String fuse_id;
    private String retain_device;
    private String remove_ground_no;
    private String remove_ground_num;
    private String guarder_id;
    private String guarder_name;
    private String guarder_content;
    private String other_content;
    private String ticket_type;
    // PDA工作任务集合
    List<TicketWork> workList;
    // PDA接地线集合
    List<TicketFirstGround> groundList;
    // PDA许可集合
    List<TicketFirstPermit> permitList;
    // PDA终结报告集合
    List<TicketFirstEnd> endList;
    // PDA人员签名集合
    List<TicketSign> signList;
    // PDA作业人员集合
    List<TicketUser> userList;
    // PDA安全事项 sz
    List<TicketSafeContent> safeList;
    private String unit_id;
    private String ticket_number;

    public List<TicketSafeContent> getSafeList() {
        return safeList;
    }

    public void setSafeList(List<TicketSafeContent> safeList) {
        this.safeList = safeList;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
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

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getRelate_device_operate() {
        return relate_device_operate;
    }

    public void setRelate_device_operate(String relate_device_operate) {
        this.relate_device_operate = relate_device_operate;
    }

    public String getSwitch_id() {
        return switch_id;
    }

    public void setSwitch_id(String switch_id) {
        this.switch_id = switch_id;
    }

    public String getGate_id() {
        return gate_id;
    }

    public void setGate_id(String gate_id) {
        this.gate_id = gate_id;
    }

    public String getFuse_id() {
        return fuse_id;
    }

    public void setFuse_id(String fuse_id) {
        this.fuse_id = fuse_id;
    }

    public String getRetain_device() {
        return retain_device;
    }

    public void setRetain_device(String retain_device) {
        this.retain_device = retain_device;
    }

    public String getRemove_ground_no() {
        return remove_ground_no;
    }

    public void setRemove_ground_no(String remove_ground_no) {
        this.remove_ground_no = remove_ground_no;
    }

    public String getRemove_ground_num() {
        return remove_ground_num;
    }

    public void setRemove_ground_num(String remove_ground_num) {
        this.remove_ground_num = remove_ground_num;
    }

    public String getGuarder_id() {
        return guarder_id;
    }

    public void setGuarder_id(String guarder_id) {
        this.guarder_id = guarder_id;
    }

    public String getGuarder_name() {
        return guarder_name;
    }

    public void setGuarder_name(String guarder_name) {
        this.guarder_name = guarder_name;
    }

    public String getGuarder_content() {
        return guarder_content;
    }

    public void setGuarder_content(String guarder_content) {
        this.guarder_content = guarder_content;
    }

    public String getOther_content() {
        return other_content;
    }

    public void setOther_content(String other_content) {
        this.other_content = other_content;
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

    public List<TicketFirstGround> getGroundList() {
        return groundList;
    }

    public void setGroundList(List<TicketFirstGround> groundList) {
        this.groundList = groundList;
    }

    public List<TicketFirstPermit> getPermitList() {
        return permitList;
    }

    public void setPermitList(List<TicketFirstPermit> permitList) {
        this.permitList = permitList;
    }

    public List<TicketFirstEnd> getEndList() {
        return endList;
    }

    public void setEndList(List<TicketFirstEnd> endList) {
        this.endList = endList;
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
