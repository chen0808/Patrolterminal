package com.patrol.terminal.bean;

public class FieldAntiInspectionBean {
    private String id;
    private String illegal;
    private String check_content;
    private String illegal_type;
    private String score;
    private String task_illegal_id;
    private String check_user;
    private String task_id;

    public FieldAntiInspectionBean(String task_illegal_id, String check_user, String task_id) {
        this.task_illegal_id = task_illegal_id;
        this.check_user = check_user;
        this.task_id = task_id;
    }

    public String getCheck_user() {
        return check_user;
    }

    public void setCheck_user(String check_user) {
        this.check_user = check_user;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIllegal() {
        return illegal;
    }

    public void setIllegal(String illegal) {
        this.illegal = illegal;
    }

    public String getCheck_content() {
        return check_content;
    }

    public void setCheck_content(String check_content) {
        this.check_content = check_content;
    }

    public String getIllegal_type() {
        return illegal_type;
    }

    public void setIllegal_type(String illegal_type) {
        this.illegal_type = illegal_type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTask_illegal_id() {
        return task_illegal_id;
    }

    public void setTask_illegal_id(String task_illegal_id) {
        this.task_illegal_id = task_illegal_id;
    }
}
