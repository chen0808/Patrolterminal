package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.SaveCheckReqBean;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DepBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//验收报告
public class CheckActivity extends BaseActivity {

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
    @BindView(R.id.check_et)
    EditText checkEt;
    private String task_id;
    private String audit_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("上传验收报告");

       String jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        task_id = getIntent().getStringExtra("task_id");
        String content = getIntent().getStringExtra("content");
        audit_status = getIntent().getStringExtra("audit_status");
        if (content!=null){
            checkEt.setText(content);
        }
        if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)&&"2".equals(audit_status)) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("审核");
            checkEt.setFocusable(false);
            checkEt.setFocusableInTouchMode(false);
        } else if ("0".equals(audit_status)) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("提交");
        } else {
            titleSetting.setVisibility(View.GONE);
            checkEt.setFocusable(false);
            checkEt.setFocusableInTouchMode(false);
        }
    }

    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                if ("2".equals(audit_status)){
                    CancelOrOkDialog dialog = new CancelOrOkDialog(this, "审核", "不同意", "同意") {
                        @Override
                        public void ok() {
                            super.ok();
                            savaCheck("3");
                            dismiss();
                        }

                        @Override
                        public void cancle() {
                            super.cancle();
                            savaCheck("4");
                            dismiss();
                        }
                    };
                    dialog.show();
                }else {
                    savaCheck("2");
                }

                break;
        }
    }
    //上传验收报告
    public void savaCheck(String audit_status) {
        String content = checkEt.getText().toString().trim();
        if ("".equals(content)){
            Toast.makeText(this,"请输入验收报告",Toast.LENGTH_SHORT).show();
            return;
        }
        ProgressDialog.show(this,false,"正在提交。。。");
        SaveCheckReqBean bean=new SaveCheckReqBean();
        bean.setAudit_status(audit_status);
        bean.setCheck_report(content);
        bean.setId(task_id);
        BaseRequest.getInstance().getService()
                .savaCheck(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DepBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DepBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode()==1){
                            Toast.makeText(CheckActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }
}
