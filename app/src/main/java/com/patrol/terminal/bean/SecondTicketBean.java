package com.patrol.terminal.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SecondTicketBean implements Serializable {
    private String unit_id;
    private String ticket_number;
    private String leader_id;
    private String dep_id;
    private String crew_id;
    private String task_id;
    private String plan_s_time;
    private String plan_e_time;
    private String remark_safe;
    private String ticket_sign;
    private String ticket_leader_sign;
    private String remark_time_a;
    private String remark_time_b;
    private String remark_time_c;
    private String ticket_group_sign;
    private String work_a_time;
    private String work_b_time;
    private String delay_time;
    private String remark;
    private List<TaskContentBean> secWorkList;
    /**
     * id : BE5BF9C56D714E18B33A4BCB5C514F92
     * crew_id : null
     * plan_s_time : null
     * plan_e_time : null
     * remark_safe : null
     * ticket_sign : null
     * ticket_leader_sign : null
     * ticket_group_sign : null
     * delay_time : null
     * remark : null
     * type : 2
     * task_type : 4
     */

    private String id;
    private String type;
    private String task_type;
    private List<FileListBean> fileList;

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getTicket_number() {
        return ticket_number;
    }

    public void setTicket_number(String ticket_number) {
        this.ticket_number = ticket_number;
    }

    public String getLeader_id() {
        return leader_id;
    }

    public void setLeader_id(String leader_id) {
        this.leader_id = leader_id;
    }

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getCrew_id() {
        return crew_id;
    }

    public void setCrew_id(String crew_id) {
        this.crew_id = crew_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getPlan_s_time() {
        return plan_s_time;
    }

    public void setPlan_s_time(String plan_s_time) {
        this.plan_s_time = plan_s_time;
    }

    public String getPlan_e_time() {
        return plan_e_time;
    }

    public void setPlan_e_time(String plan_e_time) {
        this.plan_e_time = plan_e_time;
    }

    public List<TaskContentBean> getSecWorkList() {
        return secWorkList;
    }

    public void setSecWorkList(List<TaskContentBean> secWorkList) {
        this.secWorkList = secWorkList;
    }

    public String getRemark_safe() {
        return remark_safe;
    }

    public void setRemark_safe(String remark_safe) {
        this.remark_safe = remark_safe;
    }

    public String getTicket_sign() {
        return ticket_sign;
    }

    public void setTicket_sign(String ticket_sign) {
        this.ticket_sign = ticket_sign;
    }

    public String getTicket_leader_sign() {
        return ticket_leader_sign;
    }

    public void setTicket_leader_sign(String ticket_leader_sign) {
        this.ticket_leader_sign = ticket_leader_sign;
    }

    public String getRemark_time_a() {
        return remark_time_a;
    }

    public void setRemark_time_a(String remark_time_a) {
        this.remark_time_a = remark_time_a;
    }

    public String getRemark_time_b() {
        return remark_time_b;
    }

    public void setRemark_time_b(String remark_time_b) {
        this.remark_time_b = remark_time_b;
    }

    public String getRemark_time_c() {
        return remark_time_c;
    }

    public void setRemark_time_c(String remark_time_c) {
        this.remark_time_c = remark_time_c;
    }

    public String getTicket_group_sign() {
        return ticket_group_sign;
    }

    public void setTicket_group_sign(String ticket_group_sign) {
        this.ticket_group_sign = ticket_group_sign;
    }

    public String getWork_a_time() {
        return work_a_time;
    }

    public void setWork_a_time(String work_a_time) {
        this.work_a_time = work_a_time;
    }

    public String getWork_b_time() {
        return work_b_time;
    }

    public void setWork_b_time(String work_b_time) {
        this.work_b_time = work_b_time;
    }

    public String getDelay_time() {
        return delay_time;
    }

    public void setDelay_time(String delay_time) {
        this.delay_time = delay_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public List<FileListBean> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileListBean> fileList) {
        this.fileList = fileList;
    }

    public static class FileListBean implements Serializable {
        /**
         * id : D9ADB8CE4C7343D4ADA43F426771F9AF
         * data_id : BE5BF9C56D714E18B33A4BCB5C514F92
         * filename : 5defc3a8-4c04-44e8-9c37-0a9113b9b97a.jpg
         * old_name : aaa.jpg
         * file_type : 1
         * file_path : /upload.folder/
         * file_size : 17628
         * repair_type : 0
         * content : aaa.jpg
         */

        @SerializedName("id")
        private String idX;
        private String data_id;
        private String filename;
        private String old_name;
        private String file_type;
        private String file_path;
        private String file_size;
        private String repair_type;
        private String content;

        public String getIdX() {
            return idX;
        }

        public void setIdX(String idX) {
            this.idX = idX;
        }

        public String getData_id() {
            return data_id;
        }

        public void setData_id(String data_id) {
            this.data_id = data_id;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getOld_name() {
            return old_name;
        }

        public void setOld_name(String old_name) {
            this.old_name = old_name;
        }

        public String getFile_type() {
            return file_type;
        }

        public void setFile_type(String file_type) {
            this.file_type = file_type;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public String getFile_size() {
            return file_size;
        }

        public void setFile_size(String file_size) {
            this.file_size = file_size;
        }

        public String getRepair_type() {
            return repair_type;
        }

        public void setRepair_type(String repair_type) {
            this.repair_type = repair_type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
