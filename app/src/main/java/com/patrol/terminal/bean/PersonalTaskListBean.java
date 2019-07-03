package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

@Table(database = AppDataBase.class)
public class PersonalTaskListBean extends BaseModel implements Serializable {


        /**
         * id : 0F3DE59A4DB84237A95E0A04896DDE4A
         * group_list_id : 7B9A3192338245108EAD4C115A6E1A77
         * name : 刘海生2019-05-28关于1116桃南线#028杆塔的个人任务
         * type_id : C5A6C19CCA4E4C009AED7B8393150593
         * type_sign : 3
         * type_name : 接地电阻检测
         * plan_type : 1
         * line_id : F3BA53A0C28E4EEC9D6DB821CDAAA6EC
         * line_name : 1116桃南线
         * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
         * dep_name : 西固运维班
         * user_id : 4B01F91D1E10479BA898DE45023CF25B
         * user_name : 刘海生
         * tower_id : DC3AA99CC1D740D8A9759A0BB3194AB9
         * tower_name : #028
         * year : 2019
         * month : 5
         * week : 5
         * day : 28
         * audit_status : 0
         * done_status : 0
         * done_time : null
         * sub_time : null
         * towers_id : null
         */

    @PrimaryKey(autoincrement = true)
    private int local_id;
    @Column
    private String id;
    @Column
    private String group_list_id;
    @Column
    private String name;
    @Column
    private String type_id;
    @Column
    private String type_sign;
    @Column
    private String type_name;
    @Column
    private String plan_type;
    @Column
    private String line_id;
    @Column
    private String line_name;
    @Column
    private String dep_id;
    @Column
    private String dep_name;
    @Column
    private String user_id;
    @Column
    private String user_name;
    @Column
    private String tower_id;
    @Column
    private String tower_name;
    @Column
    private int year;
    @Column
    private int month;
    @Column
    private int week;
    @Column
    private int day;
    @Column
    private String audit_status;
    @Column
    private String done_status;
    @Column
    private String done_time;
    @Column
    private String sub_time;
    @Column
    private String towers_id;
    @Column
    private String check_report;
    private boolean isCheck;
    @Column
    private String tower_model;
    @Column
    private String is_save;

    public String getIs_save() {
        return is_save;
    }

    public void setIs_save(String is_save) {
        this.is_save = is_save;
    }

    public String getTower_model() {
        return tower_model;
    }

    public void setTower_model(String tower_model) {
        this.tower_model = tower_model;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public String getCheck_report() {
        return check_report;
    }

    public void setCheck_report(String check_report) {
        this.check_report = check_report;
    }

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

        public String getSub_time() {
            return sub_time;
        }

        public void setSub_time(String sub_time) {
            this.sub_time = sub_time;
        }

        public String getTowers_id() {
            return towers_id;
        }

        public void setTowers_id(String towers_id) {
            this.towers_id = towers_id;
        }



}
