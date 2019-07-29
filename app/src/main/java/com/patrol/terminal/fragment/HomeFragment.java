package com.patrol.terminal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.CheckActivity;
import com.patrol.terminal.activity.DangerVerifyActivity;
import com.patrol.terminal.activity.DefectActivity;
import com.patrol.terminal.activity.HongWaiCeWenActivity;
import com.patrol.terminal.activity.JiediDianZuCeLiangActicivity;
import com.patrol.terminal.activity.JueYuanZiLingZhiJianCeActivity;
import com.patrol.terminal.activity.LoginActivity;
import com.patrol.terminal.activity.MonitoringRecordActivity;
import com.patrol.terminal.activity.NewMainActivity;
import com.patrol.terminal.activity.NewPlanActivity;
import com.patrol.terminal.activity.NewTaskActivity;
import com.patrol.terminal.activity.PatrolRecordActivity;
import com.patrol.terminal.activity.SettingActivity;
import com.patrol.terminal.activity.TroubleActivity;
import com.patrol.terminal.activity.XieGanTaQingXieCeWenActivity;
import com.patrol.terminal.adapter.BackLogTaskAdapter;
import com.patrol.terminal.adapter.BackTodoYXAdapter;
import com.patrol.terminal.adapter.PlanFinishRateAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.PlanFinishRateBean;
import com.patrol.terminal.overhaul.OverhaulPlanActivity;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends BaseFragment /*implements IRfid.QueryCallbackListener, IRfid.CallbackListener */ {


    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_dep)
    TextView tvDep;
    @BindView(R.id.tv_job)
    TextView tvJob;
    @BindView(R.id.rl_plan)
    RelativeLayout rlPlan;
    @BindView(R.id.rl_task)
    RelativeLayout rlTask;
    @BindView(R.id.rl_defact)
    RelativeLayout rlDefact;
    @BindView(R.id.rl_trouble)
    RelativeLayout rlTrouble;
    @BindView(R.id.rv_todo)
    RecyclerView rvTodo;
    @BindView(R.id.home_todo_no_data)
    TextView homeTodoNoData;
    @BindView(R.id.ll_backlog)
    RelativeLayout llBacklog;
    @BindView(R.id.rv_task)
    RecyclerView rvTask;
    @BindView(R.id.home_task_no_data)
    TextView homeTaskNoData;
    @BindView(R.id.ll_task)
    RelativeLayout llTask;
    @BindView(R.id.rv_plan_finish_rate)
    RecyclerView rvPlanFinishRate;
    @BindView(R.id.ll_plan_finish_rate)
    LinearLayout llPlanFinishRate;
    @BindView(R.id.scanner_iv)
    ImageView scannerIv;
    @BindView(R.id.one_line)
    TextView oneLine;
    @BindView(R.id.two_line)
    TextView twoLine;
    @BindView(R.id.rv_last_task)
    RecyclerView rvLastTask;
    @BindView(R.id.home_last_task_no_data)
    TextView homeLastTaskNoData;
    @BindView(R.id.ll_last_task)
    RelativeLayout llLastTask;
    @BindView(R.id.home_done_data)
    TextView homeDoneData;
    @BindView(R.id.data_change)
    ImageView dataChange;
    @BindView(R.id.wanchenglv_name)
    TextView wanchenglvNmae;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.rl_todo)
    RelativeLayout rlTodo;
    @BindView(R.id.rl_task_now)
    RelativeLayout rlTaskNow;
    @BindView(R.id.rl_task_history)
    RelativeLayout rlTaskHistory;
    @BindView(R.id.rl_day_plan_finish)
    RelativeLayout rlDayPlanFinish;

    private List<PersonalTaskListBean> backLogData = new ArrayList<>();
    private List<PersonalTaskListBean> taskData = new ArrayList<>();
    private List<PersonalTaskListBean> lastData = new ArrayList<>();
    private List<PlanFinishRateBean> data = new ArrayList<>();
    private List<PlanFinishRateBean> data1 = new ArrayList<>();
    private String status;
    private String jobType;
    private BackTodoYXAdapter adapter;
    private ProgressDialog progressDialog;
    private BackLogTaskAdapter backLogTaskAdapter;
    private String year, month, day, time;
    private int type = 1;
    private PlanFinishRateAdapter planFinishRateAdapter;
    private BackLogTaskAdapter lastTaskAdapter;
    private Disposable refreshTodo;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressDialog = new ProgressDialog();
        return view;
    }

    @Override
    protected void initData() {
        time = DateUatil.getCurrTime();
        inteDate();
        String name = SPUtil.getString(getContext(), Constant.USER, Constant.USERNAME, "");
        String dep = SPUtil.getDepName(getContext());
        String job = SPUtil.getString(getContext(), Constant.USER, Constant.USERJOBNAME, "");
        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        if (!name.isEmpty() && !job.isEmpty()) {
            tvUserName.setText(name);
            tvDep.setText(dep);
            tvJob.setText(job);
        }
        rlPlan.setVisibility(View.VISIBLE);
        //运行班才有任务,检修班是计划
        if (jobType.startsWith("yx")) {
            rlTask.setVisibility(View.VISIBLE);
        }
        //运行班组长和组员进来隐藏计划
        if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
            rlPlan.setVisibility(View.VISIBLE);
            dataChange.setVisibility(View.VISIBLE);
            Random random = new Random();
            data.add(new PlanFinishRateBean("刘海生", random.nextInt(100), random.nextInt(100)));
            data.add(new PlanFinishRateBean("蒋秀珍", random.nextInt(100), random.nextInt(100)));
            data.add(new PlanFinishRateBean("胡作铸", random.nextInt(100), random.nextInt(100)));

            data1.add(new PlanFinishRateBean("刘海生", random.nextInt(100), random.nextInt(100)));
            data1.add(new PlanFinishRateBean("蒋秀珍", random.nextInt(100), random.nextInt(100)));
            data1.add(new PlanFinishRateBean("胡作铸", random.nextInt(100), random.nextInt(100)));
        } else if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
            rlPlan.setVisibility(View.GONE);
            wanchenglvNmae.setText("类别");
            homeDoneData.setText("完成率");
            Random random = new Random();
            data.add(new PlanFinishRateBean("日计划", random.nextInt(100), random.nextInt(100)));
            data.add(new PlanFinishRateBean("周计划", random.nextInt(100), random.nextInt(100)));
        }
        if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED) || jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED) || jobType.contains(Constant.SAFETY_SPECIALIZED)) {
            status = "1,2,3,4,5";
        } else if (jobType.endsWith("_zz") && !jobType.contains("b_")) {

            status = "4,5";
        }
