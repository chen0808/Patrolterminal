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
public class LocalLandMarkBean extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    private int local_id;

    @Column
    private String user_id;

    @Column
    private String user_name;

    private String temp_project_id;
    private String temp_project_name;

    @SerializedName("status")
    @Column
    private String landmark_sbjd;//上报阶段

    @SerializedName("plan")
    @Column
    private String landmark_jd;//进度
    @SerializedName("content")
    @Column
    private String landmark_qkms;//情况描述
    @SerializedName("remarks")
    @Column
    private String landmark_bz;//备注
    @SerializedName("created_date")
    @Column
    private String date;//添加时间

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

    public int getLandmark_sbjd() {
        return Integer.valueOf(landmark_sbjd);
    }

    public void setLandmark_sbjd(String landmark_sbjd) {
        this.landmark_sbjd = landmark_sbjd;
    }

    public int getLandmark_jd() {
        return Integer.valueOf(landmark_jd);
    }

    public void setLandmark_jd(String landmark_jd) {
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
