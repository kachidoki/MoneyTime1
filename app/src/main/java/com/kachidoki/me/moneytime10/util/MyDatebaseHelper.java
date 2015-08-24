package com.kachidoki.me.moneytime10.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Frank on 15/8/12.
 */
public class MyDatebaseHelper extends SQLiteOpenHelper {

    private static MyDatebaseHelper instance;

    public static MyDatebaseHelper getInstance(Context context){
        if(instance==null){
            instance = new MyDatebaseHelper(context,"Item.db",null,1);
        }
        return instance;
    }


    public static final String CREATE_ITEM = "create table Item("
            +"id integer primary key autoincrement,"
            +"startTime float,"
            +"endTime float,"
            +"year integer,"
            +"day integer,"
            +"month integer,"
            +"weekDay integer,"
            +"weekOfYear integer,"
            +"describe text,"
            +"color text)";


    private Context mcontext;

    public MyDatebaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ITEM);
        Toast.makeText(mcontext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
