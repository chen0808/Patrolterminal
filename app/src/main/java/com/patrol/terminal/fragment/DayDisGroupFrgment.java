package com.patrol.terminal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.GroupPersonalAdapter;
import com.patrol.terminal.adapter.GroupTeamAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.AddGroupTaskReqBean;
import com.patrol.terminal.bean.DangerBean;
import com.patrol.terminal.bean.DepUserBean;
import com.patrol.terminal.bean.GroupTeamBean;
import com.patrol.terminal.bean.GroupTeamSaveBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.TimeUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DayDisGroupFrgment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.select_group_personal_rv)
    RecyclerView selectGroupPersonalRv;
    @BindView(R.id.select_group_team_rv)
    RecyclerView selectGroupTeamRv;
    @BindView(R.id.change_group_team)
    TextView changeGroupTeam;
    @BindView(R.id.change_group_team_rl)
    RelativeLayout changeGroupTeamRl;
    @BindView(R.id.change_group_personal)
    TextView changeGroupPersonal;
    @BindView(R.id.change_group_personal_rl)
    RelativeLayout changeGroupPersonalRl;
    @BindView(R.id.change_group_back)
    TextView changeGroupBack;
    @BindView(R.id.change_group_back_rl)
    RelativeLayout changeGroupBackRl;
    @BindView(R.id.group_save)
    TextView groupSave;
    @BindView(R.id.group_make)
    RelativeLayout groupMake;
    @BindView(R.id.personal_no_data)
    ImageView personalNoData;
    @BindView(R.id.team_no_data)
    ImageView teamNoData;
    private int month, year, day;
    private String time;
    private List<GroupTeamBean> teamList = new ArrayList<>();
    private List<DepUserBean> personalsList = new ArrayList<>();
    private List<DepUserBean> allPersonal = new ArrayList<>();
    private List<DepUserBean> selectList = new ArrayList<>();
    private List<DepUserBean> backList = new ArrayList<>();
    private GroupTeamAdapter groupTeamAdapter;
    private GroupPersonalAdapter groupPersonalAdapter;
    private int type = 0; // 1 组长 2组员 3 撤回
    private Disposable subscribe;
    private int selectPosition = -1;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_dis_group, container, false);
        return view;
    }

    @Override
    protected void initData() {
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate(time);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        LinearLayoutManager manager1 = new LinearLayoutManager(getContext());
        selectGroupTeamRv.setLayoutManager(manager);
        selectGroupPersonalRv.setLayoutManager(manager1);
        groupTeamAdapter = new GroupTeamAdapter(R.layout.iteam_group_team, teamList);
        selectGroupTeamRv.setAdapter(groupTeamAdapter);
        groupPersonalAdapter = new GroupPersonalAdapter(R.layout.iteam_people, personalsList);
        selectGroupPersonalRv.setAdapter(groupPersonalAdapter);
        groupTeamAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.item_group_delete) {
                    GroupTeamBean groupTeamBean = teamList.get(position);
                    List<DepUserBean> personals = groupTeamBean.getPersonalsList();
                    List<DepUserBean> teams = groupTeamBean.getTeamList();
                    if (personals.size() != 0 || teams.size() != 0) {
                        for (int i = 0; i < personals.size(); i++) {
                            DepUserBean bean = personals.get(i);
                            bean.setCheck(false);
                            personalsList.add(bean);
                        }
                        for (int i = 0; i < teams.size(); i++) {
                            DepUserBean bean = teams.get(i);
                            bean.setCheck(false);
                            personalsList.add(bean);
                        }
                        groupPersonalAdapter.setNewData(personalsList);
                    }
                    adapter.notifyItemRemoved(position);
                    teamList.remove(position);
                }
            }
        });
        groupTeamAdapter.setOnItemClickListener(this);
        groupPersonalAdapter.setOnItemClickListener(this);
        subscribe = RxRefreshEvent.getObservableGroup().subscribe(new Consumer<DepUserBean>() {

            @Override
            public void accept(DepUserBean type) throws Exception {
                int isexit = 0;
                for (int i = 0; i < backList.size(); i++) {
                    DepUserBean depUserBean = backList.get(i);
                    if (type.getId().equals(depUserBean.getId())) {
                        isexit = 1;
                        backList.remove(i);
                        break;
                    }
                }
                if (isexit == 0) {
                    backList.add(type);
                }
            }
        });
        getPersonal();
        getGroupTeam();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    @OnClick({R.id.task_date, R.id.group_save, R.id.change_group_team_rl, R.id.change_group_personal_rl, R.id.change_group_back_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showDay();
                break;
            case R.id.group_save:

                savaGroupTeam();
                break;
            case R.id.change_group_team_rl:
                if (selectList.size() == 0) {
                    Toast.makeText(getContext(), "请先选择人员", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectList.size() > 1) {
                    Toast.makeText(getContext(), "组长只能选择一名", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<GroupTeamBean> groupTeamBeanList1 = groupTeamAdapter.getData();
                if (groupTeamBeanList1.size() == 0) {
                    addTeamList();
                } else {
                    GroupTeamBean groupTeamBean = null;
                    if (selectPosition == -1) {
                        groupTeamBean = groupTeamBeanList1.get(groupTeamBeanList1.size() - 1);
                    } else {
                        groupTeamBean = groupTeamBeanList1.get(selectPosition);
                    }

                    List<DepUserBean> teams = groupTeamBean.getTeamList();
                    if (teams.size() == 0) {
                        for (int i = 0; i < selectList.size(); i++) {
                            DepUserBean depUserBean = selectList.get(i);
                            depUserBean.setCheck(false);
                        }
                        teams.addAll(selectList);
                        personalsList.removeAll(selectList);
                        selectList.clear();
                    } else {
                        if (selectPosition == -1) {
                            addTeamList();
                        } else {
                            for (int i = 0; i < selectList.size(); i++) {
                                DepUserBean depUserBean = selectList.get(i);
                                depUserBean.setCheck(false);
                            }
                            DepUserBean bean = teams.get(0);
                            bean.setCheck(false);
                            personalsList.add(bean);
                            teams.clear();
                            teams.addAll(selectList);
                            personalsList.removeAll(selectList);
                            selectList.clear();
                        }
                    }
                }
                groupTeamAdapter.setNewData(teamList);
                groupPersonalAdapter.setNewData(personalsList);
                type = 1;
                if (personalsList.size()==0){
                    personalNoData.setVisibility(View.VISIBLE);
                }else {
                    personalNoData.setVisibility(View.GONE);
                }
                break;

            case R.id.change_group_personal_rl:
                if (selectList.size() == 0) {
                    Toast.makeText(getContext(), "请先选择人员", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<GroupTeamBean> groupTeamBeanList = groupTeamAdapter.getData();
                if (groupTeamBeanList.size() == 0) {
                    addPeronalList();
                } else {
                    GroupTeamBean groupTeamBean = null;
                    //选中哪个小组
                    if (selectPosition == -1) {
                        groupTeamBean = groupTeamBeanList.get(groupTeamBeanList.size() - 1);
                    } else {
                        groupTeamBean = groupTeamBeanList.get(selectPosition);
                    }
                    List<DepUserBean> personals = groupTeamBean.getPersonalsList();
                    for (int i = 0; i < selectList.size(); i++) {
                        DepUserBean depUserBean = selectList.get(i);
                        depUserBean.setCheck(false);
                    }
                    personals.addAll(selectList);
                    personalsList.removeAll(selectList);
                    selectList.clear();
                }

                groupTeamAdapter.setNewData(teamList);
                groupPersonalAdapter.setNewData(personalsList);
                type = 2;
                changeGroupTeam.setTextColor(getResources().getColor(R.color.color_33));
                if (personalsList.size()==0){
                    personalNoData.setVisibility(View.VISIBLE);
                }else {
                    personalNoData.setVisibility(View.GONE);
                }
                break;
            case R.id.change_group_back_rl:
                if (backList.size() == 0) {
                    Toast.makeText(getContext(), "请先从小组里面选择人员", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<GroupTeamBean> data = groupTeamAdapter.getData();
                for (int y = 0; y < backList.size(); y++) {
                    DepUserBean depUserBean = backList.get(y);
                    for (int i = 0; i < data.size(); i++) {
                        GroupTeamBean groupTeamBean3 = data.get(i);
                        List<DepUserBean> teamList = groupTeamBean3.getTeamList();
                        List<DepUserBean> personalsList = groupTeamBean3.getPersonalsList();
                        for (int j = 0; j < teamList.size(); j++) {
                            if (depUserBean.getId().equals(teamList.get(j).getId())) {
                                teamList.remove(j);
                            }
                        }
                        for (int j = 0; j < personalsList.size(); j++) {
                            if (depUserBean.getId().equals(personalsList.get(j).getId())) {
                                personalsList.remove(j);
                            }
                        }
                    }

                    depUserBean.setCheck(false);
                }
                type = 3;
                personalsList.addAll(backList);
                backList.clear();
                groupPersonalAdapter.setNewData(personalsList);
                groupTeamAdapter.notifyDataSetChanged();
                if (teamList.size()==0){
                    teamNoData.setVisibility(View.VISIBLE);
                }else {
                    teamNoData.setVisibility(View.GONE);
                }
                break;
        }
    }

    //添加组员
    public void addPeronalList() {
        for (int i = 0; i < selectList.size(); i++) {
            DepUserBean depUserBean = selectList.get(i);
            depUserBean.setCheck(false);
        }
        GroupTeamBean groupTeamBean = new GroupTeamBean();
        groupTeamBean.setPersonalsList(new ArrayList<DepUserBean>());
        groupTeamBean.setTeamList(new ArrayList<DepUserBean>());
        List<DepUserBean> personalsList = groupTeamBean.getPersonalsList();
        personalsList.addAll(selectList);
        teamList.add(groupTeamBean);
        this.personalsList.removeAll(selectList);
        selectList.clear();
    }

    //添加组长
    public void addTeamList() {
        for (int i = 0; i < selectList.size(); i++) {
            DepUserBean depUserBean = selectList.get(i);
            depUserBean.setCheck(false);
        }
        GroupTeamBean groupTeamBean = new GroupTeamBean();
        groupTeamBean.setPersonalsList(new ArrayList<DepUserBean>());
        groupTeamBean.setTeamList(new ArrayList<DepUserBean>());
        List<DepUserBean> teamList = groupTeamBean.getTeamList();
        teamList.addAll(selectList);
        this.teamList.add(groupTeamBean);
        personalsList.removeAll(selectList);
        selectList.clear();
    }

    //初始化日期
    public void inteDate(String oldTime) {

        String[] years = oldTime.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]);
        year = Integer.parseInt(years[0]);
        day = Integer.parseInt(days[0]);
        taskDate.setText(oldTime);
    }

    //获取每个班组组员列表
    public void getPersonal() {

        BaseRequest.getInstance().getService()
                .getPersonal(year + "", month + "", day + "", SPUtil.getDepId(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DepUserBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<DepUserBean>> t) throws Exception {
                        personalsList = t.getResults();
                        groupPersonalAdapter.setNewData(personalsList);
                      if (personalsList.size()==0){
                          personalNoData.setVisibility(View.VISIBLE);
                      }else {
                          personalNoData.setVisibility(View.GONE);
                      }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    //获取每个班小组列表
    public void getGroupTeam() {
        BaseRequest.getInstance().getService()
                .getGroupTeam(year + "", month + "", day + "", SPUtil.getDepId(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<AddGroupTaskReqBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<AddGroupTaskReqBean>> t) throws Exception {
                        teamList.clear();
                        List<AddGroupTaskReqBean> results = t.getResults();
                        RxRefreshEvent.publish("refreshGroupTeamNum@" + results.size());
                        for (int i = 0; i < results.size(); i++) {
                            GroupTeamBean groupTeamBean = new GroupTeamBean();
                            List<DepUserBean> personals = new ArrayList<>();
                            List<DepUserBean> teams = new ArrayList<>();
                            AddGroupTaskReqBean addGroupTaskReqBean = results.get(i);
                            List<AddGroupTaskReqBean.UsersBean> users = addGroupTaskReqBean.getUsers();
                            for (int j = 0; j < users.size(); j++) {
                                AddGroupTaskReqBean.UsersBean usersBean = users.get(j);
                                DepUserBean bean = new DepUserBean();
                                bean.setId(usersBean.getUser_id());
                                bean.setName(usersBean.getUser_name());
                                if ("2".equals(usersBean.getSign())) {
                                    teams.add(bean);
                                } else if ("3".equals(usersBean.getSign())) {
                                    personals.add(bean);
                                }
                            }
                            groupTeamBean.setTeamList(teams);
                            groupTeamBean.setPersonalsList(personals);
                            teamList.add(groupTeamBean);

                        }
                        if (teamList.size()==0){
                            teamNoData.setVisibility(View.VISIBLE);
                        }else {
                            teamNoData.setVisibility(View.GONE);
                        }
                        groupTeamAdapter.setNewData(teamList);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //人员列表
        if (adapter instanceof GroupPersonalAdapter) {
            DepUserBean bean = personalsList.get(position);
            TextView personalName = (TextView) adapter.getViewByPosition(selectGroupPersonalRv, position, R.id.people_name);
            boolean isexit = true;
            for (int i = 0; i < selectList.size(); i++) {
                DepUserBean depUserBean = selectList.get(i);
                if (bean.getId().equals(depUserBean.getId())) {
                    isexit = false;
                    bean.setCheck(false);
                    selectList.remove(i);
                    personalName.setTextColor(getResources().getColor(R.color.my_info));
                    break;
                }
            }
            if (isexit) {
                bean.setCheck(true);
                selectList.add(bean);
                personalName.setTextColor(getResources().getColor(R.color.green));
            }
            //小组列表
        } else if (adapter instanceof GroupTeamAdapter) {
            GroupTeamBean groupTeamBean = teamList.get(position);
            boolean check = groupTeamBean.isCheck();
            TextView groupName = (TextView) adapter.getViewByPosition(selectGroupTeamRv, position, R.id.iteam_group_name);
            if (check == true) {
                groupTeamBean.setCheck(false);
                selectPosition = -1;
                groupName.setTextColor(getResources().getColor(R.color.color_33));
            } else {
                groupTeamBean.setCheck(true);
                groupName.setTextColor(getResources().getColor(R.color.blue));
                selectPosition = position;
            }
        }
    }

    //保存
    public void savaGroupTeam() {
        GroupTeamSaveBean groupTeamSaveBean = new GroupTeamSaveBean();
        List<AddGroupTaskReqBean> list = new ArrayList<>();
        //获取小组列表数据
        List<GroupTeamBean> data = groupTeamAdapter.getData();
        if ((data.size() == 0)) {
            Toast.makeText(getContext(), "请先添加小组", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            GroupTeamBean groupTeamBean = data.get(i);
            List<DepUserBean> teams = groupTeamBean.getTeamList();
            List<DepUserBean> personals = groupTeamBean.getPersonalsList();
            List<AddGroupTaskReqBean.UsersBean> addPeoList = new ArrayList<>();
            String duty_user_id = "";
            String duty_user_name = "";
            if ((teams.size() == 0 && personals.size() != 0)) {
                Toast.makeText(getContext(), "有组员的小组必须指派负责人", Toast.LENGTH_SHORT).show();
                return;
            }
            if (teams.size() == 0 && personals.size() == 0) {
                continue;
            }
            //获取小组负责人
            for (int j = 0; j < teams.size(); j++) {
                DepUserBean depUserBean = teams.get(j);
                AddGroupTaskReqBean.UsersBean usersBean = new AddGroupTaskReqBean.UsersBean();
                duty_user_id = depUserBean.getId();
                duty_user_name = depUserBean.getName();
                usersBean.setSign("2");
                usersBean.setUser_id(depUserBean.getId());
                usersBean.setUser_name(depUserBean.getName());
                addPeoList.add(usersBean);
            }
            //获取组员
            for (int j = 0; j < personals.size(); j++) {
                DepUserBean depUserBean = personals.get(j);
                AddGroupTaskReqBean.UsersBean usersBean = new AddGroupTaskReqBean.UsersBean();
                usersBean.setSign("3");
                usersBean.setUser_id(depUserBean.getId());
                usersBean.setUser_name(depUserBean.getName());
                addPeoList.add(usersBean);
            }

            AddGroupTaskReqBean bean = new AddGroupTaskReqBean();
            bean.setDep_id(SPUtil.getDepId(getContext()));
            bean.setDay(day + "");
            bean.setMonth(month + "");
            bean.setYear(year + "");
            bean.setWeek(TimeUtil.getWeekOfYear(new Date(System.currentTimeMillis())) + "");
            bean.setFrom_user_id(SPUtil.getUserId(getContext()));
            bean.setFrom_user_name(SPUtil.getUserName(getContext()));
            bean.setDep_name(SPUtil.getDepName(getContext()));
            bean.setUsers(addPeoList);
            bean.setDuty_user_id(duty_user_id);
            bean.setDuty_user_name(duty_user_name);
            list.add(bean);
        }
        groupTeamSaveBean.setDay(day);
        groupTeamSaveBean.setMonth(month);
        groupTeamSaveBean.setYear(year);
        groupTeamSaveBean.setDep_id(SPUtil.getDepId(getContext()));
        groupTeamSaveBean.setTaskGroupList(list);
        ProgressDialog.show(getContext(), true, "正在保存。。。");
        BaseRequest.getInstance().getService()
                .savaGroupTeam(groupTeamSaveBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DangerBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<DangerBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            RxRefreshEvent.publish("refreshGroupTeamNum@" + list.size());
                            RxRefreshEvent.publishGrooup(null);
                            Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                        }
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    public void showDay() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        //选中事件回调
//是否只显示中间选中项的label文字，false则每项item全部都带有label。
        TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String oldTime = DateUatil.getDay(date);
                inteDate(oldTime);
                if (time.equals(oldTime)) {
                    groupSave.setVisibility(View.VISIBLE);
                    getPersonal();
                } else {
                    groupSave.setVisibility(View.GONE);
                }
                getGroupTeam();

            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentTextSize(18)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

}
