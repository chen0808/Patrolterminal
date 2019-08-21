package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ProjectBoardAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.ProjectBoardBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：陈飞
 * 时间：2019/08/12 09:52
 */
public class ProjectBoardActivity extends BaseActivity {
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
    @BindView(R.id.rv_project_board)
    RecyclerView rvProjectBoard;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;
    @BindView(R.id.ll_ed)
    LinearLayout llEd;
    @BindView(R.id.ll_ing)
    LinearLayout llIng;
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    @BindView(R.id.tv_project_total)
    TextView tvProjectTotal;
    @BindView(R.id.tv_project_ed)
    TextView tvProjectEd;
    @BindView(R.id.tv_persent_ed)
    TextView tvPersentEd;
    @BindView(R.id.tv_project_ing)
    TextView tvProjectIng;
    @BindView(R.id.tv_persent_ing)
    TextView tvPersentIng;
    @BindView(R.id.tv_budget_total)
    TextView tvBudgetTotal;
    private double budgetTotal = 0;
    private List<ProjectBoardBean> projectBoardEd = new ArrayList<>();
    private List<ProjectBoardBean> projectBoardIng = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_board);
        ButterKnife.bind(this);
        titleName.setText("项目看板");
        initData();
    }

    private void initData() {
        BaseRequest.getInstance().getService()
                .getProjectBoardList("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ProjectBoardBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<ProjectBoardBean>> t) throws Exception {
                        List<ProjectBoardBean> results = t.getResults();
                        if (results != null && results.size() > 0) {
                            setData(results);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void setData(List<ProjectBoardBean> results) {
        projectBoardEd.clear();
        projectBoardIng.clear();
        tvProjectTotal.setText("" + results.size());
        for (int i = 0; i < results.size(); i++) {
            budgetTotal += results.get(i).getTotal_money();
            if (results.get(i).getStatus().equals("16")) {
                projectBoardEd.add(results.get(i));
            } else {
                projectBoardIng.add(results.get(i));
            }
        }
        tvBudgetTotal.setText("" + budgetTotal / 10000.0);
        tvProjectEd.setText("" + projectBoardEd.size());
        int projectIng = projectBoardIng.size();
        tvProjectIng.setText(String.valueOf(projectIng));
        tvPersentEd.setText(2 * 100 / results.size() + "%");
        tvPersentIng.setText(projectIng * 100 / results.size() + "%");
        rvProjectBoard.setLayoutManager(new LinearLayoutManager(this));
        ProjectBoardAdapter adapter = new ProjectBoardAdapter(R.layout.item_project_board, results);
        rvProjectBoard.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ProjectBoardActivity.this, ProjectPlanActivity.class);
                intent.putExtra("bean", results.get(position));
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.title_back, R.id.ll_total, R.id.ll_ed, R.id.ll_ing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.ll_total:
                Intent intent = new Intent(this, BudgetActivity.class);
                intent.putExtra("status", "");
                startActivity(intent);
                break;
            case R.id.ll_ing:
                Intent intent2 = new Intent(this, BudgetActivity.class);
                intent2.putExtra("status", "0");
                startActivity(intent2);
                break;
            case R.id.ll_ed:
                Intent intent1 = new Intent(this, BudgetActivity.class);
                intent1.putExtra("status", "16");
                startActivity(intent1);
                break;
        }
    }
}
