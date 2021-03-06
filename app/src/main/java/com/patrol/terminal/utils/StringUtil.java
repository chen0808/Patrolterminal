package com.patrol.terminal.utils;

import com.patrol.terminal.R;

public class StringUtil {

    public static String[] typeSigns = new String[]{"定期巡视", "故障巡视", "接地电阻检测", "特殊性巡视", "红外测温检测", "杆塔倾斜检测", "保电巡视", "缺陷消除", "隐患处理", "绝缘子零值检测", "监督性巡视", "安全监督", "验收报告", "缺陷复核", "隐患巡视"};

    //获取运行班月计划审核状态
    public static String getYXBstate(String state) {
        switch (state) {
            case "0":
                return "待提交审核";
            case "1":
                return "待专责审核";
            case "2":
                return "待主管审核";
            case "3":
                return "审核通过";
            case "4":
                return "审核不通过";
            case "5":
                return "执行中";
            case "6":
                return "已完成";
        }
        return "";
    }

    //获取运行班月计划审核状态
    public static String getPersonalState(String state) {
        switch (state) {
            case "0":
                return "待执行";
            case "1":
                return "待负责人审核";
            case "2":
                return "待班长审核";
            case "3":
                return "已完成";
            case "4":
                return "审核不通过";
            case "100":
                return "待上传";
        }
        return "";
    }

    //获取运行班月计划审核状态
    public static String getGroupState(String state) {
        switch (state) {
            case "0":
                return "未分配";
            case "1":
                return "已分配";
        }
        return "";
    }

    //获取缺陷状态
    public static String getDefectState(String state) {
        switch (state) {
            case "0":
                return "编制";
            case "1":
                return "待班长审核";
            case "2":
                return "待专责审核";
            case "3":
                return "审核通过";
            case "4":
                return "审核不通过";
            case "5":
                return "复核中";
        }
        return "";
    }
    //获取缺陷状态
    public static String getDefectPlanState(String state) {
        switch (state) {
            case "0":
                return "待制定";
            case "1":
                return "待填写控制卡";
            case "2":
                return "待班张审核";
            case "3":
                return "待专责审核";
            case "4":
                return "审核通过";
            case "5":
                return "审核不通过";
            case "6":
                return "复核中";
        }
        return "";
    }
    //获取运行班月计划审核状态
    public static int getDefectColor(String state) {
        switch (state) {
            case "0":
                return R.color.blue;
            case "1":
                return R.color.line_point_1;
            case "2":
                return R.color.line_point_1;
            case "3":
                return R.color.green;
            case "4":
                return R.color.write_red;
            case "5":
                return R.color.orange;
        }
        return R.color.line_point_0;
    }

    //获取个人任务审核状态
    public static int getPersonalColor(String state) {
        switch (state) {
            case "0":
                return R.color.color_33;
            case "1":
                return R.color.line_point_1;
            case "2":
                return R.color.line_point_2;
            case "3":
                return R.color.line_point_3;
            case "4":
                return R.color.line_point_0;
            default:
                return R.color.line_point_0;
        }

    }

    //获取运行班月计划审核状态
    public static int getGroupColor(String state) {
        switch (state) {
            case "0":
                return R.color.line_point_0;
            case "1":
                return R.color.line_point_3;
            case "2":
                return R.color.line_point_2;
            case "3":
                return R.color.line_point_3;
            case "4":
                return R.color.line_point_0;
        }
        return R.color.line_point_0;
    }

    //获取运行班月计划审核状态
    public static String getYXBstateText(String state) {
        switch (state) {
            case "0":
                return "审核状态：待提交审核";
            case "1":
                return "审核状态：待专责审核";
            case "2":
                return "审核状态：待主管审核";
            case "3":
                return "审核状态：审核通过";
            case "4":
                return "审核状态：审核不通过";
            case "5":
                return "审核状态：执行中";
            case "6":
                return "审核状态：已完成";
        }
        return "";
    }


    //获取运行班周计划审核状态
    public static String getYXBWeekText(String state) {
        switch (state) {
            case "0":
                return "审核状态：待提交审核";
            case "1":
                return "审核状态：待专责审核";
            case "2":
                return "审核状态：审核通过";
            case "3":
                return "审核状态：审核不通过";
            case "4":
                return "审核状态：执行中";
            case "5":
                return "审核状态：已完成";
        }
        return "";
    }

    //获取运行班审核状态
    public static String getYxbWeekState(String state) {
        switch (state) {
            case "0":
                return "状态 : 待提交审核";
            case "1":
                return "状态 : 待专责审核";
            case "2":
                return "状态 : 审核通过";
            case "3":
                return "状态 : 审核不通过";
            case "4":
                return "状态 : 执行中";
            case "5":
                return "状态 : 已完成";
        }
        return "";
    }

    //获取项目状态
    public static String getProjectStatus(String status) {
        switch (status) {
            case "0":
                return "项目前期";
            case "1":
                return "项目立项";
            case "2":
                return "设计管理";
            case "3":
                return "招标管理";
            case "4":
                return "合同管理";
            case "5":
                return "进度管理";
            case "6":
                return "前期";
            case "7":
                return "实施准备";
            case "8":
                return "在建";
            case "9":
                return "停缓期";
            case "10":
                return "验收";
            case "11":
                return "竣工";
            case "12":
                return "保内";
            case "13":
                return "保外";
            case "14":
                return "解除";
            case "15":
                return "完成";
        }
        return "";
    }

    public static String getTypeSign(String sign) {
        String typeNmae = "";
        String[] split = sign.split(",");
        for (int i = 0; i < split.length; i++) {
            int index = Integer.parseInt(split[i]) - 1;
            if (split.length == 1) {
                typeNmae = typeSigns[index];
            } else {
                if (i == 0) {
                    typeNmae = (i + 1) + "、" + typeSigns[index];
                } else {
                    typeNmae = typeNmae + "<br/>" + (i + 1) + "、" + typeSigns[index];
                }

            }
        }
        return typeNmae;
    }

    public static String getDangerLevel(String level) {
        switch (level) {
            case "1":
                return "一般";
            case "2":
                return "重大";
            case "3":
                return "紧急";

        }
        return "";
    }
}
