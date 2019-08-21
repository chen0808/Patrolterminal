package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.utils.PickerUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：陈飞
 * 时间：2019/08/20 10:32
 */
public class DesignPlanAddActivity extends BaseActivity {

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
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    @BindView(R.id.et_project_name)
    EditText etProjectName;
    @BindView(R.id.et_plan_no)
    EditText etPlanNo;
    @BindView(R.id.et_plan_name)
    EditText etPlanName;
    @BindView(R.id.et_total_money)
    EditText etTotalMoney;
    @BindView(R.id.et_duty_user_name)
    EditText etDutyUserName;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rl_content)
    LinearLayout rlContent;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.rl_remark)
    LinearLayout rlRemark;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.defect_gridView)
    GridView defectGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_plan_add);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        titleName.setText("添加计划");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("提交");
        etPlanNo.setText("" + System.currentTimeMillis());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        tvStartTime.setText(simpleDateFormat.format(date));
    }

    @OnClick({R.id.title_back, R.id.title_setting, R.id.tv_start_time, R.id.tv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                break;
            case R.id.tv_start_time:
                PickerUtils.showDate(this, tvStartTime);
                break;
            case R.id.tv_end_time:
                PickerUtils.showDate(this, tvEndTime);
                break;
        }
    }
}
