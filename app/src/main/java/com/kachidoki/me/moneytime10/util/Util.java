package com.kachidoki.me.moneytime10.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class Util {
    private static final boolean isDebug = true;

    public static final String SETTINGCOLOR = "SETTINGCOLOR";

    private static final int DEFULARCOLORINDEX = 3;







    /**
     * 是否有网络
     * @param ctx
     * @return
     */
    public static boolean isNetWorkAvilable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * dp转px
     *
     */
    public static int dip2px(Context ctx,float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     *	px转dp
     */
    public static int px2dip(Context ctx,float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 取屏幕宽度
     * @param ctx
     * @return
     */
    public static int getScreenWidth(Context ctx){
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 取屏幕高度
     * @param ctx
     * @return
     */
    public static int getScreenHeight(Context ctx){
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 快捷的吐司显示
     * @param ctx
     * @param content
     */
    public static void Toast(Context ctx,String content){
        Toast.makeText(ctx, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 关闭输入法
     * @param act
     */
    public static void closeInputMethod(Activity act){
        View view = act.getCurrentFocus();
        if(view!=null){
            ((InputMethodManager)act.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 判断应用是否处于后台状态
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 复制文本到剪贴板
     * @param ctx
     * @param text
     */
    public static void copyToClipboard(Context ctx,String text){
        ClipboardManager cbm = (ClipboardManager) ctx.getSystemService(Activity.CLIPBOARD_SERVICE);
        cbm.setPrimaryClip(ClipData.newPlainText("redrock", text));
    }

    /**
     * 清除空行
     * @param content
     * @return
     */
    public static String clrearEmptyLine(String content){
        String result = content.replaceAll("(\r?\n(\\s*\r?\n)+)", "\r\n");
        if(result.startsWith("\r\n")){result = result.substring(2);}
        return result;
    }



    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }



    public static StateListDrawable newSelector(Context context, Drawable idNormal, Drawable idPressed, Drawable idFocused,
                                                Drawable idUnable) {
        StateListDrawable bg = new StateListDrawable();

        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, idPressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, idFocused);
        // View.ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled }, idNormal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_focused}, idFocused);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_window_focused}, idUnable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, idNormal);
        return bg;
    }
    public static void Log(String content){
        Log("test",content);
    }
    public static void Log(String tag,String content){
        if(isDebug){
            android.util.Log.i(tag, content);
        }
    }



    public static String TransformTime(float time){
        String tansTime;
        int a = 0;
        if(time - (int)time==0.5){
            a = (int)time;
            tansTime = a+":30";
            return tansTime;
        }else {
            tansTime = (int) time+":00";
            return tansTime;
        }
    }



}