package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {
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
    @BindView(R.id.iv_expand)
    ImageView ivExpand;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.level)
    Spinner level;
    @BindView(R.id.type)
    Spinner type;
    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.iv_et1)
    ImageView ivEt1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.iv_et2)
    ImageView ivEt2;
    @BindView(R.id.et3)
    EditText et3;
    @BindView(R.id.iv_et3)
    ImageView ivEt3;
    @BindView(R.id.et4)
    EditText et4;
    @BindView(R.id.iv_et4)
    ImageView ivEt4;
    @BindView(R.id.et5)
    EditText et5;
    @BindView(R.id.iv_et5)
    ImageView ivEt5;
    @BindView(R.id.et6)
    EditText et6;
    @BindView(R.id.iv_et6)
    ImageView ivEt6;
    @BindView(R.id.cb1)
    CheckBox cb1;
    @BindView(R.id.cb2)
    CheckBox cb2;
    @BindView(R.id.cb3)
    CheckBox cb3;
    @BindView(R.id.cb4)
    CheckBox cb4;
    @BindView(R.id.cb5)
    CheckBox cb5;
    @BindView(R.id.cb6)
    CheckBox cb6;
    @BindView(R.id.iv_item_save)
    ImageView ivItemSave;
    private String[] levelList = {"110kV", "35kV"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        titleName.setText("特殊属性编辑");
    }

    @OnClick({R.id.title_back, R.id.iv_expand, R.id.iv_et1, R.id.iv_et2, R.id.iv_et3, R.id.iv_et4, R.id.iv_et5, R.id.iv_et6, R.id.btn_add, R.id.iv_item_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.iv_expand:
                if (llContent.getVisibility() == View.VISIBLE) {
                    ivExpand.setImageResource(R.mipmap.next);
                    llContent.setVisibility(View.GONE);
                } else {
                    ivExpand.setImageResource(R.mipmap.btn_down);
                    llContent.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_et1:
                if (et1.getVisibility() == View.VISIBLE) {
                    et1.setVisibility(View.GONE);
                    cb1.setVisibility(View.VISIBLE);
                } else {
                    et1.setVisibility(View.VISIBLE);
                    cb1.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_et2:
                if (et2.getVisibility() == View.VISIBLE) {
                    et2.setVisibility(View.GONE);
                    cb2.setVisibility(View.VISIBLE);
                } else {
                    et2.setVisibility(View.VISIBLE);
                    cb2.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_et3:
                if (et3.getVisibility() == View.VISIBLE) {
                    et3.setVisibility(View.GONE);
                    cb3.setVisibility(View.VISIBLE);
                } else {
                    et3.setVisibility(View.VISIBLE);
                    cb3.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_et4:
                if (et4.getVisibility() == View.VISIBLE) {
                    et4.setVisibility(View.GONE);
                    cb4.setVisibility(View.VISIBLE);
                } else {
                    et4.setVisibility(View.VISIBLE);
                    cb4.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_et5:
                if (et5.getVisibility() == View.VISIBLE) {
                    et5.setVisibility(View.GONE);
                    cb5.setVisibility(View.VISIBLE);
                } else {
                    et5.setVisibility(View.VISIBLE);
                    cb5.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_et6:
                if (et6.getVisibility() == View.VISIBLE) {
                    et6.setVisibility(View.GONE);
                    cb6.setVisibility(View.VISIBLE);
                } else {
                    et6.setVisibility(View.VISIBLE);
                    cb6.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_add:
                Constant.isContent = true;
                finish();
                break;
            case R.id.iv_item_save:
                Constant.isContent = true;
                break;
        }
    }
}
