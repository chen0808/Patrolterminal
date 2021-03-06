package com.patrol.terminal.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.GridViewAdapter2;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.GraphicProgressBean;
import com.patrol.terminal.bean.InitiateProjectBean;
import com.patrol.terminal.bean.InitiateProjectBean2;
import com.patrol.terminal.bean.PatrolLevel2;
import com.patrol.terminal.bean.ProjectBoardBean;
import com.patrol.terminal.overhaul.ProjectSearchActivityNew;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.utils.Utils;
import com.patrol.terminal.widget.PinchImageView;
import com.patrol.terminal.widget.ProgressDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
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

//添加形象进度
public class AddGraphicProgressActivity extends BaseActivity {

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
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    @BindView(R.id.belong_plan_tv)
    TextView belongPlanTv;
    @BindView(R.id.belong_plan_name)
    TextView belongPlanName;
    @BindView(R.id.belong_plan_rl)
    RelativeLayout belongPlanRl;
    @BindView(R.id.create_name)
    TextView createName;
    @BindView(R.id.create_time)
    TextView createTime;
    @BindView(R.id.add_graphic_grogress_content)
    EditText addGraphicGrogressContent;
    @BindView(R.id.gridView)
    GridView gridView;
    private GridViewAdapter2 mGridViewAddImgAdapter;
    private List<String> mPicList=new ArrayList<>();
    private Uri photoUri;
    private String filePath;
    private InitiateProjectBean2 initiateProjectBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_graphic_progress);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        titleSetting.setVisibility(View.VISIBLE);
        titleSettingTv.setText("提交");
        titleName.setText("添加形象进度");
        createName.setText(SPUtil.getUserName(this));
        createTime.setText(DateUatil.getDate(new Date(System.currentTimeMillis())));

        initGridView ();
    }
    private void initGridView (){
        mGridViewAddImgAdapter = new GridViewAdapter2(this, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
                    if (mPicList.size() == Constant.MAX_SELECT_PIC_NUM) {
                        //最多添加5张图片
                        showBigImage(mPicList.get(position));
                    } else {
                        //添加凭证图片    TODO By linmeng
                        //selectPic(Constant.MAX_SELECT_PIC_NUM - mPicList.size());
                        startCamera();

                    }
                } else {
//                    viewPluImg(position);
                }
            }
        });
    }

    public  void saveData(){
        ProgressDialog.show(this, false, "正在加载。。。。");
        String planName = belongPlanName.getText().toString();
        String create_time = createTime.getText().toString();
        String content = addGraphicGrogressContent.getText().toString();
        if ("".equals(planName)||"请选择".equals(planName)){
            Toast.makeText(this,"请选择项目",Toast.LENGTH_SHORT).show();
            return;
        }
        String pics="";
        for (int i = 0; i < mPicList.size(); i++) {
            if (i==0){
                pics=mPicList.get(i);
            }else {
                pics=pics+","+mPicList.get(i);
            }
        }

        Map<String, RequestBody> params = new HashMap<>();
        params.put("temp_project_id", toRequestBody(initiateProjectBean.getId()));
        params.put("temp_project_name", toRequestBody(initiateProjectBean.getName()));
        params.put("user_id", toRequestBody(SPUtil.getUserId(this)));
        params.put("user_name", toRequestBody(SPUtil.getUserName(this)));
        params.put("upload_time ", toRequestBody(create_time));
        params.put("plan_desc", toRequestBody(content));

        for(int i=0;i<mPicList.size();i++){
            if(!mPicList.get(i).equals("")){
                File file = new File(mPicList.get(i));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                params.put("files\"; filename=\"" + i + ".jpg", requestFile);
            }
        }

        BaseRequest.getInstance().getService()
                .addGraPro(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {

                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        if(t.getCode() == 1){
                            Utils.showToast("提交成功");
                            setResult(RESULT_OK);
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Utils.showToast(e.getMessage());
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
    /**
     * 图片保存路径  这里是在SD卡目录下创建了MyPhoto文件夹
     *
     * @param fileName
     * @return
     */
    private Uri patrUri(String fileName) {  //指定了图片的名字，可以使用时间来命名
        // TODO Auto-generated method stub
        String strPhotoName = fileName + ".jpg";
        String savePath = Environment.getExternalStorageDirectory().getPath()
                + "/MyPhoto/";
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        filePath = savePath + strPhotoName;

        return FileProvider.getUriForFile(getApplicationContext(),
                "com.patrol.terminal.fileprovider",
                new File(dir, strPhotoName));
    }
    //打开相机
    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constant.PATROL_RECORD_REQUEST_CODE);
    }

    @OnClick({R.id.title_back, R.id.belong_plan_rl,R.id.title_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_setting:
                saveData();

                break;
            case R.id.belong_plan_rl:
                Intent intent=new Intent();
                intent.setClass(this, ProjectSearchActivityNew.class);
                startActivityForResult(intent,247);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== Constant.PATROL_RECORD_REQUEST_CODE&&resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            String path = Environment.getExternalStorageDirectory().getPath()
                    + "/MyPhoto/" + System.currentTimeMillis()+ ".jpg";
            try {
                FileUtil.saveFile(bitmap, path);
                mPicList.add(path);
                mGridViewAddImgAdapter.setdata(mPicList);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (requestCode== 247&&resultCode==RESULT_OK){
            initiateProjectBean = data.getParcelableExtra("search_project_item");
            belongPlanName.setText(initiateProjectBean.getName());
        }
    }
    //查看大图
    private void showBigImage(String path) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_big_image);
        PinchImageView iv = dialog.findViewById(R.id.iv);
        Glide.with(this).load(path).into(iv);
        dialog.show();
    }
}
