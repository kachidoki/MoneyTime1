package com.kachidoki.me.moneytime10.main;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.kachidoki.me.moneytime10.R;
import com.kachidoki.me.moneytime10.model.ItemModel;
import com.kachidoki.me.moneytime10.model.bean.ItemBean;
import com.kachidoki.me.moneytime10.util.MyDatebaseHelper;
import com.kachidoki.me.moneytime10.util.Util;
import com.kachidoki.me.moneytime10.widget.ColorPicker;
import com.kachidoki.me.moneytime10.widget.TimePicker;
import com.kachidoki.me.moneytime10.widget.TimePicker2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * Created by Frank on 15/8/17.
 */
public class AddActivity extends AppCompatActivity {
    private int Year=0,Month=0,Day=0,WeekOfYear=0,WeekDay=0;
    private Toolbar toolbar;
    private TextView time;
    final Calendar currentTime = Calendar.getInstance();
    private ColorPicker colorPicker;
    private TimePicker2 timePicker;
    private EditText describe;
    private TextView calendTime;
    private ButtonRectangle add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        time = (TextView) findViewById(R.id.calenderTime);
        colorPicker = (ColorPicker) findViewById(R.id.colorPicker);
        timePicker = (TimePicker2) findViewById(R.id.timePicker);
        describe = (EditText) findViewById(R.id.doSomething);
        add = (ButtonRectangle) findViewById(R.id.addButton);
        calendTime = (TextView) findViewById(R.id.calenderTime);

        SetChinaTime(currentTime);
        calendTime.setText(Year + "年\n" + Month + "月" + Day + "日");


        Log.i("Test", Year + " " + Month + "  " + Day + "  WeekDay  " + WeekDay + "  WeekOfyear  " + WeekOfYear);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        SetChinaTime(year, monthOfYear, dayOfMonth);
                        time.setText(year + "年\n" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                        Log.i("Test", Year + " " + Month + "  " + Day + "  WeekDay  " + WeekDay + "  WeekOfyear  " + WeekOfYear);
                    }
                }, currentTime.get(Calendar.YEAR), currentTime.get(Calendar.MONTH), currentTime.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(colorPicker.pickColor()==null){
                    Util.Toast(AddActivity.this, "请选择颜色");
                }else {
                    if (timePicker.startTime()==timePicker.endTime()){
                        Util.Toast(AddActivity.this, "请选择时间段");
                    }else {
                        if(isDouble()==true){
                            Util.Toast(AddActivity.this,"这段时间已经被利用了哦");
                        }else {
                            if (describe.getText().toString().equals("")){
                                Util.Toast(AddActivity.this, "请添加内容");
                            }else {
                                SQLiteDatabase db = MyDatebaseHelper.getInstance(AddActivity.this).getWritableDatabase();
                                ItemModel.getInstance().InsertItem(db, Year, Month, Day, WeekDay, WeekOfYear, timePicker.startTime(), timePicker.endTime(), describe.getText().toString(), colorPicker.pickColor());
                                Util.Toast(AddActivity.this, "添加成功");
                                finish();
                            }
                        }

                    }
                }
            }
        });




    }

    public Boolean isDouble(){
        Boolean isdouble = false;
        SQLiteDatabase db = MyDatebaseHelper.getInstance(AddActivity.this).getReadableDatabase();
        ArrayList<ItemBean> itemBeans = ItemModel.getInstance().QueryTodayItem(db,Day,Month,Year);
        for (int i=0;i<itemBeans.size();i++){
            if((timePicker.startTime()<itemBeans.get(i).getEndTime()&&timePicker.startTime()>=itemBeans.get(i).getStartTime())||(timePicker.endTime()<itemBeans.get(i).getStartTime()&&timePicker.endTime()>=itemBeans.get(i).getEndTime())||(timePicker.startTime()<=itemBeans.get(i).getStartTime()&&timePicker.endTime()>=itemBeans.get(i).getEndTime())){
                isdouble=true;
                break;
            }
        }
        return isdouble;
    }



    public void SetChinaTime(Calendar c){
        Calendar cal = new GregorianCalendar(Locale.CHINA);
        Date date = new GregorianCalendar(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).getTime();
        cal.setTime(date);
        Year = cal.get(Calendar.YEAR);
        Month = cal.get(Calendar.MONTH)+1;
        Day = cal.get(Calendar.DAY_OF_MONTH);
        WeekDay = cal.get(Calendar.DAY_OF_WEEK);
        WeekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
    }

    public void SetChinaTime(int year,int month,int day){
        Calendar cal = new GregorianCalendar(Locale.CHINA);
        Date date = new GregorianCalendar(year,month,day).getTime();
        cal.setTime(date);
        Year = cal.get(Calendar.YEAR);
        Month = cal.get(Calendar.MONTH)+1;
        Day = cal.get(Calendar.DAY_OF_MONTH);
        WeekDay = cal.get(Calendar.DAY_OF_WEEK);
        WeekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
    }
}
