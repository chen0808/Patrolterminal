package com.patrol.terminal.network;

import com.patrol.terminal.base.BaseResult;
import com.patrol.terminal.bean.AddGroupTaskReqBean;
import com.patrol.terminal.bean.AddPersonalTaskReqBean;
import com.patrol.terminal.bean.AddressBookBean;
import com.patrol.terminal.bean.AllControlCarBean;
import com.patrol.terminal.bean.ClassMemberBean;
import com.patrol.terminal.bean.ControlCardBean;
import com.patrol.terminal.bean.ControlDepWorkBean;
import com.patrol.terminal.bean.ControlDepWorkBean2;
import com.patrol.terminal.bean.ControlQualityBean;
import com.patrol.terminal.bean.ControlToolBean;
import com.patrol.terminal.bean.ControlToolBeanList;
import com.patrol.terminal.bean.CreateRobTaskBean;
import com.patrol.terminal.bean.DangerBean;
import com.patrol.terminal.bean.DayListBean;
import com.patrol.terminal.bean.DayOfWeekBean;
import com.patrol.terminal.bean.DayPlanDetailBean;
import com.patrol.terminal.bean.DefectBean;
import com.patrol.terminal.bean.DefectFragmentBean;
import com.patrol.terminal.bean.DepUserBean;
import com.patrol.terminal.bean.EqTower;
import com.patrol.terminal.bean.FieldAntiInspectionBean;
import com.patrol.terminal.bean.FirstTicketBean;
import com.patrol.terminal.bean.FourTicketBean;
import com.patrol.terminal.bean.GTQXCLbean;
import com.patrol.terminal.bean.GetToPostCheckBean;
import com.patrol.terminal.bean.GradeBean;
import com.patrol.terminal.bean.GroupBean;
import com.patrol.terminal.bean.GroupOfDayBean;
import com.patrol.terminal.bean.GroupTaskBean;
import com.patrol.terminal.bean.HwcwBean;
import com.patrol.terminal.bean.JDDZbean;
import com.patrol.terminal.bean.JYZbean;
import com.patrol.terminal.bean.JxbSignInfo;
import com.patrol.terminal.bean.LineBean;
import com.patrol.terminal.bean.LineCheckBean;
import com.patrol.terminal.bean.LineName;
import com.patrol.terminal.bean.LineTypeBean;
import com.patrol.terminal.bean.LoginReqBean;
import com.patrol.terminal.bean.MonthListBean;
import com.patrol.terminal.bean.MonthPlanBean;
import com.patrol.terminal.bean.MonthPlanDetailBean;
import com.patrol.terminal.bean.OvaTodoBean;
import com.patrol.terminal.bean.OverPlanReqBean;
import com.patrol.terminal.bean.OverhaulFzrSendBean;
import com.patrol.terminal.bean.OverhaulMonitorSendBean;
import com.patrol.terminal.bean.OverhaulMonthBean;
import com.patrol.terminal.bean.OverhaulSendUserBean;
import com.patrol.terminal.bean.OverhaulSendUserBean2;
import com.patrol.terminal.bean.OverhaulYearBean;
import com.patrol.terminal.bean.OverhaulZZSendBean;
import com.patrol.terminal.bean.OverhaulZzTaskBean;
import com.patrol.terminal.bean.PatrolContentBean;
import com.patrol.terminal.bean.PatrolDetailBean;
import com.patrol.terminal.bean.PatrolListBean;
import com.patrol.terminal.bean.PatrolRecordBean;
import com.patrol.terminal.bean.PatrolRecordPicBean;
import com.patrol.terminal.bean.PersonalTaskListBean;
import com.patrol.terminal.bean.PlanRepairBean;
import com.patrol.terminal.bean.PlanTypeBean;
import com.patrol.terminal.bean.PlanWeekReqBean;
import com.patrol.terminal.bean.PositionInfo;
import com.patrol.terminal.bean.PositionListBean;
import com.patrol.terminal.bean.RfInfo;
import com.patrol.terminal.bean.SavaEleLineBean;
import com.patrol.terminal.bean.SavaLineBean;
import com.patrol.terminal.bean.SavaMonthDefDanBean;
import com.patrol.terminal.bean.SaveDefMonthReqBean;
import com.patrol.terminal.bean.SaveTodoReqbean;
import com.patrol.terminal.bean.SecondTicketBean;
import com.patrol.terminal.bean.SituationBean;
import com.patrol.terminal.bean.SpecialAttrBean;
import com.patrol.terminal.bean.SpecialAttrList;
import com.patrol.terminal.bean.SubmitPlanReqBean;
import com.patrol.terminal.bean.SubmitPlanReqStateBean;
import com.patrol.terminal.bean.ThirdTicketBean;
import com.patrol.terminal.bean.TicketSafeContent;
import com.patrol.terminal.bean.TodoListBean;
import com.patrol.terminal.bean.Tower;
import com.patrol.terminal.bean.TowerListBean;
import com.patrol.terminal.bean.TrainAuditorBean;
import com.patrol.terminal.bean.TrainLevelBean;
import com.patrol.terminal.bean.TrainingAddTaskBean;
import com.patrol.terminal.bean.TrainingAddTempBean;
import com.patrol.terminal.bean.TrainingMonthPlanBean;
import com.patrol.terminal.bean.TrainingTaskBean;
import com.patrol.terminal.bean.TrainingTempPlanBean;
import com.patrol.terminal.bean.TrainingYearPlanBean;
import com.patrol.terminal.bean.TypeBean;
import com.patrol.terminal.bean.UserBean;
import com.patrol.terminal.bean.WeekListBean;
import com.patrol.terminal.bean.WeekOfMonthBean;
import com.patrol.terminal.bean.WeekPlanBean;
import com.patrol.terminal.bean.WeekPlanDetailBean;
import com.patrol.terminal.bean.YXtoJXbean;
import com.patrol.terminal.overhaul.OverhaulFileBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


