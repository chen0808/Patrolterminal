package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.HwcwBean;
import com.patrol.terminal.bean.SaveTodoReqbean;
import com.patrol.terminal.bean.TodoListBean;
import com.patrol.terminal.bean.TypeBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 红外测温
 */
public class HongWaiCeWenActivity extends BaseActivity {
    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.et_line_id)
    TextView etLineId;
    @BindView(R.id.et_tower_id)
    TextView etTowerId;
    @BindView(R.id.et_tower_type)
    EditText etTowerType;
    @BindView(R.id.et_link_type)
    EditText etLinkType;
    @BindView(R.id.et_b_up_temperature)
    EditText etBUpTemperature;
    @BindView(R.id.et_s_up_temperature)
    EditText etSUpTemperature;
    @BindView(R.id.et_b_middle_temperature)
    EditText etBMiddleTemperature;
    @BindView(R.id.et_s_middle_temperature)
    EditText etSMiddleTemperature;
    @BindView(R.id.et_b_down_temperature)
    EditText etBDownTemperature;
    @BindView(R.id.et_s_down_temperature)
    EditText etSDownTemperature;
    @BindView(R.id.et_sans_temperature)
    EditText etSansTemperature;
    @BindView(R.id.et_check_result)
    EditText etCheckResult;
    @BindView(R.id.et_remarks)
    EditText etRemarks;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.title_setting_iv)
    ImageView titleSettingIv;
    @BindView(R.id.title_setting_tv)
    TextView titleSettingTv;
    @BindView(R.id.title_setting)
    RelativeLayout titleSetting;
    @BindView(R.id.mengban)
    RelativeLayout mengban;
    private String line_id;
    private String line_name;
    private String tower_id;
    private String tower_name, audit_status;
    private String sign;
    private String typename;
    private String task_id;
    private String id;
    private String allout_status = "0";
    private String jobType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infrared_thermometry);
        ButterKnife.bind(this);
        initview();


    }

    private void initview() {
        titleName.setText("红外测温");
        line_name = getIntent().getStringExtra("line_name");
        tower_id = getIntent().getStringExtra("tower_id");
        task_id = getIntent().getStringExtra("task_id");
        tower_name = getIntent().getStringExtra("tower_name");
        audit_status = getIntent().getStringExtra("audit_status");
        sign = getIntent().getStringExtra("sign");
        typename = getIntent().getStringExtra("typename");
        jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        getYXtodo();
        etLineId.setText(line_name);
        etTowerId.setText(tower_name);
        getHWCW();
    }

    public void getYXtodo() {


        if ("0".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                mengban.setVisibility(View.VISIBLE);
                btnCommit.setVisibility(View.GONE);
                titleSetting.setVisibility(View.GONE);
            } else if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                mengban.setVisibility(View.GONE);
                btnCommit.setVisibility(View.VISIBLE);
            } else {
                mengban.setVisibility(View.VISIBLE);
                btnCommit.setVisibility(View.GONE);
                titleSetting.setVisibility(View.GONE);
            }

        } else if ("1".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审批");
            }
            mengban.setVisibility(View.VISIBLE);
            btnCommit.setVisibility(View.GONE);
        } else if ("2".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审批");
            }
            mengban.setVisibility(View.VISIBLE);
            btnCommit.setVisibility(View.GONE);
        } else if ("4".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                titleSetting.setVisibility(View.GONE);
                mengban.setVisibility(View.VISIBLE);
                btnCommit.setVisibility(View.GONE);
            } else if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                mengban.setVisibility(View.GONE);
                btnCommit.setVisibility(View.VISIBLE);
            } else {
                titleSetting.setVisibility(View.GONE);
                mengban.setVisibility(View.VISIBLE);
                btnCommit.setVisibility(View.GONE);
            }

        } else {
            titleSetting.setVisibility(View.GONE);
            mengban.setVisibility(View.VISIBLE);
            btnCommit.setVisibility(View.GONE);
        }

}

    @OnClick({R.id.title_back, R.id.btn_commit, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                CancelOrOkDialog dialog = new CancelOrOkDialog(this, "是否通过", "不通过", "通过") {
                    @Override
                    public void ok() {
                        super.ok();
                        if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                            saveTodoAudit("3");   //同意
                        } else if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                            saveTodoAudit("2");   //同意
                        }

                        dismiss();
                    }

                    @Override
                    public void cancel() {
                        super.cancel();
                        saveTodoAudit("4");  //不同意
                        dismiss();
                    }
                };
                dialog.show();
                break;
            case R.id.btn_commit:
                ProgressDialog.show(this, false, "正在加载。。。");
                String towerType = etTowerType.getText().toString();
                String linkType = etLinkType.getText().toString();
                String bUp = etBUpTemperature.getText().toString();
                String sUp = etSUpTemperature.getText().toString();
                String bMiddle = etBMiddleTemperature.getText().toString();
                String sMiddle = etSMiddleTemperature.getText().toString();
                String bDown = etBDownTemperature.getText().toString();
                String sDown = etSDownTemperature.getText().toString();
                String sans = etSansTemperature.getText().toString();
                String checkResult = etCheckResult.getText().toString();
                String remarks = etRemarks.getText().toString();

