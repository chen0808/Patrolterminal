package com.patrol.terminal.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.FieldAntiInspectionBean;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class FieldAntiInspectionActivity extends BaseActivity {
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
    @BindView(R.id.guanliweizhang_lv)
    LinearLayout guanliweizhangLv;
    @BindView(R.id.xingweiweizhang_lv)
    LinearLayout xingweiweizhangLv;
    @BindView(R.id.zhuagnzhiweizhang_lv)
    LinearLayout zhuagnzhiweizhangLv;
    @BindView(R.id.control_card_submit)
    TextView controlCardSubmit;


    private List<FieldAntiInspectionBean> fieldAntiInspectionBeans = new ArrayList<>();
    private String task_id = "";
    private List<FieldAntiInspectionBean> inspectionBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.field_antiinspectioin_activity);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        titleBack.setVisibility(View.VISIBLE);
        titleName.setText("现场反违章检查");

        task_id = getIntent().getStringExtra("task_id");

        getFieldAntiInspection();
    }

    private void getFieldAntiInspection() {
        ProgressDialog.show(this, false, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getFieldAntiInspection(task_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<FieldAntiInspectionBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<FieldAntiInspectionBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            inspectionBeans = t.getResults();
                            for (int i = 0; i < inspectionBeans.size(); i++) {
                                FieldAntiInspectionBean fieldAntiInspectionBean = inspectionBeans.get(i);
                                View convertView = View.inflate(FieldAntiInspectionActivity.this,R.layout.item_field_anti_inspection_listview, null);

                                TextView mContentTv = convertView.findViewById(R.id.item_content_tv);
                                TextView mLevelTv = convertView.findViewById(R.id.level_tv);
                                TextView mScoreTv = convertView.findViewById(R.id.score_tv);
                                EditText mCheckPersonEt = convertView.findViewById(R.id.check_person_et);
                                ImageView mLineIv = convertView.findViewById(R.id.item_line_iv);

                                mContentTv.setText(fieldAntiInspectionBean.getCheck_content());
                               mLevelTv.setText((fieldAntiInspectionBean.getIllegal_type()));
                               mScoreTv.setText(fieldAntiInspectionBean.getScore());
                               mCheckPersonEt.setText(fieldAntiInspectionBean.getCheck_user());
                                mCheckPersonEt.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        fieldAntiInspectionBean.setCheck_user(s.toString());
                                    }
                                });

                                if (inspectionBeans.get(i).getIllegal().equals("1")) {
                                    guanliweizhangLv.addView(convertView);

                                } else if (inspectionBeans.get(i).getIllegal().equals("2")) {
                                    xingweiweizhangLv.addView(convertView);

                                } else if (inspectionBeans.get(i).getIllegal().equals("3")) {
                                    zhuagnzhiweizhangLv.addView(convertView);

                                }
                            }
                        }
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    @OnClick({R.id.title_back, R.id.control_card_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.control_card_submit:
                fieldAntiInspectionBeans.clear();
                for (int i = 0; i < inspectionBeans.size(); i++) {
                    fieldAntiInspectionBeans.add(new FieldAntiInspectionBean(inspectionBeans.get(i).getTask_illegal_id(), inspectionBeans.get(i).getCheck_user() == null ? "x" : inspectionBeans.get(i).getCheck_user(), task_id));
                }
                String json = new Gson().toJson(fieldAntiInspectionBeans);
                Log.d("TAG", json);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
                BaseRequest.getInstance().getService().sendPostIllegal(body).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver(this) {
                            @Override
                            protected void onSuccees(BaseResult t) throws Exception {
                                if (t.getCode() == 1) {
                                    Toast.makeText(FieldAntiInspectionActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                            }
                        });
                break;

        }
    }
}
