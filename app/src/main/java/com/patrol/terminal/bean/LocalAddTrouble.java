package com.patrol.terminal.bean;

import com.patrol.terminal.sqlite.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 添加隐患本地库
 */
@Table(database = AppDataBase.class)
public class LocalAddTrouble extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    private int local_id;
    @Column//数据id
    private String id;
    @Column// 隐患类型id 类别 3防雷
    private String type_id;
    @Column// 隐患类别名
    private String type_name;
    @Column
    private String line_id;
    @Column
    private String line_name;
    @Column// 个人任务id
    private String task_id;
    @Column// 杆塔id
    private String tower_id;
    @Column// 杆塔名字
    private String tower_name;
    @Column//是否网络获取 0, 离线保存1
    private String isdownload = "1";

    @Column // 隐患内容
    private String content;
    @Column// 发现人id  当前人
    private String find_user_id;
    @Column
    private String from_user_id;
    @Column// 发现人名字
    private String find_user_name;
    @Column// 发现人班组id
    private String find_dep_id;
    @Column// 发现人班组名字
    private String find_dep_name;
    @Column// 隐患入库状态（0：编制，1：待班长审核，2：待专责审核，3：审核通过，4：审核不通过）
    private String in_status;//提交默认为1

    //防雷隐患
    @Column
    private String wares_id;//关联特殊属性
    @Column// 关联特殊属性 名字
    private String wares_name;
    @Column// 建议处理措施 选择
    private String advice_deal_notes;//处理措施
    @Column
    private String remarks;//备注
    @Column
    private String clcs;//处理措施
    @Column// 隐患级别标识（1：一般（III类），2：重大（II类），3：紧急（I类））
    private String grade_sign;
    @Column
    private String find_time;//发现时间
    @Column
    private String troubleFiles = "";//图片
    @Column
    private String done_status = "";//图片


    public static void delData(String tower_id) {
        SQLite.delete(LocalAddTrouble.class).where(LocalAddTrouble_Table.tower_id.is(tower_id)).execute();
        ;
    }

    /**
     * 删除网络数据
     *
     * @param tower_id
     */
    public static void delNetData(String tower_id) {
        SQLite.delete(LocalAddTrouble.class)
                .where(LocalAddTrouble_Table.tower_id.is(tower_id))
                .and(LocalAddTrouble_Table.isdownload.is("0")).execute();
    }
    /**
     * 获取所有数据
     *
     * @return
     */
    public static List<LocalAddTrouble> getAllData(String tower_id) {
        return SQLite.select()
                .from(LocalAddTrouble.class)
                .where(LocalAddTrouble_Table.tower_id.is(tower_id))
                .queryList();
    }

    /**
     * 获取所有待提交数据
     *
     * @return
     */
    public static List<LocalAddTrouble> getAllLocalData(String line_id, String tower_id) {
        return SQLite.select()
                .from(LocalAddTrouble.class)
                .where(LocalAddTrouble_Table.isdownload.is("1"))
                .and(LocalAddTrouble_Table.tower_id.is(tower_id))
                .and(LocalAddTrouble_Table.line_id.is(line_id))
                .queryList();
    }

    /**
     * 查询添加状态
     *
     * @return
     */
    public static boolean addStatus(String tower_id, String line_id, String type_id) {

        LocalAddTrouble trouble = SQLite.select()
                .from(LocalAddTrouble.class)
                .where(LocalAddTrouble_Table.isdownload.is("1"))
                .and(LocalAddTrouble_Table.tower_id.is(tower_id))
                .and(LocalAddTrouble_Table.line_id.is(line_id))
                .and(LocalAddTrouble_Table.type_id.is(type_id))
                .querySingle();

        if (trouble == null)
            return true;
        else
            return false;
    }

    public String getFind_time() {
        return find_time;
    }

    public void setFind_time(String find_time) {
        this.find_time = find_time;
    }

    public String getIsdownload() {
        return isdownload;
    }

    public void setIsdownload(String isdownload) {
        this.isdownload = isdownload;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getTroubleFiles() {
        return troubleFiles;
    }

    public void setTroubleFiles(String troubleFiles) {
        this.troubleFiles = troubleFiles;
    }

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTower_id() {
        return tower_id;
    }

    public void setTower_id(String tower_id) {
        this.tower_id = tower_id;
    }

    public String getTower_name() {
        return tower_name;
    }

    public void setTower_name(String tower_name) {
        this.tower_name = tower_name;
    }

    public String getFind_user_id() {
        return find_user_id;
    }

    public void setFind_user_id(String find_user_id) {
        this.find_user_id = find_user_id;
    }

    public String getFind_user_name() {
        return find_user_name;
    }

    public void setFind_user_name(String find_user_name) {
        this.find_user_name = find_user_name;
    }

    public String getFind_dep_id() {
        return find_dep_id;
    }

    public void setFind_dep_id(String find_dep_id) {
        this.find_dep_id = find_dep_id;
    }

    public String getFind_dep_name() {
        return find_dep_name;
    }

    public void setFind_dep_name(String find_dep_name) {
        this.find_dep_name = find_dep_name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getIn_status() {
        return in_status;
    }

    public void setIn_status(String in_status) {
        this.in_status = in_status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWares_id() {
        return wares_id;
    }

    public void setWares_id(String wares_id) {
        this.wares_id = wares_id;
    }

    public String getWares_name() {
        return wares_name;
    }

    public void setWares_name(String wares_name) {
        this.wares_name = wares_name;
    }

    public String getAdvice_deal_notes() {
        return advice_deal_notes;
    }

    public void setAdvice_deal_notes(String advice_deal_notes) {
        this.advice_deal_notes = advice_deal_notes;
    }

    public String getGrade_sign() {
        return grade_sign;
    }

    public void setGrade_sign(String grade_sign) {
        this.grade_sign = grade_sign;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getClcs() {
        return clcs;
    }

    public void setClcs(String clcs) {
        this.clcs = clcs;
    }

    public String getDone_status() {
        return done_status;
    }

    public void setDone_status(String done_status) {
        this.done_status = done_status;
    }
}