/**
 * Created by fg on 2018/7/25.
 */

public interface ApiServise {

    //网络请求超时时间 单位毫秒
    int DEFAULT_TIMEOUT = 20000;

    //月计划列表
    @GET("/plan/month/planGET")
    Observable<BaseResult<List<MonthPlanBean>>> getMonthPlanList(@Query("year") int year, @Query("month") int month, @Query("dep_id") String dep_id);

    //月计划列表
    @GET("/plan/month/planGET")
    Observable<BaseResult<List<MonthPlanBean>>> getMonthPlanList(@Query("year") int year, @Query("month") int month, @Query("dep_id") String dep_id, @Query("state") String state);

    //月计划列表
    @GET("plan/month/planGET")
    Observable<BaseResult<MonthListBean>> getMonthPlan(@Query("year") int year, @Query("month") int month, @Query("dep_id") String dep_id, @Query("audit_status") String audit_status, @Query("order") String order);

    //月计划列表
    @GET("/plan/month/lineGET")
    Observable<BaseResult<List<LineCheckBean>>> getLineList(@Query("year") int year, @Query("month") int month, @Query("dep_id") String dep_id, @Query("type_sign") String type_sign);

    //周计划添加获取月计划列表
    @GET("plan/month/line/monthGET")
    Observable<BaseResult<List<WeekOfMonthBean>>> getWeekList(@Query("year") int year, @Query("month") int month, @Query("dep_id") String dep_id, @Query("type_id") String type_id);

    //获取周计划杆段列表
    @GET("plan/month/line/monthGET")
    Observable<BaseResult<List<WeekOfMonthBean>>> getWeekListWeek(@Query("year") int year, @Query("month") int month, @Query("dep_id") String dep_id, @Query("type_id") String type_id, @Query("line_id") String line_id);

    //月计划列表
    @GET("/plan/month/dataGET")
    Observable<BaseResult<List<MonthPlanBean>>> getMonthPlan(@Query("id") String id);

    //获取杆段信息
    @GET("/eq/line/towers/allGET")
    Observable<BaseResult<List<EqTower>>> getTowers(@Query("line_id") String line_id);

    //根据当前日期查询所属周下所有计划接口
    @GET("/plan/day/line/planDayGET")
    Observable<BaseResult<List<DayPlanDetailBean>>> getDayLine(@Query("type_id") String type_id);


    //根据当前日期查询所属周下所有计划接口
    @GET("/plan/week/tower/weekGET")
    Observable<BaseResult<List<DayOfWeekBean>>> getDayPlan(@Query("year") String year, @Query("month") String month, @Query("dep_id") String dep_id);


    //生成抢单任务
    @POST("/task/group/list/updatePOST")
    Observable<BaseResult<List<DayOfWeekBean>>> createRobTask(@Body CreateRobTaskBean bean);

    //生成抢单任务
    @POST("/task/personal/savePOST")
    Observable<BaseResult<List<DayOfWeekBean>>> addPersonTask(@Body List<GroupTaskBean> bean);


    //根据当前日期查询所属周下所有计划接口
    @GET("/plan/week/tower/weekGET")
    Observable<BaseResult<List<DayOfWeekBean>>> getDayPlan(@Query("year") String year, @Query("month") String month, @Query("dep_id") String dep_id, @Query("type_id") String type_id, @Query("line_id") String line_id);

    //添加日计划
    @POST("/plan/day/savePOST")
    Observable<BaseResult<List<LineTypeBean>>> addDayPlan(@Body List<DayOfWeekBean> bean);


    //添加日计划
    @POST("/plan/month/line/electricPOST")
    Observable<BaseResult<List<LineTypeBean>>> saveMonthPlan(@Body SavaEleLineBean bean);

    //添加日计划
    @POST("/plan/month/line/tempPOST")
    Observable<BaseResult<List<LineTypeBean>>> saveMonthPlan(@Body SavaLineBean bean);

    //添加日计划
    @POST("/plan/week/tower/tempPOST")
    Observable<BaseResult<List<LineTypeBean>>> saveWeekPlan(@Body SavaLineBean bean);


    //添加日计划
    @POST("/plan/day/tower/tempPOST")
    Observable<BaseResult<List<LineTypeBean>>> saveDayPlan(@Body SavaLineBean bean);

    //获取月缺陷库
    @GET("/task/defect/listGET")
    Observable<BaseResult<List<DefectBean>>> getDefect(@Query("line_id") String line_id, @Query("status") String status, @Query("audit_status") String audit_status);

    //获取月缺陷库
    @GET("/task/danger/listGET")
    Observable<BaseResult<List<DefectBean>>> getDanger(@Query("line_id") String line_id, @Query("status") String status, @Query("audit_status") String audit_status);

    //获取周缺陷库
    @GET("/task/defect/listGET")
    Observable<BaseResult<List<DefectBean>>> getWeekDefect(@Query("line_id") String line_id, @Query("month_id") String month_id, @Query("week_id") String week_id, @Query("status") String status, @Query("audit_status") String audit_status);

    //获取周缺陷库
    @GET("/task/danger/listGET")
    Observable<BaseResult<List<DefectBean>>> getWeekDanger(@Query("line_id") String line_id, @Query("month_id") String month_id, @Query("week_id") String week_id, @Query("status") String status, @Query("audit_status") String audit_status);

