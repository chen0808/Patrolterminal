package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllControlCarBean implements Serializable {


    /**
     * workTools : [{"id":"DF6A4D96D613487494478D62B8571AC6","name":"dwqd","type":"edew","unit":"edewd","total":"wdwe","detail":"dwd","tool_type":"0","task_id":"F2597AA5F9B7414EA9BB8FC4C3637BD5"},{"id":"027C642CF7A04293ABE8C4C020BD4BDC","name":"dede","type":"fcasc","unit":"sdc","total":"cdsc","detail":"cdsc","tool_type":"0","task_id":"F2597AA5F9B7414EA9BB8FC4C3637BD5"},{"id":"0413C5461ECF4FADBFB8A61F3ABFB621","name":"ewd","type":"sc","unit":"sd","total":"cddsc","detail":"cdsccds","tool_type":"1","task_id":"F2597AA5F9B7414EA9BB8FC4C3637BD5"},{"id":"B24025E9FF5F4562AA8CDB97369C68D6","name":"dewde","type":"csdc","unit":"sdc","total":"cdscsd","detail":"scds","tool_type":"1","task_id":"F2597AA5F9B7414EA9BB8FC4C3637BD5"}]
     * workControlCard : {"id":"5F732A68483549A1803FCA47030F14E9","serial":null,"task_id":"F2597AA5F9B7414EA9BB8FC4C3637BD5","name":"3、断开1116和开一线31#塔三相引流。","start_time":"2019-5-7","end_time":"2019-5-9","leader":"叶怀刚","workProjectUsers":[{"id":"7A10A0AE6C834E518D50AA81A97AD121","u_id":"8085FCC8ACC84B5C82F2B8ADE40DB279","w_p_id":"33D92E685ECF4D988A3579A885CE2B76","w_c_id":"5F732A68483549A1803FCA47030F14E9","work_project_name":"明确工作任务，填写工作票；负责本项工作的安全施工和检修质量。","user_name":"李嘉辰"},{"id":"946B7843BEB34BBFB41474F08AE22CA0","u_id":"F9C9737CD8DF427586D96C5A9A9795A1","w_p_id":"B636FDC6F5654C9682B39C04B5600D23","w_c_id":"5F732A68483549A1803FCA47030F14E9","work_project_name":"登杆塔高处作业，安装视频监控设。","user_name":"贾来强"},{"id":"EBCFED46B2E34A3E9136474F5718D666","u_id":"C55FE53CAB524DE3A5F93737F40E747F","w_p_id":"35E457D301574B0594980AC3DB864336","w_c_id":"5F732A68483549A1803FCA47030F14E9","work_project_name":"工器具及材料准备，负责地面与高处作业人员的配合（工具、材料的传递）。","user_name":"安泰康"}],"workSafeRelations":[{"id":"5DC32AA63798400995353E41A267A63C","w_s_id":"1281DBC065EC4975AE3BD5D6A26DF1EB","w_c_id":"5F732A68483549A1803FCA47030F14E9","respon":"8085FCC8ACC84B5C82F2B8ADE40DB279","danger":"高空坠落","content":"高处作业必须使用安全带，安全带应系在杆塔牢固的构件上，系好安全带后必须检查扣环是否扣好；杆塔上作业转位时不得失去后备保护绳的保护。","user_name":"李嘉辰"},{"id":"86CD1E72468B4470A7EBF9E6D026E7EC","w_s_id":"5FF4FAF2B063419AA9D760C63BCBD8A6","w_c_id":"5F732A68483549A1803FCA47030F14E9","respon":"8085FCC8ACC84B5C82F2B8ADE40DB279","danger":"误登杆塔","content":"登杆塔前必须仔细核对线路双重名称、杆塔号，确认无误后方可攀登。","user_name":"李嘉辰"},{"id":"7554A8ECCADD4C27A14D786F917A0209","w_s_id":"9E9B852345C84B828FA5D9039DED42E9","w_c_id":"5F732A68483549A1803FCA47030F14E9","respon":"8085FCC8ACC84B5C82F2B8ADE40DB279","danger":"落物伤人","content":"高处作业应使用工具袋，所使用的工器具、材料应放置稳妥，防止坠落伤人。工器具及材料应用绳索绑牢传递，严禁上下抛掷。作业人员不得站在作业处的垂直下方，高空作业区内不得有无关人员通行或逗留。","user_name":"李嘉辰"}]}
     * workQualityCard : {"id":"9F521AC0F336466BA308C3762E9893EF","task_id":"F2597AA5F9B7414EA9BB8FC4C3637BD5","remark":"saas","repair_content":"3、断开1116和开一线31#塔三相引流。","leader":"叶怀刚","start_time":"2019-5-7","end_time":"2019-5-9","dep_name":null,"workers":"王小龙,李嘉辰","workStandardRelations":[{"id":"7D87FBC9D10F49D88799AB35BC5C253B","w_q_c_id":"9F521AC0F336466BA308C3762E9893EF","w_q_s_id":"EA05E8938C4D4DD486573C31A605CDBE","status":"asdsa","process":"核对现场","standard":"持票核对线路\u201c三号\u201d。","warning":"误登杆塔"},{"id":"E316517739584820B2C79C14EEB3C811","w_q_c_id":"9F521AC0F336466BA308C3762E9893EF","w_q_s_id":"B39DEB8557364D4986BA673FD2909934","status":"ada","process":"安全用具及工器具检查","standard":"安全带无破损，扣环无卡涩现象，且配件齐全；绝缘绳索无破损及断股现象;","warning":"高空坠落"},{"id":"F553B73521DD46BCB86BFB26A4375783","w_q_c_id":"9F521AC0F336466BA308C3762E9893EF","w_q_s_id":"FD6664DB916A481DBA26D3D758E91A08","status":"ada","process":"攀登杆塔","standard":"检查杆根、爬梯、脚钉完好牢固。","warning":"高空坠落"},{"id":"1D91F95DC5354BC8BFD400FD766B1B53","w_q_c_id":"9F521AC0F336466BA308C3762E9893EF","w_q_s_id":"41B82A355C9844D7A5D3DC9ED5C483B0","status":"adsa","process":"抛扔绝缘绳","standard":"杆塔上工作人员安装视频监控设备时手脚动作不得过大。杆上工作人员抛至绳索时手脚动作不得过大；","warning":"高空坠落触电"}]}
     */

    private CardControl cardControl;
    private CardQuality cardQuality;
    private List<CardTool> cardTool;

    public CardControl getCardControl() {
        return cardControl;
    }

    public void setCardControl(CardControl cardControl) {
        this.cardControl = cardControl;
    }

    public CardQuality getCardQuality() {
        return cardQuality;
    }

    public void setCardQuality(CardQuality cardQuality) {
        this.cardQuality = cardQuality;
    }

    public List<CardTool> getCardTool() {
        return cardTool;
    }

    public void setCardTool(List<CardTool> cardTool) {
        this.cardTool = cardTool;
    }
}
