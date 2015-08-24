package com.kachidoki.me.moneytime10.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kachidoki.me.moneytime10.model.bean.ItemBean;
import com.kachidoki.me.moneytime10.util.MyDatebaseHelper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Frank on 15/8/17.
 */
public class ItemModel {


    private static ItemModel instance;
    private ItemModel(){}
    public static ItemModel getInstance(){
        if(instance==null){
            instance = new ItemModel();
        }
        return instance;
    }



    public void InsertItem(SQLiteDatabase db,int year,int month,int day,int weekDay,int weekOfYear,float startTime,float endTime,String describe,String color){

        ContentValues values = new ContentValues();
        values.put("describe",describe);
        values.put("day",day);
        values.put("month",month);
        values.put("year",year);
        values.put("color",color);
        values.put("startTime",startTime);
        values.put("endTime",endTime);
        values.put("weekDay",weekDay);
        values.put("weekOfYear",weekOfYear);

        Log.i("Test", "describe = " + describe);
        Log.i("Test", "day = " + day);
        Log.i("Test","month = "+month);
        Log.i("Test","year = "+year);
        Log.i("Test","starTime"+startTime);
        Log.i("Test","endTime"+endTime);
        Log.i("Test", "weekDay =" + weekDay);
        db.insert("Item", null, values);
    }

    public ArrayList<ItemBean> TableQueryItem(SQLiteDatabase db,int weekOfyear,int Year){
        ArrayList<ItemBean> itemBeans = new ArrayList<>();
        Cursor cursor = db.query("Item", null, "weekOfYear="+weekOfyear+" AND Year="+Year, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String describe = cursor.getString(cursor.getColumnIndex("describe"));
                float startTime = cursor.getFloat(cursor.getColumnIndex("startTime"));
                float endTime = cursor.getFloat(cursor.getColumnIndex("endTime"));
                String color = cursor.getString(cursor.getColumnIndex("color"));
                int weekDay = cursor.getInt(cursor.getColumnIndex("weekDay"));
                int year = cursor.getInt(cursor.getColumnIndex("year"));
                int month = cursor.getInt(cursor.getColumnIndex("month"));
                int day = cursor.getInt(cursor.getColumnIndex("day"));
                int weekOfYear = cursor.getInt(cursor.getColumnIndex("weekOfYear"));
                itemBeans.add(new ItemBean(year,month,day,weekDay,weekOfYear,startTime, endTime,describe,color));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemBeans;
    }

    public ArrayList<ItemBean> QueryTodayItem(SQLiteDatabase db,int Day,int Month,int Year){
        ArrayList<ItemBean> itemBeans = new ArrayList<>();
        Cursor cursor = db.query("Item", null, "day="+Day+" AND year="+Year+" AND month="+Month, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String describe = cursor.getString(cursor.getColumnIndex("describe"));
                float startTime = cursor.getFloat(cursor.getColumnIndex("startTime"));
                float endTime = cursor.getFloat(cursor.getColumnIndex("endTime"));
                String color = cursor.getString(cursor.getColumnIndex("color"));
                int weekDay = cursor.getInt(cursor.getColumnIndex("weekDay"));
                int year = cursor.getInt(cursor.getColumnIndex("year"));
                int month = cursor.getInt(cursor.getColumnIndex("month"));
                int day = cursor.getInt(cursor.getColumnIndex("day"));
                int weekOfYear = cursor.getInt(cursor.getColumnIndex("weekOfYear"));
                itemBeans.add(new ItemBean(year,month,day,weekDay,weekOfYear,startTime, endTime,describe,color));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemBeans;
    }
    public ArrayList<ItemBean> QueryTodayColorItem(SQLiteDatabase db,int Day,int Month,int Year,String Color){
        ArrayList<ItemBean> itemBeans = new ArrayList<>();
        Cursor cursor = db.query("Item", null, "day="+Day+" AND year="+Year+" AND month="+Month+" AND color=?",new String[]{Color}, null, null, "startTime");
        if (cursor.moveToFirst()) {
            do {
                String describe = cursor.getString(cursor.getColumnIndex("describe"));
                float startTime = cursor.getFloat(cursor.getColumnIndex("startTime"));
                float endTime = cursor.getFloat(cursor.getColumnIndex("endTime"));
                String color = cursor.getString(cursor.getColumnIndex("color"));
                int weekDay = cursor.getInt(cursor.getColumnIndex("weekDay"));
                int year = cursor.getInt(cursor.getColumnIndex("year"));
                int month = cursor.getInt(cursor.getColumnIndex("month"));
                int day = cursor.getInt(cursor.getColumnIndex("day"));
                int weekOfYear = cursor.getInt(cursor.getColumnIndex("weekOfYear"));
                itemBeans.add(new ItemBean(year,month,day,weekDay,weekOfYear,startTime, endTime,describe,color));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemBeans;
    }

    public ArrayList<ItemBean> MainQueryItem(SQLiteDatabase db,int weekOfyear,int Year){
        ArrayList<ItemBean> itemBeans = new ArrayList<>();
        Cursor cursor = db.query("Item", null, "weekOfYear="+weekOfyear+" AND Year="+Year, null, null, null, "year, month, day, startTime");
        if (cursor.moveToFirst()) {
            do {
                String describe = cursor.getString(cursor.getColumnIndex("describe"));
                float startTime = cursor.getFloat(cursor.getColumnIndex("startTime"));
                float endTime = cursor.getFloat(cursor.getColumnIndex("endTime"));
                String color = cursor.getString(cursor.getColumnIndex("color"));
                int weekDay = cursor.getInt(cursor.getColumnIndex("weekDay"));
                int year = cursor.getInt(cursor.getColumnIndex("year"));
                int month = cursor.getInt(cursor.getColumnIndex("month"));
                int day = cursor.getInt(cursor.getColumnIndex("day"));
                int weekOfYear = cursor.getInt(cursor.getColumnIndex("weekOfYear"));
                if (itemBeans.size()==0){
                    itemBeans.add(new ItemBean(year,month,day,weekDay,0,0, 0,"","black"));
                    itemBeans.add(new ItemBean(year,month,day,weekDay,weekOfYear,startTime, endTime,describe,color));
                }else {
                    if(day!=itemBeans.get(itemBeans.size()-1).getDay()||month!=itemBeans.get(itemBeans.size()-1).getMonth()){
                        itemBeans.add(new ItemBean(year,month,day,weekDay,0,0, 0,"","black"));
                        itemBeans.add(new ItemBean(year,month,day,weekDay,weekOfYear,startTime, endTime,describe,color));
                    }else {
                        itemBeans.add(new ItemBean(year,month,day,weekDay,weekOfYear,startTime, endTime,describe,color));
                    }
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemBeans;
    }

    public void delete (SQLiteDatabase db,int year,int month,int day,float startTime,float endTime){
        db.delete("Item","year="+year+" AND day="+day+" AND startTime="+startTime+" AND endTime="+endTime,null);
    }

}
