package com.patrol.terminal.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.AddTowerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.PlanWeekLineBean;
import com.patrol.terminal.bean.PlanWeekReqBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.bean.WeekOfMonthBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.NoScrollListView;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddWeekPlanActivity extends BaseActivity {


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
    @BindView(R.id.month_plan_name)
    EditText monthPlanName;
    @BindView(R.id.month_plan_date)
    TextView monthPlanDate;
    @BindView(R.id.month_plan_class)
    TextView monthPlanClass;
    @BindView(R.id.month_plan_type)
    TextView monthPlanType;
    @BindView(R.id.month_plan_month)
    TextView monthPlanMonth;
    @BindView(R.id.month_plan_line)
    TextView monthPlanLine;
    @BindView(R.id.month_plan_type_lv)
    NoScrollListView monthPlanTypeLv;
    @BindView(R.id.trouble_more)
    LinearLayout troubleMore;
    @BindView(R.id.mon_plan_type_ll)
    LinearLayout monPlanTypeLl;
    @BindView(R.id.month_yes)
    TextView monthYes;
    @BindView(R.id.add_tower_more)
    ImageView addTowerMore;
    private List<String> lineName = new ArrayList<>();
    private List<WeekOfMonthBean> eqTowers = new ArrayList<>();
    private String curMonth;
    private String year;
    private String month;
    private String week;
    private int type = 0;
    private String lineId;
    private List<Tower> selectType = new ArrayList<>();
    private AddTowerAdapter adapter;
    private List<WeekOfMonthBean> results;
    private Disposable subscribe;
    private List<String> typeName = new ArrayList<>();
    private List<LineTypeBean> typeList = new ArrayList<>();
    private String type_id;
    private String lName;
    private String type_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_week_plan);
        ButterKnife.bind(this);
