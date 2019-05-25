package com.patrol.terminal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.PersonalTaskDetailAdapter;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.PlanTypeBean;
import com.patrol.terminal.bean.TypeBean;
import com.patrol.terminal.utils.SPUtil;
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

public class PersonalTaskDetailActivity extends Activity {


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
    @BindView(R.id.tv_table_name)
    TextView tvTableName;
    @BindView(R.id.tv_line_no)
    TextView tvLineNo;
    @BindView(R.id.tv_line_name)
    TextView tvLineName;
    @BindView(R.id.tv_line_date)
    TextView tvLineDate;

    @BindView(R.id.month_plan_detail_rc)
    RecyclerView monthPlanDetailRc;
    @BindView(R.id.tv_line_type)
    TextView tvLineType;
    @BindView(R.id.tv_line_tower)
    TextView tvLineTower;


    private String year, month, id;
    private List<PlanTypeBean> typeList = new ArrayList<>();
    private PersonalTaskDetailAdapter monthPlanDetailAdapter;
    private String sign="1";
    private PersonalTaskListBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_plan_detail);
        ButterKnife.bind(this);
        initView();
        initdata();
        getSign();
    }

    private void initdata() {
        bean = getIntent().getParcelableExtra("bean");
        id= bean.getType_id();
        SPUtil.put(this, "ids", "task_id", bean.getId());
            tvTableName.setText(bean.getName());
            tvLineName.setText("线路名称 : " + bean.getLine_name());
            tvLineNo.setText("班  组 : " + SPUtil.getDepName(PersonalTaskDetailActivity.this));
            tvLineDate.setText("杆塔名称 : "+ bean.getTower_name());


        PlanTypeBean planTypeBean = new PlanTypeBean();
        planTypeBean.setName(bean.getLine_name()+ bean.getTower_name() + bean.getType_name());
        planTypeBean.setType(0);
        typeList.add(planTypeBean);

      monthPlanDetailAdapter.setNewData(typeList);
    }


    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    private void initView() {
        tvLineTower.setVisibility(View.GONE);
        year = getIntent().getStringExtra("year");
        month = getIntent().getStringExtra("month");


        titleName.setText("个人任务详情");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        monthPlanDetailRc.setLayoutManager(manager);
        monthPlanDetailAdapter = new PersonalTaskDetailAdapter(R.layout.item_plan_detail, typeList);
        monthPlanDetailRc.setAdapter(monthPlanDetailAdapter);
        monthPlanDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("line_id",bean.getLine_id());
                intent.putExtra("line_name",bean.getLine_name());
                intent.putExtra("tower_id",bean.getTower_id());
                intent.putExtra("tower_name",bean.getTower_name());
                intent.putExtra("task_id",bean.getId());
                intent.putExtra("sign",sign);
                intent.putExtra("typename",bean.getType_name());
                switch (sign) {
                    case "1":
                        intent.setClass(PersonalTaskDetailActivity.this, PatrolRecordActivity.class);
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "tower_id", bean.getTower_id());
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "line_id", bean.getLine_id());
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "line_name", bean.getLine_name());
                        SPUtil.put(PersonalTaskDetailActivity.this, "ids", "tower_name", bean.getTower_name());
                        break;
                    case "2":
                        intent.setClass(PersonalTaskDetailActivity.this, HongWaiCeWenActivity.class);
                        break;
                    case "3":
                        intent.setClass(PersonalTaskDetailActivity.this, JiediDianZuCeLiangActicivity.class);
                        break;
                    case "10":
                        intent.setClass(PersonalTaskDetailActivity.this, JueYuanZiLingZhiJianCeActivity.class);
                        break;
                    case "5":
                        intent.setClass(PersonalTaskDetailActivity.this, HongWaiCeWenActivity.class);
                        break;
                    case "6":
                        intent.setClass(PersonalTaskDetailActivity.this, XieGanTaQingXieCeWenActivity.class);
                        break;


                }

                startActivity(intent);
            }
        });
    }


    //获取任务类型
    public void getSign() {
        ProgressDialog.show(this,false,"正在加载。。。");

        BaseRequest.getInstance().getService()
                .getSign(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TypeBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<TypeBean> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            TypeBean results = t.getResults();
                            sign=results.getSign();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }
}
