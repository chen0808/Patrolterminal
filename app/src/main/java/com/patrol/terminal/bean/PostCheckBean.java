package com.patrol.terminal.bean;

import java.io.Serializable;

public class PostCheckBean implements Serializable {
    private String task_position_id;
    private String status;
    private String task_id;

    public PostCheckBean(String task_position_id, String status, String task_id) {
        this.task_position_id = task_position_id;
        this.status = status;
        this.task_id = task_id;
    }

    public String getTask_position_id() {
        return task_position_id;
    }

    public void setTask_position_id(String task_position_id) {
        this.task_position_id = task_position_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }
}
