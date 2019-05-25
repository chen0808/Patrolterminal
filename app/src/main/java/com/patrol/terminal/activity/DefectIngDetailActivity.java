package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.DefectFragmentBean;
import com.patrol.terminal.overhaul.OverhaulFileBean;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DefectIngDetailActivity extends BaseActivity {


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
    @BindView(R.id.btn_1)
    RadioButton btn1;
    @BindView(R.id.btn_2)
    RadioButton btn2;
    @BindView(R.id.btn_3)
    RadioButton btn3;
    @BindView(R.id.btn_4)
    RadioButton btn4;
    @BindView(R.id.defect_content)
    TextView defectContent;
    @BindView(R.id.defect_line_name)
    TextView defectLineName;
    @BindView(R.id.defect_tower_name)
    TextView defectTowerName;
    @BindView(R.id.defect_task_id)
    TextView defectTaskId;
    @BindView(R.id.defect_allote_status)
    TextView defectAlloteStatus;
    @BindView(R.id.defect_type)
    TextView defectType;
//    @BindView(R.id.defect_pat_name)
//    TextView defectPatName;
//    @BindView(R.id.defect_group_name)
//    TextView defectGroupName;
    @BindView(R.id.defect_dep_name)
    TextView defectDepName;
//    @BindView(R.id.defect_reamark)
//    TextView defectReamark;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.defect_task_tv)
    ImageView defectTaskTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_defect_ing);
        ButterKnife.bind(this);

        initview();
    }

    private void initview() {
        DefectFragmentBean bean = getIntent().getParcelableExtra("bean");
        titleName.setText("缺陷详情");
        defectContent.setText("内容"+bean.getContent());
        defectDepName.setText("工作班组："+bean.getDeal_dep_name());
        getPartrolRecord(bean.getId());
    }

    @OnClick({R.id.title_back, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_1:
                tv.setText(btn1.getText().toString());
                break;
            case R.id.btn_2:
                tv.setText(btn2.getText().toString());
                break;
            case R.id.btn_3:
                tv.setText(btn3.getText().toString());
                break;
            case R.id.btn_4:
                tv.setText(btn4.getText().toString());
                break;
        }
    }
    //查询接电电阻
    public void getPartrolRecord(String id) {

        ProgressDialog.show(this, false, "正在加载。。。。");
        BaseRequest.getInstance().getService()
                .getPartrolRecord(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulFileBean>>(this) {


                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulFileBean>> t) throws Exception {
                        if (t.getCode()==1){
                            List<OverhaulFileBean> results = t.getResults();
                            for (int i = 0; i < results.size(); i++) {
                                if (i==0){
                                OverhaulFileBean overhaulFileBean = results.get(i);
                                String file_path = overhaulFileBean.getFile_path();
                                String compressPath= BaseUrl.BASE_URL+file_path.substring(1,file_path.length())+overhaulFileBean.getFilename();
                                Glide.with(DefectIngDetailActivity.this).load(compressPath).into(defectTaskTv);
                                }

                            }

                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                });
    }
}
