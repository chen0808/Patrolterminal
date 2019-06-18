package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.widget.FlowGroupView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskAppointActivity extends BaseActivity {


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
    @BindView(R.id.week_plan_name)
    EditText weekPlanName;
    @BindView(R.id.week_plan_type)
    TextView weekPlanType;
    @BindView(R.id.week_plan_class)
    TextView weekPlanClass;
    @BindView(R.id.week_num)
    TextView weekNum;
    @BindView(R.id.task_group)
    FlowGroupView taskGroup;
    @BindView(R.id.add_incontinuity_tower_group)
    ImageView addIncontinuityTowerGroup;
    @BindView(R.id.task_group_personal_ll)
    LinearLayout taskGroupPersonalLl;
    @BindView(R.id.add_incontinuity_tower)
    ImageView addIncontinuityTower;
    @BindView(R.id.incontinuity_tower)
    FlowGroupView incontinuityTower;
    @BindView(R.id.task_personal_ll)
    LinearLayout taskPersonalLl;
    @BindView(R.id.group_task_submit)
    TextView groupTaskSubmit;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_appoint);
        ButterKnife.bind(this);
    }

    public void initview() {

        String from = getIntent().getStringExtra("from");
        if ("personal".equals(from)) {
            type = 1;
            taskGroup.setVisibility(View.GONE);
            titleName.setText("个人任务指派");
        } else {
            type = 0;
            titleName.setText("小组任务指派");
        }
    }

    @OnClick({R.id.title_back, R.id.group_task_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.group_task_submit:
                break;
        }
    }
}
