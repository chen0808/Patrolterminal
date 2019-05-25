package com.patrol.terminal.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.HongWaiCeWenActivity;
import com.patrol.terminal.activity.JiediDianZuCeLiangActicivity;
import com.patrol.terminal.activity.JueYuanZiLingZhiJianCeActivity;
import com.patrol.terminal.activity.PatrolRecordActivity;
import com.patrol.terminal.activity.XieGanTaQingXieCeWenActivity;
import com.patrol.terminal.adapter.YXTodoManageAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.TodoListBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*待办管理*/
public class YXTodosManageFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {
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
    private List<TodoListBean> results;
    private List<TodoListBean> resultsHave;

    private String jobType;
    private Disposable subscribe;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.to_dos_manage_fragment, container, false);
        return view;
    }

    @Override
    protected void initData() {
        mActivity = getActivity();
        titleBack.setVisibility(View.GONE);
        titleName.setText("待办管理");
        jobType = SPUtil.getString(getContext(), Constant.USER, Constant.JOBTYPE, "");
        if (jobType.equals(Constant.RUNNING_SQUAD_LEADER)){
            getYXtodo();
            getYXtodoHave();
        }
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        fragTodoRv.setLayoutManager(manager);
        toDoManageAdapter = new YXTodoManageAdapter(R.layout.fragment_yxtodo_item, results);
        fragTodoRv.setAdapter(toDoManageAdapter);

        fragTodoRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (jobType.equals(Constant.RUNNING_SQUAD_LEADER)){
                    getYXtodo();
                    getYXtodoHave();
                }
            }
        });

        toDoManageAdapter.setOnItemClickListener(this);

        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String type) throws Exception {
                if (type.startsWith("todo")) {
                    if (jobType.equals(Constant.RUNNING_SQUAD_LEADER)){
                        getYXtodo();
                        getYXtodoHave();
                    }
                }
            }
        });
    }

    @OnClick({R.id.to_do_tv, R.id.done_tv})
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
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        TodoListBean todoListBean;
        if (isTodoPage ==IS_TODO_PAGE){
             todoListBean = results.get(position);
        }else {
            todoListBean = resultsHave.get(position);
        }
        String deal_type = todoListBean.getPlan_type_sign();
        String data_id = todoListBean.getData_id();
        Intent intent = new Intent();
        intent.putExtra("task_id", data_id);
        switch (deal_type) {
            case "1":
                intent.setClass(getContext(), PatrolRecordActivity.class);
                break;
            case "2":
                intent.setClass(getContext(), HongWaiCeWenActivity.class);
                break;
            case "3":
                intent.setClass(getContext(), JiediDianZuCeLiangActicivity.class);
                break;
            case "10":
                intent.setClass(getContext(), JueYuanZiLingZhiJianCeActivity.class);
                break;
            case "5":
                intent.setClass(getContext(), HongWaiCeWenActivity.class);
                break;
            case "6":
                intent.setClass(getContext(), XieGanTaQingXieCeWenActivity.class);
                break;

        }
        startActivity(intent);
    }
    public void getYXtodo() {
        ProgressDialog.show(getContext(),false,"正在加载中");
        BaseRequest.getInstance().getService()
                .getYXtodo(SPUtil.getDepId(getContext()),"0","CREATE_TIME desc")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TodoListBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<TodoListBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        fragTodoRef.setRefreshing(false);
                        results = t.getResults();
                        if (isTodoPage ==IS_TODO_PAGE){
                        toDoManageAdapter.setNewData(results);}
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        fragTodoRef.setRefreshing(false);
                    }
                });
    }
    public void getYXtodoHave() {
        BaseRequest.getInstance().getService()
                .getYXtodoHave(SPUtil.getDepId(getContext()),"0","CREATE_TIME desc")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TodoListBean>>(getContext()) {

                    @Override
                    protected void onSuccees(BaseResult<List<TodoListBean>> t) throws Exception {
                        fragTodoRef.setRefreshing(false);
                        resultsHave = t.getResults();
                        if (isTodoPage ==IS_DONE_PAGE){
                            toDoManageAdapter.setNewData(resultsHave);}
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        fragTodoRef.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (subscribe!=null){
            subscribe.dispose();
        }
    }
}
