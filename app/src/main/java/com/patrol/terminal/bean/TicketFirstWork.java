package com.patrol.terminal.bean;

import java.io.Serializable;

public class TicketFirstWork implements Serializable {
    // 主键id
    private String id;

    // 第一种工作票id
    private String ticket_id;

    // 工作范围
    private String work_range;

    // 工作内容
    private String work_content;

    public TicketFirstWork(String work_range, String work_content) {
        this.work_range = work_range;
        this.work_content = work_content;
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

    public String getWork_range() {
        return work_range;
    }

    public void setWork_range(String work_range) {
        this.work_range = work_range;
    }

    public String getWork_content() {
        return work_content;
    }

    public void setWork_content(String work_content) {
        this.work_content = work_content;
    }
}
