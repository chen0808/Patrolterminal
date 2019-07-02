package com.patrol.terminal.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.AddDayAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DayOfWeekBean;
import com.patrol.terminal.bean.LineCheckBean;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.TimeUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.TypedArrayUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddDayPlanActivity extends BaseActivity {


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

    @BindView(R.id.month_plan_type)
    TextView monthPlanType;
    @BindView(R.id.month_plan_month)
    TextView monthPlanMonth;
    @BindView(R.id.month_plan_line)
    TextView monthPlanLine;
    @BindView(R.id.month_plan_type_lv)
    ListView monthPlanTypeLv;
    @BindView(R.id.trouble_more)
    LinearLayout troubleMore;
    @BindView(R.id.mon_plan_type_ll)
    LinearLayout monPlanTypeLl;
    @BindView(R.id.month_yes)
    TextView monthYes;
    @BindView(R.id.add_tower_more)
    ImageView addTowerMore;
    @BindView(R.id.day_no_data)
    TextView dayNoData;
    @BindView(R.id.create_group_task)
    TextView createGroupTask;
    @BindView(R.id.add_week_num_select)
    TextView addWeekNumSelect;
    @BindView(R.id.add_week_num_all)
    TextView addWeekNumAll;
    @BindView(R.id.add_week_num_ll)
    LinearLayout addWeekNumLl;
    private String year;
    private String month;
    private int type = 0;
    private List<DayOfWeekBean> selectType = new ArrayList<>();
    private AddDayAdapter adapter;
    private List<DayOfWeekBean> results;
    private List<DayOfWeekBean> showList=new ArrayList<>();
    private List<DayOfWeekBean> linList = new ArrayList<>();
    private Disposable subscribe;
    private List<LineTypeBean> typeList = new ArrayList<>();
    private String day;
    private String sign;
    private String week;
    private String[] types;
    private int selectNum = 0;
    private int allNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_week_plan);
        ButterKnife.bind(this);
        initview();

        getLineType();
        getWeekPlan();
    }


    private void initview() {
        titleName.setClickable(false);
        String from = getIntent().getStringExtra("from");
        if ("DayPlanFrgment".equals(from)) {
            titleName.setClickable(true);
        }
        inteDate();
        titleName.setText(year + "年" + month + "月" + day + "日计划制定");
        adapter = new AddDayAdapter(this, linList);
        monthPlanTypeLv.setAdapter(adapter);

        subscribe = RxRefreshEvent.getDayObservable().subscribe(new Consumer<DayOfWeekBean>() {

            @Override
            public void accept(DayOfWeekBean type) throws Exception {
                if (selectType.size() == 0) {
                    type.setWeek_tower_id(type.getId());
                    type.setMonth(Integer.parseInt(month));
                    if (month.length()==1) {
                        type.setStart_time(year + "-0" + month + "-" + day);
                        type.setEnd_time(year + "-0" + month + "-" + day);
                    } else {
                        type.setStart_time(year + "-" + month + "-" + day);
                        type.setEnd_time(year + "-" + month + "-" + day);
                    }
                    type.setDay(day);
                    selectType.add(type);
                    selectNum++;
                } else {
                    int isExit = 0;
                    for (int i = 0; i < selectType.size(); i++) {
                        DayOfWeekBean dayOfWeekBean = selectType.get(i);
                        if (dayOfWeekBean.getId().equals(type.getId())) {
                            isExit = 1;
                            selectType.remove(i);
                            selectNum--;
                            break;

                        }
                    }
                    if (isExit == 0) {
                        type.setWeek_tower_id(type.getId());
                        type.setMonth(Integer.parseInt(month));
                        if (month.length()==1) {
                            type.setStart_time(year + "-0" + month + "-" + day);
                            type.setEnd_time(year + "-0" + month + "-" + day);
                        } else {
                            type.setStart_time(year + "-" + month + "-" + day);
                            type.setEnd_time(year + "-" + month + "-" + day);
                        }
                        type.setDay(day);
                        selectNum++;
                        selectType.add(type);
                    }
                }
                addWeekNumSelect.setText(selectNum + "");
            }
        });
    }

    @OnClick({R.id.title_back, R.id.month_plan_line, R.id.month_plan_type, R.id.month_yes, R.id.trouble_more, R.id.title_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_name:
                showDay();
                break;
            case R.id.month_plan_line:
                Intent intent = new Intent(this, LineCheckActivity.class);
                intent.putExtra("from", "Temporary");
                startActivityForResult(intent, 24);
                break;
            case R.id.month_plan_type:
                showTypeSign();
                break;
            case R.id.month_yes:
                saveDay();
                break;

            case R.id.trouble_more:
                if (type == 0) {
                    type = 1;
                    addTowerMore.setImageResource(R.mipmap.icon_newol_up);
                } else {
                    type = 0;
                    addTowerMore.setImageResource(R.mipmap.icon_newol_down);
                }
                adapter.setType(type);
                break;

        }
    }

    //展示工作类型
    public void showTypeSign() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择工作类型");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setSingleChoiceItems(types, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int options1) {
                showList.clear();
                selectType.clear();
                selectNum=0;
                addWeekNumSelect.setText(selectNum+"");
                monthPlanType.setText(types[options1]);
                if ("全部".equals(types[options1])) {
                    sign = null;
                    adapter.setData(results);
                    if (results == null || results.size() == 0) {
                        dayNoData.setVisibility(View.VISIBLE);
                        addWeekNumLl.setVisibility(View.GONE);
                    } else {
                        dayNoData.setVisibility(View.GONE);
                        addWeekNumLl.setVisibility(View.VISIBLE);
                        allNum = results.size();
                        addWeekNumAll.setText("/" + allNum + ")");
                    }
                } else {

                    //根据工作名称获取工作类型
                    for (int i = 0; i < typeList.size(); i++) {
                        LineTypeBean lineTypeBean = typeList.get(i);
                        if (types[options1].contains(lineTypeBean.getName())) {
                            sign = lineTypeBean.getSign();
                        }
                    }
                    //根据工作类型过滤
                    for (int i = 0; i < results.size(); i++) {
                        DayOfWeekBean dayOfWeekBean = results.get(i);
                        dayOfWeekBean.setIscheck(false);
                        String type_sign = dayOfWeekBean.getType_sign();
                        String[] split = type_sign.split(",");
                        for (int j = 0; j < split.length; j++) {
                            if (sign.equals(split[j])){
                                showList.add(dayOfWeekBean);
                            }
                        }
                    }
                    adapter.setData(showList);
                    if (showList == null || showList.size() == 0) {
                        dayNoData.setVisibility(View.VISIBLE);
                        addWeekNumLl.setVisibility(View.GONE);
                    } else {
                        dayNoData.setVisibility(View.GONE);
                        addWeekNumLl.setVisibility(View.VISIBLE);
                        allNum = showList.size();
                        addWeekNumAll.setText("/" + allNum + ")");
                    }
                }

                dialog.dismiss();
            }
        });

        AlertDialog typeDialog = alertBuilder.create();
        typeDialog.show();

    }



    //获取每个班组负责的线路
    public void getWeekPlan() {
        ProgressDialog.show(this, false, "正在加载。。。。");
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .getDayPlan(year, week, SPUtil.getDepId(this), sign, null, "end_time,line_id,name")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayOfWeekBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DayOfWeekBean>> t) throws Exception {


                        results = t.getResults();
                        adapter.setData(results);
                        if (results == null || results.size() == 0) {
                            dayNoData.setVisibility(View.VISIBLE);
                            addWeekNumLl.setVisibility(View.GONE);
                        } else {
                            dayNoData.setVisibility(View.GONE);
                            addWeekNumLl.setVisibility(View.VISIBLE);
                            allNum = results.size();
                            addWeekNumAll.setText("/" + allNum + ")");
                        }
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    //获取工作类型
    public void getLineType() {
        BaseRequest.getInstance().getService()
                .getWorkType("val")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        typeList = t.getResults();
                        types = new String[typeList.size() + 1];
                        for (int i = 0; i < typeList.size(); i++) {
                            types[i] = typeList.get(i).getName();
                        }
                        types[typeList.size()] = "全部";
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }


    //保存
    public void saveDay() {

        if (selectType.size() == 0) {
            Toast.makeText(this, "请添加计划", Toast.LENGTH_SHORT).show();
            return;
        }
        BaseRequest.getInstance().getService()
                .addDayPlan(selectType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            setResult(RESULT_OK);
                            Toast.makeText(AddDayPlanActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddDayPlanActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
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
        if (subscribe != null) {
            subscribe.dispose();
        }
    }


    public void inteDate() {
        month = getIntent().getStringExtra("month");
        year = getIntent().getStringExtra("year");
        day = getIntent().getStringExtra("day");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = format.parse(year + "-" + month + "-" + day);
            week = TimeUtil.getWeekOfDate(parse.getTime()) + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = DateUatil.getDay(date);
                titleName.setText(time + "计划制定");
                String[] times = time.split("年");
                String[] months = times[1].split("月");
                year = times[0];
                month = months[0];
                day = months[1].split("日")[0];
                getWeekPlan();
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
