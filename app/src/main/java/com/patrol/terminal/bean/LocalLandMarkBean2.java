package com.patrol.terminal.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.List;

@Table(database = AppDataBase.class)
public class LocalLandMarkBean2 extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    private int local_id;

    @Column
    private String user_id;

    @Column
    private String user_name;

    private String temp_project_id;
    private String temp_project_name;
    private String status;//上报阶段
    private String plan;//进度
    private String content;//情况描述
    private String remarks;//备注
    private String created_date;//添加时间

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
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

    public String getTemp_project_id() {
        return temp_project_id;
    }

    public void setTemp_project_id(String temp_project_id) {
        this.temp_project_id = temp_project_id;
    }

    public String getTemp_project_name() {
        return temp_project_name;
    }

    public void setTemp_project_name(String temp_project_name) {
        this.temp_project_name = temp_project_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemarks() {
        if (TextUtils.isEmpty(remarks))
            return "无";
        else
            return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }


}
