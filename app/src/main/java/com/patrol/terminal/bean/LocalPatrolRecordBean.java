package com.patrol.terminal.bean;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

@Table(database = AppDataBase.class)
public class LocalPatrolRecordBean extends BaseModel implements Serializable {
    @PrimaryKey()
    private String task_id;
    @Column
    private String tower_id;
    @Column
    private String tower_name;
    @Column
    private String line_id;
    @Column
    private String line_name;
    @Column
    private String user_id;
    @Column
    private String user_name;
    @Column
    private String dep_id;
    @Column
    private String dep_name;
    @Column
    private String type_sign;
    @Column
    private String pic1;
    @Column
    private String pic2;
    @Column
    private String pic3;
    @Column
    private String pic4;
    @Column
    private String pic5;
    @Column
    private String pic6;
//    @Column
//    private List<LocalPatrolDefectBean> defectBean;
//    @Column
//    private List<LocalPatrolTroubleBean> troubleBean;

    public String getType_sign() {
        return type_sign;
    }

    public void setType_sign(String type_sign) {
        this.type_sign = type_sign;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getTower_name() {
        return tower_name;
    }

    public void setTower_name(String tower_name) {
        this.tower_name = tower_name;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
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

    public String getTower_id() {
        return tower_id;
    }

    public void setTower_id(String tower_id) {
        this.tower_id = tower_id;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic4() {
        return pic4;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

    public String getPic5() {
        return pic5;
    }

    public void setPic5(String pic5) {
        this.pic5 = pic5;
    }

    public String getPic6() {
        return pic6;
    }

    public void setPic6(String pic6) {
        this.pic6 = pic6;
    }
//
//    public List<LocalPatrolDefectBean> getDefectBean() {
//        return defectBean;
//    }
//
//    public void setDefectBean(List<LocalPatrolDefectBean> defectBean) {
//        this.defectBean = defectBean;
//    }
//
//    public List<LocalPatrolTroubleBean> getTroubleBean() {
//        return troubleBean;
//    }
//
//    public void setTroubleBean(List<LocalPatrolTroubleBean> troubleBean) {
//        this.troubleBean = troubleBean;
//    }
}
