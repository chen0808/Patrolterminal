package com.patrol.terminal.bean;

import java.util.List;

public class DayPlanAllotBean {
    private AddGroupTaskReqBean bean;
    private List<GroupOfDayBean> dayPlanList;
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public AddGroupTaskReqBean getBean() {
        return bean;
    }

    public void setBean(AddGroupTaskReqBean bean) {
        this.bean = bean;
    }

    public List<GroupOfDayBean> getDayPlanList() {
        return dayPlanList;
    }

    public void setDayPlanList(List<GroupOfDayBean> dayPlanList) {
        this.dayPlanList = dayPlanList;
    }
}
