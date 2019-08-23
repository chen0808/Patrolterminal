package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.InitiateProjectBean2;
import com.patrol.terminal.overhaul.ProjectSearchActivityNew;
import com.patrol.terminal.utils.PickerUtils;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：陈飞
 * 时间：2019/08/20 10:32
 */
public class DesignPlanAddActivity extends BaseActivity {

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
    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.et_plan_no)
    EditText etPlanNo;
    @BindView(R.id.et_plan_name)
    EditText etPlanName;
    @BindView(R.id.et_total_money)
    EditText etTotalMoney;
    @BindView(R.id.et_duty_user_name)
    EditText etDutyUserName;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rl_content)
    LinearLayout rlContent;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.rl_remark)
    LinearLayout rlRemark;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.defect_gridView)
    GridView defectGridView;
    private Map<String, RequestBody> params = new HashMap<>();
    private String project_id;
    private String project_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_plan_add);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        titleName.setText("添加计划");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("提交");
        etPlanNo.setText("" + System.currentTimeMillis());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        tvStartTime.setText(simpleDateFormat.format(date));
    }

    @OnClick({R.id.title_back, R.id.title_setting, R.id.tv_project_name, R.id.tv_start_time, R.id.tv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                planSavePost();
                break;
            case R.id.tv_project_name:
                Intent intent = new Intent();
                intent.setClass(DesignPlanAddActivity.this, ProjectSearchActivityNew.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.tv_start_time:
                PickerUtils.showDate(this, tvStartTime);
                break;
            case R.id.tv_end_time:
                PickerUtils.showDate(this, tvEndTime);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1001:
                if (data != null) {
                    InitiateProjectBean2 clickedCheckProjectBean = data.getParcelableExtra("search_project_item");
                    if (clickedCheckProjectBean != null) {
                        project_id = clickedCheckProjectBean.getId();
                        project_name = clickedCheckProjectBean.getName();
                        tvProjectName.setText(project_name);
                    }
                }
                break;
        }
    }

    private void planSavePost() {
        if (project_id == null) {
            Utils.showToast("请选择项目");
            return;
        }
        ProgressDialog.show(this, false, "正在加载。。。。");
        params.clear();
        params.put("temp_project_id", toRequestBody(project_id));
        params.put("temp_project_name", toRequestBody(tvProjectName.getText().toString()));
        params.put("plan_no", toRequestBody(etPlanNo.getText().toString()));
        params.put("name", toRequestBody(etPlanName.getText().toString()));
        params.put("money", toRequestBody(etTotalMoney.getText().toString()));
        params.put("duty_user_name", toRequestBody(etDutyUserName.getText().toString()));
        params.put("start_date", toRequestBody(tvStartTime.getText().toString()));
        params.put("finish_date", toRequestBody(tvEndTime.getText().toString()));
        params.put("content", toRequestBody(etContent.getText().toString()));
        params.put("remarks", toRequestBody(etRemark.getText().toString()));
        params.put("status", toRequestBody("0"));

        BaseRequest.getInstance().getService()
                .planSavePOST(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {

                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            Utils.showToast("提交成功");
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast(e.getMessage());
                    }

                });
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
}
