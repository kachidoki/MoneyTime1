package com.kachidoki.me.moneytime10.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kachidoki.me.moneytime10.R;

/**
 * Created by Frank on 15/8/17.
 */
public class ColorPicker extends FrameLayout {

    private TextView Red;
    private TextView Yellow;
    private TextView Blue;
    private TextView Orange;
    private TextView Green;
    private Boolean redClick=false,yellowClick=false,blueClick=false,greenClick=false,orangeClick=false;

    public ColorPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public void Init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.colorpicker,this,true);
        Red = (TextView) view.findViewById(R.id.red);
        Yellow = (TextView) view.findViewById(R.id.yellow);
        Blue = (TextView) view.findViewById(R.id.blue);
        Green = (TextView) view.findViewById(R.id.green);
        Orange = (TextView) view.findViewById(R.id.orange);
        setListener();

    }

    public void setListener(){
        Red.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(redClick==false){
                    redClick = true;
                    Red.setText("拖延");
                    yellowClick=false;
                    Yellow.setText("");
                    blueClick=false;
                    Blue.setText("");
                    orangeClick=false;
                    Orange.setText("");
                    greenClick=false;
                    Green.setText("");
                }else {
                    redClick = false;
                    Red.setText("");
                }
            }
        });

        Yellow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yellowClick==false){
                    yellowClick = true;
                    Yellow.setText("高效");
                    redClick=false;
                    Red.setText("");
                    blueClick=false;
                    Blue.setText("");
                    orangeClick=false;
                    Orange.setText("");
                    greenClick=false;
                    Green.setText("");
                }else {
                    yellowClick = false;
                    Yellow.setText("");
                }
            }
        });

        Orange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orangeClick==false){
                    orangeClick = true;
                    Orange.setText("不专心");
                    redClick=false;
                    Red.setText("");
                    blueClick=false;
                    Blue.setText("");
                    yellowClick=false;
                    Yellow.setText("");
                    greenClick=false;
                    Green.setText("");
                }else {
                    orangeClick = false;
                    Orange.setText("");
                }
            }
        });

        Blue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blueClick == false) {
                    blueClick = true;
                    Blue.setText("玩耍");
                    redClick = false;
                    Red.setText("");
                    orangeClick = false;
                    Orange.setText("");
                    yellowClick = false;
                    Yellow.setText("");
                    greenClick = false;
                    Green.setText("");
                } else {
                    blueClick = false;
                    Blue.setText("");
                }
            }
        });

        Green.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (greenClick == false) {
                    greenClick = true;
                    Green.setText("休息");
                    redClick = false;
                    Red.setText("");
                    orangeClick = false;
                    Orange.setText("");
                    yellowClick = false;
                    Yellow.setText("");
                    blueClick = false;
                    Blue.setText("");
                } else {
                    greenClick = false;
                    Green.setText("");
                }
            }
        });
    }


    public String pickColor(){
        String color = null;

        if(redClick) color = "red";
        if(yellowClick) color = "yellow";
        if(orangeClick) color = "orange";
        if (blueClick) color = "blue";
        if (greenClick) color = "green";
        return color;
    }



}
