package com.patrol.terminal.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.GroupTaskDetailAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.CreateRobTaskBean;
import com.patrol.terminal.bean.DayOfWeekBean;
import com.patrol.terminal.bean.DefectBean;
import com.patrol.terminal.bean.DepUserBean;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.bean.SaveTodoReqbean;
import com.patrol.terminal.bean.TypeBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//小组任务详情
public class GroupTaskDetailActivity extends BaseActivity {


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
    @BindView(R.id.task_group_name)
    TextView taskGroupName;
    @BindView(R.id.task_work_name)
    TextView taskWorkName;
    @BindView(R.id.tv_line_tower)
    TextView tvLineTower;
    @BindView(R.id.month_plan_detail_rc)
    RecyclerView monthPlanDetailRc;
    @BindView(R.id.task_submit)
    TextView taskSubmit;
    private List<DefectBean> typeList = new ArrayList<>();
    private String year, month, day, id;
    private GroupTaskDetailAdapter adapter;
    private List<GroupTaskBean> selectList = new ArrayList<>();
    private int type = 1;
    private GroupTaskBean bean;
    private List<DepUserBean> personalList;
    private String[] personals;
    private AlertDialog personalDialog;
    private String from;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);
        initView();
        bean = (GroupTaskBean) getIntent().getParcelableExtra("GroupTaskBean");
        from = getIntent().getStringExtra("from");
        if ("todoGroup".equals(from)) {
            String task_id = getIntent().getStringExtra("task_id");
            getGroupList(task_id);
        } else if ("todoRob".equals(from)){
            titleName.setText("退还任务详情");
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("审批");
            String task_id = getIntent().getStringExtra("task_id");
            getGroupList(task_id);
        }else {
            getGroupList(bean.getId());
        }

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
        adapter = new GroupTaskDetailAdapter(R.layout.item_plan_detail, typeList);
        monthPlanDetailRc.setAdapter(adapter);

    }

    //获取月计划列表
    public void getGroupList() {
        String jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        if ((jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER) || jobType.contains(Constant.RUNNING_SQUAD_LEADER)) && "0".equals(bean.getIs_rob()) && "0".equals(bean.getAllot_status())) {
            type = 1;
            taskSubmit.setVisibility(View.VISIBLE);

            if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                titleSettingTv.setText("指派");
                titleSetting.setVisibility(View.VISIBLE);
                getPersonal();
            }

        } else if ("1".equals(bean.getIs_rob())&& "0".equals(bean.getAllot_status())) {

            type = 2;
            taskSubmit.setVisibility(View.VISIBLE);
            taskSubmit.setText("抢单");
            taskSubmit.setBackgroundColor(getResources().getColor(R.color.base_status_bar));
            tvTableName.setVisibility(View.VISIBLE);
            tvTableName.setText("关于" + bean.getLine_name() + bean.getName() + "的抢单任务");
            if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER) || jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                titleSettingTv.setText("撤销");
                titleSetting.setVisibility(View.VISIBLE);
            } else {
                titleSetting.setVisibility(View.GONE);
            }
        } else {
            tvTableName.setVisibility(View.GONE);
            taskSubmit.setVisibility(View.GONE);
            if (!"todoRob".equals(from)){
                titleSetting.setVisibility(View.GONE);
            }

        }

        tvLineDate.setText("日期："+bean.getYear()+"年"+bean.getMonth()+"月"+bean.getDay()+"日");
            String work_user_name = bean.getWork_user_name();
            if (work_user_name == null || "".equals(work_user_name)) {
                taskWorkName.setText("执行人：未指定");
            } else {
                taskWorkName.setText("执行人：" + work_user_name);
            }

            if (bean.getDuty_user_name() == null || "".equals(bean.getDuty_user_name())) {
                taskGroupName.setText("小组负责人：未指定");
            } else {
                taskGroupName.setText("小组负责人：" + bean.getDuty_user_name());
            }


        tvLineName.setText("线路名称：" + bean.getLine_name());
        tvLineNo.setText("班  组：" + bean.getDep_name());
        tvLineTower.setText("杆  段：" + bean.getName());
        typeList.clear();
        String type_sign = bean.getType_sign();
        String[] split = type_sign.split(",");
        for (int i = 0; i < split.length; i++) {
            String type = split[i];
            DefectBean planTypeBean = new DefectBean();
            planTypeBean.setContent(StringUtil.typeSigns[Integer.valueOf(type) - 1] + "任务");
            planTypeBean.setType(Integer.parseInt(bean.getAllot_status()));
            typeList.add(planTypeBean);
        }
        adapter.setNewData(typeList);
    }


    @OnClick({R.id.title_back, R.id.task_submit, R.id.title_setting_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting_tv:
                String name = titleSettingTv.getText().toString();
                if ("指派".equals(name)) {
                    showPersonalGroup();
                } else if ("撤销".equals(name)) {
                    cancleRobTask();
                }else if ("审批".equals(name)){
                    CancelOrOkDialog dialog = new CancelOrOkDialog(this, "是否同意", "不同意", "同意") {
                        @Override
                        public void ok() {
                            super.ok();
                            robBack(bean.getId(), "2");   //同意
                            dismiss();
                        }

                        @Override
                        public void cancle() {
                            super.cancle();
                            robBack(bean.getId(), "3");  //不同意
                            dismiss();
                        }
                    };
                    dialog.show();
                }

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

    //抢单任务退还
    public void robBack(String id,String  state) {
        ProgressDialog.show(this,true,"正在加载。。。");
        SaveTodoReqbean reqbean=new SaveTodoReqbean();
        reqbean.setAudit_status(state);
        reqbean.setFrom_user_id(SPUtil.getUserId(this));
        reqbean.setFrom_user_name(SPUtil.getUserName(this));
        reqbean.setId(id);
        BaseRequest.getInstance().getService()
                .robBack(reqbean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TypeBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<TypeBean> t) throws Exception {
                        RxRefreshEvent.publish("refreshTodo");
                        if ("2".equals(state)){
                            Toast.makeText(GroupTaskDetailActivity.this,"审批通过",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(GroupTaskDetailActivity.this,"审批不通过",Toast.LENGTH_SHORT).show();
                        }
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();

                    }
                });
    }

    //生成抢单任务
    public void createRobTask() {
        ProgressDialog.show(this, false, "正在加载。。。");
        List<CreateRobTaskBean> list = new ArrayList();
        CreateRobTaskBean createRobTaskBean = new CreateRobTaskBean();
        createRobTaskBean.setId(bean.getId());
        createRobTaskBean.setIs_rob("1");
        createRobTaskBean.setGroup_id(bean.getGroup_id());
        try {
            createRobTaskBean.setLine_name(bean.getLine_name());
        } catch (Exception e) {
            e.printStackTrace();
        }
        createRobTaskBean.setYear(bean.getYear() + "");
        createRobTaskBean.setMonth(bean.getMonth() + "");
        createRobTaskBean.setDay(bean.getDay() + "");
        createRobTaskBean.setName(bean.getName());
        createRobTaskBean.setFrom_user_id(SPUtil.getUserId(this));
        createRobTaskBean.setFrom_user_name(SPUtil.getUserName(this));
        list.add(createRobTaskBean);
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .createRobTasks(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayOfWeekBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DayOfWeekBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            type = 2;
                            taskSubmit.setText("抢单");
                            taskSubmit.setBackgroundColor(getResources().getColor(R.color.base_status_bar));
                            Toast.makeText(GroupTaskDetailActivity.this, "生成抢单任务", Toast.LENGTH_SHORT).show();
                            tvTableName.setVisibility(View.VISIBLE);
                            tvTableName.setText("关于" + bean.getLine_name() + bean.getName() + "的抢单任务");
                            titleSettingTv.setText("撤销");
                            titleSetting.setVisibility(View.VISIBLE);
                            RxRefreshEvent.publish("refreshTodo");
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

    //撤销抢单任务
    public void cancleRobTask() {
        ProgressDialog.show(this, false, "正在加载。。。");
        List<CreateRobTaskBean> list = new ArrayList();
        CreateRobTaskBean createRobTaskBean = new CreateRobTaskBean();
        createRobTaskBean.setId(bean.getId());
        createRobTaskBean.setIs_rob("0");
        createRobTaskBean.setGroup_id(bean.getGroup_id());
        createRobTaskBean.setLine_name(bean.getLine_name());
        createRobTaskBean.setYear(bean.getYear() + "");
        createRobTaskBean.setMonth(bean.getMonth() + "");
        createRobTaskBean.setDay(bean.getDay() + "");
        createRobTaskBean.setName(bean.getName());
        createRobTaskBean.setFrom_user_id(SPUtil.getUserId(this));
        createRobTaskBean.setFrom_user_name(SPUtil.getUserName(this));
        list.add(createRobTaskBean);
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .cancleRobTasks(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayOfWeekBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DayOfWeekBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            type = 2;
                            taskSubmit.setText("生成抢单任务");
                            taskSubmit.setBackgroundColor(getResources().getColor(R.color.write_red));
                            Toast.makeText(GroupTaskDetailActivity.this, "撤销成功", Toast.LENGTH_SHORT).show();
                            tvTableName.setVisibility(View.GONE);
                            titleSettingTv.setText("指派");
                            titleSetting.setVisibility(View.VISIBLE);
                            getPersonal();
                            RxRefreshEvent.publish("refreshTodo");
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
        ProgressDialog.show(this, false, "正在抢单。。。");
//        bean.setGroup_list_id(bean.getId());
        bean.setWork_user_id(user_id);
        bean.setWork_user_name(username);
        selectList.add(bean);
        //获取月计划列表
        BaseRequest.getInstance().getService()
                .addPersonTask(selectList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayOfWeekBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DayOfWeekBean>> t) throws Exception {
                        type = 0;
                        taskSubmit.setVisibility(View.GONE);
                        tvTableName.setVisibility(View.GONE);
                        if (flag == 1) {
                            Toast.makeText(GroupTaskDetailActivity.this, "抢单成功，请到您的个人任务列表中查看", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GroupTaskDetailActivity.this, "指派成功", Toast.LENGTH_SHORT).show();
                        }
                        taskWorkName.setText("执行人 : " + username);
                        RxRefreshEvent.publish("refreshPersonal");
                        RxRefreshEvent.publish("refreshTodo");
                        RxRefreshEvent.publish("refreshNum");
                        setResult(RESULT_OK);
                        getGroupList(bean.getId());
                    }

                    @Override
                    protected void onCodeError(BaseResult<List<DayOfWeekBean>> t) throws Exception {
                        super.onCodeError(t);
                        getGroupList(bean.getId());
                        RxRefreshEvent.publish("refreshTodo");
                        setResult(RESULT_OK);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    //获取小组任务详情
    public void getGroupList(String id) {
        ProgressDialog.show(this, false, "正在加载。。。");
        BaseRequest.getInstance().getService()
                .getGroupDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<GroupTaskBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<GroupTaskBean> t) throws Exception {
                        bean = t.getResults();
                        getGroupList();
                        ProgressDialog.cancle();
                    }


                    @Override
                    protected void onCodeError(BaseResult<GroupTaskBean> t) throws Exception {
                        super.onCodeError(t);
                        ProgressDialog.cancle();

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    //获取每个班组组员列表
    public void getPersonal() {

        BaseRequest.getInstance().getService()
                .getGroupPersonal(year, month, day, SPUtil.getDepId(this), SPUtil.getUserId(this), "2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DepUserBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DepUserBean>> t) throws Exception {
                        personalList = t.getResults();
                        personals = new String[personalList.size()];
                        for (int i = 0; i < personalList.size(); i++) {
                            personals[i] = personalList.get(i).getUser_name();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    public void showPersonalGroup() {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择负责人");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setSingleChoiceItems(personals, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int options1) {

                String name = personals[options1];
                for (int i = 0; i < personalList.size(); i++) {
                    DepUserBean depUserBean = personalList.get(i);
                    if (name.equals(depUserBean.getUser_name())) {
                        savaGroupTask(depUserBean);
                    }
                }
                personalDialog.dismiss();
            }
        });

        personalDialog = alertBuilder.create();
        personalDialog.show();
    }

    //保存
    public void savaGroupTask(DepUserBean depUserBean) {
        List<GroupTaskBean> selectBean = new ArrayList<>();
        bean.setWork_user_id(depUserBean.getUser_id());
        bean.setWork_user_name(depUserBean.getUser_name());
        bean.setFrom_user_id(SPUtil.getUserId(this));
        bean.setFrom_user_name(SPUtil.getUserName(this));
        selectBean.add(bean);
        BaseRequest.getInstance().getService()
                .addPersonTask(selectBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DayOfWeekBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DayOfWeekBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            setResult(RESULT_OK);
                            RxRefreshEvent.publish("refreshPersonal");
                            RxRefreshEvent.publish("refreshTodo");
                            RxRefreshEvent.publish("refreshNum");
                            Toast.makeText(GroupTaskDetailActivity.this, "指派成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(GroupTaskDetailActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
}
