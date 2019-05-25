package com.patrol.terminal.bean;

import java.io.Serializable;

public class GetToPostCheckBean implements Serializable {

    /**
     * id : 2984BAF9E84D47C2ABFBBD18244DFBDE
     * check_id : 1
     * check_content : 现场作业内容是否和作业计划一致，工作票所列安全措施是否满足作业要求并与现场一致。
     * task_position_id : 2984BAF9E84D47C2ABFBBD18244DFBDE
     */

    private String id;
    private String check_id;
    private String check_content;
    private String task_position_id;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheck_id() {
        return check_id;
    }

    public void setCheck_id(String check_id) {
        this.check_id = check_id;
    }

    public String getCheck_content() {
        return check_content;
    }

    public void setCheck_content(String check_content) {
        this.check_content = check_content;
    }

    public String getTask_position_id() {
        return task_position_id;
    }

    public void setTask_position_id(String task_position_id) {
        this.task_position_id = task_position_id;
    }
}
