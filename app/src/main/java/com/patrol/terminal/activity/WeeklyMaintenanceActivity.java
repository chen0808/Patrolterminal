package com.patrol.terminal.activity;

import android.content.Intent;
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

/*工作负责人及班长上报周检修工作都是这个界面，  专责接受上报周检修工作也是这个界面*/
public class WeeklyMaintenanceActivity extends BaseActivity {
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
    @BindView(R.id.week_maintenace_tv)
    TextView weekMaintenaceTv;
    @BindView(R.id.other_tv)
    TextView otherTv;
    @BindView(R.id.work_ticket_tv)
    TextView workTicketTv;
    @BindView(R.id.control_card)
    TextView controlCard;
    @BindView(R.id.weekly_maintenance_rl)
    RelativeLayout weeklyMaintenanceRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_maintenance_activity);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        titleBack.setVisibility(View.GONE);
        titleName.setText("周检修工作");
    }

    @OnClick({R.id.week_maintenace_tv, R.id.other_tv, R.id.work_ticket_tv, R.id.control_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.week_maintenace_tv:
                break;
            case R.id.other_tv:
                //专责推送的时候会选择工作票类型，一共四种，暂时先跳转到第一种  TODO
                Intent intent = new Intent();
                intent.setClass(this, FirstWTicketActivity.class);
                startActivity(intent);
                break;
            case R.id.work_ticket_tv:

                break;
            case R.id.control_card:
                break;
        }
    }
}
