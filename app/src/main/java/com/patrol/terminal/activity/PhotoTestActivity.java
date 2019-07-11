package com.patrol.terminal.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyFragmentPagerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.LocalPatrolRecordBean;
import com.patrol.terminal.bean.LocalPatrolRecordBean_Table;
import com.patrol.terminal.bean.PatrolRecordPicBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.NoScrollViewPager;
import com.patrol.terminal.widget.PinchImageView;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

public class PhotoTestActivity extends BaseActivity {
    public String audit_status;
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
        }
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
