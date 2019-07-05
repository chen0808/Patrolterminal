package com.patrol.terminal.bean;

import java.io.Serializable;

public class TssxToFile implements Serializable {

//            id":"4680BA17569C458581225D7140B85E39",
//            data_id":"51E05F9967BF4C099E6F2E55F8656103",
//            filename":"7bf4d6da-261a-456e-abd2-d1c165ed9040.jpg",
//            old_name":"/storage/emulated/0/MyPhoto/2019-04-10_1554908226588.jpg",
//            file_type":"1",
//            file_path":"/upload.folder/",
//            file_size":"10294",
//            repair_type":"0",
//            content":"/storage/emulated/0/MyPhoto/2019-04-10_1554908226588.jpg",
//            upload_time 2019-07-04;
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
