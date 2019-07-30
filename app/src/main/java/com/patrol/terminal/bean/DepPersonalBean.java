package com.patrol.terminal.bean;

import java.util.List;

public class DepPersonalBean {

        /**
         * id : BBE359C6F29042A1A5AF96F9C1B68ED8
         * name : 西固运维班
         * p_id : 4A0F9178848A4480B73D7C8770D2BC98
         * is_work : 1
         * sort : 4
         * detail : null
         * userList : [{"id":"9E4FAE63CAFF4A1EB5C888EF91978C7B","name":"刘福德","login":"刘福德","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":"1997-12-30","is_boss":"0","dep_id":"BBE359C6F29042A1A5AF96F9C1B68ED8","sort":11,"detail":null,"session_id":null,"dep_name":null,"jobIds":null,"roleIds":null,"sysJobList":null,"sysRoleList":null},{"id":"593279A75ED34E258FCD4ADE09E177A4","name":"祁浩","login":"祁浩","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":"1999-12-30","is_boss":"0","dep_id":"BBE359C6F29042A1A5AF96F9C1B68ED8","sort":13,"detail":null,"session_id":null,"dep_name":null,"jobIds":null,"roleIds":null,"sysJobList":null,"sysRoleList":null},{"id":"65D41CF8F90F4614AB7BA7B71A0FE0F3","name":"马克俭","login":"马克俭","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":null,"is_boss":"0","dep_id":"BBE359C6F29042A1A5AF96F9C1B68ED8","sort":93,"detail":"外聘","session_id":null,"dep_name":null,"jobIds":null,"roleIds":null,"sysJobList":null,"sysRoleList":null},{"id":"52570197B5F94A09A9A9965A7296E9A8","name":"祁丽鑫","login":"祁丽鑫","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":null,"is_boss":"0","dep_id":"BBE359C6F29042A1A5AF96F9C1B68ED8","sort":90,"detail":"外聘","session_id":null,"dep_name":null,"jobIds":null,"roleIds":null,"sysJobList":null,"sysRoleList":null},{"id":"0A270142F03241DBAAE505D7C232CE9E","name":"林栋","login":"林栋","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":"2000-12-30","is_boss":"0","dep_id":"BBE359C6F29042A1A5AF96F9C1B68ED8","sort":16,"detail":null,"session_id":null,"dep_name":null,"jobIds":null,"roleIds":null,"sysJobList":null,"sysRoleList":null},{"id":"9CF4DCD383474DAC89F0D0C9DCC8071B","name":"邓贵宝","login":"邓贵宝","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":"13546497548","birthday":"1965-12-30","is_boss":"0","dep_id":"BBE359C6F29042A1A5AF96F9C1B68ED8","sort":8,"detail":null,"session_id":null,"dep_name":null,"jobIds":null,"roleIds":null,"sysJobList":null,"sysRoleList":null},{"id":"8A91869C4484411F8A20884246F1B4CE","name":"邢海涛","login":"邢海涛","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":"1998-12-30","is_boss":"0","dep_id":"BBE359C6F29042A1A5AF96F9C1B68ED8","sort":12,"detail":null,"session_id":null,"dep_name":null,"jobIds":null,"roleIds":null,"sysJobList":null,"sysRoleList":null},{"id":"4B01F91D1E10479BA898DE45023CF25B","name":"刘海生","login":"刘海生","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":"11321212445","birthday":"1990-12-30","is_boss":"0","dep_id":"BBE359C6F29042A1A5AF96F9C1B68ED8","sort":14,"detail":null,"session_id":null,"dep_name":null,"jobIds":null,"roleIds":null,"sysJobList":null,"sysRoleList":null},{"id":"50C48A7050644F2CB782B6DE7233D95D","name":"赵建新","login":"赵建新","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":"1965-12-30","is_boss":"0","dep_id":"BBE359C6F29042A1A5AF96F9C1B68ED8","sort":15,"detail":null,"session_id":null,"dep_name":null,"jobIds":null,"roleIds":null,"sysJobList":null,"sysRoleList":null},{"id":"1EC657173FB54789B9912A2CAF471A2E","name":"胡作铸","login":"胡作铸","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"1","telephone":null,"birthday":null,"is_boss":"0","dep_id":"BBE359C6F29042A1A5AF96F9C1B68ED8","sort":92,"detail":"外聘","session_id":null,"dep_name":null,"jobIds":null,"roleIds":null,"sysJobList":null,"sysRoleList":null},{"id":"0B9EAADA3DBD4317B4CF80BFA309587C","name":"蒋秀珍","login":"蒋秀珍","password":"a0a22ac2958f9be136f3c320b6cb6a8b","sex":"0","telephone":null,"birthday":null,"is_boss":"0","dep_id":"BBE359C6F29042A1A5AF96F9C1B68ED8","sort":91,"detail":"外聘","session_id":null,"dep_name":null,"jobIds":null,"roleIds":null,"sysJobList":null,"sysRoleList":null}]
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
             * id : 9E4FAE63CAFF4A1EB5C888EF91978C7B
             * name : 刘福德
             * login : 刘福德
             * password : a0a22ac2958f9be136f3c320b6cb6a8b
             * sex : 1
             * telephone : null
             * birthday : 1997-12-30
             * is_boss : 0
             * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
             * sort : 11
             * detail : null
             * session_id : null
             * dep_name : null
             * jobIds : null
             * roleIds : null
             * sysJobList : null
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
            private String dep_id;
            private int sort;
            private Object detail;
            private Object session_id;
            private Object dep_name;
            private Object jobIds;
            private Object roleIds;
            private Object sysJobList;
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

            public String getDep_id() {
                return dep_id;
            }

            public void setDep_id(String dep_id) {
                this.dep_id = dep_id;
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

            public Object getDep_name() {
                return dep_name;
            }

            public void setDep_name(Object dep_name) {
                this.dep_name = dep_name;
            }

            public Object getJobIds() {
                return jobIds;
            }

            public void setJobIds(Object jobIds) {
                this.jobIds = jobIds;
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

            public Object getSysRoleList() {
                return sysRoleList;
            }

            public void setSysRoleList(Object sysRoleList) {
                this.sysRoleList = sysRoleList;
            }
        }
}
