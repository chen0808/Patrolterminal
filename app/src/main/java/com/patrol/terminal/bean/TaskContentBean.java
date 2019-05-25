package com.patrol.terminal.bean;

import java.io.Serializable;

public class TaskContentBean implements Serializable {
    private String work_name;
    private String work_range;
    private String work_content;

    public TaskContentBean(String work_name, String work_range, String work_content) {
        this.work_name = work_name;
        this.work_range = work_range;
        this.work_content = work_content;
    }

    public String getWork_name() {
        return work_name;
    }

    public void setWork_name(String work_name) {
        this.work_name = work_name;
    }

    public String getWork_range() {
        return work_range;
    }

    public void setWork_range(String work_range) {
        this.work_range = work_range;
    }

    public String getWork_content() {
        return work_content;
    }

    public void setWork_content(String work_content) {
        this.work_content = work_content;
    }
}
