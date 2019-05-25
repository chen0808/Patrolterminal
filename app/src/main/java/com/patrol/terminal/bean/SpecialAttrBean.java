package com.patrol.terminal.bean;

import java.io.Serializable;

public class SpecialAttrBean implements Serializable {

    /**
     * id : 26FECCD0F2974699867FCABA1460D92D
     * code : fws
     * name : 防污闪
     * p_id : CCC0504A0B964A2AB60296696E92383A
     * sort : 4
     * detail : null
     * level : 2
     */

    private String id;
    private String code;
    private String name;
    private String p_id;
    private int sort;
    private Object detail;
    private String isDanger;
    /**
     * remarks : null
     * p_name : null
     * full_name : null
     * leaf : null
     * leaf_total : null
     * level_no : 3
     */

    private int level_no;

    public String getIsDanger() {
        return isDanger;
    }

    public void setIsDanger(String isDanger) {
        this.isDanger = isDanger;
    }

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

    public int getLevel_no() {
        return level_no;
    }

    public void setLevel_no(int level_no) {
        this.level_no = level_no;
    }
}
