package com.patrol.terminal.bean;

import java.io.Serializable;

public class DefectFragmentBean2 implements Serializable {

    /**
     * start_name : #006
     * find_time : 2017-02-28
     * category_name : 杆塔基础
     * line_name : 1120新金二线
     * end_name : #006
     * id : 77ED74D5FC4442A086BF64D137A5637B
     * content : C塔腿外侧0.5米处塌陷一坑1.5×1×1m?并且大号侧2-5米处有三条裂纹长5米，宽5厘米，深0.5米。
     */

    private String start_name;
    private String find_time;
    private String category_name;
    private String line_name;
    private String end_name;
    private String id;
    private String content;

    public String getStart_name() {
        return start_name;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name;
    }

    public String getFind_time() {
        return find_time;
    }

    public void setFind_time(String find_time) {
        this.find_time = find_time;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getEnd_name() {
        return end_name;
    }

    public void setEnd_name(String end_name) {
        this.end_name = end_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
