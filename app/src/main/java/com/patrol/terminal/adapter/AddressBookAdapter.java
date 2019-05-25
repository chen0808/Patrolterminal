package com.patrol.terminal.adapter;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.AddressBookLevel2;

import java.util.List;

public class AddressBookAdapter extends BaseQuickAdapter<AddressBookLevel2, BaseViewHolder> {

    public AddressBookAdapter(int layoutResId, @Nullable List<AddressBookLevel2> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBookLevel2 item) {
        helper.setText(R.id.tv_content, item.getContent()).setText(R.id.tv_content_bz, item.getContentBz())
                .setText(R.id.tv_content_job, item.getContentJob()).setChecked(R.id.check_box, item.isTag());

        CheckBox checkBox = helper.getView(R.id.check_box);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setTag(isChecked);
                item.setPosition(helper.getAdapterPosition());
            }
        });
    }
}
