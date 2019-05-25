package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.RfInfo;
import com.patrol.terminal.widget.ProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RfidActivity extends BaseActivity {
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
    @BindView(R.id.tv_line_name)
    TextView tvLineName;
    @BindView(R.id.tv_ground)
    TextView tvGround;
    @BindView(R.id.tv_tower_model)
    TextView tvTowerModel;
    @BindView(R.id.tv_tower_calls)
    TextView tvTowerCalls;
    @BindView(R.id.tv_span)
    TextView tvSpan;
    @BindView(R.id.tv_producer)
    TextView tvProducer;
    @BindView(R.id.tv_tower_height)
    TextView tvTowerHeight;
    @BindView(R.id.tv_corner_degrees)
    TextView tvCornerDegrees;
    @BindView(R.id.tv_destory_date)
    TextView tvDestoryDate;
    @BindView(R.id.tv_use_date)
    TextView tvUseDate;
    @BindView(R.id.tv_use_status)
    TextView tvUseStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid);
        ButterKnife.bind(this);
        titleName.setText("扫描杆塔信息");
        String rfId = getIntent().getStringExtra("rf_id");
        ProgressDialog.show(this, false, "加载中...");
        BaseRequest.getInstance().getService()
                .getRfInfo(rfId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<RfInfo>(this) {

                    @Override
                    protected void onSuccees(BaseResult<RfInfo> t) throws Exception {
                        ProgressDialog.cancle();
                        RfInfo results = t.getResults();
                        tvName.setText(results.getName());
                        tvLineName.setText(results.getLine_name());
                        tvGround.setText(results.getGround());
                        tvTowerModel.setText(results.getTower_model());
                        tvProducer.setText(results.getProducer());
                        tvCornerDegrees.setText(results.getCorner_degrees());
                        tvUseDate.setText(results.getUse_date());
                        tvUseStatus.setText(results.getUse_status());
                        tvTowerCalls.setText(results.getTower_calls() + "m");
                        tvSpan.setText(results.getSpan() + "m");
                        tvTowerHeight.setText(results.getTower_height() + "m");
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
