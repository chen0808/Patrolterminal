package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.NextDayPlanAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.DayListBean;

import java.text.DecimalFormat;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NextDayPlanActivity extends BaseActivity {


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
    @BindView(R.id.next_plan_rv)
    RecyclerView nextPlanRv;
    @BindView(R.id.plan_total_title)
    TextView planTotalTitle;
    @BindView(R.id.month_line_total)
    TextView monthLineTotal;
    @BindView(R.id.month_line_kilo_total)
    TextView monthLineKiloTotal;
    @BindView(R.id.month_line_110kv_num)
    TextView monthLine110kvNum;
    @BindView(R.id.month_line_110kv_kilo)
    TextView monthLine110kvKilo;
    @BindView(R.id.month_line_35kv_num)
    TextView monthLine35kvNum;
    @BindView(R.id.month_line_35kv_kilo)
    TextView monthLine35kvKilo;
    private String state;
    private NextDayPlanAdapter monthPlanAdapter;
    private List<DayListBean> list;
    private int year;
    private int month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_month_plan);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        planTotalTitle.setText("明日工作计划汇总");
        list = (List<DayListBean>) getIntent().getSerializableExtra("list");

        int num_total = getIntent().getIntExtra("num_total", 0);
        double kilo_total = getIntent().getDoubleExtra("kilo_total", 0);
        int num_110kv = getIntent().getIntExtra("110kv_num", 0);
        double kilo_110kv = getIntent().getDoubleExtra("110kv_kolo", 0);
        int num_35kv = getIntent().getIntExtra("35kv_num", 0);
        double kilo_35kv = getIntent().getDoubleExtra("35kv_kolo", 0);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        monthLineTotal.setText("杆段总数 : " + num_total + "条");
        monthLineKiloTotal.setText("总公里数 : " + decimalFormat.format(kilo_total) + "公里");
        monthLine110kvNum.setText("110kv线路总数 : " + num_110kv + "条");
        monthLine110kvKilo.setText("公里数 : " + decimalFormat.format(kilo_110kv) + "公里");
        monthLine35kvNum.setText("35kv线路总数 : " + num_35kv + "条");
        monthLine35kvKilo.setText("公里数 : " + decimalFormat.format(kilo_35kv) + "公里");
        year = getIntent().getIntExtra("year", 2019);
        month = getIntent().getIntExtra("month", 23);
        day = getIntent().getIntExtra("day", 23);
        titleName.setText(year+"年"+month+"月"+day+"日计划");
        DayListBean weekListBean = list.get(0);
        state = weekListBean.getAudit_status();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        nextPlanRv.setLayoutManager(manager);
        monthPlanAdapter = new NextDayPlanAdapter(R.layout.fragment_plan_item, list, state, "");
        nextPlanRv.setAdapter(monthPlanAdapter);
        adapterClick();
    }

    public void adapterClick() {

        monthPlanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent=new Intent();
                intent.setClass(NextDayPlanActivity.this, DayPlanDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("bean",list.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });
    }

    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }



}
