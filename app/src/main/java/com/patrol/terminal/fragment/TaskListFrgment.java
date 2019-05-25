package com.patrol.terminal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.SignActivity;
import com.patrol.terminal.adapter.TaskListAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.bean.MonthPlanBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TaskListFrgment extends BaseFragment {

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
    @BindView(R.id.task_no_distribute)
    RadioButton taskNoDistribute;
    @BindView(R.id.task_no_finish)
    RadioButton taskNoFinish;
    @BindView(R.id.task_finish)
    RadioButton taskFinish;
    @BindView(R.id.task_rg)
    RadioGroup taskRg;
    @BindView(R.id.task_rv)
    RecyclerView taskRv;
    @BindView(R.id.task_list_ref)
    SwipeRefreshLayout taskListRef;
    @BindView(R.id.btn)
    Button btn;
    private TaskListAdapter monthPlanAdapter;
    private List<MonthPlanBean> result = new ArrayList<>();
    private int type = 1;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        titleName.setText("任务列表");
        titleBack.setVisibility(View.GONE);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("2");
        strings.add("2");
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        taskRv.setLayoutManager(manager);
        monthPlanAdapter = new TaskListAdapter(R.layout.fragment_plan_item, result, type);
        taskRv.setAdapter(monthPlanAdapter);
        monthPlanAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        taskRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.task_no_distribute:
                        type = 1;
                        break;
                    case R.id.task_no_finish:
                        type = 2;
                        break;
                    case R.id.task_finish:
                        type = 3;
                        break;
                }
            }
        });
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), SignActivity.class));
    }
}
