package com.patrol.terminal.bean;

import java.io.Serializable;

public class LineCheckBean implements Serializable {

    /**
     * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
     * name : 1116桃南线
     * id : F3BA53A0C28E4EEC9D6DB821CDAAA6EC
     * dep_name : 西固运维班
     */

    private String dep_id;
    private String name;
    private String id;
    private String dep_name;

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }
}
