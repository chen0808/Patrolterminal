package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class AllControlCarBean implements Parcelable {


    /**
     * workTools : [{"id":"DF6A4D96D613487494478D62B8571AC6","name":"dwqd","type":"edew","unit":"edewd","total":"wdwe","detail":"dwd","tool_type":"0","task_id":"F2597AA5F9B7414EA9BB8FC4C3637BD5"},{"id":"027C642CF7A04293ABE8C4C020BD4BDC","name":"dede","type":"fcasc","unit":"sdc","total":"cdsc","detail":"cdsc","tool_type":"0","task_id":"F2597AA5F9B7414EA9BB8FC4C3637BD5"},{"id":"0413C5461ECF4FADBFB8A61F3ABFB621","name":"ewd","type":"sc","unit":"sd","total":"cddsc","detail":"cdsccds","tool_type":"1","task_id":"F2597AA5F9B7414EA9BB8FC4C3637BD5"},{"id":"B24025E9FF5F4562AA8CDB97369C68D6","name":"dewde","type":"csdc","unit":"sdc","total":"cdscsd","detail":"scds","tool_type":"1","task_id":"F2597AA5F9B7414EA9BB8FC4C3637BD5"}]
     * workControlCard : {"id":"5F732A68483549A1803FCA47030F14E9","serial":null,"task_id":"F2597AA5F9B7414EA9BB8FC4C3637BD5","name":"3、断开1116和开一线31#塔三相引流。","start_time":"2019-5-7","end_time":"2019-5-9","leader":"叶怀刚","workProjectUsers":[{"id":"7A10A0AE6C834E518D50AA81A97AD121","u_id":"8085FCC8ACC84B5C82F2B8ADE40DB279","w_p_id":"33D92E685ECF4D988A3579A885CE2B76","w_c_id":"5F732A68483549A1803FCA47030F14E9","work_project_name":"明确工作任务，填写工作票；负责本项工作的安全施工和检修质量。","user_name":"李嘉辰"},{"id":"946B7843BEB34BBFB41474F08AE22CA0","u_id":"F9C9737CD8DF427586D96C5A9A9795A1","w_p_id":"B636FDC6F5654C9682B39C04B5600D23","w_c_id":"5F732A68483549A1803FCA47030F14E9","work_project_name":"登杆塔高处作业，安装视频监控设。","user_name":"贾来强"},{"id":"EBCFED46B2E34A3E9136474F5718D666","u_id":"C55FE53CAB524DE3A5F93737F40E747F","w_p_id":"35E457D301574B0594980AC3DB864336","w_c_id":"5F732A68483549A1803FCA47030F14E9","work_project_name":"工器具及材料准备，负责地面与高处作业人员的配合（工具、材料的传递）。","user_name":"安泰康"}],"workSafeRelations":[{"id":"5DC32AA63798400995353E41A267A63C","w_s_id":"1281DBC065EC4975AE3BD5D6A26DF1EB","w_c_id":"5F732A68483549A1803FCA47030F14E9","respon":"8085FCC8ACC84B5C82F2B8ADE40DB279","danger":"高空坠落","content":"高处作业必须使用安全带，安全带应系在杆塔牢固的构件上，系好安全带后必须检查扣环是否扣好；杆塔上作业转位时不得失去后备保护绳的保护。","user_name":"李嘉辰"},{"id":"86CD1E72468B4470A7EBF9E6D026E7EC","w_s_id":"5FF4FAF2B063419AA9D760C63BCBD8A6","w_c_id":"5F732A68483549A1803FCA47030F14E9","respon":"8085FCC8ACC84B5C82F2B8ADE40DB279","danger":"误登杆塔","content":"登杆塔前必须仔细核对线路双重名称、杆塔号，确认无误后方可攀登。","user_name":"李嘉辰"},{"id":"7554A8ECCADD4C27A14D786F917A0209","w_s_id":"9E9B852345C84B828FA5D9039DED42E9","w_c_id":"5F732A68483549A1803FCA47030F14E9","respon":"8085FCC8ACC84B5C82F2B8ADE40DB279","danger":"落物伤人","content":"高处作业应使用工具袋，所使用的工器具、材料应放置稳妥，防止坠落伤人。工器具及材料应用绳索绑牢传递，严禁上下抛掷。作业人员不得站在作业处的垂直下方，高空作业区内不得有无关人员通行或逗留。","user_name":"李嘉辰"}]}
     * workQualityCard : {"id":"9F521AC0F336466BA308C3762E9893EF","task_id":"F2597AA5F9B7414EA9BB8FC4C3637BD5","remark":"saas","repair_content":"3、断开1116和开一线31#塔三相引流。","leader":"叶怀刚","start_time":"2019-5-7","end_time":"2019-5-9","dep_name":null,"workers":"王小龙,李嘉辰","workStandardRelations":[{"id":"7D87FBC9D10F49D88799AB35BC5C253B","w_q_c_id":"9F521AC0F336466BA308C3762E9893EF","w_q_s_id":"EA05E8938C4D4DD486573C31A605CDBE","status":"asdsa","process":"核对现场","standard":"持票核对线路\u201c三号\u201d。","warning":"误登杆塔"},{"id":"E316517739584820B2C79C14EEB3C811","w_q_c_id":"9F521AC0F336466BA308C3762E9893EF","w_q_s_id":"B39DEB8557364D4986BA673FD2909934","status":"ada","process":"安全用具及工器具检查","standard":"安全带无破损，扣环无卡涩现象，且配件齐全；绝缘绳索无破损及断股现象;","warning":"高空坠落"},{"id":"F553B73521DD46BCB86BFB26A4375783","w_q_c_id":"9F521AC0F336466BA308C3762E9893EF","w_q_s_id":"FD6664DB916A481DBA26D3D758E91A08","status":"ada","process":"攀登杆塔","standard":"检查杆根、爬梯、脚钉完好牢固。","warning":"高空坠落"},{"id":"1D91F95DC5354BC8BFD400FD766B1B53","w_q_c_id":"9F521AC0F336466BA308C3762E9893EF","w_q_s_id":"41B82A355C9844D7A5D3DC9ED5C483B0","status":"adsa","process":"抛扔绝缘绳","standard":"杆塔上工作人员安装视频监控设备时手脚动作不得过大。杆上工作人员抛至绳索时手脚动作不得过大；","warning":"高空坠落触电"}]}
     */

    private WorkControlCardBean workControlCard;
    private WorkQualityCardBean workQualityCard;
    private List<WorkToolsBean> workTools;

    public WorkControlCardBean getWorkControlCard() {
        return workControlCard;
    }

    public void setWorkControlCard(WorkControlCardBean workControlCard) {
        this.workControlCard = workControlCard;
    }

    public WorkQualityCardBean getWorkQualityCard() {
        return workQualityCard;
    }

    public void setWorkQualityCard(WorkQualityCardBean workQualityCard) {
        this.workQualityCard = workQualityCard;
    }

    public List<WorkToolsBean> getWorkTools() {
        return workTools;
    }

    public void setWorkTools(List<WorkToolsBean> workTools) {
        this.workTools = workTools;
    }

    public static class WorkControlCardBean implements Parcelable {


        /**
         * id : 5F732A68483549A1803FCA47030F14E9
         * serial : null
         * task_id : F2597AA5F9B7414EA9BB8FC4C3637BD5
         * name : 3、断开1116和开一线31#塔三相引流。
         * start_time : 2019-5-7
         * end_time : 2019-5-9
         * leader : 叶怀刚
         * workProjectUsers : [{"id":"7A10A0AE6C834E518D50AA81A97AD121","u_id":"8085FCC8ACC84B5C82F2B8ADE40DB279","w_p_id":"33D92E685ECF4D988A3579A885CE2B76","w_c_id":"5F732A68483549A1803FCA47030F14E9","work_project_name":"明确工作任务，填写工作票；负责本项工作的安全施工和检修质量。","user_name":"李嘉辰"},{"id":"946B7843BEB34BBFB41474F08AE22CA0","u_id":"F9C9737CD8DF427586D96C5A9A9795A1","w_p_id":"B636FDC6F5654C9682B39C04B5600D23","w_c_id":"5F732A68483549A1803FCA47030F14E9","work_project_name":"登杆塔高处作业，安装视频监控设。","user_name":"贾来强"},{"id":"EBCFED46B2E34A3E9136474F5718D666","u_id":"C55FE53CAB524DE3A5F93737F40E747F","w_p_id":"35E457D301574B0594980AC3DB864336","w_c_id":"5F732A68483549A1803FCA47030F14E9","work_project_name":"工器具及材料准备，负责地面与高处作业人员的配合（工具、材料的传递）。","user_name":"安泰康"}]
         * workSafeRelations : [{"id":"5DC32AA63798400995353E41A267A63C","w_s_id":"1281DBC065EC4975AE3BD5D6A26DF1EB","w_c_id":"5F732A68483549A1803FCA47030F14E9","respon":"8085FCC8ACC84B5C82F2B8ADE40DB279","danger":"高空坠落","content":"高处作业必须使用安全带，安全带应系在杆塔牢固的构件上，系好安全带后必须检查扣环是否扣好；杆塔上作业转位时不得失去后备保护绳的保护。","user_name":"李嘉辰"},{"id":"86CD1E72468B4470A7EBF9E6D026E7EC","w_s_id":"5FF4FAF2B063419AA9D760C63BCBD8A6","w_c_id":"5F732A68483549A1803FCA47030F14E9","respon":"8085FCC8ACC84B5C82F2B8ADE40DB279","danger":"误登杆塔","content":"登杆塔前必须仔细核对线路双重名称、杆塔号，确认无误后方可攀登。","user_name":"李嘉辰"},{"id":"7554A8ECCADD4C27A14D786F917A0209","w_s_id":"9E9B852345C84B828FA5D9039DED42E9","w_c_id":"5F732A68483549A1803FCA47030F14E9","respon":"8085FCC8ACC84B5C82F2B8ADE40DB279","danger":"落物伤人","content":"高处作业应使用工具袋，所使用的工器具、材料应放置稳妥，防止坠落伤人。工器具及材料应用绳索绑牢传递，严禁上下抛掷。作业人员不得站在作业处的垂直下方，高空作业区内不得有无关人员通行或逗留。","user_name":"李嘉辰"}]
         */

        private String id;
        private String serial;
        private String check_task_id;
        //private String name;
        private String start_time;
        private String end_time;
        private String duty_user_id;
        private String duty_user_name;
        private List<WorkProjectUsersBean> workProjectUsers;
        private List<WorkSafeRelationsBean> workSafeUsers;
        private SysFile sysFile;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSerial() {
            return serial;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

        public String getCheck_task_id() {
            return check_task_id;
        }

        public void setCheck_task_id(String check_task_id) {
            this.check_task_id = check_task_id;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getDuty_user_id() {
            return duty_user_id;
        }

        public void setDuty_user_id(String duty_user_id) {
            this.duty_user_id = duty_user_id;
        }

        public String getDuty_user_name() {
            return duty_user_name;
        }

        public void setDuty_user_name(String duty_user_name) {
            this.duty_user_name = duty_user_name;
        }

        public List<WorkProjectUsersBean> getWorkProjectUsers() {
            return workProjectUsers;
        }

        public void setWorkProjectUsers(List<WorkProjectUsersBean> workProjectUsers) {
            this.workProjectUsers = workProjectUsers;
        }

        public List<WorkSafeRelationsBean> getWorkSafeUsers() {
            return workSafeUsers;
        }

        public void setWorkSafeUsers(List<WorkSafeRelationsBean> workSafeUsers) {
            this.workSafeUsers = workSafeUsers;
        }

        public SysFile getSysFile() {
            return sysFile;
        }

        public void setSysFile(SysFile sysFile) {
            this.sysFile = sysFile;
        }

        public static class SysFile implements Parcelable {

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.data_id);
                dest.writeString(this.filename);
                dest.writeString(this.old_name);
                dest.writeString(this.file_type);
                dest.writeString(this.file_path);
                dest.writeString(this.file_size);
                dest.writeString(this.repair_type);
                dest.writeString(this.content);
                dest.writeString(this.upload_time);
            }

            public SysFile() {
            }

            protected SysFile(Parcel in) {
                this.id = in.readString();
                this.data_id = in.readString();
                this.filename = in.readString();
                this.old_name = in.readString();
                this.file_type = in.readString();
                this.file_path = in.readString();
                this.file_size = in.readString();
                this.repair_type = in.readString();
                this.content = in.readString();
                this.upload_time = in.readString();
            }

            public static final Creator<SysFile> CREATOR = new Creator<SysFile>() {
                @Override
                public SysFile createFromParcel(Parcel source) {
                    return new SysFile(source);
                }

                @Override
                public SysFile[] newArray(int size) {
                    return new SysFile[size];
                }
            };
        }

        public static Creator<WorkControlCardBean> getCREATOR() {
            return CREATOR;
        }

        public static class WorkProjectUsersBean implements Parcelable {


            /**
             * id : 7A10A0AE6C834E518D50AA81A97AD121
             * u_id : 8085FCC8ACC84B5C82F2B8ADE40DB279
             * w_p_id : 33D92E685ECF4D988A3579A885CE2B76
             * w_c_id : 5F732A68483549A1803FCA47030F14E9
             * work_project_name : 明确工作任务，填写工作票；负责本项工作的安全施工和检修质量。
             * user_name : 李嘉辰
             */

            private String id;
            private String user_id;
            private String work_project_id;
            private String work_control_card_id;
            private String work_project_name;
            private String user_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getWork_project_id() {
                return work_project_id;
            }

            public void setWork_project_id(String work_project_id) {
                this.work_project_id = work_project_id;
            }

            public String getWork_control_card_id() {
                return work_control_card_id;
            }

            public void setWork_control_card_id(String work_control_card_id) {
                this.work_control_card_id = work_control_card_id;
            }

            public String getWork_project_name() {
                return work_project_name;
            }

            public void setWork_project_name(String work_project_name) {
                this.work_project_name = work_project_name;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public static Creator<WorkProjectUsersBean> getCREATOR() {
                return CREATOR;
            }

            public WorkProjectUsersBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.user_id);
                dest.writeString(this.work_project_id);
                dest.writeString(this.work_control_card_id);
                dest.writeString(this.work_project_name);
                dest.writeString(this.user_name);
            }

            protected WorkProjectUsersBean(Parcel in) {
                this.id = in.readString();
                this.user_id = in.readString();
                this.work_project_id = in.readString();
                this.work_control_card_id = in.readString();
                this.work_project_name = in.readString();
                this.user_name = in.readString();
            }

            public static final Creator<WorkProjectUsersBean> CREATOR = new Creator<WorkProjectUsersBean>() {
                @Override
                public WorkProjectUsersBean createFromParcel(Parcel source) {
                    return new WorkProjectUsersBean(source);
                }

                @Override
                public WorkProjectUsersBean[] newArray(int size) {
                    return new WorkProjectUsersBean[size];
                }
            };
        }

        public static class WorkSafeRelationsBean implements Parcelable {


            /**
             * id : 5DC32AA63798400995353E41A267A63C
             * w_s_id : 1281DBC065EC4975AE3BD5D6A26DF1EB
             * w_c_id : 5F732A68483549A1803FCA47030F14E9
             * respon : 8085FCC8ACC84B5C82F2B8ADE40DB279
             * danger : 高空坠落
             * content : 高处作业必须使用安全带，安全带应系在杆塔牢固的构件上，系好安全带后必须检查扣环是否扣好；杆塔上作业转位时不得失去后备保护绳的保护。
             * user_name : 李嘉辰
             */

            private String id;
            private String work_safe_id;
            private String work_control_card_id;
            private String respon_id;
            private String danger;
            private String content;
            private String respon_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getWork_safe_id() {
                return work_safe_id;
            }

            public void setWork_safe_id(String work_safe_id) {
                this.work_safe_id = work_safe_id;
            }

            public String getWork_control_card_id() {
                return work_control_card_id;
            }

            public void setWork_control_card_id(String work_control_card_id) {
                this.work_control_card_id = work_control_card_id;
            }

            public String getRespon_id() {
                return respon_id;
            }

            public void setRespon_id(String respon_id) {
                this.respon_id = respon_id;
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

            public String getRespon_name() {
                return respon_name;
            }

            public void setRespon_name(String respon_name) {
                this.respon_name = respon_name;
            }

            public static Creator<WorkSafeRelationsBean> getCREATOR() {
                return CREATOR;
            }

            public WorkSafeRelationsBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.work_safe_id);
                dest.writeString(this.work_control_card_id);
                dest.writeString(this.respon_id);
                dest.writeString(this.danger);
                dest.writeString(this.content);
                dest.writeString(this.respon_name);
            }

            protected WorkSafeRelationsBean(Parcel in) {
                this.id = in.readString();
                this.work_safe_id = in.readString();
                this.work_control_card_id = in.readString();
                this.respon_id = in.readString();
                this.danger = in.readString();
                this.content = in.readString();
                this.respon_name = in.readString();
            }

            public static final Creator<WorkSafeRelationsBean> CREATOR = new Creator<WorkSafeRelationsBean>() {
                @Override
                public WorkSafeRelationsBean createFromParcel(Parcel source) {
                    return new WorkSafeRelationsBean(source);
                }

                @Override
                public WorkSafeRelationsBean[] newArray(int size) {
                    return new WorkSafeRelationsBean[size];
                }
            };
        }

        public WorkControlCardBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.serial);
            dest.writeString(this.check_task_id);
            dest.writeString(this.start_time);
            dest.writeString(this.end_time);
            dest.writeString(this.duty_user_id);
            dest.writeString(this.duty_user_name);
            dest.writeTypedList(this.workProjectUsers);
            dest.writeTypedList(this.workSafeUsers);
            dest.writeParcelable(this.sysFile, flags);
        }

        protected WorkControlCardBean(Parcel in) {
            this.id = in.readString();
            this.serial = in.readString();
            this.check_task_id = in.readString();
            this.start_time = in.readString();
            this.end_time = in.readString();
            this.duty_user_id = in.readString();
            this.duty_user_name = in.readString();
            this.workProjectUsers = in.createTypedArrayList(WorkProjectUsersBean.CREATOR);
            this.workSafeUsers = in.createTypedArrayList(WorkSafeRelationsBean.CREATOR);
            this.sysFile = in.readParcelable(SysFile.class.getClassLoader());
        }

        public static final Creator<WorkControlCardBean> CREATOR = new Creator<WorkControlCardBean>() {
            @Override
            public WorkControlCardBean createFromParcel(Parcel source) {
                return new WorkControlCardBean(source);
            }

            @Override
            public WorkControlCardBean[] newArray(int size) {
                return new WorkControlCardBean[size];
            }
        };
    }

    public static class WorkQualityCardBean implements Parcelable {

        private String id;
        private String check_task_id;
        private String remark;
        private String duty_user_id;
        private String duty_user_name;
        private List<WorkStandardRelationsBean> workStandardStatuses;
        private SysFile sysFile;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCheck_task_id() {
            return check_task_id;
        }

        public void setCheck_task_id(String check_task_id) {
            this.check_task_id = check_task_id;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getDuty_user_id() {
            return duty_user_id;
        }

        public void setDuty_user_id(String duty_user_id) {
            this.duty_user_id = duty_user_id;
        }

        public String getDuty_user_name() {
            return duty_user_name;
        }

        public void setDuty_user_name(String duty_user_name) {
            this.duty_user_name = duty_user_name;
        }

        public List<WorkStandardRelationsBean> getWorkStandardStatuses() {
            return workStandardStatuses;
        }

        public void setWorkStandardStatuses(List<WorkStandardRelationsBean> workStandardStatuses) {
            this.workStandardStatuses = workStandardStatuses;
        }

        public SysFile getSysFile() {
            return sysFile;
        }

        public void setSysFile(SysFile sysFile) {
            this.sysFile = sysFile;
        }

        public static class SysFile implements Parcelable {

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.data_id);
                dest.writeString(this.filename);
                dest.writeString(this.old_name);
                dest.writeString(this.file_type);
                dest.writeString(this.file_path);
                dest.writeString(this.file_size);
                dest.writeString(this.repair_type);
                dest.writeString(this.content);
                dest.writeString(this.upload_time);
            }

            public SysFile() {
            }

            protected SysFile(Parcel in) {
                this.id = in.readString();
                this.data_id = in.readString();
                this.filename = in.readString();
                this.old_name = in.readString();
                this.file_type = in.readString();
                this.file_path = in.readString();
                this.file_size = in.readString();
                this.repair_type = in.readString();
                this.content = in.readString();
                this.upload_time = in.readString();
            }

            public static final Creator<SysFile> CREATOR = new Creator<SysFile>() {
                @Override
                public SysFile createFromParcel(Parcel source) {
                    return new SysFile(source);
                }

                @Override
                public SysFile[] newArray(int size) {
                    return new SysFile[size];
                }
            };
        }

        public static Creator<WorkQualityCardBean> getCREATOR() {
            return CREATOR;
        }

        public static class WorkStandardRelationsBean implements Parcelable {

            /**
             * id : 7D87FBC9D10F49D88799AB35BC5C253B
             * w_q_c_id : 9F521AC0F336466BA308C3762E9893EF
             * w_q_s_id : EA05E8938C4D4DD486573C31A605CDBE
             * status : asdsa
             * process : 核对现场
             * standard : 持票核对线路“三号”。
             * warning : 误登杆塔
             */

            private String id;
            private String work_quality_card_id;
            private String work_standard_id;
            private String status;
            private String process;
            private String standard;
            private String warning;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getWork_quality_card_id() {
                return work_quality_card_id;
            }

            public void setWork_quality_card_id(String work_quality_card_id) {
                this.work_quality_card_id = work_quality_card_id;
            }

            public String getWork_standard_id() {
                return work_standard_id;
            }

            public void setWork_standard_id(String work_standard_id) {
                this.work_standard_id = work_standard_id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getProcess() {
                return process;
            }

            public void setProcess(String process) {
                this.process = process;
            }

            public String getStandard() {
                return standard;
            }

            public void setStandard(String standard) {
                this.standard = standard;
            }

            public String getWarning() {
                return warning;
            }

            public void setWarning(String warning) {
                this.warning = warning;
            }

            public static Creator<WorkStandardRelationsBean> getCREATOR() {
                return CREATOR;
            }

            public WorkStandardRelationsBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.work_quality_card_id);
                dest.writeString(this.work_standard_id);
                dest.writeString(this.status);
                dest.writeString(this.process);
                dest.writeString(this.standard);
                dest.writeString(this.warning);
            }

            protected WorkStandardRelationsBean(Parcel in) {
                this.id = in.readString();
                this.work_quality_card_id = in.readString();
                this.work_standard_id = in.readString();
                this.status = in.readString();
                this.process = in.readString();
                this.standard = in.readString();
                this.warning = in.readString();
            }

            public static final Creator<WorkStandardRelationsBean> CREATOR = new Creator<WorkStandardRelationsBean>() {
                @Override
                public WorkStandardRelationsBean createFromParcel(Parcel source) {
                    return new WorkStandardRelationsBean(source);
                }

                @Override
                public WorkStandardRelationsBean[] newArray(int size) {
                    return new WorkStandardRelationsBean[size];
                }
            };
        }

        public WorkQualityCardBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.check_task_id);
            dest.writeString(this.remark);
            dest.writeString(this.duty_user_id);
            dest.writeString(this.duty_user_name);
            dest.writeTypedList(this.workStandardStatuses);
            dest.writeParcelable(this.sysFile, flags);
        }

        protected WorkQualityCardBean(Parcel in) {
            this.id = in.readString();
            this.check_task_id = in.readString();
            this.remark = in.readString();
            this.duty_user_id = in.readString();
            this.duty_user_name = in.readString();
            this.workStandardStatuses = in.createTypedArrayList(WorkStandardRelationsBean.CREATOR);
            this.sysFile = in.readParcelable(SysFile.class.getClassLoader());
        }

        public static final Creator<WorkQualityCardBean> CREATOR = new Creator<WorkQualityCardBean>() {
            @Override
            public WorkQualityCardBean createFromParcel(Parcel source) {
                return new WorkQualityCardBean(source);
            }

            @Override
            public WorkQualityCardBean[] newArray(int size) {
                return new WorkQualityCardBean[size];
            }
        };
    }

    public static class WorkToolsBean implements Parcelable {

        /**
         * id : DF6A4D96D613487494478D62B8571AC6
         * name : dwqd
         * type : edew
         * unit : edewd
         * total : wdwe
         * detail : dwd
         * tool_type : 0
         * task_id : F2597AA5F9B7414EA9BB8FC4C3637BD5
         */

        private String id;
        private String tool_id;
        private String tool_name;
        private String type;
        private String unit;
        private String total;
        private String detail;
        private String tool_type;
        private String check_task_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTool_id() {
            return tool_id;
        }

        public void setTool_id(String tool_id) {
            this.tool_id = tool_id;
        }

        public String getTool_name() {
            return tool_name;
        }

        public void setTool_name(String tool_name) {
            this.tool_name = tool_name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getTool_type() {
            return tool_type;
        }

        public void setTool_type(String tool_type) {
            this.tool_type = tool_type;
        }

        public String getCheck_task_id() {
            return check_task_id;
        }

        public void setCheck_task_id(String check_task_id) {
            this.check_task_id = check_task_id;
        }

        public static Creator<WorkToolsBean> getCREATOR() {
            return CREATOR;
        }

        public WorkToolsBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.tool_id);
            dest.writeString(this.tool_name);
            dest.writeString(this.type);
            dest.writeString(this.unit);
            dest.writeString(this.total);
            dest.writeString(this.detail);
            dest.writeString(this.tool_type);
            dest.writeString(this.check_task_id);
        }

        protected WorkToolsBean(Parcel in) {
            this.id = in.readString();
            this.tool_id = in.readString();
            this.tool_name = in.readString();
            this.type = in.readString();
            this.unit = in.readString();
            this.total = in.readString();
            this.detail = in.readString();
            this.tool_type = in.readString();
            this.check_task_id = in.readString();
        }

        public static final Creator<WorkToolsBean> CREATOR = new Creator<WorkToolsBean>() {
            @Override
            public WorkToolsBean createFromParcel(Parcel source) {
                return new WorkToolsBean(source);
            }

            @Override
            public WorkToolsBean[] newArray(int size) {
                return new WorkToolsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.workControlCard, flags);
        dest.writeParcelable(this.workQualityCard, flags);
        dest.writeList(this.workTools);
    }

    public AllControlCarBean() {
    }

    protected AllControlCarBean(Parcel in) {
        this.workControlCard = in.readParcelable(WorkControlCardBean.class.getClassLoader());
        this.workQualityCard = in.readParcelable(WorkQualityCardBean.class.getClassLoader());
        this.workTools = new ArrayList<WorkToolsBean>();
        in.readList(this.workTools, WorkToolsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<AllControlCarBean> CREATOR = new Parcelable.Creator<AllControlCarBean>() {
        @Override
        public AllControlCarBean createFromParcel(Parcel source) {
            return new AllControlCarBean(source);
        }

        @Override
        public AllControlCarBean[] newArray(int size) {
            return new AllControlCarBean[size];
        }
    };
}
