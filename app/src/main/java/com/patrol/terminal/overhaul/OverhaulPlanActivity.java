package com.patrol.terminal.overhaul;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.NoScrollViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//检修班计划管理界面
public class OverhaulPlanActivity extends BaseActivity {


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
    @BindView(R.id.magic_indicator4)
    MagicIndicator magicIndicator4;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private MyFragmentPagerAdapter taskPagerAdapter;
    private static final String[] CHANNELS = new String[]{"年计划", "月计划", "周计划", "周任务"};
    private static final String[] CHANNELS_01 = new String[]{"周任务"};
    private static final String[] CHANNELS_02 = new String[]{"周计划"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overhaul_plan);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("检修计划管理");
        List<Fragment> fragmentList = new ArrayList<>();
        String jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");

        if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED) || jobType.contains(Constant.MAINTENANCE_SUPERVISOR)) {   //检修专责能看年月周计划,周任务
            mDataList = Arrays.asList(CHANNELS);
            fragmentList.add(new OverhaulYearPlanFrgment());
            fragmentList.add(new OverhaulMonthPlanFrgment());
            fragmentList.add(new OverhaulWeekPlanFrgment());
            fragmentList.add(new OverhaulZzWeekTaskFrgment());
        } else if (jobType.contains(Constant.REFURBISHMENT_LEADER) || jobType.contains(Constant.REFURBISHMENT_TEMA_LEADER)    //班长,负责人能看周任务
                || jobType.contains(Constant.REFURBISHMENT_MEMBER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
            mDataList = Arrays.asList(CHANNELS_01);
            fragmentList.add(new OverhaulWeekTaskFrgment());
        } else if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)
                || jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED) || jobType.contains(Constant.SAFETY_SPECIALIZED)) {   //保电,验收,安全能查看周计划
            mDataList = Arrays.asList(CHANNELS_02);
            fragmentList.add(new OverhaulWeekPlanFrgment());
        }

        String time = DateUatil.getTime(new Date(System.currentTimeMillis()));
        SPUtil.putString(this, "date", "overhaulTime", time);
        int index = getIntent().getIntExtra("index", 0);
        taskPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(taskPagerAdapter);
        initMagicIndicator();
        viewPager.setCurrentItem(index);
        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(5);

    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.black_gray));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.orange_vip));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);

                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setColors(getResources().getColor(R.color.orange_vip));
                linePagerIndicator.setLineHeight(5);
                return linePagerIndicator;
            }
        });
        magicIndicator4.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(OverhaulPlanActivity.this, 15);
            }
        });
        ViewPagerHelper.bind(magicIndicator4, viewPager);
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
