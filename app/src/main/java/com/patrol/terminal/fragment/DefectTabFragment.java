package com.patrol.terminal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.bean.PatrolContentBean;

import java.util.List;

import butterknife.BindView;

public class DefectTabFragment extends BaseFragment {

    private final List<PatrolContentBean.ValueBean> data;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public DefectTabFragment(List<PatrolContentBean.ValueBean> value) {
        data = value;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
    }

    @Override
    protected void initData() {
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(new DefectTabAdapter(R.layout.item_patrol_content_2,data));
    }
}
