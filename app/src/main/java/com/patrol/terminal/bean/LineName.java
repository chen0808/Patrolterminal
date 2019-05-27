package com.patrol.terminal.bean;

import java.io.Serializable;

public class LineName implements Serializable {

    /**
     * assets_character : 省（直辖市、自治区）公司
     * assets_code : 110000000487
     * assets_unit : 国网甘肃省电力公司
     * belong_company : 国网兰州供电公司
     * build_unit : 国网甘肃省电力公司
     * cable_line_length : 0
     * construction_unit : 兰州鸿远电力有限责任公司
     * cross_regional_type : 不跨区域
     * dep_id : 00C8BDB1D8734710967A6834B3C6F5EE
     * design_unit : 兰州倚能电气设计咨询有限公司
     * design_voltage_level : 交流35kV
     * device_status : 1
     * dispatching_unit_id : 兰州电力调度控制中心
     * duty_unit_id : 运维检修部
     * end_position : 35kV前哨变电站     3514前兴线
     * end_power_station : 35kV前哨变电站
     * end_switch_code : 3514
     * end_type : 间隔
     * eq_code : 27M00000008599797
     * equipment_increase_way : 设备增加-盘盈
     * erection_method : 架空
     * grounding_electrode : 0
     * id : C0848BA9870747869C86817785BB4091
     * is_electrified_railway_line : 0
     * is_fibre_optical : 0
     * is_terminal_line : 1
     * line_color : 绿色
     * line_dispatch : 地调
     * line_length : 17.384
     * line_level : 5
     * line_no : 3514
     * line_type : 主线
     * max_permissible_current : 220
     * name : 3514兴前线
     * operating_load_limit : 13.34
     * overhead_line_length : 17.384
     * owner_id : 40A43B00C2E945388F38BF6432D6A0D3
     * positive_cap : 7.4404
     * positive_seq_dd : 0.0967
     * positive_seq_dn : 0.1344
     * positive_seq_r : 10.3365
     * positive_seq_reactor : 7.4404
     * professional_type : 输电
     * rated_transmission_power : 13340
     * remarks : 兴前线1#—6#于2009年10月25日线路改造后投运。7#—104#利用原金前线52#—149#。原金前线投运日期为1971年8月3日。
     * same_pole_stringing_wire : 1
     * standard : 0
     * start_position : 3514兴前线
     * start_power_station : 110kV兴隆变电站
     * start_switch_code : 3514
     * start_type : 间隔
     * supervision_unit : 甘肃光明监理公司兰州监理处
     * trans_time : 2009/10/25
     * voltage_level : 交流35kV
     * zero_seq_cap : 22.3211
     * zero_seq_dd : 0.0322
     * zero_seq_dn : 0.0448
     * zero_seq_r : 31.0096
     * zero_seq_reactor : 22.3211
     */

    private String assets_character;
    private String assets_code;
    private String assets_unit;
    private String belong_company;
    private String build_unit;
    private int cable_line_length;
    private String construction_unit;
    private String cross_regional_type;
    private String dep_id;
    private String design_unit;
    private String design_voltage_level;
    private String device_status;
    private String dispatching_unit_id;
    private String duty_unit_id;
    private String end_position;
    private String end_power_station;
    private String end_switch_code;
    private String end_type;
    private String eq_code;
    private String equipment_increase_way;
    private String erection_method;
    private String grounding_electrode;
    private String id;
    private String is_electrified_railway_line;
    private String is_fibre_optical;
    private String is_terminal_line;
    private String line_color;
    private String line_dispatch;
    private double line_length;
    private String line_level;
    private String line_no;
    private String line_type;
    private int max_permissible_current;
    private String name;
    private double operating_load_limit;
    private double overhead_line_length;
    private String owner_id;
    private double positive_cap;
    private double positive_seq_dd;
    private double positive_seq_dn;
    private double positive_seq_r;
    private double positive_seq_reactor;
    private String professional_type;
    private int rated_transmission_power;
    private String remarks;
    private String same_pole_stringing_wire;
    private String standard;
    private String start_position;
    private String start_power_station;
    private String start_switch_code;
    private String start_type;
    private String supervision_unit;
    private String trans_time;
    private String voltage_level;
    private double zero_seq_cap;
    private double zero_seq_dd;
    private double zero_seq_dn;
    private double zero_seq_r;
    private double zero_seq_reactor;

    public String getAssets_character() {
        return assets_character;
    }

