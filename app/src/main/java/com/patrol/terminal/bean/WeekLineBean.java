package com.patrol.terminal.bean;

import java.util.List;

public class WeekLineBean {
    private String id; //主键
    private String name; //计划名称
    private String type; //计划类型
    private String bzId; //班组id
    private String zTime; //执行时间
    private String month;  //月
    private String year; //年
    private String week; //第几周
    private String detail; //详情

    public void setType(String type) {
        this.type = type;
    }

    public List<LineMakeBean> getEqLineList() {
        return eqLineList;
    }

    public void setEqLineList(List<LineMakeBean> eqLineList) {
        this.eqLineList = eqLineList;
    }

    private List<LineMakeBean> eqLineList; //线路id


    public String getType() {
        return type;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getBzId() {
        return bzId;
    }

    public void setBzId(String bzId) {
        this.bzId = bzId;
    }

    public String getzTime() {
        return zTime;
    }

    public void setzTime(String zTime) {
        this.zTime = zTime;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    public static class LineMakeBean {
        private List<TowerMakeBean> eqTowerList;
        private String id;
        private String name;

        public LineMakeBean(List<TowerMakeBean> eqTowerList, String id, String name) {
            this.eqTowerList = eqTowerList;
            this.id = id;
            this.name = name;
        }

        public static class TowerMakeBean {
            private String id;
            private String name;

            public TowerMakeBean(String id, String name) {
                this.id = id;
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
