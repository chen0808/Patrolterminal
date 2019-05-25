package com.patrol.terminal.bean;

public class OvaTodoBean {

        /**
         * id : C49232468B2545E79993879C7D26564F
         * repair_id : 2362CE2BFFC24889B2982C4B5620A6B1
         * user_id : 94AE9C0BD55A43E49E47D12BD1F48391
         * status : 3
         * user_name : null
         * planRepair : {"id":"2362CE2BFFC24889B2982C4B5620A6B1","apply_unit_id":"34B9F165BF9B4527B01ABF328055FBD3","voltage_level":"110kv","device_id":"A4EFEC9CF1124D19B421B7536D443517","repair_content":"4、断开1116和开一线31#塔三相引流。","is_blackout":"1","blackout_range":"西固区","task_source":"停电配合","start_time":"2019-5-8","end_time":"2019-5-20","blackout_days":1,"last_repair_time":"2019-2-1","risk_level":"6","type_id":null,"type_val":null,"substation_id":"110kV源泰变电站","remark":"123123123","year":2019,"month":5,"week":2,"status":"0","line_name":"西陈二线","user_id":null,"userList":null,"sysFiles":null,"workControlCard":null,"workTools":null,"workQualityCard":null}
         */

        private String id;
        private String repair_id;
        private String user_id;
        private String status;
        private String user_name;
        private PlanRepairBean planRepair;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRepair_id() {
            return repair_id;
        }

        public void setRepair_id(String repair_id) {
            this.repair_id = repair_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public PlanRepairBean getPlanRepair() {
            return planRepair;
        }

        public void setPlanRepair(PlanRepairBean planRepair) {
            this.planRepair = planRepair;
        }

        public static class PlanRepairBean {
            /**
             * id : 2362CE2BFFC24889B2982C4B5620A6B1
             * apply_unit_id : 34B9F165BF9B4527B01ABF328055FBD3
             * voltage_level : 110kv
             * device_id : A4EFEC9CF1124D19B421B7536D443517
             * repair_content : 4、断开1116和开一线31#塔三相引流。
             * is_blackout : 1
             * blackout_range : 西固区
             * task_source : 停电配合
             * start_time : 2019-5-8
             * end_time : 2019-5-20
             * blackout_days : 1
             * last_repair_time : 2019-2-1
             * risk_level : 6
             * type_id : null
             * type_val : null
             * substation_id : 110kV源泰变电站
             * remark : 123123123
             * year : 2019
             * month : 5
             * week : 2
             * status : 0
             * line_name : 西陈二线
             * user_id : null
             * userList : null
             * sysFiles : null
             * workControlCard : null
             * workTools : null
             * workQualityCard : null
             */

            private String id;
            private String apply_unit_id;
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
            private Object type_id;
            private Object type_val;
            private String substation_id;
            private String remark;
            private int year;
            private int month;
            private int week;
            private String status;
            private String line_name;
            private Object user_id;
            private Object userList;
            private Object sysFiles;
            private Object workControlCard;
            private Object workTools;
            private Object workQualityCard;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getApply_unit_id() {
                return apply_unit_id;
            }

            public void setApply_unit_id(String apply_unit_id) {
                this.apply_unit_id = apply_unit_id;
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

            public Object getType_id() {
                return type_id;
            }

            public void setType_id(Object type_id) {
                this.type_id = type_id;
            }

            public Object getType_val() {
                return type_val;
            }

            public void setType_val(Object type_val) {
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

            public String getLine_name() {
                return line_name;
            }

            public void setLine_name(String line_name) {
                this.line_name = line_name;
            }

            public Object getUser_id() {
                return user_id;
            }

            public void setUser_id(Object user_id) {
                this.user_id = user_id;
            }

            public Object getUserList() {
                return userList;
            }

            public void setUserList(Object userList) {
                this.userList = userList;
            }

            public Object getSysFiles() {
                return sysFiles;
            }

            public void setSysFiles(Object sysFiles) {
                this.sysFiles = sysFiles;
            }

            public Object getWorkControlCard() {
                return workControlCard;
            }

            public void setWorkControlCard(Object workControlCard) {
                this.workControlCard = workControlCard;
            }

            public Object getWorkTools() {
                return workTools;
            }

            public void setWorkTools(Object workTools) {
                this.workTools = workTools;
            }

            public Object getWorkQualityCard() {
                return workQualityCard;
            }

            public void setWorkQualityCard(Object workQualityCard) {
                this.workQualityCard = workQualityCard;
            }
        }

}
