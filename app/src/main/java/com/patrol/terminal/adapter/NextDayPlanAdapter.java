package com.patrol.terminal.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DayListBean;
import com.patrol.terminal.utils.AdapterUtils;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.HorizontalLineView;

import java.util.List;

import androidx.annotation.Nullable;

public class NextDayPlanAdapter extends BaseQuickAdapter<DayListBean, BaseViewHolder> {

    private String jobType;

    public NextDayPlanAdapter(int layoutResId, @Nullable List<DayListBean> data, String state, String jovType) {
        super(layoutResId, data);
        this.jobType = jovType;
    }

    public NextDayPlanAdapter(int layoutResId, @Nullable List<DayListBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, DayListBean item) {
        //添加子控件的点击事件
        viewHolder.addOnClickListener(R.id.plan_to_change);

        //图标
        TextView icon = viewHolder.getView(R.id.tv_icon);
        AdapterUtils.setIconText(icon, item.getDep_name());

        //线路
        viewHolder.setText(R.id.tv_line_name,item.getLine_name());

        //时间
//        if (item.getStart_time()!=null&&item.getEnd_time()!=null){
//            viewHolder.setText(R.id.tv_time, "开始时间：" + item.getStart_time() + "   结束时间：" + item.getEnd_time());
//        }

        //编辑
        ImageView edit = viewHolder.getView(R.id.iv_edit);
        AdapterUtils.setStatus(edit, item.getAudit_status());

        //审核状态
        HorizontalLineView horizontalLineView = viewHolder.getView(R.id.hlv_plan_state);
        horizontalLineView.setStatus(item.getAudit_status());

        //计划类型
        TextView tvContent = viewHolder.getView(R.id.tv_content);
        AdapterUtils.setText(tvContent, StringUtil.getTypeSign(item.getType_sign()));
    }

}