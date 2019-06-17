package com.patrol.terminal.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import com.patrol.terminal.R;

public class MyOpenhelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "patrol_db";
    private static final int DB_VERSION = 1;
    private static final String TAG = "MyOpenhelper";
    private static final String CREATE_AUTO_TV_TABLE =
            "CREATE TABLE " + TABLE.AUTO_TEXTVIEW_TABLE + "(" +
                    AutoTvColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    AutoTvColumns.QUEXIAN_ID + " INTEGER," +
                    AutoTvColumns.CONTENT + " TEXT" +
                    ")";
    private Context mContext;

    public MyOpenhelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "[DB_VERSION:] " + DB_VERSION + "[DB_NAME]: " + DB_NAME);

        try {
            db.beginTransaction();
            db.execSQL(CREATE_AUTO_TV_TABLE);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        Log.d(TAG, "table has been created");

        initDataBase(db);
    }

    private void initDataBase(SQLiteDatabase db) {
        String[] autoTvContents = mContext.getResources().getStringArray(R.array.auto_textview_contents);

        ContentValues values;
        for (int i = 0; i < autoTvContents.length; i++) {
            values = new ContentValues();
            values.put(AutoTvColumns.QUEXIAN_ID, i);
            values.put(AutoTvColumns.CONTENT, autoTvContents[i]);
            db.insert(TABLE.AUTO_TEXTVIEW_TABLE, null, values);
            Log.d("linmeng", "values" + i + ":" + values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // the table name
    public interface TABLE {
        public static final String AUTO_TEXTVIEW_TABLE = "auto_textview_table";
    }

    // sms table columns
    public interface AutoTvColumns extends BaseColumns {
        public static final String QUEXIAN_ID = "quexian_id"; // ID，每个内容唯一
        public static final String CONTENT = "content"; // 内容
    }
}
