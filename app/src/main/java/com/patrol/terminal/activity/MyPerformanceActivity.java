package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyPerformanceActivity extends BaseActivity {
    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.rl_score_standard)
    RelativeLayout rlScoreStandard;
    @BindView(R.id.rl_score_add)
    RelativeLayout rlScoreAdd;
    @BindView(R.id.rl_score_delete)
    RelativeLayout rlScoreDelete;
    @BindView(R.id.tv_performance_list)
    TextView tvPerformanceList;
    @BindView(R.id.tv_score_rule)
    TextView tvScoreRule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_performance);
        ButterKnife.bind(this);
        titleName.setText("我的绩效");
    }

    @OnClick({R.id.title_back, R.id.rl_score_standard, R.id.rl_score_add, R.id.rl_score_delete, R.id.tv_score_rule, R.id.tv_performance_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.rl_score_standard:
                startActivity(new Intent(this, ScoreStandardActivity.class));
                break;
            case R.id.rl_score_add:
                startActivity(new Intent(this, ScoreAddActivity.class));
                break;
            case R.id.rl_score_delete:
                startActivity(new Intent(this, ScoreDeleteActivity.class));
                break;
            case R.id.tv_score_rule:
                startActivity(new Intent(this, ScoreRuleActivity.class));
                break;
            case R.id.tv_performance_list:
                startActivity(new Intent(this, PerformanceListActivity.class));
                break;
        }
    }

    @OnClick()
    public void onViewClicked() {
    }
}
