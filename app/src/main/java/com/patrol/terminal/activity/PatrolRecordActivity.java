package com.patrol.terminal.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

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
import com.patrol.terminal.bean.LocalPatrolRecordBean;
import com.patrol.terminal.bean.PatrolRecordPicBean;
import com.patrol.terminal.bean.SaveTodoReqbean;
import com.patrol.terminal.bean.TaskBean;
import com.patrol.terminal.bean.TypeBean;
import com.patrol.terminal.fragment.DefectFrgment;
import com.patrol.terminal.fragment.PatrolContentFrgment;
import com.patrol.terminal.fragment.SpecialTSSXFrgment;
import com.patrol.terminal.fragment.TroubleFrgment;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.CancelOrOkDialog;
import com.patrol.terminal.widget.NoScrollViewPager;
import com.patrol.terminal.widget.PinchImageView;
import com.patrol.terminal.widget.ProgressDialog;
import com.patrol.terminal.widget.SignDialog;
import com.raizlabs.android.dbflow.sql.language.SQLite;

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
    public static final int PHOTO1 = 1;
    public static final int PHOTO2 = 2;
    public static final int PHOTO3 = 3;
    public static final int PHOTO4 = 4;
    public static final int PHOTO5 = 5;
    public static final int PHOTO6 = 6;
    private List<LocalMedia> localMedia;
    private List<PatrolRecordPicBean> picBeanList;
    private String jobType;
    private String task_id;
    private String sign;
    private String audit_status;
    private String lineName;
    private LocalPatrolRecordBean localBean;
    private int picIndex = 0;
    private String tower_id;

    public String getLineName() {
        return lineName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_record);
        ButterKnife.bind(this);
        titleName.setText("巡视记录");

        jobType = SPUtil.getString(this, Constant.USER, Constant.JOBTYPE, "");
        task_id = getIntent().getStringExtra("task_id");
        tower_id = getIntent().getStringExtra("tower_id");
        audit_status = getIntent().getStringExtra("audit_status");
        initview();
//        getPartrolRecord();
        getYXtodo();
        getTask(task_id);
