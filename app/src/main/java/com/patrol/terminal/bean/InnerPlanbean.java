package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class InnerPlanbean implements Parcelable {


    /**
     * id : 3FA7F48029144EF485469D61FC2FF55E
     * check_id : 3BAF7CE3EEB44581B3D38658B43FA56E
     * plan_name : 关于-7、断开1116和开一线31#塔三相引流。-的保电计划
     * begin_time : 2019-5-8
     * end_time : 2019-5-20
     * plan_type : 1
     * dep_name : 兰州供电局
     * line_name : 西陈二线
     * voltage_level : 110kv
     * repair_content : 7、断开1116和开一线31#塔三相引流。
     * is_blackout : 1
     * blackout_range : 西固区
     * task_source : 停电配合
     * last_repair_time : null
     * risk_level : 6
     * substation_id : 110kV源泰变电站
     * remark : 123123123
     */

    private String id;
    private String check_id;
    private String plan_name;
    private String begin_time;
    private String end_time;
    private String plan_type;
    private String dep_name;
    private String line_name;
    private String voltage_level;
    private String repair_content;
    private String is_blackout;
    private String blackout_range;
    private String task_source;
    private String last_repair_time;
    private String risk_level;
    private String substation_id;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheck_id() {
        return check_id;
    }

    public void setCheck_id(String check_id) {
        this.check_id = check_id;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
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

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getVoltage_level() {
        return voltage_level;
    }

    public void setVoltage_level(String voltage_level) {
        this.voltage_level = voltage_level;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.check_id);
        dest.writeString(this.plan_name);
        dest.writeString(this.begin_time);
        dest.writeString(this.end_time);
        dest.writeString(this.plan_type);
        dest.writeString(this.dep_name);
        dest.writeString(this.line_name);
        dest.writeString(this.voltage_level);
        dest.writeString(this.repair_content);
        dest.writeString(this.is_blackout);
        dest.writeString(this.blackout_range);
        dest.writeString(this.task_source);
        dest.writeString(this.last_repair_time);
        dest.writeString(this.risk_level);
        dest.writeString(this.substation_id);
        dest.writeString(this.remark);
    }

    public InnerPlanbean() {
    }

    protected InnerPlanbean(Parcel in) {
        this.id = in.readString();
        this.check_id = in.readString();
        this.plan_name = in.readString();
        this.begin_time = in.readString();
        this.end_time = in.readString();
        this.plan_type = in.readString();
        this.dep_name = in.readString();
        this.line_name = in.readString();
        this.voltage_level = in.readString();
        this.repair_content = in.readString();
        this.is_blackout = in.readString();
        this.blackout_range = in.readString();
        this.task_source = in.readString();
        this.last_repair_time = in.readString();
        this.risk_level = in.readString();
        this.substation_id = in.readString();
        this.remark = in.readString();
    }

    public static final Parcelable.Creator<InnerPlanbean> CREATOR = new Parcelable.Creator<InnerPlanbean>() {
        @Override
        public InnerPlanbean createFromParcel(Parcel source) {
            return new InnerPlanbean(source);
        }

        @Override
        public InnerPlanbean[] newArray(int size) {
            return new InnerPlanbean[size];
        }
    };
}
