package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class WorkingLogBean implements Serializable {

    private String id;
    private String log_no;
    private String temp_project_id;
    private String temp_project_name;
    private String user_name;
    private Double user_number;
    private String created_user_id;
    private String created_user_name;
    private String weather;
    private String created_date;
    private String happen_date;
    private Double temperature_am;
    private Double temperature_noon;
    private Double temperature_pm;
    private String construction_content;
    private String emergency_content;
    private String work_content;
    private String check_content;
    private String tool_content;
    private String permit_content;
    private String contact_content;
    private String other_content;
    private String log_sign;
    private List<FileBean> tempImgList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLog_no() {
        return log_no;
    }

    public void setLog_no(String log_no) {
        this.log_no = log_no;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Double getUser_number() {
        return user_number;
    }

    public void setUser_number(Double user_number) {
        this.user_number = user_number;
    }

    public String getCreated_user_id() {
        return created_user_id;
    }

    public void setCreated_user_id(String created_user_id) {
        this.created_user_id = created_user_id;
    }

    public String getCreated_user_name() {
        return created_user_name;
    }

    public void setCreated_user_name(String created_user_name) {
        this.created_user_name = created_user_name;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getHappen_date() {
        return happen_date;
    }

    public void setHappen_date(String happen_date) {
        this.happen_date = happen_date;
    }

    public String getConstruction_content() {
        return construction_content;
    }

    public void setConstruction_content(String construction_content) {
        this.construction_content = construction_content;
    }

    public String getEmergency_content() {
        return emergency_content;
    }

    public void setEmergency_content(String emergency_content) {
        this.emergency_content = emergency_content;
    }

    public String getWork_content() {
        return work_content;
    }

    public void setWork_content(String work_content) {
        this.work_content = work_content;
    }

    public String getCheck_content() {
        return check_content;
    }

    public void setCheck_content(String check_content) {
        this.check_content = check_content;
    }

    public String getTool_content() {
        return tool_content;
    }

    public void setTool_content(String tool_content) {
        this.tool_content = tool_content;
    }

    public String getPermit_content() {
        return permit_content;
    }

    public void setPermit_content(String permit_content) {
        this.permit_content = permit_content;
    }

    public String getContact_content() {
        return contact_content;
    }

    public void setContact_content(String contact_content) {
        this.contact_content = contact_content;
    }

    public String getOther_content() {
        return other_content;
    }

    public void setOther_content(String other_content) {
        this.other_content = other_content;
    }

    public String getLog_sign() {
        return log_sign;
    }

    public void setLog_sign(String log_sign) {
        this.log_sign = log_sign;
    }

    public Double getTemperature_am() {
        return temperature_am;
    }

    public void setTemperature_am(Double temperature_am) {
        this.temperature_am = temperature_am;
    }

    public Double getTemperature_noon() {
        return temperature_noon;
    }

    public void setTemperature_noon(Double temperature_noon) {
        this.temperature_noon = temperature_noon;
    }

    public Double getTemperature_pm() {
        return temperature_pm;
    }

    public void setTemperature_pm(Double temperature_pm) {
        this.temperature_pm = temperature_pm;
    }

    public List<FileBean> getTempImgList() {
        return tempImgList;
    }

    public void setTempImgList(List<FileBean> tempImgList) {
        this.tempImgList = tempImgList;
    }
}
