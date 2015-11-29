package com.kachidoki.me.moneytime10.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.kachidoki.me.moneytime10.util.Listener.TimeListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Frank on 15/11/21.
 */
public class SMSManager {

    private static final SMSManager instance = new SMSManager();
    public static SMSManager getInstance(){
        return instance;
    }

    public interface SendCallback{
        void send();
        void error();
    }

    private SendCallback callback;

    public void setCallBack(SendCallback callBack){
        this.callback = callBack;
    }

    public ArrayList<TimeListener> timeList = new ArrayList<>();
    private boolean inited = false;
    private Timer timer;
    private int last = 0;


    EventHandler eh=new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {

            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            handler.sendMessage(msg);
        }

    };

    private void startTimer(){
        timer = new Timer();
        notifyEnable();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                last -= 1;
                notifyLastTime();
                if (last == 0) {
                    notifyEnable();
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    public boolean sendMessage(Context ctx,String number){
        if (!inited){
            SMSSDK.initSDK(ctx, "c7c819cbc4f7", "65f2d617453c10d6b3dc73af6a5fced3");
            SMSSDK.registerEventHandler(eh);
            inited = true;

        }
        if (last==0) {
            SMSSDK.getVerificationCode("86", number);
            last = 60;
            startTimer();
            return true;
        }else{
            return false;
        }
    }
    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event="+event);
            if (result == SMSSDK.RESULT_COMPLETE) {

                Log.i("Message", "回调完成");
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    callback.send();
                    Log.i("Message", "提交验证码成功");
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Log.i("Message", "获取验证码成功");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    Log.i("Message", "返回支持发送验证码的国家列表");
                }
            } else {
                Log.i("Message", "Error");
                callback.error();
                ((Throwable) data).printStackTrace();
            }


        }

    };

    public void destroy(){
        SMSSDK.unregisterEventHandler(eh);
    }


    public void notifyLastTime(){
        for (TimeListener listener:timeList){
            final TimeListener finalListener = listener;
            try {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        finalListener.onLastTimeNotify(last);
                    }
                });
            }catch (Exception e){
                unRegisterTimeListener(listener);
            }
        }
    }

    public void notifyEnable(){
        for (TimeListener listener:timeList){
            final TimeListener finalListener = listener;
            try {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        finalListener.onAbleNotify(last == 0);
                    }
                });
            }catch (Exception e){
                unRegisterTimeListener(listener);
            }
        }
    }


    public void registerTimeListener(TimeListener listener){
        timeList.add(listener);
        listener.onLastTimeNotify(last);
        listener.onAbleNotify(last==0);
    }

    public void unRegisterTimeListener(TimeListener listener){
        timeList.remove(listener);
    }
}
