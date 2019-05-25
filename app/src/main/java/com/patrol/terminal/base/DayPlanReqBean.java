package com.patrol.terminal.base;

import com.patrol.terminal.bean.EqTower;

import java.util.List;

public class DayPlanReqBean {
    private String id;
    private List<EqTower> planDayTowerList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<EqTower> getPlanDayTowerList() {
        return planDayTowerList;
    }

    public void setPlanDayTowerList(List<EqTower> planDayTowerList) {
        this.planDayTowerList = planDayTowerList;
    }
}
