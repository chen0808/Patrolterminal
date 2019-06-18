package com.patrol.terminal.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

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
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.TimeUtil;
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
    private List<GroupOfDayBean> selectType = new ArrayList<>();
    private AddGroupTaskAdapter adapter;
    private GroupTaskSelectAdapter selectAdapter;
    private List<GroupOfDayBean> results=new ArrayList<>();
    private Disposable subscribe;
    private List<DefectBean> list1 = new ArrayList<>();
    private List<String> lineName = new ArrayList<>();

    private String year;
    private String month;
    private String week;
    private int type = 0;
    private String lineId;

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
    private List<GroupOfDayBean> patSelectList=new ArrayList<>();
    private AlertDialog personalDialog;
    private String[] personals;
    private  List<String> personalPosin=new ArrayList<>();
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
        titleName.setText("制定任务");
        adapter = new AddGroupTaskAdapter(this, results);
        selectAdapter = new GroupTaskSelectAdapter(this, selectType);
        groupTaskTypeLv.setAdapter(adapter);
        manthPlanDetailLv.setAdapter(selectAdapter);
         subscribe = RxRefreshEvent.getGroopuObservable().subscribe(new Consumer<GroupOfDayBean>() {

            @Override
            public void accept(GroupOfDayBean type) throws Exception {
                if (selectType.size()==0){
                    if (type.getId()!=null){
                        type.setDay_tower_id(type.getId());
                    }

                    type.setId(null);
                    selectType.add(type);
                }else {
                    int isExit=0;
                    for (int i = 0; i < selectType.size(); i++) {
                        GroupOfDayBean dayOfWeekBean = selectType.get(i);
                        if (dayOfWeekBean.getDay_tower_id().equals(type.getDay_tower_id())){
                            isExit=1;
                            selectType.remove(i);
                            break;
                        }
                    }
                    if (isExit==0){
                        if (type.getId()!=null){
                            type.setDay_tower_id(type.getId());
                        }
                        type.setId(null);
                        selectType.add(type);
                    }
                }
                selectAdapter.setData(selectType);
            }
        });
    }

    public void setVisibility(List<GroupOfDayBean> list) {
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
    public void showPersonal(){
        boolean[] booleans=new boolean[personals.length];
        for (int i = 0; i < personals.length; i++) {
            String personal = personals[i];
            int i1 = personalPosin.indexOf(personal);
            if (i1==-1){
                booleans[i]=false;
            }else {
                booleans[i]=true;
            }
        }
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择组员");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(personals, booleans, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {

                if (isChecked){
                    personalPosin.add(personals[i]);
                }else {
                    for (int j = 0; j < personalPosin.size(); j++) {
                        String s = personalPosin.get(j);
                        if (s.equals(personals[i])){
                            personalPosin.remove(j);
                            break;
                        }
                    }
                }
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                incontinuityTower.removeAllViews();
                for (int j = 0; j < personalPosin.size(); j++) {
                    String name = personalPosin.get(j);
                        addTextView(name);
                        for (int i = 0; i < personalList.size(); i++) {
                            DepUserBean depUserBean = personalList.get(i);
                            if (name.equals(depUserBean.getName())) {
                                AddGroupTaskReqBean.UsersBean bean=new  AddGroupTaskReqBean.UsersBean();
                                bean.setSign("3");
                                bean.setUser_id(depUserBean.getId());
                                bean.setUser_name(depUserBean.getName());
                                addPeoList.add(bean);
                            }
                        }

                }

                personalDialog.dismiss();
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                personalDialog.dismiss();
            }
        });


        personalDialog = alertBuilder.create();
        personalDialog.show();
    }

    //获取日计划列表
    public void getDayList() {
        ProgressDialog.show(this,false,"正在加载中");

        BaseRequest.getInstance().getService()
                .getDayofGroup( year,month,day,SPUtil.getDepId(this), type_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GroupOfDayBean>>(this) {

                               @Override
                               protected void onSuccees(BaseResult<List<GroupOfDayBean>> t) throws Exception {
                                  results = t.getResults();
                                   adapter.setData(results);
                               }

                               @Override
                               protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                               }});
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
                            if ("2".equals(usersBean.getSign())) {
                                userList.add(usersBean.getUser_name());
                                addPeoList.remove(i);
                            }
                        }
                    }
                    for (int i = 0; i < personalList.size(); i++) {
                        DepUserBean depUserBean = personalList.get(i);
                        if (name.equals(depUserBean.getName())) {
                            AddGroupTaskReqBean.UsersBean bean=new  AddGroupTaskReqBean.UsersBean();
                            bean.setSign("2");
                            duty_user_name = depUserBean.getName();
                            duty_user_id = depUserBean.getId();
                            bean.setUser_id(duty_user_id);
                            bean.setUser_name(duty_user_name);
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
                for (int i = 0; i < addPeoList.size(); i++) {
                    if (tower.equals(addPeoList.get(i).getUser_name())) {
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
                        personals = new String[personalList.size()];
                        for (int i = 0; i < personalList.size(); i++) {
                            userList.add(personalList.get(i).getName());
                            personals[i]=personalList.get(i).getName();
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
        AddGroupTaskReqBean.UsersBean usersBean=new AddGroupTaskReqBean.UsersBean();
        usersBean.setSign("1");
        usersBean.setUser_id(SPUtil.getUserId(this));
        usersBean.setUser_name(SPUtil.getUserName(this));
        addPeoList.add(usersBean);

        AddGroupTaskReqBean bean = new AddGroupTaskReqBean();
        bean.setDep_id(SPUtil.getDepId(this));
        bean.setDay(day);
        bean.setMonth(month);
        bean.setYear(year);
        bean.setWeek(TimeUtil.getWeekOfYear(new Date(System.currentTimeMillis()))+"");
        bean.setFrom_user_id(SPUtil.getUserId(this));
        bean.setFrom_user_name(SPUtil.getUserName(this));
        bean.setDep_name(SPUtil.getDepName(this));
        bean.setDuty_user_id(duty_user_id);
        bean.setDuty_user_name(duty_user_name);
        bean.setUsers(addPeoList);
        bean.setLists(selectType);
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
               showPersonal();
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
                if (duty_user_id==null){
                    Toast.makeText(this,"请先选择负责人",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectType.size()==0){
                    Toast.makeText(this,"请先添加日计划",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (addPeoList.size()<2){
                    Toast.makeText(this,"请添加组员",Toast.LENGTH_SHORT).show();
                    return;
                }

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
