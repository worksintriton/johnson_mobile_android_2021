package com.triton.johnsonapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBMain extends SQLiteOpenHelper {
    public static final String DB="SpinnerData.Db";
    public static final int VERSION=1;
    String query;
    public DBMain(@Nullable Context context) {
        super(context,DB,null,VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

query= "create table STORE_DATA (id integer primary key, storeroomname text,storename text,sname text,roomname text,strname text,liftpit text,liftshaft text,lftshaft text,liftsht text,lftsht text,ls text,lfts text,lft text,shaft text,sft text,lft text,lst text,machineroom text,mroom text,machiner text,machinrm text,mchneroom text,mhinerm text,machinr text,mrm text,minroom text,minrm text,mainrm text,loadhook text,otherrequirements text,orequirements text,otherrmts text,ormts text,orements text,otherr text,otherequirmts text,othrrmts text,otrrmts text,orrmts text,othrrqmts text,othrrmtqs text,observation text,totalscore number)";
db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        query= "DROP TABLE IF EXISTS  STORE_DATA";
        db.execSQL(query);
        onCreate(db);

    }

}
