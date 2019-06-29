package com.patrol.terminal.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.luck.picture.lib.entity.LocalMedia;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.DefectListActivity;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.DefectTypeBean;
import com.patrol.terminal.bean.PatrolLevel1;
import com.patrol.terminal.bean.PatrolLevel2;
import com.patrol.terminal.bean.TowerListBean;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.PickerUtils;
import com.patrol.terminal.utils.PictureSelectorConfig;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PatrolContentAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    private final Activity activity;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter2 mGridViewAddImgAdapter;
    private String[] strings = {"否", "是"};
    private PatrolLevel1 mItem1;
    private List<String> typeName = new ArrayList<>();
    private List<String> towerNameList = new ArrayList<>();
    private Map<String, RequestBody> params = new HashMap<>();
    private List<DefectTypeBean> defectTypeBeans;
    private String grade_id;
    private List<TowerListBean> towerListBeans;
    private String startTowerId;
    private String startTowerName;
    private String endTowerId;
    private String endTowerName;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    public PatrolContentAdapter(List<MultiItemEntity> data, Activity activity, String line_id) {
        super(data);
        this.activity = activity;
        addItemType(TYPE_1, R.layout.item_patrol_content_1);
        addItemType(TYPE_2, R.layout.item_patrol_content_2);
        getType();
        getLine(line_id);
    }

    private void getType() {
        BaseRequest.getInstance().getService().getDefectType("qxjb").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<DefectTypeBean>>(mContext) {
                    @Override
                    protected void onSuccees(BaseResult<List<DefectTypeBean>> t) throws Exception {
                        defectTypeBeans = t.getResults();
                        typeName.clear();
                        for (int i = 0; i < defectTypeBeans.size(); i++) {
                            typeName.add(defectTypeBeans.get(i).getName());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    private void getLine(String line_id) {
        BaseRequest.getInstance().getService()
                .towerList(line_id, "sort")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<TowerListBean>>(mContext) {

                    @Override
                    protected void onSuccees(BaseResult<List<TowerListBean>> t) throws Exception {
                        ProgressDialog.cancle();
                        towerListBeans = t.getResults();
                        towerNameList.clear();
                        for (int i = 0; i < towerListBeans.size(); i++) {
                            towerNameList.add(towerListBeans.get(i).getName());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                    }

                });
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_1:
                PatrolLevel1 item1 = (PatrolLevel1) item;
                mItem1 = item1;
                helper.setText(R.id.tv_title, item1.getTitle()).setImageResource(R.id.iv_expand, item1.isExpanded() ? R.mipmap.next : R.mipmap.btn_down);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (item1.isExpanded()) {
                            collapse(pos);
                        } else {
//                            for (int i = 0; i < mData.size(); i++) {
//                                collapse(i);
//                            }
                            expand(pos);
//      }
                        }
                    }
                });

                if (item1.getStatus().equals("0")) {
                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_undefined);
                } else if (item1.getStatus().equals("1")) {
                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_false);
                } else {
                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_true);
                }
                List<PatrolLevel2> items = item1.getSubItems();
                int size = items.size();
                int count = 0;
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getStatus().equals("2")) {
                        count++;
                    }
                }
                if (count == size) {
                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_true);
                } else {
                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_undefined);
                }
                ImageView ivCheck = helper.getView(R.id.iv_check);
                ivCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(mContext).setTitle(item1.getTitle()).setItems(strings, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(context, "您已经选择了: " + which + ":" + workers[which],Toast.LENGTH_LONG).show();
                                if (which == 0) {
                                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_false);
                                    item1.setStatus("1");
                                } else {
                                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_true);
                                    item1.setStatus("2");
                                    List<PatrolLevel2> items = item1.getSubItems();
                                    for (int i = 0; i < items.size(); i++) {
                                        items.get(i).setStatus("2");
                                    }
                                }
                                dialog.dismiss();
                                notifyDataSetChanged();
                            }
                        }).show();
                    }
                });
                break;
            case TYPE_2:
                PatrolLevel2 item2 = (PatrolLevel2) item;
                GridView gridView = helper.getView(R.id.gridView);
                helper.setText(R.id.tv_content, item2.getContent());
                if (item2.getStatus().equals("0")) {
                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_undefined);
                } else if (item2.getStatus().equals("1")) {
                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_false);
                    helper.setGone(R.id.ll_content, true);
                    initGridView(gridView, item2);
                } else {
                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_true);
                    helper.setGone(R.id.ll_content, false);
                }
                ImageView ivCheck2 = helper.getView(R.id.iv_check);
                ivCheck2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(mContext).setTitle(item2.getContent()).setItems(strings, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(context, "您已经选择了: " + which + ":" + workers[which],Toast.LENGTH_LONG).show();
                                if (which == 0) {
                                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_false);
                                    item2.setStatus("1");
                                    mItem1.setStatus("1");
                                } else {
                                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_true);
                                    item2.setStatus("2");
                                }
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }).show();
                    }
                });
                RelativeLayout rlContent = helper.getView(R.id.rl_content);
                LinearLayout llContent = helper.getView(R.id.ll_content);
                TextView tvItemContent = helper.getView(R.id.tv_item_content);
                EditText etContent = helper.getView(R.id.et_content);
                TextView tvContentType = helper.getView(R.id.tv_content_type);
                tvContentType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, DefectListActivity.class);
                        intent.putExtra("category", item2.getCategory());
                        mContext.startActivity(intent);
                    }
                });
                Spinner spStart = helper.getView(R.id.sp_start);
                Spinner spEnd = helper.getView(R.id.sp_end);
                TextView tvTime = helper.getView(R.id.tv_time);
                ImageView ivCommit = helper.getView(R.id.iv_commit);
                Spinner spContentType = helper.getView(R.id.sp_content_type);
                spContentType.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, typeName));
                spContentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        grade_id = defectTypeBeans.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spStart.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, towerNameList));
                spEnd.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, towerNameList));
                spStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        startTowerId = towerListBeans.get(position).getId();
                        startTowerName = towerListBeans.get(position).getName();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        endTowerId = towerListBeans.get(position).getId();
                        endTowerName = towerListBeans.get(position).getName();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                tvTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PickerUtils.showDate(mContext, tvTime);
                    }
                });
                ivCommit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadItem(item2, etContent.getText().toString(), tvTime.getText().toString(), rlContent, llContent, tvItemContent);
                    }
                });
                ImageView ivEdit = helper.getView(R.id.iv_edit);
                ivEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rlContent.setVisibility(View.GONE);
                        llContent.setVisibility(View.VISIBLE);
                    }
                });
                break;
        }
    }

    Uri photoUri;
    String filePath;
    private void initGridView(GridView gridView, PatrolLevel2 item2) {
        mGridViewAddImgAdapter = new GridViewAdapter2(mContext, mPicList);
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

                        photoUri = patrUri(item2.getId() + "_" + parent.getChildCount());
                        startCamera(1002, photoUri);

                    }
                } else {
//                    viewPluImg(position);
                }
            }
        });
    }

    //打开相机
    private void startCamera(int requestCode, Uri photoUri) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        activity.startActivityForResult(intent, requestCode);
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

        return FileProvider.getUriForFile(mContext.getApplicationContext(),
                "com.patrol.terminal.fileprovider",
                new File(dir, strPhotoName));
    }

    //查看大图
