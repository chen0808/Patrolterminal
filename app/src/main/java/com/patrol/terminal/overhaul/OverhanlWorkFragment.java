package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.patrol.terminal.R;
import com.patrol.terminal.activity.ControlCardActivity;
import com.patrol.terminal.activity.FirstWTicketActivity;
import com.patrol.terminal.activity.FourWTicketActivity;
import com.patrol.terminal.activity.SecondWTicketActivity;
import com.patrol.terminal.activity.ThirdWTicketActivity;
import com.patrol.terminal.adapter.GridViewAdapter5;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.bean.AllControlCarBean;
import com.patrol.terminal.bean.ControlCardBean;
import com.patrol.terminal.bean.MapUserInfo;
import com.patrol.terminal.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OverhanlWorkFragment extends BaseFragment {

    private GridViewAdapter5 weekAdapter;
    private List<MapUserInfo> results = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overhaul_work, container, false);
        return view;
    }

    @Override
    protected void initData() {

    }


}
