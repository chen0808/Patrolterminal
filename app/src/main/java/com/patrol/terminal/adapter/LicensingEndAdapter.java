package com.patrol.terminal.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.activity.SignActivity;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.SignBean;
import com.patrol.terminal.bean.TicketFirstEnd;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;

//import org.angmarch.views.OnSpinnerItemSelectedListener;

public class LicensingEndAdapter extends BaseQuickAdapter<TicketFirstEnd, BaseViewHolder> {
    public LicensingEndAdapter(int layoutResId, @Nullable List<TicketFirstEnd> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TicketFirstEnd item) {
        NiceSpinner licensingMethodSpinner = helper.getView(R.id.licensing_method_spinner);
        licensingMethodSpinner.attachDataSource(Arrays.asList(mContext.getResources().getStringArray(R.array.method_of_notification)));
        if (null != item.getEnd_way() && !item.getEnd_way().equals("")) {
            licensingMethodSpinner.setSelectedIndex(Integer.parseInt(item.getEnd_way()));
        }
//        licensingMethodSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
//            @Override
//            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
//                item.setEnd_way(String.valueOf(position));
//            }
//        });

        EditText etLicensor = helper.getView(R.id.licensor_et);
        etLicensor.setText(item.getPermit_user_name());
        etLicensor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setPermit_user_name(etLicensor.getText().toString());
            }
        });

        ImageView iv = helper.getView(R.id.sign_job_manager_iv);

        if (SignBean.getIndex() - 200 == helper.getAdapterPosition()) {
            String file = item.getFile();
            byte[] decode = Base64.decode(file, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
            iv.setImageBitmap(bitmap);
            SignBean.setIndex(0);
        } else {
            if (item.getFile_path() != null && item.getFilename() != null) {
                Glide.with(mContext).load(BaseUrl.BASE_URL + item.getFile_path() + item.getFilename()).into(iv);
            }
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, SignActivity.class);
                mContext.startActivity(intent);
                SignBean.setIndex(200 + helper.getAdapterPosition());
            }
        });

        CheckBox cbTime = helper.getView(R.id.time_checkbox);
        TextView tvTime = helper.getView(R.id.time_tv);
        tvTime.setText(item.getEnd_time());
        if (!item.getEnd_time().equals(Constant.WORK_TICKET_TIME)) {
            cbTime.setVisibility(View.GONE);
        }
        cbTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tvTime.setText(DateUatil.getCurrTime());
                } else {
                    tvTime.setText(Constant.WORK_TICKET_TIME);
                }
                item.setEnd_time(tvTime.getText().toString());
            }
        });
    }
}
