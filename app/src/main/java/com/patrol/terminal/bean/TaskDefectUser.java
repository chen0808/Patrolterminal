package com.patrol.terminal.bean;


/**
 * 表名: TASK_DEFECT_USER - 缺陷人员表
 *
 * Date：2019-07-30 14:26:26
 */
public class TaskDefectUser {

    // 数据id
    private String id;

    // 缺陷id
    private String task_defect_id;

    // 用户id
    private String user_id;

    // 用户名字
    private String user_name;

    // 人员标识（1：负责人，2：组员）
    private String sign;

    /*** 自定义字段 ***/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask_defect_id() {
        return task_defect_id;
    }

    public void setTask_defect_id(String task_defect_id) {
        this.task_defect_id = task_defect_id;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}