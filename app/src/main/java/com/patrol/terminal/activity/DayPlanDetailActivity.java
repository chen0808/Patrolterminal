package com.patrol.terminal.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.WeekPlanDetailAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.DayListBean;
import com.patrol.terminal.bean.DefectBean;
import com.patrol.terminal.bean.Trace;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DayPlanDetailActivity extends BaseActivity {


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

    private String year, month, id;

    private List<DefectBean> typeList = new ArrayList<>();
    private WeekPlanDetailAdapter monthPlanDetailAdapter;
    private String line_id;
    private String d_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_plan_detail);
        ButterKnife.bind(this);
        initView();

    }


    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    private void initView() {

        DayListBean bean = getIntent().getParcelableExtra("bean");
        year =bean.getYear()+"";
        month = bean.getMonth()+"";
        id = bean.getId()+"";
        line_id = bean.getLine_id();
        d_id =bean.getDay_id();
        titleName.setText("日计划详情");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        monthPlanDetailRc.setLayoutManager(manager);

        monthPlanDetailAdapter = new WeekPlanDetailAdapter(R.layout.item_plan_detail, typeList);
        monthPlanDetailRc.setAdapter(monthPlanDetailAdapter);
         initdata(bean);
    }

    private void initdata(DayListBean monthPlanDetailBean) {
//        tvTableName.setText(monthPlanDetailBean.getPlan_name());
        tvLineName.setText("线路名称 : " + monthPlanDetailBean.getLine_name());
        tvLineNo.setText("班  组 : " + SPUtil.getDepName(DayPlanDetailActivity.this));
        tvLineDate.setText("月  份 : " + monthPlanDetailBean.getYear() + "年" + monthPlanDetailBean.getMonth() + "月");
         tvLineTower.setText("杆  段 : " +monthPlanDetailBean.getName());

        String type_sign = monthPlanDetailBean.getType_sign();
        String[] split = type_sign.split(",");
        for (int i = 0; i <split.length; i++) {
            String type = split[i];
            DefectBean planTypeBean = new DefectBean();
            planTypeBean.setContent(StringUtil.getTypeSign(type) + "计划");
            planTypeBean.setType(0);
            typeList.add(planTypeBean);
        }
         monthPlanDetailAdapter.setNewData(typeList);
    }

//    //获取已经添加的缺陷列表
//    public void getHaveDefect() {
//        ApiServise service = BaseRequest.getInstance().getService();
//        Observable<BaseResult<List<DefectBean>>> haveDefect = service.getHaveDefect(line_id, null, null, id, "3", "1");
//        Observable<BaseResult<List<DefectBean>>> haveDanger = service.getHaveDanger(line_id, null, null, id, "3", "1");
//        Observable.zip(haveDefect,haveDanger, new BiFunction<BaseResult<List<DefectBean>>,BaseResult<List<DefectBean>>, String>() {
//            @Override
//            public String apply(BaseResult<List<DefectBean>> listBaseResult, BaseResult<List<DefectBean>> listBaseResult2) throws Exception {
//                List<DefectBean> results = listBaseResult.getResults();
//                List<DefectBean> results2 = listBaseResult2.getResults();
//                for (int i = 0; i < results.size(); i++) {
//                    DefectBean defectBean = results.get(i);
//                    defectBean.setType(2);
//                    defectBean.setContent(defectBean.getContent()+"缺陷消除");
//                    typeList.add(defectBean);
//                }
//                for (int i = 0; i < results2.size(); i++) {
//                    DefectBean defectBean = results2.get(i);
//                    defectBean.setType(1);
//                    defectBean.setContent(defectBean.getContent()+"隐患处理");
//                    typeList.add(defectBean);
//                }
//                return listBaseResult.getCode()+"";
//            }
//        }).subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String jsonObject) throws Exception {
//                        if ("1".equals(jsonObject)){
//                            monthPlanDetailAdapter.setNewData(typeList);
//                        }
//                    }
//                }, new Consumer<Throwable>() {//请求失败处理
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                    }
//                });
//    }

}
