package com.kachidoki.me.moneytime10.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.kachidoki.me.moneytime10.R;
import com.kachidoki.me.moneytime10.main.Main_Fragment;
import com.kachidoki.me.moneytime10.model.bean.ItemBean;
import com.kachidoki.me.moneytime10.util.Util;

import java.util.List;

import static com.kachidoki.me.moneytime10.R.color.White;

/**
 * Created by Frank on 15/8/15.
 */
public class Table extends FrameLayout {

    private final int width = (Util.getScreenWidth(getContext())-Util.dip2px(getContext(),20))/7;
    private final int height = Util.dip2px(getContext(), 60);
    private List<ItemBean> data;

    public Table(Context context) {
        this(context, null, 0);
    }

    public Table(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Table(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(data!=null)
        for(int i=0;i<data.size();i++){
            int x=(data.get(i).getWeekDay()-1);
            float y=(data.get(i).getStartTime()-7);

//            left=getChildAt(i).getMeasuredWidth()+(x*width);
//            top=getChildAt(i).getMeasuredHeight()+(y*height);
            left = (data.get(i).getWeekDay()-1)*width;
            right=left+width-2;
            top = (int) ((data.get(i).getStartTime()-7)*height);
            bottom= (int) (top+(data.get(i).getEndTime()-data.get(i).getStartTime())*height-2);
            getChildAt(i).layout(left, top, right, bottom);
//            Log.i("test", "left:" + left + "" + "right:" + right + "");

        }

        Log.i("Table", (data == null) + "");

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.Gray));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(1);
        paint.setAlpha(80);
        for(int i =1;i<=16;i++){
            canvas.drawLine(1,i*height,width*7,i*height,paint);
        }


    }

    public void init(){
        Log.i("Table","datasize:"+data.size());
        for(int i=0;i<data.size();i++){
            Log.i("test",data.get(i).getDescribe());
            TextView textView=new TextView(getContext());
            int a = (int) (data.get(i).getEndTime() - data.get(i).getStartTime()-2);
            textView.setLayoutParams(new ViewGroup.LayoutParams(148, a));
//            if(data.get(i).getColor().equals("red")) textView.setBackgroundResource(R.layout.red_background);
//            if(data.get(i).getColor().equals("green")) textView.setBackgroundResource(R.layout.green_background);
//            if(data.get(i).getColor().equals("blue")) textView.setBackgroundResource(R.layout.blue_background);
//            if(data.get(i).getColor().equals("orange")) textView.setBackgroundResource(R.layout.orange_background);
//            if(data.get(i).getColor().equals("yellow")) textView.setBackgroundResource(R.layout.yellow_background);
            if(data.get(i).getColor().equals("red")) textView.setBackgroundColor(getResources().getColor(R.color.Red));
            if(data.get(i).getColor().equals("green")) textView.setBackgroundColor(getResources().getColor(R.color.Green));
            if(data.get(i).getColor().equals("blue")) textView.setBackgroundColor(getResources().getColor(R.color.Blue));
            if(data.get(i).getColor().equals("orange")) textView.setBackgroundColor(getResources().getColor(R.color.Orange));
            if(data.get(i).getColor().equals("yellow")) textView.setBackgroundColor(getResources().getColor(R.color.Yellow));
            textView.getBackground().setAlpha(220);
            textView.setText(data.get(i).getDescribe());
            Log.i("Table", data.get(i).getDescribe() + "   ");
            textView.setTextColor(getResources().getColor(R.color.White));
            textView.setTextSize(12);
            Log.i("Table",textView.getGravity() + "gravity---------");
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));


//            textView.setBackgroundColor(getResources().getColor(White));
            addView(textView);
        }

    }

    public void setData(List<ItemBean> data){
        this.data= data;
        removeAllViews();
        init();
    }
}
