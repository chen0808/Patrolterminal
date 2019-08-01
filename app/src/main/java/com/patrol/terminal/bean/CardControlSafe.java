package com.patrol.terminal.bean;


/**
 * 表名: CARD_CONTROL_SAFE - 安全控制措施负责人关联表
 *
 * Date：2019-05-30 20:05:02
 */
public class CardControlSafe {
    private int divisonNo;

    // 数据id
    private String id;

    // 班组作业控制卡id
    private String card_control_id;

    // 安全措施id
    private String card_safe_id;

    // 危险点分析
    private String danger;

    // 安全控制措施
    private String content;

    // 负责人id
    private String duty_user_id;

    // 负责人名字
    private String duty_user_name;

    // 排序号
    private Integer sort;

    public int getDivisonNo() {
        return divisonNo;
    }

    public void setDivisonNo(int divisonNo) {
        this.divisonNo = divisonNo;
    }

    /*** 自定义字段 ***/
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

    public String getCard_safe_id() {
        return card_safe_id;
    }

    public void setCard_safe_id(String card_safe_id) {
        this.card_safe_id = card_safe_id;
    }

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}