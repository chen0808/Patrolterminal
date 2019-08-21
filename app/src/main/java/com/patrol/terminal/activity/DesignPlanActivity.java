package com.patrol.terminal.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.InitiateProjectAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.InitiateProjectBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.SpaceItemDecoration;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：陈飞
 * 时间：2019/08/15 14:58
 */
public class DesignPlanActivity extends BaseActivity {
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
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_qx_content)
    AutoCompleteTextView tvQxContent;
    @BindView(R.id.search_delete)
    ImageView searchDelete;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;

    private int logType = 0;
    private Context mContext;
    private InitiateProjectAdapter initiateProjectAdapter;
    private List<InitiateProjectBean> initiateProjectList = new ArrayList<>();
    private int pageNum = 1;
    private int count = 10;
    private String search_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_plan);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        titleName.setText("设计计划");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingIv.setImageResource(R.mipmap.add_white);
        titleSettingTv.setText("");

        String userName = SPUtil.getUserName(this);
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));

        InitiateProjectBean initiateProjectBean = new InitiateProjectBean();
        initiateProjectBean.setName("定期巡视");
        initiateProjectBean.setCreate_name(userName);
        initiateProjectBean.setProject_no("378529");
        initiateProjectBean.setContent("杆塔倾斜");
        initiateProjectBean.setStart_time(time);
        initiateProjectBean.setStatus(1 + "");
        initiateProjectList.add(initiateProjectBean);

        initiateProjectBean = new InitiateProjectBean();
        initiateProjectBean.setName("绝缘子检测");
        initiateProjectBean.setCreate_name(userName);
        initiateProjectBean.setProject_no("378457");
        initiateProjectBean.setContent("正常");
        initiateProjectBean.setStart_time(time);
        initiateProjectBean.setStatus(2 + "");
        initiateProjectList.add(initiateProjectBean);

        initiateProjectBean = new InitiateProjectBean();
        initiateProjectBean.setName("电阻检测");
        initiateProjectBean.setCreate_name(userName);
        initiateProjectBean.setProject_no("378555");
        initiateProjectBean.setContent("需要更换");
        initiateProjectBean.setStart_time(time);
        initiateProjectBean.setStatus(3 + "");
        initiateProjectList.add(initiateProjectBean);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        planRv.setLayoutManager(manager);
        planRv.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10)));

        initiateProjectAdapter = new InitiateProjectAdapter(R.layout.item_initiate_project, logType);
        planRv.setAdapter(initiateProjectAdapter);
        planRv.useDefaultLoadMore();
        planRv.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                getProjectList(search_name);
            }
        });

        initiateProjectAdapter.setNewData(initiateProjectList);
    }

    private void getProjectList(String search_name) {

    }

    @OnClick({R.id.title_back, R.id.iv_search, R.id.search_delete, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.iv_search:
                break;
            case R.id.search_delete:
                break;
            case R.id.title_setting:
                Intent intent = new Intent(this, DesignPlanAddActivity.class);
                startActivity(intent);
                break;
        }
    }
}
