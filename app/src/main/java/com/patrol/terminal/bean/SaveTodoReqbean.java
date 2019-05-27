package com.patrol.terminal.bean;

public class SaveTodoReqbean {
    private  String id;
    private  String audit_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }
}
