package com.patrol.terminal.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ControlToolAdapter;
import com.patrol.terminal.bean.CardTool;
import com.patrol.terminal.bean.EqToolTemp;
import com.patrol.terminal.bean.LocalTroubleTypeBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import androidx.appcompat.app.AlertDialog;

public class AddToolDialog {

    private static AlertDialog dialog;
    private static View dialogView;
    private static CustomSpinner divisonNameEt;
    private static CustomSpinner divisonModelEt;
    private static EditText divisonUnitEt;
    private static EditText divisonTotalEt;
    private static EditText divisonRemarksEt;
    private static List<String>   names = new ArrayList<>();;
    private static List<EqToolTemp> eqToolTempLists=new ArrayList<>();

    public static void show(Context context, ControlToolAdapter adapter, List<CardTool> list, List<EqToolTemp> eqToolTempList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.add_tool_dialog, null);
        divisonNameEt = dialogView.findViewById(R.id.divison_name_et);
        divisonModelEt = dialogView.findViewById(R.id.divison_model_et);
        divisonUnitEt = dialogView.findViewById(R.id.divison_unit_et);
        divisonTotalEt = dialogView.findViewById(R.id.divison_num_et);
        divisonRemarksEt = dialogView.findViewById(R.id.divison_remarks_et);
        eqToolTempLists.clear();
        names.clear();

        //获取工器具名称
        for (int i = 0; i < eqToolTempList.size(); i++) {
            EqToolTemp eqToolTemp = eqToolTempList.get(i);
            String name= eqToolTemp.getName();
            if (names.indexOf(name)==-1){
                names.add(name);
            }
            eqToolTempLists.add(eqToolTemp);
        }
        List<String> type=new ArrayList<>();
        if (names.size()>0){
            for (int i = 0; i < eqToolTempList.size(); i++) {
                EqToolTemp eqToolTemp = eqToolTempList.get(i);
                String name= eqToolTemp.getName();
                if (names.get(0).equals(name)){
                    divisonUnitEt.setText(eqToolTemp.getUnit());
                   if (eqToolTemp.getType()!=null){
                       type.add(eqToolTemp.getType());
                   }
                }
            }
        }
        divisonNameEt.attachDataSource(names);
        if (type.size()==0){
            type.add("无规格");
        }

        divisonModelEt.attachDataSource(type);
        //隐患类别
        divisonNameEt.setOnItemSelectedListener(new CustomSpinner.OnItemSelectedListenerSpinner() {
            @Override
            public void onItemSelected(CustomSpinner parent, View view, int i, long l) {
                String s = names.get(i);
                String nuit="";
              List<String>  typeList=new ArrayList<>();
                for (int j = 0; j < eqToolTempList.size(); j++) {
                    EqToolTemp eqToolTemp = eqToolTempList.get(j);
                    if (s.equals(eqToolTemp.getName())&&eqToolTemp.getType()!=null){
                        typeList.add(eqToolTemp.getType());
                        nuit=eqToolTemp.getUnit();
                    }
                }
                if (typeList.size()==0){
                    typeList.add("无规格");
                }
                divisonModelEt.attachDataSource(typeList);
                divisonUnitEt.setText(nuit);

            }
        });
        Button cancel = dialogView.findViewById(R.id.btn_cancel);
        Button sure = dialogView.findViewById(R.id.btn_sure);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                CardTool controlToolInfo = new CardTool();
                controlToolInfo.setTool_name(divisonNameEt.getSelectItem());
                controlToolInfo.setType(divisonModelEt.getSelectItem());
                controlToolInfo.setUnit(divisonUnitEt.getText().toString());
                controlToolInfo.setTotal(divisonTotalEt.getText().toString());
                controlToolInfo.setRemark(divisonRemarksEt.getText().toString());
                controlToolInfo.setNum(list.size() + 1);

                list.add(controlToolInfo);
                adapter.setData(list);

            }
        });
        builder.setView(dialogView);
        dialog = builder.show();
    }

