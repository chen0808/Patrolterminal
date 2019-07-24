package com.patrol.terminal.bean;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * 隐患 处理措施
 */
@Table(database = AppDataBase.class)
public class CLCSTypeBean extends BaseModel {
    /**
     * id : 37E5647975394B1E952DC5D2796C7D73
     * code : yb
     * name : 一般
     * p_id : C8832ACCC1FB49DE818E05D29E1FABFA
     * sort : 0
     * detail : null
     * p_code : qxjb
     * p_name : 缺陷级别
     * full_name : \缺陷级别\一般
     * leaf : 1.0
     * leaf_total : 0.0
     */
    @PrimaryKey(autoincrement = true)
    private int local_id;
    @Column
    private String name;
    @Column
    private String id;
    @Column
    private String p_id;
    @Column
    private String p_name;
    @Column
    private String full_name;

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
