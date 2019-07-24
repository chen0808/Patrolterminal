package com.patrol.terminal.bean;

/**
 * 本类由Code-Builder自动生成
 * 表名: EQ_VEHICLE - 车辆基本信息
 *
 * Created with Code-Builder.
 * Date：2019-05-07 16:02:36
 */
public class DriverBean {

    // 数据id
    private String id;

    // 编号
    private String name;

    /*** 自定义字段 ***/

    // 是否被使用（0：否；1：是）
    private String is_use;

    private boolean check;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_use() {
        return is_use;
    }

    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}