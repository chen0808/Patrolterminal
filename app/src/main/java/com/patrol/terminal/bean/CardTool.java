package com.patrol.terminal.bean;


/**
 * 表名: CARD_TOOL - 工器具材料标准配置表
 *
 * Date：2019-05-30 20:06:38
 */
public class CardTool {

    // 数据id
    private String id;

    // 工器具表id
    private String tool_id;

    // 工器具名称
    private String tool_name;

    // 检修任务id
    private String task_repair_id;

    // 型号/规格
    private String type;

    // 单位
    private String unit;

    // 数量
    private String total;

    // 备注
    private String remark;

    // （0：工器具，1：材料）
    private String tool_type;

    /*** 自定义字段 ***/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTool_id() {
        return tool_id;
    }

    public void setTool_id(String tool_id) {
        this.tool_id = tool_id;
    }

    public String getTool_name() {
        return tool_name;
    }

    public void setTool_name(String tool_name) {
        this.tool_name = tool_name;
    }

    public String getTask_repair_id() {
        return task_repair_id;
    }

    public void setTask_repair_id(String task_repair_id) {
        this.task_repair_id = task_repair_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTool_type() {
        return tool_type;
    }

    public void setTool_type(String tool_type) {
        this.tool_type = tool_type;
    }
}