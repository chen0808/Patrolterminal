package com.patrol.terminal.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.patrol.terminal.bean.GTQXCLbean;
import com.patrol.terminal.bean.GTQXCLbean_Table;
import com.patrol.terminal.bean.HwcwBean;
import com.patrol.terminal.bean.JDDZbean_Table;
import com.patrol.terminal.bean.JYZbean;
import com.patrol.terminal.bean.JYZbean_Table;
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
 * 绝缘子零值检测
 */
public class JueYuanZiLingZhiJianCeActivity extends BaseActivity implements TextWatcher {

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
    private String line_id;
    private String tower_model;
    private PersonalTaskListBean personalTaskListBean;
    private  boolean isSave=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zero_monitering);
        ButterKnife.bind(this);


        initData();

    }

    public void initClick()
    {
        etType.addTextChangedListener(this);
        etPieces.addTextChangedListener(this);
        etRemark.addTextChangedListener(this);
        spVerdict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] array1 = getResources().getStringArray(R.array.verdict);
                jyzBean.setResults(array1[position]);
                jyzBean.update();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initData() {
        jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        titleName.setText("绝缘子零值检测");
        line_id = getIntent().getStringExtra("line_id");
        line_name = getIntent().getStringExtra("line_name");
        tower_id = getIntent().getStringExtra("tower_id");
        task_id = getIntent().getStringExtra("task_id");
        tower_name = getIntent().getStringExtra("tower_name");
        tower_model = getIntent().getStringExtra("tower_model");
        audit_status = getIntent().getStringExtra("audit_status");
        sign = getIntent().getStringExtra("sign");
        typename = getIntent().getStringExtra("typename");
        jyzBean = new JYZbean();
         getdata();


    }
    public void getdata(){
        //获取接电电阻本地信息
        List<JYZbean> jyZbeans = SQLite.select().from(JYZbean.class)
                .where(JYZbean_Table.task_id.eq(task_id), JYZbean_Table.user_id.eq(SPUtil.getUserId(this)))
                //.orderBy(OrderBy.fromNameAlias(NameAlias.of("duty_user_id,line_id,name")))
                .queryList();
        //获取个人任务本地信息
        List<PersonalTaskListBean> personalTaskListBeans = SQLite.select().from(PersonalTaskListBean.class)
                .where(PersonalTaskListBean_Table.id.eq(task_id), PersonalTaskListBean_Table.user_id.eq(SPUtil.getUserId(this)))
                .queryList();
        if (personalTaskListBeans.size()>0){
            personalTaskListBean = personalTaskListBeans.get(0);
        }
        if (jyZbeans ==null|| jyZbeans.size()==0){
            jyzBean.setLine_name(line_name);
            jyzBean.setTower_id(tower_id);
            jyzBean.setPlan_type_sign(sign);
            jyzBean.setTask_id(task_id);
            jyzBean.setTower_model(tower_model);
            jyzBean.setTower_name(tower_name);
            jyzBean.setAudit_id(audit_status);
            jyzBean.setUser_id(SPUtil.getUserId(this));
            jyzBean.save();
        }else {
            jyzBean=jyZbeans.get(0);
        }
        if (Utils.isNetworkConnected(this)){
            getJYZ();
            getYXtodo();
        }else {
            showView();
        }

    }

    private void showView() {
        line_name = jyzBean.getLine_name();
        tower_name = jyzBean.getTower_name();
        tower_id = jyzBean.getTower_id();
        tvLineId.setText(line_name);
        tvTowerId.setText(tower_name);
        if (jyzBean.getTower_model()==null||"".equals(jyzBean.getTower_model())){
            tvTowerType.setText("无");
        }else {
            tvTowerType.setText(jyzBean.getTower_model());
        }
        etType.setText(jyzBean.getInsulator_type());
        etPieces.setText(jyzBean.getPieces()==0?"":jyzBean.getPieces()+"");
        String[] array = getResources().getStringArray(R.array.verdict);
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(jyzBean.getResults())) {
                spVerdict.setSelection(i);
            }
        }
        etRemark.setText(jyzBean.getRemark());
        initClick();
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
                    if (id==null||"".equals(id) || audit_status.equals("4")){
                        save();
                    }else {
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
//                save();
                break;
        }
    }

    /**
     * 网络保存
     */
    private void save() {
        ProgressDialog.show(this,true,"正在保存。。。");
        String tower_type = tvTowerType.getText().toString();
        String insulator_type = etType.getText().toString();
        String pieces = etPieces.getText().toString();
        String conclusion = spVerdict.getSelectedItem().toString();
        String remark = etRemark.getText().toString();

        Map<String, String> params = new HashMap<>();
        if (id != null) {
            params.put("id", id);
        }
        params.put("line_id", line_id);
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
                            RxRefreshEvent.publish("refreshTodo");
                            RxRefreshEvent.publish("refreshGroup");
                            if (id==null){
                                id="1111";
                            }
                            saveTodoAudit("1");
                        } else {
                            Toast.makeText(JueYuanZiLingZhiJianCeActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Toast.makeText(JueYuanZiLingZhiJianCeActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                        ProgressDialog.cancle();
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
        } else if ("2".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审批");
            }
            mengban.setVisibility(View.VISIBLE);
        } else if ("0".equals(audit_status) || "4".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("提交");
            }
            mengban.setVisibility(View.GONE);
        } else {
            titleSetting.setVisibility(View.GONE);
            mengban.setVisibility(View.VISIBLE);
        }
    }

    //保存待办信息
    public void saveTodoAudit(String state) {
        ProgressDialog.show(this,true,"正在保存。。。");
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
                                jyzBean=results;
                                jyzBean.update();
//                                tvLineId.setText(line_name);
//                                tvTowerId.setText(tower_name);
//                                tvTowerType.setText(results.getTower_type());


                            }
                            showView();
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
            String insulator_type = etType.getText().toString();
            String pieces = etPieces.getText().toString();
            String conclusion = spVerdict.getSelectedItem().toString();
            String remark = etRemark.getText().toString();

            if (personalTaskListBean!=null&&!isSave){
                isSave=true;
                personalTaskListBean.setIs_save("0");
                personalTaskListBean.update();
                setResult(RESULT_OK);
            }

            jyzBean.setPieces("".equals(pieces)?0:Double.parseDouble(pieces));
            jyzBean.setInsulator_type( insulator_type);
            jyzBean.setResults(conclusion);
            jyzBean.setRemark(remark);
            jyzBean.setWork_time( DateUatil.getCurrTime());
            jyzBean.update();
        }
    };
}
