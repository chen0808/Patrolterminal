package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.GradohicProgressAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.GraphicProgressBean;
import com.patrol.terminal.bean.GraphicProgressBean_Table;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.bean.GroupTaskBean_Table;
import com.patrol.terminal.utils.SPUtil;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//形象进度列表
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

    private List<GraphicProgressBean> list=new ArrayList<>();
    private GradohicProgressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_progress);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        getdata();
        titleName.setText("形象进度");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingIv.setVisibility(View.VISIBLE);
        titleSettingTv.setVisibility(View.GONE);
        titleSettingIv.setImageResource(R.mipmap.add_white);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        graphicProgress.setLayoutManager(manager);

        adapter = new GradohicProgressAdapter(R.layout.item_gradohic_progress
                ,list);
        graphicProgress.setAdapter(adapter);
    }

    public void getdata(){
        List<GraphicProgressBean> graphicProgressBeans = SQLite.select().from(GraphicProgressBean.class)
                .where(GraphicProgressBean_Table.createName.eq(SPUtil.getUserName(this)))
                .queryList();
        if (graphicProgressBeans!=null){
            list=graphicProgressBeans;
        }
    }
    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                Intent intent=new Intent(this,AddGraphicProgressActivity.class);
                startActivityForResult(intent,245);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==245&&resultCode==RESULT_OK){
            getdata();
            adapter.setNewData(list);
        }
    }
}
