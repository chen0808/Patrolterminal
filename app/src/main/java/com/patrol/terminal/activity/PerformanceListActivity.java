package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.PerformanceAdapter;
import com.patrol.terminal.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PerformanceListActivity extends BaseActivity {
    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.rv_performance)
    RecyclerView rvPerformance;
    @BindView(R.id.bar_chart)
    BarChart barChart;
    private Integer[] data = {43, 38, 48, 23, 86, 46, 86, 13, 57, 15, 37, 10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_list);
        ButterKnife.bind(this);
        titleName.setText("历史绩效表");
        initList();
        initBarChart();
    }

    private void initList() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvPerformance.setLayoutManager(manager);
        rvPerformance.setAdapter(new PerformanceAdapter(R.layout.item_performance, Arrays.asList(data)));
    }

    private void initBarChart() {
        setBarChartData();
    }

    private void setBarChartData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        //在这里设置自己的数据源,BarEntry 只接收float的参数，
        //图形横纵坐标默认为float形式，如果想展示文字形式，需要自定义适配器，
        for (int i = 0; i < data.length; i++) {
            yVals1.add(new BarEntry(i, data[i]));
        }
        BarDataSet set1;
        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "2019年历史绩效");
            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
//            data.setValueTypeface(mTfLight);//可以去掉，没什么用
            data.setBarWidth(0.9f);
            barChart.setData(data);
        }
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
