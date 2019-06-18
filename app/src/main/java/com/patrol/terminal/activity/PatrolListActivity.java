package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.PatrolAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.PatrolListBean;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PatrolListActivity extends BaseActivity {

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
    @BindView(R.id.day_plan_rv)
    SwipeRecyclerView dayPlanRv;
    private PatrolAdapter patrolAdapter;
    private List<PatrolListBean> results=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_list);
        ButterKnife.bind(this);
        initview();
    }
    private void initview() {
        titleName.setText("巡视记录列表");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setVisibility(View.GONE);
        titleSettingIv.setVisibility(View.VISIBLE);
        titleSettingIv.setImageResource(R.mipmap.add_black);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        dayPlanRv.setLayoutManager(manager);

        patrolAdapter = new PatrolAdapter(R.layout.item_patrol_view, results);
        dayPlanRv.setAdapter(patrolAdapter);
    }
    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                startActivity(new Intent(this,PatrolAddActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWeekList();
    }

    public void getWeekList() {
        BaseRequest.getInstance().getService()
                .getPatrolList(null,   null,null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PatrolListBean>>(this) {

                    @Override
                    protected void onSuccees(BaseResult<List<PatrolListBean>> t) throws Exception {
                        results = t.getResults();
                        patrolAdapter.setNewData(results);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

}
