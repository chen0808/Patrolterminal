package com.patrol.terminal.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddMonthPlanActivity;
import com.patrol.terminal.activity.LineCheckActivity;
import com.patrol.terminal.activity.MonthPlanDetailActivity;
import com.patrol.terminal.activity.NextMonthPlanActivity;
import com.patrol.terminal.activity.SpecialPlanDetailActivity;
import com.patrol.terminal.activity.TemporaryActivity;
import com.patrol.terminal.adapter.MonthPlanAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.MonthListBean;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.SubmitPlanReqBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.PopMenmuDialog;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*月计划*/
public class MonthPlanFrgment extends BaseFragment {


    @BindView(R.id.add_next_plan)
    TextView addNextPlan;
    @BindView(R.id.plan_submit)
    ImageView planSubmit;
    @BindView(R.id.plan_submit_next)
    ImageView planSubmitNext;
    @BindView(R.id.add_plan_right)
    ImageView addPlanRight;
    @BindView(R.id.add_plan_name)
    TextView addPlanName;
    @BindView(R.id.add_plan_status)
    TextView addPlanStatus;
    @BindView(R.id.add_plan_ll)
    RelativeLayout addPlanLl;
    @BindView(R.id.add_plan_iv)
    ImageView addPlanIv;
    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.task_screen)
    ImageView taskScreen;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.plan_refresh)
    SwipeRefreshLayout planRefresh;
    @BindView(R.id.plan_point)
    View planPoint;
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
    @BindView(R.id.done_plan_range)
    TextView donePlanRange;
    private MonthPlanAdapter monthPlanAdapter;
    private TimePickerView pvTime;
    private List<Tower> nextLineList = new ArrayList<>();
    private List<Tower> lineList = new ArrayList<>();
    private List<MonthPlanBean> data = new ArrayList<>();
    private List<MonthPlanBean> data1 = new ArrayList<>();
    private List<MonthPlanBean> data2 = new ArrayList<>();
    private String time;
    private String typeId;
    private String month, year;
    private Context mContext;
    private String state;
    private String nextState;
    private String mJobType;
    private String depId;
    private PopMenmuDialog popWinShare;
    private int nextMonth;
    private int nextYear;
    private List<MonthPlanBean> nextPatrolList;
    private int num_total = 0;
    private int num_110kv = 0;
    private int num_35kv = 0;
    private double kilo_total = 0;
    private double kilo_110kv = 0;
    private double kilo_35kv = 0;


    private int done_num_total = 0;
    private int all_num_total = 0;
    private MonthPlanBean monthPlanBean;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        mContext = getActivity();
        taskScreen.setVisibility(View.VISIBLE);
        depId = SPUtil.getDepId(getContext());
        mJobType = SPUtil.getString(mContext, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
//        state = "3";//当月只能查询已审核的月计划
        nextState = "0,1,2,3,4";//下个月查出所有的月计划
        //判断
        if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
            depId = null;
        } else if (mJobType.contains(Constant.RUN_SUPERVISOR)) {
            depId = null;
            planSubmit.setVisibility(View.VISIBLE);
        }
        time = DateUatil.getCurMonth();
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        if (Integer.parseInt(months[0]) < 12) {
            nextMonth = Integer.parseInt(months[0]) + 1;
            nextYear = Integer.parseInt(years[0]);
        } else {
            nextMonth = 1;
            nextYear = Integer.parseInt(years[0]) + 1;
        }

        taskDate.setText(time);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        monthPlanAdapter = new MonthPlanAdapter(R.layout.fragment_plan_item, data, state, mJobType);
        planRv.setAdapter(monthPlanAdapter);
        //适配器的点击事件
        adapterClick();
        planRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMonthPlanList();
                getNextMonthPlanList();
            }
        });
        getMonthPlanList();
        getNextMonthPlanList();
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
                            Intent intent = new Intent(mContext, LineCheckActivity.class);
                            intent.putExtra("id", item.getId());
                            intent.putExtra("year", item.getYear());
                            intent.putExtra("month", item.getMonth());
                            startActivityForResult(intent, 10);
                            break;
                    }
                } else {
                    switch (view.getId()) {
                        case R.id.iv_edit:
                            Intent intent = new Intent(mContext, AddMonthPlanActivity.class);
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
                    intent.setClass(getContext(), SpecialPlanDetailActivity.class);
                    intent.putExtra("from", "month");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", bean);
                    intent.putExtras(bundle);
                } else {
                    intent.setClass(getContext(), MonthPlanDetailActivity.class);
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

    //获取下月计划列表
    public void getNextMonthPlanList() {
        BaseRequest.getInstance().getService()
                .getMonthPlan(nextYear, nextMonth, depId, nextState, "create_time desc,type_sign,line_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MonthListBean>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<MonthListBean> t) throws Exception {

                        if (t.getCode() == 1) {
                            done_num_total = 0;
                            all_num_total = 0;
                            MonthListBean results = t.getResults();
                            nextLineList = getData(results, 2);
                            nextPatrolList = results.getPatrol();
                            if (nextPatrolList != null && nextPatrolList.size() > 0) {
                                addPlanIv.setVisibility(View.GONE);
                                addPlanLl.setVisibility(View.VISIBLE);
                                addPlanName.setText(nextYear + "年" + nextMonth + "月工作计划");
                                monthPlanBean = nextPatrolList.get(0);
                                addPlanStatus.setVisibility(View.VISIBLE);
                                addPlanStatus.setText(StringUtil.getYXBstate(monthPlanBean.getAudit_status()));
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
                            } else {
                                addPlanIv.setVisibility(View.VISIBLE);
                                addPlanLl.setVisibility(View.GONE);
                            }
                            if (nextLineList.size() != 0) {
//                                planSubmitNext.setVisibility(View.VISIBLE);
                            } else {
                                planSubmitNext.setVisibility(View.GONE);
                            }
                        }
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
        ProgressDialog.show(getContext(), false, "正在加载中");
        BaseRequest.getInstance().getService()
                .getMonthPlan(Integer.parseInt(year), Integer.parseInt(month), depId, state, "create_time desc,type_sign,line_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MonthListBean>(getContext()) {

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
                            monthLineTotal.setText("工作线路 : " + num_total + "条");
                            monthLineKiloTotal.setText(decimalFormat.format(kilo_total) + " km");
                            monthLine110kvNum.setText("110kv线路 : " + num_110kv + "条");
                            monthLine110kvKilo.setText( decimalFormat.format(kilo_110kv) + " km");
                            monthLine35kvNum.setText("35kv线路 : " + num_35kv + "条");
                            monthLine35kvKilo.setText( decimalFormat.format(kilo_35kv) + " km");
                            BigDecimal b1 = new BigDecimal(done_num_total);
                            BigDecimal b2 = new BigDecimal(all_num_total);
                            if (all_num_total == 0) {
                                donePlanRange.setText("计划进度 : 0%");
                            } else {
                                //默认保留两位会有错误，这里设置保留小数点后4位
                                double range = b1.divide(b2, 0, BigDecimal.ROUND_HALF_UP).doubleValue();
                                donePlanRange.setText("计划进度 : " + range + "%");
                            }
                        }
                        if (lineList.size() == 0) {
                            planSubmit.setVisibility(View.GONE);
                        } else {
                            planSubmit.setVisibility(View.VISIBLE);
                        }
                        ProgressDialog.cancle();
                        planRefresh.setRefreshing(false);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        planRefresh.setRefreshing(false);
                    }
                });

    }

    //提交月计划审核
    public void submitMonthPlan(List<Tower> list, String status, int type) {
        ProgressDialog.show(getContext(), false, "正在加载中...");
        SubmitPlanReqBean bean = new SubmitPlanReqBean();
        bean.setYear(year);
        bean.setMonth(month);
        bean.setAudit_status(status);
        bean.setFrom_user_id(SPUtil.getUserId(getContext()));
        bean.setLines(list);
        BaseRequest.getInstance().getService()
                .submitMonthPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            if ("1".equals(status)) {
                                Toast.makeText(getContext(), "提交成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "审核成功", Toast.LENGTH_SHORT).show();
                            }
                            RxRefreshEvent.publish("refreshTodo");
                            if (type == 1) {
                                getMonthPlanList();
                            } else {
                                getNextMonthPlanList();
                            }


                        } else {
                            Toast.makeText(getContext(), t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    public void showMonth() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                time = DateUatil.getTime(date);
                taskDate.setText(time);
                String[] years = time.split("年");
                String[] months = years[1].split("月");
                month = Integer.parseInt(months[0]) + "";
                year = years[0];
                planTotalTitle.setText(month+"月计划工作汇总");
                taskTitle.setText(month+"月计划列表");
                getMonthPlanList();
                RxRefreshEvent.publish("month@" + time);
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentTextSize(18)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }


    @OnClick({R.id.task_date, R.id.add_plan_right, R.id.add_plan_iv, R.id.plan_submit, R.id.task_screen, R.id.add_plan_ll, R.id.plan_submit_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showMonth();
                break;
            case R.id.add_plan_ll:
                Intent intent1 = new Intent(getContext(), NextMonthPlanActivity.class);
                intent1.putExtra("list", (Serializable) nextPatrolList);
                intent1.putExtra("linelist", (Serializable) nextLineList);
                intent1.putExtra("year", nextYear);
                intent1.putExtra("month", nextMonth);
                intent1.putExtra("audit_status", monthPlanBean.getAudit_status());
                startActivityForResult(intent1, 10);
                break;
            case R.id.add_plan_iv:
            case R.id.add_plan_right:
                Intent intent = new Intent(getContext(), TemporaryActivity.class);
                intent.putExtra("year", String.valueOf(nextYear));
                intent.putExtra("month", String.valueOf(nextMonth));
                startActivityForResult(intent, 10);
                break;
            case R.id.plan_submit_next:
                submit(nextLineList, 2);
                break;
            case R.id.plan_submit:
                submit(lineList, 1);
                break;
            case R.id.task_screen:
                if (popWinShare == null) {
                    //自定义的单击事件
                    OnClickLintener paramOnClickListener = new OnClickLintener();
                    popWinShare = new PopMenmuDialog(getActivity(), paramOnClickListener, Utils.dip2px(getContext(), 100), Utils.dip2px(getContext(), 100));
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
                if (popWinShare.isShowing()) {
                    popWinShare.dismiss();
                } else {
                    popWinShare.showAsDropDown(taskScreen, -100, 0);
//popupWindow.showAtLocation(imageView, Gravity.BOTTOM, 100, 100);
                }
                //以某个控件的x和y的偏移量位置开始显示窗口
//                popWinShare.showAsDropDown(taskScreen, 200, 0);
                //如果窗口存在，则更新
                popWinShare.update();
                break;

        }
    }

    //提交月计划审核
    public void submit(List<Tower> list, int type) {
        if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
            CancelOrOkDialog dialog = new CancelOrOkDialog(mContext, "审核", "不同意", "同意") {
                @Override
                public void ok() {
                    super.ok();
                    submitMonthPlan(list, "2", type);   //同意
                    dismiss();
                }

                @Override
                public void cancel() {
                    super.cancel();
                    submitMonthPlan(list, "4", type);  //不同意
                    dismiss();
                }
            };
            dialog.show();

        } else if (mJobType.contains((Constant.RUNNING_SQUAD_LEADER))) {
            submitMonthPlan(list, "1", type);
        } else if (mJobType.contains(Constant.RUN_SUPERVISOR)) {
            CancelOrOkDialog dialog = new CancelOrOkDialog(mContext, "审核", "不同意", "同意") {
                @Override
                public void ok() {
                    super.ok();
                    submitMonthPlan(list, "3", type);   //同意
                    dismiss();
                }

                @Override
                public void cancel() {
                    super.cancel();
                    submitMonthPlan(list, "4", type);  //不同意
                    dismiss();
                }
            };
            dialog.show();
        }
    }

    //筛选框点击事件
    class OnClickLintener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.all:
                    monthPlanAdapter.setNewData(data);
                    break;
                case R.id.popmenmu1:
                    monthPlanAdapter.setNewData(data1);
                    break;
                case R.id.popmenmu2:
                    monthPlanAdapter.setNewData(data2);
                    break;
                default:
                    break;
            }
            popWinShare.dismiss();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            getMonthPlanList();
            getNextMonthPlanList();
        }
    }

    //处理数据
    private List<Tower> getData(MonthListBean results, int type) {
        List<Tower> lineList = new ArrayList<>();
        List<MonthPlanBean> patrol = results.getPatrol();
        List<MonthPlanBean> repair = results.getRepair();
        if (patrol != null) {
            for (int j = 0; j < patrol.size(); j++) {
                MonthPlanBean monthPlanBean = patrol.get(j);
                //汇总
                if (type == 1) {
                    num_total++;
                    kilo_total += monthPlanBean.getLine_length();
                    done_num_total = done_num_total + monthPlanBean.getDone_num();
                    all_num_total = all_num_total + monthPlanBean.getAll_num();
                    if (monthPlanBean.getVoltage_level().contains("110")) {
                        kilo_110kv = kilo_110kv + monthPlanBean.getLine_length();
                        num_110kv++;
                    } else if (monthPlanBean.getVoltage_level().contains("35")) {
                        kilo_35kv = kilo_35kv + monthPlanBean.getLine_length();
                        num_35kv++;
                    }
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
                } else if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && ("0".equals(monthPlanBean.getAudit_status()) || "4".equals(monthPlanBean.getAudit_status()))) {
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
            //只有保电专责可以看保电计划
            if (repair != null&&mJobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)) {
                data.addAll(repair);
                data2.addAll(repair);
            }

        }
        return lineList;
    }


}
