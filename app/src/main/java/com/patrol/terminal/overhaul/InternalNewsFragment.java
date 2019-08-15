package com.patrol.terminal.overhaul;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectIngTabAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DefectFragmentBean;
import com.patrol.terminal.widget.ProgressDialog;
import com.patrol.terminal.widget.SpaceItemDecoration;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//内部新闻
public class InternalNewsFragment extends BaseFragment {
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;

    private DefectIngTabAdapter groupTaskAdapter;
    private int pageNum = 1;
    private int count = 10;
    private String search_name = "";
    private String line_name = "";
    private List<DefectFragmentBean> defectList = new ArrayList<>();
    private Context mContext;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_internal_announce, null);
        return view;
    }

    @Override
    protected void initData() {
        mContext = getActivity();

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        planRv.setLayoutManager(manager);
        planRv.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_10)));

        groupTaskAdapter = new DefectIngTabAdapter(R.layout.fragment_defect_not_in_item, 1);
        planRv.setAdapter(groupTaskAdapter);
        planRv.useDefaultLoadMore();
        planRv.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                getBanjiXLQx(search_name, line_name);
            }
        });

        getBanjiXLQx(search_name, line_name);
    }

    private void setDataToList(List<DefectFragmentBean> beans) {
        if (pageNum == 1) {
            groupTaskAdapter.setNewData(beans);
        } else {
            groupTaskAdapter.addData(beans);
        }
    }

    //班级线路缺陷
    public void getBanjiXLQx(String search_name, String line_id) {
//        ProgressDialog.show(mContext, true, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getXLDefact(pageNum, count, line_id, search_name, "grade_sign desc,find_time desc", "3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DefectFragmentBean>>(mContext) {
                    @Override
                    protected void onSuccees(BaseResult<List<DefectFragmentBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
                            List<DefectFragmentBean> result = t.getResults();
                            if (result != null && result.size() > 0 && result.size() == 10) {
                                planRv.loadMoreFinish(false, true);
                            } else {
                                planRv.loadMoreFinish(true, false);
                            }
                            defectList.clear();
                            defectList.addAll(result);
                            setDataToList(result);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    @OnClick({})
    public void onViewClicked(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1101) {
            switch (requestCode) {
                case 10:
                    break;
            }
        }
    }
}
