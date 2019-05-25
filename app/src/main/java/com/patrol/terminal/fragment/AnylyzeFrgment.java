package com.patrol.terminal.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseFragment;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AnylyzeFrgment extends BaseFragment implements AdapterView.OnItemClickListener {


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
    @BindView(R.id.nice_spinner)
    NiceSpinner niceSpinner;
    @BindView(R.id.pie_chart)
    PieChart pieChart;
    private List<String> spinnerList = new ArrayList<String>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        return view;
    }

    @Override
    protected void initData() {
        titleBack.setVisibility(View.GONE);
        titleName.setText("分析");
        spinnerList.clear();
        spinnerList.add("年");
        spinnerList.add("月");
        spinnerList.add("周");
        spinnerList.add("日");
        niceSpinner.attachDataSource(spinnerList);
        niceSpinner.addOnItemClickListener(this);

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(33.3f, "未完成"));
        entries.add(new PieEntry(66.7f, "已完成"));

        PieDataSet set = new PieDataSet(entries, "");
        int[] colors = {Color.parseColor("#BCDEDD"), Color.parseColor("#F7F7C5")};
        set.setColors(colors);
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:
                break;
        }
    }
}
