package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDataBase.class)
public class GroupTaskBean extends BaseModel implements Parcelable {

    /**
     * id : E4BC8BBE76FD42C1AD93E879558C3BC7
     * day_tower_id : E4BC8BBE76FD42C1AD93E879558C3BC7
     * group_id : 1A9331AFC30448CD810111A61DBB839F
     * type_id : C7A9A60BDB1B4FE986014CA7DA24A467
     * type_sign : 1
     * type_name : 定期巡视
     * plan_type : 1
     * line_id : 06CD39FC7726400F92C00D4C89C80F1C
     * line_name : 1111桃郑线
     * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
     * dep_name : 西固运维班
     * year : 2019
     * month : 5
     * week : 5
     * day : 27
     * name : #001-#038
     * tower_id : null
     * towers_id : 5EA2AB0F591442839BD285A9B0C1A941
     * tower_type : 1
     * duty_user_id : 4B01F91D1E10479BA898DE45023CF25B
     * duty_user_name : 刘海生
     * work_user_id : 4B01F91D1E10479BA898DE45023CF25B
     * work_user_name : 刘海生
     * allot_status : 1
     * done_status : 0
     * done_time : null
     * is_rob : 0
     * done_num : 3
     * all_num : 38
     * done_rate : 7.89
     */

    @PrimaryKey(autoincrement = true)
    private int local_id;
    @Column
    private String id;
    @Column
    private String day_tower_id;
    @Column
    private String group_id;
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
    private int year;
    @Column
    private int month;
    @Column
    private int week;
    @Column
    private int day;
    @Column
    private String name;
    @Column
    private String tower_id;
    @Column
    private String towers_id;
    @Column
    private String tower_type;
    @Column
    private String duty_user_id;
    @Column
    private String duty_user_name;
    @Column
    private String work_user_id;
    @Column
    private String work_user_name;
    @Column
    private String allot_status;
    @Column
    private String done_status;
    @Column
    private String done_time;
    @Column
    private String is_rob;
    @Column
    private int done_num;
    @Column
    private int all_num;
    @Column
    private String done_rate;
    @Column
    private String from_user_id;
    @Column
    private String from_user_name;
    @Column
    private String start_id;
    @Column
    private String end_id;
    @Column
    private String audit_status;
    @Column
    private boolean check;
    @Column
    private String user_id;
    @Column
    private String safe;

    public String getSafe() {
        return safe;
    }

    public void setSafe(String safe) {
        this.safe = safe;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    public String getEnd_id() {
        return end_id;
    }

    public void setEnd_id(String end_id) {
        this.end_id = end_id;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getFrom_user_name() {
        return from_user_name;
    }

    public void setFrom_user_name(String from_user_name) {
        this.from_user_name = from_user_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay_tower_id() {
        return day_tower_id;
    }

    public void setDay_tower_id(String day_tower_id) {
        this.day_tower_id = day_tower_id;
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

    public String getWork_user_id() {
        return work_user_id;
    }

    public void setWork_user_id(String work_user_id) {
        this.work_user_id = work_user_id;
    }

    public String getWork_user_name() {
        return work_user_name;
    }

    public void setWork_user_name(String work_user_name) {
        this.work_user_name = work_user_name;
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

    public String getIs_rob() {
        return is_rob;
    }

    public void setIs_rob(String is_rob) {
        this.is_rob = is_rob;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.day_tower_id);
        dest.writeString(this.group_id);
        dest.writeString(this.type_id);
        dest.writeString(this.type_sign);
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
        dest.writeString(this.work_user_id);
        dest.writeString(this.work_user_name);
        dest.writeString(this.allot_status);
        dest.writeString(this.done_status);
        dest.writeString(this.done_time);
        dest.writeString(this.is_rob);
        dest.writeInt(this.done_num);
        dest.writeInt(this.all_num);
        dest.writeString(this.done_rate);
        dest.writeString(this.from_user_id);
        dest.writeString(this.from_user_name);
        dest.writeString(this.start_id);
        dest.writeString(this.end_id);
    }

    public GroupTaskBean() {
    }

    protected GroupTaskBean(Parcel in) {
        this.id = in.readString();
        this.day_tower_id = in.readString();
        this.group_id = in.readString();
        this.type_id = in.readString();
        this.type_sign = in.readString();
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
        this.work_user_id = in.readString();
        this.work_user_name = in.readString();
        this.allot_status = in.readString();
        this.done_status = in.readString();
        this.done_time = in.readString();
        this.is_rob = in.readString();
        this.done_num = in.readInt();
        this.all_num = in.readInt();
        this.done_rate = in.readString();
        this.from_user_id = in.readString();
        this.from_user_name = in.readString();
        this.start_id = in.readString();
        this.end_id = in.readString();
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


    @Override
    public String toString() {
        return "GroupTaskBean{" +
                "local_id=" + local_id +
                ", id='" + id + '\'' +
                ", day_tower_id='" + day_tower_id + '\'' +
                ", group_id='" + group_id + '\'' +
                ", type_id='" + type_id + '\'' +
                ", type_sign='" + type_sign + '\'' +
                ", type_name='" + type_name + '\'' +
                ", plan_type='" + plan_type + '\'' +
                ", line_id='" + line_id + '\'' +
                ", line_name='" + line_name + '\'' +
                ", dep_id='" + dep_id + '\'' +
                ", dep_name='" + dep_name + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", week=" + week +
                ", day=" + day +
                ", name='" + name + '\'' +
                ", tower_id='" + tower_id + '\'' +
                ", towers_id='" + towers_id + '\'' +
                ", tower_type='" + tower_type + '\'' +
                ", duty_user_id='" + duty_user_id + '\'' +
                ", duty_user_name='" + duty_user_name + '\'' +
                ", work_user_id='" + work_user_id + '\'' +
                ", work_user_name='" + work_user_name + '\'' +
                ", allot_status='" + allot_status + '\'' +
                ", done_status='" + done_status + '\'' +
                ", done_time='" + done_time + '\'' +
                ", is_rob='" + is_rob + '\'' +
                ", done_num=" + done_num +
                ", all_num=" + all_num +
                ", done_rate='" + done_rate + '\'' +
                ", from_user_id='" + from_user_id + '\'' +
                ", from_user_name='" + from_user_name + '\'' +
                ", start_id='" + start_id + '\'' +
                ", end_id='" + end_id + '\'' +
                ", audit_status='" + audit_status + '\'' +
                ", check=" + check +
                ", user_id='" + user_id + '\'' +
                ", safe='" + safe + '\'' +
                '}';
    }
}
