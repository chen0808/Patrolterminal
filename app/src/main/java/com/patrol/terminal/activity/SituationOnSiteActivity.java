package com.patrol.terminal.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.DayListBean;
import com.patrol.terminal.bean.SignBean;
import com.patrol.terminal.bean.SituationBean;
import com.patrol.terminal.widget.SignDialog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


//从日计划过来的时候带过来
public class SituationOnSiteActivity extends BaseActivity {


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
    @BindView(R.id.line_name)
    TextView lineName;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.end_time)
    TextView endTime;
    @BindView(R.id.work_content)
    TextView workContent;
    @BindView(R.id.work_class)
    TextView workClass;
    @BindView(R.id.work_unit)
    TextView workUnit;
    @BindView(R.id.fzr_name)
    TextView fzrName;
    @BindView(R.id.iv_signature_pad)
    ImageView ivSignaturePad;
    @BindView(R.id.et_remark)
    EditText etRemark;
    private DayListBean dayListBean;
    private String id = "aaa";
    private String task_id = "bbb";
    private Map<String, RequestBody> params = new HashMap<>();
    private Bitmap bitmap;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.situation_on_site_activity);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        titleSetting.setVisibility(View.VISIBLE);
        titleName.setText("现场情况");
        titleSettingTv.setText("提交");
        dayListBean = getIntent().getParcelableExtra("bean");
        if (dayListBean != null) {
            lineName.setText(dayListBean.getLine_name());
        }
        BaseRequest.getInstance().getService().searchSituation(task_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SituationBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<SituationBean> t) throws Exception {
                        SituationBean results = t.getResults();
                        etRemark.setText(results.getRemark());
                        Glide.with(SituationOnSiteActivity.this).asBitmap().load(BaseUrl.BASE_URL + results.getSysFile().getFile_path() + results.getSysFile().getFilename()).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                ivSignaturePad.setImageBitmap(resource);
                                Log.d("TAG", BaseUrl.BASE_URL + results.getSysFile().getFile_path() + results.getSysFile().getFilename());
                                if (ivSignaturePad.getDrawable() != null) {
                                    file = SignDialog.saveBitmapFile(resource, results.getSysFile().getContent().replace(".jpg", ""));
                                }
                            }
                        });
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private SituationBean getData() {
        SituationBean bean = new SituationBean();
        bean.setId(id);
        bean.setTask_id(task_id);
        bean.setUnit(workUnit.getText().toString());
        bean.setRespon(fzrName.getText().toString());
        bean.setStart_time(startTime.getText().toString());
        bean.setEnd_time(endTime.getText().toString());
        bean.setYear(2019);
        bean.setMonth(5);
        bean.setRemark(etRemark.getText().toString());
        return bean;
    }

    private Map<String, RequestBody> setParams(SituationBean bean) {
        params.put("id", toRequestBody(id));
        params.put("task_id", toRequestBody(task_id));
        params.put("unit", toRequestBody(bean.getUnit()));
        params.put("respon", toRequestBody(bean.getRespon()));
        params.put("start_time", toRequestBody(bean.getStart_time()));
        params.put("end_time", toRequestBody(bean.getEnd_time()));
        params.put("year", toRequestBody(String.valueOf(bean.getYear())));
        params.put("month", toRequestBody(String.valueOf(bean.getMonth())));
        params.put("remark", toRequestBody(bean.getRemark()));
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        params.put("file\"; filename=\"" + file.getName(), requestFile);
        return params;
    }

    public RequestBody toRequestBody(String value) {
        if (value != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
            return requestBody;
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "");
            return requestBody;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bitmap = SignBean.getBitmap();
        if (bitmap != null) {
            ivSignaturePad.setImageBitmap(bitmap);
            file = SignDialog.saveBitmapFile(bitmap, "sign");
        }
        SignBean.setBitmap(null);
    }

    @OnClick({R.id.iv_signature_pad, R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.iv_signature_pad:
                Intent intent = new Intent();
                intent.setClass(this, SignActivity.class);
                startActivity(intent);
                break;
            case R.id.title_setting:
                SituationBean data = getData();
                setParams(data);
                BaseRequest.getInstance().getService().upLoadSituation(params).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver(this) {
                            @Override
                            protected void onSuccees(BaseResult t) throws Exception {
                                Toast.makeText(SituationOnSiteActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                Toast.makeText(SituationOnSiteActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }

}
