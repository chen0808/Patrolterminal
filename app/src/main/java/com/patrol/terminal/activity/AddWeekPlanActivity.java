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
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.bean.WeekOfMonthBean;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.TimeUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

    private List<String> lineName = new ArrayList<>();

    private int year;
    private String month;
    private int week;
    private int type = 0;
    private List<Tower> selectType = new ArrayList<>();
    private AddTowerAdapter adapter;
    private List<WeekOfMonthBean> results = new ArrayList<>();
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
    private List<String> years = new ArrayList<>();
    private List<List<String>> weeks = new ArrayList<>();
    private String[] types;
    private int selectNum=0;
    private int allNum=0;

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
        titleName.setClickable(false);
        String from = getIntent().getStringExtra("from");
        if ("WeekPlanFrgment".equals(from)) {
            titleName.setClickable(true);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //初始化日期控件的数据
                initdata();
            }
        }).start();
        initdate();
        titleName.setText("第" + week + "周计划制定(" + beginDate + "至" + endDate + ")");
        adapter = new AddTowerAdapter(this, results);
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
//                    bean.setType_id(split[5]);
//                    bean.setType_name(split[6]);
                    bean.setType_sign(split[7]);
                    bean.setMonth_line_id(split[8]);
                    bean.setDefect_id(split[9]);
                    bean.setStart_id(split[10]);
                    bean.setEnd_id(split[11]);
                    bean.setTower_type(split[12]);
                    bean.setMonth_tower_id(split[13]);
                    bean.setDep_id(SPUtil.getDepId(AddWeekPlanActivity.this));
                    bean.setDep_name(SPUtil.getDepName(AddWeekPlanActivity.this));
                    selectType.add(bean);
                    selectNum++;
                } else if (type.startsWith("delete")) {
                    String[] split = type.split("@");
                    for (int i = 0; i < selectType.size(); i++) {
                        String tower_id = selectType.get(i).getTowers_id();
                        String id = split[1];
                        if (id.equals(tower_id)) {
                            selectType.remove(i);
                            selectNum--;
                            break;
                        }
                    }

                }
                addWeekNumSelect.setText(selectNum+"");
            }
        });
    }

    //初始化周数据
    public void initdata() {
        for (int i = 2019; i < 2100; i++) {
            years.add(i + "年");
            int maxWeekNumOfYear = TimeUtil.getMaxWeekNumOfYear(i);
            List<String> options3Items_01 = new ArrayList<>();
            for (int j = 1; j < maxWeekNumOfYear + 1; j++) {
                String firstDayOfWeek = TimeUtil.getFirstDayOfWeek(i, j);
                String lastDayOfWeek = TimeUtil.getLastDayOfWeek(i, j);
                options3Items_01.add("第" + (j) + "周(" + firstDayOfWeek + "至" + lastDayOfWeek + ")");

            }
            weeks.add(options3Items_01);
        }

    }

    //初始化日期
    public void initdate() {
        year = getIntent().getIntExtra("year", 0);
        week = getIntent().getIntExtra("week", 0);
        beginDate = TimeUtil.getFirstDayOfWeek(year, week);
        endDate = TimeUtil.getLastDayOfWeek(year, week);
        //获取下周起始和终止日期
        String beginDate = TimeUtil.getFirstDayOfWeek(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7));
        String end2Date = TimeUtil.getLastDayOfWeek(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7));
        String[] start = beginDate.split("-");
        String startMonth = start[0];
        String[] end = end2Date.split("-");
        String endMonth = end[0];
        if (startMonth.equals(endMonth)) {
            month = Integer.valueOf(startMonth) + "";
        } else {
            month = Integer.valueOf(startMonth) + "," + Integer.valueOf(endMonth);
        }
    }

    private void getWeekInfoList() {
        ProgressDialog.show(this, false, "正在加载。。。");
        //获取周计划杆段列表
        BaseRequest.getInstance().getService()
                .getWeekListWeek(year, month, SPUtil.getDepId(this), sign, line_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<WeekOfMonthBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<WeekOfMonthBean>> t) throws Exception {
                        selectType.clear();
                        selectNum=0;
                        addWeekNumSelect.setText(selectNum+"");
                        ProgressDialog.cancle();
                        results = t.getResults();
                        adapter.setData(results);
                        if (results == null || results.size() == 0) {
                            dayNoData.setVisibility(View.VISIBLE);
                            addWeekNumLl.setVisibility(View.GONE);
                        } else {
                            dayNoData.setVisibility(View.GONE);
                            addWeekNumLl.setVisibility(View.VISIBLE);
                            allNum=results.size();
                            addWeekNumAll.setText("/"+allNum+")");
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
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
                showWeek();
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
                monthPlanType.setText(types[options1]);
                if ("全部".equals(types[options1])) {
                    sign = null;
                    getWeekInfoList();
                } else {
                    for (int i = 0; i < typeList.size(); i++) {
                        LineTypeBean lineTypeBean = typeList.get(i);
                        if (types[options1].contains(lineTypeBean.getName())) {
                            sign = lineTypeBean.getSign();
                            getWeekInfoList();
                        }
                    }
                }

                dialog.dismiss();
            }
        });

        AlertDialog typeDialog = alertBuilder.create();
        typeDialog.show();
