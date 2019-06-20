package com.patrol.terminal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddPersonalTaskActivity;
import com.patrol.terminal.activity.PersonalTaskDetailActivity;
import com.patrol.terminal.adapter.PersonalTaskAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PersonalTaskFrgment extends BaseFragment {


    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.task_add)
    ImageView taskAdd;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.plan_create)
    TextView planCreate;
    @BindView(R.id.plan_refresh)
    SwipeRefreshLayout mRefrsh;
    private PersonalTaskAdapter personalTaskAdapter;
    private TimePickerView pvTime;
    private List<GroupTaskBean> result = new ArrayList<>();
    private String time;
    private String id;

    private String gId;
    private String week;
    private String year,month,day;
    private Disposable refreshPersonal;
    private String depid,userid;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        String jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)){
            userid=SPUtil.getUserId(getContext());
        }else if (jobType.contains(Constant.RUNNING_SQUAD_LEADER) ) {   //检修班班长，组员,验收，保电，安全专责只能看周计划
            depid=SPUtil.getDepId(getContext());
          taskAdd.setVisibility(View.GONE);
        }else if (jobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)){
            taskAdd.setVisibility(View.GONE);
        }else  if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER)){
            userid=SPUtil.getUserId(getContext());
            taskAdd.setVisibility(View.GONE);
        }
        planCreate.setVisibility(View.GONE);
        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate();
        taskTitle.setText("个人任务列表");
        taskDate.setText(time);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);

        personalTaskAdapter = new PersonalTaskAdapter(R.layout.fragment_task_item, result);
        planRv.setAdapter(personalTaskAdapter);
        personalTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GroupTaskBean bean = result.get(position);
                Intent intent=new Intent();
                intent.setClass(getContext(), PersonalTaskDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("bean",bean);
                intent.putExtras(bundle);
                startActivityForResult(intent,12);
            }
        });
        getGroupList();
        mRefrsh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGroupList();
            }
        });
        refreshPersonal = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.equals("refreshPersonal")) {
                    getGroupList();
                }
            }
        });
    }
    public void inteDate() {
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        day = Integer.parseInt(days[0]) + "";
    }
//    //获取个人任务列表
//    public void getPersonalList() {
//
//        ProgressDialog.show(getContext(),false,"正在加载。。。");
//        BaseRequest.getInstance().getService()
//                .getPersonalList(year,month, day, SPUtil.getUserId(getContext()))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<PersonalTaskListBean>>(getContext()) {
//                    @Override
//                    protected void onSuccees(BaseResult<List<PersonalTaskListBean>> t) throws Exception {
//                        result = t.getResults();
//                        personalTaskAdapter.setNewData(result);
//                        ProgressDialog.cancle();
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        ProgressDialog.cancle();
//                    }
//                });
//
//    }

    //获取小组任务列表
    public void getGroupList() {
        BaseRequest.getInstance().getService()
                .getPersonalOfGroup(year, month, day,depid,null,"1",userid,"type_sign,line_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GroupTaskBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<GroupTaskBean>> t) throws Exception {
                        result = t.getResults();
                        personalTaskAdapter.setNewData(result);
                        mRefrsh.setRefreshing(false);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mRefrsh.setRefreshing(false);
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
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                time = DateUatil.getDay(date);
                taskDate.setText(time);
                inteDate();
                getGroupList();
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
    @OnClick({R.id.task_date, R.id.task_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showDay();
                break;
            case R.id.task_add:
                Intent intent = new Intent(getContext(), AddPersonalTaskActivity.class);
                startActivityForResult(intent,12);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==12&&resultCode==-1){
            getGroupList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (refreshPersonal!=null){
            refreshPersonal.dispose();
        }
    }
}
