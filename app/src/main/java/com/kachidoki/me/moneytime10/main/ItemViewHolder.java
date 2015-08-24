package com.kachidoki.me.moneytime10.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.kachidoki.me.moneytime10.R;
import com.kachidoki.me.moneytime10.model.bean.ItemBean;
import com.kachidoki.me.moneytime10.util.PopupWindowsUtils;
import com.kachidoki.me.moneytime10.util.Position;
import com.kachidoki.me.moneytime10.util.Util;

/**
 * Created by Frank on 15/8/12.
 */
public class ItemViewHolder extends BaseViewHolder<ItemBean> {

    private TextView tv_startTime;
    private TextView tv_endTime;
    private TextView tv_describe;
    private ImageView color;
    private TextView tv_line;
    private LinearLayout colorView;
    private LinearLayout jinzhi;
    private Boolean isCancle=true;
    @Override
    public void setData(final ItemBean item) {

            tv_startTime.setText(Util.TransformTime(item.getStartTime()));
            tv_endTime.setText(Util.TransformTime(item.getEndTime()));
            tv_describe.setText(item.getDescribe());
            tv_line.setText("—");
            if(item.getColor().equals("red")) color.setImageResource(R.drawable.icon_round_red);
            if(item.getColor().equals("blue")) color.setImageResource(R.drawable.icon_round_blue);
            if(item.getColor().equals("orange")) color.setImageResource(R.drawable.icon_round_orange);
            if(item.getColor().equals("yellow")) color.setImageResource(R.drawable.icon_round_yellow);
            if(item.getColor().equals("green")) color.setImageResource(R.drawable.icon_round_green);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimatorSet animSet = new AnimatorSet();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(jinzhi, "translationX", 0F, -100F);
                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(jinzhi, "alpha", 0f, 1f);
                    animSet.setInterpolator(new LinearInterpolator());
                    animSet.playTogether(animator1, animator2);
                    animSet.setDuration(1000);

                    if (getLayoutPosition() == Position.getInstance().getPostion()) {
                        itemView.animate().cancel();
                        setData(item);
                    } else {
                        if (Position.getInstance().getPostion() == -1) {
                            animSet.start();
                            Position.getInstance().setPostion(getLayoutPosition());
                        } else {
                            animSet.cancel();
                            setData(item);
                            animSet.start();
                            Position.getInstance().setPostion(getLayoutPosition());
                        }

                    }
                    Log.i("viewHolder", Position.getInstance().getPostion() + "");
                }
            });




    }

    public ItemViewHolder(ViewGroup parent) {
        super(parent,R.layout.item_view);
        tv_describe = (TextView) itemView.findViewById(R.id.describe);
        tv_startTime = (TextView) itemView.findViewById(R.id.starttime);
        tv_endTime = (TextView) itemView.findViewById(R.id.endtime);
        color = (ImageView) itemView.findViewById(R.id.color);
        tv_line = (TextView) itemView.findViewById(R.id.line);
        colorView = (LinearLayout) itemView.findViewById(R.id.colorView);
        jinzhi = (LinearLayout) itemView.findViewById(R.id.jinzhi);
    }



}
