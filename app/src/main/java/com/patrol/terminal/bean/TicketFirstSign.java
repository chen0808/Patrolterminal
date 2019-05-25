package com.patrol.terminal.bean;

import java.io.Serializable;

public class TicketFirstSign implements Serializable {
    // 主键id
    private String id;

    // 第一种工作票id
    private String ticket_id;

    // 签名文件路径
    private String file_id;

    // 签名对应位置（1：工作票1-6项确认-签发人1签名，2：工作票1-6项确认-签发人2签名，
    // 3：工作票1-6项确认-负责人签名，4：确认工作-工作班组人员签名，5：工作负责人变动-签发人签名，6：作业人员变动-负责人签名，
    // 7：工作票延期-负责人签名， 8：工作票延期-许可人签名）
    private String sign;

    // 签名时间
    private String sign_time;

    /*** 自定义字段 ***/

    // PDA工作票签名文件BASE64
    private String file;

    private String file_path;

    private String filename;

    public TicketFirstSign(String sign, String sign_time, String file) {
        this.sign = sign;
        this.sign_time = sign_time;
        this.file = file;
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

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_time() {
        return sign_time;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
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
