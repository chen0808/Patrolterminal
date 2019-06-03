package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.NextWeekPlanAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.SubmitPlanReqBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.bean.WeekListBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.TimeUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.ProgressDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NextWeekPlanActivity extends BaseActivity {


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
    @BindView(R.id.next_plan_rv)
    RecyclerView nextPlanRv;
    @BindView(R.id.plan_total_title)
    TextView planTotalTitle;
    @BindView(R.id.month_line_total)
    TextView monthLineTotal;
    @BindView(R.id.month_line_kilo_total)
    TextView monthLineKiloTotal;
    @BindView(R.id.month_line_110kv_num)
    TextView monthLine110kvNum;
    @BindView(R.id.month_line_110kv_kilo)
    TextView monthLine110kvKilo;
    @BindView(R.id.month_line_35kv_num)
    TextView monthLine35kvNum;
    @BindView(R.id.month_line_35kv_kilo)
    TextView monthLine35kvKilo;
    private String state;
    private NextWeekPlanAdapter monthPlanAdapter;
    private List<WeekListBean> list;
    private String mJobType;
    private List<Tower> lineList = new ArrayList<>();
    private int year;
    private int week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_month_plan);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        planTotalTitle.setText("下周工作计划汇总");
        list = (List<WeekListBean>) getIntent().getSerializableExtra("list");
        lineList = (List<Tower>) getIntent().getSerializableExtra("linelist");

        int num_total = getIntent().getIntExtra("num_total", 0);
        double kilo_total = getIntent().getDoubleExtra("kilo_total", 0);
        int num_110kv = getIntent().getIntExtra("110kv_num", 0);
        double kilo_110kv = getIntent().getDoubleExtra("110kv_kolo", 0);
        int num_35kv = getIntent().getIntExtra("35kv_num", 0);
        double kilo_35kv = getIntent().getDoubleExtra("35kv_kolo", 0);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        monthLineTotal.setText("工作线路总数 : " + num_total + "条");
        monthLineKiloTotal.setText("总公里数 : " + decimalFormat.format(kilo_total) + "公里");
        monthLine110kvNum.setText("110kv线路总数 : " + num_110kv + "条");
        monthLine110kvKilo.setText("公里数 : " + decimalFormat.format(kilo_110kv) + "公里");
        monthLine35kvNum.setText("35kv线路总数 : " + num_35kv + "条");
        monthLine35kvKilo.setText("公里数 : " + decimalFormat.format(kilo_35kv) + "公里");
        year = getIntent().getIntExtra("year", 2019);
        week = getIntent().getIntExtra("week", 23);
        String nextBeginTime = TimeUtil.getFirstDayOfWeek(year,week);
        String nextEndTime = TimeUtil.getLastDayOfWeek(year,week);
        titleName.setText("第"+week+"周计划("+nextBeginTime+"-"+nextEndTime+")");
        WeekListBean weekListBean = list.get(0);
        state = weekListBean.getAudit_status();
        mJobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        //判断
        if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && "0".equals(state)) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("提交");
        }
        if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && "1".equals(state)) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("撤回");
        } else if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED) && "1".equals(state)) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("审核");
        } else if (mJobType.contains(Constant.RUN_SUPERVISOR) && "2".equals(state)) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("审核");
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        nextPlanRv.setLayoutManager(manager);
        monthPlanAdapter = new NextWeekPlanAdapter(R.layout.fragment_plan_item, list, state, mJobType);
        nextPlanRv.setAdapter(monthPlanAdapter);
        adapterClick();
    }

    public void adapterClick() {

        monthPlanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(NextWeekPlanActivity.this, WeekPlanDetailActivity.class);
                if (list.get(position).getWeek_id() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("bean", list.get(position));
                    intent.putExtras(bundle);

                } else {
                    intent.setClass(NextWeekPlanActivity.this, SpecialPlanDetailActivity.class);
                    intent.putExtra("from", "week");
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("bean", list.get(position));
                    intent.putExtras(bundle);
                }
                startActivity(intent);
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
                submit(lineList);
                break;
        }
    }

    //提交月计划审核
    public void submit(List<Tower> lineList) {
        if (mJobType.contains(Constant.RUNNING_SQUAD_SPECIALIZED)) {
            CancelOrOkDialog dialog = new CancelOrOkDialog(this, "审核", "不同意", "同意") {
                @Override
                public void ok() {
                    super.ok();
                    submitMonthPlan(lineList, "2");   //同意
                    dismiss();
                }

                @Override
                public void cancel() {
                    super.cancel();
                    submitMonthPlan(lineList, "4");  //不同意
                    dismiss();
                }
            };
            dialog.show();
        } else if (mJobType.contains((Constant.RUNNING_SQUAD_LEADER))) {
            if ("0".equals(state)) {
                CancelOrOkDialog dialog = new CancelOrOkDialog(this, "是否提交审核", "取消", "确定") {
                    @Override
                    public void ok() {
                        super.ok();
                        submitMonthPlan(lineList, "1");   //同意
                        dismiss();
                    }

                    @Override
                    public void cancel() {
                        super.cancel();
                        dismiss();
                    }
                };
                dialog.show();
            } else {
                CancelOrOkDialog dialog = new CancelOrOkDialog(this, "是否撤回计划", "取消", "确定") {
                    @Override
                    public void ok() {
                        super.ok();
                        submitMonthPlan(lineList, "0");   //同意
                        dismiss();
                    }

                    @Override
                    public void cancel() {
                        super.cancel();
                        dismiss();
                    }
                };
                dialog.show();
            }


        } else if (mJobType.contains(Constant.RUN_SUPERVISOR)) {
            CancelOrOkDialog dialog = new CancelOrOkDialog(this, "审核", "不同意", "同意") {
                @Override
                public void ok() {
                    super.ok();
                    submitMonthPlan(lineList, "3");   //同意
                    dismiss();
                }

                @Override
                public void cancel() {
                    super.cancel();
                    submitMonthPlan(lineList, "4");  //不同意
                    dismiss();
                }
            };
            dialog.show();
        }
    }

    //提交月计划审核
    public void submitMonthPlan(List<Tower> list, String status) {
        ProgressDialog.show(NextWeekPlanActivity.this, false, "正在加载中...");
        SubmitPlanReqBean bean = new SubmitPlanReqBean();
        bean.setYear(year + "");
        bean.setMonth(week + "");
        bean.setAudit_status(status);
        bean.setFrom_user_id(SPUtil.getUserId(NextWeekPlanActivity.this));
        bean.setLines(list);
        BaseRequest.getInstance().getService()
                .submitMonthPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(NextWeekPlanActivity.this) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            if ("1".equals(status)) {
                                Toast.makeText(NextWeekPlanActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(NextWeekPlanActivity.this, "审核成功", Toast.LENGTH_SHORT).show();
                            }
                            RxRefreshEvent.publish("refreshTodo");
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(NextWeekPlanActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }
}