    //获取日缺陷库
    @GET("/task/defect/listGET")
    Observable<BaseResult<List<DefectBean>>> getDayDefect(@Query("line_id") String line_id, @Query("week_id") String week_id, @Query("day_id") String day_id, @Query("status") String status, @Query("audit_status") String audit_status);

    //获取日缺陷库
    @GET("/task/danger/listGET")
    Observable<BaseResult<List<DefectBean>>> getDayDanger(@Query("line_id") String line_id, @Query("week_id") String week_id, @Query("day_id") String day_id, @Query("status") String status, @Query("audit_status") String audit_status);

    //获取已经存在的缺陷列表
    @GET("/task/defect/listGET")
    Observable<BaseResult<List<DefectBean>>> getHaveDefect(@Query("month_line_id") String month_line_id, @Query("status") String status, @Query("audit_status") String audit_status);

    //获取已经存在的隐患列表
    @GET("/task/danger/listGET")
    Observable<BaseResult<List<DefectBean>>> getHaveDanger(@Query("month_line_id") String month_line_id, @Query("status") String status, @Query("audit_status") String audit_status);


    //获取组成员列表
    @GET("/task/group/user/usersGET")
    Observable<BaseResult<List<DepUserBean>>> getPersonal(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("dep_id") String dep_id);

    //组长获取组成员列表
    @GET("/task/group/user/teamGET")
    Observable<BaseResult<List<DepUserBean>>> getGroupPersonal(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("dep_id") String dep_id, @Query("user_id") String user_id);

    //组长获取组任务列表
    @GET("/task/group/list/listGET")
    Observable<BaseResult<List<GroupTaskBean>>> getAddGroupList(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("duty_user_id") String duty_user_id, @Query("type_id") String type_id, @Query("allot_status") String allot_status, @Query("is_rob") String is_rob);


    //月计划添加缺陷
    @POST("/plan/month/savePOST")
    Observable<BaseResult<List<DangerBean>>> savaDefDanMonth(@Body SavaMonthDefDanBean bean);

    //月计划添加缺陷
    @POST("/plan/week/editPOST")
    Observable<BaseResult<List<DangerBean>>> savaDefDanWeek(@Body SavaMonthDefDanBean bean);

    //月计划添加缺陷
    @POST("/plan/day/editPOST")
    Observable<BaseResult<List<DangerBean>>> savaDefDanDay(@Body SavaMonthDefDanBean bean);

    //月计划添加缺陷
    @POST("/task/defect/saveDefectByPlan")
    Observable<BaseResult<List<DangerBean>>> savaDefMonth(@Body SaveDefMonthReqBean bean);

    //月计划添加隐患
    @POST("/task/danger/saveDangerByPlan")
    Observable<BaseResult<List<DangerBean>>> savaDanMonth(@Body SaveDefMonthReqBean bean);


    //添加个人任务
    @POST("/task/personal/savePOST")
    Observable<BaseResult<List<DangerBean>>> savaPersonalTask(@Body AddPersonalTaskReqBean bean);

    //添加小组任务
    @POST("/task/group/savePOST")
    Observable<BaseResult<List<DangerBean>>> savaGroupTask(@Body AddGroupTaskReqBean bean);

    //周计划添加
    @POST("/plan/week/auditPOST")
    Observable<BaseResult<List<MonthPlanBean>>> submitWeekPlan(@Body SubmitPlanReqBean bean);

    //月计划添加
    @POST("/plan/month/auditPOST")
    Observable<BaseResult<List<MonthPlanBean>>> submitMonthPlan(@Body SubmitPlanReqBean bean);


    //提交检修月计划审核
    //http://172.16.15.60:9096/plan/repair/updatePOST
    @POST("plan/repair/auditPOST")
    Observable<BaseResult<List<MonthPlanBean>>> submitMonthOve(@Body List<OverPlanReqBean> bean);

    //一键审核
    @POST("/plan/month/line/batchAuditPOST/state")
    Observable<BaseResult<List<MonthPlanBean>>> submitMonthPlanState(@Body SubmitPlanReqStateBean bean);

    //一键审核周计划
    @POST("plan/week/line/batchAuditPOST/state")
    Observable<BaseResult<List<WeekPlanBean>>> submitWeekPlanState(@Body SubmitPlanReqStateBean bean);


    //月计划列表
    @GET("/plan/week/dataGET")
    Observable<BaseResult<List<MonthPlanBean>>> getMonthPlanList(@Query("year") String year, @Query("month") String month, @Query("dep_id") String dep_id);


    //月计划详情
    @GET("/plan/month/line/listGET")
    Observable<BaseResult<List<MonthPlanDetailBean>>> getMonthDetail(@Query("year") int year, @Query("month") int month, @Query("line_id") String line_id);

    //周计划详情
    @GET("/plan/week/line/listGET")
    Observable<BaseResult<List<WeekListBean>>> getWeekDetail(@Query("year") String year, @Query("month") String month, @Query("week") String week, @Query("line_id") String line_id);


    //保存周计划
    @POST("/plan/week/savePOST")
    Observable<BaseResult<List<LineTypeBean>>> saveWeek(@Body PlanWeekReqBean bean);


    //周计划列表
    @GET("/plan/week/tower/listGET ")
    Observable<BaseResult<List<WeekListBean>>> getWeekList(@Query("year") String year, @Query("month") String month, @Query("week") String week, @Query("dep_id") String dep_id, @Query("audit_status") String audit_status, @Query("order") String order);

    //日计划列表
    @GET("/plan/day/tower/listGET")
    Observable<BaseResult<List<DayListBean>>> getDayList(@Query("year") String year, @Query("month") String month, @Query("day") String week, @Query("dep_id") String dep_id, @Query("order") String order);

