package com.patrol.terminal.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MyMultipleItem implements MultiItemEntity {
    public static final int FIRST_TYPE = 1;
    public static final int SECOND_TYPE = 2;
    public static final int NORMAL_TYPE = 3;

    private int itemType;
    private Model data;

    public MyMultipleItem(int itemType, Model data) {
        this.itemType = itemType;
        this.data = data;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public Model getData() {
        return data;
    }
}
