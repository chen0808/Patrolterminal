package com.patrol.terminal.bean;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = AppDataBase.class)
public class GraphicProgressBean extends BaseModel {
    @PrimaryKey(autoincrement = true)
    private int local_id;
    @Column
    private String id;
    @Column
    private String  planName;
    @Column
    private String  createName;
    @Column
    private String  createTime;
    @Column
    private String progressContent;
    @Column
    private String  picList;

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProgressContent() {
        return progressContent;
    }

    public void setProgressContent(String progressContent) {
        this.progressContent = progressContent;
    }

    public String getPicList() {
        return picList;
    }

    public void setPicList(String picList) {
        this.picList = picList;
    }
}
