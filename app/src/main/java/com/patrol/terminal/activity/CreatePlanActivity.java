package com.patrol.terminal.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.CreatePlanAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreatePlanActivity extends BaseActivity {


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
    @BindView(R.id.carete_plan_tv)
    TextView caretePlanTv;
    @BindView(R.id.create_plan_Rc)
    RecyclerView createPlanRc;
    @BindView(R.id.create_plan_submit)
    TextView createPlanSubmit;
    private int from;
    private CreatePlanAdapter createPlanAdapter;
    private List<MonthPlanBean> results = new ArrayList<>();
    private List<String> planList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        from = getIntent().getIntExtra("from", 0);
        switch (from) {
            case 0:
                titleName.setText("月计划生成");
                caretePlanTv.setText("年计划列表");
                break;
            case 1:
                titleName.setText("周计划生成");
                caretePlanTv.setText("月计划列表");
                break;
            case 2:
                titleName.setText("日计划生成");
                caretePlanTv.setText("周计划列表");
                break;
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        createPlanRc.setLayoutManager(manager);
        results.add(null);
        results.add(null);
        results.add(null);
        results.add(null);
        createPlanAdapter = new CreatePlanAdapter(R.layout.item_craete_plan, results);
        createPlanRc.setAdapter(createPlanAdapter);
        //条目子控件点击事件
        createPlanAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                //判断id
                if (view.getId() == R.id.item_carete_plan_check) {
                    boolean checked = ((CheckBox) view).isChecked();
                    if (checked) {
                    } else {
                    }
                    Log.i("123213213", planList.size() + "");
                }
            }
        });
        getMonthPlanList();
    }

    //获取月计划列表
    public void getMonthPlanList() {

        BaseRequest.getInstance().getService()
                .getMonthPlanList("2019", "4", SPUtil.getDepId(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<MonthPlanBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<MonthPlanBean>> t) throws Exception {
                        results = t.getResults();
                        createPlanAdapter.setNewData(results
                        );
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

    }


    @OnClick({R.id.title_back, R.id.create_plan_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.create_plan_submit:
                finish();
                break;
        }
    }
}
