package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class TssxToTaskTrouble implements Serializable {

//             "id":"51E05F9967BF4C099E6F2E55F8656103",
//            "task_id":"73882AA8DE4549F69F4AE83F7A841500",
//            "line_id":"05ADEB82B2234C6E98222DB0DAFE7D10",
//            "towers":null,
//            "start_id":"A502D0C78E0349359E82303F4EDA0EE0",
//            "end_id":"A502D0C78E0349359E82303F4EDA0EE0",
//            "start_name":"#001",
//            "end_name":"#001",
//            "tower_number":null,
//            "type_id":"E1C4D215842044CE82BE1F05704238B0",
//            "find_time":"2019-07-04",
//            "done_time":null,
//            "year":"2019",
//            "month":"4",
//            "day":"10",
//            "status":null,
//            "trouble_level":"危急",
//            "content":"拉棒、拉线被埋并重压，造成拉线张力严重不平衡增大及方向改变（危急）",
//            "towerList":null,
//            "tower_id":null,
//            "tower_name":null,
//            "line_name":"1118西炼线",
//            "type_name":"高速铁路",
//            "owner_name":"邓贵宝",
//            "line_level":"3",
//            "trouble_file":null,
//            "fileList":

    private String id;
    private String task_id;
    private String line_id;
    private String towers;
    private String start_id;
    private String end_id;
    private String start_name;
    private String end_name;
    private String type_id;//特殊属性id
    private String trouble_level;//隐患内容
    private String content;//
    private String type_name;//特殊屬性
    private String line_name;

    private List<TssxToFile> fileList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getTowers() {
        return towers;
    }

    public void setTowers(String towers) {
        this.towers = towers;
    }

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    public String getEnd_id() {
        return end_id;
    }

    public void setEnd_id(String end_id) {
        this.end_id = end_id;
    }

    public String getStart_name() {
        return start_name;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name;
    }

    public String getEnd_name() {
        return end_name;
    }

    public void setEnd_name(String end_name) {
        this.end_name = end_name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getTrouble_level() {
        return trouble_level;
    }

    public void setTrouble_level(String trouble_level) {
        this.trouble_level = trouble_level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public List<TssxToFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<TssxToFile> fileList) {
        this.fileList = fileList;
    }
}
