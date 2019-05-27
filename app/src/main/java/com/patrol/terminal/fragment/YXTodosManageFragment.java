package com.patrol.terminal.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.HongWaiCeWenActivity;
import com.patrol.terminal.activity.JiediDianZuCeLiangActicivity;
import com.patrol.terminal.activity.JueYuanZiLingZhiJianCeActivity;
import com.patrol.terminal.activity.NewMainActivity;
import com.patrol.terminal.activity.PatrolRecordActivity;
import com.patrol.terminal.activity.XieGanTaQingXieCeWenActivity;
import com.patrol.terminal.adapter.YXTodoManageAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.GroupBean;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.TodoListBean;
import com.patrol.terminal.overhaul.OverhaulWeekDetailActivity;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*待办管理*/
public class YXTodosManageFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {
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
    @BindView(R.id.to_do_tv)
    TextView toDoTv;
    @BindView(R.id.done_tv)
    TextView doneTv;
    @BindView(R.id.frag_todo_rv)
    RecyclerView fragTodoRv;
    @BindView(R.id.frag_todo_ref)
    SwipeRefreshLayout fragTodoRef;
    private Activity mActivity;
    private YXTodoManageAdapter toDoManageAdapter;
    private static final int IS_TODO_PAGE = 0;
    private static final int IS_DONE_PAGE = 1;
    private int isTodoPage = IS_TODO_PAGE;
    private String status;
    private List<PersonalTaskListBean> results=new ArrayList<>();
    private List<PersonalTaskListBean> resultsHave;

    private String jobType;
    private Disposable subscribe;
    private String state;
    private String dep_id;
    private String user_id;
    private String year,month,day,time;
    private String haveState;

