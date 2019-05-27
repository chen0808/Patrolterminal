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
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.PlanTypeBean;
import com.patrol.terminal.bean.TypeBean;
import com.patrol.terminal.utils.RxRefreshEvent;
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


    private int year, month, day;
    private List<PlanTypeBean> typeList = new ArrayList<>();
    private PersonalTaskDetailAdapter monthPlanDetailAdapter;
    private GroupTaskBean bean;
    private List<PersonalTaskListBean> results=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_plan_detail);
        ButterKnife.bind(this);
        initView();
        initdata();
        getPersonalList();
    }

    private void initdata() {
        bean = getIntent().getParcelableExtra("bean");
        SPUtil.put(this, "ids", "task_id", bean.getId());
             tvLineType.setVisibility(View.VISIBLE);
             tvLineType.setText("执行人 : "+bean.getWork_user_name());
            tvTableName.setText(bean.getName());
            tvLineName.setText("线路名称 : " + bean.getLine_name());
            tvLineNo.setText("班  组 : " + SPUtil.getDepName(PersonalTaskDetailActivity.this));
            tvLineDate.setText("杆塔名称 : "+ bean.getName());
            year=bean.getYear();
        month=bean.getMonth();
        day=bean.getDay();

    }


    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    private void initView() {
        tvLineTower.setVisibility(View.GONE);

        titleName.setText("个人任务详情");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        monthPlanDetailRc.setLayoutManager(manager);
        monthPlanDetailAdapter = new PersonalTaskDetailAdapter(R.layout.item_plan_detail, results);
        monthPlanDetailRc.setAdapter(monthPlanDetailAdapter);
        monthPlanDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PersonalTaskListBean bean = results.get(position);
                Intent intent = new Intent();
                intent.putExtra("line_id",bean.getLine_id());
                intent.putExtra("line_name",bean.getLine_name());
                intent.putExtra("tower_id",bean.getTower_id());
                intent.putExtra("tower_name",bean.getTower_name());
                intent.putExtra("task_id",bean.getId());
                intent.putExtra("sign",bean.getType_sign());
                intent.putExtra("audit_status",bean.getAudit_status());
                intent.putExtra("typename",bean.getType_name());
                switch (bean.getType_sign()) {
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

                startActivityForResult(intent,25);
            }
        });
    }

    //获取个人任务列表
    public void getPersonalList() {

        ProgressDialog.show(this,false,"正在加载。。。");
        BaseRequest.getInstance().getService()
                .getPersonalListOfGroup(year+"",month+"", day+"",bean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PersonalTaskListBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<PersonalTaskListBean>> t) throws Exception {
                        results = t.getResults();
                        monthPlanDetailAdapter.setNewData(results);
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==25&&resultCode==RESULT_OK){
            getPersonalList();
            RxRefreshEvent.publish("refreshPersonal");
        }
    }
}
