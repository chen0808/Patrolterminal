package com.patrol.terminal.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import com.patrol.terminal.R;
import com.patrol.terminal.bean.DefactTvModel;

import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

public class MyOpenhelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "patrol_db";
    private static final int DB_VERSION = 1;
    private static final String TAG = "MyOpenhelper";
    private SQLiteDatabase mDb;

//    private static final String CREATE_AUTO_TV_TABLE =
//            "CREATE TABLE " + TABLE.AUTO_TEXTVIEW_TABLE + "(" +
//                    AutoTvColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    AutoTvColumns.QUEXIAN_ID + " INTEGER," +
//                    AutoTvColumns.CONTENT + " TEXT" +
//                    ")";


    private static final String CREATE_DEFACT_TV_TABLE =
            "CREATE TABLE " + TABLE.AUTO_DEFACT_TABLE + "(" +
                    DefactTvColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DefactTvColumns.CONTENT + " TEXT," +
                    DefactTvColumns.LEVEL + " TEXT" +
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
            db.execSQL(CREATE_DEFACT_TV_TABLE);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        Log.d(TAG, "table has been created");

        initDataBase(db);
    }

//    private void initDataBase(SQLiteDatabase db) {
//        String[] autoTvContents = mContext.getResources().getStringArray(R.array.auto_textview_contents);
//
//        ContentValues values;
//        for (int i = 0; i < autoTvContents.length; i++) {
//            values = new ContentValues();
//            values.put(AutoTvColumns.QUEXIAN_ID, i);
//            values.put(AutoTvColumns.CONTENT, autoTvContents[i]);
//            db.insert(TABLE.AUTO_TEXTVIEW_TABLE, null, values);
//            Log.d("linmeng", "values" + i + ":" + values);
//        }
//    }

    private void initDataBase(SQLiteDatabase db) {
        //从Excel取数据
        mDb = db;
        new ExcelDataLoader().execute("defact.xls");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // the table name
    public interface TABLE {
        //public static final String AUTO_TEXTVIEW_TABLE = "auto_textview_table";
        public static final String AUTO_DEFACT_TABLE = "auto_defact_table";
    }

//    // sms table columns
//    public interface AutoTvColumns extends BaseColumns {
//        public static final String QUEXIAN_ID = "quexian_id"; // ID，每个内容唯一
//        public static final String CONTENT = "content"; // 内容
//    }

    // sms table columns
    public interface DefactTvColumns extends BaseColumns {
        public static final String CONTENT = "content"; // 内容
        public static final String LEVEL = "level";
    }


    //在异步方法中 调用
    private class ExcelDataLoader extends AsyncTask<String, Void, ArrayList<DefactTvModel>> {

        @Override
        protected void onPreExecute() {
//            progressDialog.setMessage("加载中,请稍后......");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
            Log.w("linmeng", "ExcelDataLoader onPreExecute:");
        }

        @Override
        protected ArrayList<DefactTvModel> doInBackground(String... params) {
            return getXlsData(params[0], 0);
        }

        @Override
        protected void onPostExecute(ArrayList<DefactTvModel> defactTvModels) {
//            if (progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }

            if(defactTvModels != null && defactTvModels.size() > 0){
                //存在数据
                ContentValues values;
                for (int i = 0; i < defactTvModels.size(); i++) {
                    values = new ContentValues();
                    values.put(DefactTvColumns.LEVEL, defactTvModels.get(i).getLevel());
                    values.put(DefactTvColumns.CONTENT, defactTvModels.get(i).getContent());
                    mDb.insert(TABLE.AUTO_DEFACT_TABLE, null, values);
                    Log.d("linmeng", "values" + i + ":" + values);
                }

            }else {
                //加载失败


            }

        }
    }


    /**
     * 获取 excel 表格中的数据,不能在主线程中调用
     *
     * @param xlsName excel 表格的名称
     * @param index   第几张表格中的数据
     */
    private ArrayList<DefactTvModel> getXlsData(String xlsName, int index) {
        ArrayList<DefactTvModel> countryList = new ArrayList<DefactTvModel>();
        AssetManager assetManager = mContext.getAssets();

        try {
            Workbook workbook = Workbook.getWorkbook(assetManager.open(xlsName));
            Sheet sheet = workbook.getSheet(index);

            int sheetNum = workbook.getNumberOfSheets();
            int sheetRows = sheet.getRows();
            int sheetColumns = sheet.getColumns();

            Log.d(TAG, "the num of sheets is " + sheetNum);
            Log.d(TAG, "the name of sheet is  " + sheet.getName());
            Log.d(TAG, "total rows is 行=" + sheetRows);
            Log.d(TAG, "total cols is 列=" + sheetColumns);

            for (int i = 0; i < sheetRows; i++) {
                DefactTvModel defactTvModel = new DefactTvModel();
                Log.d(TAG, "sheet.getCell(5,0).getContents()=" + sheet.getCell(5,0).getContents());
                defactTvModel.setContent(sheet.getCell(5, i).getContents());
                defactTvModel.setLevel(sheet.getCell(6, i).getContents());
                countryList.add(defactTvModel);
            }

            workbook.close();

        } catch (Exception e) {
            Log.e(TAG, "read error=" + e, e);
        }

        return countryList;
    }


}
