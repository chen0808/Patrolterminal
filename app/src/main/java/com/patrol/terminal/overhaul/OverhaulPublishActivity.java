package com.patrol.terminal.overhaul;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.WeekTaskZzPublishAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.OverhaulSendUserBean;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.bean.OverhaulZZSendBean;
import com.patrol.terminal.bean.WeekTaskToPersonBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.NoScrollListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*检修专责发布周检修工作*/
public class OverhaulPublishActivity extends BaseActivity {
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
    @BindView(R.id.power_specialized)
    TextView powerSpecialized;
    @BindView(R.id.safe_specialized)
    TextView safeSpecialized;
    @BindView(R.id.check_specialized)
    TextView checkSpecialized;
    @BindView(R.id.send_btn)
    Button sendBtn;
    OverhaulYearBean overhaulYearBean;
    @BindView(R.id.work_task_lv)
    NoScrollListView workTaskLv;
    @BindView(R.id.task_ll)
    LinearLayout taskLl;

    private String year, month, week;

    private List<WeekTaskToPersonBean> weekTaskToPersonBeans =  new ArrayList<>();
    private WeekTaskZzPublishAdapter mWeekTaskPublishAdapter;
    private String[] weekTaskContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overhaul_publish_weekly_activity);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        String time = SPUtil.getString(this, "date", "overhaulTime", DateUatil.getTime(new Date(System.currentTimeMillis())));
        String[] years = time.split("年");  //2019年8月第3周
        String[] months = years[1].split("月");
        String [] weeks = months[1].split("周");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        week = weeks[0].substring(1, weeks[0].length());

        overhaulYearBean = getIntent().getParcelableExtra("bean");
        weekTaskContents = getIntent().getStringArrayExtra("weekTaskContents");    //分发的任务内容

        mWeekTaskPublishAdapter = new WeekTaskZzPublishAdapter(this);
        workTaskLv.setAdapter(mWeekTaskPublishAdapter);

        getAllSendToPerson();
    }

    private String eleUserId;       //保电专责ID
    private String eleUserName;    //保电专责Name
    private String userId1;        //安全专责ID
    private String userName1;     //安全专责Name
    private String userId2;        //验收专责ID
    private String userName2;     //验收专责Name

    private void getAllSendToPerson() {
        Log.w("linmeng", "getAllSendToPerson!");
        weekTaskToPersonBeans.clear();
        BaseRequest.getInstance().getService()
                .getSendOverhaulUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulSendUserBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulSendUserBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            for (int i = 0; i < t.getResults().size(); i++) {
                                OverhaulSendUserBean bean = t.getResults().get(i);
                                if (bean.getSysJobList().size() > 0) {
                                    for (int i1 = 0; i1 < bean.getSysJobList().size(); i1++) {
                                        String job_sign = bean.getSysJobList().get(i1).getSign();
                                        String userName = bean.getName();
                                        String userId = bean.getId();
                                        if (job_sign.equals(Constant.POWER_CONSERVATION_SPECIALIZED)) {
                                            powerSpecialized.setText(userName);
                                            eleUserId = userId;
                                            eleUserName = userName;
                                        } else if (job_sign.equals(Constant.SAFETY_SPECIALIZED)) {
                                            safeSpecialized.setText(userName);
                                            userId1 = userId;
                                            userName1 = userName;
                                        } else if (job_sign.equals(Constant.ACCEPTANCE_CHECK_SPECIALIZED)) {
                                            checkSpecialized.setText(userName);
                                            userId2 = userId;
                                            userName2 = userName;
                                        } else if (job_sign.equals(Constant.REFURBISHMENT_LEADER)) {
//                                            monitorSpecialized.setText(userName);
//                                            overhaulSendUserBeans.add(bean);

                                            for (int j = 0; j < weekTaskContents.length; j++) {
                                                WeekTaskToPersonBean weekTaskToPersonBean = new WeekTaskToPersonBean();
                                                weekTaskToPersonBean.setNum(j + 1);
                                                weekTaskToPersonBean.setContent(weekTaskContents[j]);
                                                weekTaskToPersonBean.setUserName(userName);
                                                weekTaskToPersonBean.setUserId(userId);
                                                weekTaskToPersonBeans.add(weekTaskToPersonBean);
                                            }
                                            mWeekTaskPublishAdapter.setData(weekTaskToPersonBeans);
                                        }
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    private void sendToUser() {
        OverhaulZZSendBean overhaulZZSendBean = new OverhaulZZSendBean();
        overhaulZZSendBean.setId(overhaulYearBean.getId());
        overhaulZZSendBean.setWeek_audit_status("2");
        overhaulZZSendBean.setEle_user_id(eleUserId);
        overhaulZZSendBean.setEle_user_name(eleUserName);
        overhaulZZSendBean.setSafe_user_id(userId1);
        overhaulZZSendBean.setSafe_user_name(userName1);
        overhaulZZSendBean.setCheck_user_id(userId2);
        overhaulZZSendBean.setCheck_user_name(userName2);
        overhaulZZSendBean.setUserId1(userId1);
        overhaulZZSendBean.setUserName1(userName1);
        overhaulZZSendBean.setUserId2(userId2);
        overhaulZZSendBean.setUserName2(userName2);

        List<OverhaulZZSendBean.TaskInfo> taskInfos = new ArrayList<>();
        for (int i = 0; i < weekTaskToPersonBeans.size(); i++) {
            OverhaulZZSendBean.TaskInfo taskInfo =  new OverhaulZZSendBean.TaskInfo();
            taskInfo.setTask_content(weekTaskToPersonBeans.get(i).getContent());
            taskInfo.setTask_status("1");
            taskInfo.setUserId3(weekTaskToPersonBeans.get(i).getUserId());
            taskInfo.setUserName3(weekTaskToPersonBeans.get(i).getUserName());
            taskInfo.setYear(year);
            taskInfo.setMonth(month);
            taskInfo.setWeek(week);
            taskInfos.add(taskInfo);
        }
        overhaulZZSendBean.setTaskList(taskInfos);

        BaseRequest.getInstance().getService()
                .sendOverhaulZzPlan(overhaulZZSendBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulSendUserBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulSendUserBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            //result = t.getResults();
                            Toast.makeText(OverhaulPublishActivity.this, "分发成功！", Toast.LENGTH_SHORT).show();
                            RxRefreshEvent.publish("publishRepair");
                            finish();
                        } else {
                            Toast.makeText(OverhaulPublishActivity.this, "分发失败！", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

    }


    @OnClick({R.id.send_btn, R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.send_btn:
                sendToUser();
                break;
        }
    }
}
