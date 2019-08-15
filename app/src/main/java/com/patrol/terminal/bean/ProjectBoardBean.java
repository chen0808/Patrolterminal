package com.patrol.terminal.bean;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * 作者：陈飞
 * 时间：2019/08/12 10:29
 */
@Table(database = AppDataBase.class)
public class ProjectBoardBean extends BaseModel implements Serializable {
    @PrimaryKey(autoincrement = true)
    private int id;//项目id
    @Column
    private String project_name;//项目名称
    @Column
    private double money;//项目资金
    @Column
    private int date_total;//项目总时间
    @Column
    private int date_now;//已完成时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
