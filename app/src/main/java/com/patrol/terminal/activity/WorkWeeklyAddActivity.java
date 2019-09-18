package com.patrol.terminal.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.TssxPhotoAdapter;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.LocalGcjbBean;
import com.patrol.terminal.bean.LocalWorkWeeklyBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.ProgressDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 工作周报  添加
 */
public class WorkWeeklyAddActivity extends AppCompatActivity {

    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_tv)
    TextView title_setting_tv;
    @BindView(R.id.title_setting)
    RelativeLayout title_setting;

    @BindView(R.id.work_addname)
    TextView work_addname;
    @BindView(R.id.work_add_date)
    TextView work_add_date;

    @BindView(R.id.work_zj)
    EditText work_zj;
    @BindView(R.id.work_xzjh)
    EditText work_xzjh;
    @BindView(R.id.work_xtnr)
    EditText work_xtnr;
    @BindView(R.id.work_bz)
    EditText work_bz;

    @BindView(R.id.work_photo)
    GridView work_photo;

    private List<String> photoList = new ArrayList<>();
    private TssxPhotoAdapter photoAdapter;
    private int position;//点击图片项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workweekly_add);
        ButterKnife.bind(this);

        titleName.setText("工作周报");

        title_setting_tv.setText("提交");
        title_setting.setVisibility(View.VISIBLE);

        initView();
        localData();
    }

    public void localData() {
        LocalWorkWeeklyBean item = (LocalWorkWeeklyBean) getIntent().getSerializableExtra("workBean");
        if (item != null) {

            work_addname.setText(item.getUser_name());
            work_add_date.setText(item.getWork_date());
            work_zj.setText(item.getWork_bzzj());
            work_xzjh.setText(item.getWork_xzjh());
            work_xtnr.setText(item.getWork_xtnr());
            work_bz.setText(item.getWork_bz());

            photoList.addAll(Utils.photoToList(item.getTempWeeklyImgList()));
            photoAdapter.setAddStatus(false);
            photoAdapter.notifyDataSetChanged();

            work_zj.setEnabled(false);
            work_xzjh.setEnabled(false);
            work_xtnr.setEnabled(false);
            work_bz.setEnabled(false);
            work_photo.setEnabled(false);

            title_setting.setVisibility(View.GONE);
            title_setting_tv.setVisibility(View.GONE);

        }

    }

    public void initView() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String format1 = format.format(new Date(System.currentTimeMillis()));

        work_addname.setText(SPUtil.getUserName(this));
        work_add_date.setText(format1);

        photoAdapter = new TssxPhotoAdapter(this, photoList);
        work_photo.setAdapter(photoAdapter);
        work_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                startCamera();
            }
        });

    }


    @OnClick({R.id.title_back, R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.title_setting:

//                if(photoList.size()==0){
//                    Utils.showToast("必须添加一张照片");
//                    return;
//                }

                String bzzj = work_zj.getText().toString().trim();
                String xzjh = work_xzjh.getText().toString().trim();
                if(TextUtils.isEmpty(bzzj)){
                    Utils.showToast("请填写本周总结");
                    return;
                }
                if(TextUtils.isEmpty(xzjh)){
                    Utils.showToast("请填写下周计划");
                    return;
                }

                Map<String, RequestBody> params = new HashMap<>();
                for (int i = 0; i < photoList.size(); i++) {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(photoList.get(i)));
                    String[] split = photoList.get(i).split("/");
                    String name = split[split.length - 1];
                    params.put("files\"; filename=\"" + name, requestFile);
                }
                params.put("user_id",Utils.toRequestBody(SPUtil.getUserId(this)));//
                params.put("user_name",Utils.toRequestBody(SPUtil.getUserName(this)));
                params.put("created_date",Utils.toRequestBody(work_add_date.getText().toString()));
                params.put("conclusion_content",Utils.toRequestBody(bzzj));
                params.put("plan_content",Utils.toRequestBody(xzjh));
                params.put("coordination_content",Utils.toRequestBody(work_xtnr.getText().toString().trim()));
                params.put("remarks",Utils.toRequestBody(work_bz.getText().toString().trim()));
                saveWorkly(params);
                break;

        }
    }

    public void saveWorkly(Map<String, RequestBody> params ) {
        ProgressDialog.show(this);
        BaseRequest.getInstance().getService()
                .saveWorklyPOST(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast("提交成功");

                        Intent intent = new Intent();
                        intent.putExtra("projectname", "");
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }
                });
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
                    String path = Environment.getExternalStorageDirectory().getPath()
                            + "/MyPhoto/" + DateUatil.getDateStr() + "_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                    try {
                        //保存本地成功 刷新刷新数据添加到页面
                        FileUtil.saveFile(bitmap, path);
                        Log.e("保存照片地址", path);
                        if (position == photoList.size()) {
                            photoList.add(path);
                        } else {
                            photoList.set(position, path);
                        }

                        photoAdapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

    //打开相机
    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constant.DEFECT_REQUEST_CODE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.isEditStatus = false;
//        Utils.hideSysInput(this);
    }
}
