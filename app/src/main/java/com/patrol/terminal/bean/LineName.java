package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LineName implements Parcelable {

    /**
     * id : D149C9313E3F4904AF1EBEA526CDE95F
     * name : 1113建腾一线
     * line_level : 5
     * duty_unit_id : 运维检修部
     * dep_id : 35AEE071C72A4D45BBF379741D22E605
     * owner_id : 028D9100F92C41B2AEFDA9FA76F0DE49
     * trans_time : null
     * reject_time : 2014-09-19
     * assets_character : 省（直辖市、自治区）公司
     * assets_unit : 国网兰州供电公司
     * assets_code : 110000000432
     * project_code : null
     * project_name : null
     * equipment_increase_way : null
     * pipelined_code : 设备增加-基本建设
     * wbs_code : null
     * line_no : 1113
     * belong_company : 国网兰州供电公司
     * dispatching_unit_id : 兰州电力调度控制中心
     * cross_regional_type : 不跨区域
     * is_other : 1
     * standard : 0
     * rural_network : 0
     * voltage_level : 交流110kV
     * design_voltage_level : 交流110kV
     * device_status : 1
     * grounding_electrode : 0
     * line_type : 主线
     * line_color : 蓝色
     * belong_main_line : null
     * erection_method : 架空
     * line_length : 15.84
     * overhead_line_length : 15.84
     * cable_line_length : 0
     * start_type : 间隔
     * start_power_station : 220kV建设坪变电站
     * start_position : 1113建腾Ⅰ线间隔
     * start_switch_code : 1113建腾一线断路器
     * end_type : 间隔
     * end_power_station : 腾达西北铁合金变电站
     * end_position : 腾达西北铁合金变电站     1113腾建一线
     * end_switch_code : 1113
     * max_permissible_current : 610
     * operating_load_limit : 116.221
     * rated_transmission_power : 116211
     * design_unit : 兰州倚能电力设计咨询有限公司
     * build_unit : 国网甘肃省电力公司
     * construction_unit : 甘肃陇丰公司
     * supervision_unit : 甘肃光明监理公司
     * functional_location_code : 27-2701-L-11202
     * professional_type : 输电
     * is_fibre_optical : 0
     * is_terminal_line : 0
     * is_electrified_railway_line : 0
     * same_pole_stringing_wire : 0
     * zero_seq_cap : 0.0558
     * positive_cap : 5.9717
     * positive_seq_r : 0.1181
     * positive_seq_reactor : 5.9717
     * positive_seq_dn : 0.1675
     * positive_seq_dd : 0.5346
     * zero_seq_r : 5.6121
     * zero_seq_reactor : 0.0558
     * zero_seq_dn : 0.0558
     * zero_seq_dd : 0.1782
     * eq_code : 27M00000022357968
     * register_time : 2014-09-19
     * issue_status : 1
     * remarks : null
     * line_dispatch : 地调
     * sort : null
     * owner_name : null
     * dep_name : null
     * towers : null
     */

    private String id;
    private String name;
    private String line_level;
    private String duty_unit_id;
    private String dep_id;
    private String owner_id;
    private String trans_time;
    private String reject_time;
    private String assets_character;
    private String assets_unit;
    private String assets_code;
    private String project_code;
    private String project_name;
    private String equipment_increase_way;
    private String pipelined_code;
    private String wbs_code;
    private String line_no;
    private String belong_company;
    private String dispatching_unit_id;
    private String cross_regional_type;
    private String is_other;
    private String standard;
    private String rural_network;
    private String voltage_level;
    private String design_voltage_level;
    private String device_status;
    private String grounding_electrode;
    private String line_type;
    private String line_color;
    private String belong_main_line;
    private String erection_method;
    private double line_length;
    private double overhead_line_length;
    private int cable_line_length;
    private String start_type;
    private String start_power_station;
    private String start_position;
    private String start_switch_code;
    private String end_type;
    private String end_power_station;
    private String end_position;
    private String end_switch_code;
    private int max_permissible_current;
    private double operating_load_limit;
    private double rated_transmission_power;
    private String design_unit;
    private String build_unit;
    private String construction_unit;
    private String supervision_unit;
    private String functional_location_code;
    private String professional_type;
    private String is_fibre_optical;
    private String is_terminal_line;
    private String is_electrified_railway_line;
    private String same_pole_stringing_wire;
    private double zero_seq_cap;
    private double positive_cap;
    private double positive_seq_r;
    private double positive_seq_reactor;
    private double positive_seq_dn;
    private double positive_seq_dd;
    private double zero_seq_r;
    private double zero_seq_reactor;
    private double zero_seq_dn;
    private double zero_seq_dd;
    private String eq_code;
    private String register_time;
    private String issue_status;
    private String remarks;
    private String line_dispatch;
    private String sort;
    private String owner_name;
    private String dep_name;
    private String towers;

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

    public String getLine_level() {
        return line_level;
    }

    public void setLine_level(String line_level) {
        this.line_level = line_level;
    }

    public String getDuty_unit_id() {
        return duty_unit_id;
    }

    public void setDuty_unit_id(String duty_unit_id) {
        this.duty_unit_id = duty_unit_id;
    }

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    public String getReject_time() {
        return reject_time;
    }

    public void setReject_time(String reject_time) {
        this.reject_time = reject_time;
    }

    public String getAssets_character() {
        return assets_character;
    }

    public void setAssets_character(String assets_character) {
        this.assets_character = assets_character;
    }

    public String getAssets_unit() {
        return assets_unit;
    }

    public void setAssets_unit(String assets_unit) {
        this.assets_unit = assets_unit;
    }

    public String getAssets_code() {
        return assets_code;
    }

    public void setAssets_code(String assets_code) {
        this.assets_code = assets_code;
    }

    public String getProject_code() {
        return project_code;
    }

    public void setProject_code(String project_code) {
        this.project_code = project_code;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getEquipment_increase_way() {
        return equipment_increase_way;
    }

    public void setEquipment_increase_way(String equipment_increase_way) {
        this.equipment_increase_way = equipment_increase_way;
    }

    public String getPipelined_code() {
        return pipelined_code;
    }

    public void setPipelined_code(String pipelined_code) {
        this.pipelined_code = pipelined_code;
    }

    public String getWbs_code() {
        return wbs_code;
    }

    public void setWbs_code(String wbs_code) {
        this.wbs_code = wbs_code;
    }

    public String getLine_no() {
        return line_no;
    }

    public void setLine_no(String line_no) {
        this.line_no = line_no;
    }

    public String getBelong_company() {
        return belong_company;
    }

    public void setBelong_company(String belong_company) {
        this.belong_company = belong_company;
    }

    public String getDispatching_unit_id() {
        return dispatching_unit_id;
    }

    public void setDispatching_unit_id(String dispatching_unit_id) {
        this.dispatching_unit_id = dispatching_unit_id;
    }

    public String getCross_regional_type() {
        return cross_regional_type;
    }

    public void setCross_regional_type(String cross_regional_type) {
        this.cross_regional_type = cross_regional_type;
    }

    public String getIs_other() {
        return is_other;
    }

    public void setIs_other(String is_other) {
        this.is_other = is_other;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getRural_network() {
        return rural_network;
    }

    public void setRural_network(String rural_network) {
        this.rural_network = rural_network;
    }

    public String getVoltage_level() {
        return voltage_level;
    }

    public void setVoltage_level(String voltage_level) {
        this.voltage_level = voltage_level;
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

    public String getGrounding_electrode() {
        return grounding_electrode;
    }

    public void setGrounding_electrode(String grounding_electrode) {
        this.grounding_electrode = grounding_electrode;
    }

    public String getLine_type() {
        return line_type;
    }

    public void setLine_type(String line_type) {
        this.line_type = line_type;
    }

    public String getLine_color() {
        return line_color;
    }

    public void setLine_color(String line_color) {
        this.line_color = line_color;
    }

    public String getBelong_main_line() {
        return belong_main_line;
    }

    public void setBelong_main_line(String belong_main_line) {
        this.belong_main_line = belong_main_line;
    }

    public String getErection_method() {
        return erection_method;
    }

    public void setErection_method(String erection_method) {
        this.erection_method = erection_method;
    }

    public double getLine_length() {
        return line_length;
    }

    public void setLine_length(double line_length) {
        this.line_length = line_length;
    }

    public double getOverhead_line_length() {
        return overhead_line_length;
    }

    public void setOverhead_line_length(double overhead_line_length) {
        this.overhead_line_length = overhead_line_length;
    }

    public int getCable_line_length() {
        return cable_line_length;
    }

    public void setCable_line_length(int cable_line_length) {
        this.cable_line_length = cable_line_length;
    }

    public String getStart_type() {
        return start_type;
    }

    public void setStart_type(String start_type) {
        this.start_type = start_type;
    }

    public String getStart_power_station() {
        return start_power_station;
    }

    public void setStart_power_station(String start_power_station) {
        this.start_power_station = start_power_station;
    }

    public String getStart_position() {
        return start_position;
    }

    public void setStart_position(String start_position) {
        this.start_position = start_position;
    }

    public String getStart_switch_code() {
        return start_switch_code;
    }

    public void setStart_switch_code(String start_switch_code) {
        this.start_switch_code = start_switch_code;
    }

    public String getEnd_type() {
        return end_type;
    }

    public void setEnd_type(String end_type) {
        this.end_type = end_type;
    }

    public String getEnd_power_station() {
        return end_power_station;
    }

    public void setEnd_power_station(String end_power_station) {
        this.end_power_station = end_power_station;
    }

    public String getEnd_position() {
        return end_position;
    }

    public void setEnd_position(String end_position) {
        this.end_position = end_position;
    }

    public String getEnd_switch_code() {
        return end_switch_code;
    }

    public void setEnd_switch_code(String end_switch_code) {
        this.end_switch_code = end_switch_code;
    }

    public int getMax_permissible_current() {
        return max_permissible_current;
    }

    public void setMax_permissible_current(int max_permissible_current) {
        this.max_permissible_current = max_permissible_current;
    }

    public double getOperating_load_limit() {
        return operating_load_limit;
    }

    public void setOperating_load_limit(double operating_load_limit) {
        this.operating_load_limit = operating_load_limit;
    }

    public double getRated_transmission_power() {
        return rated_transmission_power;
    }

    public void setRated_transmission_power(double rated_transmission_power) {
        this.rated_transmission_power = rated_transmission_power;
    }

    public String getDesign_unit() {
        return design_unit;
    }

    public void setDesign_unit(String design_unit) {
        this.design_unit = design_unit;
    }

    public String getBuild_unit() {
        return build_unit;
    }

    public void setBuild_unit(String build_unit) {
        this.build_unit = build_unit;
    }

    public String getConstruction_unit() {
        return construction_unit;
    }

    public void setConstruction_unit(String construction_unit) {
        this.construction_unit = construction_unit;
    }

    public String getSupervision_unit() {
        return supervision_unit;
    }

    public void setSupervision_unit(String supervision_unit) {
        this.supervision_unit = supervision_unit;
    }

    public String getFunctional_location_code() {
        return functional_location_code;
    }

    public void setFunctional_location_code(String functional_location_code) {
        this.functional_location_code = functional_location_code;
    }

    public String getProfessional_type() {
        return professional_type;
    }

    public void setProfessional_type(String professional_type) {
        this.professional_type = professional_type;
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

    public String getIs_electrified_railway_line() {
        return is_electrified_railway_line;
    }

    public void setIs_electrified_railway_line(String is_electrified_railway_line) {
        this.is_electrified_railway_line = is_electrified_railway_line;
    }

    public String getSame_pole_stringing_wire() {
        return same_pole_stringing_wire;
    }

    public void setSame_pole_stringing_wire(String same_pole_stringing_wire) {
        this.same_pole_stringing_wire = same_pole_stringing_wire;
    }

    public double getZero_seq_cap() {
        return zero_seq_cap;
    }

    public void setZero_seq_cap(double zero_seq_cap) {
        this.zero_seq_cap = zero_seq_cap;
    }

    public double getPositive_cap() {
        return positive_cap;
    }

    public void setPositive_cap(double positive_cap) {
        this.positive_cap = positive_cap;
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

    public double getPositive_seq_dn() {
        return positive_seq_dn;
    }

    public void setPositive_seq_dn(double positive_seq_dn) {
        this.positive_seq_dn = positive_seq_dn;
    }

    public double getPositive_seq_dd() {
        return positive_seq_dd;
    }

    public void setPositive_seq_dd(double positive_seq_dd) {
        this.positive_seq_dd = positive_seq_dd;
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

    public double getZero_seq_dn() {
        return zero_seq_dn;
    }

    public void setZero_seq_dn(double zero_seq_dn) {
        this.zero_seq_dn = zero_seq_dn;
    }

    public double getZero_seq_dd() {
        return zero_seq_dd;
    }

    public void setZero_seq_dd(double zero_seq_dd) {
        this.zero_seq_dd = zero_seq_dd;
    }

    public String getEq_code() {
        return eq_code;
    }

    public void setEq_code(String eq_code) {
        this.eq_code = eq_code;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getIssue_status() {
        return issue_status;
    }

    public void setIssue_status(String issue_status) {
        this.issue_status = issue_status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLine_dispatch() {
        return line_dispatch;
    }

    public void setLine_dispatch(String line_dispatch) {
        this.line_dispatch = line_dispatch;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getTowers() {
        return towers;
    }

    public void setTowers(String towers) {
        this.towers = towers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.line_level);
        dest.writeString(this.duty_unit_id);
        dest.writeString(this.dep_id);
        dest.writeString(this.owner_id);
        dest.writeString(this.trans_time);
        dest.writeString(this.reject_time);
        dest.writeString(this.assets_character);
        dest.writeString(this.assets_unit);
        dest.writeString(this.assets_code);
        dest.writeString(this.project_code);
        dest.writeString(this.project_name);
        dest.writeString(this.equipment_increase_way);
        dest.writeString(this.pipelined_code);
        dest.writeString(this.wbs_code);
        dest.writeString(this.line_no);
        dest.writeString(this.belong_company);
        dest.writeString(this.dispatching_unit_id);
        dest.writeString(this.cross_regional_type);
        dest.writeString(this.is_other);
        dest.writeString(this.standard);
        dest.writeString(this.rural_network);
        dest.writeString(this.voltage_level);
        dest.writeString(this.design_voltage_level);
        dest.writeString(this.device_status);
        dest.writeString(this.grounding_electrode);
        dest.writeString(this.line_type);
        dest.writeString(this.line_color);
        dest.writeString(this.belong_main_line);
        dest.writeString(this.erection_method);
        dest.writeDouble(this.line_length);
        dest.writeDouble(this.overhead_line_length);
        dest.writeInt(this.cable_line_length);
        dest.writeString(this.start_type);
        dest.writeString(this.start_power_station);
        dest.writeString(this.start_position);
        dest.writeString(this.start_switch_code);
        dest.writeString(this.end_type);
        dest.writeString(this.end_power_station);
        dest.writeString(this.end_position);
        dest.writeString(this.end_switch_code);
        dest.writeInt(this.max_permissible_current);
        dest.writeDouble(this.operating_load_limit);
        dest.writeDouble(this.rated_transmission_power);
        dest.writeString(this.design_unit);
        dest.writeString(this.build_unit);
        dest.writeString(this.construction_unit);
        dest.writeString(this.supervision_unit);
        dest.writeString(this.functional_location_code);
        dest.writeString(this.professional_type);
        dest.writeString(this.is_fibre_optical);
        dest.writeString(this.is_terminal_line);
        dest.writeString(this.is_electrified_railway_line);
        dest.writeString(this.same_pole_stringing_wire);
        dest.writeDouble(this.zero_seq_cap);
        dest.writeDouble(this.positive_cap);
        dest.writeDouble(this.positive_seq_r);
        dest.writeDouble(this.positive_seq_reactor);
        dest.writeDouble(this.positive_seq_dn);
        dest.writeDouble(this.positive_seq_dd);
        dest.writeDouble(this.zero_seq_r);
        dest.writeDouble(this.zero_seq_reactor);
        dest.writeDouble(this.zero_seq_dn);
        dest.writeDouble(this.zero_seq_dd);
        dest.writeString(this.eq_code);
        dest.writeString(this.register_time);
        dest.writeString(this.issue_status);
        dest.writeString(this.remarks);
        dest.writeString(this.line_dispatch);
        dest.writeString(this.sort);
        dest.writeString(this.owner_name);
        dest.writeString(this.dep_name);
        dest.writeString(this.towers);
    }

    public LineName() {
    }

    protected LineName(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.line_level = in.readString();
        this.duty_unit_id = in.readString();
        this.dep_id = in.readString();
        this.owner_id = in.readString();
        this.trans_time = in.readString();
        this.reject_time = in.readString();
        this.assets_character = in.readString();
        this.assets_unit = in.readString();
        this.assets_code = in.readString();
        this.project_code = in.readString();
        this.project_name = in.readString();
        this.equipment_increase_way = in.readString();
        this.pipelined_code = in.readString();
        this.wbs_code = in.readString();
        this.line_no = in.readString();
        this.belong_company = in.readString();
        this.dispatching_unit_id = in.readString();
        this.cross_regional_type = in.readString();
        this.is_other = in.readString();
        this.standard = in.readString();
        this.rural_network = in.readString();
        this.voltage_level = in.readString();
        this.design_voltage_level = in.readString();
        this.device_status = in.readString();
        this.grounding_electrode = in.readString();
        this.line_type = in.readString();
        this.line_color = in.readString();
        this.belong_main_line = in.readString();
        this.erection_method = in.readString();
        this.line_length = in.readDouble();
        this.overhead_line_length = in.readDouble();
        this.cable_line_length = in.readInt();
        this.start_type = in.readString();
        this.start_power_station = in.readString();
        this.start_position = in.readString();
        this.start_switch_code = in.readString();
        this.end_type = in.readString();
        this.end_power_station = in.readString();
        this.end_position = in.readString();
        this.end_switch_code = in.readString();
        this.max_permissible_current = in.readInt();
        this.operating_load_limit = in.readDouble();
        this.rated_transmission_power = in.readInt();
        this.design_unit = in.readString();
        this.build_unit = in.readString();
        this.construction_unit = in.readString();
        this.supervision_unit = in.readString();
        this.functional_location_code = in.readString();
        this.professional_type = in.readString();
        this.is_fibre_optical = in.readString();
        this.is_terminal_line = in.readString();
        this.is_electrified_railway_line = in.readString();
        this.same_pole_stringing_wire = in.readString();
        this.zero_seq_cap = in.readDouble();
        this.positive_cap = in.readDouble();
        this.positive_seq_r = in.readDouble();
        this.positive_seq_reactor = in.readDouble();
        this.positive_seq_dn = in.readDouble();
        this.positive_seq_dd = in.readDouble();
        this.zero_seq_r = in.readDouble();
        this.zero_seq_reactor = in.readDouble();
        this.zero_seq_dn = in.readDouble();
        this.zero_seq_dd = in.readDouble();
        this.eq_code = in.readString();
        this.register_time = in.readString();
        this.issue_status = in.readString();
        this.remarks = in.readString();
        this.line_dispatch = in.readString();
        this.sort = in.readString();
        this.owner_name = in.readString();
        this.dep_name = in.readString();
        this.towers = in.readString();
    }

    public static final Parcelable.Creator<LineName> CREATOR = new Parcelable.Creator<LineName>() {
        @Override
        public LineName createFromParcel(Parcel source) {
            return new LineName(source);
        }

        @Override
        public LineName[] newArray(int size) {
            return new LineName[size];
        }
    };
}
