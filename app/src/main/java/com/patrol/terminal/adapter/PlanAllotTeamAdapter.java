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
    private int type = 1;

    public PlanAllotTeamAdapter(int layoutResId, @Nullable List<TeamAndTaskBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, TeamAndTaskBean item) {
        List<GroupOfDayBean> dayPlanList = item.getLists();
        boolean check = item.isCheck();
        if (check==true){
            viewHolder.setTextColor(R.id.iteam_group_team_name,mContext.getResources().getColor(R.color.green));
        }else {
            viewHolder.setTextColor(R.id.iteam_group_team_name,mContext.getResources().getColor(R.color.color_33));
        }
        LinearLayout personal = viewHolder.getView(R.id.item_plan_allot_ll);
        personal.removeAllViews();
        viewHolder.setText(R.id.iteam_group_team_name, item.getDuty_user_name() + "的小组");
        for (int i = 0; i < dayPlanList.size(); i++) {
            GroupOfDayBean groupOfDayBean = dayPlanList.get(i);
            PlanAllotView view = new PlanAllotView(mContext, groupOfDayBean);
            personal.addView(view);
        }


    }
}