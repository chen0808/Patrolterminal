package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.TroubleAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.TroubleFragmentBean;
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
    private TroubleAdapter adapter;
    private int page_num = 0;
    private int page_size = 10;
    private List<TroubleFragmentBean> troubleList = new ArrayList<>();
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
                .getAllDanger(page_num, page_size, "线")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TroubleFragmentBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<TroubleFragmentBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            List<TroubleFragmentBean> result = t.getResults();
                            if (result != null && result.size() > 0) {
                                planRv.loadMoreFinish(false, true);
                                troubleList.addAll(result);
                                setDataToList(troubleList);
                            } else {
                                planRv.loadMoreFinish(true, false);
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void setDataToList(List<TroubleFragmentBean> beans) {
        adapter = new TroubleAdapter(R.layout.fragment_defect_item, beans);
        planRv.setAdapter(adapter);
    }

    private void initview() {
        titleName.setText("隐患查询");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        planRv.setLayoutManager(manager);
        planRv.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                page_num++;
                getAllDef();
            }
        });
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
