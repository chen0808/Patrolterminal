package com.patrol.terminal.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DefactContentDBHelper {

    private static final String TAG = "SMSDBHelper";
    private SQLiteDatabase db;
    private MyOpenhelper openHelper;

    public DefactContentDBHelper(Context context) {
        openHelper = new MyOpenhelper(context);
        db = openHelper.getReadableDatabase();
    }

    // query all
    public Cursor queryAll() {
        Cursor cursor = db.query(MyOpenhelper.TABLE.AUTO_DEFACT_TABLE, null, null, null, null, null,
                null);
        return cursor;
    }

    // query all
    public Cursor queryAllContent() {
        Cursor cursor = db.query(MyOpenhelper.TABLE.AUTO_DEFACT_TABLE, new String[]{MyOpenhelper.DefactTvColumns.CONTENT}, null, null, null, null,
                null);
        return cursor;
    }

    public Cursor queryFliter(String constraint) {
        String sql = "select * from " + MyOpenhelper.TABLE.AUTO_DEFACT_TABLE + " where " + MyOpenhelper.DefactTvColumns.CONTENT + " like ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + constraint+"%"});
        return cursor;
    }

    // query autoContent info througth quexianId
    public Cursor queryById(int id) {
        Cursor cursor = db.query(MyOpenhelper.TABLE.AUTO_DEFACT_TABLE, null, MyOpenhelper.DefactTvColumns._ID + " = " + id,
                null, null, null,
                null);
        return cursor;
    }


    // insert one autoContent
    public void insert(ContentValues values) {
        db.insert(MyOpenhelper.TABLE.AUTO_DEFACT_TABLE, null, values);
    }

//    /**
//     * 分页查询
//     *
//     * @param currentPage 当前页
//     * @param pageSize 每页显示的记录
//     * @return 当前页的记录
//     */
//    public Cursor queryPage(int currentPage, int pageSize) {
//        int firstResult = (currentPage - 1) * pageSize;
//        int maxResult = currentPage * pageSize;
//        String sql = "select * from smstable limit ?,?";
//        Cursor mCursor = db.rawQuery(
//                sql,
//                new String[] {
//                        String.valueOf(firstResult),
//                        String.valueOf(maxResult)
//                });
//
//        Log.d("linmeng", "query mCursor:" + mCursor);
//        Log.d("linmeng", "query mCursor.getCount():" + mCursor.getCount());
//        return mCursor;
//    }

    public long delelte() {
        long result = db.delete(MyOpenhelper.TABLE.AUTO_DEFACT_TABLE, null, null);
        return result;
    }

    // delete one message
    public void delelteSignal(String id) {
        db.delete(MyOpenhelper.TABLE.AUTO_DEFACT_TABLE, MyOpenhelper.DefactTvColumns._ID + " = " + id, null);
    }

    public void updateLastContent(String lastContentId, ContentValues values) {
        db.update(MyOpenhelper.TABLE.AUTO_DEFACT_TABLE, values, MyOpenhelper.DefactTvColumns._ID + " = " + lastContentId, null);
    }

    public void closeDB() {
        if (db != null) {
            db.close();
        }
    }
}
