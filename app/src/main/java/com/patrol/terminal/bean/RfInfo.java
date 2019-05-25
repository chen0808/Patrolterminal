package com.patrol.terminal.bean;

import java.io.Serializable;

public class RfInfo implements Serializable {

    /**
     * id : 3D8816C7BB154CBA8888836C7ECDA1FF
     * name : #001
     * line_id : 815CE6A3D6C44148B421B19E5F0F2D83
     * location : 国网兰州供电公司
     * duty_unit_id : 运维检修部
     * duty_dep_id : null
     * tower_id : 3521达化二线001号
     * use_date : 1959-04-16 00:00:00.0
     * use_status : 在运
     * character : 省（直辖市、自治区）公司
     * tower_model : JGDU塔
     * producer : 上海川力电器有限公司
     * tower_type : 耐张
     * tower_inside_type : 角钢塔
     * tower_code : 27M00000012188974
     * assets_code : 110000000453
     * register_time : 1959-04-16 00:00:00.0
     * tower_area : null
     * owner_id : 丁学刚
     * voltage : 交流35kV
     * span : 118
     * is_terminal : 是
     * tower_calls : 12
     * tower_position : 左
     * is_commutation : 否
     * wind_speed : 31
     * tower_box : null
     * bear_ice : 5~10
     * install_level : BAC
     * is_corner : 是
     * corner_direction : 左
     * corner_degrees : 14°13′00〞
     * tower_lines : null
     * ground : 放射型
     * tower_loop : 2
     * work_unit : 兰州供电局工程处
     * tower_height : 23
     * ground_resistance : 5.4
     * character_unit : 国网甘肃省电力公司
     * is_loop : 是
     * area_trait : 乡镇
     * cite_dep : null
     * is_other : 否
     * tower_number : 1
     * relevance_tower : null
     * type_code : 103
     * major_type : 输电
     * device_code : 27M00000008606324
     * arm_texture : 铁
     * base_from : 钢筋混凝土
     * tower_weight : 1.131
     * ruling_span : 118
     * tower_length : 118
     * tower_a_height : 15.5
     * tower_b_height : 19
     * tower_c_height : 12
     * tower_a_range : 4.2
     * tower_b_range : 3.7
     * tower_c_range : 4.7
     * l_height : 23
     * l_left_range : 0.9
     * l_right_range : 0.9
     * remarks : 3521达化二线001号
     * sort : 1
     * towers_id : null
     * lon : 103.35118756
     * lat : 36.12889657
     * rf_id : 027020000000000001921121
     * line_name : 达化二线
     * line_no : 3521
     */

    private String id;
    private String name;
    private String line_id;
    private String location;
    private String duty_unit_id;
    private Object duty_dep_id;
    private String tower_id;
    private String use_date;
    private String use_status;
    private String character;
    private String tower_model;
    private String producer;
    private String tower_type;
    private String tower_inside_type;
    private String tower_code;
    private String assets_code;
    private String register_time;
    private Object tower_area;
    private String owner_id;
    private String voltage;
    private int span;
    private String is_terminal;
    private int tower_calls;
    private String tower_position;
    private String is_commutation;
    private int wind_speed;
    private Object tower_box;
    private String bear_ice;
    private String install_level;
    private String is_corner;
    private String corner_direction;
    private String corner_degrees;
    private Object tower_lines;
    private String ground;
    private int tower_loop;
    private String work_unit;
    private int tower_height;
    private double ground_resistance;
    private String character_unit;
    private String is_loop;
    private String area_trait;
    private Object cite_dep;
    private String is_other;
    private int tower_number;
    private Object relevance_tower;
    private String type_code;
    private String major_type;
    private String device_code;
    private String arm_texture;
    private String base_from;
    private double tower_weight;
    private int ruling_span;
    private int tower_length;
    private double tower_a_height;
    private int tower_b_height;
    private int tower_c_height;
    private double tower_a_range;
    private double tower_b_range;
    private double tower_c_range;
    private int l_height;
    private double l_left_range;
    private double l_right_range;
    private String remarks;
    private int sort;
    private Object towers_id;
    private double lon;
    private double lat;
    private String rf_id;
    private String line_name;
    private String line_no;

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

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDuty_unit_id() {
        return duty_unit_id;
    }

