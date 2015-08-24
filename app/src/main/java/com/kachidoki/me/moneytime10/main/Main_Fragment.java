package com.kachidoki.me.moneytime10.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloat;
import com.jude.easyrecyclerview.EasyRecyclerView;
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
import java.util.List;
import java.util.Locale;

/**
 * Created by Frank on 15/8/17.
 */
public class Main_Fragment extends Fragment {
    private Toolbar toolbar;
    private ItemAdapter itemAdapter;
    private EasyRecyclerView easyRecyclerView;
    private ButtonFloat add;
    private int Year=0,Month=0,Day=0,WeekOfYear=0,WeekDay=0;
    final Calendar currentTime = Calendar.getInstance();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("life","main_fragment is on createView");
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        add = (ButtonFloat) view.findViewById(R.id.add);
        easyRecyclerView = (EasyRecyclerView) view.findViewById(R.id.MyRecyclerview);
        InitRecyclerView();

        SetChinaTime(currentTime);
        InitAdapther();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddActivity.class));
            }
        });
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("life", "main_fragment is on create");

    }

    @Override
    public void onResume() {
        Log.i("life", "main_fragment is on resume");
        super.onResume();
        SQLiteDatabase db = MyDatebaseHelper.getInstance(getActivity()).getReadableDatabase();
        ArrayList<ItemBean> itemBeans = ItemModel.getInstance().MainQueryItem(db, WeekOfYear, Year);
        Collections.reverse(itemBeans);
        itemAdapter.clear();
        itemAdapter.addAll(itemBeans);
    }
    public void InitRecyclerView(){
        itemAdapter = new ItemAdapter(getActivity());
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        easyRecyclerView.setAdapter(itemAdapter);
    }


    public void InitAdapther(){
        SQLiteDatabase db = MyDatebaseHelper.getInstance(getActivity()).getReadableDatabase();
        ArrayList<ItemBean> itemBeans = ItemModel.getInstance().MainQueryItem(db, WeekOfYear, Year);
        Collections.reverse(itemBeans);
        itemAdapter.addAll(itemBeans);
//        itemAdapter.add(new ItemBean(2015,8,17,5,35,8,9,"看书写代码","blue"));
//        itemAdapter.add(new ItemBean(2015, 8, 17, 5, 35, 9, 10, "看书写代码", "red"));
//        itemAdapter.add(new ItemBean(2015, 8, 17, 5, 35, 11, 12, "看书写代码", "orange"));
//        itemAdapter.add(new ItemBean(2015,8,17,5,35,13,14,"看书写代码",1));
//        itemAdapter.add(new ItemBean(2015,8,17,5,35,14,19,"看书写代码",1));
//        itemAdapter.add(new ItemBean(2015,8,17,5,35,19,20,"看书写代码",1));
//        itemAdapter.add(new ItemBean(2015,8,17,5,35,20,24,"看书写代码",1));


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
