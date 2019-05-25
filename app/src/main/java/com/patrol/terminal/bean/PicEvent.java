package com.patrol.terminal.bean;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

public class PicEvent {
    private List<LocalMedia> data;

    public PicEvent(List<LocalMedia> data) {
        this.data = data;
    }

    public List<LocalMedia> getData() {
        return data;
    }

    public void setData(List<LocalMedia> data) {
        this.data = data;
    }
}
