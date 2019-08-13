package com.patrol.terminal.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.TssxPhotoAdapter;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.LocalGcjbBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.StringUtil;
import com.patrol.terminal.utils.TimeUtil;
import com.patrol.terminal.utils.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 工程简报 添加
 */
public class EngineeringBriefAddActivity extends AppCompatActivity {

    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_tv)
    TextView title_setting_tv;
    @BindView(R.id.title_setting)
    RelativeLayout title_setting;

    @BindView(R.id.gcjb_add_bh)
    TextView gcjb_add_bh;
    @BindView(R.id.gcjb_add_ssxm)
    TextView gcjb_add_ssxm;
    @BindView(R.id.gcjb_add_tbr)
    TextView gcjb_add_tbr;
    @BindView(R.id.gcjb_add_jblb)
    TextView gcjb_add_jblb;

    @BindView(R.id.gcjb_sgqk)
    EditText gcjb_sgqk;
    @BindView(R.id.gcjb_glqk)
    EditText gcjb_glqk;
    @BindView(R.id.gcjb_czqk)
    EditText gcjb_czqk;
    @BindView(R.id.gcjb_jdjh)
    EditText gcjb_jdjh;
    @BindView(R.id.gcjb_bz)
    EditText gcjb_bz;

    @BindView(R.id.img_allow)
    ImageView img_allow;

    @BindView(R.id.gcjb_photo)
    GridView gcjb_photo;

    private List<String> photoList = new ArrayList<>();
    private TssxPhotoAdapter photoAdapter;
    private int position;//点击图片项
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineering_brief_add);
        ButterKnife.bind(this);

        type = getIntent().getStringExtra(Constant.GCJB_TYPE_STR);
        if (type.equals(Constant.GCJB_YZF_STR)) {
            titleName.setText("业主方");
        } else if (type.equals(Constant.GCJB_JLF_STR)) {
            titleName.setText("监理方");
        } else if (type.equals(Constant.GCJB_SGF_STR) || type.equals(Constant.GCJB_ADD_STR)) {
            titleName.setText("施工方");
        }

        title_setting_tv.setText("提交");
        title_setting.setVisibility(View.VISIBLE);

        initView();
        localData();
    }

    public void localData() {
        LocalGcjbBean item = (LocalGcjbBean) getIntent().getSerializableExtra("gclbBean");
        if (item != null) {
            gcjb_add_bh.setText(item.getProject_bgnum());
            gcjb_add_ssxm.setText(item.getProject_ssxm());
            gcjb_add_tbr.setText(item.getProject_tbr());
            gcjb_add_jblb.setText(item.getProject_type());
            gcjb_sgqk.setText(item.getProject_sgqk());
            gcjb_glqk.setText(item.getProject_glqk());
            gcjb_czqk.setText(item.getProject_czqt());
            gcjb_jdjh.setText(item.getProject_jdjh());
            gcjb_bz.setText(item.getProject_bz());

            photoList.addAll(Utils.strToList(item.getProject_photo()));
            photoAdapter.setAddStatus(false);
            photoAdapter.notifyDataSetChanged();

            gcjb_sgqk.setEnabled(false);
            gcjb_glqk.setEnabled(false);
            gcjb_czqk.setEnabled(false);
            gcjb_jdjh.setEnabled(false);
            gcjb_bz.setEnabled(false);
            gcjb_photo.setEnabled(false);
            gcjb_add_jblb.setEnabled(false);

            title_setting.setVisibility(View.GONE);
            title_setting_tv.setVisibility(View.GONE);
            img_allow.setVisibility(View.GONE);

        }

    }

    public void initView() {
        long l = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String format1 = format.format(new Date(l));
        String name = "";
        if (type.equals(Constant.GCJB_YZF_STR)) {
            name = "业主方";
        } else if (type.equals(Constant.GCJB_JLF_STR)) {
            name = "监理方";
        } else if (type.equals(Constant.GCJB_SGF_STR) || type.equals(Constant.GCJB_ADD_STR)) {
            name = "施工方";
        }

        gcjb_add_bh.setText("00" + format1);
        gcjb_add_ssxm.setText(name + format1);
        gcjb_add_tbr.setText("马宝龙");
        gcjb_add_jblb.setText("初步设计图");

        photoAdapter = new TssxPhotoAdapter(this, photoList);
        gcjb_photo.setAdapter(photoAdapter);
        gcjb_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                startCamera();
            }
        });

    }


    @OnClick({R.id.title_back, R.id.title_setting,R.id.gcjb_add_jblb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.gcjb_add_jblb:
                String[] types = new String[]{"初步设计", "方案设计", "施工期", "竣工"};
                int checkedItem = 0;
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setTitle("选择简报类型");
                alertBuilder.setSingleChoiceItems(types, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int options1) {
//                        checkedItem = options1;
                        gcjb_add_jblb.setText(types[options1]);
                        dialog.dismiss();
                    }
                });
                AlertDialog typeDialog = alertBuilder.create();
                typeDialog.show();
                break;
            case R.id.title_setting:

//                if(photoList.size()==0){
//                    Utils.showToast("必须添加一张照片");
//                    return;
//                }

                LocalGcjbBean bean = new LocalGcjbBean();
                bean.setType(type);

                bean.setProject_name(gcjb_add_ssxm.getText().toString());
                bean.setProject_ssxm(gcjb_add_ssxm.getText().toString());
                bean.setProject_bgnum(gcjb_add_bh.getText().toString());
                bean.setProject_tbr(gcjb_add_tbr.getText().toString());
                bean.setProject_type(gcjb_add_jblb.getText().toString());
                bean.setProject_date(DateUatil.getMin());
                bean.setProject_sgqk(gcjb_sgqk.getText().toString().trim());
                bean.setProject_glqk(gcjb_glqk.getText().toString().trim());
                bean.setProject_czqt(gcjb_czqk.getText().toString().trim());
                bean.setProject_jdjh(gcjb_jdjh.getText().toString().trim());
                bean.setProject_bz(gcjb_bz.getText().toString().trim());
                bean.setProject_photo(Utils.listToStr(photoList));
                bean.save();

                Utils.showToast("提交成功");
                setResult(RESULT_OK);
                finish();
                break;

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
    }
}
