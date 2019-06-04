package com.patrol.terminal.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.patrol.terminal.R;
import com.patrol.terminal.adapter.ControlDepdapter1;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.OverhaulSendUserBean2;
import com.patrol.terminal.bean.OverhaulUserInfo;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 取消或者确认类型的Dialog
 * <p>
 * 作者： 周旭 on 2017/5/27/0027.
 * 邮箱：374952705@qq.com
 * 博客：http://www.jianshu.com/u/56db5d78044d
 */

public class QfrDialog extends Dialog {
    private final String[] aaa={"a","b","c","d","e"};
    private Activity context;
    private TextView qfrTv;
    private TextView qfrTv2;
    private int qfrSpinner1Postiion = 0;
    private int qfrSpinner2Postiion = 0;
    private List<OverhaulSendUserBean2> overhaulSendUserBeans = new ArrayList<>();
    private List<OverhaulUserInfo> userData = new ArrayList<>();

    public QfrDialog(Activity context, String title, String cancleStr, String okStr) {

        //使用自定义Dialog样式
        super(context, R.style.custom_dialog);

        this.context = context;
        //指定布局
        setContentView(R.layout.dialog_qfr_cancel_or_ok);
        //点击外部不可消失
        setCancelable(false);

        TextView cancleTv = (TextView) findViewById(R.id.cancel_tv);
        TextView sureTv = (TextView) findViewById(R.id.ok_tv);
        cancleTv.setText(cancleStr);
        sureTv.setText(okStr);

        ImageView addIv = (ImageView) findViewById(R.id.add_btn_iv);
        LinearLayout qfrLl2 = (LinearLayout) findViewById(R.id.qfr_ll_2);

        qfrTv = (TextView)findViewById(R.id.qfr_tv);
        qfrTv2 = (TextView) findViewById(R.id.qfr_tv_2);

        findViewById(R.id.qfr_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                getAllSendToPerson(1);

            }
        });

        findViewById(R.id.qfr_tv_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                getAllSendToPerson(2);
            }
        });


        findViewById(R.id.add_btn_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                qfrLl2.setVisibility(View.VISIBLE);
                addIv.setVisibility(View.GONE);
            }
        });


        findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                //取消
                cancel();
            }
        });

        findViewById(R.id.ok_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                if (TextUtils.isEmpty(qfrTv.getText().toString())) {
                    Toast.makeText(context, "签发人不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                ok();
            }
        });

    }

    //确认
    public void ok() {
    }

    public void cancel() {

    }

    private void getAllSendToPerson(int qfrPos) {
        overhaulSendUserBeans.clear();
        List<String> qfrList = new ArrayList<>();
        BaseRequest.getInstance().getService()
                .getSendOverhaulUsers2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<OverhaulSendUserBean2>>(context) {
                    @Override
                    protected void onSuccees(BaseResult<List<OverhaulSendUserBean2>> t) throws Exception {
                        if (t.getCode() == 1) {
                            for (int i = 0; i < t.getResults().size(); i++) {
                                OverhaulSendUserBean2 bean = t.getResults().get(i);
                                if (!bean.getUserList().isEmpty() && bean.getUserList().size() > 0 && bean.getName().contains("专责")) {
                                    for (int i1 = 0; i1 < bean.getUserList().size(); i1++) {
                                        overhaulSendUserBeans.add(bean);
                                    }
                                }
                            }
                            qfrList.clear();
                            Log.w("linmeng", "111qfrList.size():" + qfrList.size());
                            for (int i = 0; i < overhaulSendUserBeans.size(); i++) {
                                for (int i1 = 0; i1 < overhaulSendUserBeans.get(i).getUserList().size(); i1++) {
                                    String userId = overhaulSendUserBeans.get(i).getUserList().get(i1).getId();
                                    String userName = overhaulSendUserBeans.get(i).getUserList().get(i1).getName();
                                    qfrList.add(userName);
                                    userData.add(new OverhaulUserInfo(userId, userName));
                                }
                            }
                            Log.w("linmeng", "222qfrList.size():" + qfrList.size());

                            String zzs[] = new String[qfrList.size()];
                            for (int i = 0; i < qfrList.size(); i++) {
                                zzs[i] = qfrList.get(i);
                            }
                            showSingleChooseDialog(context, "选择签发人", zzs, qfrPos);


//                            qfrSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                    qfrSpinner1Postiion = position;
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
//
//                                }
//                            });

//                            qfrSpinner2.attachDataSource(qfrList);
//                            qfrSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                    qfrSpinner2Postiion = position;
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
//
//                                }
//                            });
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    /*private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view= LayoutInflater.from(context).inflate(R.layout.item_choose,null);
            TextView tvName = view.findViewById(R.id.tv_name);
            tvName.setText("asd");
            return view;
        }
    }*/

    public void showSingleChooseDialog(Context context, String title, String[] workers, int qfrPos) {
        new AlertDialog.Builder(context).setTitle(title).setItems(workers, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context, "您已经选择了: " + which + ":" + workers[which],Toast.LENGTH_LONG).show();
//                holder.mDivisonName.setText(workers[which]);
//                list.get(position).setDivisonName(workers[which]);
//                list.get(position).setUserId(workers_id[which]);

                if (qfrPos == 1) {
                    qfrTv.setText(workers[which]);
                }else if (qfrPos == 2) {
                    qfrTv2.setText(workers[which]);
                }

                dialog.dismiss();
            }
        }).show();
    }
}
