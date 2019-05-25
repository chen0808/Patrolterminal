package com.patrol.terminal.bean;

import java.util.List;

public class SubmitPlanReqStateBean {
    private List<Tower> lineIds;
    private List<Tower> weekIds;

    public List<Tower> getWeekIds() {
        return weekIds;
    }

    public void setWeekIds(List<Tower> weekIds) {
        this.weekIds = weekIds;
    }

    private String state;

    public List<Tower> getLineIds() {
        return lineIds;
    }

    public void setLineIds(List<Tower> lineIds) {
        this.lineIds = lineIds;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
