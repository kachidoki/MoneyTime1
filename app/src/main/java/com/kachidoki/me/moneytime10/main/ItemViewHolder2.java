package com.kachidoki.me.moneytime10.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.kachidoki.me.moneytime10.R;
import com.kachidoki.me.moneytime10.model.bean.ItemBean;
import com.kachidoki.me.moneytime10.util.Util;

/**
 * Created by Frank on 15/8/12.
 */
public class ItemViewHolder2 extends BaseViewHolder<ItemBean> {

    private TextView tv_time;
    private ImageView color;
    @Override
    public void setData(ItemBean item) {

        tv_time.setText(item.getMonth()+"月"+item.getDay()+"日");

        color.setImageResource(R.drawable.icon_round_black);
    }

    public ItemViewHolder2(ViewGroup parent) {
        super(parent,R.layout.item_view2);

        tv_time = (TextView) itemView.findViewById(R.id.time);
        color = (ImageView) itemView.findViewById(R.id.color);

    }


}
