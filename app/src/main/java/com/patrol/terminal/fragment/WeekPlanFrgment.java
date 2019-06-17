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

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddWeekPlanActivity;
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
    private TimePickerView pvTime;
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
    private PopMenmuDialog popWinShare;
    private int nextYear;
    private int nextWeek;
    private List<WeekListBean> nextWeekList;
    private String nextBeginTime;

    private int num_total = 0;
    private int num_110kv = 0;
    private int num_35kv = 0;
    private double kilo_total = 0;
    private double kilo_110kv = 0;
    private double kilo_35kv = 0;

    private int next_num_total = 0;
    private int next_num_110kv = 0;
    private int next_num_35kv = 0;
    private double next_kilo_total = 0;
    private double next_kilo_110kv = 0;
    private double next_kilo_35kv = 0;
    private int done_num_total = 0;
    private int all_num_total = 0;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_list, container, false);
        return view;
    }

    @Override
    protected void initData() {

        depId = SPUtil.getDepId(getContext());
        mJobType = SPUtil.getString(getActivity(), Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
            state = "1,2,3,4,5";
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
        taskDate.setText(year + "年第" + week + "周(" + beginDate + "-" + endDate + ")");
        addNextPlan.setText("下周工作计划制定(" + nextBeginTime + "-" + nextEndTime + ")");
        planTotalTitle.setText("本周计划工作汇总");
        // 设置监听器。
        planRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        planRv.setOnItemMenuClickListener(mItemMenuClickListener);
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

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
//            int width = getResources().getDimensionPixelSize(R.dimen.dp_60);
//
//            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
//            // 2. 指定具体的高，比如80;
//            // 3. WRAP_CONTENT，自身高度，不推荐;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
//            deleteItem.setWidth(width);
//            deleteItem.setHeight(height);
//            deleteItem.setTextSize(15);
//            deleteItem.setTextColorResource(R.color.white);
//            deleteItem.setBackground(R.color.orange_vip);
//            deleteItem.setText("编辑");
//            SwipeMenuItem deleteItem1 = new SwipeMenuItem(getContext());
//            deleteItem1.setWidth(width);
//            deleteItem1.setHeight(height);
//            deleteItem1.setBackground(R.color.home_red);
//            deleteItem1.setTextSize(15);
//            deleteItem1.setTextColorResource(R.color.white);
//            deleteItem1.setText("删除");
//            // 各种文字和图标属性设置。
//            rightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。
//            rightMenu.addMenuItem(deleteItem1); // 在Item右侧添加一个菜单。
//            // 注意：哪边不想要菜单，那么不要添加即可。
        }
    };


    //获取本周计划
    public void getWeekList() {
        BaseRequest.getInstance().getService()
                .getWeekList(year, week + "", depId, state, "type_sign,line_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<WeekListBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<WeekListBean>> t) throws Exception {
                        lineList.clear();
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
                        monthLineTotal.setText("杆段总数 : " + num_total + "条");
                        monthLineKiloTotal.setText("总公里数 : " + decimalFormat.format(kilo_total) + "公里");
                        BigDecimal b1 = new BigDecimal(done_num_total);
                        BigDecimal b2 = new BigDecimal(all_num_total);
                        if (all_num_total == 0) {
                            donePlanRange.setText("计划进度 : 0%");
                        } else {
                            //默认保留两位会有错误，这里设置保留小数点后4位
                            double range = b1.divide(b2, 0, BigDecimal.ROUND_HALF_UP).doubleValue();
                            donePlanRange.setText("计划进度 : " + range + "%");
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
                .getWeekList(nextYear + "", nextWeek + "", depId, state, "type_sign,line_id")
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

                            WeekListBean weekListBean = nextWeekList.get(0);
                            String audit_status = weekListBean.getAudit_status();
                            addPlanStatus.setText(StringUtil.getYxbWeekState(weekListBean.getAudit_status()));
                            if ("2".equals(weekListBean.getAudit_status())) {
                                addPlanStatus.setBackgroundResource(R.drawable.state_green_bg);
                                addPlanStatus.setTextColor(getResources().getColor(R.color.green));
                            }
                            if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && "0".equals(audit_status)) {
                                addPlanRight.setVisibility(View.VISIBLE);
                                planSubmit.setVisibility(View.VISIBLE);
                            } else if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED) && "1".equals(audit_status)) {
                                planSubmit.setVisibility(View.VISIBLE);
                                addPlanRight.setVisibility(View.GONE);
                            } else {
                                addPlanRight.setVisibility(View.GONE);
                                planSubmit.setVisibility(View.GONE);
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

    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

//            // 左侧还是右侧菜单：
//            int direction = menuBridge.getDirection();
//            // 菜单在Item中的Position：
//            int menuPosition = menuBridge.getPosition();
        }
    };


    @OnClick({R.id.task_date, R.id.add_plan_right, R.id.add_plan_iv, R.id.plan_submit, R.id.add_plan_ll})
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
                intent1.putExtra("110kv_num", next_num_110kv);
                intent1.putExtra("110kv_kolo", next_kilo_110kv);
                intent1.putExtra("35kv_num", next_num_35kv);
                intent1.putExtra("35kv_kolo", next_kilo_35kv);
                intent1.putExtra("year", nextYear);
                intent1.putExtra("week", nextWeek);
                startActivityForResult(intent1, 10);
                break;
            case R.id.plan_submit:
                subimt(lineList, 1);
                break;
            case R.id.plan_submit_next:
                subimt(nextlineList, 2);
                break;
            case R.id.add_plan_iv:
                Intent intent = new Intent(getContext(), AddWeekPlanActivity.class);
                intent.putExtra("year", nextYear);
                intent.putExtra("week", nextWeek);
                startActivityForResult(intent, 10);
                break;
            case R.id.add_plan_right:
                Intent intent2 = new Intent(getContext(), TemporaryActivity.class);
                intent2.putExtra("year", String.valueOf(nextYear));
                intent2.putExtra("week", String.valueOf(nextWeek));
                startActivityForResult(intent2, 10);
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
                public void cancel() {
                    super.cancel();
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
                public void cancel() {
                    super.cancel();
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
        bean.setAudit_status(status);
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
                options3Items_01.add("第" + (j) + "周(" + firstDayOfWeek + "-" + lastDayOfWeek + ")");

            }
            weeks.add(options3Items_01);
        }

    }

    //展示月份
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
