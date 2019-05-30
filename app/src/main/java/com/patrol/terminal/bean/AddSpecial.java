package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class AddSpecial implements Serializable {

    /**
     * line_id : B511327CB4BB4D4A9E544F6972510B4E
     * wares : [{"end_id":"47267B13EA7D418FBFC1DD2E9278405C","end_sort":"","line_id":"B511327CB4BB4D4A9E544F6972510B4E","start_id":"B6F89FCEF9ED49C49CC31B1FEFA09449","start_sort":"2","towers":"#001-#002","wares_id":"4A2959A104244125A33946F1E2A91338"}]
     * wares_id : 4A2959A104244125A33946F1E2A91338
     */

    private String line_id;
    private List<WaresBean> wares;
    private List<String> waresIdList;

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public List<WaresBean> getWares() {
        return wares;
    }

    public void setWares(List<WaresBean> wares) {
        this.wares = wares;
    }

    public List<String> getWaresIdList() {
        return waresIdList;
    }

    public void setWaresIdList(List<String> waresIdList) {
        this.waresIdList = waresIdList;
    }

    public static class WaresBean {
        /**
         * end_id : 47267B13EA7D418FBFC1DD2E9278405C
         * end_sort :
         * line_id : B511327CB4BB4D4A9E544F6972510B4E
         * start_id : B6F89FCEF9ED49C49CC31B1FEFA09449
         * start_sort : 2
         * towers : #001-#002
         * wares_id : 4A2959A104244125A33946F1E2A91338
         */
        private String line_id;
        private String towers;
        private String start_id;
        private String end_id;
        private String start_sort;
        private String end_sort;

        public WaresBean(String end_id, String end_sort, String line_id, String start_id, String start_sort, String towers) {
            this.end_id = end_id;
            this.end_sort = end_sort;
            this.line_id = line_id;
            this.start_id = start_id;
            this.start_sort = start_sort;
            this.towers = towers;
        }

        public String getEnd_id() {
            return end_id;
        }

        public void setEnd_id(String end_id) {
            this.end_id = end_id;
        }

        public String getEnd_sort() {
            return end_sort;
        }

        public void setEnd_sort(String end_sort) {
            this.end_sort = end_sort;
        }

        public String getLine_id() {
            return line_id;
        }

        public void setLine_id(String line_id) {
            this.line_id = line_id;
        }

        public String getStart_id() {
            return start_id;
        }

        public void setStart_id(String start_id) {
            this.start_id = start_id;
        }

        public String getStart_sort() {
            return start_sort;
        }

        public void setStart_sort(String start_sort) {
            this.start_sort = start_sort;
        }

        public String getTowers() {
            return towers;
        }

        public void setTowers(String towers) {
            this.towers = towers;
        }
    }
}
