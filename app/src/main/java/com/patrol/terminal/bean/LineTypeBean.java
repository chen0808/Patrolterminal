package com.patrol.terminal.bean;

public class LineTypeBean {


    /**
     * id : C7A9A60BDB1B4FE986014CA7DA24A467
     * name : 定期巡视
     * val : 1
     * eqTowers : null
     */

    private String id;
    private String name;
    private String val;
    private String sign;
    private Object eqTowers;

    public LineTypeBean(String name, String sign) {
        this.name = name;
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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

    public Object getEqTowers() {
        return eqTowers;
    }

    public void setEqTowers(Object eqTowers) {
        this.eqTowers = eqTowers;
    }
}
