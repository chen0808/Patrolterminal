package com.patrol.terminal.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.luck.picture.lib.entity.LocalMedia;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.PatrolRecordActivity;
import com.patrol.terminal.adapter.DefectTabAdapter;
import com.patrol.terminal.adapter.MyPagerAdapter;
import com.patrol.terminal.adapter.PatrolContentAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.PatrolContentBean;
import com.patrol.terminal.bean.PatrolLevel1;
import com.patrol.terminal.bean.PatrolLevel2;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*先从本地数据库取数据，再有网络情况下去服务器数据刷新*/
public class PatrolContentFrgment extends BaseFragment {
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private Disposable subscribe;
    private String task_id;
    private PatrolContentAdapter adapter;
    private List<PatrolContentBean.ValueBean> valueBean = new ArrayList<>();
    private String line_id;
    List<View> fragmentList = new ArrayList<>();
    private List<String> tabName = new ArrayList<>();
    private DefectTabAdapter adapter6;
    private DefectTabAdapter adapter5;
    private DefectTabAdapter adapter4;
    private DefectTabAdapter adapter3;
    private DefectTabAdapter adapter2;
    private DefectTabAdapter adapter1;
    private DefectTabAdapter adapter0;
    private List<PatrolContentBean> results;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patrol_content, null);
        return view;
    }

    @Override
    protected void initData() {
//        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {
//
//            @Override
//            public void accept(String s) throws Exception {
//                if (s.startsWith("updateDefect")) {
//                    getdata();
//                }
//            }
//        });
        //从本地数据库获取

        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                if (s.startsWith("updateDefect")) {
                    getdata();
                }
            }
        });
        task_id = (String) SPUtil.get(getActivity(), "ids", "task_id", "");
        line_id = (String) SPUtil.get(getActivity(), "ids", "line_id", "");
        getdata();
    }

    public void getdata() {
        //这里在主页的时候就已经加载了，存在sp
        BaseRequest.getInstance().getService().getPatrolContent(task_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PatrolContentBean>>(getActivity()) {
                    @Override
                    protected void onSuccees(BaseResult<List<PatrolContentBean>> t) throws Exception {
                        results = t.getResults();
//                        rvPatrolContent.setLayoutManager(new LinearLayoutManager(getActivity()));
//                        adapter = new PatrolContentAdapter(getData(results), getActivity(), line_id);
//                        rvPatrolContent.setAdapter(adapter);

                        //存入到本地数据库  TODO


                        initTab(results);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                    }
                });
//        List<PatrolContentBean> results = (List<PatrolContentBean>) SPUtil.get(getActivity(), "list", "defectList", null);
//        if (results != null) {
//            rvPatrolContent.setLayoutManager(new LinearLayoutManager(getActivity()));
//            adapter = new PatrolContentAdapter(getData(results), getActivity(), line_id);
//            rvPatrolContent.setAdapter(adapter);
//        }
    }

    private void initTab(List<PatrolContentBean> results) {
        tabName.clear();
        fragmentList.clear();
        for (int i = 0; i < results.size(); i++) {
            tabName.add(results.get(i).getName());
        }

        View view0 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView0 = view0.findViewById(R.id.recycler_view);
        recyclerView0.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter0 = new DefectTabAdapter(this, R.layout.item_defect_tab, results.get(0).getValue(), 0);
        recyclerView0.setAdapter(adapter0);
        fragmentList.add(view0);

        View view1 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView1 = view1.findViewById(R.id.recycler_view);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter1 = new DefectTabAdapter(this, R.layout.item_defect_tab, results.get(1).getValue(), 1);
        recyclerView1.setAdapter(adapter1);
        fragmentList.add(view1);

        View view2 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView2 = view2.findViewById(R.id.recycler_view);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter2 = new DefectTabAdapter(this, R.layout.item_defect_tab, results.get(2).getValue(), 2);
        recyclerView2.setAdapter(adapter2);
        fragmentList.add(view2);

        View view3 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView3 = view3.findViewById(R.id.recycler_view);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter3 = new DefectTabAdapter(this, R.layout.item_defect_tab, results.get(3).getValue(), 3);
        recyclerView3.setAdapter(adapter3);
        fragmentList.add(view3);

        View view4 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView4 = view4.findViewById(R.id.recycler_view);
        recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter4 = new DefectTabAdapter(this, R.layout.item_defect_tab, results.get(4).getValue(), 4);
        recyclerView4.setAdapter(adapter4);
        fragmentList.add(view4);

        View view5 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView5 = view5.findViewById(R.id.recycler_view);
        recyclerView5.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter5 = new DefectTabAdapter(this, R.layout.item_defect_tab, results.get(5).getValue(), 5);
        recyclerView5.setAdapter(adapter5);
        fragmentList.add(view5);

        View view6 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView6 = view6.findViewById(R.id.recycler_view);
        recyclerView6.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter6 = new DefectTabAdapter(this, R.layout.item_defect_tab, results.get(6).getValue(), 6);
        recyclerView6.setAdapter(adapter6);
        fragmentList.add(view6);

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(fragmentList);
        viewPager.setAdapter(pagerAdapter);
        initMagicIndicator(tabName);
    }

    private void initMagicIndicator(List<String> data) {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);

                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.black_gray));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.orange_vip));
                simplePagerTitleView.setText(data.get(index));
                simplePagerTitleView.setTextColor(getResources().getColor(R.color.black_gray));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);

                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setColors(getResources().getColor(R.color.orange_vip));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(getActivity(), 15);
            }
        });
        ViewPagerHelper.bind(magicIndicator, viewPager);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constant.DEFECT_REQUEST_CODE:
                    Log.d("TAG", "success");
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    if (Constant.defect_tab != -1 && Constant.defect_index != -1) {
                        if (results.get(Constant.defect_tab).getValue().get(Constant.defect_index).getPicList() != null) {
                            results.get(Constant.defect_tab).getValue().get(Constant.defect_index).getPicList().add(bitmap);
                        } else {
                            List<Bitmap> list = new ArrayList<>();
                            list.add(bitmap);
                            results.get(Constant.defect_tab).getValue().get(Constant.defect_index).setPicList(list);
                        }
                        switch (Constant.defect_tab) {
                            case 0:
                                adapter0.notifyItemChanged(Constant.defect_index);
                                break;
                            case 1:
                                adapter1.notifyItemChanged(Constant.defect_index);
                                break;
                            case 2:
                                adapter2.notifyItemChanged(Constant.defect_index);
                                break;
                            case 3:
                                adapter3.notifyItemChanged(Constant.defect_index);
                                break;
                            case 4:
                                adapter4.notifyItemChanged(Constant.defect_index);
                                break;
                            case 5:
                                adapter5.notifyItemChanged(Constant.defect_index);
                                break;
                            case 6:
                                adapter6.notifyItemChanged(Constant.defect_index);
                                break;
                        }
                    }
                    break;
            }
        }
    }

    /*   @OnClick(R.id.btn_commit)
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
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        BaseRequest.getInstance().getService().upLoadPatrolContent(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(getActivity()) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(getActivity(), "上传成功！", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
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
    }*/
}
