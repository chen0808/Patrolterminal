package com.patrol.terminal.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.PatrolDetailBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PatrolDetailActivity extends BaseActivity {
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
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type_name)
    TextView tvTypeName;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.tv_msg)
    TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getIntent().getStringExtra("id");
        setContentView(R.layout.activity_partol_detail);
        ButterKnife.bind(this);
        initData(id);
    }

    private void initData(String id) {
        BaseRequest.getInstance().getService().getPatrolDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<PatrolDetailBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<PatrolDetailBean> t) throws Exception {
                        tvName.setText("计划名称：" + t.getResults().getName());
                        tvTypeName.setText("计划类型：" + t.getResults().getTypeName());
                        tvId.setText("计划ID：" + t.getResults().getId());
                        tvStart.setText("起始杆塔：" + t.getResults().getTStart());
                        tvEnd.setText("终点杆塔：" + t.getResults().getTEnd());
                        tvMsg.setText("备注：" + t.getResults().getDetail());
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.d("TAG", e.getMessage());
                    }
                });
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
