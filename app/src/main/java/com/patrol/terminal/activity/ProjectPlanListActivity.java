package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.ProjectBoardBean;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：陈飞
 * 时间：2019/08/12 16:45
 */
public class ProjectPlanListActivity extends BaseActivity {
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
    @BindView(R.id.rv_project_plan)
    RecyclerView rvProjectPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_plan_list);
        ButterKnife.bind(this);
        titleName.setText("未开始的计划");

//        List<ProjectBoardBean> projectBoardBeans = initData();
//
//        rvProjectPlan.setLayoutManager(new LinearLayoutManager(this));
//        ProjectPlanAdapter adapter = new ProjectPlanAdapter(R.layout.item_project_plan, projectBoardBeans);
//        rvProjectPlan.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        rvProjectPlan.setAdapter(adapter);
    }

    private List<ProjectBoardBean> initData() {
        List<ProjectBoardBean> projectBoardBeans = SQLite.select().from(ProjectBoardBean.class).queryList();
        return projectBoardBeans;
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
