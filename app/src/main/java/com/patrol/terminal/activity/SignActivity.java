package com.patrol.terminal.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.SignBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends BaseActivity {

    @BindView(R.id.signature_pad)
    SignaturePad signaturePad;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.btn_sure)
    Button btnSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_cancel, R.id.btn_clear, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                SignBean.setBitmap(null);
                finish();
                break;
            case R.id.btn_clear:
                signaturePad.clear();
                break;
            case R.id.btn_sure:
                Bitmap bitmap = signaturePad.getSignatureBitmap();
                SignBean.setBitmap(bitmap);
                finish();
                break;
        }
    }
}
