package com.patrol.terminal.activity;

import android.content.Context;
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

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.AddTowerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.LineCheckBean;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.PlanWeekReqBean;
import com.patrol.terminal.bean.SavaLineBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.bean.WeekOfMonthBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.utils.TimeUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
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

    private List<String> lineName = new ArrayList<>();

    private int year;
    private int month;
    private int week;
    private int type = 0;
    private List<Tower> selectType = new ArrayList<>();
    private AddTowerAdapter adapter;
    private List<WeekOfMonthBean> results=new ArrayList<>();
    private Disposable subscribe;
    private List<LineTypeBean> typeList = new ArrayList<>();

    private String type_name;
    private List<WeekOfMonthBean> linelist;
    private String beginDate;
    private String endDate;
    private String sign;
    private List<String> typeVal = new ArrayList<>();
    private List<List<String>> typeSign = new ArrayList<>();
    private LineCheckBean lineCheckBean;
    private String line_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_week_plan);
        ButterKnife.bind(this);
        initview();
        getWeekInfoList();
        getLineType();
    }

    private void initview() {
        initdate();
        titleName.setText("第"+week+"周计划制定("+beginDate +"-"+ endDate +")");
        adapter = new AddTowerAdapter(this,results);
        monthPlanTypeLv.setAdapter(adapter);
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.startsWith("add")) {
                    String[] split = type.split("@");
                    Tower bean = new Tower();
                    bean.setTowers_id(split[1]);
                    bean.setLine_id(split[2]);
                    bean.setLine_name(split[3]);
                    bean.setName(split[4]);
                    bean.setType_id(split[5]);
                    bean.setType_name(split[6]);
                    bean.setType_sign(split[7]);
                    bean.setMonth_line_id(split[8]);
                    bean.setDefect_id(split[9]);
                    bean.setDep_id(SPUtil.getDepId(AddWeekPlanActivity.this));
                    bean.setDep_name(SPUtil.getDepName(AddWeekPlanActivity.this));
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
    //初始化日期
    public void initdate() {
       year=getIntent().getIntExtra("year",2019);
        week=getIntent().getIntExtra("week",23)-1;
        beginDate = TimeUtil.getFirstDayOfWeek(year,week);
        endDate = TimeUtil.getLastDayOfWeek(year,week);
        month=Integer.parseInt(TimeUtil.getMonthOfWeek(year,week));
    }
    private void getWeekInfoList() {
        ProgressDialog.show(this,false,"正在加载。。。");
        //获取周计划杆段列表
        BaseRequest.getInstance().getService()
                .getWeekListWeek(year, month, SPUtil.getDepId(this),null,null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<WeekOfMonthBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<WeekOfMonthBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        results = t.getResults();
                        adapter.setData(results);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    @OnClick({R.id.title_back, R.id.month_plan_line, R.id.month_plan_type, R.id.month_yes, R.id.trouble_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
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
                if (line_id==null){
                    saveWeek();
                }else {
                    saveWeekPlan();
                }

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
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                type_name = typeSign.get(options1).get(option2);
                monthPlanType.setText(type_name);
                for (int i = 0; i < typeList.size(); i++) {
                    LineTypeBean lineTypeBean = typeList.get(i);
                    if (type_name.equals(lineTypeBean.getName())){
                        sign = lineTypeBean.getSign();
                    }
                }

            }
        }).build();
        pvOptions.setPicker(typeVal, typeSign);
        pvOptions.setSelectOptions(0, 0);
        pvOptions.show();
    }

    //获取每个班组负责的线路
    public void getLine() {
        lineName.clear();
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .getWeekList(year, month, SPUtil.getDepId(this), null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<WeekOfMonthBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<WeekOfMonthBean>> t) throws Exception {
                        linelist = t.getResults();
                        for (int i = 0; i < linelist.size(); i++) {
                            WeekOfMonthBean bean = linelist.get(i);
                            String line_name = bean.getLine_name();
                            if (lineName.indexOf(line_name)==-1){
                                lineName.add(line_name);
                            }
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }


    //获取工作类型
    public void getLineType() {
        BaseRequest.getInstance().getService()
                .getWorkType("sort")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        typeList = t.getResults();
                        initType(typeList);

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }




    //保存
    public void saveWeek() {

        PlanWeekReqBean bean = new PlanWeekReqBean();
        bean.setYear(year+"");
        bean.setMonth(month + "");
        bean.setWeek(week+"");
        bean.setBegin_time(DateUatil.dateToDate(year+"年"+beginDate));
        bean.setEnd_time(DateUatil.dateToDate(year+"年"+endDate));
        bean.setTowers(selectType);

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


    public void initType(List<LineTypeBean> list){
        List<String> list1=new ArrayList<>();
        List<String> list2=new ArrayList<>();
        List<String> list3=new ArrayList<>();
        List<String> list4=new ArrayList<>();
        typeVal.add("定巡");
        typeVal.add("定检");
        typeVal.add("缺陷");
        typeVal.add("隐患");
        for (int i = 0; i < list.size(); i++) {
            LineTypeBean lineTypeBean = list.get(i);
            if ("1".equals(lineTypeBean.getVal())){
                list1.add(StringUtil.getTypeSign(lineTypeBean.getSign()));
            }else if ("2".equals(lineTypeBean.getVal())){
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==24&&resultCode==-1){
            if (data != null) {
                selectType.clear();
                lineCheckBean = (LineCheckBean) data.getSerializableExtra("bean");
                line_id = lineCheckBean.getId();
                monthPlanLine.setText(lineCheckBean.getName());
                getTempTower();
            }
        }
    }

    //获取杆塔
    public void getTempTower() {
        ProgressDialog.show(this,false,"正在加载。。。");
        BaseRequest.getInstance().getService()
                .getTempTower(line_id,"name")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<Tower>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<Tower>> t) throws Exception {
                        ProgressDialog.cancle();
                        TempTowerAdapter adapter=new TempTowerAdapter(AddWeekPlanActivity.this,t.getResults());
                        monthPlanTypeLv.setAdapter(adapter);

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
        private int type=0;
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
            TempTowerAdapter.ViewHolder holder;
            if (convertView != null) {
                holder = (TempTowerAdapter.ViewHolder) convertView.getTag();
            } else {
                holder = new TempTowerAdapter.ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_add_group_task, parent, false);
                holder.itemTroubleName = (TextView) convertView.findViewById(R.id.add_group_task_name);
                holder.taskType = (TextView) convertView.findViewById(R.id.add_group_task_type);
                holder.itemTroubleCheck = (CheckBox) convertView.findViewById(R.id.add_group_task_check);
                holder.itemTaskCheck = (RadioButton) convertView.findViewById(R.id.add_group_task_rb);
                holder.item = (RelativeLayout) convertView.findViewById(R.id.personal_task_item);

                convertView.setTag(holder);
            }
            Tower listBean = lineTypeBeans.get(position);
            holder.itemTroubleName.setText(listBean.getName());
            boolean check = listBean.isCheck();
            if (check==true){
                holder.itemTroubleCheck.setChecked(true);
            }else {
                holder.itemTroubleCheck.setChecked(false);
            }
            holder.taskType.setVisibility(View.GONE);
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectType.size()==0){
                        Tower tower=new Tower();
                        tower.setTower_type("2");
                        tower.setName(listBean.getName());
                        tower.setTower_id(listBean.getId());
                        listBean.setCheck(true);
                        selectType.add(tower);
                        holder.itemTroubleCheck.setChecked(true);
                    }else {
                        int isExit=0;
                        for (int i = 0; i < selectType.size(); i++) {
                            Tower dayOfWeekBean = selectType.get(i);
                            if (dayOfWeekBean.getTower_id().equals(listBean.getId())){
                                isExit=1;
                                selectType.remove(i);
                                holder.itemTroubleCheck.setChecked(false);
                                listBean.setCheck(false);
                                return;
                            }
                        }
                        if (isExit==0){
                            Tower tower=new Tower();
                            tower.setTower_type("2");
                            tower.setTower_id(listBean.getId());
                            tower.setName(listBean.getName());
                            selectType.add(tower);
                            listBean.setCheck(true);
                            holder.itemTroubleCheck.setChecked(true);
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
            private   TextView itemTroubleName;
            private   TextView taskType;
            private   RelativeLayout item;
            private CheckBox itemTroubleCheck;
            private RadioButton itemTaskCheck;
        }
    }

    //保存周计划
    public void saveWeekPlan() {
        SavaLineBean bean = new SavaLineBean();
        bean.setLine_id(lineCheckBean.getId());
        bean.setLine_name(lineCheckBean.getName());
        bean.setType_sign(sign);
        bean.setType_name(type_name);
        bean.setStart_time(beginDate);
        bean.setEnd_time(endDate);
        bean.setYear(year+"");
        bean.setMonth(month+"");
        bean.setWeek(week+"");
        bean.setTowers(selectType);
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .saveWeekPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            Toast.makeText(AddWeekPlanActivity.this, "制定成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
}
