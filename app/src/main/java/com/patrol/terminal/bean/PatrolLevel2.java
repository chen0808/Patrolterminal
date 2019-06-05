package com.patrol.terminal.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.patrol.terminal.adapter.PatrolContentAdapter;

public class PatrolLevel2 extends AbstractExpandableItem<PatrolLevel3> implements MultiItemEntity {
    private String name;
    private String category;
    private String content;
    private String status;
    private String id;
    private String patrol_id;
    private String task_id;

    public PatrolLevel2(String content, String status, String category, String name, String id, String task_id, String patrol_id) {
        this.content = content;
        this.status = status;
        this.category = category;
        this.name = name;
        this.id = id;
        this.task_id = task_id;
        this.patrol_id = patrol_id;
    }

    public String getPatrol_id() {
        return patrol_id;
    }

    public void setPatrol_id(String patrol_id) {
        this.patrol_id = patrol_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PatrolLevel2(String content, String status, String category, String name, String id) {
        this.content = content;
        this.status = status;
        this.category = category;
        this.name = name;
        this.id = id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
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

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public int getItemType() {
        return PatrolContentAdapter.TYPE_2;
    }
}
