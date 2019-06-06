package com.patrol.terminal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.WeekPlanDetailAdapter;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.CreateRobTaskBean;
import com.patrol.terminal.bean.DayOfWeekBean;
import com.patrol.terminal.bean.DefectBean;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GroupTaskDetailActivity extends Activity {


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
    private List<DefectBean> typeList = new ArrayList<>();
    private String year, month, day, id;
    private WeekPlanDetailAdapter adapter;
    private List<GroupTaskBean> selectList = new ArrayList<>();
    private int type = 1;
    private GroupTaskBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_plan_detail);
        ButterKnife.bind(this);
        initView();
        bean = (GroupTaskBean) getIntent().getParcelableExtra("GroupTaskBean");
        getGroupList();
    }


    private void initView() {
        titleName.setText("小组任务详情");
        String time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        day = Integer.parseInt(days[0]) + "";
        LinearLayoutManager manager = new LinearLayoutManager(this);
        monthPlanDetailRc.setLayoutManager(manager);
        adapter = new WeekPlanDetailAdapter(R.layout.item_plan_detail, typeList);
        monthPlanDetailRc.setAdapter(adapter);

    }

    //获取月计划列表
    public void getGroupList() {
        String jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
       if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER) && "0".equals(bean.getIs_rob()) && "0".equals(bean.getAllot_status())) {
            type = 1;
            taskSubmit.setVisibility(View.VISIBLE);
        } else if ("1".equals(bean.getIs_rob())) {
            type = 2;
            taskSubmit.setVisibility(View.VISIBLE);
            taskSubmit.setText("抢单");
            taskSubmit.setBackgroundColor(getResources().getColor(R.color.write_red));
            tvTableName.setVisibility(View.VISIBLE);
            tvTableName.setText("关于" + bean.getLine_name() + bean.getName() + "的抢单任务");
        }

        if ("1".equals(bean.getAllot_status())) {
            tvLineType.setVisibility(View.VISIBLE);
            tvLineType.setText("执行人 : " + bean.getWork_user_name());
        }
        tvTableName.setText(bean.getYear() + "年" + bean.getMonth() + "月" + bean.getDay() + "日班组任务");
        tvLineName.setText("线路名称 : " + bean.getLine_name());
        tvLineNo.setText("班  组 : " + bean.getDep_name());
        if (bean.getDuty_user_name()==null){
            tvLineDate.setText("小组负责人 : 暂无" );
        }else {
            tvLineDate.setText("小组负责人 :" + bean.getDuty_user_name());
        }
        tvLineTower.setText("杆  段 : " + bean.getName());

        String type_sign = bean.getType_sign();
        String[] split = type_sign.split(",");
        for (int i = 0; i < split.length; i++) {
            String type = split[i];
            DefectBean planTypeBean = new DefectBean();
            planTypeBean.setContent(StringUtil.getTypeSign(type) + "任务");
            planTypeBean.setType(0);
            typeList.add(planTypeBean);
        }
        adapter.setNewData(typeList);
    }


    @OnClick({R.id.title_back, R.id.task_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.task_submit:
                if (type == 1) {
                    createRobTask();
                } else if (type == 2) {
                    addPersonTask(SPUtil.getUserId(this), SPUtil.getUserName(this), 1);
                }
                break;
        }
    }


    //生成抢单任务
    public void createRobTask() {
        ProgressDialog.show(this, false, "正在加载。。。");
        CreateRobTaskBean createRobTaskBean = new CreateRobTaskBean();
        createRobTaskBean.setId(bean.getId());
        createRobTaskBean.setIs_rob("1");
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .createRobTask(createRobTaskBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayOfWeekBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DayOfWeekBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            type = 2;
                            taskSubmit.setText("抢单");
                            taskSubmit.setBackgroundColor(getResources().getColor(R.color.write_red));
                            Toast.makeText(GroupTaskDetailActivity.this, "成功生成抢单任务", Toast.LENGTH_SHORT).show();
                            tvTableName.setVisibility(View.VISIBLE);
                            tvTableName.setText("关于" + bean.getLine_name() + bean.getName() + "的抢单任务");
                            setResult(RESULT_OK);
                        }
                        ProgressDialog.cancle();

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    //抢单
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
                            type = 0;
                            taskSubmit.setVisibility(View.GONE);
                            tvTableName.setVisibility(View.GONE);
                            if (flag == 1) {
                                Toast.makeText(GroupTaskDetailActivity.this, "抢单成功，请到您的个人任务列表中查看", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(GroupTaskDetailActivity.this, "指派成功", Toast.LENGTH_SHORT).show();
                            }
                            tvLineType.setVisibility(View.VISIBLE);
                            tvLineType.setText("执行人 : " + username);
                            RxRefreshEvent.publish("refreshPersonal");
                            setResult(RESULT_OK);
                        } else {
                            if (flag == 1) {
                                Toast.makeText(GroupTaskDetailActivity.this, "抢单失败", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(GroupTaskDetailActivity.this, "网络连接失败，服务器异常", Toast.LENGTH_SHORT).show();
                            }

                        }
                        ProgressDialog.cancle();

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }


}
