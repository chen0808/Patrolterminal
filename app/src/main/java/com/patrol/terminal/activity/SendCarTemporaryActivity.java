package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.PickerUtils;
import com.patrol.terminal.widget.SignDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendCarTemporaryActivity extends BaseActivity {
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
    @BindView(R.id.iv_signature_pad)
    ImageView ivSignaturePad;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.ll_place)
    LinearLayout llPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_car_temporary);
        ButterKnife.bind(this);
        titleName.setText("临时用车申请单");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("申请");
    }

    @OnClick({R.id.title_back, R.id.title_setting, R.id.ll_date, R.id.ll_time, R.id.ll_place, R.id.iv_signature_pad})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                Toast.makeText(this, "申请成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_date:
                PickerUtils.showDate(this, tvDate);
                break;
            case R.id.ll_time:
                PickerUtils.showTime(this, tvTime);
                break;
            case R.id.ll_place:
                startActivityForResult(new Intent(this, MapActivity.class), Constant.MAP_REQUEST_CODE);
                break;
            case R.id.iv_signature_pad:
                SignDialog.show(this, ivSignaturePad);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.MAP_REQUEST_CODE) {
            String address = data.getStringExtra("address");
            tvPlace.setText(address);
        }
    }
}