//        refreshTodo = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {
//
//            @Override
//            public void accept(String type) throws Exception {
//                if (type.startsWith("refreshTodo")) {
//                    if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
//                        getYXtodo("2");
//                    } else if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
//                        getYXtodo("1");
//                    } else if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER)) {
//                        getYXtodo("0");
//                    }
//
//                }
//            }
//        });
//        initBackLog();
        initTask();
        initLastTask();
        initPlanFinishRate();
        getPersonalList();
        getLastTask();


        //退出系统快捷方式
        ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtil.clear(getActivity(), Constant.USER);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().setResult(RESULT_OK);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
//            getYXtodo("2");
//        } else if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
//            getYXtodo("1");
//        } else if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER)) {
//            getYXtodo("0");
//        }
    }

    //获取已完成任务
    private void getLastTask() {
        BaseRequest.getInstance().getService()
                .getDepPersonalList(year, month, day, SPUtil.getUserId(getContext()), "3", "5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PersonalTaskListBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<PersonalTaskListBean>> t) throws Exception {
                        lastData = t.getResults();

                        lastTaskAdapter.setNewData(lastData);

                        if (taskData.size() == 0) {
                            homeLastTaskNoData.setVisibility(View.VISIBLE);
                        } else {
                            homeLastTaskNoData.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

    }

    //获取待办
    private void initBackLog() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvTodo.setLayoutManager(manager);

        adapter = new BackTodoYXAdapter(R.layout.item_back_log, backLogData);
        rvTodo.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PersonalTaskListBean todoListBean = backLogData.get(position);
                gotoPersonal(todoListBean);
            }
        });
    }

    //首页跳转
    public void gotoPersonal(PersonalTaskListBean todoListBean) {
        String deal_type = todoListBean.getType_sign();
        String data_id = todoListBean.getId();
        Intent intent = new Intent();
        intent.putExtra("task_id", data_id);
        switch (deal_type) {
            case "1":
            case "2":
            case "7":
            case "11":
                intent.setClass(getContext(), PatrolRecordActivity.class);
                break;
            case "5":
                intent.setClass(getContext(), HongWaiCeWenActivity.class);
                break;
            case "3":
                intent.setClass(getContext(), JiediDianZuCeLiangActicivity.class);
                break;
            case "10":
                intent.setClass(getContext(), JueYuanZiLingZhiJianCeActivity.class);
                break;
            case "6":
                intent.setClass(getContext(), XieGanTaQingXieCeWenActivity.class);
                break;
            case "12":
                intent.setClass(getContext(), MonitoringRecordActivity.class);
                break;
            case "13":
                intent.setClass(getContext(), CheckActivity.class);
                break;
        }
        startActivity(intent);
    }

    //当前任务
    private void initTask() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvTask.setLayoutManager(manager);
        backLogTaskAdapter = new BackLogTaskAdapter(R.layout.item_back_log, taskData);
        rvTask.setAdapter(backLogTaskAdapter);
        backLogTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PersonalTaskListBean bean = taskData.get(position);
                gotoPersonal(bean);
            }
        });
    }

    //历史任务
    private void initLastTask() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvLastTask.setLayoutManager(manager);
        lastTaskAdapter = new BackLogTaskAdapter(R.layout.item_back_log, lastData);
        rvLastTask.setAdapter(this.lastTaskAdapter);
        lastTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PersonalTaskListBean bean = lastData.get(position);
                gotoPersonal(bean);
            }
        });
    }

    //获取个人任务列表
    public void getPersonalList() {

        BaseRequest.getInstance().getService()
                .getDepPersonalList(year, month, day, SPUtil.getUserId(getContext()), "5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PersonalTaskListBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<PersonalTaskListBean>> t) throws Exception {
                        taskData = t.getResults();
                        backLogTaskAdapter.setNewData(taskData);
                        if (taskData.size() == 0) {
                            homeTaskNoData.setVisibility(View.VISIBLE);
                        } else {
                            homeTaskNoData.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

    }

    private void initPlanFinishRate() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvPlanFinishRate.setLayoutManager(manager);


        planFinishRateAdapter = new PlanFinishRateAdapter(R.layout.item_plan_finish_rate, data);
        rvPlanFinishRate.setAdapter(planFinishRateAdapter);
        planFinishRateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }


    @OnClick({R.id.rl_plan, R.id.rl_task, R.id.rl_defact, R.id.rl_trouble, R.id.scanner_iv, R.id.rl_todo, R.id.rl_task_now, R.id.rl_task_history, R.id.rl_day_plan_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_plan:
                //startActivity(new Intent(getActivity(), OverhaulPlanActivity.class));
                Log.w("linmeng", "jobType:" + jobType);
                if (jobType.contains(Constant.RUNNING_SQUAD_LEADER) || jobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)
                        || jobType.contains(Constant.RUN_SUPERVISOR) || jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)) {
                    startActivity(new Intent(getActivity(), NewPlanActivity.class));
                } else if (jobType.contains(Constant.REFURBISHMENT_LEADER) || jobType.contains(Constant.REFURBISHMENT_TEMA_LEADER)
                        || jobType.contains(Constant.REFURBISHMENT_MEMBER)
                        || jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED) || jobType.contains(Constant.SAFETY_SPECIALIZED)
                        || jobType.contains(Constant.REFURBISHMENT_SPECIALIZED) || jobType.contains(Constant.MAINTENANCE_SUPERVISOR)) {      //TODO其他还没加
                    //startActivity(new Intent(getActivity(), OverhaulWeekPlanActivity.class));
                    startActivity(new Intent(getActivity(), OverhaulPlanActivity.class));
                }

                break;
            case R.id.rl_task:
                startActivity(new Intent(getActivity(), NewTaskActivity.class));
                break;
            case R.id.rl_defact:
                startActivity(new Intent(getActivity(), DefectActivity.class));
                break;
            case R.id.rl_trouble:
                startActivity(new Intent(getActivity(), TroubleActivity.class));
                break;
            case R.id.scanner_iv:  //扫一扫
                progressDialog.show(getContext(), false, "正在搜索RFID...");
                //openRFID();
                break;
            case R.id.rl_todo:
                ((NewMainActivity) getActivity()).setFragment(1);
                break;

            case R.id.rl_task_now:
            case R.id.rl_task_history:
                Intent intent = new Intent(getActivity(), NewTaskActivity.class);
                if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)){
                    intent.putExtra("index", 4);
                }else {
                    intent.putExtra("index", 1);
                }

                getActivity().startActivity(intent);
                break;
            case R.id.rl_day_plan_finish:
                Intent intent1 = new Intent(getActivity(), NewPlanActivity.class);
                if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)){
                    intent1.putExtra("index", 5);
                }else {
                    intent1.putExtra("index", 2);
                }
                getActivity().startActivity(intent1);
                break;
        }
    }

