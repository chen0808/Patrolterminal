package com.patrol.terminal.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseUrl;
import com.patrol.terminal.bean.TicketFirstEnd;
import com.patrol.terminal.utils.Constant;
import com.patrol.terminal.utils.DateUatil;
import com.patrol.terminal.utils.FileUtil;
import com.patrol.terminal.widget.SignDialog;

import java.io.File;
import java.util.List;

public class LicensingEndAdapter extends BaseAdapter {
    private Context context;
    private List<TicketFirstEnd> mLicensingList;

    public LicensingEndAdapter(Context context, List<TicketFirstEnd> licensingList) {
        this.context = context;
        this.mLicensingList = licensingList;
    }

    @Override
    public int getCount() {
        if (mLicensingList != null) {
            return mLicensingList.size();
        }
        return 0;
    }

    @Override
    public TicketFirstEnd getItem(int position) {
        return mLicensingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_licensing_started, parent, false);
            holder.licensingMethodSpinner = (Spinner) convertView.findViewById(R.id.licensing_method_spinner);
            holder.licensorEt = (EditText) convertView.findViewById(R.id.licensor_et);
            holder.signJobManagerIv = (ImageView) convertView.findViewById(R.id.sign_job_manager_iv);
            holder.timeTv = (TextView) convertView.findViewById(R.id.time_tv);
            holder.timeCb = (CheckBox) convertView.findViewById(R.id.time_checkbox);
            convertView.setTag(holder);
        }

        TicketFirstEnd item = getItem(position);
//        holder.licensingMethodSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                item.setEnd_way(String.valueOf(position));
//            }
//        });
        holder.licensorEt.setText(item.getPermit_user_name());
        holder.licensorEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setPermit_user_name(holder.licensorEt.getText().toString());
            }
        });
        Glide.with(context).load(BaseUrl.BASE_URL + item.getFile_path() + item.getFilename()).into(holder.signJobManagerIv);
        holder.timeTv.setText(item.getEnd_time());
        holder.signJobManagerIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = SignDialog.show(context, holder.signJobManagerIv);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (holder.signJobManagerIv.getDrawable() != null) {
                            File file = SignDialog.saveBitmapFile(((BitmapDrawable) (holder.signJobManagerIv).getDrawable()).getBitmap(), "end");
                            item.setFile(FileUtil.fileToBase64(file));
                        }
                    }
                });
            }
        });

        holder.timeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    holder.timeTv.setText(DateUatil.getCurrTime());
                } else {
                    holder.timeTv.setText(Constant.WORK_TICKET_TIME);
                }
                item.setEnd_time(holder.timeTv.getText().toString());
            }
        });

        return convertView;
    }

    public void setData(List<TicketFirstEnd> licensingStartedList) {
        mLicensingList = licensingStartedList;
        notifyDataSetChanged();

    }


    private static class ViewHolder {
        private Spinner licensingMethodSpinner;
        private EditText licensorEt;
        private ImageView signJobManagerIv;
        private TextView timeTv;
        private CheckBox timeCb;
    }

}
