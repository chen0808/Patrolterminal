package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.MonthPlanBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpecialPlanDetailActivity extends BaseActivity {

    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_iv)
    ImageView titleSettingIv;
    @BindView(R.id.title_setting_tv)
    TextView titleSettingTv;
    @BindView(R.id.title_setting)
    RelativeLayout titleSetting;
    @BindView(R.id.special_plan_name)
    TextView specialPlanName;
    @BindView(R.id.tv_line_name)
    TextView tvLineName;
    @BindView(R.id.special_plan_begin_time)
    TextView specialPlanBeginTime;
    @BindView(R.id.special_plan_end_time)
    TextView specialPlanEndTime;
    @BindView(R.id.special_plan_task)
    TextView specialPlanTask;
    @BindView(R.id.special_plan_level)
    TextView specialPlanLevel;
    @BindView(R.id.special_plan_dep)
    TextView specialPlanDep;
    @BindView(R.id.special_plan_isblackout)
    TextView specialPlanIsblackout;
    @BindView(R.id.special_plan_range)
    TextView specialPlanRange;
    @BindView(R.id.special_plan_remark)
    TextView specialPlanRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_plan_detail);
        ButterKnife.bind(this);
        String from = getIntent().getStringExtra("from");
        if ("month".equals(from)){
            MonthPlanBean bean = (MonthPlanBean) getIntent().getSerializableExtra("bean");
        titleName.setText("计划详情");
        specialPlanName.setText(bean.getRepair_content());
        tvLineName.setText("线路名称 : "+bean.getLine_name());
        specialPlanBeginTime.setText("开始时间 : "+bean.getStart_time());
        specialPlanEndTime.setText("结束时间 : "+bean.getEnd_time());
        specialPlanLevel.setText("电压等级 : "+bean.getVoltage_level());
        specialPlanDep.setText("申请单位 :" +bean.getDep_name());
        specialPlanTask.setText("任务来源 : "+bean.getTask_source());
        specialPlanRemark.setText("备注 : "+bean.getRemark());
        if ("1".equals(bean.getIs_blackout())){
            specialPlanIsblackout.setText("是否停电 : 是");
            specialPlanRange.setText("停电区域 :"+bean.getBlackout_range());
        }else {
            specialPlanIsblackout.setText("是否停电 : 否");
        }
        }else if ("week".equals(from)){
//            WeekListBean bean = getIntent().getParcelableExtra("bean");
//            titleName.setText("计划详情");
//            specialPlanName.setText(bean.getLine_name());
//            tvLineName.setText("线路名称 : "+bean.getLine_name());
//            specialPlanBeginTime.setText("开始时间 : "+bean.getBegin_time());
//            specialPlanEndTime.setText("结束时间 : "+bean.getEnd_time());
//            specialPlanLevel.setText("电压等级 : "+bean.getVoltage_level());
//            specialPlanDep.setText("申请单位 : "+bean.getDep_name());
//            specialPlanTask.setText("任务来源 : "+bean.getTask_source());
//            specialPlanRemark.setText("备注 : "+bean.getRemark());
//            if ("1".equals(bean.getIs_blackout())){
//                specialPlanIsblackout.setText("是否停电 : 是");
//                specialPlanRange.setText("停电区域 :"+bean.getBlackout_range());
//            }else {
//                specialPlanIsblackout.setText("是否停电 : 否");
//            }

        }else if ("day".equals(from)){
//            DayListBean bean = getIntent().getParcelableExtra("bean");
//            titleName.setText("计划详情");
//            specialPlanName.setText(bean.getPlan_name());
//            tvLineName.setText("线路名称 :"+bean.getLine_name());
//            specialPlanBeginTime.setText("开始时间 : "+bean.getBegin_time());
//            specialPlanEndTime.setText("结束时间 : "+bean.getEnd_time());
//            specialPlanLevel.setText("电压等级 : "+bean.getVoltage_level());
//            specialPlanDep.setText("申请单位 : "+bean.getDep_name());
//            specialPlanTask.setText("任务来源 : " +bean.getTask_source());
//            specialPlanRemark.setText("备注 : "+bean.getRemark());
//            if ("1".equals(bean.getIs_blackout())){
//                specialPlanIsblackout.setText("是否停电 : 是");
//                specialPlanRange.setText("停电区域 : "+bean.getBlackout_range());
//            }else {
//                specialPlanIsblackout.setText("是否停电 : 否");
//            }
        }
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
