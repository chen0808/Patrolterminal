package com.patrol.terminal.bean;

import java.util.ArrayList;
import java.util.List;
public class TSSXBean{

//        "id":"79CC4ABA387B43ED957269160AAB31DD",    // 数据id
//        "wares_id":"274EE1B7A4F444F9AD8F218791BA2740",// 特殊属性id
//        "line_id":"84DB131F8BEA4F52BA5DD74C1A3D72DA",// 线路id
//        "tower_id":"BDFF622AA6B647318AEA3ADAE65C68F0",// 杆塔id
//        "remarks":"特殊属性为：（三跨）重要输电通道", // 备注
//        "sort":2,                                    // 排序号
//        "line_name":"1114龚华线",
//        "tower_name":"#002",
//        "wares_name":"重要输电通道",
//        "towers":null,
//        "taskTrouble":null

    private String key;
    private String parKey;
    private String values;

    private boolean isCheck = false;//是否被选中添加到列表
    private String yhnr ="";//隐患内容
    private String dj = "";//等级

    private List<String> photoList = new ArrayList<>();//图片内容

    public TSSXBean() {
    }

    public TSSXBean(String key, String parKey, String values) {
        this.key = key;
        this.parKey = parKey;
        this.values = values;
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

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
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

    public void setClearPhotoList()
    {
        photoList.clear();
    }
}
