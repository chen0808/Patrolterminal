package com.patrol.terminal.bean;

public class MonthPlanBean {


        /**
         * full_plan : 接地电阻,定期巡视
         * plan_type : 1
         * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
         * month : 6
         * year : 2019
         * month_id : 12E39DC62F604886973E69BC4E0AA44B
         * line_name : 新川线
         * dep_name : 西固运维班
         * line_id : CA4C0F9B205C4FF1833A82E002CB09FA
         */

        private String full_plan;
        private String plan_type;
        private String dep_id;
        private int month;
        private int year;
        private String audit_status;
        private String month_id;
        private String line_name;
        private String dep_name;
        private String line_id;

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public String getFull_plan() {
            return full_plan;
        }

        public void setFull_plan(String full_plan) {
            this.full_plan = full_plan;
        }

        public String getPlan_type() {
            return plan_type;
        }

        public void setPlan_type(String plan_type) {
            this.plan_type = plan_type;
        }

        public String getDep_id() {
            return dep_id;
        }

        public void setDep_id(String dep_id) {
            this.dep_id = dep_id;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getMonth_id() {
            return month_id;
        }

        public void setMonth_id(String month_id) {
            this.month_id = month_id;
        }

        public String getLine_name() {
            return line_name;
        }

        public void setLine_name(String line_name) {
            this.line_name = line_name;
        }

        public String getDep_name() {
            return dep_name;
        }

        public void setDep_name(String dep_name) {
            this.dep_name = dep_name;
        }

        public String getLine_id() {
            return line_id;
        }

        public void setLine_id(String line_id) {
            this.line_id = line_id;
        }
}