    //日计划列表
    @GET("/plan/day/line/dayGET")
    Observable<BaseResult<GroupOfDayBean>> getDayList(@Query("dep_id") String dep_id, @Query("type_id") String type_id);

    //根据当前日期查询所属周下所有计划接口
    @GET("plan/day/tower/dayGET")
    Observable<BaseResult<List<GroupOfDayBean>>> getDayofGroup(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("dep_id") String dep_id, @Query("type_id") String type_id);

    //组任务列表
    @GET("/task/group/list/listGET")
    Observable<BaseResult<List<GroupTaskBean>>> getGroupList(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("dep_id") String dep_id, @Query("duty_user_id") String duty_user_id, @Query("work_user_id") String work_user_id, @Query("order") String order);

    @GET("/task/group/user/groupGET")
    Observable<BaseResult<List<GroupTaskBean>>> getGroupList(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("user_id") String user_id);

    @GET("/task/repair/user/taskGET")
    Observable<BaseResult<List<YXtoJXbean>>> getRepairList(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("user_id") String user_id, @Query("sign") String sign);

    //个人任务列表
    @GET("task/personal/listGET")
    Observable<BaseResult<List<PersonalTaskListBean>>> getPersonalListOfGroup(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("group_list_id") String group_list_id);

    //个人任务列表
    @GET("task/personal/listGET")
    Observable<BaseResult<List<PersonalTaskListBean>>> getPersonalList(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("user_id") String user_id);

    //班长和小组长获取运行待办
    @GET("task/personal/listGET")
    Observable<BaseResult<List<PersonalTaskListBean>>> getYXtodoList(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("dep_id") String dep_id, @Query("user_id") String user_id, @Query("audit_status") String audit_status);


    @GET("task/personal/listGET")
    Observable<BaseResult<List<PersonalTaskListBean>>> getCheckList(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("user_id") String user_id, @Query("audit_status") String audit_status);

    //个人任务列表
    @GET("task/personal/listGET")
    Observable<BaseResult<List<PersonalTaskListBean>>> getDepPersonalList(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("dep_id") String dep_id);

    //个人任务列表
    @GET("task/personal/listGET")
    Observable<BaseResult<List<PersonalTaskListBean>>> getDepPersonalList(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("dep_id") String dep_id, @Query("limit") String limit);

    //个人任务列表
    @GET("task/personal/listGET")
    Observable<BaseResult<List<PersonalTaskListBean>>> getDepPersonalList(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("dep_id") String dep_id, @Query("audit_status") String audit_status, @Query("limit") String limit);

    //日计划详情
    @GET("/plan/day/line/allGET")
    Observable<BaseResult<List<WeekPlanDetailBean>>> getDayDetail(@Query("id") String d_id);

    //小组任务详情
    @GET("task/group/list/listGET")
    Observable<BaseResult<List<GroupTaskBean>>> getGroupDetail(@Query("id") String id);

    //个人任务详情
    @GET("/plan/type/dataGET")
    Observable<BaseResult<TypeBean>> getSign(@Query("id") String id);

    //获取线路和杆塔信息
    @GET("/plan/week/android/line")
    Observable<BaseResult<List<LineBean>>> getLine(@Query("year") String year, @Query("month") String month, @Query("dep_id") String dep_id);

    //周计划杆塔信息
    @GET("admin/tower/listGET")
    Observable<BaseResult<List<EqTower>>> getTower(@Query("order") String order, @Query("line_id") String lineId);

    //日计划杆塔信息
    @GET("plan/week/tower/towerGET")
    Observable<BaseResult<List<PlanTypeBean>>> getDayTower(@Query("year") String year, @Query("month") String month, @Query("week") String week, @Query("dep_id") String dep_id, @Query("line_id") String lineId);


    //获取组任务的线路和杆塔信息
    @GET("group/android/line")
    Observable<BaseResult<List<LineBean>>> getGroupLine(@Query("year") String year, @Query("month") String month, @Query("type_id") String type_id, @Query("day") String day);


    //获取个人任务的线路和杆塔信息
    @GET("personal/line")
    Observable<BaseResult<List<LineBean>>> getPersonalLine(@Query("year") String year, @Query("month") String month, @Query("type_id") String type_id, @Query("day") String day);

    //获取组员列表
    @GET("personal/user")
    Observable<BaseResult<List<UserBean>>> getUserList(@Query("dep_id") String dep_id);

    //计划类型
    @GET("plan/type/listGET")
    Observable<BaseResult<List<LineTypeBean>>> getLineType();

    //计划类型
    @GET("plan/type/listGET")
    Observable<BaseResult<List<LineTypeBean>>> getLineType(@Query("temp") String temp);

    //获取临时任务线路的杆塔
    @GET("/eq/tower/partGET")
    Observable<BaseResult<List<Tower>>> getTempTower(@Query("line_id") String line_id, @Query("order") String order);

    //巡视记录条件查询
    @GET("patrol/list")
    Observable<BaseResult<List<PatrolListBean>>> getPatrolList(@Query("Inspector") String Inspector, @Query("Liable") String Liable, @Query("Liable") String team);


    //巡视记录详情
    @GET("patrol/detail")
    Observable<BaseResult<PatrolDetailBean>> getPatrolDetail(@Query("id") String id);

    //审核
    @GET("patrol/confirm")
    Observable<BaseResult<List<PatrolRecordBean>>> patrolConfirm(@Query("Id") String Id, @Query("auditorId") String auditorId, @Query("operateType") String operateType);


    //上传打卡信息
    @Multipart
    @POST("card/save")
    Observable<BaseResult> upload(@QueryMap Map<String, String> params, @Part MultipartBody.Part file);


