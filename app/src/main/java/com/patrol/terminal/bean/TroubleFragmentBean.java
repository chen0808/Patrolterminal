package com.patrol.terminal.bean;

import java.io.Serializable;

public class TroubleFragmentBean implements Serializable {

    /**
     * owner_name : 杨刚
     * line_level : 4
     * line_name : 1122东新二线
     * dep_name : 开永运维班
     * line_id : C017AD2D0D494645A4EF50353B0EF526
     * ROW_ID : 1
     */

    private String owner_name;
    private String line_level;
    private String line_name;
    private String dep_name;
    private String line_id;
    private int ROW_ID;

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getLine_level() {
        return line_level;
    }

    public void setLine_level(String line_level) {
        this.line_level = line_level;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }
}
