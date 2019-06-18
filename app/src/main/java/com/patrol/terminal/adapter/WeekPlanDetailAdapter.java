package com.patrol.terminal.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DefectBean;
import com.patrol.terminal.utils.AdapterUtils;

import java.util.List;

public class WeekPlanDetailAdapter  extends BaseQuickAdapter<DefectBean, BaseViewHolder> {


    public WeekPlanDetailAdapter(int layoutResId, @Nullable List<DefectBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, DefectBean item) {

        switch (item.getType()){
            case 0:
                viewHolder.setText(R.id.item_plan_name,item.getContent())
                        .setText(R.id.item_plan_time,item.getFind_time());
                break;
            case 1:
            case 2:
                viewHolder.setText(R.id.item_plan_name,item.getLine_name()+item.getStart_name()+item.getContent())
                        .setText(R.id.item_plan_time,item.getFind_time());
                break;

            default:
                viewHolder.setText(R.id.item_plan_name,item.getContent())
                        .setText(R.id.item_plan_time,item.getFind_time());
                break;
        }
        TextView tvContent = viewHolder.getView(R.id.item_plan_name);
        AdapterUtils.setText(tvContent, tvContent.getText().toString());
    }
}