//    private void openRFID() {
//        //弹Dialog
//        RFIDManager.getRFIDInstance().searchTag(this);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
            //将扫描出的信息显示出来
            Log.w("linmeng", "scanResult:" + scanResult);
        }
    }


//    @OnClick({R.id.btn_plan, R.id.btn_task, R.id.btn_defact, R.id.btn_trouble})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_plan:
//                startActivity(new Intent(getActivity(), NewPlanActivity.class));
//                break;
//            case R.id.btn_task:
//                startActivity(new Intent(getActivity(), NewTaskActivity.class));
//                break;
//            case R.id.btn_defact:
//                startActivity(new Intent(getActivity(), DefectActivity.class));
//                break;
//            case R.id.btn_trouble:
//                startActivity(new Intent(getActivity(), TroubleActivity.class));
//                break;
//        }
//    }


    public void getYXtodo(String status) {
        BaseRequest.getInstance().getService()
                .getDepPersonalList(year, month, day, SPUtil.getUserId(getContext()), status, "5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PersonalTaskListBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<PersonalTaskListBean>> t) throws Exception {
                        backLogData = t.getResults();
                        if (adapter!=null){
                        adapter.setNewData(backLogData);}
                        if (backLogData.size() == 0) {
                            homeTodoNoData.setVisibility(View.VISIBLE);
                        } else {
                            homeTodoNoData.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

//    @Override
//    public void callback(boolean b, String s, List<String> results) {
//        progressDialog.cancle();
//        getActivity().runOnUiThread(() -> {
//            if (s != null) {
//                Log.w("linmeng", s);
//            }
//            if (results != null) {
//                for (String result : results) {
//                    Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
//                    Log.w("linmeng", result);
//                    Intent intent = new Intent();
//                    intent.setClass(getActivity(), RfidActivity.class);
//                    intent.putExtra("rf_id", result);
//                    getActivity().startActivity(intent);
//                }
//                RFIDManager.getRFIDInstance().stopSearchTag(this);
//            } else {
//                Toast.makeText(getContext(), "未搜索到设备!", Toast.LENGTH_SHORT).show();
//                RFIDManager.getRFIDInstance().stopSearchTag(this);
//            }
//        });
//    }

    //    @Override
//    public void callback(boolean b, int i, String s) {
//        progressDialog.cancle();
//        Log.w("linmeng", "s:" + s);
//    }
    public void inteDate() {
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        day = Integer.parseInt(days[0]) + "";
    }

    @OnClick(R.id.data_change)
    public void onViewClicked() {

        if (type == 1) {
            type = 2;
            planFinishRateAdapter.setNewData(data1);
            homeDoneData.setText("周计划完成率");
        } else if (type == 2) {
            type = 1;
            planFinishRateAdapter.setNewData(data);
            homeDoneData.setText("日计划完成率");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (refreshTodo != null) {
            refreshTodo.dispose();
        }
    }
}
