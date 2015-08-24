package com.kachidoki.me.moneytime10.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kachidoki.me.moneytime10.R;

/**
 * Created by Frank on 15/8/18.
 */
public class TimePicker extends FrameLayout {
    private TextView textView0,textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8,textView9,textView10,textView11,textView12,textView13,textView14,textView15,textView16,textView17,textView18,textView19,textView20,textView21,textView22,textView23,textView24,textView25,textView26,textView27,textView28,textView29,textView30,textView31,textView32,textView33,textView34;
    private TextView[] times ={textView0,textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8,textView9,textView10,textView11,textView12,textView13,textView14,textView15,textView16,textView17,textView18,textView19,textView20,textView21,textView22,textView23,textView24,textView25,textView26,textView27,textView28,textView29,textView30,textView31,textView32,textView33,textView34};
    private Boolean[] onClickes = {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    private void Init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.timepicker,this,true);
        times[0] = (TextView) view.findViewById(R.id.time7);
        times[1] = (TextView) view.findViewById(R.id.time75);
        times[2] = (TextView) view.findViewById(R.id.time8);
        times[3] = (TextView) view.findViewById(R.id.time85);
        times[4] = (TextView) view.findViewById(R.id.time9);
        times[5] = (TextView) view.findViewById(R.id.time95);
        times[6] = (TextView) view.findViewById(R.id.time10);
        times[7] = (TextView) view.findViewById(R.id.time105);
        times[8] = (TextView) view.findViewById(R.id.time11);
        times[9] = (TextView) view.findViewById(R.id.time115);
        times[10] = (TextView) view.findViewById(R.id.time12);
        times[11] = (TextView) view.findViewById(R.id.time125);
        times[12] = (TextView) view.findViewById(R.id.time13);
        times[13] = (TextView) view.findViewById(R.id.time135);
        times[14] = (TextView) view.findViewById(R.id.time14);
        times[15] = (TextView) view.findViewById(R.id.time145);
        times[16] = (TextView) view.findViewById(R.id.time15);
        times[17] = (TextView) view.findViewById(R.id.time155);
        times[18] = (TextView) view.findViewById(R.id.time16);
        times[19] = (TextView) view.findViewById(R.id.time165);
        times[20] = (TextView) view.findViewById(R.id.time17);
        times[21] = (TextView) view.findViewById(R.id.time175);
        times[22] = (TextView) view.findViewById(R.id.time18);
        times[23] = (TextView) view.findViewById(R.id.time185);
        times[24] = (TextView) view.findViewById(R.id.time19);
        times[25] = (TextView) view.findViewById(R.id.time195);
        times[26] = (TextView) view.findViewById(R.id.time20);
        times[27] = (TextView) view.findViewById(R.id.time205);
        times[28] = (TextView) view.findViewById(R.id.time21);
        times[29] = (TextView) view.findViewById(R.id.time215);
        times[30] = (TextView) view.findViewById(R.id.time22);
        times[31] = (TextView) view.findViewById(R.id.time225);
        times[32] = (TextView) view.findViewById(R.id.time23);
        times[33] = (TextView) view.findViewById(R.id.time235);
        times[34] = (TextView) view.findViewById(R.id.time24);
        setListener();
    }

    public void setListener(){
        times[0].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickes[0]==false){
                    if(onClickes[1]==true){
                        onClickes[0]=true;
                        times[0].setBackgroundResource(R.color.pink);
                    }else {
                        for (int i =1;i<=34;i++){
                            onClickes[i]=false;
                            times[i].setBackgroundResource(R.color.White);
                        }
                        onClickes[0]=true;
                        times[0].setBackgroundResource(R.color.pink);
                    }

                }else {
                    onClickes[0]=false;
                    times[0].setBackgroundResource(R.color.White);
                }
            }
        });

       for(int i=1;i<=33;i++){
           final int finalI = i;
           times[i].setOnClickListener(new OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (onClickes[finalI] == false) {
                       //若右没有点击
                       if(onClickes[finalI-1]==false&&onClickes[finalI+1]==false){
                           //其他全部取消
                           for (int x=0;x<finalI;x++){
                               onClickes[x]=false;
                               times[x].setBackgroundResource(R.color.White);
                           }
                           for (int x=34;x>=finalI;x--){
                               onClickes[x]=false;
                               times[x].setBackgroundResource(R.color.White);
                           }

                       }

                       onClickes[finalI] = true;
                       times[finalI].setBackgroundResource(R.color.pink);
                   } else {

                       //若左右都点击
                       if (onClickes[finalI-1]==true&&onClickes[finalI+1]==true){
                           //其他全部取消
                           for (int x=0;x<finalI;x++){
                               onClickes[x]=false;
                               times[x].setBackgroundResource(R.color.White);
                           }
                           for (int x=34;x>=finalI;x--){
                               onClickes[x]=false;
                               times[x].setBackgroundResource(R.color.White);
                           }
                           onClickes[finalI]=true;
                           times[finalI].setBackgroundResource(R.color.pink);
                       }else {
                           onClickes[finalI] = false;
                           times[finalI].setBackgroundResource(R.color.White);
                       }

                   }
               }
           });
       }

        times[34].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickes[34] == false) {
                    if(onClickes[33]==false){
                        for (int i=33;i<=0;i--){
                            onClickes[i]=false;
                            times[i].setBackgroundResource(R.color.White);
                        }
                        onClickes[34] = true;
                        times[34].setBackgroundResource(R.color.pink);
                    }else {
                        onClickes[34] = true;
                        times[34].setBackgroundResource(R.color.pink);
                    }



                } else {
                    onClickes[34] = false;
                    times[34].setBackgroundResource(R.color.White);
                }
            }
        });


    }



    public float startTime(){
       float start = 0;
        for (int i=0;i<=34;i++){
           if (onClickes[i]==true){
               start = i;
               break;
           }
       }

        return (start/2)+7;
    }

    public float endTime(){
        float end = 0;
        for (int i=34;i>=0;i--){
            if (onClickes[i]==true){
                end = i;
                break;
            }
        }
        return (end/2)+7;
    }

}
