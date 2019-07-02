package com.patrol.terminal.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.TSSXBean;
import com.patrol.terminal.sqlite.DefactContentDBHelper;
import com.patrol.terminal.sqlite.MyOpenhelper;
import com.patrol.terminal.widget.CancelOrOkDialog;
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
    private ViewHolder holder;
    private onClickAdapter clickAdapter;

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

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tssx_edititem, parent, false);
            holder.tssx_edit = convertView.findViewById(R.id.item_tssxedit);
            convertView.setTag(holder);
        }

        holder.tssx_edit.setItemTilte(tssxList.get(position).getValues());

        holder.tssx_edit.setOnItemClick(new SankuaEditView.onTssxClick() {
            @Override
            public void clickDel() {

                CancelOrOkDialog dialog = new CancelOrOkDialog(context, "是否删除？", "取消", "删除") {
                    @Override
                    public void ok() {
                        super.ok();
                        tssxList.remove(position);
                        clickAdapter.delObject(tssxList.get(position-1));
                        notifyDataSetChanged();
                         dismiss();
                    }
                    @Override
                    public void cancle() {
                        super.cancle();
                        dismiss();
                    }
                };
                dialog.show();
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
        Log.e("getCheckList",tssxList.size()+"");
            return tssxList;
    }


    public void setData(List<TSSXBean> typeBeanList,CursorAdapter cursorAdapter,Cursor cursor) {

        if(typeBeanList != null){
            Log.e("getCheckList",tssxList.size()+"");
            tssxList = typeBeanList;
            notifyDataSetChanged();
        }

//        holder.tssx_edit.setAutoAdapter(cursorAdapter,cursor);

    }

    class ViewHolder {
        private SankuaEditView tssx_edit;

    }

    public void setOnclickAdapter(onClickAdapter clickAdapter)
    {
        this.clickAdapter = clickAdapter;
    }

    public interface onClickAdapter
    {
        void delObject(TSSXBean bean);
    }

}