package com.patrol.terminal.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.PlanNumBean;
import com.patrol.terminal.fragment.DayPlanFrgment;
import com.patrol.terminal.fragment.MonthPlanFrgment;
import com.patrol.terminal.fragment.WeekPlanFrgment;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.TimeUtil;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * home计划
 */
public class NewPlanActivity extends BaseActivity {


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
    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.task_def)
    TextView taskDef;
    @BindView(R.id.task_dan)
    TextView taskDan;
    @BindView(R.id.task_cheak)
    TextView taskCheak;
    @BindView(R.id.task_rg)
    LinearLayout taskRg;
    @BindView(R.id.month_plan_num)
    TextView monthPlanNum;
    @BindView(R.id.week_plan_num)
    TextView weekPlanNum;
    @BindView(R.id.day_plan_num)
    TextView dayPlanNum;

    private List<String> mDataList = Arrays.asList(CHANNELS);
    private MyFragmentPagerAdapter taskPagerAdapter;
    private static final String[] CHANNELS = new String[]{"月计划", "周计划", "日计划"};
    private int year;
    private int month;
    private int day;
    private int week;
    private String depId="";
    private Disposable subscribe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plan);
        ButterKnife.bind(this);
        initview();

    }

    private void initview() {
        titleName.setText("运行计划管理");
        String mJobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
            depId = SPUtil.getDepId(this);
        }
        initdate();
        getPlanNum();
        String time = DateUatil.getTime(new Date(System.currentTimeMillis()));
        SPUtil.putString(this, "date", "time", time);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MonthPlanFrgment());//月计划
        fragmentList.add(new WeekPlanFrgment());//周计划
        fragmentList.add(new DayPlanFrgment()); //日计划

        taskPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(taskPagerAdapter);
        initMagicIndicator();

//        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(5);
        int index = getIntent().getIntExtra("index", 0);
        viewPager.setCurrentItem(index);
        String from = getIntent().getStringExtra("from");
        if ("todoMonth".equals(from)) {
            viewPager.setCurrentItem(0);
        } else if ("todoWeek".equals(from)) {
            viewPager.setCurrentItem(1);
        }
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.startsWith("refreshMonthNum")) {
                    String[] split = type.split("@");
                    monthPlanNum.setText(split[1]);
                } else if (type.startsWith("refreshWeekNum")) {
                    String[] split = type.split("@");
                    weekPlanNum.setText(split[1]);
                } else if (type.startsWith("refreshDayNum")) {
                    String[] split = type.split("@");
                    dayPlanNum.setText(split[1]);
                }
            }
        });

    }

    //初始化日期
    public void initdate() {
        String time = DateUatil.getTime();
        String[] years = time.split("-");
        year = Integer.parseInt(years[0]);
        month = Integer.parseInt(years[1]);
        day = Integer.parseInt(years[2]);
        week = TimeUtil.getCurrWeek();
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
                simplePagerTitleView.setPadding(0,26, 0,  0);
                simplePagerTitleView.setTextSize(13);
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
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(NewPlanActivity.this, 50);
            }
        });
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    //获取月，周，日计划个数
    public void getPlanNum() {

        BaseRequest.getInstance().getService()
                .getPlanNum(depId, year, month, day, week)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<PlanNumBean>(this) {

                    @Override
                    protected void onSuccees(BaseResult<PlanNumBean> t) throws Exception {
                        if (t.getCode() == 1) {
                            PlanNumBean results = t.getResults();
                            monthPlanNum.setText(results.getMonth()+"");
                            weekPlanNum.setText(results.getWeek()+"");
                            dayPlanNum.setText(results.getDay()+"");
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe!=null){
            subscribe.dispose();
        }
    }
}
