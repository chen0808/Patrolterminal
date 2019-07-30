package com.patrol.terminal.bean;

public class DepUserBean {


        /**
         * id : 8A91869C4484411F8A20884246F1B4CE
         * name : 邢海涛
         * login : 邢海涛
         * password : a0a22ac2958f9be136f3c320b6cb6a8b
         * sex : 1
         * telephone : null
         * birthday : 1998-12-30
         * is_boss : 0
         * sort : 12
         * detail : null
         * session_id : null
         * jobIds : null
         * depIds : null
         * roleIds : null
         * sysJobList : null
         * sysDepList : null
         * sysRoleList : null
         *
         */

        private String id;
         private String user_id;
        private String name;
        private String user_name;
        private String sign;
        private String login;
        private String password;
        private String sex;
        private String telephone;
        private String birthday;
        private String is_boss;
        private int sort;
        private String detail;
        private String session_id;
        private String jobIds;
        private String depIds;
        private String roleIds;
        private String sysJobList;
        private String sysDepList;
        private String sysRoleList;
        private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
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

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getIs_boss() {
            return is_boss;
        }

        public void setIs_boss(String is_boss) {
            this.is_boss = is_boss;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }

        public String getJobIds() {
            return jobIds;
        }

        public void setJobIds(String jobIds) {
            this.jobIds = jobIds;
        }

        public String getDepIds() {
            return depIds;
        }

        public void setDepIds(String depIds) {
            this.depIds = depIds;
        }

        public String getRoleIds() {
            return roleIds;
        }

        public void setRoleIds(String roleIds) {
            this.roleIds = roleIds;
        }

        public String getSysJobList() {
            return sysJobList;
        }

        public void setSysJobList(String sysJobList) {
            this.sysJobList = sysJobList;
        }

        public String getSysDepList() {
            return sysDepList;
        }

        public void setSysDepList(String sysDepList) {
            this.sysDepList = sysDepList;
        }

        public String getSysRoleList() {
            return sysRoleList;
        }

        public void setSysRoleList(String sysRoleList) {
            this.sysRoleList = sysRoleList;
        }
}
