package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class OverhaulSendUserBean implements Serializable {

    /**
     * depIds : ["8FD97A30026F49D3913DC0120A039A9A"]
     * detail : 生产计划及生产指标管理
     * id : 94AE9C0BD55A43E49E47D12BD1F48391
     * is_boss : 0
     * jobIds : ["82C9AF3F59B84BFC8B7AD539CF46B389","D6C267AD4C93460E8A7EFA3A6E66AEAB"]
     * login : 王小龙
     * name : 王小龙
     * password : a0a22ac2958f9be136f3c320b6cb6a8b
     * roleIds : ["2E6AEA2554DE417EBCCCE962ED56E339","214FC278121E4E699EAA89F7294D6D14","A5ED93727DB94BCEBA5ADC04825097C0"]
     * sex : 1
     * sort : 52
     * sysDepList : [{"id":"8FD97A30026F49D3913DC0120A039A9A","is_work":"0","name":"综合组","p_id":"4A0F9178848A4480B73D7C8770D2BC98","sort":9}]
     * sysJobList : [{"detail":"运行专责","id":"82C9AF3F59B84BFC8B7AD539CF46B389","name":"运行专责","p_id":"8C647FB9B786424688890A579651AC78","sign":"yx_zz","sort":0},{"detail":"保电专责","id":"D6C267AD4C93460E8A7EFA3A6E66AEAB","name":"保电专责","p_id":"62A60AF2C9734F7AB4C662355524F851","sign":"bd_zz","sort":5}]
     * sysRoleList : [{"id":"2E6AEA2554DE417EBCCCE962ED56E339","name":"开发人员"},{"id":"214FC278121E4E699EAA89F7294D6D14","name":"主管"},{"id":"A5ED93727DB94BCEBA5ADC04825097C0","name":"副主管"}]
     */

    private String detail;
    private String id;
    private String is_boss;
    private String login;
    private String name;
    private String password;
    private String sex;
    private int sort;
    private List<String> depIds;
    private List<String> jobIds;
    private List<String> roleIds;
    private List<SysDepListBean> sysDepList;
    private List<SysJobListBean> sysJobList;
    private List<SysRoleListBean> sysRoleList;

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

    public List<String> getDepIds() {
        return depIds;
    }

    public void setDepIds(List<String> depIds) {
        this.depIds = depIds;
    }

    public List<String> getJobIds() {
        return jobIds;
    }

    public void setJobIds(List<String> jobIds) {
        this.jobIds = jobIds;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public List<SysDepListBean> getSysDepList() {
        return sysDepList;
    }

    public void setSysDepList(List<SysDepListBean> sysDepList) {
        this.sysDepList = sysDepList;
    }

    public List<SysJobListBean> getSysJobList() {
        return sysJobList;
    }

    public void setSysJobList(List<SysJobListBean> sysJobList) {
        this.sysJobList = sysJobList;
    }

    public List<SysRoleListBean> getSysRoleList() {
        return sysRoleList;
    }

    public void setSysRoleList(List<SysRoleListBean> sysRoleList) {
        this.sysRoleList = sysRoleList;
    }

    public static class SysDepListBean {
        /**
         * id : 8FD97A30026F49D3913DC0120A039A9A
         * is_work : 0
         * name : 综合组
         * p_id : 4A0F9178848A4480B73D7C8770D2BC98
         * sort : 9
         */

        private String id;
        private String is_work;
        private String name;
        private String p_id;
        private int sort;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIs_work() {
            return is_work;
        }

        public void setIs_work(String is_work) {
            this.is_work = is_work;
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
    }

    public static class SysJobListBean {
        /**
         * detail : 运行专责
         * id : 82C9AF3F59B84BFC8B7AD539CF46B389
         * name : 运行专责
         * p_id : 8C647FB9B786424688890A579651AC78
         * sign : yx_zz
         * sort : 0
         */

        private String detail;
        private String id;
        private String name;
        private String p_id;
        private String sign;
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

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }

    public static class SysRoleListBean {
        /**
         * id : 2E6AEA2554DE417EBCCCE962ED56E339
         * name : 开发人员
         */

        private String id;
        private String name;

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
    }
}
