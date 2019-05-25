package com.patrol.terminal.bean;

public class DangerBean {

        /**
         * id : DB21C808C65B4C449824F0297946D3DC
         * task_id : 桃开一线031#
         * line_id : 922FE5FE89D84D43ABFBF7261A10512F
         * attr_id : 51EF54F89B6F48B69B98DA4FE84F1439
         * content : 违章建筑
         * find_time : 2019-04-02
         * deal_notes : 计划拆除
         * status : null
         * deal_unit : null
         * deal_time : 2019-04-25
         * attr_name : 高大建筑
         * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
         * dep_name : 西固运维班
         * line_name : 桃开一线
         * deal_unit_name : null
         * towers : null
         */

        private String id;
        private String task_id;
        private String line_id;
        private String attr_id;
        private String content;
        private String find_time;
        private String deal_notes;
        private Object status;
        private Object deal_unit;
        private String deal_time;
        private String attr_name;
        private String dep_id;
        private String dep_name;
        private int type;
        private String line_name;
        private Object deal_unit_name;
        private Object towers;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

        public String getLine_id() {
            return line_id;
        }

        public void setLine_id(String line_id) {
            this.line_id = line_id;
        }

        public String getAttr_id() {
            return attr_id;
        }

        public void setAttr_id(String attr_id) {
            this.attr_id = attr_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFind_time() {
            return find_time;
        }

        public void setFind_time(String find_time) {
            this.find_time = find_time;
        }

        public String getDeal_notes() {
            return deal_notes;
        }

        public void setDeal_notes(String deal_notes) {
            this.deal_notes = deal_notes;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getDeal_unit() {
            return deal_unit;
        }

        public void setDeal_unit(Object deal_unit) {
            this.deal_unit = deal_unit;
        }

        public String getDeal_time() {
            return deal_time;
        }

        public void setDeal_time(String deal_time) {
            this.deal_time = deal_time;
        }

        public String getAttr_name() {
            return attr_name;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
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

        public Object getDeal_unit_name() {
            return deal_unit_name;
        }

        public void setDeal_unit_name(Object deal_unit_name) {
            this.deal_unit_name = deal_unit_name;
        }

        public Object getTowers() {
            return towers;
        }

        public void setTowers(Object towers) {
            this.towers = towers;
        }
}
