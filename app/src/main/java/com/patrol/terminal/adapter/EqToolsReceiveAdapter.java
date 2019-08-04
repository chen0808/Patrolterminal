package com.patrol.terminal.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.EqToolsBean;

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
//            private CharSequence temp;
//            private int editStart ;
//            private int editEnd ;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                ed.setText(s + "");
            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = ed.getText().toString().trim();
                if(!TextUtils.isEmpty(trim) && !trim.equals("0")) {
//                    editStart = ed.getSelectionStart();
//                    editEnd = ed.getSelectionEnd();
//
//                    Pattern p = Pattern.compile("^(100|[1-9]\\d|\\d)$");//处理0~100正则
//
//                    Matcher m =p.matcher(s.toString());
//                    if(m.find() || ("").equals(s.toString())){
//                        Log.i("yinyanhua", "OK!");
//                    }else{
//                        Log.i("yinyanhua", "请输入正确的数值比例!");
//                        s.delete(editStart-1, editEnd);
//                        int tempSelection = editStart;
//                        Log.i("yinyanhua", "11111111");
//                        ed.setText(s.toString());
//                        ed.setSelection(tempSelection);
//                    }

                    item.setTotal(Integer.parseInt(trim));
                    viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.base_status_bar));
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