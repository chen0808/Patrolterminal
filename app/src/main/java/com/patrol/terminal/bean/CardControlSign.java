package com.patrol.terminal.bean;


import java.io.Serializable;

/**
 * 表名: CARD_CONTROL_SIGN - 班组作业控制卡签名表
 *
 * Date：2019-05-30 20:05:05
 */
public class CardControlSign implements Serializable {

    // 数据id
    private String id;

    // 班组作业控制卡id
    private String card_control_id;

    // 文件路径
    private String file_path;

    // 文件名字
    private String filename;

    // 标识（0：负责人；1：工作人员）
    private String sign;

    /*** 自定义字段 ***/

    // PDA工作票签名文件BASE64 sz
    private String file;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCard_control_id() {
        return card_control_id;
    }

    public void setCard_control_id(String card_control_id) {
        this.card_control_id = card_control_id;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}