package com.patrol.terminal.bean;

import java.io.Serializable;

public class ControlCardSafe implements Serializable {

    /**
     * id : C4F4676EC4E64D92BA850631C3A32CB5
     * danger : 高空坠落
     * content : 上塔作业前，必须认真检查安全用具是否完好无损，检查基础、脚钉是否牢固；上塔过程中，手脚必须抓稳踩实，并随时检查脚钉是否牢固；中途需检查螺栓紧固情况时，必须系好安全带后方可开始检查。
     * sort : 1
     * card_safe_id : C4F4676EC4E64D92BA850631C3A32CB5
     */

    private String id;
    private String danger;
    private String content;
    private int sort;
    private String card_safe_id;
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

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
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

    public String getCard_safe_id() {
        return card_safe_id;
    }

    public void setCard_safe_id(String card_safe_id) {
        this.card_safe_id = card_safe_id;
    }
}
