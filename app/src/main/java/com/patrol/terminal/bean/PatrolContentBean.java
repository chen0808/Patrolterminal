package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class PatrolContentBean implements Serializable {

    /**
     * id : 9B662FA12B3B4CD59440B01208669F03
     * task_id : 2D27F3AE93C04FB6A9653DF5C1FAEC8A
     * patrol_id : 596AB41C09E24C4F975F5D129D009789
     * status : 1
     * name : 沿线环境
     * category : 9DA16BC597404773AA97260131A7DEE9
     * remarks : （1）向线路设施射击、抛掷物体
     * taskDefect : {"id":"A791A9210A0D480D8F3CF2DA42153157","month_line_id":null,"week_id":null,"day_id":null,"group_id":null,"task_id":"2D27F3AE93C04FB6A9653DF5C1FAEC8A","category_id":"9DA16BC597404773AA97260131A7DEE9","grade_id":"10C639F13341484997EE8D955322BE02","patrol_id":"596AB41C09E24C4F975F5D129D009789","content":"缺陷内容1","line_id":"231CE67C06B74C1BBF6FD0597D16D91C","start_name":"#001","end_name":"#001","find_time":null,"deal_notes":null,"status":"0","deal_dep_name":null,"deal_time":null,"auditor":null,"audit_status":"0","week_line_id":null,"day_line_id":null,"month_id":null,"group_list_id":null,"deal_dep_id":null,"start_id":"CBDB702DF552425E82145132A62FF009","end_id":"CBDB702DF552425E82145132A62FF009","line_name":"1119兰西树一线","find_user_id":"4B01F91D1E10479BA898DE45023CF25B","find_user_name":"刘海生","deal_user_id":null,"deal_user_name":null,"remark":null,"find_dep_id":"BBE359C6F29042A1A5AF96F9C1B68ED8","find_dep_name":"西固运维班","category_name":"沿线环境","grade_name":"危急","fileList":[{"id":"78FC7CBA9668413AB745B4B2A701EDD6","data_id":"A791A9210A0D480D8F3CF2DA42153157","filename":"ff5c98ab-017f-41c6-9d28-1f36b8177315.jpg","old_name":"596AB41C09E24C4F975F5D129D009789_0.jpg","file_type":"1","file_path":"/upload.folder/","file_size":"11142","repair_type":"0","content":"596AB41C09E24C4F975F5D129D009789_0.jpg","upload_time":"2019-07-03"}],"user_name":"刘海生","dep_name":"西固运维班","defect_file":null}
     */

    private String id;
    private String task_id;
    private String patrol_id;
    private String status;
    private String name;
    private String category;
    private String remarks;
    private TaskDefectBean taskDefect;

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

    public String getPatrol_id() {
        return patrol_id;
    }

    public void setPatrol_id(String patrol_id) {
        this.patrol_id = patrol_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public TaskDefectBean getTaskDefect() {
        return taskDefect;
    }

    public void setTaskDefect(TaskDefectBean taskDefect) {
        this.taskDefect = taskDefect;
    }

    public static class TaskDefectBean {
        /**
         * id : A791A9210A0D480D8F3CF2DA42153157
         * month_line_id : null
         * week_id : null
         * day_id : null
         * group_id : null
         * task_id : 2D27F3AE93C04FB6A9653DF5C1FAEC8A
         * category_id : 9DA16BC597404773AA97260131A7DEE9
         * grade_id : 10C639F13341484997EE8D955322BE02
         * patrol_id : 596AB41C09E24C4F975F5D129D009789
         * content : 缺陷内容1
         * line_id : 231CE67C06B74C1BBF6FD0597D16D91C
         * start_name : #001
         * end_name : #001
         * find_time : null
         * deal_notes : null
         * status : 0
         * deal_dep_name : null
         * deal_time : null
         * auditor : null
         * audit_status : 0
         * week_line_id : null
         * day_line_id : null
         * month_id : null
         * group_list_id : null
         * deal_dep_id : null
         * start_id : CBDB702DF552425E82145132A62FF009
         * end_id : CBDB702DF552425E82145132A62FF009
         * line_name : 1119兰西树一线
         * find_user_id : 4B01F91D1E10479BA898DE45023CF25B
         * find_user_name : 刘海生
         * deal_user_id : null
         * deal_user_name : null
         * remark : null
         * find_dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
         * find_dep_name : 西固运维班
         * category_name : 沿线环境
         * grade_name : 危急
         * fileList : [{"id":"78FC7CBA9668413AB745B4B2A701EDD6","data_id":"A791A9210A0D480D8F3CF2DA42153157","filename":"ff5c98ab-017f-41c6-9d28-1f36b8177315.jpg","old_name":"596AB41C09E24C4F975F5D129D009789_0.jpg","file_type":"1","file_path":"/upload.folder/","file_size":"11142","repair_type":"0","content":"596AB41C09E24C4F975F5D129D009789_0.jpg","upload_time":"2019-07-03"}]
         * user_name : 刘海生
         * dep_name : 西固运维班
         * defect_file : null
         */

        private String id;
        private Object month_line_id;
        private Object week_id;
        private Object day_id;
        private Object group_id;
        private String task_id;
        private String category_id;
        private String grade_id;
        private String patrol_id;
        private String content;
        private String line_id;
        private String start_name;
        private String end_name;
        private Object find_time;
        private Object deal_notes;
        private String status;
        private Object deal_dep_name;
        private Object deal_time;
        private Object auditor;
        private String audit_status;
        private Object week_line_id;
        private Object day_line_id;
        private Object month_id;
        private Object group_list_id;
        private Object deal_dep_id;
        private String start_id;
        private String end_id;
        private String line_name;
        private String find_user_id;
        private String find_user_name;
        private Object deal_user_id;
        private Object deal_user_name;
        private Object remark;
        private String find_dep_id;
        private String find_dep_name;
        private String category_name;
        private String grade_name;
        private String user_name;
        private String dep_name;
        private Object defect_file;
        private List<FileListBean> fileList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getMonth_line_id() {
            return month_line_id;
        }

        public void setMonth_line_id(Object month_line_id) {
            this.month_line_id = month_line_id;
        }

        public Object getWeek_id() {
            return week_id;
        }

        public void setWeek_id(Object week_id) {
            this.week_id = week_id;
        }

        public Object getDay_id() {
            return day_id;
        }

        public void setDay_id(Object day_id) {
            this.day_id = day_id;
        }

        public Object getGroup_id() {
            return group_id;
        }

        public void setGroup_id(Object group_id) {
            this.group_id = group_id;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(String grade_id) {
            this.grade_id = grade_id;
        }

        public String getPatrol_id() {
            return patrol_id;
        }

        public void setPatrol_id(String patrol_id) {
            this.patrol_id = patrol_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLine_id() {
            return line_id;
        }

        public void setLine_id(String line_id) {
            this.line_id = line_id;
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

        public Object getFind_time() {
            return find_time;
        }

        public void setFind_time(Object find_time) {
            this.find_time = find_time;
        }

        public Object getDeal_notes() {
            return deal_notes;
        }

        public void setDeal_notes(Object deal_notes) {
            this.deal_notes = deal_notes;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getDeal_dep_name() {
            return deal_dep_name;
        }

        public void setDeal_dep_name(Object deal_dep_name) {
            this.deal_dep_name = deal_dep_name;
        }

        public Object getDeal_time() {
            return deal_time;
        }

        public void setDeal_time(Object deal_time) {
            this.deal_time = deal_time;
        }

        public Object getAuditor() {
            return auditor;
        }

        public void setAuditor(Object auditor) {
            this.auditor = auditor;
        }

        public String getAudit_status() {
            return audit_status;
        }

        public void setAudit_status(String audit_status) {
            this.audit_status = audit_status;
        }

        public Object getWeek_line_id() {
            return week_line_id;
        }

        public void setWeek_line_id(Object week_line_id) {
            this.week_line_id = week_line_id;
        }

        public Object getDay_line_id() {
            return day_line_id;
        }

        public void setDay_line_id(Object day_line_id) {
            this.day_line_id = day_line_id;
        }

        public Object getMonth_id() {
            return month_id;
        }

        public void setMonth_id(Object month_id) {
            this.month_id = month_id;
        }

        public Object getGroup_list_id() {
            return group_list_id;
        }

        public void setGroup_list_id(Object group_list_id) {
            this.group_list_id = group_list_id;
        }

        public Object getDeal_dep_id() {
            return deal_dep_id;
        }

        public void setDeal_dep_id(Object deal_dep_id) {
            this.deal_dep_id = deal_dep_id;
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

        public String getLine_name() {
            return line_name;
        }

        public void setLine_name(String line_name) {
            this.line_name = line_name;
        }

        public String getFind_user_id() {
            return find_user_id;
        }

        public void setFind_user_id(String find_user_id) {
            this.find_user_id = find_user_id;
        }

        public String getFind_user_name() {
            return find_user_name;
        }

        public void setFind_user_name(String find_user_name) {
            this.find_user_name = find_user_name;
        }

        public Object getDeal_user_id() {
            return deal_user_id;
        }

        public void setDeal_user_id(Object deal_user_id) {
            this.deal_user_id = deal_user_id;
        }

        public Object getDeal_user_name() {
            return deal_user_name;
        }

        public void setDeal_user_name(Object deal_user_name) {
            this.deal_user_name = deal_user_name;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getFind_dep_id() {
            return find_dep_id;
        }

        public void setFind_dep_id(String find_dep_id) {
            this.find_dep_id = find_dep_id;
        }

        public String getFind_dep_name() {
            return find_dep_name;
        }

        public void setFind_dep_name(String find_dep_name) {
            this.find_dep_name = find_dep_name;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getGrade_name() {
            return grade_name;
        }

        public void setGrade_name(String grade_name) {
            this.grade_name = grade_name;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getDep_name() {
            return dep_name;
        }

        public void setDep_name(String dep_name) {
            this.dep_name = dep_name;
        }

        public Object getDefect_file() {
            return defect_file;
        }

        public void setDefect_file(Object defect_file) {
            this.defect_file = defect_file;
        }

        public List<FileListBean> getFileList() {
            return fileList;
        }

        public void setFileList(List<FileListBean> fileList) {
            this.fileList = fileList;
        }

        public static class FileListBean {
            /**
             * id : 78FC7CBA9668413AB745B4B2A701EDD6
             * data_id : A791A9210A0D480D8F3CF2DA42153157
             * filename : ff5c98ab-017f-41c6-9d28-1f36b8177315.jpg
             * old_name : 596AB41C09E24C4F975F5D129D009789_0.jpg
             * file_type : 1
             * file_path : /upload.folder/
             * file_size : 11142
             * repair_type : 0
             * content : 596AB41C09E24C4F975F5D129D009789_0.jpg
             * upload_time : 2019-07-03
             */

            private String id;
            private String data_id;
            private String filename;
            private String old_name;
            private String file_type;
            private String file_path;
            private String file_size;
            private String repair_type;
            private String content;
            private String upload_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getData_id() {
                return data_id;
            }

            public void setData_id(String data_id) {
                this.data_id = data_id;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getOld_name() {
                return old_name;
            }

            public void setOld_name(String old_name) {
                this.old_name = old_name;
            }

            public String getFile_type() {
                return file_type;
            }

            public void setFile_type(String file_type) {
                this.file_type = file_type;
            }

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }

            public String getFile_size() {
                return file_size;
            }

            public void setFile_size(String file_size) {
                this.file_size = file_size;
            }

            public String getRepair_type() {
                return repair_type;
            }

            public void setRepair_type(String repair_type) {
                this.repair_type = repair_type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUpload_time() {
                return upload_time;
            }

            public void setUpload_time(String upload_time) {
                this.upload_time = upload_time;
            }
        }
    }
}
