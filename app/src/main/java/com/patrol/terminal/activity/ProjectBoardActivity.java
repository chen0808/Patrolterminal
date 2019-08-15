package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ProjectBoardAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.ProjectBoardBean;
import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private List<ProjectBoardBean> localBean;
    private double budgetTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_board);
        ButterKnife.bind(this);
        titleName.setText("项目看板");

        List<ProjectBoardBean> query = query();
        if (query == null || query.size() == 0) {
            saveLocalAsync();
            query = query();
        }

        tvProjectTotal.setText("" + query.size());
        for (int i = 0; i < query.size(); i++) {
            budgetTotal += query.get(i).getMoney();
        }
        tvBudgetTotal.setText("" + budgetTotal / 10000.0);
        tvProjectEd.setText("2");
        int projectIng = query.size() - 2;
        tvProjectIng.setText(String.valueOf(projectIng));
        tvPersentEd.setText(2 * 100 / query.size() + "%");
        tvPersentIng.setText(projectIng * 100 / query.size() + "%");
        rvProjectBoard.setLayoutManager(new LinearLayoutManager(this));
        ProjectBoardAdapter adapter = new ProjectBoardAdapter(R.layout.item_project_board, query);
        rvProjectBoard.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(ProjectBoardActivity.this, ProjectPlanActivity.class));
            }
        });
    }

    //异步存储到本地
    private void saveLocalAsync() {
        FlowManager.getDatabase(AppDataBase.class).beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                ProjectBoardBean localBean = new ProjectBoardBean();
                localBean.setId(1);
                localBean.setProject_name("秦皇岛市玉带湾居住小区(五期)工程");
                localBean.setDate_total(366);
                localBean.setDate_now(228);
                localBean.setMoney(32462.00);
                localBean.save();
                localBean.setId(2);
                localBean.setProject_name("汕头市湖南区峡山污水处理厂二期厂网一体建设及一期提标改造PPP项目");
                localBean.setDate_total(17);
                localBean.setDate_now(0);
                localBean.setMoney(643144.00);
                localBean.save();
                localBean.setId(3);
                localBean.setProject_name("武汉核建中核城");
                localBean.setDate_total(665);
                localBean.setDate_now(288);
                localBean.setMoney(164379.00);
                localBean.save();
                localBean.setId(4);
                localBean.setProject_name("三门县职业中等专业学校新校园迁建工程PPP项目");
                localBean.setDate_total(1044);
                localBean.setDate_now(325);
                localBean.setMoney(445634.00);
                localBean.save();
                localBean.setId(5);
                localBean.setProject_name("丽水盆地易涝区防洪排涝好溪堰水系整治三阶段工程项目");
                localBean.setDate_total(396);
                localBean.setDate_now(28);
                localBean.setMoney(645424.00);
                localBean.save();
            }
        }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
            }
        }).build().execute();
    }

    private List<ProjectBoardBean> query() {
        return localBean = SQLite.select().from(ProjectBoardBean.class).queryList();
    }

    @OnClick({R.id.title_back, R.id.ll_total, R.id.ll_ed, R.id.ll_ing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.ll_total:
                Intent intent = new Intent(this, BudgetActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                break;
            case R.id.ll_ing:
                Intent intent2 = new Intent(this, BudgetActivity.class);
                intent2.putExtra("type", 1);
                startActivity(intent2);
                break;
            case R.id.ll_ed:
                Intent intent1 = new Intent(this, BudgetActivity.class);
                intent1.putExtra("type", 2);
                startActivity(intent1);
                break;
        }
    }
}
