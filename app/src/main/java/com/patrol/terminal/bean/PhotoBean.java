package com.patrol.terminal.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PhotoBean implements Serializable{

        /**
         * id : F0A9445DC17E40B6B576E54DAD386796
         * temp_brief_id : 660D24FDA2E3493B8ABD80AAE74CA76B
         * upload_time : 2019-08-20 11:02:20
         * filename : 49d53dcc-71ae-461c-95d8-afa981f46255.jpg
         * file_path : /upload.folder/
         */

        @SerializedName("id")
        public String id;
        public String tempBriefId;
        @SerializedName("upload_time")
        public String uploadTime;
        @SerializedName("filename")
        public String filename;
        @SerializedName("file_path")
        public String filePath;


}
