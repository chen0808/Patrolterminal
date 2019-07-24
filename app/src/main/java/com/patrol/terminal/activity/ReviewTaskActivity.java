package com.patrol.terminal.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.TowerPartAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.LineCheckBean;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.SavaLineBean2;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.bean.TowerPart;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.Calendar;
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
 * 复核任务制定
 */
public class ReviewTaskActivity extends BaseActivity {

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
    @BindView(R.id.month_yes)
    TextView monthYes;
    @BindView(R.id.create_group_task)
    TextView createGroupTask;
    @BindView(R.id.month_plan_start)
    TextView monthPlanStart;
    @BindView(R.id.month_plan_end)
    TextView monthPlanEnd;
    @BindView(R.id.ll_start_time)
    LinearLayout llStartTime;
    @BindView(R.id.ll_end_time)
    LinearLayout llEndTime;
    @BindView(R.id.line4)
    View line4;
    @BindView(R.id.line5)
    View line5;
    @BindView(R.id.day_plan_time)
    TextView dayPlanTime;

    private List<String> typeNameList = new ArrayList<>();
    private List<LineTypeBean> typeList = new ArrayList<>();
    private List<String> typeVal = new ArrayList<>();
    private String[] nameList = {"监督性巡视", "故障巡视", "特殊性巡视"};
    private List<LineTypeBean> typeVal2 = new ArrayList<>();
    private List<List<String>> typeSign = new ArrayList<>();
    private String typeName;
    private LineCheckBean lineCheckBean;
    private String sign;
    private String year;
    private String month;
    private String week;
    private String starttime;
    private String endTime;
    private long start = 0;
    private long end = -1;
    private String line_id;
    private List<Tower> selectBean = new ArrayList<>();
    private List<TowerPart> towerPart = new ArrayList<>();
    private TowerPartAdapter adapter;
    private String startMonth;
    private String startDay;
    private String endMonth;
    private String endDay;
    private String day;
    private AlertDialog personalGroupDialog;
    private Disposable subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_task);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        dayPlanTime.setText(time);

        titleName.setText("复核任务制定");
        getLineType();

        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {
            @Override
            public void accept(String type) throws Exception {
                if (type.equals("hide")) {
                    hideBottomUIMenu();
                } else if (type.equals("show")) {
                    showBottomUIMenu();
                }
            }
        });
    }

    @OnClick({R.id.title_back, R.id.month_plan_type, R.id.month_plan_line, R.id.month_yes, R.id.month_plan_start, R.id.month_plan_end, R.id.day_plan_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.day_plan_time:
                showDay();
                break;
            case R.id.month_yes:
                if (sign == null) {
                    Toast.makeText(this, "请选择工作类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (lineCheckBean == null) {
                    Toast.makeText(this, "请选择一条线路", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (start > end && start != 0 && end != -1 && day == null) {
                    Toast.makeText(this, "请选择起始结束时间，且起始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (adapter.getData() == null || adapter.getData().size() == 0) {
                    Toast.makeText(this, "请选择杆段", Toast.LENGTH_SHORT).show();
                    return;
                }

                ProgressDialog.show(this, true, "正在保存。。。");

                if (month == null) {
                    saveWeekPlan();
                } else if (day != null) {
                    saveDayPlan();
                } else {
                    saveMonthPlan();
                }
                break;
        }
    }

    //获取工作类型
    public void getLineType() {
        typeNameList.clear();
        BaseRequest.getInstance().getService()
                .getWorkType("val")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        typeList = t.getResults();
                        for (int i = 0; i < typeList.size(); i++) {
                            LineTypeBean lineTypeBean = typeList.get(i);
                            typeNameList.add(lineTypeBean.getName());
                        }
                        initType(typeList);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    //类型添加选项框
    private void showType() {// 不联动的多级选项
        if (typeList.size() == 0) {
            Toast.makeText(this, "没有获取到工作类型信息，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                typeName = typeList.get(options1).getName();
                monthPlanType.setText(typeName);
//                type_id = typeList.get(options1).getId();
                sign = typeList.get(options1).getSign();

            }
        }).build();
        pvOptions.setPicker(typeNameList);
        pvOptions.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    public void initType(List<LineTypeBean> list) {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        List<String> list4 = new ArrayList<>();
        typeVal.add("定巡");
        typeVal.add("定检");
        typeVal.add("缺陷");
        typeVal.add("隐患");
        for (int i = 0; i < list.size(); i++) {
            LineTypeBean lineTypeBean = list.get(i);
            if ("1".equals(lineTypeBean.getVal())) {
                list1.add(StringUtil.getTypeSign(lineTypeBean.getSign()));
            } else if ("2".equals(lineTypeBean.getVal())) {
                list2.add(StringUtil.getTypeSign(lineTypeBean.getSign()));
            }
        }
        list3.add("缺陷处理");
        list4.add("隐患消除");
        typeSign.add(list1);
        typeSign.add(list2);
        typeSign.add(list3);
        typeSign.add(list4);
    }

    //展示工作类型
    public void showTypeSign() {

        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                typeName = typeSign.get(options1).get(option2);
                monthPlanType.setText(typeName);
                for (int i = 0; i < typeList.size(); i++) {
                    LineTypeBean lineTypeBean = typeList.get(i);
                    if (typeName.contains(lineTypeBean.getName())) {
//                        type_id = lineTypeBean.getId();
                        sign = lineTypeBean.getSign();
                    }
                }

            }
        }).build();
        pvOptions.setPicker(typeVal, typeSign);
        pvOptions.setSelectOptions(0, 0);
        pvOptions.show();
    }

    private void showTypeSign2() {
        typeVal2.clear();
        typeVal2.add(new LineTypeBean("监督性巡视", "11"));
        typeVal2.add(new LineTypeBean("故障巡视", "2"));
        typeVal2.add(new LineTypeBean("特殊性巡视", "4"));
        //条件选择器
       /* OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                typeName = typeVal2.get(options1).getName();
                monthPlanType.setText(typeName);
                for (int i = 0; i < typeVal2.size(); i++) {
                    LineTypeBean lineTypeBean = typeVal2.get(i);
                    if (typeName.contains(lineTypeBean.getName())) {
                        sign = lineTypeBean.getSign();
                    }
                }
            }
        }).build();
        pvOptions.setPicker(nameList);
        pvOptions.setSelectOptions(0);
        pvOptions.show();*/


        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择工作类型");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setSingleChoiceItems(nameList, nameList.length, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int options1) {
                monthPlanType.setText(nameList[options1]);
                for (int i = 0; i < typeVal2.size(); i++) {
                    LineTypeBean lineTypeBean = typeVal2.get(i);
                    if (nameList[options1].contains(lineTypeBean.getName())) {
                        sign = lineTypeBean.getSign();
                    }
                }
                personalGroupDialog.dismiss();
            }
        });

        personalGroupDialog = alertBuilder.create();
        personalGroupDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 24 && resultCode == RESULT_OK) {
            if (data != null) {
                lineCheckBean = (LineCheckBean) data.getSerializableExtra("bean");
                line_id = lineCheckBean.getId();
                monthPlanLine.setText(lineCheckBean.getName());
                getTempTower();
            }
        }
    }

    //保存临时月计划
    public void saveMonthPlan() {
        SavaLineBean2 bean = new SavaLineBean2();
        bean.setLine_id(lineCheckBean.getId());
        bean.setLine_name(lineCheckBean.getName());
//        bean.setType_id(type_id);
        bean.setType_sign(sign);
        bean.setType_name(typeName);
        bean.setStart_time(starttime);
        bean.setEnd_time(endTime);
        bean.setYear(String.valueOf(year));
        bean.setMonth(String.valueOf(month));
        List<TowerPart> data = adapter.getData();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName_start() != null && data.get(i).getName_end() != null) {
                data.get(i).setName(data.get(i).getName_start() + "-" + data.get(i).getName_end());
            }
        }
        bean.setTowers(data);
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .saveMonthPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            Toast.makeText(ReviewTaskActivity.this, "制定成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            ProgressDialog.cancle();
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    //保存临时周计划
    public void saveWeekPlan() {
        SavaLineBean2 bean = new SavaLineBean2();
        bean.setLine_id(lineCheckBean.getId());
        bean.setLine_name(lineCheckBean.getName());
//        bean.setType_id(type_id);
        bean.setType_sign(sign);
        bean.setType_name(typeName);
        bean.setStart_time(starttime);
        bean.setEnd_time(endTime);
        bean.setYear(String.valueOf(year));
        bean.setWeek(String.valueOf(week));
        List<TowerPart> data = adapter.getData();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName_start() != null && data.get(i).getName_end() != null) {
                data.get(i).setName(data.get(i).getName_start() + "-" + data.get(i).getName_end());
            }
        }
        bean.setTowers(data);
        BaseRequest.getInstance().getService()
                .saveWeekPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            Toast.makeText(ReviewTaskActivity.this, "制定成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            ProgressDialog.cancle();
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    //保存临时日计划
    public void saveDayPlan() {
        SavaLineBean2 bean = new SavaLineBean2();
        bean.setLine_id(lineCheckBean.getId());
        bean.setLine_name(lineCheckBean.getName());
//        bean.setType_id(type_id);
        bean.setType_sign(sign);
        bean.setType_name(typeName);
        bean.setStart_time(year + "-" + month + "-" + day);
        bean.setEnd_time(year + "-" + month + "-" + day);
        bean.setYear(String.valueOf(year));
        bean.setMonth(String.valueOf(month));
        bean.setDay(String.valueOf(day));
        List<TowerPart> data = adapter.getData();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName_start() != null && data.get(i).getName_end() != null) {
                data.get(i).setName(data.get(i).getName_start() + "-" + data.get(i).getName_end());
            }
        }
        bean.setTowers(data);
        BaseRequest.getInstance().getService()
                .saveDayPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            Toast.makeText(ReviewTaskActivity.this, "制定成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            ProgressDialog.cancle();
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    public void showDay() {
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        int curMonth = Integer.parseInt(months[0]);
        int curYear = Integer.parseInt(years[0]);
        int curDay = Integer.parseInt(days[0]);
        if(curMonth == 1){
            curMonth = 12;
            curYear = curYear - 1;
        } else {
            curMonth = curMonth - 1;
        }

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(curYear, curMonth, curDay);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        //选中事件回调
        //是否只显示中间选中项的label文字，false则每项item全部都带有label。
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = DateUatil.getDay(date);
                dayPlanTime.setText(time);
                String[] times = time.split("年");
                String[] months = times[1].split("月");
                year = times[0];
                month = months[0];
                day = months[1].split("日")[0];
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

    public void showDay(int type) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        if (month != null) {
            startDate.set(Integer.valueOf(year), Integer.valueOf(month) - 1, 1);
            endDate.set(Integer.valueOf(year), Integer.valueOf(month) - 1, 31);
        } else if (week != null) {
            startDate.set(Integer.valueOf(year), Integer.valueOf(startMonth) - 1, Integer.valueOf(startDay));
            endDate.set(Integer.valueOf(year), Integer.valueOf(endMonth) - 1, Integer.valueOf(endDay));
        }
        //时间选择器 ，自定义布局
        //选中事件回调
        //是否只显示中间选中项的label文字，false则每项item全部都带有label。
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                if (type == 1) {
                    starttime = DateUatil.getDate(date);
                    start = date.getTime();
                    monthPlanStart.setText(starttime);
                } else {
                    endTime = DateUatil.getDate(date);
                    end = date.getTime();
                    monthPlanEnd.setText(endTime);
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

    //获取工作类型
    public void getTempTower() {
        ProgressDialog.show(this, false, "正在加载。。。");
        BaseRequest.getInstance().getService()
                .getTempTower(line_id, "name")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<Tower>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<Tower>> t) throws Exception {
                        ProgressDialog.cancle();
//                        monPlanTowerLl.setVisibility(View.VISIBLE);
////                        TempTowerAdapter adapter = new TempTowerAdapter(TemporaryActivity.this, t.getResults());
////                        monthPlanTower.setAdapter(adapter);
//                        monthPlanTower.setLayoutManager(new LinearLayoutManager(ReviewTaskActivity.this));
//                        towerPart.clear();
//                        towerPart.add(new TowerPart("", "", "", "3"));
//                        adapter = new TowerPartAdapter(R.layout.item_tower_part, towerPart, t.getResults());
//                        monthPlanTower.setAdapter(adapter);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    class TempTowerAdapter extends BaseAdapter {
        private Context context;
        private List<Tower> lineTypeBeans;
        private int type = 0;

        public TempTowerAdapter(Context context, List<Tower> traceList) {
            this.context = context;
            this.lineTypeBeans = traceList;
        }

        @Override
        public int getCount() {

            return lineTypeBeans.size();

        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_add_group_task, parent, false);
                holder.itemTroubleName = (TextView) convertView.findViewById(R.id.add_group_task_name);
                holder.taskType = (TextView) convertView.findViewById(R.id.add_group_task_type);
                holder.itemTroubleCheck = (ImageView) convertView.findViewById(R.id.add_group_task_check);
                holder.itemTaskCheck = (RadioButton) convertView.findViewById(R.id.add_group_task_rb);
                holder.item = (RelativeLayout) convertView.findViewById(R.id.personal_task_item);

                convertView.setTag(holder);
            }
            Tower listBean = lineTypeBeans.get(position);
            holder.itemTroubleName.setText(listBean.getName());
            boolean check = listBean.isCheck();
            if (check==true){
                holder.itemTroubleCheck.setImageResource(R.mipmap.check_yes);
            }else {
                holder.itemTroubleCheck.setImageResource(R.mipmap.check_no);
            }
            holder.taskType.setVisibility(View.GONE);
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectBean.size() == 0) {
                        Tower tower = new Tower();
                        tower.setTower_type("2");
                        tower.setName(listBean.getName());
                        tower.setTower_id(listBean.getId());
                        selectBean.add(tower);
                        listBean.setCheck(true);
                        holder.itemTroubleCheck.setImageResource(R.mipmap.check_yes);
                    } else {
                        int isExit = 0;
                        for (int i = 0; i < selectBean.size(); i++) {
                            Tower dayOfWeekBean = selectBean.get(i);
                            if (dayOfWeekBean.getTower_id().equals(listBean.getId())) {
                                isExit = 1;
                                selectBean.remove(i);
                                listBean.setCheck(false);
                                holder.itemTroubleCheck.setImageResource(R.mipmap.check_no);
                                return;
                            }
                        }
                        if (isExit == 0) {
                            Tower tower = new Tower();
                            tower.setTower_type("2");
                            tower.setTower_id(listBean.getId());
                            tower.setName(listBean.getName());
                            selectBean.add(tower);
                            listBean.setCheck(true);
                            holder.itemTroubleCheck.setImageResource(R.mipmap.check_yes);
                        }
                    }


                }
            });
            return convertView;
        }

        public void setData(List<Tower> typeBeanList) {
            lineTypeBeans = typeBeanList;
            notifyDataSetChanged();

        }


        class ViewHolder {
            private TextView itemTroubleName;
            private TextView taskType;
            private RelativeLayout item;
            private ImageView itemTroubleCheck;
            private RadioButton itemTaskCheck;
        }
    }

    protected void hideBottomUIMenu() {

        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                //布局位于状态栏下方
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                //全屏
//                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                //隐藏导航栏
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= 19) {
            uiOptions |= 0x00001000;
        } else {
            uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

    }

    protected void showBottomUIMenu() {
        //显示虚拟按键
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            //低版本sdk
            View v = getWindow().getDecorView();
            v.setSystemUiVisibility(View.VISIBLE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE ;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
