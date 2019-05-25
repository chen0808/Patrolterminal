package com.patrol.terminal.bean;

import java.util.List;

public class DayPlanReqBean {
    private String id;
    private List<AddWeekListBean.TowersBean> planDayTowerList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AddWeekListBean.TowersBean> getPlanDayTowerList() {
        return planDayTowerList;
    }

    public void setPlanDayTowerList(List<AddWeekListBean.TowersBean> planDayTowerList) {
        this.planDayTowerList = planDayTowerList;
    }
}
