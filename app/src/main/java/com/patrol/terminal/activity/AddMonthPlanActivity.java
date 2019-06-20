package com.patrol.terminal.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.LineTypeAdapter;
import com.patrol.terminal.adapter.MonthTypeSelectAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DangerBean;
import com.patrol.terminal.bean.DefectBean;
import com.patrol.terminal.bean.IdsBean;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.SavaMonthDefDanBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.StringUtil;
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

public class AddMonthPlanActivity extends BaseActivity {

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
    @BindView(R.id.month_plan_month)
    TextView monthPlanMonth;
    @BindView(R.id.month_plan_line)
    TextView monthPlanLine;
    @BindView(R.id.month_plan_type1)
    RadioButton monthPlanType1;
    @BindView(R.id.month_plan_type2)
    RadioButton monthPlanType2;
    @BindView(R.id.month_plan_type_rg)
    RadioGroup monthPlanTypeRg;
    @BindView(R.id.month_plan_type_lv)
    ListView monthPlanTypeLv;
    @BindView(R.id.manth_plan_detail_lv)
    NoScrollListView manthPlanDetailLv;
    @BindView(R.id.month_yes)
    TextView monthYes;
    @BindView(R.id.month_plan_no_data)
    TextView monthPlanNoData;
    @BindView(R.id.mon_plan_type_ll)
    LinearLayout monPlanTypeLl;
    @BindView(R.id.trouble_more)
    LinearLayout troubleMore;
    @BindView(R.id.add_more_iv)
    ImageView addMoreIv;


    private String curMonth;
    private String year;
    private String month;
    private int type =0;
    private List<DefectBean> selectType = new ArrayList<>();
    private List<DefectBean> addType = new ArrayList<>();
    private LineTypeAdapter adapter;
    private MonthTypeSelectAdapter selectAdapter;
    private List<LineTypeBean> results;
    private Disposable subscribe;
    private List<DefectBean> list1 = new ArrayList<>();
    private List<DefectBean> list2 = new ArrayList<>();
    private String line_id;
    private String line_name;
    private String Id;
    private String month_line_id;
    private String week_id;
    private String day_id;
    private String week;
    private String day;
    private int  index=2;
    private int from;

//    private static final int MONTH_PLAN = 0;
//    private static final int WEEK_PLAN = 1;
//    private static final int DAY_PLAN = 2;
//    private int changeType = MONTH_PLAN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_month_plan);
        ButterKnife.bind(this);
        initview();

        line_id = getIntent().getStringExtra("line_id");
        month_line_id = getIntent().getStringExtra("id");
            getHaveDefect();
            getHaveDanger();
        
