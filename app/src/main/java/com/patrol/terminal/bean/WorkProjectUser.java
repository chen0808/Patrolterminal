package com.patrol.terminal.bean;

import java.io.Serializable;

public class WorkProjectUser implements Serializable {
    private String id;
    private String u_id;
    private String w_p_id;
    private String w_c_id;
    private String task_id;
    private String work_project_name;

    public WorkProjectUser(String u_id, String w_p_id) {
        this.u_id = u_id;
        this.w_p_id = w_p_id;
    }

    public WorkProjectUser(String u_id) {
        this.u_id = u_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getW_p_id() {
        return w_p_id;
    }

    public void setW_p_id(String w_p_id) {
        this.w_p_id = w_p_id;
    }

    public String getW_c_id() {
        return w_c_id;
    }

    public void setW_c_id(String w_c_id) {
        this.w_c_id = w_c_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getWork_project_name() {
        return work_project_name;
    }

    public void setWork_project_name(String work_project_name) {
        this.work_project_name = work_project_name;
    }
}
