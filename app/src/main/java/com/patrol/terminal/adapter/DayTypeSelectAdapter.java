package com.patrol.terminal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.EqTower;
import com.patrol.terminal.bean.PlanTypeBean;

import java.util.ArrayList;
import java.util.List;

public class DayTypeSelectAdapter extends BaseAdapter {
    private Context context;
    private  List<PlanTypeBean> lineTypeBeans;
    private int start,end;

    public DayTypeSelectAdapter(Context context, List<PlanTypeBean> traceList) {
        this.context = context;
        this.lineTypeBeans = traceList;
    }


    public DayTypeSelectAdapter(Context context, List<PlanTypeBean> selectType, List<EqTower> eqTowers, List<String>  names) {
        this.context = context;
        this.lineTypeBeans = selectType;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_week_type, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.item_type_name);
            holder.monthPlanTowerStart = (TextView) convertView.findViewById(R.id.month_plan_tower_start);
            holder.monthPlanTowerEnd = (TextView) convertView.findViewById(R.id.month_plan_tower_end);
            convertView.setTag(holder);
        }
        PlanTypeBean listBean = lineTypeBeans.get(position);
        holder.name.setText(listBean.getName());
            holder.name.setTextColor(context.getResources().getColor(R.color.tv_gray));
         holder.monthPlanTowerStart.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showLineStart(holder,listBean);
             }
         });
        holder.monthPlanTowerEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLineEnd(holder,listBean);
            }
        });
        return convertView;
    }

    public void setData(List<PlanTypeBean> typeBeanList) {
        lineTypeBeans = typeBeanList;
        notifyDataSetChanged();

    }
    static class ViewHolder {
        public TextView name;
        public TextView monthPlanTowerStart;
        public TextView monthPlanTowerEnd;
    }

    //线路类型添加选项框
    private void showLineStart(ViewHolder viewHolder, PlanTypeBean listBean) {// 不联动的多级选项
        List<EqTower> eqTowers = listBean.getEqTowers();
        List<String>  towerList=new ArrayList<>();
        for (int i = 0; i < eqTowers.size(); i++) {
            towerList.add(eqTowers.get(i).getTowers());
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                viewHolder.monthPlanTowerStart.setText(towerList.get(options1));
                for (int i = 0; i < eqTowers.size(); i++) {
                    EqTower tower = eqTowers.get(i);
                    if (towerList.get(options1).equals(tower.getTowers())){
                        start = i;
                    }
                }
                listBean.setStart(start);
            }
        }).build();
        pvOptions.setPicker(towerList);
        pvOptions.show();
    }

    //显示结束杆塔选择框
    private void showLineEnd(ViewHolder viewHolder, PlanTypeBean listBean) {
        List<EqTower> eqTowers = listBean.getEqTowers();
        List<String>  towerList=new ArrayList<>();
        for (int i = 0; i < eqTowers.size(); i++) {
            towerList.add(eqTowers.get(i).getTowers());
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                viewHolder.monthPlanTowerEnd.setText(towerList.get(options1));
                for (int i = 0; i <eqTowers.size(); i++) {
                    EqTower tower = eqTowers.get(i);
                    if (towerList.get(options1).equals(tower.getTowers())){
                        end = i;
                    }
                }
                listBean.setEnd(end);
            }
        }).build();
        pvOptions.setPicker(towerList);
        pvOptions.show();
    }



    public PlanTypeBean getBean(int poistion){
        PlanTypeBean bean = lineTypeBeans.get(poistion);
        return bean;
    }
}