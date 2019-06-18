package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MonthPlanAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.utils.DateUatil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlanListActivity extends BaseActivity {

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
    @BindView(R.id.plan_change_date)
    TextView planChangeDate;
    @BindView(R.id.mon_plan_rv)
    RecyclerView monPlanRv;
    @BindView(R.id.month_list_ref)
    SwipeRefreshLayout monthListRef;

    private MonthPlanAdapter monthPlanAdapter;
    private TimePickerView pvTime;
    private List<MonthPlanBean> result = new ArrayList<>();
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);
        ButterKnife.bind(this);
        initData();
    }

 
    protected void initData() {
        titleName.setText("月计划列表");
        titleSettingTv.setText("周计划");
        titleSetting.setVisibility(View.VISIBLE);
        titleBack.setVisibility(View.GONE);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        monPlanRv.setLayoutManager(manager);
        monthPlanAdapter = new MonthPlanAdapter(R.layout.fragment_plan_item, result);
        monPlanRv.setAdapter(monthPlanAdapter);
        time = DateUatil.getTime(new Date(System.currentTimeMillis()));
        planChangeDate.setText(time);
        monthListRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMonthPlanList(time);
            }
        });
        getMonthPlanList(time);
    }


    public void showMonth() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                 time = DateUatil.getTime(date);
                planChangeDate.setText(time);
                getMonthPlanList(time);
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentTextSize(18)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }



    //获取月计划列表
    public void getMonthPlanList(String date) {
        String[] years = date.split("年");
        String[] months = years[1].split("月");
        int month = Integer.parseInt(months[0]);
        BaseRequest.getInstance().getService()
                .getMonthPlanList(years[0],month+"","")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        monthListRef.setRefreshing(false);
                        result = t.getResults();
                        monthPlanAdapter.setNewData(result);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        monthListRef.setRefreshing(false);
                    }
                });

    }

    @OnClick({R.id.title_setting, R.id.plan_change_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_setting:
                Intent intent = new Intent(this, WeekPlanListActivity.class);
                intent.putExtra("date",time);
                startActivity(intent);
                break;
            case R.id.plan_change_date:
                showMonth();
                break;
        }
    }
}
