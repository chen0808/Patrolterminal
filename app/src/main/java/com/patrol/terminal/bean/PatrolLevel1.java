package com.patrol.terminal.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.patrol.terminal.adapter.PatrolContentAdapter;

public class PatrolLevel1 extends AbstractExpandableItem<PatrolLevel2> implements MultiItemEntity {

    private String title;
    private boolean tag;

    public PatrolLevel1(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getItemType() {
        return PatrolContentAdapter.TYPE_1;
    }
}
