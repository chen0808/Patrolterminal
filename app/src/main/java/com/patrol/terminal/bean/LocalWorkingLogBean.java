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
public class LocalWorkingLogBean extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    private int local_id;

    @Column
    private String user_id;

    @Column
    private String user_name;

    @Column
    private String type;//填写人类型  区分建设方，监理方，施工方

    @Column
    private String log_num;//项目编号

    @Column
    private String project_id;//项目编号

    @Column
    private String project_name;//项目名

    @Column
    private String working_name;//施工人员

    @Column
    private String working_num;//施工人数

    @Column
    private String report_name;//上报人

    @Column
    private String weather_status;//气候状况

    @Column
    private String compile_date;//编制日期

    @Column
    private String occurrence_date;//发生日期

    @Column
    private String morning_temperature;//上午温度

    @Column
    private String middle_temperature;//午间温度

    @Column
    private String afternoon_temperature;//下午温度

    @Column
    private String working_remark;//施工部分

    @Column
    private String emergency_remark;//突发事件

    @Column
    private String content_remark;//工作内容

    @Column
    private String check_remark;//检查、检验情况

    @Column
    private String materials_remark;//材料、设备进场

    @Column
    private String visa_remark;//设计变更及工程签证

    @Column
    private String file_remark;//工作联系单及往来文件记录

    @Column
    private String other_remark;//工作联系单及往来文件记录

    @Column
    private int status;

    @Column
    private String project_photo;//照片


    public static List<LocalWorkingLogBean> getWorkingLogList(String type){
        return SQLite.select().from(LocalWorkingLogBean.class).where(LocalWorkingLogBean_Table.type.is(type)).queryList();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLog_num() {
        return log_num;
    }

    public void setLog_num(String log_num) {
        this.log_num = log_num;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getWorking_name() {
        return working_name;
    }

    public void setWorking_name(String working_name) {
        this.working_name = working_name;
    }

    public String getWorking_num() {
        return working_num;
    }

    public void setWorking_num(String working_num) {
        this.working_num = working_num;
    }

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    public String getWeather_status() {
        return weather_status;
    }

    public void setWeather_status(String weather_status) {
        this.weather_status = weather_status;
    }

    public String getCompile_date() {
        return compile_date;
    }

    public void setCompile_date(String compile_date) {
        this.compile_date = compile_date;
    }

    public String getOccurrence_date() {
        return occurrence_date;
    }

    public void setOccurrence_date(String occurrence_date) {
        this.occurrence_date = occurrence_date;
    }

    public String getMorning_temperature() {
        return morning_temperature;
    }

    public void setMorning_temperature(String morning_temperature) {
        this.morning_temperature = morning_temperature;
    }

    public String getMiddle_temperature() {
        return middle_temperature;
    }

    public void setMiddle_temperature(String middle_temperature) {
        this.middle_temperature = middle_temperature;
    }

    public String getAfternoon_temperature() {
        return afternoon_temperature;
    }

    public void setAfternoon_temperature(String afternoon_temperature) {
        this.afternoon_temperature = afternoon_temperature;
    }

    public String getWorking_remark() {
        return working_remark;
    }

    public void setWorking_remark(String working_remark) {
        this.working_remark = working_remark;
    }

    public String getEmergency_remark() {
        return emergency_remark;
    }

    public void setEmergency_remark(String emergency_remark) {
        this.emergency_remark = emergency_remark;
    }

    public String getContent_remark() {
        return content_remark;
    }

    public void setContent_remark(String content_remark) {
        this.content_remark = content_remark;
    }

    public String getCheck_remark() {
        return check_remark;
    }

    public void setCheck_remark(String check_remark) {
        this.check_remark = check_remark;
    }

    public String getMaterials_remark() {
        return materials_remark;
    }

    public void setMaterials_remark(String materials_remark) {
        this.materials_remark = materials_remark;
    }

    public String getVisa_remark() {
        return visa_remark;
    }

    public void setVisa_remark(String visa_remark) {
        this.visa_remark = visa_remark;
    }

    public String getFile_remark() {
        return file_remark;
    }

    public void setFile_remark(String file_remark) {
        this.file_remark = file_remark;
    }

    public String getOther_remark() {
        return other_remark;
    }

    public void setOther_remark(String other_remark) {
        this.other_remark = other_remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProject_photo() {
        return project_photo;
    }

    public void setProject_photo(String project_photo) {
        this.project_photo = project_photo;
    }
}
