package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class WeekListBean implements Parcelable {


        /**
         * id : 9DC66C3D80AC4636B6030C1EFB3190FB
         * week_id : F45F8EFD7A504FA8B8F607442EDCABB0
         * month_line_id : null
         * week_line_id : null
         * type_id : C7A9A60BDB1B4FE986014CA7DA24A467
         * type_sign : 1
         * type_name : 定期巡视
         * line_id : A4EFEC9CF1124D19B421B7536D443517
         * line_name : 1125西陈二线
         * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
         * dep_name : 西固运维班
         * name : #001-#027
         * tower_id : null
         * towers_id : 90AF31611EF740C2A0DCC289CB72049B
         * tower_type : 1
         * year : 2019
         * month : 5
         * week : 5
         * allot_status : 0
         * audit_status : 0
         * done_status : 0
         * done_time : null
         */

        private String id;
        private String week_id;
        private String month_line_id;
        private String week_line_id;
        private String type_id;
        private String type_sign;
        private String type_name;
        private String line_id;
        private String line_name;
        private String dep_id;
        private String dep_name;
        private String name;
        private String tower_id;
        private String towers_id;
        private String tower_type;
        private int year;
        private int month;
        private int week;
        private String allot_status;
        private String audit_status;
        private String done_status;
        private String done_time;
    private int done_num;
    private int all_num;
    private String done_rate;

    public int getDone_num() {
        return done_num;
    }

    public void setDone_num(int done_num) {
        this.done_num = done_num;
    }

    public int getAll_num() {
        return all_num;
    }

    public void setAll_num(int all_num) {
        this.all_num = all_num;
    }

    public String getDone_rate() {
        return done_rate;
    }

    public void setDone_rate(String done_rate) {
        this.done_rate = done_rate;
    }

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWeek_id() {
            return week_id;
        }

        public void setWeek_id(String week_id) {
            this.week_id = week_id;
        }

        public String getMonth_line_id() {
            return month_line_id;
        }

        public void setMonth_line_id(String month_line_id) {
            this.month_line_id = month_line_id;
        }

        public String getWeek_line_id() {
            return week_line_id;
        }

        public void setWeek_line_id(String week_line_id) {
            this.week_line_id = week_line_id;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getType_sign() {
            return type_sign;
        }

        public void setType_sign(String type_sign) {
            this.type_sign = type_sign;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
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

        public String getDep_id() {
            return dep_id;
        }

        public void setDep_id(String dep_id) {
            this.dep_id = dep_id;
        }

        public String getDep_name() {
            return dep_name;
        }

        public void setDep_name(String dep_name) {
            this.dep_name = dep_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTower_id() {
            return tower_id;
        }

        public void setTower_id(String tower_id) {
            this.tower_id = tower_id;
        }

        public String getTowers_id() {
            return towers_id;
        }

        public void setTowers_id(String towers_id) {
            this.towers_id = towers_id;
        }

        public String getTower_type() {
            return tower_type;
        }

        public void setTower_type(String tower_type) {
            this.tower_type = tower_type;
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

        public String getAllot_status() {
            return allot_status;
        }

        public void setAllot_status(String allot_status) {
            this.allot_status = allot_status;
        }

        public String getAudit_status() {
            return audit_status;
        }

        public void setAudit_status(String audit_status) {
            this.audit_status = audit_status;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.week_id);
        dest.writeString(this.month_line_id);
        dest.writeString(this.week_line_id);
        dest.writeString(this.type_id);
        dest.writeString(this.type_sign);
        dest.writeString(this.type_name);
        dest.writeString(this.line_id);
        dest.writeString(this.line_name);
        dest.writeString(this.dep_id);
        dest.writeString(this.dep_name);
        dest.writeString(this.name);
        dest.writeString(this.tower_id);
        dest.writeString(this.towers_id);
        dest.writeString(this.tower_type);
        dest.writeInt(this.year);
        dest.writeInt(this.month);
        dest.writeInt(this.week);
        dest.writeString(this.allot_status);
        dest.writeString(this.audit_status);
        dest.writeString(this.done_status);
        dest.writeString(this.done_time);
        dest.writeInt(this.done_num);
        dest.writeInt(this.all_num);
        dest.writeString(this.done_rate);
    }

    public WeekListBean() {
    }

    protected WeekListBean(Parcel in) {
        this.id = in.readString();
        this.week_id = in.readString();
        this.month_line_id = in.readString();
        this.week_line_id = in.readString();
        this.type_id = in.readString();
        this.type_sign = in.readString();
        this.type_name = in.readString();
        this.line_id = in.readString();
        this.line_name = in.readString();
        this.dep_id = in.readString();
        this.dep_name = in.readString();
        this.name = in.readString();
        this.tower_id = in.readString();
        this.towers_id = in.readString();
        this.tower_type = in.readString();
        this.year = in.readInt();
        this.month = in.readInt();
        this.week = in.readInt();
        this.allot_status = in.readString();
        this.audit_status = in.readString();
        this.done_status = in.readString();
        this.done_time = in.readString();
        this.done_num = in.readInt();
        this.all_num = in.readInt();
        this.done_rate = in.readString();
    }

    public static final Parcelable.Creator<WeekListBean> CREATOR = new Parcelable.Creator<WeekListBean>() {
        @Override
        public WeekListBean createFromParcel(Parcel source) {
            return new WeekListBean(source);
        }

        @Override
        public WeekListBean[] newArray(int size) {
            return new WeekListBean[size];
        }
    };
}
