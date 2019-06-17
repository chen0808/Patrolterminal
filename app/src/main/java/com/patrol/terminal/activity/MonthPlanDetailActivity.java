package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MonthPlanDetailAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DefectBean;
import com.patrol.terminal.bean.MonthPlanDetailBean;
import com.patrol.terminal.bean.Trace;
import com.patrol.terminal.network.ApiServise;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MonthPlanDetailActivity extends BaseActivity {


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
    private List<Trace> traceList = new ArrayList<>();

    private int year;
    private int month;
    private String id;

    private MonthPlanDetailAdapter monthPlanDetailAdapter;
    private String id1;
    private List<DefectBean> selectType = new ArrayList<>();
    private String month_id;
    private String line_id, month_line_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_plan_detail);
        ButterKnife.bind(this);
        initView();
        getMonthDetail();
        getHaveDefect();
    }


    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    private void initView() {
        tvLineTower.setVisibility(View.GONE);
        year = getIntent().getIntExtra("year", 2019);
        month = getIntent().getIntExtra("month", 4);
        line_id = getIntent().getStringExtra("id");
        month_line_id = getIntent().getStringExtra("month_line_id");
        month_id = getIntent().getStringExtra("month_id");

        titleName.setText("月计划详情");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        monthPlanDetailRc.setLayoutManager(manager);
        monthPlanDetailAdapter = new MonthPlanDetailAdapter(R.layout.item_plan_detail, selectType);
        monthPlanDetailRc.setAdapter(monthPlanDetailAdapter);

    }

    //获取已经添加的缺陷列表
    public void getHaveDefect() {
        ApiServise service = BaseRequest.getInstance().getService();
        Observable<BaseResult<List<DefectBean>>> haveDefect = service.getHaveDefect(month_line_id, "1,2,3", "1");
        Observable<BaseResult<List<DefectBean>>> haveDanger = service.getHaveDanger(month_line_id, "1,2,3", "1");
        Observable.zip(haveDefect, haveDanger, new BiFunction<BaseResult<List<DefectBean>>, BaseResult<List<DefectBean>>, String>() {
            @Override
            public String apply(BaseResult<List<DefectBean>> listBaseResult, BaseResult<List<DefectBean>> listBaseResult2) throws Exception {
                List<DefectBean> results = listBaseResult.getResults();
                List<DefectBean> results2 = listBaseResult2.getResults();
                for (int i = 0; i < results.size(); i++) {
                    DefectBean defectBean = results.get(i);
                    defectBean.setType(2);
                    defectBean.setContent(defectBean.getContent() + "缺陷消除");
                    selectType.add(defectBean);
                }
                for (int i = 0; i < results2.size(); i++) {
                    DefectBean defectBean = results2.get(i);
                    defectBean.setType(1);
                    defectBean.setContent(defectBean.getContent() + "隐患处理");
                    selectType.add(defectBean);
                }
                return listBaseResult.getCode() + "";
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String jsonObject) throws Exception {
                        ProgressDialog.cancle();
                        if ("1".equals(jsonObject)) {
                            monthPlanDetailAdapter.setNewData(selectType);
                        }
                    }
                }, new Consumer<Throwable>() {//请求失败处理
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }


    //获取月计划详情
    public void getMonthDetail() {
        BaseRequest.getInstance().getService()
                .getMonthDetail(year, month, line_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanDetailBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanDetailBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            List<MonthPlanDetailBean> results = t.getResults();
                            for (int i = 0; i < results.size(); i++) {
                                MonthPlanDetailBean monthPlanDetailBean = results.get(i);
                                String type_sign = monthPlanDetailBean.getType_sign();
                                if (i == 0) {
                                    tvTableName.setText(monthPlanDetailBean.getPlan_name());
                                    tvLineName.setText("线路名称 : " + monthPlanDetailBean.getLine_name());
                                    tvLineNo.setText("班  组 : " + monthPlanDetailBean.getDep_name());
                                    tvLineDate.setText("月  份 : " + monthPlanDetailBean.getYear() + "年" + monthPlanDetailBean.getMonth() + "月");

                                }
                                String[] split = type_sign.split(",");
                                for (int j = 0; j < split.length; j++) {
                                    String sign = split[j];
                                    DefectBean defectBean = new DefectBean();
                                    defectBean.setType(0);
                                    defectBean.setContent(StringUtil.typeSigns[Integer.valueOf(sign) - 1] + "计划");
                                    defectBean.setFind_time("");
                                    selectType.add(defectBean);
                                }
                                tvLineTower.setVisibility(View.VISIBLE);
                                tvLineTower.setText("电压等级 : " + monthPlanDetailBean.getVoltage_level());

                            }

                            monthPlanDetailAdapter.setNewData(selectType);
                        }


                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
}
