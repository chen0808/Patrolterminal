package com.patrol.terminal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.SpecialContentAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.PatrolLevel1;
import com.patrol.terminal.bean.PatrolLevel2;
import com.patrol.terminal.bean.PatrolLevel3;
import com.patrol.terminal.bean.PatrolLevel4;
import com.patrol.terminal.bean.SpecialAttrBean;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SpecialAttrFrgment extends BaseFragment {
    @BindView(R.id.rv_special_content)
    RecyclerView rvSpecialContent;
    private List<SpecialAttrBean> listLevel3;
    private List<SpecialAttrBean> listLevel4;
    private String tower_id;
    private Disposable subscribe;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special_attr, null);
        return view;
    }

    @Override
    protected void initData() {

        tower_id = (String) SPUtil.get(getActivity(), "ids", "tower_id", "");
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
    public void getdata(){
        BaseRequest.getInstance().getService().getSpecialAttr(tower_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<SpecialAttrBean>>(getActivity()) {
                    @Override
                    protected void onSuccees(BaseResult<List<SpecialAttrBean>> t) throws Exception {
                        List<SpecialAttrBean> results = t.getResults();
                        rvSpecialContent.setLayoutManager(new LinearLayoutManager(getActivity()));
                        SpecialContentAdapter adapter = new SpecialContentAdapter(getData(results), listLevel3, listLevel4);
                        rvSpecialContent.setAdapter(adapter);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private List<MultiItemEntity> getData(List<SpecialAttrBean> results) {
        List<MultiItemEntity> list = new ArrayList<>();
        List<SpecialAttrBean> listLevel1 = new ArrayList<>();
        List<SpecialAttrBean> listLevel2 = new ArrayList<>();
        listLevel3 = new ArrayList<>();
        listLevel4 = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getLevel_no() == 1) {
                listLevel1.add(results.get(i));
            } else if (results.get(i).getLevel_no() == 2) {
                listLevel2.add(results.get(i));
            } else if (results.get(i).getLevel_no() == 3) {
                listLevel3.add(results.get(i));
            } else if (results.get(i).getLevel_no() == 4) {
                listLevel4.add(results.get(i));
            }
        }
        for (int i = 0; i < listLevel1.size(); i++) {
            PatrolLevel1 level1 = new PatrolLevel1(listLevel1.get(i).getName());
            for (int j = 0; j < listLevel2.size(); j++) {
                if (listLevel1.get(i).getId().equals(listLevel2.get(j).getP_id())) {
                    PatrolLevel2 level2 = new PatrolLevel2("", false, "", listLevel2.get(j).getName(), listLevel2.get(j).getId());
                    level1.addSubItem(j, level2);
                    for (int k = 0; k < listLevel3.size(); k++) {
                        if (listLevel2.get(j).getId().equals(listLevel3.get(k).getP_id())) {
                            PatrolLevel3 level3 = new PatrolLevel3(listLevel3.get(k).getName(), listLevel3.get(k).getId());
                            level2.addSubItem(k, level3);
                            for (int l = 0; l < listLevel4.size(); l++) {
                                if (listLevel3.get(k).getId().equals(listLevel4.get(l).getP_id())) {
                                    PatrolLevel4 level4 = new PatrolLevel4(listLevel4.get(l).getName(), listLevel4.get(l).getId());
                                    level3.addSubItem(l, level4);
                                }
                            }
                        }
                    }
                }
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
