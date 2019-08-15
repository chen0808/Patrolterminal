package com.patrol.terminal.overhaul;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.InitiateProjectAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.InitiateProjectBean;
import com.patrol.terminal.utils.Constant;
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

//项目立项
public class InitiateProjectActivity extends BaseActivity {

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
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;

    private int logType = 0;
    private Context mContext;
    private InitiateProjectAdapter initiateProjectAdapter;
    private List<InitiateProjectBean> initiateProjectList = new ArrayList<>();
    private int pageNum = 1;
    private int count = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_project);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        mContext = this;

        titleName.setText("项目");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingIv.setImageResource(R.mipmap.add_white);
        titleSettingTv.setText("");

        String userName = SPUtil.getUserName(this);
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));

        InitiateProjectBean initiateProjectBean = new InitiateProjectBean();
        initiateProjectBean.setProject_name("定期巡视");
        initiateProjectBean.setCreate_name(userName);
        initiateProjectBean.setCode("378529");
        initiateProjectBean.setContent("杆塔倾斜");
        initiateProjectBean.setTime(time);
        initiateProjectBean.setStatus(1);
        initiateProjectList.add(initiateProjectBean);

        initiateProjectBean = new InitiateProjectBean();
        initiateProjectBean.setProject_name("绝缘子检测");
        initiateProjectBean.setCreate_name(userName);
        initiateProjectBean.setCode("378457");
        initiateProjectBean.setContent("正常");
        initiateProjectBean.setTime(time);
        initiateProjectBean.setStatus(2);
        initiateProjectList.add(initiateProjectBean);

        initiateProjectBean = new InitiateProjectBean();
        initiateProjectBean.setProject_name("电阻检测");
        initiateProjectBean.setCreate_name(userName);
        initiateProjectBean.setCode("378555");
        initiateProjectBean.setContent("需要更换");
        initiateProjectBean.setTime(time);
        initiateProjectBean.setStatus(3);
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
            }
        });

        initiateProjectAdapter.setNewData(initiateProjectList);
    }

    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                intent = new Intent(this, InitiateProjectAddActivity.class);
                intent.putExtra("logType", logType);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_ADDRESS_BOOK) {
            }
        }
    }
}
