package com.patrol.terminal.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.fragment.DayPlanFrgment;
import com.patrol.terminal.fragment.MonthPlanFrgment;
import com.patrol.terminal.fragment.SafeAndQulityFrgment;
import com.patrol.terminal.fragment.WeekPlanFrgment;
import com.patrol.terminal.overhaul.OverhanDaidianFragment;
import com.patrol.terminal.overhaul.OverhanlPublishFragment;
import com.patrol.terminal.overhaul.OverhanlQiangxiuFragment;
import com.patrol.terminal.overhaul.OverhanlWorkFragment;
import com.patrol.terminal.overhaul.OverhanlWorkReadyFragment;
import com.patrol.terminal.overhaul.OverhaulWeekPlanFrgment;
import com.patrol.terminal.overhaul.OverhaulWeekTaskFrgment;
import com.patrol.terminal.overhaul.OverhaulYearPlanFrgment;
import com.patrol.terminal.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JXTotalActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

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
    @BindView(R.id.jx_total1)
    RadioButton jxTotal1;
    @BindView(R.id.jx_total2)
    RadioButton jxTotal2;
    @BindView(R.id.jx_total3)
    RadioButton jxTotal3;
    @BindView(R.id.jx_total4)
    RadioButton jxTotal4;
    @BindView(R.id.jx_total5)
    RadioButton jxTotal5;
    @BindView(R.id.jx_total6)
    RadioButton jxTotal6;
    @BindView(R.id.jx_viewpager)
    ViewPager jxViewpager;
    @BindView(R.id.jx_title)
    TextView jxTitle;
    @BindView(R.id.jx_rg1)
    RadioGroup jxRg1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxtotal);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("检修总览");
        jxRg1.setOnCheckedChangeListener(this);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();//左侧设置的间距
        int height = Utils.dip2px(this, 41);//处

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) jxTotal4.getLayoutParams();
        params.setMargins(-width, height,  Utils.dip2px(this, 1), 0);//宽度设置为屏幕的一半，高度为合适的高度值
        jxTotal4.setLayoutParams(params);


        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new OverhaulWeekTaskFrgment());
        fragmentList.add(new OverhanlWorkReadyFragment());
        fragmentList.add(new OverhanlWorkFragment());
        fragmentList.add(new OverhanDaidianFragment());
        fragmentList.add(new OverhanlQiangxiuFragment());
        fragmentList.add(new SafeAndQulityFrgment());
        MyFragmentPagerAdapter taskPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        jxViewpager.setAdapter(taskPagerAdapter);
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.jx_total1:
                jxViewpager.setCurrentItem(0);
                jxTitle.setText(jxTotal1.getText());
                break;
            case R.id.jx_total2:
                jxViewpager.setCurrentItem(1);
                jxTitle.setText(jxTotal2.getText());
                break;
            case R.id.jx_total3:
                jxViewpager.setCurrentItem(2);
                jxTitle.setText(jxTotal3.getText());
                break;
            case R.id.jx_total4:
                jxViewpager.setCurrentItem(3);
                jxTitle.setText(jxTotal4.getText());
                break;
            case R.id.jx_total5:
                jxViewpager.setCurrentItem(4);
                jxTitle.setText(jxTotal5.getText());
                break;
            case R.id.jx_total6:
                jxViewpager.setCurrentItem(5);
                jxTitle.setText(jxTotal6.getText());
                break;
        }

    }
}
