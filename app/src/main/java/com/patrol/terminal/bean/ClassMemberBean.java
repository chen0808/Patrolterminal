package com.patrol.terminal.bean;

import java.util.List;

public class ClassMemberBean {


    /**
     * id : B7FF21A674F144DE8D13EB8B3B79E64F
     * name : 带电作业班
     * p_id : 4A0F9178848A4480B73D7C8770D2BC98
     * is_work : 0
     * sort : 3
     * detail : null
     * userList : [{"id":"F9C9737CD8DF427586D96C5A9A9795A1","name":"贾来强","login":"贾来强","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":"1965-12-30","is_boss":"0","sort":21,"detail":null,"session_id":null,"jobIds":null,"depIds":null,"roleIds":null,"sysJobList":null,"sysDepList":null,"sysRoleList":null},{"id":"110B2AB3A1A24F5DB34E1B87E556E956","name":"施宇新","login":"施宇新","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":"1991-12-30","is_boss":"0","sort":18,"detail":null,"session_id":null,"jobIds":null,"depIds":null,"roleIds":null,"sysJobList":null,"sysDepList":null,"sysRoleList":null},{"id":"C83B74B70A794DD1BCD6528246CCE229","name":"刘博通","login":"刘博通","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":"1965-12-30","is_boss":"0","sort":20,"detail":null,"session_id":null,"jobIds":null,"depIds":null,"roleIds":null,"sysJobList":null,"sysDepList":null,"sysRoleList":null},{"id":"3621BE00F0E54AE6818A22DA5E94FD53","name":"段维全","login":"段维全","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":null,"is_boss":"0","sort":120,"detail":null,"session_id":null,"jobIds":null,"depIds":null,"roleIds":null,"sysJobList":null,"sysDepList":null,"sysRoleList":null},{"id":"C55FE53CAB524DE3A5F93737F40E747F","name":"安泰康","login":"安泰康","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":null,"is_boss":"0","sort":121,"detail":null,"session_id":null,"jobIds":null,"depIds":null,"roleIds":null,"sysJobList":null,"sysDepList":null,"sysRoleList":null},{"id":"74513137BF7E40CBA94C33CEF4FA3481","name":"顾哲屹","login":"顾哲屹","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":"1965-12-30","is_boss":"0","sort":22,"detail":null,"session_id":null,"jobIds":null,"depIds":null,"roleIds":null,"sysJobList":null,"sysDepList":null,"sysRoleList":null},{"id":"219C5BCF0D2E4A5689C13266F15CA871","name":"郭靖波","login":"郭靖波","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":"1965-12-30","is_boss":"0","sort":25,"detail":null,"session_id":null,"jobIds":null,"depIds":null,"roleIds":null,"sysJobList":null,"sysDepList":null,"sysRoleList":null},{"id":"D2F326C8BBF34F78A872DACDE87A0355","name":"李小东","login":"李小东","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":null,"is_boss":"0","sort":123,"detail":null,"session_id":null,"jobIds":null,"depIds":null,"roleIds":null,"sysJobList":null,"sysDepList":null,"sysRoleList":null},{"id":"75DD0E45CCC54226AD2693122169F0D8","name":"王立春","login":"王立春","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":null,"is_boss":"0","sort":124,"detail":null,"session_id":null,"jobIds":null,"depIds":null,"roleIds":null,"sysJobList":null,"sysDepList":null,"sysRoleList":null},{"id":"8085FCC8ACC84B5C82F2B8ADE40DB279","name":"李嘉辰","login":"李嘉辰","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":null,"is_boss":"0","sort":122,"detail":null,"session_id":null,"jobIds":null,"depIds":null,"roleIds":null,"sysJobList":null,"sysDepList":null,"sysRoleList":null}]
     */

    private String id;
    private String name;
    private String p_id;
    private String is_work;
    private int sort;
    private Object detail;
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

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getIs_work() {
        return is_work;
    }

    public void setIs_work(String is_work) {
        this.is_work = is_work;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean {
        /**
         * id : F9C9737CD8DF427586D96C5A9A9795A1
         * name : 贾来强
         * login : 贾来强
         * password : a0a22ac2958f9be136f3c320b6cb6a8b
         * sex : 1
         * telephone : null
         * birthday : 1965-12-30
         * is_boss : 0
         * sort : 21
         * detail : null
         * session_id : null
         * jobIds : null
         * depIds : null
         * roleIds : null
         * sysJobList : null
         * sysDepList : null
         * sysRoleList : null
         */

        private String id;
        private String name;
        private String login;
        private String password;
        private String sex;
        private Object telephone;
        private String birthday;
        private String is_boss;
        private int sort;
        private Object detail;
        private Object session_id;
        private Object jobIds;
        private Object depIds;
        private Object roleIds;
        private Object sysJobList;
        private Object sysDepList;
        private Object sysRoleList;

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

        public Object getTelephone() {
            return telephone;
        }

        public void setTelephone(Object telephone) {
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

        public Object getDetail() {
            return detail;
        }

        public void setDetail(Object detail) {
            this.detail = detail;
        }

        public Object getSession_id() {
            return session_id;
        }

        public void setSession_id(Object session_id) {
            this.session_id = session_id;
        }

        public Object getJobIds() {
            return jobIds;
        }

        public void setJobIds(Object jobIds) {
            this.jobIds = jobIds;
        }

        public Object getDepIds() {
            return depIds;
        }

        public void setDepIds(Object depIds) {
            this.depIds = depIds;
        }

        public Object getRoleIds() {
            return roleIds;
        }

        public void setRoleIds(Object roleIds) {
            this.roleIds = roleIds;
        }

        public Object getSysJobList() {
            return sysJobList;
        }

        public void setSysJobList(Object sysJobList) {
            this.sysJobList = sysJobList;
        }

        public Object getSysDepList() {
            return sysDepList;
        }

        public void setSysDepList(Object sysDepList) {
            this.sysDepList = sysDepList;
        }

        public Object getSysRoleList() {
            return sysRoleList;
        }

        public void setSysRoleList(Object sysRoleList) {
            this.sysRoleList = sysRoleList;
        }
    }
}
