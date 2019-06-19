package com.patrol.terminal.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.fragment.GroupTaskFrgment;
import com.patrol.terminal.fragment.PersonalTaskFrgment;
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
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTaskActivity extends BaseActivity {


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
    @BindView(R.id.task_def)
    TextView taskDef;
    @BindView(R.id.task_dan)
    TextView taskDan;
    @BindView(R.id.task_cheak)
    TextView taskCheak;
    @BindView(R.id.task_rg)
    LinearLayout taskRg;
    @BindView(R.id.magic_indicator4)
    MagicIndicator magicIndicator4;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    private List<String> mDataList = new ArrayList<>();
    private MyFragmentPagerAdapter taskPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        ButterKnife.bind(this);
        initview();

    }

    private void initview() {
        titleName.setText("任务管理");
//        taskRg.setVisibility(View.VISIBLE);
        String time = DateUatil.getTime(new Date(System.currentTimeMillis()));
        SPUtil.putString(this, "date", "time", time);
        String job = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new GroupTaskFrgment());
        fragmentList.add(new PersonalTaskFrgment());
        taskPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(taskPagerAdapter);
        initMagicIndicator(job);
//        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(5);
        int index = getIntent().getIntExtra("index", 0);
        viewPager.setCurrentItem(index);
    }

    private void initMagicIndicator(String job) {
        mDataList.add("小组任务");
        mDataList.add("个人任务");
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
                linePagerIndicator.setColors(getResources().getColor(R.color.orange_vip));
                linePagerIndicator.setLineHeight(15);
                return linePagerIndicator;
            }
        });
        magicIndicator4.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(NewTaskActivity.this, 60);
            }
        });
        ViewPagerHelper.bind(magicIndicator4, viewPager);
    }


    @OnClick({R.id.title_back, R.id.task_def, R.id.task_dan, R.id.task_cheak})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.title_back:
                finish();
                break;
            case R.id.task_def:
                intent.setClass(this, DefectActivity.class);
                startActivity(intent);
                break;
            case R.id.task_dan:
                intent.setClass(this, TroubleActivity.class);
                startActivity(intent);
                break;
            case R.id.task_cheak:
                intent.setClass(this, YXCheckActivity.class);
                startActivity(intent);
                break;
        }
    }
}
