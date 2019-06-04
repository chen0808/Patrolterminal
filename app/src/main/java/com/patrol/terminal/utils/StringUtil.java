package com.patrol.terminal.utils;

public class StringUtil {

    private static String[] typeSigns=new String[]{"定期巡视","故障巡视","接电电阻检测","特殊性巡视","红外测温检测","杆塔倾斜检测","保电巡视","缺陷消除","隐患处理","绝缘子零值检测","监督性巡视","安全监督","验收报告"};
    //获取运行班月计划审核状态
    public static String getYXBstate(String state) {
        switch (state) {
            case "0":
                return "待提交审核";
            case "1":
                return "待专责审核";
            case "2":
                return "待主管审核";
            case "4":
                return "审核不通过";
            case "3":
                return "审核通过";
            case "5":
                return "执行中";
            case "6":
                return "2已完成";
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

    public static String getTypeSign(String sign){
        String typeNmae="";
        String[] split = sign.split(",");
        for (int i = 0; i < split.length; i++) {
            int index = Integer.parseInt(split[i])-1;
            if (i==0){
                typeNmae=typeSigns[index];
            }else {
                typeNmae=(i+"."+typeNmae)+";"+(i+1)+"."+typeSigns[i];
            }
        }
        return typeNmae;
    }
}
