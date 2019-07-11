package com.patrol.terminal.bean;

public class PatrolRecordPicBean {
    // 主键id
    private String id;

    // 任务id
    private String task_id;

    // 缺陷id
    private String task_defect_id;

    // 上传时间
    private String upload_time;

    // 文件名
    private String filename;

    // 文件路径
    private String file_path;

    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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

    public String getTask_defect_id() {
        return task_defect_id;
    }

    public void setTask_defect_id(String task_defect_id) {
        this.task_defect_id = task_defect_id;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
