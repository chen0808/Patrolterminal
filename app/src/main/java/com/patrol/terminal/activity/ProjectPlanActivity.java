package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：陈飞
 * 时间：2019/08/12 15:56
 */
public class ProjectPlanActivity extends BaseActivity {
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
    @BindView(R.id.tv_name_and_time)
    TextView tvNameAndTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rl_ready)
    RelativeLayout rlReady;
    @BindView(R.id.rl_ing)
    RelativeLayout rlIng;
    @BindView(R.id.rl_past)
    RelativeLayout rlPast;
    @BindView(R.id.rl_finish)
    RelativeLayout rlFinish;
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    @BindView(R.id.btn_gantt)
    Button btnGantt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_plan);
        ButterKnife.bind(this);
        titleName.setText("计划");
    }

    @OnClick({R.id.title_back, R.id.rl_ready, R.id.rl_ing, R.id.rl_past, R.id.rl_finish, R.id.btn_gantt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.rl_ready:
                Intent intent1 = new Intent(this, ProjectPlanListActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_ing:
                Intent intent2 = new Intent(this, ProjectPlanListActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_past:
                Intent intent3 = new Intent(this, ProjectPlanListActivity.class);
                startActivity(intent3);
                break;
            case R.id.rl_finish:
                Intent intent4 = new Intent(this, ProjectPlanListActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_gantt:
                Intent intent5 = new Intent(this, GanttActivity.class);
                startActivity(intent5);
                break;
        }
    }
}