//    private void viewPluImg(int position) {
//        Intent intent = new Intent(mContext, PlusImageActivity.class);
//        intent.putStringArrayListExtra(Constant.IMG_LIST, mPicList);
//        intent.putExtra(Constant.POSITION, position);
//        activity.startActivityForResult(intent, Constant.REQUEST_CODE_MAIN);
//    }

    /**
     * 打开相册或者照相机选择凭证图片，最多5张
     *
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig2(activity, maxTotal);
    }

    private void uploadItem(PatrolLevel2 item2, String content, String time, RelativeLayout rlContent, LinearLayout llContent, TextView tvContent) {
        params.clear();
        params.put("task_id", toRequestBody(item2.getTask_id()));
        params.put("grade_id", toRequestBody(grade_id));
        params.put("content", toRequestBody(content));
        params.put("patrol_id", toRequestBody(item2.getPatrol_id()));
        params.put("category_id", toRequestBody(item2.getCategory()));
        params.put("find_time", toRequestBody(time));
        params.put("start_id", toRequestBody(startTowerId));
        params.put("start_name", toRequestBody(startTowerName));
        params.put("end_id", toRequestBody(endTowerId));
        params.put("end_name", toRequestBody(endTowerName));
        String line_id = (String) SPUtil.get(mContext, "ids", "line_id", "");
        if (line_id != null || !line_id.equals("")) {
            params.put("line_id", toRequestBody(line_id));
        }
        String line_name = (String) SPUtil.get(mContext, "ids", "line_name", "");
        if (line_name != null || !line_name.equals("")) {
            params.put("line_name", toRequestBody(line_name));
        }
        String find_user_id = (String) SPUtil.get(mContext, "ids", "find_user_id", "");
        if (find_user_id != null || !find_user_id.equals("")) {
            params.put("find_user_id", toRequestBody(find_user_id));
        }
        String find_user_name = (String) SPUtil.get(mContext, "ids", "find_user_name", "");
        if (find_user_name != null || !find_user_name.equals("")) {
            params.put("find_user_name", toRequestBody(find_user_name));
        }
        params.put("towerList[0].id", toRequestBody(item2.getId()));
        //杆塔名从个人任务获取
        String tower_name = (String) SPUtil.get(mContext, "ids", "tower_name", "");
        if (tower_name != null || !tower_name.equals("")) {
            params.put("start_name", toRequestBody(tower_name));
            params.put("towerList[0].name", toRequestBody(tower_name));
        } else {
            params.put("start_name", toRequestBody("#001"));
            params.put("towerList[0].name", toRequestBody("#001"));
        }
        if (mPicList.size() == 0) {
            Toast.makeText(mContext, "请上传图片", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < mPicList.size(); i++) {
                File file = new File(mPicList.get(i));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            params.put("towerList[0].file", requestFile);
                params.put("file\"; filename=\"" + file.getName(), requestFile);
            }
        }
        BaseRequest.getInstance().getService().commitPatrolContent(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(mContext) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(mContext, "上传成功！", Toast.LENGTH_SHORT).show();
//                        RxRefreshEvent.publish("updateDefect@2");
                        rlContent.setVisibility(View.VISIBLE);
                        llContent.setVisibility(View.GONE);
                        tvContent.setText(content);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(mContext, "上传失败", Toast.LENGTH_SHORT).show();
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

    // 处理选择的照片的地址
    public void refreshAdapter(List<LocalMedia> picList) {
        mPicList.clear();
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
    }
}
