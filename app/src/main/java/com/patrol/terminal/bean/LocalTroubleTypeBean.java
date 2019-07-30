package com.patrol.terminal.bean;

import com.patrol.terminal.sqlite.AppDataBase;
import com.patrol.terminal.widget.CustomSpinner;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 隐患标识配置
 * <p>
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
@Table(database = AppDataBase.class)
public class LocalTroubleTypeBean extends BaseModel implements CustomSpinner.CustomSpinnerItem {

    public final static String TROUBLE_SK = "sk";
    public final static String TROUBLE_FN = "fnh";
    public final static String TROUBLE_FL = "fl";
    public final static String TROUBLE_FF = "ff";
    public final static String TROUBLE_FSH = "fsh";
    public final static String TROUBLE_FWP = "fwp";
    public final static String TROUBLE_DZ = "dz";
    public final static String TROUBLE_SK_INT = "1";
    public final static String TROUBLE_FN_INT = "2";
    public final static String TROUBLE_FL_INT = "3";
    public final static String TROUBLE_FF_INT = "4";
    public final static String TROUBLE_FSH_INT = "5";
    public final static String TROUBLE_FWP_INT = "6";
    public final static String TROUBLE_DZ_INT = "7";

    private static List<LocalTroubleTypeBean> clcsList;
    private static List<LocalTroubleTypeBean> indexClcsList = new ArrayList<>();
    @PrimaryKey(autoincrement = true)
    private int local_id;
    @Column
    private String id;
    @Column
    private String name;
    @Column
    private String p_code;
    @Column
    private String p_name;
    @Column
    private String full_name;

    public static List<LocalTroubleTypeBean.TroubleType> troubleTypes = null;

    /**
     * 1：三跨，2：防鸟，3：防雷，4：防风，5：防山火，6：防外破，7：地灾
     */
    public static List<LocalTroubleTypeBean.TroubleType> getTroubleType() {

        if (troubleTypes == null) {
            troubleTypes = new ArrayList<>();
            LocalTroubleTypeBean.TroubleType type1 = new LocalTroubleTypeBean.TroubleType();
            type1.setId("1");
            type1.setTypeStr(TROUBLE_SK);
            type1.setName("三跨");
            troubleTypes.add(type1);
            LocalTroubleTypeBean.TroubleType type2 = new LocalTroubleTypeBean.TroubleType();
            type2.setId("2");
            type2.setTypeStr(TROUBLE_FN);
            type2.setName("防鸟害");
            troubleTypes.add(type2);
            LocalTroubleTypeBean.TroubleType type3 = new LocalTroubleTypeBean.TroubleType();
            type3.setId("3");
            type3.setTypeStr(TROUBLE_FL);
            type3.setName("防雷");
            troubleTypes.add(type3);
            LocalTroubleTypeBean.TroubleType type4 = new LocalTroubleTypeBean.TroubleType();
            type4.setId("4");
            type4.setTypeStr(TROUBLE_FF);
            type4.setName("防风");
            troubleTypes.add(type4);
            LocalTroubleTypeBean.TroubleType type5 = new LocalTroubleTypeBean.TroubleType();
            type5.setId("5");
            type5.setTypeStr(TROUBLE_FSH);
            type5.setName("防山火");
            troubleTypes.add(type5);
            LocalTroubleTypeBean.TroubleType type6 = new LocalTroubleTypeBean.TroubleType();
            type6.setId("6");
            type6.setTypeStr(TROUBLE_FWP);
            type6.setName("防外破");
            troubleTypes.add(type6);
            LocalTroubleTypeBean.TroubleType type7 = new LocalTroubleTypeBean.TroubleType();
            type7.setId("7");
            type7.setTypeStr(TROUBLE_DZ);
            type7.setName("地灾");
            troubleTypes.add(type7);
        }

        return troubleTypes;
    }

    public static class TroubleType implements CustomSpinner.CustomSpinnerItem {
        private String id;//1
        private String typeStr;//fl
        private String name;//防雷

        public String getTypeStr() {
            return typeStr;
        }

        public void setTypeStr(String typeStr) {
            this.typeStr = typeStr;
        }

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

        @Override
        public String getItemStr() {
            return name;
        }
    }

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

    /**
     * 获取当前类别的处理措施
     *
     * @param type
     * @return
     */
    public static List<LocalTroubleTypeBean> indexList(String type) {
        if (clcsList == null || clcsList.size() == 0)
            clcsList = SQLite.select().from(LocalTroubleTypeBean.class).queryList();
        indexClcsList.clear();
        for (int i = 0; i < clcsList.size(); i++) {
            LocalTroubleTypeBean typeBean = clcsList.get(i);
            if (typeBean.getP_code().equals(type)) {
                indexClcsList.add(typeBean);
            }
        }
        return indexClcsList;
    }

    /**
     * 删除所有数据
     */
    public static void delAllData() {
        SQLite.delete().from(LocalTroubleTypeBean.class).execute();
    }

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }


}
