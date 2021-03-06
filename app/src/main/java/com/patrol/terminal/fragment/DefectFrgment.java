package com.patrol.terminal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectFragmentAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DefectFragmentBean2;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DefectFrgment extends BaseFragment {
    @BindView(R.id.rv_defect)
    RecyclerView rvDefect;
    private Disposable subscribe;
    private List<DefectFragmentBean2> results = new ArrayList<>();
    private DefectFragmentAdapter adapter;
    private String line_id;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_defect, null);
        return view;
    }

    @Override
    protected void initData() {
        rvDefect.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDefect.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10)));
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
        line_id = (String) SPUtil.get(getActivity(), "ids", "line_id", "");
        if (line_id.equals("") || line_id == null) {
            line_id = "662CFCB4D6A54009956EF859727C1F78";
        }
        getdata();
    }

    public void getdata() {
        BaseRequest.getInstance().getService()
                .getDefectFragment(line_id, "grade_sign desc,find_time desc", "3").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DefectFragmentBean2>>(getActivity()) {
                    @Override
                    protected void onSuccees(BaseResult<List<DefectFragmentBean2>> t) throws Exception {
                        results = t.getResults();
                        adapter.setNewData(results);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
