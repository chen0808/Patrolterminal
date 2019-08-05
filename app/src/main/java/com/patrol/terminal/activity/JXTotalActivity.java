package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JXTotalActivity extends BaseActivity {

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
    @BindView(R.id.jx_total1)
    RadioButton jxTotal1;
    @BindView(R.id.jx_total2)
    RadioButton jxTotal2;
    @BindView(R.id.jx_total3)
    RadioButton jxTotal3;
    @BindView(R.id.jx_total4)
    RadioButton jxTotal4;
    @BindView(R.id.jx_total5)
    RadioButton jxTotal5;
    @BindView(R.id.jx_total6)
    RadioButton jxTotal6;
    @BindView(R.id.jx_viewpager)
    ViewPager jxViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxtotal);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("检修总览");
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
