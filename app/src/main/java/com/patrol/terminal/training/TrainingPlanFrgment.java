package com.patrol.terminal.training;

import android.app.Activity;
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
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.TrainingMonthPlanBean;
import com.patrol.terminal.bean.TrainingTempPlanBean;
import com.patrol.terminal.bean.TrainingYearPlanBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TrainingPlanFrgment extends BaseFragment {

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
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.train_plan_rl)
    RelativeLayout trainPlanRl;
    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    private TrainingYearAdapter yearAdapter;
    private List<TrainingYearPlanBean> trainYearList = new ArrayList<>();

    private TrainingMonthAdapter monthAdapter;
    private List<TrainingMonthPlanBean> trainMonthList = new ArrayList<>();

    private TrainingTempAdapter tempAdapter;
    private List<TrainingTempPlanBean> trainTempList = new ArrayList<>();

    private Activity mActivity;
    private String year;
    private String month;
    private TimePickerView pvTime;

    private static final int YEAR_TYPE = 0;
    private static final int MONTH_TYPE = 1;
    private static final int TEMP_TYPE = 2;
    private int yearMonthTaskType = YEAR_TYPE;

    private static final int ADD_TASK_REQUEST_CODE = 1001;
    private static final int TEMP_REQUEST_CODE = 1002;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.training_fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        mActivity = getActivity();
        String time = DateUatil.getTime(new Date(System.currentTimeMillis()));
        //time = SPUtil.getString(getContext(), "date", "overhaulTime", DateUatil.getYear(new Date(System.currentTimeMillis())));
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        taskDate.setText(year + "年");
        taskTitle.setText("年计划列表");
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        yearAdapter = new TrainingYearAdapter(R.layout.fragment_training_year_item, trainYearList);
        planRv.setAdapter(yearAdapter);
        yearAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getContext(), NewTrainPlanActivity.class);
                intent.putExtra(Constant.ADD_TRAINING_PLAN_ENTER_TYPE, Constant.FROM_YEAR_TO_ENTER_TYPE);
                intent.putExtra(Constant.PLAN_TRAIN_ID, trainYearList.get(position).getId());
                intent.putExtra(Constant.TRAIN_YEAR_PLAN, trainYearList.get(position));
                getContext().startActivity(intent);
            }
        });

        monthAdapter = new TrainingMonthAdapter(R.layout.fragment_training_year_item, trainMonthList);
        //planRv.setAdapter(monthAdapter);
        monthAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getContext(), NewTrainPlanActivity.class);
                intent.putExtra(Constant.ADD_TRAINING_PLAN_ENTER_TYPE, Constant.FROM_MONTH_TO_ENTER_TYPE);
                intent.putExtra(Constant.PLAN_TRAIN_ID, trainMonthList.get(position).getId());
                intent.putExtra(Constant.TRAIN_MONTH_PLAN, trainMonthList.get(position));
                getContext().startActivity(intent);
            }
        });

        tempAdapter = new TrainingTempAdapter(R.layout.fragment_training_temp_item, trainTempList);
        //planRv.setAdapter(tempAdapter);
        tempAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getContext(), NewTrainPlanActivity.class);
                intent.putExtra(Constant.ADD_TRAINING_PLAN_ENTER_TYPE, Constant.FROM_TEMP_TO_ENTER_TYPE);
                intent.putExtra(Constant.PLAN_TRAIN_ID, trainTempList.get(position).getId());
                intent.putExtra(Constant.TRAIN_TEMP_PLAN, trainTempList.get(position));
                String audioStatus = trainTempList.get(position).getStatus();
                if (audioStatus.equals("0") || audioStatus.equals("1")) {         //未审核通过前
                    intent.putExtra(Constant.ADD_TRAIN_PLAN_IS_DISTRIBUTE, Constant.NOT_AUDITED);
                } else if(audioStatus.equals("2")){                               //已审核
                    intent.putExtra(Constant.ADD_TRAIN_PLAN_IS_DISTRIBUTE, Constant.IS_AUDITED);
                }else if (audioStatus.equals("3") || audioStatus.equals("4")) {   //已分发
                    intent.putExtra(Constant.ADD_TRAIN_PLAN_IS_DISTRIBUTE, Constant.IS_SEND);
                }

                startActivityForResult(intent, TEMP_REQUEST_CODE);
            }
        });

        getYearPlanList();
    }

    //获取年计划列表
    public void getYearPlanList() {
        trainYearList.clear();
//        TrainingYearPlanBean trainingYearPlanBean = new TrainingYearPlanBean();
//        trainPlanList.add(trainingYearPlanBean);
//        trainPlanList.add(trainingYearPlanBean);
//        trainPlanList.add(trainingYearPlanBean);
//        yearAdapter.setNewData(trainPlanList);

        BaseRequest.getInstance().getService()
                .getTrainYearList(year)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TrainingYearPlanBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<TrainingYearPlanBean>> t) throws Exception {
                        trainYearList = t.getResults();
                        yearAdapter.setNewData(trainYearList);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });


    }

    public void getMonthPlanList() {
        trainMonthList.clear();

        BaseRequest.getInstance().getService()
                .getTrainMonthList(year, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TrainingMonthPlanBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<TrainingMonthPlanBean>> t) throws Exception {
                        trainMonthList = t.getResults();
                        monthAdapter.setNewData(trainMonthList);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }


    public void getTempPlanList() {
        trainTempList.clear();
//        TrainingTempPlanBean trainingTempPlanBean = new TrainingTempPlanBean();
//        trainingTempPlanBean.setAudioStatus("0");
//        trainTempList.add(trainingTempPlanBean);
//
//        TrainingTempPlanBean trainingTempPlanBean1 = new TrainingTempPlanBean();
//        trainingTempPlanBean1.setAudioStatus("3");
//        trainTempList.add(trainingTempPlanBean1);
//
//        tempAdapter.setNewData(trainTempList);

        BaseRequest.getInstance().getService()
                .getTrainTempList(year, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TrainingTempPlanBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<TrainingTempPlanBean>> t) throws Exception {
                        trainTempList = t.getResults();
                        tempAdapter.setNewData(trainTempList);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

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
                if (yearMonthTaskType == MONTH_TYPE) {
                    getMonthPlanList();
                }else if (yearMonthTaskType == TEMP_TYPE) {
                    getTempPlanList();
                }
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
                String time = DateUatil.getYear(date);
                taskDate.setText(time);
                String[] years = time.split("年");
                //String[] months = years[1].split("月");
                //month = Integer.parseInt(months[0]) + "";
                year = years[0];
                getYearPlanList();
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TASK_REQUEST_CODE) {
            getTempPlanList();
        }else if (requestCode == TEMP_REQUEST_CODE) {   //临时计划列表分发成功后切换状态  TODO
            getTempPlanList();
        }
    }

    @OnClick({R.id.year_tv, R.id.month_tv, R.id.temp_tv, R.id.task_add, R.id.task_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.year_tv:
                taskDate.setText(year + "年");
                taskTitle.setText("年计划列表");
                yearTv.setTextColor(getResources().getColor(R.color.white));
                yearTv.setBackgroundResource(R.drawable.train_year_bg);
                monthTv.setTextColor(getResources().getColor(R.color.tra_et_coler));
                monthTv.setBackgroundResource(R.drawable.stoke_commit_bg);
                tempTv.setTextColor(getResources().getColor(R.color.tra_et_coler));
                tempTv.setBackgroundResource(R.drawable.stoke_commit_bg);
                taskAdd.setVisibility(View.INVISIBLE);
                planRv.setAdapter(yearAdapter);
                getYearPlanList();
                yearMonthTaskType = YEAR_TYPE;
                break;

            case R.id.month_tv:
                taskDate.setText(year + "年" + month + "月");
                taskTitle.setText("月计划列表");
                monthTv.setTextColor(getResources().getColor(R.color.white));
                monthTv.setBackgroundResource(R.drawable.train_year_bg);
                yearTv.setTextColor(getResources().getColor(R.color.tra_et_coler));
                yearTv.setBackgroundResource(R.drawable.stoke_commit_bg);
                tempTv.setTextColor(getResources().getColor(R.color.tra_et_coler));
                tempTv.setBackgroundResource(R.drawable.stoke_commit_bg);
                taskAdd.setVisibility(View.INVISIBLE);
                planRv.setAdapter(monthAdapter);
                getMonthPlanList();
                yearMonthTaskType = MONTH_TYPE;
                break;

            case R.id.temp_tv:
                taskDate.setText(year + "年" + month + "月");
                taskTitle.setText("月计划列表");
                tempTv.setTextColor(getResources().getColor(R.color.white));
                tempTv.setBackgroundResource(R.drawable.train_year_bg);
                monthTv.setTextColor(getResources().getColor(R.color.tra_et_coler));
                monthTv.setBackgroundResource(R.drawable.stoke_commit_bg);
                yearTv.setTextColor(getResources().getColor(R.color.tra_et_coler));
                yearTv.setBackgroundResource(R.drawable.stoke_commit_bg);
                taskAdd.setVisibility(View.VISIBLE);
                planRv.setAdapter(tempAdapter);
                getTempPlanList();
                yearMonthTaskType = TEMP_TYPE;
                break;

            case R.id.task_add:
                Intent intent = new Intent();
                intent.setClass(mActivity, NewTrainPlanActivity.class);
                intent.putExtra(Constant.ADD_TRAINING_PLAN_ENTER_TYPE, Constant.FROM_ADD_PLAN_TO_ENTER_TYPE);
                startActivityForResult(intent, ADD_TASK_REQUEST_CODE);
                break;

            case R.id.task_date:
                if (yearMonthTaskType == MONTH_TYPE || yearMonthTaskType == TEMP_TYPE) {
                    showMonth();
                }else if (yearMonthTaskType == YEAR_TYPE) {
                    showYear();
                }

                break;
        }
    }


}
