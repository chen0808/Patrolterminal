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

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.MonthPlanDetailActivity;
import com.patrol.terminal.activity.SpecialPlanDetailActivity;
import com.patrol.terminal.activity.TemporaryActivity;
import com.patrol.terminal.adapter.MonthPlanAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.MonthListBean;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.SubmitPlanReqBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.PopMenmuDialog;
import com.patrol.terminal.widget.ProgressDialog;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
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
    @BindView(R.id.task_screen)
    ImageView taskScreen;

    private MonthPlanAdapter monthPlanAdapter;
    private TimePickerView pvTime;
    private List<Tower> lineList = new ArrayList<>();
    private List<MonthPlanBean> data = new ArrayList<>();
    private List<MonthPlanBean> data1 = new ArrayList<>();
    private List<MonthPlanBean> data2 = new ArrayList<>();
    private String time;
    private String typeId;
    private String month, year;
    private Context mContext;
    private String state;
    private String mJobType;
    private String depId;
    private PopMenmuDialog popWinShare;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    protected void initData() {
        mContext = getActivity();
        taskScreen.setVisibility(View.VISIBLE);
        depId = SPUtil.getDepId(getContext());
        mJobType = SPUtil.getString(mContext, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        //判断
        if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
            state = "1,2,3,4,5";
            depId = null;
            planSubmit.setText("审核");
            planCreate.setVisibility(View.INVISIBLE);
        } else if (mJobType.contains(Constant.RUN_SUPERVISOR)) {
            state = "2,3,4,5";
            planSubmit.setText("审核");
            depId = null;
            planSubmit.setVisibility(View.VISIBLE);
            planCreate.setVisibility(View.INVISIBLE);
        }else if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER)){
            taskAdd.setVisibility(View.VISIBLE);
        }
        time = DateUatil.getCurMonth();
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        taskDate.setText(time);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        planRv.setLayoutManager(manager);
        monthPlanAdapter = new MonthPlanAdapter(R.layout.fragment_plan_item, data, state, mJobType);
        planRv.setAdapter(monthPlanAdapter);
        monthPlanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                MonthPlanBean bean = (MonthPlanBean) data.get(position);
                Intent intent = new Intent();

                if (bean.getRepair_content() == null) {
                    intent.setClass(getContext(), MonthPlanDetailActivity.class);
                    intent.putExtra("year", bean.getYear());
                    intent.putExtra("month", bean.getMonth());
                    intent.putExtra("month_id", bean.getMonth_id());
                    intent.putExtra("month_line_id", bean.getId());
                    intent.putExtra("id", bean.getLine_id());
                } else {
                    intent.setClass(getContext(), SpecialPlanDetailActivity.class);
                    intent.putExtra("from","month");
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("bean", bean);
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }

        });

        getMonthPlanList();
    }

    //获取月计划列表
    public void getMonthPlanList() {
        ProgressDialog.show(getContext(), false, "正在加载中");
        data.clear();
        data1.clear();
        data2.clear();
        BaseRequest.getInstance().getService()
                .getMonthPlan(Integer.parseInt(year), Integer.parseInt(month), depId, state,"type_sign,line_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MonthListBean>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<MonthListBean> t) throws Exception {

                        if (t.getCode() == 1) {
                            MonthListBean results = t.getResults();
                            lineList.clear();
                            getData(results);
                            monthPlanAdapter.setNewData(data);

                        }
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    //提交月计划审核
    public void submitMonthPlan(String status) {
        ProgressDialog.show(getContext(), false, "正在加载中...");
        SubmitPlanReqBean bean = new SubmitPlanReqBean();
        bean.setYear(year);
        bean.setMonth(month);
        bean.setAudit_status(status);
        bean.setFrom_user_id(SPUtil.getUserId(getContext()));
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
                            RxRefreshEvent.publish("refreshTodo");
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


    @OnClick({R.id.task_date, R.id.task_add, R.id.plan_create, R.id.plan_submit,R.id.task_screen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.task_date:
                showMonth();
                break;
            case R.id.task_add:
               Intent intent=new Intent(getContext(), TemporaryActivity.class);
               intent.putExtra("year",year);
                intent.putExtra("month",month);
                startActivityForResult(intent,10);
                break;
            case R.id.plan_submit:
                if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
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

                } else if (mJobType.contains((Constant.RUNNING_SQUAD_LEADER))) {
                    submitMonthPlan("1");
                } else if (mJobType.contains(Constant.RUN_SUPERVISOR)) {
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
            case R.id.task_screen:
                if (popWinShare == null) {
                    //自定义的单击事件
                    OnClickLintener paramOnClickListener = new OnClickLintener();
                    popWinShare = new PopMenmuDialog(getActivity(), paramOnClickListener, dip2px(getContext(), 140), dip2px(getContext(), 120));
                    //监听窗口的焦点事件，点击窗口外面则取消显示
                    popWinShare.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                popWinShare.dismiss();
                            }
                        }
                    });
                }
               //设置默认获取焦点
                popWinShare.setFocusable(true);
               //以某个控件的x和y的偏移量位置开始显示窗口
                popWinShare.showAsDropDown(taskAdd, 0, 0);
               //如果窗口存在，则更新
                popWinShare.update();
                break;

        }
    }

    class OnClickLintener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.all:
                    monthPlanAdapter.setNewData(data);
                    break;
                case R.id.popmenmu1:
                    monthPlanAdapter.setNewData(data1);
                    break;
                case R.id.popmenmu2:
                    monthPlanAdapter.setNewData(data2);
                    break;
                default:
                    break;
            }
            popWinShare.dismiss();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            getMonthPlanList();
        }
    }

    private void getData(MonthListBean results) {

        List<MonthPlanBean> patrol = results.getPatrol();
        List<MonthPlanBean> ele = results.getEle();
        List<MonthPlanBean> repair = results.getRepair();
        if (patrol != null) {
            data1 = patrol;
            data.addAll(patrol);
            for (int j = 0; j < patrol.size(); j++) {

                MonthPlanBean monthPlanBean = patrol.get(j);
                //当身份是专责时，获取需要审批的列表
                if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED) && "1".equals(monthPlanBean.getAudit_status())) {
                    Tower lineBean = new Tower();
                    lineBean.setLine_id(monthPlanBean.getLine_id());
                    lineBean.setMonth_line_id(monthPlanBean.getId());
                    lineList.add(lineBean);
                    //当身份是主管时，获取需要审批的列表
                } else if (mJobType.contains(Constant.RUN_SUPERVISOR) && "2".equals(monthPlanBean.getAudit_status())) {
                    Tower lineBean = new Tower();
                    lineBean.setLine_id(monthPlanBean.getLine_id());
                    lineBean.setMonth_line_id(monthPlanBean.getId());
                    lineList.add(lineBean);
                    //当身份是班长时，获取需要审核的列表
                } else if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && "0".equals(monthPlanBean.getAudit_status())){
                    Tower lineBean = new Tower();
                    lineBean.setLine_id(monthPlanBean.getLine_id());
                    lineBean.setMonth_line_id(monthPlanBean.getId());
                    lineList.add(lineBean);
                }
            }
        }
        if (repair != null) {
            data.addAll(repair);
            data2.addAll(repair);
        }
        if (ele != null) {
            data.addAll(ele);
            data2.addAll(ele);
        }
        if (lineList.size()!=0){
                planSubmit.setVisibility(View.VISIBLE);
        }else {
            planSubmit.setVisibility(View.GONE);
        }

    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
