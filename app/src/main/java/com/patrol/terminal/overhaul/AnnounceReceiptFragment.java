package com.patrol.terminal.overhaul;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.NoticeAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.NoticeBean;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;
import com.patrol.terminal.widget.SpaceItemDecoration;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//公告回执
public class AnnounceReceiptFragment extends BaseFragment {
    @BindView(R.id.plan_rv)
    SwipeRecyclerView planRv;

    private NoticeAdapter noticeAdapter;
    private List<NoticeBean> noticeList = new ArrayList<>();
    private int pageNum = 1;
    private int count = 10;
    private String search_name = "";
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

        noticeAdapter = new NoticeAdapter(R.layout.item_notice, 1);
        planRv.setAdapter(noticeAdapter);
        planRv.useDefaultLoadMore();
        planRv.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                getNoticeList(search_name);
            }
        });

//        getNoticeList(search_name);
    }

    //获取项目列表
    public void getNoticeList(String search_name) {
//        ProgressDialog.show(mContext, true, "正在加载中。。。。");
        BaseRequest.getInstance().getService()
                .getNoticeList(pageNum, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<NoticeBean>>(mContext) {
                    @Override
                    protected void onSuccees(BaseResult<List<NoticeBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.isSuccess()) {
                            List<NoticeBean> result = t.getResults();
                            if (result != null && result.size() > 0 && result.size() == 10) {
                                planRv.loadMoreFinish(false, true);
                            } else {
                                planRv.loadMoreFinish(true, false);
                            }
                            noticeList.clear();
                            noticeList.addAll(result);
                            setDataToList(result);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast(e.getMessage());
                    }
                });
    }

    private void setDataToList(List<NoticeBean> beans) {
        if (pageNum == 1) {
            noticeAdapter.setNewData(beans);
        } else {
            noticeAdapter.addData(beans);
        }
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
