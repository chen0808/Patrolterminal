package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ClassMemberBean implements Parcelable {
    

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
    private String detail;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean implements Parcelable {


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

        public UserListBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.name);
            dest.writeString(this.login);
            dest.writeString(this.password);
            dest.writeString(this.sex);
            dest.writeString(this.telephone);
            dest.writeString(this.birthday);
            dest.writeString(this.is_boss);
            dest.writeInt(this.sort);
            dest.writeString(this.detail);
            dest.writeString(this.session_id);
            dest.writeString(this.jobIds);
            dest.writeString(this.depIds);
            dest.writeString(this.roleIds);
            dest.writeString(this.sysJobList);
            dest.writeString(this.sysDepList);
            dest.writeString(this.sysRoleList);
        }

        protected UserListBean(Parcel in) {
            this.id = in.readString();
            this.name = in.readString();
            this.login = in.readString();
            this.password = in.readString();
            this.sex = in.readString();
            this.telephone = in.readString();
            this.birthday = in.readString();
            this.is_boss = in.readString();
            this.sort = in.readInt();
            this.detail = in.readString();
            this.session_id = in.readString();
            this.jobIds = in.readString();
            this.depIds = in.readString();
            this.roleIds = in.readString();
            this.sysJobList = in.readString();
            this.sysDepList = in.readString();
            this.sysRoleList = in.readString();
        }

        public static final Parcelable.Creator<UserListBean> CREATOR = new Parcelable.Creator<UserListBean>() {
            @Override
            public UserListBean createFromParcel(Parcel source) {
                return new UserListBean(source);
            }

            @Override
            public UserListBean[] newArray(int size) {
                return new UserListBean[size];
            }
        };
    }

    public ClassMemberBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.p_id);
        dest.writeString(this.is_work);
        dest.writeInt(this.sort);
        dest.writeString(this.detail);
        dest.writeTypedList(this.userList);
    }

    protected ClassMemberBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.p_id = in.readString();
        this.is_work = in.readString();
        this.sort = in.readInt();
        this.detail = in.readString();
        this.userList = in.createTypedArrayList(UserListBean.CREATOR);
    }

    public static final Parcelable.Creator<ClassMemberBean> CREATOR = new Parcelable.Creator<ClassMemberBean>() {
        @Override
        public ClassMemberBean createFromParcel(Parcel source) {
            return new ClassMemberBean(source);
        }

        @Override
        public ClassMemberBean[] newArray(int size) {
            return new ClassMemberBean[size];
        }
    };
}
