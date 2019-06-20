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
import com.patrol.terminal.bean.OverhaulZzTaskBean;
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

/*专责周任务*/
public class OverhaulZzWeekTaskFrgment extends BaseFragment {
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

    private OverhaulZzTaskAdapter weekTaskAdapter;
    private String time;
    private List<OverhaulZzTaskBean> results = new ArrayList<>();

    private List<String> years = new ArrayList<>();
    private List<List<String>> months = new ArrayList<>();
    private List<List<String>> weeks = new ArrayList<>();

    //private TimePickerView pvTime;
    private String year, month/*,day*/;
    private int week;
    private String status;
    private String userId;
    private Disposable subscribe;

    private String jobType;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {

        //time = DateUatil.getDay(new Date(System.currentTimeMillis()));
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

        //initDate();
        taskAdd.setVisibility(View.GONE);
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
//        String[] years = time.split("年");
//        String[] months = years[1].split("月");
//        month = Integer.parseInt(months[0]) + "";
//        year = years[0];
//        week = DateUatil.getWeekNum() + "";
        taskTitle.setText("周任务列表");
        //taskDate.setText(time + "第" + week + "周");

//        Map<String, Object> taskDateStr = DateUatil.getScopeForWeeks(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(week));
//        String beginDate = (String) taskDateStr.get("beginDate");
//        String endDate = (String) taskDateStr.get("endDate");
//        taskDate.setText(beginDate + "至" + endDate + "（" + "第" + week + "周" + "）");

        String beginDate = TimeUtil.getFirstDayOfWeek(new Date(System.currentTimeMillis()));
        String endDate = TimeUtil.getLastDayOfWeek(new Date(System.currentTimeMillis()));
        taskDate.setText(year + "年第" + week + "周(" + beginDate + "至" + endDate + ")");

        // 设置监听器。
        planRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        planRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        weekTaskAdapter = new OverhaulZzTaskAdapter(R.layout.fragment_overhaul_week_item, results);
        planRv.setAdapter(weekTaskAdapter);
        weekTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getContext(), OverhaulZzWeekTaskDetailActivity.class);   //专责周任务详情   TODO
                Bundle bundle = new Bundle();
                if (results.get(position) != null) {
                    bundle.putString("id", results.get(position).getId());
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //getDayList();
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


    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_60);
        }
    };

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


    //专责获取周任务列表
    public void getWeekList() {
        BaseRequest.getInstance().getService()
                .getOverhaulZzTaskList(year, month, week + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulZzTaskBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulZzTaskBean>> t) throws Exception {
                        planRefresh.setRefreshing(false);
                        results = t.getResults();
                        weekTaskAdapter.setNewData(results);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        planRefresh.setRefreshing(false);
                        Log.e("fff", e.toString());
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




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            //getDayList();
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
