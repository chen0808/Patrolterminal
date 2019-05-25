package com.patrol.terminal.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.patrol.terminal.adapter.PatrolContentAdapter;

public class PatrolLevel2 extends AbstractExpandableItem<PatrolLevel3> implements MultiItemEntity {
    private String name;
    private String category;
    private String content;
    private boolean tag;
    private String id;

    public PatrolLevel2(String content, boolean tag, String category, String name, String id) {
        this.content = content;
        this.tag = tag;
        this.category = category;
        this.name = name;
        this.id = id;
    }

    public PatrolLevel2(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public int getItemType() {
        return PatrolContentAdapter.TYPE_2;
    }
}
