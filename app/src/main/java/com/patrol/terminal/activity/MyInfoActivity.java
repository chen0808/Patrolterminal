package com.patrol.terminal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.SPUtil;

public class MyInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        initview();
    }

    private void initview() {
//        myDep.setText(SPUtil.getDepName(this));
//        myName.setText(SPUtil.getString(this, Constant.USER, Constant.USERNAME, ""));
    }
}
