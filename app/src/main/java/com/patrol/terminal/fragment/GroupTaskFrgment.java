package com.patrol.terminal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.patrol.terminal.bean.YXtoJXbean;
import com.patrol.terminal.overhaul.OverhaulPlanActivity;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
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
    private String userId,duty_user_id;
    private String depId;
    private String jobType;
    private Disposable refreshGroup;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        planCreate.setVisibility(View.GONE);
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate();
        taskDate.setText(time);
        taskTitle.setText("组任务列表");
        taskAdd.setVisibility(View.GONE);
        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        getData();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        groupTaskAdapter = new GroupTaskAdapter(R.layout.fragment_task_item, result);
        planRv.setAdapter(groupTaskAdapter);
        groupTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                GroupTaskBean bean = result.get(position);
                intent.setClass(getContext(), GroupTaskDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("GroupTaskBean",bean);
                intent.putExtras(bundle);
                startActivityForResult(intent,11);
            }
        });
        refreshGroup = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.equals("refreshGroup")) {
                    getData();
                }
            }
        });
        mRefrsh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    //根据职位获取页面数据
    public void getData(){
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
    //获取小组任务列表
    public void getGroupList() {

        BaseRequest.getInstance().getService()
                .getGroupList(year, month, day,depId,duty_user_id,userId,"type_sign,line_id","1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GroupTaskBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<GroupTaskBean>> t) throws Exception {
                        result = t.getResults();
                        groupTaskAdapter.setNewData(result);
                        mRefrsh.setRefreshing(false);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mRefrsh.setRefreshing(false);
                    }
                });

    }

    //获取小组负责人的检修任务
    public void getRepairList() {

        BaseRequest.getInstance().getService()
                .getRepairList(year, month, day,SPUtil.getUserId(getContext()),"4")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<YXtoJXbean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<YXtoJXbean>> t) throws Exception {
                        List<YXtoJXbean> jxlist = t.getResults();
                        if (jxlist!=null&&jxlist.size()>0){
                            planSubmit.setText("检修");
                            planSubmit.setVisibility(View.VISIBLE);
                        }else {
                            planSubmit.setVisibility(View.GONE);
                        }
                        mRefrsh.setRefreshing(false);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mRefrsh.setRefreshing(false);
                    }
                });

    }

    //获取小组任务列表
    public void getGroupListZy() {

        BaseRequest.getInstance().getService()
                .getGroupList(year, month, day,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GroupTaskBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<GroupTaskBean>> t) throws Exception {
                        result = t.getResults();
                        groupTaskAdapter.setNewData(result);
                        mRefrsh.setRefreshing(false);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mRefrsh.setRefreshing(false);
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
                inteDate();
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

    @OnClick({R.id.task_date, R.id.task_add,R.id.plan_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showDay();
                break;
            case R.id.plan_submit:
                Intent intent1 = new Intent(getContext(), OverhaulPlanActivity.class);
                startActivityForResult(intent1,11);
                break;
            case R.id.task_add:
                Intent intent = new Intent(getContext(), AddGroupTaskActivity.class);
                startActivityForResult(intent,11);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==11&&resultCode==-1){
            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                taskAdd.setVisibility(View.VISIBLE);
                depId = SPUtil.getDepId(getContext());
                getGroupList();
            }else if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)){
                duty_user_id = SPUtil.getUserId(getContext());
                getGroupList();
            }else if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER)){
                userId = SPUtil.getUserId(getContext());
                getGroupListZy();
            }
            getRepairList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (refreshGroup!=null){
            refreshGroup.dispose();
        }
    }
}
