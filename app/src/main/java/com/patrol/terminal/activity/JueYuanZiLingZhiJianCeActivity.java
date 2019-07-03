package com.patrol.terminal.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.patrol.terminal.bean.HwcwBean;
import com.patrol.terminal.bean.JYZbean;
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
 * 绝缘子零值检测
 */
public class JueYuanZiLingZhiJianCeActivity extends BaseActivity {

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
    @BindView(R.id.tv_tower_type)
    TextView tvTowerType;
    @BindView(R.id.et_pieces)
    EditText etPieces;//脉冲零值
    @BindView(R.id.et_remark)
    EditText etRemark;//备注
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.mengban)
    RelativeLayout mengban;
    @BindView(R.id.et_type)
    EditText etType;//绝缘子型号
    @BindView(R.id.tv_line_id)
    TextView tvLineId;
    @BindView(R.id.tv_tower_id)
    TextView tvTowerId;
    @BindView(R.id.sp_verdict)
    Spinner spVerdict;
    private String line_name, jobType;
    private String tower_id;
    private String tower_name, task_id, sign, typename, id, audit_status;
    private JYZbean jyzBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zero_monitering);
        ButterKnife.bind(this);


        initData();
        initClick();
    }

    public void initClick()
    {
        etType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                jyzBean.setInsulator_type(editable.toString().trim().toString());
                jyzBean.setResults(spVerdict.getSelectedItem().toString());
                jyzBean.update();
            }
        });
        etPieces.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            jyzBean.setPieces(Double.valueOf(editable.toString()));
            jyzBean.setResults(spVerdict.getSelectedItem().toString());
            jyzBean.update();
        }
    });
        etRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                jyzBean.setRemark(editable.toString().trim().toString());
                jyzBean.setResults(spVerdict.getSelectedItem().toString());
                jyzBean.update();
            }
        });
    }

    private void initData() {
        jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        titleName.setText("绝缘子零值检测");
        line_name = getIntent().getStringExtra("line_name");
        tower_id = getIntent().getStringExtra("tower_id");
        task_id = getIntent().getStringExtra("task_id");
        tower_name = getIntent().getStringExtra("tower_name");
        audit_status = getIntent().getStringExtra("audit_status");
//        sign = getIntent().getStringExtra("sign");
        typename = getIntent().getStringExtra("typename");
        saveLineData();

        getJYZ();
        getYXtodo();
        getTask(task_id);
    }

    //保存本地本地数据
    public void saveLineData()
    {
        JYZbean  jyzBean = new JYZbean();
        jyzBean.setLine_name(line_name);
        jyzBean.setTower_id(tower_id);
        jyzBean.setTask_id(task_id);
        jyzBean.setTower_name(tower_name);
        jyzBean.setAudit_id(audit_status);
        jyzBean.save();

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
                        if (bean != null) {
                            tvTowerType.setText(bean.getTower_model());
                        } else {
                            tvTowerType.setText("无");
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @OnClick({R.id.title_back, R.id.btn_commit, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    if (etType.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写绝缘子型号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etPieces.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写绝缘子片数", Toast.LENGTH_SHORT).show();
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

    /**
     * 网络保存
     */
    private void save() {
        String tower_type = tvTowerType.getText().toString();
        String insulator_type = etType.getText().toString();
        String pieces = etPieces.getText().toString();
        String conclusion = spVerdict.getSelectedItem().toString();
        String remark = etRemark.getText().toString();

        Map<String, String> params = new HashMap<>();
        if (id != null) {
            params.put("id", id);
        }
        params.put("pieces", pieces);
        params.put("task_id", task_id);
        params.put("tower_type", tower_type);
        params.put("insulator_type", insulator_type);
        params.put("results", conclusion);
        params.put("remark", remark);
        params.put("tower_id", tower_id);
        params.put("work_time", DateUatil.getCurrTime());

//                params.put("user_id", SPUtil.getUserId(this));
//                params.put("audit_id", SPUtil.getDepId(this));
//                params.put("content", "关于" + line_name + tower_name + "的" + typename);
//                params.put("plan_type_sign", sign);
//                params.put("agents_user_id", SPUtil.getUserId(this));
        BaseRequest.getInstance().getService().upLoadInsulator(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        if (t.getCode() == 1) {
                            Toast.makeText(JueYuanZiLingZhiJianCeActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            RxRefreshEvent.publish("refreshTodo");
                            RxRefreshEvent.publish("refreshGroup");
                            if (id==null){
                                id="1111";
                            }
                        } else {
                            Toast.makeText(JueYuanZiLingZhiJianCeActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Toast.makeText(JueYuanZiLingZhiJianCeActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
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

                            Toast.makeText(JueYuanZiLingZhiJianCeActivity.this, "成功", Toast.LENGTH_SHORT).show();
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

    //查询接电电阻
    public void getJYZ() {

        ProgressDialog.show(this, false, "正在加载。。。。");
        BaseRequest.getInstance().getService()
                .getJYZ(task_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<JYZbean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<JYZbean> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            JYZbean results = t.getResults();
                            if (results != null) {
                                id = results.getId();
//                                line_name = results.getLine_name();
                                tower_name = results.getTower_name();
                                tower_id = results.getTower_id();
//                                tvLineId.setText(line_name);
//                                tvTowerId.setText(tower_name);
//                                tvTowerType.setText(results.getTower_type());
                                etType.setText(results.getInsulator_type());
                                etPieces.setText(results.getPieces() + "");
                                String[] array = getResources().getStringArray(R.array.verdict);
                                for (int i = 0; i < array.length; i++) {
                                    if (array[i].equals(results.getResults())) {
                                        spVerdict.setSelection(i);
                                    }
                                }
                                etRemark.setText(results.getRemark());

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
