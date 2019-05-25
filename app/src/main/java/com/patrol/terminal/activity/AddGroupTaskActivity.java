package com.patrol.terminal.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.AddGroupTaskAdapter;
import com.patrol.terminal.adapter.GroupTaskSelectAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.AddGroupTaskReqBean;
import com.patrol.terminal.bean.DangerBean;
import com.patrol.terminal.bean.DefectBean;
import com.patrol.terminal.bean.DepUserBean;
import com.patrol.terminal.bean.GroupOfDayBean;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.TaskLineBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.FlowGroupView;
import com.patrol.terminal.widget.NoScrollListView;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddGroupTaskActivity extends BaseActivity {


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
    @BindView(R.id.group_task_type)
    TextView groupTaskType;
    @BindView(R.id.group_task_group)
    TextView groupTaskGroup;
    @BindView(R.id.add_group_people)
    ImageView addGroupPeople;
    @BindView(R.id.incontinuity_tower)
    FlowGroupView incontinuityTower;
    @BindView(R.id.task_personal_ll)
    LinearLayout taskPersonalLl;
    @BindView(R.id.group_task_type_lv)
    NoScrollListView groupTaskTypeLv;
    @BindView(R.id.add_more_iv)
    ImageView addMoreIv;
    @BindView(R.id.trouble_more)
    LinearLayout troubleMore;
    @BindView(R.id.group_task_no_data)
    TextView groupTaskNoData;
    @BindView(R.id.manth_plan_detail_lv)
    NoScrollListView manthPlanDetailLv;
    @BindView(R.id.mon_plan_type_ll)
    LinearLayout monPlanTypeLl;
    @BindView(R.id.month_yes)
    TextView monthYes;
    private List<TaskLineBean> selectType = new ArrayList<>();
    private AddGroupTaskAdapter adapter;
    private GroupTaskSelectAdapter selectAdapter;
    private GroupOfDayBean results;
    private Disposable subscribe;
    private List<DefectBean> list1 = new ArrayList<>();
    private List<String> lineName = new ArrayList<>();

    private String year;
    private String month;
    private String week;
    private int type = 0;
    private String lineId;
    private List<GroupOfDayBean.PatrolBean> taskList = new ArrayList<>();

    private List<String> typeName = new ArrayList<>();
    private List<LineTypeBean> typeList = new ArrayList<>();
    private String type_id;
    private List<String> userList = new ArrayList<>();
    private String time;
    private String day;
    private String day_id;
    private List<DepUserBean> personalList;
    private List<AddGroupTaskReqBean.UsersBean> addPeoList = new ArrayList<>();
    private String duty_user_name;
    private String duty_user_id;
    private List<GroupOfDayBean.PatrolBean> patrol;
    private List<DefectBean> danger;
    private List<DefectBean> defect;
    private List<GroupOfDayBean.PatrolBean> patSelectList=new ArrayList<>();
    private List<DefectBean> danSelectList=new ArrayList<>();
    private List<DefectBean> defSelectList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_task);
        ButterKnife.bind(this);
        initview();
        getLineType();
        getDayList();
        getPersonal();
    }

    private void initview() {
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate();
        titleName.setText("制定班组任务");
        adapter = new AddGroupTaskAdapter(this, taskList);
        selectAdapter = new GroupTaskSelectAdapter(this, selectType);
        groupTaskTypeLv.setAdapter(adapter);
        manthPlanDetailLv.setAdapter(selectAdapter);
         subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.startsWith("addgroup")) {
                    String[] split = type.split("@");
                    String type_val = split[1];
                    String id = split[2];
                    TaskLineBean bean = new TaskLineBean();
                    bean.setL_id(split[2]);
                    bean.setName(split[3]);
                    bean.setTime(split[4]);
                    selectType.add(bean);
                    selectAdapter.setData(selectType);
                    if ("1".equals(type_val)||"2".equals(type_val)){
                        for (int i = 0; i < patrol.size(); i++) {
                            GroupOfDayBean.PatrolBean patrolBean = patrol.get(i);
                            String patrolBeanId = patrolBean.getId();
                            if (id.equals(patrolBeanId)){
                                patSelectList.add(patrolBean);
                            }
                        }
                    }else if ("3".equals(type_val)){
                        for (int i = 0; i < defect.size(); i++) {
                            DefectBean defectBean = defect.get(i);
                            if (id.equals(defectBean.getId())){
                                defSelectList.add(defectBean);
                            }
                        }
                    }else if ("4".equals(type_val)){
                        for (int i = 0; i < danger.size(); i++) {
                            DefectBean defectBean = danger.get(i);
                            if (id.equals(defectBean.getId())){
                                danSelectList.add(defectBean);
                            }
                        }
                    }

                } else if (type.startsWith("deletegroup")) {
                    String[] split = type.split("@");
                    String type_val = split[1];
                    String id = split[2];
                    for (int i = 0; i < selectType.size(); i++) {
                        if (id.equals(selectType.get(i).getL_id())) {
                            selectType.remove(i);
                            break;
                        }
                    }
                    selectAdapter.setData(selectType);
                    if ("1".equals(type_val)||"2".equals(type_val)){
                        for (int i = 0; i < patSelectList.size(); i++) {
                            GroupOfDayBean.PatrolBean patrolBean = patSelectList.get(i);
                            String patrolBeanId = patrolBean.getId();
                            if (id.equals(patrolBeanId)){
                                patSelectList.remove(i);
                                break;
                            }
                        }
                    }else if ("3".equals(type_val)){
                        for (int i = 0; i < defSelectList.size(); i++) {
                            DefectBean defectBean = defSelectList.get(i);
                            if (id.equals(defectBean.getId())){
                                defSelectList.remove(i);
                                break;
                            }
                        }
                    }else if ("4".equals(type_val)){

                        for (int i = 0; i < danSelectList.size(); i++) {
                            DefectBean defectBean = danSelectList.get(i);
                            if (id.equals(defectBean.getId())){
                                danSelectList.remove(i);
                                break;
                            }
                        }
                    }
                }


            }
        });
    }

    public void setVisibility(List<GroupOfDayBean.PatrolBean> list) {
        if (list.size() == 0) {
            groupTaskNoData.setVisibility(View.VISIBLE);
        } else {
            groupTaskNoData.setVisibility(View.GONE);
        }
        if (list.size() > 4) {
            troubleMore.setVisibility(View.VISIBLE);
        } else {
            troubleMore.setVisibility(View.GONE);
        }
        adapter.setData(list);
    }

    //获取日计划列表
    public void getDayList() {
        ProgressDialog.show(this,false,"正在加载中");
        taskList.clear();
        BaseRequest.getInstance().getService()
                .getDayList( SPUtil.getDepId(this), type_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<GroupOfDayBean>(this) {

                    @Override
                    protected void onSuccees(BaseResult<GroupOfDayBean> t) throws Exception {
                        ProgressDialog.cancle();
                        results = t.getResults();
                        patrol = results.getPatrol();
                        danger = results.getDanger();
                        defect = results.getDefect();
                        if (patrol.size()>0){
                            day_id=patrol.get(0).getId();
                        }
                        for (int i = 0; i < defect.size(); i++) {
                            DefectBean defectBean = defect.get(i);
                            GroupOfDayBean.PatrolBean bean=new GroupOfDayBean.PatrolBean();
                            bean.setId(defectBean.getId());
                            bean.setType_val("3");
                            bean.setType_name("缺陷处理");
                            bean.setLine_name(defectBean.getLine_name());
                            bean.setDone_time(defectBean.getFind_time());
                            bean.setName(defectBean.getLine_name()+defectBean.getStart_name()+"(杆塔)"+defectBean.getContent());
                            taskList.add(bean);
                        }
                        for (int i = 0; i < danger.size(); i++) {
                            DefectBean defectBean = defect.get(i);
                            GroupOfDayBean.PatrolBean bean=new GroupOfDayBean.PatrolBean();
                            bean.setId(defectBean.getId());
                            bean.setType_val("4");
                            bean.setType_name("隐患消除");
                            bean.setLine_name(defectBean.getLine_name());
                            bean.setDone_time(defectBean.getFind_time());
                            bean.setName(defectBean.getLine_name()+defectBean.getStart_name()+"(杆塔)"+defectBean.getContent());
                            taskList.add(bean);
                        }
                        taskList.addAll(patrol);
                        setVisibility(taskList);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Log.e("fff", e.toString());
                    }
                });
    }


    //展示人员列表
    private void showPersonalLine(int type) {// 不联动的多级选项
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                if (type == 0) {
                    String s = groupTaskGroup.getText().toString();
                    groupTaskGroup.setText(userList.get(options1));
                    String name = userList.get(options1);
                    userList.remove(options1);
                    if (!s.isEmpty()) {
                        for (int i = 0; i < addPeoList.size(); i++) {
                            AddGroupTaskReqBean.UsersBean usersBean = addPeoList.get(i);
                            if ("1".equals(usersBean.getIs_boss())) {
                                userList.add(usersBean.getUser_name());
                                addPeoList.remove(i);
                            }
                        }
                    }
                    for (int i = 0; i < personalList.size(); i++) {
                        DepUserBean depUserBean = personalList.get(i);
                        if (name.equals(depUserBean.getName())) {
                            AddGroupTaskReqBean.UsersBean bean=new  AddGroupTaskReqBean.UsersBean();
                            bean.setIs_boss("1");
                            duty_user_name = depUserBean.getName();
                            duty_user_id = depUserBean.getId();
                            bean.setUser_id(duty_user_id);
                            bean.setUser_name(duty_user_name);
                            addPeoList.add(bean);
                        }
                    }
                } else {
                    String name = userList.get(options1);
                    addTextView(name);
                    userList.remove(options1);
                    for (int i = 0; i < personalList.size(); i++) {
                        DepUserBean depUserBean = personalList.get(i);
                        if (name.equals(depUserBean.getName())) {
                            AddGroupTaskReqBean.UsersBean bean=new  AddGroupTaskReqBean.UsersBean();
                            bean.setIs_boss("0");
                            bean.setUser_id(depUserBean.getId());
                            bean.setUser_name(depUserBean.getName());
                            addPeoList.add(bean);
                        }
                    }

                }


            }
        }).build();
        pvOptions.setPicker(userList);
        pvOptions.show();

    }

    /**
     * 动态添加布局
     *
     * @param str
     */
    private void addTextView(String str) {
        TextView child = new TextView(this);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        child.setLayoutParams(params);
        child.setText(str);
        child.setBackgroundResource(R.drawable.tower_tv);
        child.setTextColor(Color.WHITE);
        initEvents(child);//监听
        incontinuityTower.addView(child);
    }

    /**
     * 为每个view 添加点击事件
     */
    private void initEvents(final TextView tv) {
        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String tower = tv.getText().toString();
                for (int i = 0; i < personalList.size(); i++) {
                    if (tower.equals(personalList.get(i).getName())) {
                        userList.add(i, tower);
                        addPeoList.remove(i);
                    }
                }
                incontinuityTower.removeView(v);
            }
        });
    }


    //类型添加选项框
    private void showType() {// 不联动的多级选项
        if (typeList.size() == 0) {
            Toast.makeText(this, "没有获取到工作类型信息，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                groupTaskType.setText(typeList.get(options1).getName());
                type_id = typeList.get(options1).getId();
                getDayList();
            }
        }).build();
        pvOptions.setPicker(typeName);
        pvOptions.show();
    }

    //获取每个班组负责的工作类型
    public void getLineType() {
        typeName.clear();
        BaseRequest.getInstance().getService()
                .getLineType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<LineTypeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<LineTypeBean>> t) throws Exception {
                        typeList = t.getResults();
                        for (int i = 0; i < typeList.size(); i++) {
                            LineTypeBean lineTypeBean = typeList.get(i);
                            typeName.add(lineTypeBean.getName());
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    //获取每个班组组员列表
    public void getPersonal() {

        BaseRequest.getInstance().getService()
                .getPersonal(year,month,day, SPUtil.getDepId(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DepUserBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DepUserBean>> t) throws Exception {
                        personalList = t.getResults();
                        for (int i = 0; i < personalList.size(); i++) {
                            userList.add(personalList.get(i).getName());
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
//
//    //获取缺陷库
//    public void getDefect() {
//        list1.clear();
//        BaseRequest.getInstance().getService()
//                .getDefect("", "0", "1")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<DefectBean>>(this) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<DefectBean>> t) throws Exception {
//                        List<DefectBean> results = t.getResults();
//                        list1.addAll(results);
//                        setVisibility(list1);
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//                    }
//                });
//    }


//    //获取已经添加的缺陷列表
//    public void getHaveDefect() {
//        BaseRequest.getInstance().getService()
//                .getHaveDefect(month_id, week_id, day_id, group_id, task_id, "1", "1")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<DefectBean>>(this) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<DefectBean>> t) throws Exception {
//                        List<DefectBean> results = t.getResults();
//                        selectType.addAll(results);
//                        selectAdapter.setData(selectType);
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//                    }
//                });
//    }

    //保存
    public void savaGroupTask() {

        AddGroupTaskReqBean bean = new AddGroupTaskReqBean();
        bean.setDay_id(day_id);
        bean.setDep_id(SPUtil.getDepId(this));
        bean.setDay(day);
        bean.setMonth(month);
        bean.setYear(year);
        bean.setDep_name(SPUtil.getDepName(this));
        bean.setDuty_user_id(duty_user_id);
        bean.setDuty_user_name(duty_user_name);
        bean.setUsers(addPeoList);
        bean.setDangers(danSelectList);
        bean.setDefects(defSelectList);
        bean.setLists(patSelectList);
        BaseRequest.getInstance().getService()
                .savaGroupTask(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DangerBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DangerBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            setResult(RESULT_OK);
                            Toast.makeText(AddGroupTaskActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddGroupTaskActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    @OnClick({R.id.title_back, R.id.group_task_type, R.id.group_task_group, R.id.add_group_people, R.id.add_more_iv, R.id.month_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.group_task_type:
                showType();
                break;
            case R.id.group_task_group:
                showPersonalLine(0);
                break;
            case R.id.add_group_people:
                showPersonalLine(1);
                break;
            case R.id.add_more_iv:
                if (type == 0) {
                    type = 1;
                    addMoreIv.setImageResource(R.mipmap.icon_newol_up);
                } else {
                    type = 0;
                    addMoreIv.setImageResource(R.mipmap.icon_newol_down);
                }
                adapter.setType(type);
                break;
            case R.id.month_yes:
                savaGroupTask();
                break;
        }
    }

    public void inteDate() {
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        day = Integer.parseInt(days[0]) + "";
    }
}
