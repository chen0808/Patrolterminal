package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {
    @BindView(R.id.main_to_task)
    LinearLayout mainToTask;
    @BindView(R.id.main_to_plan)
    LinearLayout mainToPlan;
    @BindView(R.id.main_to_auditing)
    LinearLayout mainToAuditing;
    @BindView(R.id.main_to_total)
    LinearLayout mainToTotal;
    @BindView(R.id.main_history)
    TextView mainHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.main_to_task, R.id.main_to_plan, R.id.main_to_auditing, R.id.main_to_total, R.id.main_history})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.main_to_task:
                startActivity(new Intent(this,TaskListActivity.class));
                break;
            case R.id.main_to_plan:
                startActivity(new Intent(this,PlanListActivity.class));
                break;
            case R.id.main_to_auditing:
                startActivity(new Intent(this,TaskListActivity.class));
                break;
            case R.id.main_to_total:
                startActivity(new Intent(this,TaskListActivity.class));
                break;
            case R.id.main_history:
                break;
        }
    }
}
