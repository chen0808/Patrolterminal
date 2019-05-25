package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class FourTicketBean implements Serializable {
    private String crew_id;
    private String repair_site;//抢修地点
    private String repair_head;//负责人
    private String repair_decorate;//布置人
    private String supplement_step;//补充安全措施
    private String licensor_repair_time;//许可抢修时间
    private String repair_end_time;//结束时间
    private String remark;//现场设备状况及保留的安全措施
    private String current_date;//填写时间
    private List<TaskContentBean> workList;//任务列表
    private List<FileListBean> fileList;//图片

    public String getCrew_id() {
        return crew_id;
    }

    public void setCrew_id(String crew_id) {
        this.crew_id = crew_id;
    }

    public String getRepair_head() {
        return repair_head;
    }

    public void setRepair_head(String repair_head) {
        this.repair_head = repair_head;
    }

    public List<TaskContentBean> getWorkList() {
        return workList;
    }

    public void setWorkList(List<TaskContentBean> workList) {
        this.workList = workList;
    }

    public String getSupplement_step() {
        return supplement_step;
    }

    public void setSupplement_step(String supplement_step) {
        this.supplement_step = supplement_step;
    }

    public List<FileListBean> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileListBean> fileList) {
        this.fileList = fileList;
    }

    public String getRepair_site() {
        return repair_site;
    }

    public void setRepair_site(String repair_site) {
        this.repair_site = repair_site;
    }


    public String getRepair_decorate() {
        return repair_decorate;
    }

    public void setRepair_decorate(String repair_decorate) {
        this.repair_decorate = repair_decorate;
    }

    public String getLicensor_repair_time() {
        return licensor_repair_time;
    }

    public void setLicensor_repair_time(String licensor_repair_time) {
        this.licensor_repair_time = licensor_repair_time;
    }

    public String getRepair_end_time() {
        return repair_end_time;
    }

    public void setRepair_end_time(String repair_end_time) {
        this.repair_end_time = repair_end_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
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
        private String data_id;
        private String filename;
        private String old_name;
        private String file_type;
        private String file_path;
        private String file_size;
        private String repair_type;
        private String content;

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