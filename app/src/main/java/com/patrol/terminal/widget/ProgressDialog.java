package com.patrol.terminal.widget;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by fg on 2017/3/23.
 */

public class ProgressDialog {
    private static android.app.ProgressDialog progressDialog;

    public static void show(Context mContext) {
        show(mContext, false, "正在加载中...");
    }

    public static void show(Context cxt, boolean cancelable, String str) {

        try {
//            if (progressDialog!=null)
//            {
//                if (progressDialog.isShowing())
//                    progressDialog.cancel();
//            }
            progressDialog = new android.app.ProgressDialog(cxt);
            progressDialog.setCancelable(cancelable);
            progressDialog.setMessage(str);
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    progressDialog.cancel();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancle() {
        if (progressDialog == null)
            return;
//        if (progressDialog.isShowing())
            progressDialog.cancel();
    }

}
