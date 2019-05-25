package com.patrol.terminal.overhaul;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.utils.SPUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//检修班年计划制定界面
public class OverhaulAddYearPlanActivity extends BaseActivity {

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
    EditText yearPlanNuit;
    @BindView(R.id.year_plan_device_name)
    EditText yearPlanDeviceName;
    @BindView(R.id.month_plan_month)
    TextView monthPlanMonth;
    @BindView(R.id.year_plan_)
    TextView yearPlan;
    @BindView(R.id.year_plan_yes)
    RadioButton yearPlanYes;
    @BindView(R.id.year_plan_no)
    RadioButton yearPlanNo;
    @BindView(R.id.year_plan_day_num)
    EditText yearPlanDayNum;
    @BindView(R.id.year_plan_range)
    TextView yearPlanRange;
    @BindView(R.id.year_plan_source)
    TextView yearPlanSource;
    @BindView(R.id.year_plan_time_start)
    TextView yearPlanTimeStart;
    @BindView(R.id.year_plan_time_end)
    TextView yearPlanTimeEnd;
    @BindView(R.id.year_plan_time_before)
    EditText yearPlanTimeBefore;
    @BindView(R.id.year_plan_rish_level)
    TextView yearPlanRishLevel;
    @BindView(R.id.power_failure_ll)
    LinearLayout powerFailureLl;
    @BindView(R.id.year_plan_content)
    EditText yearPlanContent;
    @BindView(R.id.year_plan_submit)
    TextView yearPlanSubmit;
    @BindView(R.id.year_plan_rg)
    RadioGroup yearPlanRg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overhaul_add_year_plan);
        ButterKnife.bind(this);
        initview();

    }

    private void initview() {
        titleName.setText("检修年计划制定");
        yearPlanRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.year_plan_no:
                        powerFailureLl.setVisibility(View.GONE);
                        break;
                    case R.id.year_plan_yes:
                        powerFailureLl.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    @OnClick({R.id.title_back,R.id.year_plan_submit})
    public void onViewClicked(View view)
    {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.year_plan_submit:
//                OverhaulYearBean bean = new OverhaulYearBean();
//                bean.setApply_unit(yearPlanNuit.getText().toString());
//                bean.setDevice_name(yearPlanDeviceName.getText().toString());
//                bean.setVoltage_rating(yearPlan.getText().toString());
//
//                if (yearPlanYes.isChecked()) {
//                    bean.setIs_blackout("1");
//                }else {
//                    bean.setIs_blackout("0");
//                }
//
//                bean.setBlackout_days(yearPlanDayNum.getText().toString());
//                bean.setBlackout_range( yearPlanRange.getText().toString());
//                bean.setTask_source(yearPlanSource.getText().toString());
//                bean.setApply_statime( yearPlanTimeStart.getText().toString());
//                bean.setApply_clotime(yearPlanTimeEnd.getText().toString());
//                bean.setLastblackout_time(yearPlanTimeBefore.getText().toString());
//                bean.setRisk_level(yearPlanRishLevel.getText().toString());
//                bean.setCheak_content(yearPlanContent.getText().toString());
//
//                saveYearPlan(bean);
                break;
        }
    }

    //添加检修年计划
    private void saveYearPlan(OverhaulYearBean bean) {
        BaseRequest.getInstance().getService()
                .addOverhaulYearPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<OverhaulYearBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<OverhaulYearBean> t) throws Exception {
//                        result = t.getResults();
//                        yearAdapter.setNewData(result);
//                        SPUtil.putString(getContext(), "date", "overhaulTime", time);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

    }
}
