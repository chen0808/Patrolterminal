package com.patrol.terminal.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddWeekPlanActivity;
import com.patrol.terminal.activity.NextMonthPlanActivity;
import com.patrol.terminal.activity.NextWeekPlanActivity;
import com.patrol.terminal.activity.SpecialPlanDetailActivity;
import com.patrol.terminal.activity.TemporaryActivity;
import com.patrol.terminal.activity.WeekPlanDetailActivity;
import com.patrol.terminal.adapter.WeekPlanAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.SubmitPlanReqBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.bean.WeekListBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.utils.TimeUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.PopMenmuDialog;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeekPlanFrgment extends BaseFragment {


    @BindView(R.id.add_next_plan)
    TextView addNextPlan;
    @BindView(R.id.plan_submit)
    ImageView planSubmit;
    @BindView(R.id.plan_submit_next)
    ImageView planSubmitNext;
    @BindView(R.id.add_plan_right)
    ImageView addPlanRight;
    @BindView(R.id.plan_point)
    View planPoint;
    @BindView(R.id.add_plan_name)
    TextView addPlanName;
    @BindView(R.id.add_plan_status)
    TextView addPlanStatus;
    @BindView(R.id.add_plan_ll)
    RelativeLayout addPlanLl;
    @BindView(R.id.add_plan_iv)
    ImageView addPlanIv;
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
    @BindView(R.id.done_plan_range)
    TextView donePlanRange;
    @BindView(R.id.ll_35kv)
    LinearLayout ll35kv;

    private String time;
    private WeekPlanAdapter weekPlanAdapter;
    private List<String> years = new ArrayList<>();
    private List<List<String>> weeks = new ArrayList<>();
    private String year;
    private String month;
    private int week;
    private String state;
    private List<Tower> nextlineList = new ArrayList<>();
    private List<Tower> lineList = new ArrayList<>();
    private String mJobType;
    private String depId;
    private List<WeekListBean> results = new ArrayList<>();
    private int nextYear;
    private int nextWeek;
    private List<WeekListBean> nextWeekList;

    private int num_total = 0;
    private double kilo_total = 0;
    private int next_num_total = 0;
    private double next_kilo_total = 0;
    private int done_num_total = 0; //计划完成数
    private int all_num_total = 0;//计划总数
    private WeekListBean weekListBean;
    private List<String> lineNum = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
       ll35kv.setVisibility(View.GONE);
        depId = SPUtil.getDepId(getContext());
        mJobType = SPUtil.getString(getActivity(), Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
            state = "1,2,3,4,5";
            depId = null;
        } else if (mJobType.contains(Constant.RUN_SUPERVISOR)) {
            state = "2,3,4,5";
            depId = null;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //初始化日期控件的数据
                initdata();
            }
        }).start();

        //初始化年月周数据
        initdate();
        taskTitle.setText("周计划列表");
        String beginDate = TimeUtil.getFirstDayOfWeek(new Date(System.currentTimeMillis()));
        String endDate = TimeUtil.getLastDayOfWeek(new Date(System.currentTimeMillis()));
        String nextBeginTime = TimeUtil.getFirstDayOfWeek(nextYear, nextWeek);
        String nextEndTime = TimeUtil.getLastDayOfWeek(nextYear, nextWeek);
        taskDate.setText(year + "年第" + week + "周(" + beginDate + "至" + endDate + ")");
        addNextPlan.setText("下周计划制定(" + nextBeginTime + "至" + nextEndTime + ")");
        planTotalTitle.setText("本周计划工作汇总");
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        weekPlanAdapter = new WeekPlanAdapter(R.layout.fragment_plan_item, results);
        planRv.setAdapter(weekPlanAdapter);
        weekPlanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getContext(), WeekPlanDetailActivity.class);
                if (results.get(position).getWeek_id() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("bean", results.get(position));
                    intent.putExtras(bundle);

                } else {
                    intent.setClass(getContext(), SpecialPlanDetailActivity.class);
                    intent.putExtra("from", "week");
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("bean", results.get(position));
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });
        planRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWeekList();
                getNextWeekList();
            }
        });
        getWeekList();
        getNextWeekList();
    }

    //初始化日期
    public void initdate() {
        time = DateUatil.getCurMonth();
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        week = TimeUtil.getCurrWeek();
        int maxWeekNumOfYear = TimeUtil.getMaxWeekNumOfYear(Integer.parseInt(year));

        if (week > maxWeekNumOfYear) {
            nextWeek = 1;
            nextYear = Integer.parseInt(year) + 1;
        } else {
            nextWeek = week + 1;
            nextYear = Integer.parseInt(year);
        }

    }



    //获取本周计划
    public void getWeekList() {
        BaseRequest.getInstance().getService()
                .getWeekList(year, week + "", depId, state, "create_time desc,type_sign,line_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<WeekListBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<WeekListBean>> t) throws Exception {
                        lineList.clear();
                        lineNum.clear();
                        num_total = 0;
                        kilo_total = 0;
                        done_num_total = 0;
                        all_num_total = 0;
                        results = t.getResults();
                        weekPlanAdapter.setNewData(results);
                        for (int i = 0; i < results.size(); i++) {
                            WeekListBean weekListBean = results.get(i);
                            num_total++;
                            kilo_total = kilo_total + weekListBean.getTowers_range();
                            done_num_total = done_num_total + weekListBean.getDone_num();
                            all_num_total = all_num_total + weekListBean.getAll_num();
                            if (lineNum.indexOf(weekListBean.getLine_id()) == -1) {
                                lineNum.add(weekListBean.getLine_id());
                            }
                            //当身份是运行班专责时，获取到需要审核的列表
                            if (weekListBean.getWeek_id() != null) {
                                if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED) && "1".equals(weekListBean.getAudit_status())) {
                                    Tower lineBean = new Tower();
                                    lineBean.setId(weekListBean.getId());
                                    lineList.add(lineBean);
                                    //当身份是运行班专责时，获取到需要发布的列表
                                } else if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && ("0".equals(weekListBean.getAudit_status()) || "3".equals(weekListBean.getAudit_status()))) {
                                    Tower lineBean = new Tower();
                                    lineBean.setId(weekListBean.getId());
                                    lineList.add(lineBean);
                                }
                            }
                        }
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        monthLineTotal.setText("工作线路：" + lineNum.size() + "条");
                        monthLineKiloTotal.setText(decimalFormat.format(kilo_total) + " km");
                        monthLine110kvNum.setText("工作杆段：" + num_total + "段");
                        monthLine110kvKilo.setText(decimalFormat.format(kilo_total) + " km");

                        if (all_num_total == 0) {
                            donePlanRange.setText("计划进度：0%");
                        } else {
                            double range = Utils.div(done_num_total, all_num_total, 4)*100;
                            donePlanRange.setText("计划进度：" + range + "%");
                        }
                        if (lineList.size() != 0) {
                            planSubmit.setVisibility(View.VISIBLE);
                        } else {
                            planSubmit.setVisibility(View.GONE);
                        }
                        planRefresh.setRefreshing(false);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        planRefresh.setRefreshing(false);
                    }
                });
    }

    //获取下周计划
    public void getNextWeekList() {
        BaseRequest.getInstance().getService()
                .getWeekList(nextYear + "", nextWeek + "", depId, state, "create_time desc,type_sign,line_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<WeekListBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<WeekListBean>> t) throws Exception {
                        nextlineList.clear();
                        next_num_total = 0;
                        next_kilo_total = 0;
                        nextlineList.clear();
                        nextWeekList = t.getResults();
                        if (nextWeekList != null && nextWeekList.size() > 0) {
                            addPlanIv.setVisibility(View.GONE);
                            addPlanLl.setVisibility(View.VISIBLE);
                            addPlanName.setText(nextYear + "年第" + nextWeek + "周工作计划");
                            weekListBean = nextWeekList.get(0);
                            String audit_status = weekListBean.getAudit_status();
                            addPlanStatus.setVisibility(View.VISIBLE);
                            addPlanStatus.setText(StringUtil.getYxbWeekState(weekListBean.getAudit_status()));
                            if ("2".equals(weekListBean.getAudit_status())) {
                                addPlanStatus.setBackgroundResource(R.drawable.state_green_bg);
                                addPlanStatus.setTextColor(getResources().getColor(R.color.green));
                            }
                            if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && "0".equals(audit_status)) {
                                planSubmitNext.setVisibility(View.VISIBLE);
                            } else if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED) && "1".equals(audit_status)) {
                                planSubmitNext.setVisibility(View.VISIBLE);
                            } else {
                                planSubmitNext.setVisibility(View.GONE);
                            }
                        } else {
                            addPlanIv.setVisibility(View.VISIBLE);
                            addPlanLl.setVisibility(View.GONE);
                        }
                        for (int i = 0; i < nextWeekList.size(); i++) {
                            WeekListBean weekListBean = nextWeekList.get(i);
                            next_num_total++;
                            next_kilo_total += weekListBean.getTowers_range();
                            //当身份是运行班专责时，获取到需要审核的列表
                            if (weekListBean.getWeek_id() != null) {
                                if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED) && "1".equals(weekListBean.getAudit_status())) {
                                    Tower lineBean = new Tower();
                                    lineBean.setId(weekListBean.getId());
                                    nextlineList.add(lineBean);
                                    //当身份是运行班专责时，获取到需要发布的列表
                                } else if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && ("0".equals(weekListBean.getAudit_status()) || "3".equals(weekListBean.getAudit_status()))) {
                                    Tower lineBean = new Tower();
                                    lineBean.setId(weekListBean.getId());
                                    nextlineList.add(lineBean);
                                }
                            }
                        }
                        if (nextlineList.size() != 0) {
                            planSubmitNext.setVisibility(View.VISIBLE);
                        } else {
                            planSubmitNext.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    @OnClick({R.id.task_date, R.id.add_plan_right, R.id.add_plan_iv, R.id.plan_submit_next, R.id.plan_submit, R.id.add_plan_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showWeek();
                break;
            case R.id.add_plan_ll:

                    Intent intent1 = new Intent(getContext(), NextWeekPlanActivity.class);
                    intent1.putExtra("list", (Serializable) nextWeekList);
                    intent1.putExtra("linelist", (Serializable) nextlineList);
                    intent1.putExtra("num_total", next_num_total);
                    intent1.putExtra("kilo_total", next_kilo_total);
                    intent1.putExtra("year", nextYear);
                    intent1.putExtra("week", nextWeek);
                    intent1.putExtra("audit_status", weekListBean.getAudit_status());
                    startActivityForResult(intent1, 10);

                break;
            case R.id.plan_submit:
                subimt(lineList, 1);
                break;
            case R.id.plan_submit_next:
                subimt(nextlineList, 2);
                break;
            case R.id.add_plan_iv:
                if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                Intent intent = new Intent(getContext(), AddWeekPlanActivity.class);
                intent.putExtra("year", nextYear);
                intent.putExtra("week", nextWeek);
                //当本周和下周都没有数据是，添加周计划可以选择日期
                if (results.size()==0&&nextWeekList.size()==0){
                    intent.putExtra("from","WeekPlanFrgment");
                }

                startActivityForResult(intent, 10);
                } else {
                    Toast.makeText(getContext(), "您没有权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_plan_right:
                 if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                Intent intent2 = new Intent(getContext(), TemporaryActivity.class);
                intent2.putExtra("year", String.valueOf(nextYear));
                intent2.putExtra("week", String.valueOf(nextWeek));
                startActivityForResult(intent2, 10);
                 } else {
                     Toast.makeText(getContext(), "您没有权限", Toast.LENGTH_SHORT).show();
                 }
                break;
        }
    }

    public void subimt(List<Tower> list, int type) {
        if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED) || mJobType.contains(Constant.RUN_SUPERVISOR)) {
            CancelOrOkDialog dialog = new CancelOrOkDialog(getActivity(), "一键审核", "不同意", "同意") {
                @Override
                public void ok() {
                    super.ok();
                    submitWeekPlan(list, "2", type);   //同意
                    dismiss();
                }

                @Override
                public void cancle() {
                    super.cancle();
                    submitWeekPlan(list, "3", type);  //不同意
                    dismiss();
                }
            };
            dialog.show();
        } else if (mJobType.contains((Constant.RUNNING_SQUAD_LEADER))) {
            CancelOrOkDialog dialog = new CancelOrOkDialog(getActivity(), "是否一键提交审核", "取消", "确定") {
                @Override
                public void ok() {
                    super.ok();
                    submitWeekPlan(list, "1", type);   //同意
                    dismiss();
                }

                @Override
                public void cancle() {
                    super.cancle();
                    dismiss();
                }
            };
            dialog.show();
        }
    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //提交周计划审核
    public void submitWeekPlan(List<Tower> list, String status, int type) {
        SubmitPlanReqBean bean = new SubmitPlanReqBean();
        bean.setYear(year + "");
        bean.setWeek(week + "");
        bean.setAudit_status(status);
        bean.setFrom_user_id(SPUtil.getUserId(getContext()));
        bean.setTowers(list);
        BaseRequest.getInstance().getService()
                .submitWeekPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            if ("1".equals(status)) {
                                Toast.makeText(getContext(), "提交成功", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(getContext(), "审核成功", Toast.LENGTH_SHORT).show();
                            }

                            if (type == 1) {
                                getWeekList();
                            } else {
                                getNextWeekList();
                            }


                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
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

    //展示周
    public void showWeek() {

        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                //返回的分别是三个级别的选中位置
                String time = years.get(options1) + weeks.get(options1).get(option2);
                year = years.get(options1).split("年")[0];
                String date = weeks.get(options1).get(option2);
                week = Integer.parseInt(date.split("周")[0].split("第")[1]);
                taskDate.setText(time);
                taskTitle.setText(week+"周计划列表");
                planTotalTitle.setText(week+"周计划工作汇总");
                getWeekList();
            }
        }).build();
        pvOptions.setPicker(years, weeks);
        pvOptions.setSelectOptions(years.indexOf(year + "年"), week - 1);
        pvOptions.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            getWeekList();
            getNextWeekList();
        }
    }
}
