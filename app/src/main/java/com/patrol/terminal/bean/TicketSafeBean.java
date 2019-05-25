package com.patrol.terminal.bean;

import java.io.Serializable;

public class TicketSafeBean implements Serializable {

    /**
     * id : 7B38ACA3FF104856B491223CF3BD3949
     * type : 1
     * task_type : 1
     * safe_way : （1）工作前向工作许可人申请，明确线路已经停电，并经值班员许可后，方可开始工作。（2）到达现场后列队宣读工作票，向全体工作班成员交代清楚停电线路的双重称号及工作任务后，用合格的验电器验电，验明线路确无电压后，在工作地段两端挂设牢固可靠的接地线,挂接接电线时先接接地端后接导线端，拆除时相反。（3）停电线路杆塔上的工作应在良好的天气条件下进行，如遇雨、雪、雷、雾及风力大于5级时，均应停止工作。（4）作业人员应携带相对应停电检修线路的识别标记，攀登杆塔作业前，作业人员应仔细核对停电线路的识别标记、双重名称及杆塔号，确认无误后方可攀登杆塔进行工作。（5）高处作业必须使用安全带，安全带应系在杆塔牢固的构件上，且长短并用，系好安全带后必须检查扣环是否扣好；杆塔上作业转位时不得失去后备保护绳的保护。（6）脱导线前应先加挂防止导线脱落的保护装置。（7）起吊或放落工具、材料等物体时，应使用绝缘无极绳索，起吊绳索与110kV带电线路距离大于5m。（8）在对周围环境较复杂杆塔区段的作业应设专人进行严格监护。（9）工作结束后，收齐工作班人员，拆除接地线，向工作许可人汇报。
     * safe_ways : null
     * remark : null
     */

    private String id;
    private String type;
    private String task_type;
    private String safe_way;
    private Object safe_ways;
    private Object remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public String getSafe_way() {
        return safe_way;
    }

    public void setSafe_way(String safe_way) {
        this.safe_way = safe_way;
    }

    public Object getSafe_ways() {
        return safe_ways;
    }

    public void setSafe_ways(Object safe_ways) {
        this.safe_ways = safe_ways;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }
}
