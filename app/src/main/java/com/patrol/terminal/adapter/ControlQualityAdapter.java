package com.patrol.terminal.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.ControlQualityInfo;

import java.util.List;

import androidx.annotation.Nullable;

public class ControlQualityAdapter extends BaseQuickAdapter<ControlQualityInfo, BaseViewHolder> {
    
    private boolean isCanClick;
    public ControlQualityAdapter(int layoutResId, @Nullable List<ControlQualityInfo> data,boolean isCanClick) {
        super(layoutResId, data);
        this.isCanClick=isCanClick;
    }


    @Override
    protected void convert(BaseViewHolder holder, ControlQualityInfo item) {

        holder.setText(R.id.divison_no,"" + item.getDivisonNo());
        holder.setText(R.id.divison_key,item.getKeyDivison());
        holder.setText(R.id.divison_demand,item.getContent());
        holder.setText(R.id.divison_risk,item.getSafeDivison());
        holder.setText(R.id.divison_inspections,item.getCheckInfo());
        TextView mCheckInfo = holder.getView(R.id.divison_inspections);
        if (!isCanClick) {
            mCheckInfo.setClickable(false);
        }else {
            mCheckInfo.setClickable(true);
            mCheckInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //网络请求获取工作人员  TODO
                    String[] workers = new String[]{"合格", "不合格"};
                    showSingleChooseDialog(mContext, "检查情况", workers, holder, holder.getPosition(),item);

                }
            });

            mCheckInfo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    item.setCheckInfo(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
    public void showSingleChooseDialog(Context context, String title, String[] workers, BaseViewHolder holder, int position, ControlQualityInfo item) {
        new AlertDialog.Builder(context).setTitle(title).setItems(workers, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context, "您已经选择了: " + which + ":" + workers[which],Toast.LENGTH_LONG).show();
               holder.setText(R.id.divison_inspections,workers[which]);
                item.setCheckInfo(workers[which]);
                dialog.dismiss();
            }
        }).show();
    }
}