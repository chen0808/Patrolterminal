package com.patrol.terminal.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.patrol.terminal.adapter.SpecialContentAdapter;

public class PatrolLevel4 implements MultiItemEntity {
    private String name;
    private String category;
    private String content;
    private boolean tag;
    private String id;

    public PatrolLevel4(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public PatrolLevel4(String name, boolean tag) {
        this.name = name;
        this.tag = tag;
    }

    public PatrolLevel4(String name) {
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
    public int getItemType() {
        return SpecialContentAdapter.TYPE_4;
    }
}
