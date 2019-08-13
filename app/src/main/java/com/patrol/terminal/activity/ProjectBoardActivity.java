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
import com.patrol.terminal.bean.ProjectBoardBean;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_board);
        ButterKnife.bind(this);
        titleName.setText("项目看板");

        List<ProjectBoardBean> projectBoardBeans = initData();

        rvProjectBoard.setLayoutManager(new LinearLayoutManager(this));
        ProjectBoardAdapter adapter = new ProjectBoardAdapter(R.layout.item_project_board, projectBoardBeans);
        rvProjectBoard.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(ProjectBoardActivity.this, ProjectPlanActivity.class));
            }
        });
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
            case R.id.ll_ed:
                Intent intent1 = new Intent(this, BudgetActivity.class);
                intent1.putExtra("type", 1);
                startActivity(intent1);
                break;
            case R.id.ll_ing:
                Intent intent2 = new Intent(this, BudgetActivity.class);
                intent2.putExtra("type", 2);
                startActivity(intent2);
                break;
        }
    }
}
