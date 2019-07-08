package com.patrol.terminal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddPersonalTaskActivity;
import com.patrol.terminal.activity.PersonalTaskDetailActivity;
import com.patrol.terminal.adapter.PersonalTaskAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.bean.GroupTaskBean_Table;
import com.patrol.terminal.bean.SaveTodoReqbean;
import com.patrol.terminal.bean.TypeBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PersonalTaskFrgment extends BaseFragment {


    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.task_add)
    ImageView taskAdd;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.plan_create)
    TextView planCreate;
    @BindView(R.id.plan_refresh)
    SwipeRefreshLayout mRefrsh;
    private PersonalTaskAdapter personalTaskAdapter;
    private TimePickerView pvTime;
    private List<GroupTaskBean> result = new ArrayList<>();
    private String time;
    private String id;

    private String gId;
    private String week;
    private String year,month,day;
    private Disposable refreshPersonal;
    private String userid;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        String jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        userid=SPUtil.getUserId(getContext());
        if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)){
            taskAdd.setVisibility(View.VISIBLE);
        }else {
            taskAdd.setVisibility(View.GONE);
        }
        planCreate.setVisibility(View.GONE);
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate();
        taskTitle.setText("个人任务列表");
        taskDate.setText(time);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        // 设置监听器。
        planRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        planRv.setOnItemMenuClickListener(mItemMenuClickListener);
        personalTaskAdapter = new PersonalTaskAdapter(R.layout.fragment_task_item, result);
        planRv.setAdapter(personalTaskAdapter);
        personalTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GroupTaskBean bean = result.get(position);
                Intent intent=new Intent();
                intent.setClass(getContext(), PersonalTaskDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("bean",bean);
                intent.putExtras(bundle);
                startActivityForResult(intent,12);
            }
        });

        if (Utils.isNetworkConnected(getContext())){
            getGroupList();
        }else {
            getDbGroupList();
        }


        mRefrsh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utils.isNetworkConnected(getContext())){
                    getGroupList();
                }else {
                    getDbGroupList();
                }
            }
        });
        refreshPersonal = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.equals("refreshPersonal")) {
                    if (Utils.isNetworkConnected(getContext())){
                        getGroupList();
                    }else {
                        getDbGroupList();
                    }
                }
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


    private void getDbGroupList() {
        result = SQLite.select().from(GroupTaskBean.class)
                .where(GroupTaskBean_Table.year.eq(Integer.valueOf(year)),GroupTaskBean_Table.month.eq(Integer.valueOf(month)),
                        GroupTaskBean_Table.day.eq(Integer.valueOf(day)),GroupTaskBean_Table.allot_status.eq("1"),
                        GroupTaskBean_Table.work_user_id.eq(userid),GroupTaskBean_Table.user_id.eq(userid))
                //.orderBy(OrderBy.fromNameAlias(NameAlias.of("line_id,name")))
                .queryList();

        if (result != null) {
            personalTaskAdapter.setNewData(result);
            for (int i = 0; i < result.size(); i++) {
                String is_rob = result.get(i).getIs_rob();
                String audit_status = result.get(i).getAudit_status();
                String done_status = result.get(i).getDone_status();
                if ("1".equals(is_rob)&&("0".equals(audit_status)||"3".equals(audit_status))&&"0".equals(done_status)){
                    planRv.setSwipeItemMenuEnabled(i,true);
                }else {
                    planRv.setSwipeItemMenuEnabled(i,false);
                }
            }
            RxRefreshEvent.publish("refreshPersonalNum@"+result.size());
            if (mRefrsh != null) {
                mRefrsh.setRefreshing(false);
            }
            ProgressDialog.cancle();
        }
    }

    //获取小组任务列表
    public void getGroupList() {
        BaseRequest.getInstance().getService()
                .getPersonalOfGroup(year, month, day,null,null,"1",userid,"line_id,name")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GroupTaskBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<GroupTaskBean>> t) throws Exception {
                        result = t.getResults();
                        personalTaskAdapter.setNewData(result);

                        for (int i = 0; i < result.size(); i++) {
                            String is_rob = result.get(i).getIs_rob();
                            String audit_status = result.get(i).getAudit_status();
                            String done_status = result.get(i).getDone_status();
                            if ("1".equals(is_rob)&&("0".equals(audit_status)||"3".equals(audit_status))&&"0".equals(done_status)){
                                planRv.setSwipeItemMenuEnabled(i,true);
                            }else {
                                planRv.setSwipeItemMenuEnabled(i,false);
                            }

                            //查询后存储到本地数据库  by linmeng
                            saveToDatebase(result.get(i));
                        }
                        RxRefreshEvent.publish("refreshPersonalNum@"+result.size());
                        mRefrsh.setRefreshing(false);
                        ProgressDialog.cancle();
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


        List<GroupTaskBean> existBeans = SQLite.select().from(GroupTaskBean.class)
                .where(GroupTaskBean_Table.id.eq(bean.getId()))
                .queryList();

        if (existBeans.size() > 0) {   //数据存在
            groupTaskBean.update();
        }else {
            groupTaskBean.save();
        }

    }

    //抢单任务退还
    public void robBack(String id,int position) {
        ProgressDialog.show(getContext(),true,"正在加载。。。");
        SaveTodoReqbean reqbean=new SaveTodoReqbean();
        reqbean.setAudit_status("1");
        reqbean.setFrom_user_id(SPUtil.getUserId(getContext()));
        reqbean.setFrom_user_name(SPUtil.getUserName(getContext()));
        reqbean.setId(id);
        BaseRequest.getInstance().getService()
                .robBack(reqbean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TypeBean>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<TypeBean> t) throws Exception {
                        result.get(position).setAudit_status("1");
                        RxRefreshEvent.publish("refreshTodo");
                        planRv.setSwipeItemMenuEnabled(position,false);
                        personalTaskAdapter.notifyItemChanged(position);

                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();

                    }
                });
    }


    public void showDay() {
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
                getDbGroupList();
                getGroupList();
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
    @OnClick({R.id.task_date, R.id.task_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showDay();
                break;
            case R.id.task_add:
                Intent intent = new Intent(getContext(), AddPersonalTaskActivity.class);
                startActivityForResult(intent,12);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==12&&resultCode==-1){
            getDbGroupList();
            getGroupList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (refreshPersonal!=null){
            refreshPersonal.dispose();
        }
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
                robBack(result.get(position).getId(), position);

        }
    };
}
