package com.patrol.terminal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.TSSXBean;
import com.patrol.terminal.widget.SankuaEditView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 特殊属性 通用item
 */
public class TssxEditAdapter extends BaseAdapter {

    @BindView(R.id.item_tssxedit)
    SankuaEditView item_tssxedit;

    private Context context;
    private List<TSSXBean> tssxList = new ArrayList<TSSXBean>();

    public TssxEditAdapter(Context context) {
        this.context = context;
    }

    public TssxEditAdapter(Context context, List<TSSXBean> list) {
        this.context = context;
        this.tssxList = list;
    }

    @Override
    public int getCount() {
        if(tssxList != null){
            return tssxList.size();
        }else{
            return 0;
        }

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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tssx_edititem, parent, false);
            holder.tssx_edit = convertView.findViewById(R.id.item_tssxedit);
            convertView.setTag(holder);
        }

        holder.tssx_edit.setItemTilte(tssxList.get(position).getValues());

        return convertView;
    }

    /**
     *返回选中项
     * @return
     */
    public List<TSSXBean> getCheckList()
    {
        Log.e("getCheckList",tssxList.size()+"");
//        if(tssxList == null)
//            return null;
//        else
            return tssxList;
    }


    public void setData(List<TSSXBean> typeBeanList) {

        if(typeBeanList != null){
            Log.e("getCheckList",tssxList.size()+"");
            tssxList.clear();
            tssxList.addAll(typeBeanList);
            notifyDataSetChanged();
        }else{
            Log.e("getCheckList","00000000000000");
        }

    }

    class ViewHolder {
        private SankuaEditView tssx_edit;

    }
}