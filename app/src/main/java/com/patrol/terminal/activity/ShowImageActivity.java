package com.patrol.terminal.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.patrol.terminal.R;
import com.patrol.terminal.adapter.MyPagerAdapter;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.bean.BigPicBean;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowImageActivity extends BaseActivity {
    @BindView(R.id.show_view_pager)
    ViewPager viewPager;
    private List<View> listViews = null;  //用于获取图片资源
    private int index = 0;   //获取当前点击的图片位置
    private MyPagerAdapter adapter;   //ViewPager的适配器
    private ArrayList<Bitmap> bmp = null;
    private int position;
    private BigPicBean bean;
    private List<String> pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        ButterKnife.bind(this);

        initData();

    }


    private void initData() {
        listViews = new ArrayList<View>();   //初始化list
        bean = getIntent().getParcelableExtra("bean");

        pic = bean.getPic();
        position = bean.getPositon();
        inint();
    }

    private void inint() {

        if (pic != null && pic.size() > 0) {
            for (int i = 0; i < pic.size(); i++) {  //for循环将试图添加到list中
                View view = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.view_pager_item, null);   //绑定viewpager的item布局文件
//                ImageView iv = (ImageView) view.findViewById(R.id.view_image);   //绑定布局中的id
//                iv.setImageBitmap(bmp.get(i));   //设置当前点击的图片

                ImageView iv = (ImageView) view.findViewById(R.id.view_image);   //绑定布局中的id
                Glide.with(this).load(pic.get(i)).into(iv);
                listViews.add(view);
                /**
                 * 图片的长按监听
                 * */
                iv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
            adapter = new MyPagerAdapter(listViews);
            viewPager.setAdapter(adapter);
            viewPager.setOnPageChangeListener(new PageChangeListener()); //设置viewpager的改变监听
            //截取intent获取传递的值
            viewPager.setCurrentItem(position);    //viewpager显示指定的位置
        }
    }


    /**
     * pager的滑动监听
     */
    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            index = arg0;
        }

    }


}
