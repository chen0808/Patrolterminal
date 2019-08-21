package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class InitiateProjectBean2 implements Parcelable {


    private String id;
    private String name;
    private String create_name;
    private String project_no;
    private Double total_money;
    private String address;
    private String detailed_address;
    private String dep_name;
    private String parent_project;
    private String model;
    private String status;
    private String type_sign;
    private String start_time;
    private String end_time;
    private String content;
    private List<FileBean2> tempImgList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject_no() {
        return project_no;
    }

    public void setProject_no(String project_no) {
        this.project_no = project_no;
    }

    public Double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(Double total_money) {
        this.total_money = total_money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailed_address() {
        return detailed_address;
    }

    public void setDetailed_address(String detailed_address) {
        this.detailed_address = detailed_address;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getParent_project() {
        return parent_project;
    }

    public void setParent_project(String parent_project) {
        this.parent_project = parent_project;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType_sign() {
        return type_sign;
    }

    public void setType_sign(String type_sign) {
        this.type_sign = type_sign;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_name() {
        return create_name;
    }

    public void setCreate_name(String create_name) {
        this.create_name = create_name;
    }

    public List<FileBean2> getTempImgList() {
        return tempImgList;
    }

    public void setTempImgList(List<FileBean2> tempImgList) {
        this.tempImgList = tempImgList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.create_name);
        dest.writeString(this.project_no);
        dest.writeValue(this.total_money);
        dest.writeString(this.address);
        dest.writeString(this.detailed_address);
        dest.writeString(this.dep_name);
        dest.writeString(this.parent_project);
        dest.writeString(this.model);
        dest.writeString(this.status);
        dest.writeString(this.type_sign);
        dest.writeString(this.start_time);
        dest.writeString(this.end_time);
        dest.writeString(this.content);
        dest.writeTypedList(this.tempImgList);
    }

    public InitiateProjectBean2() {
    }

    protected InitiateProjectBean2(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.create_name = in.readString();
        this.project_no = in.readString();
        this.total_money = (Double) in.readValue(Double.class.getClassLoader());
        this.address = in.readString();
        this.detailed_address = in.readString();
        this.dep_name = in.readString();
        this.parent_project = in.readString();
        this.model = in.readString();
        this.status = in.readString();
        this.type_sign = in.readString();
        this.start_time = in.readString();
        this.end_time = in.readString();
        this.content = in.readString();
        this.tempImgList = in.createTypedArrayList(FileBean2.CREATOR);
    }

    public static final Parcelable.Creator<InitiateProjectBean2> CREATOR = new Parcelable.Creator<InitiateProjectBean2>() {
        @Override
        public InitiateProjectBean2 createFromParcel(Parcel source) {
            return new InitiateProjectBean2(source);
        }

        @Override
        public InitiateProjectBean2[] newArray(int size) {
            return new InitiateProjectBean2[size];
        }
    };
}
