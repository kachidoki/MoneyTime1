package com.kachidoki.me.moneytime10.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.kachidoki.me.moneytime10.R;
import com.kachidoki.me.moneytime10.model.ItemModel;
import com.kachidoki.me.moneytime10.model.bean.ItemBean;
import com.kachidoki.me.moneytime10.util.MyDatebaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Frank on 15/8/20.
 */
public class Mchart_Fragment extends Fragment implements OnChartValueSelectedListener {
    final Calendar currentTime = Calendar.getInstance();
    private PieChart mChart;
    private ArrayList<String> colorIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("life", "chart_fragment is on createView");
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        colorIntent = new ArrayList<>();
        mChart = (PieChart) view.findViewById(R.id.pieChart);
        showChart();

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("life", "chart_fragment is on create");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("life", "chart_fragment is on resume");
        showChart();
    }

    private void showChart(){
        //使用百分比
        mChart.setUsePercentValues(true);
        //描述
        mChart.setDescription("");


        //设置手拖动的速度
        mChart.setDragDecelerationFrictionCoef(0.98f);

        //设置是否是环状
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);//设置中心环状透明

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(50f);
        mChart.setTransparentCircleRadius(58f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(90);
        // enable rotation of the chart by touch 设置是否可旋转
        mChart.setRotationEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        mChart.setCenterText("今天的\n效率");
        mChart.setCenterTextSize(20);
        mChart.setCenterTextColor(R.color.Gray);
        setData();

//        mChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);
//
        Legend l = mChart.getLegend();//旁边的图例
//        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setTextColor(R.color.Gray);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(5f);


    }

    private void setData() {

        ArrayList<Entry> yValues = new ArrayList<>();

        ArrayList<ItemBean> itemBeans = getToday(currentTime);

        float quarterly1 = 0;
        float quarterly2 = 0;
        float quarterly3 = 0;
        float quarterly4 = 0;
        float quarterly5 = 0;
        float[] quarterlys ={quarterly1,quarterly2,quarterly3,quarterly4,quarterly5};
        Log.i("Chart", itemBeans.size() + "     size");
        for (int i=0;i<itemBeans.size();i++){
            if(itemBeans.get(i).getColor().equals("yellow")) quarterlys[0] = quarterlys[0]-itemBeans.get(i).getStartTime()+itemBeans.get(i).getEndTime();
            if(itemBeans.get(i).getColor().equals("orange")) quarterlys[1] = quarterlys[1]-itemBeans.get(i).getStartTime()+itemBeans.get(i).getEndTime();
            if(itemBeans.get(i).getColor().equals("green")) quarterlys[2] = quarterlys[2]-itemBeans.get(i).getStartTime()+itemBeans.get(i).getEndTime();
            if(itemBeans.get(i).getColor().equals("blue")) quarterlys[3] = quarterlys[3]-itemBeans.get(i).getStartTime()+itemBeans.get(i).getEndTime();
            if(itemBeans.get(i).getColor().equals("red")) quarterlys[4] = quarterlys[4]-itemBeans.get(i).getStartTime()+itemBeans.get(i).getEndTime();

//            Log.i("Chart","color = "+itemBeans.get(i).getColor());
        }


//        Log.i("Chart","q1       "+quarterly1+"");
//        Log.i("Chart","q2       "+quarterly2+"");
//        Log.i("Chart","q3       "+quarterly3+"");
//        Log.i("Chart","q4       "+quarterly4+"");
//        Log.i("Chart","q5       "+quarterly5+"");
        int x = -1;
        for(int i=0;i<=4;i++){
            if(quarterlys[i]!=0.0) {
                x++;
                yValues.add(new Entry(quarterlys[i], x));
                if (i==0)colorIntent.add("yellow");
                if (i==1)colorIntent.add("orange");
                if (i==2)colorIntent.add("green");
                if (i==3)colorIntent.add("blue");
                if (i==4)colorIntent.add("red");
            }
        }

//        if(quarterlys[0]!=0.0) yValues.add(new Entry(quarterlys[0],0));
//        if(quarterlys[1]!=0.0) yValues.add(new Entry(quarterlys[1],1));
//        if(quarterlys[2]!=0.0) yValues.add(new Entry(quarterlys[2],2));
//        if(quarterlys[3]!=0.0) yValues.add(new Entry(quarterlys[3],3));
//        if(quarterlys[4]!=0.0) yValues.add(new Entry(quarterlys[4],4));



        ArrayList<String> xVals = new ArrayList<>();
        if(quarterlys[0]!=0.0) xVals.add("高效");
        if(quarterlys[1]!=0.0) xVals.add("不专心");
        if(quarterlys[2]!=0.0) xVals.add("休息");
        if(quarterlys[3]!=0.0) xVals.add("玩耍");
        if(quarterlys[4]!=0.0) xVals.add("拖延");



        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);//设置饼图的距离
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();
        if(quarterlys[0]!=0.0) colors.add(Color.rgb(255, 193, 7));
        if(quarterlys[1]!=0.0) colors.add(Color.rgb(255, 87, 34));
        if(quarterlys[2]!=0.0) colors.add(Color.rgb(0, 150, 136));
        if(quarterlys[3]!=0.0) colors.add(Color.rgb(33, 150, 243));
        if(quarterlys[4]!=0.0) colors.add(Color.rgb(255, 68, 68));

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }



    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        if (e == null) return;
        if (e!=null){
            Intent intent = new Intent(getActivity(),DetilActivity.class);
            intent.putExtra("color",colorIntent.get(e.getXIndex()));
            startActivity(intent);
            Log.i("Chart",colorIntent.get(e.getXIndex()));
        }
    }

    @Override
    public void onNothingSelected() {

    }


    public ArrayList<ItemBean> getToday(Calendar c){

        SQLiteDatabase db = MyDatebaseHelper.getInstance(getActivity()).getReadableDatabase();
        Calendar cal = new GregorianCalendar(Locale.CHINA);
        Date date = new GregorianCalendar(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).getTime();
        cal.setTime(date);
        return ItemModel.getInstance().QueryTodayItem(db,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR));
    }

}
