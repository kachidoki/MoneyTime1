package com.kachidoki.me.moneytime10.util;

/**
 * Created by Frank on 15/8/23.
 */
public class Position {

    private static Position mPosition;

    private static int postion=-1;

    private Position() {

    }

    public static Position getInstance() {
        if (mPosition == null) {
            synchronized (Position.class) {
                if (mPosition == null) {
                    mPosition = new Position();
                }
            }
        }
        return  mPosition;
    }

    public static int getPostion() {
        return postion;
    }

    public static void setPostion(int postion) {
        Position.postion = postion;
    }


}