package com.patrol.terminal.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.EqToolsBean;

public class EqToolsAdapter extends BaseQuickAdapter<EqToolsBean, BaseViewHolder> {
    public EqToolsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, EqToolsBean item) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, DefectIngDetailActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putSerializable("id", item.getId());
//                bundle.putInt("type", mType);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
            }
        });

        viewHolder.setText(R.id.divison_no, (item.getNumber()+1) + "")
                .setText(R.id.divison_name, item.getName())
                .setText(R.id.divison_model, item.getType())
                .setText(R.id.divison_unit, item.getUnit())
                .setText(R.id.divison_num, item.getInventory() + "")
                .setText(R.id.divison_brand, item.getBrand())
                .setText(R.id.divison_remarks, item.getRemarks());
    }
}