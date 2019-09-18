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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        ButterKnife.bind(this);
        titleName.setText("预算");

        String status = getIntent().getStringExtra("status");

        if (status.equals("16")) {
            llBuilding.setVisibility(View.GONE);
        } else if (status.equals("在建")) {
            status="";
            llFinish.setVisibility(View.GONE);
        }
        initData(status);
    }

    private void initData(String status) {
        BaseRequest.getInstance().getService()
                .getProjectBoardList(status)
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
        List<ProjectBoardBean> buildingBean = new ArrayList<>();
        List<ProjectBoardBean> finishBean = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getStatus().equals("16")) {
                budgetEd += results.get(i).getTotal_money();
                finishBean.add(results.get(i));
            } else {
                budgetIng += results.get(i).getTotal_money();
                buildingBean.add(results.get(i));
            }
        }
        tvBudgetIng.setText("¥" + budgetIng);
        Log.d("tag", "budgetIng" + budgetIng);

        tvBudgetEd.setText("¥" + budgetEd);

        rvBuilding.setLayoutManager(new LinearLayoutManager(this));
        rvBuilding.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvBuilding.setAdapter(new BudgetAdapter(R.layout.item_budget, buildingBean));

        rvFinish.setLayoutManager(new LinearLayoutManager(this));
        rvFinish.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvFinish.setAdapter(new BudgetAdapter(R.layout.item_budget, finishBean));

        tvProjectTotal.setText("项目总数:" + (results == null ? 0 : results.size()));
        tvBudgetTotal.setText("¥" + (budgetIng + budgetEd));
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
