package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class OverhaulYearBean implements Parcelable {


    /**
     * id : E4E1C11AE6AF4979B29AB4212121707A
     * apply_dep_id : 34B9F165BF9B4527B01ABF328055FBD3
     * apply_dep_name : 兰州供电局
     * voltage_level : 110kv
     * line_id : ED5A142D451C4CE6B67C7A51962D9604
     * line_name : 东南线
     * repair_content : 恢复东南线1#至门架的导地线
     * is_blackout : 1
     * blackout_range : 安宁区
     * task_source : 停电配合
     * start_time : 2019-05-03
     * end_time : 2019-05-05
     * blackout_days : 1
     * last_repair_time : 2019-01-01
     * risk_level : 6
     * type_id : null
     * type_val : null
     * substation_id : 110kV源泰变电站
     * remark : 22
     * year : 2019
     * month : 5
     * week : 1
     * day : 3
     * month_audit_status : 0
     * week_audit_status : 0
     * done_status : 0
     * done_time : null
     * is_ele : 1
     * ele_user_id : null
     * ele_user_name : null
     * taskList : null
     * userId1 : null
     * userName1 : null
     * userId2 : null
     * userName2 : null
     */

    private String id;
    private String apply_dep_id;
    private String apply_dep_name;
    private String voltage_level;
    private String line_id;
    private String line_name;
    private String repair_content;
    private String is_blackout;
    private String blackout_range;
    private String task_source;
    private String start_time;
    private String end_time;
    private int blackout_days;
    private String last_repair_time;
    private String risk_level;
    private String type_id;
    private String type_val;
    private String substation_id;
    private String remark;
    private int year;
    private int month;
    private int week;
    private int day;
    private String month_audit_status;
    private String week_audit_status;
    private String done_status;
    private String done_time;
    private String is_ele;
    private String ele_user_id;
    private String ele_user_name;
    private String taskList;
    private String userId1;
    private String userName1;
    private String userId2;
    private String userName2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApply_dep_id() {
        return apply_dep_id;
    }

    public void setApply_dep_id(String apply_dep_id) {
        this.apply_dep_id = apply_dep_id;
    }

    public String getApply_dep_name() {
        return apply_dep_name;
    }

    public void setApply_dep_name(String apply_dep_name) {
        this.apply_dep_name = apply_dep_name;
    }

    public String getVoltage_level() {
        return voltage_level;
    }

    public void setVoltage_level(String voltage_level) {
        this.voltage_level = voltage_level;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getRepair_content() {
        return repair_content;
    }

    public void setRepair_content(String repair_content) {
        this.repair_content = repair_content;
    }

    public String getIs_blackout() {
        return is_blackout;
    }

    public void setIs_blackout(String is_blackout) {
        this.is_blackout = is_blackout;
    }

    public String getBlackout_range() {
        return blackout_range;
    }

    public void setBlackout_range(String blackout_range) {
        this.blackout_range = blackout_range;
    }

    public String getTask_source() {
        return task_source;
    }

    public void setTask_source(String task_source) {
        this.task_source = task_source;
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

    public int getBlackout_days() {
        return blackout_days;
    }

    public void setBlackout_days(int blackout_days) {
        this.blackout_days = blackout_days;
    }

    public String getLast_repair_time() {
        return last_repair_time;
    }

    public void setLast_repair_time(String last_repair_time) {
        this.last_repair_time = last_repair_time;
    }

    public String getRisk_level() {
        return risk_level;
    }

    public void setRisk_level(String risk_level) {
        this.risk_level = risk_level;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_val() {
        return type_val;
    }

    public void setType_val(String type_val) {
        this.type_val = type_val;
    }

    public String getSubstation_id() {
        return substation_id;
    }

    public void setSubstation_id(String substation_id) {
        this.substation_id = substation_id;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMonth_audit_status() {
        return month_audit_status;
    }

    public void setMonth_audit_status(String month_audit_status) {
        this.month_audit_status = month_audit_status;
    }

    public String getWeek_audit_status() {
        return week_audit_status;
    }

    public void setWeek_audit_status(String week_audit_status) {
        this.week_audit_status = week_audit_status;
    }

    public String getDone_status() {
        return done_status;
    }

    public void setDone_status(String done_status) {
        this.done_status = done_status;
    }

    public String getDone_time() {
        return done_time;
    }

    public void setDone_time(String done_time) {
        this.done_time = done_time;
    }

    public String getIs_ele() {
        return is_ele;
    }

    public void setIs_ele(String is_ele) {
        this.is_ele = is_ele;
    }

    public String getEle_user_id() {
        return ele_user_id;
    }

    public void setEle_user_id(String ele_user_id) {
        this.ele_user_id = ele_user_id;
    }

    public String getEle_user_name() {
        return ele_user_name;
    }

    public void setEle_user_name(String ele_user_name) {
        this.ele_user_name = ele_user_name;
    }

    public String getTaskList() {
        return taskList;
    }

    public void setTaskList(String taskList) {
        this.taskList = taskList;
    }

    public String getUserId1() {
        return userId1;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }

    public String getUserName1() {
        return userName1;
    }

    public void setUserName1(String userName1) {
        this.userName1 = userName1;
    }

    public String getUserId2() {
        return userId2;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
    }

    public String getUserName2() {
        return userName2;
    }

    public void setUserName2(String userName2) {
        this.userName2 = userName2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.apply_dep_id);
        dest.writeString(this.apply_dep_name);
        dest.writeString(this.voltage_level);
        dest.writeString(this.line_id);
        dest.writeString(this.line_name);
        dest.writeString(this.repair_content);
        dest.writeString(this.is_blackout);
        dest.writeString(this.blackout_range);
        dest.writeString(this.task_source);
        dest.writeString(this.start_time);
        dest.writeString(this.end_time);
        dest.writeInt(this.blackout_days);
        dest.writeString(this.last_repair_time);
        dest.writeString(this.risk_level);
        dest.writeString(this.type_id);
        dest.writeString(this.type_val);
        dest.writeString(this.substation_id);
        dest.writeString(this.remark);
        dest.writeInt(this.year);
        dest.writeInt(this.month);
        dest.writeInt(this.week);
        dest.writeInt(this.day);
        dest.writeString(this.month_audit_status);
        dest.writeString(this.week_audit_status);
        dest.writeString(this.done_status);
        dest.writeString(this.done_time);
        dest.writeString(this.is_ele);
        dest.writeString(this.ele_user_id);
        dest.writeString(this.ele_user_name);
        dest.writeString(this.taskList);
        dest.writeString(this.userId1);
        dest.writeString(this.userName1);
        dest.writeString(this.userId2);
        dest.writeString(this.userName2);
    }

    public OverhaulYearBean() {
    }

    protected OverhaulYearBean(Parcel in) {
        this.id = in.readString();
        this.apply_dep_id = in.readString();
        this.apply_dep_name = in.readString();
        this.voltage_level = in.readString();
        this.line_id = in.readString();
        this.line_name = in.readString();
        this.repair_content = in.readString();
        this.is_blackout = in.readString();
        this.blackout_range = in.readString();
        this.task_source = in.readString();
        this.start_time = in.readString();
        this.end_time = in.readString();
        this.blackout_days = in.readInt();
        this.last_repair_time = in.readString();
        this.risk_level = in.readString();
        this.type_id = in.readString();
        this.type_val = in.readString();
        this.substation_id = in.readString();
        this.remark = in.readString();
        this.year = in.readInt();
        this.month = in.readInt();
        this.week = in.readInt();
        this.day = in.readInt();
        this.month_audit_status = in.readString();
        this.week_audit_status = in.readString();
        this.done_status = in.readString();
        this.done_time = in.readString();
        this.is_ele = in.readString();
        this.ele_user_id = in.readString();
        this.ele_user_name = in.readString();
        this.taskList = in.readString();
        this.userId1 = in.readString();
        this.userName1 = in.readString();
        this.userId2 = in.readString();
        this.userName2 = in.readString();
    }

    public static final Parcelable.Creator<OverhaulYearBean> CREATOR = new Parcelable.Creator<OverhaulYearBean>() {
        @Override
        public OverhaulYearBean createFromParcel(Parcel source) {
            return new OverhaulYearBean(source);
        }

        @Override
        public OverhaulYearBean[] newArray(int size) {
            return new OverhaulYearBean[size];
        }
    };
}
