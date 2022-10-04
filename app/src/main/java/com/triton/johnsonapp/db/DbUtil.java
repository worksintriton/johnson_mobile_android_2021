package com.triton.johnsonapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbUtil {


    static SQLiteDatabase db;
    Context context;
    ContentValues values;
    DbHelper dbHelper;

    public DbUtil(Context context) {
        this.context = context;
        values = new ContentValues();
    }

    public SQLiteDatabase open() {
        if (dbHelper == null) {
            dbHelper = new DbHelper(context);
        }
        db = dbHelper.getWritableDatabase();
        return db;
    }

    public void close() {
        if (dbHelper != null) {
            db.close();
        }
    }

    public static final String[] DATE_FIELD = {
            DbHelper.ID,
            DbHelper.CURRENT_DATE
    };

    public long addDate(String currentDateandTime) {

        values.clear();
        values.put(DbHelper.CURRENT_DATE,currentDateandTime);
        return db.insert(DbHelper.DATE_TABLE,null,values);
    }

    public Cursor getDate() {
        return db.query(DbHelper.DATE_TABLE,DATE_FIELD,null,null,null,null,null);
    }
}
