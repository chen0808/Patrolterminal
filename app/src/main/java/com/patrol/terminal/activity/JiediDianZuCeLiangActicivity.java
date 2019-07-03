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
import com.patrol.terminal.bean.HwcwBean;
import com.patrol.terminal.bean.JDDZbean;
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
 * 接地电阻测量
 */
public class JiediDianZuCeLiangActicivity extends BaseActivity implements TextWatcher {

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
    @BindView(R.id.tv_tower_name)
    TextView tvTowerName;
    @BindView(R.id.tv_tower_type)
    TextView tvTowerType;
    @BindView(R.id.et_a_resistor)
    EditText etAResistor;
    @BindView(R.id.et_b_resistor)
    EditText etBResistor;
    @BindView(R.id.et_c_resistor)
    EditText etCResistor;
    @BindView(R.id.et_d_resistor)
    EditText etDResistor;
    @BindView(R.id.sp_weather)
    Spinner spWeather;
    @BindView(R.id.sp_verdict)
    Spinner spVerdict;
    @BindView(R.id.et_note)
    EditText etNote;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.mengban)
    RelativeLayout mengban;
    private String line_name, jobType, audit_status;
    private String tower_id;
    private String tower_name, task_id, sign, typename, id;
    private JDDZbean results;
    private String tower_model;
    private String line_id;
    private PersonalTaskListBean personalTaskListBean;
   private  boolean isSave=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grounded_electronic_measurement);
        ButterKnife.bind(this);
        initview();
        initedit();
    }

    private void initedit() {
        etAResistor.addTextChangedListener(this);
        etBResistor.addTextChangedListener(this);
        etCResistor.addTextChangedListener(this);
        etDResistor.addTextChangedListener(this);
        etNote.addTextChangedListener(this);
        spVerdict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] array1 = getResources().getStringArray(R.array.verdict);
                results.setResults(array1[position]);
                results.update();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initview() {
        jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        titleName.setText("接地电阻测量");
        results = new JDDZbean();
        line_name = getIntent().getStringExtra("line_name");
        line_id = getIntent().getStringExtra("line_id");
        tower_id = getIntent().getStringExtra("tower_id");
        task_id = getIntent().getStringExtra("task_id");
        tower_name = getIntent().getStringExtra("tower_name");
        audit_status = getIntent().getStringExtra("audit_status");
        sign = getIntent().getStringExtra("sign");
        typename = getIntent().getStringExtra("typename");
        tower_model = getIntent().getStringExtra("tower_model");

        getdata();
    }

    public void getdata() {
        //.orderBy(OrderBy.fromNameAlias(NameAlias.of("duty_user_id,line_id,name")))
        List<JDDZbean> jddZbeans = SQLite.select().from(JDDZbean.class)
                .where(JDDZbean_Table.task_id.eq(task_id), JDDZbean_Table.user_id.eq(SPUtil.getUserId(this)))
                //.orderBy(OrderBy.fromNameAlias(NameAlias.of("duty_user_id,line_id,name")))
                .queryList();
        List<PersonalTaskListBean> personalTaskListBeans = SQLite.select().from(PersonalTaskListBean.class)
                .where(PersonalTaskListBean_Table.id.eq(task_id), JDDZbean_Table.user_id.eq(SPUtil.getUserId(this)))
                .queryList();
        if (personalTaskListBeans.size()>0){
            personalTaskListBean = personalTaskListBeans.get(0);
        }
        if (jddZbeans == null || jddZbeans.size() == 0) {
            results.setLine_name(line_name);
            results.setTask_id(task_id);
            results.setTower_id(tower_id);
            results.setTower_name(tower_name);
            results.setTower_model(tower_model);
            results.setUser_id(SPUtil.getUserId(this));
            results.save();
        } else {
            results = jddZbeans.get(0);
        }
        if (Utils.isNetworkConnected(this)) {
            getJDDZ();
            getYXtodo();
        }else {
            showView();
        }

    }

    private void showView() {
        id = results.getId();
        line_name = results.getLine_name();
        tower_name = results.getTower_name();
        tower_id = results.getTower_id();
        tvLineId.setText(results.getLine_name());
        tvTowerName.setText(results.getTower_name());
        if (results.getTower_model()==null||"".equals(results.getTower_model())){
            tvTowerType.setText("无");
        }else {
            tvTowerType.setText(results.getTower_model());
        }

        etAResistor.setText(results.getMeasure_a() == 0 ? "" : results.getMeasure_a() + "");
        etBResistor.setText(results.getMeasure_b() == 0 ? "" : results.getMeasure_b() + "");
        etCResistor.setText(results.getMeasure_c() == 0 ? "" : results.getMeasure_c() + "");
        etDResistor.setText(results.getMeasure_d() == 0 ? "" : results.getMeasure_d() + "");
        String[] array = getResources().getStringArray(R.array.weather);
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(results.getWeather())) {
                spWeather.setSelection(i);
            }
        }
        String[] array1 = getResources().getStringArray(R.array.verdict);
        for (int i = 0; i < array1.length; i++) {
            if (array1[i].equals(results.getResults())) {
                spVerdict.setSelection(i);
            }
        }
        etNote.setText(results.getRemark());
    }



    @OnClick({R.id.title_back, R.id.btn_commit, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    if (etAResistor.getText().toString().isEmpty() || etBResistor.getText().toString().isEmpty() || etCResistor.getText().toString().isEmpty() || etDResistor.getText().toString().isEmpty()) {
                        Toast.makeText(this, "请填写电阻测量值", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (id == null || "".equals(id) || audit_status.equals("4")) {
                        save();
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
//                save();
                break;
        }
    }

    private void save() {
        ProgressDialog.show(this, false, "正在上传。。。。");
        String towerType = tvTowerType.getText().toString();
        String aResistor = etAResistor.getText().toString();
        String bResistor = etBResistor.getText().toString();
        String cResistor = etCResistor.getText().toString();
        String dResistor = etDResistor.getText().toString();
        String weather = spWeather.getSelectedItem().toString();
        String verdict = spVerdict.getSelectedItem().toString();
        String note = etNote.getText().toString();

        Map<String, String> params = new HashMap<>();
        if (id != null) {
            params.put("id", id);
        }
//        params.put("line_name", line_name);
        params.put("line_id", line_id);
        params.put("tower_id", tower_id);
        params.put("task_id", task_id);
        params.put("tower_type", towerType);
        params.put("measure_a", aResistor);
        params.put("line_name", line_name);
        params.put("measure_a", aResistor);
        params.put("measure_b", bResistor);
        params.put("measure_c", cResistor);
        params.put("measure_d", dResistor);
        params.put("weather", weather);
        params.put("results", verdict);
        params.put("remark", note);
        params.put("work_time", DateUatil.getCurrTime());

//        params.put("user_id", SPUtil.getUserId(this));
//        params.put("audit_id", SPUtil.getDepId(this));
//        params.put("content", "关于" + line_name + tower_name + "的" + typename);
//        params.put("plan_type_sign", sign);
//        params.put("agents_user_id", SPUtil.getUserId(this));

        BaseRequest.getInstance().getService().upLoadResistance(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {

                        if (t.getCode() == 1) {
                            if (id == null) {
                                id = "111";
                            }
                            saveTodoAudit("1");
                            RxRefreshEvent.publish("refreshTodo");
                            RxRefreshEvent.publish("refreshGroup");
                        } else {
                            Toast.makeText(JiediDianZuCeLiangActicivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(JiediDianZuCeLiangActicivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getYXtodo() {

//        if ("0".equals(audit_status)) {
//            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
//                mengban.setVisibility(View.VISIBLE);
//                btnCommit.setVisibility(View.GONE);
//                titleSetting.setVisibility(View.GONE);
//            } else if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
//                mengban.setVisibility(View.GONE);
//                btnCommit.setVisibility(View.VISIBLE);
//            } else {
//                mengban.setVisibility(View.VISIBLE);
//                btnCommit.setVisibility(View.GONE);
//                titleSetting.setVisibility(View.GONE);
//            }
//        } else if ("1".equals(audit_status)) {
//            if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
//                titleSetting.setVisibility(View.VISIBLE);
//                titleSettingTv.setText("审批");
//            }
//            mengban.setVisibility(View.VISIBLE);
//            btnCommit.setVisibility(View.GONE);
//        } else if ("2".equals(audit_status)) {
//            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
//                titleSetting.setVisibility(View.VISIBLE);
//                titleSettingTv.setText("审批");
//            }
//            mengban.setVisibility(View.VISIBLE);
//            btnCommit.setVisibility(View.GONE);
//        } else if ("4".equals(audit_status)) {
//            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
//                titleSetting.setVisibility(View.GONE);
//                mengban.setVisibility(View.VISIBLE);
//                btnCommit.setVisibility(View.GONE);
//            } else if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
//                mengban.setVisibility(View.GONE);
//                btnCommit.setVisibility(View.VISIBLE);
//            } else {
//                titleSetting.setVisibility(View.GONE);
//                mengban.setVisibility(View.VISIBLE);
//                btnCommit.setVisibility(View.GONE);
//            }
//
//        } else {
//            titleSetting.setVisibility(View.GONE);
//            mengban.setVisibility(View.VISIBLE);
//            btnCommit.setVisibility(View.GONE);
//        }

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
        ProgressDialog.show(this, false, "正在上传。。。。");
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

                            Toast.makeText(JiediDianZuCeLiangActicivity.this, "成功", Toast.LENGTH_SHORT).show();
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
    public void getJDDZ() {

        ProgressDialog.show(this, false, "正在加载。。。。");
        BaseRequest.getInstance().getService()
                .getJDDZ(task_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<JDDZbean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<JDDZbean> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {

                            if (t.getResults() != null) {
                                results = t.getResults();
                                results.update();
//                                etTowerId.setText(results.getTower_type());
//                                etLineId.setText(line_name);
//                                etToweName.setText(tower_name);
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
            String towerType = tvTowerType.getText().toString();
            String aResistor = etAResistor.getText().toString();
            String bResistor = etBResistor.getText().toString();
            String cResistor = etCResistor.getText().toString();
            String dResistor = etDResistor.getText().toString();
            String weather = spWeather.getSelectedItem().toString();
            String verdict = spVerdict.getSelectedItem().toString();
            String note = etNote.getText().toString();


            if (personalTaskListBean!=null&&!isSave){
                isSave=true;
                personalTaskListBean.setIs_save("0");
                personalTaskListBean.update();
                setResult(RESULT_OK);
            }
            results.setTower_type(towerType);
            results.setResults(verdict);
            results.setWork_time(DateUatil.getCurrTime());
            results.setRemark(note);
            results.setMeasure_a("".endsWith(aResistor) ? 0 : Double.parseDouble(aResistor));
            results.setMeasure_b("".endsWith(bResistor) ? 0 : Double.parseDouble(bResistor));
            results.setMeasure_c("".endsWith(cResistor) ? 0 : Double.parseDouble(cResistor));
            results.setMeasure_d("".endsWith(dResistor) ? 0 : Double.parseDouble(dResistor));
            results.setWeather(weather);
            results.update();
        }
    };
}
