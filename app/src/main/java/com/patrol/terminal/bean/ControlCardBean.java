package com.patrol.terminal.bean;

import java.io.Serializable;

public class ControlCardBean implements Serializable {

    /**
     * id : EBC649446E0B4CEA94466B6EBC3BA7B8
     * code : azspjk
     * name : 安装视频监控
     * p_id : 09DEF19017154AC891A139845190AA52
     * sort : 0
     * detail : null
     * p_code : kzk
     * p_name : 班组作业控制卡类型
     * full_name : \班组作业控制卡类型\安装视频监控
     * leaf : 1
     * leaf_total : 0
     */

    private String id;
    private String code;
    private String name;
    private String p_id;
    private int sort;
    private Object detail;
    private String p_code;
    private String p_name;
    private String full_name;
    private int leaf;
    private int leaf_total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getLeaf() {
        return leaf;
    }

    public void setLeaf(int leaf) {
        this.leaf = leaf;
    }

    public int getLeaf_total() {
        return leaf_total;
    }

    public void setLeaf_total(int leaf_total) {
        this.leaf_total = leaf_total;
    }
}
