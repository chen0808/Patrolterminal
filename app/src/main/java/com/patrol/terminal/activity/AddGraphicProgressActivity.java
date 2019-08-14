package com.patrol.terminal.activity;

import android.content.Intent;
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

import androidx.core.content.FileProvider;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.GridViewAdapter2;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.PatrolLevel2;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.SPUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        belongPlanName.setText("兰州电网施工项目");
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
//                        viewPluImg(position);
                    } else {
                        //添加凭证图片    TODO By linmeng
                        //selectPic(Constant.MAX_SELECT_PIC_NUM - mPicList.size());

                        photoUri = patrUri(System.currentTimeMillis() + "_" + parent.getChildCount());
                        startCamera(1002, photoUri);

                    }
                } else {
//                    viewPluImg(position);
                }
            }
        });
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
    private void startCamera(int requestCode, Uri photoUri) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, requestCode);
    }

    @OnClick({R.id.title_back, R.id.belong_plan_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.belong_plan_rl:
                break;
        }
    }
}
