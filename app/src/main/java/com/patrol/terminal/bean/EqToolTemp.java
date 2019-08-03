package com.patrol.terminal.bean;


/**
 * 表名: EQ_TOOL_TEMP - 临时工器具表
 *
 * Date：2019-07-29 09:52:01
 */
public class EqToolTemp {

    // 数据id
    private String id;

    // 名称
    private String name;

    // 型号/规格
    private String type;

    // 单位
    private String unit;

    // 备注
    private String remarks;

    /*** 自定义字段 ***/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}