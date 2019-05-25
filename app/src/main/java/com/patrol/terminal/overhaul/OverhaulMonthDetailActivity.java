package com.patrol.terminal.overhaul;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulYearBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OverhaulMonthDetailActivity extends BaseActivity {
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
    @BindView(R.id.month_plan_nuit)
    TextView monthPlanNuit;
    @BindView(R.id.month_plan_device_name)
    TextView monthPlanDeviceName;
    @BindView(R.id.voltage_grade)
    TextView voltageGrade;
    @BindView(R.id.voltage_grade_tv)
    TextView voltageGradeTv;
    @BindView(R.id.month_plan_yes)
    RadioButton monthPlanYes;
    @BindView(R.id.month_plan_no)
    RadioButton monthPlanNo;
    @BindView(R.id.month_plan_day_num)
    TextView monthPlanDayNum;
    @BindView(R.id.month_plan_range)
    TextView monthPlanRange;
    @BindView(R.id.month_plan_source)
    TextView monthPlanSource;
    @BindView(R.id.month_plan_time_start)
    TextView monthPlanTimeStart;
    @BindView(R.id.month_plan_time_end)
    TextView monthPlanTimeEnd;
    @BindView(R.id.month_plan_rish_level)
    TextView monthPlanRishLevel;
    @BindView(R.id.power_failure_ll)
    LinearLayout powerFailureLl;
    @BindView(R.id.month_plan_content)
    TextView monthPlanContent;
    @BindView(R.id.month_plan_remark)
    TextView monthPlanRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overhaul_month_detail_plan);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        OverhaulYearBean overhaulYearBean = getIntent().getParcelableExtra("bean");
        monthPlanNuit.setText(overhaulYearBean.getApply_dep_name());
        monthPlanDeviceName.setText(overhaulYearBean.getLine_name());
        voltageGradeTv.setText(overhaulYearBean.getVoltage_level());

        if (overhaulYearBean.getIs_blackout().equals("0")) {
            monthPlanYes.setChecked(false);
            monthPlanNo.setChecked(true);
            powerFailureLl.setVisibility(View.GONE);
        } else if (overhaulYearBean.getIs_blackout().equals("1")) {
            monthPlanYes.setChecked(true);
            monthPlanNo.setChecked(false);
            powerFailureLl.setVisibility(View.VISIBLE);
        }

        monthPlanDayNum.setText(overhaulYearBean.getBlackout_days() + "天");
        monthPlanRange.setText(overhaulYearBean.getBlackout_range());
        monthPlanSource.setText(overhaulYearBean.getTask_source());
        monthPlanTimeStart.setText(overhaulYearBean.getStart_time());
        monthPlanTimeEnd.setText(overhaulYearBean.getEnd_time());
        monthPlanRishLevel.setText(overhaulYearBean.getRisk_level());   //风险等级
        monthPlanRemark.setText(overhaulYearBean.getRemark());

        monthPlanContent.setText(overhaulYearBean.getRepair_content());

    }

    @OnClick(R.id.title_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
