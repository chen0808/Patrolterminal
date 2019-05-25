package com.patrol.terminal.bean;

import java.util.List;

/*专责分发给保电,安全,验收专责,班长*/
public class OverhaulZZSendBean {
    private String id;
    private String week_audit_status;
    // 保电专责id
    private String ele_user_id;
    // 保电专责名称
    private String ele_user_name;
    // 安全专责id
    private String safe_user_id;
    // 安全专责名称
    private String safe_user_name;
    // 验收专责id
    private String check_user_id;
    // 验收专责名称
    private String check_user_name;
    private String is_ele;
    private String userId1;
    private String userName1;
    private String userId2;
    private String userName2;
    private List<TaskInfo> taskList;

    public String getIs_ele() {
        return is_ele;
    }

    public void setIs_ele(String is_ele) {
        this.is_ele = is_ele;
    }

    public String getSafe_user_id() {
        return safe_user_id;
    }

    public void setSafe_user_id(String safe_user_id) {
        this.safe_user_id = safe_user_id;
    }

    public String getSafe_user_name() {
        return safe_user_name;
    }

    public void setSafe_user_name(String safe_user_name) {
        this.safe_user_name = safe_user_name;
    }

    public String getCheck_user_id() {
        return check_user_id;
    }

    public void setCheck_user_id(String check_user_id) {
        this.check_user_id = check_user_id;
    }

    public String getCheck_user_name() {
        return check_user_name;
    }

    public void setCheck_user_name(String check_user_name) {
        this.check_user_name = check_user_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeek_audit_status() {
        return week_audit_status;
    }

    public void setWeek_audit_status(String week_audit_status) {
        this.week_audit_status = week_audit_status;
    }

    public String getEle_user_id() {
        return ele_user_id;
    }

    public void setEle_user_id(String ele_user_id) {
        this.ele_user_id = ele_user_id;
    }

    public String getEle_user_name() {
        return ele_user_name;
    }

    public void setEle_user_name(String ele_user_name) {
        this.ele_user_name = ele_user_name;
    }

    public String getUserId1() {
        return userId1;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }

    public String getUserName1() {
        return userName1;
    }

    public void setUserName1(String userName1) {
        this.userName1 = userName1;
    }

    public String getUserId2() {
        return userId2;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
    }

    public String getUserName2() {
        return userName2;
    }

    public void setUserName2(String userName2) {
        this.userName2 = userName2;
    }

    public List<TaskInfo> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskInfo> taskList) {
        this.taskList = taskList;
    }

    public static class TaskInfo{
        private String task_content;
        private String task_status;
        private String userId3;
        private String userName3;
        private String year;
        private String month;
        private String week;

        public String getTask_content() {
            return task_content;
        }

        public void setTask_content(String task_content) {
            this.task_content = task_content;
        }

        public String getTask_status() {
            return task_status;
        }

        public void setTask_status(String task_status) {
            this.task_status = task_status;
        }

        public String getUserId3() {
            return userId3;
        }

        public void setUserId3(String userId3) {
            this.userId3 = userId3;
        }

        public String getUserName3() {
            return userName3;
        }

        public void setUserName3(String userName3) {
            this.userName3 = userName3;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }
    }

}
