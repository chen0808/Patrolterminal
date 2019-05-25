package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TrainingTempPlanBean implements Parcelable {

    /**
     * id : 0E5BDA3EE6694FCDB4F60C8B88170252
     * name : 计划名字1
     * start_time : 2019-5-01
     * end_time : 2019-5-03
     * train_days : 1.2
     * train_level : 15F90C926703441780F3708865580510
     * host_unit : 单位1
     * train_place : 地点1
     * type_id : 98AA734E9B074C3B8438240BFECBC4C2
     * teacher : 老师1
     * year : 2019
     * month : 5
     * auditor : 892938FF87AB4F8CB67451EE83954297
     * status : 0
     * remark : 备注1
     * train_level_name : 本单位
     * type_name : 专项计划
     * auditor_name : 王健
     */

    private String id;
    private String name;
    private String start_time;
    private String end_time;
    private double train_days;
    private String train_level;
    private String host_unit;
    private String train_place;
    private String type_id;
    private String teacher;
    private int year;
    private int month;
    private String auditor;
    private String status;
    private String remark;
    private String train_level_name;
    private String type_name;
    private String auditor_name;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public double getTrain_days() {
        return train_days;
    }

    public void setTrain_days(double train_days) {
        this.train_days = train_days;
    }

    public String getTrain_level() {
        return train_level;
    }

    public void setTrain_level(String train_level) {
        this.train_level = train_level;
    }

    public String getHost_unit() {
        return host_unit;
    }

    public void setHost_unit(String host_unit) {
        this.host_unit = host_unit;
    }

    public String getTrain_place() {
        return train_place;
    }

    public void setTrain_place(String train_place) {
        this.train_place = train_place;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTrain_level_name() {
        return train_level_name;
    }

    public void setTrain_level_name(String train_level_name) {
        this.train_level_name = train_level_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getAuditor_name() {
        return auditor_name;
    }

    public void setAuditor_name(String auditor_name) {
        this.auditor_name = auditor_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TrainingTempPlanBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.start_time);
        dest.writeString(this.end_time);
        dest.writeDouble(this.train_days);
        dest.writeString(this.train_level);
        dest.writeString(this.host_unit);
        dest.writeString(this.train_place);
        dest.writeString(this.type_id);
        dest.writeString(this.teacher);
        dest.writeInt(this.year);
        dest.writeInt(this.month);
        dest.writeString(this.auditor);
        dest.writeString(this.status);
        dest.writeString(this.remark);
        dest.writeString(this.train_level_name);
        dest.writeString(this.type_name);
        dest.writeString(this.auditor_name);
        dest.writeString(this.content);
    }

    protected TrainingTempPlanBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.start_time = in.readString();
        this.end_time = in.readString();
        this.train_days = in.readDouble();
        this.train_level = in.readString();
        this.host_unit = in.readString();
        this.train_place = in.readString();
        this.type_id = in.readString();
        this.teacher = in.readString();
        this.year = in.readInt();
        this.month = in.readInt();
        this.auditor = in.readString();
        this.status = in.readString();
        this.remark = in.readString();
        this.train_level_name = in.readString();
        this.type_name = in.readString();
        this.auditor_name = in.readString();
        this.content = in.readString();
    }

    public static final Creator<TrainingTempPlanBean> CREATOR = new Creator<TrainingTempPlanBean>() {
        @Override
        public TrainingTempPlanBean createFromParcel(Parcel source) {
            return new TrainingTempPlanBean(source);
        }

        @Override
        public TrainingTempPlanBean[] newArray(int size) {
            return new TrainingTempPlanBean[size];
        }
    };
}
