package com.patrol.terminal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectFragmentAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DefectFragmentBean;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TroubleFrgment extends BaseFragment {
    @BindView(R.id.rv_trouble)
    RecyclerView rvTrouble;
    private String task_id;
    private Disposable subscribe;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trouble, null);
        return view;
    }

    @Override
    protected void initData() {
        task_id = (String) SPUtil.get(getActivity(), "ids", "task_id", "");
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                if (s.startsWith("updateSpecial")) {
                    getdata();
                }
            }
        });
        getdata();

    }

    private void getdata() {
        BaseRequest.getInstance().getService().getTroubleFragment(task_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DefectFragmentBean>>(getActivity()) {
                    @Override
                    protected void onSuccees(BaseResult<List<DefectFragmentBean>> t) throws Exception {
                        List<DefectFragmentBean> results = t.getResults();
                        rvTrouble.setLayoutManager(new LinearLayoutManager(getActivity()));
                        DefectFragmentAdapter adapter = new DefectFragmentAdapter(R.layout.fragment_defect_item, results);
                        rvTrouble.setAdapter(adapter);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscribe!=null){
            subscribe.dispose();
        }
    }
}
