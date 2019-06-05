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

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.SignBean;
import com.patrol.terminal.bean.SituationBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.SignDialog;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
    EditText workClass;
    @BindView(R.id.work_unit)
    EditText workUnit;
    @BindView(R.id.fzr_name)
    EditText fzrName;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.iv_signature_pad)
    ImageView ivSignaturePad;
    @BindView(R.id.menban)
    TextView menban;
    private PersonalTaskListBean bean;
    private Map<String, RequestBody> params = new HashMap<>();
    private Bitmap bitmap;
    private File file;
    private SituationBean results;
    private long endtime = -1;
    private long starTime = 0;
    private String workclass;
    private String unit;
    private String name;
    private String remark;
    private String task_id;

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

        bean = getIntent().getParcelableExtra("bean");
        if (bean != null) {
            task_id = bean.getId();
            lineName.setText(bean.getLine_name());
            workContent.setText(bean.getName());
            if ("0".equals(bean.getAudit_status())||"4".equals(bean.getAudit_status())){
                menban.setVisibility(View.GONE);
                titleSetting.setVisibility(View.VISIBLE);
            }else {
                menban.setVisibility(View.VISIBLE);
                titleSetting.setVisibility(View.GONE);
            }
        }

        BaseRequest.getInstance().getService().searchSituation(task_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SituationBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<SituationBean> t) throws Exception {
                        results = t.getResults();
                        if (results != null) {
                            etRemark.setText(results.getContent());
                            startTime.setText(results.getStart_time());
                            endTime.setText(results.getEnd_time());
                            workUnit.setText(results.getUnit_name());
                            workClass.setText(results.getDep_name());
                            workContent.setText(results.getContent());
                            fzrName.setText(results.getDuty_user_name());
                            etRemark.setText(results.getCheck());
                            Glide.with(SituationOnSiteActivity.this).asBitmap().load(BaseUrl.BASE_URL + results.getFile_path() + results.getFilename()).into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    ivSignaturePad.setImageBitmap(resource);
                                    Log.d("TAG", BaseUrl.BASE_URL + results.getFile_path() + results.getFilename());
                                    if (ivSignaturePad.getDrawable() != null) {
                                        file = SignDialog.saveBitmapFile(resource, results.getContent().replace(".jpg", ""));
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private SituationBean getData() {
        SituationBean bean = new SituationBean();
        bean.setId(results == null ? "" : results.getId());
        bean.setTask_id(bean.getId());
        bean.setUnit_name(unit);
        bean.setDuty_user_name(name);
        bean.setStart_time(startTime.getText().toString());
        bean.setEnd_time(endTime.getText().toString());
        bean.setYear(this.bean.getYear());
        bean.setMonth(this.bean.getMonth());
        bean.setDay(this.bean.getDay());
        bean.setCheck(remark);
        bean.setDep_name(workclass);
        bean.setContent(workContent.getText().toString());
        bean.setTask_id(task_id);
        bean.setLine_id(this.bean.getLine_id());
        bean.setLine_name(this.bean.getLine_name());
        bean.setUser_id(SPUtil.getUserId(this));
        bean.setUser_name(SPUtil.getUserName(this));
        String files = FileUtil.fileToBase64(file);
        bean.setFile(files);
        return bean;
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

    @OnClick({R.id.iv_signature_pad, R.id.title_back, R.id.title_setting, R.id.start_time, R.id.end_time})
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
                workclass = workClass.getText().toString().trim();
                unit = workUnit.getText().toString().trim();
                name = fzrName.getText().toString().trim();
                remark = etRemark.getText().toString().trim();
                if (checkParm()) {
                    SituationBean data = getData();
                    BaseRequest.getInstance().getService().upLoadSituation(data).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver(this) {
                                @Override
                                protected void onSuccees(BaseResult t) throws Exception {
                                    Toast.makeText(SituationOnSiteActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                    Toast.makeText(SituationOnSiteActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;
            case R.id.start_time:
                showDay(1);
                break;
            case R.id.end_time:
                showDay(2);
                break;
        }
    }

    public boolean checkParm() {
        if (starTime == 0) {
            Toast.makeText(this, "请选择开始时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (endtime == -1) {
            Toast.makeText(this, "请选择结束结束时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (starTime > endtime) {
            Toast.makeText(this, "起始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ("".equals(workclass)) {
            Toast.makeText(this, "请输入运维班组", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ("".equals(unit)) {
            Toast.makeText(this, "请输入施工单位", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ("".equals(name)) {
            Toast.makeText(this, "请输入施工负责人", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ("".equals(remark)) {
            Toast.makeText(this, "请输入检查情况", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (file == null) {
            Toast.makeText(this, "请手写签名", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void showDay(int type) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        //选中事件回调
//是否只显示中间选中项的label文字，false则每项item全部都带有label。
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {


            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                String time = DateUatil.getDay(date);
                if (type == 1) {
                    starTime = date.getTime();
                    startTime.setText(time);
                } else {
                    endtime = date.getTime();
                    endTime.setText(time);
                }
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentTextSize(18)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }
}
