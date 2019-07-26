package com.patrol.terminal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddGroupTaskActivity;
import com.patrol.terminal.activity.DepOFTaskActivity;
import com.patrol.terminal.activity.GroupTaskDetailActivity;
import com.patrol.terminal.adapter.DepTaskAdapter;
import com.patrol.terminal.adapter.GroupTaskAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DepInfoBean;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

//班组任务
public class DepTaskFrgment extends BaseFragment {

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

    private TimePickerView pvTime;
    private List<DepInfoBean> result = new ArrayList<>();
    private String time;
    private String year;
    private String month;
    private String day;
    private String userId, duty_user_id;
    private String depId;
    private String jobType;
    private boolean isRefresh = true;
    private Disposable subscribe;
    private DepTaskAdapter depTaskAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dep_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        planCreate.setVisibility(View.GONE);
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate();
        taskDate.setText(time);
        taskTitle.setText("班组列表");
        taskAdd.setVisibility(View.GONE);
        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        depTaskAdapter = new DepTaskAdapter(R.layout.fragment_dep_item, result,time);
        planRv.setAdapter(depTaskAdapter);
        depTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent();
                DepInfoBean bean = result.get(position);
                intent.setClass(getContext(), DepOFTaskActivity.class);
                intent.putExtra("time", time);
                intent.putExtra("depid", bean.getId());
                startActivity(intent);
            }
        });

        mRefrsh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDepInfo();
            }
        });

        getDepInfo();
    }


    public void inteDate() {
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        day = Integer.parseInt(days[0]) + "";
    }

    //获取班组信息
    public void getDepInfo() {
        ProgressDialog.show(getContext(),true,"正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getDepInfo(year,month,day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DepInfoBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<DepInfoBean>> t) throws Exception {

                        result = t.getResults();

                        RxRefreshEvent.publish("refreshGroupNum@"+result.size());
                        depTaskAdapter.setNewData(result);
                        ProgressDialog.cancle();
                        mRefrsh.setRefreshing(false);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        mRefrsh.setRefreshing(false);
                    }
                });
    }





}
