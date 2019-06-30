package com.patrol.terminal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.adapter.TssxEditAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.bean.TSSXBean;
import com.patrol.terminal.widget.NoScrollViewPager;
import com.patrol.terminal.widget.SankuaEditView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 特殊属性
 * 三跨
 */
public class SpecialTSSXSKFrgment extends BaseFragment {



    ListView item_tssx_lv;

    private View view;

    private TssxEditAdapter editAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_xs_tssx_sankua, null);
        item_tssx_lv = view.findViewById(R.id.item_tssx_lv);
        return view;
    }

    @Override
    protected void initData() {



    }


    public void setTssxAdapter(List<TSSXBean> list)
    {
        if(editAdapter == null){
            editAdapter =new TssxEditAdapter(getActivity());
            item_tssx_lv.setAdapter(editAdapter);
        }

        editAdapter.setData(list);

    }



}
