package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceiveTaskActivity extends BaseActivity {

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
    @BindView(R.id.personal_task_name)
    TextView personalTaskName;
    @BindView(R.id.personal_task_type)
    TextView personalTaskType;
    @BindView(R.id.personal_task_time)
    TextView personalTaskTime;
    @BindView(R.id.personal_task_personal)
    TextView personalTaskPersonal;
    @BindView(R.id.personal_task_tower)
    TextView personalTaskTower;
    @BindView(R.id.personal_task_content)
    TextView personalTaskContent;
    @BindView(R.id.task_refuse)
    TextView taskRefuse;
    @BindView(R.id.task_receive)
    TextView taskReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_rask);
        ButterKnife.bind(this);
        titleName.setText("任务接收");
    }

    @OnClick({R.id.title_back,R.id.task_refuse, R.id.task_receive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_refuse:
                finish();
                break;
            case R.id.task_receive:
                finish();
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }
}
