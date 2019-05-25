package com.patrol.terminal.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.CommitDefectActivity;
import com.patrol.terminal.bean.PatrolLevel1;
import com.patrol.terminal.bean.PatrolLevel2;

import java.util.ArrayList;
import java.util.List;

public class PatrolContentAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAddImgAdapter;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    public PatrolContentAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_1, R.layout.item_patrol_content_1);
        addItemType(TYPE_2, R.layout.item_patrol_content_2);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_1:
                PatrolLevel1 item1 = (PatrolLevel1) item;
                helper.setText(R.id.tv_title, item1.getTitle()).setImageResource(R.id.iv_expand, item1.isExpanded() ? R.mipmap.next : R.mipmap.btn_down);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();

                        if (item1.isExpanded()) {
                            collapse(pos);
                        } else {
                            for (int i = 0; i < mData.size(); i++) {
                                collapse(i);
                            }
                            expand(pos);
//      }
                        }
                    }
                });
                break;
            case TYPE_2:
                PatrolLevel2 item2 = (PatrolLevel2) item;
                helper.setText(R.id.tv_content, item2.getContent()).setText(R.id.tv_tag, item2.isTag() ? "√" : "×");
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item2.isTag()) {
                            Intent intent = new Intent(mContext, CommitDefectActivity.class);
                            intent.putExtra("isPatrol", true);
                            intent.putExtra("patrol_id", item2.getId());
                            intent.putExtra("category", item2.getCategory());
                            intent.putExtra("name", item2.getName());
                            intent.putExtra("content", item2.getContent());
                            mContext.startActivity(intent);
                        }
                    }
                });
                break;
        }
    }

}
