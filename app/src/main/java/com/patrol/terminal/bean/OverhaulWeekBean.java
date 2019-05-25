package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class OverhaulWeekBean implements Parcelable {
    /**
     * id : 8D891B64FB6C4961AA82C2B5E3359EBC
     * device_name : 66
     * blackout_worktime : null
     * work_content : null
     * name : 1
     * remark : null
     * year : 2018
     * month : 10
     * week : 1
     * begin_time : 2018-12-01
     * end_time : 2018-12-07
     * m_id : 2
     * before_time : 2018-10-11
     * after_time : 2018-12-10
     * cheak_content : 13
     * trans_substation : 1
     * audit_status : 0
     */

    private String id;
    private String device_name;
    private String blackout_worktime;
    private String work_content;
    private String name;
    private String remark;
    private int year;
    private int month;
    private int week;
    private String begin_time;
    private String end_time;
    private String m_id;
    private String before_time;
    private String after_time;
    private String cheak_content;
    private String trans_substation;
    private String audit_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getBlackout_worktime() {
        return blackout_worktime;
    }

    public void setBlackout_worktime(String blackout_worktime) {
        this.blackout_worktime = blackout_worktime;
    }

    public String getWork_content() {
        return work_content;
    }

    public void setWork_content(String work_content) {
        this.work_content = work_content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getBefore_time() {
        return before_time;
    }

    public void setBefore_time(String before_time) {
        this.before_time = before_time;
    }

    public String getAfter_time() {
        return after_time;
    }

    public void setAfter_time(String after_time) {
        this.after_time = after_time;
    }

    public String getCheak_content() {
        return cheak_content;
    }

    public void setCheak_content(String cheak_content) {
        this.cheak_content = cheak_content;
    }

    public String getTrans_substation() {
        return trans_substation;
    }

    public void setTrans_substation(String trans_substation) {
        this.trans_substation = trans_substation;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.device_name);
        dest.writeString(this.blackout_worktime);
        dest.writeString(this.work_content);
        dest.writeString(this.name);
        dest.writeString(this.remark);
        dest.writeInt(this.year);
        dest.writeInt(this.month);
        dest.writeInt(this.week);
        dest.writeString(this.begin_time);
        dest.writeString(this.end_time);
        dest.writeString(this.m_id);
        dest.writeString(this.before_time);
        dest.writeString(this.after_time);
        dest.writeString(this.cheak_content);
        dest.writeString(this.trans_substation);
        dest.writeString(this.audit_status);
    }

    public OverhaulWeekBean() {
    }

    protected OverhaulWeekBean(Parcel in) {
        this.id = in.readString();
        this.device_name = in.readString();
        this.blackout_worktime = in.readString();
        this.work_content = in.readString();
        this.name = in.readString();
        this.remark = in.readString();
        this.year = in.readInt();
        this.month = in.readInt();
        this.week = in.readInt();
        this.begin_time = in.readString();
        this.end_time = in.readString();
        this.m_id = in.readString();
        this.before_time = in.readString();
        this.after_time = in.readString();
        this.cheak_content = in.readString();
        this.trans_substation = in.readString();
        this.audit_status = in.readString();
    }

    public static final Parcelable.Creator<OverhaulWeekBean> CREATOR = new Parcelable.Creator<OverhaulWeekBean>() {
        @Override
        public OverhaulWeekBean createFromParcel(Parcel source) {
            return new OverhaulWeekBean(source);
        }

        @Override
        public OverhaulWeekBean[] newArray(int size) {
            return new OverhaulWeekBean[size];
        }
    };
}
