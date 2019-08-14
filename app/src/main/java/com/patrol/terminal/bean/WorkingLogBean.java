package com.patrol.terminal.bean;

import java.io.Serializable;

public class WorkingLogBean implements Serializable {

    private String project_name;
    private String working_name;
    private String content;
    private String report_name;
    private String time;
    private int status;

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getWorking_name() {
        return working_name;
    }

    public void setWorking_name(String working_name) {
        this.working_name = working_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
