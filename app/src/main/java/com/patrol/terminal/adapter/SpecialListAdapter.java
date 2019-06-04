package com.patrol.terminal.adapter;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.AddSpecial;
import com.patrol.terminal.bean.PatrolLevel1;
import com.patrol.terminal.bean.PatrolLevel2;
import com.patrol.terminal.bean.PatrolLevel3;
import com.patrol.terminal.bean.PatrolLevel4;
import com.patrol.terminal.bean.SpecialAttrList;

import java.util.ArrayList;
import java.util.List;

public class SpecialListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    public static final int TYPE_3 = 3;
    public static final int TYPE_4 = 4;
    private final AddSpecial addSpecial;
    private List<SpecialAttrList> waresId = new ArrayList<>();

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    public SpecialListAdapter(List<MultiItemEntity> data, AddSpecial addSpecial) {
        super(data);
        addItemType(TYPE_1, R.layout.item_sepcial_attr_1);
        addItemType(TYPE_2, R.layout.item_sepcial_attr_2);
        addItemType(TYPE_3, R.layout.item_sepcial_attr_3);
        addItemType(TYPE_4, R.layout.item_sepcial_attr_4);
        this.addSpecial = addSpecial;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_1:
                PatrolLevel1 item1 = (PatrolLevel1) item;
                helper.setText(R.id.tv_title, item1.getTitle());
                if (item1.getSubItems() == null || item1.getSubItems().size() == 0) {
                    EditText etRemark = helper.getView(R.id.et_remark);
                    helper.getView(R.id.iv_expand).setVisibility(View.GONE);
                    helper.getView(R.id.cb_tag).setVisibility(View.VISIBLE);
                    helper.getView(R.id.et_remark).setVisibility(View.VISIBLE);
                    helper.setOnCheckedChangeListener(R.id.cb_tag, null);
                    helper.setChecked(R.id.cb_tag, item1.getStatus().equals("1"));
                    helper.setOnCheckedChangeListener(R.id.cb_tag, new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            SpecialAttrList list = new SpecialAttrList(item1.getId(), etRemark.getText().toString());
                            if (isChecked) {
                                waresId.add(list);
                            } else {
                                waresId.remove(list);
                            }
                            item1.setStatus(isChecked ? "1" : "2");
                            addSpecial.setWaresIdList(waresId);
                        }
                    });
                } else {
                    helper.getView(R.id.iv_expand).setVisibility(View.VISIBLE);
                    helper.getView(R.id.cb_tag).setVisibility(View.GONE);
                    helper.getView(R.id.et_remark).setVisibility(View.GONE);
                    helper.setImageResource(R.id.iv_expand, item1.isExpanded() ? R.mipmap.next : R.mipmap.btn_down);
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = helper.getAdapterPosition();
                            if (item1.isExpanded()) {
                                collapse(pos);
                            } else {
                                expand(pos);
//      }
                            }
                        }
                    });
                }
                break;
            case TYPE_2:
                PatrolLevel2 item2 = (PatrolLevel2) item;
                helper.setText(R.id.tv_title, item2.getName());
                if (item2.getSubItems() == null || item2.getSubItems().size() == 0) {
                    EditText etRemark = helper.getView(R.id.et_remark);
                    helper.getView(R.id.iv_expand).setVisibility(View.GONE);
                    helper.getView(R.id.cb_tag).setVisibility(View.VISIBLE);
                    helper.getView(R.id.et_remark).setVisibility(View.VISIBLE);
                    helper.setOnCheckedChangeListener(R.id.cb_tag, null);
                    helper.setChecked(R.id.cb_tag, item2.equals("1"));
                    helper.setOnCheckedChangeListener(R.id.cb_tag, new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            SpecialAttrList list = new SpecialAttrList(item2.getId(), etRemark.getText().toString());
                            if (isChecked) {
                                waresId.add(list);
                            } else {
                                waresId.remove(list);
                            }
                            item2.setStatus(isChecked ? "1" : "2");
                            addSpecial.setWaresIdList(waresId);
                        }
                    });
                } else {
                    helper.getView(R.id.iv_expand).setVisibility(View.VISIBLE);
                    helper.getView(R.id.cb_tag).setVisibility(View.GONE);
                    helper.getView(R.id.et_remark).setVisibility(View.GONE);
                    helper.setImageResource(R.id.iv_expand, item2.isExpanded() ? R.mipmap.next : R.mipmap.btn_down);
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = helper.getAdapterPosition();
                            if (item2.isExpanded()) {
                                collapse(pos);
                            } else {
                                expand(pos);
//      }
                            }
                        }
                    });
                }
                break;
            case TYPE_3:
                PatrolLevel3 item3 = (PatrolLevel3) item;
                helper.setText(R.id.tv_title, item3.getName());
                if (item3.getSubItems() == null || item3.getSubItems().size() == 0) {
                    EditText etRemark = helper.getView(R.id.et_remark);
                    helper.getView(R.id.iv_expand).setVisibility(View.GONE);
                    helper.getView(R.id.cb_tag).setVisibility(View.VISIBLE);
                    helper.getView(R.id.et_remark).setVisibility(View.VISIBLE);
                    helper.setOnCheckedChangeListener(R.id.cb_tag, null);
                    helper.setChecked(R.id.cb_tag, item3.isTag());
                    helper.setOnCheckedChangeListener(R.id.cb_tag, new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            SpecialAttrList list = new SpecialAttrList(item3.getId(), etRemark.getText().toString());
                            if (isChecked) {
                                waresId.add(list);
                            } else {
                                waresId.remove(list);
                            }
                            item3.setTag(isChecked);
                            addSpecial.setWaresIdList(waresId);
                        }
                    });
                } else {
                    helper.getView(R.id.iv_expand).setVisibility(View.VISIBLE);
                    helper.getView(R.id.cb_tag).setVisibility(View.GONE);
                    helper.getView(R.id.et_remark).setVisibility(View.GONE);
                    helper.setImageResource(R.id.iv_expand, item3.isExpanded() ? R.mipmap.next : R.mipmap.btn_down);
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = helper.getAdapterPosition();
                            if (item3.isExpanded()) {
                                collapse(pos);
                            } else {
                                expand(pos);
//      }
                            }
                        }
                    });
                }
                break;
            case TYPE_4:
                PatrolLevel4 item4 = (PatrolLevel4) item;
                helper.setText(R.id.tv_content, item4.getName());
                helper.getView(R.id.et_remark).setVisibility(View.VISIBLE);
                EditText etRemark = helper.getView(R.id.et_remark);
                helper.setOnCheckedChangeListener(R.id.cb_tag, null);
                helper.setChecked(R.id.cb_tag, item4.isTag());
                helper.setOnCheckedChangeListener(R.id.cb_tag, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        SpecialAttrList list = new SpecialAttrList(item4.getId(), etRemark.getText().toString());
                        if (isChecked) {
                            waresId.add(list);
                        } else {
                            waresId.remove(list);
                        }
                        item4.setTag(isChecked);
                        addSpecial.setWaresIdList(waresId);
                    }
                });
//                helper.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(mContext, CommitDefectActivity.class);
//                        intent.putExtra("isPatrol", false);
//                        intent.putExtra("name", item4.getName());
//                        intent.putExtra("category", item4.getCategory());
////                        intent.putExtra("listLevel3", (Serializable) listLevel3);
////                        intent.putExtra("listLevel4", (Serializable) listLevel4);
//                        intent.putExtra("id", item4.getId());
//                        mContext.startActivity(intent);
//                    }
//                });
                break;
        }
    }
}
