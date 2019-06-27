package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.OverPlanReqBean;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
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

public class OverhaulMonthPlanFrgment extends BaseFragment {


    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.task_add)
    ImageView taskAdd;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.plan_submit)
    TextView planSubmit;
    @BindView(R.id.plan_refresh)
    SwipeRefreshLayout planRefresh;
//    @BindView(R.id.plan_create)
//    TextView planCreate;

    private TimePickerView pvTime;
    private List<OverhaulYearBean> result = new ArrayList<>();
    private String time;
    private OverhaulMonthAdapter monthAdapter;
    private List<OverPlanReqBean> list1 = new ArrayList<>();//提交集合
    //private List<OverPlanReqBean> list2 = new ArrayList<>();//发布集合
    private List<OverPlanReqBean> list3 = new ArrayList<>();//审核集合
    private String year;
    private String month;
    private String week;
    private String jobType;

    private Disposable updateMonthPlan;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        taskAdd.setVisibility(View.VISIBLE);
        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)) {
            planSubmit.setText("提交");
            //planCreate.setVisibility(View.VISIBLE);
        } else if (jobType.contains(Constant.MAINTENANCE_SUPERVISOR)) {
            planSubmit.setText("审核");
        }
        time = SPUtil.getString(getContext(), "date", "overhaulTime", DateUatil.getTime(new Date(System.currentTimeMillis())));
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        //week = DateUatil.getWeekNum()+"";
        taskTitle.setText("月计划列表");
        taskDate.setText(time /*+ "第" + week + "周"*/);

        // 设置监听器。
        planRv.setSwipeMenuCreator(mSwipeMenuCreator);
        // 菜单点击监听。
        planRv.setOnItemMenuClickListener(mItemMenuClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        monthAdapter = new OverhaulMonthAdapter(R.layout.fragment_overhaul_month_week_item, result);
        planRv.setAdapter(monthAdapter);
        monthAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (result.get(position) == null) {
                    return;
                }

                String monthAuditStatus = result.get(position).getMonth_audit_status();
                if ("0".equals(monthAuditStatus)) {  //待提交审核   可修改
                    Intent intent = new Intent();
                    intent.setClass(getContext(), OverhaulAddMonthPlanActivity.class);
                    Bundle bundle = new Bundle();
                    if (result.get(position) != null) {
                        bundle.putParcelable("bean", result.get(position));
                    }
                    intent.putExtra("add_month_from_type", 0);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {                            //其他
                    Intent intent = new Intent();
                    intent.setClass(getContext(), OverhaulMonthDetailActivity.class);
                    Bundle bundle = new Bundle();
                    if (result.get(position) != null) {
                        bundle.putParcelable("bean", result.get(position));
                    }
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
        //getWeekList();
        getMonthList();

        updateMonthPlan = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                if (s.startsWith("updateMonthPlan")) {
                    getMonthList();
                }
            }
        });
        planRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMonthList();
            }
        });
    }


    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
        }
    };


    public void getMonthList() {
        BaseRequest.getInstance().getService()
                .getOverhaulPlanList(year, month, null, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulYearBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulYearBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            planRefresh.setRefreshing(false);
                            result = t.getResults();
                            monthAdapter.setNewData(result);
                            for (int i = 0; i < result.size(); i++) {
                                OverhaulYearBean overhaulYearBean = result.get(i);
                                if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED) && "0".equals(overhaulYearBean.getMonth_audit_status())) {   //专责   审核状态   //0:编制   1:待主管审核   2:审核通过    3:审核不通过
                                    OverPlanReqBean bean = new OverPlanReqBean();
                                    bean.setId(overhaulYearBean.getId());
                                    bean.setMonth_audit_status("1");
                                    list1.add(bean);
                                    planSubmit.setVisibility(View.VISIBLE);
                                }
//                                else if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)&&"2".equals(overhaulYearBean.getMonth_audit_status())){   //专责   审核状态
//                                    OverPlanReqBean bean=new OverPlanReqBean();
//                                    bean.setId(overhaulYearBean.getId());
//                                    bean.setAudit_status("2");
//                                    list2.add(bean);
//                                }
                                else if (jobType.contains(Constant.MAINTENANCE_SUPERVISOR) && "1".equals(overhaulYearBean.getMonth_audit_status())) {   //主管   审核状态  目前只做了审核通过  TODO
                                    OverPlanReqBean bean = new OverPlanReqBean();
                                    bean.setId(overhaulYearBean.getId());
                                    bean.setMonth_audit_status("2");
                                    bean.setWeek_audit_status("1");
                                    list3.add(bean);
                                    planSubmit.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        planRefresh.setRefreshing(false);
                        Log.e("fff", e.toString());
                    }
                });
    }


    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

