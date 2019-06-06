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
import com.patrol.terminal.adapter.YXTodoManageAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class YXCheckActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
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
    @BindView(R.id.to_do_tv)
    TextView toDoTv;
    @BindView(R.id.done_tv)
    TextView doneTv;
    @BindView(R.id.to_do_rl)
    RelativeLayout toDoRl;
    @BindView(R.id.frag_todo_rv)
    RecyclerView fragTodoRv;
    @BindView(R.id.frag_todo_ref)
    SwipeRefreshLayout fragTodoRef;
    private Activity mActivity;
    private YXTodoManageAdapter toDoManageAdapter;
    private static final int IS_TODO_PAGE = 0;
    private static final int IS_DONE_PAGE = 1;
    private int isTodoPage = IS_TODO_PAGE;
    private String status;
    private List<PersonalTaskListBean> results;
    private List<PersonalTaskListBean> resultsHave;

    private String jobType;
    private Disposable subscribe;
    private String state;
    private String dep_id;
    private String user_id;
    private String year, month, day, time;
    private String haveState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_dos_manage_fragment);
        ButterKnife.bind(this);
        initData();
    }

    protected void initData() {
        mActivity = this;

        time = DateUatil.getDay(new Date(System.currentTimeMillis()));
        inteDate();
        titleName.setText("检测管理");
        toDoRl.setVisibility(View.VISIBLE);
        jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        getYXtodo();
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        fragTodoRv.setLayoutManager(manager);
        toDoManageAdapter = new YXTodoManageAdapter(R.layout.fragment_yxtodo_item, results);
        fragTodoRv.setAdapter(toDoManageAdapter);

        fragTodoRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (jobType.contains(Constant.RUNNING_SQUAD_LEADER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                    getYXtodo();
                }
            }
        });

        toDoManageAdapter.setOnItemClickListener(this);

        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.startsWith("todo")) {
                    if (jobType.contains(Constant.RUNNING_SQUAD_LEADER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                        getYXtodo();
                    }
                }
            }
        });
    }

    public void inteDate() {
        String[] years = time.split("年");
        String[] months = years[1].split("月");
        String[] days = months[1].split("日");
        month = Integer.parseInt(months[0]) + "";
        year = years[0];
        day = Integer.parseInt(days[0]) + "";
    }

    @OnClick({R.id.to_do_tv, R.id.done_tv,R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.to_do_tv:
                toDoTv.setBackgroundResource(R.drawable.date_white_bg);
                doneTv.setBackgroundResource(R.drawable.date_grey_bg);
                toDoTv.setTextColor(getResources().getColor(R.color.date_color));
                doneTv.setTextColor(getResources().getColor(R.color.white));

                isTodoPage = IS_TODO_PAGE;
                toDoManageAdapter.setNewData(results);
                toDoManageAdapter.setNewData(results);
                break;

            case R.id.done_tv:
                toDoTv.setBackgroundResource(R.drawable.date_grey_bg);
                doneTv.setBackgroundResource(R.drawable.date_white_bg);
                toDoTv.setTextColor(getResources().getColor(R.color.white));
                doneTv.setTextColor(getResources().getColor(R.color.date_color));

                isTodoPage = IS_DONE_PAGE;
                toDoManageAdapter.setNewData(results);
                toDoManageAdapter.setNewData(resultsHave);
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PersonalTaskListBean todoListBean;
        if (isTodoPage == IS_TODO_PAGE) {
            todoListBean = results.get(position);
        } else {
            todoListBean = resultsHave.get(position);
        }
        String deal_type = todoListBean.getType_sign();
        String data_id = todoListBean.getId();
        Intent intent = new Intent();
        intent.putExtra("audit_status", todoListBean.getDone_status());
        intent.putExtra("task_id", data_id);
        switch (deal_type) {
            case "1":
                intent.setClass(this, PatrolRecordActivity.class);
                break;
            case "2":
                intent.setClass(this, HongWaiCeWenActivity.class);
                break;
            case "3":
                intent.setClass(this, JiediDianZuCeLiangActicivity.class);
                break;
            case "10":
                intent.setClass(this, JueYuanZiLingZhiJianCeActivity.class);
                break;
            case "5":
                intent.setClass(this, HongWaiCeWenActivity.class);
                break;
            case "6":
                intent.setClass(this, XieGanTaQingXieCeWenActivity.class);
                break;
        }
        startActivity(intent);
    }

    public void getYXtodo() {
        ProgressDialog.show(this, false, "正在加载中");
        BaseRequest.getInstance().getService()
                .getCheckList(year, month, day, SPUtil.getUserId(this), "1,2,3,4")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PersonalTaskListBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<PersonalTaskListBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        fragTodoRef.setRefreshing(false);

                        if (isTodoPage == IS_TODO_PAGE) {
                            toDoManageAdapter.setNewData(results);
                        }
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        fragTodoRef.setRefreshing(false);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe!=null){
            subscribe.dispose();
        }
    }

}
