package com.patrol.terminal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DayPlanAllotAdapter;
import com.patrol.terminal.adapter.PlanAllotTeamAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DangerBean;
import com.patrol.terminal.bean.GroupOfDayBean;
import com.patrol.terminal.bean.TeamAndTaskBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
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

public class DayPlanAllotFrgment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {


    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.group_team_rv)
    RecyclerView groupTeamRv;
    @BindView(R.id.plan_botton)
    ImageView planBotton;
    @BindView(R.id.plan_top)
    ImageView planTop;
    @BindView(R.id.select_day_plan_rv)
    RecyclerView selectDayPlanRv;
    private int month, year, day;
    private String time;
    private List<TeamAndTaskBean> teamList = new ArrayList<>();
    private List<GroupOfDayBean> dayPlanlist = new ArrayList<>();
    private List<GroupOfDayBean> selectList = new ArrayList<>();
    private List<GroupOfDayBean> backList = new ArrayList<>();
    private PlanAllotTeamAdapter planAllotTeamAdapter;
    private DayPlanAllotAdapter dayPlanAllotAdapter;
    private int selectPosition = -1;
    private Disposable subscribe;
    private int allotNum = 0;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_plan_allot, container, false);
        return view;
    }

    @Override
    protected void initData() {
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate(time);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        LinearLayoutManager manager1 = new LinearLayoutManager(getContext());
        groupTeamRv.setLayoutManager(manager);
        selectDayPlanRv.setLayoutManager(manager1);
        planAllotTeamAdapter = new PlanAllotTeamAdapter(R.layout.iteam_plan_allot_team, teamList);
        groupTeamRv.setAdapter(planAllotTeamAdapter);
        dayPlanAllotAdapter = new DayPlanAllotAdapter(R.layout.iteam_day_plan_allot, dayPlanlist);
        selectDayPlanRv.setAdapter(dayPlanAllotAdapter);
        dayPlanAllotAdapter.setOnItemClickListener(this);
        planAllotTeamAdapter.setOnItemChildClickListener(this);

        subscribe = RxRefreshEvent.getGroopuObservable().subscribe(new Consumer<GroupOfDayBean>() {

            @Override
            public void accept(GroupOfDayBean type) throws Exception {
                if (type.getId() == null) {
                    getGroupTeam();
                } else {
                    boolean isexit = true;
                    for (int i = 0; i < backList.size(); i++) {
                        GroupOfDayBean groupOfDayBean1 = backList.get(i);
                        if (type.getId().equals(groupOfDayBean1.getId())) {
                            isexit = false;
                            backList.remove(i);
                            break;
                        }
                    }
                    if (isexit) {
                        backList.add(type);
                    }
                }

            }
        });
        getDayList();
        getGroupTeam();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    public void inteDate(String time) {
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]);
        year = Integer.parseInt(years[0]);
        day = Integer.parseInt(days[0]);
        taskDate.setText(time);
    }

    //获取日计划列表
    public void getDayList() {

        BaseRequest.getInstance().getService()
                .getDayofGroup(year + "", month + "", day + "", SPUtil.getDepId(getContext()), null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GroupOfDayBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<GroupOfDayBean>> t) throws Exception {
                        dayPlanlist = t.getResults();
                        dayPlanAllotAdapter.setNewData(dayPlanlist);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    //获取每个班小组列表
    public void getGroupTeam() {
        BaseRequest.getInstance().getService()
                .getTeamAndTask(year + "", month + "", day + "", SPUtil.getDepId(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TeamAndTaskBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<TeamAndTaskBean>> t) throws Exception {

                        if (selectPosition == -1) {
                            teamList = t.getResults();
                            planAllotTeamAdapter.setNewData(teamList);
                        } else {
                            List<TeamAndTaskBean> results = t.getResults();
                            List<GroupOfDayBean> lists2 = results.get(selectPosition).getLists();
                            List<GroupOfDayBean> lists1 = teamList.get(selectPosition).getLists();
                            for (int i = lists1.size(); i < lists2.size(); i++) {
                                GroupOfDayBean groupOfDayBean = lists2.get(i);
                                lists1.add(groupOfDayBean);
                            }
                            planAllotTeamAdapter.notifyItemChanged(selectPosition);
                        }
                        for (int i = 0; i < teamList.size(); i++) {
                            List<GroupOfDayBean> lists = teamList.get(i).getLists();
                            if (lists.size() > 0) {
                                allotNum++;
                            }
                        }
                        RxRefreshEvent.publish("refreshHaveTask@" + allotNum);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //下面的日计划列表
        if (adapter instanceof DayPlanAllotAdapter) {
            GroupOfDayBean groupOfDayBean = dayPlanlist.get(position);
            ImageView imageView = (ImageView) adapter.getViewByPosition(selectDayPlanRv, position, R.id.day_plan_check_iv);
            boolean check = groupOfDayBean.isCheck();
            if (check == true) {
                groupOfDayBean.setCheck(false);
                imageView.setImageResource(R.mipmap.circle_no);

            } else {
                groupOfDayBean.setCheck(true);
                imageView.setImageResource(R.mipmap.circle);
            }
            boolean isexit = true;
            for (int i = 0; i < selectList.size(); i++) {
                GroupOfDayBean groupOfDayBean1 = selectList.get(i);
                if (groupOfDayBean.getId().equals(groupOfDayBean1.getId())) {
                    isexit = false;
                    selectList.remove(i);
                    break;
                }
            }
            if (isexit) {
                selectList.add(groupOfDayBean);
            }
        }
    }

    @OnClick({R.id.task_date, R.id.plan_botton, R.id.plan_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                break;
            case R.id.plan_botton:
                if (backList.size() == 0) {
                    Toast.makeText(getContext(), "请先从小组里面选择任务", Toast.LENGTH_SHORT).show();
                    return;
                }
                revokeGroupTask();
                break;
            case R.id.plan_top:
                if (selectList.size() == 0) {
                    Toast.makeText(getContext(), "请先从下面选择任务", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectPosition == -1) {
                    Toast.makeText(getContext(), "请选择小组", Toast.LENGTH_SHORT).show();
                    return;
                }
                savaGroupTask();
                break;
        }
    }

    //保存
    public void savaGroupTask() {

        ProgressDialog.show(getContext(), true, "正在保存");
        TeamAndTaskBean teamAndTaskBean =new TeamAndTaskBean();
        TeamAndTaskBean teamAndTaskBean1 = teamList.get(selectPosition);
        teamAndTaskBean.setId(teamAndTaskBean1.getId());
        for (int i = 0; i < selectList.size(); i++) {
            GroupOfDayBean groupOfDayBean = selectList.get(i);
            if (groupOfDayBean.getDay_tower_id() == null) {
                groupOfDayBean.setDay_tower_id(groupOfDayBean.getId());
            }
        }
        teamAndTaskBean.setLists(selectList);
        teamAndTaskBean.setDuty_user_id(teamAndTaskBean1.getDuty_user_id());
        teamAndTaskBean.setDuty_user_name(teamAndTaskBean1.getDuty_user_name());
        teamAndTaskBean.setDay(teamAndTaskBean1.getDay());
        teamAndTaskBean.setMonth(teamAndTaskBean1.getMonth());
        teamAndTaskBean.setYear(teamAndTaskBean1.getYear());
        teamAndTaskBean.setDep_id(teamAndTaskBean1.getDep_id());
        teamAndTaskBean.setDep_name(teamAndTaskBean1.getDep_name());
        BaseRequest.getInstance().getService()
                .savaGroupTask(teamAndTaskBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DangerBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<DangerBean>> t) throws Exception {

                        dayPlanlist.removeAll(selectList);
                        selectList.clear();

                        ProgressDialog.cancle();
                        getGroupTeam();
                        dayPlanAllotAdapter.setNewData(dayPlanlist);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    //撤回
    public void revokeGroupTask() {
        ProgressDialog.show(getContext(), true, "正在保存");

        BaseRequest.getInstance().getService()
                .revokeGroupTask(backList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DangerBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<DangerBean>> t) throws Exception {
                        allotNum=0;
                        for (int i = 0; i < teamList.size(); i++) {
                            List<GroupOfDayBean> lists = teamList.get(i).getLists();
                            for (int j = 0; j < backList.size(); j++) {
                                GroupOfDayBean groupOfDayBean = backList.get(j);
                                groupOfDayBean.setCheck(false);
                                lists.remove(groupOfDayBean);
                            }
                            if (lists.size() > 0) {
                                allotNum++;
                            }
                        }
                        RxRefreshEvent.publish("refreshHaveTask@" + allotNum);
                        dayPlanlist.addAll(backList);
                        backList.clear();

                        ProgressDialog.cancle();
                        planAllotTeamAdapter.setNewData(teamList);
                        dayPlanAllotAdapter.setNewData(dayPlanlist);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

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
                    getGroupTeam();
                } else {
                    planBotton.setVisibility(View.INVISIBLE);
                    planBotton.setVisibility(View.INVISIBLE);
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

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.iteam_group_team_name) {
            TeamAndTaskBean dayPlanAllotBean = teamList.get(position);
            boolean check = dayPlanAllotBean.isCheck();
            TextView teamName = (TextView) adapter.getViewByPosition(groupTeamRv, position, R.id.iteam_group_team_name);
            if (selectPosition == -1 || selectPosition == position) {
                selectPosition = position;
                if (check == true) {
                    selectPosition = -1;
                    dayPlanAllotBean.setCheck(false);
                    teamName.setTextColor(getContext().getResources().getColor(R.color.color_33));

                } else {

                    dayPlanAllotBean.setCheck(true);
                    teamName.setTextColor(getContext().getResources().getColor(R.color.green));
                }
            } else {
                TeamAndTaskBean teamAndTaskBean = teamList.get(selectPosition);
                teamAndTaskBean.setCheck(false);
                adapter.notifyItemChanged(selectPosition);
                dayPlanAllotBean.setCheck(true);
                adapter.notifyItemChanged(position);
                selectPosition = position;
            }
        }
    }
}
