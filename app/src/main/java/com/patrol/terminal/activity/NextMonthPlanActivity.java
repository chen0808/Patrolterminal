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
import com.patrol.terminal.adapter.NextMonthPlanAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.SubmitPlanReqBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NextMonthPlanActivity extends BaseActivity {


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
    private String state;
    private NextMonthPlanAdapter monthPlanAdapter;
    private List<MonthPlanBean> list;
    private String mJobType;
    private List<Tower> lineList=new ArrayList<>();
    private int year;
    private int month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_month_plan);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        list = (List<MonthPlanBean>) getIntent().getSerializableExtra("list");
        lineList = (List<Tower>) getIntent().getSerializableExtra("linelist");

        year = getIntent().getIntExtra("year",2019);
        month = getIntent().getIntExtra("month",6);
        titleName.setText(year + "年" + month + "月计划列表");
        MonthPlanBean monthPlanBean = list.get(0);
        state = monthPlanBean.getAudit_status();
         mJobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, Constant.RUNNING_SQUAD_LEADER);
        //判断
        if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && "0".equals(state)) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("提交");
        }  if (mJobType.contains(Constant.RUNNING_SQUAD_LEADER) && "1".equals(state)) {
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
        monthPlanAdapter = new NextMonthPlanAdapter(R.layout.fragment_plan_item, list, state, mJobType);
        nextPlanRv.setAdapter(monthPlanAdapter);
        adapterClick();
    }

    public void adapterClick() {
        monthPlanAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MonthPlanBean item = (MonthPlanBean) adapter.getItem(position);
                //判断是否是保电计划
                if (item.getRepair_content() != null) {
                    switch (view.getId()) {
                        case R.id.plan_to_change:
                            Intent intent = new Intent(NextMonthPlanActivity.this, LineCheckActivity.class);
                            intent.putExtra("from", Constant.FROM_MONTHPLAN_TO_ADDMONTH);
                            intent.putExtra("id", item.getId());
                            intent.putExtra("year", item.getYear());
                            intent.putExtra("month", item.getMonth());
                            startActivityForResult(intent, 10);
                            break;
                    }
                } else {
                    switch (view.getId()) {
                        case R.id.plan_to_change:
                            Intent intent = new Intent(NextMonthPlanActivity.this, AddMonthPlanActivity.class);
                            intent.putExtra("from", Constant.FROM_MONTHPLAN_TO_ADDMONTH);
                            intent.putExtra("line_name", item.getLine_name());
                            intent.putExtra("year", item.getYear() + "");
                            intent.putExtra("month", item.getMonth() + "");
                            intent.putExtra("line_id", item.getLine_id() + "");
                            intent.putExtra("id", item.getId());
                            intent.putExtra("type", item.getType_sign());
                            startActivity(intent);
                            break;
                    }
                }
            }
        });
        monthPlanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                MonthPlanBean bean = (MonthPlanBean) adapter.getItem(position);
                Intent intent = new Intent();

                if (bean.getRepair_content() != null) {
                    intent.setClass(NextMonthPlanActivity.this, SpecialPlanDetailActivity.class);
                    intent.putExtra("from", "month");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", bean);
                    intent.putExtras(bundle);
                } else {
                    intent.setClass(NextMonthPlanActivity.this, MonthPlanDetailActivity.class);
                    intent.putExtra("year", bean.getYear());
                    intent.putExtra("month", bean.getMonth());
                    intent.putExtra("month_id", bean.getMonth_id());
                    intent.putExtra("month_line_id", bean.getId());
                    intent.putExtra("id", bean.getLine_id());
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
            if ("0".equals(state)){  CancelOrOkDialog dialog = new CancelOrOkDialog(this, "是否提交审核", "取消", "确定") {
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
                dialog.show();}else {
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
        ProgressDialog.show(NextMonthPlanActivity.this, false, "正在加载中...");
        SubmitPlanReqBean bean = new SubmitPlanReqBean();
        bean.setYear(year+"");
        bean.setMonth(month+"");
        bean.setAudit_status(status);
        bean.setFrom_user_id(SPUtil.getUserId(NextMonthPlanActivity.this));
        bean.setLines(list);
        BaseRequest.getInstance().getService()
                .submitMonthPlan(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(NextMonthPlanActivity.this) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            if ("1".equals(status)) {
                                Toast.makeText(NextMonthPlanActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(NextMonthPlanActivity.this, "审核成功", Toast.LENGTH_SHORT).show();
                            }
                            RxRefreshEvent.publish("refreshTodo");
                           setResult(RESULT_OK);
                           finish();
                        } else {
                            Toast.makeText(NextMonthPlanActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }
}
