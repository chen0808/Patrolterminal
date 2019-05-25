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
import com.patrol.terminal.bean.JYZbean;
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
 * 绝缘子零值检测
 */
public class
JueYuanZiLingZhiJianCeActivity extends BaseActivity {

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
    @BindView(R.id.tower_name)
    TextView towerName;
    @BindView(R.id.et_tower_type)
    EditText etTowerType;
    @BindView(R.id.et_pieces)
    EditText etPieces;
    @BindView(R.id.et_conclusion)
    EditText etConclusion;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.mengban)
    RelativeLayout mengban;
    @BindView(R.id.et_jyz_type)
    EditText etJyzType;
    private String line_name, jobType;
    private String tower_id;
    private String tower_name, task_id, sign, typename, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zero_monitering);
        ButterKnife.bind(this);
        initview();
    }


    private void initview() {
        jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        titleName.setText("绝缘子零值检测");
        line_name = getIntent().getStringExtra("line_name");
        tower_id = getIntent().getStringExtra("tower_id");
        task_id = getIntent().getStringExtra("task_id");
        tower_name = getIntent().getStringExtra("tower_name");
        sign = getIntent().getStringExtra("sign");
        typename = getIntent().getStringExtra("typename");
        lineName.setText(line_name);
        towerName.setText(tower_name);
        getJYZ();
        getYXtodo();
    }

    @OnClick({R.id.title_back, R.id.btn_commit,R.id.title_setting})
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
                        saveTodoAudit("1");   //同意
                        dismiss();
                    }

                    @Override
                    public void cancel() {
                        super.cancel();
                        saveTodoAudit("2");  //不同意
                        dismiss();
                    }
                };
                dialog.show();
                break;
            case R.id.btn_commit:
                String tower_type = etTowerType.getText().toString();
                String insulator_type = etJyzType.getText().toString();
                String pieces = etPieces.getText().toString();
                String conclusion = etConclusion.getText().toString();
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

                params.put("user_id", SPUtil.getUserId(this));
                params.put("audit_id", SPUtil.getDepId(this));
                params.put("content", "关于" + line_name + tower_name + "的" + typename);
                params.put("plan_type_sign", sign);
                params.put("agents_user_id", SPUtil.getUserId(this));
                BaseRequest.getInstance().getService().upLoadInsulator(params).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver(this) {
                            @Override
                            protected void onSuccees(BaseResult t) throws Exception {
                                if (t.getCode()==1){
                                Toast.makeText(JueYuanZiLingZhiJianCeActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                                finish();
                                }else {
                                    Toast.makeText(JueYuanZiLingZhiJianCeActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                                Toast.makeText(JueYuanZiLingZhiJianCeActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }


    public void getYXtodo() {
        BaseRequest.getInstance().getService()
                .getYXOnetodo(task_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TodoListBean>>(this) {

                    @Override
                    protected void onSuccees(BaseResult<List<TodoListBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            List<TodoListBean> results = t.getResults();
                            if (results.size() > 0) {
                                TodoListBean todoListBean = results.get(0);
                                String audit_status = todoListBean.getAudit_status();
                                if ("0".equals(audit_status)) {
                                    if (jobType.equals(Constant.RUNNING_SQUAD_LEADER)) {
                                        titleSetting.setVisibility(View.VISIBLE);
                                        titleSettingTv.setText("审批");
                                    }
                                    mengban.setVisibility(View.VISIBLE);
                                    btnCommit.setVisibility(View.GONE);
                                } else if ("2".equals(audit_status)) {
                                    if (jobType.equals(Constant.RUNNING_SQUAD_MEMBER) || jobType.equals(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
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
                            } else {
                                if (jobType.equals(Constant.RUNNING_SQUAD_MEMBER) || jobType.equals(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                                    mengban.setVisibility(View.GONE);
                                    btnCommit.setVisibility(View.VISIBLE);
                                } else {
                                    mengban.setVisibility(View.VISIBLE);
                                    btnCommit.setVisibility(View.GONE);
                                    titleSetting.setVisibility(View.GONE);
                                }

                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    //保存待办信息
    public void saveTodoAudit(String state) {

        SaveTodoReqbean saveTodoReqbean = new SaveTodoReqbean();

        saveTodoReqbean.setAudit_status(state);
        saveTodoReqbean.setTask_id(task_id);

        BaseRequest.getInstance().getService()
                .saveTodoAudit(saveTodoReqbean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TypeBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<TypeBean> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            Toast.makeText(JueYuanZiLingZhiJianCeActivity.this, "审批成功", Toast.LENGTH_SHORT).show();
                            RxRefreshEvent.publish("todo");
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
                                line_name = results.getLine_name();
                                tower_name = results.getTower_name();
                                tower_id = results.getTower_id();
                                lineName.setText(line_name);
                                towerName.setText(tower_name);
                                etTowerType.setText(results.getTower_type());
                                etJyzType.setText(results.getInsulator_type());
                                etPieces.setText(results.getPieces()+"");
                                etConclusion.setText(results.getResults());
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
