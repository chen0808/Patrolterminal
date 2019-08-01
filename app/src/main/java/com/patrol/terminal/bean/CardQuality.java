package com.patrol.terminal.bean;


import java.util.List;

/**
 * 表名: CARD_QUALITY - 工序质量控制卡
 *
 * Date：2019-05-30 20:05:13
 */
public class CardQuality {

    // 数据id
    private String id;

    // 班组作业控制卡id
    private String task_repair_id;

    // 班组id
    private String dep_id;

    // 班组名字
    private String dep_name;

    // 责任人id、
    private String duty_user_id;

    // 责任人名字
    private String duty_user_name;

    // 计划开始时间
    private String start_time;

    // 计划结束时间
    private String end_time;

    // 签名路径
    private String file_path;

    // 签名名字
    private String filename;

    // 备注
    private String remark;

    /*** 自定义字段 ***/

    // PDA关键工序标准及要求
    private List<CardQualityStandard> standardList;

    // PDA成员
    private List<CardQualityUser> userList;

    // PDA工作票签名文件BASE64 sz
    private String file;

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

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<CardQualityStandard> getStandardList() {
        return standardList;
    }

    public void setStandardList(List<CardQualityStandard> standardList) {
        this.standardList = standardList;
    }

    public List<CardQualityUser> getUserList() {
        return userList;
    }

    public void setUserList(List<CardQualityUser> userList) {
        this.userList = userList;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}