//                if (lineId.isEmpty() || towerId.isEmpty()) {
//                    Toast.makeText(HongWaiCeWenActivity.this, "请传入线路名称和杆塔号！（必填）", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                Map<String, String> params = new HashMap<>();
                if (id != null) {
                    params.put("id", id);
                }
                params.put("tower_id", tower_id);
                params.put("task_id", task_id);
                params.put("tower_type", towerType);
                params.put("connection_type", linkType);
                params.put("up_big", bUp);
                params.put("up_small", sUp);
                params.put("mid_big", bMiddle);
                params.put("mid_small", sMiddle);
                params.put("down_big", bDown);
                params.put("down_small", sDown);
                params.put("temperature", sans);
                params.put("results", checkResult);
                params.put("work_time", DateUatil.getCurrTime());

                params.put("remark", remarks);

                params.put("user_id", SPUtil.getUserId(this));
                params.put("audit_id", SPUtil.getDepId(this));
                params.put("content", "关于" + line_name + tower_name + "的" + typename);
                params.put("plan_type_sign", sign);
                params.put("agents_user_id", SPUtil.getUserId(this));

                BaseRequest.getInstance().getService().upLoadInfrared(params).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<HwcwBean>(this) {
                            @Override
                            protected void onSuccees(BaseResult<HwcwBean> t) throws Exception {
                                if (t.getCode() == 1) {
                                    Toast.makeText(HongWaiCeWenActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    RxRefreshEvent.publish("refreshGroup");
                                    finish();
                                }

                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                ProgressDialog.cancle();
                                Toast.makeText(HongWaiCeWenActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }

    //保存待办信息
    public void saveTodoAudit(String state) {

        SaveTodoReqbean saveTodoReqbean = new SaveTodoReqbean();

        saveTodoReqbean.setAudit_status(state);
        saveTodoReqbean.setId(task_id);

        BaseRequest.getInstance().getService()
                .saveTodoAudit(saveTodoReqbean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TypeBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<TypeBean> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            Toast.makeText(HongWaiCeWenActivity.this, "审批成功", Toast.LENGTH_SHORT).show();
                            RxRefreshEvent.publish("todo");
                            RxRefreshEvent.publish("refreshGroup");
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

    //查询红外测温
    public void getHWCW() {

        ProgressDialog.show(this, false, "正在加载。。。。");
        BaseRequest.getInstance().getService()
                .getHWCW(task_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<HwcwBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<HwcwBean> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            HwcwBean results = t.getResults();
                            if (results != null) {
                                id = results.getId();
                                line_name = results.getLine_name();
                                tower_name = results.getTower_name();
                                tower_id = results.getTower_id();
                                etTowerType.setText(results.getTower_model());
                                etLinkType.setText(results.getConnection_type() + "");
                                etBUpTemperature.setText(results.getUp_big() + "");
                                etSUpTemperature.setText(results.getUp_small() + "");
                                etBMiddleTemperature.setText(results.getMid_big() + "");
                                etSMiddleTemperature.setText(results.getMid_small() + "");
                                etBDownTemperature.setText(results.getDown_big() + "");
                                etSDownTemperature.setText(results.getDown_small() + "");
                                etSansTemperature.setText(results.getTemperature() + "");
                                etCheckResult.setText(results.getResults() + "");
                                etRemarks.setText(results.getRemark());
                                etLineId.setText(line_name);
                                etTowerId.setText(tower_name);
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

}