    public void setDuty_unit_id(String duty_unit_id) {
        this.duty_unit_id = duty_unit_id;
    }

    public Object getDuty_dep_id() {
        return duty_dep_id;
    }

    public void setDuty_dep_id(Object duty_dep_id) {
        this.duty_dep_id = duty_dep_id;
    }

    public String getTower_id() {
        return tower_id;
    }

    public void setTower_id(String tower_id) {
        this.tower_id = tower_id;
    }

    public String getUse_date() {
        return use_date;
    }

    public void setUse_date(String use_date) {
        this.use_date = use_date;
    }

    public String getUse_status() {
        return use_status;
    }

    public void setUse_status(String use_status) {
        this.use_status = use_status;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getTower_model() {
        return tower_model;
    }

    public void setTower_model(String tower_model) {
        this.tower_model = tower_model;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getTower_type() {
        return tower_type;
    }

    public void setTower_type(String tower_type) {
        this.tower_type = tower_type;
    }

    public String getTower_inside_type() {
        return tower_inside_type;
    }

    public void setTower_inside_type(String tower_inside_type) {
        this.tower_inside_type = tower_inside_type;
    }

    public String getTower_code() {
        return tower_code;
    }

    public void setTower_code(String tower_code) {
        this.tower_code = tower_code;
    }

    public String getAssets_code() {
        return assets_code;
    }

    public void setAssets_code(String assets_code) {
        this.assets_code = assets_code;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public Object getTower_area() {
        return tower_area;
    }

    public void setTower_area(Object tower_area) {
        this.tower_area = tower_area;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    public String getIs_terminal() {
        return is_terminal;
    }

    public void setIs_terminal(String is_terminal) {
        this.is_terminal = is_terminal;
    }

    public int getTower_calls() {
        return tower_calls;
    }

    public void setTower_calls(int tower_calls) {
        this.tower_calls = tower_calls;
    }

    public String getTower_position() {
        return tower_position;
    }

    public void setTower_position(String tower_position) {
        this.tower_position = tower_position;
    }

    public String getIs_commutation() {
        return is_commutation;
    }

    public void setIs_commutation(String is_commutation) {
        this.is_commutation = is_commutation;
    }

    public int getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(int wind_speed) {
        this.wind_speed = wind_speed;
    }

    public Object getTower_box() {
        return tower_box;
    }

    public void setTower_box(Object tower_box) {
        this.tower_box = tower_box;
    }

    public String getBear_ice() {
        return bear_ice;
    }

    public void setBear_ice(String bear_ice) {
        this.bear_ice = bear_ice;
    }

    public String getInstall_level() {
        return install_level;
    }

    public void setInstall_level(String install_level) {
        this.install_level = install_level;
    }

    public String getIs_corner() {
        return is_corner;
    }

    public void setIs_corner(String is_corner) {
        this.is_corner = is_corner;
    }

    public String getCorner_direction() {
        return corner_direction;
    }

    public void setCorner_direction(String corner_direction) {
        this.corner_direction = corner_direction;
    }

    public String getCorner_degrees() {
        return corner_degrees;
    }

    public void setCorner_degrees(String corner_degrees) {
        this.corner_degrees = corner_degrees;
    }

    public Object getTower_lines() {
        return tower_lines;
    }

    public void setTower_lines(Object tower_lines) {
        this.tower_lines = tower_lines;
    }

    public String getGround() {
        return ground;
    }

    public void setGround(String ground) {
        this.ground = ground;
    }

    public int getTower_loop() {
        return tower_loop;
    }

    public void setTower_loop(int tower_loop) {
        this.tower_loop = tower_loop;
    }

    public String getWork_unit() {
        return work_unit;
    }

    public void setWork_unit(String work_unit) {
        this.work_unit = work_unit;
    }

    public int getTower_height() {
        return tower_height;
    }

    public void setTower_height(int tower_height) {
        this.tower_height = tower_height;
    }

    public double getGround_resistance() {
        return ground_resistance;
    }

    public void setGround_resistance(double ground_resistance) {
        this.ground_resistance = ground_resistance;
    }

    public String getCharacter_unit() {
        return character_unit;
    }

    public void setCharacter_unit(String character_unit) {
        this.character_unit = character_unit;
    }

    public String getIs_loop() {
        return is_loop;
    }

    public void setIs_loop(String is_loop) {
        this.is_loop = is_loop;
    }

    public String getArea_trait() {
        return area_trait;
    }

    public void setArea_trait(String area_trait) {
        this.area_trait = area_trait;
    }

    public Object getCite_dep() {
        return cite_dep;
    }

    public void setCite_dep(Object cite_dep) {
        this.cite_dep = cite_dep;
    }

    public String getIs_other() {
        return is_other;
    }

    public void setIs_other(String is_other) {
        this.is_other = is_other;
    }

    public int getTower_number() {
        return tower_number;
    }

    public void setTower_number(int tower_number) {
        this.tower_number = tower_number;
    }

    public Object getRelevance_tower() {
        return relevance_tower;
    }

    public void setRelevance_tower(Object relevance_tower) {
        this.relevance_tower = relevance_tower;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public String getMajor_type() {
        return major_type;
    }

    public void setMajor_type(String major_type) {
        this.major_type = major_type;
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public String getArm_texture() {
        return arm_texture;
    }

    public void setArm_texture(String arm_texture) {
        this.arm_texture = arm_texture;
    }

    public String getBase_from() {
        return base_from;
    }

    public void setBase_from(String base_from) {
        this.base_from = base_from;
    }

    public double getTower_weight() {
        return tower_weight;
    }

    public void setTower_weight(double tower_weight) {
        this.tower_weight = tower_weight;
    }

    public int getRuling_span() {
        return ruling_span;
    }

    public void setRuling_span(int ruling_span) {
        this.ruling_span = ruling_span;
    }

    public int getTower_length() {
        return tower_length;
    }

    public void setTower_length(int tower_length) {
        this.tower_length = tower_length;
    }

    public double getTower_a_height() {
        return tower_a_height;
    }

    public void setTower_a_height(double tower_a_height) {
        this.tower_a_height = tower_a_height;
    }

    public int getTower_b_height() {
        return tower_b_height;
    }

    public void setTower_b_height(int tower_b_height) {
        this.tower_b_height = tower_b_height;
    }

    public int getTower_c_height() {
        return tower_c_height;
    }

    public void setTower_c_height(int tower_c_height) {
        this.tower_c_height = tower_c_height;
    }

    public double getTower_a_range() {
        return tower_a_range;
    }

    public void setTower_a_range(double tower_a_range) {
        this.tower_a_range = tower_a_range;
    }

    public double getTower_b_range() {
        return tower_b_range;
    }

    public void setTower_b_range(double tower_b_range) {
        this.tower_b_range = tower_b_range;
    }

    public double getTower_c_range() {
        return tower_c_range;
    }

    public void setTower_c_range(double tower_c_range) {
        this.tower_c_range = tower_c_range;
    }

    public int getL_height() {
        return l_height;
    }

    public void setL_height(int l_height) {
        this.l_height = l_height;
    }

    public double getL_left_range() {
        return l_left_range;
    }

    public void setL_left_range(double l_left_range) {
        this.l_left_range = l_left_range;
    }

    public double getL_right_range() {
        return l_right_range;
    }

    public void setL_right_range(double l_right_range) {
        this.l_right_range = l_right_range;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Object getTowers_id() {
        return towers_id;
    }

    public void setTowers_id(Object towers_id) {
        this.towers_id = towers_id;
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

    public String getRf_id() {
        return rf_id;
    }

    public void setRf_id(String rf_id) {
        this.rf_id = rf_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getLine_no() {
        return line_no;
    }

    public void setLine_no(String line_no) {
        this.line_no = line_no;
    }
}
