package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ControlCardSafeAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.ControlDepWorkBean2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 所有危险点分析及安全措施列表
 */
public class ControlCardSafeListActivity extends BaseActivity {
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
    @BindView(R.id.rv_project_list)
    RecyclerView rvProjectList;
    private ControlCardSafeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        ButterKnife.bind(this);
        titleName.setText("危险点分析及安全措施列表");
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("确认");
        List<ControlDepWorkBean2> chooseList = (List<ControlDepWorkBean2>) getIntent().getSerializableExtra("chooseList");
        BaseRequest.getInstance().getService().controlCardSafeList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ControlDepWorkBean2>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<ControlDepWorkBean2>> t) throws Exception {
                        List<ControlDepWorkBean2> results = t.getResults();
                        if (results != null && results.size() > 0) {
                            rvProjectList.setLayoutManager(new LinearLayoutManager(ControlCardSafeListActivity.this));
                            for (int i = 0; i < chooseList.size(); i++) {
                                for (int j = 0; j < results.size(); j++) {
                                    if (results.get(j).getId().equals(chooseList.get(i).getId())) {
                                        results.get(j).setTag(true);
                                    }
                                }
                            }
                            adapter = new ControlCardSafeAdapter(R.layout.item_safe_list, results);
                            rvProjectList.setAdapter(adapter);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
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
                Intent intent = new Intent();
                List<ControlDepWorkBean2> data = new ArrayList<>();
                data.clear();
                for (int i = 0; i < adapter.getData().size(); i++) {
                    if (adapter.getData().get(i).isTag()) {
                        data.add(adapter.getData().get(i));
                    }
                }
                intent.putExtra("sureList", (Serializable) data);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
