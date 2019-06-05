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
import com.patrol.terminal.adapter.AddDayAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DayOfWeekBean;
import com.patrol.terminal.bean.LineCheckBean;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.SavaLineBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.StringUtil;
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
    private List<String> lineNameList = new ArrayList<>();
    private String year;
    private String month;
    private int type = 0;
    private List<DayOfWeekBean> selectType = new ArrayList<>();
    private AddDayAdapter adapter;
    private List<DayOfWeekBean> results;
    private List<DayOfWeekBean> linList=new ArrayList<>();
    private Disposable subscribe;
    private List<LineTypeBean> typeList=new ArrayList<>();
    private String type_id;
    private String day;
    private String lineName;
    private String typeName;
    private List<DayOfWeekBean> lineList=new ArrayList<>();

    private String sign;
    private List<String> typeVal = new ArrayList<>();
    private List<List<String>> typeSign = new ArrayList<>();

    private List<Tower> selectBean=new ArrayList<>();
    private LineCheckBean lineCheckBean;
    private String line_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_week_plan);
        ButterKnife.bind(this);
        initview();

        getLineType();
        getLine();
        getWeekPlan();
    }


    private void initview() {

        inteDate();
        titleName.setText(year+"年"+month+"月" +day+ "日计划制定");
        adapter = new AddDayAdapter(this, linList);
        monthPlanTypeLv.setAdapter(adapter);

        subscribe = RxRefreshEvent.getDayObservable().subscribe(new Consumer<DayOfWeekBean>() {

            @Override
            public void accept(DayOfWeekBean type) throws Exception {
                if (selectType.size()==0){
                    type.setWeek_tower_id(type.getId());
                    type.setDay(day);
                    selectType.add(type);
                }else {
                    int isExit=0;
                    for (int i = 0; i < selectType.size(); i++) {
                        DayOfWeekBean dayOfWeekBean = selectType.get(i);
                        if (dayOfWeekBean.getId().equals(type.getId())){
                            isExit=1;
                            selectType.remove(i);
                            return;
                        }
                    }
                    if (isExit==0){
                        type.setWeek_tower_id(type.getId());
                        type.setDay(day);
                        selectType.add(type);
                    }
                }

            }
        });
    }

    @OnClick({R.id.title_back, R.id.month_plan_line, R.id.month_plan_type,R.id.month_yes, R.id.trouble_more})
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
                    saveDay();
                }else {
                    saveDayPlan();
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
                typeName = typeSign.get(options1).get(option2);
                monthPlanType.setText(typeName);
                for (int i = 0; i < typeList.size(); i++) {
                    LineTypeBean lineTypeBean = typeList.get(i);
                    if (typeName.equals(lineTypeBean.getName())){
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
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .getDayPlan(year,month, SPUtil.getDepId(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayOfWeekBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DayOfWeekBean>> t) throws Exception {
                        lineList = t.getResults();
                        for (int i = 0; i < lineList.size(); i++) {
                            DayOfWeekBean bean = lineList.get(i);
                                String line_name = bean.getLine_name();
                                if (lineNameList.indexOf(line_name)==-1){
                                    lineNameList.add(line_name);
                                }
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
    //获取每个班组负责的线路
    public void getWeekPlan() {
        ProgressDialog.show(this,false,"正在加载。。。。");
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .getDayPlan(year,month, SPUtil.getDepId(this),type_id,null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayOfWeekBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DayOfWeekBean>> t) throws Exception {
                        results = t.getResults();
                        adapter.setData(results);
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
                .getLineType()
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
    public void saveDay() {

        if (selectType.size()==0){
            Toast.makeText(this,"请添加计划",Toast.LENGTH_SHORT).show();
            return;
        }
        BaseRequest.getInstance().getService()
                .addDayPlan(selectType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        if (t.getCode()==1){
                            setResult(RESULT_OK);
                            Toast.makeText(AddDayPlanActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
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

    public void inteDate() {
        month = getIntent().getIntExtra("month",6)+"";
        year = getIntent().getIntExtra("year",2019)+"";
        day = getIntent().getIntExtra("day",1)+"";
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
                    if (selectBean.size()==0){
                        Tower tower=new Tower();
                        tower.setTower_type("2");
                        tower.setName(listBean.getName());
                        tower.setTower_id(listBean.getId());
                        selectBean.add(tower);
                        listBean.setCheck(true);
                        holder.itemTroubleCheck.setChecked(true);
                    }else {
                        int isExit=0;
                        for (int i = 0; i < selectBean.size(); i++) {
                            Tower dayOfWeekBean = selectBean.get(i);
                            if (dayOfWeekBean.getTower_id().equals(listBean.getId())){
                                isExit=1;
                                selectBean.remove(i);
                                listBean.setCheck(false);
                                holder.itemTroubleCheck.setChecked(false);
                                return;
                            }
                        }
                        if (isExit==0){
                            Tower tower=new Tower();
                            tower.setTower_type("2");
                            tower.setTower_id(listBean.getId());
                            tower.setName(listBean.getName());
                            selectBean.add(tower);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 24 && resultCode == RESULT_OK) {
            if (data != null) {
                selectType.clear();
                lineCheckBean = (LineCheckBean) data.getSerializableExtra("bean");
                line_id = lineCheckBean.getId();
                monthPlanLine.setText(lineCheckBean.getName());
                getTempTower();
            }
        }
    }
    //获取工作类型
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
                        TempTowerAdapter adapter=new TempTowerAdapter(AddDayPlanActivity.this,t.getResults());
                        monthPlanTypeLv.setAdapter(adapter);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    //保存日临时计划
    public void saveDayPlan() {
        SavaLineBean bean = new SavaLineBean();
        bean.setLine_id(lineCheckBean.getId());
        bean.setLine_name(lineCheckBean.getName());
        bean.setType_id(type_id);
        bean.setType_sign(sign);
        bean.setType_name(typeName);
        bean.setStart_time(year+"-"+month+"-"+day);
        bean.setEnd_time(year+"-"+month+"-"+day);
        bean.setYear(year);
        bean.setDay(day);
        bean.setMonth(month);
        bean.setTowers(selectBean);
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .saveDayPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            Toast.makeText(AddDayPlanActivity.this, "制定成功", Toast.LENGTH_SHORT).show();
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
