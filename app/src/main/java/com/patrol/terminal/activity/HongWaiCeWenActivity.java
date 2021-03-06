package com.patrol.terminal.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.patrol.terminal.bean.HwcwBean_Table;
import com.patrol.terminal.bean.JDDZbean_Table;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.PersonalTaskListBean_Table;
import com.patrol.terminal.bean.SaveTodoReqbean;
import com.patrol.terminal.bean.TaskBean;
import com.patrol.terminal.bean.TypeBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.ProgressDialog;
import com.raizlabs.android.dbflow.sql.language.SQLite;

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
public class HongWaiCeWenActivity extends BaseActivity implements TextWatcher {
    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.tv_line_id)
    TextView tvLineId;
    @BindView(R.id.tv_tower_id)
    TextView tvTowerId;
    @BindView(R.id.tv_tower_type)
    TextView tvTowerType;
    @BindView(R.id.sp_link_type)
    Spinner spLinkType;
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
    private HwcwBean localBean;
    private String[] array;
    private PersonalTaskListBean personalTaskListBean;
    private  boolean isSave=false;
    private String tower_model;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infrared_thermometry);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("红外测温");
        array = getResources().getStringArray(R.array.link_type);
        line_id = getIntent().getStringExtra("line_id");
        line_name = getIntent().getStringExtra("line_name");
        tower_id = getIntent().getStringExtra("tower_id");
        task_id = getIntent().getStringExtra("task_id");
        tower_name = getIntent().getStringExtra("tower_name");
        tower_model = getIntent().getStringExtra("tower_model");
        user_id = getIntent().getStringExtra("user_id");
        audit_status = getIntent().getStringExtra("audit_status");
        sign = getIntent().getStringExtra("sign");
        typename = getIntent().getStringExtra("typename");
        jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        localBean = new HwcwBean();

        spLinkType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                localBean.setConnection_type(array[position]);
                localBean.update();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getdata();

    }

    private void getdata() {
        HwcwBean bean = SQLite.select().from(HwcwBean.class).where(HwcwBean_Table.task_id.is(task_id), JDDZbean_Table.user_id.eq(SPUtil.getUserId(this))).querySingle();
           personalTaskListBean = SQLite.select().from(PersonalTaskListBean.class)
                .where(PersonalTaskListBean_Table.id.eq(task_id), JDDZbean_Table.user_id.eq(SPUtil.getUserId(this)))
                .querySingle();

        if (bean==null){
            localBean.setLine_name(line_name);
            localBean.setTask_id(task_id);
            localBean.setTower_id(tower_id);
            localBean.setTower_name(tower_name);
            localBean.setTower_model(tower_model);
            localBean.setUser_id(SPUtil.getUserId(this));
            localBean.save();
        }else {
            localBean  =bean;
        }
        if (Utils.isNetworkConnected(this)){
            getYXtodo();
            getHWCW();
        }else {
            initLocalData(localBean);
        }

    }

    private void addTextChangeListener() {
        etBUpTemperature.addTextChangedListener(this);
        etSUpTemperature.addTextChangedListener(this);
        etBMiddleTemperature.addTextChangedListener(this);
        etSMiddleTemperature.addTextChangedListener(this);
        etBDownTemperature.addTextChangedListener(this);
        etSDownTemperature.addTextChangedListener(this);
        etSansTemperature.addTextChangedListener(this);
        etCheckResult.addTextChangedListener(this);
        etRemarks.addTextChangedListener(this);
    }





    private void initLocalData(HwcwBean localByTaskId) {
        if (localByTaskId != null) {
            tvLineId.setText(localByTaskId.getLine_name());
            tvTowerId.setText(localByTaskId.getTower_name());
            if (localByTaskId.getTower_model()==null||"".equals(localByTaskId.getTower_model())){
                tvTowerType.setText("无");
            }else {
                tvTowerType.setText(localByTaskId.getTower_model());
            }
            etBUpTemperature.setText(localByTaskId.getUp_big()==0?"":localByTaskId.getUp_big()+"");
            etSUpTemperature.setText(localByTaskId.getUp_small()==0?"":localByTaskId.getUp_small()+"");
            etBMiddleTemperature.setText(localByTaskId.getMid_big()==0?"":localByTaskId.getMid_big()+"");
            etSMiddleTemperature.setText(localByTaskId.getMid_small() ==0?"":localByTaskId.getMid_small()+"");
            etBDownTemperature.setText(localByTaskId.getDown_big()==0?"":localByTaskId.getDown_big()+"");
            etSDownTemperature.setText(localByTaskId.getDown_small()==0?"":localByTaskId.getDown_small()+"");
            etSansTemperature.setText(localByTaskId.getTemperature()==0?"":localByTaskId.getTemperature()+"");
            etCheckResult.setText(localByTaskId.getResults() ==0?"":localByTaskId.getResults()+"");
            etRemarks.setText(localByTaskId.getRemark());
            String type = localByTaskId.getConnection_type();
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(type)) {
                    spLinkType.setSelection(i);
                }
            }
        }
        addTextChangeListener();
    }





    public void getYXtodo() {
        if ("1".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审批");
            }
            mengban.setVisibility(View.VISIBLE);
//            btnCommit.setVisibility(View.GONE);
        } else if ("2".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审批");
            }
            mengban.setVisibility(View.VISIBLE);
