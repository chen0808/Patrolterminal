package com.patrol.terminal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.PerformanceAdapter;
import com.patrol.terminal.adapter.PersonalTaskDetailAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.AddressBookLevel2;
import com.patrol.terminal.bean.DayOfWeekBean;
import com.patrol.terminal.bean.DepUserBean;
import com.patrol.terminal.bean.GTQXCLbean;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.bean.GroupTaskBean_Table;
import com.patrol.terminal.bean.HwcwBean;
import com.patrol.terminal.bean.HwcwBean_Table;
import com.patrol.terminal.bean.JDDZbean;
import com.patrol.terminal.bean.JDDZbean_Table;
import com.patrol.terminal.bean.JYZbean;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.PersonalTaskListBean_Table;
import com.patrol.terminal.bean.PlanTypeBean;
import com.patrol.terminal.bean.SaveTodoReqbean;
import com.patrol.terminal.bean.TypeBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 个人任务详情
 */
public class PersonalTaskDetailActivity extends BaseActivity {


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

    private int year, month, day;
    private List<PlanTypeBean> typeList = new ArrayList<>();
    private PersonalTaskDetailAdapter monthPlanDetailAdapter;
    private GroupTaskBean bean;
    private List<PersonalTaskListBean> results = new ArrayList<>();
    private List<AddressBookLevel2> namelist = new ArrayList();
    private List<DepUserBean> depUserBeanList;
    private String[] names;
    private List<GroupTaskBean> selectList = new ArrayList<>();
    private String userId;
    private List<PersonalTaskListBean> selectPersonal =new ArrayList<>();
    private int saveNum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);
        initView();
        bean = getIntent().getParcelableExtra("bean");
        String from = getIntent().getStringExtra("from");

        //无网络直接设置上去
        //initdata();

        if ("todoPersonal".equals(from)) {
            String task_id = getIntent().getStringExtra("task_id");
            getGroupList(task_id);
        } else {
            initdata();
        }
    }

    private void initdata() {
        userId = SPUtil.getUserId(this);
        SPUtil.put(this, "ids", "task_id", bean.getId());
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

        tvLineDate.setText("日期："+bean.getYear()+"年"+bean.getMonth()+"月"+bean.getDay()+"日");

        tvTableName.setText(bean.getName());
        tvLineName.setText("线路名称 : " + bean.getLine_name());
        tvLineNo.setText("班  组 : " + SPUtil.getDepName(PersonalTaskDetailActivity.this));

        String jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        titleSetting.setVisibility(View.VISIBLE);
        if (jobType.contains(Constant.RUNNING_SQUAD_LEADER) && ("12".equals(bean.getType_sign()) || "13".equals(bean.getType_sign())) && bean.getWork_user_name() == null) {

            titleSettingTv.setText("指派");
            getPersonal();
        }else {

            titleSettingTv.setText("提交");
        }
        year = bean.getYear();
        month = bean.getMonth();
        day = bean.getDay();
        getDbPersonalList();   //add by linmeng
        if (Utils.isNetworkConnected(this)){
            getPersonalList();
        }


    }


    private void initView() {

        tvLineTower.setVisibility(View.GONE);
        titleName.setText("个人任务详情");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        monthPlanDetailRc.setLayoutManager(manager);
        monthPlanDetailAdapter = new PersonalTaskDetailAdapter(R.layout.item_plan_detail_offline, results,this);
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
                intent.putExtra("tower_model", bean.getTower_model());
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
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "tower_id", bean.getTower_id() == null ? "" : bean.getTower_id());
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "line_id", bean.getLine_id() == null ? "" : bean.getLine_id());
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "line_name", bean.getLine_name() == null ? "" : bean.getLine_name());
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "tower_name", bean.getTower_name() == null ? "" : bean.getTower_name());
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "find_user_id", bean.getUser_id() == null ? "" : bean.getUser_id());
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "find_user_name", bean.getUser_name() == null ? "" : bean.getUser_name());
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

    public void getDbPersonalList() {
        List<PersonalTaskListBean> personalTaskListBeanList = SQLite.select().from(PersonalTaskListBean.class)
                .where(PersonalTaskListBean_Table.year.eq(Integer.valueOf(year)), PersonalTaskListBean_Table.month.eq(Integer.valueOf(month)),
                        PersonalTaskListBean_Table.day.eq(Integer.valueOf(day)), PersonalTaskListBean_Table.group_list_id.eq(bean.getId()))
                //.orderBy(OrderBy.fromNameAlias(NameAlias.of("line_id,name")))
                .queryList();

        if (personalTaskListBeanList != null) {
            results=personalTaskListBeanList;
            if (!Utils.isNetworkConnected(this)){
            monthPlanDetailAdapter.setNewData(results);}
        }

    }

    //获取个人任务列表
    public void getPersonalList() {
        BaseRequest.getInstance().getService()
                .getPersonalListOfGroup(year + "", month + "", day + "", bean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PersonalTaskListBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<PersonalTaskListBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        List<PersonalTaskListBean> personalTaskListBeanList = t.getResults();
                        if (personalTaskListBeanList!=null||personalTaskListBeanList.size()>0){
                            for (int i = 0; i < personalTaskListBeanList.size(); i++) {
                                PersonalTaskListBean personalTaskListBean = personalTaskListBeanList.get(i);
                                for (int j = 0; j < results.size(); j++) {
                                    PersonalTaskListBean bean = results.get(j);
                                    if (personalTaskListBean.getId().equals(bean.getId())&&"0".equals(personalTaskListBean.getAudit_status())){
                                        personalTaskListBean.setIs_save(bean.getIs_save());
                                    }
                                }
                                saveToDatebase(personalTaskListBean);
                            }
                        }
                         results=personalTaskListBeanList;
                        monthPlanDetailAdapter.setNewData(results);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    private void saveToDatebase(PersonalTaskListBean bean) {
        PersonalTaskListBean personalTaskListBean = new PersonalTaskListBean();
        personalTaskListBean.setId(bean.getId());
        personalTaskListBean.setGroup_list_id(bean.getGroup_list_id());
        personalTaskListBean.setName(bean.getName());
        personalTaskListBean.setType_id(bean.getType_id());
        personalTaskListBean.setType_sign(bean.getType_sign());
        personalTaskListBean.setType_name(bean.getType_name());
        personalTaskListBean.setPlan_type(bean.getPlan_type());
        personalTaskListBean.setLine_id(bean.getLine_id());
        personalTaskListBean.setLine_name(bean.getLine_name());
        personalTaskListBean.setDep_id(bean.getDep_id());
        personalTaskListBean.setDep_name(bean.getDep_name());
        personalTaskListBean.setUser_id(bean.getUser_id());
        personalTaskListBean.setUser_name(bean.getUser_name());
        personalTaskListBean.setTower_id(bean.getTower_id());
        personalTaskListBean.setTower_name(bean.getTower_name());
        personalTaskListBean.setYear(bean.getYear());
        personalTaskListBean.setMonth(bean.getMonth());
        personalTaskListBean.setWeek(bean.getWeek());
        personalTaskListBean.setDay(bean.getDay());
        personalTaskListBean.setAudit_status(bean.getAudit_status());
        personalTaskListBean.setDone_status(bean.getDone_status());
        personalTaskListBean.setDone_time(bean.getDone_time());
        personalTaskListBean.setSub_time(bean.getSub_time());
        personalTaskListBean.setCheck_report(bean.getCheck_report());


        List<PersonalTaskListBean> existBeans = SQLite.select().from(PersonalTaskListBean.class)
                .where(PersonalTaskListBean_Table.id.eq(bean.getId()))
                .queryList();

        if (existBeans.size() > 0) {   //数据存在
            personalTaskListBean.update();
        }else {
            personalTaskListBean.save();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 25 && resultCode == RESULT_OK) {
            getDbPersonalList();   //add by linmeng
            if (Utils.isNetworkConnected(this)){
                getPersonalList();
            }
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
        //ProgressDialog.show(this, false, "正在加载。。。");     //由于Dialog无网络时候消失不了，所以暂时注销
//        bean.setGroup_list_id(bean.getId());
        bean.setWork_user_name(user_id);
        bean.setWork_user_id(username);
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
                            taskWorkName.setText("执行人 : " + username);
                            RxRefreshEvent.publish("refreshPersonal");

                            getDbPersonalList();   //add by linmeng
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
                String s = titleSettingTv.getText().toString();
                if ("指派".equals(s)){
                    if (names == null) {
                        Toast.makeText(this, "暂未获取人员列表信息,请稍后再试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showSingleChooseDialog(names);
                }else {
                    ProgressDialog.show(this, true, "正在加载。。。");
                    List<PersonalTaskListBean> data = monthPlanDetailAdapter.getData();
                    selectPersonal.clear();
                    saveNum=0;
                    for (int i = 0; i < data.size(); i++) {
                        PersonalTaskListBean personalTaskListBean = data.get(i);
                        if (personalTaskListBean.isCheck()){
                            selectPersonal.add(personalTaskListBean);
                            String type_sign = personalTaskListBean.getType_sign();
                            String id = personalTaskListBean.getId();
                            switch (type_sign){
                                case "3": //提交接电电阻
                                    saveJDDZ(id,type_sign);
                                    break;
                                case "5": //提交红外测温
                                    saveHWCW(id,type_sign);
                                    break;
                                case "6": //提交杆塔倾斜
                                    saveGTQXCL(id,type_sign);
                                    break;
                                case "10"://提交绝缘子
                                    saveJYZ(id,type_sign);
                                    break;
                            }
                        }
                    }
                    if (selectPersonal.size()==0){
                        Toast.makeText(this,"请先选中待上传的任务",Toast.LENGTH_SHORT).show();
                        ProgressDialog.cancle();
                    }
                }

                break;
        }
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
                        initdata();
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }
    public void saveHWCW(String id,String sign){
        HwcwBean bean = SQLite.select().from(HwcwBean.class).where(HwcwBean_Table.task_id.is(id), JDDZbean_Table.user_id.eq(userId)).querySingle();
        BaseRequest.getInstance().getService().upLoadInfrared(bean).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<HwcwBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<HwcwBean> t) throws Exception {
                        if (t.getCode() == 1) {
                            saveTodoAudit(id,sign);   //同意
                        }

                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }
    public void saveJDDZ(String id,String sign){
        JDDZbean bean = SQLite.select().from(JDDZbean.class).where(HwcwBean_Table.task_id.is(id), JDDZbean_Table.user_id.eq(userId)).querySingle();
        BaseRequest.getInstance().getService().upLoadResistance(bean).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<HwcwBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<HwcwBean> t) throws Exception {
                        if (t.getCode() == 1) {
                            saveTodoAudit(id,sign);   //同意
                        }

                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }
    public void saveGTQXCL(String id,String sign){
        GTQXCLbean bean = SQLite.select().from(GTQXCLbean.class).where(HwcwBean_Table.task_id.is(id), JDDZbean_Table.user_id.eq(userId)).querySingle();
        BaseRequest.getInstance().getService().upLoadTowerBias(bean).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<HwcwBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<HwcwBean> t) throws Exception {
                        if (t.getCode() == 1) {
                            saveTodoAudit(id,sign);   //同意
                        }

                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }
    public void saveJYZ(String id,String sign){
        JYZbean bean = SQLite.select().from(JYZbean.class).where(HwcwBean_Table.task_id.is(id), JDDZbean_Table.user_id.eq(userId)).querySingle();
        BaseRequest.getInstance().getService().upLoadInsulator(bean).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<HwcwBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<HwcwBean> t) throws Exception {
                        if (t.getCode() == 1) {
                            saveTodoAudit(id,sign);   //同意
                        }

                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }
    //保存待办信息
    public void saveTodoAudit(String id,String sign) {

        SaveTodoReqbean saveTodoReqbean = new SaveTodoReqbean();
        saveTodoReqbean.setAudit_status("1");
        saveTodoReqbean.setId(id);
        saveTodoReqbean.setType_sign(sign);
        saveTodoReqbean.setFrom_user_id(SPUtil.getUserId(this));
        BaseRequest.getInstance().getService()
                .saveTodoAudit(saveTodoReqbean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TypeBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<TypeBean> t) throws Exception {
                        saveNum++;
                        if (saveNum==selectPersonal.size()){
                            RxRefreshEvent.publish("refreshTodo");
                            RxRefreshEvent.publish("refreshGroup");
                            getPersonalList();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

}
