package com.patrol.terminal.utils;

import com.luck.picture.lib.entity.LocalMedia;
import com.patrol.terminal.widget.CustomSpinner;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static final int MAP_REQUEST_CODE = 100;//地图界面返回数据
    public static String TYPE = "type";  //计划类型
    public static String TASKTYPE = "tasktype"; //计划类型id
    public static String TASKTYPENAME = "tasktypename";//计划类型名称
    public static String TASKTYPEVAL = "tasktypeval";//计划类型名称
    public static String DATE = "date";
    public static String TASKDATE = "taskdate";
    public static String USER = "user";
    public static String USERNAME = "username";
    public static String USERID = "userid";
    public static String USERJOB = "userjob";
    public static String USERJOBNAME = "userjobname";
    public static String TELEPHONE = "telephone";
    public static String DEPNAME = "depname";
    public static String DEPID = "depid";
    public static String SEX = "sex";
    public static String JOBS = "jobs";
    public static String JOBTYPE = "jobtype";
    public static String RESULT = "result";

    public static final String IMG_LIST = "img_list"; //第几张图片
    public static final String POSITION = "position"; //第几张图片
    public static final String PIC_PATH = "pic_path"; //图片路径
    public static final int CHOOSE_REQUEST = 123;
    public static final int MAX_SELECT_PIC_NUM = 5; // 最多上传5张图片
    public static final int REQUEST_CODE_MAIN = 10; //请求码
    public static final int RESULT_CODE_VIEW_IMG = 11; //查看大图页面的结果码
    public static final int REQUEST_CODE_ADDRESS_BOOK = 12;//通讯录
    public static final int REQUEST_CODE_ADDRESS_BOOK2 = 13;//通讯录2
    public static final int REQUEST_CODE_ADDRESS_BOOK3 = 14;//通讯录3
    public static final int REQUEST_CODE_ADDRESS_BOOK4 = 15;//通讯录4
    public static final int REQUEST_CODE_SIGN = 20;//签名跳转
    public static final int SAFE_LIST = 30;//跳转到所有的注意事项
    public static final int PATROL_RECORD_REQUEST_CODE = 40;//巡视记录拍照
    public static final int DEFECT_REQUEST_CODE = 50;//常规巡视拍照
    public static final int DEFECT_AUDIT_EDIT_REQUEST_CODE = 60;//缺陷复核拍照
    public static final int DEFECT_ADD_TROUBLE_CODE = 70;//添加隐患
    public static final String PARTOL_RECORD_PIC_INDEX = "partolRecordPicIndex";
    public static String USERDEPNAME = "userdepname";


    //角色：1.运行班班长    2.运行班组长（临时）    3.运行班班员（可能是班级的审计员）      4.检修班班长     5.检修班组长（临时）
    // 6.检修班班员（可能是班级的审计员）     7.运行班专责    8.检修班专责      9.主任      10.绩效专责    11.培训专责      12.汽车班班长    13.汽车班班员
    //14.保电专责           15.安全专责           16.验收专责
    public static final String RUNNING_SQUAD_LEADER = "yxb_bz";
    public static final String RUNNING_SQUAD_TEMA_LEADER = "yxb_zz";
    public static final String RUNNING_SQUAD_MEMBER = "yxb_cy";
    public static final String REFURBISHMENT_LEADER = "jxb_bz";
    public static final String REFURBISHMENT_TEMA_LEADER = "jxb_zz";
    public static final String REFURBISHMENT_MEMBER = "jxb_cy";
    public static final String RUNNING_SQUAD_SPECIALIZED = "yx_zz";
    public static final String REFURBISHMENT_SPECIALIZED = "jx_zz";
    public static final String DISTRICT_MANAGERD = "gq_zg";
    public static final String RUN_SUPERVISOR = "yx_zg";
    public static final String MAINTENANCE_SUPERVISOR = "jx_zg";
    public static final String ACHIEVEMENTS_SPECIALIZED = "gx_zz";
    public static final String TRAINING_SPECIALIZED = "px_zz";
    public static final String CAR_SQUAD_LEADER = "qcb_bz";
    public static final String CAR_SQUAD_MEMBER = "qcb_cy";
    public static final String POWER_CONSERVATION_SPECIALIZED = "bd_zz";
    public static final String SAFETY_SPECIALIZED = "aq_zz";
    public static final String ACCEPTANCE_CHECK_SPECIALIZED = "ys_zz";

    public static final String[] JOBS_NAME = {"运行班班长", "运行班组长", "运行班班员", "检修班班长", "检修班组长",
            "检修班班员", "运行专责", "检修专责", "工区主管", "运行主管", "检修主管", "绩效专责", "培训专责",
            "汽车班班长", "汽车班班员", "保电专责", "安全专责", "验收专责"};

    public static final int CONTROL_WORK = 0;
    public static final int CONTROL_QUALITY = 1;
    public static final int CONTROL_TOOL = 2;

    public static final int FROM_MONTHPLAN_TO_ADDMONTH = 0;
    public static final int FROM_WEEKPLAN_TO_ADDMONTH = 1;
    public static final int FROM_DAYPLAN_TO_ADDMONTH = 2;

    public static final int OVERHAUL_YEAR = 0;
    public static final int OVERHAUL_MONTH = 1;
    public static final int OVERHAUL_WEEK = 2;

    public static final String STATUS_LEADER = "3";//班长,专责
    public static final String STATUS_PRINCPIAL = "4";//负责人
    public static final String STATUS_ACCEPT = "5";//组员(接收人)
    public static final String STATUS_SIGN = "6";//签发人
    public static final String STATUS_LICENCE = "7";//许可人

    public static final int IS_OTHER_LOOK = 0;  //其他人员查看控制卡模式
    public static final int IS_FZR_WRITE = 1;   //负责人填写控制卡模式
    public static final int IS_FZR_UPDATE = 2;   //负责人更新控制卡模式
    public static final String CONTROL_CARD_ENTER_TYPE = "control_card_enter_type";

    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";
    public static final int REQ_QR_CODE = 11002; // // 打开扫描界面请求码

    public static final String SORT_ASC = "sort asc";

    public static final String ADD_TRAINING_PLAN_ENTER_TYPE = "add_train_plan_enter_type";
    public static final int FROM_YEAR_TO_ENTER_TYPE = 0;
    public static final int FROM_MONTH_TO_ENTER_TYPE = 1;
    public static final int FROM_ADD_PLAN_TO_ENTER_TYPE = 2;
    public static final int FROM_TEMP_TO_ENTER_TYPE = 3;

    public static final String ADD_TRAIN_PLAN_IS_DISTRIBUTE = "add_train_plan_distribute";
    public static final int NOT_AUDITED = 0;   //未审核
    public static final int IS_AUDITED = 1;    //审核
    public static final int IS_SEND = 2;    //已分发

    public static final String PLAN_TRAIN_ID = "plan_train_id";
    public static final String TRAIN_YEAR_PLAN = "train_year_plan";
    public static final String TRAIN_MONTH_PLAN = "train_month_plan";
    public static final String TRAIN_TEMP_PLAN = "train_temp_plan";

    public static boolean isContent = false;
    public static List<LocalMedia> picList = null;
    public static String defectType = "";
    public static int defect_tab = -1;//巡视类别
    public static int defect_index = -1;//巡视位置
    public static String defect_patrol_id = "";//巡视内容id
    public static int defect_patrol_index = -1;//图片位置
    public static String patrol_record_audit_status = "";//巡视任务状态

    public static String CHECK_RESULT = "check_result";
    public static long checkResultId;

    public static String DJ_YB = "23BC6E4170E7499688225680391086F5";//隐患等级 一般
    public static String DJ_YZ = "C98E48C4EA1A4E4CBB686735AC42CAE4";//严重
    public static String DJ_WJ = "14E164AE79834B809AAEFA151658C97A";//危急

    public static String DJ_YB_STR = "一般";
    public static String DJ_YZ_STR = "严重";
    public static String DJ_WJ_STR = "危急";

    //1.三跨 ，2.防鸟，3.防雷，4.防风，5.防山火，6.防外破，7.地灾
    public static final String TYPE_ID_SK = "1";
    public static final String TYPE_ID_FN = "2";
    public static final String TYPE_ID_FL = "3";
    public static final String TYPE_ID_FF = "4";
    public static final String TYPE_ID_FSH = "5";
    public static final String TYPE_ID_FWP = "6";
    public static final String TYPE_ID_DZ = "7";


    //23、三跨，24、防鸟，25、防雷，26、防风，27、防山火，28、防外破，29、地灾
    public static final String FLOW_SIGN_SK = "23";
    public static final String FLOW_SIGN_FN = "24";
    public static final String FLOW_SIGN_FL = "25";
    public static final String FLOW_SIGN_FF = "26";
    public static final String FLOW_SIGN_FSH = "27";
    public static final String FLOW_SIGN_FWP = "28";
    public static final String FLOW_SIGN_DZ = "29";
    public static final String FLOW_SIGN_SK_STR = "三跨";
    public static final String FLOW_SIGN_FN_STR = "防鸟害";
    public static final String FLOW_SIGN_FL_STR = "防雷";
    public static final String FLOW_SIGN_FF_STR = "防风";
    public static final String FLOW_SIGN_FSH_STR = "防山火";
    public static final String FLOW_SIGN_FWP_STR = "防外破";
    public static final String FLOW_SIGN_DZ_STR = "地灾";

    //隐患审核不关联特殊属性
    public static final String YH_BUGUANLIAN = "不关联属性";

    //工程简报
    public static final String GCJB_TYPE_STR = "GCJB_TYPE_STR";
    public static final String GCJB_YZF_STR = "3";//建设方
    public static final String GCJB_JLF_STR = "2";//监理方
    public static final String GCJB_SGF_STR = "1";//施工方
    public static final String GCJB_ADD_STR = GCJB_SGF_STR;//默认添加施工方
    public static boolean isEditStatus = false;
