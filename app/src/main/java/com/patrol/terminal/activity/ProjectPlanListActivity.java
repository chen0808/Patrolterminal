package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ProjectPlanAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.ProjectBoardBean;

import java.util.ArrayList;
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

        List<ProjectBoardBean> projectBoardBeans = initData();

        rvProjectPlan.setLayoutManager(new LinearLayoutManager(this));
        ProjectPlanAdapter adapter = new ProjectPlanAdapter(R.layout.item_project_plan, projectBoardBeans);
        rvProjectPlan.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvProjectPlan.setAdapter(adapter);
    }

    private List<ProjectBoardBean> initData() {
        List<ProjectBoardBean> projectBoardBeans = new ArrayList<>();
        projectBoardBeans.add(new ProjectBoardBean("秦皇岛市玉带湾居住小区(五期)工程", 366, 228));
        projectBoardBeans.add(new ProjectBoardBean("汕头市湖南区峡山污水处理厂二期厂网一体建设及一期提标改造PPP项目", 17, 0));
        projectBoardBeans.add(new ProjectBoardBean("武汉核建中核城", 665, 228));
        projectBoardBeans.add(new ProjectBoardBean("三门县职业中等专业学校新校园迁建工程PPP项目", 1044, 0));
        projectBoardBeans.add(new ProjectBoardBean("丽水盆地易涝区防洪排涝好溪堰水系整治三阶段工程项目", 396, 28));
        return projectBoardBeans;
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
