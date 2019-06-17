package com.patrol.terminal.adapter;

import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DayListBean;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.HorizontalLineView;

import java.util.List;

public class DayPlanAdapter extends BaseQuickAdapter<DayListBean, BaseViewHolder> {
    private int type = 1;

    public DayPlanAdapter(int layoutResId, @Nullable List<DayListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, DayListBean item) {
        switch (item.getDep_name()) {
            case "西固运维班":
                viewHolder.setText(R.id.item_plan_date_tv, "西固");
                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_week_bg);
                break;
            case "兰州运维班":
                viewHolder.setText(R.id.item_plan_date_tv, "兰州");
                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_qing_bg);
                break;
            case "新城运维班":
                viewHolder.setText(R.id.item_plan_date_tv, "新城");
                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_yellow_bg);
                break;
            case "盐海运维班":
                viewHolder.setText(R.id.item_plan_date_tv, "盐海");
                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_mon_bg);
                break;
            case "开永运维班":
                viewHolder.setText(R.id.item_plan_date_tv, "开永");
                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_date_bg);
                break;
            case "和华运维班":
                viewHolder.setText(R.id.item_plan_date_tv, "和华");
                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_red_bg);
                break;
        }
//        switch (item.getType_sign()){
//            case "4":
//                viewHolder.setText(R.id.item_plan_date_tv, "特殊");
//                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_week_bg);
//                break;
//            case "7":
//                viewHolder.setText(R.id.item_plan_date_tv, "保电");
//                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_qing_bg);
//                break;
//            case "2":
//                viewHolder.setText(R.id.item_plan_date_tv, "故障");
//                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_yellow_bg);
//                break;
//            case "1":
//                viewHolder.setText(R.id.item_plan_date_tv, "定巡");
//                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_mon_bg);
//                break;
//            default:
//                viewHolder.setText(R.id.item_plan_date_tv, "定检");
//                viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_day_bg);
//                break;
//        }
        if ("1".equals(item.getAllot_status())) {
            viewHolder.setGone(R.id.plan_progressbar_ll, true);
            viewHolder.setGone(R.id.plan_progressbar_ll, true);
            viewHolder.setText(R.id.plan_progressbar_tv, "计划进度(" + item.getDone_num() + "/" + item.getAll_num() + ") :")
                    .setText(R.id.plan_progressbar_num, item.getDone_rate() + "%");
            ProgressBar progressBar = viewHolder.getView(R.id.plan_progressbar_probar);
            progressBar.setMax(item.getAll_num());
            progressBar.setProgress(item.getDone_num());
            if (Double.parseDouble(item.getDone_rate()) == 100) {
                viewHolder.setText(R.id.item_line_status, "状态 : 已完成");
            } else {
                viewHolder.setText(R.id.item_line_status, "状态 : 执行中");
            }

            TextView tvContent = viewHolder.getView(R.id.item_plan_content);
            String text = tvContent.getText().toString();
            String colorText = setColor(text);
            tvContent.setText(Html.fromHtml(colorText));
        } else {
            viewHolder.setGone(R.id.plan_progressbar_ll, false);
            viewHolder.setText(R.id.item_line_status, "状态 : 未分配");
        }
        viewHolder.setVisible(R.id.item_line_state, false);
        viewHolder.setVisible(R.id.plan_to_change, false);

        HorizontalLineView view = viewHolder.getView(R.id.item_plan_status);


        view.setVisibility(View.GONE);
        viewHolder.setText(R.id.item_plan_device_name, item.getLine_name() + item.getName())
                .setText(R.id.item_plan_content, StringUtil.getTypeSign(item.getType_sign()));

//            viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, AddMonthPlanActivity.class);
//                    intent.putExtra("from", Constant.FROM_DAYPLAN_TO_ADDMONTH);
//                    intent.putExtra("week_id", item.getWeek_line_id());
//                    intent.putExtra("day_id", item.getDay_id());
//                    intent.putExtra("line_id", item.getLine_id());
//                    intent.putExtra("line_name", item.getLine_name());
//                    intent.putExtra("year", item.getYear() + "");
//                    intent.putExtra("month", item.getMonth() + "");
//                    intent.putExtra("day", item.getDay() + "");
//                    intent.putExtra("id", item.getId());
//                    intent.putExtra("type", item.getType_name());
//                    //intent.putExtra("from","week");
//                    mContext.startActivity(intent);
//                }
//            });

    }

    private String setColor(String text) {
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
        return text;
    }
}