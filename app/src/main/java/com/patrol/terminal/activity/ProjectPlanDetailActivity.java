package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.ProjectPlanBean;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：陈飞
 * 时间：2019/08/21 15:51
 */
public class ProjectPlanDetailActivity extends BaseActivity {
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
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.tv_plan_no)
    TextView tvPlanNo;
    @BindView(R.id.tv_plan_name)
    TextView tvPlanName;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_duty_user_name)
    TextView tvDutyUserName;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rl_content)
    LinearLayout rlContent;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.rl_remark)
    LinearLayout rlRemark;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.defect_gridView)
    GridView defectGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_plan_detail);
        ButterKnife.bind(this);
        String id = getIntent().getStringExtra("id");
        initData(id);
    }

    private void initData(String id) {
        BaseRequest.getInstance().getService()
                .getProjectPlanDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ProjectPlanBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<ProjectPlanBean> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
                            ProjectPlanBean results = t.getResults();
                            tvProjectName.setText(results.getTemp_project_name());
                            tvPlanNo.setText(results.getPlan_no());
                            tvPlanName.setText(results.getName());
                            tvMoney.setText(results.getMoney());
                            tvDutyUserName.setText(results.getDuty_user_name());
                            tvStartTime.setText(results.getStart_date());
                            tvEndTime.setText(results.getFinish_date());
//                            tvContent.setText(results.getContent);
                            tvRemark.setText(results.getRemarks());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast(e.getMessage());
                    }
                });
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
