package com.patrol.terminal.bean;

import java.io.Serializable;

public class TicketFirstPermit implements Serializable {
    // 主键id
    private String id;

    // 第一种工作票id
    private String ticket_id;

    // 许可方式
    private String permit_way;

    // 许可人id
    private String permit_user_id;

    // 许可人名称
    private String permit_user_name;

    // 工作负责人签名文件
    private String sign_file_id;

    // 许可时间
    private String permit_time;

    /*** 自定义字段 ***/

    // PDA工作票许可签名文件BASE64
    private String file;

    //文件路径
    private String file_path;

    //文件名
    private String filename;

    public TicketFirstPermit(String permit_way, String permit_user_name, String sign_file_id, String permit_time) {
        this.permit_way = permit_way;
        this.permit_user_name = permit_user_name;
        this.sign_file_id = sign_file_id;
        this.permit_time = permit_time;
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

    public String getPermit_way() {
        return permit_way;
    }

    public void setPermit_way(String permit_way) {
        this.permit_way = permit_way;
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

    public String getSign_file_id() {
        return sign_file_id;
    }

    public void setSign_file_id(String sign_file_id) {
        this.sign_file_id = sign_file_id;
    }

    public String getPermit_time() {
        return permit_time;
    }

    public void setPermit_time(String permit_time) {
        this.permit_time = permit_time;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
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
}
