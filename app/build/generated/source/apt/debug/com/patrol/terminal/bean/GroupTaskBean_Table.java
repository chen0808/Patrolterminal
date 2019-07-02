package com.patrol.terminal.bean;

import android.content.ContentValues;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sql.QueryBuilder;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.IProperty;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.sql.saveable.AutoIncrementModelSaver;
import com.raizlabs.android.dbflow.sql.saveable.ModelSaver;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseStatement;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.FlowCursor;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Number;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class GroupTaskBean_Table extends ModelAdapter<GroupTaskBean> {
  /**
   * Primary Key AutoIncrement */
  public static final Property<Integer> local_id = new Property<Integer>(GroupTaskBean.class, "local_id");

  public static final Property<String> id = new Property<String>(GroupTaskBean.class, "id");

  public static final Property<String> day_tower_id = new Property<String>(GroupTaskBean.class, "day_tower_id");

  public static final Property<String> group_id = new Property<String>(GroupTaskBean.class, "group_id");

  public static final Property<String> type_id = new Property<String>(GroupTaskBean.class, "type_id");

  public static final Property<String> type_sign = new Property<String>(GroupTaskBean.class, "type_sign");

  public static final Property<String> type_name = new Property<String>(GroupTaskBean.class, "type_name");

  public static final Property<String> plan_type = new Property<String>(GroupTaskBean.class, "plan_type");

  public static final Property<String> line_id = new Property<String>(GroupTaskBean.class, "line_id");

  public static final Property<String> line_name = new Property<String>(GroupTaskBean.class, "line_name");

  public static final Property<String> dep_id = new Property<String>(GroupTaskBean.class, "dep_id");

  public static final Property<String> dep_name = new Property<String>(GroupTaskBean.class, "dep_name");

  public static final Property<Integer> year = new Property<Integer>(GroupTaskBean.class, "year");

  public static final Property<Integer> month = new Property<Integer>(GroupTaskBean.class, "month");

  public static final Property<Integer> week = new Property<Integer>(GroupTaskBean.class, "week");

  public static final Property<Integer> day = new Property<Integer>(GroupTaskBean.class, "day");

  public static final Property<String> name = new Property<String>(GroupTaskBean.class, "name");

  public static final Property<String> tower_id = new Property<String>(GroupTaskBean.class, "tower_id");

  public static final Property<String> towers_id = new Property<String>(GroupTaskBean.class, "towers_id");

  public static final Property<String> tower_type = new Property<String>(GroupTaskBean.class, "tower_type");

  public static final Property<String> duty_user_id = new Property<String>(GroupTaskBean.class, "duty_user_id");

  public static final Property<String> duty_user_name = new Property<String>(GroupTaskBean.class, "duty_user_name");

  public static final Property<String> work_user_id = new Property<String>(GroupTaskBean.class, "work_user_id");

  public static final Property<String> work_user_name = new Property<String>(GroupTaskBean.class, "work_user_name");

  public static final Property<String> allot_status = new Property<String>(GroupTaskBean.class, "allot_status");

  public static final Property<String> done_status = new Property<String>(GroupTaskBean.class, "done_status");

  public static final Property<String> done_time = new Property<String>(GroupTaskBean.class, "done_time");

  public static final Property<String> is_rob = new Property<String>(GroupTaskBean.class, "is_rob");

  public static final Property<Integer> done_num = new Property<Integer>(GroupTaskBean.class, "done_num");

  public static final Property<Integer> all_num = new Property<Integer>(GroupTaskBean.class, "all_num");

  public static final Property<String> done_rate = new Property<String>(GroupTaskBean.class, "done_rate");

  public static final Property<String> from_user_id = new Property<String>(GroupTaskBean.class, "from_user_id");

  public static final Property<String> from_user_name = new Property<String>(GroupTaskBean.class, "from_user_name");

  public static final Property<String> start_id = new Property<String>(GroupTaskBean.class, "start_id");

  public static final Property<String> end_id = new Property<String>(GroupTaskBean.class, "end_id");

  public static final Property<String> audit_status = new Property<String>(GroupTaskBean.class, "audit_status");

  public static final Property<Boolean> check = new Property<Boolean>(GroupTaskBean.class, "check");

  public static final Property<String> user_id = new Property<String>(GroupTaskBean.class, "user_id");

  public static final Property<String> safe = new Property<String>(GroupTaskBean.class, "safe");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{local_id,id,day_tower_id,group_id,type_id,type_sign,type_name,plan_type,line_id,line_name,dep_id,dep_name,year,month,week,day,name,tower_id,towers_id,tower_type,duty_user_id,duty_user_name,work_user_id,work_user_name,allot_status,done_status,done_time,is_rob,done_num,all_num,done_rate,from_user_id,from_user_name,start_id,end_id,audit_status,check,user_id,safe};

  public GroupTaskBean_Table(DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
  }

  @Override
  public final Class<GroupTaskBean> getModelClass() {
    return GroupTaskBean.class;
  }

  @Override
  public final String getTableName() {
    return "`GroupTaskBean`";
  }

  @Override
  public final GroupTaskBean newInstance() {
    return new GroupTaskBean();
  }

  @Override
  public final Property getProperty(String columnName) {
    columnName = QueryBuilder.quoteIfNeeded(columnName);
    switch ((columnName)) {
      case "`local_id`":  {
        return local_id;
      }
      case "`id`":  {
        return id;
      }
      case "`day_tower_id`":  {
        return day_tower_id;
      }
      case "`group_id`":  {
        return group_id;
      }
      case "`type_id`":  {
        return type_id;
      }
      case "`type_sign`":  {
        return type_sign;
      }
      case "`type_name`":  {
        return type_name;
      }
      case "`plan_type`":  {
        return plan_type;
      }
      case "`line_id`":  {
        return line_id;
      }
      case "`line_name`":  {
        return line_name;
      }
      case "`dep_id`":  {
        return dep_id;
      }
      case "`dep_name`":  {
        return dep_name;
      }
      case "`year`":  {
        return year;
      }
      case "`month`":  {
        return month;
      }
      case "`week`":  {
        return week;
      }
      case "`day`":  {
        return day;
      }
      case "`name`":  {
        return name;
      }
      case "`tower_id`":  {
        return tower_id;
      }
      case "`towers_id`":  {
        return towers_id;
      }
      case "`tower_type`":  {
        return tower_type;
      }
      case "`duty_user_id`":  {
        return duty_user_id;
      }
      case "`duty_user_name`":  {
        return duty_user_name;
      }
      case "`work_user_id`":  {
        return work_user_id;
      }
      case "`work_user_name`":  {
        return work_user_name;
      }
      case "`allot_status`":  {
        return allot_status;
      }
      case "`done_status`":  {
        return done_status;
      }
      case "`done_time`":  {
        return done_time;
      }
      case "`is_rob`":  {
        return is_rob;
      }
      case "`done_num`":  {
        return done_num;
      }
      case "`all_num`":  {
        return all_num;
      }
      case "`done_rate`":  {
        return done_rate;
      }
      case "`from_user_id`":  {
        return from_user_id;
      }
      case "`from_user_name`":  {
        return from_user_name;
      }
      case "`start_id`":  {
        return start_id;
      }
      case "`end_id`":  {
        return end_id;
      }
      case "`audit_status`":  {
        return audit_status;
      }
      case "`check`":  {
        return check;
      }
      case "`user_id`":  {
        return user_id;
      }
      case "`safe`":  {
        return safe;
      }
      default: {
        throw new IllegalArgumentException("Invalid column name passed. Ensure you are calling the correct table's column");
      }
    }
  }

  @Override
  public final void updateAutoIncrement(GroupTaskBean model, Number id) {
    model.setLocal_id(id.intValue());
  }

  @Override
  public final Number getAutoIncrementingId(GroupTaskBean model) {
    return model.getLocal_id();
  }

  @Override
  public final String getAutoIncrementingColumnName() {
    return "local_id";
  }

  @Override
  public final ModelSaver<GroupTaskBean> createSingleModelSaver() {
    return new AutoIncrementModelSaver<>();
  }

  @Override
  public final IProperty[] getAllColumnProperties() {
    return ALL_COLUMN_PROPERTIES;
  }

  @Override
  public final void bindToInsertValues(ContentValues values, GroupTaskBean model) {
    values.put("`id`", model.getId());
    values.put("`day_tower_id`", model.getDay_tower_id());
    values.put("`group_id`", model.getGroup_id());
    values.put("`type_id`", model.getType_id());
    values.put("`type_sign`", model.getType_sign());
    values.put("`type_name`", model.getType_name());
    values.put("`plan_type`", model.getPlan_type());
    values.put("`line_id`", model.getLine_id());
    values.put("`line_name`", model.getLine_name());
    values.put("`dep_id`", model.getDep_id());
    values.put("`dep_name`", model.getDep_name());
    values.put("`year`", model.getYear());
    values.put("`month`", model.getMonth());
    values.put("`week`", model.getWeek());
    values.put("`day`", model.getDay());
    values.put("`name`", model.getName());
    values.put("`tower_id`", model.getTower_id());
    values.put("`towers_id`", model.getTowers_id());
    values.put("`tower_type`", model.getTower_type());
    values.put("`duty_user_id`", model.getDuty_user_id());
    values.put("`duty_user_name`", model.getDuty_user_name());
    values.put("`work_user_id`", model.getWork_user_id());
    values.put("`work_user_name`", model.getWork_user_name());
    values.put("`allot_status`", model.getAllot_status());
    values.put("`done_status`", model.getDone_status());
    values.put("`done_time`", model.getDone_time());
    values.put("`is_rob`", model.getIs_rob());
    values.put("`done_num`", model.getDone_num());
    values.put("`all_num`", model.getAll_num());
    values.put("`done_rate`", model.getDone_rate());
    values.put("`from_user_id`", model.getFrom_user_id());
    values.put("`from_user_name`", model.getFrom_user_name());
    values.put("`start_id`", model.getStart_id());
    values.put("`end_id`", model.getEnd_id());
    values.put("`audit_status`", model.getAudit_status());
    values.put("`check`", model.isCheck() ? 1 : 0);
    values.put("`user_id`", model.getUser_id());
    values.put("`safe`", model.getSafe());
  }

  @Override
  public final void bindToContentValues(ContentValues values, GroupTaskBean model) {
    values.put("`local_id`", model.getLocal_id());
    bindToInsertValues(values, model);
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, GroupTaskBean model,
      int start) {
    statement.bindStringOrNull(1 + start, model.getId());
    statement.bindStringOrNull(2 + start, model.getDay_tower_id());
    statement.bindStringOrNull(3 + start, model.getGroup_id());
    statement.bindStringOrNull(4 + start, model.getType_id());
    statement.bindStringOrNull(5 + start, model.getType_sign());
    statement.bindStringOrNull(6 + start, model.getType_name());
    statement.bindStringOrNull(7 + start, model.getPlan_type());
    statement.bindStringOrNull(8 + start, model.getLine_id());
    statement.bindStringOrNull(9 + start, model.getLine_name());
    statement.bindStringOrNull(10 + start, model.getDep_id());
    statement.bindStringOrNull(11 + start, model.getDep_name());
    statement.bindLong(12 + start, model.getYear());
    statement.bindLong(13 + start, model.getMonth());
    statement.bindLong(14 + start, model.getWeek());
    statement.bindLong(15 + start, model.getDay());
    statement.bindStringOrNull(16 + start, model.getName());
    statement.bindStringOrNull(17 + start, model.getTower_id());
    statement.bindStringOrNull(18 + start, model.getTowers_id());
    statement.bindStringOrNull(19 + start, model.getTower_type());
    statement.bindStringOrNull(20 + start, model.getDuty_user_id());
    statement.bindStringOrNull(21 + start, model.getDuty_user_name());
    statement.bindStringOrNull(22 + start, model.getWork_user_id());
    statement.bindStringOrNull(23 + start, model.getWork_user_name());
    statement.bindStringOrNull(24 + start, model.getAllot_status());
    statement.bindStringOrNull(25 + start, model.getDone_status());
    statement.bindStringOrNull(26 + start, model.getDone_time());
    statement.bindStringOrNull(27 + start, model.getIs_rob());
    statement.bindLong(28 + start, model.getDone_num());
    statement.bindLong(29 + start, model.getAll_num());
    statement.bindStringOrNull(30 + start, model.getDone_rate());
    statement.bindStringOrNull(31 + start, model.getFrom_user_id());
    statement.bindStringOrNull(32 + start, model.getFrom_user_name());
    statement.bindStringOrNull(33 + start, model.getStart_id());
    statement.bindStringOrNull(34 + start, model.getEnd_id());
    statement.bindStringOrNull(35 + start, model.getAudit_status());
    statement.bindLong(36 + start, model.isCheck() ? 1 : 0);
    statement.bindStringOrNull(37 + start, model.getUser_id());
    statement.bindStringOrNull(38 + start, model.getSafe());
  }

  @Override
  public final void bindToStatement(DatabaseStatement statement, GroupTaskBean model) {
    int start = 0;
    statement.bindLong(1 + start, model.getLocal_id());
    bindToInsertStatement(statement, model, 1);
  }

  @Override
  public final void bindToUpdateStatement(DatabaseStatement statement, GroupTaskBean model) {
    statement.bindLong(1, model.getLocal_id());
    statement.bindStringOrNull(2, model.getId());
    statement.bindStringOrNull(3, model.getDay_tower_id());
    statement.bindStringOrNull(4, model.getGroup_id());
    statement.bindStringOrNull(5, model.getType_id());
    statement.bindStringOrNull(6, model.getType_sign());
    statement.bindStringOrNull(7, model.getType_name());
    statement.bindStringOrNull(8, model.getPlan_type());
    statement.bindStringOrNull(9, model.getLine_id());
    statement.bindStringOrNull(10, model.getLine_name());
    statement.bindStringOrNull(11, model.getDep_id());
    statement.bindStringOrNull(12, model.getDep_name());
    statement.bindLong(13, model.getYear());
    statement.bindLong(14, model.getMonth());
    statement.bindLong(15, model.getWeek());
    statement.bindLong(16, model.getDay());
    statement.bindStringOrNull(17, model.getName());
    statement.bindStringOrNull(18, model.getTower_id());
    statement.bindStringOrNull(19, model.getTowers_id());
    statement.bindStringOrNull(20, model.getTower_type());
    statement.bindStringOrNull(21, model.getDuty_user_id());
    statement.bindStringOrNull(22, model.getDuty_user_name());
    statement.bindStringOrNull(23, model.getWork_user_id());
    statement.bindStringOrNull(24, model.getWork_user_name());
    statement.bindStringOrNull(25, model.getAllot_status());
    statement.bindStringOrNull(26, model.getDone_status());
    statement.bindStringOrNull(27, model.getDone_time());
    statement.bindStringOrNull(28, model.getIs_rob());
    statement.bindLong(29, model.getDone_num());
    statement.bindLong(30, model.getAll_num());
    statement.bindStringOrNull(31, model.getDone_rate());
    statement.bindStringOrNull(32, model.getFrom_user_id());
    statement.bindStringOrNull(33, model.getFrom_user_name());
    statement.bindStringOrNull(34, model.getStart_id());
    statement.bindStringOrNull(35, model.getEnd_id());
    statement.bindStringOrNull(36, model.getAudit_status());
    statement.bindLong(37, model.isCheck() ? 1 : 0);
    statement.bindStringOrNull(38, model.getUser_id());
    statement.bindStringOrNull(39, model.getSafe());
    statement.bindLong(40, model.getLocal_id());
  }

  @Override
  public final void bindToDeleteStatement(DatabaseStatement statement, GroupTaskBean model) {
    statement.bindLong(1, model.getLocal_id());
  }

  @Override
  public final String getInsertStatementQuery() {
    return "INSERT INTO `GroupTaskBean`(`id`,`day_tower_id`,`group_id`,`type_id`,`type_sign`,`type_name`,`plan_type`,`line_id`,`line_name`,`dep_id`,`dep_name`,`year`,`month`,`week`,`day`,`name`,`tower_id`,`towers_id`,`tower_type`,`duty_user_id`,`duty_user_name`,`work_user_id`,`work_user_name`,`allot_status`,`done_status`,`done_time`,`is_rob`,`done_num`,`all_num`,`done_rate`,`from_user_id`,`from_user_name`,`start_id`,`end_id`,`audit_status`,`check`,`user_id`,`safe`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `GroupTaskBean`(`local_id`,`id`,`day_tower_id`,`group_id`,`type_id`,`type_sign`,`type_name`,`plan_type`,`line_id`,`line_name`,`dep_id`,`dep_name`,`year`,`month`,`week`,`day`,`name`,`tower_id`,`towers_id`,`tower_type`,`duty_user_id`,`duty_user_name`,`work_user_id`,`work_user_name`,`allot_status`,`done_status`,`done_time`,`is_rob`,`done_num`,`all_num`,`done_rate`,`from_user_id`,`from_user_name`,`start_id`,`end_id`,`audit_status`,`check`,`user_id`,`safe`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  }

  @Override
  public final String getUpdateStatementQuery() {
    return "UPDATE `GroupTaskBean` SET `local_id`=?,`id`=?,`day_tower_id`=?,`group_id`=?,`type_id`=?,`type_sign`=?,`type_name`=?,`plan_type`=?,`line_id`=?,`line_name`=?,`dep_id`=?,`dep_name`=?,`year`=?,`month`=?,`week`=?,`day`=?,`name`=?,`tower_id`=?,`towers_id`=?,`tower_type`=?,`duty_user_id`=?,`duty_user_name`=?,`work_user_id`=?,`work_user_name`=?,`allot_status`=?,`done_status`=?,`done_time`=?,`is_rob`=?,`done_num`=?,`all_num`=?,`done_rate`=?,`from_user_id`=?,`from_user_name`=?,`start_id`=?,`end_id`=?,`audit_status`=?,`check`=?,`user_id`=?,`safe`=? WHERE `local_id`=?";
  }

  @Override
  public final String getDeleteStatementQuery() {
    return "DELETE FROM `GroupTaskBean` WHERE `local_id`=?";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `GroupTaskBean`(`local_id` INTEGER PRIMARY KEY AUTOINCREMENT, `id` TEXT, `day_tower_id` TEXT, `group_id` TEXT, `type_id` TEXT, `type_sign` TEXT, `type_name` TEXT, `plan_type` TEXT, `line_id` TEXT, `line_name` TEXT, `dep_id` TEXT, `dep_name` TEXT, `year` INTEGER, `month` INTEGER, `week` INTEGER, `day` INTEGER, `name` TEXT, `tower_id` TEXT, `towers_id` TEXT, `tower_type` TEXT, `duty_user_id` TEXT, `duty_user_name` TEXT, `work_user_id` TEXT, `work_user_name` TEXT, `allot_status` TEXT, `done_status` TEXT, `done_time` TEXT, `is_rob` TEXT, `done_num` INTEGER, `all_num` INTEGER, `done_rate` TEXT, `from_user_id` TEXT, `from_user_name` TEXT, `start_id` TEXT, `end_id` TEXT, `audit_status` TEXT, `check` INTEGER, `user_id` TEXT, `safe` TEXT)";
  }

  @Override
  public final void loadFromCursor(FlowCursor cursor, GroupTaskBean model) {
    model.setLocal_id(cursor.getIntOrDefault("local_id"));
    model.setId(cursor.getStringOrDefault("id"));
    model.setDay_tower_id(cursor.getStringOrDefault("day_tower_id"));
    model.setGroup_id(cursor.getStringOrDefault("group_id"));
    model.setType_id(cursor.getStringOrDefault("type_id"));
    model.setType_sign(cursor.getStringOrDefault("type_sign"));
    model.setType_name(cursor.getStringOrDefault("type_name"));
    model.setPlan_type(cursor.getStringOrDefault("plan_type"));
    model.setLine_id(cursor.getStringOrDefault("line_id"));
    model.setLine_name(cursor.getStringOrDefault("line_name"));
    model.setDep_id(cursor.getStringOrDefault("dep_id"));
    model.setDep_name(cursor.getStringOrDefault("dep_name"));
    model.setYear(cursor.getIntOrDefault("year"));
    model.setMonth(cursor.getIntOrDefault("month"));
    model.setWeek(cursor.getIntOrDefault("week"));
    model.setDay(cursor.getIntOrDefault("day"));
    model.setName(cursor.getStringOrDefault("name"));
    model.setTower_id(cursor.getStringOrDefault("tower_id"));
    model.setTowers_id(cursor.getStringOrDefault("towers_id"));
    model.setTower_type(cursor.getStringOrDefault("tower_type"));
    model.setDuty_user_id(cursor.getStringOrDefault("duty_user_id"));
    model.setDuty_user_name(cursor.getStringOrDefault("duty_user_name"));
    model.setWork_user_id(cursor.getStringOrDefault("work_user_id"));
    model.setWork_user_name(cursor.getStringOrDefault("work_user_name"));
    model.setAllot_status(cursor.getStringOrDefault("allot_status"));
    model.setDone_status(cursor.getStringOrDefault("done_status"));
    model.setDone_time(cursor.getStringOrDefault("done_time"));
    model.setIs_rob(cursor.getStringOrDefault("is_rob"));
    model.setDone_num(cursor.getIntOrDefault("done_num"));
    model.setAll_num(cursor.getIntOrDefault("all_num"));
    model.setDone_rate(cursor.getStringOrDefault("done_rate"));
    model.setFrom_user_id(cursor.getStringOrDefault("from_user_id"));
    model.setFrom_user_name(cursor.getStringOrDefault("from_user_name"));
    model.setStart_id(cursor.getStringOrDefault("start_id"));
    model.setEnd_id(cursor.getStringOrDefault("end_id"));
    model.setAudit_status(cursor.getStringOrDefault("audit_status"));
    int index_check = cursor.getColumnIndex("check");
    if (index_check != -1 && !cursor.isNull(index_check)) {
      model.setCheck(cursor.getBoolean(index_check));
    } else {
      model.setCheck(false);
    }
    model.setUser_id(cursor.getStringOrDefault("user_id"));
    model.setSafe(cursor.getStringOrDefault("safe"));
  }

  @Override
  public final boolean exists(GroupTaskBean model, DatabaseWrapper wrapper) {
    return model.getLocal_id() > 0
    && SQLite.selectCountOf()
    .from(GroupTaskBean.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final OperatorGroup getPrimaryConditionClause(GroupTaskBean model) {
    OperatorGroup clause = OperatorGroup.clause();
    clause.and(local_id.eq(model.getLocal_id()));
    return clause;
  }
}
