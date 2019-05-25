package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TrainingMonthPlanBean implements Parcelable {



    /**
     * id : 67D9BF8347514A13A886F18F50BD423B
     * name : 培训计划001-2019-5
     * host_unit : 主办单位005
     * type_id : 98AA734E9B074C3B8438240BFECBC4C2
     * year : 2019
     * month : 5
     * type_name : 专项计划
     */

    private String id;
    private String name;
    private String host_unit;
    private String type_id;
    private int year;
    private int month;
    private String type_name;
    private String train_level;
    private String train_level_name;

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

    public String getHost_unit() {
        return host_unit;
    }

    public void setHost_unit(String host_unit) {
        this.host_unit = host_unit;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
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

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getTrain_level() {
        return train_level;
    }

    public void setTrain_level(String train_level) {
        this.train_level = train_level;
    }

    public String getTrain_level_name() {
        return train_level_name;
    }

    public void setTrain_level_name(String train_level_name) {
        this.train_level_name = train_level_name;
    }

    public static Creator<TrainingMonthPlanBean> getCREATOR() {
        return CREATOR;
    }

    public TrainingMonthPlanBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.host_unit);
        dest.writeString(this.type_id);
        dest.writeInt(this.year);
        dest.writeInt(this.month);
        dest.writeString(this.type_name);
        dest.writeString(this.train_level);
        dest.writeString(this.train_level_name);
    }

    protected TrainingMonthPlanBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.host_unit = in.readString();
        this.type_id = in.readString();
        this.year = in.readInt();
        this.month = in.readInt();
        this.type_name = in.readString();
        this.train_level = in.readString();
        this.train_level_name = in.readString();
    }

    public static final Creator<TrainingMonthPlanBean> CREATOR = new Creator<TrainingMonthPlanBean>() {
        @Override
        public TrainingMonthPlanBean createFromParcel(Parcel source) {
            return new TrainingMonthPlanBean(source);
        }

        @Override
        public TrainingMonthPlanBean[] newArray(int size) {
            return new TrainingMonthPlanBean[size];
        }
    };
}
