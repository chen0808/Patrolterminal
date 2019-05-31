package com.patrol.terminal.bean;

import java.io.Serializable;

public class ControlCardStandard implements Serializable {

    /**
     * id : BE612878582F484CAAF1874B182C1FC7
     * process : 安装杆号牌
     * standard : 作业人员不得站在作业处的垂直下，方高空作业区内不得有无关人员通行或逗留。
     * warning : 高空坠落、落物伤人、触电
     * sort : null
     * card_quality_id : BE612878582F484CAAF1874B182C1FC7
     */

    private String id;
    private String process;
    private String standard;
    private String warning;
    private Object sort;
    private String card_quality_id;
    private boolean tag;

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

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

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public String getCard_quality_id() {
        return card_quality_id;
    }

    public void setCard_quality_id(String card_quality_id) {
        this.card_quality_id = card_quality_id;
    }
}
