package com.kachidoki.me.moneytime10.main;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kachidoki.me.moneytime10.R;
import com.kachidoki.me.moneytime10.model.ItemModel;
import com.kachidoki.me.moneytime10.model.bean.ItemBean;
import com.kachidoki.me.moneytime10.util.MyDatebaseHelper;
import com.kachidoki.me.moneytime10.util.Util;
import com.kachidoki.me.moneytime10.widget.Table;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Frank on 15/8/17.
 */
public class Table_Fragment extends Fragment {
    private Table table;
    private List<ItemBean> data;
    private int Year=0,Month=0,Day=0,WeekOfYear=0,WeekDay=0;
    private Button left,right;
    private TextView datetime;
    final Calendar currentTime = Calendar.getInstance();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table,container,false);
        Log.i("life", "table_fragment is on createView");
        table = (Table) view.findViewById(R.id.table);
        left = (Button) view.findViewById(R.id.left);
        right = (Button) view.findViewById(R.id.right);
        datetime = (TextView) view.findViewById(R.id.datetime);
        datetime.setText("本周是第"+WeekOfYear+"周");
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeekOfYear = WeekOfYear - 1;
                Util.Toast(getActivity(),"这是第" + WeekOfYear + "周");
                SQLiteDatabase db = MyDatebaseHelper.getInstance(getActivity()).getReadableDatabase();
                data.clear();
                data = ItemModel.getInstance().TableQueryItem(db,WeekOfYear,Year);
                table.setData(data);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeekOfYear = WeekOfYear + 1;
                Util.Toast(getActivity(), "这是第" + WeekOfYear + "周");
                SQLiteDatabase db = MyDatebaseHelper.getInstance(getActivity()).getReadableDatabase();
                data.clear();
                data = ItemModel.getInstance().TableQueryItem(db, WeekOfYear, Year);
                table.setData(data);
            }
        });

        table.setData(data);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("life", "table_fragment is on create");
        SQLiteDatabase db = MyDatebaseHelper.getInstance(getActivity()).getReadableDatabase();
        SetChinaTime(currentTime);
        data = ItemModel.getInstance().TableQueryItem(db,WeekOfYear,Year);



//        data.add(new ItemBean(2015, 8, 17, 5, 35, 8, 9, "看书写代码", "red"));
//        data.add(new ItemBean(2015, 8, 17, 3, 35, 9, 10, "看书写代码", "yellow"));
//        data.add(new ItemBean(2015, 8, 17, 2, 35, 8, 9.5f, "看书写代码", "orange"));
//        data.add(new ItemBean(2015, 8, 17, 1, 35, 10, 12, "看书写代码", "blue"));
//        data.add(new ItemBean(2015, 8, 17, 4, 35, 8, 9, "看书写代码", "green"));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("life","table_fragment is on resume");
        SQLiteDatabase db = MyDatebaseHelper.getInstance(getActivity()).getReadableDatabase();
        data.clear();
        data = ItemModel.getInstance().TableQueryItem(db,WeekOfYear,Year);
        table.setData(data);

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
