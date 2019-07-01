package com.patrol.terminal.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.patrol.terminal.bean.DefactTvModel;
import com.patrol.terminal.utils.Utils;

import java.util.ArrayList;

public class DefactContentDBHelper {

    private static final String TAG = "DefactContentDBHelper";
    private SQLiteDatabase db;
    private MyOpenhelper openHelper;
    private Context mContext;

    public DefactContentDBHelper(Context context) {
        mContext = context;
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

    public void insertAll() {
        new ExcelDataLoader().execute("defact.xls");
    }


    //在异步方法中 调用
    private class ExcelDataLoader extends AsyncTask<String, Void, ArrayList<DefactTvModel>> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected ArrayList<DefactTvModel> doInBackground(String... params) {
            return Utils.getXlsData(mContext, params[0], 0);
        }

        @Override
        protected void onPostExecute(ArrayList<DefactTvModel> defactTvModels) {
            if(defactTvModels != null && defactTvModels.size() > 0){
                //存在数据
                ContentValues values;
                for (int i = 0; i < defactTvModels.size(); i++) {
                    values = new ContentValues();
                    values.put(MyOpenhelper.DefactTvColumns.LEVEL, defactTvModels.get(i).getLevel());
                    values.put(MyOpenhelper.DefactTvColumns.CONTENT, defactTvModels.get(i).getContent());
                    db.insert(MyOpenhelper.TABLE.AUTO_DEFACT_TABLE, null, values);
                }

            }else {
                //加载失败
            }
        }
    }


    /**
     * 获取 excel 表格中的数据,不能在主线程中调用
     *
     * xlsName excel 表格的名称
     *  index   第几张表格中的数据
     *//*
    private ArrayList<DefactTvModel> getXlsData(String xlsName, int index) {
        ArrayList<DefactTvModel> countryList = new ArrayList<DefactTvModel>();
        AssetManager assetManager = mContext.getAssets();

        try {
            Workbook workbook = Workbook.getWorkbook(assetManager.open(xlsName));
            Sheet sheet = workbook.getSheet(index);

            //int sheetNum = workbook.getNumberOfSheets();
            int sheetRows = sheet.getRows();
            //int sheetColumns = sheet.getColumns();

            for (int i = 0; i < sheetRows; i++) {
                DefactTvModel defactTvModel = new DefactTvModel();
                defactTvModel.setContent(sheet.getCell(5, i).getContents());
                defactTvModel.setLevel(sheet.getCell(6, i).getContents());
                countryList.add(defactTvModel);
            }

            workbook.close();

        } catch (Exception e) {
            Log.e(TAG, "read error=" + e, e);
        }

        return countryList;
    }*/

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
