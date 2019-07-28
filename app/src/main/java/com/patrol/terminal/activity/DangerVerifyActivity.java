package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.widget.DangerSubmitView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DangerVerifyActivity extends BaseActivity {

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
    @BindView(R.id.danger_type)
    DangerSubmitView dangerType;
    @BindView(R.id.danger_find_dep)
    DangerSubmitView dangerFindDep;
    @BindView(R.id.danger_line_name)
    DangerSubmitView dangerLineName;
    @BindView(R.id.danger_line_level)
    DangerSubmitView dangerLineLevel;
    @BindView(R.id.danger_tower_name)
    DangerSubmitView dangerTowerName;
    @BindView(R.id.danger_find_time)
    DangerSubmitView dangerFindTime;
    @BindView(R.id.danger_more_ll)
    LinearLayout dangerMoreLl;
    @BindView(R.id.danger_special_content)
    DangerSubmitView dangerSpecialContent;
    @BindView(R.id.danger_special_info)
    LinearLayout dangerSpecialInfo;
    @BindView(R.id.danger_submit_no)
    TextView dangerSubmitNo;
    @BindView(R.id.danger_submit_yes)
    TextView dangerSubmitYes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_verify);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("隐患审核");
        fanglei();
    }

    public void fanglei(){
        DangerSubmitView view1=new DangerSubmitView(this,"完成状态：","");
        DangerSubmitView view2=new DangerSubmitView(this,"处理时间：","");
        DangerSubmitView view3=new DangerSubmitView(this,"厂家：","");
        DangerSubmitView view4=new DangerSubmitView(this,"处理措施：","");
        DangerSubmitView view5=new DangerSubmitView(this,"备注：","");
        dangerMoreLl.addView(view1);
        dangerMoreLl.addView(view2);
        dangerMoreLl.addView(view3);
        dangerMoreLl.addView(view4);
        dangerMoreLl.addView(view5);
    }
    @OnClick({R.id.title_back, R.id.danger_submit_no, R.id.danger_submit_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.danger_submit_no:
                break;
            case R.id.danger_submit_yes:
                break;
        }
    }
}
