package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class GroupTaskBean implements Parcelable {


        /**
         * id : F7B452AB63C74EF196FCBB804B5A5BEA
         * day_line_id : 81D9C511128648F0A1B9A143815F1399
         * group_id : 73E8F083309A4A658910BAB906826C11
         * type_id : C7A9A60BDB1B4FE986014CA7DA24A467
         * type_val : 1
         * type_name : 定期巡视
         * plan_type : 1
         * line_id : 06CD39FC7726400F92C00D4C89C80F1C
         * line_name : 桃郑线
         * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
         * dep_name : 西固运维班
         * year : 2019
         * month : 5
         * week : 4
         * day : 20
         * name : #001-#038
         * tower_id : null
         * towers_id : 5EA2AB0F591442839BD285A9B0C1A941
         * tower_type : 1
         * duty_user_id : 9CF4DCD383474DAC89F0D0C9DCC8071B
         * duty_user_name : 邓贵宝
         * allot_status : 0
         * done_status : 0
         * done_time : null
         */

        private String id;
        private String day_line_id;
        private String group_id;
        private String type_id;
        private String type_val;
        private String type_name;
        private String plan_type;
        private String line_id;
        private String line_name;
        private String dep_id;
        private String dep_name;
        private int year;
        private int month;
        private int week;
        private int day;
        private String name;
        private String tower_id;
        private String towers_id;
        private String tower_type;
        private String duty_user_id;
        private String duty_user_name;
        private String allot_status;
        private String done_status;
        private String done_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDay_line_id() {
            return day_line_id;
        }

        public void setDay_line_id(String day_line_id) {
            this.day_line_id = day_line_id;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
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

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getPlan_type() {
            return plan_type;
        }

        public void setPlan_type(String plan_type) {
            this.plan_type = plan_type;
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

        public String getDuty_user_id() {
            return duty_user_id;
        }

        public void setDuty_user_id(String duty_user_id) {
            this.duty_user_id = duty_user_id;
        }

        public String getDuty_user_name() {
            return duty_user_name;
        }

        public void setDuty_user_name(String duty_user_name) {
            this.duty_user_name = duty_user_name;
        }

        public String getAllot_status() {
            return allot_status;
        }

        public void setAllot_status(String allot_status) {
            this.allot_status = allot_status;
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
        dest.writeString(this.day_line_id);
        dest.writeString(this.group_id);
        dest.writeString(this.type_id);
        dest.writeString(this.type_val);
        dest.writeString(this.type_name);
        dest.writeString(this.plan_type);
        dest.writeString(this.line_id);
        dest.writeString(this.line_name);
        dest.writeString(this.dep_id);
        dest.writeString(this.dep_name);
        dest.writeInt(this.year);
        dest.writeInt(this.month);
        dest.writeInt(this.week);
        dest.writeInt(this.day);
        dest.writeString(this.name);
        dest.writeString(this.tower_id);
        dest.writeString(this.towers_id);
        dest.writeString(this.tower_type);
        dest.writeString(this.duty_user_id);
        dest.writeString(this.duty_user_name);
        dest.writeString(this.allot_status);
        dest.writeString(this.done_status);
        dest.writeString(this.done_time);
    }

    public GroupTaskBean() {
    }

    protected GroupTaskBean(Parcel in) {
        this.id = in.readString();
        this.day_line_id = in.readString();
        this.group_id = in.readString();
        this.type_id = in.readString();
        this.type_val = in.readString();
        this.type_name = in.readString();
        this.plan_type = in.readString();
        this.line_id = in.readString();
        this.line_name = in.readString();
        this.dep_id = in.readString();
        this.dep_name = in.readString();
        this.year = in.readInt();
        this.month = in.readInt();
        this.week = in.readInt();
        this.day = in.readInt();
        this.name = in.readString();
        this.tower_id = in.readString();
        this.towers_id = in.readString();
        this.tower_type = in.readString();
        this.duty_user_id = in.readString();
        this.duty_user_name = in.readString();
        this.allot_status = in.readString();
        this.done_status = in.readString();
        this.done_time = in.readString();
    }

    public static final Parcelable.Creator<GroupTaskBean> CREATOR = new Parcelable.Creator<GroupTaskBean>() {
        @Override
        public GroupTaskBean createFromParcel(Parcel source) {
            return new GroupTaskBean(source);
        }

        @Override
        public GroupTaskBean[] newArray(int size) {
            return new GroupTaskBean[size];
        }
    };
}
