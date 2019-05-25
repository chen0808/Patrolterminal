package com.patrol.terminal.bean;

public class TicketBean {
    private String task_id;
    private String type;
    private String task_type;
    private FirstTicketBean firstTicketBean;
    private SecondTicketBean secondTicketBean;
    private ThirdTicketBean thirdTicketBean;
    private FourTicketBean fourTicketBean;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public FirstTicketBean getFirstTicketBean() {
        return firstTicketBean;
    }

    public void setFirstTicketBean(FirstTicketBean firstTicketBean) {
        this.firstTicketBean = firstTicketBean;
    }

    public SecondTicketBean getSecondTicketBean() {
        return secondTicketBean;
    }

    public void setSecondTicketBean(SecondTicketBean secondTicketBean) {
        this.secondTicketBean = secondTicketBean;
    }

    public ThirdTicketBean getThirdTicketBean() {
        return thirdTicketBean;
    }

    public void setThirdTicketBean(ThirdTicketBean thirdTicketBean) {
        this.thirdTicketBean = thirdTicketBean;
    }

    public FourTicketBean getFourTicketBean() {
        return fourTicketBean;
    }

    public void setFourTicketBean(FourTicketBean fourTicketBean) {
        this.fourTicketBean = fourTicketBean;
    }
}