//        initdata();
        initview();
        getLineType();

    }

    private void initview() {
        curMonth = DateUatil.getCurMonth();
        year = curMonth.substring(0, 4);
        month = curMonth.substring(5, 7);
        week = DateUatil.getWeekNum() + "";
        monthPlanDate.setText(curMonth + "第" + week + "周");
        titleName.setText(curMonth + "第" + week + "周计划制定");
        adapter = new AddTowerAdapter(this,eqTowers);
        monthPlanTypeLv.setAdapter(adapter);

        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.startsWith("add")) {
                    String[] split = type.split("@");
                    Tower bean = new Tower();
                    bean.setTower_id(split[1]);
                    bean.setName(split[2]);
                    bean.setId(split[3]);
                    selectType.add(bean);
                } else if (type.startsWith("delete")) {
                    String[] split = type.split("@");

                    for (int i = 0; i < selectType.size(); i++) {
                        Tower tower = selectType.get(i);
                        if (split[1].equals(tower.getTower_id())) {
                            selectType.remove(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    @OnClick({R.id.title_back, R.id.month_plan_line, R.id.month_plan_type, R.id.month_yes, R.id.trouble_more, R.id.month_plan_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.month_plan_line:
                if (type_id == null) {
                    Toast.makeText(this, "请先选择工作类型", Toast.LENGTH_SHORT).show();
                    break;
                }
                showLine();
                break;
            case R.id.month_plan_type:
                showType();
                break;
            case R.id.month_plan_date:
//                showWeek();
                break;
            case R.id.month_yes:
                saveWeek();
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

    //线路添加选项框
    private void showLine() {// 不联动的多级选项
        if (lineName.size() == 0) {
            Toast.makeText(this, "没有获取到线路信息，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                lName = lineName.get(options1);
                monthPlanLine.setText(lName);
                selectType.clear();
                eqTowers.clear();
                for (int i = 0; i < results.size(); i++) {
                    String line_name = results.get(i).getLine_name();
                    if (lName.equals(line_name)){
                        lineId = results.get(i).getLine_id();
                        Log.i("123321",lineId);
                        eqTowers.add(results.get(i));
                    }
                }
                adapter.setData(eqTowers);
            }
        }).build();
        pvOptions.setPicker(lineName);
        pvOptions.show();
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
                type_name = typeList.get(options1).getName();
                monthPlanType.setText(type_name);
                type_id = typeList.get(options1).getId();
                monthPlanLine.setText("点击选择线路");
                selectType.clear();
                eqTowers.clear();
                adapter.setData(eqTowers);
                getLine();
            }
        }).build();
        pvOptions.setPicker(typeName);
        pvOptions.show();
    }

    //获取每个班组负责的线路
    public void getLine() {
        ProgressDialog.show(this, false, "正在加载中");
        lineName.clear();
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .getWeekList(Integer.parseInt(year), Integer.parseInt(month), SPUtil.getDepId(this), type_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<WeekOfMonthBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<WeekOfMonthBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        results = t.getResults();
                        for (int i = 0; i <results.size(); i++) {
                            WeekOfMonthBean bean = results.get(i);
                            String line_name = bean.getLine_name();
                            if (lineName.indexOf(line_name)==-1){
                                lineName.add(line_name);
                            }
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }


    //获取工作类型
    public void getLineType() {
        typeName.clear();
        BaseRequest.getInstance().getService()
                .getLineType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        typeList = t.getResults();
                        for (int i = 0; i < typeList.size(); i++) {
                            LineTypeBean lineTypeBean = typeList.get(i);
                            typeName.add(lineTypeBean.getName());
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }




    //保存
    public void saveWeek() {
        if (type_id.isEmpty()) {
            Toast.makeText(this, "请选择工作类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (lineId.isEmpty()) {
            Toast.makeText(this, "请选择线路", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectType.size() == 0) {
            Toast.makeText(this, "请添加杆段", Toast.LENGTH_SHORT).show();
            return;
        }
        if (lineId.isEmpty()) {
            Toast.makeText(this, "请选择线路", Toast.LENGTH_SHORT).show();
            return;
        }
        PlanWeekReqBean bean = new PlanWeekReqBean();
        bean.setYear(year);
        bean.setMonth(Integer.parseInt(month) + "");
        bean.setWeek(week);
        PlanWeekLineBean planWeekLineBean = new PlanWeekLineBean();
        planWeekLineBean.setType_id(type_id);
        planWeekLineBean.setLine_id(lineId);
        planWeekLineBean.setLine_name(lName);
        planWeekLineBean.setDep_id(SPUtil.getDepId(this));
        planWeekLineBean.setDep_name(SPUtil.getDepName(this));
        planWeekLineBean.setType_name(type_name);
        List<PlanWeekLineBean.TowersBean> list = new ArrayList();
        for (int i = 0; i < selectType.size(); i++) {
            PlanWeekLineBean.TowersBean towersBean = new PlanWeekLineBean.TowersBean();
            Tower tower = selectType.get(i);
            towersBean.setTowers_id(tower.getTower_id());
            towersBean.setName(tower.getName());
            towersBean.setMonth_line_id(tower.getId());
            list.add(towersBean);
        }
        planWeekLineBean.setTowers(list);
        bean.setPlanWeekLine(planWeekLineBean);
        BaseRequest.getInstance().getService()
                .saveWeek(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            setResult(RESULT_OK);
                            Toast.makeText(AddWeekPlanActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddWeekPlanActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
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
//
//    //初始化月份数据
//    public void initdata() {
//        List<List<String>> list = new ArrayList<>();
//        for (int i = 2017; i < 2100; i++) {
//            years.add(i + "年");
//            List<String> monthList = new ArrayList<>();
//
//            for (int j = 1; j < 13; j++) {
//                monthList.add(j + "月");
//                List<String> weekList = new ArrayList<>();
//
//                int weekNumOfMonth = DateUatil.getWeekNumOfMonth(i + "", j + "");
//                for (int y = 1; y < weekNumOfMonth + 1; y++) {
//                    weekList.add("第" + y + "周");
//                }
//                list.add(weekList);
//                months.add(monthList);
//
//            }
//
//            weeks.add(list);
//        }
//
//
//    }

//    //展示月份
//    public void showWeek() {
//
//        //条件选择器
//        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3, View v) {
//
//                //返回的分别是三个级别的选中位置
//                String time = years.get(options1) + months.get(options1).get(option2) + weeks.get(options1).get(option2).get(options3);
//                year = years.get(options1).split("年")[0];
//                month = months.get(options1).get(option2).split("月")[0];
//                week = weeks.get(options1).get(option2).get(options3).substring(1, 2);
//                monthPlanDate.setText(time);
//
//            }
//        }).build();
//        pvOptions.setPicker(years, months, weeks);
//        pvOptions.setSelectOptions(years.indexOf(year + "年"), Integer.parseInt(month) - 1, Integer.parseInt(week) - 1);
//        pvOptions.show();
//    }
}
