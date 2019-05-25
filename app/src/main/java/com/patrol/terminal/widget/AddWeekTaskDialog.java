package com.patrol.terminal.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ControlToolAdapter;
import com.patrol.terminal.adapter.WeekTaskAdapter;
import com.patrol.terminal.bean.ControlToolInfo;
import com.patrol.terminal.bean.WeekTaskInfo;

import java.util.List;

import androidx.appcompat.app.AlertDialog;

public class AddWeekTaskDialog {
    private static AlertDialog dialog;
    private static View dialogView;
    private static EditText taskContentEt;

    public static void show(Context context, WeekTaskAdapter adapter, List<WeekTaskInfo> list) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.add_week_task_dialog, null);
        taskContentEt = dialogView.findViewById(R.id.task_content_et);

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

                WeekTaskInfo weekTaskInfo = new WeekTaskInfo();
                weekTaskInfo.setWeekContent(taskContentEt.getText().toString());
                weekTaskInfo.setNum(list.size());

                list.add(weekTaskInfo);
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
    public static void update(Context context, WeekTaskAdapter adapter, List<WeekTaskInfo> list, int position) {
        WeekTaskInfo info = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.add_week_task_dialog, null);
        taskContentEt = dialogView.findViewById(R.id.task_content_et);

        taskContentEt.setText(info.getWeekContent());

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

                //list.remove(position);
                //list.add(position, controlToolInfo);
                list.get(position).setWeekContent(taskContentEt.getText().toString());
                list.get(position).setNum(position);

                adapter.setData(list);

            }
        });
        builder.setView(dialogView);
        dialog = builder.show();
    }
}
