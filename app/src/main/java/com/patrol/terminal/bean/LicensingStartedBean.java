package com.patrol.terminal.bean;

public class LicensingStartedBean {
    private String licensingMethod;
    private String licensor;
    private String SignJobManager;
    private String time;

    public String getLicensingMethod() {
        return licensingMethod;
    }

    public void setLicensingMethod(String licensingMethod) {
        this.licensingMethod = licensingMethod;
    }

    public String getLicensor() {
        return licensor;
    }

    public void setLicensor(String licensor) {
        this.licensor = licensor;
    }

    public String getSignJobManager() {
        return SignJobManager;
    }

    public void setSignJobManager(String signJobManager) {
        SignJobManager = signJobManager;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
