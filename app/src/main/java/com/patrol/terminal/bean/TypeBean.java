package com.patrol.terminal.bean;

public class TypeBean {

        /**
         * id : 76914522BE0F4B36B87221A4F19F9AA5
         * name : 红外测温
         * val : 2
         * sign : 5
         * sort : 5
         * eqTowers : null
         */

        private String id;
        private String name;
        private String val;
        private String sign;
        private int sort;
        private Object eqTowers;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public Object getEqTowers() {
            return eqTowers;
        }

        public void setEqTowers(Object eqTowers) {
            this.eqTowers = eqTowers;
        }
}
