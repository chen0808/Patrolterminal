package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.GradohicProgressAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GraphicProgressActivity extends BaseActivity {

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
    @BindView(R.id.graphic_progress)
    SwipeRecyclerView graphicProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_progress);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("形象进度");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingIv.setVisibility(View.VISIBLE);
        titleSettingTv.setVisibility(View.GONE);
        titleSettingIv.setImageResource(R.mipmap.add_white);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        graphicProgress.setLayoutManager(manager);
        List<String> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i+"");
        }
        GradohicProgressAdapter adapter=new GradohicProgressAdapter(R.layout.item_gradohic_progress
                ,list);
        graphicProgress.setAdapter(adapter);
    }

    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                Intent intent=new Intent(this,AddGraphicProgressActivity.class);
                startActivity(intent);
                break;
        }
    }
}