//        initPic();
    }

    private void getDataFromDatabase() {
        //从数据库查询出数据
        List<LocalPatrolRecordBean> list = SQLite.select().from(LocalPatrolRecordBean.class).queryList();
        for (int i = 0; i < list.size(); i++) {
            if (tower_id.equals(list.get(i).getTower_id())) ;

        }
    }

    private void getTask(String task_id) {
        BaseRequest.getInstance().getService()
                .getTask(task_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TaskBean>(this) {
                    @Override
                    protected void onSuccees(BaseResult<TaskBean> t) throws Exception {
                        TaskBean bean = t.getResults();

                        localBean = new LocalPatrolRecordBean();
                        localBean.setTask_id(bean.getId());
                        localBean.setLine_id(bean.getLine_id());
                        localBean.setTower_id(bean.getTower_id());
                        localBean.save();

                        getDataFromDatabase();

                        sign = bean.getType_sign();
                        lineName = bean.getLine_name();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void getYXtodo() {
        if ("1".equals(audit_status)) {
            titleSetting.setVisibility(View.VISIBLE);
            titleSettingTv.setText("审批");
        } else if ("2".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审批");
            }
        } else if ("0".equals(audit_status) || "4".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER)) {
                titleSetting.setVisibility(View.GONE);
                titleSettingTv.setText("提交");
            }
        } else {
            titleSetting.setVisibility(View.GONE);
        }
    }

    private void initPic() {


        BaseRequest.getInstance().getService()
                .getRecordPicList(task_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<PatrolRecordPicBean>>(this) {


                    @Override
                    protected void onSuccees(BaseResult<List<PatrolRecordPicBean>> t) throws Exception {
                        picBeanList = t.getResults();
                        for (int i = 0; i < picBeanList.size(); i++) {
                            showPic(picBeanList.get(i));
                        }
                        if (picBeanList.size() == 6) {
                            titleSetting.setVisibility(View.VISIBLE);
                            if (audit_status.equals("0") || audit_status.equals("4")) {
                                titleSettingTv.setText("提交");
                            } else {
                                titleSettingTv.setText("审核");
                            }
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

    /**
     * 图片保存路径  这里是在SD卡目录下创建了MyPhoto文件夹
     *
     * @param fileName
     * @return
     */
    private Uri patrUri(String fileName, int pos) {  //指定了图片的名字，可以使用时间来命名
        // TODO Auto-generated method stub
        String strPhotoName = fileName + ".jpg";
        String savePath = Environment.getExternalStorageDirectory().getPath()
                + "/MyPhoto/";
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        switch (pos) {
            case PHOTO1:
                filePath1 = savePath + strPhotoName;
                break;

            case PHOTO2:
                filePath2 = savePath + strPhotoName;
                break;

            case PHOTO3:
                filePath3 = savePath + strPhotoName;
                break;

            case PHOTO4:
                filePath4 = savePath + strPhotoName;
                break;

            case PHOTO5:
                filePath5 = savePath + strPhotoName;
                break;

            case PHOTO6:
                filePath6 = savePath + strPhotoName;
                break;
        }

        return FileProvider.getUriForFile(getApplicationContext(),
                "com.patrol.terminal.fileprovider",
                new File(dir, strPhotoName));
    }

    //private String recordPath1;
    Uri photoUri1;
    String filePath1;
    Uri photoUri2;
    String filePath2;
    Uri photoUri3;
    String filePath3;
    Uri photoUri4;
    String filePath4;
    Uri photoUri5;
    String filePath5;
    Uri photoUri6;
    String filePath6;

    @OnClick({R.id.title_back, R.id.title_setting, R.id.iv_photo1, R.id.iv_photo2, R.id.iv_photo3, R.id.iv_photo4, R.id.iv_photo5, R.id.iv_photo6, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    saveTodoAudit("1");
                } else {
                    CancelOrOkDialog dialog = new CancelOrOkDialog(this, "是否通过", "不通过", "通过") {
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
                break;
            case R.id.iv_photo1:
              /*  if (audit_status.equals("0") || audit_status.equals("4")) {
                    photoUri1 = patrUri("record_1", PHOTO1);
                    startCamera(PHOTO1, photoUri1);
                } else {
                    showBigImage(PHOTO1);
                }*/

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, Constant.PATROL_RECORD_REQUEST_CODE);
                break;
            case R.id.iv_photo2:
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    photoUri2 = patrUri("record_2", PHOTO2);
                    startCamera(PHOTO2, photoUri2);
                } else {
                    showBigImage(PHOTO2);
                }
                break;
            case R.id.iv_photo3:
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    photoUri3 = patrUri("record_3", PHOTO3);
                    startCamera(PHOTO3, photoUri3);
                } else {
                    showBigImage(PHOTO3);
                }
                break;
            case R.id.iv_photo4:
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    photoUri4 = patrUri("record_4", PHOTO4);
                    startCamera(PHOTO4, photoUri4);
                } else {
                    showBigImage(PHOTO4);
                }
                break;
            case R.id.iv_photo5:
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    photoUri5 = patrUri("record_5", PHOTO5);
                    startCamera(PHOTO5, photoUri5);
                } else {
                    showBigImage(PHOTO5);
                }
                break;
            case R.id.iv_photo6:
                if (audit_status.equals("0") || audit_status.equals("4")) {
                    photoUri6 = patrUri("record_6", PHOTO6);
                    startCamera(PHOTO6, photoUri6);
                } else {
                    showBigImage(PHOTO6);
                }
                break;
            case R.id.fab:
                startActivity(new Intent(this, MapActivity.class));
                break;
        }
    }

    //打开相机
    private void startCamera(int requestCode, Uri photoUri) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, requestCode);
    }

    //查看大图
    private void showBigImage(int photo) {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_big_image);
        PinchImageView iv = dialog.findViewById(R.id.iv);
        for (int i = 0; i < picBeanList.size(); i++) {
            if (picBeanList.get(i).getSign() != null && picBeanList.get(i).getSign().equals(String.valueOf(photo))) {
                Glide.with(this).load(BaseUrl.BASE_URL + picBeanList.get(i).getFile_path() + picBeanList.get(i).getFilename()).into(iv);
            }
        }
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
                    case PHOTO1:
                        Bitmap bitmap1 = FileUtil.compressBySize(filePath1, 1080, 1920);  //设置压缩后图片的高度和宽度
                        FileUtil.saveFile(bitmap1, filePath1);
                        ivPhoto1.setImageBitmap(bitmap1);
                        upLoadPic(PHOTO1, bitmap1);
                        break;
                    case PHOTO2:
                        Bitmap bitmap2 = FileUtil.compressBySize(filePath2, 1080, 1920);  //设置压缩后图片的高度和宽度
                        FileUtil.saveFile(bitmap2, filePath2);
                        ivPhoto2.setImageBitmap(bitmap2);
                        upLoadPic(PHOTO2, bitmap2);
                        break;
                    case PHOTO3:
                        Bitmap bitmap3 = FileUtil.compressBySize(filePath3, 1080, 1920);  //设置压缩后图片的高度和宽度
                        FileUtil.saveFile(bitmap3, filePath3);
                        ivPhoto3.setImageBitmap(bitmap3);
                        upLoadPic(PHOTO3, bitmap3);
                        break;
                    case PHOTO4:
                        Bitmap bitmap4 = FileUtil.compressBySize(filePath4, 1080, 1920);  //设置压缩后图片的高度和宽度
                        FileUtil.saveFile(bitmap4, filePath4);
                        ivPhoto4.setImageBitmap(bitmap4);
                        upLoadPic(PHOTO4, bitmap4);
                        break;
                    case PHOTO5:
                        Bitmap bitmap5 = FileUtil.compressBySize(filePath5, 1080, 1920);  //设置压缩后图片的高度和宽度
                        FileUtil.saveFile(bitmap5, filePath5);
                        ivPhoto5.setImageBitmap(bitmap5);
                        upLoadPic(PHOTO5, bitmap5);
                        break;
                    case PHOTO6:
                        Bitmap bitmap6 = FileUtil.compressBySize(filePath6, 1080, 1920);  //设置压缩后图片的高度和宽度
                        FileUtil.saveFile(bitmap6, filePath6);
                        ivPhoto6.setImageBitmap(bitmap6);
                        upLoadPic(PHOTO6, bitmap6);
                        break;
                    case PictureConfig.CHOOSE_REQUEST:
                        localMedia = PictureSelector.obtainMultipleResult(data);
                        Constant.picList = localMedia;
                        break;
                    case Constant.PATROL_RECORD_REQUEST_CODE:
                        Bundle bundle = data.getExtras();
                        Bitmap bitmap = (Bitmap) bundle.get("data");
                        String path = Environment.getExternalStorageDirectory().getPath()
                                + "/MyPhoto/" + tower_id + "_" + picIndex + ".jpg";
                        FileUtil.saveFile(bitmap, path);
                        switch (picIndex) {
                            case 1:
                                localBean.setPic1(path);
                                break;
                            case 2:
                                localBean.setPic2(path);
                                break;
                            case 3:
                                localBean.setPic3(path);
                                break;
                            case 4:
                                localBean.setPic4(path);
                                break;
                            case 5:
                                localBean.setPic5(path);
                                break;
                            case 6:
                                localBean.setPic6(path);
                                break;
                        }
                        localBean.save();
                        break;
                }
                if (ivPhoto1.getDrawable() != null && ivPhoto2.getDrawable() != null && ivPhoto3.getDrawable() != null
                        && ivPhoto4.getDrawable() != null && ivPhoto5.getDrawable() != null && ivPhoto6.getDrawable() != null) {
                    titleSetting.setVisibility(View.VISIBLE);
                    if (audit_status.equals("0") || audit_status.equals("4")) {
                        titleSettingTv.setText("提交");
                    } else {
                        titleSettingTv.setText("审核");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public List<LocalMedia> getPics() {
        return localMedia;
    }

    private void upLoadPic(int sign, Bitmap bitmap) {
        ProgressDialog.show(this, false, "正在上传图片");
        Log.d("signsign", "sign:" + sign);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), SignDialog.saveBitmapFile(bitmap, sign + ".jpg"));
        params.clear();
        params.put("file" + "\"; filename=\"" + sign + ".jpg", requestFile);
        params.put("id", toRequestBody(task_id));
        params.put("sign", toRequestBody(String.valueOf(sign)));
        BaseRequest.getInstance().getService()
                .uploadRecordPic(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {


                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(PatrolRecordActivity.this, "图片上传成功！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(PatrolRecordActivity.this, "图片上传失败，请重新上传！", Toast.LENGTH_SHORT).show();
                    }

                });
    }

    //保存待办信息
    public void saveTodoAudit(String state) {

        SaveTodoReqbean saveTodoReqbean = new SaveTodoReqbean();

        saveTodoReqbean.setAudit_status(state);
        saveTodoReqbean.setId(task_id);
        saveTodoReqbean.setType_sign(sign);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }
}
