package com.patrol.terminal.bean;

import java.util.List;

public class SubmitPlanReqStateBean {
    private List<Tower> lineIds;
    private List<Tower> weekIds;
    private String state;
    private String from_user_id;

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public List<Tower> getWeekIds() {
        return weekIds;
    }

    public void setWeekIds(List<Tower> weekIds) {
        this.weekIds = weekIds;
    }



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
