package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class GradeBean implements Serializable {

    /**
     * name : 危急
     * value : [{"CATEGORY":"B195E069014746E0ABD132148A869DE1","GRADE_NAME":"危急","GRADE":"10C639F13341484997EE8D955322BE02","ID":"00BFB68710E94C5298A1F04AF33C5512","CONTENT":"导线本体及引流线上钢芯断股、损伤截面超过铝股或合金股总面积25%","CATEGORY_NAME":"导地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","GRADE_NAME":"危急","GRADE":"10C639F13341484997EE8D955322BE02","ID":"2882654434A74DDAAC7A534686F7BA33","CONTENT":"导线本体子导线鞭击严重","CATEGORY_NAME":"导地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","GRADE_NAME":"危急","GRADE":"10C639F13341484997EE8D955322BE02","ID":"C9879F7DBB6D4B81B2DA57C731E7CF3E","CONTENT":"导线本体子导线断线未造成事故","CATEGORY_NAME":"导地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","GRADE_NAME":"危急","GRADE":"10C639F13341484997EE8D955322BE02","ID":"5C6285C085754D7A9022449FF579EE2F","CONTENT":"普通地线钢绞线7股断2股及以上、19股断3股及以上","CATEGORY_NAME":"导地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","GRADE_NAME":"危急","GRADE":"10C639F13341484997EE8D955322BE02","ID":"C2D55F0C2D3D48728EC3AF24AB534538","CONTENT":"普通地线铝包钢、钢芯铝绞线、铝合金绞线钢芯断股或断股截面超过铝股或合金股总面积25%","CATEGORY_NAME":"导地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","GRADE_NAME":"危急","GRADE":"10C639F13341484997EE8D955322BE02","ID":"ACA3DC2CB54C43BD83F9E351B6B4813C","CONTENT":"普通地线有异物悬挂，危及安全运行","CATEGORY_NAME":"导地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","GRADE_NAME":"危急","GRADE":"10C639F13341484997EE8D955322BE02","ID":"D666E16845ED4844AC8E25291DC8587F","CONTENT":"OPGW光缆损伤截面积超过总面积17%或光纤单元未损伤","CATEGORY_NAME":"导地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","GRADE_NAME":"危急","GRADE":"10C639F13341484997EE8D955322BE02","ID":"26C9C552228841259EB6F195D197D6D1","CONTENT":"OPGW光缆上有异物悬挂，危及安全运行","CATEGORY_NAME":"导地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","GRADE_NAME":"危急","GRADE":"10C639F13341484997EE8D955322BE02","ID":"219A23AF61FA4256A6296CFE583DE601","CONTENT":"导线本体及引流线上覆冰严重，危急设备运行，需立即处理","CATEGORY_NAME":"导地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","GRADE_NAME":"危急","GRADE":"10C639F13341484997EE8D955322BE02","ID":"5B20A6777A6B411D8A21DB27D700AB7E","CONTENT":"导线本体子导线扭绞","CATEGORY_NAME":"导地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","GRADE_NAME":"危急","GRADE":"10C639F13341484997EE8D955322BE02","ID":"8A79659C116148858078EBCA5B574D78","CONTENT":"导线本体及引流线上有异物悬挂，危及安全运行","CATEGORY_NAME":"导地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","GRADE_NAME":"危急","GRADE":"10C639F13341484997EE8D955322BE02","ID":"CBC3EFA2D91E40AE9FC0D360CD3C732C","CONTENT":"普通地线断线未造成事故","CATEGORY_NAME":"导地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","GRADE_NAME":"危急","GRADE":"10C639F13341484997EE8D955322BE02","ID":"2F0B533D813B450480B1BEEAB97114A1","CONTENT":"OPGW光缆引下线松散，对带电体不满足安全距离","CATEGORY_NAME":"导地线"},{"CATEGORY":"B195E069014746E0ABD132148A869DE1","GRADE_NAME":"危急","GRADE":"10C639F13341484997EE8D955322BE02","ID":"C2DA9D1A0AAB4D19802487D75154F6E4","CONTENT":"OPGW光缆断股截面积超过总面积17%或光纤单元未损伤","CATEGORY_NAME":"导地线"}]
     */

    private String name;
    private List<ValueBean> value;

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
         * GRADE_NAME : 危急
         * GRADE : 10C639F13341484997EE8D955322BE02
         * ID : 00BFB68710E94C5298A1F04AF33C5512
         * CONTENT : 导线本体及引流线上钢芯断股、损伤截面超过铝股或合金股总面积25%
         * CATEGORY_NAME : 导地线
         */

        private String CATEGORY;
        private String GRADE_NAME;
        private String GRADE;
        private String ID;
        private String CONTENT;
        private String CATEGORY_NAME;

        public String getCATEGORY() {
            return CATEGORY;
        }

        public void setCATEGORY(String CATEGORY) {
            this.CATEGORY = CATEGORY;
        }

        public String getGRADE_NAME() {
            return GRADE_NAME;
        }

        public void setGRADE_NAME(String GRADE_NAME) {
            this.GRADE_NAME = GRADE_NAME;
        }

        public String getGRADE() {
            return GRADE;
        }

        public void setGRADE(String GRADE) {
            this.GRADE = GRADE;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getCONTENT() {
            return CONTENT;
        }

        public void setCONTENT(String CONTENT) {
            this.CONTENT = CONTENT;
        }

        public String getCATEGORY_NAME() {
            return CATEGORY_NAME;
        }

        public void setCATEGORY_NAME(String CATEGORY_NAME) {
            this.CATEGORY_NAME = CATEGORY_NAME;
        }
    }
}
