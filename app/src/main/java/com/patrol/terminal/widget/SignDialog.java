package com.patrol.terminal.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.patrol.terminal.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SignDialog {

    private static Bitmap bitmap;

    public static Dialog show(Context context, ImageView iv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_sign, null);
        builder.setView(dialogView);
        Dialog dialog = builder.show();
        SignaturePad signaturePad = dialogView.findViewById(R.id.signature_pad);
        Button cancel = dialogView.findViewById(R.id.btn_cancel);
        Button clear = dialogView.findViewById(R.id.btn_clear);
        Button sure = dialogView.findViewById(R.id.btn_sure);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                bitmap = signaturePad.getSignatureBitmap();
                iv.setImageBitmap(bitmap);
            }
        });
        return dialog;
    }

    public static File saveBitmapFile(Bitmap bitmap, String fileName) {
        File file = new File("/storage/emulated/0/" + fileName + ".jpg");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
