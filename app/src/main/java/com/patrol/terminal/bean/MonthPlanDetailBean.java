package com.patrol.terminal.bean;

/**
 * Created by 付刚 on 2018/7/25.
 */

public class MonthPlanDetailBean {


        /**
         * id : E71C5AA6F47D4C75BA8D0C8170BF1DCC
         * y_id : 1509C4EFC75D4D3DB59A02292AB67E18
         * m_id : FAF8DDDF0B5A4CFF9C5B259D14C5DF17
         * type_id : C7A9A60BDB1B4FE986014CA7DA24A467
         * type_val : 1
         * line_id : 922FE5FE89D84D43ABFBF7261A10512F
         * make_status : 0
         * audit_status : 0
         * flow_record : null
         * plan_name : 2019年4月计划
         * month : 4
         * year : 2019
         * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
         * dep_name : 西固运维班
         * line_name : 桃开一线
         * type_name : 定期巡视
         */

        private String id;
        private String y_id;
        private String m_id;
        private String type_id;
        private String type_val;
        private String line_id;
        private String make_status;
        private String audit_status;
        private Object flow_record;
        private String plan_name;
        private int month;
        private int year;
        private String dep_id;
        private String dep_name;
        private String line_name;
        private String type_name;
        private String type_sign;
    private String voltage_level;

    public String getVoltage_level() {
        return voltage_level;
    }

    public void setVoltage_level(String voltage_level) {
        this.voltage_level = voltage_level;
    }

    public String getType_sign() {
        return type_sign;
    }

    public void setType_sign(String type_sign) {
        this.type_sign = type_sign;
    }

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getY_id() {
            return y_id;
        }

        public void setY_id(String y_id) {
            this.y_id = y_id;
        }

        public String getM_id() {
            return m_id;
        }

        public void setM_id(String m_id) {
            this.m_id = m_id;
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

        public String getMake_status() {
            return make_status;
        }

        public void setMake_status(String make_status) {
            this.make_status = make_status;
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

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
}
