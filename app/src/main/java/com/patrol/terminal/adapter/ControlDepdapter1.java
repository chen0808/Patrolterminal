package com.patrol.terminal.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.CardControlProject;
import com.patrol.terminal.bean.SelectWorkerBean;

import java.util.List;

public class ControlDepdapter1 extends BaseAdapter {
    private Context context;
    private List<CardControlProject> list;
    private boolean isCanClick;
    private List<SelectWorkerBean.SelectUserInfo> mWorkerSelectList;

    public ControlDepdapter1(Context context, List<CardControlProject> traceList, boolean isCanClick, List<SelectWorkerBean.SelectUserInfo> workerSelectList) {
        this.context = context;
        this.list = traceList;
        this.isCanClick = isCanClick;
        this.mWorkerSelectList = workerSelectList;
    }

    @Override
    public int getCount() {
        return list.size();
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

            convertView = LayoutInflater.from(context).inflate(R.layout.item_control_card_division, parent, false);
            holder.mDivisonName = convertView.findViewById(R.id.divison_name);
            holder.mDivisonNo = convertView.findViewById(R.id.divison_no);
            holder.mDivisonContent = convertView.findViewById(R.id.divison_content);

            if (list != null && list.size() > 0) {
                if (list.get(position) != null) {
                    holder.mDivisonNo.setText("" + list.get(position).getDivisonNo());
                    holder.mDivisonContent.setText(list.get(position).getContent());
                    holder.mDivisonName.setText(list.get(position).getUser_name());
                }
            }

            if (!isCanClick) {  //是查看模式
                holder.mDivisonName.setClickable(false);
            } else {
                holder.mDivisonName.setClickable(true);
                holder.mDivisonName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //网络请求获取工作人员  TODO
                        if (position != 0) {  //第一个不可修改
                            getAllSendToPerson(holder, position);
                        }
                    }
                });
            }


            convertView.setTag(holder);
        }

        return convertView;
    }

    public void setData(List<CardControlProject> typeBeanList) {
        list = typeBeanList;
        notifyDataSetChanged();
    }

    public List<CardControlProject> getData() {
        return list;
    }

    static class ViewHolder {
        private TextView mDivisonNo;
        private TextView mDivisonContent;
        private TextView mDivisonName;
    }

    public void showSingleChooseDialog(Context context, String title, String[] workers, String[] workers_id, ViewHolder holder, int position) {
        new AlertDialog.Builder(context).setTitle(title).setItems(workers, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context, "您已经选择了: " + which + ":" + workers[which],Toast.LENGTH_LONG).show();
                holder.mDivisonName.setText(workers[which]);
                list.get(position).setUser_name(workers[which]);
                list.get(position).setUser_id(workers_id[which]);
                dialog.dismiss();
            }
        }).show();
    }

    private void getAllSendToPerson(ViewHolder holder, int position) {


        if (mWorkerSelectList != null && mWorkerSelectList.size() > 0) {
            String[] workers = new String[mWorkerSelectList.size()];
            String[] workers_id = new String[mWorkerSelectList.size()];

            for (int i = 0; i < mWorkerSelectList.size(); i++) {
                workers[i] = mWorkerSelectList.get(i).getUserName();
                workers_id[i] = mWorkerSelectList.get(i).getUserId();
            }

            showSingleChooseDialog(context, "工作人员", workers, workers_id, holder, position);
        }

    }

}