package com.patrol.terminal.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patrol.terminal.R;
import com.patrol.terminal.activity.AddTroubleActivity;
import com.patrol.terminal.adapter.TroubleFragmentAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.LocalAddTrouble;
import com.patrol.terminal.sqlite.AppDataBase;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TroubleFrgment extends BaseFragment {
    @BindView(R.id.rv_trouble)
    RecyclerView rvTrouble;
    @BindView(R.id.btn_add_trouble)
    Button addTrouble;

    private String line_id;
    private Disposable subscribe;

    private List<LocalAddTrouble> troubleList = new ArrayList<>();
    private TroubleFragmentAdapter adapter;

    private String tower_id;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trouble, null);
        return view;
    }

    @Override
    protected void initData() {
        line_id = (String) SPUtil.get(getActivity(), "ids", "line_id", "");
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (s.startsWith("updateSpecial")) {
                    getdata();
                }
            }
        });

        //是否可编辑状态  不可编辑  0,4可以编辑  123不能编辑
        if (Constant.patrol_record_audit_status.equals("1") || Constant.patrol_record_audit_status.equals("2") || Constant.patrol_record_audit_status.equals("3")) {
            addTrouble.setVisibility(View.GONE);
            addTrouble.setEnabled(false);
        }

        rvTrouble.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TroubleFragmentAdapter(R.layout.fragment_defect_item, troubleList);
        rvTrouble.setAdapter(adapter);

        tower_id = getActivity().getIntent().getStringExtra("tower_id");

        getdata();

    }

    @OnClick(R.id.btn_add_trouble)
    void jumpAddTrouble() {
        Intent intent = new Intent();
        intent.putExtra("line_id", getActivity().getIntent().getStringExtra("line_id"));
        intent.putExtra("line_name", getActivity().getIntent().getStringExtra("line_name"));// 线路名字
        intent.putExtra("tower_id", getActivity().getIntent().getStringExtra("tower_id"));// 杆塔id
        intent.putExtra("tower_name", getActivity().getIntent().getStringExtra("tower_name"));// 杆塔名字
        intent.putExtra("task_id", getActivity().getIntent().getStringExtra("task_id"));
        intent.setClass(getActivity(), AddTroubleActivity.class);
        startActivityForResult(intent, Constant.DEFECT_ADD_TROUBLE_CODE);

    }

    private void getdata() {
        BaseRequest.getInstance().getService()
                .getTroubleFragment2(line_id, tower_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//TroubleBean
                .subscribe(new BaseObserver<List<LocalAddTrouble>>(getActivity()) {
                    @Override
                    protected void onSuccees(BaseResult<List<LocalAddTrouble>> t) throws Exception {
                        List<LocalAddTrouble> results = t.getResults();
                        FlowManager.getDatabase(AppDataBase.class).
                                beginTransactionAsync(new ITransaction() {
                                    @Override
                                    public void execute(DatabaseWrapper databaseWrapper) {
                                        LocalAddTrouble.delNetData(tower_id);

                                        for (int i = 0; i < results.size(); i++) {
                                            LocalAddTrouble bean = results.get(i);
                                            bean.setIsdownload("0");
                                            bean.save();
                                        }

                                        getLocalData();
                                    }
                                }).build().executeSync();

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    public void getLocalData() {
        troubleList.clear();
        troubleList.addAll(LocalAddTrouble.getAllData(tower_id));
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constant.DEFECT_ADD_TROUBLE_CODE:
                    getLocalData();
                    break;

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
