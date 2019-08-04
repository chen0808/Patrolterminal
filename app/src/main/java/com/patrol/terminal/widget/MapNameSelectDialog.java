package com.patrol.terminal.widget;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.activity.NextMonthPlanActivity;
import com.patrol.terminal.adapter.BaseGridViewAdapter;
import com.patrol.terminal.adapter.GridViewAdapter;
import com.patrol.terminal.adapter.MapClassGridViewAdapter;
import com.patrol.terminal.base.BaseObserver;
import com.patrol.terminal.base.BaseRequest;
import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.base.CallBack;
import com.patrol.terminal.bean.DepBean;
import com.patrol.terminal.bean.DepPersonalBean;
import com.patrol.terminal.bean.MapUserInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MapNameSelectDialog extends PopupWindow {
    private View mainView;
    private GridView classGridView, nameGridView;
    private BaseGridViewAdapter nameGridViewAdapter;
    private static PopWindowItemClick itemClick;
    private List<DepPersonalBean.UserListBean> classMemberList = new ArrayList<>();

    public MapNameSelectDialog(Activity paramActivity, /*View.OnClickListener paramOnClickListener, int paramInt1, int paramInt2,*/ List<DepBean> depBeanList, List<DepPersonalBean.UserListBean> classMemList){
        super(paramActivity);
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.popwindow_map_name_select, null);
        classGridView = ((GridView)mainView.findViewById(R.id.class_gridview));
        nameGridView = ((GridView)mainView.findViewById(R.id.name_gridview));

        MapClassGridViewAdapter mapClassGridViewAdapter = new MapClassGridViewAdapter(paramActivity, depBeanList);
        classGridView.setAdapter(mapClassGridViewAdapter);
        classGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int clickPos, long l) {
                mapClassGridViewAdapter.setClickPos(clickPos);
                //点击后切换班级人员数据
                getClassMember(depBeanList.get(clickPos).getId());

            }
        });

        classMemberList = classMemList;
        nameGridViewAdapter = new BaseGridViewAdapter(paramActivity, classMemList);
        nameGridView.setAdapter(nameGridViewAdapter);
        nameGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //选中一个人员便查询选中人员的轨迹    TODO
                itemClick.setNameAndId(classMemberList.get(i).getName(), classMemberList.get(i).getId());

            }
        });



        setContentView(mainView);
        //设置宽度
        setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        //设置高度
        setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        //设置显示隐藏动画
        setAnimationStyle(R.style.AnimTools);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }

//    public void setTitle(String str1,String str2){
//        all.setText(str1);
//        layoutShare.setText(str2);
//        layoutCopy.setVisibility(View.GONE);
//    }


    private void getClassMember(String depId) {
        BaseRequest.getInstance().getService()
                .getDepPersonal(depId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DepPersonalBean>() {
                    @Override
                    protected void onSuccees(BaseResult<DepPersonalBean> t) throws Exception {
                        classMemberList = t.getResults().getUserList();
                        nameGridViewAdapter.setData(classMemberList);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    public void setCallback(PopWindowItemClick popWindowItemClick) {
        itemClick = popWindowItemClick;
    }

    public interface PopWindowItemClick {
        void setNameAndId(String name, String id);
    }


}


