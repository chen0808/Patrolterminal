package com.patrol.terminal.bean;

import java.util.List;

public class TrainingTaskBean {


    /**
     * id : 506748CF68FC4215A229EC83EECDA2B1
     * name : 计划名字5
     * start_time : 2019年05月16日 07时26分
     * end_time : 2019年05月16日 09时26分
     * train_days : 0
     * train_level : 15F90C926703441780F3708865580510
     * host_unit : 单位5
     * train_place : 对对对
     * type_id : 98AA734E9B074C3B8438240BFECBC4C2
     * teacher : 天天
     * year : 2019
     * month : 5
     * status : 0
     * remark : 发发发
     * plan_train_id : 62E504406FFA4181A19C068F3EC980F3
     * train_val : 1
     * content : null
     * userList : [{"id":"226858491AF34C50941AE96E000C298C","task_train_id":"506748CF68FC4215A229EC83EECDA2B1","user_id":"453131E2BBFA4B98A76303E6982B65D9","name":null,"start_time":null,"end_time":null,"train_days":null,"train_level":null,"host_unit":null,"train_place":null,"type_id":null,"teacher":null,"year":null,"month":null,"status":null,"remark":null,"plan_train_id":"62E504406FFA4181A19C068F3EC980F3","train_val":"1","user_name":"王宁","train_level_name":null,"type_name":null},{"id":"AC412948AE9C45DA80ACC776C98AFF4D","task_train_id":"506748CF68FC4215A229EC83EECDA2B1","user_id":"D8EFF1AE952A4D9C9F11BBACDC14EC45","name":null,"start_time":null,"end_time":null,"train_days":null,"train_level":null,"host_unit":null,"train_place":null,"type_id":null,"teacher":null,"year":null,"month":null,"status":null,"remark":null,"plan_train_id":"62E504406FFA4181A19C068F3EC980F3","train_val":"1","user_name":"李全录","train_level_name":null,"type_name":null}]
     * train_level_name : 本单位
     * type_name : 专项计划
     */

    private String id;
    private String name;
    private String start_time;
    private String end_time;
    private int train_days;
    private String train_level;
    private String host_unit;
    private String train_place;
    private String type_id;
    private String teacher;
    private int year;
    private int month;
    private String status;
    private String remark;
    private String plan_train_id;
    private String train_val;
    private Object content;
    private String train_level_name;
    private String type_name;
    private List<UserListBean> userList;

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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getTrain_days() {
        return train_days;
    }

    public void setTrain_days(int train_days) {
        this.train_days = train_days;
    }

    public String getTrain_level() {
        return train_level;
    }

    public void setTrain_level(String train_level) {
        this.train_level = train_level;
    }

    public String getHost_unit() {
        return host_unit;
    }

    public void setHost_unit(String host_unit) {
        this.host_unit = host_unit;
    }

    public String getTrain_place() {
        return train_place;
    }

    public void setTrain_place(String train_place) {
        this.train_place = train_place;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPlan_train_id() {
        return plan_train_id;
    }

    public void setPlan_train_id(String plan_train_id) {
        this.plan_train_id = plan_train_id;
    }

    public String getTrain_val() {
        return train_val;
    }

    public void setTrain_val(String train_val) {
        this.train_val = train_val;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getTrain_level_name() {
        return train_level_name;
    }

    public void setTrain_level_name(String train_level_name) {
        this.train_level_name = train_level_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean {
        /**
         * id : 226858491AF34C50941AE96E000C298C
         * task_train_id : 506748CF68FC4215A229EC83EECDA2B1
         * user_id : 453131E2BBFA4B98A76303E6982B65D9
         * name : null
         * start_time : null
         * end_time : null
         * train_days : null
         * train_level : null
         * host_unit : null
         * train_place : null
         * type_id : null
         * teacher : null
         * year : null
         * month : null
         * status : null
         * remark : null
         * plan_train_id : 62E504406FFA4181A19C068F3EC980F3
         * train_val : 1
         * user_name : 王宁
         * train_level_name : null
         * type_name : null
         */

        private String id;
        private String task_train_id;
        private String user_id;
        private Object name;
        private Object start_time;
        private Object end_time;
        private Object train_days;
        private Object train_level;
        private Object host_unit;
        private Object train_place;
        private Object type_id;
        private Object teacher;
        private Object year;
        private Object month;
        private Object status;
        private Object remark;
        private String plan_train_id;
        private String train_val;
        private String user_name;
        private Object train_level_name;
        private Object type_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTask_train_id() {
            return task_train_id;
        }

        public void setTask_train_id(String task_train_id) {
            this.task_train_id = task_train_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getStart_time() {
            return start_time;
        }

        public void setStart_time(Object start_time) {
            this.start_time = start_time;
        }

        public Object getEnd_time() {
            return end_time;
        }

        public void setEnd_time(Object end_time) {
            this.end_time = end_time;
        }

        public Object getTrain_days() {
            return train_days;
        }

        public void setTrain_days(Object train_days) {
            this.train_days = train_days;
        }

        public Object getTrain_level() {
            return train_level;
        }

        public void setTrain_level(Object train_level) {
            this.train_level = train_level;
        }

        public Object getHost_unit() {
            return host_unit;
        }

        public void setHost_unit(Object host_unit) {
            this.host_unit = host_unit;
        }

        public Object getTrain_place() {
            return train_place;
        }

        public void setTrain_place(Object train_place) {
            this.train_place = train_place;
        }

        public Object getType_id() {
            return type_id;
        }

        public void setType_id(Object type_id) {
            this.type_id = type_id;
        }

        public Object getTeacher() {
            return teacher;
        }

        public void setTeacher(Object teacher) {
            this.teacher = teacher;
        }

        public Object getYear() {
            return year;
        }

        public void setYear(Object year) {
            this.year = year;
        }

        public Object getMonth() {
            return month;
        }

        public void setMonth(Object month) {
            this.month = month;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getPlan_train_id() {
            return plan_train_id;
        }

        public void setPlan_train_id(String plan_train_id) {
            this.plan_train_id = plan_train_id;
        }

        public String getTrain_val() {
            return train_val;
        }

        public void setTrain_val(String train_val) {
            this.train_val = train_val;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public Object getTrain_level_name() {
            return train_level_name;
        }

        public void setTrain_level_name(Object train_level_name) {
            this.train_level_name = train_level_name;
        }

        public Object getType_name() {
            return type_name;
        }

        public void setType_name(Object type_name) {
            this.type_name = type_name;
        }
    }
}
