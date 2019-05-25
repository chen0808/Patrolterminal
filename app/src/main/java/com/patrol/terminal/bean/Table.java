package com.patrol.terminal.bean;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

@SmartTable
public class Table {
    @SmartColumn(id = 0, name = "序号", autoMerge = true)
    private int num;
    @SmartColumn(id = 1, name = "杆段")
    private String line_name;
    @SmartColumn(id = 2, name = "经度")
    private String lat;
    @SmartColumn(id = 3, name = "纬度")
    private String lon;
    public Table(int num, String line_name, String lat,String lon) {
        this.num = num;
        this.line_name = line_name;
        this.lat = lat;
        this.lon = lon;
    }
}
