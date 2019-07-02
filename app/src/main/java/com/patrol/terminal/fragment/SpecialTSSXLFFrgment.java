package com.patrol.terminal.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.adapter.TssxEditAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.bean.TSSXBean;
import com.patrol.terminal.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 特殊属性
 * 六防
 */
public class SpecialTSSXLFFrgment extends BaseFragment {

    @BindView(R.id.item_tssx_lv)
    ListView item_tssx_lv;

    private View view;

    private TssxEditAdapter editAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_xs_tssx_sankua, null);
        return view;
    }

    @Override
    protected void initData() {

        editAdapter =new TssxEditAdapter(getActivity());
        item_tssx_lv.setAdapter(editAdapter);

    }


    public void setTssxAdapter(List<TSSXBean> list, CursorAdapter cursorAdapter, Cursor cursor)
    {
        editAdapter.setData(list, cursorAdapter, cursor);
    }

}
