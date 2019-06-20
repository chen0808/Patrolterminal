package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.TimeUtil;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/* 专责周计划*/
public class OverhaulWeekPlanFrgment extends BaseFragment {
    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.task_add)
    ImageView taskAdd;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.plan_refresh)
    SwipeRefreshLayout planRefresh;

    private OverhaulWeekAdapter weekAdapter;
    private String time;
    private List<OverhaulYearBean> results = new ArrayList<>();

    private List<String> years = new ArrayList<>();
    private List<List<String>> weeks = new ArrayList<>();

    //private TimePickerView pvTime;
    private String year, month/*,day*/;
    private int week;
    private String status;
    private Disposable subscribe;

    private String jobType;
    private String ele_user_id;
    private String check_user_id;
    private String safe_user_id;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
//初始化年月周数据
        initWeek();
        //time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                //初始化日期控件的数据
                initdate();
            }
        }).start();

//        taskAdd.setVisibility(View.GONE);
        time = SPUtil.getString(getContext(), "date", "overhaulTime", DateUatil.getTime(new Date(System.currentTimeMillis())));
        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");

//        if (jobType.contains(Constant.REFURBISHMENT_LEADER)|| jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)
//                || jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)|| jobType.contains(Constant.SAFETY_SPECIALIZED)) { //班长发布周检修工作
////            userId = SPUtil.getUserId(getContext());
//            status="1,2,3";
//
//        } else if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)) {   //专责发布周检修工作
//
//        } else if (jobType.contains(Constant.REFURBISHMENT_MEMBER)) {  //班员接受周检修工作   TODO  班员里面分负责人和普通班员
//            userId = SPUtil.getUserId(getContext());
//            status="2,3";
//        }

        taskTitle.setText("周计划列表");
        //taskDate.setText(time + "第" + week + "周");

        String beginDate = TimeUtil.getFirstDayOfWeek(new Date(System.currentTimeMillis()));
        String endDate = TimeUtil.getLastDayOfWeek(new Date(System.currentTimeMillis()));
        taskDate.setText(year + "年第" + week + "周(" + beginDate + "至" + endDate + ")");

        // 设置监听器。
        planRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        planRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        weekAdapter = new OverhaulWeekAdapter(R.layout.fragment_overhaul_week_item, results);
        planRv.setAdapter(weekAdapter);
        weekAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (results.get(position) == null) {
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(getContext(), OverhaulWeekDetailActivity.class);
                Bundle bundle = new Bundle();
                if (results.get(position) != null) {
                    bundle.putString("id", results.get(position).getId());
                }
                intent.putExtras(bundle);
                startActivity(intent);

               /* String weekAuditStatus = results.get(position).getWeek_audit_status();
                if ("1".equals(weekAuditStatus)) {  //待分发  不可修改
                    Intent intent = new Intent();
                    intent.setClass(getContext(), OverhaulAddMonthPlanActivity.class);
                    Bundle bundle = new Bundle();
                    if (result.get(position) != null) {
                        bundle.putParcelable("bean", result.get(position));
                    }
                    intent.putExtra("add_month_from_type", 0);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }else {    //已分发
                    Intent intent = new Intent();
                    intent.setClass(getContext(), OverhaulWeekDetailActivity.class);
                    Bundle bundle = new Bundle();
                    if (results.get(position) != null) {
                        bundle.putString("id", results.get(position).getId());
                    }
                    intent.putExtras(bundle);
                    startActivity(intent);
                }*/
            }
        });
        //getDayList();
        getWeekList();
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                if (s.startsWith("updateMonthPlan")) {
                    getWeekList();
                }
            }
        });
        planRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWeekList();
            }
        });
    }


    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_60);
        }
    };


    //获取周计划列表
    public void getWeekList() {
        results.clear();
        String userId = SPUtil.getString(getContext(), Constant.USER, Constant.USERID, "");

        if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)) {
            userId = null;
        } else if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)) {
            ele_user_id = userId;
            userId = null;
        } else if (jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)) {
            check_user_id = userId;
        } else if (jobType.contains(Constant.SAFETY_SPECIALIZED)) {
            safe_user_id = userId;
        }

        //安全,验收,保电需要传userId
        BaseRequest.getInstance().getService()
                .getOverhaulPlanList(year, month, week+"", "2", userId, ele_user_id, check_user_id, safe_user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulYearBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulYearBean>> t) throws Exception {
                        planRefresh.setRefreshing(false);
                        List<OverhaulYearBean> overhaulYearBeans = t.getResults();
                        for (int i = 0; i < overhaulYearBeans.size(); i++) {
                            if ("2".equals(overhaulYearBeans.get(i).getMonth_audit_status())) {   //只显示审核通过的周计划
                                results.add(overhaulYearBeans.get(i));
                            }
                        }
                        weekAdapter.setNewData(results);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        planRefresh.setRefreshing(false);
                        Log.e("fff", e.toString());
                    }
                });
    }

 /*  //获取周计划列表
    public void getWeekList() {
            BaseRequest.getInstance().getService()
                    .getOverhaulZzWeekList(year,month,week)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<List<OverhaulYearBean>>(getContext()) {

                        @Override
                        protected void onSuccees(BaseResult<List<OverhaulYearBean>> t) throws Exception {
                            results = t.getResults();
                            weekAdapter.setNewData(results);
                        }

                        @Override
                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                            Log.e("fff", e.toString());
                        }
                    });
    }*/


    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();
        }
    };


    @OnClick({R.id.task_date, R.id.task_add/*,R.id.plan_create*/})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showWeek();
                break;
            case R.id.task_add:
                Intent intent = new Intent(getContext(), OverhaulAddMonthPlanActivity.class);
                intent.putExtra("add_month_from_type", 2);
                startActivityForResult(intent, 10);
                break;
//            case R.id.plan_create:
//                Intent intent1 = new Intent(getContext(), CreatePlanActivity.class);
//                intent1.putExtra("from",0);
//                startActivityForResult(intent1,10);
//                break;
        }
    }

    //展示月份
    public void showWeek() {

        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                //返回的分别是三个级别的选中位置
                //返回的分别是三个级别的选中位置
                String time = years.get(options1) + weeks.get(options1).get(option2);
                year = years.get(options1).split("年")[0];
                String date = weeks.get(options1).get(option2);
                week = Integer.parseInt(date.split("周")[0].split("第")[1]);
                taskDate.setText(time);
                //taskDate.setText(time);

                Map<String, Object> taskDateStr = DateUatil.getScopeForWeeks(Integer.parseInt(year), Integer.parseInt(month), week);
                String beginDate = (String) taskDateStr.get("beginDate");
                String endDate = (String) taskDateStr.get("endDate");
                taskDate.setText(beginDate + "至" + endDate + "（" + "第" + week + "周" + "）");

                getWeekList();

                SPUtil.putString(getContext(), "date", "overhaulTime", time);
            }
        }).build();
        pvOptions.setPicker(years, weeks);
        pvOptions.setSelectOptions(years.indexOf(year + "年"), week - 1);
        pvOptions.show();
    }

    //初始化日期
    public void initWeek() {
        time = DateUatil.getCurMonth();
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        week = TimeUtil.getCurrWeek();
    }
    //初始化周数据
    public void initdate() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            getWeekList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
