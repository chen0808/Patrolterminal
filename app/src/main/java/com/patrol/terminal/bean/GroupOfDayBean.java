package com.patrol.terminal.bean;

import java.util.List;

public class GroupOfDayBean {

        private List<PatrolBean> patrol;
        private List<DefectBean> defect;
        private List<DefectBean> danger;


        public List<PatrolBean> getPatrol() {
            return patrol;
        }

        public void setPatrol(List<PatrolBean> patrol) {
            this.patrol = patrol;
        }

        public List<DefectBean> getDefect() {
            return defect;
        }

        public void setDefect(List<DefectBean> defect) {
            this.defect = defect;
        }

        public List<DefectBean> getDanger() {
            return danger;
        }

        public void setDanger(List<DefectBean> danger) {
            this.danger = danger;
        }

        public static class PatrolBean {
            /**
             * id : F7B452AB63C74EF196FCBB804B5A5BEA
             * week_line_id : 98AC40EAF2F344F8A1F6A19A8CC6F242
             * day_line_id : 81D9C511128648F0A1B9A143815F1399
             * line_id : 06CD39FC7726400F92C00D4C89C80F1C
             * line_name : 桃郑线
             * name : #001-#038
             * tower_id : null
             * towers_id : 5EA2AB0F591442839BD285A9B0C1A941
             * tower_type : 1
             * done_status : 0
             * done_time : null
             */

            private String id;
            private String week_line_id;
            private String day_line_id;
            private String line_id;
            private String line_name;
            private String name;
            private String type_id;
            private String type_val;
            private String type_name;
            private String tower_id;
            private String towers_id;
            private String tower_type;
            private String done_status;
            private String done_time;

            public String getType_val() {
                return type_val;
            }

            public void setType_val(String type_val) {
                this.type_val = type_val;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTower_id() {
                return tower_id;
            }

            public void setTower_id(String tower_id) {
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

            public String getDone_time() {
                return done_time;
            }

            public void setDone_time(String done_time) {
                this.done_time = done_time;
            }
        }

}
