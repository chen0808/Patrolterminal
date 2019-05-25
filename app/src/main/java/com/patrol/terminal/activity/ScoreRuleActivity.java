package com.patrol.terminal.activity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.photoview.PhotoView;
import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScoreRuleActivity extends BaseActivity {


    @BindView(R.id.photo_view)
    PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_rule);
        ButterKnife.bind(this);
        Glide.with(this).load(R.mipmap.rule1).into(photoView);
    }
}
