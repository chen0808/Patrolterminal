package com.patrol.terminal.bean;

import java.io.Serializable;

public class DefectListBean implements Serializable {

    /**
     * id : 37966DC516FB46FA9635741D32409F78
     * category : A5D9B34EEA45485EBD50B788F607AC64
     * grade : 37E5647975394B1E952DC5D2796C7D73
     * content : 杆塔基础混凝土表面有较大面积水泥脱落、蜂窝、露石或麻面。
     * category_name : 杆塔基础
     * grade_name : 一般
     */

    private String id;
    private String category;
    private String grade;
    private String content;
    private String category_name;
    private String grade_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }
}
