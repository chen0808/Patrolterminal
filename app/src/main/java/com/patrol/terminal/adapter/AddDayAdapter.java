package com.patrol.terminal.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.DayOfWeekBean;
import com.patrol.terminal.utils.RxRefreshEvent;
import com.patrol.terminal.utils.StringUtil;

import java.util.List;

public class AddDayAdapter extends BaseAdapter {
    private Context context;
    private List<DayOfWeekBean> lineTypeBeans;
    private int type = 0;

    public AddDayAdapter(Context context, List<DayOfWeekBean> traceList) {
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
            holder.tvTowerName = convertView.findViewById(R.id.tv_tower_name);
            holder.tvLineName = (TextView) convertView.findViewById(R.id.tv_line_name);
            holder.towerCheck = (CheckBox) convertView.findViewById(R.id.item_add_tower_check);
            holder.towerType = (TextView) convertView.findViewById(R.id.item_add_tower_type);
            convertView.setTag(holder);
        }
        DayOfWeekBean listBean = lineTypeBeans.get(position);
        String planName = listBean.getLine_name() + listBean.getName();
        holder.tvLineName.setText(listBean.getLine_name());
        holder.tvTowerName.setText(listBean.getName());
        holder.towerType.setText(StringUtil.getTypeSign(listBean.getType_sign()));
        holder.towerCheck.setChecked(false);

        TextView tvContent = holder.towerType;
        String text = tvContent.getText().toString();
        String colorText = setColor(text);
        tvContent.setText(Html.fromHtml(colorText));

        holder.towerCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.towerCheck.isChecked()) {
                    RxRefreshEvent.publishDay(listBean);
                } else {
                    RxRefreshEvent.publishDay(listBean);
                }

            }
        });
        return convertView;
    }

    public void setData(List<DayOfWeekBean> typeBeanList) {
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
        private TextView tvLineName, tvTowerName, towerType;
        private CheckBox towerCheck;

    }
}