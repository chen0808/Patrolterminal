package com.patrol.terminal.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.patrol.terminal.R;

/**
 * 取消或者确认类型的Dialog
 * CancelOrOkDialog.setOnDialogclick(DefectActivity.this, "请再次确认操作", "取消", "确认", new CancelOrOkDialog.onDialogClick() {
 *                         @Override
 *                         public void ok() {
 *
 *                         }
 *
 *                         @Override
 *                         public void cancel() {
 *
 *                         }
 *                     });
 */
public class CancelOrOkDialog extends Dialog {

    private static onDialogClick click;
    private static CancelOrOkDialog dialog;


    public CancelOrOkDialog(Context context, String title, String cancleStr, String okStr) {
        //使用自定义Dialog样式
        super(context, R.style.custom_dialog);
        //指定布局
        setContentView(R.layout.dialog_cancel_or_ok);
        //点击外部不可消失
//        setCancelable(false);

        //设置标题
        TextView titleTv = (TextView) findViewById(R.id.dialog_title_tv);
        titleTv.setText(title);

        TextView cancleTv = (TextView) findViewById(R.id.cancel_tv);
        TextView sureTv = (TextView) findViewById(R.id.ok_tv);
        cancleTv.setText(cancleStr);
        sureTv.setText(okStr);

        findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                if (paramView.getId()==R.id.cancel_tv){
                //取消
                    if (click != null)
                        click.cancle();
                    cancle();
                    dismiss();
                }
            }
        });

        findViewById(R.id.ok_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                if (paramView.getId()==R.id.ok_tv){
                    ok();
                    if (click != null)
                        click.ok();
                    dismiss();
                }
            }
        });

    }


    //确认
   protected    void ok() {
    }

    public static void setOnDialogclick(Context context, String title, String cancleStr, String okStr, onDialogClick onDialogClick) {
        dialog = new CancelOrOkDialog(context, title, cancleStr, okStr);
        click = onDialogClick;
        dialog.show();
    }

    protected  void cancle() {

    }


    public interface onDialogClick {
        void ok();

        void cancle();
    }
}
