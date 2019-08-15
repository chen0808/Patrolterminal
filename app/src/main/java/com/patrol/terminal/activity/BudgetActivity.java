package com.patrol.terminal.activity;

import android.os.Bundle;
import android.util.Log;
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
import com.patrol.terminal.bean.ProjectBoardBean_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

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
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    @BindView(R.id.tv_project_total)
    TextView tvProjectTotal;
    @BindView(R.id.tv_budget_total)
    TextView tvBudgetTotal;
    @BindView(R.id.tv_budget_ing)
    TextView tvBudgetIng;
    @BindView(R.id.tv_budget_ed)
    TextView tvBudgetEd;
    private double budgetIng = 0;
    private double budgetEd = 0;
    private List<ProjectBoardBean> projectBoardBeans1;
    private List<ProjectBoardBean> projectBoardBeans2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        ButterKnife.bind(this);
        titleName.setText("预算");

        int type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 0:
                projectBoardBeans1 = init1();
                projectBoardBeans2 = init2();
                break;
            case 1:
                llFinish.setVisibility(View.GONE);
                projectBoardBeans1 = init1();
                break;
            case 2:
                llBuilding.setVisibility(View.GONE);
                projectBoardBeans2 = init2();
                break;
        }

        tvProjectTotal.setText("项目总数:" + ((projectBoardBeans1 == null ? 0 : projectBoardBeans1.size()) + (projectBoardBeans2 == null ? 0 : projectBoardBeans2.size())));
        tvBudgetTotal.setText("¥" + (budgetIng + budgetEd));
    }


    private List<ProjectBoardBean> init1() {
        List<ProjectBoardBean> projectBoardBeans1 = initData1();
        for (int i = 0; i < projectBoardBeans1.size(); i++) {
            budgetIng += projectBoardBeans1.get(i).getMoney();
        }
        tvBudgetIng.setText("¥" + budgetIng);
        Log.d("tag", "budgetIng" + budgetIng);
        rvBuilding.setLayoutManager(new LinearLayoutManager(this));
        rvBuilding.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvBuilding.setAdapter(new BudgetAdapter(R.layout.item_budget, projectBoardBeans1));
        return projectBoardBeans1;
    }

    private List<ProjectBoardBean> init2() {
        List<ProjectBoardBean> projectBoardBeans2 = initData2();
        for (int i = 0; i < projectBoardBeans2.size(); i++) {
            budgetEd += projectBoardBeans2.get(i).getMoney();
        }
        tvBudgetEd.setText("¥" + budgetEd);
        Log.d("tag", "budgetEd" + budgetEd);
        rvFinish.setLayoutManager(new LinearLayoutManager(this));
        rvFinish.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvFinish.setAdapter(new BudgetAdapter(R.layout.item_budget, projectBoardBeans2));
        return projectBoardBeans2;
    }

    private List<ProjectBoardBean> initData1() {
        List<ProjectBoardBean> projectBoardBeans = SQLite.select().from(ProjectBoardBean.class).where(ProjectBoardBean_Table.id.lessThan(3)).queryList();
        return projectBoardBeans;
    }

    private List<ProjectBoardBean> initData2() {
        List<ProjectBoardBean> projectBoardBeans = SQLite.select().from(ProjectBoardBean.class).where(ProjectBoardBean_Table.id.greaterThan(2)).queryList();
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
