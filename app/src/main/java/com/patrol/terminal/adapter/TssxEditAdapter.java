package com.patrol.terminal.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.TSSXBean;
import com.patrol.terminal.fragment.SpecialTSSXFrgment;
import com.patrol.terminal.sqlite.MyOpenhelper;
import com.patrol.terminal.utils.Constant;
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
    //当前特殊属性集合
    private List<TSSXBean> tssxList = new ArrayList<TSSXBean>();
    private ViewHolder holder;
    private onClickAdapter clickAdapter;
    private SpecialTSSXFrgment fragment;
    private TssxPhotoAdapter photoAdapter;

    public TssxEditAdapter(Context context, SpecialTSSXFrgment fragment) {
        this.context = context;
        this.fragment = fragment;
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

        holder.tssx_edit.setOnItemClick(new SankuaEditView.onTssxClick() {
            @Override
            public void clickTitleDel() {

                CancelOrOkDialog dialog = new CancelOrOkDialog(context, "是否删除？", "取消", "删除") {
                    @Override
                    public void ok() {
                        super.ok();
                        clickAdapter.delItemObject(tssxList.get(position));
                        tssxList.remove(position);
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

            @Override
            public void onAutoItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor1 = clickAdapter.getCursorAdapter().getCursor();
                if (cursor1 != null && cursor1.getCount() > 0) {
                    boolean isExist = cursor1.moveToPosition(position);
                    if (isExist) {
                        String levelStr = cursor1.getString(clickAdapter.getCursor().getColumnIndex(MyOpenhelper.DefactTvColumns.LEVEL));
                        Log.w("linmeng", "levelStr:" + levelStr);   //这里获取的是缺陷等级，给陈飞用！  TODO
                        holder.tssx_edit.setDjStatus(levelStr);
                    }
                }
            }

            @Override
            public void addTextChangedListener(String txt) {

                tssxList.get(position).setYhnr(txt);
                clickAdapter.updateYhnr(tssxList.get(position).getKey(),txt);
            }

            @Override
            public void getDj(String djStr) {
                tssxList.get(position).setDj(djStr);
                clickAdapter.updateDJ(tssxList.get(position).getKey(),djStr);
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("position getChildCount",position+"====="+parent.getChildCount()+"===="+i);//"position  parent getChildCount"
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
                    if (tssxList.size() == Constant.MAX_SELECT_PIC_NUM) {
                        //查看大图
                    } else {
                        //添加凭证图片
                        String oldphoto = "";
                        Boolean isAdd = false;
                        if(i == 0 && tssxList.get(position).getPhotoList().size() == 0){
                            isAdd = true;
                        }else{
                            if (i == tssxList.get(position).getPhotoList().size()){
                                isAdd = true;
                            }else{
                                isAdd = false;
                                oldphoto = tssxList.get(position).getPhotoList().get(i);
                            }
                        }
                        clickAdapter.updatePhoto(tssxList.get(position).getKey(),position,i,oldphoto,isAdd);
                        startCamera();
                    }
            }

            @Override
            public void delListPhoto(String photoStr) {
                clickAdapter.delTssxPhoto(tssxList.get(position).getKey(),photoStr);
                notifyDataSetChanged();
            }
        });

        holder.tssx_edit.setAutoAdapter(clickAdapter.getCursorAdapter());

        TSSXBean tssxBean = tssxList.get(position);
        holder.tssx_edit.setItemTilte(tssxBean.getValues());
        holder.tssx_edit.setItemYhnr(tssxBean.getYhnr());

        Log.e("当前等级状态",tssxBean.getDj());
        if(tssxBean.getDj().equals("一般")){
            holder.tssx_edit.setDjStatus("一般");
        }else{
            holder.tssx_edit.setDjStatus(tssxBean.getDj());
        }

        photoAdapter = new TssxPhotoAdapter(context,tssxBean.getPhotoList());
        holder.tssx_edit.setPhotoAdapter(photoAdapter);

        //不可编辑
        if (Constant.patrol_record_audit_status.equals("1") || Constant.patrol_record_audit_status.equals("2")) {
            holder.tssx_edit.disableView(false);
        } else {
            holder.tssx_edit.disableView(true);
        }

        return convertView;
    }

    /**
     * 设置隐患等级
     */
    public String setDjIdStatus(String djid) {
        String YHDJStr = "";
        if (djid.equals(Constant.DJ_YB)) {
            YHDJStr = "一般";
        } else if (djid.equals(Constant.DJ_YZ)) {
            YHDJStr = "严重";
        } else if (djid.equals(Constant.DJ_WJ)) {
            YHDJStr = "危急";
        }
        return YHDJStr;
    }

    /**
     * 更新照片
     * @param photoStr
     */
    public void setPhotoData(String photoStr,int parIndex,int position)
    {
        Log.e("setPhotoData",parIndex+"=="+position);
        if(position == tssxList.get(parIndex).getPhotoList().size()){
            tssxList.get(parIndex).getPhotoList().add(photoStr);
        }else{
            tssxList.get(parIndex).getPhotoList().set(position,photoStr);
        }

        holder.tssx_edit.setNotifyDataSetChanged(tssxList.get(parIndex).getPhotoList());
        notifyDataSetChanged();
    }

    //打开相机
    private void startCamera() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fragment.startActivityForResult(intent, Constant.DEFECT_REQUEST_CODE);
    }

    public void setData(List<TSSXBean> typeBeanList) {
        if(typeBeanList != null){
            tssxList = typeBeanList;
            notifyDataSetChanged();
        }
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
        void delItemObject(TSSXBean bean);
        AutoCursorAdapter getCursorAdapter();
        Cursor getCursor();
        void updateYhnr(String task_key,String yhnr);//task_key  屬性key
        void updateDJ(String task_key,String dj);//task_key  屬性key
        void updatePhoto(String task_key,int index,int position,String oldPhoto,boolean isAdd);//更新adapter index  item的position
        void delTssxPhoto(String key,String photoStr);
    }

}