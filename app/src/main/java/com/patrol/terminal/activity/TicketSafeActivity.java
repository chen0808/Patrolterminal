package com.patrol.terminal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.patrol.terminal.R;
import com.patrol.terminal.base.BaseActivity;
import com.patrol.terminal.widget.LineView;

public class TicketSafeActivity extends BaseActivity implements View.OnClickListener {

    private LineView lineVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_safe);
        RelativeLayout back = findView(R.id.title_back);
        Button black = findViewById(R.id.btn_black);
        Button red = findViewById(R.id.btn_red);
        Button saveLine = findViewById(R.id.btn_save_line);
        Button tower = findViewById(R.id.btn_tower);
        Button tower2 = findViewById(R.id.btn_tower2);
        Button saveTower = findViewById(R.id.btn_save_tower);
        lineVIew = findViewById(R.id.line_view);
        back.setOnClickListener(this);
        black.setOnClickListener(this);
        red.setOnClickListener(this);
        saveLine.setOnClickListener(this);
        tower.setOnClickListener(this);
        tower2.setOnClickListener(this);
        saveTower.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_black:
                lineVIew.setBlackColor();
                break;
            case R.id.btn_red:
                lineVIew.setRedColor();
                break;
            case R.id.btn_save_line:
                lineVIew.saveLine();
                break;
            case R.id.btn_tower:
                lineVIew.addTower();
                break;
            case R.id.btn_tower2:
                lineVIew.addTower2();
                break;
            case R.id.btn_save_tower:
                lineVIew.saveTower();
                break;
        }
    }
}
