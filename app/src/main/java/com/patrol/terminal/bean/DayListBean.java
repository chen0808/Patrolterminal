package com.patrol.terminal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class DayListBean implements Parcelable {

        /**
         * id : 396F0050851B40F0A0FBD22B941AF78A
         * month_id : 8BBC749760814E85958C5D2AE7BA3531
         * week_id : 7EB8FC1AAE3F42ACAF7BD01E136C922D
         * day_id : 880AEED966F4402B97801FDB8E0DA658
         * type_id : C7A9A60BDB1B4FE986014CA7DA24A467
         * type_val : null
         * type_name : 定期巡视
         * plan_type : 1
         * line_id : CB97B4C5B9FB41738EF3BE64EEC5C802
         * line_name : 南刘线
         * dep_id : BBE359C6F29042A1A5AF96F9C1B68ED8
         * dep_name : 西固运维班
         * year : 2019
         * month : 5
         * week : 3
         * day : 18
         * allot_status : 0
         * done_status : 0
         * audit_status : 0
         * done_time : null
         * towers : [{"id":"E0873C778A3843FF9FA1BAE561402C12","week_line_id":"8345072D28074D14860F8AAA4894E419","day_line_id":"396F0050851B40F0A0FBD22B941AF78A","line_id":"CB97B4C5B9FB41738EF3BE64EEC5C802","line_name":"南刘线","name":"#033-#055","tower_id":null,"towers_id":"E9628556482F4AF281DD4D6B9DC5E9EC","tower_type":"1","done_status":"0","done_time":null},{"id":"F100D98579A548988236020CD48A070D","week_line_id":"1FD073CEF71F4D7697586C1670F12123","day_line_id":"396F0050851B40F0A0FBD22B941AF78A","line_id":"CB97B4C5B9FB41738EF3BE64EEC5C802","line_name":"南刘线","name":"#021-#032","tower_id":null,"towers_id":"9C816A8D85184F0A82B076488D8808CD","tower_type":"1","done_status":"0","done_time":null}]
         */

        private String id;
        private String month_id;
        private String week_id;
        private String day_id;
        private String type_id;
        private String type_val;
        private String type_name;
        private String plan_type;
        private String line_id;
        private String line_name;
        private String dep_id;
        private String dep_name;
        private int year;
        private int month;
        private int week;
        private int day;
        private String allot_status;
        private String done_status;
        private String audit_status;
        private String done_time;
        private List<TowersBean> towers;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMonth_id() {
            return month_id;
        }

        public void setMonth_id(String month_id) {
            this.month_id = month_id;
        }

        public String getWeek_id() {
            return week_id;
        }

        public void setWeek_id(String week_id) {
            this.week_id = week_id;
        }

        public String getDay_id() {
            return day_id;
        }

        public void setDay_id(String day_id) {
            this.day_id = day_id;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getType_val() {
            return type_val;
        }

        public void setType_val(String type_val) {
            this.type_val = type_val;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getPlan_type() {
            return plan_type;
        }

        public void setPlan_type(String plan_type) {
            this.plan_type = plan_type;
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

        public String getDep_id() {
            return dep_id;
        }

        public void setDep_id(String dep_id) {
            this.dep_id = dep_id;
        }

        public String getDep_name() {
            return dep_name;
        }

        public void setDep_name(String dep_name) {
            this.dep_name = dep_name;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getAllot_status() {
            return allot_status;
        }

        public void setAllot_status(String allot_status) {
            this.allot_status = allot_status;
        }

        public String getDone_status() {
            return done_status;
        }

        public void setDone_status(String done_status) {
            this.done_status = done_status;
        }

        public String getAudit_status() {
            return audit_status;
        }

        public void setAudit_status(String audit_status) {
            this.audit_status = audit_status;
        }

        public String getDone_time() {
            return done_time;
        }

        public void setDone_time(String done_time) {
            this.done_time = done_time;
        }

        public List<TowersBean> getTowers() {
            return towers;
        }

        public void setTowers(List<TowersBean> towers) {
            this.towers = towers;
        }

        public static class TowersBean implements Parcelable {
            /**
             * id : E0873C778A3843FF9FA1BAE561402C12
             * week_line_id : 8345072D28074D14860F8AAA4894E419
             * day_line_id : 396F0050851B40F0A0FBD22B941AF78A
             * line_id : CB97B4C5B9FB41738EF3BE64EEC5C802
             * line_name : 南刘线
             * name : #033-#055
             * tower_id : null
             * towers_id : E9628556482F4AF281DD4D6B9DC5E9EC
             * tower_type : 1
             * done_status : 0
             * done_time : null
             */

            private String id;
            private String week_line_id;
            private String day_line_id;
            private String line_id;
            private String line_name;
            private String name;
            private String tower_id;
            private String towers_id;
            private String tower_type;
            private String done_status;
            private String done_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getWeek_line_id() {
                return week_line_id;
            }

            public void setWeek_line_id(String week_line_id) {
                this.week_line_id = week_line_id;
            }

            public String getDay_line_id() {
                return day_line_id;
            }

            public void setDay_line_id(String day_line_id) {
                this.day_line_id = day_line_id;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTower_id() {
                return tower_id;
            }

            public void setTower_id(String tower_id) {
                this.tower_id = tower_id;
            }

            public String getTowers_id() {
                return towers_id;
            }

            public void setTowers_id(String towers_id) {
                this.towers_id = towers_id;
            }

            public String getTower_type() {
                return tower_type;
            }

            public void setTower_type(String tower_type) {
                this.tower_type = tower_type;
            }

            public String getDone_status() {
                return done_status;
            }

            public void setDone_status(String done_status) {
                this.done_status = done_status;
            }

            public String getDone_time() {
                return done_time;
            }

            public void setDone_time(String done_time) {
                this.done_time = done_time;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.week_line_id);
                dest.writeString(this.day_line_id);
                dest.writeString(this.line_id);
                dest.writeString(this.line_name);
                dest.writeString(this.name);
                dest.writeString(this.tower_id);
                dest.writeString(this.towers_id);
                dest.writeString(this.tower_type);
                dest.writeString(this.done_status);
                dest.writeString(this.done_time);
            }

            public TowersBean() {
            }

            protected TowersBean(Parcel in) {
                this.id = in.readString();
                this.week_line_id = in.readString();
                this.day_line_id = in.readString();
                this.line_id = in.readString();
                this.line_name = in.readString();
                this.name = in.readString();
                this.tower_id = in.readString();
                this.towers_id = in.readString();
                this.tower_type = in.readString();
                this.done_status = in.readString();
                this.done_time = in.readString();
            }

            public static final Creator<TowersBean> CREATOR = new Creator<TowersBean>() {
                @Override
                public TowersBean createFromParcel(Parcel source) {
                    return new TowersBean(source);
                }

                @Override
                public TowersBean[] newArray(int size) {
                    return new TowersBean[size];
                }
            };
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.month_id);
        dest.writeString(this.week_id);
        dest.writeString(this.day_id);
        dest.writeString(this.type_id);
        dest.writeString(this.type_val);
        dest.writeString(this.type_name);
        dest.writeString(this.plan_type);
        dest.writeString(this.line_id);
        dest.writeString(this.line_name);
        dest.writeString(this.dep_id);
        dest.writeString(this.dep_name);
        dest.writeInt(this.year);
        dest.writeInt(this.month);
        dest.writeInt(this.week);
        dest.writeInt(this.day);
        dest.writeString(this.allot_status);
        dest.writeString(this.done_status);
        dest.writeString(this.audit_status);
        dest.writeString(this.done_time);
        dest.writeList(this.towers);
    }

    public DayListBean() {
    }

    protected DayListBean(Parcel in) {
        this.id = in.readString();
        this.month_id = in.readString();
        this.week_id = in.readString();
        this.day_id = in.readString();
        this.type_id = in.readString();
        this.type_val = in.readString();
        this.type_name = in.readString();
        this.plan_type = in.readString();
        this.line_id = in.readString();
        this.line_name = in.readString();
        this.dep_id = in.readString();
        this.dep_name = in.readString();
        this.year = in.readInt();
        this.month = in.readInt();
        this.week = in.readInt();
        this.day = in.readInt();
        this.allot_status = in.readString();
        this.done_status = in.readString();
        this.audit_status = in.readString();
        this.done_time = in.readString();
        this.towers = new ArrayList<TowersBean>();
        in.readList(this.towers, TowersBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<DayListBean> CREATOR = new Parcelable.Creator<DayListBean>() {
        @Override
        public DayListBean createFromParcel(Parcel source) {
            return new DayListBean(source);
        }

        @Override
        public DayListBean[] newArray(int size) {
            return new DayListBean[size];
        }
    };
}
