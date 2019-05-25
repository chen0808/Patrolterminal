package com.patrol.terminal.bean;

public class EqTower {


        /**
         * id : 5443020B60CB4B1891F5CE03B6C9219E
         * line_id : F3BA53A0C28E4EEC9D6DB821CDAAA6EC
         * towers : 001#-012#
         * start_id : 3F2C3152D4C24D26BE8AC57FFC6C47C5
         * end_id : 9043687B557B4E488CE2FB8DBE6A0A1E
         * remarks : null
         * line_name : 桃南线
         * start_name : 桃南线001#
         * end_name : 桃南线012#
         */

        private String id;
          private String tower_id;
        private String line_id;
        private String towers;
        private String start_id;
        private String end_id;
        private Object remarks;
        private String line_name;
        private String start_name;
        private String end_name;

    public String getTower_id() {
        return tower_id;
    }

    public void setTower_id(String tower_id) {
        this.tower_id = tower_id;
    }

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLine_id() {
            return line_id;
        }

        public void setLine_id(String line_id) {
            this.line_id = line_id;
        }

        public String getTowers() {
            return towers;
        }

        public void setTowers(String towers) {
            this.towers = towers;
        }

        public String getStart_id() {
            return start_id;
        }

        public void setStart_id(String start_id) {
            this.start_id = start_id;
        }

        public String getEnd_id() {
            return end_id;
        }

        public void setEnd_id(String end_id) {
            this.end_id = end_id;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public String getLine_name() {
            return line_name;
        }

        public void setLine_name(String line_name) {
            this.line_name = line_name;
        }

        public String getStart_name() {
            return start_name;
        }

        public void setStart_name(String start_name) {
            this.start_name = start_name;
        }

        public String getEnd_name() {
            return end_name;
        }

        public void setEnd_name(String end_name) {
            this.end_name = end_name;
        }

}
