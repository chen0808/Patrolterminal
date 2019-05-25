package com.patrol.terminal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.PatrolContentAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.PatrolContentBean;
import com.patrol.terminal.bean.PatrolLevel1;
import com.patrol.terminal.bean.PatrolLevel2;
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

public class PatrolContentFrgment extends BaseFragment {
    @BindView(R.id.rv_patrol_content)
    RecyclerView rvPatrolContent;
    private Disposable subscribe;
    private String task_id;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patrol_content, null);
        return view;
    }

    @Override
    protected void initData() {
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
        BaseRequest.getInstance().getService().getPatrolContent(task_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PatrolContentBean>>(getActivity()) {
                    @Override
                    protected void onSuccees(BaseResult<List<PatrolContentBean>> t) throws Exception {
                        List<PatrolContentBean> results = t.getResults();
                        rvPatrolContent.setLayoutManager(new LinearLayoutManager(getActivity()));
                        PatrolContentAdapter adapter = new PatrolContentAdapter(getData(results));
                        rvPatrolContent.setAdapter(adapter);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private List<MultiItemEntity> getData(List<PatrolContentBean> results) {
        List<MultiItemEntity> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            PatrolLevel1 level1 = new PatrolLevel1(results.get(i).getName());
            for (int j = 0; j < results.get(i).getValue().size(); j++) {
                PatrolLevel2 level2 = new PatrolLevel2(results.get(i).getValue().get(j).getREMARKS()
                        , results.get(i).getValue().get(j).getISDEFECT().equals("N") ? true : false
                        , results.get(i).getValue().get(j).getCATEGORY()
                        , results.get(i).getValue().get(j).getNAME(),
                        results.get(i).getValue().get(j).getID());
                level1.addSubItem(j, level2);
            }
            list.add(level1);
        }
        return list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscribe!=null){
            subscribe.dispose();
        }
    }
}
