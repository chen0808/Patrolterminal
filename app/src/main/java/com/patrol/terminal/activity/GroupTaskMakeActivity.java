package com.patrol.terminal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.LineBean;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.WeekPlanBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.FlowGroupView;
import com.patrol.terminal.widget.WeekPlanView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GroupTaskMakeActivity extends BaseActivity {


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
    @BindView(R.id.week_plan_name)
    EditText weekPlanName;
    @BindView(R.id.week_plan_type)
    TextView weekPlanType;
    @BindView(R.id.week_plan_class)
    TextView weekPlanClass;
    @BindView(R.id.week_start_date)
    TextView weekStartDate;
    @BindView(R.id.week_end_date)
    TextView weekEndDate;
    @BindView(R.id.week_num)
    TextView weekNum;
    @BindView(R.id.week_plan_detail)
    EditText weekPlanDetail;
    @BindView(R.id.week_plan_line_ll)
    LinearLayout weekPlanLineLl;
    @BindView(R.id.week_add_line)
    TextView weekAddLine;
    @BindView(R.id.week_delet_line)
    TextView weekDeletLine;
    @BindView(R.id.week_yes)
    TextView weekYes;
    @BindView(R.id.week_start_date_tv)
    TextView weekStartDateTv;
    @BindView(R.id.week_end_date_11)
    LinearLayout weekEndDate11;
    @BindView(R.id.date_line)
    View dateLine;
    @BindView(R.id.incontinuity_tower)
    FlowGroupView incontinuityTower;
    @BindView(R.id.add_incontinuity_tower)
    ImageView addIncontinuityTower;
    @BindView(R.id.task_personal_ll)
    LinearLayout taskPersonalLl;

    private List<LineBean> results;
    private TimePickerView pvTime;
    private long start;
    private String startTime = "";
    private String endTime = "";
    private long end;
    private String year;
    private String month;
    private String week;
    private String m_id;
    private String y_id;
    private String f_id;
    private String w_id;
    private String d_id;
    private String day;
    private String dayNum;
    private boolean flag = true;
    List<String> lineList = new ArrayList<>();
    List<String> towerLine = new ArrayList<>();
    List<String> addList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_plan_make);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("制定组任务");
        weekEndDate11.setVisibility(View.GONE);
        dateLine.setVisibility(View.GONE);
        weekStartDateTv.setText("执行日期");
        String time = getIntent().getStringExtra("time");
        m_id = getIntent().getStringExtra("m_id");
        y_id = getIntent().getStringExtra("y_id");
        f_id = getIntent().getStringExtra("f_id");
        w_id = getIntent().getStringExtra("w_id");
        d_id = getIntent().getStringExtra("d_id");
        day = getIntent().getStringExtra("day");
        week = getIntent().getStringExtra("week");

        weekStartDate.setText(time);
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        if (months.length > 1) {
            dayNum = day.split(" ")[0];
        }
        weekPlanName.setText(time+"组任务");
        weekNum.setText(year + "年" + month + "月第" + week + "周");
        String typeName = SPUtil.getString(this, Constant.TYPE, Constant.TASKTYPENAME, "");
        weekPlanType.setText(typeName);
        getLine();
    }

    @OnClick({R.id.title_back, R.id.week_yes, R.id.week_add_line, R.id.week_delet_line})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.week_yes:
                saveWeekPlan();
                break;
            case R.id.week_add_line:
                hideSoftKeyboard(this);
                if (flag) {
                    showLine();
                }
                break;
            case R.id.week_delet_line:
                showAddLine();
                break;


        }
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //保存周计划
    public void saveWeekPlan() {

        WeekPlanBean bean = new WeekPlanBean();
        List<String> lines = new ArrayList<>();
        bean.setName(weekPlanName.getText().toString().trim());
        bean.setBegin_time(startTime);
        bean.setEnd_time(endTime);
//        bean.set(weekPlanDetail.getText().toString().trim());
        bean.setY_id(y_id);
        bean.setM_id(m_id);
        bean.setF_id(f_id);
        bean.setW_id(w_id);
        bean.setD_id(d_id);
        bean.setDay(day);
        bean.setFinish_time(day);
        bean.setDep_id(SPUtil.getDepId(this));
        bean.setType_val(SPUtil.getString(this, Constant.TYPE, Constant.TASKTYPEVAL, ""));
        bean.setType_id(SPUtil.getTypeId(this));
        bean.setWeek(week);
        int childCount = weekPlanLineLl.getChildCount();
        for (int i = 0; i < childCount; i++) {
            WeekPlanView childAt = (WeekPlanView) weekPlanLineLl.getChildAt(i);
            List<String> lineDetail = childAt.getLineDetail();
            lines.addAll(lineDetail);
        }
        bean.setData_id(lines);
        BaseRequest.getInstance().getService()
                .saveGroupTask(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        RxRefreshEvent.publish("refreshgroup");
                        finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

    //获取每个班组负责的线路
    public void getLine() {
        BaseRequest.getInstance().getService()
                .getGroupLine(year, month, SPUtil.getTypeId(this), dayNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineBean>> t) throws Exception {
                        results = t.getResults();
                        if (results != null) {
                            for (int i = 0; i < results.size(); i++) {
                                lineList.add(results.get(i).getName());
                            }
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

    }


    //所有线路选择框
    private void showLine() {// 不联动的多级选项
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                for (int i = 0; i < results.size(); i++) {
                    if (lineList.get(options1).equals(results.get(i).getName())) {
                        WeekPlanView view = new WeekPlanView(GroupTaskMakeActivity.this, results.get(i), year, month);
                        weekPlanLineLl.addView(view);
                        flag = false;
                    }
                }
                addList.add(lineList.get(options1));
                lineList.remove(options1);
            }
        }).build();
        pvOptions.setPicker(lineList);
        pvOptions.show();

    }

    //已添加选项框
    private void showAddLine() {// 不联动的多级选项
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                weekPlanLineLl.removeViewAt(options1);
                lineList.add(addList.get(options1));
                addList.remove(options1);
                flag = true;
            }
        }).build();
        pvOptions.setPicker(addList);
        pvOptions.show();

    }

    public void showMonth(int type) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        //选中事件回调
//是否只显示中间选中项的label文字，false则每项item全部都带有label。
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if (type == 0) {
                    start = date.getTime();
                    startTime = format.format(date);
                    weekStartDate.setText(startTime);
                } else {
                    if ("".equals(startTime)) {
                        Toast.makeText(GroupTaskMakeActivity.this, "请先选择起始时间", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    end = date.getTime();
                    if (end < start) {
                        Toast.makeText(GroupTaskMakeActivity.this, "结束时间不能低于起始时间", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    endTime = format.format(date);
                    weekEndDate.setText(endTime);
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
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

}