//    public static ControlToolInfo getControlToolInfo() {
//        return controlToolInfo;
//    }
     //点击一行修改
    public static void update(Context context, ControlToolAdapter adapter, List<CardTool> list, int position, List<EqToolTemp> eqToolTempList) {
        CardTool info = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.add_tool_dialog, null);
        divisonNameEt = dialogView.findViewById(R.id.divison_name_et);
        divisonModelEt = dialogView.findViewById(R.id.divison_model_et);
        divisonUnitEt = dialogView.findViewById(R.id.divison_unit_et);
        divisonTotalEt = dialogView.findViewById(R.id.divison_num_et);
        divisonRemarksEt = dialogView.findViewById(R.id.divison_remarks_et);
         names.clear();
        eqToolTempLists.clear();
        //获取工器具名称
        for (int i = 0; i < eqToolTempList.size(); i++) {
            EqToolTemp eqToolTemp = eqToolTempList.get(i);
            String name= eqToolTemp.getName();
            if (names.indexOf(name)==-1){
                names.add(name);
            }
            eqToolTempLists.add(eqToolTemp);
        }
        divisonNameEt.attachDataSource(names);

        List<String>  typeList=new ArrayList<>();
        for (int i = 0; i <eqToolTempLists.size() ; i++) {
            EqToolTemp eqToolTemp = eqToolTempLists.get(i);
            if (info.getTool_name().equals(eqToolTemp.getName())&&eqToolTemp.getType()!=null){
                typeList.add(eqToolTemp.getType());
            }
        }
        if (typeList.size()==0){
            typeList.add("无规格");
        }

        //隐患类别
        divisonNameEt.setOnItemSelectedListener(new CustomSpinner.OnItemSelectedListenerSpinner() {
            @Override
            public void onItemSelected(CustomSpinner parent, View view, int i, long l) {
                String s = names.get(i);
                String nuit="";
                List<String>  typeList=new ArrayList<>();
                for (int j = 0; j < eqToolTempList.size(); j++) {
                    EqToolTemp eqToolTemp = eqToolTempList.get(j);
                    if (s.equals(eqToolTemp.getName())&&eqToolTemp.getType()!=null){
                        typeList.add(eqToolTemp.getType());
                        nuit=eqToolTemp.getUnit();
                    }
                }
                if (typeList.size()==0){
                    typeList.add("无");
                }
                divisonModelEt.attachDataSource(typeList);
                divisonUnitEt.setText(nuit);

            }
        });
        //隐患类别
        divisonModelEt.setOnItemSelectedListener(new CustomSpinner.OnItemSelectedListenerSpinner() {
            @Override
            public void onItemSelected(CustomSpinner parent, View view, int i, long l) {

            }
        });
        divisonNameEt.setSelectedIndex(names.indexOf(info.getTool_name()));
        divisonModelEt.setSelectedIndex(typeList.indexOf(info.getType()));
        divisonUnitEt.setText(info.getUnit());
        divisonTotalEt.setText(info.getTotal());
        divisonRemarksEt.setText(info.getRemark());

        Button cancel = dialogView.findViewById(R.id.btn_cancel);
        Button sure = dialogView.findViewById(R.id.btn_sure);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

//                ControlToolInfo controlToolInfo = new ControlToolInfo();
//                controlToolInfo.setName(divisonNameEt.getText().toString());
//                controlToolInfo.setType(divisonModelEt.getText().toString());
//                controlToolInfo.setUnit(divisonUnitEt.getText().toString());
//                controlToolInfo.setTotal(divisonNumEt.getText().toString());
//                controlToolInfo.setDetail(divisonRemarksEt.getText().toString());
//                controlToolInfo.setNum(position);

                //list.remove(position);
                //list.add(position, controlToolInfo);
                list.get(position).setTool_name(divisonNameEt.getSelectItem());
                list.get(position).setType(divisonModelEt.getSelectItem());
                list.get(position).setUnit(divisonUnitEt.getText().toString());
                list.get(position).setTotal(divisonTotalEt.getText().toString());
                list.get(position).setRemark(divisonRemarksEt.getText().toString());
                list.get(position).setNum(position);

                adapter.setData(list);

            }
        });
        builder.setView(dialogView);
        dialog = builder.show();
    }
}
