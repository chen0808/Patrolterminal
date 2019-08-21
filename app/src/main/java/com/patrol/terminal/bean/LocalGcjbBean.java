package com.patrol.terminal.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Table(database = AppDataBase.class)
public class LocalGcjbBean extends BaseModel implements Serializable {

    /**
     * id : 660D24FDA2E3493B8ABD80AAE74CA76B
     * files : null
     * tempBriefImgList : [{"id":"F0A9445DC17E40B6B576E54DAD386796","temp_brief_id":"660D24FDA2E3493B8ABD80AAE74CA76B","upload_time":"2019-08-20 11:02:20","filename":"49d53dcc-71ae-461c-95d8-afa981f46255.jpg","file_path":"/upload.folder/"}]
     */


    @PrimaryKey(autoincrement = true)
    private int local_id;
    @Column
    private String user_id;

    @Column
    private String user_name;

    @Column
    private String brief_sign;//标识（1：施工方，2：监理方，3：建设方）

    @Column
    private String temp_project_name;//项目名

    @Column
    private String temp_project_id;//项目ID

    @Column
    private String brief_no;//简报编号

//    @Column
//    private String project_bgnum;//报告编号

    @Column
    private String brief_type;//简报类型  简报类型（0：初级设计，1：方案设计，2：施工期，3：竣工）

    @Column
    private String project_date;//填报时间

    @Column
    private String project_tbr;//填报人

//    @Column
//    private String project_ssxm;//所属项目

    @Column
    private String construction_content;//施工情况

    @Column
    private String management_content;//管理情况

    @Column
    private String question_content;//存在问题

    @Column
    private String plan_content;//进度计划

    @SerializedName("remarks")
    @Column
    private String remark;//备注

    //    @Column
//    private String project_photo;//照片
    private String[] files;

    @SerializedName("id")
    private String id;
    @SerializedName("tempImgList")
    private List<PhotoBean> tempBriefImgList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PhotoBean> getTempBriefImgList() {
        return tempBriefImgList;
    }

    public void setTempBriefImgList(List<PhotoBean> tempBriefImgList) {
        this.tempBriefImgList = tempBriefImgList;
    }

    public static List<LocalGcjbBean> getGcjbLsit(String type) {
        return SQLite.select().from(LocalGcjbBean.class).queryList();
    }

    public static List<LocalGcjbBean> arrayLocalGcjbBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<LocalGcjbBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
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

    public String getBrief_sign() {
        return brief_sign;
    }

    public void setBrief_sign(String brief_sign) {
        this.brief_sign = brief_sign;
    }

    public String getTemp_project_name() {
        return temp_project_name;
    }

    public void setTemp_project_name(String temp_project_name) {
        this.temp_project_name = temp_project_name;
    }

    public String getTemp_project_id() {
        return temp_project_id;
    }

    public void setTemp_project_id(String temp_project_id) {
        this.temp_project_id = temp_project_id;
    }

    public String getBrief_no() {
        return brief_no;
    }

    public void setBrief_no(String brief_no) {
        this.brief_no = brief_no;
    }

    public String getBrief_type() {
        return brief_type;
    }

    public void setBrief_type(String brief_type) {
        this.brief_type = brief_type;
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

    public String getConstruction_content() {
        return construction_content;
    }

    public void setConstruction_content(String construction_content) {
        this.construction_content = construction_content;
    }

    public String getManagement_content() {
        return management_content;
    }

    public void setManagement_content(String management_content) {
        this.management_content = management_content;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public String getPlan_content() {
        return plan_content;
    }

    public void setPlan_content(String plan_content) {
        this.plan_content = plan_content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remarks) {
        this.remark = remarks;
    }

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }


}
