package com.patrol.terminal.bean;

import java.util.List;

public class DayPlanDetailBean {


        /**
         * id : BBD6BEB3C595406E8DC2818B0F263A0A
         * w_id : 5484F43FD40D44898AF3A71A18B846FF
         * type_id : CF749DB258E94349A937B5955A0E919A
         * type_val : 1
         * line_id : F3BA53A0C28E4EEC9D6DB821CDAAA6EC
         * towers : null
         * done_status : null
         * audit_status : 1
         * flow_record : null
         * plan_name : 2019年4月第5周计划
         * year : 2019
         * month : 4
         * week : 5
         * begin_time : 2019-04-28T16:00:00.000+0000
         * end_time : 2019-04-29T16:00:00.000+0000
         * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
         * dep_name : 西固运维班
         * line_name : 桃南线
         * line_no : null
         * line_level : null
         * type_name : 事故巡视
         * planTypeList : null
         * planWeekTowerList : ["001#-012#","013#-019#","020#-023#"]
         */

        private String id;
        private String w_id;
        private String type_id;
        private String type_val;
        private String line_id;
        private Object towers;
        private Object done_status;
        private String audit_status;
        private Object flow_record;
        private String plan_name;
        private int year;
        private int month;
        private int week;
        private String begin_time;
        private String end_time;
        private String dep_id;
        private String dep_name;
        private String line_name;
        private Object line_no;
        private Object line_level;
        private String type_name;
        private Object planTypeList;
        private List<String> planWeekTowerList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getW_id() {
            return w_id;
        }

        public void setW_id(String w_id) {
            this.w_id = w_id;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getType_val() {
            return type_val;
        }

        public void setType_val(String type_val) {
            this.type_val = type_val;
        }

        public String getLine_id() {
            return line_id;
        }

        public void setLine_id(String line_id) {
            this.line_id = line_id;
        }

        public Object getTowers() {
            return towers;
        }

        public void setTowers(Object towers) {
            this.towers = towers;
        }

        public Object getDone_status() {
            return done_status;
        }

        public void setDone_status(Object done_status) {
            this.done_status = done_status;
        }

        public String getAudit_status() {
            return audit_status;
        }

        public void setAudit_status(String audit_status) {
            this.audit_status = audit_status;
        }

        public Object getFlow_record() {
            return flow_record;
        }

        public void setFlow_record(Object flow_record) {
            this.flow_record = flow_record;
        }

        public String getPlan_name() {
            return plan_name;
        }

        public void setPlan_name(String plan_name) {
            this.plan_name = plan_name;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public String getBegin_time() {
            return begin_time;
        }

        public void setBegin_time(String begin_time) {
            this.begin_time = begin_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
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

        public String getLine_name() {
            return line_name;
        }

        public void setLine_name(String line_name) {
            this.line_name = line_name;
        }

        public Object getLine_no() {
            return line_no;
        }

        public void setLine_no(Object line_no) {
            this.line_no = line_no;
        }

        public Object getLine_level() {
            return line_level;
        }

        public void setLine_level(Object line_level) {
            this.line_level = line_level;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public Object getPlanTypeList() {
            return planTypeList;
        }

        public void setPlanTypeList(Object planTypeList) {
            this.planTypeList = planTypeList;
        }

        public List<String> getPlanWeekTowerList() {
            return planWeekTowerList;
        }

        public void setPlanWeekTowerList(List<String> planWeekTowerList) {
            this.planWeekTowerList = planWeekTowerList;
        }
}