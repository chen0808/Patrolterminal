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
import com.patrol.terminal.activity.NewPlanActivity;
import com.patrol.terminal.activity.NewTaskActivity;
import com.patrol.terminal.activity.TroubleActivity;
import com.patrol.terminal.adapter.BackLogAdapter;
import com.patrol.terminal.adapter.BackLogTaskAdapter;
import com.patrol.terminal.adapter.PlanFinishRateAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.OvaTodoBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulZzTaskBean;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.PlanFinishRateBean;
import com.patrol.terminal.overhaul.OverhaulPlanActivity;
import com.patrol.terminal.overhaul.OverhaulWeekPlanDetailActivity;
import com.patrol.terminal.overhaul.OverhaulZzWeekTaskDetailActivity;
import com.patrol.terminal.overhaul.OvhaulHomeOtherTaskAdapter;
import com.patrol.terminal.overhaul.OvhaulHomeZzTaskAdapter;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class JXHomeFragment extends BaseFragment /*implements IRfid.QueryCallbackListener, IRfid.CallbackListener */ {


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
    @BindView(R.id.rv_last_task)
    RecyclerView rvLastTask;
    @BindView(R.id.home_last_task_no_data)
    TextView homeLastTaskNoData;
    @BindView(R.id.ll_last_task)
    RelativeLayout llLastTask;

    private String[] typeList = new String[]{};
    private List<String> types = new ArrayList<>();
    private List<LineTypeBean> result;
    private List<OvaTodoBean> backLogData = new ArrayList<>();
    private List<PersonalTaskListBean> taskData = new ArrayList<>();
    private List<PlanFinishRateBean> data = new ArrayList<>();
    private String status;
    private String jobType;
    private BackLogAdapter adapter;
    private ProgressDialog progressDialog;

    private String year,month;
    private String week;
    private String userId;
    private String sign;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressDialog = new ProgressDialog();
        return view;
    }

    @Override
    protected void initData() {
        String time = SPUtil.getString(getContext(), "date", "overhaulTime", DateUatil.getTime(new Date(System.currentTimeMillis())));
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        week = DateUatil.getWeekNum()+"";


        String name = SPUtil.getString(getContext(), Constant.USER, Constant.USERNAME, "");
        String dep = SPUtil.getDepName(getContext());
        String job = SPUtil.getString(getContext(), Constant.USER, Constant.USERJOBNAME, "");
        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        if (!name.isEmpty() && !job.isEmpty()) {
            tvUserName.setText(name);
            tvDep.setText(dep);
            tvJob.setText(job);
        }

        userId = SPUtil.getUserId(getContext());

        rlPlan.setVisibility(View.VISIBLE);
        rlTask.setVisibility(View.GONE);
        //运行班组长和组员进来隐藏计划
//        if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
//            rlPlan.setVisibility(View.GONE);
//        }

        if (jobType.contains(Constant.REFURBISHMENT_MEMBER) || jobType.contains(Constant.REFURBISHMENT_TEMA_LEADER)) {  //检修班成员只有当前任务和历史任务
            llBacklog.setVisibility(View.GONE);
            llLastTask.setVisibility(View.VISIBLE);
        }else if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED) || jobType.contains(Constant.REFURBISHMENT_LEADER)
                || jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED) || jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)
                || jobType.contains(Constant.SAFETY_SPECIALIZED) || jobType.contains(Constant.MAINTENANCE_SUPERVISOR)) { //检修班长和检修专责,保电专责，验收专责，安全专责只有待办和当前任务 //检修主管只在待办审核月计划
            llBacklog.setVisibility(View.VISIBLE);
            llLastTask.setVisibility(View.GONE);
        }


        if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED) || jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED) || jobType.contains(Constant.SAFETY_SPECIALIZED)) {
            status = "1,2,3,4,5";
        } else if (jobType.endsWith("_zz") && !jobType.contains("b_")) {
            status = "4,5";
        }

        if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED) || jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)) {
            getOverhaulTodo();
        }

        initBackLog();
        initTask();
        initPlanFinishRate();
    }

    private void initBackLog() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvBacklog.setLayoutManager(manager);

        adapter = new BackLogAdapter(R.layout.item_back_log, backLogData);
        rvBacklog.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OvaTodoBean ovaTodoBean = backLogData.get(position);
                Intent intent = new Intent();
                intent.setClass(getContext(), OverhaulWeekPlanDetailActivity.class);
                intent.putExtra("id", ovaTodoBean.getRepair_id());
                startActivity(intent);
            }
        });
    }

    private void initTask() {
        if (jobType.contains(Constant.REFURBISHMENT_LEADER)) {
            sign = "3";
        }else if (jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)){
            sign = "1";
        }else if (jobType.contains(Constant.SAFETY_SPECIALIZED)) {
            sign = "2";
        }else if (jobType.contains(Constant.REFURBISHMENT_MEMBER)) {
            sign = "4";
        }

       if (jobType.equals(Constant.REFURBISHMENT_SPECIALIZED)) {
           getZzWeekList();
       }else {
           getWeekList(sign);
       }
    }

    //检修专责获取周任务列表
    public void getZzWeekList() {
        BaseRequest.getInstance().getService()
                .getOverhaulZzTaskList(year,month,week)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulZzTaskBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulZzTaskBean>> t) throws Exception {
                        List<OverhaulZzTaskBean> results = t.getResults();
                        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                        rvTask.setLayoutManager(manager);
                        OvhaulHomeZzTaskAdapter adapter = new OvhaulHomeZzTaskAdapter(R.layout.item_back_log, results);
                        rvTask.setAdapter(adapter);
                        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), OverhaulZzWeekTaskDetailActivity.class);
                                intent.putExtra("id", results.get(position).getId());
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.e("fff", e.toString());
                    }
                });
    }

    //其他人员获取周任务列表
    public void getWeekList(String sign) {
        Log.w("linmeng", "sign:" + sign);
        BaseRequest.getInstance().getService()
                .getOverhaulTaskList(year,month,week, userId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulMonthBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulMonthBean>> t) throws Exception {
                        List<OverhaulMonthBean> results = t.getResults();
                        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                        rvTask.setLayoutManager(manager);
                        OvhaulHomeOtherTaskAdapter adapter = new OvhaulHomeOtherTaskAdapter(R.layout.item_back_log, results);
                        rvTask.setAdapter(adapter);
                        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), OverhaulWeekPlanDetailActivity.class);
                                intent.putExtra("id", results.get(position).getId());
                                startActivity(intent);
                            }
                        });

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Log.e("fff", e.toString());
                    }
                });
    }

    private void initPlanFinishRate() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvPlanFinishRate.setLayoutManager(manager);
        data.add(new PlanFinishRateBean("刘海生", 50, 60));
        data.add(new PlanFinishRateBean("蒋秀珍", 15, 54));
        data.add(new PlanFinishRateBean("胡作铸", 87, 76));
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


    @Override
    public void onStop() {
        super.onStop();
        backLogData.clear();
        taskData.clear();
        data.clear();
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

    public void getOverhaulTodo() {

        BaseRequest.getInstance().getService()
                .getOverhaulTodo(SPUtil.getUserId(getContext()), status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OvaTodoBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<OvaTodoBean>> t) throws Exception {

                        backLogData = t.getResults();
                        if (backLogData.size() == 0) {
                            homeTodoNoData.setVisibility(View.VISIBLE);
                        } else {
                            homeTodoNoData.setVisibility(View.GONE);
                            if (backLogData.size() > 3) {
                                backLogData = backLogData.subList(0, 3);
                            }
                            adapter.setNewData(backLogData);
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
}
