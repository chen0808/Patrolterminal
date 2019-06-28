package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MonitoringRecordAdapter;
import com.patrol.terminal.adapter.SaveCheckReqBean;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DepBean;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*质量监督记录表*/
public class MonitoringRecordActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

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
    @BindView(R.id.rv_monitoring_record)
    RecyclerView rvMonitoringRecord;
    private String[] data = {"一、现场情况", "二、到岗到位检查", "三、现场反违章检查", "四、违章检查参考", "五、检查监督要求"};
    private PersonalTaskListBean bean;
    private String audit_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_record);
        ButterKnife.bind(this);
        titleName.setText("到场质量监督记录");
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvMonitoringRecord.setLayoutManager(manager);
        MonitoringRecordAdapter adapter = new MonitoringRecordAdapter(R.layout.item_monitoring_record, Arrays.asList(data));
        rvMonitoringRecord.setAdapter(adapter);
        String jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        adapter.setOnItemClickListener(this);

        initData();
        if (bean!=null){
        audit_status = bean.getAudit_status();}
        if (jobType.contains(Constant.RUNNING_SQUAD_LEADER) && "2".equals(audit_status)) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("审核");
        } else if ("0".equals(audit_status)) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("保存");
        } else {
            titleSetting.setVisibility(View.GONE);
        }
    }

    private void initData() {
        bean = getIntent().getParcelableExtra("bean");

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                Intent intent = new Intent();
                intent.putExtra("bean", bean);
                intent.setClass(this, SituationOnSiteActivity.class);
                startActivity(intent);
                break;

            case 1:
                Intent intent1 = new Intent();
                intent1.putExtra("task_id", bean.getId());
                intent1.putExtra("audit_status", bean.getAudit_status());
                intent1.setClass(this, GetToPostCheckActivity.class);
                startActivity(intent1);
                break;

            case 2:
                Intent intent2 = new Intent();
                intent2.putExtra("task_id", bean.getId());
                intent2.putExtra("audit_status", bean.getAudit_status());
                intent2.setClass(this, FieldAntiInspectionActivity.class);
                startActivity(intent2);
                break;

            case 3:
                Intent intent3 = new Intent();
                intent3.setClass(this, IllegalInspectionReferenceActivity.class);
                startActivity(intent3);
                break;

            case 4:
                Intent intent4 = new Intent();
                intent4.setClass(this, InspectionRequirementsActivity.class);
                intent4.putExtra("audit_status", bean.getAudit_status());
                intent4.putExtra("task_id", bean.getId());
                startActivity(intent4);
                break;
        }
    }

    //上传安全质量监督
    public void savaCheck(String audit_status) {

        ProgressDialog.show(this, false, "正在提交。。。");
        SaveCheckReqBean bean = new SaveCheckReqBean();
        bean.setAudit_status(audit_status);
        bean.setId(this.bean.getId());
        BaseRequest.getInstance().getService()
                .savaCheck(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DepBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DepBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            Toast.makeText(MonitoringRecordActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
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
}
