package com.patrol.terminal.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.CommitDefectActivity;
import com.patrol.terminal.activity.PlusImageActivity;
import com.patrol.terminal.bean.PatrolLevel1;
import com.patrol.terminal.bean.PatrolLevel2;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.PictureSelectorConfig;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatrolContentAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    private final Activity activity;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter2 mGridViewAddImgAdapter;
    private String[] strings = {"否", "是"};
    private PatrolLevel1 mItem1;
    private String[] nsType = {"危急", "严重", "一般"};

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
                    helper.setVisible(R.id.ll_content, true);
                    initGridView(gridView);
                } else {
                    helper.setImageResource(R.id.iv_check, R.mipmap.patrol_true);
                    helper.setVisible(R.id.ll_content, false);
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
}
