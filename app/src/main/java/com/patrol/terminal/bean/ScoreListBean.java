package com.patrol.terminal.bean;

import java.io.Serializable;

public class ScoreListBean implements Serializable {
    private String name;
    private int score;
    private int status;

    public ScoreListBean(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public ScoreListBean(String name, int score, int status) {
        this.name = name;
        this.score = score;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