    public void setAssets_character(String assets_character) {
        this.assets_character = assets_character;
    }

    public String getAssets_code() {
        return assets_code;
    }

    public void setAssets_code(String assets_code) {
        this.assets_code = assets_code;
    }

    public String getAssets_unit() {
        return assets_unit;
    }

    public void setAssets_unit(String assets_unit) {
        this.assets_unit = assets_unit;
    }

    public String getBelong_company() {
        return belong_company;
    }

    public void setBelong_company(String belong_company) {
        this.belong_company = belong_company;
    }

    public String getBuild_unit() {
        return build_unit;
    }

    public void setBuild_unit(String build_unit) {
        this.build_unit = build_unit;
    }

    public int getCable_line_length() {
        return cable_line_length;
    }

    public void setCable_line_length(int cable_line_length) {
        this.cable_line_length = cable_line_length;
    }

    public String getConstruction_unit() {
        return construction_unit;
    }

    public void setConstruction_unit(String construction_unit) {
        this.construction_unit = construction_unit;
    }

    public String getCross_regional_type() {
        return cross_regional_type;
    }

    public void setCross_regional_type(String cross_regional_type) {
        this.cross_regional_type = cross_regional_type;
    }

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getDesign_unit() {
        return design_unit;
    }

    public void setDesign_unit(String design_unit) {
        this.design_unit = design_unit;
    }

    public String getDesign_voltage_level() {
        return design_voltage_level;
    }

    public void setDesign_voltage_level(String design_voltage_level) {
        this.design_voltage_level = design_voltage_level;
    }

    public String getDevice_status() {
        return device_status;
    }

    public void setDevice_status(String device_status) {
        this.device_status = device_status;
    }

    public String getDispatching_unit_id() {
        return dispatching_unit_id;
    }

    public void setDispatching_unit_id(String dispatching_unit_id) {
        this.dispatching_unit_id = dispatching_unit_id;
    }

    public String getDuty_unit_id() {
        return duty_unit_id;
    }

    public void setDuty_unit_id(String duty_unit_id) {
        this.duty_unit_id = duty_unit_id;
    }

    public String getEnd_position() {
        return end_position;
    }

    public void setEnd_position(String end_position) {
        this.end_position = end_position;
    }

    public String getEnd_power_station() {
        return end_power_station;
    }

    public void setEnd_power_station(String end_power_station) {
        this.end_power_station = end_power_station;
    }

    public String getEnd_switch_code() {
        return end_switch_code;
    }

    public void setEnd_switch_code(String end_switch_code) {
        this.end_switch_code = end_switch_code;
    }

    public String getEnd_type() {
        return end_type;
    }

    public void setEnd_type(String end_type) {
        this.end_type = end_type;
    }

    public String getEq_code() {
        return eq_code;
    }

    public void setEq_code(String eq_code) {
        this.eq_code = eq_code;
    }

    public String getEquipment_increase_way() {
        return equipment_increase_way;
    }

    public void setEquipment_increase_way(String equipment_increase_way) {
        this.equipment_increase_way = equipment_increase_way;
    }

    public String getErection_method() {
        return erection_method;
    }

    public void setErection_method(String erection_method) {
        this.erection_method = erection_method;
    }

    public String getGrounding_electrode() {
        return grounding_electrode;
    }

