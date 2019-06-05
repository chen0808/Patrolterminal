package com.patrol.terminal.overhaul;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.PickerUtils;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.TimeUtil;

import java.sql.Time;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


//检修班月计划添加和修订界面
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
    @BindView(R.id.month_plan_vo)
    TextView monthPlanVo;
    @BindView(R.id.dian_month_plan_rish_level)
    TextView dianMonthPlanRishLevel;
    @BindView(R.id.ll_blackout_day)
    LinearLayout llBlackoutDay;
    @BindView(R.id.ll_blackout_range)
    LinearLayout llBlackoutRange;
    @BindView(R.id.rg_need_blackout)
    RadioGroup rgNeedBlackout;

    private OverhaulYearBean overhaulYearBean;
    private int fromType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overhaul_add_month_plan);
        ButterKnife.bind(this);

        fromType = getIntent().getIntExtra("add_month_from_type", 0);  //fromType == 1是新增  fromType == 0是修改
        if (fromType == 0) {
            titleName.setText("检修月计划修改");
            monthPlanSubmit.setText("修改");
        } else if (fromType == 1) {
            titleName.setText("检修月计划制定");
            monthPlanSubmit.setText("提交");
        } else if (fromType == 2) {
            titleName.setText("检修周计划制定");
            monthPlanSubmit.setText("提交");
        }

        rgNeedBlackout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (monthPlanYes.isChecked()) {
                    llBlackoutDay.setVisibility(View.VISIBLE);
                    llBlackoutRange.setVisibility(View.VISIBLE);
                } else {
                    llBlackoutDay.setVisibility(View.GONE);
                    llBlackoutRange.setVisibility(View.GONE);
                }
            }
        });

        overhaulYearBean = getIntent().getParcelableExtra("bean");
        if (overhaulYearBean != null) {
            monthPlanNuit.setText(overhaulYearBean.getApply_dep_name());
            monthPlanNuit.setEnabled(false);
            monthPlanNuit.setTextColor(Color.BLACK);
            monthPlanDeviceName.setText(overhaulYearBean.getLine_name());
            monthPlanDeviceName.setEnabled(false);
            monthPlanDeviceName.setTextColor(Color.BLACK);
            monthPlanVo.setText(overhaulYearBean.getVoltage_level());
            monthPlanVo.setEnabled(true);
            monthPlanVo.setTextColor(Color.BLACK);
            if (overhaulYearBean.getIs_blackout().equals("0")) {
                monthPlanYes.setChecked(false);
                monthPlanNo.setChecked(true);
                powerFailureLl.setVisibility(View.GONE);
                llBlackoutDay.setVisibility(View.GONE);
                llBlackoutRange.setVisibility(View.GONE);
            } else if (overhaulYearBean.getIs_blackout().equals("1")) {
                monthPlanYes.setChecked(true);
                monthPlanNo.setChecked(false);
                llBlackoutDay.setVisibility(View.VISIBLE);
                llBlackoutRange.setVisibility(View.VISIBLE);
                powerFailureLl.setVisibility(View.VISIBLE);
                monthPlanDayNum.setText(String.valueOf(overhaulYearBean.getBlackout_days()));
                monthPlanRange.setText(overhaulYearBean.getBlackout_range());
                monthPlanSource.setText(overhaulYearBean.getTask_source());
                monthPlanTimeStart.setText(overhaulYearBean.getStart_time());
                monthPlanTimeEnd.setText(overhaulYearBean.getEnd_time());
                monthPlanRishLevel.setText(overhaulYearBean.getRisk_level());
                dianMonthPlanRishLevel.setText("2");

            }

            monthPlanContent.setText(overhaulYearBean.getRepair_content());
            monthPlanRemark.setText(overhaulYearBean.getRemark());
        }
    }

    @OnClick({R.id.title_back, R.id.month_plan_time_start, R.id.month_plan_time_end, R.id.month_plan_source,
            R.id.month_plan_range, R.id.month_plan_submit, R.id.month_plan_rish_level, R.id.dian_month_plan_rish_level, R.id.month_plan_vo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.month_plan_time_start:
                PickerUtils.showDate(OverhaulAddMonthPlanActivity.this, monthPlanTimeStart);
                break;

            case R.id.month_plan_time_end:
                PickerUtils.showDate(OverhaulAddMonthPlanActivity.this, monthPlanTimeEnd);
                break;

            case R.id.month_plan_source:
                showTaskDialog();
                break;

            case R.id.month_plan_vo:
                showDianyaLevel();
                break;

            case R.id.month_plan_submit:
                OverhaulYearBean updateOverhaulYearBean = new OverhaulYearBean();
                if (fromType == 1 || fromType == 2) {  //新增
                    String startTimeStr = monthPlanTimeStart.getText().toString();
                    if (!TextUtils.isEmpty(startTimeStr)) {
                        String times[] = startTimeStr.split("-");
                        updateOverhaulYearBean.setYear(Integer.valueOf(times[0]));
                        updateOverhaulYearBean.setMonth(Integer.valueOf(times[1]));
                        try {
                            updateOverhaulYearBean.setWeek(TimeUtil.getWeekOfDate(DateUatil.dateToLong(startTimeStr)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        updateOverhaulYearBean.setDay(Integer.valueOf(times[2]));
                    }

                    if (fromType == 2) {
                        updateOverhaulYearBean.setMonth_audit_status("2");
                        updateOverhaulYearBean.setWeek_audit_status("1");
                    }
                } else {   //修改
                    if (overhaulYearBean != null) {
                        updateOverhaulYearBean.setId(overhaulYearBean.getId());
                        updateOverhaulYearBean.setYear(overhaulYearBean.getYear());
                        updateOverhaulYearBean.setMonth(overhaulYearBean.getMonth());
                        updateOverhaulYearBean.setWeek(overhaulYearBean.getWeek());
                        updateOverhaulYearBean.setDay(overhaulYearBean.getDay());
                    }
                }

                updateOverhaulYearBean.setApply_dep_name(monthPlanNuit.getText().toString());
                updateOverhaulYearBean.setLine_name(monthPlanDeviceName.getText().toString());
                updateOverhaulYearBean.setVoltage_level(monthPlanVo.getText().toString());
                if (monthPlanYes.isChecked()) {
                    updateOverhaulYearBean.setIs_blackout("1");
                } else {
                    updateOverhaulYearBean.setIs_blackout("0");
                }

                if (!TextUtils.isEmpty(monthPlanDayNum.getText().toString())) {
                    String[] blackout_daysStr = monthPlanDayNum.getText().toString().split("天");
                    updateOverhaulYearBean.setBlackout_days(Integer.valueOf(blackout_daysStr[0]));
                }

                updateOverhaulYearBean.setBlackout_range(monthPlanRange.getText().toString());
                updateOverhaulYearBean.setTask_source(monthPlanSource.getText().toString());
                updateOverhaulYearBean.setStart_time(monthPlanTimeStart.getText().toString());
                updateOverhaulYearBean.setEnd_time(monthPlanTimeEnd.getText().toString());
                updateOverhaulYearBean.setRisk_level(monthPlanRishLevel.getText().toString());
                updateOverhaulYearBean.setRepair_content(monthPlanContent.getText().toString());
                updateOverhaulYearBean.setRemark(monthPlanRemark.getText().toString());

                updateOverhaulMonthList(updateOverhaulYearBean);
                break;

            case R.id.month_plan_rish_level:
                showRistDialog();
                break;

            case R.id.dian_month_plan_rish_level:
                showDianRistDialog();
                break;

            case R.id.month_plan_range:
                showTingdianDialog();
                break;
        }
    }

    private void updateOverhaulMonthList(OverhaulYearBean overhaulYearBean) {
        BaseRequest.getInstance().getService()
                .updateJxMonthPlan(overhaulYearBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulYearBean>>(this) {

                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulYearBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            //刷新界面
                            Toast.makeText(OverhaulAddMonthPlanActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                            RxRefreshEvent.publish("updateMonthPlan");
                            finish();


//                            result = t.getResults();
//                            monthAdapter.setNewData(result);
//                            for (int i = 0; i < result.size(); i++) {
//                                OverhaulYearBean overhaulYearBean = result.get(i);
//                                if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)&&"0".equals(overhaulYearBean.getMonth_audit_status())){   //专责   审核状态   //0:编制   1:待主管审核   2:审核通过    3:审核不通过
//                                    OverPlanReqBean bean=new OverPlanReqBean();
//                                    bean.setId(overhaulYearBean.getId());
//                                    bean.setMonth_audit_status("1");
//                                    list1.add(bean);
//                                }
//                                else if (jobType.contains(Constant.MAINTENANCE_SUPERVISOR)&&"1".equals(overhaulYearBean.getMonth_audit_status())){   //主管   审核状态  目前只做了审核通过  TODO
//                                    OverPlanReqBean bean=new OverPlanReqBean();
//                                    bean.setId(overhaulYearBean.getId());
//                                    bean.setMonth_audit_status("2");
//                                    bean.setWeek_audit_status("1");
//                                    list3.add(bean);
//                                }
//                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.e("fff", e.toString());
                    }
                });

    }

    private int dianyaCheckItem = 0;

    private void showDianyaLevel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OverhaulAddMonthPlanActivity.this);
        builder.setTitle("电压等级选择");
        String[] taskSourceNames = getResources().getStringArray(R.array.jx_dianya_level);

        builder.setSingleChoiceItems(taskSourceNames, dianyaCheckItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dianyaCheckItem = which;
                monthPlanVo.setText(taskSourceNames[dianyaCheckItem]);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int checkItem = 0;

    private void showTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OverhaulAddMonthPlanActivity.this);
        builder.setTitle("任务来源选择");
        String[] taskSourceNames = getResources().getStringArray(R.array.jx_task_source_names);

        builder.setSingleChoiceItems(taskSourceNames, checkItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkItem = which;
                monthPlanSource.setText(taskSourceNames[checkItem]);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int riskCheckItem = 0;

    private void showRistDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OverhaulAddMonthPlanActivity.this);
        builder.setTitle("作业风险等级选择");
        String[] taskSourceNames = getResources().getStringArray(R.array.jx_risk_names);

        builder.setSingleChoiceItems(taskSourceNames, riskCheckItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                riskCheckItem = which;
                monthPlanRishLevel.setText(taskSourceNames[riskCheckItem]);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private int dianRiskCheckItem = 0;

    private void showDianRistDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OverhaulAddMonthPlanActivity.this);
        builder.setTitle("电网风险等级选择");
        String[] taskSourceNames = getResources().getStringArray(R.array.jx_risk_names);

        builder.setSingleChoiceItems(taskSourceNames, dianRiskCheckItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dianRiskCheckItem = which;
                dianMonthPlanRishLevel.setText(taskSourceNames[dianRiskCheckItem]);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int tingdianCheckItem = 0;

    private void showTingdianDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OverhaulAddMonthPlanActivity.this);
        builder.setTitle("停电范围选择");
        String[] taskSourceNames = getResources().getStringArray(R.array.tingdian_range_names);

        builder.setSingleChoiceItems(taskSourceNames, tingdianCheckItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tingdianCheckItem = which;
                monthPlanRange.setText(taskSourceNames[tingdianCheckItem]);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
