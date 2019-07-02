package com.patrol.terminal.bean;

import android.graphics.Bitmap;

import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.List;

public class PatrolContentBean extends BaseModel implements Serializable {

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

        private String category;
        private String remarks;
        private String id;
        private String name;
        private String task_id;
        private String patrol_id;
        private String status;
        private List<Bitmap> picList;

        public List<Bitmap> getPicList() {
            return picList;
        }

        public void setPicList(List<Bitmap> picList) {
            this.picList = picList;
        }

        public ValueBean(String id, String task_id, String patrol_id, String status) {
            this.id = id;
            this.task_id = task_id;
            this.patrol_id = patrol_id;
            this.status = status;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

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

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

        public String getPatrol_id() {
            return patrol_id;
        }

        public void setPatrol_id(String patrol_id) {
            this.patrol_id = patrol_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
