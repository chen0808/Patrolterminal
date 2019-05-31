package com.patrol.terminal.bean;

import java.io.Serializable;

public class ControlCardProject implements Serializable {

    /**
     * id : D84F2A3E35DB46768EEE761D0909D8D3
     * content : 明确工作任务，填写工作票；负责本项工作的安全施工和检修质量。
     * sort : 1
     * card_project_content : D84F2A3E35DB46768EEE761D0909D8D3
     */

    private String id;
    private String content;
    private int sort;
    private String card_project_content;
    private boolean tag;

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCard_project_content() {
        return card_project_content;
    }

    public void setCard_project_content(String card_project_content) {
        this.card_project_content = card_project_content;
    }
}
