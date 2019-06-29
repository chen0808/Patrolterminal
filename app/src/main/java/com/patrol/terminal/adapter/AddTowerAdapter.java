package com.patrol.terminal.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.WeekOfMonthBean;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.StringUtil;

import java.util.List;

public class AddTowerAdapter extends BaseAdapter {
    private Context context;
    private List<WeekOfMonthBean> lineTypeBeans;
    private int type = 0;

    public AddTowerAdapter(Context context, List<WeekOfMonthBean> traceList) {
        this.context = context;
        this.lineTypeBeans = traceList;
    }

    @Override
    public int getCount() {

        return lineTypeBeans.size();

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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_add_tower, parent, false);
            holder.tvLineName = convertView.findViewById(R.id.tv_line_name);
            holder.tvLineDate = convertView.findViewById(R.id.tv_tower_date);

            holder.tvTowerName = (TextView) convertView.findViewById(R.id.tv_tower_name);
            holder.towerCheck = (ImageView) convertView.findViewById(R.id.item_add_tower_check);
            holder.towerType = (TextView) convertView.findViewById(R.id.item_add_tower_type);
            holder.item = (RelativeLayout) convertView.findViewById(R.id.add_tower_item);
            convertView.setTag(holder);
        }


        holder.tvLineName.setText(lineTypeBeans.get(position).getLine_name()/*lineTypeBeans.get(position).getTowers()*/);
        holder.tvTowerName.setText(lineTypeBeans.get(position).getTowers_name());
        holder.towerType.setText(StringUtil.getTypeSign(lineTypeBeans.get(position).getType_sign()));  //TODO
        holder.tvLineDate.setText(lineTypeBeans.get(position).getYear()+"-"+lineTypeBeans.get(position).getMonth());
        TextView tvContent = holder.towerType;
        String text = tvContent.getText().toString();
        String colorText = setColor(text);
        tvContent.setText(Html.fromHtml(colorText));

        WeekOfMonthBean listBean = lineTypeBeans.get(position);
        boolean check = listBean.isCheck();
        if (check == true) {
            holder.towerCheck.setImageResource(R.mipmap.check_yes);
        } else {
            holder.towerCheck.setImageResource(R.mipmap.check_no);
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listBean.isCheck()) {
                    listBean.setCheck(true);
                    holder.towerCheck.setImageResource(R.mipmap.check_yes);
                    String defectId = listBean.getDefect_id() == null ? "" : listBean.getDefect_id();
                    String towersId = listBean.getTowers_id() == null ? "" : listBean.getTowers_id();
                    RxRefreshEvent.publish("add@" + towersId + "@" +
                            listBean.getLine_id() + "@" + listBean.getLine_name() + "@" +
                            listBean.getTowers_name() + "@" + listBean.getType_id() + "@" +
                            listBean.getType_name() + "@" + listBean.getType_sign() + "@" +
                            listBean.getMonth_line_id() + "@" + defectId + "@" +
                            listBean.getStart_id() + "@" + listBean.getEnd_id() + "@" +
                            listBean.getTower_type() + "@" + listBean.getMonth_tower_id());

                } else {
                    listBean.setCheck(false);
                    holder.towerCheck.setImageResource(R.mipmap.check_no);
                    RxRefreshEvent.publish("delete@" + listBean.getTowers_id());

                }

            }
        });
        return convertView;
    }

    public void setData(List<WeekOfMonthBean> typeBeanList) {
        lineTypeBeans = typeBeanList;
        notifyDataSetChanged();

    }

    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();

    }

    private String setColor(String text) {
        if (text.contains("定期巡视")) {
            text = text.replace("定期巡视", "<font color='#007bff'>定期巡视</font>");
        }
        if (text.contains("故障巡视")) {
            text = text.replace("故障巡视", "<font color='#6610f2'>故障巡视</font>");
        }
        if (text.contains("接地电阻检测")) {
            text = text.replace("接地电阻检测", "<font color='#6f42c1'>接地电阻检测</font>");
        }
        if (text.contains("特殊性巡视")) {
            text = text.replace("特殊性巡视", "<font color='#e83e8c'>特殊性巡视</font>");
        }
        if (text.contains("红外测温检测")) {
            text = text.replace("红外测温检测", "<font color='#d4237a'>红外测温检测</font>");
        }
        if (text.contains("杆塔倾斜检测")) {
            text = text.replace("杆塔倾斜检测", "<font color='#fd7e14'>杆塔倾斜检测</font>");
        }
        if (text.contains("保电巡视")) {
            text = text.replace("保电巡视", "<font color='#ffc107'>保电巡视</font>");
        }
        if (text.contains("缺陷消除")) {
            text = text.replace("缺陷消除", "<font color='#28a745'>缺陷消除</font>");
        }
        if (text.contains("隐患处理")) {
            text = text.replace("隐患处理", "<font color='#20c997'>隐患处理</font>");
        }
        if (text.contains("绝缘子零值检测")) {
            text = text.replace("绝缘子零值检测", "<font color='#17a2b8'>绝缘子零值检测</font>");
        }
        if (text.contains("监督性巡视")) {
            text = text.replace("监督性巡视", "<font color='#28c7a7'>监督性巡视</font>");
        }
        if (text.contains("安全监督")) {
            text = text.replace("安全监督", "<font color='#1aa2b8'>安全监督</font>");
        }
        if (text.contains("验收")) {
            text = text.replace("验收", "<font color='#0070ff'>验收</font>");
        }
        return text;
    }

    static class ViewHolder {
        private TextView tvLineName,tvLineDate;
        private TextView tvTowerName;
        private ImageView towerCheck;
        private TextView towerType;
        private RelativeLayout item;

    }
}