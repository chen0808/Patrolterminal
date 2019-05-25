package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class ThirdTicketBean implements Serializable {
    private String crew_id;
    private String s_time;
    private String e_time;
    private String stop_reclose;//停用重合线路
    private String work_conditions;//工作条件
    private String sign_time;//签发人签名时间
    private String regulation_time;//许可人签名时间
    private String custody_man;//专责监护人
    private String supply_safety_measures;//补充安全措施
    private String final_regulation_time;//最终汇报调控许可人签名时间
    private String remark;//备注
    private List<TaskContentBean> workList;
    private List<FileListBean> fileList;

    public String getCrew_id() {
        return crew_id;
    }

    public void setCrew_id(String crew_id) {
        this.crew_id = crew_id;
    }

    public String getS_time() {
        return s_time;
    }

    public void setS_time(String s_time) {
        this.s_time = s_time;
    }

    public String getE_time() {
        return e_time;
    }

    public void setE_time(String e_time) {
        this.e_time = e_time;
    }

    public String getStop_reclose() {
        return stop_reclose;
    }

    public void setStop_reclose(String stop_reclose) {
        this.stop_reclose = stop_reclose;
    }

    public String getWork_conditions() {
        return work_conditions;
    }

    public void setWork_conditions(String work_conditions) {
        this.work_conditions = work_conditions;
    }

    public String getSign_time() {
        return sign_time;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }

    public String getRegulation_time() {
        return regulation_time;
    }

    public void setRegulation_time(String regulation_time) {
        this.regulation_time = regulation_time;
    }

    public String getCustody_man() {
        return custody_man;
    }

    public void setCustody_man(String custody_man) {
        this.custody_man = custody_man;
    }

    public String getSupply_safety_measures() {
        return supply_safety_measures;
    }

    public void setSupply_safety_measures(String supply_safety_measures) {
        this.supply_safety_measures = supply_safety_measures;
    }

    public String getFinal_regulation_time() {
        return final_regulation_time;
    }

    public void setFinal_regulation_time(String final_regulation_time) {
        this.final_regulation_time = final_regulation_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<TaskContentBean> getWorkList() {
        return workList;
    }

    public void setWorkList(List<TaskContentBean> workList) {
        this.workList = workList;
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