    //查询打卡记录
    @GET("card/record")
    Observable<BaseResult<List<PatrolRecordBean>>> sendPatrolRecord(@Query("user_id") String card_user, @Query("card_time like") String card_time);

    //周计划添加
    @POST("plan/week/android/save")
    Observable<BaseResult<List<MonthPlanBean>>> saveWeekPlan(@Body WeekPlanBean bean);


    //组任务添加
    @POST("group/android/save")
    Observable<BaseResult<List<MonthPlanBean>>> saveGroupTask(@Body WeekPlanBean bean);


    //个人任务添加
    @POST("personal/save")
    Observable<BaseResult<List<MonthPlanBean>>> savePersonalTask(@Body WeekPlanBean bean);

    //巡视记录上传
    @Multipart
    @POST("patrol/save")
    Observable<BaseResult<List<PatrolRecordBean>>> savePatrol(@QueryMap Map<String, String> params, @Part() List<MultipartBody.Part> parts);


    //登录
    @POST("sys/pda/entry")
    Observable<BaseResult<LoginReqBean>> login(@Query("login") String phone, @Query("password") String pwd);

    //上传红外线测温
    @POST("task/temperature/pda/updatePOST")
    Observable<BaseResult<HwcwBean>> upLoadInfrared(@QueryMap Map<String, String> params);

    //上传接地电阻
    @POST("task/resistance/pda/updatePOST")
    Observable<BaseResult> upLoadResistance(@QueryMap Map<String, String> params);

    //上传零值检测
    @POST("task/insulator/pda/updatePOST")
    Observable<BaseResult> upLoadInsulator(@QueryMap Map<String, String> params);

    //上传斜杆塔倾斜测量
    @POST("task/tilt/pda/updatePOST")
    Observable<BaseResult> upLoadTowerBias(@QueryMap Map<String, String> params);

    //缺陷巡视内容列表
    @GET("task/defect/patrol/patrolList")
    Observable<BaseResult<List<PatrolContentBean>>> getPatrolContent(@Query("task_id") String task_id);

    //巡视内容对应缺陷标准列表
    @GET("task/defect/grade/gradeList")
    Observable<BaseResult<List<GradeBean>>> getPatrolSpinner(@Query("category") String category);

    //缺陷录入
    @Multipart
    @POST("/task/defect/savePOST")
//    Observable<BaseResult> commitPatrolContent(@Part("patrol_id") RequestBody patrol_id, @Part("category_id") RequestBody category_id,
//                                               @Part("grade_id") RequestBody grade_id, @Part("content") RequestBody content,
//                                               @Part("start_id") RequestBody start_id, @Part("end_id") RequestBody end_id,
//                                               @Part("line_id") RequestBody line_id, @Part("task_id") RequestBody task_id,
//                                               @Part() List<MultipartBody.Part> parts);
    Observable<BaseResult> commitPatrolContent(@PartMap Map<String, RequestBody> params);

    //缺陷录入
    @Multipart
    @POST("task/personal/picturePOST")
//    Observable<BaseResult> commitPatrolContent(@Part("patrol_id") RequestBody patrol_id, @Part("category_id") RequestBody category_id,
//                                               @Part("grade_id") RequestBody grade_id, @Part("content") RequestBody content,
//                                               @Part("start_id") RequestBody start_id, @Part("end_id") RequestBody end_id,
//                                               @Part("line_id") RequestBody line_id, @Part("task_id") RequestBody task_id,
//                                               @Part() List<MultipartBody.Part> parts);
    Observable<BaseResult> commitPatrolRecord(@PartMap Map<String, RequestBody> params);

    //巡视内容对应隐患列表
    @GET("eq/tower/wares/towerByIdGET")
    Observable<BaseResult<List<SpecialAttrBean>>> getSpecialAttr(@Query("id") String p_id);

    //隐患录入
    @Multipart
    @POST("/task/danger/savePOST")
    Observable<BaseResult> commitTrouble(@PartMap Map<String, RequestBody> params);

    //获取所有用户
    @GET("sys/user/allGET")
    Observable<BaseResult<List<AddressBookBean>>> getAllUser();

    //获取班组作业控制卡-作业项目
    @GET("work/project/listGET")
    Observable<BaseResult<List<ControlDepWorkBean>>> getControlDepWork1(@Query("type_id") String type_id, @Query("order") String order);

    //获取班组作业控制卡-安全控制措施
    @GET("work/safe/listGET")
    Observable<BaseResult<List<ControlDepWorkBean2>>> getControlDepWork2(@Query("type_id") String type_id, @Query("order") String order);

    //获取工序质量控制卡
    @GET("work/standard/listGET")
    Observable<BaseResult<List<ControlQualityBean>>> getControlQuality(@Query("type_id") String type, @Query("order") String order);

    //上传工序控制卡
    @Multipart
    @POST("work/quality/card/pda/updatePOST")
    Observable<BaseResult> upLoadControlQuality(@PartMap Map<String, RequestBody> params);

    //上传班组作业控制卡
    @Multipart
    @POST("work/control/card/pda/updatePOST")
    Observable<BaseResult> upLoadControlCard(@PartMap Map<String, RequestBody> params);

    //上传工器具配置
    @POST("work/tool/pda/updatePOST")
    Observable<BaseResult<ControlToolBeanList>> postControlTool(@Body List<ControlToolBean> beans);

    //巡视记录缺陷列表
    @GET("/task/defect/listGET")
    Observable<BaseResult<List<DefectFragmentBean>>> getDefectFragment(@Query("task_id") String task_id);

    //获取运行班小组长
    @GET("/task/group/user/oneGET")
    Observable<BaseResult<GroupBean>> getGroupName(@Query("year") String year, @Query("month") String month, @Query("day") String day, @Query("dep_id") String dep_id, @Query("user_id") String user_id);

