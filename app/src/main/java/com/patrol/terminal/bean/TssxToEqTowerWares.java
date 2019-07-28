package com.patrol.terminal.bean;


import java.io.Serializable;
import java.util.List;

/**
 * 表名: EQ_TOWER_WARES - 杆塔特殊属性
 *
 * Date：2019-05-13 18:07:45
 */
public class TssxToEqTowerWares implements Serializable {
//         "id":"DE46491D16944F4F9536D2121592D122",
//         "wares_id":"E1C4D215842044CE82BE1F05704238B0",
//         "line_id":"06CD39FC7726400F92C00D4C89C80F1C",
//         "tower_id":"CB07BCAE1869460985102E979E2066E2",
//         "remarks":"三跨",
//         "sort":1,
//         "line_name":"1111桃郑线",
//         "tower_name":"#001",
//         "wares_name":"高速铁路",
//         "towers":null,
//         "taskTrouble":null,
//         "pda_sign":"1"
    // 数据id
    private String id;

    // 特殊属性id 
    private String wares_id;

    // 线路id 
    private String line_id;

    // 杆塔id 
    private String tower_id;

    // 备注 
    private String remarks;
    //特殊属性 parKey
    private String pda_sign;

    private String wares_name;//
    //
    private List<TssxToFile> eqTowerWaresImgList;//图片

    public String getWares_name() {
        return wares_name;
    }

    public void setWares_name(String wares_name) {
        this.wares_name = wares_name;
    }

    public String getPda_sign() {
        return pda_sign;
    }

    public void setPda_sign(String pda_sign) {
        this.pda_sign = pda_sign;
    }

    public List<TssxToFile> getEqTowerWaresImgList() {
        return eqTowerWaresImgList;
    }

    public void setEqTowerWaresImgList(List<TssxToFile> eqTowerWaresImgList) {
        this.eqTowerWaresImgList = eqTowerWaresImgList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWares_id() {
        return wares_id;
    }

    public void setWares_id(String wares_id) {
        this.wares_id = wares_id;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}