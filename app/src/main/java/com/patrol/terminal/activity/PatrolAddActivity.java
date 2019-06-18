package com.patrol.terminal.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.GridViewAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.PictureSelectorConfig;

import java.io.File;
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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PatrolAddActivity extends BaseActivity {


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
    @BindView(R.id.patrol_start_tower)
    TextView patrolStartTower;
    @BindView(R.id.patrol_ower_end)
    TextView patrolOwerEnd;
    @BindView(R.id.patrol_ower_date)
    TextView patrolOwerDate;
    @BindView(R.id.patrol_ower_content)
    EditText patrolOwerContent;
    @BindView(R.id.patrol_ower_standar)
    TextView patrolOwerStandar;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.patrol_ower_finish)
    TextView patrolOwerFinish;

    private static final String TAG = "MainActivity";
    @BindView(R.id.et_patrol_name)
    EditText etPartolName;

    private Context mContext;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_add);
        ButterKnife.bind(this);
        mContext = this;
        initview();
        initGridView();
    }

    private void initview() {
        titleName.setText("添加巡视记录");
        patrolOwerStandar.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        patrolOwerStandar.getPaint().setAntiAlias(true);//抗锯齿
        patrolOwerDate.setText(DateUatil.getTime(new Date(System.currentTimeMillis())));
    }

    //初始化展示上传图片的GridView
    private void initGridView() {
        mGridViewAddImgAdapter = new GridViewAdapter(mContext, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
                    if (mPicList.size() == Constant.MAX_SELECT_PIC_NUM) {
                        //最多添加5张图片
                        viewPluImg(position);
                    } else {
                        //添加凭证图片
                        selectPic(Constant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constant.IMG_LIST, mPicList);
        intent.putExtra(Constant.POSITION, position);
        startActivityForResult(intent, Constant.REQUEST_CODE_MAIN);
    }

    /**
     * 打开相册或者照相机选择凭证图片，最多5张
     *
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == Constant.REQUEST_CODE_MAIN && resultCode == Constant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(Constant.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.title_back, R.id.patrol_start_tower, R.id.patrol_ower_end, R.id.patrol_ower_standar, R.id.patrol_ower_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.patrol_ower_standar:
                Intent intent = new Intent(this, PatrolStandardActivity.class);
                startActivity(intent);
                break;
            case R.id.patrol_ower_finish:
                savePatrol();
                break;
            case R.id.patrol_start_tower:
                patrolStartTower.setText("杆塔001");
                break;
            case R.id.patrol_ower_end:
                patrolOwerEnd.setText("杆塔002");
                break;
        }
    }

    //上传巡视记录
    public void savePatrol() {

        Map<String, String> params = new HashMap<>();
        params.put("name", etPartolName.getText().toString());
        params.put("start",patrolStartTower.getText().toString());
        params.put("end", patrolOwerEnd.getText().toString());
        params.put("detail", patrolOwerContent.getText().toString().trim());
        params.put("time",patrolOwerDate.getText().toString());
        params.put("inspector","999");

        List<  MultipartBody.Part> files=new ArrayList<>();
        for (int i = 0; i < mPicList.size(); i++) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(mPicList.get(i)));
            MultipartBody.Part body = MultipartBody.Part.createFormData("files",new File(mPicList.get(i)).getName(), requestFile);
            files.add(body);
        }

        BaseRequest.getInstance().getService().savePatrol(params, files).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        Toast.makeText(PatrolAddActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Toast.makeText(PatrolAddActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