//    public static final int GCJB_YZF = 1;
//    public static final int GCJB_JLF = 2;
//    public static final int GCJB_SGF = 3;
    public static final int GCJB_ADD = 10001;
    public static final int GCJB_ADD_PROJECT = 10002;


    public static final String SWITCH_NAME = "switch_name";
    public static final String SWITCH_PWD = "switch_pwd";

    public static List<LoginBean> getLoginList() {
        List<LoginBean> list = new ArrayList<>();
        LoginBean bean0 = new LoginBean();
        bean0.setLoginName("");
        bean0.setLoginPwd("");
        bean0.setName("切换角色");

        LoginBean bean11 = new LoginBean();
        bean11.setLoginName("王健");
        bean11.setLoginPwd("123456");
        bean11.setName("运行主管");
        LoginBean bean = new LoginBean();
        bean.setLoginName("王小龙");
        bean.setLoginPwd("123456");
        bean.setName("运行专责");
        LoginBean bean1 = new LoginBean();
        bean1.setLoginName("黄静波");
        bean1.setLoginPwd("123456");
        bean1.setName("运行班长");
        LoginBean bean2 = new LoginBean();
        bean2.setLoginName("马宝龙");
        bean2.setLoginPwd("123456");
        bean2.setName("运行班员");

        LoginBean bean21 = new LoginBean();
        bean21.setLoginName("徐向军");
        bean21.setLoginPwd("123456");
        bean21.setName("检修主管");
        LoginBean bean22 = new LoginBean();
        bean22.setLoginName("桑彦斌");
        bean22.setLoginPwd("123456");
        bean22.setName("检修专责");
        LoginBean bean23 = new LoginBean();
        bean23.setLoginName("叶怀刚");
        bean23.setLoginPwd("123456");
        bean23.setName("检修班长");
        LoginBean bean24 = new LoginBean();
        bean24.setLoginName("李小东");
        bean24.setLoginPwd("123456");
        bean24.setName("检修班员");

        list.add(bean0);

        list.add(bean11);
        list.add(bean);
        list.add(bean1);
        list.add(bean2);

        list.add(bean21);
        list.add(bean22);
        list.add(bean23);
        list.add(bean24);

        return list;
    }


    public static class LoginBean implements CustomSpinner.CustomSpinnerItem {

        String loginName;
        String loginPwd;
        String name;

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getLoginPwd() {
            return loginPwd;
        }

        public void setLoginPwd(String loginPwd) {
            this.loginPwd = loginPwd;
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
}
