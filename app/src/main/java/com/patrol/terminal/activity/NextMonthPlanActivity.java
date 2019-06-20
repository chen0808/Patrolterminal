package com.patrol.terminal.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.NextMonthPlanAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DepBean;
import com.patrol.terminal.bean.MonthListBean;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.SubmitPlanReqBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.PopListDialog;
import com.patrol.terminal.widget.ProgressDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NextMonthPlanActivity extends BaseActivity {


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
    @BindView(R.id.next_plan_rv)
    RecyclerView nextPlanRv;
    @BindView(R.id.plan_total_title)
    TextView planTotalTitle;
    @BindView(R.id.month_line_total)
    TextView monthLineTotal;
    @BindView(R.id.month_line_kilo_total)
    TextView monthLineKiloTotal;
    @BindView(R.id.month_line_110kv_num)
    TextView monthLine110kvNum;
    @BindView(R.id.month_line_110kv_kilo)
    TextView monthLine110kvKilo;
    @BindView(R.id.month_line_35kv_num)
    TextView monthLine35kvNum;
    @BindView(R.id.month_line_35kv_kilo)
    TextView monthLine35kvKilo;
    @BindView(R.id.task_screen)
    ImageView taskScreen;
    @BindView(R.id.next_plan_done)
    TextView nextPlanDone;
    private String state;
    private String state2;
    private NextMonthPlanAdapter monthPlanAdapter;
    private List<MonthPlanBean> list = new ArrayList<>();
    private String mJobType;
    private List<Tower> lineList = new ArrayList<>();
    private int year;
    private int month;
    private int num_total;
    private double kilo_total;
    private int num_110kv;
    private double kilo_110kv;
    private int num_35kv;
    private double kilo_35kv;
    private String depId;
    private List<MonthPlanBean> data = new ArrayList<>();
    private List<MonthPlanBean> data1 = new ArrayList<>();
    private List<MonthPlanBean> data2 = new ArrayList<>();
    private PopListDialog popWinShare;
    private Disposable subscribe;
    private List<DepBean> depList;
    private String time;
    private int nextMonth;
    private int nextYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_month_plan);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
//        list = (List<MonthPlanBean>) getIntent().getSerializableExtra("list");
//        lineList = (List<Tower>) getIntent().getSerializableExtra("linelist");
//
//        num_total = getIntent().getIntExtra("num_total", 0);
//        kilo_total = getIntent().getDoubleExtra("kilo_total", 0);
//        num_110kv = getIntent().getIntExtra("110kv_num", 0);
//        kilo_110kv = getIntent().getDoubleExtra("110kv_kolo", 0);
//        num_35kv = getIntent().getIntExtra("35kv_num", 0);
//        kilo_35kv = getIntent().getDoubleExtra("35kv_kolo", 0);


