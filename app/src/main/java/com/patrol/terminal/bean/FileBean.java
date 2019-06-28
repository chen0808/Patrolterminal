package com.patrol.terminal.bean;

import java.io.Serializable;

public class FileBean implements Serializable {

    /**
     * id : 6AC88B105ED4461B8B641D886818A654
     * data_id : C17CA35B660143879909A071E3FD94FD
     * filename : 902ffb2e-baeb-4c0a-b5a5-6593e78ba1e6.jpg
     * old_name : 5.jpg
     * file_type : 1
     * file_path : /upload.folder/
     * file_size : 46512
     * repair_type : 0
     * content : 5.jpg
     * upload_time : 2019-06-28
     */

    private String id;
    private String data_id;
    private String filename;
    private String old_name;
    private String file_type;
    private String file_path;
    private String file_size;
    private String repair_type;
    private String content;
    private String upload_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }
}
