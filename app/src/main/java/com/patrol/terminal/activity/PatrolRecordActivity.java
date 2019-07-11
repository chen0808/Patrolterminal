package com.patrol.terminal.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.LocalPatrolDefectBean;
import com.patrol.terminal.bean.LocalPatrolDefectBean_Table;
import com.patrol.terminal.bean.LocalPatrolRecordBean;
import com.patrol.terminal.bean.LocalPatrolRecordBean_Table;
import com.patrol.terminal.bean.PatrolRecordPicBean;
import com.patrol.terminal.bean.SaveTodoReqbean;
import com.patrol.terminal.bean.TSSXLocalBean;
import com.patrol.terminal.bean.TSSXLocalBean_Table;
import com.patrol.terminal.bean.TaskBean;
import com.patrol.terminal.bean.TypeBean;
import com.patrol.terminal.fragment.DefectFrgment;
import com.patrol.terminal.fragment.PatrolContentFrgment;
import com.patrol.terminal.fragment.SpecialTSSXFrgment;
import com.patrol.terminal.fragment.TroubleFrgment;
import com.patrol.terminal.sqlite.AppDataBase;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.NoScrollViewPager;
import com.patrol.terminal.widget.PinchImageView;
import com.patrol.terminal.widget.ProgressDialog;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 巡视任务
 */
public class PatrolRecordActivity extends BaseActivity {
    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_iv)
    ImageView titleSettingIv;
    @BindView(R.id.title_setting_tv)
    TextView titleSettingTv;
    @BindView(R.id.title_setting)
    RelativeLayout titleSetting;
    @BindView(R.id.iv_photo1)
    ImageView ivPhoto1;
    @BindView(R.id.iv_photo2)
    ImageView ivPhoto2;
    @BindView(R.id.iv_photo3)
    ImageView ivPhoto3;
    @BindView(R.id.iv_photo4)
    ImageView ivPhoto4;
    @BindView(R.id.iv_photo5)
    ImageView ivPhoto5;
    @BindView(R.id.iv_photo6)
    ImageView ivPhoto6;
    private static final String[] data = new String[]{"常规巡视", "特殊属性", "缺陷", "隐患"};
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private MyFragmentPagerAdapter pagerAdapter;
    private Disposable subscribe;
    private Map<String, RequestBody> params = new HashMap<>();
    private String jobType;
    private String task_id;
    public String audit_status;
    private int picIndex = 0;
    private LocalPatrolRecordBean localByTaskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_record);
        ButterKnife.bind(this);
        titleName.setText("巡视记录");

        jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        task_id = getIntent().getStringExtra("task_id");
        audit_status = getIntent().getStringExtra("audit_status");
        Constant.patrol_record_audit_status = audit_status;
        getYXtodo();
        initview();
        query();
        getTask(task_id);
    }

    //初始化界面，创建fragment
    private void initview() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new PatrolContentFrgment()); //常规巡视
