package com.patrol.terminal.overhaul;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//检修班周计划制定界面
public class OverhaulAddWeekPlanActivity extends BaseActivity {

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
    @BindView(R.id.week_plan_device_name)
    EditText weekPlanDeviceName;
    @BindView(R.id.month_plan_month)
    TextView monthPlanMonth;
    @BindView(R.id.week_plan_time)
    TextView weekPlanTime;
    @BindView(R.id.week_plan_content)
    EditText weekPlanContent;
    @BindView(R.id.week_plan_remark)
    EditText weekPlanRemark;
    @BindView(R.id.week_plan_submit)
    TextView weekPlanSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overhaul_add_week_plan);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
