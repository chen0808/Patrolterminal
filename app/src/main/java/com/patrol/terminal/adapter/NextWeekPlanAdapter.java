package com.patrol.terminal.adapter;

import android.widget.ImageView;
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

public class NextWeekPlanAdapter extends BaseQuickAdapter<WeekListBean, BaseViewHolder> {

    private String jobType;

    public NextWeekPlanAdapter(int layoutResId, @Nullable List<WeekListBean> data, String state, String jovType) {
        super(layoutResId, data);
        this.jobType = jovType;
    }

    public NextWeekPlanAdapter(int layoutResId, @Nullable List<WeekListBean> data) {
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
        }else {
            viewHolder.setVisible(R.id.tv_time, false);
        }

//        编辑
//        ImageView edit = viewHolder.getView(R.id.iv_edit);
//        AdapterUtils.setStatus(edit, item.getAudit_status());
        viewHolder.setGone(R.id.iv_edit,false);

        //审核状态
        viewHolder.setVisible(R.id.tv_audit_status, true);
        TextView tvAuditStatus = viewHolder.getView(R.id.tv_audit_status);

        if ("0".equals(item.getDone_status())) {
            AdapterUtils.setText(tvAuditStatus, "审核状态：待提交审核");
        } else if ("1".equals(item.getDone_status())){
            AdapterUtils.setText(tvAuditStatus, "审核状态：待专责审核");
        }else if ("2".equals(item.getDone_status())) {
            AdapterUtils.setText(tvAuditStatus, "审核状态：待主管审核");
        }else if ("3".equals(item.getDone_status())){
            AdapterUtils.setText(tvAuditStatus, "审核状态：审核通过");
        }else if ("4".equals(item.getDone_status())){
            AdapterUtils.setText(tvAuditStatus, "审核状态：审核不通过");
        }

        //计划类型
        TextView tvContent = viewHolder.getView(R.id.tv_content);
        AdapterUtils.setText(tvContent, StringUtil.getTypeSign(item.getType_sign()));

    }

}