//        fragmentList.add(new SpecialAttrFrgment3());  //特殊属性
        fragmentList.add(new SpecialTSSXFrgment());//特殊属性
        fragmentList.add(new DefectFrgment());        //缺陷
        fragmentList.add(new TroubleFrgment());       //隐患
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        initMagicIndicator();
        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(5);
        subscribe = RxRefreshEvent.getObservable().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (s.startsWith("update")) {
                    int page = Integer.parseInt(s.split("@")[1]);
                    viewPager.setCurrentItem(page);
                    magicIndicator.onPageSelected(page);
                }
            }
        });
        viewPager.setCurrentItem(0);
        magicIndicator.onPageSelected(0);
    }

    //创建indicator
    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return data.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.color_33));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.base_status_bar));
                simplePagerTitleView.setText(data[index]);
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
                linePagerIndicator.setColors(getResources().getColor(R.color.base_status_bar));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(PatrolRecordActivity.this, 30);
            }
        });
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    //获取当前任务完成状态
    private void getYXtodo() {
        if ("1".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审批");
            }
        } else if ("2".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审批");
            }
        } else if ("0".equals(audit_status) || "4".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("提交");
            }
        } else {
            titleSetting.setVisibility(View.GONE);
        }
    }

    //从后台获取数据
    private void getTask(String task_id) {
        BaseRequest.getInstance().getService()
                .getTask(task_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TaskBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<TaskBean> t) throws Exception {
                        TaskBean bean = t.getResults();
                        saveLocalAsync(bean);
//                        save(localBean);
                        query();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    //异步存储到本地
    private void saveLocalAsync(TaskBean bean) {
        FlowManager.getDatabase(AppDataBase.class).beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                LocalPatrolRecordBean localBean = SQLite.select().from(LocalPatrolRecordBean.class).where(LocalPatrolRecordBean_Table.task_id.is(task_id)).querySingle();
                if (localBean == null) {
                    localBean = new LocalPatrolRecordBean();
                    localBean.setTask_id(bean.getId());
                    localBean.setLine_id(bean.getLine_id());
                    localBean.setType_sign(bean.getType_sign());
                    localBean.setLine_name(bean.getLine_name());
                    localBean.setTower_id(bean.getTower_id());
                    localBean.setTower_name(bean.getTower_name());
                    localBean.setUser_id(bean.getUser_id());
                    localBean.setUser_name(bean.getUser_name());
                    localBean.setDep_id(bean.getDep_id());
                    localBean.setDep_name(bean.getDep_name());
                    localBean.save();
                } else {
                    localBean.setTask_id(bean.getId());
                    localBean.setLine_id(bean.getLine_id());
                    localBean.setType_sign(bean.getType_sign());
                    localBean.setLine_name(bean.getLine_name());
                    localBean.setTower_id(bean.getTower_id());
                    localBean.setTower_name(bean.getTower_name());
                    localBean.setUser_id(bean.getUser_id());
                    localBean.setUser_name(bean.getUser_name());
                    localBean.setDep_id(bean.getDep_id());
                    localBean.setDep_name(bean.getDep_name());
                    localBean.update();
                }
            }
        }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                query();
            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
            }
        }).build().execute();

    }

    //查询当前task_id对应数据是否为空，如果为空，保存，否则，更新
    private void save(LocalPatrolRecordBean localBean) {
        localByTaskId = SQLite.select().from(LocalPatrolRecordBean.class).where(LocalPatrolRecordBean_Table.task_id.is(task_id)).querySingle();
        if (localByTaskId != null) {
            localBean.update();
        } else {
            localBean.save();
        }
    }

    //获取当前task_id对应的数据
    private void query() {
        localByTaskId = SQLite.select().from(LocalPatrolRecordBean.class).where(LocalPatrolRecordBean_Table.task_id.is(task_id)).querySingle();
        if (audit_status.equals("1") || audit_status.equals("2")) {
            initOnLineData();
        } else {
            initLocalData(localByTaskId);
        }
    }

    //本地数据
    private void initLocalData(LocalPatrolRecordBean localByTaskId) {
        if (localByTaskId != null) {
            if (localByTaskId.getPic1() != null) {
                Glide.with(this).load(new File(localByTaskId.getPic1())).into(ivPhoto1);
            }
            if (localByTaskId.getPic2() != null) {
                Glide.with(this).load(new File(localByTaskId.getPic2())).into(ivPhoto2);
            }
            if (localByTaskId.getPic3() != null) {
                Glide.with(this).load(new File(localByTaskId.getPic3())).into(ivPhoto3);
            }
            if (localByTaskId.getPic4() != null) {
                Glide.with(this).load(new File(localByTaskId.getPic4())).into(ivPhoto4);
            }
            if (localByTaskId.getPic5() != null) {
                Glide.with(this).load(new File(localByTaskId.getPic5())).into(ivPhoto5);
            }
            if (localByTaskId.getPic6() != null) {
                Glide.with(this).load(new File(localByTaskId.getPic6())).into(ivPhoto6);
            }
        }
    }

    private void initOnLineData() {
        BaseRequest.getInstance().getService()
                .getRecordPicList(task_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PatrolRecordPicBean>>(this) {


                    @Override
                    protected void onSuccees(BaseResult<List<PatrolRecordPicBean>> t) throws Exception {
                        List<PatrolRecordPicBean> picBeanList = t.getResults();
                        for (int i = 0; i < picBeanList.size(); i++) {
                            showPic(picBeanList.get(i));
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                });
    }

    private void showPic(PatrolRecordPicBean picBean) {
        String path = BaseUrl.BASE_URL + picBean.getFile_path() + picBean.getFilename();
        switch (picBean.getSign()) {
            case "1":
                Glide.with(this).load(path).into(ivPhoto1);
                break;
            case "2":
                Glide.with(this).load(path).into(ivPhoto2);
                break;
            case "3":
                Glide.with(this).load(path).into(ivPhoto3);
                break;
            case "4":
                Glide.with(this).load(path).into(ivPhoto4);
                break;
            case "5":
                Glide.with(this).load(path).into(ivPhoto5);
                break;
            case "6":
                Glide.with(this).load(path).into(ivPhoto6);
                break;
        }
    }

    @OnClick({R.id.title_back, R.id.title_setting, R.id.iv_photo1, R.id.iv_photo2, R.id.iv_photo3, R.id.iv_photo4, R.id.iv_photo5, R.id.iv_photo6, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                addTodo();
                break;
            case R.id.iv_photo1:
                picIndex = 1;
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    startCamera();
                } else {
                    showBigImage(1);
                }
                break;
            case R.id.iv_photo2:
                picIndex = 2;
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    startCamera();
                } else {
                    showBigImage(2);
                }
                break;
            case R.id.iv_photo3:
                picIndex = 3;
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    startCamera();
                } else {
                    showBigImage(3);
                }
                break;
            case R.id.iv_photo4:
                picIndex = 4;
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    startCamera();
                } else {
                    showBigImage(4);
                }
                break;
            case R.id.iv_photo5:
                picIndex = 5;
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    startCamera();
                } else {
                    showBigImage(5);
                }
                break;
            case R.id.iv_photo6:
                picIndex = 6;
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    startCamera();
                } else {
                    showBigImage(6);
                }
                break;
            case R.id.fab:
                startActivity(new Intent(this, MapActivity.class));
                break;
        }
    }

    private void addTodo() {
        if (audit_status.equals("0") || audit_status.equals("4")) {
            commit();
        } else {
            CancelOrOkDialog dialog = new CancelOrOkDialog(PatrolRecordActivity.this, "是否通过", "不通过", "通过") {
                @Override
                public void ok() {
                    super.ok();
                    if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                        saveTodoAudit("3");   //同意
                    } else {
                        saveTodoAudit("2");   //同意
                    }

                    dismiss();
                }

                @Override
                public void cancle() {
                    super.cancle();
                    saveTodoAudit("4");  //不同意
                    dismiss();
                }
            };
            dialog.show();
        }
    }

    private void commit() {
        params.clear();
        params.put("task_id", toRequestBody(task_id));
        localByTaskId = SQLite.select().from(LocalPatrolRecordBean.class).where(LocalPatrolRecordBean_Table.task_id.is(task_id)).querySingle();
        if (localByTaskId != null) {
            if (localByTaskId.getPic1() != null) {
                RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), new File(localByTaskId.getPic1()));
                params.put("patrolFile\"; filename=\"1.jpg", requestFile1);
            }
            if (localByTaskId.getPic2() != null) {
                RequestBody requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), new File(localByTaskId.getPic2()));
                params.put("patrolFile\"; filename=\"2.jpg", requestFile2);
            }
            if (localByTaskId.getPic3() != null) {
                RequestBody requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), new File(localByTaskId.getPic3()));
                params.put("patrolFile\"; filename=\"3.jpg", requestFile3);
            }
            if (localByTaskId.getPic4() != null) {
                RequestBody requestFile4 = RequestBody.create(MediaType.parse("multipart/form-data"), new File(localByTaskId.getPic4()));
                params.put("patrolFile\"; filename=\"4.jpg", requestFile4);
            }
            if (localByTaskId.getPic5() != null) {
                RequestBody requestFile5 = RequestBody.create(MediaType.parse("multipart/form-data"), new File(localByTaskId.getPic5()));
                params.put("patrolFile\"; filename=\"5.jpg", requestFile5);
            }
            if (localByTaskId.getPic6() != null) {
                RequestBody requestFile6 = RequestBody.create(MediaType.parse("multipart/form-data"), new File(localByTaskId.getPic6()));
                params.put("patrolFile\"; filename=\"6.jpg", requestFile6);
            }
        }

        List<LocalPatrolDefectBean> localDefectByTaskId = SQLite.select().from(LocalPatrolDefectBean.class).where(LocalPatrolDefectBean_Table.task_id.is(task_id)).queryList();
        for (int i = 0; i < localDefectByTaskId.size(); i++) {
            params.put("taskDefectPatrolRecodeList[" + i + "].task_id", toRequestBody(task_id));
            params.put("taskDefectPatrolRecodeList[" + i + "].patrol_id", toRequestBody(localDefectByTaskId.get(i).getPatrol_id()));
            params.put("taskDefectPatrolRecodeList[" + i + "].status", toRequestBody(localDefectByTaskId.get(i).getStatus()));
            if (localDefectByTaskId.get(i).getStatus() == null) {
                Toast.makeText(PatrolRecordActivity.this, "常规巡视未填写完整", Toast.LENGTH_SHORT).show();
                return;
            }
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.task_id", toRequestBody(task_id));
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.category_id", toRequestBody(localDefectByTaskId.get(i).getCategory_id()));
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.grade_id", toRequestBody(localDefectByTaskId.get(i).getGrade_id()));
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.patrol_id", toRequestBody(localDefectByTaskId.get(i).getPatrol_id()));
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.content", toRequestBody(localDefectByTaskId.get(i).getContent()));
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.line_id", toRequestBody(localByTaskId.getLine_id()));
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.line_name", toRequestBody(localByTaskId.getLine_name()));
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.start_id", toRequestBody(localByTaskId.getTower_id()));
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.end_id", toRequestBody(localByTaskId.getTower_id()));
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.start_name", toRequestBody(localByTaskId.getTower_name()));
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.end_name", toRequestBody(localByTaskId.getTower_name()));
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.find_user_id", toRequestBody(localByTaskId.getUser_id()));
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.find_user_name", toRequestBody(localByTaskId.getUser_name()));
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.find_dep_id", toRequestBody(localByTaskId.getDep_id()));
            params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.find_dep_name", toRequestBody(localByTaskId.getDep_name()));
            String pics = localDefectByTaskId.get(i).getPics();
            if (pics != null) {
                String[] split = pics.split(";");
                for (int j = 0; j < split.length; j++) {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(split[j]));
                    params.put("taskDefectPatrolRecodeList[" + i + "].taskDefect.defect_file\"; filename=\"" + localDefectByTaskId.get(i).getPatrol_id() + "_" + j + ".jpg", requestFile);
                }
            }
        }
        substepUpload(params);
        Map<String, RequestBody> params1 = new HashMap<>();
        //本地特殊屬性
        List<TSSXLocalBean> localByTssx = SQLite.select().from(TSSXLocalBean.class).where(TSSXLocalBean_Table.task_id.is(task_id)).queryList();
        for (int i = 0; i < localByTssx.size(); i++) {
            String pics = localByTssx.get(i).getPhotoStr();
            //数据为空提交时删除
            if(TextUtils.isEmpty(localByTssx.get(i).getYhnr()) && TextUtils.isEmpty(pics)){
                localByTssx.get(i).delete();
            }

            params1.put("eqTowerWaresList[" + i + "].task_id", toRequestBody(task_id));
            params1.put("eqTowerWaresList[" + i + "].wares_id", toRequestBody(localByTssx.get(i).getKey()));
            params1.put("eqTowerWaresList[" + i + "].line_id", toRequestBody(localByTssx.get(i).getLine_id()));//线路id
            params1.put("eqTowerWaresList[" + i + "].tower_id", toRequestBody(localByTaskId.getTower_id()));//杆塔id
            params1.put("eqTowerWaresList[" + i + "].taskTrouble.task_id", toRequestBody(task_id));
            params1.put("eqTowerWaresList[" + i + "].taskTrouble.line_id", toRequestBody(localByTssx.get(i).getLine_id()));
            params1.put("eqTowerWaresList[" + i + "].taskTrouble.start_id", toRequestBody(localByTaskId.getTower_id()));
            params1.put("eqTowerWaresList[" + i + "].taskTrouble.end_id", toRequestBody(localByTaskId.getTower_id()));
            params1.put("eqTowerWaresList[" + i + "].taskTrouble.start_name", toRequestBody(localByTaskId.getTower_name()));
            params1.put("eqTowerWaresList[" + i + "].taskTrouble.end_name", toRequestBody(localByTaskId.getTower_name()));
            params1.put("eqTowerWaresList[" + i + "].taskTrouble.type_id", toRequestBody(localByTssx.get(i).getKey()));
            params1.put("eqTowerWaresList[" + i + "].taskTrouble.year", toRequestBody(localByTssx.get(i).getYear()));//
            params1.put("eqTowerWaresList[" + i + "].taskTrouble.month", toRequestBody(localByTssx.get(i).getMonth()));//
            params1.put("eqTowerWaresList[" + i + "].taskTrouble.day", toRequestBody(localByTssx.get(i).getDay()));//
            params1.put("eqTowerWaresList[" + i + "].taskTrouble.trouble_level", toRequestBody(localByTssx.get(i).getDj()));//隐患等级
            params1.put("eqTowerWaresList[" + i + "].taskTrouble.content", toRequestBody(localByTssx.get(i).getYhnr()));//隐患内容


            if (pics != null) {
                String[] split = pics.split(";");
                for (int j = 0; j < split.length; j++) {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(split[j]));
                    params1.put("eqTowerWaresList[" + i + "].taskTrouble.trouble_file\"; filename=\"" + split[j], requestFile);
                }
            }
        }
        substepUpload(params1);

    }

    /**
     * @param params
     */
    public void substepUpload(Map<String, RequestBody> params) {
        ProgressDialog.show(this, true, "正在上传...");
        BaseRequest.getInstance().getService().uploadPatrolRecord(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        saveTodoAudit("1");
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    //保存待办信息
    public void saveTodoAudit(String state) {
        SaveTodoReqbean saveTodoReqbean = new SaveTodoReqbean();
        saveTodoReqbean.setAudit_status(state);
        saveTodoReqbean.setId(task_id);
        saveTodoReqbean.setType_sign(localByTaskId.getType_sign());
        saveTodoReqbean.setFrom_user_id(SPUtil.getUserId(this));
        BaseRequest.getInstance().getService()
                .saveTodoAudit(saveTodoReqbean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TypeBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<TypeBean> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            Toast.makeText(PatrolRecordActivity.this, "成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            RxRefreshEvent.publish("refreshGroup");
                            RxRefreshEvent.publish("refreshTodo");
                            finish();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }

    //打开相机
    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constant.PATROL_RECORD_REQUEST_CODE);
    }

    //查看大图
    private void showBigImage(int position) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_big_image);
        PinchImageView iv = dialog.findViewById(R.id.iv);
       /* for (int i = 0; i < picBeanList.size(); i++) {
            if (picBeanList.get(i).getSign() != null && picBeanList.get(i).getSign().equals(String.valueOf(picIndex))) {
                Glide.with(this).load(BaseUrl.BASE_URL + picBeanList.get(i).getFile_path() + picBeanList.get(i).getFilename()).into(iv);
            }
        }*/
        Glide.with(this).load(new File(Environment.getExternalStorageDirectory().getPath() + "/MyPhoto/" + task_id + "_" + position + ".jpg")).into(iv);
        dialog.show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            try {
                switch (requestCode) {
                    case Constant.PATROL_RECORD_REQUEST_CODE:
                        Bundle bundle = data.getExtras();
                        Bitmap bitmap = (Bitmap) bundle.get("data");
                        String path = Environment.getExternalStorageDirectory().getPath()
                                + "/MyPhoto/" + task_id + "_" + picIndex + ".jpg";
                        FileUtil.saveFile(bitmap, path);
                        switch (picIndex) {
                            case 1:
                                ivPhoto1.setImageBitmap(bitmap);
                                SQLite.update(LocalPatrolRecordBean.class)
                                        .set(LocalPatrolRecordBean_Table.pic1.eq(path))
                                        .where(LocalPatrolRecordBean_Table.task_id.eq(task_id))
                                        .execute();
                                break;
                            case 2:
                                ivPhoto2.setImageBitmap(bitmap);
                                SQLite.update(LocalPatrolRecordBean.class)
                                        .set(LocalPatrolRecordBean_Table.pic2.eq(path))
                                        .where(LocalPatrolRecordBean_Table.task_id.eq(task_id))
                                        .execute();
                                break;
                            case 3:
                                ivPhoto3.setImageBitmap(bitmap);
                                SQLite.update(LocalPatrolRecordBean.class)
                                        .set(LocalPatrolRecordBean_Table.pic3.eq(path))
                                        .where(LocalPatrolRecordBean_Table.task_id.eq(task_id))
                                        .execute();
                                break;
                            case 4:
                                ivPhoto4.setImageBitmap(bitmap);
                                SQLite.update(LocalPatrolRecordBean.class)
                                        .set(LocalPatrolRecordBean_Table.pic4.eq(path))
                                        .where(LocalPatrolRecordBean_Table.task_id.eq(task_id))
                                        .execute();
                                break;
                            case 5:
                                ivPhoto5.setImageBitmap(bitmap);
                                SQLite.update(LocalPatrolRecordBean.class)
                                        .set(LocalPatrolRecordBean_Table.pic5.eq(path))
                                        .where(LocalPatrolRecordBean_Table.task_id.eq(task_id))
                                        .execute();
                                break;
                            case 6:
                                ivPhoto6.setImageBitmap(bitmap);
                                SQLite.update(LocalPatrolRecordBean.class)
                                        .set(LocalPatrolRecordBean_Table.pic6.eq(path))
                                        .where(LocalPatrolRecordBean_Table.task_id.eq(task_id))
                                        .execute();
                                break;
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
