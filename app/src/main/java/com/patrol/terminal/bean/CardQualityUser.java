package com.patrol.terminal.bean;


import java.io.Serializable;

/**
 * 表名: CARD_QUALITY_USER - 工序质量控制成员关系表
 *
 * Date：2019-05-30 20:05:19
 */
public class CardQualityUser implements Serializable {

    // 数据id
    private String id;

    // 工序质量卡id
    private String card_quality_id;

    // 成员id
    private String user_id;

    // 成员名字
    private String user_name;

    /*** 自定义字段 ***/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCard_quality_id() {
        return card_quality_id;
    }

    public void setCard_quality_id(String card_quality_id) {
        this.card_quality_id = card_quality_id;
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
}