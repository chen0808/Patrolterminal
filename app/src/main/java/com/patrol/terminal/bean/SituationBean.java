package com.patrol.terminal.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SituationBean implements Serializable {
    private String id;
    private String task_id;
    private String unit;
    private String respon;
    private String start_time;
    private String end_time;
    private int year;
    private int month;
    private String remark;
    /**
     * month : 5
     * sysFile : {"content":"sign.jpg","data_id":"D7B4095B304D4483B489300C5ED57AD9","file_path":"/upload.folder/","file_size":"77523","file_type":"1","filename":"149f15b1-b76c-45f5-ab8c-99ebb1ab0761.jpg","id":"C22D6FE177ED4785BF3E3AD07AB21AE4","old_name":"sign.jpg","repair_type":"0"}
     * year : 2019
     */
    private SysFileBean sysFile;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public SysFileBean getSysFile() {
        return sysFile;
    }

    public void setSysFile(SysFileBean sysFile) {
        this.sysFile = sysFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRespon() {
        return respon;
    }

    public void setRespon(String respon) {
        this.respon = respon;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static class SysFileBean {
        /**
         * content : sign.jpg
         * data_id : D7B4095B304D4483B489300C5ED57AD9
         * file_path : /upload.folder/
         * file_size : 77523
         * file_type : 1
         * filename : 149f15b1-b76c-45f5-ab8c-99ebb1ab0761.jpg
         * id : C22D6FE177ED4785BF3E3AD07AB21AE4
         * old_name : sign.jpg
         * repair_type : 0
         */

        private String content;
        private String data_id;
        private String file_path;
        private String file_size;
        private String file_type;
        private String filename;
        @SerializedName("id")
        private String idX;
        private String old_name;
        private String repair_type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getData_id() {
            return data_id;
        }

        public void setData_id(String data_id) {
            this.data_id = data_id;
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

        public String getFile_type() {
            return file_type;
        }

        public void setFile_type(String file_type) {
            this.file_type = file_type;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getIdX() {
            return idX;
        }

        public void setIdX(String idX) {
            this.idX = idX;
        }

        public String getOld_name() {
            return old_name;
        }

        public void setOld_name(String old_name) {
            this.old_name = old_name;
        }

        public String getRepair_type() {
            return repair_type;
        }

        public void setRepair_type(String repair_type) {
            this.repair_type = repair_type;
        }
    }
}
