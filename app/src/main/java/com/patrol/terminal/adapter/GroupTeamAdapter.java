package com.patrol.terminal.adapter;

import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.patrol.terminal.R;
import com.patrol.terminal.bean.DepUserBean;
import com.patrol.terminal.bean.GroupTeamBean;
import com.patrol.terminal.widget.GroupTeamView;

import java.util.List;

import androidx.annotation.Nullable;

public class GroupTeamAdapter extends BaseQuickAdapter<GroupTeamBean, BaseViewHolder> {
    private int type = 1;

    public GroupTeamAdapter(int layoutResId, @Nullable List<GroupTeamBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, GroupTeamBean item) {
        viewHolder.addOnClickListener(R.id.item_group_delete);
        LinearLayout personal = viewHolder.getView(R.id.item_group_personal_ll);
        personal.removeAllViews();
        if (item.getTeamList()==null||item.getTeamList().size()==0){
            viewHolder.setText(R.id.iteam_group_name,"小组"+(viewHolder.getPosition()+1));
        }else {
            DepUserBean depUserBean = item.getTeamList().get(0);
            viewHolder.setText(R.id.iteam_group_name,item.getTeamList().get(0).getName()+"的小组");
            GroupTeamView view=new GroupTeamView(mContext,depUserBean,1);
            personal.addView(view,0);
        }
         if (item.getPersonalsList()!=null){
             for (int i = 0; i < item.getPersonalsList().size(); i++) {

                 DepUserBean depUserBean = item.getPersonalsList().get(i);
                 GroupTeamView view=new GroupTeamView(mContext, depUserBean,2);
                 personal.addView(view);
             }
         }

    }
}