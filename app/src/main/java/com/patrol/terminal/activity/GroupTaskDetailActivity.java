package com.patrol.terminal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.WeekPlanDetailAdapter;
import com.patrol.terminal.bean.DefectBean;
import com.patrol.terminal.bean.GroupTaskBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupTaskDetailActivity extends Activity {

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
    @BindView(R.id.tv_table_name)
    TextView tvTableName;
    @BindView(R.id.tv_line_no)
    TextView tvLineNo;
    @BindView(R.id.tv_line_name)
    TextView tvLineName;
    @BindView(R.id.tv_line_date)
    TextView tvLineDate;

    @BindView(R.id.month_plan_detail_rc)
    RecyclerView monthPlanDetailRc;
    @BindView(R.id.tv_line_type)
    TextView tvLineType;
    @BindView(R.id.tv_line_tower)
    TextView tvLineTower;


    private List<DefectBean> typeList = new ArrayList<>();
    private String year, month, id;
    private WeekPlanDetailAdapter adapter;
    private List<GroupTaskBean> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_plan_detail);
        ButterKnife.bind(this);
        initView();
        GroupTaskBean bean = (  GroupTaskBean)getIntent().getParcelableExtra("GroupTaskBean");
       getGroupList(bean);
    }


    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    private void initView() {
        titleName.setText("小组任务详情");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        monthPlanDetailRc.setLayoutManager(manager);
        adapter = new WeekPlanDetailAdapter(R.layout.item_plan_detail, typeList);
        monthPlanDetailRc.setAdapter(adapter);
    }
    //获取月计划列表
    public void getGroupList(GroupTaskBean bean) {


                tvTableName.setText(bean.getYear()+"年"+ bean.getMonth()+"月"+bean.getDay()+"日班组任务");
                tvLineName.setText("线路名称 : " + bean.getLine_name());
                tvLineNo.setText("班  组 : "+ bean.getDep_name() );
                tvLineDate.setText("小组负责人 " + bean.getDuty_user_name());

                if (bean.getName() == null) {
                    tvLineTower.setText("杆  段 : 无");
                } else {

                    tvLineTower.setText("杆  段 : " +bean.getName());
                }

            DefectBean planTypeBean = new DefectBean();
            planTypeBean.setContent(bean.getLine_name() + bean.getType_name() + "任务");
            planTypeBean.setType(0);
            typeList.add(planTypeBean);
        }


}
