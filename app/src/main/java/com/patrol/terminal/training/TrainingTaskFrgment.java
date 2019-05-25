package com.patrol.terminal.training;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.CreatePlanActivity;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.bean.TrainingTaskBean;
import com.patrol.terminal.bean.TrainingTempPlanBean;
import com.patrol.terminal.bean.TrainingYearPlanBean;
import com.patrol.terminal.overhaul.OverhaulAddYearPlanActivity;
import com.patrol.terminal.overhaul.OverhaulYearAdapter;
import com.patrol.terminal.overhaul.OverhaulYearDetailActivity;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TrainingTaskFrgment extends BaseFragment {


    @BindView(R.id.year_tv)
    TextView yearTv;
    @BindView(R.id.month_tv)
    TextView monthTv;
    @BindView(R.id.temp_tv)
    TextView tempTv;
    @BindView(R.id.task_rg)
    LinearLayout taskRg;
    @BindView(R.id.task_add)
    ImageView taskAdd;
    @BindView(R.id.train_plan_rl)
    RelativeLayout trainPlanRl;
    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;

    private TimePickerView pvTime;
    private String month, year;

    private TrainingTaskAdapter taskAdapter;
    private List<TrainingTaskBean> trainTaskList = new ArrayList<>();

    private  Disposable publishRepair;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.training_fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        taskTitle.setText("月任务列表");
        trainPlanRl.setVisibility(View.GONE);

        String time =  DateUatil.getTime(new Date(System.currentTimeMillis()));
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        taskDate.setText(year + "年" + month + "月");

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        taskAdapter = new TrainingTaskAdapter(R.layout.fragment_training_task_item, trainTaskList);
        planRv.setAdapter(taskAdapter);
        taskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //Intent intent = new Intent();
               // intent.setClass(getContext(), NewTrainPlanActivity.class);
               // intent.putExtra(Constant.ADD_TRAINING_PLAN_ENTER_TYPE, Constant.FROM_YEAR_TO_ENTER_TYPE);

                //getContext().startActivity(intent);
            }
        });

        getTrainTaskList();

        publishRepair = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                if (s.startsWith("publishTrainTask")) {
                    getTrainTaskList();
                }
            }
        });
    }

//    //获取月计划列表
//    public void getMonthPlanList() {
//
//        BaseRequest.getInstance().getService()
//                .getMonthPlanList(year, month, "E984654407E744DFA7B06F625287F3FF")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<MonthPlanBean>>(getContext()) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
//                        result = t.getResults();
//                        yearAdapter.setNewData(result);
//                        SPUtil.putString(getContext(), "date", "time", time);
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                    }
//                });
//
//    }

    public void getTrainTaskList() {
        BaseRequest.getInstance().getService()
                .getTrainTaskList(year, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TrainingTaskBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<TrainingTaskBean>> t) throws Exception {
                        List<TrainingTaskBean> result = t.getResults();
                        taskAdapter.setNewData(result);
                        //SPUtil.putString(getContext(), "date", "overhaulTime", time);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (publishRepair != null) {
            publishRepair.dispose();
        }
    }

    public void showMonth() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2008, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = DateUatil.getTime(date);
                String[] years = time.split("年");
                String[] months = years[1].split("月");
                month = Integer.parseInt(months[0]) + "";
                year = years[0];
                taskDate.setText(year + "年" + month + "月");
                getTrainTaskList();
                //.publish("month@" + time);
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


    @OnClick({R.id.task_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showMonth();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            getTrainTaskList();
        }
    }

}
