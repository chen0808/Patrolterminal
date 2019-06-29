package com.patrol.terminal.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.bean.TowerPart;
import com.patrol.terminal.utils.RxRefreshEvent;

import java.util.ArrayList;
import java.util.List;

public class TowerPartAdapter extends BaseQuickAdapter<TowerPart, TowerPartAdapter.MyViewHilder> {

    private final List<Tower> towers;
    private final List<TowerPart> towerParts;

    public TowerPartAdapter(int layoutResId, @Nullable List<TowerPart> data, List<Tower> towers) {
        super(layoutResId, data);
        towerParts = data;
        this.towers = towers;
    }

    @Override
    protected void convert(MyViewHilder helper, TowerPart item) {
        TextView tvStart = helper.getView(R.id.tv_start);
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTower(helper, towers, tvStart, true, helper.getAdapterPosition());
            }
        });
        TextView tvEnd = helper.getView(R.id.tv_end);
        tvEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTower(helper, towers, tvEnd, false, helper.getAdapterPosition());
            }
        });
        ImageView ivAdd = helper.getView(R.id.iv_add);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvStart.getText().toString().isEmpty() || tvEnd.getText().toString().isEmpty()) {
                    Toast.makeText(mContext, "请选择起始和终止杆塔", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = helper.endtower; i >  helper.startower-1; i--) {
                        towers.remove(i);
                    }
                    towerParts.add(new TowerPart("", "", "", "3"));
                    notifyDataSetChanged();
                }
            }
        });
        if (helper.getAdapterPosition() != towerParts.size() - 1) {
            ivAdd.setVisibility(View.INVISIBLE);
        }
    }

    //选择杆段
    private void showTower(MyViewHilder helper, List<Tower> towerList, TextView tv, boolean isStart, int position) {
        RxRefreshEvent.publish("hide");
        List<String> towerNameList = new ArrayList<>();
        towerNameList.clear();
        for (int i = 0; i < towerList.size(); i++) {
            towerNameList.add(towerList.get(i).getName());
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                String tower = towerList.get(options1).getName();
                String tower_id = towerList.get(options1).getId();
                int sort =Integer.parseInt(towerList.get(options1).getSort());
                int index = towerNameList.indexOf(tower);
                if (isStart) {
                    RxRefreshEvent.publish("show");
                    if (helper.endtower == -1 ||index< helper.endtower || index == helper.endtower) {
                        for (int i = 0; i < towerParts.size(); i++) {
                            int start_sort = towerParts.get(i).getStart_sort();
                            int end_sort = towerParts.get(i).getEnd_sort();
                            int endSort = towerParts.get(position).getEnd_sort();
                            if (sort<start_sort&&endSort>end_sort){
                                Toast.makeText(mContext, "该杆段包含重复杆塔，请重新选择", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        helper.startower = index;
                        towerParts.get(position).setName_start(tower);
                        towerParts.get(position).setStart_id(tower_id);
                        towerParts.get(position).setStart_sort(sort);
                        towerParts.get(position).setStart_index(index);
                        tv.setText(tower);
                    } else {
                        Toast.makeText(mContext, "起始杆塔不能大于终止杆塔", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    RxRefreshEvent.publish("show");

                    if (helper.startower == -1 || index > helper.startower || index == helper.startower) {
                        for (int i = 0; i < towerParts.size(); i++) {
                            int start_sort = towerParts.get(i).getStart_sort();
                            int end_sort = towerParts.get(i).getEnd_sort();
                            int startSort = towerParts.get(position).getStart_sort();
                            if (startSort<start_sort&&sort>end_sort){
                                Toast.makeText(mContext, "该杆段包含重复杆塔，请重新选择", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        helper.endtower = index;
                        towerParts.get(position).setName_end(tower);
                        towerParts.get(position).setEnd_sort(sort);
                        towerParts.get(position).setEnd_index(sort);
                        towerParts.get(position).setEnd_id(tower_id);
                        tv.setText(tower);

                    } else {
                        Toast.makeText(mContext, "终止杆塔不能小于起始杆塔", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).build();
        pvOptions.setPicker(towerNameList);

        pvOptions.show();

    }

    class MyViewHilder extends BaseViewHolder {
        private int startower = -1;
        private int endtower = -1;

        public MyViewHilder(View view) {
            super(view);
        }

    }

}
