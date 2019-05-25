package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UIListActivity extends BaseActivity {
    @BindView(R.id.btn_zero_monitering)
    Button btnZeroMonitering;
    @BindView(R.id.btn_grounded_electronic_measurement)
    Button btnGroundedElectronicMeasurement;
    @BindView(R.id.btn_infrared_thermometry)
    Button btnInfraredThermometry;
    @BindView(R.id.btn_incline_thermometry)
    Button btnInclineThermometry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uilist);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_zero_monitering, R.id.btn_grounded_electronic_measurement, R.id.btn_infrared_thermometry, R.id.btn_incline_thermometry})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_zero_monitering:
                startActivity(new Intent(this, JueYuanZiLingZhiJianCeActivity.class));
                break;
            case R.id.btn_grounded_electronic_measurement:
                startActivity(new Intent(this, JiediDianZuCeLiangActicivity.class));
                break;
            case R.id.btn_infrared_thermometry:
                startActivity(new Intent(this, HongWaiCeWenActivity.class));
                break;
            case R.id.btn_incline_thermometry:
                startActivity(new Intent(this, XieGanTaQingXieCeWenActivity.class));
                break;
        }
    }
}
