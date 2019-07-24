package com.patrol.terminal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DriverAdapter;
import com.patrol.terminal.adapter.EqVehicleAdapter;
import com.patrol.terminal.adapter.PlanAllotTeamAdapter;
import com.patrol.terminal.adapter.TaskGroupVehicleAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DriverBean;
import com.patrol.terminal.bean.EqVehicleBean;
import com.patrol.terminal.bean.GroupOfDayBean;
import com.patrol.terminal.bean.TeamAndTaskBean;
import com.patrol.terminal.bean.TeamAndVehicleBean;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

//车辆选择
public class CarAllotFrgment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

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
    @BindView(R.id.select_group_personal_rv)
    RecyclerView selectGroupPersonalRv;
    @BindView(R.id.car_team_rv)
    RecyclerView carTeamRv;

    private int month, year, day;
    private String time;
    private List<TeamAndTaskBean> teamList = new ArrayList<>();
    private List<TeamAndVehicleBean> dayVehiclelist = new ArrayList<>();
    private List<TeamAndVehicleBean> selectList = new ArrayList<>();
    private List<TeamAndVehicleBean> backList = new ArrayList<>();
    private List<DriverBean> driverList = new ArrayList<>();
    private List<EqVehicleBean> carList = new ArrayList<>();
    private PlanAllotTeamAdapter planAllotTeamAdapter;
    private TaskGroupVehicleAdapter taskGroupVehicleAdapter;
    private DriverAdapter driverAdapter;
    private EqVehicleAdapter eqVehicleAdapter;
    private int selectPosition = -1;
    private int selectDriverPosition = -1;
    private int selectCarPosition = -1;
    private Disposable subscribe;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_allot, container, false);
        return view;
    }

    @Override
    protected void initData() {
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate(time);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        LinearLayoutManager manager1 = new LinearLayoutManager(getContext());
        LinearLayoutManager manager2 = new LinearLayoutManager(getContext());
        LinearLayoutManager manager3 = new LinearLayoutManager(getContext());
        groupTeamRv.setLayoutManager(manager);
        selectDayPlanRv.setLayoutManager(manager1);
        selectGroupPersonalRv.setLayoutManager(manager2);
        carTeamRv.setLayoutManager(manager3);
        planAllotTeamAdapter = new PlanAllotTeamAdapter(R.layout.iteam_plan_allot_team, teamList);
        groupTeamRv.setAdapter(planAllotTeamAdapter);
        taskGroupVehicleAdapter = new TaskGroupVehicleAdapter(R.layout.iteam_task_group_vehicle, dayVehiclelist);
        selectDayPlanRv.setAdapter(taskGroupVehicleAdapter);
        driverAdapter = new DriverAdapter(R.layout.iteam_driver, driverList);
        selectGroupPersonalRv.setAdapter(driverAdapter);
        eqVehicleAdapter = new EqVehicleAdapter(R.layout.iteam_car, carList);
        carTeamRv.setAdapter(eqVehicleAdapter);
        taskGroupVehicleAdapter.setOnItemClickListener(this);
        planAllotTeamAdapter.setOnItemChildClickListener(this);
        driverAdapter.setOnItemClickListener(this);
        eqVehicleAdapter.setOnItemClickListener(this);

        subscribe = RxRefreshEvent.getGroopuObservable().subscribe(new Consumer<GroupOfDayBean>() {

            @Override
            public void accept(GroupOfDayBean type) throws Exception {
                if (type.getId() == null) {
                    getGroupTeam();
                }
            }
        });

        getVehicle();
        getDriver();
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
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]);
        year = Integer.parseInt(years[0]);
        day = Integer.parseInt(days[0]);
        taskDate.setText(time);
    }

    //获取每个班组车辆列表
    public void getVehicle() {

        BaseRequest.getInstance().getService()
                .getVehicle(year + "", month + "", day + "", SPUtil.getDepId(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<EqVehicleBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<EqVehicleBean>> t) throws Exception {
                        carList = t.getResults();
                        eqVehicleAdapter.setNewData(carList);
                        eqVehicleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    //获取司机列表
    public void getDriver() {
        BaseRequest.getInstance().getService()
                .getDriver(year + "", month + "", day + "", SPUtil.getDepId(getContext()),"39420EBCC73C4977B01BADCEABB4A157")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DriverBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<DriverBean>> t) throws Exception {
                        driverList = t.getResults();
                        driverAdapter.setNewData(driverList);
                        driverAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    //获取车辆分配列表
    public void getDayList() {
        BaseRequest.getInstance().getService()
                .getVehicleofGroup(year + "", month + "", day + "", SPUtil.getDepId(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TeamAndVehicleBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<TeamAndVehicleBean>> t) throws Exception {
                        dayVehiclelist = t.getResults();
                        taskGroupVehicleAdapter.setNewData(dayVehiclelist);
                        taskGroupVehicleAdapter.notifyDataSetChanged();
                        RxRefreshEvent.publish("refreshVehicleNum@" + dayVehiclelist.size());
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
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //下面的日计划列表
        if (adapter instanceof TaskGroupVehicleAdapter) {
            TeamAndVehicleBean teamAndVehicleBean = dayVehiclelist.get(position);
            ImageView imageView = (ImageView) adapter.getViewByPosition(selectDayPlanRv, position, R.id.day_plan_check_iv);
            boolean check = teamAndVehicleBean.isCheck();
            if (check == true) {
                teamAndVehicleBean.setCheck(false);
                imageView.setImageResource(R.mipmap.circle_no);
            } else {
                teamAndVehicleBean.setCheck(true);
                imageView.setImageResource(R.mipmap.circle);
            }

            boolean isexit = true;
            for (int i = 0; i < selectList.size(); i++) {
                TeamAndVehicleBean teamAndVehicleBeanTemp = selectList.get(i);
                if (teamAndVehicleBean.getId().equals(teamAndVehicleBeanTemp.getId())) {
                    isexit = false;
                    selectList.remove(i);
                    break;
                }
            }

            if (isexit) {
                selectList.add(teamAndVehicleBean);
            }
        } else if (adapter instanceof DriverAdapter) {
            DriverBean driverBean = driverList.get(position);
            if(driverBean.getIs_use().equals("0")){
                boolean check = driverBean.isCheck();
                TextView name = (TextView) adapter.getViewByPosition(selectGroupPersonalRv, position, R.id.name);
                if (selectDriverPosition == -1 || selectDriverPosition == position) {
                    selectDriverPosition = position;
                    if (check == true) {
                        selectDriverPosition = -1;
                        driverBean.setCheck(false);
                        name.setTextColor(getContext().getResources().getColor(R.color.color_33));
                    } else {
                        driverBean.setCheck(true);
                        name.setTextColor(getContext().getResources().getColor(R.color.green));
                    }
                } else {
                    DriverBean driverBeanTemp = driverList.get(selectDriverPosition);
                    driverBeanTemp.setCheck(false);
                    adapter.notifyItemChanged(selectDriverPosition);
                    driverBean.setCheck(true);
                    adapter.notifyItemChanged(position);
                    selectDriverPosition = position;
                }
            }
        } else if (adapter instanceof EqVehicleAdapter) {
            EqVehicleBean eqVehicleBean = carList.get(position);
            if(eqVehicleBean.getIs_use().equals("0")){
                if(eqVehicleBean.getIs_use().equals("0")){
                    boolean check = eqVehicleBean.isCheck();
                    TextView carNumber = (TextView) adapter.getViewByPosition(carTeamRv, position, R.id.car_number);
                    if (selectCarPosition == -1 || selectCarPosition == position) {
                        selectCarPosition = position;
                        if (check == true) {
                            selectCarPosition = -1;
                            eqVehicleBean.setCheck(false);
                            carNumber.setTextColor(getContext().getResources().getColor(R.color.color_33));
                        } else {
                            eqVehicleBean.setCheck(true);
                            carNumber.setTextColor(getContext().getResources().getColor(R.color.green));
                        }
                    } else {
                        EqVehicleBean eqVehicleBeanTemp = carList.get(selectCarPosition);
                        eqVehicleBeanTemp.setCheck(false);
                        adapter.notifyItemChanged(selectCarPosition);
                        eqVehicleBean.setCheck(true);
                        adapter.notifyItemChanged(position);
                        selectCarPosition = position;
                    }
                }
            }
        }
    }

    @OnClick({R.id.task_date, R.id.plan_botton, R.id.plan_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showDay();
                break;
            case R.id.plan_botton:
                if(selectCarPosition == -1){
                    Toast.makeText(getContext(), "请选择一辆车", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selectDriverPosition == -1){
                    Toast.makeText(getContext(), "请选择一个司机", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selectPosition == -1){
                    Toast.makeText(getContext(), "请选择一个小组", Toast.LENGTH_SHORT).show();
                    return;
                }

                savaGroupTask();
                break;
            case R.id.plan_top:
                if (selectList.size() == 0) {
                    Toast.makeText(getContext(), "请先从下面选择任务", Toast.LENGTH_SHORT).show();
                    return;
                }
                revokeGroupTask();
                break;
        }
    }

    //保存
    public void savaGroupTask() {
        ProgressDialog.show(getContext(), true, "正在保存");
        TeamAndVehicleBean teamAndVehicleBean = new TeamAndVehicleBean();
        teamAndVehicleBean.setGroup_id(teamList.get(selectPosition).getId());
        teamAndVehicleBean.setDep_id(SPUtil.getDepId(getContext()));
        teamAndVehicleBean.setVehicle_id(carList.get(selectCarPosition).getId());
        teamAndVehicleBean.setDriver_id(driverList.get(selectDriverPosition).getId());
        teamAndVehicleBean.setDay(day);
        teamAndVehicleBean.setMonth(month);
        teamAndVehicleBean.setYear(year);
        teamAndVehicleBean.setGroup_name(teamList.get(selectPosition).getDuty_user_name() + "的小组");
        teamAndVehicleBean.setDep_name(SPUtil.getDepName(getContext()));
        teamAndVehicleBean.setCar_number(carList.get(selectCarPosition).getCar_number());
        teamAndVehicleBean.setDriver_name(driverList.get(selectDriverPosition).getName());

        BaseRequest.getInstance().getService()
                .savaGroupVehicleTask(teamAndVehicleBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TeamAndVehicleBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<TeamAndVehicleBean>> t) throws Exception {
                        dayVehiclelist.removeAll(selectList);
                        selectList.clear();

                        ProgressDialog.cancle();
                        getVehicle();
                        getDriver();
                        getGroupTeam();
                        getDayList();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    //撤回
    public void revokeGroupTask() {
        ProgressDialog.show(getContext(), true, "正在保存");

        String ids;
        if(selectList.size() == 1){
            ids = selectList.get(0).getId();
        } else {
            StringBuilder tempString = new StringBuilder();
            for(int i=0;i<selectList.size();i++){
                tempString.append(selectList.get(i).getId());
                if(i < selectList.size()-1){
                    tempString.append(",");
                }
            }
            ids = tempString.toString();
        }

        BaseRequest.getInstance().getService()
                .deleteVehicleGroup(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TeamAndVehicleBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<TeamAndVehicleBean>> t) throws Exception {
                        dayVehiclelist.removeAll(selectList);
                        selectList.clear();

                        ProgressDialog.cancle();
                        getVehicle();
                        getDriver();
                        getGroupTeam();
                        getDayList();
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
                time = DateUatil.getDay(date);
                inteDate(time);
                getDayList();
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
