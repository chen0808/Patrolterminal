package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.InitiateProjectAdapter;
import com.patrol.terminal.adapter.ProjectBoardAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.InitiateProjectBean;
import com.patrol.terminal.bean.ProjectBoardBean;
import com.patrol.terminal.sqlite.AppDataBase;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;
import com.patrol.terminal.widget.SpaceItemDecoration;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectListActivity extends BaseActivity {

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
    @BindView(R.id.rv_project_list)
    SwipeRecyclerView rvProjectList;
    private List<InitiateProjectBean> initiateProjectList=new ArrayList<>();
    private int pageNum = 1;
    private int count = 10;
    private InitiateProjectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {

        titleName.setText("项目列表");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvProjectList.setLayoutManager(manager);
        rvProjectList.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10)));
        getProjectList("");
        adapter = new InitiateProjectAdapter(R.layout.item_initiate_project, initiateProjectList);
        rvProjectList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                InitiateProjectBean projectBoardBean = initiateProjectList.get(position);
                Intent intent=new Intent();
                intent.putExtra("bean",projectBoardBean);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    //获取项目列表
    public void getProjectList(String search_name) {
        ProgressDialog.show(this, true, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getProjectList(pageNum, count, search_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<InitiateProjectBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<InitiateProjectBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
                            List<InitiateProjectBean> result = t.getResults();
                            if (result != null && result.size() > 0 && result.size() == 10) {
                                rvProjectList.loadMoreFinish(false, true);
                            } else {
                                rvProjectList.loadMoreFinish(true, false);
                            }
                            initiateProjectList.clear();
                            initiateProjectList.addAll(result);
                            setDataToList(result);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast(e.getMessage());
                    }
                });
    }

    private void setDataToList(List<InitiateProjectBean> beans) {
        if (pageNum == 1) {
            adapter.setNewData(beans);
        } else {
            adapter.addData(beans);
        }
    }
    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }


}
