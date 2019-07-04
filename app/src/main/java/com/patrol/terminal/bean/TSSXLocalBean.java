package com.patrol.terminal.bean;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(database = AppDataBase.class)
public class TSSXLocalBean extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    private int local_id;
    @Column
    private String key;
    @Column
    private String parKey;
    @Column
    private String values;

    private boolean isCheck = false;//是否被选中添加到列表
    @Column
    private String yhnr ="";//隐患内容
    @Column
    private String dj = "一般";//等级

    private List<String> photoList = new ArrayList<>();//图片内容

    // 数据id
    @Column
    private String id;

    // 个人任务id
    @Column
    private String task_id;

    // 线路id 线路不能为空！
    @Column
    private String line_id;
    //task_key  特殊属性 key
    @Column
    private String task_key;
    // 杆塔id
    @Column
    private String  tower_id;
    // 开始杆塔id
    @Column
    private String start_id;

    // 结束杆塔id
    @Column
    private String end_id;

    // 开始杆塔排序号
    @Column
    private String start_name;

    // 结束杆塔排序号
    @Column
    private String end_name;

    // 杆塔基数 杆段数量不传
    @Column
    private Integer tower_number;

    @Column
    private String year;
    @Column
    private String month;
    @Column
    private String day;


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getTask_key() {
        return task_key;
    }

    public void setTask_key(String task_key) {
        this.task_key = task_key;
    }

    public String getTower_id() {
        return tower_id;
    }

    public void setTower_id(String tower_id) {
        this.tower_id = tower_id;
    }

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    public String getEnd_id() {
        return end_id;
    }

    public void setEnd_id(String end_id) {
        this.end_id = end_id;
    }

    public String getStart_name() {
        return start_name;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name;
    }

    public String getEnd_name() {
        return end_name;
    }

    public void setEnd_name(String end_name) {
        this.end_name = end_name;
    }

    public Integer getTower_number() {
        return tower_number;
    }

    public void setTower_number(Integer tower_number) {
        this.tower_number = tower_number;
    }
}
