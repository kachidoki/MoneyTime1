package com.kachidoki.me.moneytime10.main;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.kachidoki.me.moneytime10.R;
import com.kachidoki.me.moneytime10.model.ItemModel;
import com.kachidoki.me.moneytime10.model.bean.ItemBean;
import com.kachidoki.me.moneytime10.util.MyDatebaseHelper;
import com.kachidoki.me.moneytime10.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Frank on 15/8/20.
 */
public class DetilActivity extends AppCompatActivity {
    private int Year=0,Month=0,Day=0,WeekOfYear=0,WeekDay=0;
    final Calendar currentTime = Calendar.getInstance();
    private DetilAdapter DetilAdapter;
    private EasyRecyclerView easyRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil);
        SetChinaTime(currentTime);
        easyRecyclerView = (EasyRecyclerView) findViewById(R.id.MyRecyclerviewDetil);
        InitRecyclerView();
        InitAdapter();
    }

    public void InitRecyclerView(){
        DetilAdapter = new DetilAdapter(DetilActivity.this);
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(DetilActivity.this));
        easyRecyclerView.setAdapter(DetilAdapter);
    }

    public void InitAdapter(){
        Intent intent = getIntent();
        SQLiteDatabase db = MyDatebaseHelper.getInstance(DetilActivity.this).getReadableDatabase();
        ArrayList<ItemBean> itemBeans = ItemModel.getInstance().QueryTodayColorItem(db,Day,Month,Year,intent.getStringExtra("color"));
        Collections.reverse(itemBeans);
        DetilAdapter.addAll(itemBeans);
//        if(intent.getStringExtra("color")==null){
//            Log.i("Detil",null);
//        }else {
//            Log.i("Detil",intent.getStringExtra("color"));
//        }

    }

    public class DetilAdapter extends RecyclerArrayAdapter<ItemBean> {


        public DetilAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup viewGroup, int i) {
            return new DetilViewHolder(viewGroup);
        }

    }

    public class DetilViewHolder extends BaseViewHolder<ItemBean> {

        private TextView tv_startTime;
        private TextView tv_endTime;
        private TextView tv_describe;
        private ImageView color;
        @Override
        public void setData(ItemBean item) {
            tv_startTime.setText(Util.TransformTime(item.getStartTime()));
            tv_endTime.setText(Util.TransformTime(item.getEndTime()));
            tv_describe.setText(item.getDescribe());
            if(item.getColor().equals("red")) color.setImageResource(R.drawable.icon_round_red);
            if(item.getColor().equals("blue")) color.setImageResource(R.drawable.icon_round_blue);
            if(item.getColor().equals("orange")) color.setImageResource(R.drawable.icon_round_orange);
            if(item.getColor().equals("yellow")) color.setImageResource(R.drawable.icon_round_yellow);
            if(item.getColor().equals("green")) color.setImageResource(R.drawable.icon_round_green);
        }

        public DetilViewHolder(ViewGroup parent) {

            super(parent,R.layout.detil_view);
            tv_describe = (TextView) itemView.findViewById(R.id.describeDetil);
            tv_startTime = (TextView) itemView.findViewById(R.id.starttimeDetil);
            tv_endTime = (TextView) itemView.findViewById(R.id.endtimeDetil);
            color = (ImageView) itemView.findViewById(R.id.colorDetil);

        }
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
}
