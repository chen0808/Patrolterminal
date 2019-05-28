package com.patrol.terminal.bean;

public class PatrolRecordPicBean {

        /**
         * id : F811BF00FF844D4C8D7132474ABCF6F7
         * task_id : 86CC2097F4F74B389087CFDC9C8FC502
         * sign : 1
         * upload_time : 2019-05-28T15:38:43.519
         * filename : 9392e8b6-0f16-4a41-bb70-167ac4d13d21.jpg
         * file_path : /upload.folder/
         */

        private String id;
        private String task_id;
        private String sign;
        private String upload_time;
        private String filename;
        private String file_path;

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

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
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
