package com.patrol.terminal.bean;

public class DayOfWeekBean {


        /**
         * id : C5A2664825B04EB8A506089F116FCE1A
         * week_line_id : 8345072D28074D14860F8AAA4894E419
         * name : #033-#055
         * tower_id : null
         * towers_id : E9628556482F4AF281DD4D6B9DC5E9EC
         * tower_type : 1
         * done_status : 0
         * done_time : null
         * line_id : CB97B4C5B9FB41738EF3BE64EEC5C802
         * line_name : 南刘线
         */

        private String id;
        private String week_line_id;
    private String month_line_id;
        private String name;
        private Object tower_id;
        private String towers_id;
        private String tower_type;
        private String done_status;
        private Object done_time;
        private String line_id;
        private String line_name;

    public String getMonth_line_id() {
        return month_line_id;
    }

    public void setMonth_line_id(String month_line_id) {
        this.month_line_id = month_line_id;
    }

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public Object getTower_id() {
            return tower_id;
        }

        public void setTower_id(Object tower_id) {
            this.tower_id = tower_id;
        }

        public String getTowers_id() {
            return towers_id;
        }

        public void setTowers_id(String towers_id) {
            this.towers_id = towers_id;
        }

        public String getTower_type() {
            return tower_type;
        }

        public void setTower_type(String tower_type) {
            this.tower_type = tower_type;
        }

        public String getDone_status() {
            return done_status;
        }

        public void setDone_status(String done_status) {
            this.done_status = done_status;
        }

        public Object getDone_time() {
            return done_time;
        }

        public void setDone_time(Object done_time) {
            this.done_time = done_time;
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

}
