package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DepOfGroupAdapter;
import com.patrol.terminal.adapter.GroupTaskAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DepOFTaskActivity extends BaseActivity {

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
    @BindView(R.id.dep_of_task_rv)
    RecyclerView depOfTaskRv;


    private DepOfGroupAdapter groupTaskAdapter;
    private List<GroupTaskBean> result = new ArrayList<>();
    private String year;
    private String month;
    private String day;
    private String depId;
    private boolean isRefresh = true;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dep_oftask);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleName.setText("任务列表");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        depOfTaskRv.setLayoutManager(manager);
        groupTaskAdapter = new DepOfGroupAdapter(R.layout.fragment_task_item, result);
        depOfTaskRv.setAdapter(groupTaskAdapter);
        groupTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                GroupTaskBean bean = result.get(position);
                intent.setClass(DepOFTaskActivity.this, GroupTaskDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("GroupTaskBean", bean);
                bundle.putString("GroupTaskTime", time);
                intent.putExtras(bundle);
                startActivityForResult(intent, 11);
            }
        });
        getGroupList();
    }

    //获取小组任务列表
    public void getGroupList() {
        BaseRequest.getInstance().getService()
                .getGroupList(year, month, day, depId,"duty_user_id,line_id,name", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GroupTaskBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<GroupTaskBean>> t) throws Exception {
                        result = t.getResults();
                        groupTaskAdapter.setNewData(result);
                        RxRefreshEvent.publish("refreshGroupNum@"+result.size());
                        isRefresh=true;
                        ProgressDialog.cancle();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });

    }
}
