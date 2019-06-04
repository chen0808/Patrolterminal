package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class PatrolContentBean implements Serializable {

    /**
     * name : 导线、地线
     * value : [{"CATEGORY":"B195E069014746E0ABD132148A869DE1","REMARKS":"（1）导线、地线锈蚀、断股、损伤或闪络烧伤\n","ID":"6E57D7EBDA464CEA8DB3CA2D1806C2EB","isDefect":"N","NAME":"导线、地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","REMARKS":"（2）导线、地线弧垂变化；导线、地线上扬、振动、舞动、脱冰跳跃\n","ID":"7AC64E2FC84342B891F4E642BE9ED2A9","isDefect":"N","NAME":"导线、地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","REMARKS":"（3）导线、地线接续金具过热、变色、变形、滑移；导线在线夹内滑动\n","ID":"09AC684BA72C411C83DC517AAF4998EE","isDefect":"N","NAME":"导线、地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","REMARKS":"（4）跳线断股、歪扭变形，跳线与杆塔空气间隙变化，跳线间扭绞；跳线舞动、摆动过大\n","ID":"8F0E5F4430A74238BACFC912386D64A3","isDefect":"N","NAME":"导线、地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","REMARKS":"（5）导线对地、对交叉跨越设施及对其他物体距离变化；导线、地线上悬挂有异物\n","ID":"EF16752C059F44B7B38237CD91C4C73A","isDefect":"N","NAME":"导线、地线"}]
     */

    private String name;
    private List<ValueBean> value;

    public PatrolContentBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class ValueBean {
        /**
         * CATEGORY : B195E069014746E0ABD132148A869DE1
         * REMARKS : （1）导线、地线锈蚀、断股、损伤或闪络烧伤
         * ID : 6E57D7EBDA464CEA8DB3CA2D1806C2EB
         * isDefect : N
         * NAME : 导线、地线
         */

        private String CATEGORY;
        private String REMARKS;
        private String ID;
        private String NAME;
        /**
         * STATUS : 0
         * RECODE_ID : 922E407DCE4340F88F923034999C5168
         */

        private String STATUS;
        private String RECODE_ID;

        public String getCATEGORY() {
            return CATEGORY;
        }

        public void setCATEGORY(String CATEGORY) {
            this.CATEGORY = CATEGORY;
        }

        public String getREMARKS() {
            return REMARKS;
        }

        public void setREMARKS(String REMARKS) {
            this.REMARKS = REMARKS;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }

        public String getRECODE_ID() {
            return RECODE_ID;
        }

        public void setRECODE_ID(String RECODE_ID) {
            this.RECODE_ID = RECODE_ID;
        }
    }
}
