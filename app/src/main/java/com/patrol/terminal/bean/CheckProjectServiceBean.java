package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CheckProjectServiceBean implements Parcelable {


    /**
     * id : B9D566DD47B643DDB0E93FFD095BA5D1
     * temp_project_id : 6373455434
     * temp_project_name : 项目名字1
     * type_sign : 2
     * time : 2019-08-16 16:50
     * check_user_name : 张三
     * check_project : 检查项...
     * tempCheckResultList : [{"id":"83B710F236394CA38157AD4810CD3EAF","temp_check_id":"B9D566DD47B643DDB0E93FFD095BA5D1","result":"1","content":"检查结果","tempImgList":[{"id":"EE8A510F19364425A8B206B1E8DC1699","data_id":"83B710F236394CA38157AD4810CD3EAF","upload_time":"2019-08-20 14:04:01","filename":"956319bb-51ab-46b7-ba03-2acdbe5ec320.jpg","file_path":"/upload.folder/"},{"id":"3D2113DF144D46E98AA56F9078FD513F","data_id":"83B710F236394CA38157AD4810CD3EAF","upload_time":"2019-08-20 14:05:19","filename":"052272c0-2dec-4c4f-9bf5-65c9ba0048ff.jpg","file_path":"/upload.folder/"}],"files":null},{"id":"7CEA91C3FFFA47888C281E0C4BA0742D","temp_check_id":"B9D566DD47B643DDB0E93FFD095BA5D1","result":"2","content":"检查结果","tempImgList":[],"files":null}]
     */

    private String id;
    private String temp_project_id;
    private String temp_project_name;
    private String type_sign;  //1,2,3,4
    private String time;
    private String check_user_name;
    private String check_project;
    private String state_sign;
    private List<TempCheckResultListBean> tempCheckResultList;

    public String getState_sign() {
        return state_sign;
    }

    public void setState_sign(String state_sign) {
        this.state_sign = state_sign;
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

    public String getType_sign() {
        return type_sign;
    }

    public void setType_sign(String type_sign) {
        this.type_sign = type_sign;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCheck_user_name() {
        return check_user_name;
    }

    public void setCheck_user_name(String check_user_name) {
        this.check_user_name = check_user_name;
    }

    public String getCheck_project() {
        return check_project;
    }

    public void setCheck_project(String check_project) {
        this.check_project = check_project;
    }

    public List<TempCheckResultListBean> getTempCheckResultList() {
        return tempCheckResultList;
    }

    public void setTempCheckResultList(List<TempCheckResultListBean> tempCheckResultList) {
        this.tempCheckResultList = tempCheckResultList;
    }

    public static class TempCheckResultListBean implements Parcelable {

        /**
         * id : 83B710F236394CA38157AD4810CD3EAF
         * temp_check_id : B9D566DD47B643DDB0E93FFD095BA5D1
         * result : 1
         * content : 检查结果
         * tempImgList : [{"id":"EE8A510F19364425A8B206B1E8DC1699","data_id":"83B710F236394CA38157AD4810CD3EAF","upload_time":"2019-08-20 14:04:01","filename":"956319bb-51ab-46b7-ba03-2acdbe5ec320.jpg","file_path":"/upload.folder/"},{"id":"3D2113DF144D46E98AA56F9078FD513F","data_id":"83B710F236394CA38157AD4810CD3EAF","upload_time":"2019-08-20 14:05:19","filename":"052272c0-2dec-4c4f-9bf5-65c9ba0048ff.jpg","file_path":"/upload.folder/"}]
         * files : null
         */

        private String id;
        private String temp_check_id;
        private String result;
        private String content;
        //private Object files;
        private List<TempImgListBean> tempImgList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTemp_check_id() {
            return temp_check_id;
        }

        public void setTemp_check_id(String temp_check_id) {
            this.temp_check_id = temp_check_id;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

//        public Object getFiles() {
//            return files;
//        }
//
//        public void setFiles(Object files) {
//            this.files = files;
//        }

        public List<TempImgListBean> getTempImgList() {
            return tempImgList;
        }

        public void setTempImgList(List<TempImgListBean> tempImgList) {
            this.tempImgList = tempImgList;
        }

        public static class TempImgListBean implements Parcelable {

            /**
             * id : EE8A510F19364425A8B206B1E8DC1699
             * data_id : 83B710F236394CA38157AD4810CD3EAF
             * upload_time : 2019-08-20 14:04:01
             * filename : 956319bb-51ab-46b7-ba03-2acdbe5ec320.jpg
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.data_id);
                dest.writeString(this.upload_time);
                dest.writeString(this.filename);
                dest.writeString(this.file_path);
            }

            public TempImgListBean() {
            }

            protected TempImgListBean(Parcel in) {
                this.id = in.readString();
                this.data_id = in.readString();
                this.upload_time = in.readString();
                this.filename = in.readString();
                this.file_path = in.readString();
            }

            public static final Creator<TempImgListBean> CREATOR = new Creator<TempImgListBean>() {
                @Override
                public TempImgListBean createFromParcel(Parcel source) {
                    return new TempImgListBean(source);
                }

                @Override
                public TempImgListBean[] newArray(int size) {
                    return new TempImgListBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.temp_check_id);
            dest.writeString(this.result);
            dest.writeString(this.content);
            dest.writeTypedList(this.tempImgList);
        }

        public TempCheckResultListBean() {
        }

        protected TempCheckResultListBean(Parcel in) {
            this.id = in.readString();
            this.temp_check_id = in.readString();
            this.result = in.readString();
            this.content = in.readString();
            this.tempImgList = in.createTypedArrayList(TempImgListBean.CREATOR);
        }

        public static final Creator<TempCheckResultListBean> CREATOR = new Creator<TempCheckResultListBean>() {
            @Override
            public TempCheckResultListBean createFromParcel(Parcel source) {
                return new TempCheckResultListBean(source);
            }

            @Override
            public TempCheckResultListBean[] newArray(int size) {
                return new TempCheckResultListBean[size];
            }
        };
    }

    public CheckProjectServiceBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.temp_project_id);
        dest.writeString(this.temp_project_name);
        dest.writeString(this.type_sign);
        dest.writeString(this.time);
        dest.writeString(this.check_user_name);
        dest.writeString(this.check_project);
        dest.writeString(this.state_sign);
        dest.writeList(this.tempCheckResultList);
    }

    protected CheckProjectServiceBean(Parcel in) {
        this.id = in.readString();
        this.temp_project_id = in.readString();
        this.temp_project_name = in.readString();
        this.type_sign = in.readString();
        this.time = in.readString();
        this.check_user_name = in.readString();
        this.check_project = in.readString();
        this.state_sign = in.readString();
        this.tempCheckResultList = new ArrayList<TempCheckResultListBean>();
        in.readList(this.tempCheckResultList, TempCheckResultListBean.class.getClassLoader());
    }

    public static final Creator<CheckProjectServiceBean> CREATOR = new Creator<CheckProjectServiceBean>() {
        @Override
        public CheckProjectServiceBean createFromParcel(Parcel source) {
            return new CheckProjectServiceBean(source);
        }

        @Override
        public CheckProjectServiceBean[] newArray(int size) {
            return new CheckProjectServiceBean[size];
        }
    };
}
