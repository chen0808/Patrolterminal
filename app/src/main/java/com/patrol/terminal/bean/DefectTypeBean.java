package com.patrol.terminal.bean;

import java.io.Serializable;

public class DefectTypeBean implements Serializable {

    /**
     * id : 37E5647975394B1E952DC5D2796C7D73
     * code : yb
     * name : 一般
     * p_id : C8832ACCC1FB49DE818E05D29E1FABFA
     * sort : 0
     * detail : null
     * p_code : qxjb
     * p_name : 缺陷级别
     * full_name : \缺陷级别\一般
     * leaf : 1.0
     * leaf_total : 0.0
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
    private double leaf;
    private double leaf_total;

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

    public double getLeaf() {
        return leaf;
    }

    public void setLeaf(double leaf) {
        this.leaf = leaf;
    }

    public double getLeaf_total() {
        return leaf_total;
    }

    public void setLeaf_total(double leaf_total) {
        this.leaf_total = leaf_total;
    }
}
