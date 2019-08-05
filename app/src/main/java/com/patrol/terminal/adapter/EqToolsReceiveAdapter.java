package com.patrol.terminal.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.EqToolsBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EqToolsReceiveAdapter extends BaseQuickAdapter<EqToolsBean, BaseViewHolder> {
    private Context mContext;

    public EqToolsReceiveAdapter(int layoutResId, Context mContext) {
        super(layoutResId);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, EqToolsBean item) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        viewHolder.setText(R.id.divison_no, (item.getNumber()+1) + "")
                .setText(R.id.divison_name, item.getName())
                .setText(R.id.divison_model, item.getType())
                .setText(R.id.divison_unit, item.getUnit())
                .setText(R.id.divison_num, item.getInventory() + "")
                .setText(R.id.divison_brand, item.getBrand());

        if(item.getInventory() != null){
            viewHolder.setText(R.id.divison_num, item.getInventory() + "");
        } else {
            viewHolder.setText(R.id.divison_num, "0");
        }

        EditText ed = viewHolder.getView(R.id.divison_remarks);
        if (ed.getTag() instanceof TextWatcher) {
            ed.removeTextChangedListener((TextWatcher) ed.getTag());
        }

        if(item.getTotal() == null || item.getTotal() == 0){
            viewHolder.setText(R.id.divison_remarks, "0");
            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            viewHolder.setText(R.id.divison_remarks, item.getTotal().toString());
            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.base_status_bar));
        }

        TextWatcher watcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = ed.getText().toString().trim();
                if(!TextUtils.isEmpty(trim) && !trim.equals("0")) {
                    Pattern p = Pattern.compile("^[1-9]\\d*$");

                    Matcher m = p.matcher(s.toString());
                    if(m.find() || ("").equals(s.toString())){
                        if(item.getInventory() != null){
                            if(Integer.parseInt(trim) > item.getInventory()){
                                int selEndIndex = Selection.getSelectionEnd(s);
                                //截取新字符串
                                String newStr = trim.substring(0, trim.length()-1);
                                ed.setText(newStr);
                                s = ed.getText();
                                //新字符串长度
                                int newLen = s.length();
                                //旧光标位置超过字符串长度
                                if(selEndIndex > newLen){
                                    selEndIndex = s.length();
                                }
                                //设置新的光标所在位置
                                Selection.setSelection(s, selEndIndex);

                                String temp = ed.getText().toString().trim();
                                if(!TextUtils.isEmpty(temp) && !temp.equals("0")){
                                    item.setTotal(Integer.parseInt(temp));
                                    viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.base_status_bar));
                                } else {
                                    item.setTotal(0);
                                    viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                                }
                            } else {
                                item.setTotal(Integer.parseInt(trim));
                                viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.base_status_bar));
                            }
                        } else {
                            if(Integer.parseInt(trim) > 0){
                                int selEndIndex = Selection.getSelectionEnd(s);
                                //截取新字符串
                                String newStr = trim.substring(0, trim.length()-1);
                                ed.setText(newStr);
                                s = ed.getText();
                                //新字符串长度
                                int newLen = s.length();
                                //旧光标位置超过字符串长度
                                if(selEndIndex > newLen){
                                    selEndIndex = s.length();
                                }
                                //设置新的光标所在位置
                                Selection.setSelection(s, selEndIndex);

                                String temp = ed.getText().toString().trim();
                                if(!TextUtils.isEmpty(temp) && !temp.equals("0")){
                                    item.setTotal(Integer.parseInt(temp));
                                    viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.base_status_bar));
                                } else {
                                    item.setTotal(0);
                                    viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                                }
                            }
                        }
                    } else {
                        int selEndIndex = Selection.getSelectionEnd(s);
                        //截取新字符串
                        String newStr = trim.substring(0, trim.length()-1);
                        ed.setText(newStr);
                        s = ed.getText();
                        //新字符串长度
                        int newLen = s.length();
                        //旧光标位置超过字符串长度
                        if(selEndIndex > newLen){
                            selEndIndex = s.length();
                        }
                        //设置新的光标所在位置
                        Selection.setSelection(s, selEndIndex);

                        String temp = ed.getText().toString().trim();
                        if(!TextUtils.isEmpty(temp) && !temp.equals("0")){
                            item.setTotal(Integer.parseInt(temp));
                            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.base_status_bar));
                        } else {
                            item.setTotal(0);
                            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }
                    }
                } else {
                    item.setTotal(0);
                    viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
            }
        };

        ed.addTextChangedListener(watcher);
        ed.setTag(watcher);
    }
}