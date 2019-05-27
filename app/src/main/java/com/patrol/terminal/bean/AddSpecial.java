package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class AddSpecial implements Serializable {

    /**
     * line_id : B511327CB4BB4D4A9E544F6972510B4E
     * wares : [{"end_id":"47267B13EA7D418FBFC1DD2E9278405C","end_sort":"","line_id":"B511327CB4BB4D4A9E544F6972510B4E","start_id":"B6F89FCEF9ED49C49CC31B1FEFA09449","start_sort":"2","towers":"#001-#002","wares_id":"4A2959A104244125A33946F1E2A91338"}]
     * wares_id : 4A2959A104244125A33946F1E2A91338
     */

    private static String line_id;
    private static String wares_id;
    private static List<WaresBean> wares;
    private static AddSpecial addSpecial;

    public static synchronized AddSpecial getInstance() {
        if (addSpecial == null) {
            addSpecial = new AddSpecial();
        }
        return addSpecial;
    }

    public static String getLine_id() {
        return line_id;
    }

    public static void setLine_id(String line_id) {
        AddSpecial.line_id = line_id;
    }

    public static String getWares_id() {
        return wares_id;
    }

    public static void setWares_id(String wares_id) {
        AddSpecial.wares_id = wares_id;
    }

    public static List<WaresBean> getWares() {
        return wares;
    }

    public static void setWares(List<WaresBean> wares) {
        AddSpecial.wares = wares;
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

        private String end_id;
        private String end_sort;
        private String line_id;
        private String start_id;
        private String start_sort;
        private String towers;
        private String wares_id;

        public WaresBean(String end_id, String end_sort, String line_id, String start_id, String start_sort, String towers, String wares_id) {
            this.end_id = end_id;
            this.end_sort = end_sort;
            this.line_id = line_id;
            this.start_id = start_id;
            this.start_sort = start_sort;
            this.towers = towers;
            this.wares_id = wares_id;
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

        public String getWares_id() {
            return wares_id;
        }

        public void setWares_id(String wares_id) {
            this.wares_id = wares_id;
        }
    }
}
