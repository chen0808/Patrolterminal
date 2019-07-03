package com.patrol.terminal.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddGroupTaskActivity;
import com.patrol.terminal.activity.GroupTaskDetailActivity;
import com.patrol.terminal.adapter.GroupTaskAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.bean.GroupTaskBean_Table;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.TaskBean;
import com.patrol.terminal.bean.YXtoJXbean;
import com.patrol.terminal.overhaul.OverhaulPlanActivity;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;
import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GroupTaskFrgment extends BaseFragment {


    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.plan_submit)
    TextView planSubmit;
    @BindView(R.id.plan_create)
    TextView planCreate;
    @BindView(R.id.task_add)
    ImageView taskAdd;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.plan_refresh)
    SwipeRefreshLayout mRefrsh;


    private GroupTaskAdapter groupTaskAdapter;
    private TimePickerView pvTime;
    private List<GroupTaskBean> result = new ArrayList<>();
    private String time;
    private String year;
    private String month;
    private String day;
    private String userId, duty_user_id;
    private String depId;
    private String jobType;
    private boolean isRefresh=true;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        ProgressDialog.show(getContext(),true,"正在加载。。。");
        planCreate.setVisibility(View.GONE);
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate();
        taskDate.setText(time);
        taskTitle.setText("组任务列表");
        taskAdd.setVisibility(View.GONE);
        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        clearTodoAll();
        if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
            // 设置监听器。
            planRv.setSwipeMenuCreator(mSwipeMenuCreator);
            // 菜单点击监听。
            planRv.setOnItemMenuClickListener(mItemMenuClickListener);
        }
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        groupTaskAdapter = new GroupTaskAdapter(R.layout.fragment_task_item, result,jobType);
        planRv.setAdapter(groupTaskAdapter);
        groupTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                GroupTaskBean bean = result.get(position);
                intent.setClass(getContext(), GroupTaskDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("GroupTaskBean", bean);
                intent.putExtras(bundle);
                startActivityForResult(intent, 11);
            }
        });

