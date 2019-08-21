package com.patrol.terminal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.CheckProjectBean;
import com.patrol.terminal.bean.CheckProjectServiceBean;
import com.patrol.terminal.bean.InitiateProjectBean2;
import com.patrol.terminal.bean.LocalGcjbBean;
import com.patrol.terminal.bean.LocalLandMarkBean;
import com.patrol.terminal.bean.LocalWorkWeeklyBean;
import com.patrol.terminal.overhaul.ProjectSearchActivity;
import com.patrol.terminal.overhaul.ProjectSearchActivityNew;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;
import com.patrol.terminal.widget.RoundProgressBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    @BindView(R.id.title_qx_content)
    EditText titleQxContent;
    @BindView(R.id.landmark_view)
    RelativeLayout landmarkView;

    private List<LocalLandMarkBean> landMarkList = new ArrayList<>();
    private int pageNum = 1;
    private int count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_list);
        ButterKnife.bind(this);

        titleName.setText("里程碑");

        title_setting_tv.setText("添加");
        title_setting.setVisibility(View.VISIBLE);

        titleQxContent.setFocusable(false);
//        initView();
    }


    public void initView(List<LocalLandMarkBean> result) {

        landMarkList.clear();
        landMarkList.addAll(result);

        for (int i = 0; i < landMarkList.size(); i++) {
            LocalLandMarkBean bean = landMarkList.get(i);
            String sbjd = Constant.lcbList[bean.getLandmark_sbjd()];//bean.getLandmark_sbjd();

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

    public void quesyList(String temp_project_id) {
        ProgressDialog.show(this);
        BaseRequest.getInstance().getService()
                .queryLcbGET(temp_project_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LocalLandMarkBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LocalLandMarkBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
                            initView(t.getResults());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constant.GCJB_ADD:
                case Constant.GCJB_ADD_PROJECT:

                    InitiateProjectBean2 clickedCheckProjectBean = data.getParcelableExtra("search_project_item");
                    if (clickedCheckProjectBean != null) {
                        titleQxContent.setText(clickedCheckProjectBean.getName());

                        quesyList(clickedCheckProjectBean.getName());
                        landmarkView.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    }

    @OnClick({R.id.title_back, R.id.title_setting, R.id.probar_xmqq, R.id.probar_xmlx,
            R.id.probar_sjgl, R.id.probar_zbgl, R.id.probar_sszb, R.id.probar_qq,
            R.id.probar_jdgl, R.id.probar_htgl, R.id.probar_zj, R.id.probar_thj,
            R.id.probar_ys, R.id.probar_jg, R.id.probar_jc, R.id.probar_bw,
            R.id.probar_bn, R.id.title_qx_content})
    public void onViewClicked(View view) {
        int marks = 5;
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
                marks = 1;
            case R.id.probar_xmlx:
                marks = 2;
            case R.id.probar_sjgl:
                marks = 3;
            case R.id.probar_zbgl:
                marks = 4;
            case R.id.probar_sszb:
                marks = 5;
            case R.id.probar_qq:
                marks = 6;
            case R.id.probar_jdgl:
                marks = 7;
            case R.id.probar_htgl:
                marks = 8;
            case R.id.probar_zj:
                marks= 9;
            case R.id.probar_thj:
                marks = 10;
            case R.id.probar_ys:
                marks = 11;
            case R.id.probar_jg:
                marks = 12;
            case R.id.probar_jc:
//                marks = 13;
            case R.id.probar_bw:
//                marks = 14;
            case R.id.probar_bn:
//                marks = 15;
                Intent intent1 = new Intent();
                intent1.putExtra("marks",Constant.lcbList[marks]);
                intent1.putExtra("list",(Serializable)landMarkList);
                intent1.setClass(this, LandMarkDetailActivity.class);
                startActivity(intent1);
                break;
            case R.id.title_qx_content:
                intent = new Intent();
                intent.setClass(this, ProjectSearchActivityNew.class);
                startActivityForResult(intent, Constant.GCJB_ADD_PROJECT);
                break;
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


}
