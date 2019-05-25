package com.patrol.terminal.utils;

public class StringUtil {
    //获取运行班月计划审核状态
    public static String getYXBstate(String state) {
        switch (state) {
            case "0":
                return "状态 : 待提交审核";
            case "1":
                return "状态 : 待专责审核";
            case "2":
                return "状态 : 待主管审核";
            case "4":
                return "状态 : 审核不通过";
            case "3":
                return "状态 : 审核通过";
            case "5":
                return "状态 : 执行中";
            case "6":
                return "状态 : 已完成";
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
}
