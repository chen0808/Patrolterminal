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
import com.patrol.terminal.fragment.DefectIsRidFragment;
import com.patrol.terminal.fragment.DefectNotInFragment;
import com.patrol.terminal.fragment.DefectNotRidFragment;
import com.patrol.terminal.fragment.TroubleIsRidFragment;
import com.patrol.terminal.fragment.TroubleNotInFragment;
import com.patrol.terminal.fragment.TroubleNotRidFragment;
import com.patrol.terminal.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*隐患库Tab选项卡*/
public class TroubleTabActivity extends BaseActivity {
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
    @BindView(R.id.trouble_rb1)
    RadioButton troubleRb1;
    @BindView(R.id.trouble_rb2)
    RadioButton troubleRb2;
    @BindView(R.id.trouble_rb3)
    RadioButton troubleRb3;
    @BindView(R.id.trouble_tab_vg)
    NoScrollViewPager troubleTabVg;
    @BindView(R.id.trouble_tab_rg)
    RadioGroup troubleTabRg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble_tab);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("隐患库");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new TroubleNotInFragment());
        fragmentList.add(new TroubleNotRidFragment());
        fragmentList.add(new TroubleIsRidFragment());

        MyFragmentPagerAdapter CardPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        troubleTabVg.setAdapter(CardPagerAdapter);
        troubleTabVg.setNoScroll(true);
        troubleTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.trouble_rb1:
                        troubleTabVg.setCurrentItem(0);
                        break;
                    case R.id.trouble_rb2:
                        troubleTabVg.setCurrentItem(1);
                        break;
                    case R.id.trouble_rb3:
                        troubleTabVg.setCurrentItem(2);
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