            getDefect();
            getDanger();

    }

    private void initview() {
        from = getIntent().getIntExtra("from", Constant.FROM_MONTHPLAN_TO_ADDMONTH);
        Log.w("linmeng", "initview from:" + from);
        monthPlanClass.setText(SPUtil.getDepName(this));
        line_name = getIntent().getStringExtra("line_name");
        String typename  = getIntent().getStringExtra("type");
        year= getIntent().getStringExtra("year");
        month= getIntent().getStringExtra("month");
        week = getIntent().getStringExtra("week");
        day = getIntent().getStringExtra("day");

        if (from == Constant.FROM_MONTHPLAN_TO_ADDMONTH) {
            curMonth = year+"年"+month+"月";
            titleName.setText(curMonth + "计划");
            //changeType = MONTH_PLAN;
        }
        monthPlanDate.setText(curMonth);
        monthPlanLine.setText(line_name);
        String[] split = typename.split(",");
        for (int i = 0; i < split.length; i++) {
            DefectBean bean = new DefectBean();
            bean.setContent(StringUtil.getTypeSign(split[i])+"计划");
            bean.setFind_time("");
            bean.setType(0);
            bean.setLine_id(line_id);
            selectType.add(bean);
        }
        adapter = new LineTypeAdapter(this, list1);
        selectAdapter = new MonthTypeSelectAdapter(this, selectType);
        monthPlanTypeLv.setAdapter(adapter);
        manthPlanDetailLv.setAdapter(selectAdapter);
        monthPlanTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.month_plan_type1:
                        index=2;
                        setVisibility(list1);
                        break;
                    case R.id.month_plan_type2:
                        index=1;
                        setVisibility(list2);
                        break;
                }
            }
        });
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                if (s.startsWith("add")) {
                    String[] split = s.split("@");
                    DefectBean bean = new DefectBean();
                    bean.setId(split[1]);
                    bean.setFind_time(split[2]);
                    bean.setStart_id(split[3]);
                    bean.setContent(split[4]);
                    bean.setLine_name(line_name);
                    bean.setType(index);
                    selectType.add(bean);
                    addType.add(bean);
                } else if (s.startsWith("delete")) {
                    for (int i = 0; i < selectType.size(); i++) {
                        DefectBean bean = selectType.get(i);
                        String[] split = s.split("@");
                        if (split[1].equals(bean.getId())) {
                            selectType.remove(i);
                            break;
                        }
                    }
                    for (int i = 0; i < addType.size(); i++) {
                        DefectBean bean = addType.get(i);
                        String[] split = s.split("@");
                        if (split[1].equals(bean.getId())) {
                            addType.remove(i);
                            break;
                        }
                    }
                }
                Log.i("11111", selectType.size() + "");
                selectAdapter.setData(selectType);
            }
        });
    }

    public void setVisibility( List<DefectBean> list){
        if (list.size() == 0) {
            monthPlanNoData.setVisibility(View.VISIBLE);
        } else {
            monthPlanNoData.setVisibility(View.GONE);
        }
        if (list.size()>4){
            troubleMore.setVisibility(View.VISIBLE);
        }else {
            troubleMore.setVisibility(View.GONE);
        }
        adapter.setData(list);
    }
    @OnClick({R.id.title_back, R.id.month_plan_line, R.id.trouble_more,R.id.month_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.month_yes:
                if (addType.size()==0){
                    finish();
                    return;
                }
                    savaDefMonth();

                break;
            case R.id.trouble_more:
                if (type == 0) {
                    type = 1;
                    addMoreIv.setImageResource(R.mipmap.icon_newol_up);
                } else {
                    type = 0;
                    addMoreIv.setImageResource(R.mipmap.icon_newol_down);
                }
                adapter.setType(type);
                break;
        }
    }


    //获取月缺陷库
    public void getDefect() {
        list1.clear();
        BaseRequest.getInstance().getService()
                .getDefect(line_id,"0","1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DefectBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DefectBean>> t) throws Exception {
                        List<DefectBean> results = t.getResults();
                        list1.addAll(results);
                        setVisibility(list1);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
    //获取月隐患库
    public void getDanger() {
        list1.clear();
        BaseRequest.getInstance().getService()
                .getDanger(line_id,"0","1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DefectBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DefectBean>> t) throws Exception {
                        List<DefectBean> results = t.getResults();
                        list2.addAll(results);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    //获取已经添加的缺陷列表
    public void getHaveDefect() {
        BaseRequest.getInstance().getService()
                .getHaveDefect(month_line_id,"1","1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DefectBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DefectBean>> t) throws Exception {
                        List<DefectBean> results = t.getResults();
                        for (int i = 0; i < results.size(); i++) {
                            DefectBean defectBean = results.get(i);
                            defectBean.setType(2);
                            selectType.add(defectBean);
                        }

                        selectAdapter.setData(selectType);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }



    //获取已经添加的隐患列表
    public void getHaveDanger() {
        BaseRequest.getInstance().getService()
                .getHaveDanger(month_line_id,"1","1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DefectBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DefectBean>> t) throws Exception {
                        List<DefectBean> results = t.getResults();
                        for (int i = 0; i < results.size(); i++) {
                            DefectBean defectBean = results.get(i);
                            defectBean.setType(1);
                            selectType.add(defectBean);
                        }

                        selectAdapter.setData(selectType);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }


    //保存月缺陷
    public void savaDefMonth() {
        SavaMonthDefDanBean bean = getReqBean(1);
        ProgressDialog.show(this,false,"正在加载");
        BaseRequest.getInstance().getService()
                .savaDefDanMonth(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DangerBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DangerBean>> t) throws Exception {
                       ProgressDialog.cancle();
                        if (t.getCode()==1){
                            Toast.makeText(AddMonthPlanActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(AddMonthPlanActivity.this,t.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
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

    //获取请求参数对象
    public SavaMonthDefDanBean getReqBean(int state){

        SavaMonthDefDanBean bean=new SavaMonthDefDanBean();
        if (state==1){
            bean.setYear(year);
            bean.setMonth(month);
            bean.setId(month_line_id);
        }else if (state==2){
            bean.setId(Id);
            bean.setWeek_id(week_id );
        }else {
            bean.setId(Id);
            bean.setDay_id(day_id );
        }
        List<IdsBean> idlistDec=new ArrayList<>();
        List<IdsBean> idlistDan=new ArrayList<>();
        for (int i = 0; i < addType.size(); i++) {
            if (addType.get(i).getId()!=null){
                IdsBean idsBean=new IdsBean();
                DefectBean defectBean = addType.get(i);
                int type = defectBean.getType();
                idsBean.setId(addType.get(i).getId());
                if (type==1){
                    idlistDan.add(idsBean);
                }else if (type==2){
                    idlistDec.add(idsBean);
                }
            }
        }
        bean.setDangers(idlistDan);
        bean.setDefects(idlistDec);
        return bean;
    }

}
