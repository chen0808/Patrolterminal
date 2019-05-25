package com.patrol.terminal.bean;

import java.util.List;

public class SavaMonthDefDanBean {
    private String year;
    private String month;
    private String id;
    private String week_id;
    private String day_id;
    private List<IdsBean> defects;
    private List<IdsBean> dangers;

    public String getDay_id() {
        return day_id;
    }

    public void setDay_id(String day_id) {
        this.day_id = day_id;
    }

    public String getWeek_id() {
        return week_id;
    }

    public void setWeek_id(String week_id) {
        this.week_id = week_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<IdsBean> getDefects() {
        return defects;
    }

    public void setDefects(List<IdsBean> defects) {
        this.defects = defects;
    }

    public List<IdsBean> getDangers() {
        return dangers;
    }

    public void setDangers(List<IdsBean> dangers) {
        this.dangers = dangers;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