//        year = getIntent().getIntExtra("year", 2019);
//        month = getIntent().getIntExtra("month", 6);
        depId = SPUtil.getDepId(this);
        state = getIntent().getStringExtra("audit_status");
        mJobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        //判断
        if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && ("0".equals(state) || "4".equals(state))) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("提交");
        } else if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && "1".equals(state)) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("撤回");
        } else if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
            state2 = "1,2,3";
            depId = null;
            taskScreen.setVisibility(View.VISIBLE);
            if ("1".equals(state)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审核");
            }
        } else if (mJobType.contains(Constant.RUN_SUPERVISOR)) {
            state2 = "1,2,3";
            depId = null;
            taskScreen.setVisibility(View.VISIBLE);
            if ("2".equals(state)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审核");
            }
        }
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.startsWith("getDep")) {
                    depId = type.split("@")[1];
                    getMonthPlanList();
                }
            }
        });
        getNextMonthPlanList();
        getDepList();
    }

    //获取下月计划列表
    public void getNextMonthPlanList() {
        time = DateUatil.getCurMonth();
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]);
        year = Integer.parseInt(years[0]);
        if (Integer.parseInt(months[0]) < 12) {
            nextMonth = Integer.parseInt(months[0]) + 1;
            nextYear = Integer.parseInt(years[0]);
        } else {
            nextMonth = 1;
            nextYear = Integer.parseInt(years[0]) + 1;
        }
        String from = getIntent().getStringExtra("from");
        if ("todoMonth".equals(from)){
            year=getIntent().getIntExtra("year",2019);
            month=getIntent().getIntExtra("month",6);
        }
        ProgressDialog.show(this,false,"正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getMonthPlan(nextYear, nextMonth, depId, state2, "create_time desc,type_sign,line_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MonthListBean>(this) {

                    @Override
                    protected void onSuccees(BaseResult<MonthListBean> t) throws Exception {

                        if (t.getCode() == 1) {
//                            next_num_total = 0;
//                            next_num_110kv = 0;
//                            next_num_35kv = 0;
//                            next_kilo_total = 0;
//                            next_kilo_110kv = 0;
//                            next_kilo_35kv = 0;
//                            done_num_total=0;
//                            all_num_total=0;
                            MonthListBean results = t.getResults();
                            lineList = getData(results, 2);
                            list = results.getPatrol();
                            if (list != null && list.size() > 0) {
                                MonthPlanBean monthPlanBean = list.get(0);

                                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                                monthLineTotal.setText("工作线路总数 : " + num_total + "条");
                                monthLineKiloTotal.setText("总公里数 : " + decimalFormat.format(kilo_total) + "公里");
                                monthLine110kvNum.setText("110kv线路总数 : " + num_110kv + "条");
                                monthLine110kvKilo.setText("公里数 : " + decimalFormat.format(kilo_110kv) + "公里");
                                monthLine35kvNum.setText("35kv线路总数 : " + num_35kv + "条");
                                monthLine35kvKilo.setText("公里数 : " + decimalFormat.format(kilo_35kv) + "公里");
                                titleName.setText(nextYear + "年" + nextMonth + "月计划列表");

                                state = monthPlanBean.getAudit_status();

                                LinearLayoutManager manager = new LinearLayoutManager(NextMonthPlanActivity.this);
                                nextPlanRv.setLayoutManager(manager);
                                monthPlanAdapter = new NextMonthPlanAdapter(R.layout.fragment_plan_item, list, mJobType);
                                nextPlanRv.setAdapter(monthPlanAdapter);
                                adapterClick();
//                                if ("3".equals(monthPlanBean.getAudit_status())) {
//                                    addPlanStatus.setBackgroundResource(R.drawable.state_green_bg);
//                                    addPlanStatus.setText(getResources().getColor(R.color.green));
//                                }
//                                if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && "0".equals(monthPlanBean.getAudit_status())) {
//                                    addPlanRight.setVisibility(View.VISIBLE);
//                                } else if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED) && "1".equals(monthPlanBean.getAudit_status())) {
//                                    addPlanRight.setVisibility(View.GONE);
//                                } else if (mJobType.contains(Constant.RUN_SUPERVISOR) && "2".equals(monthPlanBean.getAudit_status())) {
//                                    addPlanRight.setVisibility(View.GONE);
//                                } else {
//                                    addPlanRight.setVisibility(View.GONE);
//                                }
//                            } else {
//                                addPlanIv.setVisibility(View.VISIBLE);
//                                addPlanLl.setVisibility(View.GONE);
//                            }
//                            if (nextLineList.size() != 0) {
//                                planSubmitNext.setVisibility(View.VISIBLE);
//                            } else {
//                                planSubmitNext.setVisibility(View.GONE);
                            }
                        }
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    public void adapterClick() {
        monthPlanAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MonthPlanBean item = (MonthPlanBean) adapter.getItem(position);
                //判断是否是保电计划
                if (item.getRepair_content() != null) {
                    switch (view.getId()) {
                        case R.id.iv_edit:
                            Intent intent = new Intent(NextMonthPlanActivity.this, LineCheckActivity.class);
                            intent.putExtra("from", Constant.FROM_MONTHPLAN_TO_ADDMONTH);
                            intent.putExtra("id", item.getId());
                            intent.putExtra("year", item.getYear());
                            intent.putExtra("month", item.getMonth());
                            startActivityForResult(intent, 10);
                            break;
                    }
                } else {
                    switch (view.getId()) {
                        case R.id.iv_edit:
                            Intent intent = new Intent(NextMonthPlanActivity.this, AddMonthPlanActivity.class);
                            intent.putExtra("from", Constant.FROM_MONTHPLAN_TO_ADDMONTH);
                            intent.putExtra("line_name", item.getLine_name());
                            intent.putExtra("year", item.getYear() + "");
                            intent.putExtra("month", item.getMonth() + "");
                            intent.putExtra("line_id", item.getLine_id() + "");
                            intent.putExtra("id", item.getId());
                            intent.putExtra("type", item.getType_sign());
                            startActivity(intent);
                            break;
                    }
                }
            }
        });
        monthPlanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                MonthPlanBean bean = (MonthPlanBean) adapter.getItem(position);
                Intent intent = new Intent();

                if (bean.getRepair_content() != null) {
                    intent.setClass(NextMonthPlanActivity.this, SpecialPlanDetailActivity.class);
                    intent.putExtra("from", "month");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", bean);
                    intent.putExtras(bundle);
                } else {
                    intent.setClass(NextMonthPlanActivity.this, MonthPlanDetailActivity.class);
                    intent.putExtra("year", bean.getYear());
                    intent.putExtra("month", bean.getMonth());
                    intent.putExtra("month_id", bean.getMonth_id());
                    intent.putExtra("month_line_id", bean.getId());
                    intent.putExtra("id", bean.getLine_id());
                }
                startActivity(intent);
            }

        });
    }

    @OnClick({R.id.title_back, R.id.title_setting, R.id.task_screen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                submit(lineList);
                break;
            case R.id.task_screen:
                if (depList == null || depList.size() == 0) {
                    Toast.makeText(this, "暂未获取班组信息,请稍后再试", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (popWinShare == null) {
                    //自定义的单击事件
                    popWinShare = new PopListDialog(this, dip2px(this, 120), dip2px(this, 41) * depList.size()+dip2px(this, 15), depList);
                    //监听窗口的焦点事件，点击窗口外面则取消显示
                    popWinShare.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                popWinShare.dismiss();
                            }
                        }
                    });
                }
                //设置默认获取焦点
                popWinShare.setFocusable(true);
                //以某个控件的x和y的偏移量位置开始显示窗口
                popWinShare.showAsDropDown(taskScreen, -225, 0);
                //如果窗口存在，则更新
                popWinShare.update();
                break;
        }
    }

    //提交月计划审核
    public void submit(List<Tower> lineList) {
        if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
            CancelOrOkDialog dialog = new CancelOrOkDialog(this, "审核", "不同意", "同意") {
                @Override
                public void ok() {
                    super.ok();
                    submitMonthPlan(lineList, "2");   //同意
                    dismiss();
                }

                @Override
                public void cancel() {
                    super.cancel();
                    submitMonthPlan(lineList, "4");  //不同意
                    dismiss();
                }
            };
            dialog.show();
        } else if (mJobType.contains((Constant.RUNNING_SQUAD_LEADER))) {
            if ("0".equals(state) || "4".equals(state)) {
                CancelOrOkDialog dialog = new CancelOrOkDialog(this, "是否提交审核", "取消", "确定") {
                    @Override
                    public void ok() {
                        super.ok();
                        submitMonthPlan(lineList, "1");   //提交
                        dismiss();
                    }

                    @Override
                    public void cancel() {
                        super.cancel();
                        dismiss();
                    }
                };
                dialog.show();
            } else if ("1".equals(state)) {
                CancelOrOkDialog dialog = new CancelOrOkDialog(this, "是否撤回计划", "取消", "确定") {
                    @Override
                    public void ok() {
                        super.ok();
                        submitMonthPlan(lineList, "0");   //撤回
                        dismiss();
                    }

                    @Override
                    public void cancel() {
                        super.cancel();
                        dismiss();
                    }
                };
                dialog.show();
            }
        } else if (mJobType.contains(Constant.RUN_SUPERVISOR)) {
            CancelOrOkDialog dialog = new CancelOrOkDialog(this, "审核", "不同意", "同意") {
                @Override
                public void ok() {
                    super.ok();
                    submitMonthPlan(lineList, "3");   //同意
                    dismiss();
                }

                @Override
                public void cancel() {
                    super.cancel();
                    submitMonthPlan(lineList, "4");  //不同意
                    dismiss();
                }
            };
            dialog.show();
        }
    }

    //提交月计划审核
    public void submitMonthPlan(List<Tower> list, String status) {
        ProgressDialog.show(NextMonthPlanActivity.this, false, "正在加载中...");
        SubmitPlanReqBean bean = new SubmitPlanReqBean();
        bean.setYear(nextYear + "");
        bean.setMonth(nextMonth + "");
        bean.setAudit_status(status);
        bean.setFrom_user_id(SPUtil.getUserId(NextMonthPlanActivity.this));
        bean.setLines(list);
        BaseRequest.getInstance().getService()
                .submitMonthPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(NextMonthPlanActivity.this) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            if ("1".equals(status)) {
                                Toast.makeText(NextMonthPlanActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                            } else if ("0".equals(status)) {
                                Toast.makeText(NextMonthPlanActivity.this, "撤回成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(NextMonthPlanActivity.this, "审核成功", Toast.LENGTH_SHORT).show();
                            }
                            RxRefreshEvent.publish("refreshTodo");
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(NextMonthPlanActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    //获取所有班组
    public void getDepList() {
        BaseRequest.getInstance().getService()
                .getDepList("SYS_DEP", "ID,name", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DepBean>>(NextMonthPlanActivity.this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DepBean>> t) throws Exception {
                        depList = t.getResults();

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

    }

    //获取月计划列表
    public void getMonthPlanList() {
        data.clear();
        data1.clear();
        data2.clear();
        ProgressDialog.show(this, false, "正在加载中");
        BaseRequest.getInstance().getService()
                .getMonthPlan(year, month, depId, state, "create_time desc,type_sign,line_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MonthListBean>(this) {

                    @Override
                    protected void onSuccees(BaseResult<MonthListBean> t) throws Exception {

                        if (t.getCode() == 1) {
                            num_total = 0;
                            num_110kv = 0;
                            num_35kv = 0;
                            kilo_total = 0;
                            kilo_110kv = 0;
                            kilo_35kv = 0;
                            MonthListBean results = t.getResults();
                            lineList = getData(results, 1);
                            monthPlanAdapter.setNewData(data);
                            DecimalFormat decimalFormat = new DecimalFormat("0.00");
                            monthLineTotal.setText("工作线路总数 : " + num_total + "条");
                            monthLineKiloTotal.setText("总公里数 : " + decimalFormat.format(kilo_total) + "公里");
                            monthLine110kvNum.setText("110kv线路总数 : " + num_110kv + "条");
                            monthLine110kvKilo.setText("公里数 : " + decimalFormat.format(kilo_110kv) + "公里");
                            monthLine35kvNum.setText("35kv线路总数 : " + num_35kv + "条");
                            monthLine35kvKilo.setText("公里数 : " + decimalFormat.format(kilo_35kv) + "公里");

                            if (lineList.size() != 0) {
                                titleSetting.setVisibility(View.VISIBLE);
                            } else {
                                titleSetting.setVisibility(View.GONE);
                            }
                        }
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    private List<Tower> getData(MonthListBean results, int type) {
        List<Tower> lineList = new ArrayList<>();
        List<MonthPlanBean> patrol = results.getPatrol();
        List<MonthPlanBean> ele = results.getEle();
        List<MonthPlanBean> repair = results.getRepair();
        if (patrol != null) {
            for (int j = 0; j < patrol.size(); j++) {
                MonthPlanBean monthPlanBean = patrol.get(j);
                num_total++;
                kilo_total += monthPlanBean.getLine_length();
                if (monthPlanBean.getVoltage_level().contains("110")) {
                    kilo_110kv = kilo_110kv + monthPlanBean.getLine_length();
                    num_110kv++;
                } else if (monthPlanBean.getVoltage_level().contains("35")) {
                    kilo_35kv = kilo_35kv + monthPlanBean.getLine_length();
                    num_35kv++;
                }
                //当身份是专责时，获取需要审批的列表
                if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED) && "1".equals(monthPlanBean.getAudit_status())) {
                    Tower lineBean = new Tower();
                    lineBean.setLine_id(monthPlanBean.getLine_id());
                    lineBean.setMonth_line_id(monthPlanBean.getId());
                    lineList.add(lineBean);
                    //当身份是主管时，获取需要审批的列表
                } else if (mJobType.contains(Constant.RUN_SUPERVISOR) && "2".equals(monthPlanBean.getAudit_status())) {
                    Tower lineBean = new Tower();
                    lineBean.setLine_id(monthPlanBean.getLine_id());
                    lineBean.setMonth_line_id(monthPlanBean.getId());
                    lineList.add(lineBean);
                    //当身份是班长时，获取需要审核的列表
                } else if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && ("0".equals(monthPlanBean.getAudit_status()) || "1".equals(monthPlanBean.getAudit_status()) || "4".equals(monthPlanBean.getAudit_status()))) {
                    Tower lineBean = new Tower();
                    lineBean.setLine_id(monthPlanBean.getLine_id());
                    lineBean.setMonth_line_id(monthPlanBean.getId());
                    lineList.add(lineBean);
                }
            }
        }
        if (type == 1) {
            if (patrol != null) {
                data1 = patrol;
                data.addAll(patrol);
            }
            if (repair != null) {
                data.addAll(repair);
                data2.addAll(repair);
            }
            if (ele != null) {
                data.addAll(ele);
                data2.addAll(ele);
            }
        }
        return lineList;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