//        getDataFromDatabase();
        //getData();

        mRefrsh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (Utils.isNetworkConnected(getContext())){
                    getData();
                }else {
                    getDataFromDatabase();
                }
            }
        });

    }

    private void getDataFromDatabase() {
        //从数据库查询出数据
        if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
            depId = SPUtil.getDepId(getContext());
            getDbGroupList();
        } else if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
            duty_user_id = SPUtil.getUserId(getContext());
            getDbGroupList();
        } else if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER)) {
            userId = SPUtil.getUserId(getContext());
            getDbGroupListZy();
        }
    }

    private void getDbGroupListZy() {
        result = SQLite.select().from(GroupTaskBean.class)
                .where(GroupTaskBean_Table.year.eq(Integer.valueOf(year)),GroupTaskBean_Table.month.eq(Integer.valueOf(month)),
                        GroupTaskBean_Table.day.eq(Integer.valueOf(day)))
                .queryList();

        if (result != null) {
            groupTaskAdapter.setNewData(result);
            RxRefreshEvent.publish("refreshGroupNum@" + result.size());
            mRefrsh.setRefreshing(false);
            isRefresh = true;
            ProgressDialog.cancle();
        }
    }

    private void getDbGroupList() {
        result = SQLite.select().from(GroupTaskBean.class)
                .where(GroupTaskBean_Table.year.eq(Integer.valueOf(year)),GroupTaskBean_Table.month.eq(Integer.valueOf(month)),
                        GroupTaskBean_Table.day.eq(Integer.valueOf(day)), GroupTaskBean_Table.dep_id.eq(depId),
                        GroupTaskBean_Table.duty_user_id.eq(duty_user_id),GroupTaskBean_Table.user_id.eq(userId),
                        GroupTaskBean_Table.safe.eq("1"))
                //.orderBy(OrderBy.fromNameAlias(NameAlias.of("duty_user_id,line_id,name")))
                .queryList();

        if (result != null && result.size() != 0) {
            groupTaskAdapter.setNewData(result);
            for (int i = 0; i < result.size(); i++) {
                String allot_status = result.get(i).getAllot_status();
                if ("0".equals(allot_status)) {
                    planRv.setSwipeItemMenuEnabled(i, true);
                } else {
                    planRv.setSwipeItemMenuEnabled(i, false);
                }
            }
            RxRefreshEvent.publish("refreshGroupNum@" + result.size());
            mRefrsh.setRefreshing(false);
            isRefresh = true;
            ProgressDialog.cancle();
        }
    }

    //根据职位获取页面数据
    public void getData() {
        isRefresh=false;
        if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
            taskAdd.setVisibility(View.VISIBLE);
            depId = SPUtil.getDepId(getContext());
            getGroupList();
        } else if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
            duty_user_id = SPUtil.getUserId(getContext());
            getGroupList();
        } else if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER)) {
            userId = SPUtil.getUserId(getContext());
            getGroupListZy();
        }
        getRepairList();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isRefresh){
            if (Utils.isNetworkConnected(getContext())){
                getData();
            }else {
                getDataFromDatabase();
            }


        }
    }

    //获取小组任务列表
    public void getGroupList() {
        BaseRequest.getInstance().getService()
                .getGroupList(year, month, day, depId, duty_user_id, userId, "duty_user_id,line_id,name", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GroupTaskBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<GroupTaskBean>> t) throws Exception {
                        result = t.getResults();
                        groupTaskAdapter.setNewData(result);
                        for (int i = 0; i < result.size(); i++) {
                            String allot_status = result.get(i).getAllot_status();
                            if ("0".equals(allot_status)){
                                planRv.setSwipeItemMenuEnabled(i,true);
                            }else {
                                planRv.setSwipeItemMenuEnabled(i,false);
                            }

                            //查询后存储到本地数据库  by linmeng
                            saveToDatebase(result.get(i));
                        }

                        RxRefreshEvent.publish("refreshGroupNum@"+result.size());
                        mRefrsh.setRefreshing(false);
                        isRefresh=true;
                        ProgressDialog .cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mRefrsh.setRefreshing(false);
                        ProgressDialog.cancle();
                    }
                });

    }

    private void saveToDatebase(GroupTaskBean bean) {
        GroupTaskBean groupTaskBean = new GroupTaskBean();
        groupTaskBean.setId(bean.getId());
        groupTaskBean.setDay_tower_id(bean.getDay_tower_id());
        groupTaskBean.setGroup_id(bean.getGroup_id());
        groupTaskBean.setType_sign(bean.getType_sign());
        groupTaskBean.setPlan_type(bean.getPlan_type());
        groupTaskBean.setLine_id(bean.getLine_id());
        groupTaskBean.setLine_name(bean.getLine_name());
        groupTaskBean.setDep_id(bean.getDep_id());
        groupTaskBean.setDep_name(bean.getDep_name());
        groupTaskBean.setYear(bean.getYear());
        groupTaskBean.setMonth(bean.getMonth());
        groupTaskBean.setDay(bean.getDay());
        groupTaskBean.setName(bean.getName());
        groupTaskBean.setStart_id(bean.getStart_id());
        groupTaskBean.setEnd_id(bean.getEnd_id());
        groupTaskBean.setDuty_user_id(bean.getDuty_user_id());
        groupTaskBean.setDuty_user_name(bean.getDuty_user_name());
        groupTaskBean.setWork_user_id(bean.getWork_user_id());
        groupTaskBean.setWork_user_name(bean.getWork_user_name());
        groupTaskBean.setAllot_status(bean.getAllot_status());
        groupTaskBean.setDone_status(bean.getDone_status());
        groupTaskBean.setDone_time(bean.getDone_time());
        groupTaskBean.setIs_rob(bean.getIs_rob());
        groupTaskBean.setAudit_status(bean.getAudit_status());
        groupTaskBean.setUser_id(SPUtil.getUserId(getContext()));
        groupTaskBean.setDone_rate(bean.getDone_rate());
            groupTaskBean.setSafe("1");

        List<GroupTaskBean> existBeans = SQLite.select().from(GroupTaskBean.class)
                .where(GroupTaskBean_Table.id.eq(bean.getId()))
                .queryList();

        if (existBeans.size() > 0) {   //数据存在
            groupTaskBean.update();
        }else {
            groupTaskBean.save();
        }

    }

    //获取小组负责人的检修任务
    public void getRepairList() {

        BaseRequest.getInstance().getService()
                .getRepairList(year, month, day, SPUtil.getUserId(getContext()), "4")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<YXtoJXbean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<YXtoJXbean>> t) throws Exception {
                        List<YXtoJXbean> jxlist = t.getResults();
                        if (jxlist != null && jxlist.size() > 0) {
                            planSubmit.setText("检修");
                            planSubmit.setVisibility(View.VISIBLE);
                        } else {
                            planSubmit.setVisibility(View.GONE);
                        }
                        mRefrsh.setRefreshing(false);
                        isRefresh=true;
                        ProgressDialog .cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mRefrsh.setRefreshing(false);
                        isRefresh=true;
                        ProgressDialog .cancle();
                    }
                });

    }

    //获取小组任务列表
    public void getGroupListZy() {

        BaseRequest.getInstance().getService()
                .getGroupList(year, month, day, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GroupTaskBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<GroupTaskBean>> t) throws Exception {
                        result = t.getResults();
                        groupTaskAdapter.setNewData(result);

                        for (int i = 0; i < result.size(); i++) {
                            //查询后存储到本地数据库  by linmeng
                            saveToDatebase(result.get(i));
                        }

                        RxRefreshEvent.publish("refreshGroupNum@"+result.size());
                        mRefrsh.setRefreshing(false);
                        isRefresh=true;
                        ProgressDialog .cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (mRefrsh!=null){
                            mRefrsh.setRefreshing(false);
                        }

                        isRefresh=true;
                        ProgressDialog .cancle();
                    }
                });

    }

    public void inteDate() {
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        day = Integer.parseInt(days[0]) + "";
    }

    public void showDay() {
        isRefresh=false;
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        //选中事件回调
//是否只显示中间选中项的label文字，false则每项item全部都带有label。
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                time = DateUatil.getDay(date);
                taskDate.setText(time);
                RxRefreshEvent.publish("refreshNum");
                ProgressDialog.show(getContext(),true,"正在加载。。。");
                inteDate();
                getDataFromDatabase();
                getData();
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentTextSize(18)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    @OnClick({R.id.task_date, R.id.task_add, R.id.plan_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showDay();
                break;
            case R.id.plan_submit:
                Intent intent1 = new Intent(getContext(), OverhaulPlanActivity.class);
                startActivityForResult(intent1, 11);
                break;
            case R.id.task_add:
                Intent intent = new Intent(getContext(), AddGroupTaskActivity.class);
                startActivityForResult(intent, 11);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == -1) {
//            getDataFromDatabase();
            getData();
        }
    }



    //消除所有查看类型待办
    public void clearTodoAll() {

        BaseRequest.getInstance().getService()
                .clearTodo(SPUtil.getUserId(getContext()), "3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TaskBean>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<TaskBean> t) throws Exception {
                        if (t.getCode() == 1) {
                            RxRefreshEvent.publish("refreshTodo");
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }


    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_60);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem1 = new SwipeMenuItem(getContext());
            deleteItem1.setImage(R.mipmap.plan_delete);
            deleteItem1.setWidth(width);
            deleteItem1.setHeight(height);
//            deleteItem1.setBackground(R.color.home_red);
//            deleteItem1.setTextSize(15);
//            deleteItem1.setTextColorResource(R.color.white);
//            deleteItem1.setText("删除");
            // 各种文字和图标属性设置。
            rightMenu.addMenuItem(deleteItem1); // 在Item右侧添加一个菜单。
            // 注意：哪边不想要菜单，那么不要添加即可。
        }
    };
    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            String allot_status = result.get(position).getAllot_status();
            if ("0".equals(allot_status)) {
                deleteGroupTask(result.get(position).getId(), position);
            } else {
                Toast.makeText(getContext(), "已经分配的小组任务不能删除", Toast.LENGTH_SHORT).show();
            }

            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();
        }
    };

    //删除小组任务
    public void deleteGroupTask(String id, int position) {
        ProgressDialog.show(getContext(), false, "正在加载中...");
        BaseRequest.getInstance().getService()
                .deleteGroupTask(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            result.remove(position);
                            groupTaskAdapter.notifyItemRemoved(position);
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


}
