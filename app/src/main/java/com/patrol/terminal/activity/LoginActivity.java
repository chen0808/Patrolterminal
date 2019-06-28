package com.patrol.terminal.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.LoginReqBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_user_name)
    EditText longinName;
    @BindView(R.id.et_password)
    EditText loginPsw;
    @BindView(R.id.btn_login)
    Button loginYes;

    private static final int DEP_CHOOSE_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        String userJob = (String) SPUtil.get(this, Constant.USER, Constant.USERJOBNAME, "");
        if (!userJob.equals("")) {
            startActivity(new Intent(this, NewMainActivity.class));
            finish();
        }
        longinName.setText("邓贵宝");
        loginPsw.setText("123456");
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String name = longinName.getText().toString();
        String password = loginPsw.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        ProgressDialog.show(this, false,"正在登录。。。");
        BaseRequest.getInstance().getService()
                .login(name, password/*"a0a22ac2958f9be136f3c320b6cb6a8b"*/)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<LoginReqBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<LoginReqBean> t) throws Exception {
                        Log.d("linmeng", "t.toString():" + t.toString());
                        if (t.getCode() == 1) {
                            LoginReqBean results = t.getResults();
                            SPUtil.putString(LoginActivity.this, Constant.USER, Constant.USERNAME, results.getName());
                            SPUtil.putString(LoginActivity.this, Constant.USER, Constant.USERID, results.getId());
                            SPUtil.putString(LoginActivity.this, Constant.USER, Constant.TELEPHONE, results.getTelephone());
                            SPUtil.putString(LoginActivity.this, Constant.USER, Constant.SEX, results.getSex());
                            SPUtil.putString(LoginActivity.this, Constant.USER, Constant.DEPNAME, results.getDep_name());
                            SPUtil.putString(LoginActivity.this, Constant.USER, Constant.DEPID, results.getDep_id());

                            if (results.getSysJobList() != null) {
                                String job = "";
                                String firstJob = "";
                                String jobName = "";
                                int size = results.getSysJobList().size();
                                for (int i = 0; i <size; i++) {
                                    LoginReqBean.SysJobListBean sysJobListBean = results.getSysJobList().get(i);
                                    String sign = sysJobListBean.getSign();
                                    if (i==0) {
                                        job = sign;
                                        firstJob = sign;
                                        jobName=Utils.getJobName(sign);
                                    } else {
                                        if ("3".equals(sysJobListBean.getGrade())){
                                            firstJob = sign;
                                            jobName=Utils.getJobName(sign);
                                        }else if ("2".equals(sysJobListBean.getGrade())){
                                            jobName=jobName.split("专责")[0]+"•"+Utils.getJobName(sign);
                                        }
                                        job = job + "," + sign;
                                    }
                                }
                                SPUtil.putString(LoginActivity.this, Constant.USER, Constant.JOBTYPE, job);
                                SPUtil.putString(LoginActivity.this, Constant.USER, Constant.USERJOBNAME,jobName);

                                goToMainActivity(firstJob);
                            }


//                            startActivity(new Intent(LoginActivity.this, NewMainActivity.class));
//                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case DEP_CHOOSE_REQUEST:

                    String jobType = data.getExtras().getString(Constant.RESULT);
                    SPUtil.putString(LoginActivity.this, Constant.USER, Constant.JOBTYPE, jobType);  //只选择第一种角色  TODO
                    goToMainActivity(jobType);
                    break;
            }
        }
    }

    private void goToMainActivity(String jobType) {
//        String[] JOBS = new String[results.getJobs().size()];
//        for (int i = 0; i < results.getJobs().size(); i++) {
//            JOBS[i] = Constant.JOBS + i;
//            SPUtil.putString(LoginActivity.this, Constant.USER, JOBS[i], results.getJobs().get(i));
//        }



        //角色：1.运行班班长    2.运行班组长（临时）    3.运行班班员（可能是班级的审计员）      4.检修班班长     5.检修班组长（临时）
        // 6.检修班班员（可能是班级的审计员）     7.运行专责    8.检修专责      9.主任      10.绩效专责    11.培训专责      12.汽车班班长    13.汽车班班员
        //14.保电专责           15.安全专责           16.验收专责

        switch (jobType) {
            case Constant.RUNNING_SQUAD_LEADER:          //1.运行班班长
            case Constant.RUNNING_SQUAD_TEMA_LEADER:    //2.运行班组长（临时）
            case Constant.RUNNING_SQUAD_MEMBER:          //3.运行班班员（可能是班级的审计员）
            case Constant.REFURBISHMENT_LEADER:          //4.检修班班长
            case Constant.REFURBISHMENT_TEMA_LEADER:    //5.检修班组长（临时）
            case Constant.REFURBISHMENT_MEMBER:          // 6.检修班班员（可能是班级的审计员）
            case Constant.RUNNING_SQUAD_SPECIALIZED:     //7.运行专责
            case Constant.REFURBISHMENT_SPECIALIZED:     //8.检修专责
            case Constant.RUN_SUPERVISOR:                  //9.运行主管
            case Constant.MAINTENANCE_SUPERVISOR:        //10.检修主管
                Intent intent = new Intent(LoginActivity.this, NewMainActivity.class);
                startActivity(intent);
                finish();
                break;


            case Constant.DISTRICT_MANAGERD:    //主任与副主任
                Intent intent1 = new Intent(LoginActivity.this, NewMainActivity.class);
                startActivity(intent1);
                finish();
                break;

            case Constant.ACHIEVEMENTS_SPECIALIZED:     //11.绩效专责
                Intent intent2 = new Intent(LoginActivity.this, NewMainActivity.class);
                startActivity(intent2);
                finish();
                break;

            case Constant.TRAINING_SPECIALIZED:         //12.培训专责
                Intent intent3 = new Intent(LoginActivity.this, NewMainActivity.class);
                startActivity(intent3);
                finish();
                break;

            case Constant.CAR_SQUAD_LEADER:            //13.汽车班班长
            case Constant.CAR_SQUAD_MEMBER:            //14.汽车班班员
                Intent intent4 = new Intent(LoginActivity.this, NewMainActivity.class);
                startActivity(intent4);
                finish();
                break;

            case Constant.POWER_CONSERVATION_SPECIALIZED:
            case Constant.SAFETY_SPECIALIZED:
            case Constant.ACCEPTANCE_CHECK_SPECIALIZED:
                startActivity(new Intent(LoginActivity.this, NewMainActivity.class));
                finish();
                break;
        }
    }
}
