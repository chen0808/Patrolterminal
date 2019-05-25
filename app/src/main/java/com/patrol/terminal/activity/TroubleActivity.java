package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectIngAdapter;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.adapter.TroubleIngAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DefectDetailBean;
import com.patrol.terminal.bean.DefectFragmentBean;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TroubleActivity extends BaseActivity {
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_iv)
    ImageView titleSettingIv;
    @BindView(R.id.title_setting_tv)
    TextView titleSettingTv;
    @BindView(R.id.title_setting)
    RelativeLayout titleSetting;
    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;
    private MyFragmentPagerAdapter taskPagerAdapter;
    private DefectIngAdapter groupTaskAdapter;
    //private List<DefectDetailBean> result = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble);
        ButterKnife.bind(this);
        initview();
        getAllDef();
    }

    private void getAllDef() {
        BaseRequest.getInstance().getService()
                .getAllDanger()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DefectFragmentBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<DefectFragmentBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            List<DefectFragmentBean> result = t.getResults();
                            if (result != null && result.size() > 0) {
                                setDataToList(result);
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void setDataToList(List<DefectFragmentBean> beans) {

//        for (int i = 0; i < 10; i++) {
//            result.add(i, new DefectDetailBean("461faasdgarea466", "巡视" + i + "号杆塔", String.valueOf(i % 2), "巡视类型", "2019-04-15",
//                    "备注:备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注备注",
//                    "检测人", "工作负责人", "工作班组", "是否确认", "确认审核员id", "杆塔名"));
//        }
        groupTaskAdapter = new DefectIngAdapter(R.layout.fragment_defect_item, beans, 1);
        planRv.setAdapter(groupTaskAdapter);


    }

    private void initview() {
        titleName.setText("隐患查询");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        planRv.setLayoutManager(manager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (subscribe != null) {
//            subscribe.dispose();
//        }
    }

    @OnClick({R.id.title_back, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.iv_search:
                break;
        }
    }
}
