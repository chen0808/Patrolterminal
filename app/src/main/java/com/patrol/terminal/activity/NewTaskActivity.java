package com.patrol.terminal.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.fragment.CarAllotFrgment;
import com.patrol.terminal.fragment.DayDisGroupFrgment;
import com.patrol.terminal.fragment.DayPlanAllotFrgment;
import com.patrol.terminal.fragment.GroupTaskFrgment;
import com.patrol.terminal.fragment.PersonalTaskFrgment;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
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

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
    @BindView(R.id.group_task_num)
    TextView groupTaskNum;
    @BindView(R.id.personal_task_num)
    TextView personalTaskNum;
    @BindView(R.id.group_num)
    TextView groupNum;
    @BindView(R.id.day_plan_num)
    TextView dayPlanNum;
    @BindView(R.id.car_num)
    TextView carNum;

    private List<String> mDataList = new ArrayList<>();
    private MyFragmentPagerAdapter taskPagerAdapter;
    private int year;
    private int month;
    private int day;
    private String depId = "";
    private Disposable subscribe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        ButterKnife.bind(this);
        initview();

    }

    private void initview() {
        titleName.setText("任务管理");
        List<Fragment> fragmentList = new ArrayList<>();
//        taskRg.setVisibility(View.VISIBLE);
        String mJobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
            depId = SPUtil.getDepId(this);
            fragmentList.add(new DayDisGroupFrgment());
            fragmentList.add(new CarAllotFrgment());
            fragmentList.add(new DayPlanAllotFrgment());
        } else {
            depId = "";
        }
        initdate();
        String time = DateUatil.getTime(new Date(System.currentTimeMillis()));
        SPUtil.putString(this, "date", "time", time);

        fragmentList.add(new GroupTaskFrgment());
        fragmentList.add(new PersonalTaskFrgment());
        taskPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(taskPagerAdapter);
        initMagicIndicator(mJobType);
//        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(0);
        int index = getIntent().getIntExtra("index", 0);
        viewPager.setCurrentItem(index);
        viewPager.setNoScroll(true);
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.startsWith("refreshGroupNum")) {
                    String[] split = type.split("@");
                    groupTaskNum.setText(split[1]);
                } else if (type.startsWith("refreshPersonalNum")) {
                    String[] split = type.split("@");
                    personalTaskNum.setText(split[1]);
                } else if (type.startsWith("refreshGroupTeamNum")) {
                    String[] split = type.split("@");
                    groupNum.setText(split[1]);
                } else if (type.startsWith("refreshHaveTask")) {
                    String[] split = type.split("@");
                    dayPlanNum.setText(split[1]);
                } else if (type.startsWith("refreshVehicleNum")) {
                    String[] split = type.split("@");
                    carNum.setText(split[1]);
                }
            }
        });
    }

    private void initMagicIndicator(String job) {
        if (job.contains(Constant.RUNNING_SQUAD_LEADER)) {
            mDataList.add("分组");
            mDataList.add("车辆");
            mDataList.add("计划");

            groupNum.setVisibility(View.VISIBLE);
            carNum.setVisibility(View.VISIBLE);
            dayPlanNum.setVisibility(View.VISIBLE);
        } else {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(UIUtil.dip2px(this, 60), UIUtil.dip2px(this, 30));
            layoutParams.setMargins(UIUtil.dip2px(this, 112), 0, UIUtil.dip2px(this, 30), 0);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(UIUtil.dip2px(this, 60), UIUtil.dip2px(this, 30));
            layoutParams1.setMargins(UIUtil.dip2px(this, 43), 0, UIUtil.dip2px(this, 60), 0);
            groupTaskNum.setLayoutParams(layoutParams);
            personalTaskNum.setLayoutParams(layoutParams1);
            groupTaskNum.setGravity(Gravity.BOTTOM);
            personalTaskNum.setGravity(Gravity.BOTTOM);
            groupNum.setVisibility(View.GONE);
            carNum.setVisibility(View.GONE);
            dayPlanNum.setVisibility(View.GONE);
        }

        mDataList.add("小组");
        mDataList.add("个人");
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
                if (job.contains(Constant.RUNNING_SQUAD_LEADER)) {
                    return UIUtil.dip2px(NewTaskActivity.this, 10);
                } else {
                    return UIUtil.dip2px(NewTaskActivity.this, 85);
                }

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

    //初始化日期
    public void initdate() {
        String time = DateUatil.getTime();
        String[] years = time.split("-");
        year = Integer.parseInt(years[0]);
        month = Integer.parseInt(years[1]);
        day = Integer.parseInt(years[2]);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
