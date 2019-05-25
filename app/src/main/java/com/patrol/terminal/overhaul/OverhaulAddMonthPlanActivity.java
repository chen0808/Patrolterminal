package com.patrol.terminal.overhaul;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//检修班月计划制定界面
public class OverhaulAddMonthPlanActivity extends AppCompatActivity {

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
    EditText monthPlanNuit;
    @BindView(R.id.month_plan_device_name)
    EditText monthPlanDeviceName;
    @BindView(R.id.month_plan_month)
    TextView monthPlanMonth;
    @BindView(R.id.month_plan_)
    TextView monthPlan;
    @BindView(R.id.month_plan_yes)
    RadioButton monthPlanYes;
    @BindView(R.id.month_plan_no)
    RadioButton monthPlanNo;
    @BindView(R.id.month_plan_day_num)
    EditText monthPlanDayNum;
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
    EditText monthPlanContent;
    @BindView(R.id.month_plan_remark)
    EditText monthPlanRemark;
    @BindView(R.id.month_plan_submit)
    TextView monthPlanSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overhaul_add_month_plan);
        ButterKnife.bind(this);
        titleName.setText("检修月计划制定");
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
