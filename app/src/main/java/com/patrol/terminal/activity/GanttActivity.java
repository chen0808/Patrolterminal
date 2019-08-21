package com.patrol.terminal.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：陈飞
 * 时间：2019/08/21 10:52
 */
public class GanttActivity extends BaseActivity {
    @BindView(R.id.title_back)
    RelativeLayout titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_setting_iv)
    ImageView titleSettingIv;
    @BindView(R.id.title_setting_tv)
    TextView titleSettingTv;
    @BindView(R.id.title_setting)
    RelativeLayout titleSetting;
    @BindView(R.id.title_item)
    RelativeLayout titleItem;
    /**
     * 任务时长
     * 假设任务是12.1 到 12.9
     * end - start + 1
     */
    //      开始
    int start = 0;
    int end = 0;
    private LinearLayout llprogress;
    private LinearLayout llshijian;
    //  设置表示1天的宽度
    private int dayWidth;

    /**
     * dp转为px
     * Android内部是px
     *
     * @param context  上下文
     * @param dipValue dp值
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gantt);
        ButterKnife.bind(this);
        titleName.setText("甘特图");

        //      单位是px
        dayWidth = getPinMuWidth() / 7;
//      根据返回的天数判断，大于多少天*时间轴的长度  就是向左的距离
//      进度条的长度就是，任务天数*时间周的长度，就是进度条的总长度
        llshijian = (LinearLayout) findViewById(R.id.llshijian);
        llprogress = (LinearLayout) findViewById(R.id.llprogress);
        addTextView();

        List<MyTask> listTask = listMyTask();
        for (int i = 0; i < listTask.size(); i++) {
//         添加进度条
            addProgress(listTask.get(i).startTask, listTask.get(i).endTask, listTask.get(i).jindu);
        }
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    /**
     * 拿到手机屏幕的宽度
     * 单位是px
     */
    private int getPinMuWidth() {
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        return width;
    }

    /**
     * 添加TextView的方法
     * 添加时间
     */
    private void addTextView() {
        for (int i = 0; i < getTimeData().size(); i++) {
//          设置textView 并添加到 布局中
            TextView textView = new TextView(this);
            llshijian.addView(textView);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
            params.width = dayWidth;
            params.height = dip2px(this, 30);
            // params.leftMargin = dip2px(context, 1);
            // params.setMargins(dip2px(MainActivity.this, 1), 0, 0, 0); // 可以实现设置位置信息，如居左距离，其它类推
            // params.leftMargin = dip2px(MainActivity.this, 1);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);//居中
            textView.setText(getTimeData().get(i) + "");
            textView.setBackgroundColor(Color.WHITE);
//            textView.setBackgroundResource(R.mipmap.xhdpi);
        }
    }

    private List<String> getTimeData() {
        List<String> list = new ArrayList<>();
        list.add("8.1");
        list.add("8.2");
        list.add("8.3");
        list.add("8.4");
        list.add("8.5");
        list.add("8.6");
        list.add("8.7");
        list.add("8.8");
        list.add("8.9");
        list.add("8.10");
        list.add("8.11");
        list.add("8.12");
        list.add("8.13");
        list.add("8.14");
        list.add("8.15");
        list.add("8.16");

        return list;
    }

    private void renwuTime(String startDay, String endDay) {
        List<String> list = getTimeData();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(startDay)) {
                start = i;
            }
            if (list.get(i).equals(endDay)) {
                end = i;
            }

        }

    }

    private void addProgress(String startDay, String endDay, int jindu) {
//      添加相对布局
        RelativeLayout relativeLayout = new RelativeLayout(this);
        llprogress.addView(relativeLayout);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.topMargin = dip2px(this, 10);
//      宽高默认都是包裹
        layoutParams.height = dip2px(this, 30);


//      把进度条添加到相对布局里面===
//      开始时间，结束时间
        renwuTime(startDay, endDay);
//      任务的天数
        int tianshu = end - start + 1;

        ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
//        ProgressBar progressBar = new ProgressBar(context);
//      进度条装到相对布局里面
        relativeLayout.addView(progressBar);
        RelativeLayout.LayoutParams progressBarParams = (RelativeLayout.LayoutParams) progressBar.getLayoutParams();
//      一天的宽度*天数+边框的宽度
        progressBarParams.width = dayWidth * tianshu;
        progressBarParams.height = dip2px(this, 30);
        progressBarParams.leftMargin = dayWidth * start;
        progressBar.setLayoutParams(progressBarParams);

        progressBar.setIndeterminate(false);
        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.patrol_record_progress));
        progressBar.setProgress(jindu);
        progressBar.setMax(100);

