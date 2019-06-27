package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.GTQXCLbean;
import com.patrol.terminal.bean.HwcwBean;
import com.patrol.terminal.bean.SaveTodoReqbean;
import com.patrol.terminal.bean.TaskBean;
import com.patrol.terminal.bean.TypeBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 斜杆塔倾斜测温
 */
public class XieGanTaQingXieCeWenActivity extends BaseActivity {
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
    @BindView(R.id.tv_line_id)
    TextView tvLineId;
    @BindView(R.id.tv_tower_id)
    TextView tvTowerId;
    @BindView(R.id.tv_tower_type)
    TextView tvTowerType;
    @BindView(R.id.et_front_tilt)
    EditText etFrontTilt;
    @BindView(R.id.et_side_tilt)
    EditText etSideTilt;
    @BindView(R.id.et_shope_rate)
    EditText etShopeRate;
    @BindView(R.id.et_height_dif)
    EditText etHeightDif;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.mengban)
    RelativeLayout mengban;
    @BindView(R.id.sp_verdict)
    Spinner spVerdict;
    private String line_id;
    private String line_name;
    private String tower_id;
    private String tower_name, audit_status;
    private String task_id, sign, typename;
    private String jobType;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incline_thermometry);
        ButterKnife.bind(this);
        initview();

    }

    private void initview() {
        jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");

        titleName.setText("斜杆塔倾斜测温");
        line_id = getIntent().getStringExtra("line_id");
        line_name = getIntent().getStringExtra("line_name");
        tower_id = getIntent().getStringExtra("tower_id");
        tower_name = getIntent().getStringExtra("tower_name");
        task_id = getIntent().getStringExtra("task_id");
        sign = getIntent().getStringExtra("sign");
        audit_status = getIntent().getStringExtra("audit_status");
        typename = getIntent().getStringExtra("typename");
        getYXtodo();
        getGTQXCL();
        getTask(task_id);
    }

    private void getTask(String task_id) {
        BaseRequest.getInstance().getService()
                .getTask(task_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TaskBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<TaskBean> t) throws Exception {
                        TaskBean bean = t.getResults();
                        sign = bean.getType_sign();
                        tvLineId.setText(bean.getLine_name());
                        tvTowerId.setText(bean.getTower_name());
                        getTowerModel(bean.getTower_id());
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void getTowerModel(String tower_id) {
        BaseRequest.getInstance().getService()
                .getTowerModel("EQ_TOWER", "tower_model", tower_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<HwcwBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<HwcwBean> t) throws Exception {
                        HwcwBean bean = t.getResults();
                        tvTowerType.setText(bean.getTower_model());
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    public void getYXtodo() {
        if ("1".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("审批");
            }
            mengban.setVisibility(View.VISIBLE);
            btnCommit.setVisibility(View.GONE);
        } else if ("2".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审批");
            }
            mengban.setVisibility(View.VISIBLE);
            btnCommit.setVisibility(View.GONE);
        } else if ("0".equals(audit_status) || "4".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("提交");
            }
            mengban.setVisibility(View.GONE);
            btnCommit.setVisibility(View.VISIBLE);
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
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    if (etFrontTilt.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写正面倾斜", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etSideTilt.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写侧面倾斜", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etShopeRate.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写倾斜率", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etHeightDif.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写横断两端高差", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (id==null) {
                        Toast.makeText(this, "请先保存数据后再提交", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    saveTodoAudit("1");
                } else {
                    CancelOrOkDialog dialog = new CancelOrOkDialog(this, "是否通过", "不通过", "通过") {
                        @Override
                        public void ok() {
                            super.ok();
                            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                                saveTodoAudit("3");   //同意
                            } else {
                                saveTodoAudit("2");   //同意
                            }

                            dismiss();
                        }

                        @Override
                        public void cancle() {
                            super.cancle();
                            saveTodoAudit("4");  //不同意
                            dismiss();
                        }
                    };
                    dialog.show();
                }
                break;
            case R.id.btn_commit:
                save();
                break;
        }
    }

    private void save() {
        String towerType = tvTowerType.getText().toString();
        String frontTilt = etFrontTilt.getText().toString();
        String sideTilt = etSideTilt.getText().toString();
        String heightDif = etHeightDif.getText().toString();
        String rante = etShopeRate.getText().toString();
        String conclusion = spVerdict.getSelectedItem().toString();
        String remark = etRemark.getText().toString();

//                if (lineId.isEmpty() || towerId.isEmpty() || towerType.isEmpty() || frontTilt.isEmpty()) {
//                    Toast.makeText(XieGanTaQingXieCeWenActivity.this, "必须填写线路名称，杆塔号，杆塔型号和正面倾斜", Toast.LENGTH_SHORT).show();
//                    return;
//                }
        Map<String, String> params = new HashMap<>();
        if (id != null) {
            params.put("id", id);
        }
        params.put("line_id", line_id);
        params.put("tower_id", tower_id);
        params.put("task_id", task_id);
        params.put("tower_type", towerType);
        params.put("positive_tilt", frontTilt.equals("") ? "0" : frontTilt);
        params.put("Flank_tilt", sideTilt.equals("") ? "0" : sideTilt);
        params.put("rate", rante.equals("") ? "0" : rante);
        params.put("high_difference", heightDif.equals("") ? "0" : heightDif);
        params.put("results", conclusion);
        params.put("work_time", DateUatil.getCurrTime());
        params.put("remark", remark);
        params.put("user_id", SPUtil.getUserId(this));

//        params.put("audit_id", SPUtil.getDepId(this));
//        params.put("content", "关于" + line_name + tower_name + "的" + typename);
//        params.put("plan_type_sign", sign);
//        params.put("agents_user_id", SPUtil.getUserId(this));
        BaseRequest.getInstance().getService().upLoadTowerBias(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        if (t.getCode() == 1) {
                            if (id==null){
                                id="1111";
                            }
                            Toast.makeText(XieGanTaQingXieCeWenActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);

                            RxRefreshEvent.publish("refreshTodo");
                            RxRefreshEvent.publish("refreshGroup");

                        } else {
                            Toast.makeText(XieGanTaQingXieCeWenActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Toast.makeText(XieGanTaQingXieCeWenActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //保存待办信息
    public void saveTodoAudit(String state) {

        SaveTodoReqbean saveTodoReqbean = new SaveTodoReqbean();

        saveTodoReqbean.setAudit_status(state);
        saveTodoReqbean.setId(task_id);
        saveTodoReqbean.setType_sign(sign);
        saveTodoReqbean.setFrom_user_id(SPUtil.getUserId(this));
        BaseRequest.getInstance().getService()
                .saveTodoAudit(saveTodoReqbean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TypeBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<TypeBean> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            Toast.makeText(XieGanTaQingXieCeWenActivity.this, "成功", Toast.LENGTH_SHORT).show();
                            RxRefreshEvent.publish("refreshTodo");
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

    //查询杆塔倾斜测量
    public void getGTQXCL() {

        ProgressDialog.show(this, false, "正在加载。。。。");
        BaseRequest.getInstance().getService()
                .getGTQXCL(task_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<GTQXCLbean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<GTQXCLbean> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            GTQXCLbean results = t.getResults();
                            if (results != null) {
                                id = results.getId();
                                line_name = results.getLine_name();
                                tower_name = results.getTower_name();
                                tower_id = results.getTower_id();
                                etFrontTilt.setText(results.getPositive_tilt() + "");
                                etSideTilt.setText(results.getFlank_tilt() + "");
                                etShopeRate.setText(results.getRate() + "");
                                etHeightDif.setText(results.getHigh_difference() + "");
                                String[] array = getResources().getStringArray(R.array.verdict);
                                for (int i = 0; i < array.length; i++) {
                                    if (array[i].equals(results.getResults())) {
                                        spVerdict.setSelection(i);
                                    }
                                }
                                etRemark.setText(results.getRemark());
//                                etTowerType.setText(results.getTower_type());
//                                etLineId.setText(line_name);
//                                etTowerId.setText(tower_name);
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
