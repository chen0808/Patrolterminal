package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.TSSXBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 特殊属性 添加
 */
public class TssxAddAdapter extends BaseAdapter {
    private Context context;
    private List<TSSXBean> tssxList = new ArrayList<>();

    public TssxAddAdapter(Context context, List<TSSXBean> list) {
        this.context = context;
        this.tssxList = list;
    }

    @Override
    public int getCount() {
        return tssxList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tssx_additem, parent, false);
            holder.checkBox = convertView.findViewById(R.id.tssx_checkbox);
            convertView.setTag(holder);
        }


        holder.checkBox.setText(tssxList.get(position).getValues());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TSSXBean bean = tssxList.get(position);
                if(bean.isCheck()){
                    bean.setCheck(false);
                    holder.checkBox.setChecked(false);
                }else{
                    bean.setCheck(true);
                    holder.checkBox.setChecked(true);
                }

            }
        });

        return convertView;
    }

    /**
     *返回选中项
     * @return
     */
    public List<TSSXBean> getCheckList()
    {
        List<TSSXBean> addList = new ArrayList<>();
        int count  = tssxList.size();
        for (int i=0;i<count;i++){
            if(tssxList.get(i).isCheck()){
                addList.add(tssxList.get(i));
            }
        }
        return addList;
    }


    public void setData(List<TSSXBean> typeBeanList) {
        tssxList = typeBeanList;
        notifyDataSetChanged();
    }

    class ViewHolder {
        private RadioButton checkBox;
    }
}