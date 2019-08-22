package com.patrol.terminal.bean;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.List;

public class GraphicProgressBean  implements Serializable {

    /**
     * id : 4E3F591435CD4E3A9A5F8AA546DCA504
     * temp_project_id : 58465
     * temp_project_name : 项目名字
     * user_id : 16161616
     * user_name : xxx
     * upload_time : 2019-08-01
     * plan_desc : 施工打个电话费田间地头大家都听人家
     * files : null
     * tempImgList : [{"id":"9C8140295F91442298574E7B70814438","data_id":"4E3F591435CD4E3A9A5F8AA546DCA504","upload_time":"2019-08-21 11:02:42","filename":"46ef10f7-424c-4914-a471-dbf8c1635ddb.jpg","file_path":"/upload.folder/"},{"id":"B2419A29F53D477093307B9FD91556AD","data_id":"4E3F591435CD4E3A9A5F8AA546DCA504","upload_time":"2019-08-21 11:08:16","filename":"03a73094-afaf-46e3-abb3-f3526d8c86f8.jpg","file_path":"/upload.folder/"}]
     */
    private int local_id;
    private String id;
    private String temp_project_id;
    private String temp_project_name;
    private String user_id;
    private String user_name;
    private String upload_time;
    private String plan_desc;
    private String files;
    private List<TempImgListBean> tempImgList;

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemp_project_id() {
        return temp_project_id;
    }

    public void setTemp_project_id(String temp_project_id) {
        this.temp_project_id = temp_project_id;
    }

    public String getTemp_project_name() {
        return temp_project_name;
    }

    public void setTemp_project_name(String temp_project_name) {
        this.temp_project_name = temp_project_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getPlan_desc() {
        return plan_desc;
    }

    public void setPlan_desc(String plan_desc) {
        this.plan_desc = plan_desc;
    }

    public Object getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public List<TempImgListBean> getTempImgList() {
        return tempImgList;
    }

    public void setTempImgList(List<TempImgListBean> tempImgList) {
        this.tempImgList = tempImgList;
    }
    public static class TempImgListBean  implements Serializable{
        /**
         * id : 9C8140295F91442298574E7B70814438
         * data_id : 4E3F591435CD4E3A9A5F8AA546DCA504
         * upload_time : 2019-08-21 11:02:42
         * filename : 46ef10f7-424c-4914-a471-dbf8c1635ddb.jpg
         * file_path : /upload.folder/
         */

        private String id;

        private String data_id;

        private String upload_time;

        private String filename;

        private String file_path;



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

        public String getUpload_time() {
            return upload_time;
        }

        public void setUpload_time(String upload_time) {
            this.upload_time = upload_time;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }
    }

}
