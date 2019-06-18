package com.patrol.terminal.training;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddressBookActivity;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.AddressBookLevel2;
import com.patrol.terminal.bean.TrainAuditorBean;
import com.patrol.terminal.bean.TrainLevelBean;
import com.patrol.terminal.bean.TrainingAddTaskBean;
import com.patrol.terminal.bean.TrainingAddTempBean;
import com.patrol.terminal.bean.TrainingMonthPlanBean;
import com.patrol.terminal.bean.TrainingTempPlanBean;
import com.patrol.terminal.bean.TrainingYearPlanBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;

import org.angmarch.views.NiceSpinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewTrainPlanActivity extends BaseActivity {


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
    @BindView(R.id.train_name_et)
    EditText trainNameEt;
    @BindView(R.id.start_time_title)
    TextView startTimeTitle;
    @BindView(R.id.start_time_tv)
    TextView startTimeTv;
    @BindView(R.id.end_time_title)
    TextView endTimeTitle;
    @BindView(R.id.end_time_tv)
    TextView endTimeTv;
    @BindView(R.id.train_days_title)
    TextView trainDaysTitle;
    @BindView(R.id.train_days_tv)
    TextView trainDaysTv;
    @BindView(R.id.train_level_title)
    TextView trainLevelTitle;
    @BindView(R.id.train_level_spinner)
    NiceSpinner trainLevelSpinner;
    @BindView(R.id.train_unit_title)
    TextView trainUnitTitle;
    @BindView(R.id.train_unit_et)
    EditText trainUnitEt;
    @BindView(R.id.train_position_title)
    TextView trainPositionTitle;
    @BindView(R.id.train_position_et)
    EditText trainPositionEt;
    @BindView(R.id.train_type_title)
    TextView trainTypeTitle;
    @BindView(R.id.train_type_spinner)
    NiceSpinner trainTypeSpinner;
    @BindView(R.id.train_teacher_title)
    TextView trainTeacherTitle;
    @BindView(R.id.train_teacher_et)
    EditText trainTeacherEt;
    @BindView(R.id.week_plan_content)
    EditText weekPlanContent;
    @BindView(R.id.train_person_title)
    TextView trainPersonTitle;
    @BindView(R.id.train_person_tv)
    TextView trainPersonTv;
    @BindView(R.id.train_person_ll)
    LinearLayout trainPersonLl;
    @BindView(R.id.train_auditor_title)
    TextView trainAuditorTitle;
    @BindView(R.id.train_auditor_spinner)
    NiceSpinner trainAuditorSpinner;
    @BindView(R.id.train_auditor_ll)
    LinearLayout trainAuditorLl;
    @BindView(R.id.person_detail)
    EditText personDetail;
    @BindView(R.id.train_level_spinner_tv)
    TextView trainLevelSpinnerTv;
    @BindView(R.id.train_type_spinner_tv)
    TextView trainTypeSpinnerTv;
    @BindView(R.id.train_name_ll)
    LinearLayout trainNameLl;
    @BindView(R.id.start_time_ll)
    LinearLayout startTimeLl;
    @BindView(R.id.end_time_ll)
    LinearLayout endTimeLl;
    @BindView(R.id.train_days_ll)
    LinearLayout trainDaysLl;
    @BindView(R.id.train_level_ll)
    LinearLayout trainLevelLl;
    @BindView(R.id.train_unit_ll)
    LinearLayout trainUnitLl;
    @BindView(R.id.train_position_ll)
    LinearLayout trainPositionLl;
    @BindView(R.id.train_type_ll)
    LinearLayout trainTypeLl;
    @BindView(R.id.train_teacher_ll)
    LinearLayout trainTeacherLl;
    @BindView(R.id.person_remark_ll)
    LinearLayout personDetailLl;
    @BindView(R.id.train_person_ll_enter)
    LinearLayout trainPersonLlEnter;
    private int fromType;
    private int auditedType;

    private String trainLevelId = "";
    private String trainTypeId = "";
    private String trainAuditorId = "";

    private String planTrainId = "";
    private long startTime;
    private long endTime;
    //private String startTimeStr;
    //private String endTimeStr;
    private TimePickerView pvTime;
    private String year, month;

    private List<AddressBookLevel2> nameList = new ArrayList<>();
    private List<AddressBookLevel2> selectNameList = new ArrayList<>();

    private static final int IS_SEND = 0;
    private static final int IS_AUDITOR = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_new_plan_detail);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        fromType = getIntent().getIntExtra(Constant.ADD_TRAINING_PLAN_ENTER_TYPE, Constant.FROM_YEAR_TO_ENTER_TYPE);
        auditedType = getIntent().getIntExtra(Constant.ADD_TRAIN_PLAN_IS_DISTRIBUTE, Constant.NOT_AUDITED);
        planTrainId = getIntent().getStringExtra(Constant.PLAN_TRAIN_ID);

        titleSetting.setVisibility(View.VISIBLE);

        if (fromType == Constant.FROM_YEAR_TO_ENTER_TYPE || fromType == Constant.FROM_MONTH_TO_ENTER_TYPE) {    //直接分发生成培训任务

            if (fromType == Constant.FROM_YEAR_TO_ENTER_TYPE) {
                TrainingYearPlanBean trainingYearPlanBean = getIntent().getParcelableExtra(Constant.TRAIN_YEAR_PLAN);
                trainTypeId = trainingYearPlanBean.getType_id();
                trainLevelId = trainingYearPlanBean.getTrain_level();
                trainNameEt.setText(trainingYearPlanBean.getName());
                //trainTypeSpinner.setText(trainingYearPlanBean.getType_name());
                trainTypeSpinnerTv.setText(trainingYearPlanBean.getType_name());
                trainLevelSpinnerTv.setText(trainingYearPlanBean.getTrain_level_name());
                trainUnitEt.setText(trainingYearPlanBean.getHost_unit());
            } else if (fromType == Constant.FROM_MONTH_TO_ENTER_TYPE) {
                TrainingMonthPlanBean trainingMonthPlanBean = getIntent().getParcelableExtra(Constant.TRAIN_MONTH_PLAN);
                trainTypeId = trainingMonthPlanBean.getType_id();
                trainLevelId = trainingMonthPlanBean.getTrain_level();
                trainNameEt.setText(trainingMonthPlanBean.getName());
                //trainTypeSpinner.setText(trainingMonthPlanBean.getType_name());
                trainTypeSpinnerTv.setText(trainingMonthPlanBean.getType_name());
                trainLevelSpinnerTv.setText(trainingMonthPlanBean.getTrain_level_name());
                trainUnitEt.setText(trainingMonthPlanBean.getHost_unit());
            }

            titleSettingTv.setText("分发");
            trainNameEt.setEnabled(false);
            trainNameLl.setBackgroundColor(getResources().getColor(R.color.line_color));
            trainLevelSpinner.setVisibility(View.GONE);
            trainLevelSpinnerTv.setVisibility(View.VISIBLE);
            trainLevelLl.setBackgroundColor(getResources().getColor(R.color.line_color));
            trainUnitEt.setEnabled(false);
            trainUnitLl.setBackgroundColor(getResources().getColor(R.color.line_color));
            trainTypeSpinner.setVisibility(View.GONE);
            trainTypeSpinnerTv.setVisibility(View.VISIBLE);
            trainTypeLl.setBackgroundColor(getResources().getColor(R.color.line_color));
            trainPersonLl.setVisibility(View.VISIBLE);
            trainAuditorLl.setVisibility(View.GONE);
        } else {                                            //需要审核
            titleSettingTv.setText("审核");
            trainNameEt.setEnabled(true);
            trainLevelSpinner.setVisibility(View.VISIBLE);
            trainLevelSpinnerTv.setVisibility(View.GONE);
            trainUnitEt.setEnabled(true);
            trainTypeSpinner.setVisibility(View.VISIBLE);
            trainTypeSpinnerTv.setVisibility(View.GONE);
            trainPersonLl.setVisibility(View.GONE);
            personDetailLl.setVisibility(View.GONE);
            trainAuditorLl.setVisibility(View.VISIBLE);

            if (fromType == Constant.FROM_TEMP_TO_ENTER_TYPE) {
                if (auditedType == Constant.IS_AUDITED || auditedType == Constant.IS_SEND) {    //已经审核完毕, 不可修改大部分内容，只能选人
                    TrainingTempPlanBean trainingTempPlanBean = getIntent().getParcelableExtra(Constant.TRAIN_TEMP_PLAN);

                    trainNameEt.setEnabled(false);
                    trainNameEt.setText(trainingTempPlanBean.getName());
                    trainNameLl.setBackgroundColor(getResources().getColor(R.color.line_color));

                    trainLevelSpinner.setVisibility(View.GONE);
                    trainLevelSpinnerTv.setVisibility(View.VISIBLE);
                    trainLevelSpinnerTv.setText(trainingTempPlanBean.getTrain_level_name());
                    trainLevelLl.setBackgroundColor(getResources().getColor(R.color.line_color));

                    trainUnitEt.setEnabled(false);
                    trainUnitEt.setText(trainingTempPlanBean.getHost_unit());
                    trainUnitLl.setBackgroundColor(getResources().getColor(R.color.line_color));

                    trainTypeSpinner.setVisibility(View.GONE);
                    trainTypeSpinnerTv.setVisibility(View.VISIBLE);
                    trainTypeSpinnerTv.setText(trainingTempPlanBean.getType_name());
                    trainTypeLl.setBackgroundColor(getResources().getColor(R.color.line_color));

                    startTimeTv.setEnabled(false);
                    startTimeTv.setText(trainingTempPlanBean.getStart_time());
                    startTimeLl.setBackgroundColor(getResources().getColor(R.color.line_color));

                    endTimeTv.setEnabled(false);
                    endTimeTv.setText(trainingTempPlanBean.getEnd_time());
                    endTimeLl.setBackgroundColor(getResources().getColor(R.color.line_color));

                    trainPositionEt.setEnabled(false);
                    trainPositionEt.setText(trainingTempPlanBean.getTrain_place());
                    trainPositionLl.setBackgroundColor(getResources().getColor(R.color.line_color));

                    trainTeacherEt.setEnabled(false);
                    trainTeacherEt.setText(trainingTempPlanBean.getTeacher());
                    trainTeacherLl.setBackgroundColor(getResources().getColor(R.color.line_color));

                    weekPlanContent.setEnabled(false);
                    weekPlanContent.setText(trainingTempPlanBean.getContent());
                    weekPlanContent.setBackgroundResource(R.drawable.btn_grey);

                    trainPersonTv.setEnabled(true);
                    trainPersonLl.setVisibility(View.VISIBLE);
                    personDetailLl.setVisibility(View.VISIBLE);
                    personDetail.setEnabled(true);

                    trainAuditorLl.setVisibility(View.GONE);

                    trainDaysLl.setBackgroundColor(getResources().getColor(R.color.line_color));
                    trainDaysTv.setText(trainingTempPlanBean.getTrain_days() + "天");

                    if (auditedType == Constant.IS_SEND) {   //审核完毕后分发，再进来就只能查看
                        titleSettingTv.setVisibility(View.GONE);
                        personDetailLl.setVisibility(View.GONE);
                        trainPersonLl.setVisibility(View.GONE);
                        //personDetail.setEnabled(false);
                        //personDetail.setBackgroundResource(R.drawable.btn_grey);

                        //trainPersonTv.setEnabled(false);
                        //trainPersonLlEnter.setBackgroundColor(getResources().getColor(R.color.line_color));


                    } else {
                        titleSettingTv.setVisibility(View.VISIBLE);
                        titleSettingTv.setText("分发");
                    }

                } else if (auditedType == Constant.NOT_AUDITED) {   //未审核通过前

                }
            }

            getTrainLevel();
            getTrainType();
            getTrainAuditor();

        }


    }

    private void getTrainLevel() {
        BaseRequest.getInstance().getService()
                .getTrainLevel("pxcj")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TrainLevelBean>>(this) {

                    @Override
                    protected void onSuccees(BaseResult<List<TrainLevelBean>> t) throws Exception {
                        List<TrainLevelBean> trainLevelList = t.getResults();
                        if (trainLevelList != null && trainLevelList.size() > 0) {
                            trainLevelId = trainLevelList.get(0).getId();

                            String[] mItems = new String[trainLevelList.size()];
                            for (int i = 0; i < trainLevelList.size(); i++) {
                                String name = trainLevelList.get(i).getName();
                                mItems[i] = name;
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewTrainPlanActivity.this, android.R.layout.simple_spinner_item, mItems);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            trainLevelSpinner.setAdapter(adapter);
                            trainLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int pos, long id) {
                                    trainLevelId = trainLevelList.get(pos).getId();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }


                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void getTrainType() {
        BaseRequest.getInstance().getService()
                .getTrainLevel("pxlx")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TrainLevelBean>>(this) {

                    @Override
                    protected void onSuccees(BaseResult<List<TrainLevelBean>> t) throws Exception {
                        List<TrainLevelBean> trainTypeList = t.getResults();
                        if (trainTypeList != null && trainTypeList.size() > 0) {
                            trainTypeId = trainTypeList.get(0).getId();

                            String[] mItems = new String[trainTypeList.size()];
                            for (int i = 0; i < trainTypeList.size(); i++) {
                                String name = trainTypeList.get(i).getName();
                                mItems[i] = name;
                            }


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewTrainPlanActivity.this, android.R.layout.simple_spinner_item, mItems);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            trainTypeSpinner.setAdapter(adapter);
                            trainTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int pos, long id) {
                                    trainTypeId = trainTypeList.get(pos).getId();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void getTrainAuditor() {
        BaseRequest.getInstance().getService()
                .getAuditorList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TrainAuditorBean>>(this) {

                    @Override
                    protected void onSuccees(BaseResult<List<TrainAuditorBean>> t) throws Exception {
                        List<TrainAuditorBean> trainAuditorList = t.getResults();
                        if (trainAuditorList != null && trainAuditorList.size() > 0) {
                            trainAuditorId = trainAuditorList.get(0).getUser_id();

                            String[] mItems = new String[trainAuditorList.size()];
                            for (int i = 0; i < trainAuditorList.size(); i++) {
                                String name = trainAuditorList.get(i).getUser_name();
                                mItems[i] = name;
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewTrainPlanActivity.this, android.R.layout.simple_spinner_item, mItems);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            trainAuditorSpinner.setAdapter(adapter);
                            trainAuditorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int pos, long id) {
                                    trainAuditorId = trainAuditorList.get(pos).getUser_id();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }


                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void sendToMember(TrainingAddTaskBean trainingTempAddPlanBean) {
        BaseRequest.getInstance().getService()
                .sentToMember(trainingTempAddPlanBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TrainLevelBean>>(this) {

                    @Override
                    protected void onSuccees(BaseResult<List<TrainLevelBean>> t) throws Exception {
                        if (t.getCode() == 1) {
//                            if(fromType == Constant.FROM_TEMP_TO_ENTER_TYPE && auditedType == Constant.IS_AUDITED) {
//                                auditedType = Constant.IS_COMLETED;   //分发成功
//                            }
                            Toast.makeText(NewTrainPlanActivity.this, "分发成功，生成培训任务！", Toast.LENGTH_SHORT).show();
                            RxRefreshEvent.publish("publishTrainTask");
                            finish();
                        } else {
                            Toast.makeText(NewTrainPlanActivity.this, "分发失败！", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void addTempInfo(TrainingAddTempBean trainingAddTempBean) {
        BaseRequest.getInstance().getService()
                .addTempInfo(trainingAddTempBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TrainingAddTempBean>>(this) {

                    @Override
                    protected void onSuccees(BaseResult<List<TrainingAddTempBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            Toast.makeText(NewTrainPlanActivity.this, "分发成功，生成培训临时计划，等待主任审核！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(NewTrainPlanActivity.this, "分发失败！", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }


    public void showStartTime(boolean isStartTime) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2008, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (isStartTime) {
                    String startTimeStr = DateUatil.getSelectTime(date);
                    startTimeTv.setText(startTimeStr);
                    startTime = date.getTime();
                } else {
                    String endTimeStr = DateUatil.getSelectTime(date);
                    endTimeTv.setText(endTimeStr);
                    endTime = date.getTime();
                    trainDaysTv.setText(DateUatil.formatDuring(endTime - startTime));
                }
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentTextSize(18)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }


    @OnClick({R.id.title_back, R.id.train_person_tv, R.id.title_setting_tv, R.id.start_time_tv, R.id.end_time_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.train_person_tv:
                Intent intent = new Intent();
                intent.setClass(this, AddressBookActivity.class);
                intent.putExtra("nameList", (Serializable) nameList);
                startActivityForResult(intent, Constant.REQUEST_CODE_ADDRESS_BOOK);
                break;

            case R.id.title_setting_tv:
                if (fromType == Constant.FROM_YEAR_TO_ENTER_TYPE || fromType == Constant.FROM_MONTH_TO_ENTER_TYPE
                        || (fromType == Constant.FROM_TEMP_TO_ENTER_TYPE && auditedType == Constant.IS_AUDITED)) {   //已经审核过后分发时，调更新接口

                    if (isCanSend(IS_SEND)) {
                        TrainingAddTaskBean trainingTempAddPlanBean = new TrainingAddTaskBean();    //后台差一个更新审核状态的字段
                        trainingTempAddPlanBean.setName(trainNameEt.getText().toString());
                        trainingTempAddPlanBean.setStart_time(startTimeTv.getText().toString());
                        trainingTempAddPlanBean.setEnd_time(endTimeTv.getText().toString());
                        trainingTempAddPlanBean.setTrain_days(DateUatil.formatString(endTime - startTime));   //后面修改为计算的时间
                        trainingTempAddPlanBean.setTrain_level(trainLevelId);
                        trainingTempAddPlanBean.setHost_unit(trainUnitEt.getText().toString());
                        trainingTempAddPlanBean.setTrain_place(trainPositionEt.getText().toString());
                        trainingTempAddPlanBean.setType_id(trainTypeId);   //后面选择后修改
                        trainingTempAddPlanBean.setTeacher(trainTeacherEt.getText().toString());
                        trainingTempAddPlanBean.setYear(2019);
                        trainingTempAddPlanBean.setMonth(5);
                        trainingTempAddPlanBean.setRemark(personDetail.getText().toString());
                        trainingTempAddPlanBean.setContent(weekPlanContent.getText().toString());
                        trainingTempAddPlanBean.setPlan_train_id(planTrainId);
                        if (fromType == Constant.FROM_YEAR_TO_ENTER_TYPE) {
                            trainingTempAddPlanBean.setTrain_val("1");
                        } else if (fromType == Constant.FROM_MONTH_TO_ENTER_TYPE) {
                            trainingTempAddPlanBean.setTrain_val("2");
                        } else if (fromType == Constant.FROM_TEMP_TO_ENTER_TYPE) {
                            trainingTempAddPlanBean.setTrain_val("3");
                        }

                        List<TrainingAddTaskBean.UserListBean> userListBeanList = new ArrayList<>();
                        for (int i = 0; i < selectNameList.size(); i++) {
                            TrainingAddTaskBean.UserListBean userListBean = new TrainingAddTaskBean.UserListBean();
                            userListBean.setUser_id(selectNameList.get(i).getUserId());
                            userListBeanList.add(userListBean);
                        }

                        trainingTempAddPlanBean.setUserList(userListBeanList);
                        sendToMember(trainingTempAddPlanBean);
                    }


                } else if (fromType == Constant.FROM_ADD_PLAN_TO_ENTER_TYPE                                                         //添加一个临时计划并提交审核
                        || (fromType == Constant.FROM_TEMP_TO_ENTER_TYPE && auditedType == Constant.NOT_AUDITED)) {         //未审核通过前从临时列表进来再次修改提交审核
                    if (isCanSend(IS_AUDITOR)) {
                        TrainingAddTempBean trainingAddTempBean = new TrainingAddTempBean();
                        trainingAddTempBean.setName(trainNameEt.getText().toString());
                        trainingAddTempBean.setStart_time(startTimeTv.getText().toString());
                        trainingAddTempBean.setEnd_time(endTimeTv.getText().toString());
                        trainingAddTempBean.setTrain_days(DateUatil.formatString(endTime - startTime));   //后面修改为计算的时间
                        trainingAddTempBean.setTrain_level(trainLevelId);
                        trainingAddTempBean.setHost_unit(trainUnitEt.getText().toString());
                        trainingAddTempBean.setTrain_place(trainPositionEt.getText().toString());
                        trainingAddTempBean.setType_id(trainTypeId);   //后面选择后修改
                        trainingAddTempBean.setTeacher(trainTeacherEt.getText().toString());
                        trainingAddTempBean.setContent(weekPlanContent.getText().toString());
                        trainingAddTempBean.setYear(2019/*Integer.valueOf(year)*/);
                        trainingAddTempBean.setMonth(5/*Integer.valueOf(month)*/);
                        //trainingAddTempBean.setRemark(personDetail.getText().toString());
                        trainingAddTempBean.setAuditor(trainAuditorId);   //选择的审核人
                        trainingAddTempBean.setStatus("1");

                        addTempInfo(trainingAddTempBean);
                    }


                }


                break;

            case R.id.start_time_tv:
                showStartTime(true);

                break;

            case R.id.end_time_tv:
                showStartTime(false);
                break;
        }
    }

    private boolean isCanSend(int type) {
        if (TextUtils.isEmpty(trainNameEt.getText())) {
            Toast.makeText(this, "培训名称不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(startTimeTv.getText())) {
            Toast.makeText(this, "开始时间不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(endTimeTv.getText())) {
            Toast.makeText(this, "结束时间不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(trainLevelId)) {
            Toast.makeText(this, "培训层级不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(trainUnitEt.getText())) {
            Toast.makeText(this, "主办单位不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(trainPositionEt.getText())) {
            Toast.makeText(this, "培训地点不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(trainTypeId)) {
            Toast.makeText(this, "培训类型不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(trainTeacherEt.getText())) {
            Toast.makeText(this, "授课人不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(weekPlanContent.getText())) {
            Toast.makeText(this, "培训内容详情不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (type == IS_SEND) {
            if (TextUtils.isEmpty(trainPersonTv.getText())) {
                Toast.makeText(this, "培训人员不能为空！", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (type == IS_AUDITOR) {
            if (TextUtils.isEmpty(trainAuditorId)) {
                Toast.makeText(this, "审核人员不能为空！", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_ADDRESS_BOOK) {
                nameList = (List<AddressBookLevel2>) data.getSerializableExtra("nameList");
                String nameArray = "";
                int j = 0;
                for (int i = 0; i < nameList.size(); i++) {
                    if (nameList.get(i).isTag()) {
                        nameArray += nameList.get(i).getContent() + " ";
                        selectNameList.add(nameList.get(i));
                        j++;
                    }
                }
                trainPersonTv.setText(nameArray);
                //tvPerson.setText("共" + j + "人");
            }
        }
    }

}
