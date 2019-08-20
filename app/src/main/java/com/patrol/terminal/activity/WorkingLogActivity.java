package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//施工日志
public class WorkingLogActivity extends BaseActivity {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_log);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        titleName.setText("施工日志");
    }

    @OnClick({R.id.title_back, R.id.layout_construction_side, R.id.layout_supervisor_side, R.id.layout_building_side, R.id.tv_add})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.layout_construction_side:
                intent = new Intent(this, ConstructionSideActivity.class);
                intent.putExtra("logType", 1);
                startActivity(intent);
                break;
            case R.id.layout_supervisor_side:
                intent = new Intent(this, ConstructionSideActivity.class);
                intent.putExtra("logType", 2);
                startActivity(intent);
                break;
            case R.id.layout_building_side:
                intent = new Intent(this, ConstructionSideActivity.class);
                intent.putExtra("logType", 3);
                startActivity(intent);
                break;
            case R.id.tv_add:
                intent = new Intent(this, WorkingLogDetailActivity.class);
                intent.putExtra("logType", 1);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_ADDRESS_BOOK) {
            }
        }
    }
}
