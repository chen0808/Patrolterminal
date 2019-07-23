package com.patrol.terminal.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.BanjiXLBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 缺陷 线路
 */
public class DefectBanjiXLAdapter extends BaseAdapter {
    ViewHolder holder = null;
    private Context context;
    private List<BanjiXLBean> bajixlList = new ArrayList<>();

    public DefectBanjiXLAdapter(Context context, List<BanjiXLBean> list) {
        this.context = context;
        this.bajixlList = list;
    }

    @Override
    public int getCount() {
        return bajixlList.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_defect_banji_xl, parent, false);
            holder.tvName = convertView.findViewById(R.id.tv_banji);
            holder.imgStatus = convertView.findViewById(R.id.img_banji_xl);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(bajixlList.get(position).getName());

        if (bajixlList.get(position).isCheck()) {
            holder.imgStatus.setImageResource(R.mipmap.defect_level_checked);
        } else {
            holder.imgStatus.setImageResource(R.mipmap.defect_level_unchecked);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bajixlList.get(position).isCheck()) {
                    bajixlList.get(position).setCheck(false);
                } else {
                    bajixlList.get(position).setCheck(true);
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    /**
     * 返回选中线路
     *
     * @return
     */
    public String getSelectLine() {
        String line = "";
        for (int i = 0; i < bajixlList.size(); i++) {
            if (bajixlList.get(i).isCheck()) {
                BanjiXLBean bean = bajixlList.get(i);
                if (TextUtils.isEmpty(line)) {
                    line = bean.getId();
                } else {
                    line = line + "," + bean.getId();
                }
            }
        }
        return line;
    }

    class ViewHolder {
        private TextView tvName;
        private ImageView imgStatus;
    }


}