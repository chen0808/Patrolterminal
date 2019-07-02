package com.patrol.terminal.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.patrol.terminal.R;
import com.patrol.terminal.sqlite.DefactContentDBHelper;
import com.patrol.terminal.sqlite.MyOpenhelper;

public class AutoCursorAdapter extends CursorAdapter {

    private Context mContext;

    public AutoCursorAdapter(Context context, Cursor c) {
        super(context, c);
        this.mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.simple_defact_tv_content_item_layout, parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView contentTv =(TextView) view.findViewById(R.id.content_tv);
        int contentColumnIndex = cursor.getColumnIndex(MyOpenhelper.DefactTvColumns.CONTENT);
        String content = cursor.getString(contentColumnIndex);
        contentTv.setText(content);
    }

    @Override
    public String convertToString(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(MyOpenhelper.DefactTvColumns.CONTENT));
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        if (getFilterQueryProvider() != null) {
            return getFilterQueryProvider().runQuery(constraint);
        }

        DefactContentDBHelper defactContentDBHelper = new DefactContentDBHelper(mContext);
        Cursor cursor = defactContentDBHelper.queryFliter(String.valueOf(constraint));
        Log.w("linmeng", "cursor.getCount() filter:" + cursor.getCount());
        return cursor;
    }
}
