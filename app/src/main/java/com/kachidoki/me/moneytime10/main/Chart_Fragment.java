package com.kachidoki.me.moneytime10.main;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
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
import com.kachidoki.me.moneytime10.R;
import com.kachidoki.me.moneytime10.model.ItemModel;
import com.kachidoki.me.moneytime10.model.bean.ItemBean;
import com.kachidoki.me.moneytime10.util.MyDatebaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Frank on 15/8/17.
 */
public class Chart_Fragment extends Fragment {
    final Calendar currentTime = Calendar.getInstance();
    private PieChart mChart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart,container,false);

        mChart = (PieChart) view.findViewById(R.id.pieChart);
        PieData mPieDate = getPieData();
        showChart(mChart,mPieDate);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void showChart(PieChart pieChart,PieData pieData){

        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(50f);//半径
        pieChart.setTransparentCircleRadius(58f);//半透明圆


        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(90);//初始旋转角度

        pieChart.setRotationEnabled(true);//可手动旋转

        pieChart.setUsePercentValues(true);//显示百分比
        pieChart.setDescription("");
        pieChart.setTransparentCircleColor(Color.WHITE);


        pieChart.setCenterText("今天的\n效率");
        pieChart.setCenterTextSize(20);
        pieChart.setCenterTextColor(R.color.Gray);

        pieChart.setData(pieData);

        Legend mLegend = pieChart.getLegend();//设置比例图
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        mLegend.setTextColor(R.color.Gray);
        pieChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);

    }


    private PieData getPieData(){
        ArrayList<String> xValues = new ArrayList<>();
        xValues.add("高效");
        xValues.add("不专心");
        xValues.add("休息");
        xValues.add("玩耍");
        xValues.add("拖延");

        ArrayList<Entry> yValues = new ArrayList<>();

        ArrayList<ItemBean> itemBeans = getToday(currentTime);

        float quarterly1 = 0;
        float quarterly2 = 0;
        float quarterly3 = 0;
        float quarterly4 = 0;
        float quarterly5 = 0;

        Log.i("Chart",itemBeans.size()+"     size");
        for (int i=0;i<itemBeans.size();i++){
            if(itemBeans.get(i).getColor().equals("yellow")) quarterly1 = quarterly1-itemBeans.get(i).getStartTime()+itemBeans.get(i).getEndTime();
            if(itemBeans.get(i).getColor().equals("orange")) quarterly2 = quarterly1-itemBeans.get(i).getStartTime()+itemBeans.get(i).getEndTime();
            if(itemBeans.get(i).getColor().equals("green")) quarterly3 = quarterly1-itemBeans.get(i).getStartTime()+itemBeans.get(i).getEndTime();
            if(itemBeans.get(i).getColor().equals("blue")) quarterly4 = quarterly1-itemBeans.get(i).getStartTime()+itemBeans.get(i).getEndTime();
            if(itemBeans.get(i).getColor().equals("red")) quarterly5 = quarterly1-itemBeans.get(i).getStartTime()+itemBeans.get(i).getEndTime();

//            Log.i("Chart","color = "+itemBeans.get(i).getColor());
        }


        Log.i("Chart","q1       "+quarterly1+"");
        Log.i("Chart","q2       "+quarterly2+"");
        Log.i("Chart","q3       "+quarterly3+"");
        Log.i("Chart","q4       "+quarterly4+"");
        Log.i("Chart","q5       "+quarterly5+"");

        yValues.add(new Entry(quarterly1,0));
        yValues.add(new Entry(quarterly2,1));
        yValues.add(new Entry(quarterly3,2));
        yValues.add(new Entry(quarterly4,3));
        yValues.add(new Entry(quarterly5, 4));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues,"");
        pieDataSet.setSliceSpace(3f);//饼图之间的距离
        pieDataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(255,193,7));
        colors.add(Color.rgb(255,87,34));
        colors.add(Color.rgb(0,150,136));
        colors.add(Color.rgb(33,150,243));
        colors.add(Color.rgb(255,68,68));

        pieDataSet.setColors(colors);

        pieDataSet.setSelectionShift(5f);

        PieData pieData = new PieData(xValues,pieDataSet);
        pieData.setValueTextSize(14f);
        pieData.setValueTextColor(Color.WHITE);

        return pieData;
    }

    public ArrayList<ItemBean> getToday(Calendar c){

        SQLiteDatabase db = MyDatebaseHelper.getInstance(getActivity()).getReadableDatabase();
        Calendar cal = new GregorianCalendar(Locale.CHINA);
        Date date = new GregorianCalendar(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).getTime();
        cal.setTime(date);
        return ItemModel.getInstance().QueryTodayItem(db,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR));
    }

}
