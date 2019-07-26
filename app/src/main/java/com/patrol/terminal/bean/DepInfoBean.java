package com.patrol.terminal.bean;

import java.util.List;

public class DepInfoBean {


    /**
     * done_num : 0
     * big_count : 5
     * big_length : 13599.0
     * small_length : 0.0
     * small_count : 0
     * name : 榆中运维班
     * duty : [{"duty_user_name":"黄静波"}]
     * id : 024CD2E91A8447A799335E21B0FDADB6
     * all_num : 106
     */

    private int done_num;
    private int big_count;
    private double big_length;
    private double small_length;
    private int small_count;
    private String name;
    private String id;
    private int all_num;
    private List<DutyBean> duty;

    public int getDone_num() {
        return done_num;
    }

    public void setDone_num(int done_num) {
        this.done_num = done_num;
    }

    public int getBig_count() {
        return big_count;
    }

    public void setBig_count(int big_count) {
        this.big_count = big_count;
    }

    public double getBig_length() {
        return big_length;
    }

    public void setBig_length(double big_length) {
        this.big_length = big_length;
    }

    public double getSmall_length() {
        return small_length;
    }

    public void setSmall_length(double small_length) {
        this.small_length = small_length;
    }

    public int getSmall_count() {
        return small_count;
    }

    public void setSmall_count(int small_count) {
        this.small_count = small_count;
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

    public int getAll_num() {
        return all_num;
    }

    public void setAll_num(int all_num) {
        this.all_num = all_num;
    }

    public List<DutyBean> getDuty() {
        return duty;
    }

    public void setDuty(List<DutyBean> duty) {
        this.duty = duty;
    }

    public static class DutyBean {
        /**
         * duty_user_name : 黄静波
         */

        private String duty_user_name;

        public String getDuty_user_name() {
            return duty_user_name;
        }

        public void setDuty_user_name(String duty_user_name) {
            this.duty_user_name = duty_user_name;
        }
    }
}
