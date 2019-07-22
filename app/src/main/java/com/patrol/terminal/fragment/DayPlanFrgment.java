package com.patrol.terminal.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddDayPlanActivity;
import com.patrol.terminal.activity.DayPlanDetailActivity;
import com.patrol.terminal.activity.NextDayPlanActivity;
import com.patrol.terminal.activity.TemporaryActivity;
import com.patrol.terminal.adapter.DayPlanAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DayListBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.TimeUtil;
import com.patrol.terminal.utils.Utils;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    TextView addPlanRight;
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
    @BindView(R.id.done_plan_range)
    TextView donePlanRange;
    @BindView(R.id.ll_35kv)
    LinearLayout ll35kv;
    @BindView(R.id.plan_submit_next)
    ImageView planSubmitNext;
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
    private int done_num_total = 0;
    private int all_num_total = 0;
    private int nextDay;
    private int nextmonth;
    private int nextyear;
    private List<DayListBean> nextDayList;
    private List<String> lineNum=new ArrayList<>();
    private String mJobType;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        ll35kv.setVisibility(View.GONE);
        addPlanStatus.setVisibility(View.GONE);
        addNextPlan.setText("日计划制定");
        planTotalTitle.setText("今日工作计划汇总");
        mJobType = SPUtil.getString(getActivity(), Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate();
        taskTitle.setText("日计划列表");
        taskDate.setText(time);

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


    //获取日计划列表
    public void getDayList() {
        BaseRequest.getInstance().getService()
                .getDayList(year, month, day, SPUtil.getDepId(getContext()), "create_time desc,type_sign,line_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayListBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<DayListBean>> t) throws Exception {
                        planRefresh.setRefreshing(false);
                        lineNum.clear();
                        num_total = 0;
                        kilo_total = 0;
                        done_num_total = 0;
                        all_num_total = 0;
                        results = t.getResults();
                        RxRefreshEvent.publish("refreshDayNum@"+results.size());
                        dayPlanAdapter.setNewData(results);
                        for (int i = 0; i < results.size(); i++) {
                            DayListBean dayListBean = results.get(i);
                            num_total++;
                            kilo_total = kilo_total + dayListBean.getTowers_range();
                            done_num_total = done_num_total + dayListBean.getDone_num();
                            all_num_total = all_num_total + dayListBean.getAll_num();
                            if (lineNum.indexOf(dayListBean.getLine_id())==-1){
                                lineNum.add(dayListBean.getLine_id());
                            }
                        }
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        monthLineTotal.setText("工作线路 : " + lineNum.size() + "条");
                        monthLineKiloTotal.setText(decimalFormat.format(kilo_total) + "km");
                        monthLine110kvNum.setText("工作杆段 : " + num_total + "段");
                        monthLine110kvKilo.setText( decimalFormat.format(kilo_total) + "km");

                        if (all_num_total == 0) {
                            donePlanRange.setText("计划进度 : 0%");
                        } else {
                            //默认保留两位会有错误，这里设置保留小数点后4位
                            double range = Utils.div(done_num_total, all_num_total, 4)*100;
                            donePlanRange.setText("计划进度 : " + range + "%");
                        }
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
                .getDayList(nextyear + "", nextmonth + "", nextDay + "", SPUtil.getDepId(getContext()), "create_time desc,type_sign,line_id")
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
                            addPlanName.setText(nextyear + "年第" + nextmonth + "月" + nextDay + "日工作计划");

                        }
                        for (int i = 0; i < nextDayList.size(); i++) {
                            DayListBean dayListBean = nextDayList.get(i);
                            next_num_total++;
                            next_kilo_total = kilo_total + dayListBean.getTowers_range();
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
                if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                Intent intent2 = new Intent(getContext(), TemporaryActivity.class);
                intent2.putExtra("year", String.valueOf(nextyear));
                intent2.putExtra("month", String.valueOf(nextmonth));
                intent2.putExtra("day", String.valueOf(nextDay));
                startActivityForResult(intent2, 10);
                } else {
                    Toast.makeText(getContext(), "您没有权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_plan_iv:
                if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                    Intent intent = new Intent(getContext(), AddDayPlanActivity.class);
                    intent.putExtra("year", String.valueOf(nextyear));
                    intent.putExtra("month", String.valueOf(nextmonth));
                    intent.putExtra("day", String.valueOf(day));
                    intent.putExtra("from", "DayPlanFrgment");
                    startActivityForResult(intent, 10);
                } else {
                    Toast.makeText(getContext(), "您没有权限", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.add_plan_ll:
                Intent intent1 = new Intent(getContext(), NextDayPlanActivity.class);
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
                planTotalTitle.setText(day+"日计划工作汇总");
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
        nextDay = Integer.parseInt(days[0]) + 1;
        nextmonth = Integer.parseInt(months[0]);
        nextyear = Integer.parseInt(years[0]);
        int monthOfDay = TimeUtil.getMonthOfDay(Integer.parseInt(year), Integer.parseInt(month));
        if (nextDay > monthOfDay) {
            nextDay = 1;
            nextmonth = Integer.parseInt(months[0]) + 1;
            if (nextmonth > 12) {
                nextmonth = 1;
                nextyear = Integer.parseInt(year) + 1;
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
