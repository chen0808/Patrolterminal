package com.patrol.terminal.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public  class PlanRepairBean implements Parcelable {


    /**
     * id : 795E298B86214B55B0C259A516A302B2
     * apply_unit_id : 34B9F165BF9B4527B01ABF328055FBD3
     * voltage_level : 110kv
     * device_id : A4EFEC9CF1124D19B421B7536D443517
     * repair_content : test1
     * is_blackout : 1
     * blackout_range : 西固区
     * task_source : 停电配合
     * start_time : 2019-5-13
     * end_time : 2019-5-9
     * blackout_days : 1
     * last_repair_time : 2019-1-1
     * risk_level : 5
     * type_id : null
     * type_val : null
     * substation_id : 110kV源泰变电站
     * remark : 123123
     * year : 2019
     * month : 5
     * week : 3
     * status : 3
     * voltage_status : 1
     * ticket_type : 0
     * ticket_task_type : 0
     * line_name : 西陈二线
     * userList : null
     * sysFiles : null
     */

    private String id;
    private String apply_unit_name;
    private String voltage_level;
    private String device_id;
    private String repair_content;
    private String is_blackout;
    private String blackout_range;
    private String task_source;
    private String start_time;
    private String end_time;
    private int blackout_days;
    private String last_repair_time;
    private String risk_level;
    private String type_id;
    private String type_val;
    private String substation_id;
    private String remark;
    private int year;
    private int month;
    private int week;
    private String status;
    private String voltage_status;
    private String ticket_type;
    private String ticket_task_type;
    private String line_name;
    private String userList;
    private List<SysFileBean> sysFiles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApply_unit_name() {
        return apply_unit_name;
    }

    public void setApply_unit_name(String apply_unit_name) {
        this.apply_unit_name = apply_unit_name;
    }

    public String getVoltage_level() {
        return voltage_level;
    }

    public void setVoltage_level(String voltage_level) {
        this.voltage_level = voltage_level;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getRepair_content() {
        return repair_content;
    }

    public void setRepair_content(String repair_content) {
        this.repair_content = repair_content;
    }

    public String getIs_blackout() {
        return is_blackout;
    }

    public void setIs_blackout(String is_blackout) {
        this.is_blackout = is_blackout;
    }

    public String getBlackout_range() {
        return blackout_range;
    }

    public void setBlackout_range(String blackout_range) {
        this.blackout_range = blackout_range;
    }

    public String getTask_source() {
        return task_source;
    }

    public void setTask_source(String task_source) {
        this.task_source = task_source;
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

    public int getBlackout_days() {
        return blackout_days;
    }

    public void setBlackout_days(int blackout_days) {
        this.blackout_days = blackout_days;
    }

    public String getLast_repair_time() {
        return last_repair_time;
    }

    public void setLast_repair_time(String last_repair_time) {
        this.last_repair_time = last_repair_time;
    }

    public String getRisk_level() {
        return risk_level;
    }

    public void setRisk_level(String risk_level) {
        this.risk_level = risk_level;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_val() {
        return type_val;
    }

    public void setType_val(String type_val) {
        this.type_val = type_val;
    }

    public String getSubstation_id() {
        return substation_id;
    }

    public void setSubstation_id(String substation_id) {
        this.substation_id = substation_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVoltage_status() {
        return voltage_status;
    }

    public void setVoltage_status(String voltage_status) {
        this.voltage_status = voltage_status;
    }

    public String getTicket_type() {
        return ticket_type;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }

    public String getTicket_task_type() {
        return ticket_task_type;
    }

    public void setTicket_task_type(String ticket_task_type) {
        this.ticket_task_type = ticket_task_type;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getUserList() {
        return userList;
    }

    public void setUserList(String userList) {
        this.userList = userList;
    }

    public List<SysFileBean> getSysFiles() {
        return sysFiles;
    }

    public void setSysFiles(List<SysFileBean> sysFiles) {
        this.sysFiles = sysFiles;
    }

    public static class SysFileBean implements Parcelable {

        private String id;
        private String data_id;
        private String filename;
        private String old_name;
        private String file_type;
        private String file_path;
        private String file_size;
        private String repair_type;
        private String content;

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

        public SysFileBean() {
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
        }

        protected SysFileBean(Parcel in) {
            this.id = in.readString();
            this.data_id = in.readString();
            this.filename = in.readString();
            this.old_name = in.readString();
            this.file_type = in.readString();
            this.file_path = in.readString();
            this.file_size = in.readString();
            this.repair_type = in.readString();
            this.content = in.readString();
        }

        public static final Creator<SysFileBean> CREATOR = new Creator<SysFileBean>() {
            @Override
            public SysFileBean createFromParcel(Parcel source) {
                return new SysFileBean(source);
            }

            @Override
            public SysFileBean[] newArray(int size) {
                return new SysFileBean[size];
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
        dest.writeString(this.apply_unit_name);
        dest.writeString(this.voltage_level);
        dest.writeString(this.device_id);
        dest.writeString(this.repair_content);
        dest.writeString(this.is_blackout);
        dest.writeString(this.blackout_range);
        dest.writeString(this.task_source);
        dest.writeString(this.start_time);
        dest.writeString(this.end_time);
        dest.writeInt(this.blackout_days);
        dest.writeString(this.last_repair_time);
        dest.writeString(this.risk_level);
        dest.writeString(this.type_id);
        dest.writeString(this.type_val);
        dest.writeString(this.substation_id);
        dest.writeString(this.remark);
        dest.writeInt(this.year);
        dest.writeInt(this.month);
        dest.writeInt(this.week);
        dest.writeString(this.status);
        dest.writeString(this.voltage_status);
        dest.writeString(this.ticket_type);
        dest.writeString(this.ticket_task_type);
        dest.writeString(this.line_name);
        dest.writeString(this.userList);
        dest.writeList(this.sysFiles);
    }

    public PlanRepairBean() {
    }

    protected PlanRepairBean(Parcel in) {
        this.id = in.readString();
        this.apply_unit_name = in.readString();
        this.voltage_level = in.readString();
        this.device_id = in.readString();
        this.repair_content = in.readString();
        this.is_blackout = in.readString();
        this.blackout_range = in.readString();
        this.task_source = in.readString();
        this.start_time = in.readString();
        this.end_time = in.readString();
        this.blackout_days = in.readInt();
        this.last_repair_time = in.readString();
        this.risk_level = in.readString();
        this.type_id = in.readString();
        this.type_val = in.readString();
        this.substation_id = in.readString();
        this.remark = in.readString();
        this.year = in.readInt();
        this.month = in.readInt();
        this.week = in.readInt();
        this.status = in.readString();
        this.voltage_status = in.readString();
        this.ticket_type = in.readString();
        this.ticket_task_type = in.readString();
        this.line_name = in.readString();
        this.userList = in.readString();
        this.sysFiles = new ArrayList<SysFileBean>();
        in.readList(this.sysFiles, SysFileBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<PlanRepairBean> CREATOR = new Parcelable.Creator<PlanRepairBean>() {
        @Override
        public PlanRepairBean createFromParcel(Parcel source) {
            return new PlanRepairBean(source);
        }

        @Override
        public PlanRepairBean[] newArray(int size) {
            return new PlanRepairBean[size];
        }
    };
}
