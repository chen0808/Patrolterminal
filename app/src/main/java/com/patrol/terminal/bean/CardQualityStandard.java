package com.patrol.terminal.bean;


/**
 * 表名: CARD_QUALITY_STANDARD - 工序质量控制标准关系表
 *
 * Date：2019-05-30 20:05:16
 */
public class CardQualityStandard {

    // 数据id
    private String id;

    // 工序质量卡id
    private String card_quality_id;

    // 关键工序
    private String process;

    // 标准及要求
    private String standard;

    // 风险提醒
    private String warning;

    // 工序标准id
    private String card_standard_id;

    // 检查情况
    private String status;

    /*** 自定义字段 ***/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCard_quality_id() {
        return card_quality_id;
    }

    public void setCard_quality_id(String card_quality_id) {
        this.card_quality_id = card_quality_id;
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

    public String getCard_standard_id() {
        return card_standard_id;
    }

    public void setCard_standard_id(String card_standard_id) {
        this.card_standard_id = card_standard_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}