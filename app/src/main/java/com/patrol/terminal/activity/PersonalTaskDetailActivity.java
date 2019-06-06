package com.patrol.terminal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.PersonalTaskDetailAdapter;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.AddressBookLevel2;
import com.patrol.terminal.bean.DayOfWeekBean;
import com.patrol.terminal.bean.DepUserBean;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.PlanTypeBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PersonalTaskDetailActivity extends Activity {


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
    @BindView(R.id.tv_table_name)
    TextView tvTableName;
    @BindView(R.id.tv_line_name)
    TextView tvLineName;
    @BindView(R.id.tv_line_no)
    TextView tvLineNo;
    @BindView(R.id.tv_line_date)
    TextView tvLineDate;
    @BindView(R.id.tv_line_type)
    TextView tvLineType;
    @BindView(R.id.tv_line_tower)
    TextView tvLineTower;
    @BindView(R.id.month_plan_detail_rc)
    RecyclerView monthPlanDetailRc;
    @BindView(R.id.task_submit)
    TextView taskSubmit;
    private int year, month, day;
    private List<PlanTypeBean> typeList = new ArrayList<>();
    private PersonalTaskDetailAdapter monthPlanDetailAdapter;
    private GroupTaskBean bean;
    private List<PersonalTaskListBean> results = new ArrayList<>();
    private List<AddressBookLevel2> namelist = new ArrayList();
    private List<DepUserBean> depUserBeanList;
    private String[] names;
    private List<GroupTaskBean> selectList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_plan_detail);
        ButterKnife.bind(this);
        initView();
        initdata();
        getPersonalList();
    }

    private void initdata() {
        bean = getIntent().getParcelableExtra("bean");
        SPUtil.put(this, "ids", "task_id", bean.getId());
        tvLineType.setVisibility(View.VISIBLE);

        tvTableName.setText(bean.getName());
        tvLineName.setText("线路名称 : " + bean.getLine_name());
        tvLineNo.setText("班  组 : " + SPUtil.getDepName(PersonalTaskDetailActivity.this));
        if ("12".equals(bean.getType_sign()) || "13".equals(bean.getType_sign())) {
            tvLineDate.setText("作业内容 : " + bean.getName());
        } else {
            tvLineDate.setText("杆塔名称 : " + bean.getName());
        }
        tvLineType.setText("执行人 : " + bean.getWork_user_name());
        String jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if (jobType.contains(Constant.RUNNING_SQUAD_LEADER) && ("12".equals(bean.getType_sign()) || "13".equals(bean.getType_sign()))&&bean.getWork_user_name()==null) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("指派");
            tvLineType.setText("执行人 : 暂无" );
            getPersonal();
        }
        year = bean.getYear();
        month = bean.getMonth();
        day = bean.getDay();

    }


    private void initView() {
        tvLineTower.setVisibility(View.GONE);

        titleName.setText("个人任务详情");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        monthPlanDetailRc.setLayoutManager(manager);
        monthPlanDetailAdapter = new PersonalTaskDetailAdapter(R.layout.item_plan_detail, results);
        monthPlanDetailRc.setAdapter(monthPlanDetailAdapter);
        monthPlanDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PersonalTaskListBean bean = results.get(position);
                Intent intent = new Intent();
                intent.putExtra("line_id", bean.getLine_id());
                intent.putExtra("line_name", bean.getLine_name());
                intent.putExtra("tower_id", bean.getTower_id());
                intent.putExtra("tower_name", bean.getTower_name());
                intent.putExtra("task_id", bean.getId());
                intent.putExtra("sign", bean.getType_sign());
                intent.putExtra("audit_status", bean.getAudit_status());
                intent.putExtra("typename", bean.getType_name());
                switch (bean.getType_sign()) {
                    case "1":
                    case "2":
                    case "7":
                    case "11":
                        intent.setClass(PersonalTaskDetailActivity.this, PatrolRecordActivity.class);
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "tower_id", bean.getTower_id());
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "line_id", bean.getLine_id());
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "line_name", bean.getLine_name());
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "tower_name", bean.getTower_name());
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "find_user_id", bean.getUser_id());
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "find_user_name", bean.getUser_name());
                        break;
                    case "5":
                        intent.setClass(PersonalTaskDetailActivity.this, HongWaiCeWenActivity.class);
                        break;
                    case "3":
                        intent.setClass(PersonalTaskDetailActivity.this, JiediDianZuCeLiangActicivity.class);
                        break;
                    case "10":
                        intent.setClass(PersonalTaskDetailActivity.this, JueYuanZiLingZhiJianCeActivity.class);
                        break;
                    case "6":
                        intent.setClass(PersonalTaskDetailActivity.this, XieGanTaQingXieCeWenActivity.class);
                        break;
                    case "12":
                        intent.setClass(PersonalTaskDetailActivity.this, MonitoringRecordActivity.class);
                        intent.putExtra("bean", bean);
                        break;
                    case "13":
                        intent.setClass(PersonalTaskDetailActivity.this, CheckActivity.class);
                        intent.putExtra("content", bean.getCheck_report());
                        break;


                }
                startActivityForResult(intent, 25);
            }
        });
    }


    //获取个人任务列表
    public void getPersonalList() {

        ProgressDialog.show(this, false, "正在加载。。。");
        BaseRequest.getInstance().getService()
                .getPersonalListOfGroup(year + "", month + "", day + "", bean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PersonalTaskListBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<PersonalTaskListBean>> t) throws Exception {
                        results = t.getResults();
                        monthPlanDetailAdapter.setNewData(results);

                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 25 && resultCode == RESULT_OK) {
            getPersonalList();
            RxRefreshEvent.publish("refreshPersonal");
        }
    }

    //获取每个班组组员列表
    public void getPersonal() {

        BaseRequest.getInstance().getService()
                .getPersonal(year + "", month + "", day + "", SPUtil.getDepId(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DepUserBean>>(this) {


                    @Override
                    protected void onSuccees(BaseResult<List<DepUserBean>> t) throws Exception {
                        depUserBeanList = t.getResults();
                        names = new String[depUserBeanList.size()];
                        for (int i = 0; i < depUserBeanList.size(); i++) {
                            names[i] = depUserBeanList.get(i).getName();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    public void showSingleChooseDialog(String[] names) {
        new AlertDialog.Builder(this).setTitle("选择组员").setItems(names, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                DepUserBean depUserBean = depUserBeanList.get(which);
                addPersonTask(depUserBean.getId(), depUserBean.getName(), 2);
                dialog.dismiss();
            }
        }).show();
    }

    //指派个人任务
    public void addPersonTask(String user_id, String username, int flag) {
        ProgressDialog.show(this, false, "正在加载。。。");
        bean.setGroup_list_id(bean.getId());
        bean.setUser_id(user_id);
        bean.setUser_name(username);
        selectList.add(bean);
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .addPersonTask(selectList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayOfWeekBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DayOfWeekBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            Toast.makeText(PersonalTaskDetailActivity.this, "指派成功", Toast.LENGTH_SHORT).show();
                            titleSetting.setVisibility(View.GONE);
                            tvLineType.setVisibility(View.VISIBLE);
                            tvLineType.setText("执行人 : " + username);
                            RxRefreshEvent.publish("refreshPersonal");
                            getPersonalList();
                            setResult(RESULT_OK);
                        } else {
                            Toast.makeText(PersonalTaskDetailActivity.this, "网络连接失败，服务器异常", Toast.LENGTH_SHORT).show();

                        }
                        ProgressDialog.cancle();

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
                if (names==null){
                    Toast.makeText(this,"暂未获取人员列表信息,请稍后再试",Toast.LENGTH_SHORT).show();
                    return;
                }
                showSingleChooseDialog(names);
                break;
        }
    }
}