    //获取检修班人员状态
    @GET("task/repair/user/listGET")
    Observable<BaseResult<List<JxbSignInfo>>> getSign(@Query("year") String year, @Query("month") String month, @Query("week") String day, @Query("user_id") String user_id);

    //巡视记录缺陷列表
    @GET("task/danger/listGET")
    Observable<BaseResult<List<DefectFragmentBean>>> getTroubleFragment(@Query("task_id") String task_id);

    //获取所有缺陷库
    //http://172.16.15.151:9096/task/defect/allGET?line_id=F3BA53A0C28E4EEC9D6DB821CDAAA6EC&month_id=F8118212B09A487D945EAACBD0B2A5AC&week_id=7F181C9BD52E4DBF881E6A6A763BDA6D&STATUS=1&AUDIT_STATUS=1
    @GET("task/defect/listGET")
    Observable<BaseResult<List<DefectFragmentBean>>> getAllDefact();

    //获取所有隐患库task/defect/saveDefect
    //http://172.16.15.151:9096/task/defect/allGET?line_id=F3BA53A0C28E4EEC9D6DB821CDAAA6EC&month_id=F8118212B09A487D945EAACBD0B2A5AC&week_id=7F181C9BD52E4DBF881E6A6A763BDA6D&STATUS=1&AUDIT_STATUS=1
    @GET("task/danger/listGET")
    Observable<BaseResult<List<DefectFragmentBean>>> getAllDanger();

    //获取检修年,月,周计划列表
    //http://172.16.15.60:9096/plan/repair/listGET?year=2019&month=5&week=2
    @GET("plan/repair/listGET")
    Observable<BaseResult<List<OverhaulYearBean>>> getOverhaulPlanList(@Query("year") String year, @Query("month") String month, @Query("week") String week, @Query("month_audit_status") String month_audit_status, @Query("id") String id, @Query("ele_user_id") String ele_user_id, @Query("check_user_id") String check_user_id, @Query("safe_user_id") String safe_user_id);

    @GET("plan/repair/listGET")
    Observable<BaseResult<List<OverhaulYearBean>>> getOverhaulPlanList(@Query("year") String year, @Query("month") String month, @Query("week") String week, @Query("month_audit_status") String month_audit_status, @Query("id") String id);

    //检修专责获取周计划列表
    @GET("plan/repair/listGET")
    Observable<BaseResult<List<OverhaulYearBean>>> getOverhaulZzWeekList(@Query("year") String year, @Query("month") String month, @Query("week") String week);

    //保电专责获取月计划列表
    @GET("plan/repair/listGET")
    Observable<BaseResult<List<OverhaulYearBean>>> getOverhaulList(@Query("year") String year, @Query("month") String month, @Query("ele_user_id") String ele_user_id, @Query("is_ele") String is_ele);

    //专责获取周任务列表
    //http://172.16.15.60:9096/task/repair/listGET
    @GET("task/repair/listGET")
    Observable<BaseResult<List<OverhaulZzTaskBean>>> getOverhaulZzTaskList(@Query("year") String year, @Query("month") String month, @Query("week") String week);


    //检修专责获取周计划列表
    @GET("plan/repair/oneGET")
    Observable<BaseResult<OverhaulYearBean>> getOverhaulZzWeekPlan(@Query("id") String id);

    //其他人员获取检修周任务列表
    //http://172.16.15.60:9096/task/repair/user/taskGET?user_id=3E88F1592A414E7D92D3F743FB821BE1&sign=1
    @GET("task/repair/user/taskGET")
    Observable<BaseResult<List<OverhaulMonthBean>>> getOverhaulTaskList(@Query("year") String year, @Query("month") String month, @Query("week") String week, @Query("user_id") String user_id, @Query("sign") String sign);

    //根据ID查询负责人
    @GET("plan/repair/user/listGET")
    Observable<BaseResult<List<OverhaulMonthBean>>> getFzrInfoByWeekId(@Query("repair_id") String repair_id, @Query("status") String status);

    //根据ID查询负责人
    @GET("plan/repair/user/listGET")
    Observable<BaseResult<List<OverhaulMonthBean>>> getMonthBean(@Query("repair_id") String repair_id);

    //根据ID查询负责人
    @GET("plan/repair/user/listGET")
    Observable<BaseResult<List<OverhaulMonthBean>>> getMonthBean(@Query("user_id") String user_id, @Query("repair_id") String repair_id);

    //其他人员查询任务内容根据ID
    //http://172.16.15.60:9096/task/repair/byIdGET?id=CC3AB35F6A4C49B7A578BA2FAB59686F
    @GET("task/repair/byIdGET")
    Observable<BaseResult<OverhaulMonthBean>> getTaskInfo(@Query("id") String id);

    //根据周检修id查询保电，验收方案
    @GET("/sys/file/listGET")
    Observable<BaseResult<List<OverhaulFileBean>>> getOverFile(@Query("data_id") String id);

    //专责查询任务内容根据ID
    @GET("task/repair/byIdGET")
    Observable<BaseResult<OverhaulZzTaskBean>> getZzTaskInfo(@Query("id") String id);

    //添加检修年计划
    @POST("plan/day/line/insertPOST")
    Observable<BaseResult<OverhaulYearBean>> addOverhaulYearPlan(@Body OverhaulYearBean bean);

    //    专责检修计划发布获取人员信息
    @GET("sys/user/allGET")
    Observable<BaseResult<List<OverhaulSendUserBean>>> getSendOverhaulUsers();

    //根据职位查询人员信息
    @GET("sys/role/listGET")
    Observable<BaseResult<List<OverhaulSendUserBean2>>> getSendOverhaulUsers2();

