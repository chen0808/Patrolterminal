package com.patrol.terminal.bean;

import com.patrol.terminal.widget.CustomSpinner;

/**
 * 隐患标识配置
 * 1：三跨，2：防鸟，3：防雷，4：防风，5：防山火，6：防外破，7：地灾
 * "id": "4193B5063B6944379CBFEB3A215DEF60",
 * "code": "azfnc",
 * "name": "安装防鸟刺 ",
 * "p_id": "036316AB53AC4C99A719339BCDA5C0B7",
 * "sort": 0,
 * "detail": null,
 * "p_code": "fnh",
 * "p_name": "防鸟害处理措施",
 * "full_name": "\\防鸟害处理措施\\安装防鸟刺 ",
 * "leaf": 1,
 * "leaf_total": 0
 */
public class TroubleTypeBean implements CustomSpinner.CustomSpinnerItem {

    public final static String TROUBLE_SK = "sk";
    public final static String TROUBLE_FN = "fn";
    public final static String TROUBLE_FL = "fl";
    public final static String TROUBLE_FF = "ff";
    public final static String TROUBLE_FSH = "fsh";
    public final static String TROUBLE_FWP = "fwp";
    public final static String TROUBLE_DZ = "dz";


    private String id;
    private String name;
    private String p_code;
    private String p_name;
    private String full_name;

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

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
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

    @Override
    public String getItemStr() {
        return name;
    }
}
