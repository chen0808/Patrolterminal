package com.patrol.terminal.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.luck.picture.lib.entity.LocalMedia;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.PatrolLevel1;
import com.patrol.terminal.bean.PatrolLevel2;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.PictureSelectorConfig;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.SPUtil;
import com.patrol.terminal.widget.ProgressDialog;

import org.angmarch.views.NiceSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
    private String[] nsType = {"危急", "严重", "一般"};
    private Map<String, RequestBody> params = new HashMap<>();

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    public PatrolContentAdapter(List<MultiItemEntity> data, Activity activity) {
        super(data);
        this.activity = activity;
        addItemType(TYPE_1, R.layout.item_patrol_content_1);
        addItemType(TYPE_2, R.layout.item_patrol_content_2);
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
                NiceSpinner nsContentType = helper.getView(R.id.ns_content_type);
                nsContentType.attachDataSource(Arrays.asList(nsType));
                GridView gridView = helper.getView(R.id.gridView);
                helper.setText(R.id.tv_content, item2.getContent());
//                helper.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (item2.getStatus().equals("1")) {
//                Intent intent = new Intent(mContext, CommitDefectActivity.class);
//                            intent.putExtra("isPatrol", true);
//                            intent.putExtra("patrol_id", item2.getId());
//                            intent.putExtra("category", item2.getCategory());
//                            intent.putExtra("name", item2.getName());
//                            intent.putExtra("content", item2.getContent());
//                            mContext.startActivity(intent);
//                        }
//                    }
//                });
                if (item2.getStatus().equals("0")) {
                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_undefined);
                } else if (item2.getStatus().equals("1")) {
                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_false);
                    helper.setGone(R.id.ll_content, true);
                    initGridView(gridView);
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
                EditText etContent = helper.getView(R.id.et_content);
                ImageView ivCommit = helper.getView(R.id.iv_commit);
                ivCommit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadItem(item2, etContent.getText().toString());
                    }
                });
                break;
        }
    }

    private void initGridView(GridView gridView) {
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
                        //添加凭证图片
                        selectPic(Constant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                } else {
//                    viewPluImg(position);
                }
            }
        });
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

    private void uploadItem(PatrolLevel2 item2, String content) {
        params.put("task_id", toRequestBody(item2.getTask_id()));
        params.put("grade_id", toRequestBody("37E5647975394B1E952DC5D2796C7D73"));
        params.put("content", toRequestBody(content));
        params.put("patrol_id", toRequestBody(item2.getPatrol_id()));
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
            File file = new File(mPicList.get(0));
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            params.put("towerList[0].file", requestFile);
            params.put("file\"; filename=\"" + file.getName(), requestFile);
        }
        BaseRequest.getInstance().getService().commitPatrolContent(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(mContext) {
                    @Override
                    protected void onSuccees(BaseResult t) throws Exception {
                        ProgressDialog.cancle();
                        Toast.makeText(mContext, "上传成功！", Toast.LENGTH_SHORT).show();
                        RxRefreshEvent.publish("updateDefect@2");
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