//            btnCommit.setVisibility(View.GONE);
        } else if ("0".equals(audit_status) || "4".equals(audit_status)) {
            if (user_id.equals(SPUtil.getUserId(this))) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("提交");
                mengban.setVisibility(View.GONE);
            }else {
                titleSetting.setVisibility(View.GONE);
                mengban.setVisibility(View.VISIBLE);
            }
        } else {
            titleSetting.setVisibility(View.GONE);
            mengban.setVisibility(View.VISIBLE);
//            btnCommit.setVisibility(View.GONE);
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
                    if (etBUpTemperature.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写上相大号侧", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etSUpTemperature.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写上相小号侧", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etBMiddleTemperature.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写中相大号侧", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etSMiddleTemperature.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写中相小号侧", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etBDownTemperature.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写下相大号侧", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etSDownTemperature.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写下相小号侧", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etSansTemperature.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写环境温度", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etCheckResult.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写检查结果", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (id == null || "".equals(id) || audit_status.equals("4")) {
                        saveData();
                    } else {
                        saveTodoAudit("1");
                    }
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
//                saveData();
                break;
        }
    }

    private void saveData() {
        ProgressDialog.show(this, false, "正在加载。。。");
        String towerType = tvTowerType.getText().toString();
        String linkType = spLinkType.getSelectedItem().toString();
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

//        params.put("user_id", SPUtil.getUserId(this));
//        params.put("audit_id", SPUtil.getDepId(this));
//        params.put("content", "关于" + line_name + tower_name + "的" + typename);
//        params.put("plan_type_sign", sign);
//        params.put("agents_user_id", SPUtil.getUserId(this));

        BaseRequest.getInstance().getService().upLoadInfrared(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<HwcwBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<HwcwBean> t) throws Exception {
                        if (t.getCode() == 1) {
                            if (id == null) {
                                id = "123321";
                            }
                            saveTodoAudit("1");   //同意
                            RxRefreshEvent.publish("refreshTodo");
                            RxRefreshEvent.publish("refreshGroup");
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(HongWaiCeWenActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //保存待办信息
    public void saveTodoAudit(String state) {
        ProgressDialog.show(this, false, "正在加载。。。");
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
                            Toast.makeText(HongWaiCeWenActivity.this, "成功", Toast.LENGTH_SHORT).show();
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
                                localBean=results;
                                localBean.update();
//                                tvLineId.setText(line_name);
//                                tvTowerId.setText(tower_name);
                            }
                            initLocalData(localBean);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }


    @Override
    public void afterTextChanged(Editable s) {
        if (delayRun != null) {
            //每次editText有变化的时候，则移除上次发出的延迟线程
            handler.removeCallbacks(delayRun);
        }

        handler.postDelayed(delayRun, 1000);
    }

    private Handler handler = new Handler();

    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            String towerType = tvTowerType.getText().toString();
            String linkType = spLinkType.getSelectedItem().toString();
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
            if (personalTaskListBean!=null&&!isSave){
                isSave=true;
                personalTaskListBean.setIs_save("0");
                personalTaskListBean.update();
                setResult(RESULT_OK);
            }

            localBean.setConnection_type(linkType);
            localBean.setUp_big("".equals(bUp)?0:Double.parseDouble(bUp));
            localBean.setUp_small("".equals(sUp)?0:Double.parseDouble(sUp) );
            localBean.setMid_big("".equals(bMiddle)?0:Double.parseDouble(bMiddle) );
            localBean.setMid_small("".equals(sMiddle)?0:Double.parseDouble(sMiddle) );
            localBean.setDown_big("".equals(bDown)?0:Double.parseDouble(bDown));
            localBean.setDown_small("".equals(sDown)?0:Double.parseDouble(sDown));
            localBean.setTemperature("".equals(sans)?0:Double.parseDouble(sans));
            localBean.setResults("".equals(checkResult)?0:Double.parseDouble(checkResult));
            localBean.setWork_time( DateUatil.getCurrTime());

            localBean.setRemark(remarks);

        }
    };
}
