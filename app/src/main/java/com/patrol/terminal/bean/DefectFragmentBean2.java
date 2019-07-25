package com.patrol.terminal.bean;

import java.io.Serializable;

public class DefectFragmentBean2 implements Serializable {

    /**能收到的所有字段
     * "id": "C3DA178EF8BE40CCB8643654A55FFFDA",
     *             "task_id": "14171CAEE3324DD9BFFFD640C7EBD913",
     *             "category_id": null,
     *             "category_name": "导线、地线",
     *             "grade_id": "10C639F13341484997EE8D955322BE02",
     *             "grade_name": "危急",
     *             "patrol_id": "6E57D7EBDA464CEA8DB3CA2D1806C2EB",
     *             "patrol_name": "（1）导线、地线锈蚀、断股、损伤或闪络烧伤",
     *             "content": " 基础严重变形,造成杆塔严重上拔微倾斜、位移",
     *             "close_time": null,
     *             "line_id": "3B14083B22604672B96A907699F28FE2",
     *             "line_name": "3513华阿一线",
     *             "tower_id": "2419E817CB554AC69F7AF1D515B27343",
     *             "tower_name": "#029",
     *             "find_time": "2019-07-25",
     *             "find_user_id": "9C9164DB3CFA4AACB4DE8D54BE198541",
     *             "find_user_name": "马宝龙",
     *             "find_dep_id": "024CD2E91A8447A799335E21B0FDADB6",
     *             "find_dep_name": "榆中运维班",
     *             "deal_notes": "喷涂",
     *             "deal_user_id": null,
     *             "deal_user_name": null,
     *             "deal_dep_id": null,
     *             "deal_dep_name": null,
     *             "deal_time": null,
     *             "done_status": "0",
     *             "in_status": "2",
     *             "remark": null,
     *             "defect_file": null,
     *             "fileList": [
     *                 {
     *                     "id": "9882E9CFD6374CA59EC561579279B0BD",
     *                     "task_id": "14171CAEE3324DD9BFFFD640C7EBD913",
     *                     "task_defect_id": "C3DA178EF8BE40CCB8643654A55FFFDA",
     *                     "upload_time": "2019-07-25 11:02:45",
     *                     "filename": "37f6c92a-43a6-499d-a4b0-e84ef26a71ac.jpg",
     *                     "file_path": "/upload.folder/"
     *                 }
     *             ],
     *             "from_user_id": null,
     *             "from_user_name": null
     */

    private String start_name;
    private String find_time;
    private String category_name;
    private String line_name;
    private String end_name;
    private String id;
    private String content;
    private String tower_name;

    public String getTower_name() {
        return tower_name;
    }

    public void setTower_name(String tower_name) {
        this.tower_name = tower_name;
    }

    public String getStart_name() {
        return start_name;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name;
    }

    public String getFind_time() {
        return find_time;
    }

    public void setFind_time(String find_time) {
        this.find_time = find_time;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getEnd_name() {
        return end_name;
    }

    public void setEnd_name(String end_name) {
        this.end_name = end_name;
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
}
