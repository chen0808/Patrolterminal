package com.patrol.terminal.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddMonthPlanActivity;
import com.patrol.terminal.activity.MonthPlanDetailActivity;
import com.patrol.terminal.adapter.MonthPlanAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.PatrolContentBean;
import com.patrol.terminal.bean.PatrolLevel1;
import com.patrol.terminal.bean.PatrolLevel2;
import com.patrol.terminal.bean.SubmitPlanReqBean;
import com.patrol.terminal.bean.SubmitPlanReqStateBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MonthPlanFrgment extends BaseFragment {


    @BindView(R.id.task_title)
    TextView taskTitle;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.plan_submit)
    TextView planSubmit;
    @BindView(R.id.plan_create)
    TextView planCreate;
    @BindView(R.id.task_add)
    ImageView taskAdd;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    @BindView(R.id.yx__month_rg)
    RadioGroup mRg;
    @BindView(R.id.dxdj)
    RadioButton mDxdj;
    @BindView(R.id.bdjh)
    RadioButton mbdjh;
    @BindView(R.id.ysjh)
    RadioButton mYsjh;
    @BindView(R.id.aqjh)
    RadioButton mAqjh;



    private MonthPlanAdapter monthPlanAdapter;
    private TimePickerView pvTime;
    private List<MonthPlanBean> planData = new ArrayList<>();
    private List<Tower> lineList = new ArrayList<>();
    private List<Tower> relList = new ArrayList<>();
    private String time;
    private String typeId;
    private String month, year;
    private Context mContext;
    private String state;
    private String mJobType;
    private String depId;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        mContext = getActivity();
        taskAdd.setVisibility(View.INVISIBLE);
        planSubmit.setVisibility(View.VISIBLE);
        depId = SPUtil.getDepId(getContext());
        mJobType = SPUtil.getString(mContext, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        //判断
        if (mJobType.equals(Constant.RUNNING_SQUAD_SPECIALIZED)) {
            state = "1,2,3,4,5";
            depId = null;
            planSubmit.setText("审核");
            planCreate.setVisibility(View.INVISIBLE);
        } else if (mJobType.equals(Constant.RUN_SUPERVISOR)) {
            state = "2,3,4,5";
            planSubmit.setText("审核");
            depId = null;
            planSubmit.setVisibility(View.VISIBLE);
            planCreate.setVisibility(View.INVISIBLE);
        }

        time = DateUatil.getCurMonth();
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        taskDate.setText(time);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        monthPlanAdapter = new MonthPlanAdapter(R.layout.fragment_plan_item, planData, year, month, state);
        planRv.setAdapter(monthPlanAdapter);
        monthPlanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MonthPlanBean bean = planData.get(position);
                Intent intent = new Intent();

                if (bean.getId() != null) {
                    intent.setClass(getContext(), MonthPlanDetailActivity.class);
                    intent.putExtra("year", bean.getYear());
                    intent.putExtra("month", bean.getMonth());
                } else {
//                    intent.setClass(getContext(), SpecialPlanDetailActivity.class);
//                    intent.putExtra("from","month");
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable("bean", bean.getPlanCheck());
//                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });

        getMonthPlanList();
    }

    //获取月计划列表
    public void getMonthPlanList() {
        planData.clear();
        ProgressDialog.show(getContext(), false, "正在加载中");
        BaseRequest.getInstance().getService()
                .getMonthPlan(Integer.parseInt(year), Integer.parseInt(month), depId, state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {

                        if (t.getResults().size() > 0) {
                            lineList.clear();
                            List<MonthPlanBean> result = t.getResults();
                            for (int i = 0; i < result.size(); i++) {
                                MonthPlanBean bean1 = result.get(i);
                                if (bean1.getMonth_line_id() != null) {
                                    //当身份是专责时，获取需要审批的列表
                                    if (mJobType.equals(Constant.RUNNING_SQUAD_SPECIALIZED) && "1".equals(result.get(i).getAudit_status())) {
                                        MonthPlanBean bean = result.get(i);
                                        Tower lineBean = new Tower();
                                        lineBean.setLine_id(bean.getId());
                                        lineList.add(lineBean);
                                        //当身份是主管时，获取需要审批的列表
                                    } else if (mJobType.equals(Constant.RUN_SUPERVISOR) && "2".equals(result.get(i).getAudit_status())) {
                                        MonthPlanBean bean = result.get(i);
                                        Tower lineBean = new Tower();
                                        lineBean.setLine_id(bean.getId());
                                        lineList.add(lineBean);
                                        //当身份是专责时，获取需要发布的列表
                                    } else if (mJobType.equals(Constant.RUNNING_SQUAD_LEADER) && "0".equals(result.get(i).getAudit_status())) {
                                        MonthPlanBean bean = result.get(i);
                                        Tower lineBean = new Tower();
                                        lineBean.setLine_id(bean.getId());
                                        lineList.add(lineBean);
                                    }
                                    planData.add(bean1);
                                } else {
//                                    List<InnerPlanbean> planCheckList = bean1.getPlanCheckList();
//                                    for (int j = 0; j < planCheckList.size(); j++) {
//                                        InnerPlanbean planbean = planCheckList.get(j);
//                                        MonthPlanBean monthPlanBean = new MonthPlanBean();
//                                        monthPlanBean.setPlanCheck(planbean);
//                                        planData.add(monthPlanBean);
//                                    }
                                }
                            }
                            monthPlanAdapter.setNewData(planData);
                            ProgressDialog.cancle();

                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    //提交月计划审核
    public void submitMonthPlan(String status) {
        ProgressDialog.show(getContext(), false, "正在加载中");
        SubmitPlanReqBean bean = new SubmitPlanReqBean();
        bean.setYear(year);
        bean.setMonth(month);
        bean.setAudit_status(status);
        bean.setLines(lineList);
        BaseRequest.getInstance().getService()
                .submitMonthPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            if ("1".equals(status)) {
                                Toast.makeText(getContext(), "一键提交成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "一键审核成功", Toast.LENGTH_SHORT).show();
                            }

                            getMonthPlanList();
                        } else {
                            Toast.makeText(getContext(), t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    public void showMonth() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 23);
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
                getMonthPlanList();
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


    @OnClick({R.id.task_date, R.id.task_add, R.id.plan_create, R.id.plan_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showMonth();
                break;
            case R.id.plan_submit:
                if (mJobType.equals(Constant.RUNNING_SQUAD_SPECIALIZED)) {
                    CancelOrOkDialog dialog = new CancelOrOkDialog(mContext, "一键审核", "不同意", "同意") {
                        @Override
                        public void ok() {
                            super.ok();
                            submitMonthPlan("2");   //同意
                            dismiss();
                        }

                        @Override
                        public void cancel() {
                            super.cancel();
                            submitMonthPlan("4");  //不同意
                            dismiss();
                        }
                    };
                    dialog.show();

                } else if (mJobType.equals((Constant.RUNNING_SQUAD_LEADER))) {
                    submitMonthPlan("1");
                } else if (mJobType.equals(Constant.RUN_SUPERVISOR)) {
                    CancelOrOkDialog dialog = new CancelOrOkDialog(mContext, "一键审核", "不同意", "同意") {
                        @Override
                        public void ok() {
                            super.ok();
                            submitMonthPlan("3");   //同意
                            dismiss();
                        }

                        @Override
                        public void cancel() {
                            super.cancel();
                            submitMonthPlan("4");  //不同意
                            dismiss();
                        }
                    };
                    dialog.show();
                }
                break;
            case R.id.task_add:
                startActivityForResult(new Intent(getContext(), AddMonthPlanActivity.class), 10);
                break;
            case R.id.plan_create:

                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            getMonthPlanList();
        }
    }


    //审核月计划, 同意或者拒绝
    public void checkMonthPlan(String state) {
        ProgressDialog.show(getContext(), false, "正在加载中");
        //SubmitPlanReqBean bean=new SubmitPlanReqBean();

        SubmitPlanReqStateBean bean = new SubmitPlanReqStateBean();
        if ("4".equals(state)) {
            bean.setLineIds(relList);
        } else {
            bean.setLineIds(lineList);
        }
        bean.setState(state);

        BaseRequest.getInstance().getService()
                .submitMonthPlanState(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(getContext()) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            if ("4".equals(state)) {
                                Toast.makeText(getContext(), "一键发布成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "一键审核成功", Toast.LENGTH_SHORT).show();
                            }
                            getMonthPlanList();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    private List<MultiItemEntity> getData(List<PatrolContentBean> results) {
        List<MultiItemEntity> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            PatrolLevel1 level1 = new PatrolLevel1(results.get(i).getName());
            for (int j = 0; j < results.get(i).getValue().size(); j++) {
                PatrolLevel2 level2 = new PatrolLevel2(results.get(i).getValue().get(j).getREMARKS()
                        , results.get(i).getValue().get(j).getISDEFECT().equals("N") ? true : false
                        , results.get(i).getValue().get(j).getCATEGORY()
                        , results.get(i).getValue().get(j).getNAME(),
                        results.get(i).getValue().get(j).getID());
                level1.addSubItem(j, level2);
            }
            list.add(level1);
        }
        return list;
    }
}
