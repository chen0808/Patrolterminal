package com.patrol.terminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.patrol.terminal.bean.GradeBean;
import com.patrol.terminal.bean.SpecialAttrBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.PictureSelectorConfig;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import org.angmarch.views.NiceSpinner;

import java.io.File;
import java.util.ArrayList;
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

public class CommitDefectActivity extends BaseActivity {
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
    @BindView(R.id.nice_spinner1)
    NiceSpinner niceSpinner1;
    @BindView(R.id.nice_spinner2)
    NiceSpinner niceSpinner2;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.et_content)
    EditText etContent;
    List<String> list1 = new ArrayList<>();
    List<String> listId = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<MultipartBody.Part> files = new ArrayList<>();
    private GridViewAdapter mGridViewAddImgAdapter;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private List<GradeBean> results;
    private int position1;
    private int position2;
    private boolean isPatrol;
    private String category;
    private String tower_id, tower_name;
    private String line_id, line_name;
    private Map<String, RequestBody> params = new HashMap<>();
    private String patrol_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_defect);
        ButterKnife.bind(this);
        String name = getIntent().getStringExtra("name");
        String content = getIntent().getStringExtra("content");
        titleName.setText(name);
        tvCategory.setText(content);
        isPatrol = getIntent().getBooleanExtra("isPatrol", false);
        if (isPatrol) {
            patrol_id = getIntent().getStringExtra("patrol_id");
            category = getIntent().getStringExtra("category");
            initSpinner(category);
        } else {
            niceSpinner1.setVisibility(View.GONE);
            niceSpinner2.setVisibility(View.GONE);
//            String id = getIntent().getStringExtra("id");
//            List<SpecialAttrBean> listLevel3 = (List<SpecialAttrBean>) getIntent().getSerializableExtra("listLevel3");
//            List<SpecialAttrBean> listLevel4 = (List<SpecialAttrBean>) getIntent().getSerializableExtra("listLevel4");
//            initSpecialSpinner(id, listLevel3, listLevel4);
        }
        initGridView();
    }

    private void initSpecialSpinner(String id, List<SpecialAttrBean> listLevel3, List<SpecialAttrBean> listLevel4) {
        list1.clear();
        listId.clear();
        list2.clear();
        for (int i = 0; i < listLevel3.size(); i++) {
            if (listLevel3.get(i).getP_id().equals(id)) {
                list1.add(listLevel3.get(i).getName());
                listId.add(listLevel3.get(i).getId());
            }
        }
        if (listId.size() > 0) {
            for (int j = 0; j < listLevel4.size(); j++) {
                if (listLevel4.get(j).getP_id().equals(listId.get(0))) {
                    list2.add(listLevel4.get(j).getName());
                }
            }
        }
        if (list1.size() > 0) {
            niceSpinner1.attachDataSource(list1);
        }
        if (list2.size() > 0) {
            etContent.setText(list2.get(0));
        }
        if (list2.size() > 0) {
            niceSpinner2.attachDataSource(list2);
        }
        niceSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list2.clear();
                for (int i = 0; i < listLevel4.size(); i++) {
                    if (listLevel4.get(i).getP_id().equals(listId.get(position))) {
                        list2.add(listLevel4.get(i).getName());
                        position1 = position;
                    }
                }
                if (list2 != null && list2.size() > 0) {
                    niceSpinner2.attachDataSource(list2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        niceSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position2 = position;
                etContent.setText(list2.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initSpinner(String category) {
        BaseRequest.getInstance().getService().getPatrolSpinner(category).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<GradeBean>>(this) {
                    @Override
                    protected void onSuccees(BaseResult<List<GradeBean>> t) throws Exception {
                        results = t.getResults();
                        list1.clear();
                        for (int i = 0; i < results.size(); i++) {
                            list1.add(results.get(i).getName());

                        }
                        niceSpinner1.attachDataSource(list1);

                        list2.clear();
                        for (int j = 0; j < results.get(0).getValue().size(); j++) {
                            list2.add(results.get(0).getValue().get(j).getCONTENT());
                        }
                        etContent.setText(results.get(0).getValue().get(0).getCONTENT());
                        niceSpinner2.attachDataSource(list2);
                        niceSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                list2.clear();
                                for (int i = 0; i < results.get(position).getValue().size(); i++) {
                                    list2.add(results.get(position).getValue().get(i).getCONTENT());
                                    position1 = position;
                                }
                                niceSpinner2.attachDataSource(list2);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        niceSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                position2 = position;
                                etContent.setText(results.get(position1).getValue().get(position).getCONTENT());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initGridView() {
        mGridViewAddImgAdapter = new GridViewAdapter(this, mPicList);
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
        Intent intent = new Intent(this, PlusImageActivity.class);
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
//            mPicList.clear();
//            mPicList.addAll(toDeletePicList);
//            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
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

    @OnClick({R.id.title_back, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_commit:
                commit();
                break;
        }
    }

    private void commit() {
        params.clear();
        if (mPicList == null || mPicList.size() < 2) {
            Toast.makeText(this, "至少上传2张图片", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < mPicList.size(); i++) {
//                    RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"), mPicList.get(i));
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(mPicList.get(i)));
//                    MultipartBody.Part body = MultipartBody.Part.createFormData("files", mPicList.get(i).getName(), requestFile);
            String[] split = mPicList.get(i).split("/");
            String name = split[split.length - 1];
            params.put("file\"; filename=\"" + name, requestFile);
        }
        String task_id = (String) SPUtil.get(this, "ids", "task_id", "");
        tower_id = (String) SPUtil.get(this, "ids", "tower_id", "");
        line_id = (String) SPUtil.get(this, "ids", "line_id", "");
        line_name = (String) SPUtil.get(this, "ids", "line_name", "");
        tower_name = (String) SPUtil.get(this, "ids", "tower_name", "");
        ProgressDialog.show(this,false,"正在上传。。。");
        if (isPatrol) {
            commitPatrol(task_id);
        } else {
            commitSpecial(task_id);
        }

    }

    private void commitPatrol(String task_id) {

        params.put("patrol_id", toRequestBody(patrol_id));
        params.put("category_id", toRequestBody(results.get(position1).getValue().get(position2).getCATEGORY()));
        params.put("grade_id", toRequestBody(results.get(position1).getValue().get(position2).getGRADE()));
        params.put("content", toRequestBody(etContent.getText().toString().trim()));
        params.put("start_id", toRequestBody(tower_id));
        params.put("end_id", toRequestBody(tower_id));
        params.put("line_id", toRequestBody(line_id));
        params.put("task_id", toRequestBody(task_id));

        params.put("line_name", toRequestBody(line_name));
        params.put("start_name", toRequestBody(tower_name));
        params.put("end_name", toRequestBody(tower_name));
        BaseRequest.getInstance().getService().commitPatrolContent(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(CommitDefectActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                        RxRefreshEvent.publish("updateDefect@2");
                        finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(CommitDefectActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void commitSpecial(String task_id) {
        params.put("patrol_id", toRequestBody("root"));
        params.put("category_id", toRequestBody("root"));
        params.put("grade_id", toRequestBody("root"));
        params.put("content", toRequestBody(etContent.getText().toString().trim()));
        params.put("start_id", toRequestBody(tower_id));
        params.put("end_id", toRequestBody(tower_id));
        params.put("line_id", toRequestBody(line_id));
        params.put("task_id", toRequestBody(task_id));

        params.put("line_name", toRequestBody(line_name));
        params.put("start_name", toRequestBody(tower_name));
        params.put("end_name", toRequestBody(tower_name));
        BaseRequest.getInstance().getService().commitTrouble(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(this) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(CommitDefectActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                        RxRefreshEvent.publish("updateSpecial@3");
                        finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(CommitDefectActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
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
}
