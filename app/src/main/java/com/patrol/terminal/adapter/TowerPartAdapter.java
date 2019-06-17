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

import java.util.ArrayList;
import java.util.List;

public class TowerPartAdapter extends BaseQuickAdapter<TowerPart, BaseViewHolder> {

    private final List<Tower> towers;
    private final List<TowerPart> towerParts;

    public TowerPartAdapter(int layoutResId, @Nullable List<TowerPart> data, List<Tower> towers) {
        super(layoutResId, data);
        towerParts = data;
        this.towers = towers;
    }

    @Override
    protected void convert(BaseViewHolder helper, TowerPart item) {
        TextView tvStart = helper.getView(R.id.tv_start);
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTower(towers, tvStart, true, helper.getAdapterPosition());
            }
        });
        TextView tvEnd = helper.getView(R.id.tv_end);
        tvEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTower(towers, tvEnd, false, helper.getAdapterPosition());
            }
        });
        ImageView ivAdd = helper.getView(R.id.iv_add);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvStart.getText().toString().isEmpty() || tvEnd.getText().toString().isEmpty()) {
                    Toast.makeText(mContext, "请选择起始和终止杆塔", Toast.LENGTH_SHORT).show();
                } else {
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
    private void showTower(List<Tower> towerList, TextView tv, boolean isStart, int position) {
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
                tv.setText(tower);
                if (isStart) {
                    towerParts.get(position).setName_start(tower);
                    towerParts.get(position).setStart_id(tower_id);
                } else {
                    towerParts.get(position).setName_end(tower);
                    towerParts.get(position).setEnd_id(tower_id);
                }
            }
        }).build();
        pvOptions.setPicker(towerNameList);
        pvOptions.show();

    }
}