    //专责发布检修计划
    @POST("plan/repair/savePOST")
    Observable<BaseResult<List<OverhaulSendUserBean>>> sendOverhaulZzPlan(@Body OverhaulZZSendBean bean);

    //班长分发检修任务
    @POST("/task/repair/auditPOST")
    Observable<BaseResult<List<OverhaulSendUserBean>>> sendOverhaulMonitorPlan(@Body OverhaulMonitorSendBean bean);

    //负责人提交检修任务
    @POST("/task/repair/auditPOST")
    Observable<BaseResult<List<OverhaulSendUserBean>>> sendOverhaulFzrPlan(@Body OverhaulFzrSendBean bean);

    @POST("plan/repair/updatePOST")
    Observable<BaseResult<List<OverhaulSendUserBean>>> updataOverhaulMonitorPlan(@Body OverhaulZZSendBean bean);

    //专责更新检修计划
    @POST("plan/repair/updatePOST")
    Observable<BaseResult<List<OverhaulYearBean>>> updateJxMonthPlan(@Body OverhaulYearBean bean);


    //获取带电作业班人员
    @GET("sys/dep/listGET")
    Observable<BaseResult<List<ClassMemberBean>>> getAllClassMember(@Query("id") String dep_id);


    //工作票注意事项
    @GET("ticket/safe/type/listGET")
    Observable<BaseResult<List<TicketSafeContent>>> getTicketSafe(@Query("ticket_type") String type, @Query("type_id") String task_type, @Query("order") String order);

    //所有工作票注意事项列表
    @GET("ticket/safe/listGET")
    Observable<BaseResult<List<TicketSafeContent>>> safeList();

    //所有控制卡作业项目列表
    @GET("card/project/listGET")
    Observable<BaseResult<List<ControlDepWorkBean>>> controlCardProjectList();

    //所有控制卡作业危险点分析及安全措施列表
    @GET("card/safe/listGET")
    Observable<BaseResult<List<ControlDepWorkBean2>>> controlCardSafeList();

    //所有控制卡工序质量列表
    @GET("card/standrad/listGET")
    Observable<BaseResult<List<ControlQualityBean>>> controlCardStandradList();

    //上传文档
    @Multipart
    @POST("sys/file/upload")
    Observable<BaseResult> uploadWord(@PartMap Map<String, RequestBody> params);

    //上传文档
    @Multipart
    @POST("plan/repair/uploadPOST")
    Observable<BaseResult> uploadWords(@PartMap Map<String, RequestBody> params);

    @GET("admin/dict/listGET")
    Observable<BaseResult<List<ControlCardBean>>> controlCardType(@Query("p_code") String p_code);

    @GET("work/control/card/pda/oneGET")
    Observable<BaseResult<AllControlCarBean>> getControlCardContent(@Query("check_task_id") String check_task_id);

//    @GET("work/quality/oneGET")
//    Observable<BaseResult<List<ControlCardBean>>> getQualityCardContent(@Query("task_id") String task_id);
//
//    @GET("work/tool/oneGET")
//    Observable<BaseResult<List<ControlCardBean>>> getToolCardContent(@Query("task_id") String task_id);


    //专责获取检修待办列表
    @GET("plan/repair/user/listGET")
    Observable<BaseResult<List<OvaTodoBean>>> getOverhaulTodo(@Query("user_id") String user_id, @Query("status") String status);


    //检修计划详情
    @GET("plan/repair/byIdGET")
    Observable<BaseResult<PlanRepairBean>> getOverhaulDetail(@Query("id") String id);

//    //获取班组作业控制卡签名
//    @GET("work/control/download")
//    Observable<BaseResult<List<OvaTodoBean>>> getOverhaulDetail(@Query("id") String id);

//    //查询第一种工作票内容
//    @GET("task/tic/safe/listGET")
//    Observable<BaseResult<List<OvaTodoBean>>> safe(@Query("id") String id);

//    //发布待办
//    @POST("task/need/deal/savePOST")
//    Observable<BaseResult> releaseDeal(@Body OverhaulZZSendBean bean);

    //上传现场质量监督记录现场情况
    @Multipart
    @POST("task/supervise/pda/updatePOST")
    Observable<BaseResult> upLoadSituation(@PartMap Map<String, RequestBody> params);

    //查询现场质量监督记录现场情况
    @GET("task/supervise/oneGET")
    Observable<BaseResult<SituationBean>> searchSituation(@Query("task_id") String id);

    //现场勘察记录表-到岗到位检查获取模板
    @GET("task/position/listGET")
    Observable<BaseResult<List<GetToPostCheckBean>>> getToPostCheck();

    //到岗到位检查提交
    @POST("task/position/status/list/updatePOST")
    Observable<BaseResult> sendPostCheck(@Body RequestBody body);

    //现场勘察记录表-现场反违章检查
    @GET("task/illegal/listGET")
    Observable<BaseResult<List<FieldAntiInspectionBean>>> getFieldAntiInspection();

    //上传第一种工作票
    @Multipart
    @POST("ticket/first/savePOST")
    Observable<BaseResult> upLoadFirstTicket(@PartMap Map<String, RequestBody> params);

    //查询第一种工作票内容
    @GET("ticket/first/allGET")
    Observable<BaseResult<FirstTicketBean>> searchFirstTicket(@Query("task_id") String id);

    //上传第二种工作票
    @Multipart
    @POST("ticket/second/savePOST")
    Observable<BaseResult> upLoadSecondTicket(@PartMap Map<String, RequestBody> params);

    //查看第二种工作票
    @GET("ticket/second/allGET")
    Observable<BaseResult<SecondTicketBean>> searchSecondTicket(@Query("task_id") String id);

