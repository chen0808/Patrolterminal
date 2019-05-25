package com.patrol.terminal.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ControlToolAdapter;
import com.patrol.terminal.bean.ControlToolInfo;

import java.util.List;

import androidx.appcompat.app.AlertDialog;

public class AddToolDialog {

    private static AlertDialog dialog;
    private static View dialogView;
    private static EditText divisonNameEt;
    private static EditText divisonModelEt;
    private static EditText divisonUnitEt;
    private static EditText divisonTotalEt;
    private static EditText divisonRemarksEt;


    public static void show(Context context, ControlToolAdapter adapter, List<ControlToolInfo> list) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.add_tool_dialog, null);
        divisonNameEt = dialogView.findViewById(R.id.divison_name_et);
        divisonModelEt = dialogView.findViewById(R.id.divison_model_et);
        divisonUnitEt = dialogView.findViewById(R.id.divison_unit_et);
        divisonTotalEt = dialogView.findViewById(R.id.divison_num_et);
        divisonRemarksEt = dialogView.findViewById(R.id.divison_remarks_et);

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

                ControlToolInfo controlToolInfo = new ControlToolInfo();
                controlToolInfo.setName(divisonNameEt.getText().toString());
                controlToolInfo.setType(divisonModelEt.getText().toString());
                controlToolInfo.setUnit(divisonUnitEt.getText().toString());
                controlToolInfo.setTotal(divisonTotalEt.getText().toString());
                controlToolInfo.setDetail(divisonRemarksEt.getText().toString());
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
    public static void update(Context context, ControlToolAdapter adapter, List<ControlToolInfo> list, int position) {
        ControlToolInfo info = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.add_tool_dialog, null);
        divisonNameEt = dialogView.findViewById(R.id.divison_name_et);
        divisonModelEt = dialogView.findViewById(R.id.divison_model_et);
        divisonUnitEt = dialogView.findViewById(R.id.divison_unit_et);
        divisonTotalEt = dialogView.findViewById(R.id.divison_num_et);
        divisonRemarksEt = dialogView.findViewById(R.id.divison_remarks_et);

        divisonNameEt.setText(info.getName());
        divisonModelEt.setText(info.getType());
        divisonUnitEt.setText(info.getUnit());
        divisonTotalEt.setText(info.getTotal());
        divisonRemarksEt.setText(info.getDetail());

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
                list.get(position).setName(divisonNameEt.getText().toString());
                list.get(position).setType(divisonModelEt.getText().toString());
                list.get(position).setUnit(divisonUnitEt.getText().toString());
                list.get(position).setTotal(divisonTotalEt.getText().toString());
                list.get(position).setDetail(divisonRemarksEt.getText().toString());
                list.get(position).setNum(position);

                adapter.setData(list);

            }
        });
        builder.setView(dialogView);
        dialog = builder.show();
    }
}
