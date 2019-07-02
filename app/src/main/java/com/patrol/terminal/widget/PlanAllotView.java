package com.patrol.terminal.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.GroupOfDayBean;
import com.patrol.terminal.utils.RxRefreshEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlanAllotView extends LinearLayout {


    @BindView(R.id.day_plan_name)
    TextView dayPlanName;
    @BindView(R.id.day_plan_check_iv)
    ImageView dayPlanCheckIv;

    public PlanAllotView(Context context, GroupOfDayBean bean) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_day_plan_allot, this, true);
        ButterKnife.bind(inflate);
        dayPlanName.setText(bean.getLine_name()+bean.getName()+"任务");
        if (bean.isCheck() == true) {
            dayPlanCheckIv.setImageResource(R.mipmap.circle);
        } else {
            dayPlanCheckIv.setImageResource(R.mipmap.circle_no);
        }
        if ("1".equals(bean.getAllot_status())||"1".equals(bean.getIs_rob())){
            dayPlanName.setTextColor(context.getResources().getColor(R.color.color_69));
            dayPlanCheckIv.setVisibility(GONE);
        }else {
            dayPlanName.setTextColor(context.getResources().getColor(R.color.color_33));
            dayPlanCheckIv.setVisibility(VISIBLE);
            inflate.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.isCheck() == true) {
                        bean.setCheck(false);
                        dayPlanCheckIv.setImageResource(R.mipmap.circle_no);
                    } else {
                        bean.setCheck(true);
                        dayPlanCheckIv.setImageResource(R.mipmap.circle);
                    }
                    RxRefreshEvent.publishGrooup(bean);
                }
            });
        }


    }

}