//        //条件选择器
//        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3, View v) {
//                type_name = typeSign.get(options1).get(option2);
//                monthPlanType.setText(type_name);
//                for (int i = 0; i < typeList.size(); i++) {
//                    LineTypeBean lineTypeBean = typeList.get(i);
//                    if (type_name.contains(lineTypeBean.getName())) {
//                        sign = lineTypeBean.getSign();
//                        getWeekInfoList();
//                    }
//                }
//            }
//        }).build();
//        pvOptions.setPicker(typeVal, typeSign);
//        pvOptions.setSelectOptions(0, 0);
//        pvOptions.show();
    }

    //获取每个班组负责的线路
    public void getLine() {
        lineName.clear();
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .getWeekList(year, Integer.valueOf(month), SPUtil.getDepId(this), null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<WeekOfMonthBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<WeekOfMonthBean>> t) throws Exception {
                        linelist = t.getResults();
                        for (int i = 0; i < linelist.size(); i++) {
                            WeekOfMonthBean bean = linelist.get(i);
                            String line_name = bean.getLine_name();
                            if (lineName.indexOf(line_name) == -1) {
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
    public void saveWeek() {

        PlanWeekReqBean bean = new PlanWeekReqBean();
        bean.setYear(year + "");
//        bean.setMonth(month + "");
        bean.setWeek(week + "");
        bean.setBegin_time(year + "-" + beginDate);
        bean.setEnd_time(year + "-" + endDate);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 24 && resultCode == -1) {
            if (data != null) {
                selectType.clear();
                lineCheckBean = (LineCheckBean) data.getSerializableExtra("bean");
                line_id = lineCheckBean.getId();
                getWeekInfoList();
                monthPlanLine.setText(lineCheckBean.getName());
                getTempTower();
            }
        }
    }

    //获取杆塔
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
                        TempTowerAdapter adapter = new TempTowerAdapter(AddWeekPlanActivity.this, t.getResults());
                        monthPlanTypeLv.setAdapter(adapter);

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

//    //保存周计划
//    public void saveWeekPlan() {
//        SavaLineBean bean = new SavaLineBean();
//        bean.setType_sign(sign);
//        bean.setType_name(type_name);
//        bean.setStart_time(beginDate);
//        bean.setEnd_time(endDate);
//        bean.setYear(year + "");
////        bean.setMonth(month + "");
//        bean.setWeek(week + "");
//        bean.setTowers(selectType);
//        //获取月计划列表
//        BaseRequest.getInstance().getService()
//                .saveWeekPlan(bean)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
//                        if (t.getCode() == 1) {
//                            Toast.makeText(AddWeekPlanActivity.this, "制定成功", Toast.LENGTH_SHORT).show();
//                            Intent intent=new Intent();
//                            intent.putExtra("year",year);
//                            intent.putExtra("month",month);
//                            intent.putExtra("week",week);
//                            setResult(RESULT_OK,intent);
//                            finish();
//                        }
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                    }
//                });
//    }

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
                    if (selectType.size() == 0) {
                        Tower tower = new Tower();
                        tower.setTower_type(listBean.getTower_type());
                        tower.setName(listBean.getName());
                        tower.setTower_id(listBean.getId());
                        tower.setStart_id(listBean.getStart_id());
                        tower.setEnd_id(listBean.getEnd_id());
                        listBean.setCheck(true);
                        selectType.add(tower);
                        holder.itemTroubleCheck.setImageResource(R.mipmap.check_yes);
                    } else {
                        int isExit = 0;
                        for (int i = 0; i < selectType.size(); i++) {
                            Tower dayOfWeekBean = selectType.get(i);
                            if (dayOfWeekBean.getTower_id().equals(listBean.getId())) {
                                isExit = 1;
                                selectType.remove(i);
                                holder.itemTroubleCheck.setImageResource(R.mipmap.check_no);
                                listBean.setCheck(false);
                                return;
                            }
                        }
                        if (isExit == 0) {
                            Tower tower = new Tower();
                            tower.setTower_type(listBean.getTower_type());
                            tower.setTower_id(listBean.getId());
                            tower.setName(listBean.getName());
                            tower.setStart_id(listBean.getStart_id());
                            tower.setEnd_id(listBean.getEnd_id());
                            selectType.add(tower);
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

    //展示周
    public void showWeek() {

        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                //返回的分别是三个级别的选中位置
                String time = years.get(options1) + weeks.get(options1).get(option2);
                year = Integer.parseInt(years.get(options1).split("年")[0]);
                String date = weeks.get(options1).get(option2);
                String[] split = date.split("周");
                week = Integer.parseInt(date.split("周")[0].split("第")[1]);
                titleName.setText(split[0] + "周计划制定" + split[1]);
                //获取下周起始和终止日期
                String beginDate = TimeUtil.getFirstDayOfWeek(year, week);
                String end2Date = TimeUtil.getLastDayOfWeek(year, week);
                String[] start = beginDate.split("-");
                String startMonth = start[0];
                String[] end = end2Date.split("-");
                String endMonth = end[0];
                if (startMonth.equals(endMonth)) {
                    month = Integer.valueOf(startMonth) + "";
                } else {
                    month = Integer.valueOf(startMonth) + "," + Integer.valueOf(endMonth);
                }
                getWeekInfoList();
            }
        }).build();
        pvOptions.setPicker(years, weeks);
        pvOptions.setSelectOptions(years.indexOf(year + "年"), week - 1);
        pvOptions.show();
    }
}
