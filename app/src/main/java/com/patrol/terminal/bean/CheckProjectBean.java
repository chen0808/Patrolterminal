package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CheckProjectBean implements Parcelable {

    private String project_id;
    private String name;
    private String content;
    private String create_person_name;
    private String create_person_id;
    private int project_result_status;  //0:通过,1:正常,2:待整改  根据项目里面检查结果去判断  全部通过为通过;一个不通过为正常,大于一个不通过为待整改

    private int nature;  //性质  1,2,3,4
    private String time;
    private String check_person;
    private String check_person_id;

    private List<CheckResultBean> checkResultBeans;

    private String remark_person;
    private String remark_person_id;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public int getProject_result_status() {
        return project_result_status;
    }

    public void setProject_result_status(int project_result_status) {
        this.project_result_status = project_result_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_person_name() {
        return create_person_name;
    }

    public void setCreate_person_name(String create_person_name) {
        this.create_person_name = create_person_name;
    }

    public String getCreate_person_id() {
        return create_person_id;
    }

    public void setCreate_person_id(String create_person_id) {
        this.create_person_id = create_person_id;
    }

    public int getNature() {
        return nature;
    }

    public void setNature(int nature) {
        this.nature = nature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCheck_person() {
        return check_person;
    }

    public void setCheck_person(String check_person) {
        this.check_person = check_person;
    }

    public String getCheck_person_id() {
        return check_person_id;
    }

    public void setCheck_person_id(String check_person_id) {
        this.check_person_id = check_person_id;
    }

    public List<CheckResultBean> getCheckResultBeans() {
        return checkResultBeans;
    }

    public void setCheckResultBeans(List<CheckResultBean> checkResultBeans) {
        this.checkResultBeans = checkResultBeans;
    }

    public String getRemark_person() {
        return remark_person;
    }

    public void setRemark_person(String remark_person) {
        this.remark_person = remark_person;
    }

    public String getRemark_person_id() {
        return remark_person_id;
    }

    public void setRemark_person_id(String remark_person_id) {
        this.remark_person_id = remark_person_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.project_id);
        dest.writeString(this.name);
        dest.writeString(this.content);
        dest.writeString(this.create_person_name);
        dest.writeString(this.create_person_id);
        dest.writeInt(this.project_result_status);
        dest.writeInt(this.nature);
        dest.writeString(this.time);
        dest.writeString(this.check_person);
        dest.writeString(this.check_person_id);
        dest.writeList(this.checkResultBeans);
        dest.writeString(this.remark_person);
        dest.writeString(this.remark_person_id);
    }

    public CheckProjectBean() {
    }

    protected CheckProjectBean(Parcel in) {
        this.project_id = in.readString();
        this.name = in.readString();
        this.content = in.readString();
        this.create_person_name = in.readString();
        this.create_person_id = in.readString();
        this.project_result_status = in.readInt();
        this.nature = in.readInt();
        this.time = in.readString();
        this.check_person = in.readString();
        this.check_person_id = in.readString();
        this.checkResultBeans = new ArrayList<CheckResultBean>();
        in.readList(this.checkResultBeans, CheckResultBean.class.getClassLoader());
        this.remark_person = in.readString();
        this.remark_person_id = in.readString();
    }

    public static final Parcelable.Creator<CheckProjectBean> CREATOR = new Parcelable.Creator<CheckProjectBean>() {
        @Override
        public CheckProjectBean createFromParcel(Parcel source) {
            return new CheckProjectBean(source);
        }

        @Override
        public CheckProjectBean[] newArray(int size) {
            return new CheckProjectBean[size];
        }
    };
}
