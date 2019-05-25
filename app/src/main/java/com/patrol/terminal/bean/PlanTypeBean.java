package com.patrol.terminal.bean;

import java.util.List;

public class PlanTypeBean {
    public static final int FIRST_TYPE = 1;
    public static final int SECOND_TYPE = 2;
    //添加类型变量
    public int type;
    public int start;
    public int end;
    private List<EqTower> eqTowers;

    public List<EqTower> getEqTowers() {
        return eqTowers;
    }

    public void setEqTowers(List<EqTower> eqTowers) {
        this.eqTowers = eqTowers;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * id : C7A9A60BDB1B4FE986014CA7DA24A467
     * name : 定期巡视
     * val : 1
     */

    private String id;
    private String name;
    private String val;
    private String time;
    private boolean ischeck;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
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

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

}
