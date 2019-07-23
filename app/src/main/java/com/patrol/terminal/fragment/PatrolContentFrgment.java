package com.patrol.terminal.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.DefectTabAdapter;
import com.patrol.terminal.adapter.MyPagerAdapter;
import com.patrol.terminal.base.BaseFragment;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.LocalPatrolDefectBean;
import com.patrol.terminal.bean.LocalPatrolDefectBean_Table;
import com.patrol.terminal.bean.PatrolContentBean;
import com.patrol.terminal.sqlite.AppDataBase;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    List<View> fragmentList = new ArrayList<>();
    private List<String> tabName = new ArrayList<>();
    private DefectTabAdapter adapter7;
    private DefectTabAdapter adapter6;
    private DefectTabAdapter adapter5;
    private DefectTabAdapter adapter4;
    private DefectTabAdapter adapter3;
    private DefectTabAdapter adapter2;
    private DefectTabAdapter adapter1;
    private DefectTabAdapter adapter0;
    private List<LocalPatrolDefectBean> localList;
    private List<LocalPatrolDefectBean> localByPatrolId;
    private LocalPatrolDefectBean localBean;
    private List<LocalPatrolDefectBean> localByTaskId;
    private String TAG = "PatrolContentFrgment";

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
        task_id = getActivity().getIntent().getStringExtra("task_id");
        saveLocalAsync(null);
        getdata();
    }

    public void getdata() {
        BaseRequest.getInstance().getService().getPatrolContent(task_id, "name,sort").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PatrolContentBean>>(getActivity()) {
                    @Override
                    protected void onSuccees(BaseResult<List<PatrolContentBean>> t) throws Exception {
                        List<PatrolContentBean> results = t.getResults();
//                        localBean = SQLite.select().from(LocalPatrolDefectBean.class).querySingle();
//                        if (localBean == null) {
//
//                        }
                        saveLocalAsync(results);

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    //从asset获取json
    public String getFromAssets(String fileName) {
        String line = "";
        String result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            while ((line = bufReader.readLine()) != null)
                result += line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //异步存储到本地
    private void saveLocalAsync(List<PatrolContentBean> results) {
        FlowManager.getDatabase(AppDataBase.class).beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                List<LocalPatrolDefectBean> localByTaskId = SQLite.select().from(LocalPatrolDefectBean.class).where(LocalPatrolDefectBean_Table.task_id.is(task_id)).queryList();
                if (localByTaskId == null || localByTaskId.size() == 0) {
                    String assetsJson = getFromAssets("defect.json");
                    List<PatrolContentBean> assetsList = new Gson().fromJson(assetsJson, new TypeToken<ArrayList<PatrolContentBean>>() {
                    }.getType());
                    for (int i = 0; i < assetsList.size(); i++) {
                        localBean = new LocalPatrolDefectBean();
                        localBean.setTask_id(task_id);
                        localBean.setTab_name(assetsList.get(i).getName());
                        localBean.setPatrol_id(assetsList.get(i).getPatrol_id());
                        localBean.setStatus("");
                        if (assetsList.get(i).getRemarks() != null) {
                            localBean.setPatrol_name(assetsList.get(i).getRemarks());
                        } else {
                            localBean.setPatrol_name("");
                        }
                        if (assetsList.get(i).getTaskDefect() != null) {
                            if (assetsList.get(i).getTaskDefect().getContent() != null) {
                                localBean.setContent(assetsList.get(i).getTaskDefect().getContent());
                            } else {
                                localBean.setContent("");
                            }
                            if (assetsList.get(i).getTaskDefect().getGrade_id() != null) {
                                localBean.setGrade_id(assetsList.get(i).getTaskDefect().getGrade_id());
                            } else {
                                localBean.setGrade_id("");
                            }
                        } else {
                            localBean.setContent("");
                            localBean.setGrade_id("");
                        }
                        localBean.setCategory_id(assetsList.get(i).getCategory());
                        String onlinePics = getOnlinePics(assetsList, i);
                        if (onlinePics == null || onlinePics.equals("")) {
                            localBean.setOnline_pics("");
                        } else {
                            localBean.setOnline_pics(onlinePics);
                        }
                        localBean.save();
                    }
                } else {
                    if (Constant.patrol_record_audit_status.equals("1") || Constant.patrol_record_audit_status.equals("2") || Constant.patrol_record_audit_status.equals("3")) {
                        for (int i = 0; i < results.size(); i++) {
                            localBean = localByTaskId.get(i);
                            localBean.setTask_id(task_id);
                            localBean.setTab_name(results.get(i).getName());
                            localBean.setPatrol_id(results.get(i).getPatrol_id());
                            if (results.get(i).getStatus() != null) {
                                localBean.setStatus(results.get(i).getStatus());
                            } else {
                                localBean.setStatus("");
                            }
                            if (results.get(i).getRemarks() != null) {
                                localBean.setPatrol_name(results.get(i).getRemarks());
                            } else {
                                localBean.setPatrol_name("");
                            }
                            if (results.get(i).getTaskDefect() != null) {
                                if (results.get(i).getTaskDefect().getContent() != null) {
                                    localBean.setContent(results.get(i).getTaskDefect().getContent());
                                } else {
                                    localBean.setContent("");
                                }
                                if (results.get(i).getTaskDefect().getGrade_id() != null) {
                                    localBean.setGrade_id(results.get(i).getTaskDefect().getGrade_id());
                                } else {
                                    localBean.setGrade_id("");
                                }
                            } else {
                                localBean.setContent("");
                                localBean.setGrade_id("");
                            }
                            localBean.setCategory_id(results.get(i).getCategory());
                            String onlinePics = getOnlinePics(results, i);
                            if (onlinePics == null || onlinePics.equals("")) {
                                localBean.setOnline_pics("");
                            } else {
                                localBean.setOnline_pics(onlinePics);
                            }
                            localBean.update();
                        }
                    }
                }
               /* else {
                    if (results == null) {
                        String assetsJson = getFromAssets("defect.json");
                        List<PatrolContentBean> assetsList = new Gson().fromJson(assetsJson, new TypeToken<ArrayList<PatrolContentBean>>() {
                        }.getType());
                        for (int i = 0; i < assetsList.size(); i++) {
                            localBean = localByTaskId.get(i);
                            localBean.setTask_id(task_id);
                            localBean.setTab_name(assetsList.get(i).getName());
                            localBean.setPatrol_id(assetsList.get(i).getPatrol_id());
                            localBean.setStatus("");
                            if (assetsList.get(i).getRemarks() != null) {
                                localBean.setPatrol_name(assetsList.get(i).getRemarks());
                            } else {
                                localBean.setPatrol_name("");
                            }
                            if (assetsList.get(i).getTaskDefect() != null) {
                                if (assetsList.get(i).getTaskDefect().getContent() != null) {
                                    localBean.setContent(assetsList.get(i).getTaskDefect().getContent());
                                } else {
                                    localBean.setContent("");
                                }
                                if (assetsList.get(i).getTaskDefect().getGrade_id() != null) {
                                    localBean.setGrade_id(assetsList.get(i).getTaskDefect().getGrade_id());
                                } else {
                                    localBean.setGrade_id("");
                                }
                            } else {
                                localBean.setContent("");
                                localBean.setGrade_id("");
                            }
                            localBean.setCategory_id(assetsList.get(i).getCategory());
                            String onlinePics = getOnlinePics(assetsList, i);
                            if (onlinePics == null || onlinePics.equals("")) {
                                localBean.setOnline_pics("");
                            } else {
                                localBean.setOnline_pics(onlinePics);
                            }
                            localBean.update();
                        }
                    } else {
                        for (int i = 0; i < results.size(); i++) {
                            localBean = localByTaskId.get(i);
                            localBean.setTask_id(task_id);
                            localBean.setTab_name(results.get(i).getName());
                            localBean.setPatrol_id(results.get(i).getPatrol_id());
                            localBean.setStatus(results.get(i).getStatus());
                            if (results.get(i).getRemarks() != null) {
                                localBean.setPatrol_name(results.get(i).getRemarks());
                            } else {
                                localBean.setPatrol_name("");
                            }
                            if (results.get(i).getTaskDefect() != null) {
                                if (results.get(i).getTaskDefect().getContent() != null) {
                                    localBean.setContent(results.get(i).getTaskDefect().getContent());
                                } else {
                                    localBean.setContent("");
                                }
                                if (results.get(i).getTaskDefect().getGrade_id() != null) {
                                    localBean.setGrade_id(results.get(i).getTaskDefect().getGrade_id());
                                } else {
                                    localBean.setGrade_id("");
                                }
                            } else {
                                localBean.setContent("");
                                localBean.setGrade_id("");
                            }
                            localBean.setCategory_id(results.get(i).getCategory());
                            String onlinePics = getOnlinePics(results, i);
                            if (onlinePics == null || onlinePics.equals("")) {
                                localBean.setOnline_pics("");
                            } else {
                                localBean.setOnline_pics(onlinePics);
                            }
                            localBean.update();
                        }
                    }
                }*/
            }
        }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                Log.e(TAG, "onSuccess()");
                query();
            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                Log.e(TAG, "onError()");
            }
        }).build().execute();
    }

    private void save(String patrol_id) {
        localByPatrolId = SQLite.select().from(LocalPatrolDefectBean.class)
                .where(LocalPatrolDefectBean_Table.patrol_id.is(patrol_id))
                .and(LocalPatrolDefectBean_Table.task_id.is(task_id))
                .queryList();
        if (localByPatrolId != null && localByPatrolId.size() > 0) {
            localBean.update();
        } else {
            localBean.save();
        }
    }

    private String getOnlinePics(List<PatrolContentBean> results, int i) {
        String picsOnline = "";
        if (results.get(i).getTaskDefect() != null) {
            if (results.get(i).getTaskDefect().getFileList() != null && results.get(i).getTaskDefect().getFileList().size() > 0) {
                for (int j = 0; j < results.get(i).getTaskDefect().getFileList().size(); j++) {
                    String filePath = results.get(i).getTaskDefect().getFileList().get(j).getFile_path();
                    String filename = results.get(i).getTaskDefect().getFileList().get(j).getFilename();
                    picsOnline += BaseUrl.BASE_URL + filePath + filename + ";";
                }
            }
        }
        return picsOnline;
    }

    //查询本地数据
    private void query() {
        localByTaskId = SQLite.select().from(LocalPatrolDefectBean.class).where(LocalPatrolDefectBean_Table.task_id.is(task_id)).queryList();
        if (localByTaskId != null && localByTaskId.size() != 0) {
            initTabFromLocal(localByTaskId);
        }
    }

    private void initTabFromLocal(List<LocalPatrolDefectBean> localByTaskId) {
        tabName.clear();
        fragmentList.clear();
        List<LocalPatrolDefectBean> list0 = new ArrayList<>();
        List<LocalPatrolDefectBean> list1 = new ArrayList<>();
        List<LocalPatrolDefectBean> list2 = new ArrayList<>();
        List<LocalPatrolDefectBean> list3 = new ArrayList<>();
        List<LocalPatrolDefectBean> list4 = new ArrayList<>();
        List<LocalPatrolDefectBean> list5 = new ArrayList<>();
        List<LocalPatrolDefectBean> list6 = new ArrayList<>();
        List<LocalPatrolDefectBean> list7 = new ArrayList<>();
        for (int i = 0; i < localByTaskId.size(); i++) {
            if (!tabName.contains(localByTaskId.get(i).getTab_name())) {
                tabName.add(localByTaskId.get(i).getTab_name());
            }
            switch (localByTaskId.get(i).getCategory_id()) {
                case "15C2D010A99D4D9485D9C5DD80F4831D"://导线，地线
                    list0.add(localByTaskId.get(i));
                    break;
                case "19D16418045B4802A3D1EE01157778BB"://防雷及接地装置
                    list1.add(localByTaskId.get(i));
                    break;
                case "3734AF649F3543D0B87F9EE10F961456"://附件及其他措施
                    list2.add(localByTaskId.get(i));
                    break;
                case "F1B635B0B74245C8BED1E1038C429982"://杆塔
                    list3.add(localByTaskId.get(i));
                    break;
                case "C0636A87A49A4727ABC71C1A63525E98"://金具
                    list4.add(localByTaskId.get(i));
                    break;
                case "BED42123A79B4F46B4D9922C2ABF989D"://绝缘子
                    list5.add(localByTaskId.get(i));
                    break;
                case "079A78FA7DAB4EE4AC72B680CEC09E8C"://拉线和基础
                    list6.add(localByTaskId.get(i));
                    break;
                case "9DA16BC597404773AA97260131A7DEE9"://沿线环境
                    list7.add(localByTaskId.get(i));
                    break;
            }
        }

        View view0 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView0 = view0.findViewById(R.id.recycler_view);
        recyclerView0.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter0 = new DefectTabAdapter(this, R.layout.item_defect_tab, list0, 0);
        recyclerView0.setAdapter(adapter0);
        fragmentList.add(view0);

        View view1 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView1 = view1.findViewById(R.id.recycler_view);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter1 = new DefectTabAdapter(this, R.layout.item_defect_tab, list1, 1);
        recyclerView1.setAdapter(adapter1);
        fragmentList.add(view1);

        View view2 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView2 = view2.findViewById(R.id.recycler_view);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter2 = new DefectTabAdapter(this, R.layout.item_defect_tab, list2, 2);
        recyclerView2.setAdapter(adapter2);
        fragmentList.add(view2);

        View view3 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView3 = view3.findViewById(R.id.recycler_view);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter3 = new DefectTabAdapter(this, R.layout.item_defect_tab, list3, 3);
        recyclerView3.setAdapter(adapter3);
        fragmentList.add(view3);

        View view4 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView4 = view4.findViewById(R.id.recycler_view);
        recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter4 = new DefectTabAdapter(this, R.layout.item_defect_tab, list4, 4);
        recyclerView4.setAdapter(adapter4);
        fragmentList.add(view4);

        View view5 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView5 = view5.findViewById(R.id.recycler_view);
        recyclerView5.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter5 = new DefectTabAdapter(this, R.layout.item_defect_tab, list5, 5);
        recyclerView5.setAdapter(adapter5);
        fragmentList.add(view5);

        View view6 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView6 = view6.findViewById(R.id.recycler_view);
        recyclerView6.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter6 = new DefectTabAdapter(this, R.layout.item_defect_tab, list6, 6);
        recyclerView6.setAdapter(adapter6);
        fragmentList.add(view6);

        View view7 = View.inflate(getActivity(), R.layout.fragment_defect_tab, null);
        RecyclerView recyclerView7 = view7.findViewById(R.id.recycler_view);
        recyclerView7.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter7 = new DefectTabAdapter(this, R.layout.item_defect_tab, list7, 7);
        recyclerView7.setAdapter(adapter7);
        fragmentList.add(view7);

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

    @Override
    public void onResume() {
        super.onResume();
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

                    if (Constant.defect_patrol_id != "") {
                       /* if (results.get(Constant.defect_tab).getValue().get(Constant.defect_index).getPicList() != null) {
                            results.get(Constant.defect_tab).getValue().get(Constant.defect_index).getPicList().add(bitmap);
                        } else {
                            List<Bitmap> list = new ArrayList<>();
                            list.add(bitmap);
                            results.get(Constant.defect_tab).getValue().get(Constant.defect_index).setPicList(list);
                        }*/
                        for (int i = 0; i < localByTaskId.size(); i++) {
                            if (localByTaskId.get(i).getPatrol_id().equals(Constant.defect_patrol_id)) {
                                String path = Environment.getExternalStorageDirectory().getPath()
                                        + "/MyPhoto/" + Constant.defect_patrol_id + "_" + Constant.defect_patrol_index + ".jpg";
                                try {
                                    FileUtil.saveFile(bitmap, path);
                                    if (localByTaskId.get(i).getPics() == null) {
                                        localByTaskId.get(i).setPics(path + ";");
                                    } else {
                                        localByTaskId.get(i).setPics(localByTaskId.get(i).getPics() + path + ";");
                                    }
                                    localByTaskId.get(i).update();
//                                    SQLite.update(LocalPatrolDefectBean.class)
//                                            .set(LocalPatrolDefectBean_Table.pics.eq(localByTaskId.get(i).getPics() + path + ";"))
//                                            .where(LocalPatrolDefectBean_Table.patrol_id.is(Constant.defect_patrol_id))
//                                            .execute();
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
                                        case 7:
                                            adapter7.notifyItemChanged(Constant.defect_index);
                                            break;
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
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
