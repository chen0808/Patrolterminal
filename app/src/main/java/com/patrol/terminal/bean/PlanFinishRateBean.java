package com.patrol.terminal.bean;

import java.io.Serializable;

public class PlanFinishRateBean implements Serializable {
    private String username;
    private int percent1;
    private int percent2;

    public PlanFinishRateBean(String username, int percent1, int percent2) {
        this.username = username;
        this.percent1 = percent1;
        this.percent2 = percent2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPercent1() {
        return percent1;
    }

    public void setPercent1(int percent1) {
        this.percent1 = percent1;
    }

    public int getPercent2() {
        return percent2;
    }

    public void setPercent2(int percent2) {
        this.percent2 = percent2;
    }
}
