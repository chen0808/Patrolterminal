package com.patrol.terminal.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.SPUtil;

import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.rl_exit)
    RelativeLayout rlExit;
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        titleName.setText("设置");
    }

    private AlertDialog dialog;

    @OnClick({R.id.title_back, R.id.rl_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.rl_exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exit_sure, null);
                builder.setView(dialogView);
                TextView sure = dialogView.findViewById(R.id.tv_sure);
                TextView cancel = dialogView.findViewById(R.id.tv_cancel);
                sure.setOnClickListener(this);
                cancel.setOnClickListener(this);
                dialog = builder.show();
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                SPUtil.clear(SettingActivity.this, Constant.USER);
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                setResult(RESULT_OK);
                dialog.dismiss();
                startActivity(intent);
                finish();
                break;
            case R.id.tv_cancel:
                dialog.dismiss();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
