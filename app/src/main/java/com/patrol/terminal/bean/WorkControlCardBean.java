package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class WorkControlCardBean implements Serializable {
    //private String id;
    //private String m_u_id;//负责人
    //private String s_u_id;
    //private String p_u_id;
    //private String name;
    //private String type;
    //private String dep_id;
    //private String banks;//票号
    //private String serial;
    //private String start_time;
    //private String end_time;
    private String id;
    private String task_id;

    private String duty_user_id;
    private String duty_user_name;

    private String year;
    private String month;

    private List<WorkProjectUser> workProjectUsers;
    private List<WorkSafeRelation> workSafeRelations;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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

    //
//    public String getM_u_id() {
//        return m_u_id;
//    }
//
//    public void setM_u_id(String m_u_id) {
//        this.m_u_id = m_u_id;
//    }
//
//    public String getS_u_id() {
//        return s_u_id;
//    }
//
//    public void setS_u_id(String s_u_id) {
//        this.s_u_id = s_u_id;
//    }
//
//    public String getP_u_id() {
//        return p_u_id;
//    }
//
//    public void setP_u_id(String p_u_id) {
//        this.p_u_id = p_u_id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getStart_time() {
//        return start_time;
//    }
//
//    public void setStart_time(String start_time) {
//        this.start_time = start_time;
//    }
//
//    public String getEnd_time() {
//        return end_time;
//    }
//
//    public void setEnd_time(String end_time) {
//        this.end_time = end_time;
//    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public List<WorkProjectUser> getWorkProjectUsers() {
        return workProjectUsers;
    }

    public void setWorkProjectUsers(List<WorkProjectUser> workProjectUsers) {
        this.workProjectUsers = workProjectUsers;
    }

    public List<WorkSafeRelation> getWorkSafeRelations() {
        return workSafeRelations;
    }

    public void setWorkSafeRelations(List<WorkSafeRelation> workSafeRelations) {
        this.workSafeRelations = workSafeRelations;
    }

//    public String getDep_id() {
//        return dep_id;
//    }
//
//    public void setDep_id(String dep_id) {
//        this.dep_id = dep_id;
//    }
//
//    public String getBanks() {
//        return banks;
//    }
//
//    public void setBanks(String banks) {
//        this.banks = banks;
//    }
//
//    public String getSerial() {
//        return serial;
//    }
//
//    public void setSerial(String serial) {
//        this.serial = serial;
//    }

    public static class WorkSafeRelation implements Serializable {
        private String id;
        private String w_s_id;
        private String w_c_id;
        private String respon;
        private String task_id;
        private String danger;
        private String content;

        public WorkSafeRelation(String w_s_id, String respon) {
            this.w_s_id = w_s_id;
            this.respon = respon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getW_s_id() {
            return w_s_id;
        }

        public void setW_s_id(String w_s_id) {
            this.w_s_id = w_s_id;
        }

        public String getW_c_id() {
            return w_c_id;
        }

        public void setW_c_id(String w_c_id) {
            this.w_c_id = w_c_id;
        }

        public String getRespon() {
            return respon;
        }

        public void setRespon(String respon) {
            this.respon = respon;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
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
    }
}
