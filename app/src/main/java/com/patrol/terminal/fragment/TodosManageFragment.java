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
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DayOfWeekBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.overhaul.OverhaulWeekPlanDetailActivity;
import com.patrol.terminal.overhaul.OverhaulWeekTaskAdapter;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;

import java.util.ArrayList;
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
public class TodosManageFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {
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
    @BindView(R.id.to_do_rl)
    RelativeLayout toDoRl;
    @BindView(R.id.frag_todo_rv)
    RecyclerView fragTodoRv;
    @BindView(R.id.frag_todo_ref)
    SwipeRefreshLayout fragTodoRef;
    private Activity mActivity;
    private OverhaulWeekTaskAdapter toDoManageAdapter;
    private static final int IS_TODO_PAGE = 0;
    private static final int IS_DONE_PAGE = 1;
    private int isTodoPage = IS_TODO_PAGE;
    private String status;
    private List<OverhaulMonthBean> results = new ArrayList<>();
    private String jobType;
    private String time, year, month;
    private String ele_user_id;
    private String check_user_id;
    private String safe_user_id;
    private Disposable subscribe;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.to_dos_manage_fragment, container, false);
        return view;
    }

    @Override
    protected void initData() {
        mActivity = getActivity();
        titleBack.setVisibility(View.GONE);
        titleName.setText("待办管理");
        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        String userId = SPUtil.getString(getContext(), Constant.USER, Constant.USERID, "");

        if (jobType.contains(Constant.POWER_CONSERVATION_SPECIALIZED)) {
            ele_user_id = userId;
        } else if (jobType.contains(Constant.ACCEPTANCE_CHECK_SPECIALIZED)) {
            check_user_id = userId;
        } else if (jobType.contains(Constant.SAFETY_SPECIALIZED)) {
            safe_user_id = userId;
        }

        time = DateUatil.getCurMonth();

        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        fragTodoRv.setLayoutManager(manager);

        toDoManageAdapter = new OverhaulWeekTaskAdapter(R.layout.fragment_overhaul_week_item, results, 2);
        fragTodoRv.setAdapter(toDoManageAdapter);
        toDoManageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        fragTodoRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (jobType.contains(Constant.REFURBISHMENT_LEADER)) {
                    getBzAgentsTodo("4");
                } else {
                    getOverhaulTodo();
                }
            }
        });

        toDoManageAdapter.setOnItemClickListener(this);

        Log.w("linmeng", "toDoManageAdapter jobType:" + jobType);
        if (jobType.contains(Constant.REFURBISHMENT_LEADER)) {
            getBzAgentsTodo("4");
        } else {
            getOverhaulTodo();
        }


        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.startsWith("todoUpdate")) {
                    if (isTodoPage == IS_TODO_PAGE) {
                        getBzAgentsTodo("4");
                    }else if (isTodoPage == IS_DONE_PAGE) {
                        getBzAgentsTodo("5,6");
                    }
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    @OnClick({R.id.to_do_tv, R.id.done_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.to_do_tv:
                toDoTv.setBackgroundResource(R.drawable.date_white_bg);
                doneTv.setBackgroundResource(R.drawable.date_grey_bg);
                toDoTv.setTextColor(getResources().getColor(R.color.date_color));
                doneTv.setTextColor(getResources().getColor(R.color.white));

                getBzAgentsTodo("4");
                isTodoPage = IS_TODO_PAGE;
                toDoManageAdapter.setIsTodoType(isTodoPage);
                //toDoManageAdapter.setNewData(results);

                break;

            case R.id.done_tv:
                toDoTv.setBackgroundResource(R.drawable.date_grey_bg);
                doneTv.setBackgroundResource(R.drawable.date_white_bg);
                toDoTv.setTextColor(getResources().getColor(R.color.white));
                doneTv.setTextColor(getResources().getColor(R.color.date_color));

                getBzAgentsTodo("5,6");
                isTodoPage = IS_DONE_PAGE;
                toDoManageAdapter.setIsTodoType(isTodoPage);
                //toDoManageAdapter.setNewData(results);

                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent();
        intent.setClass(getContext(), OverhaulWeekPlanDetailActivity.class);
        Bundle bundle = new Bundle();
        if (results.get(position) != null) {
            bundle.putString("id", results.get(position).getId());
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getBzAgentsTodo(String task_status) {
        results.clear();
        BaseRequest.getInstance().getService()
                .getBzAgents(task_status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulMonthBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulMonthBean>> t) throws Exception {
                        fragTodoRef.setRefreshing(false);
                        List<OverhaulMonthBean> overhaulMonthBeans = t.getResults();
                        for (int i = 0; i < overhaulMonthBeans.size(); i++) {
                            //if ("2".equals(overhaulMonthBeans.get(i).getMonth_audit_status())) {   //只显示审核通过的周计划
                            results.add(overhaulMonthBeans.get(i));
                            //}
                        }
                        toDoManageAdapter.setNewData(results);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        fragTodoRef.setRefreshing(false);
                    }
                });

    }

    public void getOverhaulTodo() {
        //安全,验收,保电需要传userId
//        BaseRequest.getInstance().getService()
//                .getOverhaulPlanList(year,month,null, "2", null,ele_user_id,check_user_id,safe_user_id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<OverhaulYearBean>>(getContext()) {
//
//                    @Override
//                    protected void onSuccees(BaseResult<List<OverhaulYearBean>> t) throws Exception {
//                        fragTodoRef.setRefreshing(false);
//                        List<OverhaulYearBean> overhaulYearBeans = t.getResults();
//                        for (int i = 0; i < overhaulYearBeans.size(); i++) {
//                            if ("2".equals(overhaulYearBeans.get(i).getMonth_audit_status())) {   //只显示审核通过的周计划
//                                results.add(overhaulYearBeans.get(i));
//                            }
//                        }
//                        toDoManageAdapter.setNewData(results);
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        fragTodoRef.setRefreshing(false);
//                    }
//                });

    }

}
