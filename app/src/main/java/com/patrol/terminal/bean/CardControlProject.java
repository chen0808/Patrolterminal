package com.patrol.terminal.bean;


import java.io.Serializable;

/**
 * 表名: CARD_CONTROL_PROJECT - 作业项目人员关系表
 *
 * Date：2019-05-30 20:04:57
 */
public class CardControlProject implements Serializable {
    private int divisonNo;
    // 数据id
    private String id;

    // 班组作业控制卡id
    private String card_control_id;

    // 作业人员id
    private String user_id;

    // 作业人员姓名
    private String user_name;

    // 作业项目配置id
    private String card_project_id;

    // 作业项目配置内容
    private String content;

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

    public String getCard_project_id() {
        return card_project_id;
    }

    public void setCard_project_id(String card_project_id) {
        this.card_project_id = card_project_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}