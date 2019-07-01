package com.patrol.terminal.bean;

import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

public class LocalPatrolTroubleBean extends BaseModel implements Serializable {
    @PrimaryKey(autoincrement = true)
    private int local_id;

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }
}
