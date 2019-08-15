package com.patrol.terminal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.LocalLandMarkBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.widget.RoundProgressBar;

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

    private List<LocalLandMarkBean> landMarkList = new ArrayList<>();

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

        landMarkList.clear();
        landMarkList.addAll(LocalLandMarkBean.getAllLsit());

        for (int i = 0; i < landMarkList.size(); i++) {
            LocalLandMarkBean bean = landMarkList.get(i);
            String sbjd = bean.getLandmark_sbjd();
            if (sbjd.equals("项目前期")) {
                initProBar(probar_xmqq, bean.getLandmark_jd());
            } else if (sbjd.equals("项目立项")) {
                initProBar(probar_xmlx, bean.getLandmark_jd());
            } else if (sbjd.equals("设计管理")) {
                initProBar(probar_sjgl, bean.getLandmark_jd());
            } else if (sbjd.equals("招标管理")) {
                initProBar(probar_zbgl, bean.getLandmark_jd());
            } else if (sbjd.equals("合同管理")) {
                initProBar(probar_htgl, bean.getLandmark_jd());
            } else if (sbjd.equals("进度管理")) {
                initProBar(probar_jdgl, bean.getLandmark_jd());
            } else if (sbjd.equals("前期")) {
                initProBar(probar_qq, bean.getLandmark_jd());
            } else if (sbjd.equals("实施准备")) {
                initProBar(probar_sszb, bean.getLandmark_jd());
            } else if (sbjd.equals("在建")) {
                initProBar(probar_zj, bean.getLandmark_jd());
            } else if (sbjd.equals("停缓建")) {
                initProBar(probar_thj, bean.getLandmark_jd());
            } else if (sbjd.equals("验收")) {
                initProBar(probar_ys, bean.getLandmark_jd());
            } else if (sbjd.equals("竣工")) {
                initProBar(probar_jg, bean.getLandmark_jd());
            } else if (sbjd.equals("保内")) {
                initProBar(probar_bn, bean.getLandmark_jd());
            } else if (sbjd.equals("保外")) {
                initProBar(probar_bw, bean.getLandmark_jd());
            } else if (sbjd.equals("解除")) {
                initProBar(probar_jc, bean.getLandmark_jd());
            }
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Constant.GCJB_ADD) {
            initView();
        }
    }


    @OnClick({R.id.title_back, R.id.title_setting, R.id.probar_xmqq, R.id.probar_xmlx, R.id.probar_sjgl, R.id.probar_zbgl, R.id.probar_sszb, R.id.probar_qq, R.id.probar_jdgl, R.id.probar_htgl, R.id.probar_zj, R.id.probar_thj, R.id.probar_ys, R.id.probar_jg, R.id.probar_jc, R.id.probar_bw, R.id.probar_bn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                Intent intent = new Intent();
                intent.setClass(this, LandMarkAddActivity.class);
                startActivityForResult(intent, Constant.GCJB_ADD);
                break;
            case R.id.probar_xmqq:
            case R.id.probar_xmlx:
            case R.id.probar_sjgl:
            case R.id.probar_zbgl:
            case R.id.probar_sszb:
            case R.id.probar_qq:
            case R.id.probar_jdgl:
            case R.id.probar_htgl:
            case R.id.probar_zj:
            case R.id.probar_thj:
            case R.id.probar_ys:
            case R.id.probar_jg:
            case R.id.probar_jc:
            case R.id.probar_bw:
            case R.id.probar_bn:
                Intent intent1 = new Intent();
                intent1.setClass(this, LandMarkDetailActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
