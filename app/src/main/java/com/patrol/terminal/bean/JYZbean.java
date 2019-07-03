package com.patrol.terminal.bean;

import android.os.Parcelable;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

@Table(database = AppDataBase.class)
public class JYZbean extends BaseModel implements Serializable {

    /**
     * id : 6308CC5B64334B9DBE5576D632E0597B
     * task_id : 3047143CBCA545B5BB3B3DEDEC5A4F12
     * tower_id : CB07BCAE1869460985102E979E2066E2
     * pieces : 8522.0
     * work_time : 2019年05月23日 03时36分
     * results : 哈哈
     * insulator_type : 白百合
     * remark : 哈哈
     * tower_type : 投影仪
     * line_name : 桃郑线
     * tower_name : #001
     * audit_id : null
     * content : null
     * plan_type_sign : null
     * agents_user_id : null
     */

    @PrimaryKey(autoincrement = true)
    private int local_id;
    @Column
    private String id;
    @Column
    private String task_id;
    @Column
    private String tower_id;
    @Column
    private double pieces;
    @Column
    private String work_time;
    @Column
    private String results;
    @Column
    private String insulator_type;
    @Column
    private String remark;
    @Column
    private String tower_type;
    @Column
    private String line_name;
    @Column
    private String tower_name;
    @Column
    private String audit_id;
    @Column
    private String content;
    @Column
    private String plan_type_sign;
    @Column
    private String agents_user_id;

    @Column
    private String user_id;

    @Column
    private String tower_model;

    public String getTower_model() {
        return tower_model;
    }

    public void setTower_model(String tower_model) {
        this.tower_model = tower_model;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getTower_id() {
        return tower_id;
    }

    public void setTower_id(String tower_id) {
        this.tower_id = tower_id;
    }

    public double getPieces() {
        return pieces;
    }

    public void setPieces(double pieces) {
        this.pieces = pieces;
    }

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getInsulator_type() {
        return insulator_type;
    }

    public void setInsulator_type(String insulator_type) {
        this.insulator_type = insulator_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTower_type() {
        return tower_type;
    }

    public void setTower_type(String tower_type) {
        this.tower_type = tower_type;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getTower_name() {
        return tower_name;
    }

    public void setTower_name(String tower_name) {
        this.tower_name = tower_name;
    }

    public String getAudit_id() {
        return audit_id;
    }

    public void setAudit_id(String audit_id) {
        this.audit_id = audit_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlan_type_sign() {
        return plan_type_sign;
    }

    public void setPlan_type_sign(String plan_type_sign) {
        this.plan_type_sign = plan_type_sign;
    }

    public String getAgents_user_id() {
        return agents_user_id;
    }

    public void setAgents_user_id(String agents_user_id) {
        this.agents_user_id = agents_user_id;
    }
}
