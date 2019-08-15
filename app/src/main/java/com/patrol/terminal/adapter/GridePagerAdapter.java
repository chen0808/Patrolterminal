package com.patrol.terminal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.viewpager.widget.PagerAdapter;

import com.patrol.terminal.R;
import com.patrol.terminal.activity.EngineeringBriefActivity;
import com.patrol.terminal.activity.GraphicProgressActivity;
import com.patrol.terminal.activity.LandMarkActivity;
import com.patrol.terminal.activity.ProjectBoardActivity;
import com.patrol.terminal.activity.ThirdWTicketActivity;
import com.patrol.terminal.activity.WorkWeeklyListActivity;
import com.patrol.terminal.activity.WorkingLogActivity;
import com.patrol.terminal.bean.MapUserInfo;
import com.patrol.terminal.overhaul.ElectronicNoticeActivity;
import com.patrol.terminal.overhaul.InternalNewsActivity;
import com.patrol.terminal.overhaul.SafeCheckMainActivity;
import com.patrol.terminal.overhaul.TechnicalSpecificationActivity;

import java.util.List;

/**
 * 图片浏览的适配器
 * <p>
 * 作者： 周旭 on 2017年7月30日 0030.
 * 邮箱：374952705@qq.com
 * 博客：http://www.jianshu.com/u/56db5d78044d
 */

public class GridePagerAdapter extends PagerAdapter {

    private Context context;
    private List<List<MapUserInfo>> imgList; //图片的数据源

    public GridePagerAdapter(Context context, List<List<MapUserInfo>> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    //判断当前的View 和 我们想要的Object(值为View) 是否一样;返回 true/false
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    //instantiateItem()：将当前view添加到ViewGroup中，并返回当前View
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = getItemView(R.layout.view_gride);
        GridView gridview = (GridView) itemView.findViewById(R.id.gridview);
        GridViewAdapter5 weekAdapter = new GridViewAdapter5(context, imgList.get(position));
        gridview.setAdapter(weekAdapter);
        //检修首页界面跳转
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                if (position == 0) {
                    switch (i) {
                        //项目看板
                        case 0:
                            intent.setClass(context, ProjectBoardActivity.class);
                            break;
                            //形象进度
                        case 1:
                            intent.setClass(context, GraphicProgressActivity.class);
                            break;
                            //里程碑
                        case 2:
                            intent.setClass(context, LandMarkActivity.class);
                            break;
                            //质量检查
                        case 3:
                            intent.setClass(context, SafeCheckMainActivity.class);
                            break;
                            //施工日志
                        case 4:
                            intent.setClass(context, WorkingLogActivity.class);
                            break;
                            //工程简报
                        case 5:
                            intent.setClass(context, EngineeringBriefActivity.class);
                            break;
                            //工程周报
                        case 6:
                            intent.setClass(context, WorkWeeklyListActivity.class);
                            break;
                            //安全检查
                        case 7:
                            intent.setClass(context, SafeCheckMainActivity.class);
                            break;
                    }
                } else if(position == 1){
                    switch (i) {
                        //项目立项
                        case 0:
                            intent.setClass(context, ElectronicNoticeActivity.class);
                            break;
                        //设计计划
                        case 1:
                            intent.setClass(context, ElectronicNoticeActivity.class);
                            break;
                        //电子公告
                        case 2:
                            intent.setClass(context, ElectronicNoticeActivity.class);
                            break;
                        //内部新闻
                        case 3:
                            intent.setClass(context, InternalNewsActivity.class);
                            break;
                        //技术规范
                        case 4:
                            intent.setClass(context, TechnicalSpecificationActivity.class);
                            break;
                    }
                }
                context.startActivity(intent);
            }
        });
        container.addView(itemView);
        return itemView;
    }

    //destroyItem()：删除当前的View;  
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private View getItemView(int layoutId) {
        View itemView = LayoutInflater.from(this.context).inflate(layoutId, null, false);
        return itemView;
    }
}
