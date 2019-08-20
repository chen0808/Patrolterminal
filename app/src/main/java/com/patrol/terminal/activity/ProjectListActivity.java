package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.InitiateProjectAdapter;
import com.patrol.terminal.adapter.ProjectBoardAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.InitiateProjectBean;
import com.patrol.terminal.bean.ProjectBoardBean;
import com.patrol.terminal.sqlite.AppDataBase;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.SpaceItemDecoration;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProjectListActivity extends BaseActivity {

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
    @BindView(R.id.rv_project_list)
    RecyclerView rvProjectList;
    private List<InitiateProjectBean> initiateProjectList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        String userName = SPUtil.getUserName(ProjectListActivity.this);
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));

        InitiateProjectBean initiateProjectBean = new InitiateProjectBean();
        initiateProjectBean.setName("定期巡视");
        initiateProjectBean.setCreate_name(userName);
        initiateProjectBean.setProject_no("378529");
        initiateProjectBean.setContent("杆塔倾斜");
        initiateProjectBean.setStart_time(time);
        initiateProjectBean.setStatus(1+"");
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
        titleName.setText("项目列表");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvProjectList.setLayoutManager(manager);
        rvProjectList.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10)));

        InitiateProjectAdapter    adapter = new InitiateProjectAdapter(R.layout.item_initiate_project, initiateProjectList);
        rvProjectList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                InitiateProjectBean projectBoardBean = initiateProjectList.get(position);
                Intent intent=new Intent();
                intent.putExtra("bean",projectBoardBean);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }


}
