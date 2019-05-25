package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ScoreAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.ScoreListBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScoreListActivity extends BaseActivity {

    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.rv_score_list)
    RecyclerView rvScoreList;
    private List<ScoreListBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);
        ButterKnife.bind(this);
        titleName.setText("评分列表");
        data.add(new ScoreListBean("杨德林", 46));
        data.add(new ScoreListBean("郑烈辉", 25));
        data.add(new ScoreListBean("闵玉林", 68));
        data.add(new ScoreListBean("董红军", 59));
        data.add(new ScoreListBean("李永文", 65));
        data.add(new ScoreListBean("陆昊", 57));
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvScoreList.setLayoutManager(manager);
        rvScoreList.setAdapter(new ScoreAdapter(R.layout.item_score, data));
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
