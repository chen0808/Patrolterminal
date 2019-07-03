package com.patrol.terminal.bean;

import java.util.ArrayList;
import java.util.List;

public class TSSXBean{

    private String key;
    private String parKey;
    private String values;
    private boolean isCheck = false;//是否被选中添加到列表
    private String yhnr ="";//隐患内容
    private String dj = "";//等级
    private List<String> photoList = new ArrayList<>();//图片内容

    public TSSXBean(String key, String parKey, String values) {
        this.key = key;
        this.parKey = parKey;
        this.values = values;
    }

    public String getYhnr() {
        return yhnr;
    }

    public void setYhnr(String yhnr) {
        this.yhnr = yhnr;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
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
