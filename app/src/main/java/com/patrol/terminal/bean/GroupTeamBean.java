package com.patrol.terminal.bean;

import java.util.List;

public class GroupTeamBean {
    private List<DepUserBean> personalsList;
    private List<DepUserBean> TeamList;
    private  boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public List<DepUserBean> getPersonalsList() {
        return personalsList;
    }

    public void setPersonalsList(List<DepUserBean> personalsList) {
        this.personalsList = personalsList;
    }

    public List<DepUserBean> getTeamList() {
        return TeamList;
    }

    public void setTeamList(List<DepUserBean> teamList) {
        TeamList = teamList;
    }
}
