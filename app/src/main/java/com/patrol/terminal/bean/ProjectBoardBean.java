package com.patrol.terminal.bean;

import java.io.Serializable;

/**
 * 作者：陈飞
 * 时间：2019/08/12 10:29
 */
public class ProjectBoardBean implements Serializable {
    private String project_name;//项目名称
    private double money;//项目资金
    private int date_total;//项目总时间
    private int date_now;//已完成时间

    public ProjectBoardBean(String project_name, double money) {
        this.project_name = project_name;
        this.money = money;
    }

    public ProjectBoardBean(String project_name, int date_total, int date_now) {
        this.project_name = project_name;
        this.date_total = date_total;
        this.date_now = date_now;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public int getDate_total() {
        return date_total;
    }

    public void setDate_total(int date_total) {
        this.date_total = date_total;
    }

    public int getDate_now() {
        return date_now;
    }

    public void setDate_now(int date_now) {
        this.date_now = date_now;
    }
}
