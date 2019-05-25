package com.patrol.terminal.bean;

import java.util.List;

public class AddressBookBean {


    /**
     * id : D8EFF1AE952A4D9C9F11BBACDC14EC45
     * name : 李全录
     * login : 李全录
     * password : a0a22ac2958f9be136f3c320b6cb6a8b
     * sex : 1
     * telephone : null
     * birthday : null
     * is_boss : 0
     * sort : 80
     * detail : null
     * session_id : null
     * jobs : []
     * groups : []
     * units : ["00C8BDB1D8734710967A6834B3C6F5EE"]
     * depList : [{"id":"DB374C08562A4F838258EE1F21B48DE6","user_id":"D8EFF1AE952A4D9C9F11BBACDC14EC45","dep_id":"00C8BDB1D8734710967A6834B3C6F5EE","dep_name":"兰州运维班"}]
     * roles : ["6913325C8A6F4AB2B90F27428DD952AF"]
     * roleList : [{"id":"BCA3BA98ACBB4FF081DE72A791D61DC5","user_id":"D8EFF1AE952A4D9C9F11BBACDC14EC45","role_id":"6913325C8A6F4AB2B90F27428DD952AF","role_name":"组员"}]
     */

    private String id;
    private String name;
    private String login;
    private String password;
    private String sex;
    private Object telephone;
    private Object birthday;
    private String is_boss;
    private int sort;
    private Object detail;
    private Object session_id;
    private List<?> jobs;
    private List<?> groups;
    private List<String> units;
    private List<DepListBean> depList;
    private List<String> roles;
    private List<RoleListBean> roleList;

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

    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
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

    public List<?> getJobs() {
        return jobs;
    }

    public void setJobs(List<?> jobs) {
        this.jobs = jobs;
    }

    public List<?> getGroups() {
        return groups;
    }

    public void setGroups(List<?> groups) {
        this.groups = groups;
    }

    public List<String> getUnits() {
        return units;
    }

    public void setUnits(List<String> units) {
        this.units = units;
    }

    public List<DepListBean> getDepList() {
        return depList;
    }

    public void setDepList(List<DepListBean> depList) {
        this.depList = depList;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<RoleListBean> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleListBean> roleList) {
        this.roleList = roleList;
    }

    public static class DepListBean {
        /**
         * id : DB374C08562A4F838258EE1F21B48DE6
         * user_id : D8EFF1AE952A4D9C9F11BBACDC14EC45
         * dep_id : 00C8BDB1D8734710967A6834B3C6F5EE
         * dep_name : 兰州运维班
         */

        private String id;
        private String user_id;
        private String dep_id;
        private String dep_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getDep_id() {
            return dep_id;
        }

        public void setDep_id(String dep_id) {
            this.dep_id = dep_id;
        }

        public String getDep_name() {
            return dep_name;
        }

        public void setDep_name(String dep_name) {
            this.dep_name = dep_name;
        }
    }

    public static class RoleListBean {
        /**
         * id : BCA3BA98ACBB4FF081DE72A791D61DC5
         * user_id : D8EFF1AE952A4D9C9F11BBACDC14EC45
         * role_id : 6913325C8A6F4AB2B90F27428DD952AF
         * role_name : 组员
         */

        private String id;
        private String user_id;
        private String role_id;
        private String role_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }
    }
}
