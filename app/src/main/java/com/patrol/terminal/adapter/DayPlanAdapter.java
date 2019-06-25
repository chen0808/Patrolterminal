package com.patrol.terminal.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DayListBean;
import com.patrol.terminal.utils.AdapterUtils;
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
        //图标
        TextView icon = viewHolder.getView(R.id.tv_icon);
        AdapterUtils.setIconText(icon, item.getDep_name());

        //线路
        viewHolder.setText(R.id.tv_line_name, item.getLine_name() + "   " + item.getName());

        //时间
//        if (item.getStart_time() != null && item.getEnd_time() != null) {
//            viewHolder.setText(R.id.tv_time, "时间：" + item.getStart_time() + " ~ " + item.getEnd_time());
//            viewHolder.setVisible(R.id.tv_time, true);
//        }

        //编辑
        ImageView edit = viewHolder.getView(R.id.iv_edit);
        AdapterUtils.setStatus(edit, item.getAudit_status());

        //审核状态
        HorizontalLineView horizontalLineView = viewHolder.getView(R.id.hlv_plan_state);
        horizontalLineView.setVisibility(View.GONE);

        //计划类型
        TextView tvContent = viewHolder.getView(R.id.tv_content);
        AdapterUtils.setText(tvContent, StringUtil.getTypeSign(item.getType_sign()));
        if ("1".equals(item.getAllot_status())) {
            viewHolder.setGone(R.id.plan_progressbar_ll, true);
            viewHolder.setGone(R.id.plan_progressbar_ll, true);
            viewHolder.setText(R.id.plan_progressbar_tv, "计划进度(" + item.getDone_num() + "/" + item.getAll_num() + ")：")
                    .setText(R.id.plan_progressbar_num, item.getDone_rate() + "%");
            ProgressBar progressBar = viewHolder.getView(R.id.plan_progressbar_probar);
            progressBar.setMax(item.getAll_num());
            progressBar.setProgress(item.getDone_num());
            if (Double.parseDouble(item.getDone_rate()) == 100) {
                viewHolder.setText(R.id.tv_line_state, "状态：已完成");
            } else {
                viewHolder.setText(R.id.tv_line_state, "状态：执行中");
            }
        } else {
            viewHolder.setGone(R.id.plan_progressbar_ll, false);
            viewHolder.setText(R.id.tv_line_state, "状态：未分配");
        }
        viewHolder.setVisible(R.id.tv_line_state, false);
        viewHolder.setVisible(R.id.iv_edit, false);
    }
}