package com.patrol.terminal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.DefectActivity;
import com.patrol.terminal.activity.DefectTabActivity;
import com.patrol.terminal.activity.HongWaiCeWenActivity;
import com.patrol.terminal.activity.JiediDianZuCeLiangActicivity;
import com.patrol.terminal.activity.JueYuanZiLingZhiJianCeActivity;
import com.patrol.terminal.activity.NewPlanActivity;
import com.patrol.terminal.activity.NewTaskActivity;
import com.patrol.terminal.activity.PatrolRecordActivity;
import com.patrol.terminal.activity.PersonalTaskDetailActivity;
import com.patrol.terminal.activity.TroubleActivity;
import com.patrol.terminal.activity.TroubleTabActivity;
import com.patrol.terminal.activity.XieGanTaQingXieCeWenActivity;
import com.patrol.terminal.adapter.BackLogAdapter;
import com.patrol.terminal.adapter.BackLogTaskAdapter;
import com.patrol.terminal.adapter.BackTaskYXAdapter;
import com.patrol.terminal.adapter.PlanFinishRateAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.OvaTodoBean;
import com.patrol.terminal.bean.PatrolListBean;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.PlanFinishRateBean;
import com.patrol.terminal.overhaul.OverhaulPlanActivity;
import com.patrol.terminal.overhaul.OverhaulWeekPlanDetailActivity;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class ZyHomeFragment extends BaseFragment /*implements IRfid.QueryCallbackListener, IRfid.CallbackListener */{


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
    @BindView(R.id.rv_backlog)
    RecyclerView rvBacklog;
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

    private String[] typeList = new String[]{};
    private List<String> types = new ArrayList<>();
    private List<PersonalTaskListBean> result;
    private List<PersonalTaskListBean> backLogData = new ArrayList<>();
    private List<PersonalTaskListBean> taskData = new ArrayList<>();
    private List<PlanFinishRateBean> data = new ArrayList<>();
    private String status;
    private String jobType;
    private BackTaskYXAdapter adapter;
    private ProgressDialog progressDialog;
    private String year,month,day,time;
    private BackLogTaskAdapter backLogTaskAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_zy, container, false);
        progressDialog = new ProgressDialog();
        return view;
    }

    @Override
    protected void initData() {
         time= DateUatil.getCurrTime();
        inteDate();
        rlPlan.setVisibility(View.GONE);
        String name = SPUtil.getString(getContext(), Constant.USER, Constant.USERNAME, "");
        String dep = SPUtil.getDepName(getContext());
        String job = SPUtil.getString(getContext(), Constant.USER, Constant.USERJOBNAME, "");
        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        if (!name.isEmpty() && !job.isEmpty()) {
            tvUserName.setText(name);
            tvDep.setText(dep);
            tvJob.setText(job);
        }
        initBackLog();
        initTask();
        initPlanFinishRate();

        getPersonalList();
    }

    private void initBackLog() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvBacklog.setLayoutManager(manager);

        adapter = new BackTaskYXAdapter(R.layout.item_back_log, backLogData);
        rvBacklog.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PersonalTaskListBean todoListBean = backLogData.get(position);
                String deal_type = todoListBean.getType_sign();
                String data_id = todoListBean.getId();
                Intent intent = new Intent();
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

                }
                startActivity(intent);
            }
        });
    }

    private void initTask() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvTask.setLayoutManager(manager);
        backLogTaskAdapter = new BackLogTaskAdapter(R.layout.item_back_log, taskData);
        rvTask.setAdapter(backLogTaskAdapter);
        backLogTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PersonalTaskListBean todoListBean = taskData.get(position);
                String deal_type = todoListBean.getType_sign();
                String data_id = todoListBean.getId();
                Intent intent = new Intent();
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

                }
                startActivity(intent);
            }
        });
    }

    private void initPlanFinishRate() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvPlanFinishRate.setLayoutManager(manager);
        data.add(new PlanFinishRateBean("日计划", 50, 60));
        data.add(new PlanFinishRateBean("周计划", 15, 54));
        PlanFinishRateAdapter adapter = new PlanFinishRateAdapter(R.layout.item_plan_finish_rate, data);
        rvPlanFinishRate.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), NewPlanActivity.class);
                startActivity(intent);
            }
        });
    }

    //获取个人任务列表
    public void getPersonalList() {

        ProgressDialog.show(getContext(),false,"正在加载。。。");
        BaseRequest.getInstance().getService()
                .getPersonalList(year,month, day, SPUtil.getUserId(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PersonalTaskListBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<PersonalTaskListBean>> t) throws Exception {
                        result = t.getResults();
                        for (int i = 0; i < result.size(); i++) {
                            PersonalTaskListBean personalTaskListBean = result.get(i);
                            if ("0".equals(personalTaskListBean.getDone_status())){
                                backLogData.add(personalTaskListBean);
                            }else  if ("1".equals(personalTaskListBean.getDone_status())){
                                taskData.add(personalTaskListBean);
                            }
                        }
                        adapter.setNewData(backLogData);
                        backLogTaskAdapter.setNewData(taskData);
                        if (backLogData.size()==0){
                            homeTodoNoData.setVisibility(View.VISIBLE);
                        }else {
                            homeTodoNoData.setVisibility(View.GONE);
                        }
                        if (taskData.size()==0){
                            homeTaskNoData.setVisibility(View.VISIBLE);
                        }else {
                            homeTaskNoData.setVisibility(View.GONE);
                        }
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }



    @OnClick({R.id.rl_plan, R.id.rl_task, R.id.rl_defact, R.id.rl_trouble, R.id.scanner_iv})
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
                        ||jobType.contains(Constant.REFURBISHMENT_SPECIALIZED) || jobType.contains(Constant.MAINTENANCE_SUPERVISOR)) {      //TODO其他还没加
                    //startActivity(new Intent(getActivity(), OverhaulWeekPlanActivity.class));
                    startActivity(new Intent(getActivity(), OverhaulPlanActivity.class));
                }

                break;
            case R.id.rl_task:
                startActivity(new Intent(getActivity(), NewTaskActivity.class));
                break;
            case R.id.rl_defact:
//                startActivity(new Intent(getActivity(), DefectActivity.class));
                startActivity(new Intent(getActivity(), DefectTabActivity.class));
                break;
            case R.id.rl_trouble:
                startActivity(new Intent(getActivity(), TroubleTabActivity.class));
                break;

            case R.id.scanner_iv:  //扫一扫
                progressDialog.show(getContext(), false, "正在搜索RFID...");
                //openRFID();


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




    public void inteDate(){
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0])+"";
        year =years[0];
        day=Integer.parseInt(days[0])+"";
    }
}
