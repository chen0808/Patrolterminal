package com.patrol.terminal.utils;

import com.luck.picture.lib.entity.LocalMedia;

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
}
