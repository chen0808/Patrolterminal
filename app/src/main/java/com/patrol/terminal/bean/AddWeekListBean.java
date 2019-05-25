package com.patrol.terminal.bean;

import java.util.List;

public class AddWeekListBean {

        /**
         * line_name : 桃郑线
         * line_id : 06CD39FC7726400F92C00D4C89C80F1C
         * towers : [{"id":"5EA2AB0F591442839BD285A9B0C1A941","line_id":"06CD39FC7726400F92C00D4C89C80F1C","towers":"#001-#038","start_id":"CB07BCAE1869460985102E979E2066E2","end_id":"1396BF2D10F042918C163E7F6541BE49","start_sort":null,"end_sort":null,"remarks":"与桃建西线1#-37#同塔架设","line_towers":null,"towerList":null}]
         */

        private String line_name;
        private String line_id;
        private List<TowersBean> towers;

        public String getLine_name() {
            return line_name;
        }

        public void setLine_name(String line_name) {
            this.line_name = line_name;
        }

        public String getLine_id() {
            return line_id;
        }

        public void setLine_id(String line_id) {
            this.line_id = line_id;
        }

        public List<TowersBean> getTowers() {
            return towers;
        }

        public void setTowers(List<TowersBean> towers) {
            this.towers = towers;
        }

        public static class TowersBean {
            /**
             * id : 5EA2AB0F591442839BD285A9B0C1A941
             * line_id : 06CD39FC7726400F92C00D4C89C80F1C
             * towers : #001-#038
             * start_id : CB07BCAE1869460985102E979E2066E2
             * end_id : 1396BF2D10F042918C163E7F6541BE49
             * start_sort : null
             * end_sort : null
             * remarks : 与桃建西线1#-37#同塔架设
             * line_towers : null
             * towerList : null
             */

            private String id;
            private String line_id;
            private String towers;
            private String start_id;
            private String end_id;
            private Object start_sort;
            private Object end_sort;
            private String remarks;
            private Object line_towers;
            private Object towerList;
            private boolean ischeck;

            public boolean isIscheck() {
                return ischeck;
            }

            public void setIscheck(boolean ischeck) {
                this.ischeck = ischeck;
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

            public Object getStart_sort() {
                return start_sort;
            }

            public void setStart_sort(Object start_sort) {
                this.start_sort = start_sort;
            }

            public Object getEnd_sort() {
                return end_sort;
            }

            public void setEnd_sort(Object end_sort) {
                this.end_sort = end_sort;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public Object getLine_towers() {
                return line_towers;
            }

            public void setLine_towers(Object line_towers) {
                this.line_towers = line_towers;
            }

            public Object getTowerList() {
                return towerList;
            }

            public void setTowerList(Object towerList) {
                this.towerList = towerList;
            }
        }
}
