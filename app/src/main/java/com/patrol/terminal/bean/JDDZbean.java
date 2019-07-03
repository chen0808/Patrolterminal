package com.patrol.terminal.bean;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDataBase.class)
public class JDDZbean extends BaseModel {

    /**
     * id : 772E5BFB025C4627800CCCB685C05779
     * task_id : 5E2C34EB13514C84A4ECDFF5CB76A8F0
     * tower_id : 28F1AEA9B6CD4C20AEAE02036B064EA5
     * measure_a : 1.0
     * measure_b : 2.0
     * measure_c : 3.0
     * measure_d : 4.0
     * weather : 好
     * work_time : 2019年05月23日 11时22分
     * tower_type : 嘎嘎嘎
     * results : 正常
     * remark : 没有
     * line_name : 西陈二线
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
    private double measure_a;
    @Column
    private double measure_b;
    @Column
    private double measure_c;
    @Column
    private double measure_d;
    @Column
    private String weather;
    @Column
    private String work_time;
    @Column
    private String tower_type;
    @Column
    private String results;
    @Column
    private String remark;
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
    private String line_id;
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

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
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

    public double getMeasure_a() {
        return measure_a;
    }

    public void setMeasure_a(double measure_a) {
        this.measure_a = measure_a;
    }

    public double getMeasure_b() {
        return measure_b;
    }

    public void setMeasure_b(double measure_b) {
        this.measure_b = measure_b;
    }

    public double getMeasure_c() {
        return measure_c;
    }

    public void setMeasure_c(double measure_c) {
        this.measure_c = measure_c;
    }

    public double getMeasure_d() {
        return measure_d;
    }

    public void setMeasure_d(double measure_d) {
        this.measure_d = measure_d;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getTower_type() {
        return tower_type;
    }

    public void setTower_type(String tower_type) {
        this.tower_type = tower_type;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
