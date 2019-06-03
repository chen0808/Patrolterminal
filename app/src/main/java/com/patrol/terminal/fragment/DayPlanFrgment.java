package com.patrol.terminal.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddDayPlanActivity;
import com.patrol.terminal.activity.DayPlanDetailActivity;
import com.patrol.terminal.activity.NextDayPlanActivity;
import com.patrol.terminal.adapter.DayPlanAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DayListBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.TimeUtil;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DayPlanFrgment extends BaseFragment {


    @BindView(R.id.add_next_plan)
    TextView addNextPlan;
    @BindView(R.id.plan_submit)
    ImageView planSubmit;
    @BindView(R.id.add_plan_right)
    ImageView addPlanRight;
    @BindView(R.id.plan_point)
    View planPoint;
    @BindView(R.id.add_plan_name)
    TextView addPlanName;
    @BindView(R.id.add_plan_status)
    TextView addPlanStatus;
    @BindView(R.id.add_plan_ll)
    RelativeLayout addPlanLl;
    @BindView(R.id.add_plan_iv)
    ImageView addPlanIv;
    @BindView(R.id.plan_total_title)
    TextView planTotalTitle;
    @BindView(R.id.month_line_total)
    TextView monthLineTotal;
    @BindView(R.id.month_line_kilo_total)
    TextView monthLineKiloTotal;
    @BindView(R.id.month_line_110kv_num)
    TextView monthLine110kvNum;
    @BindView(R.id.month_line_110kv_kilo)
    TextView monthLine110kvKilo;
    @BindView(R.id.month_line_35kv_num)
    TextView monthLine35kvNum;
    @BindView(R.id.month_line_35kv_kilo)
    TextView monthLine35kvKilo;
    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.task_screen)
    ImageView taskScreen;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.plan_refresh)
    SwipeRefreshLayout planRefresh;
    private DayPlanAdapter dayPlanAdapter;
    private String time;
    private List<DayListBean> results;

    private TimePickerView pvTime;
    private String year, month, day;
    private int num_total = 0;
    private int num_110kv = 0;
    private int num_35kv = 0;
    private double kilo_total = 0;
    private double kilo_110kv = 0;
    private double kilo_35kv = 0;

    private int next_num_total = 0;
    private int next_num_110kv = 0;
    private int next_num_35kv = 0;
    private double next_kilo_total = 0;
    private double next_kilo_110kv = 0;
    private double next_kilo_35kv = 0;
    private int nextDay;
    private int nextmonth;
    private int nextyear;
    private List<DayListBean> nextDayList;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        addPlanStatus.setVisibility(View.GONE);
        addNextPlan.setText("明日工作计划制定");
        planTotalTitle.setText("今日工作计划汇总");

        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate();
        taskTitle.setText("日计划列表");
        taskDate.setText(time);
        // 设置监听器。
        planRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        planRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        dayPlanAdapter = new DayPlanAdapter(R.layout.fragment_plan_item, results);
        planRv.setAdapter(dayPlanAdapter);
        dayPlanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getContext(), DayPlanDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("bean", results.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        planRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDayList();
                getNextDayList();
            }
        });
        getDayList();
        getNextDayList();
    }


    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
