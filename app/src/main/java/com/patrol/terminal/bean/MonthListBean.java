package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class MonthListBean implements Serializable {

        private List<MonthPlanBean> patrol;
        private List<DefectBean> defect;
        private List<MonthPlanBean> electric;
       private List<MonthPlanBean> repair;

    public List<MonthPlanBean> getRepair() {
        return repair;
    }

    public void setRepair(List<MonthPlanBean> repair) {
        this.repair = repair;
    }

    public List<MonthPlanBean> getPatrol() {
            return patrol;
        }

        public void setPatrol(List<MonthPlanBean> patrol) {
            this.patrol = patrol;
        }

        public List<DefectBean> getDefect() {
            return defect;
        }

        public void setDefect(List<DefectBean> defect) {
            this.defect = defect;
        }

        public List<MonthPlanBean> getEle() {
            return electric;
        }

        public void setEle(List<MonthPlanBean> ele) {
            this.electric = ele;
        }

}
