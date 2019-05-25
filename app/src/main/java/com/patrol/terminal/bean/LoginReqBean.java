package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class LoginReqBean implements Serializable {

    /**
     * id : 94AE9C0BD55A43E49E47D12BD1F48391
     * name : 王小龙
     * login : 王小龙
     * password : a0a22ac2958f9be136f3c320b6cb6a8b
     * sex : 1
     * telephone : null
     * birthday : null
     * is_boss : 0
     * sort : 52
     * detail : 生产计划及生产指标管理
     * session_id : null
     * jobIds : ["82C9AF3F59B84BFC8B7AD539CF46B389"]
     * depIds : ["8FD97A30026F49D3913DC0120A039A9A"]
     * roleIds : ["2E6AEA2554DE417EBCCCE962ED56E339","214FC278121E4E699EAA89F7294D6D14","A5ED93727DB94BCEBA5ADC04825097C0"]
     * sysJobList : [{"id":"82C9AF3F59B84BFC8B7AD539CF46B389","name":"运行专责","p_id":"8C647FB9B786424688890A579651AC78","sort":0,"detail":"运行专责","sign":"yx_zz"}]
     * sysDepList : [{"id":"8FD97A30026F49D3913DC0120A039A9A","name":"综合组","p_id":"4A0F9178848A4480B73D7C8770D2BC98","is_work":"0","sort":10,"detail":null}]
     * sysRoleList : [{"id":"2E6AEA2554DE417EBCCCE962ED56E339","name":"开发人员","sort":null,"detail":null},{"id":"214FC278121E4E699EAA89F7294D6D14","name":"主管","sort":null,"detail":null},{"id":"A5ED93727DB94BCEBA5ADC04825097C0","name":"副主管","sort":null,"detail":null}]
     */

    private String id;
    private String name;
    private String login;
    private String password;
    private String sex;
    private String telephone;
    private String birthday;
    private String is_boss;
    private String dep_id;
    private String dep_name;
    private int sort;
    private String detail;
    private String session_id;
    private List<String> jobIds;
    private List<String> depIds;
    private List<String> roleIds;
    private List<SysJobListBean> sysJobList;
    private List<SysDepListBean> sysDepList;
    private List<SysRoleListBean> sysRoleList;

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

    public List<String> getJobIds() {
        return jobIds;
    }

    public void setJobIds(List<String> jobIds) {
        this.jobIds = jobIds;
    }

    public List<String> getDepIds() {
        return depIds;
    }

    public void setDepIds(List<String> depIds) {
        this.depIds = depIds;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public List<SysJobListBean> getSysJobList() {
        return sysJobList;
    }

    public void setSysJobList(List<SysJobListBean> sysJobList) {
        this.sysJobList = sysJobList;
    }

    public List<SysDepListBean> getSysDepList() {
        return sysDepList;
    }

    public void setSysDepList(List<SysDepListBean> sysDepList) {
        this.sysDepList = sysDepList;
    }

    public List<SysRoleListBean> getSysRoleList() {
        return sysRoleList;
    }

    public void setSysRoleList(List<SysRoleListBean> sysRoleList) {
        this.sysRoleList = sysRoleList;
    }

    public static class SysJobListBean {
        /**
         * id : 82C9AF3F59B84BFC8B7AD539CF46B389
         * name : 运行专责
         * p_id : 8C647FB9B786424688890A579651AC78
         * sort : 0
         * detail : 运行专责
         * sign : yx_zz
         */

        private String id;
        private String name;
        private String p_id;
        private int sort;
        private String detail;
        private String sign;

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

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }

    public static class SysDepListBean {
        /**
         * id : 8FD97A30026F49D3913DC0120A039A9A
         * name : 综合组
         * p_id : 4A0F9178848A4480B73D7C8770D2BC98
         * is_work : 0
         * sort : 10
         * detail : null
         */

        private String id;
        private String name;
        private String p_id;
        private String is_work;
        private int sort;
        private String detail;

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

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }

    public static class SysRoleListBean {
        /**
         * id : 2E6AEA2554DE417EBCCCE962ED56E339
         * name : 开发人员
         * sort : null
         * detail : null
         */

        private String id;
        private String name;
        private String sort;
        private String detail;

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

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}
