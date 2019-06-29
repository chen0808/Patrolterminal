package com.patrol.terminal.adapter;

import android.widget.ImageView;
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

public class NextDayPlanAdapter extends BaseQuickAdapter<DayListBean, BaseViewHolder> {



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
        viewHolder.setText(R.id.tv_line_name, item.getLine_name() + "   " + item.getName());

        //时间
//        if (item.getStart_time() != null && item.getEnd_time() != null) {
//            viewHolder.setText(R.id.tv_time, "时间：" + item.getStart_time() + " ~ " + item.getEnd_time());
//            viewHolder.setVisible(R.id.tv_time, true);
//        }



        //审核状态
        HorizontalLineView horizontalLineView = viewHolder.getView(R.id.hlv_plan_state);
        horizontalLineView.setStatus(item.getAudit_status());


        //计划类型
        TextView tvContent = viewHolder.getView(R.id.tv_content);
        AdapterUtils.setText(tvContent, StringUtil.getTypeSign(item.getType_sign()));
    }

}