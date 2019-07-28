package com.patrol.terminal.bean;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * 常规巡视本地数据库
 */
@Table(database = AppDataBase.class)
public class LocalPatrolDefectBean extends BaseModel implements Serializable {

    /**
     * task_id : 111
     * patrol_id : 123
     * status : 1
     * taskDefect : {"task_id":"111","category_id":"222","grade_id":"adad","patrol_id":"fafasf","content":"gsdgdgdfgdfgfdgdfgd","line_id":"fafasf","line_name":"fafasf","start_id":"fafasf","end_id":"fafasf","start_name":"fafasf","end_name":"fafasf","find_user_id":"fafasf","find_user_name":"fafasf","find_dep_id":"fafasf","find_dep_name":"fafasf"}
     */

    @PrimaryKey(autoincrement = true)
    private int id;
    @Column
    private String patrol_id;
    @Column
    private String patrol_name;//缺陷巡视内容   （1）导线，地线腐蚀、断股
    @Column
    private String task_id;
    @Column
    private String tab_name;//缺陷类别
    @Column
    private String status;
    @Column
    private String category_id;
    @Column
    private String category_name;// 缺陷类别  导线，地线
    @Column
    private String grade_id;//
    @Column
    private String grade_name;// 缺陷级别   危急，严重，一般
    @Column
    private String grade_sign;// 缺陷标识（1：一般，2：严重，3：危急）
    @Column
    private String content;
    @Column
    private String line_id;
    @Column
    private String line_name;
    @Column
    private String tower_id;
    @Column
    private String tower_name;
    @Column
    private String start_id;
    @Column
    private String end_id;
    @Column
    private String start_name;
    @Column
    private String end_name;
    @Column
    private String find_user_id;
    @Column
    private String find_user_name;
    @Column
    private String find_dep_id;
    @Column
    private String find_dep_name;
    @Column
    private String pics;
    @Column
    private String online_pics;
    @Column
    private String clcsName;//处理措施
    @Column
    private String clcsId;

    public String getTower_id() {
        return tower_id;
    }

    public void setTower_id(String tower_id) {
        this.tower_id = tower_id;
    }

    public String getTower_name() {
        return tower_name;
    }

    public void setTower_name(String tower_name) {
        this.tower_name = tower_name;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getClcsName() {
        return clcsName;
    }

    public void setClcsName(String clcsName) {
        this.clcsName = clcsName;
    }

    public String getClcsId() {
        return clcsId;
    }

    public void setClcsId(String clcsId) {
        this.clcsId = clcsId;
    }

    public String getOnline_pics() {
        return online_pics;
    }

    public void setOnline_pics(String online_pics) {
        this.online_pics = online_pics;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatrol_name() {
        return patrol_name;
    }

    public void setPatrol_name(String patrol_name) {
        this.patrol_name = patrol_name;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTab_name() {
        return tab_name;
    }

    public void setTab_name(String tab_name) {
        this.tab_name = tab_name;
    }

    public String getPatrol_id() {
        return patrol_id;
    }

    public void setPatrol_id(String patrol_id) {
        this.patrol_id = patrol_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(String grade_id) {
        this.grade_id = grade_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
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

    public String getFind_user_id() {
        return find_user_id;
    }

    public void setFind_user_id(String find_user_id) {
        this.find_user_id = find_user_id;
    }

    public String getFind_user_name() {
        return find_user_name;
    }

    public void setFind_user_name(String find_user_name) {
        this.find_user_name = find_user_name;
    }

    public String getFind_dep_id() {
        return find_dep_id;
    }

    public void setFind_dep_id(String find_dep_id) {
        this.find_dep_id = find_dep_id;
    }

    public String getFind_dep_name() {
        return find_dep_name;
    }

    public void setFind_dep_name(String find_dep_name) {
        this.find_dep_name = find_dep_name;
    }

    public String getGrade_sign() {
        return grade_sign;
    }

    public void setGrade_sign(String grade_sign) {
        this.grade_sign = grade_sign;
    }
}
