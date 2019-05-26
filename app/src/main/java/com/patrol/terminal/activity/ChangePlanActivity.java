package com.patrol.terminal.activity;

import android.os.Bundle;
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
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.EqTower;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.MonthPlanDetailBean;
import com.patrol.terminal.bean.PlanTypeBean;
import com.patrol.terminal.bean.PlanWeekReqBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.bean.WeekOfMonthBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.widget.NoScrollListView;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChangePlanActivity extends AppCompatActivity {

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
    @BindView(R.id.month_plan_month)
    TextView monthPlanMonth;
    @BindView(R.id.month_plan_line)
    TextView monthPlanLine;
    @BindView(R.id.month_plan_type_lv)
    NoScrollListView monthPlanTypeLv;
    @BindView(R.id.add_tower_more)
    ImageView addTowerMore;
    @BindView(R.id.trouble_more)
    LinearLayout troubleMore;
    @BindView(R.id.mon_plan_type_ll)
    LinearLayout monPlanTypeLl;
    @BindView(R.id.month_yes)
    TextView monthYes;
    @BindView(R.id.create_group_task)
    TextView createGroupTask;

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
    private List<MonthPlanDetailBean> results;
    private Disposable subscribe;
    private List<PlanTypeBean> list1 = new ArrayList<>();
    private List<String> towerList = new ArrayList<>();
    private List<String> typeName = new ArrayList<>();
    private List<LineTypeBean> typeList=new ArrayList<>();
    private String type_id;
    private String type_val;
    private List<String> years = new ArrayList<>();
    private List<List<String>> months = new ArrayList<>();
    private List<List<List<String>>> weeks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_plan);
        ButterKnife.bind(this);
        getLine();
        initview();
    }

    private void initview() {
        titleName.setText("保电计划制定");
        adapter = new AddTowerAdapter(this, eqTowers);
        monthPlanTypeLv.setAdapter(adapter);
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {
            @Override
            public void accept(String type) throws Exception {
                if (type.startsWith("add")) {
                    String[] split = type.split("@");
                    Tower bean = new Tower();
                    bean.setTower_id(split[1]);
                    selectType.add(bean);
                } else if (type.startsWith("delete")) {
                    for (int i = 0; i < selectType.size(); i++) {
                        Tower tower = selectType.get(i);
                        String[] split = type.split("@");
                        if (split[1].equals(tower.getTower_id())) {
                            selectType.remove(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    @OnClick({R.id.title_back, R.id.month_plan_line,R.id.month_yes, R.id.trouble_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.month_plan_line:
                showLine();
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
                monthPlanLine.setText(lineName.get(options1));
                lineId = results.get(options1).getLine_id();
                getTower();
            }
        }).build();
        pvOptions.setPicker(lineName);
        pvOptions.show();
    }

    //获取每个班组负责的线路
    public void getLine() {
        ProgressDialog.show(this,false,"正在加载中");
        selectType.clear();
        lineName.clear();
//        //获取月计划列表
//        BaseRequest.getInstance().getService()
//                .getWeekList(Integer.parseInt(year),Integer.parseInt(month), SPUtil.getDepId(this),null)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<AddWeekListBean>>(this) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<AddWeekListBean>> t) throws Exception {
//                        ProgressDialog.cancle();
////                        results = t.getResults();
//                        for (int i = 0; i < results.size(); i++) {
//                            MonthPlanDetailBean bean = results.get(i);
//                            lineName.add(bean.getLine_name());
//                        }
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        ProgressDialog.cancle();
//                    }
//                });
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

    //获取杆段信息
    public void getTower() {

        towerList.clear();
        eqTowers.clear();
        BaseRequest.getInstance().getService()
                .getTowers( lineId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<EqTower>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<EqTower>> t) throws Exception {
//                        eqTowers = t.getResults();
                        for (int i = 0; i < eqTowers.size(); i++) {
                            towerList.add(eqTowers.get(i).getTowers_name());
                        }
                        adapter.setData(eqTowers);
                        if (towerList.size()<6){
                            addTowerMore.setVisibility(View.GONE);
                        }else {
                            addTowerMore.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }


    //保存
    public void saveWeek() {
        if (type_id.isEmpty()){
            Toast.makeText(this,"请选择工作类型",Toast.LENGTH_SHORT).show();
            return;
        }
        if (lineId.isEmpty()){
            Toast.makeText(this,"请选择线路",Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectType.size()==0){
            Toast.makeText(this,"请添加杆段",Toast.LENGTH_SHORT).show();
            return;
        }
        if (lineId.isEmpty()){
            Toast.makeText(this,"请选择线路",Toast.LENGTH_SHORT).show();
            return;
        }
        PlanWeekReqBean bean = new PlanWeekReqBean();
        bean.setLine_id(lineId);
        bean.setYear(year);
        bean.setMonth(Integer.parseInt(month)+"");
        bean.setWeek(week);
        bean.setType_id(type_id);
        bean.setType_val(type_val);
        bean.setPlanWeekTowerList(selectType);
        BaseRequest.getInstance().getService()
                .saveWeek(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        if (t.getCode()==1){
                            setResult(RESULT_OK);
                            Toast.makeText(ChangePlanActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(ChangePlanActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
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

    //初始化月份数据
    public void initdata() {
        List<List<String>> list =new ArrayList<>();
        for (int i = 2017; i < 2100; i++) {
            years.add(i + "年");
            List<String> monthList=new ArrayList<>();

            for (int j= 1; j< 13; j++) {
                monthList.add(j + "月");
                List<String> weekList=new ArrayList<>();

                int weekNumOfMonth = DateUatil.getWeekNumOfMonth(i + "", j + "");
                for (int y = 1; y < weekNumOfMonth+1; y++) {
                    weekList.add("第" +y + "周");
                }
                list.add(weekList);
                months.add(monthList);

            }

            weeks.add(list);
        }


    }

}
