package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.TSSXBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 特殊属性 添加
 */
public class TssxAddAdapter extends BaseAdapter {
    private Context context;
    private List<TSSXBean> tssxList = new ArrayList<>();

    ViewHolder holder=null;
    private Map<Integer,String> map = new HashMap<>();

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

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tssx_additem, parent, false);
            holder.checkBox = convertView.findViewById(R.id.tssx_checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.checkBox.setText(tssxList.get(position).getValues());


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TSSXBean bean = tssxList.get(position);
                if (b){
                    map.put(position,bean.getValues());
                    bean.setCheck(true);
                }else{
                    map.remove(position);
                    bean.setCheck(false);
                }
            }
        });

        if(map!=null && map.containsKey(position)){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }

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

    public void removeStatus(TSSXBean bean) {
        int index = tssxList.indexOf(bean);
        tssxList.get(index).setCheck(false);

        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            Object key = entry.getKey();
            Object values = entry.getValue();
            if(values.equals(bean.getValues()))
            {
                map.remove(key);
            }
        }
        notifyDataSetChanged();
    }

    class ViewHolder {
        private CheckBox checkBox;
    }
}