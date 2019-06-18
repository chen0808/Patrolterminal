package com.patrol.terminal.adapter;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.LineCheckActivity;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.HorizontalLineView;

import java.util.List;

public class NextMonthPlanAdapter extends BaseQuickAdapter<MonthPlanBean, BaseViewHolder> {

    private String jobType;

    public NextMonthPlanAdapter(int layoutResId, @Nullable List<MonthPlanBean> data, String jovType) {
        super(layoutResId, data);
        this.jobType = jovType;
    }

    public NextMonthPlanAdapter(int layoutResId, @Nullable List<MonthPlanBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, MonthPlanBean item) {
        //添加子控件的点击事件
        viewHolder.addOnClickListener(R.id.plan_to_change);

        if (item.getRepair_content() == null) {
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
//            switch (item.getType_sign()) {
//                case "4":
//                    viewHolder.setText(R.id.item_plan_date_tv, "特殊");
//                    viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_week_bg);
//                    break;
//                case "7":
//                    viewHolder.setText(R.id.item_plan_date_tv, "保电");
//                    viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_qing_bg);
//                    break;
//                case "2":
//                    viewHolder.setText(R.id.item_plan_date_tv, "故障");
//                    viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_yellow_bg);
//                    break;
//                case "1":
//                    viewHolder.setText(R.id.item_plan_date_tv, "定巡");
//                    viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_mon_bg);
//                    break;
//                default:
//                    viewHolder.setText(R.id.item_plan_date_tv, "定检");
//                    viewHolder.setBackgroundRes(R.id.item_plan_date_tv, R.drawable.plan_day_bg);
//                    break;
//            }

            //根据审核类型隐藏图标按钮
            switch (item.getAudit_status()) {
                case "0":
                case "4":
                    viewHolder.setVisible(R.id.plan_to_change, true);
                    break;
                default:
                    viewHolder.setVisible(R.id.plan_to_change, false);
                    break;
            }

            viewHolder.setGone(R.id.item_line_state, false);
            HorizontalLineView horizontalLineView = viewHolder.getView(R.id.item_plan_status);
            horizontalLineView.setStatus(item.getAudit_status());
            horizontalLineView.setVisibility(View.VISIBLE);
            viewHolder.setText(R.id.item_plan_device_name, item.getLine_name())
                    .setText(R.id.tv_start_time, "开始时间：" + item.getStart_time())
                    .setText(R.id.tv_end_time, "结束时间：" + item.getEnd_time())
                    .setText(R.id.item_plan_content, StringUtil.getTypeSign(item.getType_sign()));
            TextView tvContent = viewHolder.getView(R.id.item_plan_content);
            String text = tvContent.getText().toString();
            String colorText = setColor(text);
            tvContent.setText(Html.fromHtml(colorText));
        } else {
            viewHolder.setText(R.id.item_plan_date_tv, "保")
                    .setVisible(R.id.plan_to_change, true);
            viewHolder.setOnClickListener(R.id.plan_to_change, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LineCheckActivity.class);
                    intent.putExtra("from", Constant.FROM_MONTHPLAN_TO_ADDMONTH);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("year", item.getYear());
                    intent.putExtra("month", item.getMonth());
                    mContext.startActivity(intent);
                }
            });
            View view = viewHolder.getView(R.id.item_plan_status);
            view.setVisibility(View.GONE);
            viewHolder.setText(R.id.item_plan_device_name, item.getRepair_content())
                    .setText(R.id.item_plan_content, "停电区域 : " + item.getBlackout_range())
                    .setText(R.id.item_line_status, "停电时间 : " + item.getStart_time() + " - " + item.getEnd_time());
            ;
        }
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