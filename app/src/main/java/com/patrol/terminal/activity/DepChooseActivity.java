package com.patrol.terminal.activity;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DepChooseAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DepChooseActivity extends Activity {
    @BindView(R.id.rv_dep_choose)
    RecyclerView rvDepChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dep_choose);
        ButterKnife.bind(this);
        initData();


    }

    private void initData() {
        String[] jobNames = getIntent().getStringArrayExtra(Constant.USERJOBNAME);
        String[] jobTypes = getIntent().getStringArrayExtra(Constant.JOBTYPE);
//        String[] data = new String[jobs.size()];
//        for (int i = 0; i < jobs.size(); i++) {
//            String jobName = Utils.getJobName(jobs.get(i));
//            data[i] = jobName;
//        }


        rvDepChoose.setLayoutManager(new LinearLayoutManager(this));
        DepChooseAdapter adapter = new DepChooseAdapter(R.layout.item_dep_choose, Arrays.asList(jobNames));
        rvDepChoose.setAdapter(adapter);

        //没走
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //数据是使用Intent返回
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra(Constant.RESULT, jobTypes[position]);
                //设置返回数据
                setResult(RESULT_OK, intent);
                //关闭Activity
                finish();

            }
        });

    }

}
