package com.patrol.terminal.adapter;

public class SaveCheckReqBean {
    private String id,audit_status,check_report;

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

    public String getCheck_report() {
        return check_report;
    }

    public void setCheck_report(String check_report) {
        this.check_report = check_report;
    }
}
