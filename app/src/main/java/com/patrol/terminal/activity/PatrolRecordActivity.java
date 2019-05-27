package com.patrol.terminal.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import com.patrol.terminal.utils.FileUtil;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.content.FileProvider;
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
    private String tower_name, task_id, sign, typename, audit_status;
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
        audit_status = getIntent().getStringExtra("audit_status");

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
            case 1:
                filePath1 = savePath + strPhotoName;
                break;

            case 2:
                filePath2 = savePath + strPhotoName;
                break;

            case 3:
                filePath3 = savePath + strPhotoName;
                break;

            case 4:
                filePath4 = savePath + strPhotoName;
                break;

            case 5:
                filePath5 = savePath + strPhotoName;
                break;

            case 6:
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
                if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)|| jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {

                    CancelOrOkDialog dialog = new CancelOrOkDialog(this, "是否通过", "不通过", "通过") {
                        @Override
                        public void ok() {
                            super.ok();
                            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                                saveTodoAudit("3");   //同意
                            } else if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                                saveTodoAudit("2");   //同意
                            }

                            dismiss();
                        }

                        @Override
                        public void cancel() {
                            super.cancel();
                            saveTodoAudit("4");  //不同意
                            dismiss();
                        }
                    };
                    dialog.show();
                } else if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) ) {
                    commitPatrolRecord();
                }
                break;
            case R.id.iv_photo1:

                //打卡相机前先检测SD卡是否可以用
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    photoUri1 = patrUri("record_1", 1);
                    startCamera(100, photoUri1);
                } else {
                    Toast.makeText(PatrolRecordActivity.this, "SD卡不可用", Toast.LENGTH_SHORT).show();
                }


                // selectPic();
                SPUtil.put(this, Constant.PARTOL_RECORD_PIC_INDEX, "index", 1);
                break;
            case R.id.iv_photo2:
                //selectPic();

                //打卡相机前先检测SD卡是否可以用
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    photoUri2 = patrUri("record_2", 2);
                    startCamera(200, photoUri2);
                } else {
                    Toast.makeText(PatrolRecordActivity.this, "SD卡不可用", Toast.LENGTH_SHORT).show();
                }

                SPUtil.put(this, Constant.PARTOL_RECORD_PIC_INDEX, "index", 2);
                break;
            case R.id.iv_photo3:
                //selectPic();
                //打卡相机前先检测SD卡是否可以用
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    photoUri3 = patrUri("record_3", 3);
                    startCamera(300, photoUri3);
                } else {
                    Toast.makeText(PatrolRecordActivity.this, "SD卡不可用", Toast.LENGTH_SHORT).show();
                }
                SPUtil.put(this, Constant.PARTOL_RECORD_PIC_INDEX, "index", 3);
                break;
            case R.id.iv_photo4:
                //selectPic();
                //打卡相机前先检测SD卡是否可以用
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    photoUri4 = patrUri("record_4", 4);
                    startCamera(400, photoUri4);
                } else {
                    Toast.makeText(PatrolRecordActivity.this, "SD卡不可用", Toast.LENGTH_SHORT).show();
                }
                SPUtil.put(this, Constant.PARTOL_RECORD_PIC_INDEX, "index", 4);
                break;
            case R.id.iv_photo5:
                //selectPic();
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    photoUri5 = patrUri("record_5", 5);
                    startCamera(500, photoUri5);
                } else {
                    Toast.makeText(PatrolRecordActivity.this, "SD卡不可用", Toast.LENGTH_SHORT).show();
                }
                SPUtil.put(this, Constant.PARTOL_RECORD_PIC_INDEX, "index", 5);
                break;
            case R.id.iv_photo6:
                //selectPic();
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    photoUri6 = patrUri("record_6", 6);
                    startCamera(600, photoUri6);
                } else {
                    Toast.makeText(PatrolRecordActivity.this, "SD卡不可用", Toast.LENGTH_SHORT).show();
                }
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

    //打开相机
    private void startCamera(int requestCode, Uri photoUri) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, requestCode);
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
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 100:
                    try {
                        Bitmap bitmap = FileUtil.compressBySize(filePath1, 300, 200);  //设置压缩后图片的高度和宽度
                        FileUtil.saveFile(bitmap, filePath1);
                        ivPhoto1.setImageBitmap(bitmap);

                        for (int i = 0; i < fileList.size(); i++) {
                            if (filePath1.contains(fileList.get(i).getName())) {
                                fileList.remove(i);
                            }
                        }

                        fileList.add(new File(filePath1));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;
                case 200:
                    try {
                        Bitmap bitmap = FileUtil.compressBySize(filePath2, 300, 200);  //设置压缩后图片的高度和宽度
                        FileUtil.saveFile(bitmap, filePath2);
                        ivPhoto2.setImageBitmap(bitmap);

                        for (int i = 0; i < fileList.size(); i++) {
                            if (filePath2.contains(fileList.get(i).getName())) {
                                fileList.remove(i);
                            }
                        }

                        fileList.add(new File(filePath2));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case 300:
                    try {
                        Bitmap bitmap = FileUtil.compressBySize(filePath3, 300, 200);  //设置压缩后图片的高度和宽度
                        FileUtil.saveFile(bitmap, filePath3);
                        ivPhoto3.setImageBitmap(bitmap);

                        for (int i = 0; i < fileList.size(); i++) {
                            if (filePath3.contains(fileList.get(i).getName())) {
                                fileList.remove(i);
                            }
                        }

                        fileList.add(new File(filePath3));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case 400:
                    try {
                        Bitmap bitmap = FileUtil.compressBySize(filePath4, 300, 200);  //设置压缩后图片的高度和宽度
                        FileUtil.saveFile(bitmap, filePath4);
                        ivPhoto4.setImageBitmap(bitmap);

                        for (int i = 0; i < fileList.size(); i++) {
                            if (filePath4.contains(fileList.get(i).getName())) {
                                fileList.remove(i);
                            }
                        }

                        fileList.add(new File(filePath4));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case 500:
                    try {
                        Bitmap bitmap = FileUtil.compressBySize(filePath5, 300, 200);  //设置压缩后图片的高度和宽度
                        FileUtil.saveFile(bitmap, filePath5);
                        ivPhoto5.setImageBitmap(bitmap);

                        for (int i = 0; i < fileList.size(); i++) {
                            if (filePath5.contains(fileList.get(i).getName())) {
                                fileList.remove(i);
                            }
                        }

                        fileList.add(new File(filePath5));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case 600:
                    try {
                        Bitmap bitmap = FileUtil.compressBySize(filePath6, 300, 200);  //设置压缩后图片的高度和宽度
                        FileUtil.saveFile(bitmap, filePath6);
                        ivPhoto6.setImageBitmap(bitmap);

                        for (int i = 0; i < fileList.size(); i++) {
                            if (filePath6.contains(fileList.get(i).getName())) {
                                fileList.remove(i);
                            }
                        }

                        fileList.add(new File(filePath6));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
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

    //t提交巡视记录
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

                        if (t.getCode() == 1) {
                            Toast.makeText(PatrolRecordActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
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

        if ("0".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("上传图片");
            } else {
                titleSetting.setVisibility(View.GONE);
            }
        } else if ("1".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审批");
            }else {
                titleSetting.setVisibility(View.GONE);
            }
        }  else if ("2".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("审批");
            }else {
                titleSetting.setVisibility(View.GONE);
            }
        }else if ("4".equals(audit_status)) {
            if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
                titleSetting.setVisibility(View.GONE);
            } else if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
                titleSetting.setVisibility(View.VISIBLE);
                titleSettingTv.setText("上传图片");
            } else {
                titleSetting.setVisibility(View.GONE);
            }
        } else {
            titleSetting.setVisibility(View.GONE);
        }
//    } else   {
//        if (jobType.contains(Constant.RUNNING_SQUAD_LEADER)) {
//            titleSetting.setVisibility(View.GONE);
//        } else if (jobType.contains(Constant.RUNNING_SQUAD_MEMBER) || jobType.contains(Constant.RUNNING_SQUAD_TEMA_LEADER)) {
//            titleSetting.setVisibility(View.VISIBLE);
//            titleSettingTv.setText("上传图片");
//        } else {
//            titleSetting.setVisibility(View.GONE);
//        }
//
//    }
    }

    //保存待办信息
    public void saveTodoAudit(String state) {

        SaveTodoReqbean saveTodoReqbean = new SaveTodoReqbean();

        saveTodoReqbean.setAudit_status(state);
        saveTodoReqbean.setId(task_id);

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
                            setResult(RESULT_OK);
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
