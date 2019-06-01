package com.patrol.terminal.bean;

import java.io.Serializable;

public class TicketSafeContent implements Serializable {

    // 数据id
    private String id;

    // 工作票id
//    private String ticket_id;

    // 工作票安全措施标准表id
    private String ticket_safe_id;

    // 安全措施
    private String ticket_safe_content;

    // 1：第一种工作票，2：第二种工作票，3：带电作业工作票，4：事故应急抢修单
    private String safe_type;

    // 排序号
    private Integer sort;

    private String content;

    private boolean tag;

    private String status;

    public TicketSafeContent(String ticket_safe_content, String status) {
        this.ticket_safe_content = ticket_safe_content;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getTicket_id() {
//        return ticket_id;
//    }

//    public void setTicket_id(String ticket_id) {
//        this.ticket_id = ticket_id;
//    }

    public String getTicket_safe_id() {
        return ticket_safe_id;
    }

    public void setTicket_safe_id(String ticket_safe_id) {
        this.ticket_safe_id = ticket_safe_id;
    }

    public String getTicket_safe_content() {
        return ticket_safe_content;
    }

    public void setTicket_safe_content(String ticket_safe_content) {
        this.ticket_safe_content = ticket_safe_content;
    }

    public String getSafe_type() {
        return safe_type;
    }

    public void setSafe_type(String safe_type) {
        this.safe_type = safe_type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
