package com.patrol.terminal.utils;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.patrol.terminal.R;

public class AdapterUtils {
    //设置图标文字
    public static void setIconText(TextView icon, String dep_name) {
        switch (dep_name) {
            case "西固运维班":
                icon.setText("西固");
                icon.setBackgroundResource(R.drawable.plan_week_bg);
                break;
            case "兰州运维班":
                icon.setText("兰州");
                icon.setBackgroundResource(R.drawable.plan_qing_bg);
                break;
            case "新城运维班":
                icon.setText("新城");
                icon.setBackgroundResource(R.drawable.plan_yellow_bg);
                break;
            case "盐海运维班":
                icon.setText("盐海");
                icon.setBackgroundResource(R.drawable.plan_mon_bg);
                break;
            case "开永运维班":
                icon.setText("开永");
                icon.setBackgroundResource(R.drawable.plan_kaiyong_bg);
                break;
            case "和华运维班":
                icon.setText("和华");
                icon.setBackgroundResource(R.drawable.plan_red_bg);
                break;
            case "榆中运维班":
                icon.setText("榆中");
                icon.setBackgroundResource(R.drawable.plan_green_bg);
        }
    }

    //设置图标文字
    public static void setIconTextNew(TextView icon, String type_name) {
        switch (type_name) {
            case "三跨":
                icon.setText("三跨");
                icon.setBackgroundResource(R.drawable.plan_sankua_bg);
                break;
            case "防雷":
                icon.setText("防雷");
                icon.setBackgroundResource(R.drawable.plan_week_bg);
                break;
            case "防鸟患":
                icon.setText("防鸟");
                icon.setBackgroundResource(R.drawable.plan_qing_bg);
                break;
            case "防风":
                icon.setText("防风");
                icon.setBackgroundResource(R.drawable.plan_yellow_bg);
                break;
            case "防山火":
                icon.setText("防火");
                icon.setBackgroundResource(R.drawable.plan_mon_bg);
                break;
            case "防外破":
                icon.setText("防破");
                icon.setBackgroundResource(R.drawable.plan_kaiyong_bg);
                break;
            case "防地灾":
                icon.setText("防灾");
                icon.setBackgroundResource(R.drawable.plan_red_bg);
                break;
            case "防洪防汛":
                icon.setText("防洪");
                icon.setBackgroundResource(R.drawable.plan_green_bg);
        }
    }

    //设置内容颜色
    public static void setText(TextView textView, String text) {
        if (text.contains("定期巡视")) {
            text = text.replace("定期巡视", "<font color='#007bff'>定期巡视</font>");
        }
        if (text.contains("故障巡视")) {
            text = text.replace("故障巡视", "<font color='#6610f2'>故障巡视</font>");
        }
        if (text.contains("接地电阻检测")) {
            text = text.replace("接地电阻检测", "<font color='#6f42c1'>接地电阻检测</font>");
        }
        if (text.contains("特殊性巡视")) {
            text = text.replace("特殊性巡视", "<font color='#e83e8c'>特殊性巡视</font>");
        }
        if (text.contains("红外测温检测")) {
            text = text.replace("红外测温检测", "<font color='#d4237a'>红外测温检测</font>");
        }
        if (text.contains("杆塔倾斜检测")) {
            text = text.replace("杆塔倾斜检测", "<font color='#fd7e14'>杆塔倾斜检测</font>");
        }
        if (text.contains("保电巡视")) {
            text = text.replace("保电巡视", "<font color='#ffc107'>保电巡视</font>");
        }
        if (text.contains("缺陷消除")) {
            text = text.replace("缺陷消除", "<font color='#28a745'>缺陷消除</font>");
        }
        if (text.contains("隐患处理")) {
            text = text.replace("隐患处理", "<font color='#20c997'>隐患处理</font>");
        }
        if (text.contains("绝缘子零值检测")) {
            text = text.replace("绝缘子零值检测", "<font color='#17a2b8'>绝缘子零值检测</font>");
        }
        if (text.contains("监督性巡视")) {
            text = text.replace("监督性巡视", "<font color='#28c7a7'>监督性巡视</font>");
        }
        if (text.contains("安全监督")) {
            text = text.replace("安全监督", "<font color='#1aa2b8'>安全监督</font>");
        }
        if (text.contains("验收")) {
            text = text.replace("验收", "<font color='#0070ff'>验收</font>");
        }
        if (text.contains("已分配")) {
            text = text.replace("已分配", "<font color='#8BC34A'>已分配</font>");
        }
        if (text.contains("未分配")) {
            text = text.replace("未分配", "<font color='#fb5050'>未分配</font>");
        }
        if (text.contains("已抢单")) {
            text = text.replace("已抢单", "<font color='#057dff'>已抢单</font>");
        }
        if (text.contains("已完成")) {
            text = text.replace("已完成", "<font color='#8BC34A'>已完成</font>");
        }
        if (text.contains("未完成")) {
            text = text.replace("未完成", "<font color='#fb5050'>未完成</font>");
        }
        if (text.contains("待提交审核")) {
            text = text.replace("待提交审核", "<font color='#ff7272'>待提交审核</font>");
        }
        if (text.contains("待专责审核")) {
            text = text.replace("待专责审核", "<font color='#f7d035'>待专责审核</font>");
        }
        if (text.contains("待主管审核")) {
            text = text.replace("待主管审核", "<font color='#057dff'>待主管审核</font>");
        }
        if (text.contains("审核通过")) {
            text = text.replace("审核通过", "<font color='#00bc9f'>审核通过</font>");
        }
        if (text.contains("审核不通过")) {
            text = text.replace("审核不通过", "<font color='#ff7272'>审核不通过</font>");
        }
        if (text.contains("退还审核中")) {
            text = text.replace("退还审核中", "<font color='#057dff'>退还审核中</font>");
        }
        if (text.contains("退还不通过")) {
            text = text.replace("退还不通过", "<font color='#ff7272'>退还不通过</font>");
        }

        if (text.contains("跨高速公路")) {
            text = text.replace("跨高速公路", "<font color='#007bff'>跨高速公路</font>");
        }
        if (text.contains("跨高速铁路")) {
            text = text.replace("跨高速铁路", "<font color='#6610f2'>跨高速铁路</font>");
        }
        if (text.contains("跨普通铁路")) {
            text = text.replace("跨普通铁路", "<font color='#6f42c1'>跨普通铁路</font>");
        }
        if (text.contains("防鸟患")) {
            text = text.replace("防鸟患", "<font color='#e83e8c'>防鸟患</font>");
        }
        if (text.contains("防雷")) {
            text = text.replace("防雷", "<font color='#d4237a'>防雷</font>");
        }
        if (text.contains("防风")) {
            text = text.replace("防风", "<font color='#fd7e14'>防风</font>");
        }
        if (text.contains("防山火")) {
            text = text.replace("防山火", "<font color='#ffc107'>防山火</font>");
        }
        if (text.contains("防外破")) {
            text = text.replace("防外破", "<font color='#28a745'>防外破</font>");
        }
        if (text.contains("防地灾")) {
            text = text.replace("防地灾", "<font color='#20c997'>防地灾</font>");
        }
        if (text.contains("防洪防汛")) {
            text = text.replace("防洪防汛", "<font color='#17a2b8'>防洪防汛</font>");
        }
        textView.setText(Html.fromHtml(text));
    }

    //设置是否可以修改
    public static void setStatus(ImageView edit, String audit_status) {
        switch (audit_status) {
            case "0":
            case "4":
                edit.setVisibility(View.VISIBLE);
                break;
            default:
                edit.setVisibility(View.GONE);
                break;
        }
    }
}
