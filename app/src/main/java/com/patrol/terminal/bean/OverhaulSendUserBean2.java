package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class OverhaulSendUserBean2 implements Serializable {

    /**
     * id : 6CBFE0670D0B4095B0D921EE55F25450
     * name : 行政综合专责
     * userList : [{"detail":"行政事务综合","id":"8B6B236C27014A4CA93C8CE99EAA55E6","is_boss":"0","login":"刘昭君","name":"刘昭君","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"0","sort":51}]
     */

    private String id;
    private String name;
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

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean {
        /**
         * detail : 行政事务综合
         * id : 8B6B236C27014A4CA93C8CE99EAA55E6
         * is_boss : 0
         * login : 刘昭君
         * name : 刘昭君
         * password : a0a22ac2958f9be136f3c320b6cb6a8b
         * sex : 0
         * sort : 51
         */

        private String detail;
        private String id;
        private String is_boss;
        private String login;
        private String name;
        private String password;
        private String sex;
        private int sort;

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIs_boss() {
            return is_boss;
        }

        public void setIs_boss(String is_boss) {
            this.is_boss = is_boss;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
