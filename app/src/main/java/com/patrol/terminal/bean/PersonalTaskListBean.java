package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonalTaskListBean implements Parcelable {


        /**
         * id : D6986342094D4800816E3ACEE4C73C77
         * group_list_id : E4BC8BBE76FD42C1AD93E879558C3BC7
         * name : 刘海生2019-05-27关于1111桃郑线#021杆塔的个人任务
         * type_id : C7A9A60BDB1B4FE986014CA7DA24A467
         * type_sign : 1
         * type_name : 定期巡视
         * plan_type : 1
         * line_id : 06CD39FC7726400F92C00D4C89C80F1C
         * line_name : 1111桃郑线
         * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
         * dep_name : 西固运维班
         * user_id : 4B01F91D1E10479BA898DE45023CF25B
         * user_name : 刘海生
         * tower_id : 00FF28535F314DEC938FBA61F2598367
         * tower_name : #021
         * year : 2019
         * month : 5
         * week : 5
         * day : 27
         * audit_status : 0
         * done_status : 0
         * done_time : null
         * towers_id : null
         */

        private String id;
        private String group_list_id;
        private String name;
        private String type_id;
        private String type_sign;
        private String type_name;
        private String plan_type;
        private String line_id;
        private String line_name;
        private String dep_id;
        private String dep_name;
        private String user_id;
        private String user_name;
        private String tower_id;
        private String tower_name;
        private int year;
        private int month;
        private int week;
        private int day;
        private String audit_status;
        private String done_status;
        private String done_time;
        private String towers_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGroup_list_id() {
            return group_list_id;
        }

        public void setGroup_list_id(String group_list_id) {
            this.group_list_id = group_list_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getTower_id() {
            return tower_id;
        }

        public void setTower_id(String tower_id) {
            this.tower_id = tower_id;
        }

        public String getTower_name() {
            return tower_name;
        }

        public void setTower_name(String tower_name) {
            this.tower_name = tower_name;
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

        public String getTowers_id() {
            return towers_id;
        }

        public void setTowers_id(String towers_id) {
            this.towers_id = towers_id;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.group_list_id);
        dest.writeString(this.name);
        dest.writeString(this.type_id);
        dest.writeString(this.type_sign);
        dest.writeString(this.type_name);
        dest.writeString(this.plan_type);
        dest.writeString(this.line_id);
        dest.writeString(this.line_name);
        dest.writeString(this.dep_id);
        dest.writeString(this.dep_name);
        dest.writeString(this.user_id);
        dest.writeString(this.user_name);
        dest.writeString(this.tower_id);
        dest.writeString(this.tower_name);
        dest.writeInt(this.year);
        dest.writeInt(this.month);
        dest.writeInt(this.week);
        dest.writeInt(this.day);
        dest.writeString(this.audit_status);
        dest.writeString(this.done_status);
        dest.writeString(this.done_time);
        dest.writeString(this.towers_id);
    }

    public PersonalTaskListBean() {
    }

    protected PersonalTaskListBean(Parcel in) {
        this.id = in.readString();
        this.group_list_id = in.readString();
        this.name = in.readString();
        this.type_id = in.readString();
        this.type_sign = in.readString();
        this.type_name = in.readString();
        this.plan_type = in.readString();
        this.line_id = in.readString();
        this.line_name = in.readString();
        this.dep_id = in.readString();
        this.dep_name = in.readString();
        this.user_id = in.readString();
        this.user_name = in.readString();
        this.tower_id = in.readString();
        this.tower_name = in.readString();
        this.year = in.readInt();
        this.month = in.readInt();
        this.week = in.readInt();
        this.day = in.readInt();
        this.audit_status = in.readString();
        this.done_status = in.readString();
        this.done_time = in.readString();
        this.towers_id = in.readString();
    }

    public static final Parcelable.Creator<PersonalTaskListBean> CREATOR = new Parcelable.Creator<PersonalTaskListBean>() {
        @Override
        public PersonalTaskListBean createFromParcel(Parcel source) {
            return new PersonalTaskListBean(source);
        }

        @Override
        public PersonalTaskListBean[] newArray(int size) {
            return new PersonalTaskListBean[size];
        }
    };
}
