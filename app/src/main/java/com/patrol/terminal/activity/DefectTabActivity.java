package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.fragment.ControlToolFragment;
import com.patrol.terminal.fragment.DefectNotInFragment;
import com.patrol.terminal.fragment.NewControlQualityFragment;
import com.patrol.terminal.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*缺陷库Tab选项卡*/
public class DefectTabActivity extends BaseActivity {
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
    @BindView(R.id.defect_rb3)
    RadioButton defectRb3;
    @BindView(R.id.defect_tab_vg)
    NoScrollViewPager defectTabVg;
    @BindView(R.id.defect_tab_rg)
    RadioGroup defectTabRg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defect_tab);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("缺陷库");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new DefectNotInFragment());
        fragmentList.add(new DefectNotInFragment());
        fragmentList.add(new DefectNotInFragment());
//        fragmentList.add(new NewControlQualityFragment());
//        fragmentList.add(new ControlToolFragment());
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
                    case R.id.defect_rb3:
                        defectTabVg.setCurrentItem(2);
                        break;
                }
            }
        });
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
