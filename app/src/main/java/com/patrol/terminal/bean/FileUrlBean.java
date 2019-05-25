package com.patrol.terminal.bean;

import java.io.File;
import java.util.List;

public class FileUrlBean {
    private List<UrlBean> fileList;
    private String id;

    private String name;

    private String state;

    private String typeName;

    private String sort;

    private String typeSort;

    private String time;

    private String detail;

    private String inspector;

    private String liable;

    private String team;

    private String isConfirm;

    private String auditorId;

    private String tStart;

    private String tEnd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(String typeSort) {
        this.typeSort = typeSort;
    }

    public String getLiable() {
        return liable;
    }

    public void setLiable(String liable) {
        this.liable = liable;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(String isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String gettEnd() {
        return tEnd;
    }

    public void settEnd(String tEnd) {
        this.tEnd = tEnd;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String gettStart() {
        return tStart;
    }

    public void settStart(String tStart) {
        this.tStart = tStart;
    }

    public List<UrlBean> getFileList() {
        return fileList;
    }

    public void setFileList(List<UrlBean> fileList) {
        this.fileList = fileList;
    }

   public static class UrlBean {
         private File files;

       public File getFiles() {
           return files;
       }

       public void setFiles(File files) {
           this.files = files;
       }
   }
}
