package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.ProjectBoardBean;
import com.patrol.terminal.bean.ProjectPlanBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：陈飞
 * 时间：2019/08/12 15:56
 */
public class ProjectPlanActivity extends BaseActivity {
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
    @BindView(R.id.tv_name_and_time)
    TextView tvNameAndTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rl_ready)
    RelativeLayout rlReady;
    @BindView(R.id.rl_ing)
    RelativeLayout rlIng;
    @BindView(R.id.rl_past)
    RelativeLayout rlPast;
    @BindView(R.id.rl_finish)
    RelativeLayout rlFinish;
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    @BindView(R.id.btn_gantt)
    Button btnGantt;
    @BindView(R.id.tv_date_total)
    TextView tvDateTotal;
    @BindView(R.id.tv_date_ing)
    TextView tvDateIng;
    @BindView(R.id.tv_date_delay)
    TextView tvDateDelay;
    private long startTime;
    private long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_plan);
        ButterKnife.bind(this);
        ProjectBoardBean bean = (ProjectBoardBean) getIntent().getSerializableExtra("bean");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            startTime = sdf.parse(bean.getStart_time()).getTime();
            endTime = sdf.parse(bean.getEnd_time()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long daysTotal = endTime / 24 / 60 / 60 / 1000 - startTime / 24 / 60 / 60 / 1000 + 1;
        long daysIng = System.currentTimeMillis() / 24 / 60 / 60 / 1000 - startTime / 24 / 60 / 60 / 1000;

        titleName.setText(bean.getName());
        tvNameAndTime.setText("总工期:" + daysTotal + "  进度:" + (daysIng * 100 / daysTotal) + "%");
        tvTime.setText("开始:" + bean.getStart_time() + "  截至:" + bean.getEnd_time());
        tvDateTotal.setText("" + daysTotal);
        tvDateIng.setText("" + daysIng);
        tvDateDelay.setText("" + (daysTotal - daysIng));
        initData(bean.getId());
    }

    private void initData(String id) {
        BaseRequest.getInstance().getService()
                .getProjectPlan(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ProjectPlanBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<ProjectPlanBean>> t) throws Exception {
                        List<ProjectPlanBean> results = t.getResults();
                        if (results != null && results.size() > 0) {
                            setData(results);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void setData(List<ProjectPlanBean> results) {

    }

    @OnClick({R.id.title_back, R.id.rl_ready, R.id.rl_ing, R.id.rl_past, R.id.rl_finish, R.id.btn_gantt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.rl_ready:
                Intent intent1 = new Intent(this, ProjectPlanListActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_ing:
                Intent intent2 = new Intent(this, ProjectPlanListActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_past:
                Intent intent3 = new Intent(this, ProjectPlanListActivity.class);
                startActivity(intent3);
                break;
            case R.id.rl_finish:
                Intent intent4 = new Intent(this, ProjectPlanListActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_gantt:
                Intent intent5 = new Intent(this, GanttActivity.class);
                startActivity(intent5);
                break;
        }
    }
}
