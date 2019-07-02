package com.patrol.terminal.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.DepUserBean;
import com.patrol.terminal.utils.RxRefreshEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupTeamView extends LinearLayout {
    @BindView(R.id.view_personal_name)
    TextView viewPersonalName;
    @BindView(R.id.view_group_team)
    LinearLayout viewGroupTeam;
    @BindView(R.id.view_person_icon)
    ImageView viewPersonIcon;

    public GroupTeamView(Context context, DepUserBean depUserBean,int type) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_group_team, this, true);
        ButterKnife.bind(inflate);

        if (depUserBean.isCheck()==true){
            viewPersonalName.setTextColor(context.getResources().getColor(R.color.green));
        }else {
            viewPersonalName.setTextColor(context.getResources().getColor(R.color.my_info));
        }
        viewPersonalName.setText(depUserBean.getName());
        if (type==1){
            viewPersonIcon.setImageResource(R.mipmap.group_icon);
        }else{
            viewPersonIcon.setImageResource(R.mipmap.personal_icon);
        }
        viewGroupTeam.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (depUserBean.isCheck()==true){
                    depUserBean.setCheck(false);
                    viewPersonalName.setTextColor(context.getResources().getColor(R.color.my_info));
                }else {
                    depUserBean.setCheck(true);
                    viewPersonalName.setTextColor(context.getResources().getColor(R.color.green));
                }
                RxRefreshEvent.publishGroup(depUserBean);
            }
        });

    }

}