    private String ele_user_id;
    private String check_user_id;
    private String safe_user_id;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.to_dos_manage_fragment, container, false);
        return view;
    }

    @Override
    protected void initData() {
        mActivity = getActivity();
        titleBack.setVisibility(View.GONE);
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate();
        titleName.setText("待办管理");

        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)){
            dep_id=SPUtil.getDepId(getContext());
            state="2";
            haveState="3";
            getYXtodo();
            getYXtodoHave();



        }else if (jobType.contains("yxb")){
            getGroupName();
        }
        if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED) || jobType.contains(Constant.SAFETY_SPECIALIZED) || jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)) {
            getWeekList();
        }
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        fragTodoRv.setLayoutManager(manager);
        toDoManageAdapter = new YXTodoManageAdapter(R.layout.fragment_yxtodo_item, results);
        fragTodoRv.setAdapter(toDoManageAdapter);

        fragTodoRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)||jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)){
                    results.clear();
                    getYXtodo();
                    getYXtodoHave();
                    if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED) || jobType.contains(Constant.SAFETY_SPECIALIZED) || jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)) {
                        getWeekList();
                    }
                }
            }
        });

        toDoManageAdapter.setOnItemClickListener(this);

        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.startsWith("todo")) {
                    results.clear();
                    if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)||jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)){
                        getYXtodo();
                        getYXtodoHave();
                    }
                    if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED) || jobType.contains(Constant.SAFETY_SPECIALIZED) || jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)) {
                        getWeekList();
                    }
                }
            }
        });
    }
    public void inteDate(){
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0])+"";
        year =years[0];
        day=Integer.parseInt(days[0])+"";
    }
    @OnClick({R.id.to_do_tv, R.id.done_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.to_do_tv:
                toDoTv.setBackgroundResource(R.drawable.date_white_bg);
                doneTv.setBackgroundResource(R.drawable.date_grey_bg);
                toDoTv.setTextColor(getResources().getColor(R.color.date_color));
                doneTv.setTextColor(getResources().getColor(R.color.white));

                isTodoPage = IS_TODO_PAGE;
                toDoManageAdapter.setNewData(results);
                toDoManageAdapter.setNewData(results);
                break;

            case R.id.done_tv:
                toDoTv.setBackgroundResource(R.drawable.date_grey_bg);
                doneTv.setBackgroundResource(R.drawable.date_white_bg);
                toDoTv.setTextColor(getResources().getColor(R.color.white));
                doneTv.setTextColor(getResources().getColor(R.color.date_color));

                isTodoPage = IS_DONE_PAGE;
                toDoManageAdapter.setNewData(results);
                toDoManageAdapter.setNewData(resultsHave);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PersonalTaskListBean todoListBean;
        if (isTodoPage ==IS_TODO_PAGE){
             todoListBean = results.get(position);
        }else {
            todoListBean = resultsHave.get(position);
        }
        String deal_type = todoListBean.getType_sign();
        String data_id = todoListBean.getId();
        Intent intent = new Intent();
        intent.putExtra("audit_status",todoListBean.getAudit_status());
        intent.putExtra("task_id", data_id);
        switch (deal_type) {
            case "1":
                intent.setClass(getContext(), PatrolRecordActivity.class);
                break;
            case "2":
                intent.setClass(getContext(), HongWaiCeWenActivity.class);
                break;
            case "3":
                intent.setClass(getContext(), JiediDianZuCeLiangActicivity.class);
                break;
            case "10":
                intent.setClass(getContext(), JueYuanZiLingZhiJianCeActivity.class);
                break;
            case "5":
                intent.setClass(getContext(), HongWaiCeWenActivity.class);
                break;
            case "6":
                intent.setClass(getContext(), XieGanTaQingXieCeWenActivity.class);
                break;
            case "20":
                intent.setClass(getContext(), OverhaulWeekDetailActivity.class);
                intent.putExtra("id",todoListBean.getId());
                break;

        }
        startActivity(intent);
    }

    //获取周计划列表
    public void getWeekList() {

        String userId = SPUtil.getString(getContext(), Constant.USER, Constant.USERID, "");

        if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)) {
            userId = null;
        }else if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)){
            ele_user_id =userId;
            userId = null;
        }else if (jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)){
            check_user_id =userId;
        }else if (jobType.contains(Constant.SAFETY_SPECIALIZED)){
            safe_user_id =userId;
        }

        String week = DateUatil.getWeekNum()+"";

        //安全,验收,保电需要传userId
        BaseRequest.getInstance().getService()
                .getOverhaulPlanList(year, month, week, "2", userId, ele_user_id, check_user_id,safe_user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulYearBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulYearBean>> t) throws Exception {
                        List<OverhaulYearBean> overhaulYearBeans = t.getResults();
                        for (int i = 0; i < overhaulYearBeans.size(); i++) {
                            OverhaulYearBean overhaulYearBean = overhaulYearBeans.get(i);
                            PersonalTaskListBean bean=new PersonalTaskListBean();
                            bean.setLine_name(overhaulYearBean.getLine_name());
                            bean.setLine_id(overhaulYearBean.getLine_id());
                            bean.setDone_time(overhaulYearBean.getStart_time());
                            bean.setId(overhaulYearBean.getId());
                            bean.setTower_name("");
                            bean.setUser_name("检修专责");
                            bean.setType_name("检修");
                            bean.setType_sign("20");
                            bean.setAudit_status("-1");
                            results.add(bean);
                        }
                        toDoManageAdapter.setNewData(results);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.e("fff", e.toString());
                    }
                });
    }

    public void getYXtodo() {
        ProgressDialog.show(getContext(),false,"正在加载中");
        BaseRequest.getInstance().getService()
                .getYXtodoList(year,month,day,dep_id,user_id,state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PersonalTaskListBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<PersonalTaskListBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        fragTodoRef.setRefreshing(false);
                        results = t.getResults();
                        if (isTodoPage ==IS_TODO_PAGE){
                        toDoManageAdapter.setNewData(results);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        fragTodoRef.setRefreshing(false);
                    }
                });
    }
    public void getYXtodoHave() {
        BaseRequest.getInstance().getService()
                .getYXtodoList(year,month,day,dep_id,user_id,haveState)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PersonalTaskListBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<PersonalTaskListBean>> t) throws Exception {
                        fragTodoRef.setRefreshing(false);
                        resultsHave = t.getResults();
                        if (isTodoPage ==IS_DONE_PAGE){
                            toDoManageAdapter.setNewData(resultsHave);}
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        fragTodoRef.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (subscribe!=null){
            subscribe.dispose();
        }
    }
    //获取是否是运行班负责人
    public void getGroupName() {
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        String month = Integer.parseInt(months[0])+"";
        String year =years[0];
        String day=Integer.parseInt(days[0])+"";
        BaseRequest.getInstance().getService()
                .getGroupName(year,month,day,SPUtil.getDepId(getContext()),SPUtil.getUserId(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<GroupBean>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<GroupBean> t) throws Exception {
                        if (t.getCode() == 1) {
                            GroupBean results = t.getResults();
                            if (results!=null){
                                if ("1".contains(results.getIs_boss())){
                                    SPUtil.putString(getContext(), Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_TEMA_LEADER);
                                    user_id=SPUtil.getUserId(getContext());
                                    state="1";
                                    haveState="2";
                                    getYXtodo();
                                    getYXtodoHave();
                                }
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
}
