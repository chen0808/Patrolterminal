package com.patrol.terminal.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.JDDZbean;
import com.patrol.terminal.bean.OverhaulZzTaskBean;
import com.patrol.terminal.bean.PicEvent;
import com.patrol.terminal.bean.SaveTodoReqbean;
import com.patrol.terminal.bean.TodoListBean;
import com.patrol.terminal.bean.TypeBean;
import com.patrol.terminal.fragment.DefectFrgment;
import com.patrol.terminal.fragment.PatrolContentFrgment;
import com.patrol.terminal.fragment.SpecialAttrFrgment;
import com.patrol.terminal.fragment.TroubleFrgment;
import com.patrol.terminal.overhaul.OverhaulFileBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.PictureSelectorConfig;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.NoScrollViewPager;
import com.patrol.terminal.widget.ProgressDialog;

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

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
    private static final String[] data = new String[]{"巡视内容", "特殊属性", "缺陷", "隐患"};
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private MyFragmentPagerAdapter pagerAdapter;
    private Disposable subscribe;
    private Map<String, RequestBody> params = new HashMap<>();
    private String line_name, jobType;
    private String tower_id;
    private String tower_name, task_id, sign, typename, id;
    private List<File> fileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_record);
        ButterKnife.bind(this);

        jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        titleName.setText("巡视记录");
        line_name = getIntent().getStringExtra("line_name");
        tower_id = getIntent().getStringExtra("tower_id");
        task_id = getIntent().getStringExtra("task_id");
        tower_name = getIntent().getStringExtra("tower_name");
        sign = getIntent().getStringExtra("sign");
        typename = getIntent().getStringExtra("typename");
        initview();
        getPartrolRecord();
        getYXtodo();

    }

    private void initview() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new PatrolContentFrgment());
        fragmentList.add(new SpecialAttrFrgment());
        fragmentList.add(new DefectFrgment());
        fragmentList.add(new TroubleFrgment());
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
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.white));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.orange_vip));
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
                linePagerIndicator.setColors(Color.WHITE);
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(PatrolRecordActivity.this, 15);
            }
        });
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @OnClick({R.id.title_back, R.id.title_setting, R.id.iv_photo1, R.id.iv_photo2, R.id.iv_photo3, R.id.iv_photo4, R.id.iv_photo5, R.id.iv_photo6, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {

                    CancelOrOkDialog dialog = new CancelOrOkDialog(this, "是否通过", "不通过", "通过") {
                        @Override
                        public void ok() {
                            super.ok();
                            saveTodoAudit("1");   //同意
                            dismiss();
                        }

                        @Override
                        public void cancel() {
                            super.cancel();
                            saveTodoAudit("2");  //不同意
                            dismiss();
                        }
                    };
                    dialog.show();
                } else if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                    commitPatrolRecord();
                }
                break;
            case R.id.iv_photo1:
                selectPic();
                SPUtil.put(this, Constant.PARTOL_RECORD_PIC_INDEX, "index", 1);
                break;
            case R.id.iv_photo2:
                selectPic();
                SPUtil.put(this, Constant.PARTOL_RECORD_PIC_INDEX, "index", 2);
                break;
            case R.id.iv_photo3:
                selectPic();
                SPUtil.put(this, Constant.PARTOL_RECORD_PIC_INDEX, "index", 3);
                break;
            case R.id.iv_photo4:
                selectPic();
                SPUtil.put(this, Constant.PARTOL_RECORD_PIC_INDEX, "index", 4);
                break;
            case R.id.iv_photo5:
                selectPic();
                SPUtil.put(this, Constant.PARTOL_RECORD_PIC_INDEX, "index", 5);
                break;
            case R.id.iv_photo6:
                selectPic();
                SPUtil.put(this, Constant.PARTOL_RECORD_PIC_INDEX, "index", 6);
                break;
            case R.id.fab:
                startActivity(new Intent(this, MapActivity.class));
                break;
        }
    }

    /**
     * 打开相册或者照相机选择凭证图片
     */
    private void selectPic() {
        PictureSelectorConfig.initSingleConfig(this);
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                int index = (int) SPUtil.get(this, Constant.PARTOL_RECORD_PIC_INDEX, "index", 0);
                switch (index) {
                    case 1:
                        Glide.with(this).load(compressPath).into(ivPhoto1);
                        fileList.add(new File(compressPath));
                        break;
                    case 2:
                        Glide.with(this).load(compressPath).into(ivPhoto2);
                        fileList.add(new File(compressPath));
                        break;
                    case 3:
                        Glide.with(this).load(compressPath).into(ivPhoto3);
                        fileList.add(new File(compressPath));
                        break;
                    case 4:
                        Glide.with(this).load(compressPath).into(ivPhoto4);
                        fileList.add(new File(compressPath));
                        break;
                    case 5:
                        Glide.with(this).load(compressPath).into(ivPhoto5);
                        fileList.add(new File(compressPath));
                        break;
                    case 6:
                        Glide.with(this).load(compressPath).into(ivPhoto6);
                        fileList.add(new File(compressPath));
                        break;
                }

//                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
//                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                    EventBus.getDefault().post(new PicEvent(localMedia));
                    break;
            }
        }
        if (requestCode == Constant.REQUEST_CODE_MAIN && resultCode == Constant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(Constant.IMG_LIST); //要删除的图片的集合
//            mPicList.clear();
//            mPicList.addAll(toDeletePicList);
//            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
    }

    //查询接电电阻
    public void getPartrolRecord() {

        ProgressDialog.show(this, false, "正在加载。。。。");
        BaseRequest.getInstance().getService()
                .getPartrolRecord(task_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulFileBean>>(this) {


                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulFileBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            List<OverhaulFileBean> results = t.getResults();
                            for (int i = 0; i < results.size(); i++) {
                                OverhaulFileBean overhaulFileBean = results.get(i);
                                String file_path = overhaulFileBean.getFile_path();
                                String compressPath = BaseUrl.BASE_URL + file_path.substring(1, file_path.length()) + overhaulFileBean.getFilename();
                                switch (i + 1) {
                                    case 1:
                                        Glide.with(PatrolRecordActivity.this).load(compressPath).into(ivPhoto1);
                                        break;
                                    case 2:
                                        Glide.with(PatrolRecordActivity.this).load(compressPath).into(ivPhoto2);
                                        break;
                                    case 3:
                                        Glide.with(PatrolRecordActivity.this).load(compressPath).into(ivPhoto3);
                                        break;
                                    case 4:
                                        Glide.with(PatrolRecordActivity.this).load(compressPath).into(ivPhoto4);
                                        break;
                                    case 5:
                                        Glide.with(PatrolRecordActivity.this).load(compressPath).into(ivPhoto5);
                                        break;
                                    case 6:
                                        Glide.with(PatrolRecordActivity.this).load(compressPath).into(ivPhoto6);
                                        break;
                                }
                            }

                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    private void commitPatrolRecord() {
        if (fileList.size() < 6) {
            Toast.makeText(PatrolRecordActivity.this, "请上传6张对应图片！", Toast.LENGTH_SHORT).show();
            return;
        }
        ProgressDialog.show(this, false, "正在上传。。。");
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            params.put("file" + (i + 1) + "\"; filename=\"" + file.getName(), requestFile);
        }

        params.put("user_id", toRequestBody(SPUtil.getUserId(this)));
        params.put("name", toRequestBody("关于" + line_name + tower_name + "的" + typename));
        params.put("type_sign", toRequestBody(sign));
        params.put("audit_id", toRequestBody(SPUtil.getDepId(this)));
        params.put("id", toRequestBody(task_id));
        BaseRequest.getInstance().getService().commitPatrolRecord(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(PatrolRecordActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                        if (t.getCode() == 1) {
                            Toast.makeText(PatrolRecordActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Log.i("11111", e.toString());
                        Toast.makeText(PatrolRecordActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
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

    public void getYXtodo() {
        BaseRequest.getInstance().getService()
                .getYXOnetodo(task_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TodoListBean>>(this) {

                    @Override
                    protected void onSuccees(BaseResult<List<TodoListBean>> t) throws Exception {
                        if (t.getCode() == 1) {
                            List<TodoListBean> results = t.getResults();
                            if (results.size() > 0) {
                                TodoListBean todoListBean = results.get(0);
                                String audit_status = todoListBean.getAudit_status();
                                if ("0".equals(audit_status)) {
                                    if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                                        titleSetting.setVisibility(View.VISIBLE);
                                        titleSettingTv.setText("审批");
                                    }
                                } else if ("2".equals(audit_status)) {
                                    if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                                        titleSetting.setVisibility(View.GONE);
                                    }else  if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                                        titleSetting.setVisibility(View.VISIBLE);
                                        titleSettingTv.setText("上传图片");
                                    } else {
                                        titleSetting.setVisibility(View.GONE);

                                    }

                                } else {
                                    titleSetting.setVisibility(View.GONE);

                                }
                            } else {
                                if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                                    titleSetting.setVisibility(View.GONE);
                                }else   if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                                    titleSetting.setVisibility(View.VISIBLE);
                                    titleSettingTv.setText("上传图片");
                                } else {
                                    titleSetting.setVisibility(View.GONE);
                                }

                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    //保存待办信息
    public void saveTodoAudit(String state) {

        SaveTodoReqbean saveTodoReqbean = new SaveTodoReqbean();

        saveTodoReqbean.setAudit_status(state);
        saveTodoReqbean.setTask_id(task_id);

        BaseRequest.getInstance().getService()
                .saveTodoAudit(saveTodoReqbean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TypeBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<TypeBean> t) throws Exception {
                        ProgressDialog.cancle();
                        if (t.getCode() == 1) {
                            Toast.makeText(PatrolRecordActivity.this, "审批成功", Toast.LENGTH_SHORT).show();
                            RxRefreshEvent.publish("todo");
                            finish();
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
    }
}
