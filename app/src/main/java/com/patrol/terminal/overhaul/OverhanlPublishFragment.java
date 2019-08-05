package com.patrol.terminal.overhaul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.GridViewAdapter;
import com.patrol.terminal.adapter.GridViewAdapter5;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.MapUserInfo;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OverhanlPublishFragment extends BaseFragment {
    @BindView(R.id.gridview)
    GridView gridview;
    private GridViewAdapter5 weekAdapter;
    private List<MapUserInfo> results = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overhaul_other, container, false);
        return view;
    }

    @Override
    protected void initData() {
        MapUserInfo mapUserInfo1 = new MapUserInfo();
        mapUserInfo1.setUserImgId(R.mipmap.todo1);
        mapUserInfo1.setUserName("添加年计划");
        results.add(mapUserInfo1);

        MapUserInfo mapUserInfo2 = new MapUserInfo();
        mapUserInfo2.setUserImgId(R.mipmap.todo2);
        mapUserInfo2.setUserName("添加月计划");
        results.add(mapUserInfo2);

        MapUserInfo mapUserInfo3 = new MapUserInfo();
        mapUserInfo3.setUserImgId(R.mipmap.todo3);
        mapUserInfo3.setUserName("添加周计划");
        results.add(mapUserInfo3);

        MapUserInfo mapUserInfo4 = new MapUserInfo();
        mapUserInfo4.setUserImgId(R.mipmap.todo4);
        mapUserInfo4.setUserName("班长发布周检修工作");
        results.add(mapUserInfo4);

        MapUserInfo mapUserInfo5 = new MapUserInfo();
        mapUserInfo5.setUserImgId(R.mipmap.todo5);
        mapUserInfo5.setUserName("月计划详情");
        results.add(mapUserInfo5);

        MapUserInfo mapUserInfo7 = new MapUserInfo();
        mapUserInfo7.setUserImgId(R.mipmap.todo6);
        mapUserInfo7.setUserName("周计划详情");
        results.add(mapUserInfo7);

        MapUserInfo mapUserInfo8 = new MapUserInfo();
        mapUserInfo8.setUserImgId(R.mipmap.todo7);
        mapUserInfo8.setUserName("检修任务详情详情");
        results.add(mapUserInfo8);

        MapUserInfo mapUserInfo9 = new MapUserInfo();
        mapUserInfo9.setUserImgId(R.mipmap.todo6);
        mapUserInfo9.setUserName("年计划详情");
        results.add(mapUserInfo9);

        MapUserInfo mapUserInfo10 = new MapUserInfo();
        mapUserInfo10.setUserImgId(R.mipmap.todo9);
        mapUserInfo10.setUserName("专责任务详情");
        results.add(mapUserInfo10);

        weekAdapter = new GridViewAdapter5(getContext(), results);
        gridview.setAdapter(weekAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                switch (i) {
                    case 0:
                        intent.setClass(getContext(), OverhaulAddYearPlanActivity.class);
                        break;
                    case 1:
                        intent.setClass(getContext(), OverhaulAddMonthPlanActivity.class);
                        break;
                    case 2:
                        intent.setClass(getContext(), OverhaulAddWeekPlanActivity.class);
                        break;

                    case 3:
                        intent.setClass(getContext(), OverhaulMonitorPublishActivity.class);
                        break;

                    case 4:
                        intent.setClass(getContext(), OverhaulMonthDetailActivity.class);
                        break;

                    case 5:
                        intent.setClass(getContext(), OverhaulWeekDetailActivity.class);
                        break;

                    case 6:
                        intent.setClass(getContext(), OverhaulWeekPlanDetailActivity.class);
                        break;

                    case 7:
                        intent.setClass(getContext(), OverhaulYearDetailActivity.class);
                        break;

                    case 8:
                        intent.setClass(getContext(), OverhaulYearDetailActivity.class);
                        break;

                    case 9:
                        intent.setClass(getContext(), OverhaulZzWeekTaskDetailActivity.class);
                        break;

                }
                startActivity(intent);

            }
        });

    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
