package com.patrol.terminal.bean;

import java.util.List;

public class GroupTeamSaveBean {
  private  int year;
  private  int month;
  private int day;
    private String  dep_id;

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    private List<AddGroupTaskReqBean>  taskGroupList;

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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<AddGroupTaskReqBean> getTaskGroupList() {
        return taskGroupList;
    }

    public void setTaskGroupList(List<AddGroupTaskReqBean> taskGroupList) {
        this.taskGroupList = taskGroupList;
    }
}
