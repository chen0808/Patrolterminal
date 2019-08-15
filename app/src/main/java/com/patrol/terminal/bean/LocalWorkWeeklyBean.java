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
public class LocalWorkWeeklyBean extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    private int local_id;

    @Column
    private String user_id;

    @Column
    private String user_name;

    @Column
    private String work_date;//填报时间

    @Column
    private String work_bzzj;//本周总结

    @Column
    private String work_xzjh;//下周计划

    @Column
    private String work_xtnr;//协调内容

    @Column
    private String work_bz;//备注

    @Column
    private String work_photo;//照片


    public static List<LocalWorkWeeklyBean> getAllLsit(){
        return SQLite.select().from(LocalWorkWeeklyBean.class).queryList();
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

    public String getWork_date() {
        return work_date;
    }

    public void setWork_date(String work_date) {
        this.work_date = work_date;
    }

    public String getWork_bzzj() {
        return work_bzzj;
    }

    public void setWork_bzzj(String work_bzzj) {
        this.work_bzzj = work_bzzj;
    }

    public String getWork_xzjh() {
        return work_xzjh;
    }

    public void setWork_xzjh(String work_xzjh) {
        this.work_xzjh = work_xzjh;
    }

    public String getWork_xtnr() {
        return TextUtils.isEmpty(work_xtnr) ? "无" : work_xtnr;
    }

    public void setWork_xtnr(String work_xtnr) {
        this.work_xtnr = work_xtnr;
    }

    public String getWork_bz() {
        return TextUtils.isEmpty(work_bz) ? "无" : work_bz;
    }

    public void setWork_bz(String work_bz) {
        this.work_bz = work_bz;
    }

    public String getWork_photo() {
        return work_photo;
    }

    public void setWork_photo(String work_photo) {
        this.work_photo = work_photo;
    }
}