//      把TextView放入相对布局中===
        TextView textView = new TextView(this);
        relativeLayout.addView(textView);
        RelativeLayout.LayoutParams textViewParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        textViewParams.height = dip2px(this, 30);
//      距离左边是10
        textViewParams.leftMargin = dayWidth * start + dip2px(this, 10);
        textView.setLayoutParams(textViewParams);

        textView.setText(jindu + "%");
        textView.setGravity(Gravity.CENTER_VERTICAL);
    }

    /**
     * 造数据
     *
     * @return
     */
    private List<MyTask> listMyTask() {
        List<MyTask> list = new ArrayList<>();
        MyTask myTask1 = new MyTask();
        myTask1.startTask = "12.2";
        myTask1.endTask = "12.6";
        myTask1.jindu = 60;

        MyTask myTask2 = new MyTask();
        myTask2.startTask = "12.3";
        myTask2.endTask = "12.4";
        myTask2.jindu = 30;

        MyTask myTask3 = new MyTask();
        myTask3.startTask = "12.4";
        myTask3.endTask = "12.9";
        myTask3.jindu = 80;

        MyTask myTask4 = new MyTask();
        myTask4.startTask = "12.5";
        myTask4.endTask = "12.9";
        myTask4.jindu = 15;

        MyTask myTask5 = new MyTask();
        myTask5.startTask = "12.6";
        myTask5.endTask = "12.7";
        myTask5.jindu = 70;

        MyTask myTask6 = new MyTask();
        myTask6.startTask = "12.3";
        myTask6.endTask = "12.5";
        myTask6.jindu = 60;

        MyTask myTask7 = new MyTask();
        myTask7.startTask = "12.1";
        myTask7.endTask = "12.3";
        myTask7.jindu = 10;

        MyTask myTask8 = new MyTask();
        myTask8.startTask = "12.5";
        myTask8.endTask = "12.10";
        myTask8.jindu = 90;

        MyTask myTask9 = new MyTask();
        myTask9.startTask = "12.1";
        myTask9.endTask = "12.5";
        myTask9.jindu = 70;

        MyTask myTask10 = new MyTask();
        myTask10.startTask = "12.2";
        myTask10.endTask = "12.9";
        myTask10.jindu = 50;

        MyTask myTask11 = new MyTask();
        myTask11.startTask = "12.3";
        myTask11.endTask = "12.7";
        myTask11.jindu = 30;

        MyTask myTask12 = new MyTask();
        myTask12.startTask = "12.1";
        myTask12.endTask = "12.8";
        myTask12.jindu = 20;

        MyTask myTask13 = new MyTask();
        myTask13.startTask = "12.2";
        myTask13.endTask = "12.10";
        myTask13.jindu = 60;

        MyTask myTask14 = new MyTask();
        myTask14.startTask = "12.5";
        myTask14.endTask = "12.13";
        myTask14.jindu = 60;

        MyTask myTask15 = new MyTask();
        myTask15.startTask = "12.5";
        myTask15.endTask = "12.10";
        myTask15.jindu = 60;

        MyTask myTask16 = new MyTask();
        myTask16.startTask = "12.1";
        myTask16.endTask = "12.6";
        myTask16.jindu = 60;

        MyTask myTask17 = new MyTask();
        myTask17.startTask = "12.5";
        myTask17.endTask = "12.15";
        myTask17.jindu = 60;

        MyTask myTask18 = new MyTask();
        myTask18.startTask = "12.3";
        myTask18.endTask = "12.10";
        myTask18.jindu = 60;

        MyTask myTask19 = new MyTask();
        myTask19.startTask = "12.2";
        myTask19.endTask = "12.11";
        myTask19.jindu = 60;

        MyTask myTask20 = new MyTask();
        myTask20.startTask = "12.1";
        myTask20.endTask = "12.16";
        myTask20.jindu = 60;

        list.add(myTask1);
        list.add(myTask2);
        list.add(myTask3);
        list.add(myTask4);
        list.add(myTask5);
        list.add(myTask6);
        list.add(myTask7);
        list.add(myTask8);
        list.add(myTask9);

        list.add(myTask10);
        list.add(myTask11);
        list.add(myTask12);
        list.add(myTask13);
        list.add(myTask14);
        list.add(myTask15);
        list.add(myTask16);
        list.add(myTask17);
        list.add(myTask18);
        list.add(myTask19);
        list.add(myTask20);
        return list;
    }

    class MyTask implements Serializable {
        public String startTask;
        public String endTask;
        public int jindu;
    }
}
