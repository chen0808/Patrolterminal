package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SafeCheckMainActivity extends BaseActivity {

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
    @BindView(R.id.check_ll)
    LinearLayout checkLl;
    @BindView(R.id.reform_ll)
    LinearLayout reformLl;
    @BindView(R.id.recheck_ll)
    LinearLayout recheckLl;
    @BindView(R.id.nature_of_inspection_ll)
    LinearLayout natureOfInspectionLl;
    @BindView(R.id.add_iv)
    ImageView addIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safe_check_main_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleName.setText("安全");
    }

    @OnClick({R.id.title_back, R.id.check_ll, R.id.reform_ll, R.id.recheck_ll, R.id.nature_of_inspection_ll, R.id.add_iv})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.check_ll:
                intent.setClass(SafeCheckMainActivity.this, CheckProjectActivity.class);
                break;
            case R.id.reform_ll:
                //break;
            case R.id.recheck_ll:
                //break;
            case R.id.nature_of_inspection_ll:
                //break;
            case R.id.add_iv:
                intent.setClass(SafeCheckMainActivity.this, AddSafeCheckActivity.class);
                break;

        }
        startActivity(intent);
    }
}
