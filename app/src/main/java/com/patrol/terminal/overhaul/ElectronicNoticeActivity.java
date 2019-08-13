package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.fragment.DefectNotRidFragment;
import com.patrol.terminal.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*电子公告*/
public class ElectronicNoticeActivity extends BaseActivity {
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
    @BindView(R.id.defect_rb1)
    RadioButton defectRb1;
    @BindView(R.id.defect_rb2)
    RadioButton defectRb2;
    @BindView(R.id.defect_tab_vg)
    NoScrollViewPager defectTabVg;
    @BindView(R.id.defect_tab_rg)
    RadioGroup defectTabRg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic_notice);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("电子公告");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingIv.setImageResource(R.mipmap.add_black);
        titleSettingTv.setText("");

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new InternalAnnounceFragment());
        fragmentList.add(new InternalAnnounceFragment());

        MyFragmentPagerAdapter CardPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        defectTabVg.setAdapter(CardPagerAdapter);
        defectTabVg.setNoScroll(true);
        defectTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.defect_rb1:
                        defectTabVg.setCurrentItem(0);
                        break;
                    case R.id.defect_rb2:
                        defectTabVg.setCurrentItem(1);
                        break;
                }
            }
        });
    }

    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                Intent intent = new Intent(this, ElectronicNoticeAddActivity.class);
                startActivity(intent);
                break;
        }
    }
}
