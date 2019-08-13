package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.BudgetAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.ProjectBoardBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：陈飞
 * 时间：2019/08/12 11:44
 */
public class BudgetActivity extends BaseActivity {
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
    @BindView(R.id.rv_building)
    RecyclerView rvBuilding;
    @BindView(R.id.ll_building)
    LinearLayout llBuilding;
    @BindView(R.id.rv_finish)
    RecyclerView rvFinish;
    @BindView(R.id.ll_finish)
    LinearLayout llFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        ButterKnife.bind(this);
        titleName.setText("预算");

        int type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 1:
                llFinish.setVisibility(View.GONE);
                break;
            case 2:
                llBuilding.setVisibility(View.GONE);
                break;
        }

        List<ProjectBoardBean> projectBoardBeans1 = initData1();
        rvBuilding.setLayoutManager(new LinearLayoutManager(this));
        rvBuilding.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvBuilding.setAdapter(new BudgetAdapter(R.layout.item_budget, projectBoardBeans1));

        List<ProjectBoardBean> projectBoardBeans2 = initData2();
        rvFinish.setLayoutManager(new LinearLayoutManager(this));
        rvFinish.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvFinish.setAdapter(new BudgetAdapter(R.layout.item_budget, projectBoardBeans2));
    }

    private List<ProjectBoardBean> initData1() {
        List<ProjectBoardBean> projectBoardBeans = new ArrayList<>();
        projectBoardBeans.add(new ProjectBoardBean("秦皇岛市玉带湾居住小区(五期)工程", 1243535.00));
        projectBoardBeans.add(new ProjectBoardBean("汕头市湖南区峡山污水处理厂二期厂网一体建设及一期提标改造PPP项目", 146300.15));
        projectBoardBeans.add(new ProjectBoardBean("武汉核建中核城", 6354631.00));
        projectBoardBeans.add(new ProjectBoardBean("三门县职业中等专业学校新校园迁建工程PPP项目", 41646.05));
        projectBoardBeans.add(new ProjectBoardBean("丽水盆地易涝区防洪排涝好溪堰水系整治三阶段工程项目", 4643348.46));
        return projectBoardBeans;
    }

    private List<ProjectBoardBean> initData2() {
        List<ProjectBoardBean> projectBoardBeans = new ArrayList<>();
        projectBoardBeans.add(new ProjectBoardBean("秦皇岛市玉带湾居住小区(五期)工程", 1243535.00));
        projectBoardBeans.add(new ProjectBoardBean("丽水盆地易涝区防洪排涝好溪堰水系整治三阶段工程项目", 4643348.46));
        return projectBoardBeans;
    }

    @OnClick({R.id.title_back, R.id.ll_building, R.id.ll_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.ll_building:
                if (rvBuilding.getVisibility() == View.VISIBLE) {
                    rvBuilding.setVisibility(View.GONE);
                } else {
                    rvBuilding.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_finish:
                if (rvFinish.getVisibility() == View.VISIBLE) {
                    rvFinish.setVisibility(View.GONE);
                } else {
                    rvFinish.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
