package com.patrol.terminal.bean;

import java.io.Serializable;

public class TicketFirstUser implements Serializable {
    // 主键id
    private String id;

    // 第一种工作票id
    private String ticket_id;

    // 人员id
    private String user_id;

    // 人员姓名
    private String user_name;

    // 人员状态（1：正常，2：离开，3：加入）
    private String user_status;

    // 人员标识（1：负责人，2：班成员）
    private String sign;

    // 人员变更时间
    private String alter_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
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

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAlter_time() {
        return alter_time;
    }

    public void setAlter_time(String alter_time) {
        this.alter_time = alter_time;
    }
}
