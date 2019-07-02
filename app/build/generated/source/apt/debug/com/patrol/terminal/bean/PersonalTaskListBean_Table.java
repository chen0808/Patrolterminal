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
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Number;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class PersonalTaskListBean_Table extends ModelAdapter<PersonalTaskListBean> {
  /**
   * Primary Key AutoIncrement */
  public static final Property<Integer> local_id = new Property<Integer>(PersonalTaskListBean.class, "local_id");

  public static final Property<String> id = new Property<String>(PersonalTaskListBean.class, "id");

  public static final Property<String> group_list_id = new Property<String>(PersonalTaskListBean.class, "group_list_id");

  public static final Property<String> name = new Property<String>(PersonalTaskListBean.class, "name");

  public static final Property<String> type_id = new Property<String>(PersonalTaskListBean.class, "type_id");

  public static final Property<String> type_sign = new Property<String>(PersonalTaskListBean.class, "type_sign");

  public static final Property<String> type_name = new Property<String>(PersonalTaskListBean.class, "type_name");

  public static final Property<String> plan_type = new Property<String>(PersonalTaskListBean.class, "plan_type");

  public static final Property<String> line_id = new Property<String>(PersonalTaskListBean.class, "line_id");

  public static final Property<String> line_name = new Property<String>(PersonalTaskListBean.class, "line_name");

  public static final Property<String> dep_id = new Property<String>(PersonalTaskListBean.class, "dep_id");

  public static final Property<String> dep_name = new Property<String>(PersonalTaskListBean.class, "dep_name");

  public static final Property<String> user_id = new Property<String>(PersonalTaskListBean.class, "user_id");

  public static final Property<String> user_name = new Property<String>(PersonalTaskListBean.class, "user_name");

  public static final Property<String> tower_id = new Property<String>(PersonalTaskListBean.class, "tower_id");

  public static final Property<String> tower_name = new Property<String>(PersonalTaskListBean.class, "tower_name");

  public static final Property<Integer> year = new Property<Integer>(PersonalTaskListBean.class, "year");

  public static final Property<Integer> month = new Property<Integer>(PersonalTaskListBean.class, "month");

  public static final Property<Integer> week = new Property<Integer>(PersonalTaskListBean.class, "week");

  public static final Property<Integer> day = new Property<Integer>(PersonalTaskListBean.class, "day");

  public static final Property<String> audit_status = new Property<String>(PersonalTaskListBean.class, "audit_status");

  public static final Property<String> done_status = new Property<String>(PersonalTaskListBean.class, "done_status");

  public static final Property<String> done_time = new Property<String>(PersonalTaskListBean.class, "done_time");

  public static final Property<String> sub_time = new Property<String>(PersonalTaskListBean.class, "sub_time");

  public static final Property<String> towers_id = new Property<String>(PersonalTaskListBean.class, "towers_id");

  public static final Property<String> check_report = new Property<String>(PersonalTaskListBean.class, "check_report");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{local_id,id,group_list_id,name,type_id,type_sign,type_name,plan_type,line_id,line_name,dep_id,dep_name,user_id,user_name,tower_id,tower_name,year,month,week,day,audit_status,done_status,done_time,sub_time,towers_id,check_report};

  public PersonalTaskListBean_Table(DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
  }

  @Override
  public final Class<PersonalTaskListBean> getModelClass() {
    return PersonalTaskListBean.class;
  }

  @Override
  public final String getTableName() {
    return "`PersonalTaskListBean`";
  }

  @Override
  public final PersonalTaskListBean newInstance() {
    return new PersonalTaskListBean();
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
      case "`group_list_id`":  {
        return group_list_id;
      }
      case "`name`":  {
        return name;
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
      case "`user_id`":  {
        return user_id;
      }
      case "`user_name`":  {
        return user_name;
      }
      case "`tower_id`":  {
        return tower_id;
      }
      case "`tower_name`":  {
        return tower_name;
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
      case "`audit_status`":  {
        return audit_status;
      }
      case "`done_status`":  {
        return done_status;
      }
      case "`done_time`":  {
        return done_time;
      }
      case "`sub_time`":  {
        return sub_time;
      }
      case "`towers_id`":  {
        return towers_id;
      }
      case "`check_report`":  {
        return check_report;
      }
      default: {
        throw new IllegalArgumentException("Invalid column name passed. Ensure you are calling the correct table's column");
      }
    }
  }

  @Override
  public final void updateAutoIncrement(PersonalTaskListBean model, Number id) {
    model.setLocal_id(id.intValue());
  }

  @Override
  public final Number getAutoIncrementingId(PersonalTaskListBean model) {
    return model.getLocal_id();
  }

  @Override
  public final String getAutoIncrementingColumnName() {
    return "local_id";
  }

  @Override
  public final ModelSaver<PersonalTaskListBean> createSingleModelSaver() {
    return new AutoIncrementModelSaver<>();
  }

  @Override
  public final IProperty[] getAllColumnProperties() {
    return ALL_COLUMN_PROPERTIES;
  }

  @Override
  public final void bindToInsertValues(ContentValues values, PersonalTaskListBean model) {
    values.put("`id`", model.getId());
    values.put("`group_list_id`", model.getGroup_list_id());
    values.put("`name`", model.getName());
    values.put("`type_id`", model.getType_id());
    values.put("`type_sign`", model.getType_sign());
    values.put("`type_name`", model.getType_name());
    values.put("`plan_type`", model.getPlan_type());
    values.put("`line_id`", model.getLine_id());
    values.put("`line_name`", model.getLine_name());
    values.put("`dep_id`", model.getDep_id());
    values.put("`dep_name`", model.getDep_name());
    values.put("`user_id`", model.getUser_id());
    values.put("`user_name`", model.getUser_name());
    values.put("`tower_id`", model.getTower_id());
    values.put("`tower_name`", model.getTower_name());
    values.put("`year`", model.getYear());
    values.put("`month`", model.getMonth());
    values.put("`week`", model.getWeek());
    values.put("`day`", model.getDay());
    values.put("`audit_status`", model.getAudit_status());
    values.put("`done_status`", model.getDone_status());
    values.put("`done_time`", model.getDone_time());
    values.put("`sub_time`", model.getSub_time());
    values.put("`towers_id`", model.getTowers_id());
    values.put("`check_report`", model.getCheck_report());
  }

  @Override
  public final void bindToContentValues(ContentValues values, PersonalTaskListBean model) {
    values.put("`local_id`", model.getLocal_id());
    bindToInsertValues(values, model);
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, PersonalTaskListBean model,
      int start) {
    statement.bindStringOrNull(1 + start, model.getId());
    statement.bindStringOrNull(2 + start, model.getGroup_list_id());
    statement.bindStringOrNull(3 + start, model.getName());
    statement.bindStringOrNull(4 + start, model.getType_id());
    statement.bindStringOrNull(5 + start, model.getType_sign());
    statement.bindStringOrNull(6 + start, model.getType_name());
    statement.bindStringOrNull(7 + start, model.getPlan_type());
    statement.bindStringOrNull(8 + start, model.getLine_id());
    statement.bindStringOrNull(9 + start, model.getLine_name());
    statement.bindStringOrNull(10 + start, model.getDep_id());
    statement.bindStringOrNull(11 + start, model.getDep_name());
    statement.bindStringOrNull(12 + start, model.getUser_id());
    statement.bindStringOrNull(13 + start, model.getUser_name());
    statement.bindStringOrNull(14 + start, model.getTower_id());
    statement.bindStringOrNull(15 + start, model.getTower_name());
    statement.bindLong(16 + start, model.getYear());
    statement.bindLong(17 + start, model.getMonth());
    statement.bindLong(18 + start, model.getWeek());
    statement.bindLong(19 + start, model.getDay());
    statement.bindStringOrNull(20 + start, model.getAudit_status());
    statement.bindStringOrNull(21 + start, model.getDone_status());
    statement.bindStringOrNull(22 + start, model.getDone_time());
    statement.bindStringOrNull(23 + start, model.getSub_time());
    statement.bindStringOrNull(24 + start, model.getTowers_id());
    statement.bindStringOrNull(25 + start, model.getCheck_report());
  }

  @Override
  public final void bindToStatement(DatabaseStatement statement, PersonalTaskListBean model) {
    int start = 0;
    statement.bindLong(1 + start, model.getLocal_id());
    bindToInsertStatement(statement, model, 1);
  }

  @Override
  public final void bindToUpdateStatement(DatabaseStatement statement, PersonalTaskListBean model) {
    statement.bindLong(1, model.getLocal_id());
    statement.bindStringOrNull(2, model.getId());
    statement.bindStringOrNull(3, model.getGroup_list_id());
    statement.bindStringOrNull(4, model.getName());
    statement.bindStringOrNull(5, model.getType_id());
    statement.bindStringOrNull(6, model.getType_sign());
    statement.bindStringOrNull(7, model.getType_name());
    statement.bindStringOrNull(8, model.getPlan_type());
    statement.bindStringOrNull(9, model.getLine_id());
    statement.bindStringOrNull(10, model.getLine_name());
    statement.bindStringOrNull(11, model.getDep_id());
    statement.bindStringOrNull(12, model.getDep_name());
    statement.bindStringOrNull(13, model.getUser_id());
    statement.bindStringOrNull(14, model.getUser_name());
    statement.bindStringOrNull(15, model.getTower_id());
    statement.bindStringOrNull(16, model.getTower_name());
    statement.bindLong(17, model.getYear());
    statement.bindLong(18, model.getMonth());
    statement.bindLong(19, model.getWeek());
    statement.bindLong(20, model.getDay());
    statement.bindStringOrNull(21, model.getAudit_status());
    statement.bindStringOrNull(22, model.getDone_status());
    statement.bindStringOrNull(23, model.getDone_time());
    statement.bindStringOrNull(24, model.getSub_time());
    statement.bindStringOrNull(25, model.getTowers_id());
    statement.bindStringOrNull(26, model.getCheck_report());
    statement.bindLong(27, model.getLocal_id());
  }

  @Override
  public final void bindToDeleteStatement(DatabaseStatement statement, PersonalTaskListBean model) {
    statement.bindLong(1, model.getLocal_id());
  }

  @Override
  public final String getInsertStatementQuery() {
    return "INSERT INTO `PersonalTaskListBean`(`id`,`group_list_id`,`name`,`type_id`,`type_sign`,`type_name`,`plan_type`,`line_id`,`line_name`,`dep_id`,`dep_name`,`user_id`,`user_name`,`tower_id`,`tower_name`,`year`,`month`,`week`,`day`,`audit_status`,`done_status`,`done_time`,`sub_time`,`towers_id`,`check_report`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `PersonalTaskListBean`(`local_id`,`id`,`group_list_id`,`name`,`type_id`,`type_sign`,`type_name`,`plan_type`,`line_id`,`line_name`,`dep_id`,`dep_name`,`user_id`,`user_name`,`tower_id`,`tower_name`,`year`,`month`,`week`,`day`,`audit_status`,`done_status`,`done_time`,`sub_time`,`towers_id`,`check_report`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  }

  @Override
  public final String getUpdateStatementQuery() {
    return "UPDATE `PersonalTaskListBean` SET `local_id`=?,`id`=?,`group_list_id`=?,`name`=?,`type_id`=?,`type_sign`=?,`type_name`=?,`plan_type`=?,`line_id`=?,`line_name`=?,`dep_id`=?,`dep_name`=?,`user_id`=?,`user_name`=?,`tower_id`=?,`tower_name`=?,`year`=?,`month`=?,`week`=?,`day`=?,`audit_status`=?,`done_status`=?,`done_time`=?,`sub_time`=?,`towers_id`=?,`check_report`=? WHERE `local_id`=?";
  }

  @Override
  public final String getDeleteStatementQuery() {
    return "DELETE FROM `PersonalTaskListBean` WHERE `local_id`=?";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `PersonalTaskListBean`(`local_id` INTEGER PRIMARY KEY AUTOINCREMENT, `id` TEXT, `group_list_id` TEXT, `name` TEXT, `type_id` TEXT, `type_sign` TEXT, `type_name` TEXT, `plan_type` TEXT, `line_id` TEXT, `line_name` TEXT, `dep_id` TEXT, `dep_name` TEXT, `user_id` TEXT, `user_name` TEXT, `tower_id` TEXT, `tower_name` TEXT, `year` INTEGER, `month` INTEGER, `week` INTEGER, `day` INTEGER, `audit_status` TEXT, `done_status` TEXT, `done_time` TEXT, `sub_time` TEXT, `towers_id` TEXT, `check_report` TEXT)";
  }

  @Override
  public final void loadFromCursor(FlowCursor cursor, PersonalTaskListBean model) {
    model.setLocal_id(cursor.getIntOrDefault("local_id"));
    model.setId(cursor.getStringOrDefault("id"));
    model.setGroup_list_id(cursor.getStringOrDefault("group_list_id"));
    model.setName(cursor.getStringOrDefault("name"));
    model.setType_id(cursor.getStringOrDefault("type_id"));
    model.setType_sign(cursor.getStringOrDefault("type_sign"));
    model.setType_name(cursor.getStringOrDefault("type_name"));
    model.setPlan_type(cursor.getStringOrDefault("plan_type"));
    model.setLine_id(cursor.getStringOrDefault("line_id"));
    model.setLine_name(cursor.getStringOrDefault("line_name"));
    model.setDep_id(cursor.getStringOrDefault("dep_id"));
    model.setDep_name(cursor.getStringOrDefault("dep_name"));
    model.setUser_id(cursor.getStringOrDefault("user_id"));
    model.setUser_name(cursor.getStringOrDefault("user_name"));
    model.setTower_id(cursor.getStringOrDefault("tower_id"));
    model.setTower_name(cursor.getStringOrDefault("tower_name"));
    model.setYear(cursor.getIntOrDefault("year"));
    model.setMonth(cursor.getIntOrDefault("month"));
    model.setWeek(cursor.getIntOrDefault("week"));
    model.setDay(cursor.getIntOrDefault("day"));
    model.setAudit_status(cursor.getStringOrDefault("audit_status"));
    model.setDone_status(cursor.getStringOrDefault("done_status"));
    model.setDone_time(cursor.getStringOrDefault("done_time"));
    model.setSub_time(cursor.getStringOrDefault("sub_time"));
    model.setTowers_id(cursor.getStringOrDefault("towers_id"));
    model.setCheck_report(cursor.getStringOrDefault("check_report"));
  }

  @Override
  public final boolean exists(PersonalTaskListBean model, DatabaseWrapper wrapper) {
    return model.getLocal_id() > 0
    && SQLite.selectCountOf()
    .from(PersonalTaskListBean.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final OperatorGroup getPrimaryConditionClause(PersonalTaskListBean model) {
    OperatorGroup clause = OperatorGroup.clause();
    clause.and(local_id.eq(model.getLocal_id()));
    return clause;
  }
}
