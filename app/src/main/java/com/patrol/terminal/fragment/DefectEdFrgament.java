package com.patrol.terminal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectEdAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.bean.DefectDetailBean;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DefectEdFrgament extends BaseFragment {
    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    private DefectEdAdapter groupTaskAdapter;
    private List<DefectDetailBean> result = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        taskTitle.setText("已完成");

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        for (int i = 0; i < 5; i++) {
            result.add(i, new DefectDetailBean("461faasdgarea466", "巡视" + i + "号杆塔", String.valueOf(i < 3 ? 0 : 1), "巡视类型", "2019-04-15",
                    "备注:备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注",
                    "检测人", "工作负责人", "工作班组", "是否确认", "确认审核员id", "杆塔名"));
        }
        groupTaskAdapter = new DefectEdAdapter(R.layout.fragment_defect_item, result);
        planRv.setAdapter(groupTaskAdapter);
    }
}
