package com.patrol.terminal.bean;

public class SaveTodoReqbean {
    private  String data_id;
    private  String audit_status;

    public String getTask_id() {
        return data_id;
    }

    public void setTask_id(String task_id) {
        this.data_id = task_id;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }
}
