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
import com.patrol.terminal.adapter.WorkWeeklyListAdapter;
import com.patrol.terminal.bean.LocalWorkWeeklyBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.widget.RoundProgressBar;
import com.patrol.terminal.widget.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 里程碑
 */
public class LandMarkActivity extends AppCompatActivity {

    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_tv)
    TextView title_setting_tv;
    @BindView(R.id.title_setting)
    RelativeLayout title_setting;

    @BindView(R.id.probar_sszb)
    RoundProgressBar probar_sszb;
    @BindView(R.id.probar_xmqq)
    RoundProgressBar probar_xmqq;
    @BindView(R.id.probar_xmlx)
    RoundProgressBar probar_xmlx;
    @BindView(R.id.probar_sjgl)
    RoundProgressBar probar_sjgl;
    @BindView(R.id.probar_zbgl)
    RoundProgressBar probar_zbgl;
    @BindView(R.id.probar_qq)
    RoundProgressBar probar_qq;
    @BindView(R.id.probar_jdgl)
    RoundProgressBar probar_jdgl;
    @BindView(R.id.probar_htgl)
    RoundProgressBar probar_htgl;
    @BindView(R.id.probar_zj)
    RoundProgressBar probar_zj;
    @BindView(R.id.probar_thj)
    RoundProgressBar probar_thj;
    @BindView(R.id.probar_ys)
    RoundProgressBar probar_ys;
    @BindView(R.id.probar_jg)
    RoundProgressBar probar_jg;
    @BindView(R.id.probar_jc)
    RoundProgressBar probar_jc;
    @BindView(R.id.probar_bw)
    RoundProgressBar probar_bw;
    @BindView(R.id.probar_bn)
    RoundProgressBar probar_bn;

    private List<LocalWorkWeeklyBean> workList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_list);
        ButterKnife.bind(this);

        titleName.setText("里程碑");

        title_setting_tv.setText("添加");
        title_setting.setVisibility(View.VISIBLE);


        initView();
    }


    public void initView() {
//        workList.clear();
//        workList.addAll(LocalWorkWeeklyBean.getAllLsit());

        initProBar(probar_xmqq, 30);
        initProBar(probar_xmlx, 0);
        initProBar(probar_sjgl, 0);
        initProBar(probar_zbgl, 0);
        initProBar(probar_sszb, 0);
        initProBar(probar_qq, 0);
        initProBar(probar_jdgl, 0);
        initProBar(probar_htgl, 0);
        initProBar(probar_zj, 0);
        initProBar(probar_thj, 0);
        initProBar(probar_ys, 0);
        initProBar(probar_jg, 0);
        initProBar(probar_jc, 0);
        initProBar(probar_bw, 0);
        initProBar(probar_bn, 0);



    }

    public void initProBar(RoundProgressBar bar, int probar) {
        int color = getResources().getColor(R.color.base_status_bar);
        bar.setCircleProgressColor(color);
//        bar.setCircleColor(color);
        bar.setTextColor(getResources().getColor(R.color.home_title_bg));
        bar.setTextSize(20);
        bar.setTextIsDisplayable(true);
        bar.setStyle(RoundProgressBar.FILL_UP);
        bar.setProgress(probar);
    }

    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
//                Intent intent = new Intent();
//                intent.setClass(this,WorkWeeklyAddActivity.class);
//                startActivityForResult(intent,Constant.GCJB_ADD);

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
