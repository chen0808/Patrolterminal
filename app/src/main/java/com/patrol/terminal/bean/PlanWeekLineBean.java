package com.patrol.terminal.bean;

import java.util.List;

public class PlanWeekLineBean {

    /**
     * line_id :
     * line_name :
     * dep_id :
     * dep_name :
     * type_id :
     * type_name :
     * plan_type :
     * towers : [{"towers_id":""},{"towers_id":""}]
     */

    private String line_id;
    private String line_name;
    private String dep_id;
    private String dep_name;
    private String type_id;
    private String type_name;
    private String plan_type;

    private List<TowersBean> towers;


    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }

    public List<TowersBean> getTowers() {
        return towers;
    }

    public void setTowers(List<TowersBean> towers) {
        this.towers = towers;
    }

    public static class TowersBean {
        /**
         * towers_id :
         */

        private String towers_id;
        private String name;
        private String week_line_id;
        private String month_line_id;

        public String getMonth_line_id() {
            return month_line_id;
        }

        public void setMonth_line_id(String month_line_id) {
            this.month_line_id = month_line_id;
        }

        public String getWeek_line_id() {
            return week_line_id;
        }

        public void setWeek_line_id(String week_line_id) {
            this.week_line_id = week_line_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTowers_id() {
            return towers_id;
        }

        public void setTowers_id(String towers_id) {
            this.towers_id = towers_id;
        }
    }
}
