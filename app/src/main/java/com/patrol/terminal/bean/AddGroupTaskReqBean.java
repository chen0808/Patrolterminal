package com.patrol.terminal.bean;

import java.util.List;

public class AddGroupTaskReqBean {

    /**
     * day_id :
     * dep_id :
     * dep_name :
     * duty_user_id :
     * duty_user_name :
     * year :
     * month :
     * day :
     * lists : [{"day_line_id":"","line_id":"","line_name":"","type_id":"","type_name":"","plan_type":"","towers_id":""}]
     * users : [{"user_id":"","user_name":"","is_boss":""}]
     * defects : [{"id":""}]
     * dangers : [{"id":""}]
     */

    private String day_id;
    private String dep_id;
    private String dep_name;
    private String duty_user_id; //小组负责人id
    private String from_user_id;  //班长id
    private String duty_user_name;
    private String year;
    private String month;
    private String day;
    private String week;
    private List< GroupOfDayBean> lists;
    private List<UsersBean> users;
    private List<DefectBean> defects;
    private List<DefectBean> dangers;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public List<DefectBean> getDefects() {
        return defects;
    }

    public void setDefects(List<DefectBean> defects) {
        this.defects = defects;
    }

    public List<DefectBean> getDangers() {
        return dangers;
    }

    public void setDangers(List<DefectBean> dangers) {
        this.dangers = dangers;
    }

    public String getDay_id() {
        return day_id;
    }

    public void setDay_id(String day_id) {
        this.day_id = day_id;
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

    public String getDuty_user_id() {
        return duty_user_id;
    }

    public void setDuty_user_id(String duty_user_id) {
        this.duty_user_id = duty_user_id;
    }

    public String getDuty_user_name() {
        return duty_user_name;
    }

    public void setDuty_user_name(String duty_user_name) {
        this.duty_user_name = duty_user_name;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List< GroupOfDayBean> getLists() {
        return lists;
    }

    public void setLists(List< GroupOfDayBean> lists) {
        this.lists = lists;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }



    public static class ListsBean {
        /**
         * day_line_id :
         * line_id :
         * line_name :
         * type_id :
         * type_name :
         * plan_type :
         * towers_id :
         */

        private String day_line_id;
        private String line_id;
        private String line_name;
        private String type_id;
        private String type_name;
        private String plan_type;
        private String towers_id;

        public String getDay_line_id() {
            return day_line_id;
        }

        public void setDay_line_id(String day_line_id) {
            this.day_line_id = day_line_id;
        }

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

        public String getTowers_id() {
            return towers_id;
        }

        public void setTowers_id(String towers_id) {
            this.towers_id = towers_id;
        }
    }

    public static class UsersBean {
        /**
         * user_id :
         * user_name :
         * is_boss :
         */

        private String user_id;
        private String user_name;
        private String is_boss;
        private String sign;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getIs_boss() {
            return is_boss;
        }

        public void setIs_boss(String is_boss) {
            this.is_boss = is_boss;
        }
    }


}
