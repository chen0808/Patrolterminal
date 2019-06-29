package com.patrol.terminal.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AutoTvDBHelper {
//    private static final String TAG = "SMSDBHelper";
//    private SQLiteDatabase db;
//    private MyOpenhelper openHelper;
//
//    public AutoTvDBHelper(Context context) {
//        openHelper = new MyOpenhelper(context);
//        db = openHelper.getReadableDatabase();
//    }
//
//    // query all
//    public Cursor queryAll() {
//        Cursor cursor = db.query(MyOpenhelper.TABLE.AUTO_TEXTVIEW_TABLE, null, null, null, null, null,
//                "date desc");
//        return cursor;
//    }
//
//    // query autoContent info througth quexianId
//    public Cursor querySMSFromThreadId(int quexianId) {
//        Cursor cursor = db.query(MyOpenhelper.TABLE.AUTO_TEXTVIEW_TABLE, null, MyOpenhelper.AutoTvColumns.QUEXIAN_ID + " = " + quexianId,
//                null, null, null,
//                "date");
//        return cursor;
//    }
//
//
//    // insert one autoContent
//    public void insert(ContentValues values) {
//        db.insert(MyOpenhelper.TABLE.AUTO_TEXTVIEW_TABLE, null, values);
//    }
//
////    /**
////     * 分页查询
////     *
////     * @param currentPage 当前页
////     * @param pageSize 每页显示的记录
////     * @return 当前页的记录
////     */
////    public Cursor queryPage(int currentPage, int pageSize) {
////        int firstResult = (currentPage - 1) * pageSize;
////        int maxResult = currentPage * pageSize;
////        String sql = "select * from smstable limit ?,?";
////        Cursor mCursor = db.rawQuery(
////                sql,
////                new String[] {
////                        String.valueOf(firstResult),
////                        String.valueOf(maxResult)
////                });
////
////        Log.d("linmeng", "query mCursor:" + mCursor);
////        Log.d("linmeng", "query mCursor.getCount():" + mCursor.getCount());
////        return mCursor;
////    }
//
//    public long delelte() {
//        long result = db.delete(MyOpenhelper.TABLE.AUTO_TEXTVIEW_TABLE, null, null);
//        return result;
//    }
//
//    // delete one message
//    public void delelteSignal(String id) {
//        db.delete(MyOpenhelper.TABLE.AUTO_TEXTVIEW_TABLE, MyOpenhelper.AutoTvColumns._ID + " = " + id, null);
//    }
//
//    public void updateLastContent(String lastContentId, ContentValues values) {
//        db.update(MyOpenhelper.TABLE.AUTO_TEXTVIEW_TABLE, values, MyOpenhelper.AutoTvColumns._ID + " = " + lastContentId, null);
//    }
//
//    public void closeDB() {
//        if (db != null) {
//            db.close();
//        }
//    }
}
