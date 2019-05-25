package com.patrol.terminal.bean;

import java.io.Serializable;
import java.util.List;

public class WorkQualityCard implements Serializable {
    List<WorkStandardRelation> workStandardRelations;
    private String id;
    private String task_id;
    private String name;
    private String dep_id;
    private String leader;
    private String workers;
    private String banks;
    private String start_time;
    private String end_time;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getWorkers() {
        return workers;
    }

    public void setWorkers(String workers) {
        this.workers = workers;
    }

    public String getBanks() {
        return banks;
    }

    public void setBanks(String banks) {
        this.banks = banks;
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

    public List<WorkStandardRelation> getWorkStandardRelations() {
        return workStandardRelations;
    }

    public void setWorkStandardRelations(List<WorkStandardRelation> workStandardRelations) {
        this.workStandardRelations = workStandardRelations;
    }

    public static class WorkStandardRelation implements Serializable {
        private String id;
        private String w_q_c_id;
        private String w_q_s_id;
        private String status;
        private String task_id;
        private String process;
        private String standard;
        private String warning;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getW_q_c_id() {
            return w_q_c_id;
        }

        public void setW_q_c_id(String w_q_c_id) {
            this.w_q_c_id = w_q_c_id;
        }

        public String getW_q_s_id() {
            return w_q_s_id;
        }

        public void setW_q_s_id(String w_q_s_id) {
            this.w_q_s_id = w_q_s_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
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
    }
}
