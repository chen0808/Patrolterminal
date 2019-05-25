package com.patrol.terminal.bean;

public class PositionListBean {


    /**
     * id : FB15FF3FA53A4E8DBA03081327E8520B
     * lon : 103.7124462890625
     * lat : 36.10351047092014
     * user_id : 9CF4DCD383474DAC89F0D0C9DCC8071B
     * loc_time : 2019-05-25 20:32:14
     * address : 甘肃省兰州市安宁区
     * sign : null
     * data_id : null
     */

    private String id;
    private double lon;
    private double lat;
    private String user_id;
    private String loc_time;
    private String address;
    private Object sign;
    private Object data_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLoc_time() {
        return loc_time;
    }

    public void setLoc_time(String loc_time) {
        this.loc_time = loc_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getSign() {
        return sign;
    }

    public void setSign(Object sign) {
        this.sign = sign;
    }

    public Object getData_id() {
        return data_id;
    }

    public void setData_id(Object data_id) {
        this.data_id = data_id;
    }
}