    public void setGrounding_electrode(String grounding_electrode) {
        this.grounding_electrode = grounding_electrode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_electrified_railway_line() {
        return is_electrified_railway_line;
    }

    public void setIs_electrified_railway_line(String is_electrified_railway_line) {
        this.is_electrified_railway_line = is_electrified_railway_line;
    }

    public String getIs_fibre_optical() {
        return is_fibre_optical;
    }

    public void setIs_fibre_optical(String is_fibre_optical) {
        this.is_fibre_optical = is_fibre_optical;
    }

    public String getIs_terminal_line() {
        return is_terminal_line;
    }

    public void setIs_terminal_line(String is_terminal_line) {
        this.is_terminal_line = is_terminal_line;
    }

    public String getLine_color() {
        return line_color;
    }

    public void setLine_color(String line_color) {
        this.line_color = line_color;
    }

    public String getLine_dispatch() {
        return line_dispatch;
    }

    public void setLine_dispatch(String line_dispatch) {
        this.line_dispatch = line_dispatch;
    }

    public double getLine_length() {
        return line_length;
    }

    public void setLine_length(double line_length) {
        this.line_length = line_length;
    }

    public String getLine_level() {
        return line_level;
    }

    public void setLine_level(String line_level) {
        this.line_level = line_level;
    }

    public String getLine_no() {
        return line_no;
    }

    public void setLine_no(String line_no) {
        this.line_no = line_no;
    }

    public String getLine_type() {
        return line_type;
    }

    public void setLine_type(String line_type) {
        this.line_type = line_type;
    }

    public int getMax_permissible_current() {
        return max_permissible_current;
    }

    public void setMax_permissible_current(int max_permissible_current) {
        this.max_permissible_current = max_permissible_current;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOperating_load_limit() {
        return operating_load_limit;
    }

    public void setOperating_load_limit(double operating_load_limit) {
        this.operating_load_limit = operating_load_limit;
    }

    public double getOverhead_line_length() {
        return overhead_line_length;
    }

    public void setOverhead_line_length(double overhead_line_length) {
        this.overhead_line_length = overhead_line_length;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public double getPositive_cap() {
        return positive_cap;
    }

    public void setPositive_cap(double positive_cap) {
        this.positive_cap = positive_cap;
    }

    public double getPositive_seq_dd() {
        return positive_seq_dd;
    }

    public void setPositive_seq_dd(double positive_seq_dd) {
        this.positive_seq_dd = positive_seq_dd;
    }

    public double getPositive_seq_dn() {
        return positive_seq_dn;
    }

    public void setPositive_seq_dn(double positive_seq_dn) {
        this.positive_seq_dn = positive_seq_dn;
    }

    public double getPositive_seq_r() {
        return positive_seq_r;
    }

    public void setPositive_seq_r(double positive_seq_r) {
        this.positive_seq_r = positive_seq_r;
    }

    public double getPositive_seq_reactor() {
        return positive_seq_reactor;
    }

    public void setPositive_seq_reactor(double positive_seq_reactor) {
        this.positive_seq_reactor = positive_seq_reactor;
    }

    public String getProfessional_type() {
        return professional_type;
    }

    public void setProfessional_type(String professional_type) {
        this.professional_type = professional_type;
    }

    public int getRated_transmission_power() {
        return rated_transmission_power;
    }

    public void setRated_transmission_power(int rated_transmission_power) {
        this.rated_transmission_power = rated_transmission_power;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSame_pole_stringing_wire() {
        return same_pole_stringing_wire;
    }

    public void setSame_pole_stringing_wire(String same_pole_stringing_wire) {
        this.same_pole_stringing_wire = same_pole_stringing_wire;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getStart_position() {
        return start_position;
    }

    public void setStart_position(String start_position) {
        this.start_position = start_position;
    }

    public String getStart_power_station() {
        return start_power_station;
    }

    public void setStart_power_station(String start_power_station) {
        this.start_power_station = start_power_station;
    }

    public String getStart_switch_code() {
        return start_switch_code;
    }

    public void setStart_switch_code(String start_switch_code) {
        this.start_switch_code = start_switch_code;
    }

    public String getStart_type() {
        return start_type;
    }

    public void setStart_type(String start_type) {
        this.start_type = start_type;
    }

    public String getSupervision_unit() {
        return supervision_unit;
    }

    public void setSupervision_unit(String supervision_unit) {
        this.supervision_unit = supervision_unit;
    }

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    public String getVoltage_level() {
        return voltage_level;
    }

    public void setVoltage_level(String voltage_level) {
        this.voltage_level = voltage_level;
    }

    public double getZero_seq_cap() {
        return zero_seq_cap;
    }

    public void setZero_seq_cap(double zero_seq_cap) {
        this.zero_seq_cap = zero_seq_cap;
    }

    public double getZero_seq_dd() {
        return zero_seq_dd;
    }

    public void setZero_seq_dd(double zero_seq_dd) {
        this.zero_seq_dd = zero_seq_dd;
    }

    public double getZero_seq_dn() {
        return zero_seq_dn;
    }

    public void setZero_seq_dn(double zero_seq_dn) {
        this.zero_seq_dn = zero_seq_dn;
    }

    public double getZero_seq_r() {
        return zero_seq_r;
    }

    public void setZero_seq_r(double zero_seq_r) {
        this.zero_seq_r = zero_seq_r;
    }

    public double getZero_seq_reactor() {
        return zero_seq_reactor;
    }

    public void setZero_seq_reactor(double zero_seq_reactor) {
        this.zero_seq_reactor = zero_seq_reactor;
    }
}
