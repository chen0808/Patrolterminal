//package com.patrol.terminal.bean;
//
//import com.chad.library.adapter.base.entity.MultiItemEntity;
//import com.patrol.terminal.adapter.WeekPlanDetailAdapter;
//
//import java.util.List;
//
//public class ExpandItem2 implements MultiItemEntity {
//
//    /**
//     * eqLine : {"id":"636DB6A63D60409393C55A2C6D14894A","line_no":"1120","name":"桃开二线"}
//     * eqTowerList : [{"lat":"36.11740743","longitude":"103.7198516","name":"桃开二线003#"},{"lat":"36.1177838","longitude":"103.7234819","name":"桃开二线005#"}]
//     */
//
//    private EqLineBean eqLine;
//    private List<EqTowerListBean> eqTowerList;
//
//    @Override
//    public int getItemType() {
//        return WeekPlanDetailAdapter.TYPE_LEVEL_2;
//    }
//
//    public EqLineBean getEqLine() {
//        return eqLine;
//    }
//
//    public void setEqLine(EqLineBean eqLine) {
//        this.eqLine = eqLine;
//    }
//
//    public List<EqTowerListBean> getEqTowerList() {
//        return eqTowerList;
//    }
//
//    public void setEqTowerList(List<EqTowerListBean> eqTowerList) {
//        this.eqTowerList = eqTowerList;
//    }
//
//
//    public static class EqLineBean {
//        /**
//         * id : 636DB6A63D60409393C55A2C6D14894A
//         * line_no : 1120
//         * name : 桃开二线
//         */
//
//        private String id;
//        private String line_no;
//        private String name;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getLine_no() {
//            return line_no;
//        }
//
//        public void setLine_no(String line_no) {
//            this.line_no = line_no;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//    }
//
//    public static class EqTowerListBean {
//
//        /**
//         * lat : 36.11740743
//         * longitude : 103.7198516
//         * name : 桃开二线003#
//         */
//
//        private String lat;
//        private String longitude;
//        private String name;
//
//        public String getLat() {
//            return lat;
//        }
//
//        public void setLat(String lat) {
//            this.lat = lat;
//        }
//
//        public String getLongitude() {
//            return longitude;
//        }
//
//        public void setLongitude(String longitude) {
//            this.longitude = longitude;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//    }
//}