//            int width = getResources().getDimensionPixelSize(R.dimen.dp_60);
//
//            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
//            // 2. 指定具体的高，比如80;
//            // 3. WRAP_CONTENT，自身高度，不推荐;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
//            deleteItem.setWidth(width);
//            deleteItem.setHeight(height);
//            deleteItem.setTextSize(15);
//            deleteItem.setTextColorResource(R.color.white);
//            deleteItem.setBackground(R.color.orange_vip);
//            deleteItem.setText("编辑");
//            SwipeMenuItem deleteItem1 = new SwipeMenuItem(getContext());
//            deleteItem1.setWidth(width);
//            deleteItem1.setHeight(height);
//            deleteItem1.setBackground(R.color.home_red);
//            deleteItem1.setTextSize(15);
//            deleteItem1.setTextColorResource(R.color.white);
//            deleteItem1.setText("删除");
//            // 各种文字和图标属性设置。
//            rightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。
//            rightMenu.addMenuItem(deleteItem1); // 在Item右侧添加一个菜单。
//            // 注意：哪边不想要菜单，那么不要添加即可。
        }
    };

    //获取日计划列表
    public void getDayList() {
        BaseRequest.getInstance().getService()
                .getDayList(year, month, day, SPUtil.getDepId(getContext()), "type_sign,line_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayListBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<DayListBean>> t) throws Exception {
                        results = t.getResults();
                        dayPlanAdapter.setNewData(results);
                        for (int i = 0; i < results.size(); i++) {
                            DayListBean dayListBean = results.get(i);
                            num_total++;
                            kilo_total=kilo_total+dayListBean.getTowers_range();
                        }
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        monthLineTotal.setText("杆段总数 : " + num_total + "条");
                        monthLineKiloTotal.setText("总公里数 : " + decimalFormat.format(kilo_total) + "公里");
                        planRefresh.setRefreshing(false);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.e("fff", e.toString());
                        planRefresh.setRefreshing(false);
                    }
                });
    }
    //获取明日计划列表
    public void getNextDayList() {

        BaseRequest.getInstance().getService()
                .getDayList(nextyear+"", nextmonth+"", nextDay+"", SPUtil.getDepId(getContext()), "type_sign,line_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayListBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<DayListBean>> t) throws Exception {
                        nextDayList = t.getResults();
                        if (nextDayList != null && nextDayList.size() > 0) {
                            addPlanIv.setVisibility(View.GONE);
                            addPlanRight.setVisibility(View.VISIBLE);
                            addPlanLl.setVisibility(View.VISIBLE);
                            addPlanName.setText(nextyear + "年第" + nextmonth + "月"+nextDay+"日工作计划");

                        }
                        for (int i = 0; i < nextDayList.size(); i++) {
                            DayListBean dayListBean = nextDayList.get(i);
                            next_num_total++;
                            next_kilo_total=kilo_total+dayListBean.getTowers_range();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }


    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();
        }
    };


    @OnClick({R.id.task_date, R.id.add_plan_iv, R.id.add_plan_right, R.id.add_plan_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showDay();
                break;
            case R.id.add_plan_right:
            case R.id.add_plan_iv:

                Intent intent = new Intent(getContext(), AddDayPlanActivity.class);
                intent.putExtra("year", nextyear);
                intent.putExtra("month", nextmonth);
                intent.putExtra("day", nextDay);
                startActivityForResult(intent, 10);
                break;
            case R.id.add_plan_ll:
                Intent intent1 = new Intent(getContext(), NextDayPlanActivity.class);
                intent1.putExtra("list", (Serializable) nextDayList);
                intent1.putExtra("num_total",next_num_total);
                intent1.putExtra("kilo_total",next_kilo_total);
                intent1.putExtra("110kv_num",next_num_110kv);
                intent1.putExtra("110kv_kolo",next_kilo_110kv);
                intent1.putExtra("35kv_num",next_num_35kv);
                intent1.putExtra("35kv_kolo",next_kilo_35kv);
                intent1.putExtra("year", nextyear);
                intent1.putExtra("month", nextmonth);
                intent1.putExtra("day", nextDay);
                startActivityForResult(intent1, 10);
                break;
        }

    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void showDay() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        //选中事件回调
//是否只显示中间选中项的label文字，false则每项item全部都带有label。
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                time = DateUatil.getDay(date);
                taskDate.setText(time);
                inteDate();
                getDayList();
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentTextSize(18)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    public void inteDate() {
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        day = Integer.parseInt(days[0]) + "";
        nextDay = Integer.parseInt(days[0])+1;
        nextmonth=Integer.parseInt(months[0]);
        nextyear=Integer.parseInt(years[0]);
        int monthOfDay = TimeUtil.getMonthOfDay(Integer.parseInt(year), Integer.parseInt(month));
        if (nextDay>monthOfDay){
            nextDay=1;
            nextmonth = Integer.parseInt(months[0])+1;
            if (nextmonth >12){
                nextmonth =1;
                nextyear = Integer.parseInt(year)+1;
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            getDayList();
            getNextDayList();
        }
    }
}
