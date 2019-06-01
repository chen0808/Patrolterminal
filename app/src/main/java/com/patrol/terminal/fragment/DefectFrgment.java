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

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DefectFrgment extends BaseFragment {
    @BindView(R.id.rv_defect)
    RecyclerView rvDefect;
    private String task_id;
    private Disposable subscribe;
    private List<DefectFragmentBean> results=new ArrayList<>();
    private DefectFragmentAdapter adapter;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_defect, null);
        return view;
    }

    @Override
    protected void initData() {
        rvDefect.setLayoutManager(new LinearLayoutManager(getActivity()));
         adapter = new DefectFragmentAdapter(R.layout.fragment_defect_item, results);
        rvDefect.setAdapter(adapter);
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                if (s.startsWith("updateDefect")) {
                    getdata();
                }
            }
        });
        task_id = (String) SPUtil.get(getActivity(), "ids", "task_id", "");
        getdata();
    }
    public void getdata(){
        BaseRequest.getInstance().getService().getDefectFragment(task_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DefectFragmentBean>>(getActivity()) {
                    @Override
                    protected void onSuccees(BaseResult<List<DefectFragmentBean>> t) throws Exception {
                        results = t.getResults();
                        adapter.setNewData(results);
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
