package com.patrol.terminal.overhaul;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.OverhaulYearBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OverhaulYearDetailActivity extends BaseActivity {


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
    @BindView(R.id.year_plan_nuit)
    TextView yearPlanNuit;
    @BindView(R.id.year_plan_device_name)
    TextView yearPlanDeviceName;
    @BindView(R.id.voltage_grade)
    TextView voltageGrade;
    @BindView(R.id.voltage_grade_tv)
    TextView voltageGradeTv;
    @BindView(R.id.year_plan_yes)
    RadioButton yearPlanYes;
    @BindView(R.id.year_plan_no)
    RadioButton yearPlanNo;
    @BindView(R.id.year_plan_rg)
    RadioGroup yearPlanRg;
    @BindView(R.id.year_plan_day_num)
    TextView yearPlanDayNum;
    @BindView(R.id.year_plan_range)
    TextView yearPlanRange;
    @BindView(R.id.year_plan_source)
    TextView yearPlanSource;
    @BindView(R.id.year_plan_time_start)
    TextView yearPlanTimeStart;
    @BindView(R.id.year_plan_time_end)
    TextView yearPlanTimeEnd;
    @BindView(R.id.year_plan_time_before)
    TextView yearPlanTimeBefore;
    @BindView(R.id.year_plan_rish_level)
    TextView yearPlanRishLevel;
    @BindView(R.id.power_failure_ll)
    LinearLayout powerFailureLl;
    @BindView(R.id.year_plan_content)
    TextView yearPlanContent;

    private OverhaulYearBean overhaulYearBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overhaul_year_detail);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        overhaulYearBean = getIntent().getParcelableExtra("bean");
        yearPlanNuit.setText(overhaulYearBean.getApply_dep_name());
        yearPlanDeviceName.setText(overhaulYearBean.getLine_name());
        voltageGradeTv.setText(overhaulYearBean.getVoltage_level());

        if (overhaulYearBean.getIs_blackout().equals("0")) {
            yearPlanYes.setChecked(false);
            yearPlanNo.setChecked(true);
            powerFailureLl.setVisibility(View.GONE);
        }else if(overhaulYearBean.getIs_blackout().equals("1")) {
            yearPlanYes.setChecked(true);
            yearPlanNo.setChecked(false);
            powerFailureLl.setVisibility(View.VISIBLE);

            yearPlanDayNum.setText(overhaulYearBean.getBlackout_days() + "天");
            yearPlanRange.setText(overhaulYearBean.getBlackout_range());
            yearPlanSource.setText(overhaulYearBean.getTask_source());
            yearPlanTimeStart.setText(overhaulYearBean.getStart_time());
            yearPlanTimeEnd.setText(overhaulYearBean.getEnd_time());
            yearPlanTimeBefore.setText(overhaulYearBean.getLast_repair_time());
            yearPlanRishLevel.setText(overhaulYearBean.getRisk_level());
        }

        yearPlanContent.setText(overhaulYearBean.getRepair_content());
    }

    @OnClick({R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

        }
    }
}
