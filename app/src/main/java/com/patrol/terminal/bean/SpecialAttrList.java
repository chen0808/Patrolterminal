package com.patrol.terminal.bean;

import java.io.Serializable;

public class SpecialAttrList implements Serializable {

    /**
     * id : 98F764466504450DBC4C490F6DB512C2
     * name : 八防
     * p_id : 0
     * sort : 1
     * remarks : null
     * level_no : 1
     * p_name : null
     * full_name : null
     * leaf : null
     * leaf_total : null
     */

    private String id;
    private String name;
    private String p_id;
    private int sort;
    private String remarks;
    private int level_no;
    private String p_name;
    private String full_name;
    private String leaf;
    private String leaf_total;

    public SpecialAttrList(String id, String remarks) {
        this.id = id;
        this.remarks = remarks;
    }

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getLevel_no() {
        return level_no;
    }

    public void setLevel_no(int level_no) {
        this.level_no = level_no;
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

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public String getLeaf_total() {
        return leaf_total;
    }

    public void setLeaf_total(String leaf_total) {
        this.leaf_total = leaf_total;
    }
}
