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
import com.patrol.terminal.bean.JxbSignInfo;
import com.patrol.terminal.bean.OverhaulMonthBean;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*其他人员周任务*/
public class OverhaulWeekTaskFrgment extends BaseFragment {
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

    private OverhaulWeekTaskAdapter weekTaskAdapter;
    private String time;
    private List<OverhaulMonthBean> results = new ArrayList<>();

    private List<String> years = new ArrayList<>();
    private List<List<String>> months = new ArrayList<>();
    private List<List<String>> weeks = new ArrayList<>();

    private String year, month/*,day*/;
    private int week;
    private String userId;
    private String sign;
    private Disposable subscribe;

    private String jobType;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        //初始化年月周数据
        initWeek();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //初始化日期控件的数据
                initdate();
            }
        }).start();


        taskAdd.setVisibility(View.GONE);
        time = SPUtil.getString(getContext(), "date", "overhaulTime", DateUatil.getTime(new Date(System.currentTimeMillis())));
        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        userId = SPUtil.getUserId(getContext());


        if (jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)) {
            sign = "1";
        } else if (jobType.contains(Constant.SAFETY_SPECIALIZED)) {
            sign = "2";
        }

        taskTitle.setText("周任务列表");

        String beginDate = TimeUtil.getFirstDayOfWeek(new Date(System.currentTimeMillis()));
        String endDate = TimeUtil.getLastDayOfWeek(new Date(System.currentTimeMillis()));
        taskDate.setText(year + "年第" + week + "周(" + beginDate + "至" + endDate + ")");

        // 设置监听器。
        planRv.setSwipeMenuCreator(mSwipeMenuCreator);

        // 菜单点击监听。
        planRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        weekTaskAdapter = new OverhaulWeekTaskAdapter(R.layout.fragment_overhaul_week_item, results, 1);
        planRv.setAdapter(weekTaskAdapter);
        weekTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getContext(), OverhaulWeekPlanDetailActivity.class);
//                intent.setClass(getContext(), JXTotalActivity.class);
                Bundle bundle = new Bundle();
                if (results.get(position) != null) {
                    bundle.putString("id", results.get(position).getId());
                }
                intent.putExtras(bundle);
                startActivityForResult(intent, 1001);
            }
        });

        getWeekList();

        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                if (s.startsWith("publishRepair")) {
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


    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_60);
        }
    };


    //其他人员获取周任务列表
    public void getWeekList() {
        Log.w("linmeng", "sign:" + sign);

        BaseRequest.getInstance().getService()
                .getSign(year, month, week + "", userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<JxbSignInfo>>(getActivity()) {
                    @Override
                    protected void onSuccees(BaseResult<List<JxbSignInfo>> t) throws Exception {
                        planRefresh.setRefreshing(false);
                        List<JxbSignInfo> result = t.getResults();
                        if (result != null && result.size() > 0) {
                            SPUtil.put(getActivity(), "jxb", "sign", result.get(0).getSign());
                            BaseRequest.getInstance().getService()
                                    .getOverhaulTaskList(year, month, week + "", userId, result.get(0).getSign())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new BaseObserver<List<OverhaulMonthBean>>(getContext()) {

                                        @Override
                                        protected void onSuccees(BaseResult<List<OverhaulMonthBean>> t) throws Exception {
                                            results = t.getResults();
                                            weekTaskAdapter.setNewData(results);
                                        }

                                        @Override
                                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                            Log.e("fff", e.toString());
                                        }
                                    });
                        } else {
                            results.clear();
                            weekTaskAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        planRefresh.setRefreshing(false);
                    }
                });
    }


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
                Intent intent = new Intent(getContext(), OverhaulAddWeekPlanActivity.class);
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
                String time = years.get(options1) + weeks.get(options1).get(option2);
                year = years.get(options1).split("年")[0];
                String date = weeks.get(options1).get(option2);
                week = Integer.parseInt(date.split("周")[0].split("第")[1]);
                taskDate.setText(time);

                getWeekList();

                SPUtil.putString(getContext(), "date", "overhaulTime", time);
            }
        }).build();
        pvOptions.setPicker(years, weeks);
        pvOptions.setSelectOptions(years.indexOf(year + "年"), week - 1);
        pvOptions.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
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
