package com.patrol.terminal.adapter;

import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.GroupOfDayBean;
import com.patrol.terminal.bean.TeamAndTaskBean;
import com.patrol.terminal.widget.PlanAllotView;

import java.util.List;

import androidx.annotation.Nullable;

public class PlanAllotTeamAdapter extends BaseQuickAdapter<TeamAndTaskBean, BaseViewHolder> {
    private boolean isShowLine;

    public PlanAllotTeamAdapter(int layoutResId, @Nullable List<TeamAndTaskBean> data, boolean isShowLine) {
        super(layoutResId, data);
        this.isShowLine = isShowLine;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, TeamAndTaskBean item) {
        viewHolder.addOnClickListener(R.id.iteam_group_team_name);
        List<GroupOfDayBean> dayPlanList = item.getLists();
        boolean check = item.isCheck();
        if (check){
            viewHolder.setTextColor(R.id.iteam_group_team_name,mContext.getResources().getColor(R.color.green));
        } else {
            viewHolder.setTextColor(R.id.iteam_group_team_name,mContext.getResources().getColor(R.color.color_33));
        }

        viewHolder.setText(R.id.iteam_group_team_name, item.getDuty_user_name() + "的小组");

        if(isShowLine){
            LinearLayout personal = viewHolder.getView(R.id.item_plan_allot_ll);
            personal.removeAllViews();
            for (int i = 0; i < dayPlanList.size(); i++) {
                GroupOfDayBean groupOfDayBean = dayPlanList.get(i);
                PlanAllotView view = new PlanAllotView(mContext, groupOfDayBean);
                personal.addView(view);
            }
        }
    }
}