package com.patrol.terminal.bean;

public class TSSXBean{

    private String key;
    private String parKey;
    private String values;
    private boolean isCheck;

    public TSSXBean(String key, String parKey, String values) {
        this.key = key;
        this.parKey = parKey;
        this.values = values;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParKey() {
        return parKey;
    }

    public void setParKey(String parKey) {
        this.parKey = parKey;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}
