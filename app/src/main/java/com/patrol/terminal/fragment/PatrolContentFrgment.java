package com.patrol.terminal.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.luck.picture.lib.entity.LocalMedia;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.PatrolRecordActivity;
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
import com.patrol.terminal.widget.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PatrolContentFrgment extends BaseFragment {
    @BindView(R.id.rv_patrol_content)
    RecyclerView rvPatrolContent;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private Disposable subscribe;
    private String task_id;
    private PatrolContentAdapter adapter;
    private List<PatrolContentBean.ValueBean> valueBean = new ArrayList<>();

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

    public void getdata() {
        BaseRequest.getInstance().getService().getPatrolContent("C16D44496A834E959411F49F2C73FF7E").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PatrolContentBean>>(getActivity()) {
                    @Override
                    protected void onSuccees(BaseResult<List<PatrolContentBean>> t) throws Exception {
                        List<PatrolContentBean> results = t.getResults();
                        rvPatrolContent.setLayoutManager(new LinearLayoutManager(getActivity()));
                        adapter = new PatrolContentAdapter(getData(results), getActivity());
                        rvPatrolContent.setAdapter(adapter);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private List<MultiItemEntity> getData(List<PatrolContentBean> results) {
        List<MultiItemEntity> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            PatrolLevel1 level1 = new PatrolLevel1(results.get(i).getName(), "0");
            for (int j = 0; j < results.get(i).getValue().size(); j++) {
                PatrolLevel2 level2 = new PatrolLevel2(results.get(i).getValue().get(j).getRemarks()
                        , results.get(i).getValue().get(j).getStatus()
                        , results.get(i).getValue().get(j).getCategory()
                        , results.get(i).getValue().get(j).getName(),
                        results.get(i).getValue().get(j).getId(),
                        results.get(i).getValue().get(j).getTask_id(),
                        results.get(i).getValue().get(j).getPatrol_id());
                level1.addSubItem(j, level2);
            }
            list.add(level1);
        }
        return list;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<LocalMedia> localMedia = ((PatrolRecordActivity) getActivity()).getPics();
        if (localMedia != null) {
            adapter.refreshAdapter(localMedia);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        valueBean.clear();
        List<MultiItemEntity> data = adapter.getData();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getItemType() == 1) {
                PatrolLevel1 patrolLevel1 = (PatrolLevel1) data.get(i);
                for (int j = 0; j < patrolLevel1.getSubItems().size(); j++) {
                    valueBean.add(new PatrolContentBean.ValueBean(
                            ((PatrolLevel1) data.get(i)).getSubItems().get(j).getId(),
                            ((PatrolLevel1) data.get(i)).getSubItems().get(j).getTask_id(),
                            ((PatrolLevel1) data.get(i)).getSubItems().get(j).getPatrol_id(),
                            ((PatrolLevel1) data.get(i)).getSubItems().get(j).getStatus()));
                }
            }
        }
        String json = new Gson().toJson(valueBean);
        Log.d("tag", json);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        BaseRequest.getInstance().getService().upLoadPatrolContent(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(getActivity()) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(getActivity(), "上传成功！", Toast.LENGTH_SHORT).show();
                        RxRefreshEvent.publish("updateDefect@2");
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public RequestBody toRequestBody(String value) {
        if (value != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
            return requestBody;
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "");
            return requestBody;
        }
    }
}
