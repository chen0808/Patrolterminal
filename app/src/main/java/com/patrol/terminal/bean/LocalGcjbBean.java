package com.patrol.terminal.bean;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.List;

@Table(database = AppDataBase.class)
public class LocalGcjbBean extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    private int local_id;

    @Column
    private String user_id;

    @Column
    private String user_name;

    @Column
    private String type;//填写人类型  区分业主方，监理方，施工方

    @Column
    private String project_name;//项目名

    @Column
    private String project_num;//简报编号

    @Column
    private String project_bgnum;//报告编号

    @Column
    private String project_type;//简报类型  初步设计图

    @Column
    private String project_date;//填报时间

    @Column
    private String project_tbr;//填报人

    @Column
    private String project_ssxm;//所属项目

    @Column
    private String project_sgqk;//施工情况

    @Column
    private String project_glqk;//管理情况

    @Column
    private String project_czqt;//存在问题

    @Column
    private String project_jdjh;//进度计划

    @Column
    private String project_bz;//备注

    @Column
    private String project_photo;//照片


    public static List<LocalGcjbBean> getGcjbLsit(String type){
        return SQLite.select().from(LocalGcjbBean.class).where(LocalGcjbBean_Table.type.is(type)).queryList();
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_num() {
        return project_num;
    }

    public void setProject_num(String project_num) {
        this.project_num = project_num;
    }

    public String getProject_bgnum() {
        return project_bgnum;
    }

    public void setProject_bgnum(String project_bgnum) {
        this.project_bgnum = project_bgnum;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getProject_date() {
        return project_date;
    }

    public void setProject_date(String project_date) {
        this.project_date = project_date;
    }

    public String getProject_tbr() {
        return project_tbr;
    }

    public void setProject_tbr(String project_tbr) {
        this.project_tbr = project_tbr;
    }

    public String getProject_ssxm() {
        return project_ssxm;
    }

    public void setProject_ssxm(String project_ssxm) {
        this.project_ssxm = project_ssxm;
    }

    public String getProject_sgqk() {
        return project_sgqk;
    }

    public void setProject_sgqk(String project_sgqk) {
        this.project_sgqk = project_sgqk;
    }

    public String getProject_glqk() {
        return project_glqk;
    }

    public void setProject_glqk(String project_glqk) {
        this.project_glqk = project_glqk;
    }

    public String getProject_czqt() {
        return project_czqt;
    }

    public void setProject_czqt(String project_czqt) {
        this.project_czqt = project_czqt;
    }

    public String getProject_jdjh() {
        return project_jdjh;
    }

    public void setProject_jdjh(String project_jdjh) {
        this.project_jdjh = project_jdjh;
    }

    public String getProject_bz() {
        return project_bz;
    }

    public void setProject_bz(String project_bz) {
        this.project_bz = project_bz;
    }

    public String getProject_photo() {
        return project_photo;
    }

    public void setProject_photo(String project_photo) {
        this.project_photo = project_photo;
    }
}