//            // 左侧还是右侧菜单：
//            int direction = menuBridge.getDirection();
//            // 菜单在Item中的Position：
//            int menuPosition = menuBridge.getPosition();
        }
    };


    @OnClick({R.id.task_date, R.id.task_add, R.id.plan_submit/*,R.id.plan_create*/})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showMonth();
                break;
            case R.id.task_add:
                Intent intent = new Intent(getContext(), OverhaulAddMonthPlanActivity.class);
                intent.putExtra("add_month_from_type", 1);
                startActivityForResult(intent, 10);
                break;
            case R.id.plan_submit:
                if (jobType.contains(Constant.REFURBISHMENT_SPECIALIZED)) {
                    CancelOrOkDialog dialog = new CancelOrOkDialog(getContext(), "是否提交审核?", "取消", "确定") {
                        @Override
                        public void ok() {
                            super.ok();
                            submitMonthPlan(list1);   //同意
                            dismiss();
                        }

                        @Override
                        public void cancle() {
                            super.cancle();
                            dismiss();
                        }
                    };
                    dialog.show();

                } else if (jobType.contains(Constant.MAINTENANCE_SUPERVISOR)) {
                    CancelOrOkDialog dialog = new CancelOrOkDialog(getContext(), "一键处理审核", "不同意", "同意") {
                        @Override
                        public void ok() {
                            super.ok();
                            submitMonthPlan(list3);   //同意
                            dismiss();
                        }

                        @Override
                        public void cancle() {
                            super.cancle();
                            for (int i = 0; i < list3.size(); i++) {
                                OverPlanReqBean bean = list3.get(i);
                                bean.setMonth_audit_status("3");
                            }
                            submitMonthPlan(list3);   //不同意
                            dismiss();
                        }
                    };
                    dialog.show();
                }
                break;
//            case R.id.plan_create:
//                CancelOrOkDialog dialog = new CancelOrOkDialog(getContext(), "是否发布?", "取消", "确定") {
//                    @Override
//                    public void ok() {
//                        super.ok();
//                        submitMonthPlan(list2);   //同意
//                        dismiss();
//                    }
//
//                    @Override
//                    public void cancel() {
//                        super.cancel();
//                        dismiss();
//                    }
//                };
//                dialog.show();
//
//                break;
        }
    }


    //一键提交月计划审核
    public void submitMonthPlan(List<OverPlanReqBean> list1) {
        ProgressDialog.show(getContext(), false, "正在加载中");
        BaseRequest.getInstance().getService()
                .submitMonthOve(list1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            Toast.makeText(getContext(), "成功受理", Toast.LENGTH_SHORT).show();
                            getMonthList();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }


    //展示月份
/*    public void showMonth() {

        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                //返回的分别是三个级别的选中位置
                String time =  years.get(options1)  + months.get(options1).get(option2)  *//*+ weeks.get(options1).get(option2).get(options3)*//* ;
                year=years.get(options1).split("年")[0];
                month= months.get(options1).get(option2).split("月")[0];
                //week=  weeks.get(options1).get(option2).get(options3).substring(1,2);
                taskDate.setText(time);
                //getWeekList();
                getMonthList();
            }
        }).build();
        pvOptions.setPicker(years, months*//*, weeks*//*);
        pvOptions.setSelectOptions(years.indexOf(year + "年"),Integer.parseInt(month)-1,Integer.parseInt(week)-1);
        pvOptions.show();
    }*/

    public void showMonth() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2008, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2028, 2, 28);
        //时间选择器 ，自定义布局
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                time = DateUatil.getTime(date);
                taskDate.setText(time);
                String[] years = time.split("年");
                String[] months = years[1].split("月");
                month = Integer.parseInt(months[0]) + "";
                year = years[0];
                getMonthList();
                RxRefreshEvent.publish("month@" + time);
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentTextSize(18)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        pvTime.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            //getWeekList();
            getMonthList();
        }
    }
}
