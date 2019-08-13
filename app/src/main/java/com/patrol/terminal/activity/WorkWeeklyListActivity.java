package com.patrol.terminal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.EngineeringBriefListAdapter;
import com.patrol.terminal.adapter.WorkWeeklyListAdapter;
import com.patrol.terminal.bean.LocalGcjbBean;
import com.patrol.terminal.bean.LocalWorkWeeklyBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.widget.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 工作周报  列表
 */
public class WorkWeeklyListActivity extends AppCompatActivity {

    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_tv)
    TextView title_setting_tv;
    @BindView(R.id.title_setting)
    RelativeLayout title_setting;

    @BindView(R.id.gclb_lsit)
    RecyclerView gclb_lsit;

    private List<LocalWorkWeeklyBean> workList = new ArrayList<>();
    private WorkWeeklyListAdapter adapter;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineering_brief_list);
        ButterKnife.bind(this);

        titleName.setText("工作周报");

        title_setting_tv.setText("添加");
        title_setting.setVisibility(View.VISIBLE);

        initView();
    }


    public void initView(){
        workList.clear();
        workList.addAll(LocalWorkWeeklyBean.getAllLsit());

        gclb_lsit.setLayoutManager(new LinearLayoutManager(this));
        gclb_lsit.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10)));
        adapter = new WorkWeeklyListAdapter(R.layout.item_workweekly_list,workList);
        gclb_lsit.setAdapter(adapter);

    }


    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                Intent intent = new Intent();
                intent.setClass(this,WorkWeeklyAddActivity.class);
                startActivityForResult(intent,Constant.GCJB_ADD);

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Constant.GCJB_ADD) {
            initView();
        }
    }
}
