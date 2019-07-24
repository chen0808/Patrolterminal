package com.patrol.terminal.bean;

/**
 * 本类由Code-Builder自动生成
 * 表名: EQ_VEHICLE - 车辆基本信息
 *
 * Created with Code-Builder.
 * Date：2019-05-07 16:02:36
 */
public class EqVehicleBean {

    // 数据id
    private String id;

    // 编号
    private String vehicle_num;

    // 车型
    private String vehicle_model;

    // 车牌号
    private String car_number;

    // 经销商
    private String distributor;

    // 生产厂家
    private String factory;

    // 采购价格
    private Double price;

    // 经办人
    private String operator;

    // 结构特点（1：自动挡，2：手动挡，3：ABS，4：其他）
    private String structure;

    // 使用性质
    private String use_nature;

    // 发动机型号
    private String engine_num;

    // 车架型号
    private String frame_num;

    // 车辆状态（1：正常，2：出勤，3：故障，4：报废）
    private String status;

    // 备注
    private String remarks;

    // 司机
    private String driver;

    // 所属班组id
    private String dep_id;

    /*** 自定义字段 ***/

    // 是否被使用（0：否；1：是）
    private String is_use;

    private boolean check;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicle_num() {
        return vehicle_num;
    }

    public void setVehicle_num(String vehicle_num) {
        this.vehicle_num = vehicle_num;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getUse_nature() {
        return use_nature;
    }

    public void setUse_nature(String use_nature) {
        this.use_nature = use_nature;
    }

    public String getEngine_num() {
        return engine_num;
    }

    public void setEngine_num(String engine_num) {
        this.engine_num = engine_num;
    }

    public String getFrame_num() {
        return frame_num;
    }

    public void setFrame_num(String frame_num) {
        this.frame_num = frame_num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getIs_use() {
        return is_use;
    }

    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}