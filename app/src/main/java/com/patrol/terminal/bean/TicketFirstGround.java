package com.patrol.terminal.bean;

import java.io.Serializable;

public class TicketFirstGround implements Serializable {
    // 主键ID
    private String id;

    // 第一种工作票id
    private String ticket_id;

    // 挂设位置
    private String install_site;

    // 地线编号
    private String ground_id;

    // 挂设时间
    private String install_time;

    // 拆除时间
    private String remove_time;

    public TicketFirstGround(String install_site, String ground_id, String install_time, String remove_time) {
        this.install_site = install_site;
        this.ground_id = ground_id;
        this.install_time = install_time;
        this.remove_time = remove_time;
    }

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

    public String getInstall_site() {
        return install_site;
    }

    public void setInstall_site(String install_site) {
        this.install_site = install_site;
    }

    public String getGround_id() {
        return ground_id;
    }

    public void setGround_id(String ground_id) {
        this.ground_id = ground_id;
    }

    public String getInstall_time() {
        return install_time;
    }

    public void setInstall_time(String install_time) {
        this.install_time = install_time;
    }

    public String getRemove_time() {
        return remove_time;
    }

    public void setRemove_time(String remove_time) {
        this.remove_time = remove_time;
    }
}
