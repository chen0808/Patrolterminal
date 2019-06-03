package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*检修专责年计划*/
public class OverhaulYearPlanFrgment extends BaseFragment {


    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.task_add)
    ImageView taskAdd;
    @BindView(R.id.plan_submit)
    TextView planSubmit;
    @BindView(R.id.plan_refresh)
    SwipeRefreshLayout planRefresh;
//    @BindView(R.id.plan_create)
//    TextView planCreate;


    private OverhaulYearAdapter yearAdapter;
    private TimePickerView pvTime;
    private List<OverhaulYearBean> result = new ArrayList<>();
    private String time;
    private String typeId;
    private String month, year;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        taskAdd.setVisibility(View.INVISIBLE);
        taskTitle.setText("年计划列表");
        time = SPUtil.getString(getContext(), "date", "overhaulTime", DateUatil.getYear(new Date(System.currentTimeMillis())));
        String[] years = time.split("年");
//        String[] months = years[1].split("月");
//        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        taskDate.setText(year + "年");
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        yearAdapter = new OverhaulYearAdapter(R.layout.fragment_overhaul_year_item, result);
        planRv.setAdapter(yearAdapter);
        yearAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getContext(), OverhaulYearDetailActivity.class);
                Bundle bundle = new Bundle();
                if (result.get(position) != null) {
                    bundle.putParcelable("bean", result.get(position));
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        getYearPlanList();
        planRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getYearPlanList();
            }
        });
    }

    //获取年计划列表
    public void getYearPlanList() {

        BaseRequest.getInstance().getService()
                .getOverhaulPlanList(year, null, null, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulYearBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulYearBean>> t) throws Exception {
                        planRefresh.setRefreshing(false);
                        result = t.getResults();
                        yearAdapter.setNewData(result);
                        SPUtil.putString(getContext(), "date", "overhaulTime", time);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        planRefresh.setRefreshing(false);
                    }
                });

    }


    public void showYear() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2008, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                time = DateUatil.getYear(date);
                taskDate.setText(time);
                String[] years = time.split("年");
                //String[] months = years[1].split("月");
                //month = Integer.parseInt(months[0]) + "";
                year = years[0];
                //getMonthPlanList();
                getYearPlanList();
                RxRefreshEvent.publish("month@" + time);
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentTextSize(18)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .setType(new boolean[]{true, false, false, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }


    @OnClick({R.id.task_date, R.id.task_add/*, R.id.plan_create*/})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showYear();
                break;
            case R.id.task_add:
                startActivityForResult(new Intent(getContext(), OverhaulAddYearPlanActivity.class), 10);
                break;
//            case R.id.plan_create:
////                Intent intent = new Intent(getContext(), CreatePlanActivity.class);
////                intent.putExtra("from", 0);
////                startActivityForResult(intent, 10);
////                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            //getMonthPlanList();
            getYearPlanList();
        }
    }
}
