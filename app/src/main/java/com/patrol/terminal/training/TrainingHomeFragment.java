package com.patrol.terminal.training;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.overhaul.OverhaulMonthPlanFrgment;
import com.patrol.terminal.overhaul.OverhaulPlanActivity;
import com.patrol.terminal.overhaul.OverhaulWeekPlanFrgment;
import com.patrol.terminal.overhaul.OverhaulYearPlanFrgment;
import com.patrol.terminal.utils.Constant;
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
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;

public class TrainingHomeFragment extends BaseFragment {
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
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    private Activity mActivity;
    private MyFragmentPagerAdapter taskPagerAdapter;
    private List<String> mDataList;
    private static final String[] CHANNELS = new String[]{"培训计划", "培训任务", "考试任务"};


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.training_plan, container, false);

        return view;
    }

    @Override
    protected void initData() {
        titleName.setText("培训管理");
        mActivity = getActivity();
        titleBack.setVisibility(View.GONE);

        List<Fragment> fragmentList = new ArrayList<>();

        mDataList = Arrays.asList(CHANNELS);
        fragmentList.add(new TrainingPlanFrgment());
        fragmentList.add(new TrainingTaskFrgment());
        fragmentList.add(new ExamTaskFrgment());

        taskPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager()/*getSupportFragmentManager()*/, fragmentList);
        viewPager.setAdapter(taskPagerAdapter);
        initMagicIndicator();

        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(5);

    }


    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);

                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.white));
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
                linePagerIndicator.setColors(Color.WHITE);
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(mActivity, 15);
            }
        });
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @OnClick(R.id.title_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                break;
        }
    }
}
