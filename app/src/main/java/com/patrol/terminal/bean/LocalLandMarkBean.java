package com.patrol.terminal.bean;

import android.text.TextUtils;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.List;

@Table(database = AppDataBase.class)
public class LocalLandMarkBean extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    private int local_id;

    @Column
    private String user_id;

    @Column
    private String user_name;

    @Column
    private String landmark_sbjd;//上报阶段

    @Column
    private int landmark_jd;//进度

    @Column
    private String landmark_qkms;//情况描述

    @Column
    private String landmark_bz;//备注

    @Column
    private String date;//添加时间

    @Column
    private String year;//添加时间年


    public static List<LocalLandMarkBean> getAllLsit() {
        return SQLite.select().from(LocalLandMarkBean.class).queryList();
    }

    public static Boolean addStatus(String landmark_sbjd) {
        LocalLandMarkBean bean = SQLite.select().from(LocalLandMarkBean.class).where(LocalLandMarkBean_Table.landmark_sbjd.is(landmark_sbjd)).querySingle();
        if (bean == null)
            return false;
        else
            return true;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getLandmark_sbjd() {
        return landmark_sbjd;
    }

    public void setLandmark_sbjd(String landmark_sbjd) {
        this.landmark_sbjd = landmark_sbjd;
    }

    public int getLandmark_jd() {
        return landmark_jd;
    }

    public void setLandmark_jd(int landmark_jd) {
        this.landmark_jd = landmark_jd;
    }

    public String getLandmark_qkms() {
        return landmark_qkms;
    }

    public void setLandmark_qkms(String landmark_qkms) {
        this.landmark_qkms = landmark_qkms;
    }

    public String getLandmark_bz() {
        if (TextUtils.isEmpty(landmark_bz))
            return "无";
        else
            return landmark_bz;
    }

    public void setLandmark_bz(String landmark_bz) {
        this.landmark_bz = landmark_bz;
    }
}
