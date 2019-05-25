package com.patrol.terminal.bean;

public class ControlQualityBean {

    /**
     * id : 5230377F837240E2A70BB2D2A019C039
     * process : 核对现场
     * standard : 根据工作任务核对线路名称、编号、色标、杆塔号。
     * warning : 误登杆塔
     */

    private String id;
    private String process;
    private String standard;
    private String warning;
    private String type_id;
    private String work_standard_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getWork_standard_id() {
        return work_standard_id;
    }

    public void setWork_standard_id(String work_standard_id) {
        this.work_standard_id = work_standard_id;
    }
}