    //上传第三种工作票
    @Multipart
    @POST("ticket/electric/savePOST")
    Observable<BaseResult> upLoadThirdTicket(@PartMap Map<String, RequestBody> params);

    //查询第三种工作票内容
    @GET("ticket/electric/allGET")
    Observable<BaseResult<ThirdTicketBean>> searchThirdTicket(@Query("task_id") String id);

    //上传第四种工作票
    @Multipart
    @POST("ticket/fault/repair/savePOST")
    Observable<BaseResult> upLoadFourTicket(@PartMap Map<String, RequestBody> params);

    //查询第四种工作票内容
    @GET("ticket/fault/repair/allGET")
    Observable<BaseResult<FourTicketBean>> searchFourTicket(@Query("task_id") String id);


    //培训年计划列表查询
    @GET("plan/train/year/listGET")
    Observable<BaseResult<List<TrainingYearPlanBean>>> getTrainYearList(@Query("year") String year);


    //培训月计划列表查询
    @GET("plan/train/month/listGET")
    Observable<BaseResult<List<TrainingMonthPlanBean>>> getTrainMonthList(@Query("year") String year, @Query("month") String month);


    //培训临时计划列表查询
    @GET("plan/train/temp/listGET")
    Observable<BaseResult<List<TrainingTempPlanBean>>> getTrainTempList(@Query("year") String year, @Query("month") String month);


    //获取培新层级和类型
    @GET("admin/dict/listGET")
    Observable<BaseResult<List<TrainLevelBean>>> getTrainLevel(@Query("p_code") String p_code);

    //从年月计划添加培训任务
    @POST("task/train/pda/updatePOST")
    Observable<BaseResult<List<TrainLevelBean>>> sentToMember(@Body TrainingAddTaskBean bean);

    //获取培训任务列表
    @GET("task/train/listGET")
    Observable<BaseResult<List<TrainingTaskBean>>> getTrainTaskList(@Query("year") String year, @Query("month") String month);

    //添加临时培新计划并提交审核
    @POST("plan/train/temp/updatePOST")
    Observable<BaseResult<List<TrainingAddTempBean>>> addTempInfo(@Body TrainingAddTempBean bean);

    //获取主任列表
    @GET("sys/user/relate/findGET")
    Observable<BaseResult<List<TrainAuditorBean>>> getAuditorList();

    //运行班待办列表
    @POST("/task/need/deal/savePOST")
    Observable<BaseResult<TypeBean>> saveTodo(@Body SaveTodoReqbean reqbean);

    //获取pda扫描的杆塔信息
    @GET("eq/tower/oneGET")
    Observable<BaseResult<RfInfo>> getRfInfo(@Query("rf_id") String rfId);

    //查询红外测温
    @GET("task/temperature/oneGET")
    Observable<BaseResult<HwcwBean>> getHWCW(@Query("task_id") String rf_id);

    //查询接电电阻
    @GET("task/resistance/oneGET")
    Observable<BaseResult<JDDZbean>> getJDDZ(@Query("task_id") String rf_id);

    //查询巡视记录
    @GET("/task/patrol/img/listGET")
    Observable<BaseResult<List<PatrolRecordPicBean>>> getPartrolRecord(@Query("task_id") String task_id, @Query("order") String order);

    //查询绝缘子
    @GET("task/insulator/oneGET")
    Observable<BaseResult<JYZbean>> getJYZ(@Query("task_id") String rf_id);

    //查询红外测温
    @GET("task/tilt/oneGET")
    Observable<BaseResult<GTQXCLbean>> getGTQXCL(@Query("task_id") String rf_id);

    //查询红外测温
    @GET("task/temperature/byIdGE")
    Observable<BaseResult<HwcwBean>> getHWCWbyId(@Query("id") String id);

    //更新个人任务代办信息
    @POST("/task/personal/updatePOST ")
    Observable<BaseResult<TypeBean>> saveTodoAudit(@Body SaveTodoReqbean reqbean);

    //上传个人位置信息
    @POST("task/gps/pda/updatePOST")
    Observable<BaseResult<TypeBean>> setPosition(@Body PositionInfo positionInfo);

    //获取个人轨迹集合
    @GET("task/gps/listGET")
    Observable<BaseResult<List<PositionListBean>>> getPositonList(@Query("user_id") String user_id, @Query("loc_time like") String date);

    //获取检修班班长待办
    @GET("task/repair/listGET")
    Observable<BaseResult<List<OverhaulMonthBean>>> getBzAgents(@Query("task_status") String task_status);

    //更新检修班班长待办
    @POST("task/repair/pda/updatePOST")
    Observable<BaseResult<List<OverhaulMonthBean>>> updateBzAgents(@Query("task_status") String task_status, @Query("id") String id);

    //线路或双重设备名称
    @GET("eq/line/allGET")
    Observable<BaseResult<List<LineName>>> getDoubleLine(@Query("id") String id);

    //特殊属性列表
    @GET("task/danger/wares/waresAllGET")
    Observable<BaseResult<List<SpecialAttrList>>> specialAttrList();

    //起始终点杆塔
    @GET("eq/tower/allGET")
    Observable<BaseResult<List<TowerListBean>>> towerList(@Query("line_id") String line_id, @Query("order") String order);

    //添加特殊属性
    @POST("eq/tower/wares/pda/savePOST")
    Observable<BaseResult> addSpecial(@Body RequestBody info);

    //获取待办列表
    @GET("/task/remind/listGET")
    Observable<BaseResult<List<TodoListBean>>> getYXtodo(@Query("to_user_id") String user_id, @Query("done_status") String done_status, @Query("order") String order);
}
