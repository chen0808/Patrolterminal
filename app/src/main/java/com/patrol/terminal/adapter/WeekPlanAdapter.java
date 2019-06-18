package com.patrol.terminal.adapter;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.WeekListBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.HorizontalLineView;

import java.util.List;

public class WeekPlanAdapter extends BaseQuickAdapter<WeekListBean, BaseViewHolder> {

    public WeekPlanAdapter(int layoutResId, @Nullable List<WeekListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, WeekListBean item) {
        //图标
        TextView icon = viewHolder.getView(R.id.tv_icon);
        AdapterUtils.setIconText(icon, item.getDep_name());

        //线路
        viewHolder.setText(R.id.tv_line_name, item.getLine_name() + "   " + item.getName());

        //时间
        if (item.getStart_time() != null && item.getEnd_time() != null) {
            viewHolder.setText(R.id.tv_time, "时间：" + item.getStart_time() + " ~ " + item.getEnd_time());
            viewHolder.setVisible(R.id.tv_time, true);
        }

        //编辑
        ImageView edit = viewHolder.getView(R.id.iv_edit);
        AdapterUtils.setStatus(edit, item.getAudit_status());

        //审核状态
        HorizontalLineView horizontalLineView = viewHolder.getView(R.id.hlv_plan_state);
        horizontalLineView.setStatus(item.getAudit_status());

        //计划类型
        TextView tvContent = viewHolder.getView(R.id.tv_content);
        AdapterUtils.setText(tvContent, StringUtil.getTypeSign(item.getType_sign()));

        if ("0".equals(item.getDone_status())) {
            viewHolder.setText(R.id.tv_allot_status, "分配状态: 未分配");
        } else {
            viewHolder.setText(R.id.tv_allot_status, "分配状态:已分配");
        }

        if ("1".equals(item.getAllot_status())) {
            viewHolder.setGone(R.id.plan_progressbar_ll, true);
            viewHolder.setText(R.id.plan_progressbar_tv, "计划进度(" + item.getDone_num() + "/" + item.getAll_num() + ") :")
                    .setText(R.id.plan_progressbar_num, item.getDone_rate() + "%");
            ProgressBar progressBar = viewHolder.getView(R.id.plan_progressbar_probar);
            progressBar.setMax(item.getAll_num());
            progressBar.setProgress(item.getDone_num());
        } else {
            viewHolder.setGone(R.id.plan_progressbar_ll, false);
        }
        viewHolder.setVisible(R.id.tv_allot_status, true);
        viewHolder.setVisible(R.id.iv_detail, true);
        viewHolder.setVisible(R.id.iv_edit, false);
    }
}