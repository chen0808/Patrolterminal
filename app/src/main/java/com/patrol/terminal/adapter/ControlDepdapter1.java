package com.patrol.terminal.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.ClassMemberBean;
import com.patrol.terminal.bean.ControlDepWorkInfo;
import com.patrol.terminal.bean.OverhaulSendUserBean;
import com.patrol.terminal.overhaul.OverhaulMonitorPublishActivity;
import com.patrol.terminal.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ControlDepdapter1 extends BaseAdapter {
    private Context context;
    private List<ControlDepWorkInfo> list;
    private boolean isCanClick;

    public ControlDepdapter1(Context context, List<ControlDepWorkInfo> traceList, boolean isCanClick) {
        this.context = context;
        this.list = traceList;
        this.isCanClick = isCanClick;
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
                    holder.mDivisonName.setText(list.get(position).getDivisonName());
                }
            }

            if (!isCanClick) {  //是查看模式
                holder.mDivisonName.setClickable(false);
            }else {
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

    public void setData(List<ControlDepWorkInfo> typeBeanList) {
        list = typeBeanList;
        notifyDataSetChanged();
    }


    static class ViewHolder {
        private TextView mDivisonNo;
        private TextView mDivisonContent;
        private TextView mDivisonName;
    }

    public void showSingleChooseDialog(Context context, String title, String[] workers, String[] workers_id,  ViewHolder holder, int position) {
        new AlertDialog.Builder(context).setTitle(title).setItems(workers, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context, "您已经选择了: " + which + ":" + workers[which],Toast.LENGTH_LONG).show();
                holder.mDivisonName.setText(workers[which]);
                list.get(position).setDivisonName(workers[which]);
                list.get(position).setUserId(workers_id[which]);
                dialog.dismiss();
            }
        }).show();
    }

    private void getAllSendToPerson(ViewHolder holder, int position) {
        BaseRequest.getInstance().getService()
                .getAllClassMember("B7FF21A674F144DE8D13EB8B3B79E64F")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ClassMemberBean>>(context) {
                    @Override
                    protected void onSuccees(BaseResult<List<ClassMemberBean>> t) throws Exception {
                        if(t.getCode() == 1) {
                            if (t.getResults() != null && t.getResults().size() > 0) {
                                List<ClassMemberBean.UserListBean> userListBeans = t.getResults().get(0).getUserList();
                                String[] workers = new String[userListBeans.size()];
                                String[] workers_id = new String[userListBeans.size()];
                                for (int i = 0; i < userListBeans.size(); i++) {
                                    String userName = userListBeans.get(i).getName();
                                    String userId = userListBeans.get(i).getId();
                                    workers[i] = userName;
                                    workers_id[i] = userId;
                                }

                                showSingleChooseDialog(context, "工作人员", workers, workers_id, holder, position);
                            }
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });

    }

}