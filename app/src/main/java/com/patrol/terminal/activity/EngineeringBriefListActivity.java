package com.patrol.terminal.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.EngineeringBriefListAdapter;
import com.patrol.terminal.adapter.TssxPhotoAdapter;
import com.patrol.terminal.bean.LocalGcjbBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.SpaceItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 工程简报 简报列表
 */
public class EngineeringBriefListActivity extends AppCompatActivity {

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

    private List<LocalGcjbBean> gcjbList = new ArrayList<>();
    private EngineeringBriefListAdapter adapter;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineering_brief_list);
        ButterKnife.bind(this);

        type = getIntent().getStringExtra(Constant.GCJB_TYPE_STR);
        if (type.equals(Constant.GCJB_YZF_STR)) {
            titleName.setText("业主方简报");
        } else if (type.equals(Constant.GCJB_JLF_STR)) {
            titleName.setText("监理方简报");
        } else if (type.equals(Constant.GCJB_SGF_STR) || type.equals(Constant.GCJB_ADD_STR)) {
            titleName.setText("施工方简报");
        }

        title_setting_tv.setText("添加");
        title_setting.setVisibility(View.VISIBLE);

        initView();
    }


    public void initView(){
        gcjbList.clear();
        gcjbList.addAll(LocalGcjbBean.getGcjbLsit(type));

        gclb_lsit.setLayoutManager(new LinearLayoutManager(this));
        gclb_lsit.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10)));
        adapter = new EngineeringBriefListAdapter(R.layout.item_project_list,gcjbList);
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
                intent.setClass(this,EngineeringBriefAddActivity.class);
                intent.putExtra(Constant.GCJB_TYPE_STR,type);
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
