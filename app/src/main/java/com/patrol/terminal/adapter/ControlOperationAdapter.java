package com.patrol.terminal.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.ControlQualityInfo;

import java.util.List;

public class ControlOperationAdapter extends BaseAdapter {
    private Context context;
    private List<ControlQualityInfo> list;
    private boolean isCanClick;
    public ControlOperationAdapter(Context context, List<ControlQualityInfo> traceList, boolean isCanClick) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_control_quality_division, parent, false);

            holder.mDivisonNo = convertView.findViewById(R.id.divison_no);
            holder.mDivisonKey = convertView.findViewById(R.id.divison_key);
            holder.mContent = convertView.findViewById(R.id.divison_demand);
            holder.mSafeDivison = convertView.findViewById(R.id.divison_risk);
            holder.mCheckInfo = convertView.findViewById(R.id.divison_inspections);
            holder.mItem = convertView.findViewById(R.id.control_card_division_rl);

            if (list != null && list.get(position) != null) {
                holder.mDivisonNo.setText("" + list.get(position).getDivisonNo());
                holder.mDivisonKey.setText(list.get(position).getKeyDivison());
                holder.mContent.setText(list.get(position).getContent());
                holder.mSafeDivison.setText(list.get(position).getSafeDivison());
                holder.mCheckInfo.setText(list.get(position).getCheckInfo());

                if (!isCanClick) {
                    holder.mCheckInfo.setClickable(false);
                }else {
                    holder.mCheckInfo.setClickable(true);
                    holder.mCheckInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //网络请求获取工作人员  TODO
                            String[] workers = new String[]{"合格", "不合格"};
                            showSingleChooseDialog(context, "检查情况", workers, holder, position);

                        }
                    });

                    holder.mCheckInfo.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            list.get(position).setCheckInfo(s.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }

            }



//                case Constant.CONTROL_TOOL:
//                    convertView = LayoutInflater.from(context).inflate(R.layout.item_control_tool_division, parent, false);
//                    break;


            convertView.setTag(holder);
        }

        return convertView;
    }

    public void setData(List<ControlQualityInfo> typeBeanList) {
        list = typeBeanList;
        notifyDataSetChanged();

    }


    static class ViewHolder {
        private TextView mDivisonNo;
        private TextView mDivisonKey;
        private TextView mContent;
        private TextView mSafeDivison;
        private TextView mCheckInfo;
        private LinearLayout mItem;
    }

    public void showSingleChooseDialog(Context context, String title, String[] workers, ViewHolder holder, int position) {
        new AlertDialog.Builder(context).setTitle(title).setItems(workers, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context, "您已经选择了: " + which + ":" + workers[which],Toast.LENGTH_LONG).show();
                holder.mCheckInfo.setText(workers[which]);
                list.get(position).setCheckInfo(workers[which]);
                dialog.dismiss();
            }
        }).show();
    }
}