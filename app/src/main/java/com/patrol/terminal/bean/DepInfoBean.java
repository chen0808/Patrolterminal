package com.patrol.terminal.bean;

import java.util.List;

public class DepInfoBean {


        /**
         * big_count : 0
         * big_length : 0.0
         * small_length : 0.0
         * dep_id : 00C8BDB1D8734710967A6834B3C6F5EE
         * small_count : 0
         * name : 兰州运维班
         * duty : []
         * finish : 0
         */

        private int big_count;
        private double big_length;
        private double small_length;
        private String dep_id;
        private int small_count;
        private String name;
        private int finish;
        private List<DutyBean> duty;
public class DutyBean{
   String duty_user_name;

    public String getDuty_user_name() {
        return duty_user_name;
    }

    public void setDuty_user_name(String duty_user_name) {
        this.duty_user_name = duty_user_name;
    }
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

        public String getDep_id() {
            return dep_id;
        }

        public void setDep_id(String dep_id) {
            this.dep_id = dep_id;
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

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        public List<DutyBean> getDuty() {
            return duty;
        }

        public void setDuty(List<DutyBean> duty) {
            this.duty = duty;
        }
}
